// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.edit.ChainActionTypeDescriptionEdit;
import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.form.EditChainActionTypeDescriptionForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainActionTypeDescriptionResult;
import com.echothree.control.user.chain.common.spec.ChainActionTypeDescriptionSpec;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainActionType;
import com.echothree.model.data.chain.server.entity.ChainActionTypeDescription;
import com.echothree.model.data.chain.server.value.ChainActionTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditChainActionTypeDescriptionCommand
        extends BaseAbstractEditCommand<ChainActionTypeDescriptionSpec, ChainActionTypeDescriptionEdit, EditChainActionTypeDescriptionResult, ChainActionTypeDescription, ChainActionType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainActionType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainActionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditChainActionTypeDescriptionCommand */
    public EditChainActionTypeDescriptionCommand(UserVisitPK userVisitPK, EditChainActionTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainActionTypeDescriptionResult getResult() {
        return ChainResultFactory.getEditChainActionTypeDescriptionResult();
    }

    @Override
    public ChainActionTypeDescriptionEdit getEdit() {
        return ChainEditFactory.getChainActionTypeDescriptionEdit();
    }

    @Override
    public ChainActionTypeDescription getEntity(EditChainActionTypeDescriptionResult result) {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        ChainActionTypeDescription chainActionTypeDescription = null;
        String chainActionTypeName = spec.getChainActionTypeName();
        ChainActionType chainActionType = chainControl.getChainActionTypeByName(chainActionTypeName);

        if(chainActionType != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    chainActionTypeDescription = chainControl.getChainActionTypeDescription(chainActionType, language);
                } else { // EditMode.UPDATE
                    chainActionTypeDescription = chainControl.getChainActionTypeDescriptionForUpdate(chainActionType, language);
                }

                if(chainActionTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownChainActionTypeDescription.name(), chainActionTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainActionTypeName.name(), chainActionTypeName);
        }

        return chainActionTypeDescription;
    }

    @Override
    public ChainActionType getLockEntity(ChainActionTypeDescription chainActionTypeDescription) {
        return chainActionTypeDescription.getChainActionType();
    }

    @Override
    public void fillInResult(EditChainActionTypeDescriptionResult result, ChainActionTypeDescription chainActionTypeDescription) {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);

        result.setChainActionTypeDescription(chainControl.getChainActionTypeDescriptionTransfer(getUserVisit(), chainActionTypeDescription));
    }

    @Override
    public void doLock(ChainActionTypeDescriptionEdit edit, ChainActionTypeDescription chainActionTypeDescription) {
        edit.setDescription(chainActionTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ChainActionTypeDescription chainActionTypeDescription) {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        ChainActionTypeDescriptionValue chainActionTypeDescriptionValue = chainControl.getChainActionTypeDescriptionValue(chainActionTypeDescription);

        chainActionTypeDescriptionValue.setDescription(edit.getDescription());

        chainControl.updateChainActionTypeDescriptionFromValue(chainActionTypeDescriptionValue, getPartyPK());
    }

}
