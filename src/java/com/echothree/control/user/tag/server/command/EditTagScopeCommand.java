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

package com.echothree.control.user.tag.server.command;

import com.echothree.control.user.tag.common.edit.TagEditFactory;
import com.echothree.control.user.tag.common.edit.TagScopeEdit;
import com.echothree.control.user.tag.common.form.EditTagScopeForm;
import com.echothree.control.user.tag.common.result.EditTagScopeResult;
import com.echothree.control.user.tag.common.result.TagResultFactory;
import com.echothree.control.user.tag.common.spec.TagScopeSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.TagControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.entity.TagScopeDescription;
import com.echothree.model.data.tag.server.entity.TagScopeDetail;
import com.echothree.model.data.tag.server.value.TagScopeDescriptionValue;
import com.echothree.model.data.tag.server.value.TagScopeDetailValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditTagScopeCommand
        extends BaseAbstractEditCommand<TagScopeSpec, TagScopeEdit, EditTagScopeResult, TagScope, TagScope> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TagScope.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditTagScopeCommand */
    public EditTagScopeCommand(UserVisitPK userVisitPK, EditTagScopeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTagScopeResult getResult() {
        return TagResultFactory.getEditTagScopeResult();
    }

    @Override
    public TagScopeEdit getEdit() {
        return TagEditFactory.getTagScopeEdit();
    }

    @Override
    public TagScope getEntity(EditTagScopeResult result) {
        TagControl tagControl = (TagControl)Session.getModelController(TagControl.class);
        TagScope tagScope = null;
        String tagScopeName = spec.getTagScopeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            tagScope = tagControl.getTagScopeByName(tagScopeName);
        } else { // EditMode.UPDATE
            tagScope = tagControl.getTagScopeByNameForUpdate(tagScopeName);
        }

        if(tagScope != null) {
            result.setTagScope(tagControl.getTagScopeTransfer(getUserVisit(), tagScope));
        } else {
            addExecutionError(ExecutionErrors.UnknownTagScopeName.name(), tagScopeName);
        }

        return tagScope;
    }

    @Override
    public TagScope getLockEntity(TagScope tagScope) {
        return tagScope;
    }

    @Override
    public void fillInResult(EditTagScopeResult result, TagScope tagScope) {
        TagControl tagControl = (TagControl)Session.getModelController(TagControl.class);

        result.setTagScope(tagControl.getTagScopeTransfer(getUserVisit(), tagScope));
    }

    @Override
    public void doLock(TagScopeEdit edit, TagScope tagScope) {
        TagControl tagControl = (TagControl)Session.getModelController(TagControl.class);
        TagScopeDescription tagScopeDescription = tagControl.getTagScopeDescription(tagScope, getPreferredLanguage());
        TagScopeDetail tagScopeDetail = tagScope.getLastDetail();

        edit.setTagScopeName(tagScopeDetail.getTagScopeName());
        edit.setIsDefault(tagScopeDetail.getIsDefault().toString());
        edit.setSortOrder(tagScopeDetail.getSortOrder().toString());

        if(tagScopeDescription != null) {
            edit.setDescription(tagScopeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(TagScope tagScope) {
        TagControl tagControl = (TagControl)Session.getModelController(TagControl.class);
        String tagScopeName = edit.getTagScopeName();
        TagScope duplicateTagScope = tagControl.getTagScopeByName(tagScopeName);

        if(duplicateTagScope != null && !tagScope.equals(duplicateTagScope)) {
            addExecutionError(ExecutionErrors.DuplicateTagScopeName.name(), tagScopeName);
        }
    }

    @Override
    public void doUpdate(TagScope tagScope) {
        TagControl tagControl = (TagControl)Session.getModelController(TagControl.class);
        PartyPK partyPK = getPartyPK();
        TagScopeDetailValue tagScopeDetailValue = tagControl.getTagScopeDetailValueForUpdate(tagScope);
        TagScopeDescription tagScopeDescription = tagControl.getTagScopeDescriptionForUpdate(tagScope, getPreferredLanguage());
        String description = edit.getDescription();

        tagScopeDetailValue.setTagScopeName(edit.getTagScopeName());
        tagScopeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        tagScopeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        tagControl.updateTagScopeFromValue(tagScopeDetailValue, partyPK);

        if(tagScopeDescription == null && description != null) {
            tagControl.createTagScopeDescription(tagScope, getPreferredLanguage(), description, partyPK);
        } else if(tagScopeDescription != null && description == null) {
            tagControl.deleteTagScopeDescription(tagScopeDescription, partyPK);
        } else if(tagScopeDescription != null && description != null) {
            TagScopeDescriptionValue tagScopeDescriptionValue = tagControl.getTagScopeDescriptionValue(tagScopeDescription);

            tagScopeDescriptionValue.setDescription(description);
            tagControl.updateTagScopeDescriptionFromValue(tagScopeDescriptionValue, partyPK);
        }
    }

}
