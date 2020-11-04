// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.sequence.server.command;

import com.echothree.control.user.sequence.common.form.GetSequenceEncoderTypesForm;
import com.echothree.control.user.sequence.common.result.SequenceResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderType;
import com.echothree.model.data.sequence.server.factory.SequenceEncoderTypeFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetSequenceEncoderTypesCommand
        extends BaseMultipleEntitiesCommand<SequenceEncoderType, GetSequenceEncoderTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SequenceEncoderType.name(), SecurityRoles.List.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
        ));
    }

    /** Creates a new instance of GetSequenceEncoderTypesCommand */
    public GetSequenceEncoderTypesCommand(UserVisitPK userVisitPK, GetSequenceEncoderTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<SequenceEncoderType> getEntities() {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);

        return sequenceControl.getSequenceEncoderTypes();
    }

    @Override
    protected BaseResult getTransfers(Collection<SequenceEncoderType> entities) {
        var result = SequenceResultFactory.getGetSequenceEncoderTypesResult();
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);

        if(session.hasLimit(SequenceEncoderTypeFactory.class)) {
            result.setSequenceEncoderTypeCount(sequenceControl.countSequenceEncoderTypes());
        }

        result.setSequenceEncoderTypes(sequenceControl.getSequenceEncoderTypeTransfers(getUserVisit(), entities));

        return result;
    }

}
