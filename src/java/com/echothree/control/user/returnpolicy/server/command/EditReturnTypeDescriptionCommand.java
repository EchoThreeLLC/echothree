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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyEditFactory;
import com.echothree.control.user.returnpolicy.common.edit.ReturnTypeDescriptionEdit;
import com.echothree.control.user.returnpolicy.common.form.EditReturnTypeDescriptionForm;
import com.echothree.control.user.returnpolicy.common.result.EditReturnTypeDescriptionResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeDescription;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeDescriptionValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditReturnTypeDescriptionCommand
        extends BaseAbstractEditCommand<ReturnTypeDescriptionSpec, ReturnTypeDescriptionEdit, EditReturnTypeDescriptionResult, ReturnTypeDescription, ReturnType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditReturnTypeDescriptionCommand */
    public EditReturnTypeDescriptionCommand(UserVisitPK userVisitPK, EditReturnTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditReturnTypeDescriptionResult getResult() {
        return ReturnPolicyResultFactory.getEditReturnTypeDescriptionResult();
    }

    @Override
    public ReturnTypeDescriptionEdit getEdit() {
        return ReturnPolicyEditFactory.getReturnTypeDescriptionEdit();
    }

    @Override
    public ReturnTypeDescription getEntity(EditReturnTypeDescriptionResult result) {
        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        ReturnTypeDescription returnTypeDescription = null;
        String returnKindName = spec.getReturnKindName();
        ReturnKind returnKind = returnPolicyControl.getReturnKindByName(returnKindName);

        if(returnKind != null) {
            String returnTypeName = spec.getReturnTypeName();
            ReturnType returnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName);

            if(returnType != null) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        returnTypeDescription = returnPolicyControl.getReturnTypeDescription(returnType, language);
                    } else { // EditMode.UPDATE
                        returnTypeDescription = returnPolicyControl.getReturnTypeDescriptionForUpdate(returnType, language);
                    }

                    if(returnTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownReturnTypeDescription.name(), returnKindName, returnTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownReturnTypeName.name(), returnKindName, returnTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }

        return returnTypeDescription;
    }

    @Override
    public ReturnType getLockEntity(ReturnTypeDescription returnTypeDescription) {
        return returnTypeDescription.getReturnType();
    }

    @Override
    public void fillInResult(EditReturnTypeDescriptionResult result, ReturnTypeDescription returnTypeDescription) {
        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);

        result.setReturnTypeDescription(returnPolicyControl.getReturnTypeDescriptionTransfer(getUserVisit(), returnTypeDescription));
    }

    @Override
    public void doLock(ReturnTypeDescriptionEdit edit, ReturnTypeDescription returnTypeDescription) {
        edit.setDescription(returnTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ReturnTypeDescription returnTypeDescription) {
        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        ReturnTypeDescriptionValue returnTypeDescriptionValue = returnPolicyControl.getReturnTypeDescriptionValue(returnTypeDescription);

        returnTypeDescriptionValue.setDescription(edit.getDescription());

        returnPolicyControl.updateReturnTypeDescriptionFromValue(returnTypeDescriptionValue, getPartyPK());
    }

}
