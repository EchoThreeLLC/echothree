// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.control.user.comment.server.command;

import com.echothree.control.user.comment.common.form.CreateCommentUsageTypeDescriptionForm;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.comment.server.entity.CommentUsageType;
import com.echothree.model.data.comment.server.entity.CommentUsageTypeDescription;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
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

public class CreateCommentUsageTypeDescriptionCommand
        extends BaseSimpleCommand<CreateCommentUsageTypeDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommentUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateCommentUsageTypeDescriptionCommand */
    public CreateCommentUsageTypeDescriptionCommand(UserVisitPK userVisitPK, CreateCommentUsageTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        String componentVendorName = form.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            String entityTypeName = form.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var commentControl = Session.getModelController(CommentControl.class);
                String commentTypeName = form.getCommentTypeName();
                CommentType commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
                
                if(commentType != null) {
                    String commentUsageTypeName = form.getCommentUsageTypeName();
                    CommentUsageType commentUsageType = commentControl.getCommentUsageTypeByName(commentType, commentUsageTypeName);
                    
                    if(commentUsageType != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        String languageIsoName = form.getLanguageIsoName();
                        Language language = partyControl.getLanguageByIsoName(languageIsoName);
                        
                        if(language != null) {
                            CommentUsageTypeDescription commentUsageTypeDescription = commentControl.getCommentUsageTypeDescription(commentUsageType, language);
                            
                            if(commentUsageTypeDescription == null) {
                                var description = form.getDescription();
                                
                                commentControl.createCommentUsageTypeDescription(commentUsageType, language, description, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.DuplicateCommentTypeDescription.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommentUsageTypeName.name(), commentUsageTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCommentTypeName.name(), commentTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }
        
        return null;
    }
    
}
