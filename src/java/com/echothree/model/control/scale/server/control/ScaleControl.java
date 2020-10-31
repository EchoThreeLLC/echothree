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

package com.echothree.model.control.scale.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.scale.common.choice.ScaleChoicesBean;
import com.echothree.model.control.scale.common.choice.ScaleTypeChoicesBean;
import com.echothree.model.control.scale.common.choice.ScaleUseTypeChoicesBean;
import com.echothree.model.control.scale.common.transfer.PartyScaleUseTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleDescriptionTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleTypeDescriptionTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleTypeTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleUseTypeDescriptionTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleUseTypeTransfer;
import com.echothree.model.control.scale.server.transfer.PartyScaleUseTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleDescriptionTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleTransferCaches;
import com.echothree.model.control.scale.server.transfer.ScaleTypeDescriptionTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleTypeTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleUseTypeDescriptionTransferCache;
import com.echothree.model.control.scale.server.transfer.ScaleUseTypeTransferCache;
import com.echothree.model.data.core.common.pk.ServerServicePK;
import com.echothree.model.data.core.server.entity.ServerService;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.scale.common.pk.ScalePK;
import com.echothree.model.data.scale.common.pk.ScaleTypePK;
import com.echothree.model.data.scale.common.pk.ScaleUseTypePK;
import com.echothree.model.data.scale.server.entity.PartyScaleUse;
import com.echothree.model.data.scale.server.entity.Scale;
import com.echothree.model.data.scale.server.entity.ScaleDescription;
import com.echothree.model.data.scale.server.entity.ScaleDetail;
import com.echothree.model.data.scale.server.entity.ScaleType;
import com.echothree.model.data.scale.server.entity.ScaleTypeDescription;
import com.echothree.model.data.scale.server.entity.ScaleTypeDetail;
import com.echothree.model.data.scale.server.entity.ScaleUseType;
import com.echothree.model.data.scale.server.entity.ScaleUseTypeDescription;
import com.echothree.model.data.scale.server.entity.ScaleUseTypeDetail;
import com.echothree.model.data.scale.server.factory.PartyScaleUseFactory;
import com.echothree.model.data.scale.server.factory.ScaleDescriptionFactory;
import com.echothree.model.data.scale.server.factory.ScaleDetailFactory;
import com.echothree.model.data.scale.server.factory.ScaleFactory;
import com.echothree.model.data.scale.server.factory.ScaleTypeDescriptionFactory;
import com.echothree.model.data.scale.server.factory.ScaleTypeDetailFactory;
import com.echothree.model.data.scale.server.factory.ScaleTypeFactory;
import com.echothree.model.data.scale.server.factory.ScaleUseTypeDescriptionFactory;
import com.echothree.model.data.scale.server.factory.ScaleUseTypeDetailFactory;
import com.echothree.model.data.scale.server.factory.ScaleUseTypeFactory;
import com.echothree.model.data.scale.server.value.PartyScaleUseValue;
import com.echothree.model.data.scale.server.value.ScaleDescriptionValue;
import com.echothree.model.data.scale.server.value.ScaleDetailValue;
import com.echothree.model.data.scale.server.value.ScaleTypeDescriptionValue;
import com.echothree.model.data.scale.server.value.ScaleTypeDetailValue;
import com.echothree.model.data.scale.server.value.ScaleUseTypeDescriptionValue;
import com.echothree.model.data.scale.server.value.ScaleUseTypeDetailValue;
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

