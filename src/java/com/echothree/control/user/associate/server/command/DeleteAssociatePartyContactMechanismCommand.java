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

package com.echothree.control.user.associate.server.command;

import com.echothree.control.user.associate.common.form.DeleteAssociatePartyContactMechanismForm;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.model.data.associate.server.entity.AssociatePartyContactMechanism;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
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

public class DeleteAssociatePartyContactMechanismCommand
        extends BaseSimpleCommand<DeleteAssociatePartyContactMechanismForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("AssociateName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("AssociatePartyContactMechanismName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of DeleteAssociatePartyContactMechanismCommand */
    public DeleteAssociatePartyContactMechanismCommand(UserVisitPK userVisitPK, DeleteAssociatePartyContactMechanismForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var associateControl = Session.getModelController(AssociateControl.class);
        String associateProgramName = form.getAssociateProgramName();
        AssociateProgram associateProgram = associateControl.getAssociateProgramByName(associateProgramName);
        
        if(associateProgram != null) {
            String associateName = form.getAssociateName();
            Associate associate = associateControl.getAssociateByName(associateProgram, associateName);
            
            if(associate != null) {
                String associatePartyContactMechanismName = form.getAssociatePartyContactMechanismName();
                AssociatePartyContactMechanism associatePartyContactMechanism = associateControl.getAssociatePartyContactMechanismByNameForUpdate(associate, associatePartyContactMechanismName);
                
                if(associatePartyContactMechanism != null) {
                    associateControl.deleteAssociatePartyContactMechanism(associatePartyContactMechanism, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownAssociatePartyContactMechanismName.name(), associatePartyContactMechanismName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownAssociateName.name(), associateName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownAssociateProgramName.name(), associateProgramName);
        }
        
        return null;
    }
    
}
