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

package com.echothree.util.server.validation.fieldtype;

import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.validation.Validator;

public class StringFieldType
        extends BaseFieldType {
    
    /** Creates a new instance of StringFieldType */
    public StringFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue, String []splitFieldName, FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    @Override
    public String validate() {
        Long minimumValue = fieldDefinition.getMinimumValue();
        Long maximumValue = fieldDefinition.getMaximumValue();
        boolean hadErrors = false;
        
        if(minimumValue != null || maximumValue != null) {
            int length = fieldValue.codePointCount(0, fieldValue.length());
            
            if(minimumValue != null && length < minimumValue) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_MINIMUM_LENGTH, minimumValue));
                hadErrors = true;
            }
            
            if(maximumValue != null && length > maximumValue) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_MAXIMUM_LENGTH, maximumValue));
                hadErrors = true;
            }
        }
        
        return hadErrors? null: fieldValue;
    }
    
}
