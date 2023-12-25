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

import com.echothree.control.user.core.common.form.GetApplicationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetApplicationResult;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetApplicationCommand
        extends BaseSimpleCommand<GetApplicationForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetApplicationCommand */
    public GetApplicationCommand(UserVisitPK userVisitPK, GetApplicationForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetApplicationResult result = CoreResultFactory.getGetApplicationResult();
        String applicationName = form.getApplicationName();
        var parameterCount = (applicationName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            var coreControl = getCoreControl();
            Application application = null;

            if(applicationName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.Application.name());
                
                if(!hasExecutionErrors()) {
                    application = coreControl.getApplicationByEntityInstance(entityInstance);
                }
            } else {
                application = coreControl.getApplicationByName(applicationName);

                if(application == null) {
                    addExecutionError(ExecutionErrors.UnknownApplicationName.name(), applicationName);
                }
            }

            if(!hasExecutionErrors()) {
                result.setApplication(coreControl.getApplicationTransfer(getUserVisit(), application));
                sendEvent(application.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
