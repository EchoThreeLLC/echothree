// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.index.server.command;

import com.echothree.control.user.index.common.form.GetIndexFieldForm;
import com.echothree.control.user.index.common.result.GetIndexFieldResult;
import com.echothree.control.user.index.common.result.IndexResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.index.server.entity.IndexField;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetIndexFieldCommand
        extends BaseSimpleCommand<GetIndexFieldForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.IndexField.name(), SecurityRoles.Review.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IndexTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IndexFieldName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetIndexFieldCommand */
    public GetIndexFieldCommand(UserVisitPK userVisitPK, GetIndexFieldForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var indexControl = (IndexControl)Session.getModelController(IndexControl.class);
        GetIndexFieldResult result = IndexResultFactory.getGetIndexFieldResult();
        String indexTypeName = form.getIndexTypeName();
        IndexType indexType = indexControl.getIndexTypeByName(indexTypeName);
        
        if(indexType != null) {
            String indexFieldName = form.getIndexFieldName();
            IndexField indexField = indexControl.getIndexFieldByName(indexType, indexFieldName);
            
            if(indexField != null) {
                result.setIndexField(indexControl.getIndexFieldTransfer(getUserVisit(), indexField));
                
                sendEventUsingNames(indexField.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownIndexFieldName.name(), indexTypeName, indexFieldName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownIndexTypeName.name(), indexTypeName);
        }
        
        return result;
    }
    
}
