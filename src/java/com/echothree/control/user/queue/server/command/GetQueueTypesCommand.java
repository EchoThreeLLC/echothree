// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.queue.server.command;

import com.echothree.control.user.queue.common.form.GetQueueTypesForm;
import com.echothree.control.user.queue.common.result.QueueResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetQueueTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<QueueType, GetQueueTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.QueueType.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetQueueTypesCommand */
    public GetQueueTypesCommand(UserVisitPK userVisitPK, GetQueueTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var queueControl = Session.getModelController(QueueControl.class);

        return queueControl.countQueueTypes();
    }

    @Override
    protected Collection<QueueType> getEntities() {
        var queueControl = Session.getModelController(QueueControl.class);

        return queueControl.getQueueTypes();
    }

    @Override
    protected BaseResult getResult(Collection<QueueType> entities) {
        var result = QueueResultFactory.getGetQueueTypesResult();

        if(entities != null) {
            var queueControl = Session.getModelController(QueueControl.class);

            result.setQueueTypes(queueControl.getQueueTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
