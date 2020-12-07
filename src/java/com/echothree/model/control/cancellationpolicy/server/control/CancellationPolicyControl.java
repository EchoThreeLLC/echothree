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

package com.echothree.model.control.cancellationpolicy.server.control;

import com.echothree.model.control.cancellationpolicy.common.choice.CancellationKindChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationReasonChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationTypeChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationKindDescriptionTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationKindTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyReasonTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTranslationTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonDescriptionTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonTypeTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationTypeDescriptionTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationTypeTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.PartyCancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationKindTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationPolicyReasonTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationPolicyTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationPolicyTransferCaches;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationPolicyTranslationTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationReasonTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationReasonTypeTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationTypeTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.PartyCancellationPolicyTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationKindPK;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationPolicyPK;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationReasonPK;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationTypePK;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKindDescription;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKindDetail;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyDetail;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyReason;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyTranslation;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReason;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonDescription;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonDetail;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonType;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationType;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationTypeDescription;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationTypeDetail;
import com.echothree.model.data.cancellationpolicy.server.entity.PartyCancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationKindDescriptionFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationKindDetailFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationKindFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationPolicyDetailFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationPolicyFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationPolicyReasonFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationPolicyTranslationFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationReasonDescriptionFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationReasonDetailFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationReasonFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationReasonTypeFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationTypeDescriptionFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationTypeDetailFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationTypeFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.PartyCancellationPolicyFactory;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationKindDescriptionValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationKindDetailValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationPolicyDetailValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationPolicyReasonValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationPolicyTranslationValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationReasonDescriptionValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationReasonDetailValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationReasonTypeValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationTypeDescriptionValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationTypeDetailValue;
import com.echothree.model.data.cancellationpolicy.server.value.PartyCancellationPolicyValue;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
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
import java.util.Objects;

