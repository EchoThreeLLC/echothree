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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.form.CreateSelectorForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.logic.SelectorLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;

public class CreateSelectorCommand
        extends BaseSimpleCommand<CreateSelectorForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Selector.name(), SecurityRoles.Create.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of CreateSelectorCommand */
    public CreateSelectorCommand(UserVisitPK userVisitPK, CreateSelectorForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SelectorResultFactory.getCreateSelectorResult();
        var selectorKindName = form.getSelectorKindName();
        var selectorTypeName = form.getSelectorTypeName();
        var selectorName = form.getSelectorName();
        var isDefault = Boolean.valueOf(form.getIsDefault());
        var sortOrder = Integer.valueOf(form.getSortOrder());
        var description = form.getDescription();
        var createdBy = getPartyPK();

        var selector = SelectorLogic.getInstance().createSelector(this, selectorKindName, selectorTypeName,
                selectorName, isDefault, sortOrder, getPreferredLanguage(), description, createdBy);

        if(selector != null) {
            var selectorDetail = selector.getLastDetail();
            var selectorTypeDetail = selectorDetail.getSelectorType().getLastDetail();

            result.setEntityRef(selector.getPrimaryKey().getEntityRef());
            result.setSelectorKindName(selectorTypeDetail.getSelectorKind().getLastDetail().getSelectorKindName());
            result.setSelectorTypeName(selectorTypeDetail.getSelectorTypeName());
            result.setSelectorName(selectorDetail.getSelectorName());
        }
        
        return result;
    }
    
}
