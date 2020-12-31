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

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.PartyApplicationEditorUseEdit;
import com.echothree.control.user.core.common.form.EditPartyApplicationEditorUseForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditPartyApplicationEditorUseResult;
import com.echothree.control.user.core.common.spec.PartyApplicationEditorUseSpec;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.entity.Editor;
import com.echothree.model.data.core.server.entity.PartyApplicationEditorUse;
import com.echothree.model.data.core.server.entity.PartyApplicationEditorUseDetail;
import com.echothree.model.data.core.server.value.PartyApplicationEditorUseDetailValue;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPartyApplicationEditorUseCommand
        extends BaseAbstractEditCommand<PartyApplicationEditorUseSpec, PartyApplicationEditorUseEdit, EditPartyApplicationEditorUseResult, PartyApplicationEditorUse, PartyApplicationEditorUse> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyApplicationEditorUse.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ApplicationEditorUseName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EditorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("PreferredWidth", FieldType.UNSIGNED_INTEGER, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyApplicationEditorUseCommand */
    public EditPartyApplicationEditorUseCommand(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyApplicationEditorUseResult getResult() {
        return CoreResultFactory.getEditPartyApplicationEditorUseResult();
    }

    @Override
    public PartyApplicationEditorUseEdit getEdit() {
        return CoreEditFactory.getPartyApplicationEditorUseEdit();
    }

    Application application;
    
    @Override
    public PartyApplicationEditorUse getEntity(EditPartyApplicationEditorUseResult result) {
        PartyApplicationEditorUse partyApplicationEditorUse = null;
        String partyName = spec.getPartyName();
        Party party = partyName == null ? getParty() : PartyLogic.getInstance().getPartyByName(this, partyName);
        
        if(!hasExecutionErrors()) {
            String applicationName = spec.getApplicationName();
            
            application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
            
            if(!hasExecutionErrors()) {
                String applicationEditorUseName = spec.getApplicationEditorUseName();
                ApplicationEditorUse applicationEditorUse = ApplicationLogic.getInstance().getApplicationEditorUseByName(this, application, applicationEditorUseName);
                
                if(!hasExecutionErrors()) {
                    var coreControl = getCoreControl();
                    
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        partyApplicationEditorUse = coreControl.getPartyApplicationEditorUse(party, applicationEditorUse);
                    } else { // EditMode.UPDATE
                        partyApplicationEditorUse = coreControl.getPartyApplicationEditorUseForUpdate(party, applicationEditorUse);
                    }
                    
                    if(partyApplicationEditorUse == null) {
                        addExecutionError(ExecutionErrors.DuplicatePartyApplicationEditorUse.name(), partyName, applicationName, applicationEditorUseName);
                    }
                }
            }
        }

        return partyApplicationEditorUse;
    }

    @Override
    public PartyApplicationEditorUse getLockEntity(PartyApplicationEditorUse partyApplicationEditorUse) {
        return partyApplicationEditorUse;
    }

    @Override
    public void fillInResult(EditPartyApplicationEditorUseResult result, PartyApplicationEditorUse partyApplicationEditorUse) {
        var coreControl = getCoreControl();

        result.setPartyApplicationEditorUse(coreControl.getPartyApplicationEditorUseTransfer(getUserVisit(), partyApplicationEditorUse));
    }

    ApplicationEditor applicationEditor;
    
    @Override
    public void doLock(PartyApplicationEditorUseEdit edit, PartyApplicationEditorUse partyApplicationEditorUse) {
        PartyApplicationEditorUseDetail partyApplicationEditorUseDetail = partyApplicationEditorUse.getLastDetail();
        Integer preferredHeight = partyApplicationEditorUseDetail.getPreferredHeight();
        Integer preferredWidth = partyApplicationEditorUseDetail.getPreferredWidth();

        applicationEditor = partyApplicationEditorUseDetail.getApplicationEditor();
       
        edit.setEditorName(applicationEditor == null ? null : applicationEditor.getLastDetail().getEditor().getLastDetail().getEditorName());
        edit.setPreferredHeight(preferredHeight == null ? null : preferredHeight.toString());
        edit.setPreferredWidth(preferredWidth == null ? null : preferredWidth.toString());
    }

    @Override
    public void canUpdate(PartyApplicationEditorUse partyApplicationEditorUse) {
        String editorName = edit.getEditorName();
        Editor editor = editorName == null ? null : ApplicationLogic.getInstance().getEditorByName(this, editorName);

        if(!hasExecutionErrors()) {
            applicationEditor = editor == null ? null : ApplicationLogic.getInstance().getApplicationEditor(this, application, editor);
        }
    }

    @Override
    public void doUpdate(PartyApplicationEditorUse partyApplicationEditorUse) {
        var coreControl = getCoreControl();
        var partyPK = getPartyPK();
        PartyApplicationEditorUseDetailValue partyApplicationEditorUseDetailValue = coreControl.getPartyApplicationEditorUseDetailValueForUpdate(partyApplicationEditorUse);
        String strPreferredHeight = edit.getPreferredHeight();
        Integer preferredHeight = strPreferredHeight == null ? null : Integer.valueOf(strPreferredHeight);
        String strPreferredWidth = edit.getPreferredWidth();
        Integer preferredWidth = strPreferredWidth == null ? null : Integer.valueOf(strPreferredWidth);

        partyApplicationEditorUseDetailValue.setApplicationEditorPK(applicationEditor == null ? null : applicationEditor.getPrimaryKey());
        partyApplicationEditorUseDetailValue.setPreferredHeight(preferredHeight);
        partyApplicationEditorUseDetailValue.setPreferredWidth(preferredWidth);
        
        coreControl.updatePartyApplicationEditorUseFromValue(partyApplicationEditorUseDetailValue, partyPK);
    }
    
}