public class ScaleControl
        extends BaseModelControl {
    
    /** Creates a new instance of ScaleControl */
    public ScaleControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Scale Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ScaleTransferCaches scaleTransferCaches;
    
    public ScaleTransferCaches getScaleTransferCaches(UserVisit userVisit) {
        if(scaleTransferCaches == null) {
            scaleTransferCaches = new ScaleTransferCaches(userVisit, this);
        }
        
        return scaleTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Scale Group Types
    // --------------------------------------------------------------------------------

    public ScaleType createScaleType(String scaleTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ScaleType defaultScaleType = getDefaultScaleType();
        boolean defaultFound = defaultScaleType != null;

        if(defaultFound && isDefault) {
            ScaleTypeDetailValue defaultScaleTypeDetailValue = getDefaultScaleTypeDetailValueForUpdate();

            defaultScaleTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateScaleTypeFromValue(defaultScaleTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ScaleType scaleType = ScaleTypeFactory.getInstance().create();
        ScaleTypeDetail scaleTypeDetail = ScaleTypeDetailFactory.getInstance().create(scaleType,
                scaleTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        scaleType = ScaleTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                scaleType.getPrimaryKey());
        scaleType.setActiveDetail(scaleTypeDetail);
        scaleType.setLastDetail(scaleTypeDetail);
        scaleType.store();

        sendEventUsingNames(scaleType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return scaleType;
    }

    private static final Map<EntityPermission, String> getScaleTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaletypes, scaletypedetails " +
                "WHERE scltyp_activedetailid = scltypdt_scaletypedetailid " +
                "AND scltypdt_scaletypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaletypes, scaletypedetails " +
                "WHERE scltyp_activedetailid = scltypdt_scaletypedetailid " +
                "AND scltypdt_scaletypename = ? " +
                "FOR UPDATE");
        getScaleTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ScaleType getScaleTypeByName(String scaleTypeName, EntityPermission entityPermission) {
        return ScaleTypeFactory.getInstance().getEntityFromQuery(entityPermission, getScaleTypeByNameQueries, scaleTypeName);
    }

    public ScaleType getScaleTypeByName(String scaleTypeName) {
        return getScaleTypeByName(scaleTypeName, EntityPermission.READ_ONLY);
    }

    public ScaleType getScaleTypeByNameForUpdate(String scaleTypeName) {
        return getScaleTypeByName(scaleTypeName, EntityPermission.READ_WRITE);
    }

    public ScaleTypeDetailValue getScaleTypeDetailValueForUpdate(ScaleType scaleType) {
        return scaleType == null? null: scaleType.getLastDetailForUpdate().getScaleTypeDetailValue().clone();
    }

    public ScaleTypeDetailValue getScaleTypeDetailValueByNameForUpdate(String scaleTypeName) {
        return getScaleTypeDetailValueForUpdate(getScaleTypeByNameForUpdate(scaleTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultScaleTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaletypes, scaletypedetails " +
                "WHERE scltyp_activedetailid = scltypdt_scaletypedetailid " +
                "AND scltypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaletypes, scaletypedetails " +
                "WHERE scltyp_activedetailid = scltypdt_scaletypedetailid " +
                "AND scltypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultScaleTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ScaleType getDefaultScaleType(EntityPermission entityPermission) {
        return ScaleTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultScaleTypeQueries);
    }

    public ScaleType getDefaultScaleType() {
        return getDefaultScaleType(EntityPermission.READ_ONLY);
    }

    public ScaleType getDefaultScaleTypeForUpdate() {
        return getDefaultScaleType(EntityPermission.READ_WRITE);
    }

    public ScaleTypeDetailValue getDefaultScaleTypeDetailValueForUpdate() {
        return getDefaultScaleTypeForUpdate().getLastDetailForUpdate().getScaleTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getScaleTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaletypes, scaletypedetails " +
                "WHERE scltyp_activedetailid = scltypdt_scaletypedetailid " +
                "ORDER BY scltypdt_sortorder, scltypdt_scaletypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaletypes, scaletypedetails " +
                "WHERE scltyp_activedetailid = scltypdt_scaletypedetailid " +
                "FOR UPDATE");
        getScaleTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ScaleType> getScaleTypes(EntityPermission entityPermission) {
        return ScaleTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getScaleTypesQueries);
    }

    public List<ScaleType> getScaleTypes() {
        return getScaleTypes(EntityPermission.READ_ONLY);
    }

    public List<ScaleType> getScaleTypesForUpdate() {
        return getScaleTypes(EntityPermission.READ_WRITE);
    }

    public ScaleTypeTransfer getScaleTypeTransfer(UserVisit userVisit, ScaleType scaleType) {
        return getScaleTransferCaches(userVisit).getScaleTypeTransferCache().getScaleTypeTransfer(scaleType);
    }

    public List<ScaleTypeTransfer> getScaleTypeTransfers(UserVisit userVisit, List<ScaleType> scaleTypes) {
        List<ScaleTypeTransfer> scaleTypeTransfers = new ArrayList<>(scaleTypes.size());
        ScaleTypeTransferCache scaleTypeTransferCache = getScaleTransferCaches(userVisit).getScaleTypeTransferCache();

        scaleTypes.stream().forEach((scaleType) -> {
            scaleTypeTransfers.add(scaleTypeTransferCache.getScaleTypeTransfer(scaleType));
        });

        return scaleTypeTransfers;
    }

    public List<ScaleTypeTransfer> getScaleTypeTransfers(UserVisit userVisit) {
        return getScaleTypeTransfers(userVisit, getScaleTypes());
    }

    public ScaleTypeChoicesBean getScaleTypeChoices(String defaultScaleTypeChoice, Language language, boolean allowNullChoice) {
        List<ScaleType> scaleTypes = getScaleTypes();
        int size = scaleTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultScaleTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(ScaleType scaleType: scaleTypes) {
            ScaleTypeDetail scaleTypeDetail = scaleType.getLastDetail();

            String label = getBestScaleTypeDescription(scaleType, language);
            String value = scaleTypeDetail.getScaleTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultScaleTypeChoice != null && defaultScaleTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && scaleTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ScaleTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateScaleTypeFromValue(ScaleTypeDetailValue scaleTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(scaleTypeDetailValue.hasBeenModified()) {
            ScaleType scaleType = ScaleTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     scaleTypeDetailValue.getScaleTypePK());
            ScaleTypeDetail scaleTypeDetail = scaleType.getActiveDetailForUpdate();

            scaleTypeDetail.setThruTime(session.START_TIME_LONG);
            scaleTypeDetail.store();

            ScaleTypePK scaleTypePK = scaleTypeDetail.getScaleTypePK(); // Not updated
            String scaleTypeName = scaleTypeDetailValue.getScaleTypeName();
            Boolean isDefault = scaleTypeDetailValue.getIsDefault();
            Integer sortOrder = scaleTypeDetailValue.getSortOrder();

            if(checkDefault) {
                ScaleType defaultScaleType = getDefaultScaleType();
                boolean defaultFound = defaultScaleType != null && !defaultScaleType.equals(scaleType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ScaleTypeDetailValue defaultScaleTypeDetailValue = getDefaultScaleTypeDetailValueForUpdate();

                    defaultScaleTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateScaleTypeFromValue(defaultScaleTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            scaleTypeDetail = ScaleTypeDetailFactory.getInstance().create(scaleTypePK, scaleTypeName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            scaleType.setActiveDetail(scaleTypeDetail);
            scaleType.setLastDetail(scaleTypeDetail);

            sendEventUsingNames(scaleTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateScaleTypeFromValue(ScaleTypeDetailValue scaleTypeDetailValue, BasePK updatedBy) {
        updateScaleTypeFromValue(scaleTypeDetailValue, true, updatedBy);
    }

    public void deleteScaleType(ScaleType scaleType, BasePK deletedBy) {
        deleteScaleTypeDescriptionsByScaleType(scaleType, deletedBy);
        deleteScalesByScaleType(scaleType, deletedBy);

        ScaleTypeDetail scaleTypeDetail = scaleType.getLastDetailForUpdate();
        scaleTypeDetail.setThruTime(session.START_TIME_LONG);
        scaleType.setActiveDetail(null);
        scaleType.store();

        // Check for default, and pick one if necessary
        ScaleType defaultScaleType = getDefaultScaleType();
        if(defaultScaleType == null) {
            List<ScaleType> scaleTypes = getScaleTypesForUpdate();

            if(!scaleTypes.isEmpty()) {
                Iterator<ScaleType> iter = scaleTypes.iterator();
                if(iter.hasNext()) {
                    defaultScaleType = iter.next();
                }
                ScaleTypeDetailValue scaleTypeDetailValue = defaultScaleType.getLastDetailForUpdate().getScaleTypeDetailValue().clone();

                scaleTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateScaleTypeFromValue(scaleTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(scaleType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Scale Group Type Descriptions
    // --------------------------------------------------------------------------------

    public ScaleTypeDescription createScaleTypeDescription(ScaleType scaleType,
            Language language, String description, BasePK createdBy) {
        ScaleTypeDescription scaleTypeDescription = ScaleTypeDescriptionFactory.getInstance().create(scaleType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(scaleType.getPrimaryKey(), EventTypes.MODIFY.name(), scaleTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return scaleTypeDescription;
    }

    private static final Map<EntityPermission, String> getScaleTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaletypedescriptions " +
                "WHERE scltypd_scltyp_scaletypeid = ? AND scltypd_lang_languageid = ? AND scltypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaletypedescriptions " +
                "WHERE scltypd_scltyp_scaletypeid = ? AND scltypd_lang_languageid = ? AND scltypd_thrutime = ? " +
                "FOR UPDATE");
        getScaleTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ScaleTypeDescription getScaleTypeDescription(ScaleType scaleType,
            Language language, EntityPermission entityPermission) {
        return ScaleTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getScaleTypeDescriptionQueries,
                scaleType, language, Session.MAX_TIME);
    }

    public ScaleTypeDescription getScaleTypeDescription(ScaleType scaleType, Language language) {
        return getScaleTypeDescription(scaleType, language, EntityPermission.READ_ONLY);
    }

    public ScaleTypeDescription getScaleTypeDescriptionForUpdate(ScaleType scaleType, Language language) {
        return getScaleTypeDescription(scaleType, language, EntityPermission.READ_WRITE);
    }

    public ScaleTypeDescriptionValue getScaleTypeDescriptionValue(ScaleTypeDescription scaleTypeDescription) {
        return scaleTypeDescription == null? null: scaleTypeDescription.getScaleTypeDescriptionValue().clone();
    }

    public ScaleTypeDescriptionValue getScaleTypeDescriptionValueForUpdate(ScaleType scaleType, Language language) {
        return getScaleTypeDescriptionValue(getScaleTypeDescriptionForUpdate(scaleType, language));
    }

    private static final Map<EntityPermission, String> getScaleTypeDescriptionsByScaleTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaletypedescriptions, languages " +
                "WHERE scltypd_scltyp_scaletypeid = ? AND scltypd_thrutime = ? AND scltypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaletypedescriptions " +
                "WHERE scltypd_scltyp_scaletypeid = ? AND scltypd_thrutime = ? " +
                "FOR UPDATE");
        getScaleTypeDescriptionsByScaleTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ScaleTypeDescription> getScaleTypeDescriptionsByScaleType(ScaleType scaleType,
            EntityPermission entityPermission) {
        return ScaleTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getScaleTypeDescriptionsByScaleTypeQueries,
                scaleType, Session.MAX_TIME);
    }

    public List<ScaleTypeDescription> getScaleTypeDescriptionsByScaleType(ScaleType scaleType) {
        return getScaleTypeDescriptionsByScaleType(scaleType, EntityPermission.READ_ONLY);
    }

    public List<ScaleTypeDescription> getScaleTypeDescriptionsByScaleTypeForUpdate(ScaleType scaleType) {
        return getScaleTypeDescriptionsByScaleType(scaleType, EntityPermission.READ_WRITE);
    }

    public String getBestScaleTypeDescription(ScaleType scaleType, Language language) {
        String description;
        ScaleTypeDescription scaleTypeDescription = getScaleTypeDescription(scaleType, language);

        if(scaleTypeDescription == null && !language.getIsDefault()) {
            scaleTypeDescription = getScaleTypeDescription(scaleType, getPartyControl().getDefaultLanguage());
        }

        if(scaleTypeDescription == null) {
            description = scaleType.getLastDetail().getScaleTypeName();
        } else {
            description = scaleTypeDescription.getDescription();
        }

        return description;
    }

    public ScaleTypeDescriptionTransfer getScaleTypeDescriptionTransfer(UserVisit userVisit, ScaleTypeDescription scaleTypeDescription) {
        return getScaleTransferCaches(userVisit).getScaleTypeDescriptionTransferCache().getScaleTypeDescriptionTransfer(scaleTypeDescription);
    }

    public List<ScaleTypeDescriptionTransfer> getScaleTypeDescriptionTransfersByScaleType(UserVisit userVisit, ScaleType scaleType) {
        List<ScaleTypeDescription> scaleTypeDescriptions = getScaleTypeDescriptionsByScaleType(scaleType);
        List<ScaleTypeDescriptionTransfer> scaleTypeDescriptionTransfers = new ArrayList<>(scaleTypeDescriptions.size());
        ScaleTypeDescriptionTransferCache scaleTypeDescriptionTransferCache = getScaleTransferCaches(userVisit).getScaleTypeDescriptionTransferCache();

        scaleTypeDescriptions.stream().forEach((scaleTypeDescription) -> {
            scaleTypeDescriptionTransfers.add(scaleTypeDescriptionTransferCache.getScaleTypeDescriptionTransfer(scaleTypeDescription));
        });

        return scaleTypeDescriptionTransfers;
    }

    public void updateScaleTypeDescriptionFromValue(ScaleTypeDescriptionValue scaleTypeDescriptionValue, BasePK updatedBy) {
        if(scaleTypeDescriptionValue.hasBeenModified()) {
            ScaleTypeDescription scaleTypeDescription = ScaleTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    scaleTypeDescriptionValue.getPrimaryKey());

            scaleTypeDescription.setThruTime(session.START_TIME_LONG);
            scaleTypeDescription.store();

            ScaleType scaleType = scaleTypeDescription.getScaleType();
            Language language = scaleTypeDescription.getLanguage();
            String description = scaleTypeDescriptionValue.getDescription();

            scaleTypeDescription = ScaleTypeDescriptionFactory.getInstance().create(scaleType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(scaleType.getPrimaryKey(), EventTypes.MODIFY.name(), scaleTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteScaleTypeDescription(ScaleTypeDescription scaleTypeDescription, BasePK deletedBy) {
        scaleTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(scaleTypeDescription.getScaleTypePK(), EventTypes.MODIFY.name(), scaleTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteScaleTypeDescriptionsByScaleType(ScaleType scaleType, BasePK deletedBy) {
        List<ScaleTypeDescription> scaleTypeDescriptions = getScaleTypeDescriptionsByScaleTypeForUpdate(scaleType);

        scaleTypeDescriptions.stream().forEach((scaleTypeDescription) -> {
            deleteScaleTypeDescription(scaleTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Scale Groups
    // --------------------------------------------------------------------------------

    public Scale createScale(String scaleName, ScaleType scaleType, ServerService serverService, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        Scale defaultScale = getDefaultScale();
        boolean defaultFound = defaultScale != null;

        if(defaultFound && isDefault) {
            ScaleDetailValue defaultScaleDetailValue = getDefaultScaleDetailValueForUpdate();

            defaultScaleDetailValue.setIsDefault(Boolean.FALSE);
            updateScaleFromValue(defaultScaleDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Scale scale = ScaleFactory.getInstance().create();
        ScaleDetail scaleDetail = ScaleDetailFactory.getInstance().create(scale, scaleName, scaleType, serverService, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        scale = ScaleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, scale.getPrimaryKey());
        scale.setActiveDetail(scaleDetail);
        scale.setLastDetail(scaleDetail);
        scale.store();

        sendEventUsingNames(scale.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return scale;
    }

    private static final Map<EntityPermission, String> getScaleByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scales, scaledetails " +
                "WHERE scl_activedetailid = scldt_scaledetailid " +
                "AND scldt_scalename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scales, scaledetails " +
                "WHERE scl_activedetailid = scldt_scaledetailid " +
                "AND scldt_scalename = ? " +
                "FOR UPDATE");
        getScaleByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Scale getScaleByName(String scaleName, EntityPermission entityPermission) {
        return ScaleFactory.getInstance().getEntityFromQuery(entityPermission, getScaleByNameQueries, scaleName);
    }

    public Scale getScaleByName(String scaleName) {
        return getScaleByName(scaleName, EntityPermission.READ_ONLY);
    }

    public Scale getScaleByNameForUpdate(String scaleName) {
        return getScaleByName(scaleName, EntityPermission.READ_WRITE);
    }

    public ScaleDetailValue getScaleDetailValueForUpdate(Scale scale) {
        return scale == null? null: scale.getLastDetailForUpdate().getScaleDetailValue().clone();
    }

    public ScaleDetailValue getScaleDetailValueByNameForUpdate(String scaleName) {
        return getScaleDetailValueForUpdate(getScaleByNameForUpdate(scaleName));
    }

    private static final Map<EntityPermission, String> getDefaultScaleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scales, scaledetails " +
                "WHERE scl_activedetailid = scldt_scaledetailid " +
                "AND scldt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scales, scaledetails " +
                "WHERE scl_activedetailid = scldt_scaledetailid " +
                "AND scldt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultScaleQueries = Collections.unmodifiableMap(queryMap);
    }

    private Scale getDefaultScale(EntityPermission entityPermission) {
        return ScaleFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultScaleQueries);
    }

    public Scale getDefaultScale() {
        return getDefaultScale(EntityPermission.READ_ONLY);
    }

    public Scale getDefaultScaleForUpdate() {
        return getDefaultScale(EntityPermission.READ_WRITE);
    }

    public ScaleDetailValue getDefaultScaleDetailValueForUpdate() {
        return getDefaultScaleForUpdate().getLastDetailForUpdate().getScaleDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getScalesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scales, scaledetails " +
                "WHERE scl_activedetailid = scldt_scaledetailid " +
                "ORDER BY scldt_sortorder, scldt_scalename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scales, scaledetails " +
                "WHERE scl_activedetailid = scldt_scaledetailid " +
                "FOR UPDATE");
        getScalesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Scale> getScales(EntityPermission entityPermission) {
        return ScaleFactory.getInstance().getEntitiesFromQuery(entityPermission, getScalesQueries);
    }

    public List<Scale> getScales() {
        return getScales(EntityPermission.READ_ONLY);
    }

    public List<Scale> getScalesForUpdate() {
        return getScales(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getScalesByScaleTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scales, scaledetails " +
                "WHERE scl_activedetailid = scldt_scaledetailid " +
                "AND scldt_scltyp_scaletypeid = ? " +
                "ORDER BY scldt_sortorder, scldt_scalename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scales, scaledetails " +
                "WHERE scl_activedetailid = scldt_scaledetailid " +
                "AND scldt_scltyp_scaletypeid = ? " +
                "FOR UPDATE");
        getScalesByScaleTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Scale> getScalesByScaleType(ScaleType scaleType, EntityPermission entityPermission) {
        return ScaleFactory.getInstance().getEntitiesFromQuery(entityPermission, getScalesByScaleTypeQueries,
                scaleType);
    }

    public List<Scale> getScalesByScaleType(ScaleType scaleType) {
        return getScalesByScaleType(scaleType, EntityPermission.READ_ONLY);
    }

    public List<Scale> getScalesByScaleTypeForUpdate(ScaleType scaleType) {
        return getScalesByScaleType(scaleType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getScalesByServerServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scales, scaledetails " +
                "WHERE scl_activedetailid = scldt_scaledetailid " +
                "AND scldt_servsrv_serverserviceid = ? " +
                "ORDER BY scldt_sortorder, scldt_scalename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scales, scaledetails " +
                "WHERE scl_activedetailid = scldt_scaledetailid " +
                "AND scldt_servsrv_serverserviceid = ? " +
                "FOR UPDATE");
        getScalesByServerServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Scale> getScalesByServerService(ServerService serverService, EntityPermission entityPermission) {
        return ScaleFactory.getInstance().getEntitiesFromQuery(entityPermission, getScalesByServerServiceQueries,
                serverService);
    }

    public List<Scale> getScalesByServerService(ServerService serverService) {
        return getScalesByServerService(serverService, EntityPermission.READ_ONLY);
    }

    public List<Scale> getScalesByServerServiceForUpdate(ServerService serverService) {
        return getScalesByServerService(serverService, EntityPermission.READ_WRITE);
    }

    public ScaleTransfer getScaleTransfer(UserVisit userVisit, Scale scale) {
        return getScaleTransferCaches(userVisit).getScaleTransferCache().getScaleTransfer(scale);
    }

    public List<ScaleTransfer> getScaleTransfers(UserVisit userVisit, List<Scale> scales) {
        List<ScaleTransfer> scaleTransfers = new ArrayList<>(scales.size());
        ScaleTransferCache scaleTransferCache = getScaleTransferCaches(userVisit).getScaleTransferCache();

        scales.stream().forEach((scale) -> {
            scaleTransfers.add(scaleTransferCache.getScaleTransfer(scale));
        });

        return scaleTransfers;
    }

    public List<ScaleTransfer> getScaleTransfers(UserVisit userVisit) {
        return getScaleTransfers(userVisit, getScales());
    }

    public ScaleChoicesBean getScaleChoices(String defaultScaleChoice, Language language, boolean allowNullChoice) {
        List<Scale> scales = getScales();
        int size = scales.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultScaleChoice == null) {
                defaultValue = "";
            }
        }

        for(Scale scale: scales) {
            ScaleDetail scaleDetail = scale.getLastDetail();

            String label = getBestScaleDescription(scale, language);
            String value = scaleDetail.getScaleName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultScaleChoice != null && defaultScaleChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && scaleDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ScaleChoicesBean(labels, values, defaultValue);
    }

    private void updateScaleFromValue(ScaleDetailValue scaleDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(scaleDetailValue.hasBeenModified()) {
            Scale scale = ScaleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     scaleDetailValue.getScalePK());
            ScaleDetail scaleDetail = scale.getActiveDetailForUpdate();

            scaleDetail.setThruTime(session.START_TIME_LONG);
            scaleDetail.store();

            ScalePK scalePK = scaleDetail.getScalePK(); // Not updated
            String scaleName = scaleDetailValue.getScaleName();
            ScaleTypePK scaleTypePK = scaleDetailValue.getScaleTypePK();
            ServerServicePK serverServicePK = scaleDetailValue.getServerServicePK();
            Boolean isDefault = scaleDetailValue.getIsDefault();
            Integer sortOrder = scaleDetailValue.getSortOrder();

            if(checkDefault) {
                Scale defaultScale = getDefaultScale();
                boolean defaultFound = defaultScale != null && !defaultScale.equals(scale);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ScaleDetailValue defaultScaleDetailValue = getDefaultScaleDetailValueForUpdate();

                    defaultScaleDetailValue.setIsDefault(Boolean.FALSE);
                    updateScaleFromValue(defaultScaleDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            scaleDetail = ScaleDetailFactory.getInstance().create(scalePK, scaleName, scaleTypePK, serverServicePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            scale.setActiveDetail(scaleDetail);
            scale.setLastDetail(scaleDetail);

            sendEventUsingNames(scalePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateScaleFromValue(ScaleDetailValue scaleDetailValue, BasePK updatedBy) {
        updateScaleFromValue(scaleDetailValue, true, updatedBy);
    }

    private void deleteScale(Scale scale, boolean checkDefault, BasePK deletedBy) {
        ScaleDetail scaleDetail = scale.getLastDetailForUpdate();

        deleteScaleDescriptionsByScale(scale, deletedBy);
        deletePartyScaleUsesByScale(scale, deletedBy);

        scaleDetail.setThruTime(session.START_TIME_LONG);
        scale.setActiveDetail(null);
        scale.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            Scale defaultScale = getDefaultScale();

            if(defaultScale == null) {
                List<Scale> scales = getScalesForUpdate();

                if(!scales.isEmpty()) {
                    Iterator<Scale> iter = scales.iterator();
                    if(iter.hasNext()) {
                        defaultScale = iter.next();
                    }
                    ScaleDetailValue scaleDetailValue = defaultScale.getLastDetailForUpdate().getScaleDetailValue().clone();

                    scaleDetailValue.setIsDefault(Boolean.TRUE);
                    updateScaleFromValue(scaleDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(scale.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteScale(Scale itemDescriptionType, BasePK deletedBy) {
        deleteScale(itemDescriptionType, true, deletedBy);
    }

    private void deleteScales(List<Scale> itemDescriptionTypes, boolean checkDefault, BasePK deletedBy) {
        itemDescriptionTypes.stream().forEach((itemDescriptionType) -> {
            deleteScale(itemDescriptionType, checkDefault, deletedBy);
        });
    }

    public void deleteScales(List<Scale> itemDescriptionTypes, BasePK deletedBy) {
        deleteScales(itemDescriptionTypes, true, deletedBy);
    }

    public void deleteScalesByScaleType(ScaleType scaleType, BasePK deletedBy) {
        deleteScales(getScalesByScaleTypeForUpdate(scaleType), false, deletedBy);
    }

    public void deleteScalesByServerService(ServerService serverService, BasePK deletedBy) {
        deleteScales(getScalesByServerServiceForUpdate(serverService), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Scale Group Descriptions
    // --------------------------------------------------------------------------------

    public ScaleDescription createScaleDescription(Scale scale,
            Language language, String description, BasePK createdBy) {
        ScaleDescription scaleDescription = ScaleDescriptionFactory.getInstance().create(scale,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(scale.getPrimaryKey(), EventTypes.MODIFY.name(), scaleDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return scaleDescription;
    }

    private static final Map<EntityPermission, String> getScaleDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaledescriptions " +
                "WHERE scld_scl_scaleid = ? AND scld_lang_languageid = ? AND scld_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaledescriptions " +
                "WHERE scld_scl_scaleid = ? AND scld_lang_languageid = ? AND scld_thrutime = ? " +
                "FOR UPDATE");
        getScaleDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ScaleDescription getScaleDescription(Scale scale,
            Language language, EntityPermission entityPermission) {
        return ScaleDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getScaleDescriptionQueries,
                scale, language, Session.MAX_TIME);
    }

    public ScaleDescription getScaleDescription(Scale scale, Language language) {
        return getScaleDescription(scale, language, EntityPermission.READ_ONLY);
    }

    public ScaleDescription getScaleDescriptionForUpdate(Scale scale, Language language) {
        return getScaleDescription(scale, language, EntityPermission.READ_WRITE);
    }

    public ScaleDescriptionValue getScaleDescriptionValue(ScaleDescription scaleDescription) {
        return scaleDescription == null? null: scaleDescription.getScaleDescriptionValue().clone();
    }

    public ScaleDescriptionValue getScaleDescriptionValueForUpdate(Scale scale, Language language) {
        return getScaleDescriptionValue(getScaleDescriptionForUpdate(scale, language));
    }

    private static final Map<EntityPermission, String> getScaleDescriptionsByScaleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaledescriptions, languages " +
                "WHERE scld_scl_scaleid = ? AND scld_thrutime = ? AND scld_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaledescriptions " +
                "WHERE scld_scl_scaleid = ? AND scld_thrutime = ? " +
                "FOR UPDATE");
        getScaleDescriptionsByScaleQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ScaleDescription> getScaleDescriptionsByScale(Scale scale,
            EntityPermission entityPermission) {
        return ScaleDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getScaleDescriptionsByScaleQueries,
                scale, Session.MAX_TIME);
    }

    public List<ScaleDescription> getScaleDescriptionsByScale(Scale scale) {
        return getScaleDescriptionsByScale(scale, EntityPermission.READ_ONLY);
    }

    public List<ScaleDescription> getScaleDescriptionsByScaleForUpdate(Scale scale) {
        return getScaleDescriptionsByScale(scale, EntityPermission.READ_WRITE);
    }

    public String getBestScaleDescription(Scale scale, Language language) {
        String description;
        ScaleDescription scaleDescription = getScaleDescription(scale, language);

        if(scaleDescription == null && !language.getIsDefault()) {
            scaleDescription = getScaleDescription(scale, getPartyControl().getDefaultLanguage());
        }

        if(scaleDescription == null) {
            description = scale.getLastDetail().getScaleName();
        } else {
            description = scaleDescription.getDescription();
        }

        return description;
    }

    public ScaleDescriptionTransfer getScaleDescriptionTransfer(UserVisit userVisit, ScaleDescription scaleDescription) {
        return getScaleTransferCaches(userVisit).getScaleDescriptionTransferCache().getScaleDescriptionTransfer(scaleDescription);
    }

    public List<ScaleDescriptionTransfer> getScaleDescriptionTransfersByScale(UserVisit userVisit, Scale scale) {
        List<ScaleDescription> scaleDescriptions = getScaleDescriptionsByScale(scale);
        List<ScaleDescriptionTransfer> scaleDescriptionTransfers = new ArrayList<>(scaleDescriptions.size());
        ScaleDescriptionTransferCache scaleDescriptionTransferCache = getScaleTransferCaches(userVisit).getScaleDescriptionTransferCache();

        scaleDescriptions.stream().forEach((scaleDescription) -> {
            scaleDescriptionTransfers.add(scaleDescriptionTransferCache.getScaleDescriptionTransfer(scaleDescription));
        });

        return scaleDescriptionTransfers;
    }

    public void updateScaleDescriptionFromValue(ScaleDescriptionValue scaleDescriptionValue, BasePK updatedBy) {
        if(scaleDescriptionValue.hasBeenModified()) {
            ScaleDescription scaleDescription = ScaleDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    scaleDescriptionValue.getPrimaryKey());

            scaleDescription.setThruTime(session.START_TIME_LONG);
            scaleDescription.store();

            Scale scale = scaleDescription.getScale();
            Language language = scaleDescription.getLanguage();
            String description = scaleDescriptionValue.getDescription();

            scaleDescription = ScaleDescriptionFactory.getInstance().create(scale, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(scale.getPrimaryKey(), EventTypes.MODIFY.name(), scaleDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteScaleDescription(ScaleDescription scaleDescription, BasePK deletedBy) {
        scaleDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(scaleDescription.getScalePK(), EventTypes.MODIFY.name(), scaleDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteScaleDescriptionsByScale(Scale scale, BasePK deletedBy) {
        List<ScaleDescription> scaleDescriptions = getScaleDescriptionsByScaleForUpdate(scale);

        scaleDescriptions.stream().forEach((scaleDescription) -> {
            deleteScaleDescription(scaleDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Scale Group Use Types
    // --------------------------------------------------------------------------------

    public ScaleUseType createScaleUseType(String scaleUseTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ScaleUseType defaultScaleUseType = getDefaultScaleUseType();
        boolean defaultFound = defaultScaleUseType != null;

        if(defaultFound && isDefault) {
            ScaleUseTypeDetailValue defaultScaleUseTypeDetailValue = getDefaultScaleUseTypeDetailValueForUpdate();

            defaultScaleUseTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateScaleUseTypeFromValue(defaultScaleUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ScaleUseType scaleUseType = ScaleUseTypeFactory.getInstance().create();
        ScaleUseTypeDetail scaleUseTypeDetail = ScaleUseTypeDetailFactory.getInstance().create(scaleUseType,
                scaleUseTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        scaleUseType = ScaleUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                scaleUseType.getPrimaryKey());
        scaleUseType.setActiveDetail(scaleUseTypeDetail);
        scaleUseType.setLastDetail(scaleUseTypeDetail);
        scaleUseType.store();

        sendEventUsingNames(scaleUseType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return scaleUseType;
    }

    private static final Map<EntityPermission, String> getScaleUseTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaleusetypes, scaleusetypedetails " +
                "WHERE sclusetyp_activedetailid = sclusetypdt_scaleusetypedetailid " +
                "AND sclusetypdt_scaleusetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaleusetypes, scaleusetypedetails " +
                "WHERE sclusetyp_activedetailid = sclusetypdt_scaleusetypedetailid " +
                "AND sclusetypdt_scaleusetypename = ? " +
                "FOR UPDATE");
        getScaleUseTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ScaleUseType getScaleUseTypeByName(String scaleUseTypeName, EntityPermission entityPermission) {
        return ScaleUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getScaleUseTypeByNameQueries, scaleUseTypeName);
    }

    public ScaleUseType getScaleUseTypeByName(String scaleUseTypeName) {
        return getScaleUseTypeByName(scaleUseTypeName, EntityPermission.READ_ONLY);
    }

    public ScaleUseType getScaleUseTypeByNameForUpdate(String scaleUseTypeName) {
        return getScaleUseTypeByName(scaleUseTypeName, EntityPermission.READ_WRITE);
    }

    public ScaleUseTypeDetailValue getScaleUseTypeDetailValueForUpdate(ScaleUseType scaleUseType) {
        return scaleUseType == null? null: scaleUseType.getLastDetailForUpdate().getScaleUseTypeDetailValue().clone();
    }

    public ScaleUseTypeDetailValue getScaleUseTypeDetailValueByNameForUpdate(String scaleUseTypeName) {
        return getScaleUseTypeDetailValueForUpdate(getScaleUseTypeByNameForUpdate(scaleUseTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultScaleUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaleusetypes, scaleusetypedetails " +
                "WHERE sclusetyp_activedetailid = sclusetypdt_scaleusetypedetailid " +
                "AND sclusetypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaleusetypes, scaleusetypedetails " +
                "WHERE sclusetyp_activedetailid = sclusetypdt_scaleusetypedetailid " +
                "AND sclusetypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultScaleUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ScaleUseType getDefaultScaleUseType(EntityPermission entityPermission) {
        return ScaleUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultScaleUseTypeQueries);
    }

    public ScaleUseType getDefaultScaleUseType() {
        return getDefaultScaleUseType(EntityPermission.READ_ONLY);
    }

    public ScaleUseType getDefaultScaleUseTypeForUpdate() {
        return getDefaultScaleUseType(EntityPermission.READ_WRITE);
    }

    public ScaleUseTypeDetailValue getDefaultScaleUseTypeDetailValueForUpdate() {
        return getDefaultScaleUseTypeForUpdate().getLastDetailForUpdate().getScaleUseTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getScaleUseTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaleusetypes, scaleusetypedetails " +
                "WHERE sclusetyp_activedetailid = sclusetypdt_scaleusetypedetailid " +
                "ORDER BY sclusetypdt_sortorder, sclusetypdt_scaleusetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaleusetypes, scaleusetypedetails " +
                "WHERE sclusetyp_activedetailid = sclusetypdt_scaleusetypedetailid " +
                "FOR UPDATE");
        getScaleUseTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ScaleUseType> getScaleUseTypes(EntityPermission entityPermission) {
        return ScaleUseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getScaleUseTypesQueries);
    }

    public List<ScaleUseType> getScaleUseTypes() {
        return getScaleUseTypes(EntityPermission.READ_ONLY);
    }

    public List<ScaleUseType> getScaleUseTypesForUpdate() {
        return getScaleUseTypes(EntityPermission.READ_WRITE);
    }

    public ScaleUseTypeTransfer getScaleUseTypeTransfer(UserVisit userVisit, ScaleUseType scaleUseType) {
        return getScaleTransferCaches(userVisit).getScaleUseTypeTransferCache().getScaleUseTypeTransfer(scaleUseType);
    }

    public List<ScaleUseTypeTransfer> getScaleUseTypeTransfers(UserVisit userVisit, List<ScaleUseType> scaleUseTypes) {
        List<ScaleUseTypeTransfer> scaleUseTypeTransfers = new ArrayList<>(scaleUseTypes.size());
        ScaleUseTypeTransferCache scaleUseTypeTransferCache = getScaleTransferCaches(userVisit).getScaleUseTypeTransferCache();

        scaleUseTypes.stream().forEach((scaleUseType) -> {
            scaleUseTypeTransfers.add(scaleUseTypeTransferCache.getScaleUseTypeTransfer(scaleUseType));
        });

        return scaleUseTypeTransfers;
    }

    public List<ScaleUseTypeTransfer> getScaleUseTypeTransfers(UserVisit userVisit) {
        return getScaleUseTypeTransfers(userVisit, getScaleUseTypes());
    }

    public ScaleUseTypeChoicesBean getScaleUseTypeChoices(String defaultScaleUseTypeChoice, Language language, boolean allowNullChoice) {
        List<ScaleUseType> scaleUseTypes = getScaleUseTypes();
        int size = scaleUseTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultScaleUseTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(ScaleUseType scaleUseType: scaleUseTypes) {
            ScaleUseTypeDetail scaleUseTypeDetail = scaleUseType.getLastDetail();

            String label = getBestScaleUseTypeDescription(scaleUseType, language);
            String value = scaleUseTypeDetail.getScaleUseTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultScaleUseTypeChoice != null && defaultScaleUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && scaleUseTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ScaleUseTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateScaleUseTypeFromValue(ScaleUseTypeDetailValue scaleUseTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(scaleUseTypeDetailValue.hasBeenModified()) {
            ScaleUseType scaleUseType = ScaleUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     scaleUseTypeDetailValue.getScaleUseTypePK());
            ScaleUseTypeDetail scaleUseTypeDetail = scaleUseType.getActiveDetailForUpdate();

            scaleUseTypeDetail.setThruTime(session.START_TIME_LONG);
            scaleUseTypeDetail.store();

            ScaleUseTypePK scaleUseTypePK = scaleUseTypeDetail.getScaleUseTypePK(); // Not updated
            String scaleUseTypeName = scaleUseTypeDetailValue.getScaleUseTypeName();
            Boolean isDefault = scaleUseTypeDetailValue.getIsDefault();
            Integer sortOrder = scaleUseTypeDetailValue.getSortOrder();

            if(checkDefault) {
                ScaleUseType defaultScaleUseType = getDefaultScaleUseType();
                boolean defaultFound = defaultScaleUseType != null && !defaultScaleUseType.equals(scaleUseType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ScaleUseTypeDetailValue defaultScaleUseTypeDetailValue = getDefaultScaleUseTypeDetailValueForUpdate();

                    defaultScaleUseTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateScaleUseTypeFromValue(defaultScaleUseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            scaleUseTypeDetail = ScaleUseTypeDetailFactory.getInstance().create(scaleUseTypePK, scaleUseTypeName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            scaleUseType.setActiveDetail(scaleUseTypeDetail);
            scaleUseType.setLastDetail(scaleUseTypeDetail);

            sendEventUsingNames(scaleUseTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateScaleUseTypeFromValue(ScaleUseTypeDetailValue scaleUseTypeDetailValue, BasePK updatedBy) {
        updateScaleUseTypeFromValue(scaleUseTypeDetailValue, true, updatedBy);
    }

    public void deleteScaleUseType(ScaleUseType scaleUseType, BasePK deletedBy) {
        deleteScaleUseTypeDescriptionsByScaleUseType(scaleUseType, deletedBy);
        deletePartyScaleUsesByScaleUseType(scaleUseType, deletedBy);

        ScaleUseTypeDetail scaleUseTypeDetail = scaleUseType.getLastDetailForUpdate();
        scaleUseTypeDetail.setThruTime(session.START_TIME_LONG);
        scaleUseType.setActiveDetail(null);
        scaleUseType.store();

        // Check for default, and pick one if necessary
        ScaleUseType defaultScaleUseType = getDefaultScaleUseType();
        if(defaultScaleUseType == null) {
            List<ScaleUseType> scaleUseTypes = getScaleUseTypesForUpdate();

            if(!scaleUseTypes.isEmpty()) {
                Iterator<ScaleUseType> iter = scaleUseTypes.iterator();
                if(iter.hasNext()) {
                    defaultScaleUseType = iter.next();
                }
                ScaleUseTypeDetailValue scaleUseTypeDetailValue = defaultScaleUseType.getLastDetailForUpdate().getScaleUseTypeDetailValue().clone();

                scaleUseTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateScaleUseTypeFromValue(scaleUseTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(scaleUseType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Scale Group Use Type Descriptions
    // --------------------------------------------------------------------------------

    public ScaleUseTypeDescription createScaleUseTypeDescription(ScaleUseType scaleUseType,
            Language language, String description, BasePK createdBy) {
        ScaleUseTypeDescription scaleUseTypeDescription = ScaleUseTypeDescriptionFactory.getInstance().create(scaleUseType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(scaleUseType.getPrimaryKey(), EventTypes.MODIFY.name(), scaleUseTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return scaleUseTypeDescription;
    }

    private static final Map<EntityPermission, String> getScaleUseTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaleusetypedescriptions " +
                "WHERE sclusetypd_sclusetyp_scaleusetypeid = ? AND sclusetypd_lang_languageid = ? AND sclusetypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaleusetypedescriptions " +
                "WHERE sclusetypd_sclusetyp_scaleusetypeid = ? AND sclusetypd_lang_languageid = ? AND sclusetypd_thrutime = ? " +
                "FOR UPDATE");
        getScaleUseTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ScaleUseTypeDescription getScaleUseTypeDescription(ScaleUseType scaleUseType,
            Language language, EntityPermission entityPermission) {
        return ScaleUseTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getScaleUseTypeDescriptionQueries,
                scaleUseType, language, Session.MAX_TIME);
    }

    public ScaleUseTypeDescription getScaleUseTypeDescription(ScaleUseType scaleUseType, Language language) {
        return getScaleUseTypeDescription(scaleUseType, language, EntityPermission.READ_ONLY);
    }

    public ScaleUseTypeDescription getScaleUseTypeDescriptionForUpdate(ScaleUseType scaleUseType, Language language) {
        return getScaleUseTypeDescription(scaleUseType, language, EntityPermission.READ_WRITE);
    }

    public ScaleUseTypeDescriptionValue getScaleUseTypeDescriptionValue(ScaleUseTypeDescription scaleUseTypeDescription) {
        return scaleUseTypeDescription == null? null: scaleUseTypeDescription.getScaleUseTypeDescriptionValue().clone();
    }

    public ScaleUseTypeDescriptionValue getScaleUseTypeDescriptionValueForUpdate(ScaleUseType scaleUseType, Language language) {
        return getScaleUseTypeDescriptionValue(getScaleUseTypeDescriptionForUpdate(scaleUseType, language));
    }

    private static final Map<EntityPermission, String> getScaleUseTypeDescriptionsByScaleUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM scaleusetypedescriptions, languages " +
                "WHERE sclusetypd_sclusetyp_scaleusetypeid = ? AND sclusetypd_thrutime = ? AND sclusetypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM scaleusetypedescriptions " +
                "WHERE sclusetypd_sclusetyp_scaleusetypeid = ? AND sclusetypd_thrutime = ? " +
                "FOR UPDATE");
        getScaleUseTypeDescriptionsByScaleUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ScaleUseTypeDescription> getScaleUseTypeDescriptionsByScaleUseType(ScaleUseType scaleUseType,
            EntityPermission entityPermission) {
        return ScaleUseTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getScaleUseTypeDescriptionsByScaleUseTypeQueries,
                scaleUseType, Session.MAX_TIME);
    }

    public List<ScaleUseTypeDescription> getScaleUseTypeDescriptionsByScaleUseType(ScaleUseType scaleUseType) {
        return getScaleUseTypeDescriptionsByScaleUseType(scaleUseType, EntityPermission.READ_ONLY);
    }

    public List<ScaleUseTypeDescription> getScaleUseTypeDescriptionsByScaleUseTypeForUpdate(ScaleUseType scaleUseType) {
        return getScaleUseTypeDescriptionsByScaleUseType(scaleUseType, EntityPermission.READ_WRITE);
    }

    public String getBestScaleUseTypeDescription(ScaleUseType scaleUseType, Language language) {
        String description;
        ScaleUseTypeDescription scaleUseTypeDescription = getScaleUseTypeDescription(scaleUseType, language);

        if(scaleUseTypeDescription == null && !language.getIsDefault()) {
            scaleUseTypeDescription = getScaleUseTypeDescription(scaleUseType, getPartyControl().getDefaultLanguage());
        }

        if(scaleUseTypeDescription == null) {
            description = scaleUseType.getLastDetail().getScaleUseTypeName();
        } else {
            description = scaleUseTypeDescription.getDescription();
        }

        return description;
    }

    public ScaleUseTypeDescriptionTransfer getScaleUseTypeDescriptionTransfer(UserVisit userVisit, ScaleUseTypeDescription scaleUseTypeDescription) {
        return getScaleTransferCaches(userVisit).getScaleUseTypeDescriptionTransferCache().getScaleUseTypeDescriptionTransfer(scaleUseTypeDescription);
    }

    public List<ScaleUseTypeDescriptionTransfer> getScaleUseTypeDescriptionTransfersByScaleUseType(UserVisit userVisit, ScaleUseType scaleUseType) {
        List<ScaleUseTypeDescription> scaleUseTypeDescriptions = getScaleUseTypeDescriptionsByScaleUseType(scaleUseType);
        List<ScaleUseTypeDescriptionTransfer> scaleUseTypeDescriptionTransfers = new ArrayList<>(scaleUseTypeDescriptions.size());
        ScaleUseTypeDescriptionTransferCache scaleUseTypeDescriptionTransferCache = getScaleTransferCaches(userVisit).getScaleUseTypeDescriptionTransferCache();

        scaleUseTypeDescriptions.stream().forEach((scaleUseTypeDescription) -> {
            scaleUseTypeDescriptionTransfers.add(scaleUseTypeDescriptionTransferCache.getScaleUseTypeDescriptionTransfer(scaleUseTypeDescription));
        });

        return scaleUseTypeDescriptionTransfers;
    }

    public void updateScaleUseTypeDescriptionFromValue(ScaleUseTypeDescriptionValue scaleUseTypeDescriptionValue, BasePK updatedBy) {
        if(scaleUseTypeDescriptionValue.hasBeenModified()) {
            ScaleUseTypeDescription scaleUseTypeDescription = ScaleUseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    scaleUseTypeDescriptionValue.getPrimaryKey());

            scaleUseTypeDescription.setThruTime(session.START_TIME_LONG);
            scaleUseTypeDescription.store();

            ScaleUseType scaleUseType = scaleUseTypeDescription.getScaleUseType();
            Language language = scaleUseTypeDescription.getLanguage();
            String description = scaleUseTypeDescriptionValue.getDescription();

            scaleUseTypeDescription = ScaleUseTypeDescriptionFactory.getInstance().create(scaleUseType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(scaleUseType.getPrimaryKey(), EventTypes.MODIFY.name(), scaleUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteScaleUseTypeDescription(ScaleUseTypeDescription scaleUseTypeDescription, BasePK deletedBy) {
        scaleUseTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(scaleUseTypeDescription.getScaleUseTypePK(), EventTypes.MODIFY.name(), scaleUseTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteScaleUseTypeDescriptionsByScaleUseType(ScaleUseType scaleUseType, BasePK deletedBy) {
        List<ScaleUseTypeDescription> scaleUseTypeDescriptions = getScaleUseTypeDescriptionsByScaleUseTypeForUpdate(scaleUseType);

        scaleUseTypeDescriptions.stream().forEach((scaleUseTypeDescription) -> {
            deleteScaleUseTypeDescription(scaleUseTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Party Scale Group Uses
    // --------------------------------------------------------------------------------
    
    public PartyScaleUse createPartyScaleUse(Party party, ScaleUseType scaleUseType, Scale scale, BasePK createdBy) {
        PartyScaleUse partyScaleUse = PartyScaleUseFactory.getInstance().create(party, scaleUseType, scale,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyScaleUse.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyScaleUse;
    }
    
    private static final Map<EntityPermission, String> getPartyScaleUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyscaleuses " +
                "WHERE parscluse_par_partyid = ? AND parscluse_sclusetyp_scaleusetypeid = ? " +
                "AND parscluse_thrutime = ?" +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyscaleuses " +
                "WHERE parscluse_par_partyid = ? AND parscluse_sclusetyp_scaleusetypeid = ? " +
                "AND parscluse_thrutime = ? " +
                "FOR UPDATE");
        getPartyScaleUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyScaleUse getPartyScaleUse(Party party, ScaleUseType scaleUseType, EntityPermission entityPermission) {
        return PartyScaleUseFactory.getInstance().getEntityFromQuery(entityPermission, getPartyScaleUseQueries,
                party, scaleUseType, Session.MAX_TIME);
    }

    public PartyScaleUse getPartyScaleUse(Party party, ScaleUseType scaleUseType) {
        return getPartyScaleUse(party, scaleUseType, EntityPermission.READ_ONLY);
    }

    public PartyScaleUse getPartyScaleUseUsingNames(Party party, String scaleUseTypeName) {
        ScaleUseType scaleUseType = getScaleUseTypeByName(scaleUseTypeName);
        PartyScaleUse partyScaleUse = null;

        if(scaleUseType != null) {
            partyScaleUse = getPartyScaleUse(party, scaleUseType, EntityPermission.READ_ONLY);
        }

        return partyScaleUse;
    }

    public PartyScaleUse getPartyScaleUseForUpdate(Party party, ScaleUseType scaleUseType) {
        return getPartyScaleUse(party, scaleUseType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyScaleUsesByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyscaleuses, scaleusetypes, scaleusetypedetails " +
                "WHERE parscluse_par_partyid = ? AND parscluse_thrutime = ? " +
                "AND parscluse_sclusetyp_scaleusetypeid = sclusetyp_scaleusetypeid AND sclusetyp_lastdetailid = sclusetypdt_scaleusetypedetailid " +
                "ORDER BY sclusetypdt_sortorder, sclusetypdt_scaleusetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyscaleuses " +
                "WHERE parscluse_par_partyid = ? AND parscluse_thrutime = ? " +
                "FOR UPDATE");
        getPartyScaleUsesByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyScaleUse> getPartyScaleUsesByParty(Party party, EntityPermission entityPermission) {
        return PartyScaleUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyScaleUsesByPartyQueries,
                party, Session.MAX_TIME);
    }

    public List<PartyScaleUse> getPartyScaleUsesByParty(Party party) {
        return getPartyScaleUsesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyScaleUse> getPartyScaleUsesByPartyForUpdate(Party party) {
        return getPartyScaleUsesByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyScaleUsesByScaleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyscaleuses, parties, partydetails " +
                "WHERE parscluse_scl_scaleid = ? AND parscluse_thrutime = ? " +
                "AND parscluse_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                "ORDER BY pardt_partyname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyscaleuses " +
                "WHERE parscluse_scl_scaleid = ? AND parscluse_thrutime = ? " +
                "FOR UPDATE");
        getPartyScaleUsesByScaleQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyScaleUse> getPartyScaleUsesByScale(Scale scale, EntityPermission entityPermission) {
        return PartyScaleUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyScaleUsesByScaleQueries,
                scale, Session.MAX_TIME);
    }

    public List<PartyScaleUse> getPartyScaleUsesByScale(Scale scale) {
        return getPartyScaleUsesByScale(scale, EntityPermission.READ_ONLY);
    }

    public List<PartyScaleUse> getPartyScaleUsesByScaleForUpdate(Scale scale) {
        return getPartyScaleUsesByScale(scale, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyScaleUsesByScaleUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyscaleuses, parties, partydetails " +
                "WHERE parscluse_sclusetyp_scaleusetypeid = ? AND parscluse_thrutime = ? " +
                "AND parscluse_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                "ORDER BY pardt_partyname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyscaleuses " +
                "WHERE parscluse_sclusetyp_scaleusetypeid = ? AND parscluse_thrutime = ? " +
                "FOR UPDATE");
        getPartyScaleUsesByScaleUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyScaleUse> getPartyScaleUsesByScaleUseType(ScaleUseType scaleUseType, EntityPermission entityPermission) {
        return PartyScaleUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyScaleUsesByScaleUseTypeQueries,
                scaleUseType, Session.MAX_TIME);
    }

    public List<PartyScaleUse> getPartyScaleUsesByScaleUseType(ScaleUseType scaleUseType) {
        return getPartyScaleUsesByScaleUseType(scaleUseType, EntityPermission.READ_ONLY);
    }

    public List<PartyScaleUse> getPartyScaleUsesByScaleUseTypeForUpdate(ScaleUseType scaleUseType) {
        return getPartyScaleUsesByScaleUseType(scaleUseType, EntityPermission.READ_WRITE);
    }

    public PartyScaleUseValue getPartyScaleUseValue(PartyScaleUse partyScaleUse) {
        return partyScaleUse == null? null: partyScaleUse.getPartyScaleUseValue().clone();
    }
    
    public PartyScaleUseValue getPartyScaleUseValueForUpdate(Party party, ScaleUseType scaleUseType) {
        return getPartyScaleUseValue(getPartyScaleUseForUpdate(party, scaleUseType));
    }
    
    public PartyScaleUseTransfer getPartyScaleUseTransfer(UserVisit userVisit, PartyScaleUse partyScaleUse) {
        return getScaleTransferCaches(userVisit).getPartyScaleUseTransferCache().getPartyScaleUseTransfer(partyScaleUse);
    }

    public List<PartyScaleUseTransfer> getPartyScaleUseTransfers(UserVisit userVisit, List<PartyScaleUse> partyScaleUses) {
        List<PartyScaleUseTransfer> partyScaleUseTransfers = new ArrayList<>(partyScaleUses.size());
        PartyScaleUseTransferCache partyScaleUseTransferCache = getScaleTransferCaches(userVisit).getPartyScaleUseTransferCache();

        partyScaleUses.stream().forEach((partyScaleUse) -> {
            partyScaleUseTransfers.add(partyScaleUseTransferCache.getPartyScaleUseTransfer(partyScaleUse));
        });

        return partyScaleUseTransfers;
    }

    public List<PartyScaleUseTransfer> getPartyScaleUseTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyScaleUseTransfers(userVisit, getPartyScaleUsesByParty(party));
    }

    public void updatePartyScaleUseFromValue(PartyScaleUseValue partyScaleUseValue, BasePK updatedBy) {
        if(partyScaleUseValue.hasBeenModified()) {
            PartyScaleUse partyScaleUse = PartyScaleUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyScaleUseValue.getPrimaryKey());
            
            partyScaleUse.setThruTime(session.START_TIME_LONG);
            partyScaleUse.store();
            
            PartyPK partyPK = partyScaleUse.getPartyPK(); // Not updated
            ScaleUseTypePK scaleUseTypePK = partyScaleUse.getScaleUseTypePK(); // Not updated
            ScalePK scalePK = partyScaleUseValue.getScalePK();
            
            partyScaleUse = PartyScaleUseFactory.getInstance().create(partyPK, scaleUseTypePK,
                    scalePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), partyScaleUse.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deletePartyScaleUse(PartyScaleUse partyScaleUse, BasePK deletedBy) {
        partyScaleUse.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(partyScaleUse.getPartyPK(), EventTypes.MODIFY.name(), partyScaleUse.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deletePartyScaleUses(List<PartyScaleUse> partyScaleUses, BasePK deletedBy) {
        partyScaleUses.stream().forEach((partyScaleUse) -> {
            deletePartyScaleUse(partyScaleUse, deletedBy);
        });
    }

    public void deletePartyScaleUsesByParty(Party party, BasePK deletedBy) {
        deletePartyScaleUses(getPartyScaleUsesByPartyForUpdate(party), deletedBy);
    }

    public void deletePartyScaleUsesByScale(Scale scale, BasePK deletedBy) {
        deletePartyScaleUses(getPartyScaleUsesByScaleForUpdate(scale), deletedBy);
    }

    public void deletePartyScaleUsesByScaleUseType(ScaleUseType scaleUseType, BasePK deletedBy) {
        deletePartyScaleUses(getPartyScaleUsesByScaleUseTypeForUpdate(scaleUseType), deletedBy);
    }

}
