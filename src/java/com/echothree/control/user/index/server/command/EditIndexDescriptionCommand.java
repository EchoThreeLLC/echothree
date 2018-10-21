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

package com.echothree.control.user.index.server.command;

import com.echothree.control.user.index.remote.edit.IndexDescriptionEdit;
import com.echothree.control.user.index.remote.edit.IndexEditFactory;
import com.echothree.control.user.index.remote.form.EditIndexDescriptionForm;
import com.echothree.control.user.index.remote.result.EditIndexDescriptionResult;
import com.echothree.control.user.index.remote.result.IndexResultFactory;
import com.echothree.control.user.index.remote.spec.IndexDescriptionSpec;
import com.echothree.model.control.index.server.IndexControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexDescription;
import com.echothree.model.data.index.server.value.IndexDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditIndexDescriptionCommand
        extends BaseAbstractEditCommand<IndexDescriptionSpec, IndexDescriptionEdit, EditIndexDescriptionResult, IndexDescription, Index> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Index.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IndexName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditIndexDescriptionCommand */
    public EditIndexDescriptionCommand(UserVisitPK userVisitPK, EditIndexDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditIndexDescriptionResult getResult() {
        return IndexResultFactory.getEditIndexDescriptionResult();
    }

    @Override
    public IndexDescriptionEdit getEdit() {
        return IndexEditFactory.getIndexDescriptionEdit();
    }

    @Override
    public IndexDescription getEntity(EditIndexDescriptionResult result) {
        IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);
        IndexDescription indexDescription = null;
        String indexName = spec.getIndexName();
        Index index = indexControl.getIndexByName(indexName);

        if(index != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    indexDescription = indexControl.getIndexDescription(index, language);
                } else { // EditMode.UPDATE
                    indexDescription = indexControl.getIndexDescriptionForUpdate(index, language);
                }

                if(indexDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownIndexDescription.name(), indexName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownIndexName.name(), indexName);
        }

        return indexDescription;
    }

    @Override
    public Index getLockEntity(IndexDescription indexDescription) {
        return indexDescription.getIndex();
    }

    @Override
    public void fillInResult(EditIndexDescriptionResult result, IndexDescription indexDescription) {
        IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);

        result.setIndexDescription(indexControl.getIndexDescriptionTransfer(getUserVisit(), indexDescription));
    }

    @Override
    public void doLock(IndexDescriptionEdit edit, IndexDescription indexDescription) {
        edit.setDescription(indexDescription.getDescription());
    }

    @Override
    public void doUpdate(IndexDescription indexDescription) {
        IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);
        IndexDescriptionValue indexDescriptionValue = indexControl.getIndexDescriptionValue(indexDescription);
        indexDescriptionValue.setDescription(edit.getDescription());

        indexControl.updateIndexDescriptionFromValue(indexDescriptionValue, getPartyPK());
    }
    
}
