// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.index.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.index.common.choice.IndexChoicesBean;
import com.echothree.model.control.index.common.choice.IndexFieldChoicesBean;
import com.echothree.model.control.index.common.choice.IndexTypeChoicesBean;
import com.echothree.model.control.index.common.transfer.IndexDescriptionTransfer;
import com.echothree.model.control.index.common.transfer.IndexFieldDescriptionTransfer;
import com.echothree.model.control.index.common.transfer.IndexFieldTransfer;
import com.echothree.model.control.index.common.transfer.IndexTransfer;
import com.echothree.model.control.index.common.transfer.IndexTypeDescriptionTransfer;
import com.echothree.model.control.index.common.transfer.IndexTypeTransfer;
import com.echothree.model.control.index.server.transfer.IndexDescriptionTransferCache;
import com.echothree.model.control.index.server.transfer.IndexFieldTransferCache;
import com.echothree.model.control.index.server.transfer.IndexTransferCache;
import com.echothree.model.control.index.server.transfer.IndexTransferCaches;
import com.echothree.model.control.index.server.transfer.IndexTypeDescriptionTransferCache;
import com.echothree.model.control.index.server.transfer.IndexTypeTransferCache;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.index.common.pk.IndexFieldPK;
import com.echothree.model.data.index.common.pk.IndexPK;
import com.echothree.model.data.index.common.pk.IndexTypePK;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexDescription;
import com.echothree.model.data.index.server.entity.IndexDetail;
import com.echothree.model.data.index.server.entity.IndexField;
import com.echothree.model.data.index.server.entity.IndexFieldDescription;
import com.echothree.model.data.index.server.entity.IndexFieldDetail;
import com.echothree.model.data.index.server.entity.IndexStatus;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.index.server.entity.IndexTypeDescription;
import com.echothree.model.data.index.server.entity.IndexTypeDetail;
import com.echothree.model.data.index.server.factory.IndexDescriptionFactory;
import com.echothree.model.data.index.server.factory.IndexDetailFactory;
import com.echothree.model.data.index.server.factory.IndexFactory;
import com.echothree.model.data.index.server.factory.IndexFieldDescriptionFactory;
import com.echothree.model.data.index.server.factory.IndexFieldDetailFactory;
import com.echothree.model.data.index.server.factory.IndexFieldFactory;
import com.echothree.model.data.index.server.factory.IndexStatusFactory;
import com.echothree.model.data.index.server.factory.IndexTypeDescriptionFactory;
import com.echothree.model.data.index.server.factory.IndexTypeDetailFactory;
import com.echothree.model.data.index.server.factory.IndexTypeFactory;
import com.echothree.model.data.index.server.value.IndexDescriptionValue;
import com.echothree.model.data.index.server.value.IndexDetailValue;
import com.echothree.model.data.index.server.value.IndexFieldDescriptionValue;
import com.echothree.model.data.index.server.value.IndexFieldDetailValue;
import com.echothree.model.data.index.server.value.IndexTypeDescriptionValue;
import com.echothree.model.data.index.server.value.IndexTypeDetailValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class IndexControl
        extends BaseModelControl {
    
    /** Creates a new instance of IndexControl */
    public IndexControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Index Transfer Caches
    // --------------------------------------------------------------------------------
    
    private IndexTransferCaches indexTransferCaches;
    
    public IndexTransferCaches getIndexTransferCaches(UserVisit userVisit) {
        if(indexTransferCaches == null) {
            indexTransferCaches = new IndexTransferCaches(userVisit, this);
        }
        
        return indexTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Index Types
    // --------------------------------------------------------------------------------

    public IndexType createIndexType(String indexTypeName, EntityType entityType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        IndexType defaultIndexType = getDefaultIndexType();
        boolean defaultFound = defaultIndexType != null;

        if(defaultFound && isDefault) {
            IndexTypeDetailValue defaultIndexTypeDetailValue = getDefaultIndexTypeDetailValueForUpdate();

            defaultIndexTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateIndexTypeFromValue(defaultIndexTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        IndexType indexType = IndexTypeFactory.getInstance().create();
        IndexTypeDetail indexTypeDetail = IndexTypeDetailFactory.getInstance().create(indexType, indexTypeName, entityType, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        indexType = IndexTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, indexType.getPrimaryKey());
        indexType.setActiveDetail(indexTypeDetail);
        indexType.setLastDetail(indexTypeDetail);
        indexType.store();

        sendEventUsingNames(indexType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return indexType;
    }

    public long countIndexTypesByEntityType(EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid AND idxtdt_ent_entitytypeid = ?",
                entityType);
    }

    public boolean isEntityTypeUsedByIndexTypes(EntityType entityType) {
        return countIndexTypesByEntityType(entityType) != 0;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.IndexType */
    public IndexType getIndexTypeByEntityInstance(EntityInstance entityInstance) {
        IndexTypePK pk = new IndexTypePK(entityInstance.getEntityUniqueId());
        IndexType indexType = IndexTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return indexType;
    }

    private static final Map<EntityPermission, String> getIndexTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "AND idxtdt_indextypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "AND idxtdt_indextypename = ? " +
                "FOR UPDATE");
        getIndexTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexType getIndexTypeByName(String indexTypeName, EntityPermission entityPermission) {
        return IndexTypeFactory.getInstance().getEntityFromQuery(entityPermission, getIndexTypeByNameQueries, indexTypeName);
    }

    public IndexType getIndexTypeByName(String indexTypeName) {
        return getIndexTypeByName(indexTypeName, EntityPermission.READ_ONLY);
    }

    public IndexType getIndexTypeByNameForUpdate(String indexTypeName) {
        return getIndexTypeByName(indexTypeName, EntityPermission.READ_WRITE);
    }

    public IndexTypeDetailValue getIndexTypeDetailValueForUpdate(IndexType indexType) {
        return indexType == null? null: indexType.getLastDetailForUpdate().getIndexTypeDetailValue().clone();
    }

    public IndexTypeDetailValue getIndexTypeDetailValueByNameForUpdate(String indexTypeName) {
        return getIndexTypeDetailValueForUpdate(getIndexTypeByNameForUpdate(indexTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultIndexTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "AND idxtdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "AND idxtdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultIndexTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexType getDefaultIndexType(EntityPermission entityPermission) {
        return IndexTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultIndexTypeQueries);
    }

    public IndexType getDefaultIndexType() {
        return getDefaultIndexType(EntityPermission.READ_ONLY);
    }

    public IndexType getDefaultIndexTypeForUpdate() {
        return getDefaultIndexType(EntityPermission.READ_WRITE);
    }

    public IndexTypeDetailValue getDefaultIndexTypeDetailValueForUpdate() {
        return getDefaultIndexTypeForUpdate().getLastDetailForUpdate().getIndexTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getIndexTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "ORDER BY idxtdt_sortorder, idxtdt_indextypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indextypes, indextypedetails " +
                "WHERE idxt_activedetailid = idxtdt_indextypedetailid " +
                "FOR UPDATE");
        getIndexTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexType> getIndexTypes(EntityPermission entityPermission) {
        return IndexTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexTypesQueries);
    }

    public List<IndexType> getIndexTypes() {
        return getIndexTypes(EntityPermission.READ_ONLY);
    }

    public List<IndexType> getIndexTypesForUpdate() {
        return getIndexTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getIndexTypesByEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indextypes, indextypedetails "
                + "WHERE idxt_activedetailid = idxtdt_indextypedetailid AND idxtdt_ent_entitytypeid = ? "
                + "ORDER BY idxtdt_sortorder, idxtdt_indextypename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indextypes, indextypedetails "
                + "WHERE idxt_activedetailid = idxtdt_indextypedetailid AND idxtdt_ent_entitytypeid = ? "
                + "FOR UPDATE");
        getIndexTypesByEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexType> getIndexTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        return IndexTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexTypesByEntityTypeQueries,
                entityType);
    }

    public List<IndexType> getIndexTypesByEntityType(EntityType entityType) {
        return getIndexTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<IndexType> getIndexTypesByEntityTypeForUpdate(EntityType entityType) {
        return getIndexTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }

   public IndexTypeTransfer getIndexTypeTransfer(UserVisit userVisit, IndexType indexType) {
        return getIndexTransferCaches(userVisit).getIndexTypeTransferCache().getIndexTypeTransfer(indexType);
    }

    public List<IndexTypeTransfer> getIndexTypeTransfers(UserVisit userVisit, List<IndexType> indexTypes) {
        List<IndexTypeTransfer> indexTypeTransfers = new ArrayList<>(indexTypes.size());
        IndexTypeTransferCache indexTypeTransferCache = getIndexTransferCaches(userVisit).getIndexTypeTransferCache();

        indexTypes.stream().forEach((indexType) -> {
            indexTypeTransfers.add(indexTypeTransferCache.getIndexTypeTransfer(indexType));
        });

        return indexTypeTransfers;
    }

    public List<IndexTypeTransfer> getIndexTypeTransfers(UserVisit userVisit) {
        return getIndexTypeTransfers(userVisit, getIndexTypes());
    }

    public List<IndexTypeTransfer> getIndexTypeTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getIndexTypeTransfers(userVisit, getIndexTypesByEntityType(entityType));
    }

    public IndexTypeChoicesBean getIndexTypeChoices(String defaultIndexTypeChoice, Language language, boolean allowNullChoice) {
        List<IndexType> indexTypes = getIndexTypes();
        int size = indexTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultIndexTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(IndexType indexType: indexTypes) {
            IndexTypeDetail indexTypeDetail = indexType.getLastDetail();

            String label = getBestIndexTypeDescription(indexType, language);
            String value = indexTypeDetail.getIndexTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultIndexTypeChoice == null? false: defaultIndexTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && indexTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new IndexTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateIndexTypeFromValue(IndexTypeDetailValue indexTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(indexTypeDetailValue.hasBeenModified()) {
            IndexType indexType = IndexTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     indexTypeDetailValue.getIndexTypePK());
            IndexTypeDetail indexTypeDetail = indexType.getActiveDetailForUpdate();

            indexTypeDetail.setThruTime(session.START_TIME_LONG);
            indexTypeDetail.store();

            IndexTypePK indexTypePK = indexTypeDetail.getIndexTypePK(); // Not updated
            String indexTypeName = indexTypeDetailValue.getIndexTypeName();
            EntityTypePK entityTypePK = indexTypeDetailValue.getEntityTypePK();
            Boolean isDefault = indexTypeDetailValue.getIsDefault();
            Integer sortOrder = indexTypeDetailValue.getSortOrder();

            if(checkDefault) {
                IndexType defaultIndexType = getDefaultIndexType();
                boolean defaultFound = defaultIndexType != null && !defaultIndexType.equals(indexType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    IndexTypeDetailValue defaultIndexTypeDetailValue = getDefaultIndexTypeDetailValueForUpdate();

                    defaultIndexTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateIndexTypeFromValue(defaultIndexTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            indexTypeDetail = IndexTypeDetailFactory.getInstance().create(indexTypePK, indexTypeName, entityTypePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            indexType.setActiveDetail(indexTypeDetail);
            indexType.setLastDetail(indexTypeDetail);

            sendEventUsingNames(indexTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateIndexTypeFromValue(IndexTypeDetailValue indexTypeDetailValue, BasePK updatedBy) {
        updateIndexTypeFromValue(indexTypeDetailValue, true, updatedBy);
    }

    private void deleteIndexType(IndexType indexType, boolean checkDefault, BasePK deletedBy) {
        IndexTypeDetail indexTypeDetail = indexType.getLastDetailForUpdate();

        deleteIndexFieldsByIndexType(indexType, deletedBy);
        deleteIndexesByIndexType(indexType, deletedBy);
        deleteIndexTypeDescriptionsByIndexType(indexType, deletedBy);

        indexTypeDetail.setThruTime(session.START_TIME_LONG);
        indexType.setActiveDetail(null);
        indexType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            IndexType defaultIndexType = getDefaultIndexType();

            if(defaultIndexType == null) {
                List<IndexType> indexTypes = getIndexTypesForUpdate();

                if(!indexTypes.isEmpty()) {
                    Iterator<IndexType> iter = indexTypes.iterator();
                    if(iter.hasNext()) {
                        defaultIndexType = iter.next();
                    }
                    IndexTypeDetailValue indexTypeDetailValue = defaultIndexType.getLastDetailForUpdate().getIndexTypeDetailValue().clone();

                    indexTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateIndexTypeFromValue(indexTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(indexType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteIndexType(IndexType indexType, BasePK deletedBy) {
        deleteIndexType(indexType, true, deletedBy);
    }

    private void deleteIndexTypes(List<IndexType> indexTypes, boolean checkDefault, BasePK deletedBy) {
        indexTypes.stream().forEach((indexType) -> {
            deleteIndexType(indexType, checkDefault, deletedBy);
        });
    }

    public void deleteIndexTypes(List<IndexType> indexTypes, BasePK deletedBy) {
        deleteIndexTypes(indexTypes, true, deletedBy);
    }

    public void deleteIndexTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteIndexTypes(getIndexTypesByEntityTypeForUpdate(entityType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Index Type Descriptions
    // --------------------------------------------------------------------------------

    public IndexTypeDescription createIndexTypeDescription(IndexType indexType, Language language, String description, BasePK createdBy) {
        IndexTypeDescription indexTypeDescription = IndexTypeDescriptionFactory.getInstance().create(indexType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(indexType.getPrimaryKey(), EventTypes.MODIFY.name(), indexTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return indexTypeDescription;
    }

    private static final Map<EntityPermission, String> getIndexTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indextypedescriptions " +
                "WHERE idxtd_idxt_indextypeid = ? AND idxtd_lang_languageid = ? AND idxtd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indextypedescriptions " +
                "WHERE idxtd_idxt_indextypeid = ? AND idxtd_lang_languageid = ? AND idxtd_thrutime = ? " +
                "FOR UPDATE");
        getIndexTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexTypeDescription getIndexTypeDescription(IndexType indexType, Language language, EntityPermission entityPermission) {
        return IndexTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getIndexTypeDescriptionQueries,
                indexType, language, Session.MAX_TIME);
    }

    public IndexTypeDescription getIndexTypeDescription(IndexType indexType, Language language) {
        return getIndexTypeDescription(indexType, language, EntityPermission.READ_ONLY);
    }

    public IndexTypeDescription getIndexTypeDescriptionForUpdate(IndexType indexType, Language language) {
        return getIndexTypeDescription(indexType, language, EntityPermission.READ_WRITE);
    }

    public IndexTypeDescriptionValue getIndexTypeDescriptionValue(IndexTypeDescription indexTypeDescription) {
        return indexTypeDescription == null? null: indexTypeDescription.getIndexTypeDescriptionValue().clone();
    }

    public IndexTypeDescriptionValue getIndexTypeDescriptionValueForUpdate(IndexType indexType, Language language) {
        return getIndexTypeDescriptionValue(getIndexTypeDescriptionForUpdate(indexType, language));
    }

    private static final Map<EntityPermission, String> getIndexTypeDescriptionsByIndexTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indextypedescriptions, languages " +
                "WHERE idxtd_idxt_indextypeid = ? AND idxtd_thrutime = ? AND idxtd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indextypedescriptions " +
                "WHERE idxtd_idxt_indextypeid = ? AND idxtd_thrutime = ? " +
                "FOR UPDATE");
        getIndexTypeDescriptionsByIndexTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexTypeDescription> getIndexTypeDescriptionsByIndexType(IndexType indexType, EntityPermission entityPermission) {
        return IndexTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexTypeDescriptionsByIndexTypeQueries,
                indexType, Session.MAX_TIME);
    }

    public List<IndexTypeDescription> getIndexTypeDescriptionsByIndexType(IndexType indexType) {
        return getIndexTypeDescriptionsByIndexType(indexType, EntityPermission.READ_ONLY);
    }

    public List<IndexTypeDescription> getIndexTypeDescriptionsByIndexTypeForUpdate(IndexType indexType) {
        return getIndexTypeDescriptionsByIndexType(indexType, EntityPermission.READ_WRITE);
    }

    public String getBestIndexTypeDescription(IndexType indexType, Language language) {
        String description;
        IndexTypeDescription indexTypeDescription = getIndexTypeDescription(indexType, language);

        if(indexTypeDescription == null && !language.getIsDefault()) {
            indexTypeDescription = getIndexTypeDescription(indexType, getPartyControl().getDefaultLanguage());
        }

        if(indexTypeDescription == null) {
            description = indexType.getLastDetail().getIndexTypeName();
        } else {
            description = indexTypeDescription.getDescription();
        }

        return description;
    }

    public IndexTypeDescriptionTransfer getIndexTypeDescriptionTransfer(UserVisit userVisit, IndexTypeDescription indexTypeDescription) {
        return getIndexTransferCaches(userVisit).getIndexTypeDescriptionTransferCache().getIndexTypeDescriptionTransfer(indexTypeDescription);
    }

    public List<IndexTypeDescriptionTransfer> getIndexTypeDescriptionTransfersByIndexType(UserVisit userVisit, IndexType indexType) {
        List<IndexTypeDescription> indexTypeDescriptions = getIndexTypeDescriptionsByIndexType(indexType);
        List<IndexTypeDescriptionTransfer> indexTypeDescriptionTransfers = new ArrayList<>(indexTypeDescriptions.size());
        IndexTypeDescriptionTransferCache indexTypeDescriptionTransferCache = getIndexTransferCaches(userVisit).getIndexTypeDescriptionTransferCache();

        indexTypeDescriptions.stream().forEach((indexTypeDescription) -> {
            indexTypeDescriptionTransfers.add(indexTypeDescriptionTransferCache.getIndexTypeDescriptionTransfer(indexTypeDescription));
        });

        return indexTypeDescriptionTransfers;
    }

    public void updateIndexTypeDescriptionFromValue(IndexTypeDescriptionValue indexTypeDescriptionValue, BasePK updatedBy) {
        if(indexTypeDescriptionValue.hasBeenModified()) {
            IndexTypeDescription indexTypeDescription = IndexTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    indexTypeDescriptionValue.getPrimaryKey());

            indexTypeDescription.setThruTime(session.START_TIME_LONG);
            indexTypeDescription.store();

            IndexType indexType = indexTypeDescription.getIndexType();
            Language language = indexTypeDescription.getLanguage();
            String description = indexTypeDescriptionValue.getDescription();

            indexTypeDescription = IndexTypeDescriptionFactory.getInstance().create(indexType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(indexType.getPrimaryKey(), EventTypes.MODIFY.name(), indexTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteIndexTypeDescription(IndexTypeDescription indexTypeDescription, BasePK deletedBy) {
        indexTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(indexTypeDescription.getIndexTypePK(), EventTypes.MODIFY.name(), indexTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteIndexTypeDescriptionsByIndexType(IndexType indexType, BasePK deletedBy) {
        List<IndexTypeDescription> indexTypeDescriptions = getIndexTypeDescriptionsByIndexTypeForUpdate(indexType);

        indexTypeDescriptions.stream().forEach((indexTypeDescription) -> {
            deleteIndexTypeDescription(indexTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Types
    // --------------------------------------------------------------------------------

    public IndexField createIndexField(IndexType indexType, String indexFieldName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        IndexField defaultIndexField = getDefaultIndexField(indexType);
        boolean defaultFound = defaultIndexField != null;

        if(defaultFound && isDefault) {
            IndexFieldDetailValue defaultIndexFieldDetailValue = getDefaultIndexFieldDetailValueForUpdate(indexType);

            defaultIndexFieldDetailValue.setIsDefault(Boolean.FALSE);
            updateIndexFieldFromValue(defaultIndexFieldDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        IndexField indexField = IndexFieldFactory.getInstance().create();
        IndexFieldDetail indexFieldDetail = IndexFieldDetailFactory.getInstance().create(session, indexField, indexType, indexFieldName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        indexField = IndexFieldFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                indexField.getPrimaryKey());
        indexField.setActiveDetail(indexFieldDetail);
        indexField.setLastDetail(indexFieldDetail);
        indexField.store();

        sendEventUsingNames(indexField.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return indexField;
    }

    private static final Map<EntityPermission, String> getIndexFieldsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid AND idxflddt_idxt_indextypeid = ? "
                + "ORDER BY idxflddt_sortorder, idxflddt_indexfieldname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid AND idxflddt_idxt_indextypeid = ? "
                + "FOR UPDATE");
        getIndexFieldsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexField> getIndexFields(IndexType indexType, EntityPermission entityPermission) {
        return IndexFieldFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexFieldsQueries,
                indexType);
    }

    public List<IndexField> getIndexFields(IndexType indexType) {
        return getIndexFields(indexType, EntityPermission.READ_ONLY);
    }

    public List<IndexField> getIndexFieldsForUpdate(IndexType indexType) {
        return getIndexFields(indexType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultIndexFieldQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid "
                + "AND idxflddt_idxt_indextypeid = ? AND idxflddt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid "
                + "AND idxflddt_idxt_indextypeid = ? AND idxflddt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultIndexFieldQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexField getDefaultIndexField(IndexType indexType, EntityPermission entityPermission) {
        return IndexFieldFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultIndexFieldQueries,
                indexType);
    }

    public IndexField getDefaultIndexField(IndexType indexType) {
        return getDefaultIndexField(indexType, EntityPermission.READ_ONLY);
    }

    public IndexField getDefaultIndexFieldForUpdate(IndexType indexType) {
        return getDefaultIndexField(indexType, EntityPermission.READ_WRITE);
    }

    public IndexFieldDetailValue getDefaultIndexFieldDetailValueForUpdate(IndexType indexType) {
        return getDefaultIndexFieldForUpdate(indexType).getLastDetailForUpdate().getIndexFieldDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getIndexFieldByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid "
                + "AND idxflddt_idxt_indextypeid = ? AND idxflddt_indexfieldname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexfields, indexfielddetails "
                + "WHERE idxfld_activedetailid = idxflddt_indexfielddetailid "
                + "AND idxflddt_idxt_indextypeid = ? AND idxflddt_indexfieldname = ? "
                + "FOR UPDATE");
        getIndexFieldByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexField getIndexFieldByName(IndexType indexType, String indexFieldName, EntityPermission entityPermission) {
        return IndexFieldFactory.getInstance().getEntityFromQuery(entityPermission, getIndexFieldByNameQueries,
                indexType, indexFieldName);
    }

    public IndexField getIndexFieldByName(IndexType indexType, String indexFieldName) {
        return getIndexFieldByName(indexType, indexFieldName, EntityPermission.READ_ONLY);
    }

    public IndexField getIndexFieldByNameForUpdate(IndexType indexType, String indexFieldName) {
        return getIndexFieldByName(indexType, indexFieldName, EntityPermission.READ_WRITE);
    }

    public IndexFieldDetailValue getIndexFieldDetailValueForUpdate(IndexField indexField) {
        return indexField == null? null: indexField.getLastDetailForUpdate().getIndexFieldDetailValue().clone();
    }

    public IndexFieldDetailValue getIndexFieldDetailValueByNameForUpdate(IndexType indexType, String indexFieldName) {
        return getIndexFieldDetailValueForUpdate(getIndexFieldByNameForUpdate(indexType, indexFieldName));
    }

    public IndexFieldChoicesBean getIndexFieldChoices(String defaultIndexFieldChoice, Language language, boolean allowNullChoice, IndexType indexType) {
        List<IndexField> indexFields = getIndexFields(indexType);
        int size = indexFields.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultIndexFieldChoice == null) {
                defaultValue = "";
            }
        }

        for(IndexField indexField: indexFields) {
            IndexFieldDetail indexFieldDetail = indexField.getLastDetail();
            String label = getBestIndexFieldDescription(indexField, language);
            String value = indexFieldDetail.getIndexFieldName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultIndexFieldChoice == null? false: defaultIndexFieldChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && indexFieldDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new IndexFieldChoicesBean(labels, values, defaultValue);
    }

    public IndexFieldTransfer getIndexFieldTransfer(UserVisit userVisit, IndexField indexField) {
        return getIndexTransferCaches(userVisit).getIndexFieldTransferCache().getIndexFieldTransfer(indexField);
    }

    public List<IndexFieldTransfer> getIndexFieldTransfersByIndexType(UserVisit userVisit, IndexType indexType) {
        List<IndexField> indexFields = getIndexFields(indexType);
        List<IndexFieldTransfer> indexFieldTransfers = new ArrayList<>(indexFields.size());
        IndexFieldTransferCache indexFieldTransferCache = getIndexTransferCaches(userVisit).getIndexFieldTransferCache();

        indexFields.stream().forEach((indexField) -> {
            indexFieldTransfers.add(indexFieldTransferCache.getIndexFieldTransfer(indexField));
        });

        return indexFieldTransfers;
    }

    private void updateIndexFieldFromValue(IndexFieldDetailValue indexFieldDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(indexFieldDetailValue.hasBeenModified()) {
            IndexField indexField = IndexFieldFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     indexFieldDetailValue.getIndexFieldPK());
            IndexFieldDetail indexFieldDetail = indexField.getActiveDetailForUpdate();

            indexFieldDetail.setThruTime(session.START_TIME_LONG);
            indexFieldDetail.store();

            IndexFieldPK indexFieldPK = indexFieldDetail.getIndexFieldPK();
            IndexType indexType = indexFieldDetail.getIndexType();
            IndexTypePK indexTypePK = indexType.getPrimaryKey();
            String indexFieldName = indexFieldDetailValue.getIndexFieldName();
            Boolean isDefault = indexFieldDetailValue.getIsDefault();
            Integer sortOrder = indexFieldDetailValue.getSortOrder();

            if(checkDefault) {
                IndexField defaultIndexField = getDefaultIndexField(indexType);
                boolean defaultFound = defaultIndexField != null && !defaultIndexField.equals(indexField);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    IndexFieldDetailValue defaultIndexFieldDetailValue = getDefaultIndexFieldDetailValueForUpdate(indexType);

                    defaultIndexFieldDetailValue.setIsDefault(Boolean.FALSE);
                    updateIndexFieldFromValue(defaultIndexFieldDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            indexFieldDetail = IndexFieldDetailFactory.getInstance().create(indexFieldPK, indexTypePK, indexFieldName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            indexField.setActiveDetail(indexFieldDetail);
            indexField.setLastDetail(indexFieldDetail);

            sendEventUsingNames(indexFieldPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateIndexFieldFromValue(IndexFieldDetailValue indexFieldDetailValue, BasePK updatedBy) {
        updateIndexFieldFromValue(indexFieldDetailValue, true, updatedBy);
    }

    public void deleteIndexField(IndexField indexField, BasePK deletedBy) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        
        deleteIndexFieldDescriptionsByIndexField(indexField, deletedBy);
        searchControl.deleteCachedSearchIndexFieldsByIndexField(indexField, deletedBy);
        
        IndexFieldDetail indexFieldDetail = indexField.getLastDetailForUpdate();
        indexFieldDetail.setThruTime(session.START_TIME_LONG);
        indexField.setActiveDetail(null);
        indexField.store();

        // Check for default, and pick one if necessary
        IndexType indexType = indexFieldDetail.getIndexType();
        IndexField defaultIndexField = getDefaultIndexField(indexType);
        if(defaultIndexField == null) {
            List<IndexField> indexFields = getIndexFieldsForUpdate(indexType);

            if(!indexFields.isEmpty()) {
                Iterator<IndexField> iter = indexFields.iterator();
                if(iter.hasNext()) {
                    defaultIndexField = iter.next();
                }
                IndexFieldDetailValue indexFieldDetailValue = defaultIndexField.getLastDetailForUpdate().getIndexFieldDetailValue().clone();

                indexFieldDetailValue.setIsDefault(Boolean.TRUE);
                updateIndexFieldFromValue(indexFieldDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(indexField.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteIndexFieldsByIndexType(IndexType indexType, BasePK deletedBy) {
        List<IndexField> indexFields = getIndexFieldsForUpdate(indexType);

        indexFields.stream().forEach((indexField) -> {
            deleteIndexField(indexField, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Type Descriptions
    // --------------------------------------------------------------------------------

    public IndexFieldDescription createIndexFieldDescription(IndexField indexField, Language language, String description,
            BasePK createdBy) {
        IndexFieldDescription indexFieldDescription = IndexFieldDescriptionFactory.getInstance().create(indexField,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(indexField.getPrimaryKey(), EventTypes.MODIFY.name(), indexFieldDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return indexFieldDescription;
    }

    private static final Map<EntityPermission, String> getIndexFieldDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexfielddescriptions "
                + "WHERE idxfldd_idxfld_indexfieldid = ? AND idxfldd_lang_languageid = ? AND idxfldd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexfielddescriptions "
                + "WHERE idxfldd_idxfld_indexfieldid = ? AND idxfldd_lang_languageid = ? AND idxfldd_thrutime = ? "
                + "FOR UPDATE");
        getIndexFieldDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexFieldDescription getIndexFieldDescription(IndexField indexField, Language language, EntityPermission entityPermission) {
        return IndexFieldDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getIndexFieldDescriptionQueries,
                indexField, language, Session.MAX_TIME);
    }

    public IndexFieldDescription getIndexFieldDescription(IndexField indexField, Language language) {
        return getIndexFieldDescription(indexField, language, EntityPermission.READ_ONLY);
    }

    public IndexFieldDescription getIndexFieldDescriptionForUpdate(IndexField indexField, Language language) {
        return getIndexFieldDescription(indexField, language, EntityPermission.READ_WRITE);
    }

    public IndexFieldDescriptionValue getIndexFieldDescriptionValue(IndexFieldDescription indexFieldDescription) {
        return indexFieldDescription == null? null: indexFieldDescription.getIndexFieldDescriptionValue().clone();
    }

    public IndexFieldDescriptionValue getIndexFieldDescriptionValueForUpdate(IndexField indexField, Language language) {
        return getIndexFieldDescriptionValue(getIndexFieldDescriptionForUpdate(indexField, language));
    }

    private static final Map<EntityPermission, String> getIndexFieldDescriptionsByIndexFieldQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexfielddescriptions, languages "
                + "WHERE idxfldd_idxfld_indexfieldid = ? AND idxfldd_thrutime = ? AND idxfldd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexfielddescriptions "
                + "WHERE idxfldd_idxfld_indexfieldid = ? AND idxfldd_thrutime = ? "
                + "FOR UPDATE");
        getIndexFieldDescriptionsByIndexFieldQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexFieldDescription> getIndexFieldDescriptionsByIndexField(IndexField indexField, EntityPermission entityPermission) {
        return IndexFieldDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexFieldDescriptionsByIndexFieldQueries,
                indexField, Session.MAX_TIME);
    }

    public List<IndexFieldDescription> getIndexFieldDescriptionsByIndexField(IndexField indexField) {
        return getIndexFieldDescriptionsByIndexField(indexField, EntityPermission.READ_ONLY);
    }

    public List<IndexFieldDescription> getIndexFieldDescriptionsByIndexFieldForUpdate(IndexField indexField) {
        return getIndexFieldDescriptionsByIndexField(indexField, EntityPermission.READ_WRITE);
    }

    public String getBestIndexFieldDescription(IndexField indexField, Language language) {
        String description;
        IndexFieldDescription indexFieldDescription = getIndexFieldDescription(indexField, language);

        if(indexFieldDescription == null && !language.getIsDefault()) {
            indexFieldDescription = getIndexFieldDescription(indexField, getPartyControl().getDefaultLanguage());
        }

        if(indexFieldDescription == null) {
            description = indexField.getLastDetail().getIndexFieldName();
        } else {
            description = indexFieldDescription.getDescription();
        }

        return description;
    }

    public IndexFieldDescriptionTransfer getIndexFieldDescriptionTransfer(UserVisit userVisit, IndexFieldDescription indexFieldDescription) {
        return getIndexTransferCaches(userVisit).getIndexFieldDescriptionTransferCache().getIndexFieldDescriptionTransfer(indexFieldDescription);
    }

    public List<IndexFieldDescriptionTransfer> getIndexFieldDescriptionTransfersByIndexField(UserVisit userVisit, IndexField indexField) {
        List<IndexFieldDescription> indexFieldDescriptions = getIndexFieldDescriptionsByIndexField(indexField);
        List<IndexFieldDescriptionTransfer> indexFieldDescriptionTransfers = new ArrayList<>(indexFieldDescriptions.size());

        indexFieldDescriptions.stream().forEach((indexFieldDescription) -> {
            indexFieldDescriptionTransfers.add(getIndexTransferCaches(userVisit).getIndexFieldDescriptionTransferCache().getIndexFieldDescriptionTransfer(indexFieldDescription));
        });

        return indexFieldDescriptionTransfers;
    }

    public void updateIndexFieldDescriptionFromValue(IndexFieldDescriptionValue indexFieldDescriptionValue, BasePK updatedBy) {
        if(indexFieldDescriptionValue.hasBeenModified()) {
            IndexFieldDescription indexFieldDescription = IndexFieldDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     indexFieldDescriptionValue.getPrimaryKey());

            indexFieldDescription.setThruTime(session.START_TIME_LONG);
            indexFieldDescription.store();

            IndexField indexField = indexFieldDescription.getIndexField();
            Language language = indexFieldDescription.getLanguage();
            String description = indexFieldDescriptionValue.getDescription();

            indexFieldDescription = IndexFieldDescriptionFactory.getInstance().create(indexField, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(indexField.getPrimaryKey(), EventTypes.MODIFY.name(), indexFieldDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteIndexFieldDescription(IndexFieldDescription indexFieldDescription, BasePK deletedBy) {
        indexFieldDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(indexFieldDescription.getIndexFieldPK(), EventTypes.MODIFY.name(), indexFieldDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteIndexFieldDescriptionsByIndexField(IndexField indexField, BasePK deletedBy) {
        List<IndexFieldDescription> indexFieldDescriptions = getIndexFieldDescriptionsByIndexFieldForUpdate(indexField);

        indexFieldDescriptions.stream().forEach((indexFieldDescription) -> {
            deleteIndexFieldDescription(indexFieldDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Indexes
    // --------------------------------------------------------------------------------

    public Index createIndex(String indexName, IndexType indexType, Language language, String directory, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Index defaultIndex = getDefaultIndex();
        boolean defaultFound = defaultIndex != null;

        if(defaultFound && isDefault) {
            IndexDetailValue defaultIndexDetailValue = getDefaultIndexDetailValueForUpdate();

            defaultIndexDetailValue.setIsDefault(Boolean.FALSE);
            updateIndexFromValue(defaultIndexDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Index index = IndexFactory.getInstance().create();
        IndexDetail indexDetail = IndexDetailFactory.getInstance().create(index, indexName, indexType, language, directory, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        index = IndexFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, index.getPrimaryKey());
        index.setActiveDetail(indexDetail);
        index.setLastDetail(indexDetail);
        index.store();

        sendEventUsingNames(index.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        createIndexStatus(index);
        
        return index;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Index */
    public Index getIndexByEntityInstance(EntityInstance entityInstance) {
        IndexPK pk = new IndexPK(entityInstance.getEntityUniqueId());
        Index index = IndexFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return index;
    }

    public int countIndexes() {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM indexes");
    }

    public int countIndexesByIndexType(IndexType indexType) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid AND idxdt_idxt_indextypeid = ?",
                indexType);
    }

    private static final Map<EntityPermission, String> getIndexQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_idxt_indextypeid = ? AND idxdt_lang_languageid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_idxt_indextypeid = ? AND idxdt_lang_languageid = ? " +
                "FOR UPDATE");
        getIndexQueries = Collections.unmodifiableMap(queryMap);
    }

    private Index getIndex(IndexType indexType, Language language, EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntityFromQuery(entityPermission, getIndexQueries, indexType, language);
    }

    public Index getIndex(IndexType indexType, Language language) {
        return getIndex(indexType, language, EntityPermission.READ_ONLY);
    }

    public Index getIndexForUpdate(IndexType indexType, Language language) {
        return getIndex(indexType, language, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getIndexByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_indexname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_indexname = ? " +
                "FOR UPDATE");
        getIndexByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Index getIndexByName(String indexName, EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntityFromQuery(entityPermission, getIndexByNameQueries, indexName);
    }

    public Index getIndexByName(String indexName) {
        return getIndexByName(indexName, EntityPermission.READ_ONLY);
    }

    public Index getIndexByNameForUpdate(String indexName) {
        return getIndexByName(indexName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getIndexByDirectoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_directory = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_directory = ? " +
                "FOR UPDATE");
        getIndexByDirectoryQueries = Collections.unmodifiableMap(queryMap);
    }

    private Index getIndexByDirectory(String directory, EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntityFromQuery(entityPermission, getIndexByDirectoryQueries, directory);
    }

    public Index getIndexByDirectory(String directory) {
        return getIndexByDirectory(directory, EntityPermission.READ_ONLY);
    }

    public Index getIndexByDirectoryForUpdate(String directory) {
        return getIndexByDirectory(directory, EntityPermission.READ_WRITE);
    }

    public IndexDetailValue getIndexDetailValueForUpdate(Index index) {
        return index == null? null: index.getLastDetailForUpdate().getIndexDetailValue().clone();
    }

    public IndexDetailValue getIndexDetailValueByNameForUpdate(String indexName) {
        return getIndexDetailValueForUpdate(getIndexByNameForUpdate(indexName));
    }

    private static final Map<EntityPermission, String> getDefaultIndexQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "AND idxdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultIndexQueries = Collections.unmodifiableMap(queryMap);
    }

    private Index getDefaultIndex(EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultIndexQueries);
    }

    public Index getDefaultIndex() {
        return getDefaultIndex(EntityPermission.READ_ONLY);
    }

    public Index getDefaultIndexForUpdate() {
        return getDefaultIndex(EntityPermission.READ_WRITE);
    }

    public IndexDetailValue getDefaultIndexDetailValueForUpdate() {
        return getDefaultIndexForUpdate().getLastDetailForUpdate().getIndexDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getIndexesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "ORDER BY idxdt_sortorder, idxdt_indexname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexes, indexdetails " +
                "WHERE idx_activedetailid = idxdt_indexdetailid " +
                "FOR UPDATE");
        getIndexesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Index> getIndexes(EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexesQueries);
    }

    public List<Index> getIndexes() {
        return getIndexes(EntityPermission.READ_ONLY);
    }

    public List<Index> getIndexesForUpdate() {
        return getIndexes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getIndexesByIndexTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexes, indexdetails "
                + "WHERE idx_activedetailid = idxdt_indexdetailid "
                + "AND idxdt_idxt_indextypeid = ? "
                + "ORDER BY idxdt_sortorder, idxdt_indexname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexes, indexdetails "
                + "WHERE idx_activedetailid = idxdt_indexdetailid "
                + "AND idxdt_idxt_indextypeid = ? "
                + "FOR UPDATE");
        getIndexesByIndexTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Index> getIndexesByIndexType(IndexType indexType, EntityPermission entityPermission) {
        return IndexFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexesByIndexTypeQueries,
                indexType);
    }

    public List<Index> getIndexesByIndexType(IndexType indexType) {
        return getIndexesByIndexType(indexType, EntityPermission.READ_ONLY);
    }

    public List<Index> getIndexesByIndexTypeForUpdate(IndexType indexType) {
        return getIndexesByIndexType(indexType, EntityPermission.READ_WRITE);
    }

    public Index getBestIndex(IndexType indexType, Language language) {
        Index index = getIndex(indexType, language);

        if(index == null && !language.getIsDefault()) {
            index = getIndex(indexType, getPartyControl().getDefaultLanguage());
        }

        return index;
    }

   public IndexTransfer getIndexTransfer(UserVisit userVisit, Index index) {
        return getIndexTransferCaches(userVisit).getIndexTransferCache().getIndexTransfer(index);
    }

    public List<IndexTransfer> getIndexTransfers(UserVisit userVisit) {
        List<Index> indexes = getIndexes();
        List<IndexTransfer> indexTransfers = new ArrayList<>(indexes.size());
        IndexTransferCache indexTransferCache = getIndexTransferCaches(userVisit).getIndexTransferCache();

        indexes.stream().forEach((index) -> {
            indexTransfers.add(indexTransferCache.getIndexTransfer(index));
        });

        return indexTransfers;
    }

    public IndexChoicesBean getIndexChoices(String defaultIndexChoice, Language language, boolean allowNullChoice) {
        List<Index> indexes = getIndexes();
        int size = indexes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultIndexChoice == null) {
                defaultValue = "";
            }
        }

        for(Index index: indexes) {
            IndexDetail indexDetail = index.getLastDetail();

            String label = getBestIndexDescription(index, language);
            String value = indexDetail.getIndexName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultIndexChoice == null? false: defaultIndexChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && indexDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new IndexChoicesBean(labels, values, defaultValue);
    }

    private void updateIndexFromValue(IndexDetailValue indexDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(indexDetailValue.hasBeenModified()) {
            Index index = IndexFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     indexDetailValue.getIndexPK());
            IndexDetail indexDetail = index.getActiveDetailForUpdate();

            indexDetail.setThruTime(session.START_TIME_LONG);
            indexDetail.store();

            IndexPK indexPK = indexDetail.getIndexPK(); // Not updated
            String indexName = indexDetailValue.getIndexName();
            IndexTypePK indexTypePK = indexDetail.getIndexTypePK(); // Not updated
            LanguagePK languagePK = indexDetail.getLanguagePK(); // Not updated
            String directory = indexDetailValue.getDirectory();
            Boolean isDefault = indexDetailValue.getIsDefault();
            Integer sortOrder = indexDetailValue.getSortOrder();

            if(checkDefault) {
                Index defaultIndex = getDefaultIndex();
                boolean defaultFound = defaultIndex != null && !defaultIndex.equals(index);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    IndexDetailValue defaultIndexDetailValue = getDefaultIndexDetailValueForUpdate();

                    defaultIndexDetailValue.setIsDefault(Boolean.FALSE);
                    updateIndexFromValue(defaultIndexDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            indexDetail = IndexDetailFactory.getInstance().create(indexPK, indexName, indexTypePK, languagePK, directory, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            index.setActiveDetail(indexDetail);
            index.setLastDetail(indexDetail);

            sendEventUsingNames(indexPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateIndexFromValue(IndexDetailValue indexDetailValue, BasePK updatedBy) {
        updateIndexFromValue(indexDetailValue, true, updatedBy);
    }

    private void deleteIndex(Index index, boolean checkDefault, BasePK deletedBy) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        IndexDetail indexDetail = index.getLastDetailForUpdate();

        searchControl.deleteCachedSearchesByIndex(index, deletedBy);
        removeIndexStatusByIndex(index);
        deleteIndexDescriptionsByIndex(index, deletedBy);

        indexDetail.setThruTime(session.START_TIME_LONG);
        index.setActiveDetail(null);
        index.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            Index defaultIndex = getDefaultIndex();

            if(defaultIndex == null) {
                List<Index> indexes = getIndexesForUpdate();

                if(!indexes.isEmpty()) {
                    Iterator<Index> iter = indexes.iterator();
                    if(iter.hasNext()) {
                        defaultIndex = iter.next();
                    }
                    IndexDetailValue indexDetailValue = defaultIndex.getLastDetailForUpdate().getIndexDetailValue().clone();

                    indexDetailValue.setIsDefault(Boolean.TRUE);
                    updateIndexFromValue(indexDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(index.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteIndex(Index index, BasePK deletedBy) {
        deleteIndex(index, true, deletedBy);
    }

    private void deleteIndexes(List<Index> indexes, boolean checkDefault, BasePK deletedBy) {
        indexes.stream().forEach((index) -> {
            deleteIndex(index, checkDefault, deletedBy);
        });
    }

    public void deleteIndexes(List<Index> indexes, BasePK deletedBy) {
        deleteIndexes(indexes, true, deletedBy);
    }

    public void deleteIndexesByIndexType(IndexType indexType, BasePK deletedBy) {
        deleteIndexes(getIndexesByIndexTypeForUpdate(indexType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Index Descriptions
    // --------------------------------------------------------------------------------

    public IndexDescription createIndexDescription(Index index, Language language, String description, BasePK createdBy) {
        IndexDescription indexDescription = IndexDescriptionFactory.getInstance().create(index, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(index.getPrimaryKey(), EventTypes.MODIFY.name(), indexDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return indexDescription;
    }

    private static final Map<EntityPermission, String> getIndexDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexdescriptions " +
                "WHERE idxd_idx_indexid = ? AND idxd_lang_languageid = ? AND idxd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexdescriptions " +
                "WHERE idxd_idx_indexid = ? AND idxd_lang_languageid = ? AND idxd_thrutime = ? " +
                "FOR UPDATE");
        getIndexDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexDescription getIndexDescription(Index index, Language language, EntityPermission entityPermission) {
        return IndexDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getIndexDescriptionQueries,
                index, language, Session.MAX_TIME);
    }

    public IndexDescription getIndexDescription(Index index, Language language) {
        return getIndexDescription(index, language, EntityPermission.READ_ONLY);
    }

    public IndexDescription getIndexDescriptionForUpdate(Index index, Language language) {
        return getIndexDescription(index, language, EntityPermission.READ_WRITE);
    }

    public IndexDescriptionValue getIndexDescriptionValue(IndexDescription indexDescription) {
        return indexDescription == null? null: indexDescription.getIndexDescriptionValue().clone();
    }

    public IndexDescriptionValue getIndexDescriptionValueForUpdate(Index index, Language language) {
        return getIndexDescriptionValue(getIndexDescriptionForUpdate(index, language));
    }

    private static final Map<EntityPermission, String> getIndexDescriptionsByIndexQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM indexdescriptions, languages " +
                "WHERE idxd_idx_indexid = ? AND idxd_thrutime = ? AND idxd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM indexdescriptions " +
                "WHERE idxd_idx_indexid = ? AND idxd_thrutime = ? " +
                "FOR UPDATE");
        getIndexDescriptionsByIndexQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<IndexDescription> getIndexDescriptionsByIndex(Index index, EntityPermission entityPermission) {
        return IndexDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getIndexDescriptionsByIndexQueries,
                index, Session.MAX_TIME);
    }

    public List<IndexDescription> getIndexDescriptionsByIndex(Index index) {
        return getIndexDescriptionsByIndex(index, EntityPermission.READ_ONLY);
    }

    public List<IndexDescription> getIndexDescriptionsByIndexForUpdate(Index index) {
        return getIndexDescriptionsByIndex(index, EntityPermission.READ_WRITE);
    }

    public String getBestIndexDescription(Index index, Language language) {
        String description;
        IndexDescription indexDescription = getIndexDescription(index, language);

        if(indexDescription == null && !language.getIsDefault()) {
            indexDescription = getIndexDescription(index, getPartyControl().getDefaultLanguage());
        }

        if(indexDescription == null) {
            description = index.getLastDetail().getIndexName();
        } else {
            description = indexDescription.getDescription();
        }

        return description;
    }

    public IndexDescriptionTransfer getIndexDescriptionTransfer(UserVisit userVisit, IndexDescription indexDescription) {
        return getIndexTransferCaches(userVisit).getIndexDescriptionTransferCache().getIndexDescriptionTransfer(indexDescription);
    }

    public List<IndexDescriptionTransfer> getIndexDescriptionTransfersByIndex(UserVisit userVisit, Index index) {
        List<IndexDescription> indexDescriptions = getIndexDescriptionsByIndex(index);
        List<IndexDescriptionTransfer> indexDescriptionTransfers = new ArrayList<>(indexDescriptions.size());
        IndexDescriptionTransferCache indexDescriptionTransferCache = getIndexTransferCaches(userVisit).getIndexDescriptionTransferCache();

        indexDescriptions.stream().forEach((indexDescription) -> {
            indexDescriptionTransfers.add(indexDescriptionTransferCache.getIndexDescriptionTransfer(indexDescription));
        });

        return indexDescriptionTransfers;
    }

    public void updateIndexDescriptionFromValue(IndexDescriptionValue indexDescriptionValue, BasePK updatedBy) {
        if(indexDescriptionValue.hasBeenModified()) {
            IndexDescription indexDescription = IndexDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    indexDescriptionValue.getPrimaryKey());

            indexDescription.setThruTime(session.START_TIME_LONG);
            indexDescription.store();

            Index index = indexDescription.getIndex();
            Language language = indexDescription.getLanguage();
            String description = indexDescriptionValue.getDescription();

            indexDescription = IndexDescriptionFactory.getInstance().create(index, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(index.getPrimaryKey(), EventTypes.MODIFY.name(), indexDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteIndexDescription(IndexDescription indexDescription, BasePK deletedBy) {
        indexDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(indexDescription.getIndexPK(), EventTypes.MODIFY.name(), indexDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteIndexDescriptionsByIndex(Index index, BasePK deletedBy) {
        List<IndexDescription> indexDescriptions = getIndexDescriptionsByIndexForUpdate(index);

        indexDescriptions.stream().forEach((indexDescription) -> {
            deleteIndexDescription(indexDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Index Statuses
    // --------------------------------------------------------------------------------
    
    public IndexStatus createIndexStatus(Index index) {
        return IndexStatusFactory.getInstance().create(index, null);
    }
    
    private static final Map<EntityPermission, String> getIndexStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM indexstatuses "
                + "WHERE idxst_idx_indexid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM indexstatuses "
                + "WHERE idxst_idx_indexid = ? "
                + "FOR UPDATE");
        getIndexStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    private IndexStatus getIndexStatus(Index index, EntityPermission entityPermission) {
        return IndexStatusFactory.getInstance().getEntityFromQuery(entityPermission, getIndexStatusQueries,
                index);
    }

    public IndexStatus getIndexStatus(Index index) {
        return getIndexStatus(index, EntityPermission.READ_ONLY);
    }
    
    public IndexStatus getIndexStatusForUpdate(Index index) {
        return getIndexStatus(index, EntityPermission.READ_WRITE);
    }
    
    public void removeIndexStatusByIndex(Index index) {
        IndexStatus indexStatus = getIndexStatusForUpdate(index);
        
        if(indexStatus != null) {
            indexStatus.remove();
        }
    }
    
}
