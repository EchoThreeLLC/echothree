// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.purchase.server.command;

import com.echothree.control.user.purchase.common.form.SetPurchaseOrderStatusForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.purchase.server.logic.PurchaseOrderLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetPurchaseOrderStatusCommand
        extends BaseSimpleCommand<SetPurchaseOrderStatusForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PurchaseOrderStatusChoice", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of SetPurchaseOrderStatusCommand */
    public SetPurchaseOrderStatusCommand(UserVisitPK userVisitPK, SetPurchaseOrderStatusForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var purchaseOrderLogic = PurchaseOrderLogic.getInstance();
        var order = purchaseOrderLogic.getOrderByName(this, form.getOrderName());
        
        if(!hasExecutionErrors()) {
            var purchaseOrderStatusChoice = form.getPurchaseOrderStatusChoice();
            
            purchaseOrderLogic.setPurchaseOrderStatus(getSession(), this, order, purchaseOrderStatusChoice, getPartyPK());
        }
        
        return null;
    }
    
}
