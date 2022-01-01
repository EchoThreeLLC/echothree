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

package com.echothree.control.user.forum.server.command;

import com.echothree.control.user.forum.common.form.CreateForumMessagePartTypeForm;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.forum.server.entity.ForumMessagePartType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateForumMessagePartTypeCommand
        extends BaseSimpleCommand<CreateForumMessagePartTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ForumMessagePartTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateForumMessagePartTypeCommand */
    public CreateForumMessagePartTypeCommand(UserVisitPK userVisitPK, CreateForumMessagePartTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = Session.getModelController(ForumControl.class);
        String forumMessagePartTypeName = form.getForumMessagePartTypeName();
        ForumMessagePartType forumMessagePartType = forumControl.getForumMessagePartTypeByName(forumMessagePartTypeName);
        
        if(forumMessagePartType == null) {
            var coreControl = getCoreControl();
            String mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();
            MimeTypeUsageType mimeTypeUsageType = coreControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);
            
            if(mimeTypeUsageTypeName == null || mimeTypeUsageType != null) {
                var sortOrder = Integer.valueOf(form.getSortOrder());
                
                forumControl.createForumMessagePartType(forumMessagePartTypeName, mimeTypeUsageType, sortOrder);
            } else {
                addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateForumMessagePartTypeName.name(), forumMessagePartTypeName);
        }
        
        return null;
    }
    
}
