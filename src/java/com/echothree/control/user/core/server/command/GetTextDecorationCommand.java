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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetTextDecorationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTextDecorationCommand
        extends BaseSingleEntityCommand<TextDecoration, GetTextDecorationForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TextDecorationName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetTextDecorationCommand */
    public GetTextDecorationCommand(UserVisitPK userVisitPK, GetTextDecorationForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected TextDecoration getEntity() {
        var coreControl = getCoreControl();
        TextDecoration textDecoration = null;
        var textDecorationName = form.getTextDecorationName();
        var parameterCount = (textDecorationName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            if(textDecorationName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.TextDecoration.name());

                if(!hasExecutionErrors()) {
                    textDecoration = coreControl.getTextDecorationByEntityInstance(entityInstance);
                }
            } else {
                textDecoration = AppearanceLogic.getInstance().getTextDecorationByName(this, textDecorationName);
            }

            if(textDecoration != null) {
                sendEvent(textDecoration.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return textDecoration;
    }
    
    @Override
    protected BaseResult getResult(TextDecoration textDecoration) {
        var coreControl = getCoreControl();
        var result = CoreResultFactory.getGetTextDecorationResult();

        if(textDecoration != null) {
            result.setTextDecoration(coreControl.getTextDecorationTransfer(getUserVisit(), textDecoration));
        }

        return result;
    }
    
}
