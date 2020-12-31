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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetColorForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetColorResult;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetColorCommand
        extends BaseSingleEntityCommand<Color, GetColorForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ColorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetColorCommand */
    public GetColorCommand(UserVisitPK userVisitPK, GetColorForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Color getEntity() {
        var coreControl = getCoreControl();
        Color color = null;
        String colorName = form.getColorName();
        int parameterCount = (colorName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            if(colorName == null) {
                EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                        ComponentVendors.ECHOTHREE.name(), EntityTypes.Color.name());

                if(!hasExecutionErrors()) {
                    color = coreControl.getColorByEntityInstance(entityInstance);
                }
            } else {
                color = AppearanceLogic.getInstance().getColorByName(this, colorName);
            }

            if(color != null) {
                sendEventUsingNames(color.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return color;
    }
    
    @Override
    protected BaseResult getTransfer(Color color) {
        var coreControl = getCoreControl();
        GetColorResult result = CoreResultFactory.getGetColorResult();

        if(color != null) {
            result.setColor(coreControl.getColorTransfer(getUserVisit(), color));
        }

        return result;
    }
    
}
