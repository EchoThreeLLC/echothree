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

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.payment.common.PaymentConstants;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTypeTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountTransfer;
import com.echothree.model.control.payment.server.transfer.BillingAccountRoleTransferCache;
import com.echothree.model.control.payment.server.transfer.BillingAccountRoleTypeTransferCache;
import com.echothree.model.control.payment.server.transfer.BillingAccountTransferCache;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPK;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.common.pk.BillingAccountPK;
import com.echothree.model.data.payment.common.pk.BillingAccountRoleTypePK;
import com.echothree.model.data.payment.server.entity.BillingAccount;
import com.echothree.model.data.payment.server.entity.BillingAccountDetail;
import com.echothree.model.data.payment.server.entity.BillingAccountRole;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleType;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleTypeDescription;
import com.echothree.model.data.payment.server.entity.BillingAccountStatus;
import com.echothree.model.data.payment.server.factory.BillingAccountDetailFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountRoleFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountRoleTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountRoleTypeFactory;
import com.echothree.model.data.payment.server.factory.BillingAccountStatusFactory;
import com.echothree.model.data.payment.server.value.BillingAccountRoleValue;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillingControl
        extends BasePaymentControl {

    /** Creates a new instance of BillingControl */
    public BillingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Types
    // --------------------------------------------------------------------------------
    
    public BillingAccountRoleType createBillingAccountRoleType(String billingAccountRoleTypeName, Integer sortOrder) {
        return BillingAccountRoleTypeFactory.getInstance().create(billingAccountRoleTypeName, sortOrder);
    }
    
    public List<BillingAccountRoleType> getBillingAccountRoleTypes() {
        PreparedStatement ps = BillingAccountRoleTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM billingaccountroletypes " +
                "ORDER BY bllactrtyp_sortorder, bllactrtyp_billingaccountroletypename");
        
        return BillingAccountRoleTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public BillingAccountRoleType getBillingAccountRoleTypeByName(String billingAccountRoleTypeName) {
        BillingAccountRoleType billingAccountRoleType = null;
        
        try {
            PreparedStatement ps = BillingAccountRoleTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM billingaccountroletypes " +
                    "WHERE bllactrtyp_billingaccountroletypename = ?");
            
            ps.setString(1, billingAccountRoleTypeName);
            
            
            billingAccountRoleType = BillingAccountRoleTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountRoleType;
    }
    
    public BillingAccountRoleTypeTransfer getBillingAccountRoleTypeTransfer(UserVisit userVisit,
            BillingAccountRoleType billingAccountRoleType) {
        return getPaymentTransferCaches(userVisit).getBillingAccountRoleTypeTransferCache().getTransfer(billingAccountRoleType);
    }

    private List<BillingAccountRoleTypeTransfer> getBillingAccountRoleTypeTransfers(final UserVisit userVisit, final List<BillingAccountRoleType> billingAccountRoleTypes) {
        List<BillingAccountRoleTypeTransfer> billingAccountRoleTypeTransfers = new ArrayList<>(billingAccountRoleTypes.size());
        BillingAccountRoleTypeTransferCache billingAccountRoleTypeTransferCache = getPaymentTransferCaches(userVisit).getBillingAccountRoleTypeTransferCache();

        billingAccountRoleTypes.stream().forEach((billingAccountRoleType) -> {
            billingAccountRoleTypeTransfers.add(billingAccountRoleTypeTransferCache.getTransfer(billingAccountRoleType));
        });

            return billingAccountRoleTypeTransfers;
    }
    
    public List<BillingAccountRoleTypeTransfer> getBillingAccountRoleTypeTransfers(UserVisit userVisit) {
        return getBillingAccountRoleTypeTransfers(userVisit, getBillingAccountRoleTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Description
    // --------------------------------------------------------------------------------
    
    public BillingAccountRoleTypeDescription createBillingAccountRoleTypeDescription(BillingAccountRoleType billingAccountRoleType, Language language, String description) {
        return BillingAccountRoleTypeDescriptionFactory.getInstance().create(billingAccountRoleType, language, description);
    }
    
    public BillingAccountRoleTypeDescription getBillingAccountRoleTypeDescription(BillingAccountRoleType billingAccountRoleType, Language language) {
        BillingAccountRoleTypeDescription billingAccountRoleTypeDescription = null;
        
        try {
            PreparedStatement ps = BillingAccountRoleTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM billingaccountroletypedescriptions " +
                    "WHERE bllactrtypd_bllactrtyp_billingaccountroletypeid = ? AND bllactrtypd_lang_languageid = ?");
            
            ps.setLong(1, billingAccountRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            billingAccountRoleTypeDescription = BillingAccountRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountRoleTypeDescription;
    }
    
    public String getBestBillingAccountRoleTypeDescription(BillingAccountRoleType billingAccountRoleType, Language language) {
        String description;
        BillingAccountRoleTypeDescription billingAccountRoleTypeDescription = getBillingAccountRoleTypeDescription(billingAccountRoleType, language);
        
        if(billingAccountRoleTypeDescription == null && !language.getIsDefault()) {
            billingAccountRoleTypeDescription = getBillingAccountRoleTypeDescription(billingAccountRoleType, getPartyControl().getDefaultLanguage());
        }
        
        if(billingAccountRoleTypeDescription == null) {
            description = billingAccountRoleType.getBillingAccountRoleTypeName();
        } else {
            description = billingAccountRoleTypeDescription.getDescription();
        }
        
        return description;
    }

    // --------------------------------------------------------------------------------
    //   Billing Accounts
    // --------------------------------------------------------------------------------
    
    public BillingAccount createBillingAccount(final Party billFrom, final Currency currency, final String reference, final String description, final BasePK createdBy) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        Sequence sequence = sequenceControl.getDefaultSequence(billFrom.getLastDetail().getPartyType().getBillingAccountSequenceType());
        String billingAccountName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
         return createBillingAccount(billingAccountName, currency, reference, description, createdBy);
    }
    
    public BillingAccount createBillingAccount(String billingAccountName, Currency currency, String reference, String description, BasePK createdBy) {
        BillingAccount billingAccount = BillingAccountFactory.getInstance().create();
        BillingAccountDetail billingAccountDetail = BillingAccountDetailFactory.getInstance().create(billingAccount,
                billingAccountName, currency, reference, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        billingAccount = BillingAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                billingAccount.getPrimaryKey());
        billingAccount.setActiveDetail(billingAccountDetail);
        billingAccount.setLastDetail(billingAccountDetail);
        billingAccount.store();
        
        sendEventUsingNames(billingAccount.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        createBillingAccountStatus(billingAccount, Long.valueOf(0), null);
        
        return billingAccount;
    }
    
    private BillingAccount getBillingAccount(Party billFrom, Party billTo, Currency currency, EntityPermission entityPermission) {
        BillingAccount billingAccount = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_cur_currencyid = ? " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "AND bllactdt_bllact_billingaccountid = barb.bllactr_bllact_billingaccountid AND barb.bllactr_par_partyid = ? AND bartb.bllactrtyp_billingaccountroletypename = ? " +
                        "AND bartb.bllactrtyp_billingaccountroletypeid = barb.bllactr_bllactrtyp_billingaccountroletypeid AND barb.bllactr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_cur_currencyid = ? " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "AND bllactdt_bllact_billingaccountid = barb.bllactr_bllact_billingaccountid AND barb.bllactr_par_partyid = ? AND bartb.bllactrtyp_billingaccountroletypename = ? " +
                        "AND bartb.bllactrtyp_billingaccountroletypeid = barb.bllactr_bllactrtyp_billingaccountroletypeid AND barb.bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, currency.getPrimaryKey().getEntityId());
            ps.setLong(2, billFrom.getPrimaryKey().getEntityId());
            ps.setString(3, PaymentConstants.BillingAccountRoleType_BILL_FROM);
            ps.setLong(4, Session.MAX_TIME);
            ps.setLong(5, billTo.getPrimaryKey().getEntityId());
            ps.setString(6, PaymentConstants.BillingAccountRoleType_BILL_TO);
            ps.setLong(7, Session.MAX_TIME);
            
            billingAccount = BillingAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccount;
    }
    
    public BillingAccount getBillingAccount(Party billFrom, Party billTo, Currency currency) {
        return getBillingAccount(billFrom, billTo, currency, EntityPermission.READ_ONLY);
    }
    
    public BillingAccount getBillingAccountForUpdate(Party billFrom, Party billTo, Currency currency) {
        return getBillingAccount(billFrom, billTo, currency, EntityPermission.READ_WRITE);
    }
    
    private List<BillingAccount> getBillingAccountsByBillFrom(Party billFrom, EntityPermission entityPermission) {
        List<BillingAccount> billingAccounts = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb, currencies, parties, partydetails " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "AND bllactdt_bllact_billingaccountid = barb.bllactr_bllact_billingaccountid AND bartb.bllactrtyp_billingaccountroletypename = ? " +
                        "AND bartb.bllactrtyp_billingaccountroletypeid = barb.bllactr_bllactrtyp_billingaccountroletypeid AND barb.bllactr_thrutime = ? " +
                        "AND bllactdt_cur_currencyid = cur_currencyid AND barb.bllactr_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "ORDER BY pardt_partyname, cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billFrom.getPrimaryKey().getEntityId());
            ps.setString(2, PaymentConstants.BillingAccountRoleType_BILL_FROM);
            ps.setLong(3, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setString(4, PaymentConstants.BillingAccountRoleType_BILL_TO);
                ps.setLong(5, Session.MAX_TIME);
            }
            
            billingAccounts = BillingAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccounts;
    }
    
    public List<BillingAccount> getBillingAccountsByBillFrom(Party billFrom) {
        return getBillingAccountsByBillFrom(billFrom, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccount> getBillingAccountsByBillFromForUpdate(Party billFrom) {
        return getBillingAccountsByBillFrom(billFrom, EntityPermission.READ_WRITE);
    }
    
    private List<BillingAccount> getBillingAccountsByBillTo(Party billTo, EntityPermission entityPermission) {
        List<BillingAccount> billingAccounts = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb, currencies, parties, partydetails " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "AND bllactdt_bllact_billingaccountid = barb.bllactr_bllact_billingaccountid AND bartb.bllactrtyp_billingaccountroletypename = ? " +
                        "AND bartb.bllactrtyp_billingaccountroletypeid = barb.bllactr_bllactrtyp_billingaccountroletypeid AND barb.bllactr_thrutime = ? " +
                        "AND bllactdt_cur_currencyid = cur_currencyid AND barb.bllactr_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "ORDER BY pardt_partyname, cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails, billingaccountroletypes barta, billingaccountroles bara, billingaccountroletypes bartb, billingaccountroles barb " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactdt_bllact_billingaccountid = bara.bllactr_bllact_billingaccountid AND bara.bllactr_par_partyid = ? AND barta.bllactrtyp_billingaccountroletypename = ? " +
                        "AND barta.bllactrtyp_billingaccountroletypeid = bara.bllactr_bllactrtyp_billingaccountroletypeid AND bara.bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billTo.getPrimaryKey().getEntityId());
            ps.setString(2, PaymentConstants.BillingAccountRoleType_BILL_TO);
            ps.setLong(3, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setString(4, PaymentConstants.BillingAccountRoleType_BILL_FROM);
                ps.setLong(5, Session.MAX_TIME);
            }
            
            billingAccounts = BillingAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccounts;
    }
    
    public List<BillingAccount> getBillingAccountsByBillTo(Party billTo) {
        return getBillingAccountsByBillTo(billTo, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccount> getBillingAccountsByBillToForUpdate(Party billTo) {
        return getBillingAccountsByBillTo(billTo, EntityPermission.READ_WRITE);
    }
    
    private BillingAccount getBillingAccountByName(String billingAccountName, EntityPermission entityPermission) {
        BillingAccount billingAccount = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid AND bllactdt_billingaccountname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccounts, billingaccountdetails " +
                        "WHERE bllact_activedetailid = bllactdt_billingaccountdetailid AND bllactdt_billingaccountname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, billingAccountName);
            
            billingAccount = BillingAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccount;
    }
    
    public BillingAccount getBillingAccountByName(String billingAccountName) {
        return getBillingAccountByName(billingAccountName, EntityPermission.READ_ONLY);
    }
    
    public BillingAccount getBillingAccountByNameForUpdate(String billingAccountName) {
        return getBillingAccountByName(billingAccountName, EntityPermission.READ_WRITE);
    }
    
    public BillingAccountTransfer getBillingAccountTransfer(UserVisit userVisit, BillingAccount billingAccount) {
        return getPaymentTransferCaches(userVisit).getBillingAccountTransferCache().getTransfer(billingAccount);
    }
    
    public List<BillingAccountTransfer> getBillingAccountTransfers(UserVisit userVisit, List<BillingAccount> billingAccounts) {
        List<BillingAccountTransfer> billingAccountTransfers = new ArrayList<>(billingAccounts.size());
        BillingAccountTransferCache billingAccountTransferCache = getPaymentTransferCaches(userVisit).getBillingAccountTransferCache();
        
        billingAccounts.stream().forEach((billingAccount) -> {
            billingAccountTransfers.add(billingAccountTransferCache.getTransfer(billingAccount));
        });
        
        return billingAccountTransfers;
    }
    
    public List<BillingAccountTransfer> getBillingAccountTransfersByBillFrom(UserVisit userVisit, Party billFrom) {
        return getBillingAccountTransfers(userVisit, getBillingAccountsByBillFrom(billFrom));
    }
    
    public List<BillingAccountTransfer> getBillingAccountTransfersByBillTo(UserVisit userVisit, Party billTo) {
        return getBillingAccountTransfers(userVisit, getBillingAccountsByBillTo(billTo));
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Statuses
    // --------------------------------------------------------------------------------
    
    public BillingAccountStatus createBillingAccountStatus(BillingAccount billingAccount, Long creditLimit,
            Long potentialCreditLimit) {
        return BillingAccountStatusFactory.getInstance().create(billingAccount, creditLimit, potentialCreditLimit);
    }
    
    private BillingAccountStatus getBillingAccountStatus(BillingAccount billingAccount, EntityPermission entityPermission) {
        BillingAccountStatus billingAccountStatus = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountstatuses " +
                        "WHERE bllactst_bllact_billingaccountid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountstatuses " +
                        "WHERE bllactst_bllact_billingaccountid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billingAccount.getPrimaryKey().getEntityId());
            
            billingAccountStatus = BillingAccountStatusFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountStatus;
    }
    
    public BillingAccountStatus getBillingAccountStatus(BillingAccount billingAccount) {
        return getBillingAccountStatus(billingAccount, EntityPermission.READ_ONLY);
    }
    
    public BillingAccountStatus getBillingAccountStatusForUpdate(BillingAccount billingAccount) {
        return getBillingAccountStatus(billingAccount, EntityPermission.READ_WRITE);
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Roles
    // --------------------------------------------------------------------------------
    
    public BillingAccountRole createBillingAccountRoleUsingNames(BillingAccount billingAccount, Party party, PartyContactMechanism partyContactMechanism,
            String billingAccountRoleTypeName, BasePK createdBy) {
        BillingAccountRoleType billingAccountRoleType = getBillingAccountRoleTypeByName(billingAccountRoleTypeName);
        
        return createBillingAccountRole(billingAccount, party, partyContactMechanism, billingAccountRoleType, createdBy);
    }
    
    public BillingAccountRole createBillingAccountRole(BillingAccount billingAccount, Party party, PartyContactMechanism partyContactMechanism,
            BillingAccountRoleType billingAccountRoleType, BasePK createdBy) {
        BillingAccountRole billingAccountRole = BillingAccountRoleFactory.getInstance().create(billingAccount, party, partyContactMechanism,
                billingAccountRoleType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(billingAccount.getPrimaryKey(), EventTypes.MODIFY.name(), billingAccountRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return billingAccountRole;
    }
    
    private BillingAccountRole getBillingAccountRole(BillingAccount billingAccount, BillingAccountRoleType billingAccountRoleType, EntityPermission entityPermission) {
        BillingAccountRole billingAccountRole = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles " +
                        "WHERE bllactr_bllact_billingaccountid = ? AND bllactr_bllactrtyp_billingaccountroletypeid = ? AND bllactr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles " +
                        "WHERE bllactr_bllact_billingaccountid = ? AND bllactr_bllactrtyp_billingaccountroletypeid = ? AND bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billingAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, billingAccountRoleType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            billingAccountRole = BillingAccountRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountRole;
    }
    
    public BillingAccountRole getBillingAccountRole(BillingAccount billingAccount, BillingAccountRoleType billingAccountRoleType) {
        return getBillingAccountRole(billingAccount, billingAccountRoleType, EntityPermission.READ_ONLY);
    }
    
    public BillingAccountRole getBillingAccountRoleUsingNames(BillingAccount billingAccount, String billingAccountRoleTypeName) {
        BillingAccountRoleType billingAccountRoleType = getBillingAccountRoleTypeByName(billingAccountRoleTypeName);
        
        return getBillingAccountRole(billingAccount, billingAccountRoleType);
    }
    
    public BillingAccountRole getBillingAccountRoleForUpdate(BillingAccount billingAccount, BillingAccountRoleType billingAccountRoleType) {
        return getBillingAccountRole(billingAccount, billingAccountRoleType, EntityPermission.READ_WRITE);
    }
    
    public BillingAccountRoleValue getBillingAccountRoleValue(BillingAccountRole billingAccountRole) {
        return billingAccountRole == null? null: billingAccountRole.getBillingAccountRoleValue().clone();
    }
    
    public BillingAccountRoleValue getBillingAccountRoleValueForUpdate(BillingAccount billingAccount, BillingAccountRoleType billingAccountRoleType) {
        return getBillingAccountRoleValue(getBillingAccountRoleForUpdate(billingAccount, billingAccountRoleType));
    }
    
    private List<BillingAccountRole> getBillingAccountRolesByBillingAccount(BillingAccount billingAccount, EntityPermission entityPermission) {
        List<BillingAccountRole> billingAccountRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles, billingaccountroletypes, parties, partydetails " +
                        "WHERE bllactr_bllact_billingaccountid = ? AND bllactr_thrutime = ? " +
                        "AND bllactr_bllactrtyp_billingaccountroletypeid = bllactrtyp_billingaccountroletypeid " +
                        "AND bllactr_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "ORDER BY bllactrtyp_sortorder, pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles " +
                        "WHERE bllactr_bllact_billingAccountid = ? AND bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, billingAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            billingAccountRoles = BillingAccountRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountRoles;
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByBillingAccount(BillingAccount billingAccount) {
        return getBillingAccountRolesByBillingAccount(billingAccount, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByBillingAccountForUpdate(BillingAccount billingAccount) {
        return getBillingAccountRolesByBillingAccount(billingAccount, EntityPermission.READ_WRITE);
    }
    
    private List<BillingAccountRole> getBillingAccountRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism, EntityPermission entityPermission) {
        List<BillingAccountRole> billingAccountRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles, billingaccounts, billingaccountdetails, billingaccountroletypes " +
                        "WHERE bllactr_pcm_partycontactmechanismid = ? AND bllactr_thrutime = ? " +
                        "AND bllactr_bllact_billingaccountid = bllact_billingaccountid AND bllact_lastdetailid = bllactdt_billingaccountdetailid " +
                        "AND bllactr_bllactrtyp_billingaccountroletypeid = bllactrtyp_billingaccountroletypeid " +
                        "ORDER BY bllactdt_billingaccountname, bllactrtyp_sortorder, bllactrtyp_billingaccountroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM billingaccountroles " +
                        "WHERE bllactr_pcm_partycontactmechanismid = ? AND bllactr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BillingAccountRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyContactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            billingAccountRoles = BillingAccountRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return billingAccountRoles;
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism) {
        return getBillingAccountRolesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<BillingAccountRole> getBillingAccountRolesByPartyContactMechanismForUpdate(PartyContactMechanism partyContactMechanism) {
        return getBillingAccountRolesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public BillingAccountRoleTransfer getBillingAccountRoleTransfer(UserVisit userVisit, BillingAccountRole billingAccountRole) {
        return getPaymentTransferCaches(userVisit).getBillingAccountRoleTransferCache().getTransfer(billingAccountRole);
    }
    
    public List<BillingAccountRoleTransfer> getBillingAccountRoleTransfers(UserVisit userVisit, List<BillingAccountRole> billingAccountRoles) {
        List<BillingAccountRoleTransfer> billingAccountRoleTransfers = new ArrayList<>(billingAccountRoles.size());
        BillingAccountRoleTransferCache billingAccountRoleTransferCache = getPaymentTransferCaches(userVisit).getBillingAccountRoleTransferCache();
        
        billingAccountRoles.stream().forEach((billingAccountRole) -> {
            billingAccountRoleTransfers.add(billingAccountRoleTransferCache.getTransfer(billingAccountRole));
        });
        
        return billingAccountRoleTransfers;
    }
    
    public List<BillingAccountRoleTransfer> getBillingAccountRoleTransfersByBillingAccount(UserVisit userVisit, BillingAccount billingAccount) {
        return getBillingAccountRoleTransfers(userVisit, getBillingAccountRolesByBillingAccount(billingAccount));
    }
    
    public void updateBillingAccountRoleFromValue(BillingAccountRoleValue billingAccountRoleValue, BasePK updatedBy) {
        if(billingAccountRoleValue.hasBeenModified()) {
            BillingAccountRole billingAccountRole = BillingAccountRoleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     billingAccountRoleValue.getPrimaryKey());
            
            billingAccountRole.setThruTime(session.START_TIME_LONG);
            billingAccountRole.store();
            
            BillingAccountPK billingAccountPK = billingAccountRole.getBillingAccountPK(); // Not updated
            PartyPK partyPK = billingAccountRole.getPartyPK(); // Not updated
            PartyContactMechanismPK partyContactMechanismPK = billingAccountRoleValue.getPartyContactMechanismPK();
            BillingAccountRoleTypePK billingAccountRoleTypePK = billingAccountRole.getBillingAccountRoleTypePK(); // Not updated
            
            billingAccountRole = BillingAccountRoleFactory.getInstance().create(billingAccountPK, partyPK, partyContactMechanismPK, billingAccountRoleTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(billingAccountPK, EventTypes.MODIFY.name(), billingAccountRole.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteBillingAccountRole(BillingAccountRole billingAccountRole, BasePK deletedBy) {
        billingAccountRole.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(billingAccountRole.getBillingAccountPK(), EventTypes.MODIFY.name(), billingAccountRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteBillingAccountRolesByBillingAccount(BillingAccount billingAccount, BasePK deletedBy) {
        getBillingAccountRolesByBillingAccountForUpdate(billingAccount).stream().forEach((billingAccountRole) -> {
            deleteBillingAccountRole(billingAccountRole, deletedBy);
        });
    }
    
    public void deleteBillingAccountRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        getBillingAccountRolesByPartyContactMechanismForUpdate(partyContactMechanism).stream().forEach((billingAccountRole) -> {
            deleteBillingAccountRole(billingAccountRole, deletedBy);
        });
    }

}
