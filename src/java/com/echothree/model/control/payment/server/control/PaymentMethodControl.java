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
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.order.server.control.OrderPaymentPreferenceControl;
import com.echothree.model.control.payment.common.choice.PaymentMethodChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentMethodDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.model.control.payment.server.transfer.PaymentMethodDescriptionTransferCache;
import com.echothree.model.control.payment.server.transfer.PaymentMethodTransferCache;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentMethodPK;
import com.echothree.model.data.payment.common.pk.PaymentMethodTypePK;
import com.echothree.model.data.payment.common.pk.PaymentProcessorPK;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodCheck;
import com.echothree.model.data.payment.server.entity.PaymentMethodCreditCard;
import com.echothree.model.data.payment.server.entity.PaymentMethodDescription;
import com.echothree.model.data.payment.server.entity.PaymentMethodDetail;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.factory.PaymentMethodCheckFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodCreditCardFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodFactory;
import com.echothree.model.data.payment.server.value.PaymentMethodCheckValue;
import com.echothree.model.data.payment.server.value.PaymentMethodCreditCardValue;
import com.echothree.model.data.payment.server.value.PaymentMethodDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentMethodDetailValue;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PaymentMethodControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentMethodControl */
    public PaymentMethodControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Methods
    // --------------------------------------------------------------------------------
    
    public PaymentMethod createPaymentMethod(String paymentMethodName, PaymentMethodType paymentMethodType, PaymentProcessor paymentProcessor,
            Selector itemSelector, Selector salesOrderItemSelector, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        PaymentMethod defaultPaymentMethod = getDefaultPaymentMethod();
        boolean defaultFound = defaultPaymentMethod != null;
        
        if(defaultFound && isDefault) {
            PaymentMethodDetailValue defaultPaymentMethodDetailValue = getDefaultPaymentMethodDetailValueForUpdate();
            
            defaultPaymentMethodDetailValue.setIsDefault(Boolean.FALSE);
            updatePaymentMethodFromValue(defaultPaymentMethodDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        PaymentMethod paymentMethod = PaymentMethodFactory.getInstance().create();
        PaymentMethodDetail paymentMethodDetail = PaymentMethodDetailFactory.getInstance().create(paymentMethod, paymentMethodName, paymentMethodType,
                paymentProcessor, itemSelector, salesOrderItemSelector, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        paymentMethod = PaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentMethod.getPrimaryKey());
        paymentMethod.setActiveDetail(paymentMethodDetail);
        paymentMethod.setLastDetail(paymentMethodDetail);
        paymentMethod.store();
        
        sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return paymentMethod;
    }
    
    public PaymentMethodDetailValue getPaymentMethodDetailValueForUpdate(PaymentMethod paymentMethod) {
        return paymentMethod.getLastDetailForUpdate().getPaymentMethodDetailValue().clone();
    }
    
    public PaymentMethod getPaymentMethodByName(String paymentMethodName, EntityPermission entityPermission) {
        PaymentMethod paymentMethod = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_paymentmethodname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_paymentmethodname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentMethodFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, paymentMethodName);
            
            paymentMethod = PaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethod;
    }
    
    public PaymentMethod getPaymentMethodByName(String paymentMethodName) {
        return getPaymentMethodByName(paymentMethodName, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethod getPaymentMethodByNameForUpdate(String paymentMethodName) {
        return getPaymentMethodByName(paymentMethodName, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodDetailValue getPaymentMethodDetailValueByNameForUpdate(String paymentMethodName) {
        return getPaymentMethodDetailValueForUpdate(getPaymentMethodByNameForUpdate(paymentMethodName));
    }
    
    private List<PaymentMethod> getPaymentMethods(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid " +
                    "ORDER BY pmdt_sortorder, pmdt_paymentmethodname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PaymentMethodFactory.getInstance().prepareStatement(query);
        
        return PaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<PaymentMethod> getPaymentMethods() {
        return getPaymentMethods(EntityPermission.READ_ONLY);
    }
    
    public List<PaymentMethod> getPaymentMethodsForUpdate() {
        return getPaymentMethods(EntityPermission.READ_WRITE);
    }
    
    private List<PaymentMethod> getPaymentMethodsByPaymentMethodType(PaymentMethodType paymentMethodType, EntityPermission entityPermission) {
        List<PaymentMethod> paymentMethod = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pmtyp_paymentmethodtypeid = ? " +
                        "ORDER BY pmdt_sortorder, pmdt_paymentmethodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pmtyp_paymentmethodtypeid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = PaymentMethodFactory.getInstance().prepareStatement(query);

            ps.setLong(1, paymentMethodType.getPrimaryKey().getEntityId());

            paymentMethod = PaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return paymentMethod;
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentMethodType(PaymentMethodType paymentMethodType) {
        return getPaymentMethodsByPaymentMethodType(paymentMethodType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentMethodTypeForUpdate(PaymentMethodType paymentMethodType) {
        return getPaymentMethodsByPaymentMethodType(paymentMethodType, EntityPermission.READ_WRITE);
    }

    private List<PaymentMethod> getPaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor, EntityPermission entityPermission) {
        List<PaymentMethod> paymentMethod = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pprc_paymentprocessorid = ? " +
                        "ORDER BY pmdt_sortorder, pmdt_paymentmethodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pprc_paymentprocessorid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = PaymentMethodFactory.getInstance().prepareStatement(query);

            ps.setLong(1, paymentProcessor.getPrimaryKey().getEntityId());

            paymentMethod = PaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return paymentMethod;
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor) {
        return getPaymentMethodsByPaymentProcessor(paymentProcessor, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentProcessorForUpdate(PaymentProcessor paymentProcessor) {
        return getPaymentMethodsByPaymentProcessor(paymentProcessor, EntityPermission.READ_WRITE);
    }

    private PaymentMethod getDefaultPaymentMethod(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PaymentMethodFactory.getInstance().prepareStatement(query);
        
        return PaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public PaymentMethod getDefaultPaymentMethod() {
        return getDefaultPaymentMethod(EntityPermission.READ_ONLY);
    }
    
    public PaymentMethod getDefaultPaymentMethodForUpdate() {
        return getDefaultPaymentMethod(EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodDetailValue getDefaultPaymentMethodDetailValueForUpdate() {
        return getPaymentMethodDetailValueForUpdate(getDefaultPaymentMethodForUpdate());
    }
    
    public PaymentMethodChoicesBean getPaymentMethodChoices(String defaultPaymentMethodChoice, Language language, boolean allowNullChoice,
            List<PaymentMethod> paymentMethods) {
        int size = paymentMethods.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }

        for(PaymentMethod paymentMethod: paymentMethods) {
            PaymentMethodDetail paymentMethodDetail = paymentMethod.getLastDetail();
            String label = getBestPaymentMethodDescription(paymentMethod, language);
            String value = paymentMethodDetail.getPaymentMethodName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultPaymentMethodChoice == null? false: defaultPaymentMethodChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && paymentMethodDetail.getIsDefault()))
                defaultValue = value;
        }

        return new PaymentMethodChoicesBean(labels, values, defaultValue);
    }

    public PaymentMethodChoicesBean getPaymentMethodChoices(String defaultPaymentMethodChoice, Language language, boolean allowNullChoice) {
        return getPaymentMethodChoices(defaultPaymentMethodChoice, language, allowNullChoice, getPaymentMethods());
    }

    public PaymentMethodChoicesBean getPaymentMethodChoicesByPaymentMethodType(String defaultPaymentMethodChoice, Language language, boolean allowNullChoice,
            PaymentMethodType paymentMethodType) {
        return getPaymentMethodChoices(defaultPaymentMethodChoice, language, allowNullChoice, getPaymentMethodsByPaymentMethodType(paymentMethodType));
    }

    public PaymentMethodTransfer getPaymentMethodTransfer(UserVisit userVisit, PaymentMethod paymentMethod) {
        return getPaymentTransferCaches(userVisit).getPaymentMethodTransferCache().getTransfer(paymentMethod);
    }
    
    public List<PaymentMethodTransfer> getPaymentMethodTransfers(UserVisit userVisit, List<PaymentMethod> paymentMethods) {
        List<PaymentMethodTransfer> paymentMethodTransfers = new ArrayList<>(paymentMethods.size());
        PaymentMethodTransferCache paymentMethodTransferCache = getPaymentTransferCaches(userVisit).getPaymentMethodTransferCache();

        paymentMethods.stream().forEach((paymentMethod) -> {
            paymentMethodTransfers.add(paymentMethodTransferCache.getTransfer(paymentMethod));
        });

        return paymentMethodTransfers;
    }

    public List<PaymentMethodTransfer> getPaymentMethodTransfers(UserVisit userVisit) {
        return getPaymentMethodTransfers(userVisit, getPaymentMethods());
    }

    public List<PaymentMethodTransfer> getPaymentMethodTransfersByPaymentMethodType(UserVisit userVisit, PaymentMethodType paymentMethodType) {
        return getPaymentMethodTransfers(userVisit, getPaymentMethodsByPaymentMethodType(paymentMethodType));
    }

    private void updatePaymentMethodFromValue(PaymentMethodDetailValue paymentMethodDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(paymentMethodDetailValue.hasBeenModified()) {
            PaymentMethod paymentMethod = PaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodDetailValue.getPaymentMethodPK());
            PaymentMethodDetail paymentMethodDetail = paymentMethod.getActiveDetailForUpdate();
            
            paymentMethodDetail.setThruTime(session.START_TIME_LONG);
            paymentMethodDetail.store();
            
            PaymentMethodPK paymentMethodPK = paymentMethodDetail.getPaymentMethodPK(); // Not updated
            String paymentMethodName = paymentMethodDetailValue.getPaymentMethodName();
            PaymentMethodTypePK paymentMethodTypePK = paymentMethodDetail.getPaymentMethodTypePK(); // Not updated
            PaymentProcessorPK paymentProcessorPK = paymentMethodDetailValue.getPaymentProcessorPK();
            SelectorPK itemSelectorPK = paymentMethodDetailValue.getItemSelectorPK();
            SelectorPK salesOrderItemSelectorPK = paymentMethodDetailValue.getSalesOrderItemSelectorPK();
            Boolean isDefault = paymentMethodDetailValue.getIsDefault();
            Integer sortOrder = paymentMethodDetailValue.getSortOrder();
            
            if(checkDefault) {
                PaymentMethod defaultPaymentMethod = getDefaultPaymentMethod();
                boolean defaultFound = defaultPaymentMethod != null && !defaultPaymentMethod.equals(paymentMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PaymentMethodDetailValue defaultPaymentMethodDetailValue = getDefaultPaymentMethodDetailValueForUpdate();
                    
                    defaultPaymentMethodDetailValue.setIsDefault(Boolean.FALSE);
                    updatePaymentMethodFromValue(defaultPaymentMethodDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            paymentMethodDetail = PaymentMethodDetailFactory.getInstance().create(paymentMethodPK, paymentMethodName, paymentMethodTypePK, paymentProcessorPK,
                    itemSelectorPK, salesOrderItemSelectorPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            paymentMethod.setActiveDetail(paymentMethodDetail);
            paymentMethod.setLastDetail(paymentMethodDetail);
            
            sendEventUsingNames(paymentMethodPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updatePaymentMethodFromValue(PaymentMethodDetailValue paymentMethodDetailValue, BasePK updatedBy) {
        updatePaymentMethodFromValue(paymentMethodDetailValue, true, updatedBy);
    }
    
    public void deletePaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {
        var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        var orderPaymentPreferenceControl = (OrderPaymentPreferenceControl)Session.getModelController(OrderPaymentPreferenceControl.class);
        var partyPaymentMethodControl = (PartyPaymentMethodControl)Session.getModelController(PartyPaymentMethodControl.class);

        customerControl.deleteCustomerTypePaymentMethodsByPaymentMethod(paymentMethod, deletedBy);
        orderPaymentPreferenceControl.deleteOrderPaymentPreferencesByPaymentMethod(paymentMethod, deletedBy);
        partyPaymentMethodControl.deletePartyPaymentMethodsByPaymentMethod(paymentMethod, deletedBy);
        deletePaymentMethodDescriptionsByPaymentMethod(paymentMethod, deletedBy);
        
        PaymentMethodDetail paymentMethodDetail = paymentMethod.getLastDetailForUpdate();
        paymentMethodDetail.setThruTime(session.START_TIME_LONG);
        paymentMethodDetail.store();
        paymentMethod.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        PaymentMethod defaultPaymentMethod = getDefaultPaymentMethod();
        if(defaultPaymentMethod == null) {
            List<PaymentMethod> paymentMethods = getPaymentMethodsForUpdate();
            
            if(!paymentMethods.isEmpty()) {
                Iterator<PaymentMethod> iter = paymentMethods.iterator();
                if(iter.hasNext()) {
                    defaultPaymentMethod = iter.next();
                }
                PaymentMethodDetailValue paymentMethodDetailValue = defaultPaymentMethod.getLastDetailForUpdate().getPaymentMethodDetailValue().clone();
                
                paymentMethodDetailValue.setIsDefault(Boolean.TRUE);
                updatePaymentMethodFromValue(paymentMethodDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deletePaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor, BasePK deletedBy) {
        List<PaymentMethod> paymentMethods = getPaymentMethodsByPaymentProcessorForUpdate(paymentProcessor);
        
        paymentMethods.stream().forEach((paymentMethod) -> {
            deletePaymentMethod(paymentMethod, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Descriptions
    // --------------------------------------------------------------------------------
    
    public PaymentMethodDescription createPaymentMethodDescription(PaymentMethod paymentMethod, Language language,
            String description, BasePK createdBy) {
        PaymentMethodDescription paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().create(session,
                paymentMethod, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(), paymentMethodDescription.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return paymentMethodDescription;
    }
    
    private PaymentMethodDescription getPaymentMethodDescription(PaymentMethod paymentMethod, Language language,
            EntityPermission entityPermission) {
        PaymentMethodDescription paymentMethodDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_lang_languageid = ? AND pmd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_lang_languageid = ? AND pmd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentMethodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodDescription;
    }
    
    public PaymentMethodDescription getPaymentMethodDescription(PaymentMethod paymentMethod, Language language) {
        return getPaymentMethodDescription(paymentMethod, language, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodDescription getPaymentMethodDescriptionForUpdate(PaymentMethod paymentMethod, Language language) {
        return getPaymentMethodDescription(paymentMethod, language, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodDescriptionValue getPaymentMethodDescriptionValue(PaymentMethodDescription paymentMethodDescription) {
        return paymentMethodDescription == null? null: paymentMethodDescription.getPaymentMethodDescriptionValue().clone();
    }
    
    public PaymentMethodDescriptionValue getPaymentMethodDescriptionValueForUpdate(PaymentMethod paymentMethod, Language language) {
        return getPaymentMethodDescriptionValue(getPaymentMethodDescriptionForUpdate(paymentMethod, language));
    }
    
    private List<PaymentMethodDescription> getPaymentMethodDescriptions(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        List<PaymentMethodDescription> paymentMethodDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions, languages " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_thrutime = ? AND pmd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentMethodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentMethodDescriptions = PaymentMethodDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodDescriptions;
    }
    
    public List<PaymentMethodDescription> getPaymentMethodDescriptions(PaymentMethod paymentMethod) {
        return getPaymentMethodDescriptions(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public List<PaymentMethodDescription> getPaymentMethodDescriptionsForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodDescriptions(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public String getBestPaymentMethodDescription(PaymentMethod paymentMethod, Language language) {
        String description;
        PaymentMethodDescription paymentMethodDescription = getPaymentMethodDescription(paymentMethod, language);
        
        if(paymentMethodDescription == null && !language.getIsDefault()) {
            paymentMethodDescription = getPaymentMethodDescription(paymentMethod, getPartyControl().getDefaultLanguage());
        }
        
        if(paymentMethodDescription == null) {
            description = paymentMethod.getLastDetail().getPaymentMethodName();
        } else {
            description = paymentMethodDescription.getDescription();
        }
        
        return description;
    }
    
    public PaymentMethodDescriptionTransfer getPaymentMethodDescriptionTransfer(UserVisit userVisit, PaymentMethodDescription paymentMethodDescription) {
        return getPaymentTransferCaches(userVisit).getPaymentMethodDescriptionTransferCache().getTransfer(paymentMethodDescription);
    }
    
    public List<PaymentMethodDescriptionTransfer> getPaymentMethodDescriptionTransfers(UserVisit userVisit, PaymentMethod paymentMethod) {
        List<PaymentMethodDescription> paymentMethodDescriptions = getPaymentMethodDescriptions(paymentMethod);
        List<PaymentMethodDescriptionTransfer> paymentMethodDescriptionTransfers = new ArrayList<>(paymentMethodDescriptions.size());
        PaymentMethodDescriptionTransferCache paymentMethodDescriptionTransferCache = getPaymentTransferCaches(userVisit).getPaymentMethodDescriptionTransferCache();
        
        paymentMethodDescriptions.stream().forEach((paymentMethodDescription) -> {
            paymentMethodDescriptionTransfers.add(paymentMethodDescriptionTransferCache.getTransfer(paymentMethodDescription));
        });
        
        return paymentMethodDescriptionTransfers;
    }
    
    public void updatePaymentMethodDescriptionFromValue(PaymentMethodDescriptionValue paymentMethodDescriptionValue, BasePK updatedBy) {
        if(paymentMethodDescriptionValue.hasBeenModified()) {
            PaymentMethodDescription paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodDescriptionValue.getPrimaryKey());
            
            paymentMethodDescription.setThruTime(session.START_TIME_LONG);
            paymentMethodDescription.store();
            
            PaymentMethod paymentMethod = paymentMethodDescription.getPaymentMethod();
            Language language = paymentMethodDescription.getLanguage();
            String description = paymentMethodDescriptionValue.getDescription();
            
            paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().create(paymentMethod, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(),
                    paymentMethodDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePaymentMethodDescription(PaymentMethodDescription paymentMethodDescription, BasePK deletedBy) {
        paymentMethodDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(paymentMethodDescription.getPaymentMethodPK(), EventTypes.MODIFY.name(),
                paymentMethodDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePaymentMethodDescriptionsByPaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {
        List<PaymentMethodDescription> paymentMethodDescriptions = getPaymentMethodDescriptionsForUpdate(paymentMethod);
        
        paymentMethodDescriptions.stream().forEach((paymentMethodDescription) -> {
            deletePaymentMethodDescription(paymentMethodDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Checks
    // --------------------------------------------------------------------------------
    
    public PaymentMethodCheck createPaymentMethodCheck(PaymentMethod paymentMethod, Integer holdDays, BasePK createdBy) {
        PaymentMethodCheck paymentMethodCheck = PaymentMethodCheckFactory.getInstance().create(paymentMethod, holdDays,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(), paymentMethodCheck.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return paymentMethodCheck;
    }
    
    private PaymentMethodCheck getPaymentMethodCheck(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        PaymentMethodCheck paymentMethodCheck = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodchecks " +
                        "WHERE pmchk_pm_paymentmethodid = ? AND pmchk_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodchecks " +
                        "WHERE pmchk_pm_paymentmethodid = ? AND pmchk_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentMethodCheckFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentMethodCheck = PaymentMethodCheckFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodCheck;
    }
    
    public PaymentMethodCheck getPaymentMethodCheck(PaymentMethod paymentMethod) {
        return getPaymentMethodCheck(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodCheck getPaymentMethodCheckForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodCheck(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodCheckValue getPaymentMethodCheckValueForUpdate(PaymentMethod paymentMethod) {
        PaymentMethodCheck paymentMethodCheck = getPaymentMethodCheckForUpdate(paymentMethod);
        
        return paymentMethodCheck == null? null: paymentMethodCheck.getPaymentMethodCheckValue().clone();
    }
    
    public void updatePaymentMethodCheckFromValue(PaymentMethodCheckValue paymentMethodCheckValue, BasePK updatedBy) {
        if(paymentMethodCheckValue.hasBeenModified()) {
            PaymentMethodCheck paymentMethodCheck = PaymentMethodCheckFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodCheckValue.getPrimaryKey());
            
            paymentMethodCheck.setThruTime(session.START_TIME_LONG);
            paymentMethodCheck.store();
            
            PaymentMethodPK paymentMethodPK = paymentMethodCheck.getPaymentMethodPK(); // Not updated
            Integer holdDays = paymentMethodCheckValue.getHoldDays();
            
            paymentMethodCheck = PaymentMethodCheckFactory.getInstance().create(paymentMethodPK, holdDays,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(paymentMethodPK, EventTypes.MODIFY.name(), paymentMethodCheck.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePaymentMethodCheck(PaymentMethodCheck paymentMethodCheck, BasePK deletedBy) {
        paymentMethodCheck.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(paymentMethodCheck.getPaymentMethodPK(), EventTypes.MODIFY.name(),
                paymentMethodCheck.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Credit Cards
    // --------------------------------------------------------------------------------
    
    public PaymentMethodCreditCard createPaymentMethodCreditCard(PaymentMethod paymentMethod, Boolean requestNameOnCard,
            Boolean requireNameOnCard, Boolean checkCardNumber, Boolean requestExpirationDate, Boolean requireExpirationDate,
            Boolean checkExpirationDate, Boolean requestSecurityCode, Boolean requireSecurityCode,
            String cardNumberValidationPattern, String securityCodeValidationPattern, Boolean retainCreditCard,
            Boolean retainSecurityCode, Boolean requestBilling, Boolean requireBilling, Boolean requestIssuer, Boolean requireIssuer, BasePK createdBy) {
        PaymentMethodCreditCard paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().create(session,
                paymentMethod, requestNameOnCard, requireNameOnCard, checkCardNumber, requestExpirationDate, requireExpirationDate,
                checkExpirationDate, requestSecurityCode, requireSecurityCode, cardNumberValidationPattern,
                securityCodeValidationPattern, retainCreditCard, retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(paymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(), paymentMethodCreditCard.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return paymentMethodCreditCard;
    }
    
    private PaymentMethodCreditCard getPaymentMethodCreditCard(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        PaymentMethodCreditCard paymentMethodCreditCard = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodcreditcards " +
                        "WHERE pmcc_pm_paymentmethodid = ? AND pmcc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodcreditcards " +
                        "WHERE pmcc_pm_paymentmethodid = ? AND pmcc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PaymentMethodCreditCardFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodCreditCard;
    }
    
    public PaymentMethodCreditCard getPaymentMethodCreditCard(PaymentMethod paymentMethod) {
        return getPaymentMethodCreditCard(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodCreditCard getPaymentMethodCreditCardForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodCreditCard(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodCreditCardValue getPaymentMethodCreditCardValueForUpdate(PaymentMethod paymentMethod) {
        PaymentMethodCreditCard paymentMethodCreditCard = getPaymentMethodCreditCardForUpdate(paymentMethod);
        
        return paymentMethodCreditCard == null? null: paymentMethodCreditCard.getPaymentMethodCreditCardValue().clone();
    }
    
    public void updatePaymentMethodCreditCardFromValue(PaymentMethodCreditCardValue paymentMethodCreditCardValue, BasePK updatedBy) {
        if(paymentMethodCreditCardValue.hasBeenModified()) {
            PaymentMethodCreditCard paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodCreditCardValue.getPrimaryKey());
            
            paymentMethodCreditCard.setThruTime(session.START_TIME_LONG);
            paymentMethodCreditCard.store();
            
            PaymentMethodPK paymentMethodPK = paymentMethodCreditCard.getPaymentMethodPK(); // Not updated
            Boolean requestNameOnCard = paymentMethodCreditCardValue.getRequestNameOnCard();
            Boolean reqireNameOnCard = paymentMethodCreditCardValue.getRequireNameOnCard();
            Boolean checkCardNumber = paymentMethodCreditCardValue.getCheckCardNumber();
            Boolean requestExpirationDate = paymentMethodCreditCardValue.getRequireExpirationDate();
            Boolean requireExpirationDate = paymentMethodCreditCardValue.getRequireExpirationDate();
            Boolean checkExpirationDate = paymentMethodCreditCardValue.getCheckExpirationDate();
            Boolean requestSecurityCode = paymentMethodCreditCardValue.getRequestSecurityCode();
            Boolean requireSecurityCode = paymentMethodCreditCardValue.getRequireSecurityCode();
            String cardNumberValidationPattern = paymentMethodCreditCardValue.getCardNumberValidationPattern();
            String securityCodeValidationPattern = paymentMethodCreditCardValue.getSecurityCodeValidationPattern();
            Boolean retainCreditCard = paymentMethodCreditCardValue.getRetainCreditCard();
            Boolean retainSecurityCode = paymentMethodCreditCardValue.getRetainSecurityCode();
            Boolean requestBilling = paymentMethodCreditCardValue.getRequestBilling();
            Boolean requireBilling = paymentMethodCreditCardValue.getRequireBilling();
            Boolean requestIssuer = paymentMethodCreditCardValue.getRequestIssuer();
            Boolean requireIssuer = paymentMethodCreditCardValue.getRequireIssuer();
            
            paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().create(paymentMethodPK,
                    requestNameOnCard, reqireNameOnCard, checkCardNumber, requestExpirationDate, requireExpirationDate,
                    checkExpirationDate, requestSecurityCode, requireSecurityCode, cardNumberValidationPattern,
                    securityCodeValidationPattern, retainCreditCard, retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(paymentMethodPK, EventTypes.MODIFY.name(), paymentMethodCreditCard.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePaymentMethodCreditCard(PaymentMethodCreditCard paymentMethodCreditCard, BasePK deletedBy) {
        paymentMethodCreditCard.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(paymentMethodCreditCard.getPaymentMethodPK(), EventTypes.MODIFY.name(),
                paymentMethodCreditCard.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
 }
