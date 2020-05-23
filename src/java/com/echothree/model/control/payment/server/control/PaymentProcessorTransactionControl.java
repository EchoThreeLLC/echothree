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
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTransactionTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.payment.common.pk.PaymentProcessorTransactionPK;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTransactionDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTransactionFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorTransactionDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PaymentProcessorTransactionControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentProcessorTransactionControl */
    public PaymentProcessorTransactionControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Transactions
    // --------------------------------------------------------------------------------

    public PaymentProcessorTransaction createPaymentProcessorTransaction(final String paymentProcessorTransactionName,
            final PaymentProcessor paymentProcessor, final PaymentProcessorActionType paymentProcessorActionType,
            final PaymentProcessorResultCode paymentProcessorResultCode, final BasePK createdBy) {

        var paymentProcessorTransaction = PaymentProcessorTransactionFactory.getInstance().create();
        var paymentProcessorTransactionDetail = PaymentProcessorTransactionDetailFactory.getInstance().create(session,
                paymentProcessorTransaction, paymentProcessorTransactionName, paymentProcessor, paymentProcessorActionType,
                paymentProcessorResultCode, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        paymentProcessorTransaction = PaymentProcessorTransactionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTransaction.getPrimaryKey());
        paymentProcessorTransaction.setActiveDetail(paymentProcessorTransactionDetail);
        paymentProcessorTransaction.setLastDetail(paymentProcessorTransactionDetail);
        paymentProcessorTransaction.store();

        sendEventUsingNames(paymentProcessorTransaction.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return paymentProcessorTransaction;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.PaymentProcessorTransaction */
    public PaymentProcessorTransaction getPaymentProcessorTransactionByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorTransactionPK(entityInstance.getEntityUniqueId());

        return PaymentProcessorTransactionFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorTransaction getPaymentProcessorTransactionByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorTransactionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTransaction getPaymentProcessorTransactionByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorTransactionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTransactionByNameQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortransactions, paymentprocessortransactiondetails " +
                    "WHERE pprctrx_paymentprocessortransactionid = pprctrxdt_pprctrx_paymentprocessortransactionid AND pprctrxdt_paymentprocessortransactionname = ? AND pprctrxdt_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentprocessortransactions, paymentprocessortransactiondetails " +
                    "WHERE pprctrx_paymentprocessortransactionid = pprctrxdt_pprctrx_paymentprocessortransactionid AND pprctrxdt_paymentprocessortransactionname = ? AND pprctrxdt_thrutime = ? " +
                    "FOR UPDATE");

    public PaymentProcessorTransaction getPaymentProcessorTransactionByName(final String paymentProcessorTransactionName, final EntityPermission entityPermission) {
        return PaymentProcessorTransactionFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorTransactionByNameQueries,
                paymentProcessorTransactionName, Session.MAX_TIME);
    }

    public PaymentProcessorTransaction getPaymentProcessorTransactionByName(final String paymentProcessorTransactionName) {
        return getPaymentProcessorTransactionByName(paymentProcessorTransactionName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTransaction getPaymentProcessorTransactionByNameForUpdate(final String paymentProcessorTransactionName) {
        return getPaymentProcessorTransactionByName(paymentProcessorTransactionName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTransactionDetailValue getPaymentProcessorTransactionDetailValueForUpdate(final PaymentProcessorTransaction paymentProcessorTransaction) {
        return paymentProcessorTransaction == null ? null: paymentProcessorTransaction.getLastDetailForUpdate().getPaymentProcessorTransactionDetailValue().clone();
    }

    public PaymentProcessorTransactionDetailValue getPaymentProcessorTransactionDetailValueByNameForUpdate(final String paymentProcessorTransactionName) {
        return getPaymentProcessorTransactionDetailValueForUpdate(getPaymentProcessorTransactionByNameForUpdate(paymentProcessorTransactionName));
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTransactionsQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortransactions, paymentprocessortransactiondetails " +
                    "WHERE pprctrx_paymentprocessortransactionid = pprctrxdt_pprctrx_paymentprocessortransactionid AND pprctrxdt_thrutime = ? " +
                    "ORDER BY pprctrxdt_paymentprocessortransactionname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortransactions, paymentprocessortransactiondetails " +
                    "WHERE pprctrx_paymentprocessortransactionid = pprctrxdt_pprctrx_paymentprocessortransactionid AND pprctrxdt_thrutime = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorTransaction> getPaymentProcessorTransactions(final EntityPermission entityPermission) {
        return PaymentProcessorTransactionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentProcessorTransactionsQueries,
                Session.MAX_TIME);
    }

    public List<PaymentProcessorTransaction> getPaymentProcessorTransactions() {
        return getPaymentProcessorTransactions(EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTransaction> getPaymentProcessorTransactionsForUpdate() {
        return getPaymentProcessorTransactions(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTransactionTransfer getPaymentProcessorTransactionTransfer(final UserVisit userVisit,
            final PaymentProcessorTransaction paymentProcessorTransaction) {
        return getPaymentTransferCaches(userVisit).getPaymentProcessorTransactionTransferCache().getTransfer(paymentProcessorTransaction);
    }

    public List<PaymentProcessorTransactionTransfer> getPaymentProcessorTransactionTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorTransaction> paymentProcessorTransactions) {
        var paymentProcessorTransactionTransfers = new ArrayList<PaymentProcessorTransactionTransfer>(paymentProcessorTransactions.size());
        var paymentProcessorTransactionTransferCache = getPaymentTransferCaches(userVisit).getPaymentProcessorTransactionTransferCache();

        paymentProcessorTransactions.stream().forEach((paymentProcessorTransaction) -> {
            paymentProcessorTransactionTransfers.add(paymentProcessorTransactionTransferCache.getTransfer(paymentProcessorTransaction));
        });

        return paymentProcessorTransactionTransfers;
    }

    public List<PaymentProcessorTransactionTransfer> getPaymentProcessorTransactionTransfers(final UserVisit userVisit) {
        return getPaymentProcessorTransactionTransfers(userVisit, getPaymentProcessorTransactions());
    }

    public void updatePaymentProcessorTransactionFromValue(final PaymentProcessorTransactionDetailValue paymentProcessorTransactionDetailValue,
            final BasePK updatedBy) {
        if(paymentProcessorTransactionDetailValue.hasBeenModified()) {
            var paymentProcessorTransaction = PaymentProcessorTransactionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorTransactionDetailValue.getPaymentProcessorTransactionPK());
            var paymentProcessorTransactionDetail = paymentProcessorTransaction.getActiveDetailForUpdate();

            paymentProcessorTransactionDetail.setThruTime(session.START_TIME_LONG);
            paymentProcessorTransactionDetail.store();

            var paymentProcessorTransactionPK = paymentProcessorTransactionDetail.getPaymentProcessorTransactionPK(); // R/O
            var paymentProcessorTransactionName = paymentProcessorTransactionDetailValue.getPaymentProcessorTransactionName(); // R/W
            var paymentProcessorPK = paymentProcessorTransactionDetailValue.getPaymentProcessorPK(); // R/W
            var paymentProcessorActionTypePK = paymentProcessorTransactionDetailValue.getPaymentProcessorActionTypePK(); // R/W
            var paymentProcessorResultCodePK = paymentProcessorTransactionDetailValue.getPaymentProcessorResultCodePK(); // R/W

            paymentProcessorTransactionDetail = PaymentProcessorTransactionDetailFactory.getInstance().create(paymentProcessorTransactionPK,
                    paymentProcessorTransactionName, paymentProcessorPK, paymentProcessorActionTypePK, paymentProcessorResultCodePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            paymentProcessorTransaction.setActiveDetail(paymentProcessorTransactionDetail);
            paymentProcessorTransaction.setLastDetail(paymentProcessorTransactionDetail);

            sendEventUsingNames(paymentProcessorTransactionPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void deletePaymentProcessorTransaction(final PaymentProcessorTransaction paymentProcessorTransaction, final BasePK deletedBy) {
        var paymentProcessorTransactionDetail = paymentProcessorTransaction.getLastDetailForUpdate();
        paymentProcessorTransactionDetail.setThruTime(session.START_TIME_LONG);
        paymentProcessorTransactionDetail.store();
        paymentProcessorTransaction.setActiveDetail(null);

        sendEventUsingNames(paymentProcessorTransaction.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

}
