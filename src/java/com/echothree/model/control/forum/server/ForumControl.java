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

package com.echothree.model.control.forum.server;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.forum.common.choice.ForumChoicesBean;
import com.echothree.model.control.forum.common.choice.ForumGroupChoicesBean;
import com.echothree.model.control.forum.common.choice.ForumMessageTypeChoicesBean;
import com.echothree.model.control.forum.common.choice.ForumRoleTypeChoicesBean;
import com.echothree.model.control.forum.common.choice.ForumTypeChoicesBean;
import com.echothree.model.control.forum.common.transfer.ForumDescriptionTransfer;
import com.echothree.model.control.forum.common.transfer.ForumForumThreadTransfer;
import com.echothree.model.control.forum.common.transfer.ForumGroupDescriptionTransfer;
import com.echothree.model.control.forum.common.transfer.ForumGroupForumTransfer;
import com.echothree.model.control.forum.common.transfer.ForumGroupTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageAttachmentDescriptionTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageAttachmentTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessagePartTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessagePartTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageRoleTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTypePartTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMimeTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumPartyRoleTransfer;
import com.echothree.model.control.forum.common.transfer.ForumPartyTypeRoleTransfer;
import com.echothree.model.control.forum.common.transfer.ForumRoleTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumThreadTransfer;
import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.model.control.forum.common.transfer.ForumTypeTransfer;
import com.echothree.model.control.forum.server.transfer.ForumDescriptionTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumForumThreadTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumGroupDescriptionTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumGroupForumTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumGroupTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessageAttachmentTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessagePartTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessageRoleTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessageTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessageTypePartTypeTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMimeTypeTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumPartyRoleTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumPartyTypeRoleTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumThreadTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumTransferCaches;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.forum.common.pk.ForumGroupPK;
import com.echothree.model.data.forum.common.pk.ForumMessageAttachmentPK;
import com.echothree.model.data.forum.common.pk.ForumMessagePK;
import com.echothree.model.data.forum.common.pk.ForumMessagePartPK;
import com.echothree.model.data.forum.common.pk.ForumMessagePartTypePK;
import com.echothree.model.data.forum.common.pk.ForumMessageTypePK;
import com.echothree.model.data.forum.common.pk.ForumPK;
import com.echothree.model.data.forum.common.pk.ForumThreadPK;
import com.echothree.model.data.forum.common.pk.ForumTypePK;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumBlobMessagePart;
import com.echothree.model.data.forum.server.entity.ForumClobMessagePart;
import com.echothree.model.data.forum.server.entity.ForumDescription;
import com.echothree.model.data.forum.server.entity.ForumDetail;
import com.echothree.model.data.forum.server.entity.ForumForumThread;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.forum.server.entity.ForumGroupDescription;
import com.echothree.model.data.forum.server.entity.ForumGroupDetail;
import com.echothree.model.data.forum.server.entity.ForumGroupForum;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachment;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachmentDescription;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachmentDetail;
import com.echothree.model.data.forum.server.entity.ForumMessageBlobAttachment;
import com.echothree.model.data.forum.server.entity.ForumMessageClobAttachment;
import com.echothree.model.data.forum.server.entity.ForumMessageDetail;
import com.echothree.model.data.forum.server.entity.ForumMessagePart;
import com.echothree.model.data.forum.server.entity.ForumMessagePartDetail;
import com.echothree.model.data.forum.server.entity.ForumMessagePartType;
import com.echothree.model.data.forum.server.entity.ForumMessagePartTypeDescription;
import com.echothree.model.data.forum.server.entity.ForumMessageRole;
import com.echothree.model.data.forum.server.entity.ForumMessageStatus;
import com.echothree.model.data.forum.server.entity.ForumMessageType;
import com.echothree.model.data.forum.server.entity.ForumMessageTypeDescription;
import com.echothree.model.data.forum.server.entity.ForumMessageTypePartType;
import com.echothree.model.data.forum.server.entity.ForumMimeType;
import com.echothree.model.data.forum.server.entity.ForumPartyRole;
import com.echothree.model.data.forum.server.entity.ForumPartyTypeRole;
import com.echothree.model.data.forum.server.entity.ForumRoleType;
import com.echothree.model.data.forum.server.entity.ForumRoleTypeDescription;
import com.echothree.model.data.forum.server.entity.ForumStringMessagePart;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.forum.server.entity.ForumThreadDetail;
import com.echothree.model.data.forum.server.entity.ForumType;
import com.echothree.model.data.forum.server.entity.ForumTypeDescription;
import com.echothree.model.data.forum.server.entity.ForumTypeMessageType;
import com.echothree.model.data.forum.server.factory.ForumBlobMessagePartFactory;
import com.echothree.model.data.forum.server.factory.ForumClobMessagePartFactory;
import com.echothree.model.data.forum.server.factory.ForumDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumFactory;
import com.echothree.model.data.forum.server.factory.ForumForumThreadFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupForumFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageAttachmentDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageAttachmentDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageAttachmentFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageBlobAttachmentFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageClobAttachmentFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageFactory;
import com.echothree.model.data.forum.server.factory.ForumMessagePartDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumMessagePartFactory;
import com.echothree.model.data.forum.server.factory.ForumMessagePartTypeDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumMessagePartTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageRoleFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageStatusFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageTypeDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageTypePartTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumMimeTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumPartyRoleFactory;
import com.echothree.model.data.forum.server.factory.ForumPartyTypeRoleFactory;
import com.echothree.model.data.forum.server.factory.ForumRoleTypeDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumRoleTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumStringMessagePartFactory;
import com.echothree.model.data.forum.server.factory.ForumThreadDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumThreadFactory;
import com.echothree.model.data.forum.server.factory.ForumTypeDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumTypeMessageTypeFactory;
import com.echothree.model.data.forum.server.value.ForumBlobMessagePartValue;
import com.echothree.model.data.forum.server.value.ForumClobMessagePartValue;
import com.echothree.model.data.forum.server.value.ForumDescriptionValue;
import com.echothree.model.data.forum.server.value.ForumDetailValue;
import com.echothree.model.data.forum.server.value.ForumForumThreadValue;
import com.echothree.model.data.forum.server.value.ForumGroupDescriptionValue;
import com.echothree.model.data.forum.server.value.ForumGroupDetailValue;
import com.echothree.model.data.forum.server.value.ForumGroupForumValue;
import com.echothree.model.data.forum.server.value.ForumMessageAttachmentDescriptionValue;
import com.echothree.model.data.forum.server.value.ForumMessageAttachmentDetailValue;
import com.echothree.model.data.forum.server.value.ForumMessageBlobAttachmentValue;
import com.echothree.model.data.forum.server.value.ForumMessageClobAttachmentValue;
import com.echothree.model.data.forum.server.value.ForumMessageDetailValue;
import com.echothree.model.data.forum.server.value.ForumMessagePartDetailValue;
import com.echothree.model.data.forum.server.value.ForumMimeTypeValue;
import com.echothree.model.data.forum.server.value.ForumStringMessagePartValue;
import com.echothree.model.data.forum.server.value.ForumThreadDetailValue;
import com.echothree.model.data.icon.common.pk.IconPK;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ForumControl
        extends BaseModelControl {
    
    /** Creates a new instance of ForumControl */
    public ForumControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ForumTransferCaches forumTransferCaches = null;
    
    public ForumTransferCaches getForumTransferCaches(UserVisit userVisit) {
        if(forumTransferCaches == null) {
            forumTransferCaches = new ForumTransferCaches(userVisit, this);
        }
        
        return forumTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Groups
    // --------------------------------------------------------------------------------
    
    public ForumGroup createForumGroup(String forumGroupName, Icon icon, Integer sortOrder, BasePK createdBy) {
        ForumGroup forumGroup = ForumGroupFactory.getInstance().create();
        ForumGroupDetail forumGroupDetail = ForumGroupDetailFactory.getInstance().create(forumGroup, forumGroupName, icon,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        forumGroup = ForumGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                forumGroup.getPrimaryKey());
        forumGroup.setActiveDetail(forumGroupDetail);
        forumGroup.setLastDetail(forumGroupDetail);
        forumGroup.store();
        
        sendEventUsingNames(forumGroup.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return forumGroup;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.ForumGroup */
    public ForumGroup getForumGroupByEntityInstance(EntityInstance entityInstance) {
        ForumGroupPK pk = new ForumGroupPK(entityInstance.getEntityUniqueId());
        ForumGroup forumGroup = ForumGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return forumGroup;
    }

    public long countForumGroups() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM forumgroups, forumgroupdetails " +
                "WHERE frmgrp_activedetailid = frmgrpdt_forumgroupdetailid");
    }

    public List<ForumGroup> getForumGroups() {
        PreparedStatement ps = ForumGroupFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forumgroups, forumgroupdetails " +
                "WHERE frmgrp_activedetailid = frmgrpdt_forumgroupdetailid " +
                "ORDER BY frmgrpdt_sortorder, frmgrpdt_forumgroupname");
        
        return ForumGroupFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    private ForumGroup getForumGroupByName(String forumGroupName, EntityPermission entityPermission) {
        ForumGroup forumGroup = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroups, forumgroupdetails " +
                        "WHERE frmgrp_activedetailid = frmgrpdt_forumgroupdetailid AND frmgrpdt_forumgroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroups, forumgroupdetails " +
                        "WHERE frmgrp_activedetailid = frmgrpdt_forumgroupdetailid AND frmgrpdt_forumgroupname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumGroupFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, forumGroupName);
            
            forumGroup = ForumGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroup;
    }
    
    public ForumGroup getForumGroupByName(String forumGroupName) {
        return getForumGroupByName(forumGroupName, EntityPermission.READ_ONLY);
    }
    
    public ForumGroup getForumGroupByNameForUpdate(String forumGroupName) {
        return getForumGroupByName(forumGroupName, EntityPermission.READ_WRITE);
    }
    
    public ForumGroupDetailValue getForumGroupDetailValueForUpdate(ForumGroup forumGroup) {
        return forumGroup == null? null: forumGroup.getLastDetailForUpdate().getForumGroupDetailValue().clone();
    }
    
    public ForumGroupDetailValue getForumGroupDetailValueByNameForUpdate(String forumGroupName) {
        return getForumGroupDetailValueForUpdate(getForumGroupByNameForUpdate(forumGroupName));
    }
    
    public ForumGroupChoicesBean getForumGroupChoices(String defaultForumGroupChoice, Language language, boolean allowNullChoice) {
        List<ForumGroup> forumGroups = getForumGroups();
        int size = forumGroups.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultForumGroupChoice == null) {
                defaultValue = "";
            }
        }
        
        for(ForumGroup forumGroup: forumGroups) {
            ForumGroupDetail forumGroupDetail = forumGroup.getLastDetail();
            
            String label = getBestForumGroupDescription(forumGroup, language);
            String value = forumGroupDetail.getForumGroupName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultForumGroupChoice == null? false: defaultForumGroupChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new ForumGroupChoicesBean(labels, values, defaultValue);
    }
    
    public ForumGroupTransfer getForumGroupTransfer(UserVisit userVisit, ForumGroup forumGroup) {
        return getForumTransferCaches(userVisit).getForumGroupTransferCache().getForumGroupTransfer(forumGroup);
    }
    
    public List<ForumGroupTransfer> getForumGroupTransfers(UserVisit userVisit, List<ForumGroup> forumGroups) {
        List<ForumGroupTransfer> forumGroupTransfers = new ArrayList<>(forumGroups.size());
        ForumGroupTransferCache forumGroupTransferCache = getForumTransferCaches(userVisit).getForumGroupTransferCache();
        
        forumGroups.stream().forEach((forumGroup) -> {
            forumGroupTransfers.add(forumGroupTransferCache.getForumGroupTransfer(forumGroup));
        });
        
        return forumGroupTransfers;
    }
    
    public List<ForumGroupTransfer> getForumGroupTransfers(UserVisit userVisit) {
        return getForumGroupTransfers(userVisit, getForumGroups());
    }
    
    public void updateForumGroupFromValue(ForumGroupDetailValue forumGroupDetailValue, BasePK updatedBy) {
        if(forumGroupDetailValue.hasBeenModified()) {
            ForumGroup forumGroup = ForumGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumGroupDetailValue.getForumGroupPK());
            ForumGroupDetail forumGroupDetail = forumGroup.getActiveDetailForUpdate();
            
            forumGroupDetail.setThruTime(session.START_TIME_LONG);
            forumGroupDetail.store();
            
            ForumGroupPK forumGroupPK = forumGroupDetail.getForumGroupPK();
            String forumGroupName = forumGroupDetailValue.getForumGroupName();
            IconPK iconPK = forumGroupDetailValue.getIconPK();
            Integer sortOrder = forumGroupDetailValue.getSortOrder();
            
            forumGroupDetail = ForumGroupDetailFactory.getInstance().create(forumGroupPK, forumGroupName, iconPK,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            forumGroup.setActiveDetail(forumGroupDetail);
            forumGroup.setLastDetail(forumGroupDetail);
            
            sendEventUsingNames(forumGroupPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteForumGroup(ForumGroup forumGroup, BasePK deletedBy) {
        deleteForumGroupDescriptionsByForumGroup(forumGroup, deletedBy);
        deleteForumGroupForumsByForumGroup(forumGroup, deletedBy);
        
        ForumGroupDetail forumGroupDetail = forumGroup.getLastDetailForUpdate();
        forumGroupDetail.setThruTime(session.START_TIME_LONG);
        forumGroup.setActiveDetail(null);
        forumGroup.store();
        
        sendEventUsingNames(forumGroup.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Group Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumGroupDescription createForumGroupDescription(ForumGroup forumGroup, Language language, String description, BasePK createdBy) {
        ForumGroupDescription forumGroupDescription = ForumGroupDescriptionFactory.getInstance().create(forumGroup,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forumGroup.getPrimaryKey(), EventTypes.MODIFY.name(), forumGroupDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumGroupDescription;
    }
    
    private ForumGroupDescription getForumGroupDescription(ForumGroup forumGroup, Language language,
            EntityPermission entityPermission) {
        ForumGroupDescription forumGroupDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupdescriptions " +
                        "WHERE frmgrpd_frmgrp_forumgroupid = ? AND frmgrpd_lang_languageid = ? AND frmgrpd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupdescriptions " +
                        "WHERE frmgrpd_frmgrp_forumgroupid = ? AND frmgrpd_lang_languageid = ? AND frmgrpd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            forumGroupDescription = ForumGroupDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupDescription;
    }
    
    public ForumGroupDescription getForumGroupDescription(ForumGroup forumGroup, Language language) {
        return getForumGroupDescription(forumGroup, language, EntityPermission.READ_ONLY);
    }
    
    public ForumGroupDescription getForumGroupDescriptionForUpdate(ForumGroup forumGroup, Language language) {
        return getForumGroupDescription(forumGroup, language, EntityPermission.READ_WRITE);
    }
    
    public ForumGroupDescriptionValue getForumGroupDescriptionValue(ForumGroupDescription forumGroupDescription) {
        return forumGroupDescription == null? null: forumGroupDescription.getForumGroupDescriptionValue().clone();
    }
    
    public ForumGroupDescriptionValue getForumGroupDescriptionValueForUpdate(ForumGroup forumGroup, Language language) {
        return getForumGroupDescriptionValue(getForumGroupDescriptionForUpdate(forumGroup, language));
    }
    
    private List<ForumGroupDescription> getForumGroupDescriptionsByForumGroup(ForumGroup forumGroup,
            EntityPermission entityPermission) {
        List<ForumGroupDescription> forumGroupDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupdescriptions, languages " +
                        "WHERE frmgrpd_frmgrp_forumgroupid = ? AND frmgrpd_thrutime = ? " +
                        "AND frmgrpd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupdescriptions " +
                        "WHERE frmgrpd_frmgrp_forumgroupid = ? AND frmgrpd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumGroupDescriptions = ForumGroupDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupDescriptions;
    }
    
    public List<ForumGroupDescription> getForumGroupDescriptionsByForumGroup(ForumGroup forumGroup) {
        return getForumGroupDescriptionsByForumGroup(forumGroup, EntityPermission.READ_ONLY);
    }
    
    public List<ForumGroupDescription> getForumGroupDescriptionsByForumGroupForUpdate(ForumGroup forumGroup) {
        return getForumGroupDescriptionsByForumGroup(forumGroup, EntityPermission.READ_WRITE);
    }
    
    public String getBestForumGroupDescription(ForumGroup forumGroup, Language language) {
        String description;
        ForumGroupDescription forumGroupDescription = getForumGroupDescription(forumGroup, language);
        
        if(forumGroupDescription == null && !language.getIsDefault()) {
            forumGroupDescription = getForumGroupDescription(forumGroup, getPartyControl().getDefaultLanguage());
        }
        
        if(forumGroupDescription == null) {
            description = forumGroup.getLastDetail().getForumGroupName();
        } else {
            description = forumGroupDescription.getDescription();
        }
        
        return description;
    }
    
    public ForumGroupDescriptionTransfer getForumGroupDescriptionTransfer(UserVisit userVisit,
            ForumGroupDescription forumGroupDescription) {
        return getForumTransferCaches(userVisit).getForumGroupDescriptionTransferCache().getForumGroupDescriptionTransfer(forumGroupDescription);
    }
    
    public List<ForumGroupDescriptionTransfer> getForumGroupDescriptionTransfersByForumGroup(UserVisit userVisit,
            ForumGroup forumGroup) {
        List<ForumGroupDescription> forumGroupDescriptions = getForumGroupDescriptionsByForumGroup(forumGroup);
        List<ForumGroupDescriptionTransfer> forumGroupDescriptionTransfers = null;
        
        if(forumGroupDescriptions != null) {
            ForumGroupDescriptionTransferCache forumGroupDescriptionTransferCache = getForumTransferCaches(userVisit).getForumGroupDescriptionTransferCache();
            
            forumGroupDescriptionTransfers = new ArrayList<>(forumGroupDescriptions.size());
            
            for(ForumGroupDescription forumGroupDescription: forumGroupDescriptions) {
                forumGroupDescriptionTransfers.add(forumGroupDescriptionTransferCache.getForumGroupDescriptionTransfer(forumGroupDescription));
            }
        }
        
        return forumGroupDescriptionTransfers;
    }
    
    public void updateForumGroupDescriptionFromValue(ForumGroupDescriptionValue forumGroupDescriptionValue, BasePK updatedBy) {
        if(forumGroupDescriptionValue.hasBeenModified()) {
            ForumGroupDescription forumGroupDescription = ForumGroupDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumGroupDescriptionValue.getPrimaryKey());
            
            forumGroupDescription.setThruTime(session.START_TIME_LONG);
            forumGroupDescription.store();
            
            ForumGroup forumGroup = forumGroupDescription.getForumGroup();
            Language language = forumGroupDescription.getLanguage();
            String description = forumGroupDescriptionValue.getDescription();
            
            forumGroupDescription = ForumGroupDescriptionFactory.getInstance().create(forumGroup, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(forumGroup.getPrimaryKey(), EventTypes.MODIFY.name(), forumGroupDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteForumGroupDescription(ForumGroupDescription forumGroupDescription, BasePK deletedBy) {
        forumGroupDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(forumGroupDescription.getForumGroupPK(), EventTypes.MODIFY.name(), forumGroupDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumGroupDescriptionsByForumGroup(ForumGroup forumGroup, BasePK deletedBy) {
        List<ForumGroupDescription> forumGroupDescriptions = getForumGroupDescriptionsByForumGroupForUpdate(forumGroup);
        
        forumGroupDescriptions.stream().forEach((forumGroupDescription) -> {
            deleteForumGroupDescription(forumGroupDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Forums
    // --------------------------------------------------------------------------------
    
    public Forum createForum(String forumName, ForumType forumType, Icon icon, Sequence forumThreadSequence,
            Sequence forumMessageSequence, Integer sortOrder, BasePK createdBy) {
        Forum forum = ForumFactory.getInstance().create();
        ForumDetail forumDetail = ForumDetailFactory.getInstance().create(forum, forumName, forumType, icon,
                forumThreadSequence, forumMessageSequence, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        forum = ForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                forum.getPrimaryKey());
        forum.setActiveDetail(forumDetail);
        forum.setLastDetail(forumDetail);
        forum.store();
        
        sendEventUsingNames(forum.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return forum;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Forum */
    public Forum getForumByEntityInstance(EntityInstance entityInstance) {
        ForumPK pk = new ForumPK(entityInstance.getEntityUniqueId());
        Forum forum = ForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return forum;
    }

    public long countForums() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM forums, forumdetails " +
                "WHERE frm_activedetailid = frmdt_forumdetailid");
    }

    public List<Forum> getForums() {
        PreparedStatement ps = ForumFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forums, forumdetails " +
                "WHERE frm_activedetailid = frmdt_forumdetailid " +
                "ORDER BY frmdt_sortorder, frmdt_forumname");
        
        return ForumFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    private Forum getForumByName(String forumName, EntityPermission entityPermission) {
        Forum forum = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forums, forumdetails " +
                        "WHERE frm_activedetailid = frmdt_forumdetailid AND frmdt_forumname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forums, forumdetails " +
                        "WHERE frm_activedetailid = frmdt_forumdetailid AND frmdt_forumname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, forumName);
            
            forum = ForumFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forum;
    }
    
    public Forum getForumByName(String forumName) {
        return getForumByName(forumName, EntityPermission.READ_ONLY);
    }
    
    public Forum getForumByNameForUpdate(String forumName) {
        return getForumByName(forumName, EntityPermission.READ_WRITE);
    }
    
    public ForumDetailValue getForumDetailValueForUpdate(Forum forum) {
        return forum == null? null: forum.getLastDetailForUpdate().getForumDetailValue().clone();
    }
    
    public ForumDetailValue getForumDetailValueByNameForUpdate(String forumName) {
        return getForumDetailValueForUpdate(getForumByNameForUpdate(forumName));
    }
    
    public ForumTransfer getForumTransfer(UserVisit userVisit, Forum forum) {
        return getForumTransferCaches(userVisit).getForumTransferCache().getForumTransfer(forum);
    }
    
    public ForumChoicesBean getForumChoices(String defaultForumChoice, Language language, boolean allowNullChoice) {
        List<Forum> forums = getForums();
        int size = forums.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultForumChoice == null) {
                defaultValue = "";
            }
        }
        
        for(Forum forum: forums) {
            ForumDetail forumDetail = forum.getLastDetail();
            
            String label = getBestForumDescription(forum, language);
            String value = forumDetail.getForumName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultForumChoice == null? false: defaultForumChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new ForumChoicesBean(labels, values, defaultValue);
    }
    
    public List<ForumTransfer> getForumTransfers(final UserVisit userVisit, final List<Forum> forums) {
        List<ForumTransfer> forumTransfers = new ArrayList<>(forums.size());
        ForumTransferCache forumTransferCache = getForumTransferCaches(userVisit).getForumTransferCache();
        
        forums.stream().forEach((forum) -> {
            forumTransfers.add(forumTransferCache.getForumTransfer(forum));
        });
        
        return forumTransfers;
    }
    
    public List<ForumTransfer> getForumTransfers(UserVisit userVisit) {
        return getForumTransfers(userVisit, getForums());
    }
    
    public void updateForumFromValue(ForumDetailValue forumDetailValue, BasePK updatedBy) {
        if(forumDetailValue.hasBeenModified()) {
            Forum forum = ForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumDetailValue.getForumPK());
            ForumDetail forumDetail = forum.getActiveDetailForUpdate();
            
            forumDetail.setThruTime(session.START_TIME_LONG);
            forumDetail.store();
            
            ForumPK forumPK = forumDetail.getForumPK();
            String forumName = forumDetailValue.getForumName();
            ForumTypePK forumTypePK = forumDetail.getForumTypePK(); // Not updated
            IconPK iconPK = forumDetailValue.getIconPK();
            SequencePK forumThreadSequencePK = forumDetailValue.getForumThreadSequencePK();
            SequencePK forumMessageSequencePK = forumDetailValue.getForumMessageSequencePK();
            Integer sortOrder = forumDetailValue.getSortOrder();
            
            forumDetail = ForumDetailFactory.getInstance().create(forumPK, forumName, forumTypePK, iconPK,
                    forumThreadSequencePK, forumMessageSequencePK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            forum.setActiveDetail(forumDetail);
            forum.setLastDetail(forumDetail);
            
            sendEventUsingNames(forumPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void touchForumsByForumThread(ForumThread forumThread, BasePK relatedPK, String relatedEventTypeName, BasePK touchedBy) {
        List<ForumForumThread> forumForumThreads = getForumForumThreadsByForumThread(forumThread);
        
        forumForumThreads.stream().forEach((forumForumThread) -> {
            sendEventUsingNames(forumForumThread.getForum().getPrimaryKey(), EventTypes.TOUCH.name(), relatedPK, relatedEventTypeName, touchedBy);
        });
    }
    
    public void deleteForum(Forum forum, BasePK deletedBy) {
        deleteForumDescriptionsByForum(forum, deletedBy);
        deleteForumGroupForumsByForum(forum, deletedBy);
        deleteForumForumThreadsByForum(forum, deletedBy);
        
        ForumDetail forumDetail = forum.getLastDetailForUpdate();
        forumDetail.setThruTime(session.START_TIME_LONG);
        forum.setActiveDetail(null);
        forum.store();
        
        sendEventUsingNames(forum.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteForums(List<Forum> forums, BasePK deletedBy) {
        forums.stream().forEach((forum) -> {
            deleteForum(forum, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumDescription createForumDescription(Forum forum, Language language, String description, BasePK createdBy) {
        ForumDescription forumDescription = ForumDescriptionFactory.getInstance().create(forum, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forum.getPrimaryKey(), EventTypes.MODIFY.name(), forumDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumDescription;
    }
    
    private ForumDescription getForumDescription(Forum forum, Language language, EntityPermission entityPermission) {
        ForumDescription forumDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumdescriptions " +
                        "WHERE frmd_frm_forumid = ? AND frmd_lang_languageid = ? AND frmd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumdescriptions " +
                        "WHERE frmd_frm_forumid = ? AND frmd_lang_languageid = ? AND frmd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            forumDescription = ForumDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumDescription;
    }
    
    public ForumDescription getForumDescription(Forum forum, Language language) {
        return getForumDescription(forum, language, EntityPermission.READ_ONLY);
    }
    
    public ForumDescription getForumDescriptionForUpdate(Forum forum, Language language) {
        return getForumDescription(forum, language, EntityPermission.READ_WRITE);
    }
    
    public ForumDescriptionValue getForumDescriptionValue(ForumDescription forumDescription) {
        return forumDescription == null? null: forumDescription.getForumDescriptionValue().clone();
    }
    
    public ForumDescriptionValue getForumDescriptionValueForUpdate(Forum forum, Language language) {
        return getForumDescriptionValue(getForumDescriptionForUpdate(forum, language));
    }
    
    private List<ForumDescription> getForumDescriptionsByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumDescription> forumDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumdescriptions, languages " +
                        "WHERE frmd_frm_forumid = ? AND frmd_thrutime = ? AND frmd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumdescriptions " +
                        "WHERE frmd_frm_forumid = ? AND frmd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumDescriptions = ForumDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumDescriptions;
    }
    
    public List<ForumDescription> getForumDescriptionsByForum(Forum forum) {
        return getForumDescriptionsByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumDescription> getForumDescriptionsByForumForUpdate(Forum forum) {
        return getForumDescriptionsByForum(forum, EntityPermission.READ_WRITE);
    }
    
    public String getBestForumDescription(Forum forum, Language language) {
        String description;
        ForumDescription forumDescription = getForumDescription(forum, language);
        
        if(forumDescription == null && !language.getIsDefault()) {
            forumDescription = getForumDescription(forum, getPartyControl().getDefaultLanguage());
        }
        
        if(forumDescription == null) {
            description = forum.getLastDetail().getForumName();
        } else {
            description = forumDescription.getDescription();
        }
        
        return description;
    }
    
    public ForumDescriptionTransfer getForumDescriptionTransfer(UserVisit userVisit, ForumDescription forumDescription) {
        return getForumTransferCaches(userVisit).getForumDescriptionTransferCache().getForumDescriptionTransfer(forumDescription);
    }
    
    public List<ForumDescriptionTransfer> getForumDescriptionTransfersByForum(UserVisit userVisit, Forum forum) {
        List<ForumDescription> forumDescriptions = getForumDescriptionsByForum(forum);
        List<ForumDescriptionTransfer> forumDescriptionTransfers = null;
        
        if(forumDescriptions != null) {
            ForumDescriptionTransferCache forumDescriptionTransferCache = getForumTransferCaches(userVisit).getForumDescriptionTransferCache();
            
            forumDescriptionTransfers = new ArrayList<>(forumDescriptions.size());
            
            for(ForumDescription forumDescription: forumDescriptions) {
                forumDescriptionTransfers.add(forumDescriptionTransferCache.getForumDescriptionTransfer(forumDescription));
            }
        }
        
        return forumDescriptionTransfers;
    }
    
    public void updateForumDescriptionFromValue(ForumDescriptionValue forumDescriptionValue, BasePK updatedBy) {
        if(forumDescriptionValue.hasBeenModified()) {
            ForumDescription forumDescription = ForumDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumDescriptionValue.getPrimaryKey());
            
            forumDescription.setThruTime(session.START_TIME_LONG);
            forumDescription.store();
            
            Forum forum = forumDescription.getForum();
            Language language = forumDescription.getLanguage();
            String description = forumDescriptionValue.getDescription();
            
            forumDescription = ForumDescriptionFactory.getInstance().create(forum, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(forum.getPrimaryKey(), EventTypes.MODIFY.name(), forumDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteForumDescription(ForumDescription forumDescription, BasePK deletedBy) {
        forumDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(forumDescription.getForumPK(), EventTypes.MODIFY.name(), forumDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumDescriptionsByForum(Forum forum, BasePK deletedBy) {
        List<ForumDescription> forumDescriptions = getForumDescriptionsByForumForUpdate(forum);
        
        forumDescriptions.stream().forEach((forumDescription) -> {
            deleteForumDescription(forumDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Group Forums
    // --------------------------------------------------------------------------------
    
    public ForumGroupForum createForumGroupForum(ForumGroup forumGroup, Forum forum, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        ForumGroupForum defaultForumGroupForum = getDefaultForumGroupForum(forum);
        boolean defaultFound = defaultForumGroupForum != null;
        
        if(defaultFound && isDefault) {
            ForumGroupForumValue defaultForumGroupForumValue = getDefaultForumGroupForumValueForUpdate(forum);
            
            defaultForumGroupForumValue.setIsDefault(Boolean.FALSE);
            updateForumGroupForumFromValue(defaultForumGroupForumValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ForumGroupForum forumGroupForum = ForumGroupForumFactory.getInstance().create(forumGroup, forum,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forumGroup.getPrimaryKey(), EventTypes.MODIFY.name(), forumGroupForum.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumGroupForum;
    }
    
    public long countForumGroupForumsByForumGroup(ForumGroup forumGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM forumgroupforums " +
                "WHERE frmgrpfrm_frmgrp_forumgroupid = ?",
                forumGroup);
    }

    private ForumGroupForum getForumGroupForum(ForumGroup forumGroup, Forum forum, EntityPermission entityPermission) {
        ForumGroupForum forumGroupForum = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frmgrp_forumgroupid = ? AND frmgrpfrm_frm_forumid = ? AND frmgrpfrm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frmgrp_forumgroupid = ? AND frmgrpfrm_frm_forumid = ? AND frmgrpfrm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumGroupForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, forum.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            forumGroupForum = ForumGroupForumFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupForum;
    }
    
    public ForumGroupForum getForumGroupForum(ForumGroup forumGroup, Forum forum) {
        return getForumGroupForum(forumGroup, forum, EntityPermission.READ_ONLY);
    }
    
    public ForumGroupForum getForumGroupForumForUpdate(ForumGroup forumGroup, Forum forum) {
        return getForumGroupForum(forumGroup, forum, EntityPermission.READ_WRITE);
    }
    
    public ForumGroupForumValue getForumGroupForumValue(ForumGroupForum forumGroupForum) {
        return forumGroupForum == null? null: forumGroupForum.getForumGroupForumValue().clone();
    }
    
    public ForumGroupForumValue getForumGroupForumValueForUpdate(ForumGroup forumGroup, Forum forum) {
        return getForumGroupForumValue(getForumGroupForumForUpdate(forumGroup, forum));
    }
    
    private ForumGroupForum getDefaultForumGroupForum(Forum forum, EntityPermission entityPermission) {
        ForumGroupForum forumGroupForum = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frm_forumid = ? AND frmgrpfrm_isdefault = 1 AND frmgrpfrm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frm_forumid = ? AND frmgrpfrm_isdefault = 1 AND frmgrpfrm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumGroupForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumGroupForum = ForumGroupForumFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupForum;
    }
    
    public ForumGroupForum getDefaultForumGroupForum(Forum forum) {
        return getDefaultForumGroupForum(forum, EntityPermission.READ_ONLY);
    }
    
    public ForumGroupForum getDefaultForumGroupForumForUpdate(Forum forum) {
        return getDefaultForumGroupForum(forum, EntityPermission.READ_WRITE);
    }
    
    public ForumGroupForumValue getDefaultForumGroupForumValueForUpdate(Forum forum) {
        ForumGroupForum forumGroupForum = getDefaultForumGroupForumForUpdate(forum);
        
        return forumGroupForum == null? null: forumGroupForum.getForumGroupForumValue().clone();
    }
    
    private List<ForumGroupForum> getForumGroupForumsByForumGroup(ForumGroup forumGroup, EntityPermission entityPermission) {
        List<ForumGroupForum> forumGroupForums = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums, forums, forumdetails " +
                        "WHERE frmgrpfrm_frmgrp_forumgroupid = ? AND frmgrpfrm_thrutime = ? " +
                        "AND frmgrpfrm_frm_forumid = frm_forumid AND frm_lastdetailid = frmdt_forumdetailid " +
                        "ORDER BY frmdt_sortorder, frmdt_forumname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frmgrp_forumgroupid = ? AND frmgrpfrm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumGroupForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumGroupForums = ForumGroupForumFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupForums;
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForumGroup(ForumGroup forumGroup) {
        return getForumGroupForumsByForumGroup(forumGroup, EntityPermission.READ_ONLY);
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForumGroupForUpdate(ForumGroup forumGroup) {
        return getForumGroupForumsByForumGroup(forumGroup, EntityPermission.READ_WRITE);
    }
    
    private List<ForumGroupForum> getForumGroupForumsByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumGroupForum> forumGroupForums = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums, forumgroups, forumgroupdetails " +
                        "WHERE frmgrpfrm_frm_forumid = ? AND frmgrpfrm_thrutime = ? " +
                        "AND frmgrpfrm_frmgrp_forumgroupid = frmgrp_forumgroupid AND frmgrp_lastdetailid = frmgrpdt_forumgroupdetailid " +
                        "ORDER BY frmgrpdt_sortorder, frmgrpdt_forumgroupname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frm_forumid = ? AND frmgrpfrm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumGroupForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumGroupForums = ForumGroupForumFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupForums;
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForum(Forum forum) {
        return getForumGroupForumsByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForumForUpdate(Forum forum) {
        return getForumGroupForumsByForum(forum, EntityPermission.READ_WRITE);
    }
    
    public List<ForumGroupForumTransfer> getForumGroupForumTransfers(UserVisit userVisit, List<ForumGroupForum> forumGroupForums) {
        List<ForumGroupForumTransfer> forumGroupForumTransfers = new ArrayList<>(forumGroupForums.size());
        ForumGroupForumTransferCache forumGroupForumTransferCache = getForumTransferCaches(userVisit).getForumGroupForumTransferCache();
        
        forumGroupForums.stream().forEach((forumGroupForum) -> {
            forumGroupForumTransfers.add(forumGroupForumTransferCache.getForumGroupForumTransfer(forumGroupForum));
        });
        
        return forumGroupForumTransfers;
    }
    
    public List<ForumGroupForumTransfer> getForumGroupForumTransfersByForumGroup(UserVisit userVisit, ForumGroup forumGroup) {
        return getForumGroupForumTransfers(userVisit, getForumGroupForumsByForumGroup(forumGroup));
    }
    
    public List<ForumGroupForumTransfer> getForumGroupForumTransfersByForum(UserVisit userVisit, Forum forum) {
        return getForumGroupForumTransfers(userVisit, getForumGroupForumsByForum(forum));
    }
    
    public ForumGroupForumTransfer getForumGroupForumTransfer(UserVisit userVisit, ForumGroupForum forumGroupForum) {
        return getForumTransferCaches(userVisit).getForumGroupForumTransferCache().getForumGroupForumTransfer(forumGroupForum);
    }
    
    private void updateForumGroupForumFromValue(ForumGroupForumValue forumGroupForumValue, boolean checkDefault, BasePK updatedBy) {
        if(forumGroupForumValue.hasBeenModified()) {
            ForumGroupForum forumGroupForum = ForumGroupForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumGroupForumValue.getPrimaryKey());
            
            forumGroupForum.setThruTime(session.START_TIME_LONG);
            forumGroupForum.store();
            
            ForumGroupPK forumGroupPK = forumGroupForum.getForumGroupPK(); // Not Updated
            Forum forum = forumGroupForum.getForum(); // Not Updated
            Boolean isDefault = forumGroupForumValue.getIsDefault();
            Integer sortOrder = forumGroupForumValue.getSortOrder();
            
            if(checkDefault) {
                ForumGroupForum defaultForumGroupForum = getDefaultForumGroupForum(forum);
                boolean defaultFound = defaultForumGroupForum != null && !defaultForumGroupForum.equals(forumGroupForum);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ForumGroupForumValue defaultForumGroupForumValue = getDefaultForumGroupForumValueForUpdate(forum);
                    
                    defaultForumGroupForumValue.setIsDefault(Boolean.FALSE);
                    updateForumGroupForumFromValue(defaultForumGroupForumValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            forumGroupForum = ForumGroupForumFactory.getInstance().create(forumGroupPK, forum.getPrimaryKey(), isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(forumGroupPK, EventTypes.MODIFY.name(), forumGroupForum.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateForumGroupForumFromValue(ForumGroupForumValue forumGroupForumValue, BasePK updatedBy) {
        updateForumGroupForumFromValue(forumGroupForumValue, true, updatedBy);
    }
    
    public void deleteForumGroupForum(ForumGroupForum forumGroupForum, BasePK deletedBy) {
        forumGroupForum.setThruTime(session.START_TIME_LONG);
        forumGroupForum.store();
        
        // Check for default, and pick one if necessary
        Forum forum = forumGroupForum.getForum();
        ForumGroupForum defaultForumGroupForum = getDefaultForumGroupForum(forum);
        if(defaultForumGroupForum == null) {
            List<ForumGroupForum> forumGroupForums = getForumGroupForumsByForumForUpdate(forum);
            
            if(!forumGroupForums.isEmpty()) {
                Iterator<ForumGroupForum> iter = forumGroupForums.iterator();
                if(iter.hasNext()) {
                    defaultForumGroupForum = iter.next();
                }
                ForumGroupForumValue forumGroupForumValue = defaultForumGroupForum.getForumGroupForumValue().clone();
                
                forumGroupForumValue.setIsDefault(Boolean.TRUE);
                updateForumGroupForumFromValue(forumGroupForumValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(forumGroupForum.getForumGroupPK(), EventTypes.MODIFY.name(), forumGroupForum.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumGroupForums(List<ForumGroupForum> forumGroupForums, BasePK deletedBy) {
        forumGroupForums.stream().forEach((forumGroupForum) -> {
            deleteForumGroupForum(forumGroupForum, deletedBy);
        });
    }
    
    public void deleteForumGroupForumsByForumGroup(ForumGroup forumGroup, BasePK deletedBy) {
        deleteForumGroupForums(getForumGroupForumsByForumGroupForUpdate(forumGroup), deletedBy);
    }
    
    public void deleteForumGroupForumsByForum(Forum forum, BasePK deletedBy) {
        deleteForumGroupForums(getForumGroupForumsByForumForUpdate(forum), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Types
    // --------------------------------------------------------------------------------
    
    public ForumRoleType createForumRoleType(String forumRoleTypeName, Boolean isDefault, Integer sortOrder) {
        return ForumRoleTypeFactory.getInstance().create(forumRoleTypeName, isDefault, sortOrder);
    }
    
    public ForumRoleType getForumRoleTypeByName(String forumRoleTypeName) {
        ForumRoleType forumRoleType = null;
        
        try {
            PreparedStatement ps = ForumRoleTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forumroletypes " +
                    "WHERE frmrtyp_forumroletypename = ?");
            
            ps.setString(1, forumRoleTypeName);
            
            forumRoleType = ForumRoleTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumRoleType;
    }
    
    public List<ForumRoleType> getForumRoleTypes() {
        PreparedStatement ps = ForumRoleTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forumroletypes " +
                "ORDER BY frmrtyp_sortorder, frmrtyp_forumroletypename");
        
        return ForumRoleTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ForumRoleTypeChoicesBean getForumRoleTypeChoices(String defaultForumRoleTypeChoice, Language language,
            boolean allowNullChoice) {
        List<ForumRoleType> forumRoleTypes = getForumRoleTypes();
        int size = forumRoleTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultForumRoleTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(ForumRoleType forumRoleType: forumRoleTypes) {
            String label = getBestForumRoleTypeDescription(forumRoleType, language);
            String value = forumRoleType.getForumRoleTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultForumRoleTypeChoice == null? false: defaultForumRoleTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && forumRoleType.getIsDefault()))
                defaultValue = value;
        }
        
        return new ForumRoleTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ForumRoleTypeTransfer getForumRoleTypeTransfer(UserVisit userVisit, ForumRoleType forumRoleType) {
        return getForumTransferCaches(userVisit).getForumRoleTypeTransferCache().getForumRoleTypeTransfer(forumRoleType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumRoleTypeDescription createForumRoleTypeDescription(ForumRoleType forumRoleType, Language language,
            String description) {
        return ForumRoleTypeDescriptionFactory.getInstance().create(forumRoleType, language, description);
    }
    
    public ForumRoleTypeDescription getForumRoleTypeDescription(ForumRoleType forumRoleType, Language language) {
        ForumRoleTypeDescription forumRoleTypeDescription = null;
        
        try {
            PreparedStatement ps = ForumRoleTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forumroletypedescriptions " +
                    "WHERE frmrtypd_frmrtyp_forumroletypeid = ? AND frmrtypd_lang_languageid = ?");
            
            ps.setLong(1, forumRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            forumRoleTypeDescription = ForumRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumRoleTypeDescription;
    }
    
    public String getBestForumRoleTypeDescription(ForumRoleType forumRoleType, Language language) {
        String description;
        ForumRoleTypeDescription forumRoleTypeDescription = getForumRoleTypeDescription(forumRoleType, language);
        
        if(forumRoleTypeDescription == null && !language.getIsDefault()) {
            forumRoleTypeDescription = getForumRoleTypeDescription(forumRoleType, getPartyControl().getDefaultLanguage());
        }
        
        if(forumRoleTypeDescription == null) {
            description = forumRoleType.getForumRoleTypeName();
        } else {
            description = forumRoleTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Types
    // --------------------------------------------------------------------------------
    
    public ForumType createForumType(String forumTypeName, Boolean isDefault, Integer sortOrder) {
        return ForumTypeFactory.getInstance().create(forumTypeName, isDefault, sortOrder);
    }
    
    public ForumType getForumTypeByName(String forumTypeName) {
        ForumType forumType = null;
        
        try {
            PreparedStatement ps = ForumTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forumtypes " +
                    "WHERE frmtyp_forumtypename = ?");
            
            ps.setString(1, forumTypeName);
            
            forumType = ForumTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumType;
    }
    
    public List<ForumType> getForumTypes() {
        PreparedStatement ps = ForumTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forumtypes " +
                "ORDER BY frmtyp_sortorder, frmtyp_forumtypename");
        
        return ForumTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ForumTypeChoicesBean getForumTypeChoices(String defaultForumTypeChoice, Language language, boolean allowNullChoice) {
        List<ForumType> forumTypes = getForumTypes();
        int size = forumTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultForumTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(ForumType forumType: forumTypes) {
            String label = getBestForumTypeDescription(forumType, language);
            String value = forumType.getForumTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultForumTypeChoice == null? false: defaultForumTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && forumType.getIsDefault()))
                defaultValue = value;
        }
        
        return new ForumTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ForumTypeTransfer getForumTypeTransfer(UserVisit userVisit, ForumType forumType) {
        return getForumTransferCaches(userVisit).getForumTypeTransferCache().getForumTypeTransfer(forumType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumTypeDescription createForumTypeDescription(ForumType forumType, Language language, String description) {
        return ForumTypeDescriptionFactory.getInstance().create(forumType, language, description);
    }
    
    public ForumTypeDescription getForumTypeDescription(ForumType forumType, Language language) {
        ForumTypeDescription forumTypeDescription = null;
        
        try {
            PreparedStatement ps = ForumTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forumtypedescriptions " +
                    "WHERE frmtypd_frmtyp_forumtypeid = ? AND frmtypd_lang_languageid = ?");
            
            ps.setLong(1, forumType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            forumTypeDescription = ForumTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumTypeDescription;
    }
    
    public String getBestForumTypeDescription(ForumType forumType, Language language) {
        String description;
        ForumTypeDescription forumTypeDescription = getForumTypeDescription(forumType, language);
        
        if(forumTypeDescription == null && !language.getIsDefault()) {
            forumTypeDescription = getForumTypeDescription(forumType, getPartyControl().getDefaultLanguage());
        }
        
        if(forumTypeDescription == null) {
            description = forumType.getForumTypeName();
        } else {
            description = forumTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Mime Types
    // --------------------------------------------------------------------------------
    
    public ForumMimeType createForumMimeType(Forum forum, MimeType mimeType, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        ForumMimeType defaultForumMimeType = getDefaultForumMimeType(forum);
        boolean defaultFound = defaultForumMimeType != null;
        
        if(defaultFound && isDefault) {
            ForumMimeTypeValue defaultForumMimeTypeValue = getDefaultForumMimeTypeValueForUpdate(forum);
            
            defaultForumMimeTypeValue.setIsDefault(Boolean.FALSE);
            updateForumMimeTypeFromValue(defaultForumMimeTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ForumMimeType forumMimeType = ForumMimeTypeFactory.getInstance().create(forum, mimeType,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forum.getPrimaryKey(), EventTypes.MODIFY.name(), forumMimeType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumMimeType;
    }
    
    private ForumMimeType getForumMimeType(Forum forum, MimeType mimeType, EntityPermission entityPermission) {
        ForumMimeType forumMimeType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_mtyp_mimetypeid = ? AND frmmtyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_mtyp_mimetypeid = ? AND frmmtyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumMimeTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, mimeType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            forumMimeType = ForumMimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMimeType;
    }
    
    public ForumMimeType getForumMimeType(Forum forum, MimeType mimeType) {
        return getForumMimeType(forum, mimeType, EntityPermission.READ_ONLY);
    }
    
    public ForumMimeType getForumMimeTypeForUpdate(Forum forum, MimeType mimeType) {
        return getForumMimeType(forum, mimeType, EntityPermission.READ_WRITE);
    }
    
    public ForumMimeTypeValue getForumMimeTypeValue(ForumMimeType forumMimeType) {
        return forumMimeType == null? null: forumMimeType.getForumMimeTypeValue().clone();
    }
    
    public ForumMimeTypeValue getForumMimeTypeValueForUpdate(Forum forum, MimeType mimeType) {
        return getForumMimeTypeValue(getForumMimeTypeForUpdate(forum, mimeType));
    }
    
    private ForumMimeType getDefaultForumMimeType(Forum forum, EntityPermission entityPermission) {
        ForumMimeType forumMimeType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_isdefault = 1 AND frmmtyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_isdefault = 1 AND frmmtyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumMimeTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumMimeType = ForumMimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMimeType;
    }
    
    public ForumMimeType getDefaultForumMimeType(Forum forum) {
        return getDefaultForumMimeType(forum, EntityPermission.READ_ONLY);
    }
    
    public ForumMimeType getDefaultForumMimeTypeForUpdate(Forum forum) {
        return getDefaultForumMimeType(forum, EntityPermission.READ_WRITE);
    }
    
    public ForumMimeTypeValue getDefaultForumMimeTypeValueForUpdate(Forum forum) {
        ForumMimeType forumMimeType = getDefaultForumMimeTypeForUpdate(forum);
        
        return forumMimeType == null? null: forumMimeType.getForumMimeTypeValue().clone();
    }
    
    private List<ForumMimeType> getForumMimeTypesByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumMimeType> forumMimeTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes, mimetypes, mimetypedetails " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_thrutime = ? " +
                        "AND frmmtyp_mtyp_mimetypeid = mtyp_mimetypeid AND mtyp_lastdetailid = mtypdt_mimetypedetailid " +
                        "ORDER BY frmmtyp_sortorder, mtypdt_sortorder, mtypdt_mimetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumMimeTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumMimeTypes = ForumMimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMimeTypes;
    }
    
    public List<ForumMimeType> getForumMimeTypesByForum(Forum forum) {
        return getForumMimeTypesByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMimeType> getForumMimeTypesByForumForUpdate(Forum forum) {
        return getForumMimeTypesByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private List<ForumMimeType> getForumMimeTypesByMimeType(MimeType mimeType, EntityPermission entityPermission) {
        List<ForumMimeType> forumMimeTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes, forums, forumdetails " +
                        "WHERE frmmtyp_mtyp_mimetypeid = ? AND frmmtyp_thrutime = ? " +
                        "AND frmmtyp_frm_forumid = frm_forumid AND frm_lastdetailid = frmdt_forumdetailid " +
                        "ORDER BY frmmtyp_sortorder, frmdt_sortorder, frmdt_forumname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_mtyp_mimetypeid = ? AND frmmtyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumMimeTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumMimeTypes = ForumMimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMimeTypes;
    }
    
    public List<ForumMimeType> getForumMimeTypesByMimeType(MimeType mimeType) {
        return getForumMimeTypesByMimeType(mimeType, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMimeType> getForumMimeTypesByMimeTypeForUpdate(MimeType mimeType) {
        return getForumMimeTypesByMimeType(mimeType, EntityPermission.READ_WRITE);
    }
    
    public MimeTypeChoicesBean getForumMimeTypeChoices(Forum forum, String defaultMimeTypeChoice, Language language,
            boolean allowNullChoice) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        List<ForumMimeType> forumMimeTypes = getForumMimeTypesByForum(forum);
        int size = forumMimeTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultMimeTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(ForumMimeType forumMimeType: forumMimeTypes) {
            MimeType mimeType = forumMimeType.getMimeType();
            String label = coreControl.getBestMimeTypeDescription(mimeType, language);
            String value = mimeType.getLastDetail().getMimeTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultMimeTypeChoice == null? false: defaultMimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && forumMimeType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new MimeTypeChoicesBean(labels, values, defaultValue);
    }
    
    public List<ForumMimeTypeTransfer> getForumMimeTypeTransfers(UserVisit userVisit, List<ForumMimeType> forumMimeTypes) {
        List<ForumMimeTypeTransfer> forumMimeTypeTransfers = new ArrayList<>(forumMimeTypes.size());
        ForumMimeTypeTransferCache forumMimeTypeTransferCache = getForumTransferCaches(userVisit).getForumMimeTypeTransferCache();
        
        forumMimeTypes.stream().forEach((forumMimeType) -> {
            forumMimeTypeTransfers.add(forumMimeTypeTransferCache.getForumMimeTypeTransfer(forumMimeType));
        });
        
        return forumMimeTypeTransfers;
    }
    
    public List<ForumMimeTypeTransfer> getForumMimeTypeTransfersByForum(UserVisit userVisit, Forum forum) {
        return getForumMimeTypeTransfers(userVisit, getForumMimeTypesByForum(forum));
    }
    
    public List<ForumMimeTypeTransfer> getForumMimeTypeTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        return getForumMimeTypeTransfers(userVisit, getForumMimeTypesByMimeType(mimeType));
    }
    
    public ForumMimeTypeTransfer getForumMimeTypeTransfer(UserVisit userVisit, ForumMimeType forumMimeType) {
        return getForumTransferCaches(userVisit).getForumMimeTypeTransferCache().getForumMimeTypeTransfer(forumMimeType);
    }
    
    private void updateForumMimeTypeFromValue(ForumMimeTypeValue forumMimeTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(forumMimeTypeValue.hasBeenModified()) {
            ForumMimeType forumMimeType = ForumMimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumMimeTypeValue.getPrimaryKey());
            
            forumMimeType.setThruTime(session.START_TIME_LONG);
            forumMimeType.store();
            
            Forum forum = forumMimeType.getForum(); // Not Updated
            ForumPK forumPK = forum.getPrimaryKey(); // Not Updated
            MimeTypePK mimeTypePK = forumMimeType.getMimeTypePK(); // Not Updated
            Boolean isDefault = forumMimeTypeValue.getIsDefault();
            Integer sortOrder = forumMimeTypeValue.getSortOrder();
            
            if(checkDefault) {
                ForumMimeType defaultForumMimeType = getDefaultForumMimeType(forum);
                boolean defaultFound = defaultForumMimeType != null && !defaultForumMimeType.equals(forumMimeType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ForumMimeTypeValue defaultForumMimeTypeValue = getDefaultForumMimeTypeValueForUpdate(forum);
                    
                    defaultForumMimeTypeValue.setIsDefault(Boolean.FALSE);
                    updateForumMimeTypeFromValue(defaultForumMimeTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            forumMimeType = ForumMimeTypeFactory.getInstance().create(forumPK, mimeTypePK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(forumPK, EventTypes.MODIFY.name(), forumMimeType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateForumMimeTypeFromValue(ForumMimeTypeValue forumMimeTypeValue, BasePK updatedBy) {
        updateForumMimeTypeFromValue(forumMimeTypeValue, true, updatedBy);
    }
    
    public void deleteForumMimeType(ForumMimeType forumMimeType, BasePK deletedBy) {
        forumMimeType.setThruTime(session.START_TIME_LONG);
        forumMimeType.store();
        
        // Check for default, and pick one if necessary
        Forum forum = forumMimeType.getForum();
        ForumMimeType defaultForumMimeType = getDefaultForumMimeType(forum);
        if(defaultForumMimeType == null) {
            List<ForumMimeType> forumMimeTypes = getForumMimeTypesByForumForUpdate(forum);
            
            if(!forumMimeTypes.isEmpty()) {
                Iterator<ForumMimeType> iter = forumMimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultForumMimeType = iter.next();
                }
                ForumMimeTypeValue forumMimeTypeValue = defaultForumMimeType.getForumMimeTypeValue().clone();
                
                forumMimeTypeValue.setIsDefault(Boolean.TRUE);
                updateForumMimeTypeFromValue(forumMimeTypeValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(forum.getPrimaryKey(), EventTypes.MODIFY.name(), forumMimeType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumMimeTypes(List<ForumMimeType> forumMimeTypes, BasePK deletedBy) {
        forumMimeTypes.stream().forEach((forumMimeType) -> {
            deleteForumMimeType(forumMimeType, deletedBy);
        });
    }
    
    public void deleteForumMimeTypesByForum(Forum forum, BasePK deletedBy) {
        deleteForumMimeTypes(getForumMimeTypesByForumForUpdate(forum), deletedBy);
    }
    
    public void deleteForumMimeTypesByMimeType(MimeType mimeType, BasePK deletedBy) {
        deleteForumMimeTypes(getForumMimeTypesByMimeTypeForUpdate(mimeType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Roles
    // --------------------------------------------------------------------------------
    
    public ForumPartyRole createForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType, BasePK createdBy) {
        ForumPartyRole forumPartyRole = ForumPartyRoleFactory.getInstance().create(forum, party, forumRoleType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forum.getPrimaryKey(), EventTypes.MODIFY.name(), forumPartyRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumPartyRole;
    }
    
    public boolean hasForumPartyRoles(Forum forum, ForumRoleType forumRoleType) {
        return !(session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM forumpartyroles " +
                "WHERE frmparr_frm_forumid = ? AND frmparr_frmrtyp_forumroletypeid = ? AND frmparr_thrutime = ?",
                forum, forumRoleType, Session.MAX_TIME) == 0);
    }

    public boolean hasForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType) {
        return !(session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM forumpartyroles " +
                "WHERE frmparr_frm_forumid = ? AND frmparr_par_partyid = ? AND frmparr_frmrtyp_forumroletypeid = ? AND frmparr_thrutime = ?",
                forum, party, forumRoleType, Session.MAX_TIME) == 0);
    }

    private List<ForumPartyRole> getForumPartyRolesByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumPartyRole> forumPartyRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles, parties, partydetails, forumroletypes " +
                        "WHERE frmparr_frm_forumid = ? AND frmparr_thrutime = ? " +
                        "AND frmparr_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "AND frmparr_frmrtyp_forumroletypeid = frmrtyp_forumroletypeid " +
                        "ORDER BY pardt_partyname, frmrtyp_sortorder, frmrtyp_forumroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles " +
                        "WHERE frmparr_frm_forumid = ? AND frmparr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumPartyRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumPartyRoles = ForumPartyRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumPartyRoles;
    }
    
    public List<ForumPartyRole> getForumPartyRolesByForum(Forum forum) {
        return getForumPartyRolesByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumPartyRole> getForumPartyRolesByForumForUpdate(Forum forum) {
        return getForumPartyRolesByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private List<ForumPartyRole> getForumPartyRolesByParty(Party party, EntityPermission entityPermission) {
        List<ForumPartyRole> forumPartyRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles, forums, forumdetails, forumroletypes " +
                        "WHERE frmparr_par_partyid = ? AND frmparr_thrutime = ? " +
                        "AND frmparr_frm_forumid = frm_forumid AND frm_lastdetailid = frmdt_forumdetailid " +
                        "AND frmparr_frmrtyp_forumroletypeid = frmrtyp_forumroletypeid " +
                        "ORDER BY frmdt_sortorder, frmdt_forumname, frmrtyp_sortorder, frmrtyp_forumroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles " +
                        "WHERE frmparr_par_partyid = ? AND frmparr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumPartyRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumPartyRoles = ForumPartyRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumPartyRoles;
    }
    
    public List<ForumPartyRole> getForumPartyRolesByParty(Party party) {
        return getForumPartyRolesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<ForumPartyRole> getForumPartyRolesByPartyForUpdate(Party party) {
        return getForumPartyRolesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private ForumPartyRole getForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType, EntityPermission entityPermission) {
        ForumPartyRole forumPartyRole = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles " +
                        "WHERE frmparr_frm_forumid = ? AND frmparr_par_partyid = ? AND frmparr_frmrtyp_forumroletypeid = ? " +
                        "AND frmparr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles " +
                        "WHERE frmparr_frm_forumid = ? AND frmparr_par_partyid = ? AND frmparr_frmrtyp_forumroletypeid = ? " +
                        "AND frmparr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumPartyRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, party.getPrimaryKey().getEntityId());
            ps.setLong(3, forumRoleType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            forumPartyRole = ForumPartyRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumPartyRole;
    }
    
    public ForumPartyRole getForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType) {
        return getForumPartyRole(forum, party, forumRoleType, EntityPermission.READ_ONLY);
    }
    
    public ForumPartyRole getForumPartyRoleForUpdate(Forum forum, Party party, ForumRoleType forumRoleType) {
        return getForumPartyRole(forum, party, forumRoleType, EntityPermission.READ_WRITE);
    }
    
    public List<ForumPartyRoleTransfer> getForumPartyRoleTransfersByForum(UserVisit userVisit, Forum forum) {
        List<ForumPartyRole> forumPartyRoles = getForumPartyRolesByForum(forum);
        List<ForumPartyRoleTransfer> forumPartyRoleTransfers = null;
        
        if(forumPartyRoles != null) {
            ForumPartyRoleTransferCache forumPartyRoleTransferCache = getForumTransferCaches(userVisit).getForumPartyRoleTransferCache();
            
            forumPartyRoleTransfers = new ArrayList<>(forumPartyRoles.size());
            
            for(ForumPartyRole forumPartyRole: forumPartyRoles) {
                forumPartyRoleTransfers.add(forumPartyRoleTransferCache.getForumPartyRoleTransfer(forumPartyRole));
            }
        }
        
        return forumPartyRoleTransfers;
    }
    
    public ForumPartyRoleTransfer getForumPartyRoleTransfer(UserVisit userVisit, ForumPartyRole forumPartyRole) {
        return getForumTransferCaches(userVisit).getForumPartyRoleTransferCache().getForumPartyRoleTransfer(forumPartyRole);
    }
    
    public void deleteForumPartyRole(ForumPartyRole forumPartyRole, BasePK deletedBy) {
        forumPartyRole.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(forumPartyRole.getForumPK(), EventTypes.MODIFY.name(), forumPartyRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumPartyRolesByForum(Forum forum, BasePK deletedBy) {
        List<ForumPartyRole> forumPartyRoles = getForumPartyRolesByForumForUpdate(forum);
        
        forumPartyRoles.stream().forEach((forumPartyRole) -> {
            deleteForumPartyRole(forumPartyRole, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Type Roles
    // --------------------------------------------------------------------------------
    
    public ForumPartyTypeRole createForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType,
            BasePK createdBy) {
        ForumPartyTypeRole forumPartyTypeRole = ForumPartyTypeRoleFactory.getInstance().create(forum, partyType,
                forumRoleType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forum.getPrimaryKey(), EventTypes.MODIFY.name(), forumPartyTypeRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumPartyTypeRole;
    }
    
    public boolean hasForumPartyTypeRoles(Forum forum, ForumRoleType forumRoleType) {
        return !(session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM forumpartytyperoles " +
                "WHERE frmptypr_frm_forumid = ? AND frmptypr_frmrtyp_forumroletypeid = ? AND frmptypr_thrutime = ?",
                forum, forumRoleType, Session.MAX_TIME) == 0);
    }

    public boolean hasForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType) {
        return !(session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM forumpartytyperoles " +
                "WHERE frmptypr_frm_forumid = ? AND frmptypr_ptyp_partytypeid = ? AND frmptypr_frmrtyp_forumroletypeid = ? AND frmptypr_thrutime = ?",
                forum, partyType, forumRoleType, Session.MAX_TIME) == 0);
    }

    private List<ForumPartyTypeRole> getForumPartyTypeRolesByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumPartyTypeRole> forumPartyTypeRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartytyperoles, partytypes, forumroletypes " +
                        "WHERE frmptypr_frm_forumid = ? AND frmptypr_thrutime = ? " +
                        "AND frmptypr_ptyp_partytypeid = ptyp_partytypeid " +
                        "AND frmptypr_frmrtyp_forumroletypeid = frmrtyp_forumroletypeid " +
                        "ORDER BY ptyp_sortorder, ptyp_partytypename, frmrtyp_sortorder, frmrtyp_forumroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartytyperoles " +
                        "WHERE frmptypr_frm_forumid = ? AND frmptypr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumPartyTypeRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumPartyTypeRoles = ForumPartyTypeRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumPartyTypeRoles;
    }
    
    public List<ForumPartyTypeRole> getForumPartyTypeRolesByForum(Forum forum) {
        return getForumPartyTypeRolesByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumPartyTypeRole> getForumPartyTypeRolesByForumForUpdate(Forum forum) {
        return getForumPartyTypeRolesByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private ForumPartyTypeRole getForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType, EntityPermission entityPermission) {
        ForumPartyTypeRole forumPartyTypeRole = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartytyperoles " +
                        "WHERE frmptypr_frm_forumid = ? AND frmptypr_ptyp_partytypeid = ? AND frmptypr_frmrtyp_forumroletypeid = ? " +
                        "AND frmptypr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartytyperoles " +
                        "WHERE frmptypr_frm_forumid = ? AND frmptypr_ptyp_partytypeid = ? AND frmptypr_frmrtyp_forumroletypeid = ? " +
                        "AND frmptypr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumPartyTypeRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, partyType.getPrimaryKey().getEntityId());
            ps.setLong(3, forumRoleType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            forumPartyTypeRole = ForumPartyTypeRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumPartyTypeRole;
    }
    
    public ForumPartyTypeRole getForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType) {
        return getForumPartyTypeRole(forum, partyType, forumRoleType, EntityPermission.READ_ONLY);
    }
    
    public ForumPartyTypeRole getForumPartyTypeRoleForUpdate(Forum forum, PartyType partyType, ForumRoleType forumRoleType) {
        return getForumPartyTypeRole(forum, partyType, forumRoleType, EntityPermission.READ_WRITE);
    }
    
    public List<ForumPartyTypeRoleTransfer> getForumPartyTypeRoleTransfersByForum(UserVisit userVisit, Forum forum) {
        List<ForumPartyTypeRole> forumPartyTypeRoles = getForumPartyTypeRolesByForum(forum);
        List<ForumPartyTypeRoleTransfer> forumPartyTypeRoleTransfers = null;
        
        if(forumPartyTypeRoles != null) {
            ForumPartyTypeRoleTransferCache forumPartyTypeRoleTransferCache = getForumTransferCaches(userVisit).getForumPartyTypeRoleTransferCache();
            
            forumPartyTypeRoleTransfers = new ArrayList<>(forumPartyTypeRoles.size());
            
            for(ForumPartyTypeRole forumPartyTypeRole: forumPartyTypeRoles) {
                forumPartyTypeRoleTransfers.add(forumPartyTypeRoleTransferCache.getForumPartyTypeRoleTransfer(forumPartyTypeRole));
            }
        }
        
        return forumPartyTypeRoleTransfers;
    }
    
    public ForumPartyTypeRoleTransfer getForumPartyTypeRoleTransfer(UserVisit userVisit, ForumPartyTypeRole forumPartyTypeRole) {
        return getForumTransferCaches(userVisit).getForumPartyTypeRoleTransferCache().getForumPartyTypeRoleTransfer(forumPartyTypeRole);
    }
    
    public void deleteForumPartyTypeRole(ForumPartyTypeRole forumPartyTypeRole, BasePK deletedBy) {
        forumPartyTypeRole.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(forumPartyTypeRole.getForumPK(), EventTypes.MODIFY.name(), forumPartyTypeRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumPartyTypeRolesByForum(Forum forum, BasePK deletedBy) {
        List<ForumPartyTypeRole> forumPartyTypeRoles = getForumPartyTypeRolesByForumForUpdate(forum);
        
        forumPartyTypeRoles.stream().forEach((forumPartyTypeRole) -> {
            deleteForumPartyTypeRole(forumPartyTypeRole, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Message Types
    // --------------------------------------------------------------------------------
    
    public ForumTypeMessageType createForumTypeMessageType(ForumType forumType, ForumMessageType forumMessageType,
            Boolean isDefault, Integer sortOrder) {
        return ForumTypeMessageTypeFactory.getInstance().create(forumType, forumMessageType, isDefault, sortOrder);
    }
    
    private List<ForumTypeMessageType> getForumTypeMessageTypesByForumType(ForumType forumType, EntityPermission entityPermission) {
        List<ForumTypeMessageType> forumTypeMessageTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes, forummessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? " +
                        "AND frmtypmsgtyp_frmmsgtyp_forummessagetypeid = frmmsgtyp_forummessagetypeid " +
                        "ORDER BY frmtypmsgtyp_sortorder, frmmsgtyp_sortorder, frmmsgtyp_forummessagetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumTypeMessageTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumType.getPrimaryKey().getEntityId());
            
            forumTypeMessageTypes = ForumTypeMessageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumTypeMessageTypes;
    }
    
    public List<ForumTypeMessageType> getForumTypeMessageTypesByForumType(ForumType forumType) {
        return getForumTypeMessageTypesByForumType(forumType, EntityPermission.READ_ONLY);
    }
    
    public List<ForumTypeMessageType> getForumTypeMessageTypesByForumTypeForUpdate(ForumType forumType) {
        return getForumTypeMessageTypesByForumType(forumType, EntityPermission.READ_WRITE);
    }
    
    private ForumTypeMessageType getForumTypeMessageType(ForumType forumType, ForumMessageType forumMessageType, EntityPermission entityPermission) {
        ForumTypeMessageType forumTypeMessageType = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? AND frmtypmsgtyp_frmmsgtyp_forummessagetypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? AND frmtypmsgtyp_frmmsgtyp_forummessagetypeid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ForumTypeMessageTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, forumType.getPrimaryKey().getEntityId());
            ps.setLong(2, forumMessageType.getPrimaryKey().getEntityId());

            forumTypeMessageType = ForumTypeMessageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return forumTypeMessageType;
    }

    public ForumTypeMessageType getForumTypeMessageType(ForumType forumType, ForumMessageType forumMessageType) {
        return getForumTypeMessageType(forumType, forumMessageType, EntityPermission.READ_ONLY);
    }

    public ForumTypeMessageType getForumTypeMessageTypeForUpdate(ForumType forumType, ForumMessageType forumMessageType) {
        return getForumTypeMessageType(forumType, forumMessageType, EntityPermission.READ_WRITE);
    }

    private ForumTypeMessageType getDefaultForumTypeMessageType(ForumType forumType, EntityPermission entityPermission) {
        ForumTypeMessageType forumTypeMessageType = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? AND frmtypmsgtyp_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? AND frmtypmsgtyp_isdefault = 1 " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ForumTypeMessageTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, forumType.getPrimaryKey().getEntityId());

            forumTypeMessageType = ForumTypeMessageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return forumTypeMessageType;
    }

    public ForumTypeMessageType getDefaultForumTypeMessageType(ForumType forumType) {
        return getDefaultForumTypeMessageType(forumType, EntityPermission.READ_ONLY);
    }

    public ForumTypeMessageType getDefaultForumTypeMessageTypeForUpdate(ForumType forumType) {
        return getDefaultForumTypeMessageType(forumType, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Forum Forum Threads
    // --------------------------------------------------------------------------------
    
    public ForumForumThread createForumForumThread(Forum forum, ForumThread forumThread, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        ForumForumThread defaultForumForumThread = getDefaultForumForumThread(forumThread);
        boolean defaultFound = defaultForumForumThread != null;
        
        if(defaultFound && isDefault) {
            ForumForumThreadValue defaultForumForumThreadValue = getDefaultForumForumThreadValueForUpdate(forumThread);
            
            defaultForumForumThreadValue.setIsDefault(Boolean.FALSE);
            updateForumForumThreadFromValue(defaultForumForumThreadValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ForumForumThread forumForumThread = ForumForumThreadFactory.getInstance().create(forum, forumThread,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forum.getPrimaryKey(), EventTypes.MODIFY.name(), forumForumThread.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumForumThread;
    }
    
    private ForumForumThread getForumForumThread(Forum forum, ForumThread forumThread, EntityPermission entityPermission) {
        ForumForumThread forumForumThread = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, forumThread.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            forumForumThread = ForumForumThreadFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumForumThread;
    }
    
    public ForumForumThread getForumForumThread(Forum forum, ForumThread forumThread) {
        return getForumForumThread(forum, forumThread, EntityPermission.READ_ONLY);
    }
    
    public ForumForumThread getForumForumThreadForUpdate(Forum forum, ForumThread forumThread) {
        return getForumForumThread(forum, forumThread, EntityPermission.READ_WRITE);
    }
    
    public ForumForumThreadValue getForumForumThreadValue(ForumForumThread forumForumThread) {
        return forumForumThread == null? null: forumForumThread.getForumForumThreadValue().clone();
    }
    
    public ForumForumThreadValue getForumForumThreadValueForUpdate(Forum forum, ForumThread forumThread) {
        return getForumForumThreadValue(getForumForumThreadForUpdate(forum, forumThread));
    }
    
    private ForumForumThread getDefaultForumForumThread(ForumThread forumThread, EntityPermission entityPermission) {
        ForumForumThread forumForumThread = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_isdefault = 1 AND frmfrmthrd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_isdefault = 1 AND frmfrmthrd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumThread.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumForumThread = ForumForumThreadFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumForumThread;
    }
    
    public ForumForumThread getDefaultForumForumThread(ForumThread forumThread) {
        return getDefaultForumForumThread(forumThread, EntityPermission.READ_ONLY);
    }
    
    public ForumForumThread getDefaultForumForumThreadForUpdate(ForumThread forumThread) {
        return getDefaultForumForumThread(forumThread, EntityPermission.READ_WRITE);
    }
    
    public ForumForumThreadValue getDefaultForumForumThreadValueForUpdate(ForumThread forumThread) {
        ForumForumThread forumForumThread = getDefaultForumForumThreadForUpdate(forumThread);
        
        return forumForumThread == null? null: forumForumThread.getForumForumThreadValue().clone();
    }
    
    private List<ForumForumThread> getForumForumThreadsByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumForumThread> forumForumThreads = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads, forumthreads, forumthreaddetails " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ? " +
                        "AND frmfrmthrd_frmthrd_forumthreadid = frmthrd_forumthreadid AND frmthrd_lastdetailid = frmthrddt_forumthreaddetailid " +
                        "ORDER BY frmthrddt_sortorder, frmthrddt_postedtime";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumForumThreads = ForumForumThreadFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumForumThreads;
    }
    
    public List<ForumForumThread> getForumForumThreadsByForum(Forum forum) {
        return getForumForumThreadsByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumForumThread> getForumForumThreadsByForumForUpdate(Forum forum) {
        return getForumForumThreadsByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private List<ForumForumThread> getForumForumThreadsByForumThread(ForumThread forumThread, EntityPermission entityPermission) {
        List<ForumForumThread> forumForumThreads = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads, forums, forumdetails " +
                        "WHERE frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_thrutime = ? " +
                        "AND frmfrmthrd_frm_forumid = frm_forumid AND frm_lastdetailid = frmdt_forumdetailid " +
                        "ORDER BY frmdt_sortorder, frmdt_forumname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumThread.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumForumThreads = ForumForumThreadFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumForumThreads;
    }
    
    public List<ForumForumThread> getForumForumThreadsByForumThread(ForumThread forumThread) {
        return getForumForumThreadsByForumThread(forumThread, EntityPermission.READ_ONLY);
    }
    
    public List<ForumForumThread> getForumForumThreadsByForumThreadForUpdate(ForumThread forumThread) {
        return getForumForumThreadsByForumThread(forumThread, EntityPermission.READ_WRITE);
    }
    
    public List<ForumForumThreadTransfer> getForumForumThreadTransfers(UserVisit userVisit, List<ForumForumThread> forumForumThreads) {
        List<ForumForumThreadTransfer> forumForumThreadTransfers = new ArrayList<>(forumForumThreads.size());
        ForumForumThreadTransferCache forumForumThreadTransferCache = getForumTransferCaches(userVisit).getForumForumThreadTransferCache();
        
        forumForumThreads.stream().forEach((forumForumThread) -> {
            forumForumThreadTransfers.add(forumForumThreadTransferCache.getForumForumThreadTransfer(forumForumThread));
        });
        
        return forumForumThreadTransfers;
    }
    
    public List<ForumForumThreadTransfer> getForumForumThreadTransfersByForum(UserVisit userVisit, Forum forum) {
        return getForumForumThreadTransfers(userVisit, getForumForumThreadsByForum(forum));
    }
    
    public List<ForumForumThreadTransfer> getForumForumThreadTransfersByForumThread(UserVisit userVisit, ForumThread forumThread) {
        return getForumForumThreadTransfers(userVisit, getForumForumThreadsByForumThread(forumThread));
    }
    
    public ForumForumThreadTransfer getForumForumThreadTransfer(UserVisit userVisit, ForumForumThread forumForumThread) {
        return getForumTransferCaches(userVisit).getForumForumThreadTransferCache().getForumForumThreadTransfer(forumForumThread);
    }
    
    private void updateForumForumThreadFromValue(ForumForumThreadValue forumForumThreadValue, boolean checkDefault, BasePK updatedBy) {
        if(forumForumThreadValue.hasBeenModified()) {
            ForumForumThread forumForumThread = ForumForumThreadFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumForumThreadValue.getPrimaryKey());
            
            forumForumThread.setThruTime(session.START_TIME_LONG);
            forumForumThread.store();
            
            ForumPK forumPK = forumForumThread.getForumPK(); // Not Updated
            ForumThread forumThread = forumForumThread.getForumThread(); // Not Updated
            Boolean isDefault = forumForumThreadValue.getIsDefault();
            Integer sortOrder = forumForumThreadValue.getSortOrder();
            
            if(checkDefault) {
                ForumForumThread defaultForumForumThread = getDefaultForumForumThread(forumThread);
                boolean defaultFound = defaultForumForumThread != null && !defaultForumForumThread.equals(forumForumThread);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ForumForumThreadValue defaultForumForumThreadValue = getDefaultForumForumThreadValueForUpdate(forumThread);
                    
                    defaultForumForumThreadValue.setIsDefault(Boolean.FALSE);
                    updateForumForumThreadFromValue(defaultForumForumThreadValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            forumForumThread = ForumForumThreadFactory.getInstance().create(forumPK, forumThread.getPrimaryKey(), isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(forumPK, EventTypes.MODIFY.name(), forumForumThread.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateForumForumThreadFromValue(ForumForumThreadValue forumForumThreadValue, BasePK updatedBy) {
        updateForumForumThreadFromValue(forumForumThreadValue, true, updatedBy);
    }
    
    private void deleteForumForumThread(ForumForumThread forumForumThread, boolean checkForumThread, BasePK deletedBy) {
        forumForumThread.setThruTime(session.START_TIME_LONG);
        forumForumThread.store();
        
        if(checkForumThread) {
            // Check for default, and pick one if necessary
            ForumThread forumThread = forumForumThread.getForumThreadForUpdate();
            ForumForumThread defaultForumForumThread = getDefaultForumForumThread(forumThread);
            if(defaultForumForumThread == null) {
                List<ForumForumThread> forumForumThreads = getForumForumThreadsByForumThreadForUpdate(forumThread);
                
                if(!forumForumThreads.isEmpty()) {
                    Iterator<ForumForumThread> iter = forumForumThreads.iterator();
                    if(iter.hasNext()) {
                        defaultForumForumThread = iter.next();
                    }
                    ForumForumThreadValue forumForumThreadValue = defaultForumForumThread.getForumForumThreadValue().clone();
                    
                    forumForumThreadValue.setIsDefault(Boolean.TRUE);
                    updateForumForumThreadFromValue(forumForumThreadValue, false, deletedBy);
                } else {
                    deleteForumThread(forumThread, deletedBy);
                }
            }
        }
        
        sendEventUsingNames(forumForumThread.getForumPK(), EventTypes.MODIFY.name(), forumForumThread.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumForumThread(ForumForumThread forumForumThread, BasePK deletedBy) {
        deleteForumForumThread(forumForumThread, true, deletedBy);
    }
    
    private void deleteForumForumThreads(List<ForumForumThread> forumForumThreads, boolean checkForumThread, BasePK deletedBy) {
        forumForumThreads.stream().forEach((forumForumThread) -> {
            deleteForumForumThread(forumForumThread, checkForumThread, deletedBy);
        });
    }
    
    public void deleteForumForumThreads(List<ForumForumThread> forumForumThreads, BasePK deletedBy) {
        deleteForumForumThreads(forumForumThreads, true, deletedBy);
    }
    
    public void deleteForumForumThreadsByForum(Forum forum, BasePK deletedBy) {
        deleteForumForumThreads(getForumForumThreadsByForumForUpdate(forum), deletedBy);
    }
    
    public void deleteForumForumThreadsByForumThread(ForumThread forumThread, BasePK deletedBy) {
        deleteForumForumThreads(getForumForumThreadsByForumThreadForUpdate(forumThread), false, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Threads
    // --------------------------------------------------------------------------------
    
    public ForumThread createForumThread(Forum forum, Icon icon, Long postedTime, Integer sortOrder, BasePK createdBy) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        Sequence sequence = forum == null? null: forum.getLastDetail().getForumThreadSequence();
        
        if(sequence == null) {
            sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.FORUM_THREAD.name());
        }
        
        return createForumThread(SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence), icon, postedTime, sortOrder, createdBy);
    }
    
    public ForumThread createForumThread(String forumThreadName, Icon icon, Long postedTime, Integer sortOrder, BasePK createdBy) {
        ForumThread forumThread = ForumThreadFactory.getInstance().create();
        ForumThreadDetail forumThreadDetail = ForumThreadDetailFactory.getInstance().create(forumThread, forumThreadName,
                icon, postedTime, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        forumThread = ForumThreadFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                forumThread.getPrimaryKey());
        forumThread.setActiveDetail(forumThreadDetail);
        forumThread.setLastDetail(forumThreadDetail);
        forumThread.store();
        
        sendEventUsingNames(forumThread.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return forumThread;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.ForumThread */
    public ForumThread getForumThreadByEntityInstance(EntityInstance entityInstance) {
        ForumThreadPK pk = new ForumThreadPK(entityInstance.getEntityUniqueId());
        ForumThread forumThread = ForumThreadFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return forumThread;
    }

    private ForumThread getForumThreadByName(String forumThreadName, EntityPermission entityPermission) {
        ForumThread forumThread = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumthreads, forumthreaddetails " +
                        "WHERE frmthrd_activedetailid = frmthrddt_forumthreaddetailid AND frmthrddt_forumthreadname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumthreads, forumthreaddetails " +
                        "WHERE frmthrd_activedetailid = frmthrddt_forumthreaddetailid AND frmthrddt_forumthreadname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, forumThreadName);
            
            forumThread = ForumThreadFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumThread;
    }
    
    public ForumThread getForumThreadByName(String forumThreadName) {
        return getForumThreadByName(forumThreadName, EntityPermission.READ_ONLY);
    }
    
    public ForumThread getForumThreadByNameForUpdate(String forumThreadName) {
        return getForumThreadByName(forumThreadName, EntityPermission.READ_WRITE);
    }
    
    public ForumThreadDetailValue getForumThreadDetailValueForUpdate(ForumThread forumThread) {
        return forumThread == null? null: forumThread.getLastDetailForUpdate().getForumThreadDetailValue().clone();
    }
    
    public ForumThreadDetailValue getForumThreadDetailValueByNameForUpdate(String forumThreadName) {
        return getForumThreadDetailValueForUpdate(getForumThreadByNameForUpdate(forumThreadName));
    }
    
    public long countForumThreadsByForum(Forum forum, boolean includeFutureForumThreads) {
        long count = 0;
        
        if (includeFutureForumThreads) {
            count = session.queryForLong(
                    "SELECT COUNT(*) "
                    + "FROM forumforumthreads "
                    + "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ?",
                    forum, Session.MAX_TIME);
        } else {
            count = session.queryForLong(
                    "SELECT COUNT(*) "
                    + "FROM forumforumthreads, forumthreads, forumthreaddetails "
                    + "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ? "
                    + "AND frmfrmthrd_frmthrd_forumthreadid = frmthrd_forumthreadid "
                    + "AND frmthrd_activedetailid = frmthrddt_forumthreaddetailid "
                    + "AND frmthrddt_postedtime <= ?",
                    forum, Session.MAX_TIME, session.START_TIME);
        }

        return count;
    }
    
    private List<ForumThread> getForumThreadsByForum(Forum forum, boolean includeFutureForumThreads, EntityPermission entityPermission) {
        List<ForumThread> forumThreads = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads, forumthreads, forumthreaddetails, componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, entitytimes " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ? " +
                        "AND frmfrmthrd_frmthrd_forumthreadid = frmthrd_forumthreadid " +
                        "AND frmthrd_activedetailid = frmthrddt_forumthreaddetailid " +
                        "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                        "AND ent_activedetailid = entdt_entitytypedetailid AND cvnd_componentvendorid = entdt_cvnd_componentvendorid AND entdt_entitytypename = ? " +
                        "AND ent_entitytypeid = eni_ent_entitytypeid AND frmthrd_forumthreadid = eni_entityuniqueid " +
                        "AND eni_entityinstanceid = etim_eni_entityinstanceid " +
                        (includeFutureForumThreads? "": "AND frmthrddt_postedtime <= ? ") +
                        "ORDER BY frmthrddt_sortorder, frmthrddt_postedtime DESC, etim_createdtime DESC" +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads, forumthreads, forumthreaddetails " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ? " +
                        "AND frmfrmthrd_frmthrd_forumthreadid = frmthrd_forumthreadid " +
                        "AND frmthrd_activedetailid = frmthrddt_forumthreaddetailid " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setString(3, ComponentVendors.ECHOTHREE.name());
            ps.setString(4, EntityTypes.ForumThread.name());
            if(!includeFutureForumThreads) {
                ps.setLong(5, session.START_TIME);
            }
            
            forumThreads = ForumThreadFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumThreads;
    }
    
    public List<ForumThread> getForumThreadsByForum(Forum forum, boolean includeFutureForumThreads) {
        return getForumThreadsByForum(forum, includeFutureForumThreads, EntityPermission.READ_ONLY);
    }
    
    public List<ForumThread> getForumThreadsByForumForUpdate(Forum forum, boolean includeFutureForumThreads) {
        return getForumThreadsByForum(forum, includeFutureForumThreads, EntityPermission.READ_WRITE);
    }
    
    public ForumThreadTransfer getForumThreadTransfer(UserVisit userVisit, ForumThread forumThread) {
        return getForumTransferCaches(userVisit).getForumThreadTransferCache().getForumThreadTransfer(forumThread);
    }
    
    public List<ForumThreadTransfer> getForumThreadTransfers(UserVisit userVisit, List<ForumThread> forumThreads) {
        List<ForumThreadTransfer> forumThreadTransfers = new ArrayList<>(forumThreads.size());
        ForumThreadTransferCache forumThreadTransferCache = getForumTransferCaches(userVisit).getForumThreadTransferCache();
        
        forumThreads.stream().forEach((forumThread) -> {
            forumThreadTransfers.add(forumThreadTransferCache.getForumThreadTransfer(forumThread));
        });
        
        return forumThreadTransfers;
    }
    
    public List<ForumThreadTransfer> getForumThreadTransfersByForum(UserVisit userVisit, Forum forum, boolean includeFutureForumThreads) {
        return getForumThreadTransfers(userVisit, getForumThreadsByForum(forum, includeFutureForumThreads));
    }
    
    public void updateForumThreadFromValue(ForumThreadDetailValue forumThreadDetailValue, BasePK updatedBy) {
        if(forumThreadDetailValue.hasBeenModified()) {
            ForumThread forumThread = ForumThreadFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumThreadDetailValue.getForumThreadPK());
            ForumThreadDetail forumThreadDetail = forumThread.getActiveDetailForUpdate();
            
            forumThreadDetail.setThruTime(session.START_TIME_LONG);
            forumThreadDetail.store();
            
            ForumThreadPK forumThreadPK = forumThreadDetail.getForumThreadPK();
            String forumThreadName = forumThreadDetail.getForumThreadName(); // Not updated
            IconPK iconPK = forumThreadDetailValue.getIconPK();
            Long postedTime = forumThreadDetailValue.getPostedTime();
            Integer sortOrder = forumThreadDetailValue.getSortOrder();
            
            forumThreadDetail = ForumThreadDetailFactory.getInstance().create(forumThreadPK, forumThreadName,
                    iconPK, postedTime, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            forumThread.setActiveDetail(forumThreadDetail);
            forumThread.setLastDetail(forumThreadDetail);
            
            sendEventUsingNames(forumThreadPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteForumThread(ForumThread forumThread, BasePK deletedBy) {
        deleteForumMessagesByForumThread(forumThread, deletedBy);
        deleteForumForumThreadsByForumThread(forumThread, deletedBy);
        
        ForumThreadDetail forumThreadDetail = forumThread.getLastDetailForUpdate();
        forumThreadDetail.setThruTime(session.START_TIME_LONG);
        forumThread.setActiveDetail(null);
        forumThread.store();
        
        sendEventUsingNames(forumThread.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteForumThreads(List<ForumThread> forumThreads, BasePK deletedBy) {
        forumThreads.stream().forEach((forumThread) -> {
            deleteForumThread(forumThread, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Messages
    // --------------------------------------------------------------------------------
    
    public ForumMessage createForumMessage(ForumThread forumThread, ForumMessageType forumMessageType,
            ForumMessage parentForumMessage, Icon icon, Long postedTime, BasePK createdBy) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        ForumForumThread forumForumThread = getDefaultForumForumThread(forumThread);
        Forum forum = forumForumThread == null? null: forumForumThread.getForum();
        Sequence sequence = forum == null? null: forum.getLastDetail().getForumThreadSequence();
        
        if(sequence == null) {
            sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.FORUM_MESSAGE.name());
        }
        
        return createForumMessage(SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence), forumThread, forumMessageType, parentForumMessage,
                icon, postedTime, createdBy);
    }
    
    public ForumMessage createForumMessage(String forumMessageName, ForumThread forumThread, ForumMessageType forumMessageType,
            ForumMessage parentForumMessage, Icon icon, Long postedTime, BasePK createdBy) {
        ForumMessage forumMessage = ForumMessageFactory.getInstance().create();
        ForumMessageDetail forumMessageDetail = ForumMessageDetailFactory.getInstance().create(forumMessage,
                forumMessageName, forumThread, forumMessageType, parentForumMessage, icon, postedTime, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        forumMessage = ForumMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                forumMessage.getPrimaryKey());
        forumMessage.setActiveDetail(forumMessageDetail);
        forumMessage.setLastDetail(forumMessageDetail);
        forumMessage.store();
        
        ForumMessagePK forumMessagePK = forumMessage.getPrimaryKey();
        sendEventUsingNames(forumMessagePK, EventTypes.CREATE.name(), null, null, createdBy);
        if(parentForumMessage != null) {
            sendEventUsingNames(forumThread.getPrimaryKey(), EventTypes.TOUCH.name(), forumMessagePK, EventTypes.CREATE.name(), createdBy);
            touchForumsByForumThread(forumThread, forumMessagePK, EventTypes.CREATE.name(), createdBy);
        }
        
        return forumMessage;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.ForumMessage */
    public ForumMessage getForumMessageByEntityInstance(EntityInstance entityInstance) {
        ForumMessagePK pk = new ForumMessagePK(entityInstance.getEntityUniqueId());
        ForumMessage forumMessage = ForumMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return forumMessage;
    }

    public long countForumMessages() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM forummessages, forummessagedetails " +
                "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid");
    }

    private ForumMessage getForumMessageByName(String forumMessageName, EntityPermission entityPermission) {
        ForumMessage forumMessage = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessages, forummessagedetails " +
                        "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_forummessagename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessages, forummessagedetails " +
                        "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_forummessagename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumMessageFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, forumMessageName);
            
            forumMessage = ForumMessageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessage;
    }
    
    public ForumMessage getForumMessageByName(String forumMessageName) {
        return getForumMessageByName(forumMessageName, EntityPermission.READ_ONLY);
    }
    
    public ForumMessage getForumMessageByNameForUpdate(String forumMessageName) {
        return getForumMessageByName(forumMessageName, EntityPermission.READ_WRITE);
    }
    
    public ForumMessageDetailValue getForumMessageDetailValueForUpdate(ForumMessage forumMessage) {
        return forumMessage == null? null: forumMessage.getLastDetailForUpdate().getForumMessageDetailValue().clone();
    }
    
    public ForumMessageDetailValue getForumMessageDetailValueByNameForUpdate(String forumMessageName) {
        return getForumMessageDetailValueForUpdate(getForumMessageByNameForUpdate(forumMessageName));
    }
    
    public long countForumMessagesByForumThread(ForumThread forumThread) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM forummessages, forummessagedetails " +
                "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_frmthrd_forumthreadid = ?",
                forumThread);
    }
    
    private List<ForumMessage> getForumMessagesByForumThread(ForumThread forumThread, EntityPermission entityPermission) {
        List<ForumMessage> forumMessages = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessages, forummessagedetails " +
                        "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_frmthrd_forumthreadid = ? " +
                        "ORDER BY frmmsgdt_postedtime, frmmsgdt_forummessagename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessages, forummessagedetails " +
                        "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_frmthrd_forumthreadid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumMessageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumThread.getPrimaryKey().getEntityId());
            
            forumMessages = ForumMessageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessages;
    }
    
    public List<ForumMessage> getForumMessagesByForumThread(ForumThread forumThread) {
        return getForumMessagesByForumThread(forumThread, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMessage> getForumMessagesByForumThreadForUpdate(ForumThread forumThread) {
        return getForumMessagesByForumThread(forumThread, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getForumMessagesByParentForumMessageQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM forummessages, forummessagedetails " +
                "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_parentforummessageid = ? " +
                "ORDER BY frmmsgdt_sortorder, frmmsgdt_forummessagename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM forummessages, forummessagedetails " +
                "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_parentforummessageid = ? " +
                "FOR UPDATE");
        getForumMessagesByParentForumMessageQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ForumMessage> getForumMessagesByParentForumMessage(ForumMessage parentForumMessage,
            EntityPermission entityPermission) {
        return ForumMessageFactory.getInstance().getEntitiesFromQuery(entityPermission, getForumMessagesByParentForumMessageQueries,
                parentForumMessage);
    }

    public List<ForumMessage> getForumMessagesByParentForumMessage(ForumMessage parentForumMessage) {
        return getForumMessagesByParentForumMessage(parentForumMessage, EntityPermission.READ_ONLY);
    }

    public List<ForumMessage> getForumMessagesByParentForumMessageForUpdate(ForumMessage parentForumMessage) {
        return getForumMessagesByParentForumMessage(parentForumMessage, EntityPermission.READ_WRITE);
    }

    public ForumMessageTransfer getForumMessageTransfer(UserVisit userVisit, ForumMessage forumMessage) {
        return getForumTransferCaches(userVisit).getForumMessageTransferCache().getForumMessageTransfer(forumMessage);
    }
    
    public List<ForumMessageTransfer> getForumMessageTransfers(UserVisit userVisit, List<ForumMessage> forumMessages) {
        List<ForumMessageTransfer> forumMessageTransfers = new ArrayList<>(forumMessages.size());
        ForumMessageTransferCache forumMessageTransferCache = getForumTransferCaches(userVisit).getForumMessageTransferCache();
        
        forumMessages.stream().forEach((forumMessage) -> {
            forumMessageTransfers.add(forumMessageTransferCache.getForumMessageTransfer(forumMessage));
        });
        
        return forumMessageTransfers;
    }
    
    public List<ForumMessageTransfer> getForumMessageTransfersByForumThread(UserVisit userVisit, ForumThread forumThread) {
        return getForumMessageTransfers(userVisit, getForumMessagesByForumThread(forumThread));
    }
    
    public void updateForumMessageFromValue(ForumMessageDetailValue forumMessageDetailValue, BasePK updatedBy) {
        if(forumMessageDetailValue.hasBeenModified()) {
            ForumMessage forumMessage = ForumMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessageDetailValue.getForumMessagePK());
            ForumMessageDetail forumMessageDetail = forumMessage.getActiveDetailForUpdate();
            
            forumMessageDetail.setThruTime(session.START_TIME_LONG);
            forumMessageDetail.store();
            
            ForumMessagePK forumMessagePK = forumMessageDetail.getForumMessagePK();
            String forumMessageName = forumMessageDetail.getForumMessageName(); // Not updated
            ForumThreadPK forumThreadPK = forumMessageDetail.getForumThreadPK(); // Not updated
            ForumMessageTypePK forumMessageTypePK = forumMessageDetail.getForumMessageTypePK(); // Not updated
            ForumMessagePK parentForumMessagePK = forumMessageDetail.getParentForumMessagePK(); // Not updated
            IconPK iconPK = forumMessageDetailValue.getIconPK();
            Long postedTime = forumMessageDetailValue.getPostedTime();
            
            forumMessageDetail = ForumMessageDetailFactory.getInstance().create(forumMessagePK, forumMessageName,
                    forumThreadPK, forumMessageTypePK, parentForumMessagePK, iconPK, postedTime, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            forumMessage.setActiveDetail(forumMessageDetail);
            forumMessage.setLastDetail(forumMessageDetail);
            
            sendEventUsingNames(forumMessagePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteForumMessage(ForumMessage forumMessage, BasePK deletedBy) {
        removeForumMessageStatusByForumMessage(forumMessage);
        deleteForumMessagesByParentForumMessage(forumMessage, deletedBy);
        deleteForumMessageRolesByForumMessage(forumMessage, deletedBy);
        deleteForumMessagePartsByForumMessage(forumMessage, deletedBy);
        deleteForumMessageAttachmentsByForumMessage(forumMessage, deletedBy);
        
        ForumMessageDetail forumMessageDetail = forumMessage.getLastDetailForUpdate();
        forumMessageDetail.setThruTime(session.START_TIME_LONG);
        forumMessage.setActiveDetail(null);
        forumMessage.store();
        
        sendEventUsingNames(forumMessage.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteForumMessages(List<ForumMessage> forumMessages, BasePK deletedBy) {
        forumMessages.stream().forEach((forumMessage) -> {
            deleteForumMessage(forumMessage, deletedBy);
        });
    }

    private void deleteForumMessagesByParentForumMessage(ForumMessage parentForumMessage, BasePK deletedBy) {
        deleteForumMessages(getForumMessagesByParentForumMessageForUpdate(parentForumMessage), deletedBy);
    }

    public void deleteForumMessagesByForumThread(ForumThread forumThread, BasePK deletedBy) {
        deleteForumMessages(getForumMessagesByForumThreadForUpdate(forumThread), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Statuses
    // --------------------------------------------------------------------------------

    public ForumMessageStatus createForumMessageStatus(ForumMessage forumMessage) {
        return ForumMessageStatusFactory.getInstance().create(forumMessage, 0);
    }

    private static final Map<EntityPermission, String> getForumMessageStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM forummessagestatuses " +
                "WHERE frmmsgst_frmmsg_forummessageid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM forummessagestatuses " +
                "WHERE frmmsgst_frmmsg_forummessageid = ? " +
                "FOR UPDATE");
        getForumMessageStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    private ForumMessageStatus getForumMessageStatus(ForumMessage forumMessage, EntityPermission entityPermission) {
        return ForumMessageStatusFactory.getInstance().getEntityFromQuery(entityPermission, getForumMessageStatusQueries,
                forumMessage);
    }

    public ForumMessageStatus getForumMessageStatus(ForumMessage forumMessage) {
        return getForumMessageStatus(forumMessage, EntityPermission.READ_ONLY);
    }

    public ForumMessageStatus getForumMessageStatusForUpdate(ForumMessage forumMessage) {
        return getForumMessageStatus(forumMessage, EntityPermission.READ_WRITE);
    }

    public ForumMessageStatus getOrCreateForumMessageStatusForUpdate(ForumMessage forumMessage) {
        ForumMessageStatus forumMessageStatus = getForumMessageStatusForUpdate(forumMessage);

        if(forumMessageStatus == null) {
            createForumMessageStatus(forumMessage);
            forumMessageStatus = getForumMessageStatusForUpdate(forumMessage);
        }

        return forumMessageStatus;
    }

    public void removeForumMessageStatusByForumMessage(ForumMessage forumMessage) {
        ForumMessageStatus forumMessageStatus = getForumMessageStatusForUpdate(forumMessage);

        if(forumMessageStatus != null) {
            forumMessageStatus.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachments
    // --------------------------------------------------------------------------------

    public ForumMessageAttachment createForumMessageAttachment(ForumMessage forumMessage, Integer forumMessageAttachmentSequence, MimeType mimeType,
            BasePK createdBy) {
        ForumMessageAttachment forumMessageAttachment = ForumMessageAttachmentFactory.getInstance().create();
        ForumMessageAttachmentDetail forumMessageAttachmentDetail = ForumMessageAttachmentDetailFactory.getInstance().create(forumMessageAttachment,
                forumMessage, forumMessageAttachmentSequence, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        forumMessageAttachment = ForumMessageAttachmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, forumMessageAttachment.getPrimaryKey());
        forumMessageAttachment.setActiveDetail(forumMessageAttachmentDetail);
        forumMessageAttachment.setLastDetail(forumMessageAttachmentDetail);
        forumMessageAttachment.store();

        sendEventUsingNames(forumMessage.getPrimaryKey(), EventTypes.MODIFY.name(), forumMessageAttachment.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return forumMessageAttachment;
    }

    private static final Map<EntityPermission, String> getForumMessageAttachmentBySequenceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageattachments, forummessageattachmentdetails "
                + "WHERE frmmsgatt_activedetailid = frmmsgattdt_forummessageattachmentdetailid AND frmmsgattdt_frmmsg_forummessageid = ? "
                + "AND frmmsgattdt_forummessageattachmentsequence = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageattachments, forummessageattachmentdetails "
                + "WHERE frmmsgatt_activedetailid = frmmsgattdt_forummessageattachmentdetailid AND frmmsgattdt_frmmsg_forummessageid = ? "
                + "AND frmmsgattdt_forummessageattachmentsequence = ? "
                + "FOR UPDATE");
        getForumMessageAttachmentBySequenceQueries = Collections.unmodifiableMap(queryMap);
    }

    private ForumMessageAttachment getForumMessageAttachmentBySequence(ForumMessage forumMessage, Integer forumMessageAttachmentSequence, EntityPermission entityPermission) {
        return ForumMessageAttachmentFactory.getInstance().getEntityFromQuery(entityPermission, getForumMessageAttachmentBySequenceQueries,
                forumMessage, forumMessageAttachmentSequence);
    }

    public ForumMessageAttachment getForumMessageAttachmentBySequence(ForumMessage forumMessage, Integer forumMessageAttachmentSequence) {
        return getForumMessageAttachmentBySequence(forumMessage, forumMessageAttachmentSequence, EntityPermission.READ_ONLY);
    }

    public ForumMessageAttachment getForumMessageAttachmentBySequenceForUpdate(ForumMessage forumMessage, Integer forumMessageAttachmentSequence) {
        return getForumMessageAttachmentBySequence(forumMessage, forumMessageAttachmentSequence, EntityPermission.READ_WRITE);
    }

    public ForumMessageAttachmentDetailValue getForumMessageAttachmentDetailValueForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return forumMessageAttachment == null ? null : forumMessageAttachment.getLastDetailForUpdate().getForumMessageAttachmentDetailValue().clone();
    }

    public ForumMessageAttachmentDetailValue getForumMessageAttachmentDetailValueBySequenceForUpdate(ForumMessage forumMessage, Integer forumMessageAttachmentSequence) {
        return getForumMessageAttachmentDetailValueForUpdate(getForumMessageAttachmentBySequenceForUpdate(forumMessage, forumMessageAttachmentSequence));
    }

    private static final Map<EntityPermission, String> getForumMessageAttachmentByForumMessageQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageattachments, forummessageattachmentdetails "
                + "WHERE frmmsgatt_activedetailid = frmmsgattdt_forummessageattachmentdetailid AND frmmsgattdt_frmmsg_forummessageid = ? "
                + "ORDER BY frmmsgattdt_forummessageattachmentsequence");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageattachments, forummessageattachmentdetails "
                + "WHERE frmmsgatt_activedetailid = frmmsgattdt_forummessageattachmentdetailid AND frmmsgattdt_frmmsg_forummessageid = ? "
                + "FOR UPDATE");
        getForumMessageAttachmentByForumMessageQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ForumMessageAttachment> getForumMessageAttachmentsByForumMessage(ForumMessage forumMessage, EntityPermission entityPermission) {
        return ForumMessageAttachmentFactory.getInstance().getEntitiesFromQuery(entityPermission, getForumMessageAttachmentByForumMessageQueries,
                forumMessage);
    }

    public List<ForumMessageAttachment> getForumMessageAttachmentsByForumMessage(ForumMessage forumMessage) {
        return getForumMessageAttachmentsByForumMessage(forumMessage, EntityPermission.READ_ONLY);
    }

    public List<ForumMessageAttachment> getForumMessageAttachmentsByForumMessageForUpdate(ForumMessage forumMessage) {
        return getForumMessageAttachmentsByForumMessage(forumMessage, EntityPermission.READ_WRITE);
    }

    public ForumMessageAttachmentTransfer getForumMessageAttachmentTransfer(UserVisit userVisit, ForumMessageAttachment forumMessageAttachment) {
        return getForumTransferCaches(userVisit).getForumMessageAttachmentTransferCache().getForumMessageAttachmentTransfer(forumMessageAttachment);
    }

    public List<ForumMessageAttachmentTransfer> getForumMessageAttachmentTransfers(UserVisit userVisit, List<ForumMessageAttachment> forumMessageAttachments) {
        List<ForumMessageAttachmentTransfer> forumMessageAttachmentTransfers = new ArrayList<>(forumMessageAttachments.size());
        ForumMessageAttachmentTransferCache forumMessageAttachmentTransferCache = getForumTransferCaches(userVisit).getForumMessageAttachmentTransferCache();

        forumMessageAttachments.stream().forEach((forumMessageAttachment) -> {
            forumMessageAttachmentTransfers.add(forumMessageAttachmentTransferCache.getForumMessageAttachmentTransfer(forumMessageAttachment));
        });

        return forumMessageAttachmentTransfers;
    }

    public List<ForumMessageAttachmentTransfer> getForumMessageAttachmentTransfersByForumMessage(UserVisit userVisit, ForumMessage forumMessage) {
        return getForumMessageAttachmentTransfers(userVisit, getForumMessageAttachmentsByForumMessage(forumMessage));
    }

    public void updateForumMessageAttachmentFromValue(ForumMessageAttachmentDetailValue forumMessageAttachmentDetailValue, BasePK updatedBy) {
        if(forumMessageAttachmentDetailValue.hasBeenModified()) {
            ForumMessageAttachment forumMessageAttachment = ForumMessageAttachmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumMessageAttachmentDetailValue.getForumMessageAttachmentPK());
            ForumMessageAttachmentDetail forumMessageAttachmentDetail = forumMessageAttachment.getActiveDetailForUpdate();

            forumMessageAttachmentDetail.setThruTime(session.START_TIME_LONG);
            forumMessageAttachmentDetail.store();

            ForumMessageAttachmentPK forumMessageAttachmentPK = forumMessageAttachmentDetail.getForumMessageAttachmentPK(); // Not updated
            ForumMessagePK forumMessagePK = forumMessageAttachmentDetail.getForumMessagePK(); // Not updated
            Integer forumMessageAttachmentSequence = forumMessageAttachmentDetail.getForumMessageAttachmentSequence(); // Not updated
            MimeTypePK mimeTypePK = forumMessageAttachmentDetailValue.getMimeTypePK();

            forumMessageAttachmentDetail = ForumMessageAttachmentDetailFactory.getInstance().create(forumMessageAttachmentPK, forumMessagePK, forumMessageAttachmentSequence,
                    mimeTypePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            forumMessageAttachment.setActiveDetail(forumMessageAttachmentDetail);
            forumMessageAttachment.setLastDetail(forumMessageAttachmentDetail);

            sendEventUsingNames(forumMessagePK, EventTypes.MODIFY.name(), forumMessageAttachmentPK, EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, BasePK deletedBy) {
        deleteForumMessageAttachmentDescriptionsByForumMessageAttachment(forumMessageAttachment, deletedBy);

        ForumMessageAttachmentDetail forumMessageAttachmentDetail = forumMessageAttachment.getLastDetailForUpdate();
        forumMessageAttachmentDetail.setThruTime(session.START_TIME_LONG);
        forumMessageAttachmentDetail.store();
        forumMessageAttachment.setActiveDetail(null);

        String entityAttributeTypeName = forumMessageAttachmentDetail.getMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            deleteForumMessageBlobAttachmentByForumMessageAttachment(forumMessageAttachment, deletedBy);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            deleteForumMessageClobAttachmentByForumMessageAttachment(forumMessageAttachment, deletedBy);
        }

        sendEventUsingNames(forumMessageAttachmentDetail.getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageAttachment.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteForumMessageAttachments(List<ForumMessageAttachment> forumMessageAttachments, BasePK deletedBy) {
        forumMessageAttachments.stream().forEach((forumMessageAttachment) -> {
            deleteForumMessageAttachment(forumMessageAttachment, deletedBy);
        });
    }

    public void deleteForumMessageAttachmentsByForumMessage(ForumMessage forumMessage, BasePK deletedBy) {
        deleteForumMessageAttachments(getForumMessageAttachmentsByForumMessageForUpdate(forumMessage), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   ForumMessageAttachment Utilities
    // --------------------------------------------------------------------------------

    private void verifyForumMessageAttachmentMimeType(ForumMessageAttachment forumMessageAttachment, String entityAttributeTypeName) {
        String forumMessageAttachmentEntityAttributeTypeName = forumMessageAttachment.getLastDetail().getMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(!forumMessageAttachmentEntityAttributeTypeName.equals(entityAttributeTypeName)) {
            throw new IllegalArgumentException("ForumMessageAttachment entityAttributeTypeName is " + forumMessageAttachmentEntityAttributeTypeName + ", expected " + entityAttributeTypeName);
        }
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Blobs
    // --------------------------------------------------------------------------------

    public ForumMessageBlobAttachment createForumMessageBlobAttachment(ForumMessageAttachment forumMessageAttachment, ByteArray blob, BasePK createdBy) {
        verifyForumMessageAttachmentMimeType(forumMessageAttachment, EntityAttributeTypes.BLOB.name());

        ForumMessageBlobAttachment forumMessageAttachmentBlob = ForumMessageBlobAttachmentFactory.getInstance().create(forumMessageAttachment, blob, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageAttachmentBlob.getPrimaryKey(), EventTypes.MODIFY.name(), createdBy);

        return forumMessageAttachmentBlob;
    }

    private static final Map<EntityPermission, String> getForumMessageBlobAttachmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageblobattachments "
                + "WHERE frmmsgbatt_frmmsgatt_forummessageattachmentid = ? AND frmmsgbatt_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageblobattachments "
                + "WHERE frmmsgbatt_frmmsgatt_forummessageattachmentid = ? AND frmmsgbatt_thrutime = ? "
                + "FOR UPDATE");
        getForumMessageBlobAttachmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private ForumMessageBlobAttachment getForumMessageBlobAttachment(ForumMessageAttachment forumMessageAttachment, EntityPermission entityPermission) {
        return ForumMessageBlobAttachmentFactory.getInstance().getEntityFromQuery(entityPermission, getForumMessageBlobAttachmentQueries,
                forumMessageAttachment, Session.MAX_TIME);
    }

    public ForumMessageBlobAttachment getForumMessageBlobAttachment(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageBlobAttachment(forumMessageAttachment, EntityPermission.READ_ONLY);
    }

    public ForumMessageBlobAttachment getForumMessageBlobAttachmentForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageBlobAttachment(forumMessageAttachment, EntityPermission.READ_WRITE);
    }

    public ForumMessageBlobAttachmentValue getForumMessageBlobAttachmentValue(ForumMessageBlobAttachment forumMessageAttachmentBlob) {
        return forumMessageAttachmentBlob == null ? null : forumMessageAttachmentBlob.getForumMessageBlobAttachmentValue().clone();
    }

    public ForumMessageBlobAttachmentValue getForumMessageBlobAttachmentValueForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageBlobAttachmentValue(getForumMessageBlobAttachmentForUpdate(forumMessageAttachment));
    }

    public void updateForumMessageBlobAttachmentFromValue(ForumMessageBlobAttachmentValue forumMessageAttachmentBlobValue, BasePK updatedBy) {
        if(forumMessageAttachmentBlobValue.hasBeenModified()) {
            ForumMessageBlobAttachment forumMessageAttachmentBlob = ForumMessageBlobAttachmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessageAttachmentBlobValue.getPrimaryKey());

            forumMessageAttachmentBlob.setThruTime(session.START_TIME_LONG);
            forumMessageAttachmentBlob.store();

            ForumMessageAttachmentPK forumMessageAttachmentPK = forumMessageAttachmentBlob.getForumMessageAttachmentPK(); // Not updated
            ByteArray blob = forumMessageAttachmentBlobValue.getBlob();

            forumMessageAttachmentBlob = ForumMessageBlobAttachmentFactory.getInstance().create(forumMessageAttachmentPK, blob, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEventUsingNames(forumMessageAttachmentBlob.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageAttachmentBlob.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteForumMessageBlobAttachment(ForumMessageBlobAttachment forumMessageAttachmentBlob, BasePK deletedBy) {
        forumMessageAttachmentBlob.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(forumMessageAttachmentBlob.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageAttachmentBlob.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteForumMessageBlobAttachmentByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, BasePK deletedBy) {
        ForumMessageBlobAttachment forumMessageAttachmentBlob = getForumMessageBlobAttachmentForUpdate(forumMessageAttachment);

        if(forumMessageAttachmentBlob != null) {
            deleteForumMessageBlobAttachment(forumMessageAttachmentBlob, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Clobs
    // --------------------------------------------------------------------------------

    public ForumMessageClobAttachment createForumMessageClobAttachment(ForumMessageAttachment forumMessageAttachment, String clob, BasePK createdBy) {
        verifyForumMessageAttachmentMimeType(forumMessageAttachment, EntityAttributeTypes.CLOB.name());

        ForumMessageClobAttachment forumMessageAttachmentClob = ForumMessageClobAttachmentFactory.getInstance().create(forumMessageAttachment, clob, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageAttachmentClob.getPrimaryKey(), EventTypes.MODIFY.name(), createdBy);

        return forumMessageAttachmentClob;
    }

    private static final Map<EntityPermission, String> getForumMessageClobAttachmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageclobattachments "
                + "WHERE frmmsgcatt_frmmsgatt_forummessageattachmentid = ? AND frmmsgcatt_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageclobattachments "
                + "WHERE frmmsgcatt_frmmsgatt_forummessageattachmentid = ? AND frmmsgcatt_thrutime = ? "
                + "FOR UPDATE");
        getForumMessageClobAttachmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private ForumMessageClobAttachment getForumMessageClobAttachment(ForumMessageAttachment forumMessageAttachment, EntityPermission entityPermission) {
        return ForumMessageClobAttachmentFactory.getInstance().getEntityFromQuery(entityPermission, getForumMessageClobAttachmentQueries,
                forumMessageAttachment, Session.MAX_TIME);
    }

    public ForumMessageClobAttachment getForumMessageClobAttachment(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageClobAttachment(forumMessageAttachment, EntityPermission.READ_ONLY);
    }

    public ForumMessageClobAttachment getForumMessageClobAttachmentForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageClobAttachment(forumMessageAttachment, EntityPermission.READ_WRITE);
    }

    public ForumMessageClobAttachmentValue getForumMessageClobAttachmentValue(ForumMessageClobAttachment forumMessageAttachmentClob) {
        return forumMessageAttachmentClob == null ? null : forumMessageAttachmentClob.getForumMessageClobAttachmentValue().clone();
    }

    public ForumMessageClobAttachmentValue getForumMessageClobAttachmentValueForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageClobAttachmentValue(getForumMessageClobAttachmentForUpdate(forumMessageAttachment));
    }

    public void updateForumMessageClobAttachmentFromValue(ForumMessageClobAttachmentValue forumMessageAttachmentClobValue, BasePK updatedBy) {
        if(forumMessageAttachmentClobValue.hasBeenModified()) {
            ForumMessageClobAttachment forumMessageAttachmentClob = ForumMessageClobAttachmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessageAttachmentClobValue.getPrimaryKey());

            forumMessageAttachmentClob.setThruTime(session.START_TIME_LONG);
            forumMessageAttachmentClob.store();

            ForumMessageAttachmentPK forumMessageAttachmentPK = forumMessageAttachmentClob.getForumMessageAttachmentPK(); // Not updated
            String clob = forumMessageAttachmentClobValue.getClob();

            forumMessageAttachmentClob = ForumMessageClobAttachmentFactory.getInstance().create(forumMessageAttachmentPK, clob, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEventUsingNames(forumMessageAttachmentClob.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageAttachmentClob.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteForumMessageClobAttachment(ForumMessageClobAttachment forumMessageAttachmentClob, BasePK deletedBy) {
        forumMessageAttachmentClob.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(forumMessageAttachmentClob.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageAttachmentClob.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteForumMessageClobAttachmentByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, BasePK deletedBy) {
        ForumMessageClobAttachment forumMessageAttachmentClob = getForumMessageClobAttachmentForUpdate(forumMessageAttachment);

        if(forumMessageAttachmentClob != null) {
            deleteForumMessageClobAttachment(forumMessageAttachmentClob, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Descriptions
    // --------------------------------------------------------------------------------

    public ForumMessageAttachmentDescription createForumMessageAttachmentDescription(ForumMessageAttachment forumMessageAttachment, Language language, String description, BasePK createdBy) {
        ForumMessageAttachmentDescription forumMessageAttachmentDescription = ForumMessageAttachmentDescriptionFactory.getInstance().create(forumMessageAttachment, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageAttachmentDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return forumMessageAttachmentDescription;
    }

    private static final Map<EntityPermission, String> getForumMessageAttachmentDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageattachmentdescriptions "
                + "WHERE frmmsgattd_frmmsgatt_forummessageattachmentid = ? AND frmmsgattd_lang_languageid = ? AND frmmsgattd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageattachmentdescriptions "
                + "WHERE frmmsgattd_frmmsgatt_forummessageattachmentid = ? AND frmmsgattd_lang_languageid = ? AND frmmsgattd_thrutime = ? "
                + "FOR UPDATE");
        getForumMessageAttachmentDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ForumMessageAttachmentDescription getForumMessageAttachmentDescription(ForumMessageAttachment forumMessageAttachment, Language language, EntityPermission entityPermission) {
        return ForumMessageAttachmentDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getForumMessageAttachmentDescriptionQueries,
                forumMessageAttachment, language, Session.MAX_TIME);
    }

    public ForumMessageAttachmentDescription getForumMessageAttachmentDescription(ForumMessageAttachment forumMessageAttachment, Language language) {
        return getForumMessageAttachmentDescription(forumMessageAttachment, language, EntityPermission.READ_ONLY);
    }

    public ForumMessageAttachmentDescription getForumMessageAttachmentDescriptionForUpdate(ForumMessageAttachment forumMessageAttachment, Language language) {
        return getForumMessageAttachmentDescription(forumMessageAttachment, language, EntityPermission.READ_WRITE);
    }

    public ForumMessageAttachmentDescriptionValue getForumMessageAttachmentDescriptionValue(ForumMessageAttachmentDescription forumMessageAttachmentDescription) {
        return forumMessageAttachmentDescription == null ? null : forumMessageAttachmentDescription.getForumMessageAttachmentDescriptionValue().clone();
    }

    public ForumMessageAttachmentDescriptionValue getForumMessageAttachmentDescriptionValueForUpdate(ForumMessageAttachment forumMessageAttachment, Language language) {
        return getForumMessageAttachmentDescriptionValue(getForumMessageAttachmentDescriptionForUpdate(forumMessageAttachment, language));
    }

    private static final Map<EntityPermission, String> getForumMessageAttachmentDescriptionsByForumMessageAttachmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageattachmentdescriptions, languages "
                + "WHERE frmmsgattd_frmmsgatt_forummessageattachmentid = ? AND frmmsgattd_thrutime = ? AND frmmsgattd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageattachmentdescriptions "
                + "WHERE frmmsgattd_frmmsgatt_forummessageattachmentid = ? AND frmmsgattd_thrutime = ? "
                + "FOR UPDATE");
        getForumMessageAttachmentDescriptionsByForumMessageAttachmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ForumMessageAttachmentDescription> getForumMessageAttachmentDescriptionsByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, EntityPermission entityPermission) {
        return ForumMessageAttachmentDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getForumMessageAttachmentDescriptionsByForumMessageAttachmentQueries,
                forumMessageAttachment, Session.MAX_TIME);
    }

    public List<ForumMessageAttachmentDescription> getForumMessageAttachmentDescriptionsByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageAttachmentDescriptionsByForumMessageAttachment(forumMessageAttachment, EntityPermission.READ_ONLY);
    }

    public List<ForumMessageAttachmentDescription> getForumMessageAttachmentDescriptionsByForumMessageAttachmentForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageAttachmentDescriptionsByForumMessageAttachment(forumMessageAttachment, EntityPermission.READ_WRITE);
    }

    public String getBestForumMessageAttachmentDescription(ForumMessageAttachment forumMessageAttachment, Language language) {
        String description;
        ForumMessageAttachmentDescription forumMessageAttachmentDescription = getForumMessageAttachmentDescription(forumMessageAttachment, language);

        if(forumMessageAttachmentDescription == null && !language.getIsDefault()) {
            forumMessageAttachmentDescription = getForumMessageAttachmentDescription(forumMessageAttachment, getPartyControl().getDefaultLanguage());
        }

        if(forumMessageAttachmentDescription == null) {
            ForumMessageAttachmentDetail forumMessageAttachmentDetail = forumMessageAttachment.getLastDetail();
            
            description = new StringBuilder(forumMessageAttachmentDetail.getForumMessage().getLastDetail().getForumMessageName()).append('-')
                    .append(forumMessageAttachmentDetail.getForumMessageAttachmentSequence()).toString();
        } else {
            description = forumMessageAttachmentDescription.getDescription();
        }

        return description;
    }

    public ForumMessageAttachmentDescriptionTransfer getForumMessageAttachmentDescriptionTransfer(UserVisit userVisit, ForumMessageAttachmentDescription forumMessageAttachmentDescription) {
        return getForumTransferCaches(userVisit).getForumMessageAttachmentDescriptionTransferCache().getForumMessageAttachmentDescriptionTransfer(forumMessageAttachmentDescription);
    }

    public List<ForumMessageAttachmentDescriptionTransfer> getForumMessageAttachmentDescriptionTransfersByForumMessageAttachment(UserVisit userVisit, ForumMessageAttachment forumMessageAttachment) {
        List<ForumMessageAttachmentDescription> forumMessageAttachmentDescriptions = getForumMessageAttachmentDescriptionsByForumMessageAttachment(forumMessageAttachment);
        List<ForumMessageAttachmentDescriptionTransfer> forumMessageAttachmentDescriptionTransfers = null;

        if(forumMessageAttachmentDescriptions != null) {
            forumMessageAttachmentDescriptionTransfers = new ArrayList<>(forumMessageAttachmentDescriptions.size());

            for(ForumMessageAttachmentDescription forumMessageAttachmentDescription: forumMessageAttachmentDescriptions) {
                forumMessageAttachmentDescriptionTransfers.add(getForumTransferCaches(userVisit).getForumMessageAttachmentDescriptionTransferCache().getForumMessageAttachmentDescriptionTransfer(forumMessageAttachmentDescription));
            }
        }

        return forumMessageAttachmentDescriptionTransfers;
    }

    public void updateForumMessageAttachmentDescriptionFromValue(ForumMessageAttachmentDescriptionValue forumMessageAttachmentDescriptionValue, BasePK updatedBy) {
        if(forumMessageAttachmentDescriptionValue.hasBeenModified()) {
            ForumMessageAttachmentDescription forumMessageAttachmentDescription = ForumMessageAttachmentDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, forumMessageAttachmentDescriptionValue.getPrimaryKey());

            forumMessageAttachmentDescription.setThruTime(session.START_TIME_LONG);
            forumMessageAttachmentDescription.store();

            ForumMessageAttachment forumMessageAttachment = forumMessageAttachmentDescription.getForumMessageAttachment();
            Language language = forumMessageAttachmentDescription.getLanguage();
            String description = forumMessageAttachmentDescriptionValue.getDescription();

            forumMessageAttachmentDescription = ForumMessageAttachmentDescriptionFactory.getInstance().create(forumMessageAttachment, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageAttachmentDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteForumMessageAttachmentDescription(ForumMessageAttachmentDescription forumMessageAttachmentDescription, BasePK deletedBy) {
        forumMessageAttachmentDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(forumMessageAttachmentDescription.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageAttachmentDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteForumMessageAttachmentDescriptionsByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, BasePK deletedBy) {
        List<ForumMessageAttachmentDescription> forumMessageAttachmentDescriptions = getForumMessageAttachmentDescriptionsByForumMessageAttachmentForUpdate(forumMessageAttachment);

        forumMessageAttachmentDescriptions.stream().forEach((forumMessageAttachmentDescription) -> {
            deleteForumMessageAttachmentDescription(forumMessageAttachmentDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Roles
    // --------------------------------------------------------------------------------
    
    public ForumMessageRole createForumMessageRole(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party,
            BasePK createdBy) {
        ForumMessageRole forumMessageRole = ForumMessageRoleFactory.getInstance().create(forumMessage, forumRoleType,
                party, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forumMessage.getPrimaryKey(), EventTypes.MODIFY.name(), forumMessageRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumMessageRole;
    }
    
    private ForumMessageRole getForumMessageRole(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party, EntityPermission entityPermission) {
        ForumMessageRole forumMessageRole = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageroles " +
                        "WHERE frmmsgr_frmmsg_forummessageid = ? AND frmmsgr_frmrtyp_forumroletypeid = ? AND frmmsgr_par_partyid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageroles " +
                        "WHERE frmmsgr_frmmsg_forummessageid = ? AND frmmsgr_frmrtyp_forumroletypeid = ? AND frmmsgr_par_partyid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumMessageRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessage.getPrimaryKey().getEntityId());
            ps.setLong(2, forumRoleType.getPrimaryKey().getEntityId());
            ps.setLong(3, party.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            forumMessageRole = ForumMessageRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageRole;
    }
    
    public ForumMessageRole getForumMessageRole(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party) {
        return getForumMessageRole(forumMessage, forumRoleType, party, EntityPermission.READ_ONLY);
    }
    
    public ForumMessageRole getForumMessageRoleForUpdate(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party) {
        return getForumMessageRole(forumMessage, forumRoleType, party, EntityPermission.READ_WRITE);
    }
    
    private List<ForumMessageRole> getForumMessageRolesByForumMessage(ForumMessage forumMessage, EntityPermission entityPermission) {
        List<ForumMessageRole> forumMessageRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageroles, forumroletypes, parties, partydetails " +
                        "WHERE frmmsgr_frmmsg_forummessageid = ? AND frmmsgr_thrutime = ? " +
                        "AND frmmsgr_frmrtyp_forumroletypeid = frmrtyp_forumroletypeid " +
                        "AND frmmsgr_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "ORDER BY frmrtyp_sortorder, frmrtyp_forumroletypename, pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageroles " +
                        "WHERE frmmsgr_frmmsg_forummessageid = ? AND frmmsgr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumMessageRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessage.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumMessageRoles = ForumMessageRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageRoles;
    }
    
    public List<ForumMessageRole> getForumMessageRolesByForumMessage(ForumMessage forumMessage) {
        return getForumMessageRolesByForumMessage(forumMessage, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMessageRole> getForumMessageRolesByForumMessageForUpdate(ForumMessage forumMessage) {
        return getForumMessageRolesByForumMessage(forumMessage, EntityPermission.READ_WRITE);
    }
    
    public ForumMessageRoleTransfer getForumMessageRoleTransfer(UserVisit userVisit, ForumMessageRole forumMessageRole) {
        return getForumTransferCaches(userVisit).getForumMessageRoleTransferCache().getForumMessageRoleTransfer(forumMessageRole);
    }
    
    public List<ForumMessageRoleTransfer> getForumMessageRoleTransfers(UserVisit userVisit, List<ForumMessageRole> forumMessageRoles) {
        List<ForumMessageRoleTransfer> forumMessageRoleTransfers = new ArrayList<>(forumMessageRoles.size());
        ForumMessageRoleTransferCache forumMessageRoleTransferCache = getForumTransferCaches(userVisit).getForumMessageRoleTransferCache();
        
        forumMessageRoles.stream().forEach((forumMessageRole) -> {
            forumMessageRoleTransfers.add(forumMessageRoleTransferCache.getForumMessageRoleTransfer(forumMessageRole));
        });
        
        return forumMessageRoleTransfers;
    }
    
    public List<ForumMessageRoleTransfer> getForumMessageRoleTransfersByForumMessage(UserVisit userVisit, ForumMessage forumMessage) {
        return getForumMessageRoleTransfers(userVisit, getForumMessageRolesByForumMessage(forumMessage));
    }
    
    public void deleteForumMessageRole(ForumMessageRole forumMessageRole, BasePK deletedBy) {
        forumMessageRole.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(forumMessageRole.getForumMessagePK(), EventTypes.MODIFY.name(), forumMessageRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumMessageRoles(List<ForumMessageRole> forumMessageRoles, BasePK deletedBy) {
        forumMessageRoles.stream().forEach((forumMessageRole) -> {
            deleteForumMessageRole(forumMessageRole, deletedBy);
        });
    }
    
    public void deleteForumMessageRolesByForumMessage(ForumMessage forumMessage, BasePK deletedBy) {
        deleteForumMessageRoles(getForumMessageRolesByForumMessageForUpdate(forumMessage), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Parts
    // --------------------------------------------------------------------------------
    
    public ForumMessagePart createForumMessagePart(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language, MimeType mimeType, BasePK createdBy) {
        
        ForumMessagePart forumMessagePart = ForumMessagePartFactory.getInstance().create();
        ForumMessagePartDetail forumMessagePartDetail = ForumMessagePartDetailFactory.getInstance().create(session,
                forumMessagePart, forumMessage, forumMessagePartType, language, mimeType, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        forumMessagePart = ForumMessagePartFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                forumMessagePart.getPrimaryKey());
        forumMessagePart.setActiveDetail(forumMessagePartDetail);
        forumMessagePart.setLastDetail(forumMessagePartDetail);
        forumMessagePart.store();
        
        sendEventUsingNames(forumMessage.getPrimaryKey(), EventTypes.MODIFY.name(), forumMessagePart.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumMessagePart;
    }
    
    private ForumMessagePart getForumMessagePart(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language, EntityPermission entityPermission) {
        ForumMessagePart forumMessagePart = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageparts, forummessagepartdetails " +
                        "WHERE frmmsgprt_activedetailid = frmmsgprtdt_forummessagepartdetailid " +
                        "AND frmmsgprtdt_frmmsg_forummessageid = ? AND frmmsgprtdt_frmmsgprttyp_forummessageparttypeid = ? AND frmmsgprtdt_lang_languageid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageparts, forummessagepartdetails " +
                        "WHERE frmmsgprt_activedetailid = frmmsgprtdt_forummessagepartdetailid " +
                        "AND frmmsgprtdt_frmmsg_forummessageid = ? AND frmmsgprtdt_frmmsgprttyp_forummessageparttypeid = ? AND frmmsgprtdt_lang_languageid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumMessagePartFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessage.getPrimaryKey().getEntityId());
            ps.setLong(2, forumMessagePartType.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            
            forumMessagePart = ForumMessagePartFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessagePart;
    }
    
    public ForumMessagePart getForumMessagePart(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language) {
        return getForumMessagePart(forumMessage, forumMessagePartType, language, EntityPermission.READ_ONLY);
    }
    
    public ForumMessagePart getForumMessagePartForUpdate(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language) {
        return getForumMessagePart(forumMessage, forumMessagePartType, language, EntityPermission.READ_WRITE);
    }
    
    public ForumMessagePartDetailValue getForumMessagePartDetailValueForUpdate(ForumMessagePart forumMessagePart) {
        return forumMessagePart == null? null: forumMessagePart.getLastDetailForUpdate().getForumMessagePartDetailValue().clone();
    }
    
    public ForumMessagePartDetailValue getForumMessagePartDetailValueForUpdate(ForumMessage forumMessage,
            ForumMessagePartType forumMessagePartType, Language language) {
        return getForumMessagePartDetailValueForUpdate(getForumMessagePartForUpdate(forumMessage, forumMessagePartType, language));
    }
    
    public List<ForumMessagePart> getForumMessagePartsByForumMessageForUpdate(ForumMessage forumMessage) {
        List<ForumMessagePart> forumMessageParts = null;
        
        try {
            PreparedStatement ps = ForumMessagePartFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessageparts, forummessagepartdetails " +
                    "WHERE frmmsgprt_activedetailid = frmmsgprtdt_forummessagepartdetailid " +
                    "AND frmmsgprtdt_frmmsg_forummessageid = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, forumMessage.getPrimaryKey().getEntityId());
            
            forumMessageParts = ForumMessagePartFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageParts;
    }
    
    public ForumMessagePart getBestForumMessagePart(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language) {
        ForumMessagePart forumMessagePart = getForumMessagePart(forumMessage, forumMessagePartType, language);
        
        if(forumMessagePart == null && !language.getIsDefault()) {
            forumMessagePart = getForumMessagePart(forumMessage, forumMessagePartType, getPartyControl().getDefaultLanguage());
        }
        
        return forumMessagePart;
    }
    
    public ForumMessagePartTransfer getForumMessagePartTransfer(UserVisit userVisit, ForumMessagePart forumMessagePart) {
        return getForumTransferCaches(userVisit).getForumMessagePartTransferCache().getForumMessagePartTransfer(forumMessagePart);
    }
    
    public List<ForumMessagePartTransfer> getForumMessagePartTransfers(UserVisit userVisit, List<ForumMessagePart> forumMessageParts) {
        List<ForumMessagePartTransfer> forumMessagePartTransfers = new ArrayList<>(forumMessageParts.size());
        ForumMessagePartTransferCache forumMessagePartTransferCache = getForumTransferCaches(userVisit).getForumMessagePartTransferCache();
        
        forumMessageParts.stream().forEach((forumMessagePart) -> {
            forumMessagePartTransfers.add(forumMessagePartTransferCache.getForumMessagePartTransfer(forumMessagePart));
        });
        
        return forumMessagePartTransfers;
    }
    
    public List<ForumMessagePartTransfer> getForumMessagePartTransfersByForumMessageAndLanguage(UserVisit userVisit, ForumMessage forumMessage, Language language) {
        List<ForumMessageTypePartType> forumMessageTypePartTypes = getForumMessageTypePartTypesByForumMessageType(forumMessage.getLastDetail().getForumMessageType());
        List<ForumMessagePartTransfer> forumMessagePartTransfers = new ArrayList<>(forumMessageTypePartTypes.size());
        
        forumMessageTypePartTypes.stream().map((forumMessageTypePartType) -> getBestForumMessagePart(forumMessage,
                forumMessageTypePartType.getForumMessagePartType(), language)).filter((forumMessagePart) -> (forumMessagePart != null)).forEach((forumMessagePart) -> {
                    forumMessagePartTransfers.add(getForumMessagePartTransfer(userVisit, forumMessagePart));
                });
        
        return forumMessagePartTransfers;
    }
    
    public void updateForumMessagePartFromValue(ForumMessagePartDetailValue forumMessagePartDetailValue, BasePK updatedBy) {
        if(forumMessagePartDetailValue.hasBeenModified()) {
            ForumMessagePart forumMessagePart = ForumMessagePartFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessagePartDetailValue.getForumMessagePartPK());
            ForumMessagePartDetail forumMessagePartDetail = forumMessagePart.getActiveDetailForUpdate();
            
            forumMessagePartDetail.setThruTime(session.START_TIME_LONG);
            forumMessagePartDetail.store();
            
            ForumMessagePartPK forumMessagePartPK = forumMessagePartDetail.getForumMessagePartPK();
            ForumMessagePK forumMessagePK = forumMessagePartDetail.getForumMessagePK(); // Not updated
            ForumMessagePartTypePK forumMessagePartTypePK = forumMessagePartDetail.getForumMessagePartTypePK(); // Not updated
            LanguagePK languagePK = forumMessagePartDetail.getLanguagePK(); // Not updated
            MimeTypePK mimeTypePK = forumMessagePartDetailValue.getMimeTypePK();
            
            forumMessagePartDetail = ForumMessagePartDetailFactory.getInstance().create(forumMessagePartPK, forumMessagePK,
                    forumMessagePartTypePK, languagePK, mimeTypePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            forumMessagePart.setActiveDetail(forumMessagePartDetail);
            forumMessagePart.setLastDetail(forumMessagePartDetail);
            
            sendEventUsingNames(forumMessagePartPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteForumMessagePart(ForumMessagePart forumMessagePart, BasePK deletedBy) {
        ForumMessagePartDetail forumMessagePartDetail = forumMessagePart.getLastDetailForUpdate();
        MimeType mimeType = forumMessagePartDetail.getMimeType();
        
        if(mimeType == null) {
            deleteForumStringMessagePartByForumMessagePart(forumMessagePart, deletedBy);
        } else {
            String entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            
            if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                deleteForumBlobMessagePartByForumMessagePart(forumMessagePart, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                deleteForumClobMessagePartByForumMessagePart(forumMessagePart, deletedBy);
            }
        }
        
        forumMessagePartDetail.setThruTime(session.START_TIME_LONG);
        forumMessagePart.setActiveDetail(null);
        forumMessagePart.store();
        
        sendEventUsingNames(forumMessagePartDetail.getForumMessagePK(), EventTypes.MODIFY.name(), forumMessagePart.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumMessageParts(List<ForumMessagePart> forumMessageParts, BasePK deletedBy) {
        forumMessageParts.stream().forEach((forumMessagePart) -> {
            deleteForumMessagePart(forumMessagePart, deletedBy);
        });
    }
    
    public void deleteForumMessagePartsByForumMessage(ForumMessage forumMessage, BasePK deletedBy) {
        deleteForumMessageParts(getForumMessagePartsByForumMessageForUpdate(forumMessage), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum String Message Parts
    // --------------------------------------------------------------------------------
    
    public ForumStringMessagePart createForumStringMessagePart(ForumMessagePart forumMessagePart, String string, BasePK createdBy) {
        ForumStringMessagePart forumStringMessagePart = ForumStringMessagePartFactory.getInstance().create(session,
                forumMessagePart, string, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forumMessagePart.getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumStringMessagePart.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumStringMessagePart;
    }
    
    private ForumStringMessagePart getForumStringMessagePart(ForumMessagePart forumMessagePart, EntityPermission entityPermission) {
        ForumStringMessagePart forumStringMessagePart = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumstringmessageparts " +
                        "WHERE frmsmsgprt_frmmsgprt_forummessagepartid = ? AND frmsmsgprt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumstringmessageparts " +
                        "WHERE frmsmsgprt_frmmsgprt_forummessagepartid = ? AND frmsmsgprt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumStringMessagePartFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessagePart.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumStringMessagePart = ForumStringMessagePartFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumStringMessagePart;
    }
    
    public ForumStringMessagePart getForumStringMessagePart(ForumMessagePart forumMessagePart) {
        return getForumStringMessagePart(forumMessagePart, EntityPermission.READ_ONLY);
    }
    
    public ForumStringMessagePart getForumStringMessagePartForUpdate(ForumMessagePart forumMessagePart) {
        return getForumStringMessagePart(forumMessagePart, EntityPermission.READ_WRITE);
    }
    
    public ForumStringMessagePartValue getForumStringMessagePartValue(ForumStringMessagePart forumStringMessagePart) {
        return forumStringMessagePart == null? null: forumStringMessagePart.getForumStringMessagePartValue().clone();
    }
    
    public ForumStringMessagePartValue getForumStringMessagePartValueForUpdate(ForumMessagePart forumMessagePart) {
        return getForumStringMessagePartValue(getForumStringMessagePartForUpdate(forumMessagePart));
    }
    
    public void updateForumStringMessagePartFromValue(ForumStringMessagePartValue forumStringMessagePartValue, BasePK updatedBy) {
        if(forumStringMessagePartValue.hasBeenModified()) {
            ForumStringMessagePart forumStringMessagePart = ForumStringMessagePartFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumStringMessagePartValue.getPrimaryKey());
            
            forumStringMessagePart.setThruTime(session.START_TIME_LONG);
            forumStringMessagePart.store();
            
            ForumMessagePartPK forumMessagePartPK = forumStringMessagePart.getForumMessagePartPK(); // Not updated
            String string = forumStringMessagePartValue.getString();
            
            forumStringMessagePart = ForumStringMessagePartFactory.getInstance().create(forumMessagePartPK, string,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(forumStringMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(),
                    EventTypes.MODIFY.name(), forumStringMessagePart.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteForumStringMessagePart(ForumStringMessagePart forumStringMessagePart, BasePK deletedBy) {
        forumStringMessagePart.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(forumStringMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(),
                EventTypes.MODIFY.name(), forumStringMessagePart.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumStringMessagePartByForumMessagePart(ForumMessagePart forumMessagePart, BasePK deletedBy) {
        ForumStringMessagePart forumStringMessagePart = getForumStringMessagePartForUpdate(forumMessagePart);
        
        if(forumStringMessagePart != null) {
            deleteForumStringMessagePart(forumStringMessagePart, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Clob Message Parts
    // --------------------------------------------------------------------------------
    
    public ForumClobMessagePart createForumClobMessagePart(ForumMessagePart forumMessagePart, String clob, BasePK createdBy) {
        ForumClobMessagePart forumClobMessagePart = ForumClobMessagePartFactory.getInstance().create(session,
                forumMessagePart, clob, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forumMessagePart.getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumClobMessagePart.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumClobMessagePart;
    }
    
    private ForumClobMessagePart getForumClobMessagePart(ForumMessagePart forumMessagePart, EntityPermission entityPermission) {
        ForumClobMessagePart forumClobMessagePart = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumclobmessageparts " +
                        "WHERE frmcmsgprt_frmmsgprt_forummessagepartid = ? AND frmcmsgprt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumclobmessageparts " +
                        "WHERE frmcmsgprt_frmmsgprt_forummessagepartid = ? AND frmcmsgprt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumClobMessagePartFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessagePart.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumClobMessagePart = ForumClobMessagePartFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumClobMessagePart;
    }
    
    public ForumClobMessagePart getForumClobMessagePart(ForumMessagePart forumMessagePart) {
        return getForumClobMessagePart(forumMessagePart, EntityPermission.READ_ONLY);
    }
    
    public ForumClobMessagePart getForumClobMessagePartForUpdate(ForumMessagePart forumMessagePart) {
        return getForumClobMessagePart(forumMessagePart, EntityPermission.READ_WRITE);
    }
    
    public ForumClobMessagePartValue getForumClobMessagePartValue(ForumClobMessagePart forumClobMessagePart) {
        return forumClobMessagePart == null? null: forumClobMessagePart.getForumClobMessagePartValue().clone();
    }
    
    public ForumClobMessagePartValue getForumClobMessagePartValueForUpdate(ForumMessagePart forumMessagePart) {
        return getForumClobMessagePartValue(getForumClobMessagePartForUpdate(forumMessagePart));
    }
    
    public void updateForumClobMessagePartFromValue(ForumClobMessagePartValue forumClobMessagePartValue, BasePK updatedBy) {
        if(forumClobMessagePartValue.hasBeenModified()) {
            ForumClobMessagePart forumClobMessagePart = ForumClobMessagePartFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumClobMessagePartValue.getPrimaryKey());
            
            forumClobMessagePart.setThruTime(session.START_TIME_LONG);
            forumClobMessagePart.store();
            
            ForumMessagePartPK forumMessagePartPK = forumClobMessagePart.getForumMessagePartPK(); // Not updated
            String clob = forumClobMessagePartValue.getClob();
            
            forumClobMessagePart = ForumClobMessagePartFactory.getInstance().create(forumMessagePartPK, clob,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(forumClobMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumClobMessagePart.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteForumClobMessagePart(ForumClobMessagePart forumClobMessagePart, BasePK deletedBy) {
        forumClobMessagePart.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(forumClobMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumClobMessagePart.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumClobMessagePartByForumMessagePart(ForumMessagePart forumMessagePart, BasePK deletedBy) {
        ForumClobMessagePart forumClobMessagePart = getForumClobMessagePartForUpdate(forumMessagePart);
        
        if(forumClobMessagePart != null) {
            deleteForumClobMessagePart(forumClobMessagePart, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Blob Message Parts
    // --------------------------------------------------------------------------------
    
    public ForumBlobMessagePart createForumBlobMessagePart(ForumMessagePart forumMessagePart, ByteArray blob, BasePK createdBy) {
        ForumBlobMessagePart forumBlobMessagePart = ForumBlobMessagePartFactory.getInstance().create(session,
                forumMessagePart, blob, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(forumMessagePart.getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumBlobMessagePart.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return forumBlobMessagePart;
    }
    
    private ForumBlobMessagePart getForumBlobMessagePart(ForumMessagePart forumMessagePart, EntityPermission entityPermission) {
        ForumBlobMessagePart forumBlobMessagePart = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumblobmessageparts " +
                        "WHERE frmbmsgprt_frmmsgprt_forummessagepartid = ? AND frmbmsgprt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumblobmessageparts " +
                        "WHERE frmbmsgprt_frmmsgprt_forummessagepartid = ? AND frmbmsgprt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ForumBlobMessagePartFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessagePart.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumBlobMessagePart = ForumBlobMessagePartFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumBlobMessagePart;
    }
    
    public ForumBlobMessagePart getForumBlobMessagePart(ForumMessagePart forumMessagePart) {
        return getForumBlobMessagePart(forumMessagePart, EntityPermission.READ_ONLY);
    }
    
    public ForumBlobMessagePart getForumBlobMessagePartForUpdate(ForumMessagePart forumMessagePart) {
        return getForumBlobMessagePart(forumMessagePart, EntityPermission.READ_WRITE);
    }
    
    public ForumBlobMessagePartValue getForumBlobMessagePartValue(ForumBlobMessagePart forumBlobMessagePart) {
        return forumBlobMessagePart == null? null: forumBlobMessagePart.getForumBlobMessagePartValue().clone();
    }
    
    public ForumBlobMessagePartValue getForumBlobMessagePartValueForUpdate(ForumMessagePart forumMessagePart) {
        return getForumBlobMessagePartValue(getForumBlobMessagePartForUpdate(forumMessagePart));
    }
    
    public void updateForumBlobMessagePartFromValue(ForumBlobMessagePartValue forumBlobMessagePartValue, BasePK updatedBy) {
        if(forumBlobMessagePartValue.hasBeenModified()) {
            ForumBlobMessagePart forumBlobMessagePart = ForumBlobMessagePartFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumBlobMessagePartValue.getPrimaryKey());
            
            forumBlobMessagePart.setThruTime(session.START_TIME_LONG);
            forumBlobMessagePart.store();
            
            ForumMessagePartPK forumMessagePartPK = forumBlobMessagePart.getForumMessagePartPK(); // Not updated
            ByteArray blob = forumBlobMessagePartValue.getBlob();
            
            forumBlobMessagePart = ForumBlobMessagePartFactory.getInstance().create(forumMessagePartPK, blob,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(forumBlobMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumBlobMessagePart.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteForumBlobMessagePart(ForumBlobMessagePart forumBlobMessagePart, BasePK deletedBy) {
        forumBlobMessagePart.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(forumBlobMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(), EventTypes.MODIFY.name(), forumBlobMessagePart.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteForumBlobMessagePartByForumMessagePart(ForumMessagePart forumMessagePart, BasePK deletedBy) {
        ForumBlobMessagePart forumBlobMessagePart = getForumBlobMessagePartForUpdate(forumMessagePart);
        
        if(forumBlobMessagePart != null) {
            deleteForumBlobMessagePart(forumBlobMessagePart, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Part Types
    // --------------------------------------------------------------------------------
    
    public ForumMessagePartType createForumMessagePartType(String forumMessagePartTypeName, MimeTypeUsageType mimeTypeUsageType,
            Integer sortOrder) {
        return ForumMessagePartTypeFactory.getInstance().create(forumMessagePartTypeName, mimeTypeUsageType, sortOrder);
    }
    
    public ForumMessagePartType getForumMessagePartTypeByName(String forumMessagePartTypeName) {
        ForumMessagePartType forumMessagePartType = null;
        
        try {
            PreparedStatement ps = ForumMessagePartTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessageparttypes " +
                    "WHERE frmmsgprttyp_forummessageparttypename = ?");
            
            ps.setString(1, forumMessagePartTypeName);
            
            forumMessagePartType = ForumMessagePartTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessagePartType;
    }
    
    public List<ForumMessagePartType> getForumMessagePartTypes() {
        PreparedStatement ps = ForumMessagePartTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forummessageparttypes " +
                "ORDER BY frmmsgprttyp_sortorder, frmmsgprttyp_forummessageparttypename");
        
        return ForumMessagePartTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ForumMessagePartTypeTransfer getForumMessagePartTypeTransfer(UserVisit userVisit, ForumMessagePartType forumMessagePartType) {
        return getForumTransferCaches(userVisit).getForumMessagePartTypeTransferCache().getForumMessagePartTypeTransfer(forumMessagePartType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Part Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumMessagePartTypeDescription createForumMessagePartTypeDescription(ForumMessagePartType forumMessagePartType,
            Language language, String description) {
        return ForumMessagePartTypeDescriptionFactory.getInstance().create(forumMessagePartType, language, description);
    }
    
    public ForumMessagePartTypeDescription getForumMessagePartTypeDescription(ForumMessagePartType forumMessagePartType,
            Language language) {
        ForumMessagePartTypeDescription forumMessagePartTypeDescription = null;
        
        try {
            PreparedStatement ps = ForumMessagePartTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessageparttypedescriptions " +
                    "WHERE frmmsgprttypd_frmmsgprttyp_forummessageparttypeid = ? AND frmmsgprttypd_lang_languageid = ?");
            
            ps.setLong(1, forumMessagePartType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            forumMessagePartTypeDescription = ForumMessagePartTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessagePartTypeDescription;
    }
    
    public String getBestForumMessagePartTypeDescription(ForumMessagePartType forumMessagePartType, Language language) {
        String description;
        ForumMessagePartTypeDescription forumMessagePartTypeDescription = getForumMessagePartTypeDescription(forumMessagePartType,
                language);
        
        if(forumMessagePartTypeDescription == null && !language.getIsDefault()) {
            forumMessagePartTypeDescription = getForumMessagePartTypeDescription(forumMessagePartType,
                    getPartyControl().getDefaultLanguage());
        }
        
        if(forumMessagePartTypeDescription == null) {
            description = forumMessagePartType.getForumMessagePartTypeName();
        } else {
            description = forumMessagePartTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Types
    // --------------------------------------------------------------------------------
    
    public ForumMessageType createForumMessageType(String forumMessageTypeName, Boolean isDefault, Integer sortOrder) {
        return ForumMessageTypeFactory.getInstance().create(forumMessageTypeName, isDefault, sortOrder);
    }
    
    public ForumMessageType getForumMessageTypeByName(String forumMessageTypeName) {
        ForumMessageType forumMessageType = null;
        
        try {
            PreparedStatement ps = ForumMessageTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypes " +
                    "WHERE frmmsgtyp_forummessagetypename = ?");
            
            ps.setString(1, forumMessageTypeName);
            
            forumMessageType = ForumMessageTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageType;
    }
    
    public List<ForumMessageType> getForumMessageTypes() {
        PreparedStatement ps = ForumMessageTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forummessagetypes " +
                "ORDER BY frmmsgtyp_sortorder, frmmsgtyp_forummessagetypename");
        
        return ForumMessageTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ForumMessageTypeChoicesBean getForumMessageTypeChoices(String defaultForumMessageTypeChoice, Language language,
            boolean allowNullChoice) {
        List<ForumMessageType> forumMessageTypes = getForumMessageTypes();
        int size = forumMessageTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultForumMessageTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(ForumMessageType forumMessageType: forumMessageTypes) {
            String label = getBestForumMessageTypeDescription(forumMessageType, language);
            String value = forumMessageType.getForumMessageTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultForumMessageTypeChoice == null? false: defaultForumMessageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && forumMessageType.getIsDefault()))
                defaultValue = value;
        }
        
        return new ForumMessageTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ForumMessageTypeTransfer getForumMessageTypeTransfer(UserVisit userVisit, ForumMessageType forumMessageType) {
        return getForumTransferCaches(userVisit).getForumMessageTypeTransferCache().getForumMessageTypeTransfer(forumMessageType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumMessageTypeDescription createForumMessageTypeDescription(ForumMessageType forumMessageType, Language language,
            String description) {
        return ForumMessageTypeDescriptionFactory.getInstance().create(forumMessageType, language, description);
    }
    
    public ForumMessageTypeDescription getForumMessageTypeDescription(ForumMessageType forumMessageType, Language language) {
        ForumMessageTypeDescription forumMessageTypeDescription = null;
        
        try {
            PreparedStatement ps = ForumMessageTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypedescriptions " +
                    "WHERE frmmsgtypd_frmmsgtyp_forummessagetypeid = ? AND frmmsgtypd_lang_languageid = ?");
            
            ps.setLong(1, forumMessageType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            forumMessageTypeDescription = ForumMessageTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageTypeDescription;
    }
    
    public String getBestForumMessageTypeDescription(ForumMessageType forumMessageType, Language language) {
        String description;
        ForumMessageTypeDescription forumMessageTypeDescription = getForumMessageTypeDescription(forumMessageType, language);
        
        if(forumMessageTypeDescription == null && !language.getIsDefault()) {
            forumMessageTypeDescription = getForumMessageTypeDescription(forumMessageType, getPartyControl().getDefaultLanguage());
        }
        
        if(forumMessageTypeDescription == null) {
            description = forumMessageType.getForumMessageTypeName();
        } else {
            description = forumMessageTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Part Types
    // --------------------------------------------------------------------------------
    
    public ForumMessageTypePartType createForumMessageTypePartType(ForumMessageType forumMessageType, Boolean includeInIndex, Boolean indexDefault,
            Integer sortOrder, ForumMessagePartType forumMessagePartType) {
        return ForumMessageTypePartTypeFactory.getInstance().create(forumMessageType, includeInIndex, indexDefault, sortOrder, forumMessagePartType);
    }
    
    public ForumMessageTypePartType getForumMessageTypePartType(ForumMessageType forumMessageType, Integer sortOrder) {
        ForumMessageTypePartType forumMessageTypePartType = null;
        
        try {
            PreparedStatement ps = ForumMessageTypePartTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypeparttypes " +
                    "WHERE frmmsgtypprttyp_frmmsgtyp_forummessagetypeid = ? AND frmmsgtypprttyp_sortorder = ?");
            
            ps.setLong(1, forumMessageType.getPrimaryKey().getEntityId());
            ps.setInt(2, sortOrder);
            
            forumMessageTypePartType = ForumMessageTypePartTypeFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageTypePartType;
    }
    
    public ForumMessageTypePartType getIndexDefaultForumMessageTypePartType(ForumMessageType forumMessageType) {
        ForumMessageTypePartType forumMessageTypePartType = null;
        
        try {
            PreparedStatement ps = ForumMessageTypePartTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypeparttypes " +
                    "WHERE frmmsgtypprttyp_frmmsgtyp_forummessagetypeid = ? AND frmmsgtypprttyp_indexdefault = 1");
            
            ps.setLong(1, forumMessageType.getPrimaryKey().getEntityId());
            
            forumMessageTypePartType = ForumMessageTypePartTypeFactory.getInstance().getEntityFromQuery(session, EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageTypePartType;
    }
    
    public List<ForumMessageTypePartType> getForumMessageTypePartTypesByForumMessageType(ForumMessageType forumMessageType) {
        List<ForumMessageTypePartType> forumMessageTypePartTypes = null;
        
        try {
            PreparedStatement ps = ForumMessageTypePartTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypeparttypes " +
                    "WHERE frmmsgtypprttyp_frmmsgtyp_forummessagetypeid = ? " +
                    "ORDER BY frmmsgtypprttyp_sortorder");
            
            ps.setLong(1, forumMessageType.getPrimaryKey().getEntityId());
            
            forumMessageTypePartTypes = ForumMessageTypePartTypeFactory.getInstance().getEntitiesFromQuery(session, EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageTypePartTypes;
    }
    
    public List<ForumMessageTypePartType> getForumMessageTypePartTypesByForumMessageTypeAndIncludeInIndex(ForumMessageType forumMessageType) {
        List<ForumMessageTypePartType> forumMessageTypePartTypes = null;
        
        try {
            PreparedStatement ps = ForumMessageTypePartTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypeparttypes " +
                    "WHERE frmmsgtypprttyp_frmmsgtyp_forummessagetypeid = ? AND frmmsgtypprttyp_includeinindex = 1 " +
                    "ORDER BY frmmsgtypprttyp_sortorder");
            
            ps.setLong(1, forumMessageType.getPrimaryKey().getEntityId());
            
            forumMessageTypePartTypes = ForumMessageTypePartTypeFactory.getInstance().getEntitiesFromQuery(session, EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageTypePartTypes;
    }
    
    public List<ForumMessageTypePartTypeTransfer> getForumMessageTypePartTypeTransfersByForumMessageType(UserVisit userVisit,
            ForumMessageType forumMessageType) {
        List<ForumMessageTypePartType> forumMessageTypePartTypes = getForumMessageTypePartTypesByForumMessageType(forumMessageType);
        List<ForumMessageTypePartTypeTransfer> forumMessageTypePartTypeTransfers = null;
        
        if(forumMessageTypePartTypes != null) {
            ForumMessageTypePartTypeTransferCache forumMessageTypePartTypeTransferCache = getForumTransferCaches(userVisit).getForumMessageTypePartTypeTransferCache();
            
            forumMessageTypePartTypeTransfers = new ArrayList<>(forumMessageTypePartTypes.size());
            
            for(ForumMessageTypePartType forumMessageTypePartType: forumMessageTypePartTypes) {
                forumMessageTypePartTypeTransfers.add(forumMessageTypePartTypeTransferCache.getForumMessageTypePartTypeTransfer(forumMessageTypePartType));
            }
        }
        
        return forumMessageTypePartTypeTransfers;
    }
    
    public ForumMessageTypePartTypeTransfer getForumMessageTypePartTypeTransfer(UserVisit userVisit, ForumMessageTypePartType forumMessageTypePartType) {
        return getForumTransferCaches(userVisit).getForumMessageTypePartTypeTransferCache().getForumMessageTypePartTypeTransfer(forumMessageTypePartType);
    }
    
}
