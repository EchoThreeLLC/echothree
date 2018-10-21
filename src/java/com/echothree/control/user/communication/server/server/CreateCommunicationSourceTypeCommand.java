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

package com.echothree.control.user.communication.server.server;

import com.echothree.control.user.communication.remote.form.CreateCommunicationSourceTypeForm;
import com.echothree.model.control.communication.server.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationSourceType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCommunicationSourceTypeCommand
        extends BaseSimpleCommand<CreateCommunicationSourceTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("CommunicationSourceTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateCommunicationSourceTypeCommand */
    public CreateCommunicationSourceTypeCommand(UserVisitPK userVisitPK, CreateCommunicationSourceTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CommunicationControl communicationControl = (CommunicationControl)Session.getModelController(CommunicationControl.class);
        String communicationSourceTypeName = form.getCommunicationSourceTypeName();
        CommunicationSourceType communicationSourceType = communicationControl.getCommunicationSourceTypeByName(communicationSourceTypeName);
        
        if(communicationSourceType == null) {
            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
            Integer sortOrder = Integer.valueOf(form.getSortOrder());
            
            communicationControl.createCommunicationSourceType(communicationSourceTypeName, isDefault, sortOrder);
        } else {
            addExecutionError(ExecutionErrors.DuplicateCommunicationSourceTypeName.name(), communicationSourceTypeName);
        }
        
        return null;
    }
    
}
