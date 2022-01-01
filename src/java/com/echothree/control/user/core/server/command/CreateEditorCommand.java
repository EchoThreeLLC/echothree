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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CreateEditorForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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

public class CreateEditorCommand
        extends BaseSimpleCommand<CreateEditorForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Editor.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EditorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("HasDimensions", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("MinimumHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MinimumWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("DefaultHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("DefaultWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateEditorCommand */
    public CreateEditorCommand(UserVisitPK userVisitPK, CreateEditorForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        String editorName = form.getEditorName();
        Editor editor = coreControl.getEditorByName(editorName);
        
        if(editor == null) {
            var partyPK = getPartyPK();
            Boolean hasDimensions = Boolean.valueOf(form.getHasDimensions());
            String strMinimumHeight = form.getMinimumHeight();
            Integer minimumHeight = strMinimumHeight == null ? null : Integer.valueOf(strMinimumHeight);
            String strMinimumWidth = form.getMinimumWidth();
            Integer minimumWidth = strMinimumWidth == null ? null : Integer.valueOf(strMinimumWidth);
            String strMaximumHeight = form.getMaximumHeight();
            Integer maximumHeight = strMaximumHeight == null ? null : Integer.valueOf(strMaximumHeight);
            String strMaximumWidth = form.getMaximumWidth();
            Integer maximumWidth = strMaximumWidth == null ? null : Integer.valueOf(strMaximumWidth);
            String strDefaultHeight = form.getDefaultHeight();
            Integer defaultHeight = strDefaultHeight == null ? null : Integer.valueOf(strDefaultHeight);
            String strDefaultWidth = form.getDefaultWidth();
            Integer defaultWidth = strDefaultWidth == null ? null : Integer.valueOf(strDefaultWidth);
            var isDefault = Boolean.valueOf(form.getIsDefault());
            var sortOrder = Integer.valueOf(form.getSortOrder());
            var description = form.getDescription();
            
            editor = coreControl.createEditor(editorName, hasDimensions, minimumHeight, minimumWidth, maximumHeight, maximumWidth, defaultHeight, defaultWidth,
                    isDefault, sortOrder, partyPK);
            
            if(description != null) {
                coreControl.createEditorDescription(editor, getPreferredLanguage(), description, partyPK);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateEditorName.name(), editorName);
        }
        
        return null;
    }
    
}
