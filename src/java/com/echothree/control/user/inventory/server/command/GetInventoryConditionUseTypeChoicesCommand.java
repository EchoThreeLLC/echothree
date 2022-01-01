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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.GetInventoryConditionUseTypeChoicesForm;
import com.echothree.control.user.inventory.common.result.GetInventoryConditionUseTypeChoicesResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetInventoryConditionUseTypeChoicesCommand
        extends BaseSimpleCommand<GetInventoryConditionUseTypeChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("DefaultInventoryConditionUseTypeChoice", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetInventoryConditionUseTypeChoicesCommand */
    public GetInventoryConditionUseTypeChoicesCommand(UserVisitPK userVisitPK, GetInventoryConditionUseTypeChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        GetInventoryConditionUseTypeChoicesResult result = InventoryResultFactory.getGetInventoryConditionUseTypeChoicesResult();
        String defaultInventoryConditionUseTypeChoice = form.getDefaultInventoryConditionUseTypeChoice();
        
        result.setInventoryConditionUseTypeChoices(inventoryControl.getInventoryConditionUseTypeChoices(defaultInventoryConditionUseTypeChoice, getPreferredLanguage()));
        
        return result;
    }
    
}
