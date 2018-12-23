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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.form.DeleteScaleUseTypeForm;
import com.echothree.model.control.scale.server.ScaleControl;
import com.echothree.model.data.scale.server.entity.ScaleUseType;
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

public class DeleteScaleUseTypeCommand
        extends BaseSimpleCommand<DeleteScaleUseTypeForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of DeleteScaleUseTypeCommand */
    public DeleteScaleUseTypeCommand(UserVisitPK userVisitPK, DeleteScaleUseTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        ScaleControl scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
        String scaleUseTypeName = form.getScaleUseTypeName();
        ScaleUseType scaleUseType = scaleControl.getScaleUseTypeByNameForUpdate(scaleUseTypeName);
        
        if(scaleUseType != null) {
            scaleControl.deleteScaleUseType(scaleUseType, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownScaleUseTypeName.name(), scaleUseTypeName);
        }
        
        return null;
    }
    
}
