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

package com.echothree.model.control.payment.server;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.payment.common.choice.PaymentProcessorActionTypeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorActionTypeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorActionTypeTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentProcessorActionTypePK;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionTypeDescription;
import com.echothree.model.data.payment.server.factory.PaymentProcessorActionTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorActionTypeDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorActionTypeFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorActionTypeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorActionTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PaymentProcessorActionTypeControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentControl */
    public PaymentProcessorActionTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Inventory Conditions
    // --------------------------------------------------------------------------------

    public PaymentProcessorActionType createPaymentProcessorActionType(final String paymentProcessorActionTypeName, Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var defaultPaymentProcessorActionType = getDefaultPaymentProcessorActionType();
        var defaultFound = defaultPaymentProcessorActionType != null;

        if(defaultFound && isDefault) {
            var defaultPaymentProcessorActionTypeDetailValue = getDefaultPaymentProcessorActionTypeDetailValueForUpdate();

            defaultPaymentProcessorActionTypeDetailValue.setIsDefault(Boolean.FALSE);
            updatePaymentProcessorActionTypeFromValue(defaultPaymentProcessorActionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var paymentProcessorActionType = PaymentProcessorActionTypeFactory.getInstance().create();
        var paymentProcessorActionTypeDetail = PaymentProcessorActionTypeDetailFactory.getInstance().create(session,
                paymentProcessorActionType, paymentProcessorActionTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        paymentProcessorActionType = PaymentProcessorActionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorActionType.getPrimaryKey());
        paymentProcessorActionType.setActiveDetail(paymentProcessorActionTypeDetail);
        paymentProcessorActionType.setLastDetail(paymentProcessorActionTypeDetail);
        paymentProcessorActionType.store();

        sendEventUsingNames(paymentProcessorActionType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return paymentProcessorActionType;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.PaymentProcessorActionType */
    public PaymentProcessorActionType getPaymentProcessorActionTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorActionTypePK(entityInstance.getEntityUniqueId());

        return PaymentProcessorActionTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorActionTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorActionTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentProcessorActionTypeByNameQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractiontypes, paymentprocessoractiontypedetails " +
                    "WHERE pprcacttyp_paymentprocessoractiontypeid = pprcacttypdt_pprcacttyp_paymentprocessoractiontypeid AND pprcacttypdt_paymentprocessoractiontypename = ? AND pprcacttypdt_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentprocessoractiontypes, paymentprocessoractiontypedetails " +
                    "WHERE pprcacttyp_paymentprocessoractiontypeid = pprcacttypdt_pprcacttyp_paymentprocessoractiontypeid AND pprcacttypdt_paymentprocessoractiontypename = ? AND pprcacttypdt_thrutime = ? " +
                    "FOR UPDATE");

    public PaymentProcessorActionType getPaymentProcessorActionTypeByName(final String paymentProcessorActionTypeName, final EntityPermission entityPermission) {
        return PaymentProcessorActionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorActionTypeByNameQueries,
                paymentProcessorActionTypeName, Session.MAX_TIME);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByName(final String paymentProcessorActionTypeName) {
        return getPaymentProcessorActionTypeByName(paymentProcessorActionTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorActionType getPaymentProcessorActionTypeByNameForUpdate(final String paymentProcessorActionTypeName) {
        return getPaymentProcessorActionTypeByName(paymentProcessorActionTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionTypeDetailValue getPaymentProcessorActionTypeDetailValueForUpdate(final PaymentProcessorActionType paymentProcessorActionType) {
        return paymentProcessorActionType == null ? null: paymentProcessorActionType.getLastDetailForUpdate().getPaymentProcessorActionTypeDetailValue().clone();
    }

    public PaymentProcessorActionTypeDetailValue getPaymentProcessorActionTypeDetailValueByNameForUpdate(final String paymentProcessorActionTypeName) {
        return getPaymentProcessorActionTypeDetailValueForUpdate(getPaymentProcessorActionTypeByNameForUpdate(paymentProcessorActionTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultPaymentProcessorActionTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractiontypes, paymentprocessoractiontypedetails " +
                    "WHERE pprcacttyp_paymentprocessoractiontypeid = pprcacttypdt_pprcacttyp_paymentprocessoractiontypeid AND pprcacttypdt_isdefault = 1 AND pprcacttypdt_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractiontypes, paymentprocessoractiontypedetails " +
                    "WHERE pprcacttyp_paymentprocessoractiontypeid = pprcacttypdt_pprcacttyp_paymentprocessoractiontypeid AND pprcacttypdt_isdefault = 1 AND pprcacttypdt_thrutime = ? " +
                    "FOR UPDATE");

    public PaymentProcessorActionType getDefaultPaymentProcessorActionType(final EntityPermission entityPermission) {
        return PaymentProcessorActionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPaymentProcessorActionTypeQueries,
                Session.MAX_TIME);
    }

    public PaymentProcessorActionType getDefaultPaymentProcessorActionType() {
        return getDefaultPaymentProcessorActionType(EntityPermission.READ_ONLY);
    }

    public PaymentProcessorActionType getDefaultPaymentProcessorActionTypeForUpdate() {
        return getDefaultPaymentProcessorActionType(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionTypeDetailValue getDefaultPaymentProcessorActionTypeDetailValueForUpdate() {
        return getDefaultPaymentProcessorActionTypeForUpdate().getLastDetailForUpdate().getPaymentProcessorActionTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPaymentProcessorActionTypesQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " + "FROM paymentprocessoractiontypes, paymentprocessoractiontypedetails " +
                    "WHERE pprcacttyp_paymentprocessoractiontypeid = pprcacttypdt_pprcacttyp_paymentprocessoractiontypeid AND pprcacttypdt_thrutime = ? " +
                    "ORDER BY pprcacttypdt_sortorder, pprcacttypdt_paymentprocessoractiontypename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentprocessoractiontypes, paymentprocessoractiontypedetails " +
                    "WHERE pprcacttyp_paymentprocessoractiontypeid = pprcacttypdt_pprcacttyp_paymentprocessoractiontypeid AND pprcacttypdt_thrutime = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorActionType> getPaymentProcessorActionTypes(final EntityPermission entityPermission) {
        return PaymentProcessorActionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentProcessorActionTypesQueries,
                Session.MAX_TIME);
    }

    public List<PaymentProcessorActionType> getPaymentProcessorActionTypes() {
        return getPaymentProcessorActionTypes(EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorActionType> getPaymentProcessorActionTypesForUpdate() {
        return getPaymentProcessorActionTypes(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionTypeTransfer getPaymentProcessorActionTypeTransfer(final UserVisit userVisit,
            final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentTransferCaches(userVisit).getPaymentProcessorActionTypeTransferCache().getTransfer(paymentProcessorActionType);
    }

    public List<PaymentProcessorActionTypeTransfer> getPaymentProcessorActionTypeTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorActionType> paymentProcessorActionTypes) {
        var paymentProcessorActionTypeTransfers = new ArrayList<PaymentProcessorActionTypeTransfer>(paymentProcessorActionTypes.size());
        var paymentProcessorActionTypeTransferCache = getPaymentTransferCaches(userVisit).getPaymentProcessorActionTypeTransferCache();

        paymentProcessorActionTypes.stream().forEach((paymentProcessorActionType) -> {
            paymentProcessorActionTypeTransfers.add(paymentProcessorActionTypeTransferCache.getTransfer(paymentProcessorActionType));
        });

        return paymentProcessorActionTypeTransfers;
    }

    public List<PaymentProcessorActionTypeTransfer> getPaymentProcessorActionTypeTransfers(final UserVisit userVisit) {
        return getPaymentProcessorActionTypeTransfers(userVisit, getPaymentProcessorActionTypes());
    }

    public PaymentProcessorActionTypeChoicesBean getPaymentProcessorActionTypeChoices(final String defaultPaymentProcessorActionTypeChoice,
            final Language language, final boolean allowNullChoice) {
        var paymentProcessorActionTypes = getPaymentProcessorActionTypes();
        var size = paymentProcessorActionTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPaymentProcessorActionTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(PaymentProcessorActionType paymentProcessorActionType: paymentProcessorActionTypes) {
            var paymentProcessorActionTypeDetail = paymentProcessorActionType.getLastDetail();

            var label = getBestPaymentProcessorActionTypeDescription(paymentProcessorActionType, language);
            var value = paymentProcessorActionTypeDetail.getPaymentProcessorActionTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            boolean usingDefaultChoice = Objects.equals(defaultPaymentProcessorActionTypeChoice, value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorActionTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PaymentProcessorActionTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePaymentProcessorActionTypeFromValue(final PaymentProcessorActionTypeDetailValue paymentProcessorActionTypeDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(paymentProcessorActionTypeDetailValue.hasBeenModified()) {
            var paymentProcessorActionType = PaymentProcessorActionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorActionTypeDetailValue.getPaymentProcessorActionTypePK());
            var paymentProcessorActionTypeDetail = paymentProcessorActionType.getActiveDetailForUpdate();

            paymentProcessorActionTypeDetail.setThruTime(session.START_TIME_LONG);
            paymentProcessorActionTypeDetail.store();

            var paymentProcessorActionTypePK = paymentProcessorActionTypeDetail.getPaymentProcessorActionTypePK();
            var paymentProcessorActionTypeName = paymentProcessorActionTypeDetailValue.getPaymentProcessorActionTypeName();
            var isDefault = paymentProcessorActionTypeDetailValue.getIsDefault();
            var sortOrder = paymentProcessorActionTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPaymentProcessorActionType = getDefaultPaymentProcessorActionType();
                var defaultFound = defaultPaymentProcessorActionType != null && !defaultPaymentProcessorActionType.equals(paymentProcessorActionType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentProcessorActionTypeDetailValue = getDefaultPaymentProcessorActionTypeDetailValueForUpdate();

                    defaultPaymentProcessorActionTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updatePaymentProcessorActionTypeFromValue(defaultPaymentProcessorActionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            paymentProcessorActionTypeDetail = PaymentProcessorActionTypeDetailFactory.getInstance().create(paymentProcessorActionTypePK,
                    paymentProcessorActionTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            paymentProcessorActionType.setActiveDetail(paymentProcessorActionTypeDetail);
            paymentProcessorActionType.setLastDetail(paymentProcessorActionTypeDetail);

            sendEventUsingNames(paymentProcessorActionTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updatePaymentProcessorActionTypeFromValue(final PaymentProcessorActionTypeDetailValue paymentProcessorActionTypeDetailValue,
            final BasePK updatedBy) {
        updatePaymentProcessorActionTypeFromValue(paymentProcessorActionTypeDetailValue, true, updatedBy);
    }

    public void deletePaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType, final BasePK deletedBy) {

        deletePaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(paymentProcessorActionType, deletedBy);

        var paymentProcessorActionTypeDetail = paymentProcessorActionType.getLastDetailForUpdate();
        paymentProcessorActionTypeDetail.setThruTime(session.START_TIME_LONG);
        paymentProcessorActionTypeDetail.store();
        paymentProcessorActionType.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var defaultPaymentProcessorActionType = getDefaultPaymentProcessorActionType();
        if(defaultPaymentProcessorActionType == null) {
            var paymentProcessorActionTypes = getPaymentProcessorActionTypesForUpdate();

            if(!paymentProcessorActionTypes.isEmpty()) {
                var iter = paymentProcessorActionTypes.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessorActionType = iter.next();
                }
                var paymentProcessorActionTypeDetailValue = defaultPaymentProcessorActionType.getLastDetailForUpdate().getPaymentProcessorActionTypeDetailValue().clone();

                paymentProcessorActionTypeDetailValue.setIsDefault(Boolean.TRUE);
                updatePaymentProcessorActionTypeFromValue(paymentProcessorActionTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(paymentProcessorActionType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Condition Descriptions
    // --------------------------------------------------------------------------------

    public PaymentProcessorActionTypeDescription createPaymentProcessorActionTypeDescription(final PaymentProcessorActionType paymentProcessorActionType,
            final Language language, final String description, final BasePK createdBy) {
        var paymentProcessorActionTypeDescription = PaymentProcessorActionTypeDescriptionFactory.getInstance().create(paymentProcessorActionType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(paymentProcessorActionType.getPrimaryKey(), EventTypes.MODIFY.name(), paymentProcessorActionTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return paymentProcessorActionTypeDescription;
    }

    private static final Map<EntityPermission, String> getPaymentProcessorActionTypeDescriptionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractiontypedescriptions " +
                    "WHERE pprcacttypd_pprcacttyp_paymentprocessoractiontypeid = ? AND pprcacttypd_lang_languageid = ? AND pprcacttypd_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractiontypedescriptions " +
                    "WHERE pprcacttypd_pprcacttyp_paymentprocessoractiontypeid = ? AND pprcacttypd_lang_languageid = ? AND pprcacttypd_thrutime = ? " +
                    "FOR UPDATE");

    private PaymentProcessorActionTypeDescription getPaymentProcessorActionTypeDescription(final PaymentProcessorActionType paymentProcessorActionType,
            final Language language, final EntityPermission entityPermission) {
        return PaymentProcessorActionTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorActionTypeDescriptionQueries,
                paymentProcessorActionType, language, Session.MAX_TIME);
    }

    public PaymentProcessorActionTypeDescription getPaymentProcessorActionTypeDescription(final PaymentProcessorActionType paymentProcessorActionType,
            final Language language) {
        return getPaymentProcessorActionTypeDescription(paymentProcessorActionType, language, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorActionTypeDescription getPaymentProcessorActionTypeDescriptionForUpdate(final PaymentProcessorActionType paymentProcessorActionType,
            final Language language) {
        return getPaymentProcessorActionTypeDescription(paymentProcessorActionType, language, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorActionTypeDescriptionValue getPaymentProcessorActionTypeDescriptionValue(final PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription) {
        return paymentProcessorActionTypeDescription == null ? null: paymentProcessorActionTypeDescription.getPaymentProcessorActionTypeDescriptionValue().clone();
    }

    public PaymentProcessorActionTypeDescriptionValue getPaymentProcessorActionTypeDescriptionValueForUpdate(final PaymentProcessorActionType paymentProcessorActionType,
            final Language language) {
        return getPaymentProcessorActionTypeDescriptionValue(getPaymentProcessorActionTypeDescriptionForUpdate(paymentProcessorActionType, language));
    }

    private static final Map<EntityPermission, String> getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractiontypedescriptions, languages " +
                    "WHERE pprcacttypd_pprcacttyp_paymentprocessoractiontypeid = ? AND pprcacttypd_thrutime = ? AND pprcacttypd_lang_languageid = lang_languageid " +
                    "ORDER BY lang_sortorder, lang_languageisoname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessoractiontypedescriptions " +
                    "WHERE pprcacttypd_pprcacttyp_paymentprocessoractiontypeid = ? AND pprcacttypd_thrutime = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorActionTypeDescription> getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType,
            final EntityPermission entityPermission) {
        return PaymentProcessorActionTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionTypeQueries,
                paymentProcessorActionType, Session.MAX_TIME);
    }

    public List<PaymentProcessorActionTypeDescription> getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(paymentProcessorActionType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorActionTypeDescription> getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionTypeForUpdate(final PaymentProcessorActionType paymentProcessorActionType) {
        return getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(paymentProcessorActionType, EntityPermission.READ_WRITE);
    }

    public String getBestPaymentProcessorActionTypeDescription(final PaymentProcessorActionType paymentProcessorActionType, final Language language) {
        var paymentProcessorActionTypeDescription = getPaymentProcessorActionTypeDescription(paymentProcessorActionType, language);
        String description;

        if(paymentProcessorActionTypeDescription == null && !language.getIsDefault()) {
            paymentProcessorActionTypeDescription = getPaymentProcessorActionTypeDescription(paymentProcessorActionType, getPartyControl().getDefaultLanguage());
        }

        if(paymentProcessorActionTypeDescription == null) {
            description = paymentProcessorActionType.getLastDetail().getPaymentProcessorActionTypeName();
        } else {
            description = paymentProcessorActionTypeDescription.getDescription();
        }

        return description;
    }

    public PaymentProcessorActionTypeDescriptionTransfer getPaymentProcessorActionTypeDescriptionTransfer(final UserVisit userVisit,
            final PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription) {
        return getPaymentTransferCaches(userVisit).getPaymentProcessorActionTypeDescriptionTransferCache().getTransfer(paymentProcessorActionTypeDescription);
    }

    public List<PaymentProcessorActionTypeDescriptionTransfer> getPaymentProcessorActionTypeDescriptionTransfersByPaymentProcessorActionType(final UserVisit userVisit,
            final PaymentProcessorActionType paymentProcessorActionType) {
        var paymentProcessorActionTypeDescriptions = getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(paymentProcessorActionType);
        var paymentProcessorActionTypeDescriptionTransfers = new ArrayList<PaymentProcessorActionTypeDescriptionTransfer>(paymentProcessorActionTypeDescriptions.size());
        var paymentProcessorActionTypeDescriptionTransferCache = getPaymentTransferCaches(userVisit).getPaymentProcessorActionTypeDescriptionTransferCache();

        paymentProcessorActionTypeDescriptions.stream().forEach((paymentProcessorActionTypeDescription) -> {
            paymentProcessorActionTypeDescriptionTransfers.add(paymentProcessorActionTypeDescriptionTransferCache.getTransfer(paymentProcessorActionTypeDescription));
        });

        return paymentProcessorActionTypeDescriptionTransfers;
    }

    public void updatePaymentProcessorActionTypeDescriptionFromValue(final PaymentProcessorActionTypeDescriptionValue paymentProcessorActionTypeDescriptionValue,
            final BasePK updatedBy) {
        if(paymentProcessorActionTypeDescriptionValue.hasBeenModified()) {
            var paymentProcessorActionTypeDescription = PaymentProcessorActionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorActionTypeDescriptionValue.getPrimaryKey());

            paymentProcessorActionTypeDescription.setThruTime(session.START_TIME_LONG);
            paymentProcessorActionTypeDescription.store();

            var paymentProcessorActionType = paymentProcessorActionTypeDescription.getPaymentProcessorActionType();
            var language = paymentProcessorActionTypeDescription.getLanguage();
            var description = paymentProcessorActionTypeDescriptionValue.getDescription();

            paymentProcessorActionTypeDescription = PaymentProcessorActionTypeDescriptionFactory.getInstance().create(paymentProcessorActionType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(paymentProcessorActionType.getPrimaryKey(), EventTypes.MODIFY.name(), paymentProcessorActionTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deletePaymentProcessorActionTypeDescription(final PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription, final BasePK deletedBy) {
        paymentProcessorActionTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(paymentProcessorActionTypeDescription.getPaymentProcessorActionTypePK(), EventTypes.MODIFY.name(), paymentProcessorActionTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deletePaymentProcessorActionTypeDescriptionsByPaymentProcessorActionType(final PaymentProcessorActionType paymentProcessorActionType, final BasePK deletedBy) {
        var paymentProcessorActionTypeDescriptions = getPaymentProcessorActionTypeDescriptionsByPaymentProcessorActionTypeForUpdate(paymentProcessorActionType);

        paymentProcessorActionTypeDescriptions.forEach((paymentProcessorActionTypeDescription) -> {
            deletePaymentProcessorActionTypeDescription(paymentProcessorActionTypeDescription, deletedBy);
        });
    }

}
