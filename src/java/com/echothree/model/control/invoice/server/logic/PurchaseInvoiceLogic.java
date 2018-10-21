// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.invoice.server.logic;

import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.logic.GlAccountLogic;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.invoice.common.InvoiceConstants;
import com.echothree.model.control.invoice.remote.choice.PurchaseInvoiceStatusChoicesBean;
import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.control.invoice.common.workflow.PurchaseInvoiceStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.invoice.server.entity.InvoiceLine;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class PurchaseInvoiceLogic {

    private PurchaseInvoiceLogic() {
        super();
    }

    private static class PurchaseInvoiceLogicHolder {
        static PurchaseInvoiceLogic instance = new PurchaseInvoiceLogic();
    }

    public static PurchaseInvoiceLogic getInstance() {
        return PurchaseInvoiceLogicHolder.instance;
    }
    
    public Invoice getInvoiceByName(String invoiceName) {
        InvoiceControl invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);
        
        return invoiceControl.getInvoiceByNameUsingNames(InvoiceConstants.InvoiceType_PURCHASE_INVOICE, invoiceName);
    }
    
    protected void validateReference(final ExecutionErrorAccumulator eea, final Party billFrom, final String reference, final Vendor vendor) {
        if(vendor.getRequireReference() && reference == null) {
            eea.addExecutionError(ExecutionErrors.PurchaseInvoiceReferenceRequired.name());
        } else if(reference != null) {
            InvoiceControl invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);
            
            if(!vendor.getAllowReferenceDuplicates() && invoiceControl.countInvoicesByInvoiceFromAndReference(billFrom, reference) != 0) {
                eea.addExecutionError(ExecutionErrors.PurchaseInvoiceDuplicateReference.name());
            } else {
                String referenceValidationPattern = vendor.getReferenceValidationPattern();
                
                if(referenceValidationPattern != null && !reference.matches(referenceValidationPattern)) {
                    eea.addExecutionError(ExecutionErrors.InvalidPurchaseInvoiceReference.name());
                }
            }
        }
    }

    protected GlAccount getApGlAccount(final ExecutionErrorAccumulator eea, final Vendor vendor) {
        GlAccount vendorApGlAccount = vendor.getApGlAccount();
        GlAccount vendorTypeDefaultApGlAccount = vendor.getVendorType().getLastDetail().getDefaultApGlAccount();
        GlAccount[] glAccounts = { vendorApGlAccount, vendorTypeDefaultApGlAccount };

        return GlAccountLogic.getInstance().getDefaultGlAccountByCategory(eea, new GlAccount[]{
                    vendor.getApGlAccount(),
                    vendor.getVendorType().getLastDetail().getDefaultApGlAccount()
                }, AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE, ExecutionErrors.UnknownDefaultApGlAccount.name());
    }
    
    public Invoice createInvoice(final Session session, final ExecutionErrorAccumulator eea, final Party billFrom,
            final PartyContactMechanism billFromPartyContactMechanism, final Party billTo, final PartyContactMechanism billToPartyContactMechanism,
            final Currency currency, final Term term, final String reference, final String description, final Long invoicedTime, final Long dueTime,
            final Long paidTime, final String workflowEntranceName, final BasePK createdBy) {
        Invoice invoice = null;
        VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
        Vendor vendor = vendorControl.getVendor(billFrom);
        GlAccount glAccount = getApGlAccount(eea, vendor);
        
        validateReference(eea, billFrom, reference, vendor);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            invoice = InvoiceLogic.getInstance().createInvoice(session, eea, InvoiceConstants.InvoiceType_PURCHASE_INVOICE, billFrom,
                    billFromPartyContactMechanism, billTo, billToPartyContactMechanism, currency, glAccount, term, reference, description, invoicedTime,
                    dueTime, paidTime, createdBy);

            if(!eea.hasExecutionErrors() && workflowEntranceName != null) {
                CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
                WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(invoice.getPrimaryKey());

                workflowControl.addEntityToWorkflowUsingNames(null, PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS, workflowEntranceName,
                        entityInstance, null, null, createdBy);
            }
        }
        
        return invoice;
    }
    
    public InvoiceLine createInvoiceLine(final ExecutionErrorAccumulator eea, final Invoice invoice, final Integer invoiceLineSequence, final InvoiceLine parentInvoiceLine,
            final Long amount, final InvoiceLineType invoiceLineType, final GlAccount glAccount, final String description, final BasePK createdBy) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        InvoiceLine invoiceLine = null;
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(invoice.getPrimaryKey());
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS, entityInstance);
        String workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();

        if(workflowStepName.equals(PurchaseInvoiceStatusConstants.WorkflowStep_ENTRY)) {
            invoiceLine = InvoiceLogic.getInstance().createInvoiceLine(eea, invoice, invoiceLineSequence, parentInvoiceLine, amount, invoiceLineType, glAccount, description, createdBy);
        } else {
            eea.addExecutionError(ExecutionErrors.InvalidPurchaseInvoiceStatus.name(), invoice.getLastDetail().getInvoiceName(), workflowStepName);
        }
        
        return invoiceLine;
    }
    
    public PurchaseInvoiceStatusChoicesBean getPurchaseInvoiceStatusChoices(final String defaultInvoiceStatusChoice, final Language language, final boolean allowNullChoice,
            final Invoice invoice, final PartyPK partyPK) {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        PurchaseInvoiceStatusChoicesBean purchaseInvoiceStatusChoicesBean = new PurchaseInvoiceStatusChoicesBean();
        
        if(invoice == null) {
            workflowControl.getWorkflowEntranceChoices(purchaseInvoiceStatusChoicesBean, defaultInvoiceStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS), partyPK);
        } else {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(invoice.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS, entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(purchaseInvoiceStatusChoicesBean, defaultInvoiceStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return purchaseInvoiceStatusChoicesBean;
    }
    
    public void setPurchaseInvoiceStatus(final ExecutionErrorAccumulator eea, final Invoice invoice, final String invoiceStatusChoice, final PartyPK modifiedBy) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(invoice.getPrimaryKey());
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = invoiceStatusChoice == null? null: workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), invoiceStatusChoice);
        
        if(workflowDestination != null || invoiceStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPurchaseInvoiceStatusChoice.name(), invoiceStatusChoice);
        }
    }
    
}
