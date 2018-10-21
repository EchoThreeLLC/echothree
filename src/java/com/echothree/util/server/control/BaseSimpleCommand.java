// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.util.server.control;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeDetail;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.remote.form.BaseForm;
import com.echothree.util.remote.form.ValidationResult;
import com.echothree.util.server.validation.Validator;
import java.util.List;

public abstract class BaseSimpleCommand<F extends BaseForm>
        extends BaseCommand {
    
    protected F form;
    private List<FieldDefinition> formFieldDefinitions;
    
    private void init(F form, List<FieldDefinition> formFieldDefinitions, boolean allowLimits) {
        this.form = form;
        this.formFieldDefinitions = formFieldDefinitions;
        
        if(form != null) {
            session.setOptions(form.getOptions());
            session.setTransferProperties(form.getTransferProperties());
            
            if(allowLimits) {
                session.setLimits(form.getLimits());
            }
        }
    }
    
    protected BaseSimpleCommand(UserVisitPK userVisitPK, F form, CommandSecurityDefinition commandSecurityDefinition,
            List<FieldDefinition> formFieldDefinitions, boolean allowLimits) {
        super(userVisitPK, commandSecurityDefinition);
        
        init(form, formFieldDefinitions, allowLimits);
    }
    
    protected BaseSimpleCommand(UserVisitPK userVisitPK, CommandSecurityDefinition commandSecurityDefinition, boolean allowLimits) {
        super(userVisitPK, commandSecurityDefinition);
        
        init(null, null, allowLimits);
    }

    protected F getForm() {
        return form;
    }

    protected List<FieldDefinition> getFormFieldDefinitions() {
        return formFieldDefinitions;
    }

    protected void setupValidator(Validator validator) {
        // Nothing.
    }

    protected ValidationResult validate(Validator validator) {
        return validator.validate(form, getFormFieldDefinitions());
    }
    
    protected void setupPreferredClobMimeType() {
        String preferredClobMimeTypeName = form.getPreferredClobMimeTypeName();
        
        if(preferredClobMimeTypeName != null) {
            CoreControl coreControl = getCoreControl();
            MimeType preferredClobMimeType = coreControl.getMimeTypeByName(preferredClobMimeTypeName);

            if(preferredClobMimeType == null) {
                addExecutionError(ExecutionErrors.UnknownPreferredClobMimeTypeName.name(), preferredClobMimeTypeName);
            } else {
                MimeTypeDetail preferredClobMimeTypeDetail = preferredClobMimeType.getLastDetail();
                String entityAttributeTypeName = preferredClobMimeTypeDetail.getEntityAttributeType().getEntityAttributeTypeName();
                
                // Must be a CLOB and for now the preferred MIME type must be HTML.
                if(!entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())
                        || !preferredClobMimeTypeDetail.getMimeTypeName().equals(MimeTypes.TEXT_HTML.mimeTypeName())) {
                    addExecutionError(ExecutionErrors.InvalidPreferredClobMimeType.name(), preferredClobMimeTypeName, entityAttributeTypeName);
                } else {
                    session.setPreferredClobMimeType(preferredClobMimeType);
                }
            }
        }
    }
    
    @Override
    protected ValidationResult validate() {
        ValidationResult validationResult = null;
        
        if(getFormFieldDefinitions() != null) {
            Validator validator = new Validator(this);
            
            setupValidator(validator);
            validationResult = validate(validator);
        }
        
        return validationResult;
    }
    
}
