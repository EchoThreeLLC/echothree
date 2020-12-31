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

package com.echothree.control.user.forum.server.command;

import com.echothree.control.user.forum.common.form.CreateForumMessageTypePartTypeForm;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMessagePartType;
import com.echothree.model.data.forum.server.entity.ForumMessageType;
import com.echothree.model.data.forum.server.entity.ForumMessageTypePartType;
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

public class CreateForumMessageTypePartTypeCommand
        extends BaseSimpleCommand<CreateForumMessageTypePartTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumMessageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IncludeInIndex", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IndexDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("ForumMessagePartTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateForumMessageTypePartTypeCommand */
    public CreateForumMessageTypePartTypeCommand(UserVisitPK userVisitPK, CreateForumMessageTypePartTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = Session.getModelController(ForumControl.class);
        String forumMessageTypeName = form.getForumMessageTypeName();
        ForumMessageType forumMessageType = forumControl.getForumMessageTypeByName(forumMessageTypeName);
        
        if(forumMessageType != null) {
            var sortOrder = Integer.valueOf(form.getSortOrder());
            ForumMessageTypePartType forumMessageTypePartType = forumControl.getForumMessageTypePartType(forumMessageType, sortOrder);
            
            if(forumMessageTypePartType == null) {
                String forumMessagePartTypeName = form.getForumMessagePartTypeName();
                ForumMessagePartType forumMessagePartType = forumControl.getForumMessagePartTypeByName(forumMessagePartTypeName);
                
                if(forumMessagePartType != null) {
                    Boolean includeInIndex = Boolean.valueOf(form.getIncludeInIndex());
                    Boolean indexDefault = Boolean.valueOf(form.getIndexDefault());
                    
                    forumControl.createForumMessageTypePartType(forumMessageType, includeInIndex, indexDefault, sortOrder, forumMessagePartType);
                } else {
                    addExecutionError(ExecutionErrors.UnknownForumMessagePartTypeName.name(), forumMessagePartTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateForumMessageTypePartType.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumMessageTypeName.name(), forumMessageTypeName);
        }
        
        return null;
    }
    
}
