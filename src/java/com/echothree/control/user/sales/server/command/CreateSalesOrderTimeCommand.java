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

package com.echothree.control.user.sales.server.command;

import com.echothree.control.user.sales.common.form.CreateSalesOrderTimeForm;
import com.echothree.model.control.sales.server.logic.SalesOrderTimeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateSalesOrderTimeCommand
        extends BaseSimpleCommand<CreateSalesOrderTimeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Time", FieldType.DATE_TIME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateSalesOrderTimeCommand */
    public CreateSalesOrderTimeCommand(UserVisitPK userVisitPK, CreateSalesOrderTimeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String orderName = form.getOrderName();
        String orderTimeTypeName = form.getOrderTimeTypeName();
        Long time = Long.valueOf(form.getTime());
        
        SalesOrderTimeLogic.getInstance().createOrderTime(this, orderName, orderTimeTypeName, time, getPartyPK());
        
        return null;
    }
    
}
