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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.DeleteApplicationEditorForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.Editor;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteApplicationEditorCommand
        extends BaseSimpleCommand<DeleteApplicationEditorForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ApplicationEditor.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EditorName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteApplicationEditorCommand */
    public DeleteApplicationEditorCommand(UserVisitPK userVisitPK, DeleteApplicationEditorForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String applicationName = form.getApplicationName();
        Application application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);
        
        if(!hasExecutionErrors()) {
            String editorName = form.getEditorName();
            Editor editor = ApplicationLogic.getInstance().getEditorByName(this, editorName);
            
            if(!hasExecutionErrors()) {
                CoreControl coreControl = getCoreControl();
                ApplicationEditor applicationEditor = coreControl.getApplicationEditorForUpdate(application, editor);
                
                if(applicationEditor != null) {
                    coreControl.deleteApplicationEditor(applicationEditor, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownApplicationEditor.name(), applicationName, editorName);
                }
            }
        }
        
        return null;
    }
    
}
