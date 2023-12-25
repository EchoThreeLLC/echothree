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

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.ProtocolDescriptionEdit;
import com.echothree.control.user.core.common.form.EditProtocolDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditProtocolDescriptionResult;
import com.echothree.control.user.core.common.spec.ProtocolDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Protocol;
import com.echothree.model.data.core.server.entity.ProtocolDescription;
import com.echothree.model.data.core.server.value.ProtocolDescriptionValue;
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

public class EditProtocolDescriptionCommand
        extends BaseAbstractEditCommand<ProtocolDescriptionSpec, ProtocolDescriptionEdit, EditProtocolDescriptionResult, ProtocolDescription, Protocol> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Protocol.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ProtocolName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditProtocolDescriptionCommand */
    public EditProtocolDescriptionCommand(UserVisitPK userVisitPK, EditProtocolDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditProtocolDescriptionResult getResult() {
        return CoreResultFactory.getEditProtocolDescriptionResult();
    }

    @Override
    public ProtocolDescriptionEdit getEdit() {
        return CoreEditFactory.getProtocolDescriptionEdit();
    }

    @Override
    public ProtocolDescription getEntity(EditProtocolDescriptionResult result) {
        var coreControl = getCoreControl();
        ProtocolDescription protocolDescription = null;
        String protocolName = spec.getProtocolName();
        Protocol protocol = coreControl.getProtocolByName(protocolName);

        if(protocol != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    protocolDescription = coreControl.getProtocolDescription(protocol, language);
                } else { // EditMode.UPDATE
                    protocolDescription = coreControl.getProtocolDescriptionForUpdate(protocol, language);
                }

                if(protocolDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownProtocolDescription.name(), protocolName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownProtocolName.name(), protocolName);
        }

        return protocolDescription;
    }

    @Override
    public Protocol getLockEntity(ProtocolDescription protocolDescription) {
        return protocolDescription.getProtocol();
    }

    @Override
    public void fillInResult(EditProtocolDescriptionResult result, ProtocolDescription protocolDescription) {
        var coreControl = getCoreControl();

        result.setProtocolDescription(coreControl.getProtocolDescriptionTransfer(getUserVisit(), protocolDescription));
    }

    @Override
    public void doLock(ProtocolDescriptionEdit edit, ProtocolDescription protocolDescription) {
        edit.setDescription(protocolDescription.getDescription());
    }

    @Override
    public void doUpdate(ProtocolDescription protocolDescription) {
        var coreControl = getCoreControl();
        ProtocolDescriptionValue protocolDescriptionValue = coreControl.getProtocolDescriptionValue(protocolDescription);
        protocolDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateProtocolDescriptionFromValue(protocolDescriptionValue, getPartyPK());
    }
    
}
