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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.edit.ChainTypeDescriptionEdit;
import com.echothree.control.user.chain.common.form.EditChainTypeDescriptionForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainTypeDescriptionResult;
import com.echothree.control.user.chain.common.spec.ChainTypeDescriptionSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.chain.server.entity.ChainTypeDescription;
import com.echothree.model.data.chain.server.value.ChainTypeDescriptionValue;
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

public class EditChainTypeDescriptionCommand
        extends BaseAbstractEditCommand<ChainTypeDescriptionSpec, ChainTypeDescriptionEdit, EditChainTypeDescriptionResult, ChainTypeDescription, ChainType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditChainTypeDescriptionCommand */
    public EditChainTypeDescriptionCommand(UserVisitPK userVisitPK, EditChainTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainTypeDescriptionResult getResult() {
        return ChainResultFactory.getEditChainTypeDescriptionResult();
    }

    @Override
    public ChainTypeDescriptionEdit getEdit() {
        return ChainEditFactory.getChainTypeDescriptionEdit();
    }

    @Override
    public ChainTypeDescription getEntity(EditChainTypeDescriptionResult result) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainTypeDescription chainTypeDescription = null;
        String chainKindName = spec.getChainKindName();
        ChainKind chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind != null) {
            String chainTypeName = spec.getChainTypeName();
            ChainType chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

            if(chainType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        chainTypeDescription = chainControl.getChainTypeDescription(chainType, language);
                    } else { // EditMode.UPDATE
                        chainTypeDescription = chainControl.getChainTypeDescriptionForUpdate(chainType, language);
                    }

                    if(chainTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownChainTypeDescription.name(), chainKindName, chainTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return chainTypeDescription;
    }

    @Override
    public ChainType getLockEntity(ChainTypeDescription chainTypeDescription) {
        return chainTypeDescription.getChainType();
    }

    @Override
    public void fillInResult(EditChainTypeDescriptionResult result, ChainTypeDescription chainTypeDescription) {
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainTypeDescription(chainControl.getChainTypeDescriptionTransfer(getUserVisit(), chainTypeDescription));
    }

    @Override
    public void doLock(ChainTypeDescriptionEdit edit, ChainTypeDescription chainTypeDescription) {
        edit.setDescription(chainTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ChainTypeDescription chainTypeDescription) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainTypeDescriptionValue chainTypeDescriptionValue = chainControl.getChainTypeDescriptionValue(chainTypeDescription);

        chainTypeDescriptionValue.setDescription(edit.getDescription());

        chainControl.updateChainTypeDescriptionFromValue(chainTypeDescriptionValue, getPartyPK());
    }

}
