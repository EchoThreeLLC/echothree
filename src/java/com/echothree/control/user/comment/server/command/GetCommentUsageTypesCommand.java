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

package com.echothree.control.user.comment.server.command;

import com.echothree.control.user.comment.common.form.GetCommentUsageTypesForm;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.control.user.comment.common.result.GetCommentUsageTypesResult;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetCommentUsageTypesCommand
        extends BaseSimpleCommand<GetCommentUsageTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetCommentUsageTypesCommand */
    public GetCommentUsageTypesCommand(UserVisitPK userVisitPK, GetCommentUsageTypesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CommentResultFactory.getGetCommentUsageTypesResult();
        var coreControl = getCoreControl();
        var componentVendorName = form.getComponentVendorName();
        var componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            var userVisit = getUserVisit();
            var entityTypeName = form.getEntityTypeName();
            var entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            result.setComponentVendor(coreControl.getComponentVendorTransfer(userVisit, componentVendor));
            
            if(entityType != null) {
                var commentControl = Session.getModelController(CommentControl.class);
                var commentTypeName = form.getCommentTypeName();
                var commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
                
                result.setEntityType(coreControl.getEntityTypeTransfer(userVisit, entityType));
                
                if(commentType != null) {
                    result.setCommentType(commentControl.getCommentTypeTransfer(userVisit, commentType));
                    result.setCommentUsageTypes(commentControl.getCommentUsageTypeTransfers(userVisit, commentType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownCommentTypeName.name(), commentTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }
        
        return result;
    }
    
}