public class CancellationPolicyControl
        extends BaseModelControl {
    
    /** Creates a new instance of CancellationPolicyControl */
    public CancellationPolicyControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Policy Transfer Caches
    // --------------------------------------------------------------------------------
    
    private CancellationPolicyTransferCaches cancellationPolicyTransferCaches;
    
    public CancellationPolicyTransferCaches getCancellationPolicyTransferCaches(UserVisit userVisit) {
        if(cancellationPolicyTransferCaches == null) {
            cancellationPolicyTransferCaches = new CancellationPolicyTransferCaches(userVisit, this);
        }
        
        return cancellationPolicyTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Party Cancellation Policies
    // --------------------------------------------------------------------------------
    
    public PartyCancellationPolicy createPartyCancellationPolicy(Party party, CancellationPolicy cancellationPolicy, BasePK createdBy) {
        PartyCancellationPolicy partyCancellationPolicy = PartyCancellationPolicyFactory.getInstance().create(party, cancellationPolicy,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyCancellationPolicy.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyCancellationPolicy;
    }
    
    public long countPartyCancellationPoliciesByParty(Party party) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partycancellationpolicies " +
                "WHERE pcnclplcy_par_partyid = ? AND pcnclplcy_thrutime = ?",
                party, Session.MAX_TIME);
    }

    public long countPartyCancellationPoliciesByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partycancellationpolicies " +
                "WHERE pcnclplcy_cnclplcy_cancellationpolicyid = ? AND pcnclplcy_thrutime = ?",
                cancellationPolicy, Session.MAX_TIME);
    }

    public long countPartyCancellationPoliciesByPartyAndCancellationPolicy(Party party, CancellationPolicy cancellationPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partycancellationpolicies " +
                "WHERE pcnclplcy_par_partyid = ? AND pcnclplcy_cnclplcy_cancellationpolicyid = ? AND pcnclplcy_thrutime = ?",
                party, cancellationPolicy, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getPartyCancellationPolicyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partycancellationpolicies " +
                "WHERE pcnclplcy_par_partyid = ? AND pcnclplcy_cnclplcy_cancellationpolicyid = ? AND pcnclplcy_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partycancellationpolicies " +
                "WHERE pcnclplcy_par_partyid = ? AND pcnclplcy_cnclplcy_cancellationpolicyid = ? AND pcnclplcy_thrutime = ? " +
                "FOR UPDATE");
        getPartyCancellationPolicyQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyCancellationPolicy getPartyCancellationPolicy(Party party, CancellationPolicy cancellationPolicy, EntityPermission entityPermission) {
        return PartyCancellationPolicyFactory.getInstance().getEntityFromQuery(entityPermission, getPartyCancellationPolicyQueries,
                party, cancellationPolicy, Session.MAX_TIME_LONG);
    }

    public PartyCancellationPolicy getPartyCancellationPolicy(Party party, CancellationPolicy cancellationPolicy) {
        return getPartyCancellationPolicy(party, cancellationPolicy, EntityPermission.READ_ONLY);
    }
    
    public PartyCancellationPolicy getPartyCancellationPolicyForUpdate(Party party, CancellationPolicy cancellationPolicy) {
        return getPartyCancellationPolicy(party, cancellationPolicy, EntityPermission.READ_WRITE);
    }
    
    public PartyCancellationPolicyValue getPartyCancellationPolicyValue(PartyCancellationPolicy partyCancellationPolicy) {
        return partyCancellationPolicy == null? null: partyCancellationPolicy.getPartyCancellationPolicyValue().clone();
    }

    private static final Map<EntityPermission, String> getPartyCancellationPoliciesByCancellationPolicyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partycancellationpolicies, cancellationpolicies, cancellationpolicydetails " +
                "WHERE pcnclplcy_cnclplcy_cancellationpolicyid = ? AND pcnclplcy_thrutime = ? " +
                "AND pcnclplcy_cnclplcy_cancellationpolicyid = cnclplcy_cancellationpolicyid AND cnclplcy_lastdetailid = cnclplcydt_cancellationpolicydetailid " +
                "ORDER BY cnclplcydt_sortorder, cnclplcydt_cancellationpolicyname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partycancellationpolicies " +
                "WHERE pcnclplcy_cnclplcy_cancellationpolicyid = ? AND pcnclplcy_thrutime = ? " +
                "FOR UPDATE");
        getPartyCancellationPoliciesByCancellationPolicyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyCancellationPolicy> getPartyCancellationPoliciesByCancellationPolicy(CancellationPolicy cancellationPolicy, EntityPermission entityPermission) {
        return PartyCancellationPolicyFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyCancellationPoliciesByCancellationPolicyQueries,
                cancellationPolicy, Session.MAX_TIME_LONG);
    }

    public List<PartyCancellationPolicy> getPartyCancellationPoliciesByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return getPartyCancellationPoliciesByCancellationPolicy(cancellationPolicy, EntityPermission.READ_ONLY);
    }

    public List<PartyCancellationPolicy> getPartyCancellationPoliciesByCancellationPolicyForUpdate(CancellationPolicy cancellationPolicy) {
        return getPartyCancellationPoliciesByCancellationPolicy(cancellationPolicy, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyCancellationPoliciesByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partycancellationpolicies, parties, partydetails " +
                "WHERE pcnclplcy_par_partyid = ? AND pcnclplcy_thrutime = ? " +
                "AND pcnclplcy_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                "ORDER BY pardt_partyname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partycancellationpolicies " +
                "WHERE pcnclplcy_par_partyid = ? AND pcnclplcy_thrutime = ? " +
                "FOR UPDATE");
        getPartyCancellationPoliciesByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyCancellationPolicy> getPartyCancellationPoliciesByParty(Party party, EntityPermission entityPermission) {
        return PartyCancellationPolicyFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyCancellationPoliciesByPartyQueries,
                party, Session.MAX_TIME_LONG);
    }

    public List<PartyCancellationPolicy> getPartyCancellationPoliciesByParty(Party party) {
        return getPartyCancellationPoliciesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyCancellationPolicy> getPartyCancellationPoliciesByPartyForUpdate(Party party) {
        return getPartyCancellationPoliciesByParty(party, EntityPermission.READ_WRITE);
    }

    public PartyCancellationPolicyTransfer getPartyCancellationPolicyTransfer(UserVisit userVisit, PartyCancellationPolicy partyCancellationPolicy) {
        return getCancellationPolicyTransferCaches(userVisit).getPartyCancellationPolicyTransferCache().getPartyCancellationPolicyTransfer(partyCancellationPolicy);
    }
    
    public List<PartyCancellationPolicyTransfer> getPartyCancellationPolicyTransfers(UserVisit userVisit, List<PartyCancellationPolicy> cancellationPolicies) {
        List<PartyCancellationPolicyTransfer> cancellationPolicyTransfers = new ArrayList<>(cancellationPolicies.size());
        PartyCancellationPolicyTransferCache cancellationPolicyTransferCache = getCancellationPolicyTransferCaches(userVisit).getPartyCancellationPolicyTransferCache();

        cancellationPolicies.forEach((cancellationPolicy) ->
                cancellationPolicyTransfers.add(cancellationPolicyTransferCache.getPartyCancellationPolicyTransfer(cancellationPolicy))
        );

        return cancellationPolicyTransfers;
    }

    public List<PartyCancellationPolicyTransfer> getPartyCancellationPolicyTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyCancellationPolicyTransfers(userVisit, getPartyCancellationPoliciesByParty(party));
    }

    public List<PartyCancellationPolicyTransfer> getPartyCancellationPolicyTransfersByCancellationPolicy(UserVisit userVisit, CancellationPolicy cancellationPolicy) {
        return getPartyCancellationPolicyTransfers(userVisit, getPartyCancellationPoliciesByCancellationPolicy(cancellationPolicy));
    }

    public void deletePartyCancellationPolicy(PartyCancellationPolicy partyCancellationPolicy, BasePK deletedBy) {
        var coreControl = Session.getModelController(CoreControl.class);

        partyCancellationPolicy.setThruTime(session.START_TIME_LONG);

        // Performed manually, since sendEvent doesn't call it for relatedEntityInstances.
        coreControl.deleteEntityInstanceDependencies(coreControl.getEntityInstanceByBasePK(partyCancellationPolicy.getPrimaryKey()), deletedBy);
        
        sendEventUsingNames(partyCancellationPolicy.getPartyPK(), EventTypes.MODIFY.name(), partyCancellationPolicy.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyCancellationPoliciesByParty(List<PartyCancellationPolicy> partyCancellationPolicies, BasePK deletedBy) {
        partyCancellationPolicies.forEach((partyCancellationPolicy) -> 
                deletePartyCancellationPolicy(partyCancellationPolicy, deletedBy)
        );
    }

    public void deletePartyCancellationPoliciesByParty(Party party, BasePK deletedBy) {
        deletePartyCancellationPoliciesByParty(getPartyCancellationPoliciesByPartyForUpdate(party), deletedBy);
    }

    public void deletePartyCancellationPoliciesByCancellationPolicy(CancellationPolicy cancellationPolicy, BasePK deletedBy) {
        deletePartyCancellationPoliciesByParty(getPartyCancellationPoliciesByCancellationPolicyForUpdate(cancellationPolicy), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Kinds
    // --------------------------------------------------------------------------------
    
    public CancellationKind createCancellationKind(String cancellationKindName, SequenceType cancellationSequenceType, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        CancellationKind defaultCancellationKind = getDefaultCancellationKind();
        boolean defaultFound = defaultCancellationKind != null;
        
        if(defaultFound && isDefault) {
            CancellationKindDetailValue defaultCancellationKindDetailValue = getDefaultCancellationKindDetailValueForUpdate();
            
            defaultCancellationKindDetailValue.setIsDefault(Boolean.FALSE);
            updateCancellationKindFromValue(defaultCancellationKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        CancellationKind cancellationKind = CancellationKindFactory.getInstance().create();
        CancellationKindDetail cancellationKindDetail = CancellationKindDetailFactory.getInstance().create(cancellationKind, cancellationKindName,
                cancellationSequenceType, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        cancellationKind = CancellationKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                cancellationKind.getPrimaryKey());
        cancellationKind.setActiveDetail(cancellationKindDetail);
        cancellationKind.setLastDetail(cancellationKindDetail);
        cancellationKind.store();
        
        sendEventUsingNames(cancellationKind.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return cancellationKind;
    }
    
    private CancellationKind getCancellationKindByName(String cancellationKindName, EntityPermission entityPermission) {
        CancellationKind cancellationKind;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationkinds, cancellationkinddetails " +
                        "WHERE cnclk_activedetailid = cnclkdt_cancellationkinddetailid AND cnclkdt_cancellationkindname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationkinds, cancellationkinddetails " +
                        "WHERE cnclk_activedetailid = cnclkdt_cancellationkinddetailid AND cnclkdt_cancellationkindname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationKindFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, cancellationKindName);
            
            cancellationKind = CancellationKindFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationKind;
    }
    
    public CancellationKind getCancellationKindByName(String cancellationKindName) {
        return getCancellationKindByName(cancellationKindName, EntityPermission.READ_ONLY);
    }
    
    public CancellationKind getCancellationKindByNameForUpdate(String cancellationKindName) {
        return getCancellationKindByName(cancellationKindName, EntityPermission.READ_WRITE);
    }
    
    public CancellationKindDetailValue getCancellationKindDetailValueForUpdate(CancellationKind cancellationKind) {
        return cancellationKind == null? null: cancellationKind.getLastDetailForUpdate().getCancellationKindDetailValue().clone();
    }
    
    public CancellationKindDetailValue getCancellationKindDetailValueByNameForUpdate(String cancellationKindName) {
        return getCancellationKindDetailValueForUpdate(getCancellationKindByNameForUpdate(cancellationKindName));
    }
    
    private CancellationKind getDefaultCancellationKind(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM cancellationkinds, cancellationkinddetails " +
                    "WHERE cnclk_activedetailid = cnclkdt_cancellationkinddetailid AND cnclkdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM cancellationkinds, cancellationkinddetails " +
                    "WHERE cnclk_activedetailid = cnclkdt_cancellationkinddetailid AND cnclkdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = CancellationKindFactory.getInstance().prepareStatement(query);
        
        return CancellationKindFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public CancellationKind getDefaultCancellationKind() {
        return getDefaultCancellationKind(EntityPermission.READ_ONLY);
    }
    
    public CancellationKind getDefaultCancellationKindForUpdate() {
        return getDefaultCancellationKind(EntityPermission.READ_WRITE);
    }
    
    public CancellationKindDetailValue getDefaultCancellationKindDetailValueForUpdate() {
        return getDefaultCancellationKind(EntityPermission.READ_WRITE).getLastDetailForUpdate().getCancellationKindDetailValue();
    }
    
    private List<CancellationKind> getCancellationKinds(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM cancellationkinds, cancellationkinddetails " +
                    "WHERE cnclk_activedetailid = cnclkdt_cancellationkinddetailid " +
                    "ORDER BY cnclkdt_sortorder, cnclkdt_cancellationkindname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM cancellationkinds, cancellationkinddetails " +
                    "WHERE cnclk_activedetailid = cnclkdt_cancellationkinddetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = CancellationKindFactory.getInstance().prepareStatement(query);
        
        return CancellationKindFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<CancellationKind> getCancellationKinds() {
        return getCancellationKinds(EntityPermission.READ_ONLY);
    }
    
    public List<CancellationKind> getCancellationKindsForUpdate() {
        return getCancellationKinds(EntityPermission.READ_WRITE);
    }
    
    public CancellationKindChoicesBean getCancellationKindChoices(String defaultCancellationKindChoice, Language language, boolean allowNullChoice) {
        List<CancellationKind> cancellationKinds = getCancellationKinds();
        int size = cancellationKinds.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCancellationKindChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var cancellationKind : cancellationKinds) {
            CancellationKindDetail cancellationKindDetail = cancellationKind.getLastDetail();
            
            String label = getBestCancellationKindDescription(cancellationKind, language);
            String value = cancellationKindDetail.getCancellationKindName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultCancellationKindChoice != null && defaultCancellationKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && cancellationKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CancellationKindChoicesBean(labels, values, defaultValue);
    }
    
    public CancellationKindTransfer getCancellationKindTransfer(UserVisit userVisit, CancellationKind cancellationKind) {
        return getCancellationPolicyTransferCaches(userVisit).getCancellationKindTransferCache().getCancellationKindTransfer(cancellationKind);
    }
    
    public List<CancellationKindTransfer> getCancellationKindTransfers(UserVisit userVisit) {
        List<CancellationKind> cancellationKinds = getCancellationKinds();
        List<CancellationKindTransfer> cancellationKindTransfers = new ArrayList<>(cancellationKinds.size());
        CancellationKindTransferCache cancellationKindTransferCache = getCancellationPolicyTransferCaches(userVisit).getCancellationKindTransferCache();
        
        cancellationKinds.forEach((cancellationKind) ->
                cancellationKindTransfers.add(cancellationKindTransferCache.getCancellationKindTransfer(cancellationKind))
        );
        
        return cancellationKindTransfers;
    }
    
    private void updateCancellationKindFromValue(CancellationKindDetailValue cancellationKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        CancellationKind cancellationKind = CancellationKindFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, cancellationKindDetailValue.getCancellationKindPK());
        CancellationKindDetail cancellationKindDetail = cancellationKind.getActiveDetailForUpdate();
        
        cancellationKindDetail.setThruTime(session.START_TIME_LONG);
        cancellationKindDetail.store();
        
        CancellationKindPK cancellationKindPK = cancellationKindDetail.getCancellationKindPK();
        String cancellationKindName = cancellationKindDetailValue.getCancellationKindName();
        SequenceTypePK cancellationSequenceTypePK = cancellationKindDetailValue.getCancellationSequenceTypePK();
        Boolean isDefault = cancellationKindDetailValue.getIsDefault();
        Integer sortOrder = cancellationKindDetailValue.getSortOrder();
        
        if(checkDefault) {
            CancellationKind defaultCancellationKind = getDefaultCancellationKind();
            boolean defaultFound = defaultCancellationKind != null && !defaultCancellationKind.equals(cancellationKind);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                CancellationKindDetailValue defaultCancellationKindDetailValue = getDefaultCancellationKindDetailValueForUpdate();
                
                defaultCancellationKindDetailValue.setIsDefault(Boolean.FALSE);
                updateCancellationKindFromValue(defaultCancellationKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }
        
        cancellationKindDetail = CancellationKindDetailFactory.getInstance().create(cancellationKindPK, cancellationKindName, cancellationSequenceTypePK,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        cancellationKind.setActiveDetail(cancellationKindDetail);
        cancellationKind.setLastDetail(cancellationKindDetail);
        cancellationKind.store();
        
        sendEventUsingNames(cancellationKindPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }
    
    public void updateCancellationKindFromValue(CancellationKindDetailValue cancellationKindDetailValue, BasePK updatedBy) {
        updateCancellationKindFromValue(cancellationKindDetailValue, true, updatedBy);
    }
    
    public void deleteCancellationKind(CancellationKind cancellationKind, BasePK deletedBy) {
        deleteCancellationKindDescriptionsByCancellationKind(cancellationKind, deletedBy);
        
        CancellationKindDetail cancellationKindDetail = cancellationKind.getLastDetailForUpdate();
        cancellationKindDetail.setThruTime(session.START_TIME_LONG);
        cancellationKind.setActiveDetail(null);
        cancellationKind.store();
        
        // Check for default, and pick one if necessary
        CancellationKind defaultCancellationKind = getDefaultCancellationKind();
        if(defaultCancellationKind == null) {
            List<CancellationKind> cancellationKinds = getCancellationKindsForUpdate();
            
            if(!cancellationKinds.isEmpty()) {
                Iterator<CancellationKind> iter = cancellationKinds.iterator();
                if(iter.hasNext()) {
                    defaultCancellationKind = iter.next();
                }
                CancellationKindDetailValue cancellationKindDetailValue = Objects.requireNonNull(defaultCancellationKind).getLastDetailForUpdate().getCancellationKindDetailValue().clone();
                
                cancellationKindDetailValue.setIsDefault(Boolean.TRUE);
                updateCancellationKindFromValue(cancellationKindDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(cancellationKind.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Kind Descriptions
    // --------------------------------------------------------------------------------
    
    public CancellationKindDescription createCancellationKindDescription(CancellationKind cancellationKind, Language language, String description,
            BasePK createdBy) {
        CancellationKindDescription cancellationKindDescription = CancellationKindDescriptionFactory.getInstance().create(cancellationKind,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(cancellationKind.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationKindDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return cancellationKindDescription;
    }
    
    private CancellationKindDescription getCancellationKindDescription(CancellationKind cancellationKind, Language language, EntityPermission entityPermission) {
        CancellationKindDescription cancellationKindDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationkinddescriptions " +
                        "WHERE cnclkd_cnclk_cancellationkindid = ? AND cnclkd_lang_languageid = ? AND cnclkd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationkinddescriptions " +
                        "WHERE cnclkd_cnclk_cancellationkindid = ? AND cnclkd_lang_languageid = ? AND cnclkd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationKindDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            cancellationKindDescription = CancellationKindDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationKindDescription;
    }
    
    public CancellationKindDescription getCancellationKindDescription(CancellationKind cancellationKind, Language language) {
        return getCancellationKindDescription(cancellationKind, language, EntityPermission.READ_ONLY);
    }
    
    public CancellationKindDescription getCancellationKindDescriptionForUpdate(CancellationKind cancellationKind, Language language) {
        return getCancellationKindDescription(cancellationKind, language, EntityPermission.READ_WRITE);
    }
    
    public CancellationKindDescriptionValue getCancellationKindDescriptionValue(CancellationKindDescription cancellationKindDescription) {
        return cancellationKindDescription == null? null: cancellationKindDescription.getCancellationKindDescriptionValue().clone();
    }
    
    public CancellationKindDescriptionValue getCancellationKindDescriptionValueForUpdate(CancellationKind cancellationKind, Language language) {
        return getCancellationKindDescriptionValue(getCancellationKindDescriptionForUpdate(cancellationKind, language));
    }
    
    private List<CancellationKindDescription> getCancellationKindDescriptionsByCancellationKind(CancellationKind cancellationKind, EntityPermission entityPermission) {
        List<CancellationKindDescription> cancellationKindDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationkinddescriptions, languages " +
                        "WHERE cnclkd_cnclk_cancellationkindid = ? AND cnclkd_thrutime = ? AND cnclkd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationkinddescriptions " +
                        "WHERE cnclkd_cnclk_cancellationkindid = ? AND cnclkd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationKindDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            cancellationKindDescriptions = CancellationKindDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationKindDescriptions;
    }
    
    public List<CancellationKindDescription> getCancellationKindDescriptionsByCancellationKind(CancellationKind cancellationKind) {
        return getCancellationKindDescriptionsByCancellationKind(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationKindDescription> getCancellationKindDescriptionsByCancellationKindForUpdate(CancellationKind cancellationKind) {
        return getCancellationKindDescriptionsByCancellationKind(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    public String getBestCancellationKindDescription(CancellationKind cancellationKind, Language language) {
        String description;
        CancellationKindDescription cancellationKindDescription = getCancellationKindDescription(cancellationKind, language);
        
        if(cancellationKindDescription == null && !language.getIsDefault()) {
            cancellationKindDescription = getCancellationKindDescription(cancellationKind, getPartyControl().getDefaultLanguage());
        }
        
        if(cancellationKindDescription == null) {
            description = cancellationKind.getLastDetail().getCancellationKindName();
        } else {
            description = cancellationKindDescription.getDescription();
        }
        
        return description;
    }
    
    public CancellationKindDescriptionTransfer getCancellationKindDescriptionTransfer(UserVisit userVisit, CancellationKindDescription cancellationKindDescription) {
        return getCancellationPolicyTransferCaches(userVisit).getCancellationKindDescriptionTransferCache().getCancellationKindDescriptionTransfer(cancellationKindDescription);
    }
    
    public List<CancellationKindDescriptionTransfer> getCancellationKindDescriptionTransfersByCancellationKind(UserVisit userVisit, CancellationKind cancellationKind) {
        List<CancellationKindDescription> cancellationKindDescriptions = getCancellationKindDescriptionsByCancellationKind(cancellationKind);
        List<CancellationKindDescriptionTransfer> cancellationKindDescriptionTransfers = new ArrayList<>(cancellationKindDescriptions.size());
        
        cancellationKindDescriptions.stream().forEach((cancellationKindDescription) -> {
            cancellationKindDescriptionTransfers.add(getCancellationPolicyTransferCaches(userVisit).getCancellationKindDescriptionTransferCache().getCancellationKindDescriptionTransfer(cancellationKindDescription));
        });
        
        return cancellationKindDescriptionTransfers;
    }
    
    public void updateCancellationKindDescriptionFromValue(CancellationKindDescriptionValue cancellationKindDescriptionValue, BasePK updatedBy) {
        if(cancellationKindDescriptionValue.hasBeenModified()) {
            CancellationKindDescription cancellationKindDescription = CancellationKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationKindDescriptionValue.getPrimaryKey());
            
            cancellationKindDescription.setThruTime(session.START_TIME_LONG);
            cancellationKindDescription.store();
            
            CancellationKind cancellationKind = cancellationKindDescription.getCancellationKind();
            Language language = cancellationKindDescription.getLanguage();
            String description = cancellationKindDescriptionValue.getDescription();
            
            cancellationKindDescription = CancellationKindDescriptionFactory.getInstance().create(cancellationKind, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(cancellationKind.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationKindDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteCancellationKindDescription(CancellationKindDescription cancellationKindDescription, BasePK deletedBy) {
        cancellationKindDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(cancellationKindDescription.getCancellationKindPK(), EventTypes.MODIFY.name(), cancellationKindDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteCancellationKindDescriptionsByCancellationKind(CancellationKind cancellationKind, BasePK deletedBy) {
        List<CancellationKindDescription> cancellationKindDescriptions = getCancellationKindDescriptionsByCancellationKindForUpdate(cancellationKind);
        
        cancellationKindDescriptions.forEach((cancellationKindDescription) -> 
                deleteCancellationKindDescription(cancellationKindDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Policies
    // --------------------------------------------------------------------------------
    
    public CancellationPolicy createCancellationPolicy(CancellationKind cancellationKind, String cancellationPolicyName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        CancellationPolicy defaultCancellationPolicy = getDefaultCancellationPolicy(cancellationKind);
        boolean defaultFound = defaultCancellationPolicy != null;
        
        if(defaultFound && isDefault) {
            CancellationPolicyDetailValue defaultCancellationPolicyDetailValue = getDefaultCancellationPolicyDetailValueForUpdate(cancellationKind);
            
            defaultCancellationPolicyDetailValue.setIsDefault(Boolean.FALSE);
            updateCancellationPolicyFromValue(defaultCancellationPolicyDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        CancellationPolicy cancellationPolicy = CancellationPolicyFactory.getInstance().create();
        CancellationPolicyDetail cancellationPolicyDetail = CancellationPolicyDetailFactory.getInstance().create(session,
                cancellationPolicy, cancellationKind, cancellationPolicyName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        cancellationPolicy = CancellationPolicyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                cancellationPolicy.getPrimaryKey());
        cancellationPolicy.setActiveDetail(cancellationPolicyDetail);
        cancellationPolicy.setLastDetail(cancellationPolicyDetail);
        cancellationPolicy.store();
        
        sendEventUsingNames(cancellationPolicy.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return cancellationPolicy;
    }
    
    private List<CancellationPolicy> getCancellationPolicies(CancellationKind cancellationKind, EntityPermission entityPermission) {
        List<CancellationPolicy> cancellationPolicies;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicies, cancellationpolicydetails " +
                        "WHERE cnclplcy_activedetailid = cnclplcydt_cancellationpolicydetailid AND cnclplcydt_cnclk_cancellationkindid = ? " +
                        "ORDER BY cnclplcydt_sortorder, cnclplcydt_cancellationpolicyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicies, cancellationpolicydetails " +
                        "WHERE cnclplcy_activedetailid = cnclplcydt_cancellationpolicydetailid AND cnclplcydt_cnclk_cancellationkindid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationPolicyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            
            cancellationPolicies = CancellationPolicyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationPolicies;
    }
    
    public List<CancellationPolicy> getCancellationPolicies(CancellationKind cancellationKind) {
        return getCancellationPolicies(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationPolicy> getCancellationPoliciesForUpdate(CancellationKind cancellationKind) {
        return getCancellationPolicies(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    private CancellationPolicy getDefaultCancellationPolicy(CancellationKind cancellationKind, EntityPermission entityPermission) {
        CancellationPolicy cancellationPolicy;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicies, cancellationpolicydetails " +
                        "WHERE cnclplcy_activedetailid = cnclplcydt_cancellationpolicydetailid " +
                        "AND cnclplcydt_cnclk_cancellationkindid = ? AND cnclplcydt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicies, cancellationpolicydetails " +
                        "WHERE cnclplcy_activedetailid = cnclplcydt_cancellationpolicydetailid " +
                        "AND cnclplcydt_cnclk_cancellationkindid = ? AND cnclplcydt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationPolicyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            
            cancellationPolicy = CancellationPolicyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationPolicy;
    }
    
    public CancellationPolicy getDefaultCancellationPolicy(CancellationKind cancellationKind) {
        return getDefaultCancellationPolicy(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public CancellationPolicy getDefaultCancellationPolicyForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationPolicy(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    public CancellationPolicyDetailValue getDefaultCancellationPolicyDetailValueForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationPolicyForUpdate(cancellationKind).getLastDetailForUpdate().getCancellationPolicyDetailValue().clone();
    }
    
    private CancellationPolicy getCancellationPolicyByName(CancellationKind cancellationKind, String cancellationPolicyName, EntityPermission entityPermission) {
        CancellationPolicy cancellationPolicy;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicies, cancellationpolicydetails " +
                        "WHERE cnclplcy_activedetailid = cnclplcydt_cancellationpolicydetailid " +
                        "AND cnclplcydt_cnclk_cancellationkindid = ? AND cnclplcydt_cancellationpolicyname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicies, cancellationpolicydetails " +
                        "WHERE cnclplcy_activedetailid = cnclplcydt_cancellationpolicydetailid " +
                        "AND cnclplcydt_cnclk_cancellationkindid = ? AND cnclplcydt_cancellationpolicyname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationPolicyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            ps.setString(2, cancellationPolicyName);
            
            cancellationPolicy = CancellationPolicyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationPolicy;
    }
    
    public CancellationPolicy getCancellationPolicyByName(CancellationKind cancellationKind, String cancellationPolicyName) {
        return getCancellationPolicyByName(cancellationKind, cancellationPolicyName, EntityPermission.READ_ONLY);
    }
    
    public CancellationPolicy getCancellationPolicyByNameForUpdate(CancellationKind cancellationKind, String cancellationPolicyName) {
        return getCancellationPolicyByName(cancellationKind, cancellationPolicyName, EntityPermission.READ_WRITE);
    }
    
    public CancellationPolicyDetailValue getCancellationPolicyDetailValueForUpdate(CancellationPolicy cancellationPolicy) {
        return cancellationPolicy == null? null: cancellationPolicy.getLastDetailForUpdate().getCancellationPolicyDetailValue().clone();
    }
    
    public CancellationPolicyDetailValue getCancellationPolicyDetailValueByNameForUpdate(CancellationKind cancellationKind, String cancellationPolicyName) {
        return getCancellationPolicyDetailValueForUpdate(getCancellationPolicyByNameForUpdate(cancellationKind, cancellationPolicyName));
    }
    
    public CancellationPolicyChoicesBean getCancellationPolicyChoices(String defaultCancellationPolicyChoice, Language language,
            boolean allowNullChoice, CancellationKind cancellationKind) {
        List<CancellationPolicy> cancellationPolicies = getCancellationPolicies(cancellationKind);
        int size = cancellationPolicies.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCancellationPolicyChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var cancellationPolicy : cancellationPolicies) {
            CancellationPolicyDetail cancellationPolicyDetail = cancellationPolicy.getLastDetail();
            String cancellationPolicyName = cancellationPolicyDetail.getCancellationPolicyName();
            CancellationPolicyTranslation cancellationPolicyTranslation = getBestCancellationPolicyTranslation(cancellationPolicy, language);

            String label = cancellationPolicyTranslation == null ? cancellationPolicyName : cancellationPolicyTranslation.getDescription();
            String value = cancellationPolicyName;
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultCancellationPolicyChoice != null && defaultCancellationPolicyChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && cancellationPolicyDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CancellationPolicyChoicesBean(labels, values, defaultValue);
    }
    
    public CancellationPolicyTransfer getCancellationPolicyTransfer(UserVisit userVisit, CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyTransferCaches(userVisit).getCancellationPolicyTransferCache().getCancellationPolicyTransfer(cancellationPolicy);
    }
    
    public List<CancellationPolicyTransfer> getCancellationPolicyTransfersByCancellationKind(UserVisit userVisit, CancellationKind cancellationKind) {
        List<CancellationPolicy> cancellationPolicies = getCancellationPolicies(cancellationKind);
        List<CancellationPolicyTransfer> cancellationPolicyTransfers = new ArrayList<>(cancellationPolicies.size());
        CancellationPolicyTransferCache cancellationPolicyTransferCache = getCancellationPolicyTransferCaches(userVisit).getCancellationPolicyTransferCache();
        
        cancellationPolicies.forEach((cancellationPolicy) ->
                cancellationPolicyTransfers.add(cancellationPolicyTransferCache.getCancellationPolicyTransfer(cancellationPolicy))
        );
        
        return cancellationPolicyTransfers;
    }
    
    private void updateCancellationPolicyFromValue(CancellationPolicyDetailValue cancellationPolicyDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(cancellationPolicyDetailValue.hasBeenModified()) {
            CancellationPolicy cancellationPolicy = CancellationPolicyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationPolicyDetailValue.getCancellationPolicyPK());
            CancellationPolicyDetail cancellationPolicyDetail = cancellationPolicy.getActiveDetailForUpdate();
            
            cancellationPolicyDetail.setThruTime(session.START_TIME_LONG);
            cancellationPolicyDetail.store();
            
            CancellationPolicyPK cancellationPolicyPK = cancellationPolicyDetail.getCancellationPolicyPK();
            CancellationKind cancellationKind = cancellationPolicyDetail.getCancellationKind();
            CancellationKindPK cancellationKindPK = cancellationKind.getPrimaryKey();
            String cancellationPolicyName = cancellationPolicyDetailValue.getCancellationPolicyName();
            Boolean isDefault = cancellationPolicyDetailValue.getIsDefault();
            Integer sortOrder = cancellationPolicyDetailValue.getSortOrder();
            
            if(checkDefault) {
                CancellationPolicy defaultCancellationPolicy = getDefaultCancellationPolicy(cancellationKind);
                boolean defaultFound = defaultCancellationPolicy != null && !defaultCancellationPolicy.equals(cancellationPolicy);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    CancellationPolicyDetailValue defaultCancellationPolicyDetailValue = getDefaultCancellationPolicyDetailValueForUpdate(cancellationKind);
                    
                    defaultCancellationPolicyDetailValue.setIsDefault(Boolean.FALSE);
                    updateCancellationPolicyFromValue(defaultCancellationPolicyDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            cancellationPolicyDetail = CancellationPolicyDetailFactory.getInstance().create(cancellationPolicyPK,
                    cancellationKindPK, cancellationPolicyName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            cancellationPolicy.setActiveDetail(cancellationPolicyDetail);
            cancellationPolicy.setLastDetail(cancellationPolicyDetail);
            
            sendEventUsingNames(cancellationPolicyPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateCancellationPolicyFromValue(CancellationPolicyDetailValue cancellationPolicyDetailValue, BasePK updatedBy) {
        updateCancellationPolicyFromValue(cancellationPolicyDetailValue, true, updatedBy);
    }
    
    public void deleteCancellationPolicy(CancellationPolicy cancellationPolicy, BasePK deletedBy) {
        deletePartyCancellationPoliciesByCancellationPolicy(cancellationPolicy, deletedBy);
        deleteCancellationPolicyReasonsByCancellationPolicy(cancellationPolicy, deletedBy);
        deleteCancellationPolicyTranslationsByCancellationPolicy(cancellationPolicy, deletedBy);
        
        CancellationPolicyDetail cancellationPolicyDetail = cancellationPolicy.getLastDetailForUpdate();
        cancellationPolicyDetail.setThruTime(session.START_TIME_LONG);
        cancellationPolicy.setActiveDetail(null);
        cancellationPolicy.store();
        
        // Check for default, and pick one if necessary
        CancellationKind cancellationKind = cancellationPolicyDetail.getCancellationKind();
        CancellationPolicy defaultCancellationPolicy = getDefaultCancellationPolicy(cancellationKind);
        if(defaultCancellationPolicy == null) {
            List<CancellationPolicy> cancellationPolicies = getCancellationPoliciesForUpdate(cancellationKind);
            
            if(!cancellationPolicies.isEmpty()) {
                Iterator<CancellationPolicy> iter = cancellationPolicies.iterator();
                if(iter.hasNext()) {
                    defaultCancellationPolicy = iter.next();
                }
                CancellationPolicyDetailValue cancellationPolicyDetailValue = Objects.requireNonNull(defaultCancellationPolicy).getLastDetailForUpdate().getCancellationPolicyDetailValue().clone();
                
                cancellationPolicyDetailValue.setIsDefault(Boolean.TRUE);
                updateCancellationPolicyFromValue(cancellationPolicyDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(cancellationPolicy.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteCancellationPoliciesByCancellationKind(CancellationKind cancellationKind, BasePK deletedBy) {
        List<CancellationPolicy> cancellationPolicies = getCancellationPoliciesForUpdate(cancellationKind);
        
        cancellationPolicies.forEach((cancellationPolicy) -> 
                deleteCancellationPolicy(cancellationPolicy, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Policy Translations
    // --------------------------------------------------------------------------------

    public CancellationPolicyTranslation createCancellationPolicyTranslation(CancellationPolicy cancellationPolicy, Language language,
            String description, MimeType policyMimeType, String policy, BasePK createdBy) {
        CancellationPolicyTranslation cancellationPolicyTranslation = CancellationPolicyTranslationFactory.getInstance().create(cancellationPolicy,
                language, description, policyMimeType, policy, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(cancellationPolicy.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationPolicyTranslation.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return cancellationPolicyTranslation;
    }

    private static final Map<EntityPermission, String> getCancellationPolicyTranslationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cancellationpolicytranslations " +
                "WHERE cnclplcytr_cnclplcy_cancellationpolicyid = ? AND cnclplcytr_lang_languageid = ? AND cnclplcytr_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cancellationpolicytranslations " +
                "WHERE cnclplcytr_cnclplcy_cancellationpolicyid = ? AND cnclplcytr_lang_languageid = ? AND cnclplcytr_thrutime = ? " +
                "FOR UPDATE");
        getCancellationPolicyTranslationQueries = Collections.unmodifiableMap(queryMap);
    }

    private CancellationPolicyTranslation getCancellationPolicyTranslation(CancellationPolicy cancellationPolicy, Language language, EntityPermission entityPermission) {
        return CancellationPolicyTranslationFactory.getInstance().getEntityFromQuery(entityPermission, getCancellationPolicyTranslationQueries, cancellationPolicy, language,
                Session.MAX_TIME);
    }

    public CancellationPolicyTranslation getCancellationPolicyTranslation(CancellationPolicy cancellationPolicy, Language language) {
        return getCancellationPolicyTranslation(cancellationPolicy, language, EntityPermission.READ_ONLY);
    }

    public CancellationPolicyTranslation getCancellationPolicyTranslationForUpdate(CancellationPolicy cancellationPolicy, Language language) {
        return getCancellationPolicyTranslation(cancellationPolicy, language, EntityPermission.READ_WRITE);
    }

    public CancellationPolicyTranslationValue getCancellationPolicyTranslationValue(CancellationPolicyTranslation cancellationPolicyTranslation) {
        return cancellationPolicyTranslation == null? null: cancellationPolicyTranslation.getCancellationPolicyTranslationValue().clone();
    }

    public CancellationPolicyTranslationValue getCancellationPolicyTranslationValueForUpdate(CancellationPolicy cancellationPolicy, Language language) {
        return getCancellationPolicyTranslationValue(getCancellationPolicyTranslationForUpdate(cancellationPolicy, language));
    }

    private static final Map<EntityPermission, String> getCancellationPolicyTranslationsByCancellationPolicyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cancellationpolicytranslations, languages " +
                "WHERE cnclplcytr_cnclplcy_cancellationpolicyid = ? AND cnclplcytr_thrutime = ? AND cnclplcytr_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cancellationpolicytranslations " +
                "WHERE cnclplcytr_cnclplcy_cancellationpolicyid = ? AND cnclplcytr_thrutime = ? " +
                "FOR UPDATE");
        getCancellationPolicyTranslationsByCancellationPolicyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CancellationPolicyTranslation> getCancellationPolicyTranslationsByCancellationPolicy(CancellationPolicy cancellationPolicy, EntityPermission entityPermission) {
        return CancellationPolicyTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, getCancellationPolicyTranslationsByCancellationPolicyQueries,
                cancellationPolicy, Session.MAX_TIME);
    }

    public List<CancellationPolicyTranslation> getCancellationPolicyTranslationsByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyTranslationsByCancellationPolicy(cancellationPolicy, EntityPermission.READ_ONLY);
    }

    public List<CancellationPolicyTranslation> getCancellationPolicyTranslationsByCancellationPolicyForUpdate(CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyTranslationsByCancellationPolicy(cancellationPolicy, EntityPermission.READ_WRITE);
    }

    public CancellationPolicyTranslation getBestCancellationPolicyTranslation(CancellationPolicy cancellationPolicy, Language language) {
        CancellationPolicyTranslation cancellationPolicyTranslation = getCancellationPolicyTranslation(cancellationPolicy, language);

        if(cancellationPolicyTranslation == null && !language.getIsDefault()) {
            cancellationPolicyTranslation = getCancellationPolicyTranslation(cancellationPolicy, getPartyControl().getDefaultLanguage());
        }

        return cancellationPolicyTranslation;
    }

    public CancellationPolicyTranslationTransfer getCancellationPolicyTranslationTransfer(UserVisit userVisit, CancellationPolicyTranslation cancellationPolicyTranslation) {
        return getCancellationPolicyTransferCaches(userVisit).getCancellationPolicyTranslationTransferCache().getCancellationPolicyTranslationTransfer(cancellationPolicyTranslation);
    }

    public List<CancellationPolicyTranslationTransfer> getCancellationPolicyTranslationTransfers(UserVisit userVisit, CancellationPolicy cancellationPolicy) {
        List<CancellationPolicyTranslation> cancellationPolicyTranslations = getCancellationPolicyTranslationsByCancellationPolicy(cancellationPolicy);
        List<CancellationPolicyTranslationTransfer> cancellationPolicyTranslationTransfers = new ArrayList<>(cancellationPolicyTranslations.size());
        CancellationPolicyTranslationTransferCache cancellationPolicyTranslationTransferCache = getCancellationPolicyTransferCaches(userVisit).getCancellationPolicyTranslationTransferCache();

        cancellationPolicyTranslations.forEach((cancellationPolicyTranslation) ->
                cancellationPolicyTranslationTransfers.add(cancellationPolicyTranslationTransferCache.getCancellationPolicyTranslationTransfer(cancellationPolicyTranslation))
        );

        return cancellationPolicyTranslationTransfers;
    }

    public void updateCancellationPolicyTranslationFromValue(CancellationPolicyTranslationValue cancellationPolicyTranslationValue, BasePK updatedBy) {
        if(cancellationPolicyTranslationValue.hasBeenModified()) {
            CancellationPolicyTranslation cancellationPolicyTranslation = CancellationPolicyTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    cancellationPolicyTranslationValue.getPrimaryKey());

            cancellationPolicyTranslation.setThruTime(session.START_TIME_LONG);
            cancellationPolicyTranslation.store();

            CancellationPolicyPK cancellationPolicyPK = cancellationPolicyTranslation.getCancellationPolicyPK();
            LanguagePK languagePK = cancellationPolicyTranslation.getLanguagePK();
            String description = cancellationPolicyTranslationValue.getDescription();
            MimeTypePK policyMimeTypePK = cancellationPolicyTranslationValue.getPolicyMimeTypePK();
            String policy = cancellationPolicyTranslationValue.getPolicy();

            cancellationPolicyTranslation = CancellationPolicyTranslationFactory.getInstance().create(cancellationPolicyPK, languagePK, description,
                    policyMimeTypePK, policy, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(cancellationPolicyPK, EventTypes.MODIFY.name(), cancellationPolicyTranslation.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteCancellationPolicyTranslation(CancellationPolicyTranslation cancellationPolicyTranslation, BasePK deletedBy) {
        cancellationPolicyTranslation.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(cancellationPolicyTranslation.getCancellationPolicyPK(), EventTypes.MODIFY.name(), cancellationPolicyTranslation.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteCancellationPolicyTranslationsByCancellationPolicy(CancellationPolicy cancellationPolicy, BasePK deletedBy) {
        List<CancellationPolicyTranslation> cancellationPolicyTranslations = getCancellationPolicyTranslationsByCancellationPolicyForUpdate(cancellationPolicy);

        cancellationPolicyTranslations.forEach((cancellationPolicyTranslation) -> 
                deleteCancellationPolicyTranslation(cancellationPolicyTranslation, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Cancellation Policy Reasons
    // --------------------------------------------------------------------------------
    
    public CancellationPolicyReason createCancellationPolicyReason(CancellationPolicy cancellationPolicy, CancellationReason cancellationReason, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        CancellationPolicyReason defaultCancellationPolicyReason = getDefaultCancellationPolicyReason(cancellationPolicy);
        boolean defaultFound = defaultCancellationPolicyReason != null;
        
        if(defaultFound && isDefault) {
            CancellationPolicyReasonValue defaultCancellationPolicyReasonValue = getDefaultCancellationPolicyReasonValueForUpdate(cancellationPolicy);
            
            defaultCancellationPolicyReasonValue.setIsDefault(Boolean.FALSE);
            updateCancellationPolicyReasonFromValue(defaultCancellationPolicyReasonValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        CancellationPolicyReason cancellationPolicyReason = CancellationPolicyReasonFactory.getInstance().create(cancellationPolicy, cancellationReason,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(cancellationPolicy.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationPolicyReason.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return cancellationPolicyReason;
    }
    
    private CancellationPolicyReason getCancellationPolicyReason(CancellationPolicy cancellationPolicy, CancellationReason cancellationReason, EntityPermission entityPermission) {
        CancellationPolicyReason cancellationPolicyReason;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicyreasons " +
                        "WHERE cnclplcyrsn_cnclplcy_cancellationpolicyid = ? AND cnclplcyrsn_cnclrsn_cancellationreasonid = ? AND cnclplcyrsn_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicyreasons " +
                        "WHERE cnclplcyrsn_cnclplcy_cancellationpolicyid = ? AND cnclplcyrsn_cnclrsn_cancellationreasonid = ? AND cnclplcyrsn_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationPolicyReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationPolicy.getPrimaryKey().getEntityId());
            ps.setLong(2, cancellationReason.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            cancellationPolicyReason = CancellationPolicyReasonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationPolicyReason;
    }
    
    public CancellationPolicyReason getCancellationPolicyReason(CancellationPolicy cancellationPolicy, CancellationReason cancellationReason) {
        return getCancellationPolicyReason(cancellationPolicy, cancellationReason, EntityPermission.READ_ONLY);
    }
    
    public CancellationPolicyReason getCancellationPolicyReasonForUpdate(CancellationPolicy cancellationPolicy, CancellationReason cancellationReason) {
        return getCancellationPolicyReason(cancellationPolicy, cancellationReason, EntityPermission.READ_WRITE);
    }
    
    public CancellationPolicyReasonValue getCancellationPolicyReasonValueForUpdate(CancellationPolicy cancellationPolicy, CancellationReason cancellationReason) {
        CancellationPolicyReason cancellationPolicyReason = getCancellationPolicyReasonForUpdate(cancellationPolicy, cancellationReason);
        
        return cancellationPolicyReason == null? null: cancellationPolicyReason.getCancellationPolicyReasonValue().clone();
    }
    
    private CancellationPolicyReason getDefaultCancellationPolicyReason(CancellationPolicy cancellationPolicy, EntityPermission entityPermission) {
        CancellationPolicyReason cancellationPolicyReason;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicyreasons " +
                        "WHERE cnclplcyrsn_cnclplcy_cancellationpolicyid = ? AND cnclplcyrsn_isdefault = 1 AND cnclplcyrsn_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicyreasons " +
                        "WHERE cnclplcyrsn_cnclplcy_cancellationpolicyid = ? AND cnclplcyrsn_isdefault = 1 AND cnclplcyrsn_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationPolicyReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationPolicy.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            cancellationPolicyReason = CancellationPolicyReasonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationPolicyReason;
    }
    
    public CancellationPolicyReason getDefaultCancellationPolicyReason(CancellationPolicy cancellationPolicy) {
        return getDefaultCancellationPolicyReason(cancellationPolicy, EntityPermission.READ_ONLY);
    }
    
    public CancellationPolicyReason getDefaultCancellationPolicyReasonForUpdate(CancellationPolicy cancellationPolicy) {
        return getDefaultCancellationPolicyReason(cancellationPolicy, EntityPermission.READ_WRITE);
    }
    
    public CancellationPolicyReasonValue getDefaultCancellationPolicyReasonValueForUpdate(CancellationPolicy cancellationPolicy) {
        CancellationPolicyReason cancellationPolicyReason = getDefaultCancellationPolicyReasonForUpdate(cancellationPolicy);
        
        return cancellationPolicyReason == null? null: cancellationPolicyReason.getCancellationPolicyReasonValue().clone();
    }
    
    private List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationPolicy(CancellationPolicy cancellationPolicy, EntityPermission entityPermission) {
        List<CancellationPolicyReason> cancellationPolicyReasons;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicyreasons, cancellationreasons, cancellationreasondetails " +
                        "WHERE cnclplcyrsn_cnclplcy_cancellationpolicyid = ? AND cnclplcyrsn_thrutime = ? " +
                        "AND cnclplcyrsn_cnclrsn_cancellationreasonid = cnclrsn_cancellationreasonid AND cnclrsn_lastdetailid = cnclrsndt_cancellationreasondetailid " +
                        "ORDER BY cnclrsndt_sortorder, cnclrsndt_cancellationreasonname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicyreasons " +
                        "WHERE cnclplcyrsn_cnclplcy_cancellationpolicyid = ? AND cnclplcyrsn_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationPolicyReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationPolicy.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            cancellationPolicyReasons = CancellationPolicyReasonFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationPolicyReasons;
    }
    
    public List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyReasonsByCancellationPolicy(cancellationPolicy, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationPolicyForUpdate(CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyReasonsByCancellationPolicy(cancellationPolicy, EntityPermission.READ_WRITE);
    }
    
    private List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationReason(CancellationReason cancellationReason, EntityPermission entityPermission) {
        List<CancellationPolicyReason> cancellationPolicyReasons;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicyreasons, cancellationpolicies, cancellationpolicydetails " +
                        "WHERE cnclplcyrsn_cnclrsn_cancellationreasonid = ? AND cnclplcyrsn_thrutime = ? " +
                        "AND cnclplcyrsn_cnclplcy_cancellationpolicyid = cnclplcy_cancellationpolicyid AND cnclplcy_lastdetailid = cnclplcydt_cancellationpolicydetailid " +
                        "ORDER BY cnclplcydt_sortorder, cnclplcydt_cancellationpolicyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationpolicyreasons " +
                        "WHERE cnclplcyrsn_cnclrsn_cancellationreasonid = ? AND cnclplcyrsn_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationPolicyReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationReason.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            cancellationPolicyReasons = CancellationPolicyReasonFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationPolicyReasons;
    }
    
    public List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationReason(CancellationReason cancellationReason) {
        return getCancellationPolicyReasonsByCancellationReason(cancellationReason, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationReasonForUpdate(CancellationReason cancellationReason) {
        return getCancellationPolicyReasonsByCancellationReason(cancellationReason, EntityPermission.READ_WRITE);
    }
    
    public List<CancellationPolicyReasonTransfer> getCancellationPolicyReasonTransfers(UserVisit userVisit, List<CancellationPolicyReason> cancellationPolicyReasons) {
        List<CancellationPolicyReasonTransfer> cancellationPolicyReasonTransfers = new ArrayList<>(cancellationPolicyReasons.size());
        CancellationPolicyReasonTransferCache cancellationPolicyReasonTransferCache = getCancellationPolicyTransferCaches(userVisit).getCancellationPolicyReasonTransferCache();
        
        cancellationPolicyReasons.forEach((cancellationPolicyReason) ->
                cancellationPolicyReasonTransfers.add(cancellationPolicyReasonTransferCache.getCancellationPolicyReasonTransfer(cancellationPolicyReason))
        );
        
        return cancellationPolicyReasonTransfers;
    }
    
    public List<CancellationPolicyReasonTransfer> getCancellationPolicyReasonTransfersByCancellationPolicy(UserVisit userVisit, CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyReasonTransfers(userVisit, getCancellationPolicyReasonsByCancellationPolicy(cancellationPolicy));
    }
    
    public List<CancellationPolicyReasonTransfer> getCancellationPolicyReasonTransfersByCancellationReason(UserVisit userVisit, CancellationReason cancellationReason) {
        return getCancellationPolicyReasonTransfers(userVisit, getCancellationPolicyReasonsByCancellationReason(cancellationReason));
    }
    
    public CancellationPolicyReasonTransfer getCancellationPolicyReasonTransfer(UserVisit userVisit, CancellationPolicyReason cancellationPolicyReason) {
        return getCancellationPolicyTransferCaches(userVisit).getCancellationPolicyReasonTransferCache().getCancellationPolicyReasonTransfer(cancellationPolicyReason);
    }
    
    private void updateCancellationPolicyReasonFromValue(CancellationPolicyReasonValue cancellationPolicyReasonValue, boolean checkDefault, BasePK updatedBy) {
        if(cancellationPolicyReasonValue.hasBeenModified()) {
            CancellationPolicyReason cancellationPolicyReason = CancellationPolicyReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationPolicyReasonValue.getPrimaryKey());
            
            cancellationPolicyReason.setThruTime(session.START_TIME_LONG);
            cancellationPolicyReason.store();
            
            CancellationPolicy cancellationPolicy = cancellationPolicyReason.getCancellationPolicy(); // Not Updated
            CancellationPolicyPK cancellationPolicyPK = cancellationPolicy.getPrimaryKey(); // Not Updated
            CancellationReasonPK cancellationReasonPK = cancellationPolicyReason.getCancellationReasonPK(); // Not Updated
            Boolean isDefault = cancellationPolicyReasonValue.getIsDefault();
            Integer sortOrder = cancellationPolicyReasonValue.getSortOrder();
            
            if(checkDefault) {
                CancellationPolicyReason defaultCancellationPolicyReason = getDefaultCancellationPolicyReason(cancellationPolicy);
                boolean defaultFound = defaultCancellationPolicyReason != null && !defaultCancellationPolicyReason.equals(cancellationPolicyReason);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    CancellationPolicyReasonValue defaultCancellationPolicyReasonValue = getDefaultCancellationPolicyReasonValueForUpdate(cancellationPolicy);
                    
                    defaultCancellationPolicyReasonValue.setIsDefault(Boolean.FALSE);
                    updateCancellationPolicyReasonFromValue(defaultCancellationPolicyReasonValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            cancellationPolicyReason = CancellationPolicyReasonFactory.getInstance().create(cancellationPolicyPK, cancellationReasonPK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(cancellationPolicyPK, EventTypes.MODIFY.name(), cancellationPolicyReason.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateCancellationPolicyReasonFromValue(CancellationPolicyReasonValue cancellationPolicyReasonValue, BasePK updatedBy) {
        updateCancellationPolicyReasonFromValue(cancellationPolicyReasonValue, true, updatedBy);
    }
    
    public void deleteCancellationPolicyReason(CancellationPolicyReason cancellationPolicyReason, BasePK deletedBy) {
        cancellationPolicyReason.setThruTime(session.START_TIME_LONG);
        cancellationPolicyReason.store();
        
        // Check for default, and pick one if necessary
        CancellationPolicy cancellationPolicy = cancellationPolicyReason.getCancellationPolicy();
        CancellationPolicyReason defaultCancellationPolicyReason = getDefaultCancellationPolicyReason(cancellationPolicy);
        if(defaultCancellationPolicyReason == null) {
            List<CancellationPolicyReason> cancellationPolicyReasons = getCancellationPolicyReasonsByCancellationPolicyForUpdate(cancellationPolicy);
            
            if(!cancellationPolicyReasons.isEmpty()) {
                Iterator<CancellationPolicyReason> iter = cancellationPolicyReasons.iterator();
                if(iter.hasNext()) {
                    defaultCancellationPolicyReason = iter.next();
                }
                CancellationPolicyReasonValue cancellationPolicyReasonValue = defaultCancellationPolicyReason.getCancellationPolicyReasonValue().clone();
                
                cancellationPolicyReasonValue.setIsDefault(Boolean.TRUE);
                updateCancellationPolicyReasonFromValue(cancellationPolicyReasonValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(cancellationPolicy.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationPolicyReason.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteCancellationPolicyReasons(List<CancellationPolicyReason> cancellationPolicyReasons, BasePK deletedBy) {
        cancellationPolicyReasons.forEach((cancellationPolicyReason) -> 
                deleteCancellationPolicyReason(cancellationPolicyReason, deletedBy)
        );
    }
    
    public void deleteCancellationPolicyReasonsByCancellationPolicy(CancellationPolicy cancellationPolicy, BasePK deletedBy) {
        deleteCancellationPolicyReasons(getCancellationPolicyReasonsByCancellationPolicyForUpdate(cancellationPolicy), deletedBy);
    }
    
    public void deleteCancellationPolicyReasonsByCancellationReason(CancellationReason cancellationReason, BasePK deletedBy) {
        deleteCancellationPolicyReasons(getCancellationPolicyReasonsByCancellationReasonForUpdate(cancellationReason), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Reasons
    // --------------------------------------------------------------------------------
    
    public CancellationReason createCancellationReason(CancellationKind cancellationKind, String cancellationReasonName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        CancellationReason defaultCancellationReason = getDefaultCancellationReason(cancellationKind);
        boolean defaultFound = defaultCancellationReason != null;
        
        if(defaultFound && isDefault) {
            CancellationReasonDetailValue defaultCancellationReasonDetailValue = getDefaultCancellationReasonDetailValueForUpdate(cancellationKind);
            
            defaultCancellationReasonDetailValue.setIsDefault(Boolean.FALSE);
            updateCancellationReasonFromValue(defaultCancellationReasonDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        CancellationReason cancellationReason = CancellationReasonFactory.getInstance().create();
        CancellationReasonDetail cancellationReasonDetail = CancellationReasonDetailFactory.getInstance().create(session,
                cancellationReason, cancellationKind, cancellationReasonName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        cancellationReason = CancellationReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                cancellationReason.getPrimaryKey());
        cancellationReason.setActiveDetail(cancellationReasonDetail);
        cancellationReason.setLastDetail(cancellationReasonDetail);
        cancellationReason.store();
        
        sendEventUsingNames(cancellationReason.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return cancellationReason;
    }
    
    private List<CancellationReason> getCancellationReasons(CancellationKind cancellationKind, EntityPermission entityPermission) {
        List<CancellationReason> cancellationReasons;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasons, cancellationreasondetails " +
                        "WHERE cnclrsn_activedetailid = cnclrsndt_cancellationreasondetailid AND cnclrsndt_cnclk_cancellationkindid = ? " +
                        "ORDER BY cnclrsndt_sortorder, cnclrsndt_cancellationreasonname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasons, cancellationreasondetails " +
                        "WHERE cnclrsn_activedetailid = cnclrsndt_cancellationreasondetailid AND cnclrsndt_cnclk_cancellationkindid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            
            cancellationReasons = CancellationReasonFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationReasons;
    }
    
    public List<CancellationReason> getCancellationReasons(CancellationKind cancellationKind) {
        return getCancellationReasons(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationReason> getCancellationReasonsForUpdate(CancellationKind cancellationKind) {
        return getCancellationReasons(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    private CancellationReason getDefaultCancellationReason(CancellationKind cancellationKind, EntityPermission entityPermission) {
        CancellationReason cancellationReason;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasons, cancellationreasondetails " +
                        "WHERE cnclrsn_activedetailid = cnclrsndt_cancellationreasondetailid " +
                        "AND cnclrsndt_cnclk_cancellationkindid = ? AND cnclrsndt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasons, cancellationreasondetails " +
                        "WHERE cnclrsn_activedetailid = cnclrsndt_cancellationreasondetailid " +
                        "AND cnclrsndt_cnclk_cancellationkindid = ? AND cnclrsndt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            
            cancellationReason = CancellationReasonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationReason;
    }
    
    public CancellationReason getDefaultCancellationReason(CancellationKind cancellationKind) {
        return getDefaultCancellationReason(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public CancellationReason getDefaultCancellationReasonForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationReason(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    public CancellationReasonDetailValue getDefaultCancellationReasonDetailValueForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationReasonForUpdate(cancellationKind).getLastDetailForUpdate().getCancellationReasonDetailValue().clone();
    }
    
    private CancellationReason getCancellationReasonByName(CancellationKind cancellationKind, String cancellationReasonName, EntityPermission entityPermission) {
        CancellationReason cancellationReason;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasons, cancellationreasondetails " +
                        "WHERE cnclrsn_activedetailid = cnclrsndt_cancellationreasondetailid " +
                        "AND cnclrsndt_cnclk_cancellationkindid = ? AND cnclrsndt_cancellationreasonname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasons, cancellationreasondetails " +
                        "WHERE cnclrsn_activedetailid = cnclrsndt_cancellationreasondetailid " +
                        "AND cnclrsndt_cnclk_cancellationkindid = ? AND cnclrsndt_cancellationreasonname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            ps.setString(2, cancellationReasonName);
            
            cancellationReason = CancellationReasonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationReason;
    }
    
    public CancellationReason getCancellationReasonByName(CancellationKind cancellationKind, String cancellationReasonName) {
        return getCancellationReasonByName(cancellationKind, cancellationReasonName, EntityPermission.READ_ONLY);
    }
    
    public CancellationReason getCancellationReasonByNameForUpdate(CancellationKind cancellationKind, String cancellationReasonName) {
        return getCancellationReasonByName(cancellationKind, cancellationReasonName, EntityPermission.READ_WRITE);
    }
    
    public CancellationReasonDetailValue getCancellationReasonDetailValueForUpdate(CancellationReason cancellationReason) {
        return cancellationReason == null? null: cancellationReason.getLastDetailForUpdate().getCancellationReasonDetailValue().clone();
    }
    
    public CancellationReasonDetailValue getCancellationReasonDetailValueByNameForUpdate(CancellationKind cancellationKind, String cancellationReasonName) {
        return getCancellationReasonDetailValueForUpdate(getCancellationReasonByNameForUpdate(cancellationKind, cancellationReasonName));
    }
    
    public CancellationReasonChoicesBean getCancellationReasonChoices(String defaultCancellationReasonChoice, Language language,
            boolean allowNullChoice, CancellationKind cancellationKind) {
        List<CancellationReason> cancellationReasons = getCancellationReasons(cancellationKind);
        int size = cancellationReasons.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCancellationReasonChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var cancellationReason : cancellationReasons) {
            CancellationReasonDetail cancellationReasonDetail = cancellationReason.getLastDetail();
            String label = getBestCancellationReasonDescription(cancellationReason, language);
            String value = cancellationReasonDetail.getCancellationReasonName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultCancellationReasonChoice != null && defaultCancellationReasonChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && cancellationReasonDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CancellationReasonChoicesBean(labels, values, defaultValue);
    }
    
    public CancellationReasonTransfer getCancellationReasonTransfer(UserVisit userVisit, CancellationReason cancellationReason) {
        return getCancellationPolicyTransferCaches(userVisit).getCancellationReasonTransferCache().getCancellationReasonTransfer(cancellationReason);
    }
    
    public List<CancellationReasonTransfer> getCancellationReasonTransfersByCancellationKind(UserVisit userVisit, CancellationKind cancellationKind) {
        List<CancellationReason> cancellationReasons = getCancellationReasons(cancellationKind);
        List<CancellationReasonTransfer> cancellationReasonTransfers = new ArrayList<>(cancellationReasons.size());
        CancellationReasonTransferCache cancellationReasonTransferCache = getCancellationPolicyTransferCaches(userVisit).getCancellationReasonTransferCache();
        
        cancellationReasons.forEach((cancellationReason) ->
                cancellationReasonTransfers.add(cancellationReasonTransferCache.getCancellationReasonTransfer(cancellationReason))
        );
        
        return cancellationReasonTransfers;
    }
    
    private void updateCancellationReasonFromValue(CancellationReasonDetailValue cancellationReasonDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(cancellationReasonDetailValue.hasBeenModified()) {
            CancellationReason cancellationReason = CancellationReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationReasonDetailValue.getCancellationReasonPK());
            CancellationReasonDetail cancellationReasonDetail = cancellationReason.getActiveDetailForUpdate();
            
            cancellationReasonDetail.setThruTime(session.START_TIME_LONG);
            cancellationReasonDetail.store();
            
            CancellationReasonPK cancellationReasonPK = cancellationReasonDetail.getCancellationReasonPK();
            CancellationKind cancellationKind = cancellationReasonDetail.getCancellationKind();
            CancellationKindPK cancellationKindPK = cancellationKind.getPrimaryKey();
            String cancellationReasonName = cancellationReasonDetailValue.getCancellationReasonName();
            Boolean isDefault = cancellationReasonDetailValue.getIsDefault();
            Integer sortOrder = cancellationReasonDetailValue.getSortOrder();
            
            if(checkDefault) {
                CancellationReason defaultCancellationReason = getDefaultCancellationReason(cancellationKind);
                boolean defaultFound = defaultCancellationReason != null && !defaultCancellationReason.equals(cancellationReason);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    CancellationReasonDetailValue defaultCancellationReasonDetailValue = getDefaultCancellationReasonDetailValueForUpdate(cancellationKind);
                    
                    defaultCancellationReasonDetailValue.setIsDefault(Boolean.FALSE);
                    updateCancellationReasonFromValue(defaultCancellationReasonDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            cancellationReasonDetail = CancellationReasonDetailFactory.getInstance().create(cancellationReasonPK,
                    cancellationKindPK, cancellationReasonName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            cancellationReason.setActiveDetail(cancellationReasonDetail);
            cancellationReason.setLastDetail(cancellationReasonDetail);
            
            sendEventUsingNames(cancellationReasonPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateCancellationReasonFromValue(CancellationReasonDetailValue cancellationReasonDetailValue, BasePK updatedBy) {
        updateCancellationReasonFromValue(cancellationReasonDetailValue, true, updatedBy);
    }
    
    public void deleteCancellationReason(CancellationReason cancellationReason, BasePK deletedBy) {
        deleteCancellationPolicyReasonsByCancellationReason(cancellationReason, deletedBy);
        deleteCancellationReasonTypesByCancellationReason(cancellationReason, deletedBy);
        deleteCancellationReasonDescriptionsByCancellationReason(cancellationReason, deletedBy);
        
        CancellationReasonDetail cancellationReasonDetail = cancellationReason.getLastDetailForUpdate();
        cancellationReasonDetail.setThruTime(session.START_TIME_LONG);
        cancellationReason.setActiveDetail(null);
        cancellationReason.store();
        
        // Check for default, and pick one if necessary
        CancellationKind cancellationKind = cancellationReasonDetail.getCancellationKind();
        CancellationReason defaultCancellationReason = getDefaultCancellationReason(cancellationKind);
        if(defaultCancellationReason == null) {
            List<CancellationReason> cancellationReasons = getCancellationReasonsForUpdate(cancellationKind);
            
            if(!cancellationReasons.isEmpty()) {
                Iterator<CancellationReason> iter = cancellationReasons.iterator();
                if(iter.hasNext()) {
                    defaultCancellationReason = iter.next();
                }
                CancellationReasonDetailValue cancellationReasonDetailValue = Objects.requireNonNull(defaultCancellationReason).getLastDetailForUpdate().getCancellationReasonDetailValue().clone();
                
                cancellationReasonDetailValue.setIsDefault(Boolean.TRUE);
                updateCancellationReasonFromValue(cancellationReasonDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(cancellationReason.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteCancellationReasonsByCancellationKind(CancellationKind cancellationKind, BasePK deletedBy) {
        List<CancellationReason> cancellationReasons = getCancellationReasonsForUpdate(cancellationKind);
        
        cancellationReasons.forEach((cancellationReason) -> 
                deleteCancellationReason(cancellationReason, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Reason Descriptions
    // --------------------------------------------------------------------------------
    
    public CancellationReasonDescription createCancellationReasonDescription(CancellationReason cancellationReason, Language language, String description,
            BasePK createdBy) {
        CancellationReasonDescription cancellationReasonDescription = CancellationReasonDescriptionFactory.getInstance().create(cancellationReason,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(cancellationReason.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationReasonDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return cancellationReasonDescription;
    }
    
    private CancellationReasonDescription getCancellationReasonDescription(CancellationReason cancellationReason, Language language, EntityPermission entityPermission) {
        CancellationReasonDescription cancellationReasonDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasondescriptions " +
                        "WHERE cnclrsnd_cnclrsn_cancellationreasonid = ? AND cnclrsnd_lang_languageid = ? AND cnclrsnd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasondescriptions " +
                        "WHERE cnclrsnd_cnclrsn_cancellationreasonid = ? AND cnclrsnd_lang_languageid = ? AND cnclrsnd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationReasonDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationReason.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            cancellationReasonDescription = CancellationReasonDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationReasonDescription;
    }
    
    public CancellationReasonDescription getCancellationReasonDescription(CancellationReason cancellationReason, Language language) {
        return getCancellationReasonDescription(cancellationReason, language, EntityPermission.READ_ONLY);
    }
    
    public CancellationReasonDescription getCancellationReasonDescriptionForUpdate(CancellationReason cancellationReason, Language language) {
        return getCancellationReasonDescription(cancellationReason, language, EntityPermission.READ_WRITE);
    }
    
    public CancellationReasonDescriptionValue getCancellationReasonDescriptionValue(CancellationReasonDescription cancellationReasonDescription) {
        return cancellationReasonDescription == null? null: cancellationReasonDescription.getCancellationReasonDescriptionValue().clone();
    }
    
    public CancellationReasonDescriptionValue getCancellationReasonDescriptionValueForUpdate(CancellationReason cancellationReason, Language language) {
        return getCancellationReasonDescriptionValue(getCancellationReasonDescriptionForUpdate(cancellationReason, language));
    }
    
    private List<CancellationReasonDescription> getCancellationReasonDescriptionsByCancellationReason(CancellationReason cancellationReason, EntityPermission entityPermission) {
        List<CancellationReasonDescription> cancellationReasonDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasondescriptions, languages " +
                        "WHERE cnclrsnd_cnclrsn_cancellationreasonid = ? AND cnclrsnd_thrutime = ? AND cnclrsnd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasondescriptions " +
                        "WHERE cnclrsnd_cnclrsn_cancellationreasonid = ? AND cnclrsnd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationReasonDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationReason.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            cancellationReasonDescriptions = CancellationReasonDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationReasonDescriptions;
    }
    
    public List<CancellationReasonDescription> getCancellationReasonDescriptionsByCancellationReason(CancellationReason cancellationReason) {
        return getCancellationReasonDescriptionsByCancellationReason(cancellationReason, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationReasonDescription> getCancellationReasonDescriptionsByCancellationReasonForUpdate(CancellationReason cancellationReason) {
        return getCancellationReasonDescriptionsByCancellationReason(cancellationReason, EntityPermission.READ_WRITE);
    }
    
    public String getBestCancellationReasonDescription(CancellationReason cancellationReason, Language language) {
        String description;
        CancellationReasonDescription cancellationReasonDescription = getCancellationReasonDescription(cancellationReason, language);
        
        if(cancellationReasonDescription == null && !language.getIsDefault()) {
            cancellationReasonDescription = getCancellationReasonDescription(cancellationReason, getPartyControl().getDefaultLanguage());
        }
        
        if(cancellationReasonDescription == null) {
            description = cancellationReason.getLastDetail().getCancellationReasonName();
        } else {
            description = cancellationReasonDescription.getDescription();
        }
        
        return description;
    }
    
    public CancellationReasonDescriptionTransfer getCancellationReasonDescriptionTransfer(UserVisit userVisit, CancellationReasonDescription cancellationReasonDescription) {
        return getCancellationPolicyTransferCaches(userVisit).getCancellationReasonDescriptionTransferCache().getCancellationReasonDescriptionTransfer(cancellationReasonDescription);
    }
    
    public List<CancellationReasonDescriptionTransfer> getCancellationReasonDescriptionTransfersByCancellationReason(UserVisit userVisit, CancellationReason cancellationReason) {
        List<CancellationReasonDescription> cancellationReasonDescriptions = getCancellationReasonDescriptionsByCancellationReason(cancellationReason);
        List<CancellationReasonDescriptionTransfer> cancellationReasonDescriptionTransfers = new ArrayList<>(cancellationReasonDescriptions.size());
        
        cancellationReasonDescriptions.stream().forEach((cancellationReasonDescription) -> {
            cancellationReasonDescriptionTransfers.add(getCancellationPolicyTransferCaches(userVisit).getCancellationReasonDescriptionTransferCache().getCancellationReasonDescriptionTransfer(cancellationReasonDescription));
        });
        
        return cancellationReasonDescriptionTransfers;
    }
    
    public void updateCancellationReasonDescriptionFromValue(CancellationReasonDescriptionValue cancellationReasonDescriptionValue, BasePK updatedBy) {
        if(cancellationReasonDescriptionValue.hasBeenModified()) {
            CancellationReasonDescription cancellationReasonDescription = CancellationReasonDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationReasonDescriptionValue.getPrimaryKey());
            
            cancellationReasonDescription.setThruTime(session.START_TIME_LONG);
            cancellationReasonDescription.store();
            
            CancellationReason cancellationReason = cancellationReasonDescription.getCancellationReason();
            Language language = cancellationReasonDescription.getLanguage();
            String description = cancellationReasonDescriptionValue.getDescription();
            
            cancellationReasonDescription = CancellationReasonDescriptionFactory.getInstance().create(cancellationReason, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(cancellationReason.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationReasonDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteCancellationReasonDescription(CancellationReasonDescription cancellationReasonDescription, BasePK deletedBy) {
        cancellationReasonDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(cancellationReasonDescription.getCancellationReasonPK(), EventTypes.MODIFY.name(), cancellationReasonDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteCancellationReasonDescriptionsByCancellationReason(CancellationReason cancellationReason, BasePK deletedBy) {
        List<CancellationReasonDescription> cancellationReasonDescriptions = getCancellationReasonDescriptionsByCancellationReasonForUpdate(cancellationReason);
        
        cancellationReasonDescriptions.forEach((cancellationReasonDescription) -> 
                deleteCancellationReasonDescription(cancellationReasonDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Reason Types
    // --------------------------------------------------------------------------------
    
    public CancellationReasonType createCancellationReasonType(CancellationReason cancellationReason, CancellationType cancellationType, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        CancellationReasonType defaultCancellationReasonType = getDefaultCancellationReasonType(cancellationReason);
        boolean defaultFound = defaultCancellationReasonType != null;
        
        if(defaultFound && isDefault) {
            CancellationReasonTypeValue defaultCancellationReasonTypeValue = getDefaultCancellationReasonTypeValueForUpdate(cancellationReason);
            
            defaultCancellationReasonTypeValue.setIsDefault(Boolean.FALSE);
            updateCancellationReasonTypeFromValue(defaultCancellationReasonTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        CancellationReasonType cancellationReasonType = CancellationReasonTypeFactory.getInstance().create(cancellationReason, cancellationType,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(cancellationReason.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationReasonType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return cancellationReasonType;
    }
    
    private CancellationReasonType getCancellationReasonType(CancellationReason cancellationReason, CancellationType cancellationType, EntityPermission entityPermission) {
        CancellationReasonType cancellationReasonType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasontypes " +
                        "WHERE cnclrsntyp_cnclrsn_cancellationreasonid = ? AND cnclrsntyp_cncltyp_cancellationtypeid = ? AND cnclrsntyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasontypes " +
                        "WHERE cnclrsntyp_cnclrsn_cancellationreasonid = ? AND cnclrsntyp_cncltyp_cancellationtypeid = ? AND cnclrsntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationReasonTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationReason.getPrimaryKey().getEntityId());
            ps.setLong(2, cancellationType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            cancellationReasonType = CancellationReasonTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationReasonType;
    }
    
    public CancellationReasonType getCancellationReasonType(CancellationReason cancellationReason, CancellationType cancellationType) {
        return getCancellationReasonType(cancellationReason, cancellationType, EntityPermission.READ_ONLY);
    }
    
    public CancellationReasonType getCancellationReasonTypeForUpdate(CancellationReason cancellationReason, CancellationType cancellationType) {
        return getCancellationReasonType(cancellationReason, cancellationType, EntityPermission.READ_WRITE);
    }
    
    public CancellationReasonTypeValue getCancellationReasonTypeValueForUpdate(CancellationReason cancellationReason, CancellationType cancellationType) {
        CancellationReasonType cancellationReasonType = getCancellationReasonTypeForUpdate(cancellationReason, cancellationType);
        
        return cancellationReasonType == null? null: cancellationReasonType.getCancellationReasonTypeValue().clone();
    }
    
    private CancellationReasonType getDefaultCancellationReasonType(CancellationReason cancellationReason, EntityPermission entityPermission) {
        CancellationReasonType cancellationReasonType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasontypes " +
                        "WHERE cnclrsntyp_cnclrsn_cancellationreasonid = ? AND cnclrsntyp_isdefault = 1 AND cnclrsntyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasontypes " +
                        "WHERE cnclrsntyp_cnclrsn_cancellationreasonid = ? AND cnclrsntyp_isdefault = 1 AND cnclrsntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationReasonTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationReason.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            cancellationReasonType = CancellationReasonTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationReasonType;
    }
    
    public CancellationReasonType getDefaultCancellationReasonType(CancellationReason cancellationReason) {
        return getDefaultCancellationReasonType(cancellationReason, EntityPermission.READ_ONLY);
    }
    
    public CancellationReasonType getDefaultCancellationReasonTypeForUpdate(CancellationReason cancellationReason) {
        return getDefaultCancellationReasonType(cancellationReason, EntityPermission.READ_WRITE);
    }
    
    public CancellationReasonTypeValue getDefaultCancellationReasonTypeValueForUpdate(CancellationReason cancellationReason) {
        CancellationReasonType cancellationReasonType = getDefaultCancellationReasonTypeForUpdate(cancellationReason);
        
        return cancellationReasonType == null? null: cancellationReasonType.getCancellationReasonTypeValue().clone();
    }
    
    private List<CancellationReasonType> getCancellationReasonTypesByCancellationReason(CancellationReason cancellationReason, EntityPermission entityPermission) {
        List<CancellationReasonType> cancellationReasonTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasontypes, cancellationtypes, cancellationtypedetails, cancellationkinds, cancellationkinddetails " +
                        "WHERE cnclrsntyp_cnclrsn_cancellationreasonid = ? AND cnclrsntyp_thrutime = ? " +
                        "AND cnclrsntyp_cncltyp_cancellationtypeid = cncltyp_cancellationtypeid AND cncltyp_lastdetailid = cncltypdt_cancellationtypedetailid " +
                        "AND cncltypdt_cnclk_cancellationkindid = cnclk_cancellationkindid AND cnclk_lastdetailid = cnclkdt_cancellationkinddetailid " +
                        "ORDER BY cncltypdt_sortorder, cncltypdt_cancellationtypename, cnclkdt_sortorder, cnclkdt_cancellationkindname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasontypes " +
                        "WHERE cnclrsntyp_cnclrsn_cancellationreasonid = ? AND cnclrsntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationReasonTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationReason.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            cancellationReasonTypes = CancellationReasonTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationReasonTypes;
    }
    
    public List<CancellationReasonType> getCancellationReasonTypesByCancellationReason(CancellationReason cancellationReason) {
        return getCancellationReasonTypesByCancellationReason(cancellationReason, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationReasonType> getCancellationReasonTypesByCancellationReasonForUpdate(CancellationReason cancellationReason) {
        return getCancellationReasonTypesByCancellationReason(cancellationReason, EntityPermission.READ_WRITE);
    }
    
    private List<CancellationReasonType> getCancellationReasonTypesByCancellationType(CancellationType cancellationType, EntityPermission entityPermission) {
        List<CancellationReasonType> cancellationReasonTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasontypes, cancellationreasons, cancellationreasondetails " +
                        "WHERE cnclrsntyp_cncltyp_cancellationtypeid = ? AND cnclrsntyp_thrutime = ? " +
                        "AND cnclrsntyp_cnclrsn_cancellationreasonid = cnclrsn_cancellationreasonid AND cnclrsn_lastdetailid = cnclrsndt_cancellationreasondetailid " +
                        "ORDER BY cnclrsndt_sortorder, cnclrsndt_cancellationreasonname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationreasontypes " +
                        "WHERE cnclrsntyp_cncltyp_cancellationtypeid = ? AND cnclrsntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationReasonTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            cancellationReasonTypes = CancellationReasonTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationReasonTypes;
    }
    
    public List<CancellationReasonType> getCancellationReasonTypesByCancellationType(CancellationType cancellationType) {
        return getCancellationReasonTypesByCancellationType(cancellationType, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationReasonType> getCancellationReasonTypesByCancellationTypeForUpdate(CancellationType cancellationType) {
        return getCancellationReasonTypesByCancellationType(cancellationType, EntityPermission.READ_WRITE);
    }
    
    public List<CancellationReasonTypeTransfer> getCancellationReasonTypeTransfers(UserVisit userVisit, List<CancellationReasonType> cancellationReasonTypes) {
        List<CancellationReasonTypeTransfer> cancellationReasonTypeTransfers = new ArrayList<>(cancellationReasonTypes.size());
        CancellationReasonTypeTransferCache cancellationReasonTypeTransferCache = getCancellationPolicyTransferCaches(userVisit).getCancellationReasonTypeTransferCache();
        
        cancellationReasonTypes.forEach((cancellationReasonType) ->
                cancellationReasonTypeTransfers.add(cancellationReasonTypeTransferCache.getCancellationReasonTypeTransfer(cancellationReasonType))
        );
        
        return cancellationReasonTypeTransfers;
    }
    
    public List<CancellationReasonTypeTransfer> getCancellationReasonTypeTransfersByCancellationReason(UserVisit userVisit, CancellationReason cancellationReason) {
        return getCancellationReasonTypeTransfers(userVisit, getCancellationReasonTypesByCancellationReason(cancellationReason));
    }
    
    public List<CancellationReasonTypeTransfer> getCancellationReasonTypeTransfersByCancellationType(UserVisit userVisit, CancellationType cancellationType) {
        return getCancellationReasonTypeTransfers(userVisit, getCancellationReasonTypesByCancellationType(cancellationType));
    }
    
    public CancellationReasonTypeTransfer getCancellationReasonTypeTransfer(UserVisit userVisit, CancellationReasonType cancellationReasonType) {
        return getCancellationPolicyTransferCaches(userVisit).getCancellationReasonTypeTransferCache().getCancellationReasonTypeTransfer(cancellationReasonType);
    }
    
    private void updateCancellationReasonTypeFromValue(CancellationReasonTypeValue cancellationReasonTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(cancellationReasonTypeValue.hasBeenModified()) {
            CancellationReasonType cancellationReasonType = CancellationReasonTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationReasonTypeValue.getPrimaryKey());
            
            cancellationReasonType.setThruTime(session.START_TIME_LONG);
            cancellationReasonType.store();
            
            CancellationReason cancellationReason = cancellationReasonType.getCancellationReason(); // Not Updated
            CancellationReasonPK cancellationReasonPK = cancellationReason.getPrimaryKey(); // Not Updated
            CancellationTypePK cancellationTypePK = cancellationReasonType.getCancellationTypePK(); // Not Updated
            Boolean isDefault = cancellationReasonTypeValue.getIsDefault();
            Integer sortOrder = cancellationReasonTypeValue.getSortOrder();
            
            if(checkDefault) {
                CancellationReasonType defaultCancellationReasonType = getDefaultCancellationReasonType(cancellationReason);
                boolean defaultFound = defaultCancellationReasonType != null && !defaultCancellationReasonType.equals(cancellationReasonType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    CancellationReasonTypeValue defaultCancellationReasonTypeValue = getDefaultCancellationReasonTypeValueForUpdate(cancellationReason);
                    
                    defaultCancellationReasonTypeValue.setIsDefault(Boolean.FALSE);
                    updateCancellationReasonTypeFromValue(defaultCancellationReasonTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            cancellationReasonType = CancellationReasonTypeFactory.getInstance().create(cancellationReasonPK, cancellationTypePK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(cancellationReasonPK, EventTypes.MODIFY.name(), cancellationReasonType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateCancellationReasonTypeFromValue(CancellationReasonTypeValue cancellationReasonTypeValue, BasePK updatedBy) {
        updateCancellationReasonTypeFromValue(cancellationReasonTypeValue, true, updatedBy);
    }
    
    public void deleteCancellationReasonType(CancellationReasonType cancellationReasonType, BasePK deletedBy) {
        cancellationReasonType.setThruTime(session.START_TIME_LONG);
        cancellationReasonType.store();
        
        // Check for default, and pick one if necessary
        CancellationReason cancellationReason = cancellationReasonType.getCancellationReason();
        CancellationReasonType defaultCancellationReasonType = getDefaultCancellationReasonType(cancellationReason);
        if(defaultCancellationReasonType == null) {
            List<CancellationReasonType> cancellationReasonTypes = getCancellationReasonTypesByCancellationReasonForUpdate(cancellationReason);
            
            if(!cancellationReasonTypes.isEmpty()) {
                Iterator<CancellationReasonType> iter = cancellationReasonTypes.iterator();
                if(iter.hasNext()) {
                    defaultCancellationReasonType = iter.next();
                }
                CancellationReasonTypeValue cancellationReasonTypeValue = defaultCancellationReasonType.getCancellationReasonTypeValue().clone();
                
                cancellationReasonTypeValue.setIsDefault(Boolean.TRUE);
                updateCancellationReasonTypeFromValue(cancellationReasonTypeValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(cancellationReason.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationReasonType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteCancellationReasonTypes(List<CancellationReasonType> cancellationReasonTypes, BasePK deletedBy) {
        cancellationReasonTypes.forEach((cancellationReasonType) -> 
                deleteCancellationReasonType(cancellationReasonType, deletedBy)
        );
    }
    
    public void deleteCancellationReasonTypesByCancellationReason(CancellationReason cancellationReason, BasePK deletedBy) {
        deleteCancellationReasonTypes(getCancellationReasonTypesByCancellationReasonForUpdate(cancellationReason), deletedBy);
    }
    
    public void deleteCancellationReasonTypesByCancellationType(CancellationType cancellationType, BasePK deletedBy) {
        deleteCancellationReasonTypes(getCancellationReasonTypesByCancellationTypeForUpdate(cancellationType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Types
    // --------------------------------------------------------------------------------
    
    public CancellationType createCancellationType(CancellationKind cancellationKind, String cancellationTypeName, Sequence cancellationSequence, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        CancellationType defaultCancellationType = getDefaultCancellationType(cancellationKind);
        boolean defaultFound = defaultCancellationType != null;
        
        if(defaultFound && isDefault) {
            CancellationTypeDetailValue defaultCancellationTypeDetailValue = getDefaultCancellationTypeDetailValueForUpdate(cancellationKind);
            
            defaultCancellationTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateCancellationTypeFromValue(defaultCancellationTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        CancellationType cancellationType = CancellationTypeFactory.getInstance().create();
        CancellationTypeDetail cancellationTypeDetail = CancellationTypeDetailFactory.getInstance().create(session,
                cancellationType, cancellationKind, cancellationTypeName, cancellationSequence, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        cancellationType = CancellationTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                cancellationType.getPrimaryKey());
        cancellationType.setActiveDetail(cancellationTypeDetail);
        cancellationType.setLastDetail(cancellationTypeDetail);
        cancellationType.store();
        
        sendEventUsingNames(cancellationType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return cancellationType;
    }
    
    private List<CancellationType> getCancellationTypes(CancellationKind cancellationKind, EntityPermission entityPermission) {
        List<CancellationType> cancellationTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationtypes, cancellationtypedetails " +
                        "WHERE cncltyp_activedetailid = cncltypdt_cancellationtypedetailid AND cncltypdt_cnclk_cancellationkindid = ? " +
                        "ORDER BY cncltypdt_sortorder, cncltypdt_cancellationtypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationtypes, cancellationtypedetails " +
                        "WHERE cncltyp_activedetailid = cncltypdt_cancellationtypedetailid AND cncltypdt_cnclk_cancellationkindid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            
            cancellationTypes = CancellationTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationTypes;
    }
    
    public List<CancellationType> getCancellationTypes(CancellationKind cancellationKind) {
        return getCancellationTypes(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationType> getCancellationTypesForUpdate(CancellationKind cancellationKind) {
        return getCancellationTypes(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    private CancellationType getDefaultCancellationType(CancellationKind cancellationKind, EntityPermission entityPermission) {
        CancellationType cancellationType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationtypes, cancellationtypedetails " +
                        "WHERE cncltyp_activedetailid = cncltypdt_cancellationtypedetailid " +
                        "AND cncltypdt_cnclk_cancellationkindid = ? AND cncltypdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationtypes, cancellationtypedetails " +
                        "WHERE cncltyp_activedetailid = cncltypdt_cancellationtypedetailid " +
                        "AND cncltypdt_cnclk_cancellationkindid = ? AND cncltypdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            
            cancellationType = CancellationTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationType;
    }
    
    public CancellationType getDefaultCancellationType(CancellationKind cancellationKind) {
        return getDefaultCancellationType(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public CancellationType getDefaultCancellationTypeForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationType(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    public CancellationTypeDetailValue getDefaultCancellationTypeDetailValueForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationTypeForUpdate(cancellationKind).getLastDetailForUpdate().getCancellationTypeDetailValue().clone();
    }
    
    private CancellationType getCancellationTypeByName(CancellationKind cancellationKind, String cancellationTypeName, EntityPermission entityPermission) {
        CancellationType cancellationType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationtypes, cancellationtypedetails " +
                        "WHERE cncltyp_activedetailid = cncltypdt_cancellationtypedetailid " +
                        "AND cncltypdt_cnclk_cancellationkindid = ? AND cncltypdt_cancellationtypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationtypes, cancellationtypedetails " +
                        "WHERE cncltyp_activedetailid = cncltypdt_cancellationtypedetailid " +
                        "AND cncltypdt_cnclk_cancellationkindid = ? AND cncltypdt_cancellationtypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationKind.getPrimaryKey().getEntityId());
            ps.setString(2, cancellationTypeName);
            
            cancellationType = CancellationTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationType;
    }
    
    public CancellationType getCancellationTypeByName(CancellationKind cancellationKind, String cancellationTypeName) {
        return getCancellationTypeByName(cancellationKind, cancellationTypeName, EntityPermission.READ_ONLY);
    }
    
    public CancellationType getCancellationTypeByNameForUpdate(CancellationKind cancellationKind, String cancellationTypeName) {
        return getCancellationTypeByName(cancellationKind, cancellationTypeName, EntityPermission.READ_WRITE);
    }
    
    public CancellationTypeDetailValue getCancellationTypeDetailValueForUpdate(CancellationType cancellationType) {
        return cancellationType == null? null: cancellationType.getLastDetailForUpdate().getCancellationTypeDetailValue().clone();
    }
    
    public CancellationTypeDetailValue getCancellationTypeDetailValueByNameForUpdate(CancellationKind cancellationKind, String cancellationTypeName) {
        return getCancellationTypeDetailValueForUpdate(getCancellationTypeByNameForUpdate(cancellationKind, cancellationTypeName));
    }
    
    public CancellationTypeChoicesBean getCancellationTypeChoices(String defaultCancellationTypeChoice, Language language,
            boolean allowNullChoice, CancellationKind cancellationKind) {
        List<CancellationType> cancellationTypes = getCancellationTypes(cancellationKind);
        int size = cancellationTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCancellationTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var cancellationType : cancellationTypes) {
            CancellationTypeDetail cancellationTypeDetail = cancellationType.getLastDetail();
            String label = getBestCancellationTypeDescription(cancellationType, language);
            String value = cancellationTypeDetail.getCancellationTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultCancellationTypeChoice != null && defaultCancellationTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && cancellationTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CancellationTypeChoicesBean(labels, values, defaultValue);
    }
    
    public CancellationTypeTransfer getCancellationTypeTransfer(UserVisit userVisit, CancellationType cancellationType) {
        return getCancellationPolicyTransferCaches(userVisit).getCancellationTypeTransferCache().getCancellationTypeTransfer(cancellationType);
    }
    
    public List<CancellationTypeTransfer> getCancellationTypeTransfersByCancellationKind(UserVisit userVisit, CancellationKind cancellationKind) {
        List<CancellationType> cancellationTypes = getCancellationTypes(cancellationKind);
        List<CancellationTypeTransfer> cancellationTypeTransfers = new ArrayList<>(cancellationTypes.size());
        CancellationTypeTransferCache cancellationTypeTransferCache = getCancellationPolicyTransferCaches(userVisit).getCancellationTypeTransferCache();
        
        cancellationTypes.forEach((cancellationType) ->
                cancellationTypeTransfers.add(cancellationTypeTransferCache.getCancellationTypeTransfer(cancellationType))
        );
        
        return cancellationTypeTransfers;
    }
    
    private void updateCancellationTypeFromValue(CancellationTypeDetailValue cancellationTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(cancellationTypeDetailValue.hasBeenModified()) {
            CancellationType cancellationType = CancellationTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationTypeDetailValue.getCancellationTypePK());
            CancellationTypeDetail cancellationTypeDetail = cancellationType.getActiveDetailForUpdate();
            
            cancellationTypeDetail.setThruTime(session.START_TIME_LONG);
            cancellationTypeDetail.store();
            
            CancellationTypePK cancellationTypePK = cancellationTypeDetail.getCancellationTypePK();
            CancellationKind cancellationKind = cancellationTypeDetail.getCancellationKind();
            CancellationKindPK cancellationKindPK = cancellationKind.getPrimaryKey();
            String cancellationTypeName = cancellationTypeDetailValue.getCancellationTypeName();
            SequencePK cancellationSequencePK = cancellationTypeDetailValue.getCancellationSequencePK();
            Boolean isDefault = cancellationTypeDetailValue.getIsDefault();
            Integer sortOrder = cancellationTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                CancellationType defaultCancellationType = getDefaultCancellationType(cancellationKind);
                boolean defaultFound = defaultCancellationType != null && !defaultCancellationType.equals(cancellationType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    CancellationTypeDetailValue defaultCancellationTypeDetailValue = getDefaultCancellationTypeDetailValueForUpdate(cancellationKind);
                    
                    defaultCancellationTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateCancellationTypeFromValue(defaultCancellationTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            cancellationTypeDetail = CancellationTypeDetailFactory.getInstance().create(cancellationTypePK, cancellationKindPK, cancellationTypeName,
                    cancellationSequencePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            cancellationType.setActiveDetail(cancellationTypeDetail);
            cancellationType.setLastDetail(cancellationTypeDetail);
            
            sendEventUsingNames(cancellationTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateCancellationTypeFromValue(CancellationTypeDetailValue cancellationTypeDetailValue, BasePK updatedBy) {
        updateCancellationTypeFromValue(cancellationTypeDetailValue, true, updatedBy);
    }
    
    public void deleteCancellationType(CancellationType cancellationType, BasePK deletedBy) {
        deleteCancellationReasonTypesByCancellationType(cancellationType, deletedBy);
        deleteCancellationTypeDescriptionsByCancellationType(cancellationType, deletedBy);
        
        CancellationTypeDetail cancellationTypeDetail = cancellationType.getLastDetailForUpdate();
        cancellationTypeDetail.setThruTime(session.START_TIME_LONG);
        cancellationType.setActiveDetail(null);
        cancellationType.store();
        
        // Check for default, and pick one if necessary
        CancellationKind cancellationKind = cancellationTypeDetail.getCancellationKind();
        CancellationType defaultCancellationType = getDefaultCancellationType(cancellationKind);
        if(defaultCancellationType == null) {
            List<CancellationType> cancellationTypes = getCancellationTypesForUpdate(cancellationKind);
            
            if(!cancellationTypes.isEmpty()) {
                Iterator<CancellationType> iter = cancellationTypes.iterator();
                if(iter.hasNext()) {
                    defaultCancellationType = iter.next();
                }
                CancellationTypeDetailValue cancellationTypeDetailValue = Objects.requireNonNull(defaultCancellationType).getLastDetailForUpdate().getCancellationTypeDetailValue().clone();
                
                cancellationTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateCancellationTypeFromValue(cancellationTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(cancellationType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteCancellationTypesByCancellationKind(CancellationKind cancellationKind, BasePK deletedBy) {
        List<CancellationType> cancellationTypes = getCancellationTypesForUpdate(cancellationKind);
        
        cancellationTypes.forEach((cancellationType) -> 
                deleteCancellationType(cancellationType, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Type Descriptions
    // --------------------------------------------------------------------------------
    
    public CancellationTypeDescription createCancellationTypeDescription(CancellationType cancellationType, Language language, String description,
            BasePK createdBy) {
        CancellationTypeDescription cancellationTypeDescription = CancellationTypeDescriptionFactory.getInstance().create(cancellationType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(cancellationType.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return cancellationTypeDescription;
    }
    
    private CancellationTypeDescription getCancellationTypeDescription(CancellationType cancellationType, Language language, EntityPermission entityPermission) {
        CancellationTypeDescription cancellationTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationtypedescriptions " +
                        "WHERE cncltypd_cncltyp_cancellationtypeid = ? AND cncltypd_lang_languageid = ? AND cncltypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationtypedescriptions " +
                        "WHERE cncltypd_cncltyp_cancellationtypeid = ? AND cncltypd_lang_languageid = ? AND cncltypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            cancellationTypeDescription = CancellationTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationTypeDescription;
    }
    
    public CancellationTypeDescription getCancellationTypeDescription(CancellationType cancellationType, Language language) {
        return getCancellationTypeDescription(cancellationType, language, EntityPermission.READ_ONLY);
    }
    
    public CancellationTypeDescription getCancellationTypeDescriptionForUpdate(CancellationType cancellationType, Language language) {
        return getCancellationTypeDescription(cancellationType, language, EntityPermission.READ_WRITE);
    }
    
    public CancellationTypeDescriptionValue getCancellationTypeDescriptionValue(CancellationTypeDescription cancellationTypeDescription) {
        return cancellationTypeDescription == null? null: cancellationTypeDescription.getCancellationTypeDescriptionValue().clone();
    }
    
    public CancellationTypeDescriptionValue getCancellationTypeDescriptionValueForUpdate(CancellationType cancellationType, Language language) {
        return getCancellationTypeDescriptionValue(getCancellationTypeDescriptionForUpdate(cancellationType, language));
    }
    
    private List<CancellationTypeDescription> getCancellationTypeDescriptionsByCancellationType(CancellationType cancellationType, EntityPermission entityPermission) {
        List<CancellationTypeDescription> cancellationTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationtypedescriptions, languages " +
                        "WHERE cncltypd_cncltyp_cancellationtypeid = ? AND cncltypd_thrutime = ? AND cncltypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM cancellationtypedescriptions " +
                        "WHERE cncltypd_cncltyp_cancellationtypeid = ? AND cncltypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CancellationTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, cancellationType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            cancellationTypeDescriptions = CancellationTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return cancellationTypeDescriptions;
    }
    
    public List<CancellationTypeDescription> getCancellationTypeDescriptionsByCancellationType(CancellationType cancellationType) {
        return getCancellationTypeDescriptionsByCancellationType(cancellationType, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationTypeDescription> getCancellationTypeDescriptionsByCancellationTypeForUpdate(CancellationType cancellationType) {
        return getCancellationTypeDescriptionsByCancellationType(cancellationType, EntityPermission.READ_WRITE);
    }
    
    public String getBestCancellationTypeDescription(CancellationType cancellationType, Language language) {
        String description;
        CancellationTypeDescription cancellationTypeDescription = getCancellationTypeDescription(cancellationType, language);
        
        if(cancellationTypeDescription == null && !language.getIsDefault()) {
            cancellationTypeDescription = getCancellationTypeDescription(cancellationType, getPartyControl().getDefaultLanguage());
        }
        
        if(cancellationTypeDescription == null) {
            description = cancellationType.getLastDetail().getCancellationTypeName();
        } else {
            description = cancellationTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public CancellationTypeDescriptionTransfer getCancellationTypeDescriptionTransfer(UserVisit userVisit, CancellationTypeDescription cancellationTypeDescription) {
        return getCancellationPolicyTransferCaches(userVisit).getCancellationTypeDescriptionTransferCache().getCancellationTypeDescriptionTransfer(cancellationTypeDescription);
    }
    
    public List<CancellationTypeDescriptionTransfer> getCancellationTypeDescriptionTransfersByCancellationType(UserVisit userVisit, CancellationType cancellationType) {
        List<CancellationTypeDescription> cancellationTypeDescriptions = getCancellationTypeDescriptionsByCancellationType(cancellationType);
        List<CancellationTypeDescriptionTransfer> cancellationTypeDescriptionTransfers = new ArrayList<>(cancellationTypeDescriptions.size());
        
        cancellationTypeDescriptions.stream().forEach((cancellationTypeDescription) -> {
            cancellationTypeDescriptionTransfers.add(getCancellationPolicyTransferCaches(userVisit).getCancellationTypeDescriptionTransferCache().getCancellationTypeDescriptionTransfer(cancellationTypeDescription));
        });
        
        return cancellationTypeDescriptionTransfers;
    }
    
    public void updateCancellationTypeDescriptionFromValue(CancellationTypeDescriptionValue cancellationTypeDescriptionValue, BasePK updatedBy) {
        if(cancellationTypeDescriptionValue.hasBeenModified()) {
            CancellationTypeDescription cancellationTypeDescription = CancellationTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationTypeDescriptionValue.getPrimaryKey());
            
            cancellationTypeDescription.setThruTime(session.START_TIME_LONG);
            cancellationTypeDescription.store();
            
            CancellationType cancellationType = cancellationTypeDescription.getCancellationType();
            Language language = cancellationTypeDescription.getLanguage();
            String description = cancellationTypeDescriptionValue.getDescription();
            
            cancellationTypeDescription = CancellationTypeDescriptionFactory.getInstance().create(cancellationType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(cancellationType.getPrimaryKey(), EventTypes.MODIFY.name(), cancellationTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteCancellationTypeDescription(CancellationTypeDescription cancellationTypeDescription, BasePK deletedBy) {
        cancellationTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(cancellationTypeDescription.getCancellationTypePK(), EventTypes.MODIFY.name(), cancellationTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteCancellationTypeDescriptionsByCancellationType(CancellationType cancellationType, BasePK deletedBy) {
        List<CancellationTypeDescription> cancellationTypeDescriptions = getCancellationTypeDescriptionsByCancellationTypeForUpdate(cancellationType);
        
        cancellationTypeDescriptions.forEach((cancellationTypeDescription) -> 
                deleteCancellationTypeDescription(cancellationTypeDescription, deletedBy)
        );
    }
    
}
