// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.invoice.server.command;

import com.echothree.control.user.invoice.common.form.CreateInvoiceTypeForm;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateInvoiceTypeCommand
        extends BaseSimpleCommand<CreateInvoiceTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.InvoiceType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentInvoiceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InvoiceSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateInvoiceTypeCommand */
    public CreateInvoiceTypeCommand(UserVisitPK userVisitPK, CreateInvoiceTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        String invoiceTypeName = form.getInvoiceTypeName();
        InvoiceType invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);
        
        if(invoiceType == null) {
            String parentInvoiceTypeName = form.getParentInvoiceTypeName();
            InvoiceType parentInvoiceType = null;
            
            if(parentInvoiceTypeName != null) {
                parentInvoiceType = invoiceControl.getInvoiceTypeByName(parentInvoiceTypeName);
            }
            
            if(parentInvoiceTypeName == null || parentInvoiceType != null) {
                var sequenceControl = Session.getModelController(SequenceControl.class);
                String invoiceSequenceTypeName = form.getInvoiceSequenceTypeName();
                SequenceType invoiceSequenceType = sequenceControl.getSequenceTypeByName(invoiceSequenceTypeName);
                
                if(invoiceSequenceTypeName == null || invoiceSequenceType != null) {
                    var partyPK = getPartyPK();
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var description = form.getDescription();
                    
                    invoiceType = invoiceControl.createInvoiceType(invoiceTypeName, parentInvoiceType, invoiceSequenceType,
                            isDefault, sortOrder, partyPK);
                    
                    if(description != null) {
                        invoiceControl.createInvoiceTypeDescription(invoiceType, getPreferredLanguage(), description, partyPK);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownInvoiceSequenceTypeName.name(), invoiceSequenceTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentInvoiceTypeName.name(), parentInvoiceTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateInvoiceTypeName.name(), invoiceTypeName);
        }
        
        return null;
    }
    
}
