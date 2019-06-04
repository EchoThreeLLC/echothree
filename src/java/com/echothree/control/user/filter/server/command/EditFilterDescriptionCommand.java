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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.edit.FilterDescriptionEdit;
import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.form.EditFilterDescriptionForm;
import com.echothree.control.user.filter.common.result.EditFilterDescriptionResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterDescriptionSpec;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterDescription;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.value.FilterDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditFilterDescriptionCommand
        extends BaseEditCommand<FilterDescriptionSpec, FilterDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditFilterDescriptionCommand */
    public EditFilterDescriptionCommand(UserVisitPK userVisitPK, EditFilterDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        EditFilterDescriptionResult result = FilterResultFactory.getEditFilterDescriptionResult();
        String filterKindName = spec.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            String filterTypeName = spec.getFilterTypeName();
            FilterType filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            if(filterType != null) {
                String filterName = spec.getFilterName();
                Filter filter = filterControl.getFilterByName(filterType, filterName);
                
                if(filter != null) {
                    var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                    String languageIsoName = spec.getLanguageIsoName();
                    Language language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            FilterDescription filterDescription = filterControl.getFilterDescription(filter, language);
                            
                            if(filterDescription != null) {
                                result.setFilterDescription(filterControl.getFilterDescriptionTransfer(getUserVisit(), filterDescription));
                                
                                if(lockEntity(filter)) {
                                    FilterDescriptionEdit edit = FilterEditFactory.getFilterDescriptionEdit();
                                    
                                    result.setEdit(edit);
                                    edit.setDescription(filterDescription.getDescription());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }
                                
                                result.setEntityLock(getEntityLockTransfer(filter));
                            } else {
                                addExecutionError(ExecutionErrors.UnknownFilterDescription.name());
                            }
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            FilterDescriptionValue filterDescriptionValue = filterControl.getFilterDescriptionValueForUpdate(filter, language);
                            
                            if(filterDescriptionValue != null) {
                                if(lockEntityForUpdate(filter)) {
                                    try {
                                        String description = edit.getDescription();
                                        
                                        filterDescriptionValue.setDescription(description);
                                        
                                        filterControl.updateFilterDescriptionFromValue(filterDescriptionValue, getPartyPK());
                                    } finally {
                                        unlockEntity(filter);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownFilterDescription.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFilterName.name(), filterName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterTypeName.name(), filterTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }
        
        return result;
    }
    
}
