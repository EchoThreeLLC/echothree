// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.form.CreateBillingAccountRoleTypeForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateBillingAccountRoleTypeCommand
        extends BaseSimpleCommand<CreateBillingAccountRoleTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null)
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BillingAccountRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateBillingAccountRoleTypeCommand */
    public CreateBillingAccountRoleTypeCommand(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var billingControl = Session.getModelController(BillingControl.class);
        String billingAccountRoleTypeName = form.getBillingAccountRoleTypeName();
        BillingAccountRoleType billingAccountRoleType = billingControl.getBillingAccountRoleTypeByName(billingAccountRoleTypeName);
        
        if(billingAccountRoleType == null) {
            var sortOrder = Integer.valueOf(form.getSortOrder());

            billingControl.createBillingAccountRoleType(billingAccountRoleTypeName, sortOrder);
        } else {
            addExecutionError(ExecutionErrors.DuplicateBillingAccountRoleTypeName.name(), billingAccountRoleTypeName);
        }
        
        return null;
    }
    
}
