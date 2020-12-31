// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.tag.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.tag.common.choice.TagChoicesBean;
import com.echothree.model.control.tag.common.choice.TagScopeChoicesBean;
import com.echothree.model.control.tag.common.transfer.EntityTagTransfer;
import com.echothree.model.control.tag.common.transfer.TagScopeDescriptionTransfer;
import com.echothree.model.control.tag.common.transfer.TagScopeEntityTypeTransfer;
import com.echothree.model.control.tag.common.transfer.TagScopeTransfer;
import com.echothree.model.control.tag.common.transfer.TagTransfer;
import com.echothree.model.control.tag.server.transfer.EntityTagTransferCache;
import com.echothree.model.control.tag.server.transfer.TagScopeDescriptionTransferCache;
import com.echothree.model.control.tag.server.transfer.TagScopeEntityTypeTransferCache;
import com.echothree.model.control.tag.server.transfer.TagScopeTransferCache;
import com.echothree.model.control.tag.server.transfer.TagTransferCache;
import com.echothree.model.control.tag.server.transfer.TagTransferCaches;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.common.pk.TagPK;
import com.echothree.model.data.tag.common.pk.TagScopePK;
import com.echothree.model.data.tag.server.entity.EntityTag;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.tag.server.entity.TagDetail;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.entity.TagScopeDescription;
import com.echothree.model.data.tag.server.entity.TagScopeDetail;
import com.echothree.model.data.tag.server.entity.TagScopeEntityType;
import com.echothree.model.data.tag.server.factory.EntityTagFactory;
import com.echothree.model.data.tag.server.factory.TagDetailFactory;
import com.echothree.model.data.tag.server.factory.TagFactory;
import com.echothree.model.data.tag.server.factory.TagScopeDescriptionFactory;
import com.echothree.model.data.tag.server.factory.TagScopeDetailFactory;
import com.echothree.model.data.tag.server.factory.TagScopeEntityTypeFactory;
import com.echothree.model.data.tag.server.factory.TagScopeFactory;
import com.echothree.model.data.tag.server.value.EntityTagValue;
import com.echothree.model.data.tag.server.value.TagDetailValue;
import com.echothree.model.data.tag.server.value.TagScopeDescriptionValue;
import com.echothree.model.data.tag.server.value.TagScopeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TagControl
        extends BaseModelControl {
    
    /** Creates a new instance of TagControl */
    public TagControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Tag Transfer Caches
    // --------------------------------------------------------------------------------
    
    private TagTransferCaches tagTransferCaches;
    
    public TagTransferCaches getTagTransferCaches(UserVisit userVisit) {
        if(tagTransferCaches == null) {
            tagTransferCaches = new TagTransferCaches(userVisit, this);
        }
        
        return tagTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Tag Scopes
    // --------------------------------------------------------------------------------
    
    public TagScope createTagScope(String tagScopeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        TagScope defaultTagScope = getDefaultTagScope();
        boolean defaultFound = defaultTagScope != null;
        
        if(defaultFound && isDefault) {
            TagScopeDetailValue defaultTagScopeDetailValue = getDefaultTagScopeDetailValueForUpdate();
            
            defaultTagScopeDetailValue.setIsDefault(Boolean.FALSE);
            updateTagScopeFromValue(defaultTagScopeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        TagScope tagScope = TagScopeFactory.getInstance().create();
        TagScopeDetail tagScopeDetail = TagScopeDetailFactory.getInstance().create(tagScope,
                tagScopeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        tagScope = TagScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                tagScope.getPrimaryKey());
        tagScope.setActiveDetail(tagScopeDetail);
        tagScope.setLastDetail(tagScopeDetail);
        tagScope.store();
        
        sendEventUsingNames(tagScope.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return tagScope;
    }
    
    private List<TagScope> getTagScopes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM tagscopes, tagscopedetails " +
                    "WHERE ts_activedetailid = tsdt_tagscopedetailid " +
                    "ORDER BY tsdt_sortorder, tsdt_tagscopename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM tagscopes, tagscopedetails " +
                    "WHERE ts_activedetailid = tsdt_tagscopedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TagScopeFactory.getInstance().prepareStatement(query);
        
        return TagScopeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TagScope> getTagScopes() {
        return getTagScopes(EntityPermission.READ_ONLY);
    }
    
    public List<TagScope> getTagScopesForUpdate() {
        return getTagScopes(EntityPermission.READ_WRITE);
    }
    
    private List<TagScope> getTagScopesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<TagScope> tagScopes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopes, tagscopedetails, tagscopeentitytypes " +
                        "WHERE ts_activedetailid = tsdt_tagscopedetailid " +
                        "AND ts_tagscopeid = tent_ts_tagscopeid AND tent_ent_entitytypeid = ? AND tent_thrutime = ? " +
                        "ORDER BY tsdt_sortorder, tsdt_tagscopename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopes, tagscopedetails, tagscopeentitytypes " +
                        "WHERE ts_activedetailid = tsdt_tagscopedetailid " +
                        "AND ts_tagscopeid = tent_ts_tagscopeid AND tent_ent_entitytypeid = ? AND tent_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TagScopeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            tagScopes = TagScopeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tagScopes;
    }
    
    public List<TagScope> getTagScopesByEntityType(EntityType entityType) {
        return getTagScopesByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<TagScope> getTagScopesByEntityTypeForUpdate(EntityType entityType) {
        return getTagScopesByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    private TagScope getDefaultTagScope(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM tagscopes, tagscopedetails " +
                    "WHERE ts_activedetailid = tsdt_tagscopedetailid AND tsdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM tagscopes, tagscopedetails " +
                    "WHERE ts_activedetailid = tsdt_tagscopedetailid AND tsdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TagScopeFactory.getInstance().prepareStatement(query);
        
        return TagScopeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public TagScope getDefaultTagScope() {
        return getDefaultTagScope(EntityPermission.READ_ONLY);
    }
    
    public TagScope getDefaultTagScopeForUpdate() {
        return getDefaultTagScope(EntityPermission.READ_WRITE);
    }
    
    public TagScopeDetailValue getDefaultTagScopeDetailValueForUpdate() {
        return getDefaultTagScopeForUpdate().getLastDetailForUpdate().getTagScopeDetailValue().clone();
    }
    
    private TagScope getTagScopeByName(String tagScopeName, EntityPermission entityPermission) {
        TagScope tagScope;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopes, tagscopedetails " +
                        "WHERE ts_activedetailid = tsdt_tagscopedetailid AND tsdt_tagscopename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopes, tagscopedetails " +
                        "WHERE ts_activedetailid = tsdt_tagscopedetailid AND tsdt_tagscopename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TagScopeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, tagScopeName);
            
            tagScope = TagScopeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tagScope;
    }
    
    public TagScope getTagScopeByName(String tagScopeName) {
        return getTagScopeByName(tagScopeName, EntityPermission.READ_ONLY);
    }
    
    public TagScope getTagScopeByNameForUpdate(String tagScopeName) {
        return getTagScopeByName(tagScopeName, EntityPermission.READ_WRITE);
    }
    
    public TagScopeDetailValue getTagScopeDetailValueForUpdate(TagScope tagScope) {
        return tagScope == null? null: tagScope.getLastDetailForUpdate().getTagScopeDetailValue().clone();
    }
    
    public TagScopeDetailValue getTagScopeDetailValueByNameForUpdate(String tagScopeName) {
        return getTagScopeDetailValueForUpdate(getTagScopeByNameForUpdate(tagScopeName));
    }
    
    public TagScopeChoicesBean getTagScopeChoices(String defaultTagScopeChoice, Language language, boolean allowNullChoice) {
        List<TagScope> tagScopes = getTagScopes();
        int size = tagScopes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultTagScopeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var tagScope : tagScopes) {
            TagScopeDetail tagScopeDetail = tagScope.getLastDetail();
            String label = getBestTagScopeDescription(tagScope, language);
            String value = tagScopeDetail.getTagScopeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultTagScopeChoice != null && defaultTagScopeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && tagScopeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new TagScopeChoicesBean(labels, values, defaultValue);
    }
    
    public TagScopeTransfer getTagScopeTransfer(UserVisit userVisit, TagScope tagScope) {
        return getTagTransferCaches(userVisit).getTagScopeTransferCache().getTagScopeTransfer(tagScope);
    }
    
    public List<TagScopeTransfer> getTagScopeTransfers(UserVisit userVisit, List<TagScope> tagScopes) {
        List<TagScopeTransfer> tagScopeTransfers = new ArrayList<>(tagScopes.size());
        TagScopeTransferCache tagScopeTransferCache = getTagTransferCaches(userVisit).getTagScopeTransferCache();
        
        tagScopes.forEach((tagScope) ->
                tagScopeTransfers.add(tagScopeTransferCache.getTagScopeTransfer(tagScope))
        );
        
        return tagScopeTransfers;
    }
    
    public List<TagScopeTransfer> getTagScopeTransfers(UserVisit userVisit) {
        return getTagScopeTransfers(userVisit, getTagScopes());
    }
    
    public List<TagScopeTransfer> getTagScopeTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getTagScopeTransfers(userVisit, getTagScopesByEntityType(entityType));
    }
    
    private void updateTagScopeFromValue(TagScopeDetailValue tagScopeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(tagScopeDetailValue.hasBeenModified()) {
            TagScope tagScope = TagScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     tagScopeDetailValue.getTagScopePK());
            TagScopeDetail tagScopeDetail = tagScope.getActiveDetailForUpdate();
            
            tagScopeDetail.setThruTime(session.START_TIME_LONG);
            tagScopeDetail.store();
            
            TagScopePK tagScopePK = tagScopeDetail.getTagScopePK();
            String tagScopeName = tagScopeDetailValue.getTagScopeName();
            Boolean isDefault = tagScopeDetailValue.getIsDefault();
            Integer sortOrder = tagScopeDetailValue.getSortOrder();
            
            if(checkDefault) {
                TagScope defaultTagScope = getDefaultTagScope();
                boolean defaultFound = defaultTagScope != null && !defaultTagScope.equals(tagScope);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    TagScopeDetailValue defaultTagScopeDetailValue = getDefaultTagScopeDetailValueForUpdate();
                    
                    defaultTagScopeDetailValue.setIsDefault(Boolean.FALSE);
                    updateTagScopeFromValue(defaultTagScopeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            tagScopeDetail = TagScopeDetailFactory.getInstance().create(tagScopePK, tagScopeName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            tagScope.setActiveDetail(tagScopeDetail);
            tagScope.setLastDetail(tagScopeDetail);
            
            sendEventUsingNames(tagScopePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateTagScopeFromValue(TagScopeDetailValue tagScopeDetailValue, BasePK updatedBy) {
        updateTagScopeFromValue(tagScopeDetailValue, true, updatedBy);
    }
    
    public void deleteTagScope(TagScope tagScope, BasePK deletedBy) {
        deleteTagScopeDescriptionsByTagScope(tagScope, deletedBy);
        deleteTagScopeEntityTypesByTagScope(tagScope, deletedBy);
        deleteTagsByTagScope(tagScope, deletedBy);
        
        TagScopeDetail tagScopeDetail = tagScope.getLastDetailForUpdate();
        tagScopeDetail.setThruTime(session.START_TIME_LONG);
        tagScope.setActiveDetail(null);
        tagScope.store();
        
        // Check for default, and pick one if necessary
        TagScope defaultTagScope = getDefaultTagScope();
        if(defaultTagScope == null) {
            List<TagScope> tagScopes = getTagScopesForUpdate();
            
            if(!tagScopes.isEmpty()) {
                Iterator<TagScope> iter = tagScopes.iterator();
                if(iter.hasNext()) {
                    defaultTagScope = iter.next();
                }
                TagScopeDetailValue tagScopeDetailValue = Objects.requireNonNull(defaultTagScope).getLastDetailForUpdate().getTagScopeDetailValue().clone();
                
                tagScopeDetailValue.setIsDefault(Boolean.TRUE);
                updateTagScopeFromValue(tagScopeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(tagScope.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Tag Scope Descriptions
    // --------------------------------------------------------------------------------
    
    public TagScopeDescription createTagScopeDescription(TagScope tagScope, Language language, String description,
            BasePK createdBy) {
        TagScopeDescription tagScopeDescription = TagScopeDescriptionFactory.getInstance().create(tagScope,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(tagScope.getPrimaryKey(), EventTypes.MODIFY.name(), tagScopeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return tagScopeDescription;
    }
    
    private TagScopeDescription getTagScopeDescription(TagScope tagScope, Language language, EntityPermission entityPermission) {
        TagScopeDescription tagScopeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopedescriptions " +
                        "WHERE tsd_ts_tagscopeid = ? AND tsd_lang_languageid = ? AND tsd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopedescriptions " +
                        "WHERE tsd_ts_tagscopeid = ? AND tsd_lang_languageid = ? AND tsd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TagScopeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tagScope.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            tagScopeDescription = TagScopeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tagScopeDescription;
    }
    
    public TagScopeDescription getTagScopeDescription(TagScope tagScope, Language language) {
        return getTagScopeDescription(tagScope, language, EntityPermission.READ_ONLY);
    }
    
    public TagScopeDescription getTagScopeDescriptionForUpdate(TagScope tagScope, Language language) {
        return getTagScopeDescription(tagScope, language, EntityPermission.READ_WRITE);
    }
    
    public TagScopeDescriptionValue getTagScopeDescriptionValue(TagScopeDescription tagScopeDescription) {
        return tagScopeDescription == null? null: tagScopeDescription.getTagScopeDescriptionValue().clone();
    }
    
    public TagScopeDescriptionValue getTagScopeDescriptionValueForUpdate(TagScope tagScope, Language language) {
        return getTagScopeDescriptionValue(getTagScopeDescriptionForUpdate(tagScope, language));
    }
    
    private List<TagScopeDescription> getTagScopeDescriptionsByTagScope(TagScope tagScope, EntityPermission entityPermission) {
        List<TagScopeDescription> tagScopeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopedescriptions, languages " +
                        "WHERE tsd_ts_tagscopeid = ? AND tsd_thrutime = ? " +
                        "AND tsd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopedescriptions " +
                        "WHERE tsd_ts_tagscopeid = ? AND tsd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TagScopeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tagScope.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            tagScopeDescriptions = TagScopeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tagScopeDescriptions;
    }
    
    public List<TagScopeDescription> getTagScopeDescriptionsByTagScope(TagScope tagScope) {
        return getTagScopeDescriptionsByTagScope(tagScope, EntityPermission.READ_ONLY);
    }
    
    public List<TagScopeDescription> getTagScopeDescriptionsByTagScopeForUpdate(TagScope tagScope) {
        return getTagScopeDescriptionsByTagScope(tagScope, EntityPermission.READ_WRITE);
    }
    
    public String getBestTagScopeDescription(TagScope tagScope, Language language) {
        String description;
        TagScopeDescription tagScopeDescription = getTagScopeDescription(tagScope, language);
        
        if(tagScopeDescription == null && !language.getIsDefault()) {
            tagScopeDescription = getTagScopeDescription(tagScope, getPartyControl().getDefaultLanguage());
        }
        
        if(tagScopeDescription == null) {
            description = tagScope.getLastDetail().getTagScopeName();
        } else {
            description = tagScopeDescription.getDescription();
        }
        
        return description;
    }
    
    public TagScopeDescriptionTransfer getTagScopeDescriptionTransfer(UserVisit userVisit, TagScopeDescription tagScopeDescription) {
        return getTagTransferCaches(userVisit).getTagScopeDescriptionTransferCache().getTagScopeDescriptionTransfer(tagScopeDescription);
    }
    
    public List<TagScopeDescriptionTransfer> getTagScopeDescriptionTransfers(UserVisit userVisit, TagScope tagScope) {
        List<TagScopeDescription> tagScopeDescriptions = getTagScopeDescriptionsByTagScope(tagScope);
        List<TagScopeDescriptionTransfer> tagScopeDescriptionTransfers = new ArrayList<>(tagScopeDescriptions.size());
        TagScopeDescriptionTransferCache tagScopeDescriptionTransferCache = getTagTransferCaches(userVisit).getTagScopeDescriptionTransferCache();
        
        tagScopeDescriptions.forEach((tagScopeDescription) ->
                tagScopeDescriptionTransfers.add(tagScopeDescriptionTransferCache.getTagScopeDescriptionTransfer(tagScopeDescription))
        );
        
        return tagScopeDescriptionTransfers;
    }
    
    public void updateTagScopeDescriptionFromValue(TagScopeDescriptionValue tagScopeDescriptionValue, BasePK updatedBy) {
        if(tagScopeDescriptionValue.hasBeenModified()) {
            TagScopeDescription tagScopeDescription = TagScopeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     tagScopeDescriptionValue.getPrimaryKey());
            
            tagScopeDescription.setThruTime(session.START_TIME_LONG);
            tagScopeDescription.store();
            
            TagScope tagScope = tagScopeDescription.getTagScope();
            Language language = tagScopeDescription.getLanguage();
            String description = tagScopeDescriptionValue.getDescription();
            
            tagScopeDescription = TagScopeDescriptionFactory.getInstance().create(tagScope, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(tagScope.getPrimaryKey(), EventTypes.MODIFY.name(), tagScopeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteTagScopeDescription(TagScopeDescription tagScopeDescription, BasePK deletedBy) {
        tagScopeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(tagScopeDescription.getTagScopePK(), EventTypes.MODIFY.name(), tagScopeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteTagScopeDescriptionsByTagScope(TagScope tagScope, BasePK deletedBy) {
        List<TagScopeDescription> tagScopeDescriptions = getTagScopeDescriptionsByTagScopeForUpdate(tagScope);
        
        tagScopeDescriptions.forEach((tagScopeDescription) -> 
                deleteTagScopeDescription(tagScopeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Tag Scope Entity Types
    // --------------------------------------------------------------------------------
    
    public TagScopeEntityType createTagScopeEntityType(TagScope tagScope, EntityType entityType, BasePK createdBy) {
        TagScopeEntityType tagScopeEntityType = TagScopeEntityTypeFactory.getInstance().create(tagScope, entityType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(tagScope.getPrimaryKey(), EventTypes.MODIFY.name(), tagScopeEntityType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return tagScopeEntityType;
    }
    
    private TagScopeEntityType getTagScopeEntityType(TagScope tagScope, EntityType entityType, EntityPermission entityPermission) {
        TagScopeEntityType tagScopeEntityType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopeentitytypes " +
                        "WHERE tent_ts_tagscopeid = ? AND tent_ent_entitytypeid = ? AND tent_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopeentitytypes " +
                        "WHERE tent_ts_tagscopeid = ? AND tent_ent_entitytypeid = ? AND tent_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TagScopeEntityTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tagScope.getPrimaryKey().getEntityId());
            ps.setLong(2, entityType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            tagScopeEntityType = TagScopeEntityTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tagScopeEntityType;
    }
    
    public TagScopeEntityType getTagScopeEntityType(TagScope tagScope, EntityType entityType) {
        return getTagScopeEntityType(tagScope, entityType, EntityPermission.READ_ONLY);
    }
    
    public TagScopeEntityType getTagScopeEntityTypeForUpdate(TagScope tagScope, EntityType entityType) {
        return getTagScopeEntityType(tagScope, entityType, EntityPermission.READ_WRITE);
    }
    
    private List<TagScopeEntityType> getTagScopeEntityTypesByTagScope(TagScope tagScope, EntityPermission entityPermission) {
        List<TagScopeEntityType> tagScopeEntityTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopeentitytypes, entitytypes, entitytypedetails " +
                        "WHERE tent_ts_tagscopeid = ? AND tent_thrutime = ? " +
                        "AND tent_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid " +
                        "ORDER BY entdt_sortorder, entdt_entitytypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopeentitytypes " +
                        "WHERE tent_ts_tagscopeid = ? AND tent_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TagScopeEntityTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tagScope.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            tagScopeEntityTypes = TagScopeEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tagScopeEntityTypes;
    }
    
    public List<TagScopeEntityType> getTagScopeEntityTypesByTagScope(TagScope tagScope) {
        return getTagScopeEntityTypesByTagScope(tagScope, EntityPermission.READ_ONLY);
    }
    
    public List<TagScopeEntityType> getTagScopeEntityTypesByTagScopeForUpdate(TagScope tagScope) {
        return getTagScopeEntityTypesByTagScope(tagScope, EntityPermission.READ_WRITE);
    }
    
    private List<TagScopeEntityType> getTagScopeEntityTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<TagScopeEntityType> tagScopeEntityTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopeentitytypes, tagscopes, tagscopedetails " +
                        "WHERE tent_ent_entitytypeid = ? AND tent_thrutime = ? " +
                        "AND tent_ts_tagscopeid = ts_tagscopeid AND ts_lastdetailid = tsdt_tagscopedetailid " +
                        "ORDER BY tsdt_sortorder, tsdt_tagscopename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM tagscopeentitytypes " +
                        "WHERE tent_ent_entitytypeid = ? AND tent_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TagScopeEntityTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            tagScopeEntityTypes = TagScopeEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tagScopeEntityTypes;
    }
    
    public List<TagScopeEntityType> getTagScopeEntityTypesByEntityType(EntityType entityType) {
        return getTagScopeEntityTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<TagScopeEntityType> getTagScopeEntityTypesByEntityTypeForUpdate(EntityType entityType) {
        return getTagScopeEntityTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    public TagScopeEntityTypeTransfer getTagScopeEntityTypeTransfer(UserVisit userVisit, TagScopeEntityType tagScopeEntityType) {
        return getTagTransferCaches(userVisit).getTagScopeEntityTypeTransferCache().getTagScopeEntityTypeTransfer(tagScopeEntityType);
    }
    
    public List<TagScopeEntityTypeTransfer> getTagScopeEntityTypeTransfers(UserVisit userVisit, List<TagScopeEntityType> tagScopeEntityTypes) {
        List<TagScopeEntityTypeTransfer> tagScopeEntityTypeTransfers = new ArrayList<>(tagScopeEntityTypes.size());
        TagScopeEntityTypeTransferCache tagScopeEntityTypeTransferCache = getTagTransferCaches(userVisit).getTagScopeEntityTypeTransferCache();
        
        tagScopeEntityTypes.forEach((tagScopeEntityType) ->
                tagScopeEntityTypeTransfers.add(tagScopeEntityTypeTransferCache.getTagScopeEntityTypeTransfer(tagScopeEntityType))
        );
        
        return tagScopeEntityTypeTransfers;
    }
    
    public List<TagScopeEntityTypeTransfer> getTagScopeEntityTypeTransfersByTagScope(UserVisit userVisit, TagScope tagScope) {
        return getTagScopeEntityTypeTransfers(userVisit, getTagScopeEntityTypesByTagScope(tagScope));
    }
    
    public List<TagScopeEntityTypeTransfer> getTagScopeEntityTypeTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getTagScopeEntityTypeTransfers(userVisit, getTagScopeEntityTypesByEntityType(entityType));
    }
    
    public void deleteTagScopeEntityType(TagScopeEntityType tagScopeEntityType, BasePK deletedBy) {
        tagScopeEntityType.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(tagScopeEntityType.getTagScopePK(), EventTypes.MODIFY.name(), tagScopeEntityType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteTagScopeEntityTypes(List<TagScopeEntityType> tagScopeEntityTypes, BasePK deletedBy) {
        tagScopeEntityTypes.forEach((tagScopeEntityType) -> 
                deleteTagScopeEntityType(tagScopeEntityType, deletedBy)
        );
    }
    
    public void deleteTagScopeEntityTypesByTagScope(TagScope tagScope, BasePK deletedBy) {
        deleteTagScopeEntityTypes(getTagScopeEntityTypesByTagScopeForUpdate(tagScope), deletedBy);
    }
    
    public void deleteTagScopeEntityTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteTagScopeEntityTypes(getTagScopeEntityTypesByEntityTypeForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Tags
    // --------------------------------------------------------------------------------
    
    public Tag createTag(TagScope tagScope, String tagName, BasePK createdBy) {
        Tag tag = TagFactory.getInstance().create();
        TagDetail tagDetail = TagDetailFactory.getInstance().create(tag, tagScope, tagName, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        tag = TagFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                tag.getPrimaryKey());
        tag.setActiveDetail(tagDetail);
        tag.setLastDetail(tagDetail);
        tag.store();
        
        sendEventUsingNames(tag.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return tag;
    }
    
    private List<Tag> getTags(TagScope tagScope, EntityPermission entityPermission) {
        List<Tag> tags;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM tags, tagdetails " +
                        "WHERE t_activedetailid = tdt_tagdetailid AND tdt_ts_tagscopeid = ? " +
                        "ORDER BY tdt_tagname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM tags, tagdetails " +
                        "WHERE t_activedetailid = tdt_tagdetailid AND tdt_ts_tagscopeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TagFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tagScope.getPrimaryKey().getEntityId());
            
            tags = TagFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tags;
    }
    
    public List<Tag> getTags(TagScope tagScope) {
        return getTags(tagScope, EntityPermission.READ_ONLY);
    }
    
    public List<Tag> getTagsForUpdate(TagScope tagScope) {
        return getTags(tagScope, EntityPermission.READ_WRITE);
    }
    
    private List<Tag> getTagsByTagScopeAndEntityInstance(TagScope tagScope, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        List<Tag> tags;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM tags, tagdetails, entitytags " +
                        "WHERE t_activedetailid = tdt_tagdetailid AND tdt_ts_tagscopeid = ? " +
                        "AND t_tagid = et_t_tagid AND et_taggedentityinstanceid = ? AND et_thrutime = ? " +
                        "ORDER BY tdt_tagname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM tags, tagdetails, entitytags " +
                        "WHERE t_activedetailid = tdt_tagdetailid AND tdt_ts_tagscopeid = ? " +
                        "AND t_tagid = et_t_tagid AND et_taggedentityinstanceid = ? AND et_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TagFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tagScope.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            tags = TagFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tags;
    }
    
    public List<Tag> getTagsByTagScopeAndEntityInstance(TagScope tagScope, EntityInstance entityInstance) {
        return getTagsByTagScopeAndEntityInstance(tagScope, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<Tag> getTagsByTagScopeAndEntityInstanceForUpdate(TagScope tagScope, EntityInstance entityInstance) {
        return getTagsByTagScopeAndEntityInstance(tagScope, entityInstance, EntityPermission.READ_WRITE);
    }
    
    private Tag getTagByName(TagScope tagScope, String tagName, EntityPermission entityPermission) {
        Tag tag;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM tags, tagdetails " +
                        "WHERE t_activedetailid = tdt_tagdetailid AND tdt_ts_tagscopeid = ? AND tdt_tagname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM tags, tagdetails " +
                        "WHERE t_activedetailid = tdt_tagdetailid AND tdt_ts_tagscopeid = ? AND tdt_tagname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TagFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tagScope.getPrimaryKey().getEntityId());
            ps.setString(2, tagName);
            
            tag = TagFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tag;
    }
    
    public Tag getTagByName(TagScope tagScope, String tagName) {
        return getTagByName(tagScope, tagName, EntityPermission.READ_ONLY);
    }
    
    public Tag getTagByNameForUpdate(TagScope tagScope, String tagName) {
        return getTagByName(tagScope, tagName, EntityPermission.READ_WRITE);
    }
    
    public TagDetailValue getTagDetailValueForUpdate(Tag tag) {
        return tag == null? null: tag.getLastDetailForUpdate().getTagDetailValue().clone();
    }
    
    public TagDetailValue getTagDetailValueByNameForUpdate(TagScope tagScope, String tagName) {
        return getTagDetailValueForUpdate(getTagByNameForUpdate(tagScope, tagName));
    }
    
    public TagChoicesBean getTagChoices(String defaultTagChoice, Language language, boolean allowNullChoice, TagScope tagScope) {
        List<Tag> tags = getTags(tagScope);
        int size = tags.size();
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            values.add("");
            
            if(defaultTagChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var tag : tags) {
            TagDetail tagDetail = tag.getLastDetail();
            String value = tagDetail.getTagName();
            
            values.add(value);
            
            boolean usingDefaultChoice = defaultTagChoice != null && defaultTagChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new TagChoicesBean(values, values, defaultValue);
    }
    
    public TagTransfer getTagTransfer(UserVisit userVisit, Tag tag) {
        return getTagTransferCaches(userVisit).getTagTransferCache().getTagTransfer(tag);
    }
    
    public List<TagTransfer> getTagTransfers(UserVisit userVisit, List<Tag> tags) {
        List<TagTransfer> tagTransfers = new ArrayList<>(tags.size());
        TagTransferCache tagTransferCache = getTagTransferCaches(userVisit).getTagTransferCache();
        
        tags.forEach((tag) ->
                tagTransfers.add(tagTransferCache.getTagTransfer(tag))
        );
        
        return tagTransfers;
    }
    
    public List<TagTransfer> getTagTransfersByTagScope(UserVisit userVisit, TagScope tagScope) {
        return getTagTransfers(userVisit, getTags(tagScope));
    }
    
    public List<TagTransfer> getTagTransfersByTagScopeAndEntityInstance(UserVisit userVisit, TagScope tagScope,
            EntityInstance entityInstance) {
        return getTagTransfers(userVisit, getTagsByTagScopeAndEntityInstance(tagScope, entityInstance));
    }
    
    public void updateTagFromValue(TagDetailValue tagDetailValue, BasePK updatedBy) {
        if(tagDetailValue.hasBeenModified()) {
            Tag tag = TagFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     tagDetailValue.getTagPK());
            TagDetail tagDetail = tag.getActiveDetailForUpdate();
            
            tagDetail.setThruTime(session.START_TIME_LONG);
            tagDetail.store();
            
            TagPK tagPK = tagDetail.getTagPK(); // Not updated
            TagScopePK tagScopePK = tagDetail.getTagScopePK(); // Not updated
            String tagName = tagDetailValue.getTagName();
            
            tagDetail = TagDetailFactory.getInstance().create(tagPK, tagScopePK, tagName, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            tag.setActiveDetail(tagDetail);
            tag.setLastDetail(tagDetail);
            
            sendEventUsingNames(tagPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteTag(Tag tag, BasePK deletedBy) {
        deleteEntityTagsByTag(tag, deletedBy);
        
        TagDetail tagDetail = tag.getLastDetailForUpdate();
        tagDetail.setThruTime(session.START_TIME_LONG);
        tag.setActiveDetail(null);
        tag.store();
        
        sendEventUsingNames(tag.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteTags(List<Tag> tags, BasePK deletedBy) {
        tags.forEach((tag) -> 
                deleteTag(tag, deletedBy)
        );
    }
    
    public void deleteTagsByTagScope(TagScope tagScope, BasePK deletedBy) {
        deleteTags(getTagsForUpdate(tagScope), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Tags
    // --------------------------------------------------------------------------------
    
    public EntityTag createEntityTag(EntityInstance taggedEntityInstance, Tag tag, BasePK createdBy) {
        EntityTag entityTag = EntityTagFactory.getInstance().create(taggedEntityInstance, tag, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(taggedEntityInstance, EventTypes.MODIFY.name(), entityTag.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityTag;
    }
    
    public long countEntityTagsByTag(Tag tag) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitytags " +
                "WHERE et_t_tagid = ? AND et_thrutime = ?",
                tag, Session.MAX_TIME_LONG);
    }

    private EntityTag getEntityTag(EntityInstance taggedEntityInstance, Tag tag, EntityPermission entityPermission) {
        EntityTag entityTag;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytags " +
                        "WHERE et_taggedentityinstanceid = ? AND et_t_tagid = ? AND et_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytags " +
                        "WHERE et_taggedentityinstanceid = ? AND et_t_tagid = ? AND et_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityTagFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, taggedEntityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, tag.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityTag = EntityTagFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTag;
    }
    
    public EntityTag getEntityTag(EntityInstance taggedEntityInstance, Tag tag) {
        return getEntityTag(taggedEntityInstance, tag, EntityPermission.READ_ONLY);
    }
    
    public EntityTag getEntityTagForUpdate(EntityInstance taggedEntityInstance, Tag tag) {
        return getEntityTag(taggedEntityInstance, tag, EntityPermission.READ_WRITE);
    }
    
    public EntityTagValue getEntityTagValue(EntityTag entityTag) {
        return entityTag == null? null: entityTag.getEntityTagValue().clone();
    }
    
    public EntityTagValue getEntityTagValueForUpdate(EntityInstance taggedEntityInstance, Tag tag) {
        return getEntityTagValue(getEntityTagForUpdate(taggedEntityInstance, tag));
    }
    
    private List<EntityTag> getEntityTagsByTaggedEntityInstance(EntityInstance taggedEntityInstance, EntityPermission entityPermission) {
        List<EntityTag> entityTags;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytags, tags, tagdetails " +
                        "WHERE et_taggedentityinstanceid = ? AND et_thrutime = ? " +
                        "AND et_t_tagid = t_tagid AND t_lastdetailid = tdt_tagdetailid " +
                        "ORDER BY tdt_tagname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytags " +
                        "WHERE et_taggedentityinstanceid = ? AND et_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityTagFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, taggedEntityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTags = EntityTagFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTags;
    }
    
    public List<EntityTag> getEntityTagsByTaggedEntityInstance(EntityInstance taggedEntityInstance) {
        return getEntityTagsByTaggedEntityInstance(taggedEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<EntityTag> getEntityTagsByTaggedEntityInstanceForUpdate(EntityInstance taggedEntityInstance) {
        return getEntityTagsByTaggedEntityInstance(taggedEntityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<EntityTag> getEntityTagsByTag(Tag tag, EntityPermission entityPermission) {
        List<EntityTag> entityTags;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytags, entityinstances, componentvendors, componentvendordetails, entitytypes, entitytypedetails " +
                        "WHERE et_t_tagid = ? AND et_thrutime = ? " +
                        "AND et_taggedentityinstanceid = eni_entityinstanceid " +
                        "AND eni_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid " +
                        "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid " +
                        "ORDER BY cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename, eni_entityuniqueid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytags " +
                        "WHERE et_t_tagid = ? AND et_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityTagFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tag.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTags = EntityTagFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTags;
    }
    
    public List<EntityTag> getEntityTagsByTag(Tag tag) {
        return getEntityTagsByTag(tag, EntityPermission.READ_ONLY);
    }
    
    public List<EntityTag> getEntityTagsByTagForUpdate(Tag tag) {
        return getEntityTagsByTag(tag, EntityPermission.READ_WRITE);
    }
    
    public EntityTagTransfer getEntityTagTransfer(UserVisit userVisit, EntityTag entityTag) {
        return getTagTransferCaches(userVisit).getEntityTagTransferCache().getEntityTagTransfer(entityTag);
    }
    
    public List<EntityTagTransfer> getEntityTagTransfers(UserVisit userVisit, List<EntityTag> entityTags) {
        List<EntityTagTransfer> entityTagTransfers = new ArrayList<>(entityTags.size());
        EntityTagTransferCache entityTagTransferCache = getTagTransferCaches(userVisit).getEntityTagTransferCache();
        
        entityTags.forEach((entityTag) ->
                entityTagTransfers.add(entityTagTransferCache.getEntityTagTransfer(entityTag))
        );
        
        return entityTagTransfers;
    }
    
    public List<EntityTagTransfer> getEntityTagTransfersByTaggedEntityInstance(UserVisit userVisit, EntityInstance taggedEntityInstance) {
        return getEntityTagTransfers(userVisit, getEntityTagsByTaggedEntityInstance(taggedEntityInstance));
    }
    
    public List<EntityTagTransfer> getEntityTagTransfersByTag(UserVisit userVisit, Tag tag) {
        return getEntityTagTransfers(userVisit, getEntityTagsByTag(tag));
    }
    
    public void deleteEntityTag(EntityTag entityTag, BasePK deletedBy) {
        EntityInstance taggedEntityInstance = entityTag.getTaggedEntityInstance();

        entityTag.setThruTime(session.START_TIME_LONG);
        entityTag.store();
        
        sendEventUsingNames(taggedEntityInstance, EventTypes.MODIFY.name(), entityTag.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityTags(List<EntityTag> entityTags, BasePK deletedBy) {
        entityTags.forEach((entityTag) -> 
                deleteEntityTag(entityTag, deletedBy)
        );
    }
    
    public void deleteEntityTagsByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityTags(getEntityTagsByTaggedEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    public void deleteEntityTagsByTag(Tag tag, BasePK deletedBy) {
        deleteEntityTags(getEntityTagsByTagForUpdate(tag), deletedBy);
    }
    
}
