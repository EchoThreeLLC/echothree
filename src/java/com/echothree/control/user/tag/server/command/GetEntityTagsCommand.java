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

package com.echothree.control.user.tag.server.command;

import com.echothree.control.user.tag.common.form.GetEntityTagsForm;
import com.echothree.control.user.tag.common.result.GetEntityTagsResult;
import com.echothree.control.user.tag.common.result.TagResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
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

public class GetEntityTagsCommand
        extends BaseSimpleCommand<GetEntityTagsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityTag.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("TagName", FieldType.TAG, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityTagsCommand */
    public GetEntityTagsCommand(UserVisitPK userVisitPK, GetEntityTagsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetEntityTagsResult result = TagResultFactory.getGetEntityTagsResult();
        String tagScopeName = form.getTagScopeName();
        String tagName = form.getTagName();
        String entityRef = form.getEntityRef();
        var parameterCount = (tagScopeName == null && tagName == null ? 0 : 1) + (entityRef == null ? 0 : 1);
        
        if(parameterCount == 1) {
            var tagControl = Session.getModelController(TagControl.class);
            UserVisit userVisit = getUserVisit();
            
            if(entityRef != null) {
                var coreControl = getCoreControl();
                EntityInstance taggedEntityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);
                
                if(taggedEntityInstance != null) {
                    result.setTaggedEntityInstance(coreControl.getEntityInstanceTransfer(userVisit, taggedEntityInstance, false, false, false, false, false));
                    result.setEntityTags(tagControl.getEntityTagTransfersByTaggedEntityInstance(userVisit, taggedEntityInstance));
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
                }
            } else {
                TagScope tagScope = tagControl.getTagScopeByName(tagScopeName);
                
                if(tagScope != null) {
                    Tag tag = tagControl.getTagByName(tagScope, tagName);
                    
                    if(tag != null) {
                        result.setTag(tagControl.getTagTransfer(userVisit, tag));
                        result.setEntityTags(tagControl.getEntityTagTransfersByTag(userVisit, tag));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTagName.name(), tagScopeName, tagName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTagScopeName.name(), tagScopeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
