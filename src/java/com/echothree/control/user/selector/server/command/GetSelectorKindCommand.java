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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.form.GetSelectorKindForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.selector.server.logic.SelectorKindLogic;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetSelectorKindCommand
        extends BaseSingleEntityCommand<SelectorKind, GetSelectorKindForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SelectorKind.name(), SecurityRoles.Review.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
        ));
    }

    /** Creates a new instance of GetSelectorKindCommand */
    public GetSelectorKindCommand(UserVisitPK userVisitPK, GetSelectorKindForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected SelectorKind getEntity() {
        var selectorKind = SelectorKindLogic.getInstance().getSelectorKindByUniversalSpec(this, form, true);

        if(selectorKind != null) {
            sendEvent(selectorKind.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return selectorKind;
    }

    @Override
    protected BaseResult getResult(SelectorKind selectorKind) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var result = SelectorResultFactory.getGetSelectorKindResult();

        if(selectorKind != null) {
            result.setSelectorKind(selectorControl.getSelectorKindTransfer(getUserVisit(), selectorKind));
        }

        return result;
    }

}
