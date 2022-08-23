// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.CreateTransactionEntityRoleTypeForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
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

public class CreateTransactionEntityRoleTypeCommand
        extends BaseSimpleCommand<CreateTransactionEntityRoleTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TransactionEntityRoleType.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateTransactionEntityRoleTypeCommand */
    public CreateTransactionEntityRoleTypeCommand(UserVisitPK userVisitPK, CreateTransactionEntityRoleTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        String transactionTypeName = form.getTransactionTypeName();
        TransactionType transactionType = accountingControl.getTransactionTypeByName(transactionTypeName);
        
        if(transactionType != null) {
            String transactionEntityRoleTypeName = form.getTransactionEntityRoleTypeName();
            TransactionEntityRoleType transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName);
            
            if(transactionEntityRoleType == null) {
                var coreControl = getCoreControl();
                String componentVendorName = form.getComponentVendorName();
                ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
                
                if(componentVendor != null) {
                    String entityTypeName = form.getEntityTypeName();
                    EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
                    
                    if(entityType != null) {
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();
                        var partyPK = getPartyPK();
                        
                        transactionEntityRoleType = accountingControl.createTransactionEntityRoleType(transactionType, transactionEntityRoleTypeName, entityType, sortOrder, partyPK);

                        if(description != null) {
                            accountingControl.createTransactionEntityRoleTypeDescription(transactionEntityRoleType, getPreferredLanguage(), description, partyPK);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateTransactionEntityRoleTypeName.name(),
                        transactionEntityRoleTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTransactionTypeName.name(), transactionTypeName);
        }
        
        return null;
    }
    
}
