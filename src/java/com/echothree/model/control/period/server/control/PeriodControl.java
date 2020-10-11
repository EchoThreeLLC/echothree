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

package com.echothree.model.control.period.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.period.common.choice.PeriodKindChoicesBean;
import com.echothree.model.control.period.common.choice.PeriodTypeChoicesBean;
import com.echothree.model.control.period.common.transfer.PeriodDescriptionTransfer;
import com.echothree.model.control.period.common.transfer.PeriodKindDescriptionTransfer;
import com.echothree.model.control.period.common.transfer.PeriodKindTransfer;
import com.echothree.model.control.period.common.transfer.PeriodTransfer;
import com.echothree.model.control.period.common.transfer.PeriodTypeDescriptionTransfer;
import com.echothree.model.control.period.common.transfer.PeriodTypeTransfer;
import com.echothree.model.control.period.common.choice.FiscalPeriodStatusChoicesBean;
import com.echothree.model.control.period.server.transfer.PeriodKindTransferCache;
import com.echothree.model.control.period.server.transfer.PeriodTransferCache;
import com.echothree.model.control.period.server.transfer.PeriodTransferCaches;
import com.echothree.model.control.period.server.transfer.PeriodTypeTransferCache;
import com.echothree.model.control.accounting.common.workflow.FiscalPeriodStatusConstants;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.period.common.pk.PeriodKindPK;
import com.echothree.model.data.period.common.pk.PeriodPK;
import com.echothree.model.data.period.common.pk.PeriodTypePK;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.entity.PeriodDescription;
import com.echothree.model.data.period.server.entity.PeriodDetail;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodKindDescription;
import com.echothree.model.data.period.server.entity.PeriodKindDetail;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.entity.PeriodTypeDescription;
import com.echothree.model.data.period.server.entity.PeriodTypeDetail;
import com.echothree.model.data.period.server.factory.PeriodDescriptionFactory;
import com.echothree.model.data.period.server.factory.PeriodDetailFactory;
import com.echothree.model.data.period.server.factory.PeriodFactory;
import com.echothree.model.data.period.server.factory.PeriodKindDescriptionFactory;
import com.echothree.model.data.period.server.factory.PeriodKindDetailFactory;
import com.echothree.model.data.period.server.factory.PeriodKindFactory;
import com.echothree.model.data.period.server.factory.PeriodTypeDescriptionFactory;
import com.echothree.model.data.period.server.factory.PeriodTypeDetailFactory;
import com.echothree.model.data.period.server.factory.PeriodTypeFactory;
import com.echothree.model.data.period.server.value.PeriodDescriptionValue;
import com.echothree.model.data.period.server.value.PeriodDetailValue;
import com.echothree.model.data.period.server.value.PeriodKindDescriptionValue;
import com.echothree.model.data.period.server.value.PeriodKindDetailValue;
import com.echothree.model.data.period.server.value.PeriodTypeDescriptionValue;
import com.echothree.model.data.period.server.value.PeriodTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.common.pk.WorkflowEntrancePK;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PeriodControl
        extends BaseModelControl {
    
    /** Creates a new instance of PeriodControl */
    public PeriodControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Period Transfer Caches
    // --------------------------------------------------------------------------------
    
    private PeriodTransferCaches periodTransferCaches = null;
    
    public PeriodTransferCaches getPeriodTransferCaches(UserVisit userVisit) {
        if(periodTransferCaches == null) {
            periodTransferCaches = new PeriodTransferCaches(userVisit, this);
        }
        
        return periodTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Period Kinds
    // --------------------------------------------------------------------------------
    
    public PeriodKind createPeriodKind(String periodKindName, WorkflowEntrance workflowEntrance, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        PeriodKind defaultPeriodKind = getDefaultPeriodKind();
        boolean defaultFound = defaultPeriodKind != null;
        
        if(defaultFound && isDefault) {
            PeriodKindDetailValue defaultPeriodKindDetailValue = getDefaultPeriodKindDetailValueForUpdate();
            
            defaultPeriodKindDetailValue.setIsDefault(Boolean.FALSE);
            updatePeriodKindFromValue(defaultPeriodKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        PeriodKind periodKind = PeriodKindFactory.getInstance().create();
        PeriodKindDetail periodKindDetail = PeriodKindDetailFactory.getInstance().create(periodKind, periodKindName, workflowEntrance, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        periodKind = PeriodKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                periodKind.getPrimaryKey());
        periodKind.setActiveDetail(periodKindDetail);
        periodKind.setLastDetail(periodKindDetail);
        periodKind.store();
        
        sendEventUsingNames(periodKind.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return periodKind;
    }
    
    private PeriodKind getPeriodKindByName(String periodKindName, EntityPermission entityPermission) {
        PeriodKind periodKind = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periodkinds, periodkinddetails " +
                        "WHERE prdk_activedetailid = prdkdt_periodkinddetailid AND prdkdt_periodkindname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periodkinds, periodkinddetails " +
                        "WHERE prdk_activedetailid = prdkdt_periodkinddetailid AND prdkdt_periodkindname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodKindFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, periodKindName);
            
            periodKind = PeriodKindFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periodKind;
    }
    
    public PeriodKind getPeriodKindByName(String periodKindName) {
        return getPeriodKindByName(periodKindName, EntityPermission.READ_ONLY);
    }
    
    public PeriodKind getPeriodKindByNameForUpdate(String periodKindName) {
        return getPeriodKindByName(periodKindName, EntityPermission.READ_WRITE);
    }
    
    public PeriodKindDetailValue getPeriodKindDetailValueForUpdate(PeriodKind periodKind) {
        return periodKind == null? null: periodKind.getLastDetailForUpdate().getPeriodKindDetailValue().clone();
    }
    
    public PeriodKindDetailValue getPeriodKindDetailValueByNameForUpdate(String periodKindName) {
        return getPeriodKindDetailValueForUpdate(getPeriodKindByNameForUpdate(periodKindName));
    }
    
    private PeriodKind getDefaultPeriodKind(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM periodkinds, periodkinddetails " +
                    "WHERE prdk_activedetailid = prdkdt_periodkinddetailid AND prdkdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM periodkinds, periodkinddetails " +
                    "WHERE prdk_activedetailid = prdkdt_periodkinddetailid AND prdkdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PeriodKindFactory.getInstance().prepareStatement(query);
        
        return PeriodKindFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public PeriodKind getDefaultPeriodKind() {
        return getDefaultPeriodKind(EntityPermission.READ_ONLY);
    }
    
    public PeriodKind getDefaultPeriodKindForUpdate() {
        return getDefaultPeriodKind(EntityPermission.READ_WRITE);
    }
    
    public PeriodKindDetailValue getDefaultPeriodKindDetailValueForUpdate() {
        return getDefaultPeriodKind(EntityPermission.READ_WRITE).getLastDetailForUpdate().getPeriodKindDetailValue();
    }
    
    private List<PeriodKind> getPeriodKinds(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM periodkinds, periodkinddetails " +
                    "WHERE prdk_activedetailid = prdkdt_periodkinddetailid " +
                    "ORDER BY prdkdt_sortorder, prdkdt_periodkindname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM periodkinds, periodkinddetails " +
                    "WHERE prdk_activedetailid = prdkdt_periodkinddetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PeriodKindFactory.getInstance().prepareStatement(query);
        
        return PeriodKindFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<PeriodKind> getPeriodKinds() {
        return getPeriodKinds(EntityPermission.READ_ONLY);
    }
    
    public List<PeriodKind> getPeriodKindsForUpdate() {
        return getPeriodKinds(EntityPermission.READ_WRITE);
    }
    
    public PeriodKindChoicesBean getPeriodKindChoices(String defaultPeriodKindChoice, Language language, boolean allowNullChoice) {
        List<PeriodKind> periodKinds = getPeriodKinds();
        int size = periodKinds.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultPeriodKindChoice == null) {
                defaultValue = "";
            }
        }
        
        for(PeriodKind periodKind: periodKinds) {
            PeriodKindDetail periodKindDetail = periodKind.getLastDetail();
            
            String label = getBestPeriodKindDescription(periodKind, language);
            String value = periodKindDetail.getPeriodKindName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultPeriodKindChoice == null? false: defaultPeriodKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && periodKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new PeriodKindChoicesBean(labels, values, defaultValue);
    }
    
    public PeriodKindTransfer getPeriodKindTransfer(UserVisit userVisit, PeriodKind periodKind) {
        return getPeriodTransferCaches(userVisit).getPeriodKindTransferCache().getPeriodKindTransfer(periodKind);
    }
    
    public List<PeriodKindTransfer> getPeriodKindTransfers(UserVisit userVisit) {
        List<PeriodKind> periodKinds = getPeriodKinds();
        List<PeriodKindTransfer> periodKindTransfers = new ArrayList<>(periodKinds.size());
        PeriodKindTransferCache periodKindTransferCache = getPeriodTransferCaches(userVisit).getPeriodKindTransferCache();
        
        periodKinds.stream().forEach((periodKind) -> {
            periodKindTransfers.add(periodKindTransferCache.getPeriodKindTransfer(periodKind));
        });
        
        return periodKindTransfers;
    }
    
    private void updatePeriodKindFromValue(PeriodKindDetailValue periodKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        PeriodKind periodKind = PeriodKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, periodKindDetailValue.getPeriodKindPK());
        PeriodKindDetail periodKindDetail = periodKind.getActiveDetailForUpdate();
        
        periodKindDetail.setThruTime(session.START_TIME_LONG);
        periodKindDetail.store();
        
        PeriodKindPK periodKindPK = periodKindDetail.getPeriodKindPK();
        String periodKindName = periodKindDetailValue.getPeriodKindName();
        WorkflowEntrancePK workflowEntrancePK = periodKindDetailValue.getWorkflowEntrancePK();
        Boolean isDefault = periodKindDetailValue.getIsDefault();
        Integer sortOrder = periodKindDetailValue.getSortOrder();
        
        if(checkDefault) {
            PeriodKind defaultPeriodKind = getDefaultPeriodKind();
            boolean defaultFound = defaultPeriodKind != null && !defaultPeriodKind.equals(periodKind);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                PeriodKindDetailValue defaultPeriodKindDetailValue = getDefaultPeriodKindDetailValueForUpdate();
                
                defaultPeriodKindDetailValue.setIsDefault(Boolean.FALSE);
                updatePeriodKindFromValue(defaultPeriodKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }
        
        periodKindDetail = PeriodKindDetailFactory.getInstance().create(periodKindPK, periodKindName, workflowEntrancePK, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        periodKind.setActiveDetail(periodKindDetail);
        periodKind.setLastDetail(periodKindDetail);
        periodKind.store();
        
        sendEventUsingNames(periodKindPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }
    
    public void updatePeriodKindFromValue(PeriodKindDetailValue periodKindDetailValue, BasePK updatedBy) {
        updatePeriodKindFromValue(periodKindDetailValue, true, updatedBy);
    }
    
    public void deletePeriodKind(PeriodKind periodKind, BasePK deletedBy) {
        deletePeriodTypesByPeriodKind(periodKind, deletedBy);
        deletePeriodKindDescriptionsByPeriodKind(periodKind, deletedBy);
        
        PeriodKindDetail periodKindDetail = periodKind.getLastDetailForUpdate();
        periodKindDetail.setThruTime(session.START_TIME_LONG);
        periodKind.setActiveDetail(null);
        periodKind.store();
        
        // Check for default, and pick one if necessary
        PeriodKind defaultPeriodKind = getDefaultPeriodKind();
        if(defaultPeriodKind == null) {
            List<PeriodKind> periodKinds = getPeriodKindsForUpdate();
            
            if(!periodKinds.isEmpty()) {
                Iterator<PeriodKind> iter = periodKinds.iterator();
                if(iter.hasNext()) {
                    defaultPeriodKind = iter.next();
                }
                PeriodKindDetailValue periodKindDetailValue = defaultPeriodKind.getLastDetailForUpdate().getPeriodKindDetailValue().clone();
                
                periodKindDetailValue.setIsDefault(Boolean.TRUE);
                updatePeriodKindFromValue(periodKindDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(periodKind.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Period Kind Descriptions
    // --------------------------------------------------------------------------------
    
    public PeriodKindDescription createPeriodKindDescription(PeriodKind periodKind, Language language, String description,
            BasePK createdBy) {
        PeriodKindDescription periodKindDescription = PeriodKindDescriptionFactory.getInstance().create(periodKind,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(periodKind.getPrimaryKey(), EventTypes.MODIFY.name(), periodKindDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return periodKindDescription;
    }
    
    private PeriodKindDescription getPeriodKindDescription(PeriodKind periodKind, Language language, EntityPermission entityPermission) {
        PeriodKindDescription periodKindDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periodkinddescriptions " +
                        "WHERE prdkd_prdk_periodkindid = ? AND prdkd_lang_languageid = ? AND prdkd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periodkinddescriptions " +
                        "WHERE prdkd_prdk_periodkindid = ? AND prdkd_lang_languageid = ? AND prdkd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodKindDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, periodKind.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            periodKindDescription = PeriodKindDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periodKindDescription;
    }
    
    public PeriodKindDescription getPeriodKindDescription(PeriodKind periodKind, Language language) {
        return getPeriodKindDescription(periodKind, language, EntityPermission.READ_ONLY);
    }
    
    public PeriodKindDescription getPeriodKindDescriptionForUpdate(PeriodKind periodKind, Language language) {
        return getPeriodKindDescription(periodKind, language, EntityPermission.READ_WRITE);
    }
    
    public PeriodKindDescriptionValue getPeriodKindDescriptionValue(PeriodKindDescription periodKindDescription) {
        return periodKindDescription == null? null: periodKindDescription.getPeriodKindDescriptionValue().clone();
    }
    
    public PeriodKindDescriptionValue getPeriodKindDescriptionValueForUpdate(PeriodKind periodKind, Language language) {
        return getPeriodKindDescriptionValue(getPeriodKindDescriptionForUpdate(periodKind, language));
    }
    
    private List<PeriodKindDescription> getPeriodKindDescriptionsByPeriodKind(PeriodKind periodKind, EntityPermission entityPermission) {
        List<PeriodKindDescription> periodKindDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periodkinddescriptions, languages " +
                        "WHERE prdkd_prdk_periodkindid = ? AND prdkd_thrutime = ? AND prdkd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periodkinddescriptions " +
                        "WHERE prdkd_prdk_periodkindid = ? AND prdkd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodKindDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, periodKind.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            periodKindDescriptions = PeriodKindDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periodKindDescriptions;
    }
    
    public List<PeriodKindDescription> getPeriodKindDescriptionsByPeriodKind(PeriodKind periodKind) {
        return getPeriodKindDescriptionsByPeriodKind(periodKind, EntityPermission.READ_ONLY);
    }
    
    public List<PeriodKindDescription> getPeriodKindDescriptionsByPeriodKindForUpdate(PeriodKind periodKind) {
        return getPeriodKindDescriptionsByPeriodKind(periodKind, EntityPermission.READ_WRITE);
    }
    
    public String getBestPeriodKindDescription(PeriodKind periodKind, Language language) {
        String description;
        PeriodKindDescription periodKindDescription = getPeriodKindDescription(periodKind, language);
        
        if(periodKindDescription == null && !language.getIsDefault()) {
            periodKindDescription = getPeriodKindDescription(periodKind, getPartyControl().getDefaultLanguage());
        }
        
        if(periodKindDescription == null) {
            description = periodKind.getLastDetail().getPeriodKindName();
        } else {
            description = periodKindDescription.getDescription();
        }
        
        return description;
    }
    
    public PeriodKindDescriptionTransfer getPeriodKindDescriptionTransfer(UserVisit userVisit, PeriodKindDescription periodKindDescription) {
        return getPeriodTransferCaches(userVisit).getPeriodKindDescriptionTransferCache().getPeriodKindDescriptionTransfer(periodKindDescription);
    }
    
    public List<PeriodKindDescriptionTransfer> getPeriodKindDescriptionTransfersByPeriodKind(UserVisit userVisit, PeriodKind periodKind) {
        List<PeriodKindDescription> periodKindDescriptions = getPeriodKindDescriptionsByPeriodKind(periodKind);
        List<PeriodKindDescriptionTransfer> periodKindDescriptionTransfers = new ArrayList<>(periodKindDescriptions.size());
        
        periodKindDescriptions.stream().forEach((periodKindDescription) -> {
            periodKindDescriptionTransfers.add(getPeriodTransferCaches(userVisit).getPeriodKindDescriptionTransferCache().getPeriodKindDescriptionTransfer(periodKindDescription));
        });
        
        return periodKindDescriptionTransfers;
    }
    
    public void updatePeriodKindDescriptionFromValue(PeriodKindDescriptionValue periodKindDescriptionValue, BasePK updatedBy) {
        if(periodKindDescriptionValue.hasBeenModified()) {
            PeriodKindDescription periodKindDescription = PeriodKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     periodKindDescriptionValue.getPrimaryKey());
            
            periodKindDescription.setThruTime(session.START_TIME_LONG);
            periodKindDescription.store();
            
            PeriodKind periodKind = periodKindDescription.getPeriodKind();
            Language language = periodKindDescription.getLanguage();
            String description = periodKindDescriptionValue.getDescription();
            
            periodKindDescription = PeriodKindDescriptionFactory.getInstance().create(periodKind, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(periodKind.getPrimaryKey(), EventTypes.MODIFY.name(), periodKindDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePeriodKindDescription(PeriodKindDescription periodKindDescription, BasePK deletedBy) {
        periodKindDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(periodKindDescription.getPeriodKindPK(), EventTypes.MODIFY.name(), periodKindDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deletePeriodKindDescriptionsByPeriodKind(PeriodKind periodKind, BasePK deletedBy) {
        List<PeriodKindDescription> periodKindDescriptions = getPeriodKindDescriptionsByPeriodKindForUpdate(periodKind);
        
        periodKindDescriptions.stream().forEach((periodKindDescription) -> {
            deletePeriodKindDescription(periodKindDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Period Types
    // --------------------------------------------------------------------------------
    
    public PeriodType createPeriodType(PeriodKind periodKind, String periodTypeName, PeriodType parentPeriodType, WorkflowEntrance workflowEntrance, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        PeriodType defaultPeriodType = getDefaultPeriodType(periodKind);
        boolean defaultFound = defaultPeriodType != null;
        
        if(defaultFound && isDefault) {
            PeriodTypeDetailValue defaultPeriodTypeDetailValue = getDefaultPeriodTypeDetailValueForUpdate(periodKind);
            
            defaultPeriodTypeDetailValue.setIsDefault(Boolean.FALSE);
            updatePeriodTypeFromValue(defaultPeriodTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        PeriodType periodType = PeriodTypeFactory.getInstance().create();
        PeriodTypeDetail periodTypeDetail = PeriodTypeDetailFactory.getInstance().create(periodType, periodKind, periodTypeName, parentPeriodType, workflowEntrance, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        periodType = PeriodTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                periodType.getPrimaryKey());
        periodType.setActiveDetail(periodTypeDetail);
        periodType.setLastDetail(periodTypeDetail);
        periodType.store();
        
        sendEventUsingNames(periodType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return periodType;
    }
    
    private List<PeriodType> getPeriodTypes(PeriodKind periodKind, EntityPermission entityPermission) {
        List<PeriodType> periodTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periodtypes, periodtypedetails " +
                        "WHERE prdt_activedetailid = prdtdt_periodtypedetailid AND prdtdt_prdk_periodkindid = ? " +
                        "ORDER BY prdtdt_sortorder, prdtdt_periodtypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periodtypes, periodtypedetails " +
                        "WHERE prdt_activedetailid = prdtdt_periodtypedetailid AND prdtdt_prdk_periodkindid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, periodKind.getPrimaryKey().getEntityId());
            
            periodTypes = PeriodTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periodTypes;
    }
    
    public List<PeriodType> getPeriodTypes(PeriodKind periodKind) {
        return getPeriodTypes(periodKind, EntityPermission.READ_ONLY);
    }
    
    public List<PeriodType> getPeriodTypesForUpdate(PeriodKind periodKind) {
        return getPeriodTypes(periodKind, EntityPermission.READ_WRITE);
    }
    
    private PeriodType getDefaultPeriodType(PeriodKind periodKind, EntityPermission entityPermission) {
        PeriodType periodType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periodtypes, periodtypedetails " +
                        "WHERE prdt_activedetailid = prdtdt_periodtypedetailid " +
                        "AND prdtdt_prdk_periodkindid = ? AND prdtdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periodtypes, periodtypedetails " +
                        "WHERE prdt_activedetailid = prdtdt_periodtypedetailid " +
                        "AND prdtdt_prdk_periodkindid = ? AND prdtdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, periodKind.getPrimaryKey().getEntityId());
            
            periodType = PeriodTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periodType;
    }
    
    public PeriodType getDefaultPeriodType(PeriodKind periodKind) {
        return getDefaultPeriodType(periodKind, EntityPermission.READ_ONLY);
    }
    
    public PeriodType getDefaultPeriodTypeForUpdate(PeriodKind periodKind) {
        return getDefaultPeriodType(periodKind, EntityPermission.READ_WRITE);
    }
    
    public PeriodTypeDetailValue getDefaultPeriodTypeDetailValueForUpdate(PeriodKind periodKind) {
        return getDefaultPeriodTypeForUpdate(periodKind).getLastDetailForUpdate().getPeriodTypeDetailValue().clone();
    }
    
    private PeriodType getPeriodTypeByName(PeriodKind periodKind, String periodTypeName, EntityPermission entityPermission) {
        PeriodType periodType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periodtypes, periodtypedetails " +
                        "WHERE prdt_activedetailid = prdtdt_periodtypedetailid " +
                        "AND prdtdt_prdk_periodkindid = ? AND prdtdt_periodtypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periodtypes, periodtypedetails " +
                        "WHERE prdt_activedetailid = prdtdt_periodtypedetailid " +
                        "AND prdtdt_prdk_periodkindid = ? AND prdtdt_periodtypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, periodKind.getPrimaryKey().getEntityId());
            ps.setString(2, periodTypeName);
            
            periodType = PeriodTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periodType;
    }
    
    public PeriodType getPeriodTypeByName(PeriodKind periodKind, String periodTypeName) {
        return getPeriodTypeByName(periodKind, periodTypeName, EntityPermission.READ_ONLY);
    }
    
    public PeriodType getPeriodTypeByNameForUpdate(PeriodKind periodKind, String periodTypeName) {
        return getPeriodTypeByName(periodKind, periodTypeName, EntityPermission.READ_WRITE);
    }
    
    public PeriodTypeDetailValue getPeriodTypeDetailValueForUpdate(PeriodType periodType) {
        return periodType == null? null: periodType.getLastDetailForUpdate().getPeriodTypeDetailValue().clone();
    }
    
    public PeriodTypeDetailValue getPeriodTypeDetailValueByNameForUpdate(PeriodKind periodKind, String periodTypeName) {
        return getPeriodTypeDetailValueForUpdate(getPeriodTypeByNameForUpdate(periodKind, periodTypeName));
    }
    
    public PeriodTypeChoicesBean getPeriodTypeChoices(String defaultPeriodTypeChoice, Language language,
            boolean allowNullChoice, PeriodKind periodKind) {
        List<PeriodType> periodTypes = getPeriodTypes(periodKind);
        int size = periodTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultPeriodTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(PeriodType periodType: periodTypes) {
            PeriodTypeDetail periodTypeDetail = periodType.getLastDetail();
            String label = getBestPeriodTypeDescription(periodType, language);
            String value = periodTypeDetail.getPeriodTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultPeriodTypeChoice == null? false: defaultPeriodTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && periodTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new PeriodTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentPeriodTypeSafe(PeriodType periodType, PeriodType parentPeriodType) {
        boolean safe = true;
        
        if(parentPeriodType != null) {
            Set<PeriodType> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(periodType);
            do {
                if(parentItemPurchasingCategories.contains(parentPeriodType)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentPeriodType);
                parentPeriodType = parentPeriodType.getLastDetail().getParentPeriodType();
            } while(parentPeriodType != null);
        }
        
        return safe;
    }
    
    public PeriodTypeTransfer getPeriodTypeTransfer(UserVisit userVisit, PeriodType periodType) {
        return getPeriodTransferCaches(userVisit).getPeriodTypeTransferCache().getPeriodTypeTransfer(periodType);
    }
    
    public List<PeriodTypeTransfer> getPeriodTypeTransfersByPeriodKind(UserVisit userVisit, PeriodKind periodKind) {
        List<PeriodType> periodTypes = getPeriodTypes(periodKind);
        List<PeriodTypeTransfer> periodTypeTransfers = new ArrayList<>(periodTypes.size());
        PeriodTypeTransferCache periodTypeTransferCache = getPeriodTransferCaches(userVisit).getPeriodTypeTransferCache();
        
        periodTypes.stream().forEach((periodType) -> {
            periodTypeTransfers.add(periodTypeTransferCache.getPeriodTypeTransfer(periodType));
        });
        
        return periodTypeTransfers;
    }
    
    private void updatePeriodTypeFromValue(PeriodTypeDetailValue periodTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(periodTypeDetailValue.hasBeenModified()) {
            PeriodType periodType = PeriodTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, periodTypeDetailValue.getPeriodTypePK());
            PeriodTypeDetail periodTypeDetail = periodType.getActiveDetailForUpdate();
            
            periodTypeDetail.setThruTime(session.START_TIME_LONG);
            periodTypeDetail.store();
            
            PeriodTypePK periodTypePK = periodTypeDetail.getPeriodTypePK();
            PeriodKind periodKind = periodTypeDetail.getPeriodKind();
            PeriodKindPK periodKindPK = periodKind.getPrimaryKey();
            String periodTypeName = periodTypeDetailValue.getPeriodTypeName();
            PeriodTypePK parentPeriodTypePK = periodTypeDetailValue.getPeriodTypePK();
            WorkflowEntrancePK workflowEntrancePK = periodTypeDetailValue.getWorkflowEntrancePK();
            Boolean isDefault = periodTypeDetailValue.getIsDefault();
            Integer sortOrder = periodTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                PeriodType defaultPeriodType = getDefaultPeriodType(periodKind);
                boolean defaultFound = defaultPeriodType != null && !defaultPeriodType.equals(periodType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PeriodTypeDetailValue defaultPeriodTypeDetailValue = getDefaultPeriodTypeDetailValueForUpdate(periodKind);
                    
                    defaultPeriodTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updatePeriodTypeFromValue(defaultPeriodTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            periodTypeDetail = PeriodTypeDetailFactory.getInstance().create(periodTypePK, periodKindPK, periodTypeName, parentPeriodTypePK, workflowEntrancePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            periodType.setActiveDetail(periodTypeDetail);
            periodType.setLastDetail(periodTypeDetail);
            
            sendEventUsingNames(periodTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updatePeriodTypeFromValue(PeriodTypeDetailValue periodTypeDetailValue, BasePK updatedBy) {
        updatePeriodTypeFromValue(periodTypeDetailValue, true, updatedBy);
    }
    
    public void deletePeriodType(PeriodType periodType, BasePK deletedBy) {
        deletePeriodsByPeriodType(periodType, deletedBy);
        deletePeriodTypeDescriptionsByPeriodType(periodType, deletedBy);
        
        PeriodTypeDetail periodTypeDetail = periodType.getLastDetailForUpdate();
        periodTypeDetail.setThruTime(session.START_TIME_LONG);
        periodType.setActiveDetail(null);
        periodType.store();
        
        // Check for default, and pick one if necessary
        PeriodKind periodKind = periodTypeDetail.getPeriodKind();
        PeriodType defaultPeriodType = getDefaultPeriodType(periodKind);
        if(defaultPeriodType == null) {
            List<PeriodType> periodTypes = getPeriodTypesForUpdate(periodKind);
            
            if(!periodTypes.isEmpty()) {
                Iterator<PeriodType> iter = periodTypes.iterator();
                if(iter.hasNext()) {
                    defaultPeriodType = iter.next();
                }
                PeriodTypeDetailValue periodTypeDetailValue = defaultPeriodType.getLastDetailForUpdate().getPeriodTypeDetailValue().clone();
                
                periodTypeDetailValue.setIsDefault(Boolean.TRUE);
                updatePeriodTypeFromValue(periodTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(periodType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deletePeriodTypesByPeriodKind(PeriodKind periodKind, BasePK deletedBy) {
        List<PeriodType> periodTypes = getPeriodTypesForUpdate(periodKind);
        
        periodTypes.stream().forEach((periodType) -> {
            deletePeriodType(periodType, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Period Type Descriptions
    // --------------------------------------------------------------------------------
    
    public PeriodTypeDescription createPeriodTypeDescription(PeriodType periodType, Language language, String description,
            BasePK createdBy) {
        PeriodTypeDescription periodTypeDescription = PeriodTypeDescriptionFactory.getInstance().create(periodType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(periodType.getPrimaryKey(), EventTypes.MODIFY.name(), periodTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return periodTypeDescription;
    }
    
    private PeriodTypeDescription getPeriodTypeDescription(PeriodType periodType, Language language, EntityPermission entityPermission) {
        PeriodTypeDescription periodTypeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periodtypedescriptions " +
                        "WHERE prdtd_prdt_periodtypeid = ? AND prdtd_lang_languageid = ? AND prdtd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periodtypedescriptions " +
                        "WHERE prdtd_prdt_periodtypeid = ? AND prdtd_lang_languageid = ? AND prdtd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, periodType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            periodTypeDescription = PeriodTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periodTypeDescription;
    }
    
    public PeriodTypeDescription getPeriodTypeDescription(PeriodType periodType, Language language) {
        return getPeriodTypeDescription(periodType, language, EntityPermission.READ_ONLY);
    }
    
    public PeriodTypeDescription getPeriodTypeDescriptionForUpdate(PeriodType periodType, Language language) {
        return getPeriodTypeDescription(periodType, language, EntityPermission.READ_WRITE);
    }
    
    public PeriodTypeDescriptionValue getPeriodTypeDescriptionValue(PeriodTypeDescription periodTypeDescription) {
        return periodTypeDescription == null? null: periodTypeDescription.getPeriodTypeDescriptionValue().clone();
    }
    
    public PeriodTypeDescriptionValue getPeriodTypeDescriptionValueForUpdate(PeriodType periodType, Language language) {
        return getPeriodTypeDescriptionValue(getPeriodTypeDescriptionForUpdate(periodType, language));
    }
    
    private List<PeriodTypeDescription> getPeriodTypeDescriptionsByPeriodType(PeriodType periodType, EntityPermission entityPermission) {
        List<PeriodTypeDescription> periodTypeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periodtypedescriptions, languages " +
                        "WHERE prdtd_prdt_periodtypeid = ? AND prdtd_thrutime = ? AND prdtd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periodtypedescriptions " +
                        "WHERE prdtd_prdt_periodtypeid = ? AND prdtd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, periodType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            periodTypeDescriptions = PeriodTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periodTypeDescriptions;
    }
    
    public List<PeriodTypeDescription> getPeriodTypeDescriptionsByPeriodType(PeriodType periodType) {
        return getPeriodTypeDescriptionsByPeriodType(periodType, EntityPermission.READ_ONLY);
    }
    
    public List<PeriodTypeDescription> getPeriodTypeDescriptionsByPeriodTypeForUpdate(PeriodType periodType) {
        return getPeriodTypeDescriptionsByPeriodType(periodType, EntityPermission.READ_WRITE);
    }
    
    public String getBestPeriodTypeDescription(PeriodType periodType, Language language) {
        String description;
        PeriodTypeDescription periodTypeDescription = getPeriodTypeDescription(periodType, language);
        
        if(periodTypeDescription == null && !language.getIsDefault()) {
            periodTypeDescription = getPeriodTypeDescription(periodType, getPartyControl().getDefaultLanguage());
        }
        
        if(periodTypeDescription == null) {
            description = periodType.getLastDetail().getPeriodTypeName();
        } else {
            description = periodTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public PeriodTypeDescriptionTransfer getPeriodTypeDescriptionTransfer(UserVisit userVisit, PeriodTypeDescription periodTypeDescription) {
        return getPeriodTransferCaches(userVisit).getPeriodTypeDescriptionTransferCache().getPeriodTypeDescriptionTransfer(periodTypeDescription);
    }
    
    public List<PeriodTypeDescriptionTransfer> getPeriodTypeDescriptionTransfersByPeriodType(UserVisit userVisit, PeriodType periodType) {
        List<PeriodTypeDescription> periodTypeDescriptions = getPeriodTypeDescriptionsByPeriodType(periodType);
        List<PeriodTypeDescriptionTransfer> periodTypeDescriptionTransfers = new ArrayList<>(periodTypeDescriptions.size());
        
        periodTypeDescriptions.stream().forEach((periodTypeDescription) -> {
            periodTypeDescriptionTransfers.add(getPeriodTransferCaches(userVisit).getPeriodTypeDescriptionTransferCache().getPeriodTypeDescriptionTransfer(periodTypeDescription));
        });
        
        return periodTypeDescriptionTransfers;
    }
    
    public void updatePeriodTypeDescriptionFromValue(PeriodTypeDescriptionValue periodTypeDescriptionValue, BasePK updatedBy) {
        if(periodTypeDescriptionValue.hasBeenModified()) {
            PeriodTypeDescription periodTypeDescription = PeriodTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     periodTypeDescriptionValue.getPrimaryKey());
            
            periodTypeDescription.setThruTime(session.START_TIME_LONG);
            periodTypeDescription.store();
            
            PeriodType periodType = periodTypeDescription.getPeriodType();
            Language language = periodTypeDescription.getLanguage();
            String description = periodTypeDescriptionValue.getDescription();
            
            periodTypeDescription = PeriodTypeDescriptionFactory.getInstance().create(periodType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(periodType.getPrimaryKey(), EventTypes.MODIFY.name(), periodTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePeriodTypeDescription(PeriodTypeDescription periodTypeDescription, BasePK deletedBy) {
        periodTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(periodTypeDescription.getPeriodTypePK(), EventTypes.MODIFY.name(), periodTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePeriodTypeDescriptionsByPeriodType(PeriodType periodType, BasePK deletedBy) {
        List<PeriodTypeDescription> periodTypeDescriptions = getPeriodTypeDescriptionsByPeriodTypeForUpdate(periodType);
        
        periodTypeDescriptions.stream().forEach((periodTypeDescription) -> {
            deletePeriodTypeDescription(periodTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Periods
    // --------------------------------------------------------------------------------
    
    public Period createPeriod(PeriodKind periodKind, String periodName, Period parentPeriod, PeriodType periodType, Long startTime, Long endTime, BasePK createdBy) {
        Period period = PeriodFactory.getInstance().create();
        PeriodDetail periodDetail = PeriodDetailFactory.getInstance().create(period, periodKind, periodName, parentPeriod, periodType, startTime,
                endTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        period = PeriodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                period.getPrimaryKey());
        period.setActiveDetail(periodDetail);
        period.setLastDetail(periodDetail);
        period.store();
        
        sendEventUsingNames(period.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return period;
    }
    
    private List<Period> getContainingPeriodsUsingNames(String periodKindName, Long time, EntityPermission entityPermission) {
        List<Period> periods = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periodkinds, periodkinddetails, periods, perioddetails " +
                        "WHERE prdk_activedetailid = prdkdt_periodkinddetailid AND prdkdt_periodkindname = ? " +
                        "AND prd_activedetailid = prddt_perioddetailid AND prddt_prdk_periodkindid = prdk_periodkindid " +
                        "AND prddt_starttime <= ? AND prddt_endtime >= ? " +
                        "ORDER BY prddt_periodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periodkinds, periodkinddetails, periods, perioddetails " +
                        "WHERE prdk_activedetailid = prdkdt_periodkinddetailid AND prdkdt_periodkindname = ? " +
                        "AND prd_activedetailid = prddt_perioddetailid AND prddt_prdk_periodkindid = prdk_periodkindid " +
                        "AND prddt_starttime <= ? AND prddt_endtime >= ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, periodKindName);
            ps.setLong(2, time);
            ps.setLong(3, time);
            
            periods = PeriodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periods;
    }
    
    public List<Period> getContainingPeriodsUsingNames(String periodKindName, Long time) {
        return getContainingPeriodsUsingNames(periodKindName, time, EntityPermission.READ_ONLY);
    }
    
    public List<Period> getContainingPeriodsUsingNamesForUpdate(String periodKindName, Long time) {
        return getContainingPeriodsUsingNames(periodKindName, time, EntityPermission.READ_WRITE);
    }
    
    private List<Period> getPeriods(PeriodType periodType, EntityPermission entityPermission) {
        List<Period> periods = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periods, perioddetails " +
                        "WHERE prd_activedetailid = prddt_perioddetailid AND prddt_prdt_periodtypeid = ? " +
                        "ORDER BY prddt_sortorder, prddt_periodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periods, perioddetails " +
                        "WHERE prd_activedetailid = prddt_perioddetailid AND prddt_prdt_periodtypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, periodType.getPrimaryKey().getEntityId());
            
            periods = PeriodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periods;
    }
    
    public List<Period> getPeriods(PeriodType periodType) {
        return getPeriods(periodType, EntityPermission.READ_ONLY);
    }
    
    public List<Period> getPeriodsForUpdate(PeriodType periodType) {
        return getPeriods(periodType, EntityPermission.READ_WRITE);
    }
    
    private List<Period> getPeriodsByParentPeriod(Period parentPeriod, EntityPermission entityPermission) {
        List<Period> periods = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periods, perioddetails " +
                        "WHERE prd_activedetailid = prddt_perioddetailid AND prddt_parentperiodid = ? " +
                        "ORDER BY prddt_sortorder, prddt_periodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periods, perioddetails " +
                        "WHERE prd_activedetailid = prddt_perioddetailid AND prddt_parentperiodid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, parentPeriod.getPrimaryKey().getEntityId());
            
            periods = PeriodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periods;
    }
    
    public List<Period> getPeriodsByParentPeriod(Period parentPeriod) {
        return getPeriodsByParentPeriod(parentPeriod, EntityPermission.READ_ONLY);
    }
    
    public List<Period> getPeriodsByParentPeriodForUpdate(Period parentPeriod) {
        return getPeriodsByParentPeriod(parentPeriod, EntityPermission.READ_WRITE);
    }
    
    public List<Period> getPeriodsContainedByPeriod(Period period) {
        List<Period> newPeriods = getPeriodsByParentPeriod(period);
        List<Period> periods = new ArrayList<>(newPeriods);

        newPeriods.stream().forEach((newPeriod) -> {
            periods.addAll(getPeriodsContainedByPeriod(newPeriod));
        });
        
        return periods;
    }
    
    public Period getPeriodByName(PeriodKind periodKind, String periodName, EntityPermission entityPermission) {
        Period period = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM periods, perioddetails " +
                        "WHERE prd_activedetailid = prddt_perioddetailid " +
                        "AND prddt_prdk_periodkindid = ? AND prddt_periodname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM periods, perioddetails " +
                        "WHERE prd_activedetailid = prddt_perioddetailid " +
                        "AND prddt_prdk_periodkindid = ? AND prddt_periodname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, periodKind.getPrimaryKey().getEntityId());
            ps.setString(2, periodName);
            
            period = PeriodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return period;
    }
    
    public Period getPeriodByName(PeriodKind periodKind, String periodName) {
        return getPeriodByName(periodKind, periodName, EntityPermission.READ_ONLY);
    }
    
    public Period getPeriodByNameForUpdate(PeriodKind periodKind, String periodName) {
        return getPeriodByName(periodKind, periodName, EntityPermission.READ_WRITE);
    }
    
    public PeriodDetailValue getPeriodDetailValueForUpdate(Period period) {
        return period == null? null: period.getLastDetailForUpdate().getPeriodDetailValue().clone();
    }
    
    public PeriodDetailValue getPeriodDetailValueByNameForUpdate(PeriodKind periodKind, String periodName) {
        return getPeriodDetailValueForUpdate(getPeriodByNameForUpdate(periodKind, periodName));
    }
    
    public boolean isParentPeriodSafe(Period period, Period parentPeriod) {
        boolean safe = true;
        
        if(parentPeriod != null) {
            Set<Period> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(period);
            do {
                if(parentItemPurchasingCategories.contains(parentPeriod)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentPeriod);
                parentPeriod = parentPeriod.getLastDetail().getParentPeriod();
            } while(parentPeriod != null);
        }
        
        return safe;
    }
    
    public PeriodTransfer getPeriodTransfer(UserVisit userVisit, Period period) {
        return getPeriodTransferCaches(userVisit).getPeriodTransferCache().getPeriodTransfer(period);
    }
    
    public List<PeriodTransfer> getPeriodTransfersByPeriodType(UserVisit userVisit, PeriodType periodType) {
        List<Period> periods = getPeriods(periodType);
        List<PeriodTransfer> periodTransfers = new ArrayList<>(periods.size());
        PeriodTransferCache periodTransferCache = getPeriodTransferCaches(userVisit).getPeriodTransferCache();
        
        periods.stream().forEach((period) -> {
            periodTransfers.add(periodTransferCache.getPeriodTransfer(period));
        });
        
        return periodTransfers;
    }
    
    public void updatePeriodFromValue(PeriodDetailValue periodDetailValue, BasePK updatedBy) {
        if(periodDetailValue.hasBeenModified()) {
            Period period = PeriodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, periodDetailValue.getPeriodPK());
            PeriodDetail periodDetail = period.getActiveDetailForUpdate();
            
            periodDetail.setThruTime(session.START_TIME_LONG);
            periodDetail.store();
            
            PeriodPK periodPK = periodDetail.getPeriodPK(); // Not updated
            PeriodKindPK periodKindPK = periodDetail.getPeriodKindPK(); // Not updated
            String periodName = periodDetailValue.getPeriodName();
            PeriodPK parentPeriodPK = periodDetailValue.getPeriodPK();
            PeriodTypePK periodTypePK = periodDetailValue.getPeriodTypePK();
            Long startTime = periodDetailValue.getStartTime();
            Long endTime = periodDetailValue.getEndTime();
            
            periodDetail = PeriodDetailFactory.getInstance().create(periodPK, periodKindPK, periodName, parentPeriodPK, periodTypePK, startTime,
                    endTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            period.setActiveDetail(periodDetail);
            period.setLastDetail(periodDetail);
            
            sendEventUsingNames(periodPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deletePeriod(Period period, BasePK deletedBy) {
        deletePeriodsByParentPeriod(period, deletedBy);
        deletePeriodDescriptionsByPeriod(period, deletedBy);
        
        PeriodDetail periodDetail = period.getLastDetailForUpdate();
        periodDetail.setThruTime(session.START_TIME_LONG);
        period.setActiveDetail(null);
        period.store();
        
        sendEventUsingNames(period.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deletePeriods(List<Period> periods, BasePK deletedBy) {
        periods.stream().forEach((period) -> {
            deletePeriod(period, deletedBy);
        });
    }

    private void deletePeriodsByParentPeriod(Period parentPeriod, BasePK deletedBy) {
        deletePeriods(getPeriodsByParentPeriodForUpdate(parentPeriod), deletedBy);
    }

    public void deletePeriodsByPeriodType(PeriodType periodType, BasePK deletedBy) {
        List<Period> periods = getPeriodsForUpdate(periodType);
        
        periods.stream().forEach((period) -> {
            deletePeriod(period, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Period Descriptions
    // --------------------------------------------------------------------------------
    
    public PeriodDescription createPeriodDescription(Period period, Language language, String description,
            BasePK createdBy) {
        PeriodDescription periodDescription = PeriodDescriptionFactory.getInstance().create(period,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(period.getPrimaryKey(), EventTypes.MODIFY.name(), periodDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return periodDescription;
    }
    
    private PeriodDescription getPeriodDescription(Period period, Language language, EntityPermission entityPermission) {
        PeriodDescription periodDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM perioddescriptions " +
                        "WHERE prdd_prd_periodid = ? AND prdd_lang_languageid = ? AND prdd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM perioddescriptions " +
                        "WHERE prdd_prd_periodid = ? AND prdd_lang_languageid = ? AND prdd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, period.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            periodDescription = PeriodDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periodDescription;
    }
    
    public PeriodDescription getPeriodDescription(Period period, Language language) {
        return getPeriodDescription(period, language, EntityPermission.READ_ONLY);
    }
    
    public PeriodDescription getPeriodDescriptionForUpdate(Period period, Language language) {
        return getPeriodDescription(period, language, EntityPermission.READ_WRITE);
    }
    
    public PeriodDescriptionValue getPeriodDescriptionValue(PeriodDescription periodDescription) {
        return periodDescription == null? null: periodDescription.getPeriodDescriptionValue().clone();
    }
    
    public PeriodDescriptionValue getPeriodDescriptionValueForUpdate(Period period, Language language) {
        return getPeriodDescriptionValue(getPeriodDescriptionForUpdate(period, language));
    }
    
    private List<PeriodDescription> getPeriodDescriptionsByPeriod(Period period, EntityPermission entityPermission) {
        List<PeriodDescription> periodDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM perioddescriptions, languages " +
                        "WHERE prdd_prd_periodid = ? AND prdd_thrutime = ? AND prdd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM perioddescriptions " +
                        "WHERE prdd_prd_periodid = ? AND prdd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PeriodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, period.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            periodDescriptions = PeriodDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return periodDescriptions;
    }
    
    public List<PeriodDescription> getPeriodDescriptionsByPeriod(Period period) {
        return getPeriodDescriptionsByPeriod(period, EntityPermission.READ_ONLY);
    }
    
    public List<PeriodDescription> getPeriodDescriptionsByPeriodForUpdate(Period period) {
        return getPeriodDescriptionsByPeriod(period, EntityPermission.READ_WRITE);
    }
    
    public String getBestPeriodDescription(Period period, Language language) {
        String description;
        PeriodDescription periodDescription = getPeriodDescription(period, language);
        
        if(periodDescription == null && !language.getIsDefault()) {
            periodDescription = getPeriodDescription(period, getPartyControl().getDefaultLanguage());
        }
        
        if(periodDescription == null) {
            description = period.getLastDetail().getPeriodName();
        } else {
            description = periodDescription.getDescription();
        }
        
        return description;
    }
    
    public PeriodDescriptionTransfer getPeriodDescriptionTransfer(UserVisit userVisit, PeriodDescription periodDescription) {
        return getPeriodTransferCaches(userVisit).getPeriodDescriptionTransferCache().getPeriodDescriptionTransfer(periodDescription);
    }
    
    public List<PeriodDescriptionTransfer> getPeriodDescriptionTransfersByPeriod(UserVisit userVisit, Period period) {
        List<PeriodDescription> periodDescriptions = getPeriodDescriptionsByPeriod(period);
        List<PeriodDescriptionTransfer> periodDescriptionTransfers = new ArrayList<>(periodDescriptions.size());
        
        periodDescriptions.stream().forEach((periodDescription) -> {
            periodDescriptionTransfers.add(getPeriodTransferCaches(userVisit).getPeriodDescriptionTransferCache().getPeriodDescriptionTransfer(periodDescription));
        });
        
        return periodDescriptionTransfers;
    }
    
    public void updatePeriodDescriptionFromValue(PeriodDescriptionValue periodDescriptionValue, BasePK updatedBy) {
        if(periodDescriptionValue.hasBeenModified()) {
            PeriodDescription periodDescription = PeriodDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     periodDescriptionValue.getPrimaryKey());
            
            periodDescription.setThruTime(session.START_TIME_LONG);
            periodDescription.store();
            
            Period period = periodDescription.getPeriod();
            Language language = periodDescription.getLanguage();
            String description = periodDescriptionValue.getDescription();
            
            periodDescription = PeriodDescriptionFactory.getInstance().create(period, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(period.getPrimaryKey(), EventTypes.MODIFY.name(), periodDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePeriodDescription(PeriodDescription periodDescription, BasePK deletedBy) {
        periodDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(periodDescription.getPeriodPK(), EventTypes.MODIFY.name(), periodDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePeriodDescriptionsByPeriod(Period period, BasePK deletedBy) {
        List<PeriodDescription> periodDescriptions = getPeriodDescriptionsByPeriodForUpdate(period);
        
        periodDescriptions.stream().forEach((periodDescription) -> {
            deletePeriodDescription(periodDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Fiscal Periods
    // --------------------------------------------------------------------------------
    
    public FiscalPeriodStatusChoicesBean getFiscalPeriodStatusChoices(String defaultFiscalPeriodStatusChoice, Language language, boolean allowNullChoice,
            Period period, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        FiscalPeriodStatusChoicesBean fiscalPeriodStatusChoicesBean = new FiscalPeriodStatusChoicesBean();
        
        if(period == null) {
            workflowControl.getWorkflowEntranceChoices(fiscalPeriodStatusChoicesBean, defaultFiscalPeriodStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(FiscalPeriodStatusConstants.Workflow_FISCAL_PERIOD_STATUS), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(period.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(FiscalPeriodStatusConstants.Workflow_FISCAL_PERIOD_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(fiscalPeriodStatusChoicesBean, defaultFiscalPeriodStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return fiscalPeriodStatusChoicesBean;
    }
    
    public void setFiscalPeriodStatus(ExecutionErrorAccumulator eea, Period period, String fiscalPeriodStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(period);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(FiscalPeriodStatusConstants.Workflow_FISCAL_PERIOD_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = fiscalPeriodStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), fiscalPeriodStatusChoice);
        
        if(workflowDestination != null || fiscalPeriodStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownFiscalPeriodStatusChoice.name(), fiscalPeriodStatusChoice);
        }
    }
    
}
