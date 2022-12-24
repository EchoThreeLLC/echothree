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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemAliasForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemAlias;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetItemAliasCommand
        extends BaseSingleEntityCommand<ItemAlias, GetItemAliasForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemAliasCommand */
    public GetItemAliasCommand(UserVisitPK userVisitPK, GetItemAliasForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected ItemAlias getEntity() {
        var itemControl = Session.getModelController(ItemControl.class);
        var alias = form.getAlias();
        var itemAlias = itemControl.getItemAliasByAliasForUpdate(alias);

        if(itemAlias == null) {
            addExecutionError(ExecutionErrors.UnknownItemAlias.name(), alias);
        }

        return itemAlias;
    }

    @Override
    protected BaseResult getTransfer(ItemAlias entity) {
        var result = ItemResultFactory.getGetItemAliasResult();

        if(entity != null) {
            var itemControl = Session.getModelController(ItemControl.class);

            result.setItemAlias(itemControl.getItemAliasTransfer(getUserVisit(), entity));
        }

        return result;
    }

}
