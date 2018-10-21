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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.remote.edit.TrainingClassPageEdit;
import com.echothree.control.user.training.remote.edit.TrainingEditFactory;
import com.echothree.control.user.training.remote.form.EditTrainingClassPageForm;
import com.echothree.control.user.training.remote.result.EditTrainingClassPageResult;
import com.echothree.control.user.training.remote.result.TrainingResultFactory;
import com.echothree.control.user.training.remote.spec.TrainingClassPageSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassPage;
import com.echothree.model.data.training.server.entity.TrainingClassPageDetail;
import com.echothree.model.data.training.server.entity.TrainingClassPageTranslation;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.training.server.value.TrainingClassPageDetailValue;
import com.echothree.model.data.training.server.value.TrainingClassPageTranslationValue;
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

public class EditTrainingClassPageCommand
        extends BaseAbstractEditCommand<TrainingClassPageSpec, TrainingClassPageEdit, EditTrainingClassPageResult, TrainingClassPage, TrainingClassPage> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassPage.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassPageName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PercentageToPass", FieldType.FRACTIONAL_PERCENT, false, null, null),
                new FieldDefinition("QuestionCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("PageMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Page", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditTrainingClassPageCommand */
    public EditTrainingClassPageCommand(UserVisitPK userVisitPK, EditTrainingClassPageForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTrainingClassPageResult getResult() {
        return TrainingResultFactory.getEditTrainingClassPageResult();
    }

    @Override
    public TrainingClassPageEdit getEdit() {
        return TrainingEditFactory.getTrainingClassPageEdit();
    }

    TrainingClassSection trainingClassSection;
    
    @Override
    public TrainingClassPage getEntity(EditTrainingClassPageResult result) {
        TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        TrainingClassPage trainingClassPage = null;
        String trainingClassName = spec.getTrainingClassName();
        TrainingClass trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            String trainingClassSectionName = spec.getTrainingClassSectionName();
            
            trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

            if(trainingClassSection != null) {
                String trainingClassPageName = spec.getTrainingClassPageName();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    trainingClassPage = trainingControl.getTrainingClassPageByName(trainingClassSection, trainingClassPageName);
                } else { // EditMode.UPDATE
                    trainingClassPage = trainingControl.getTrainingClassPageByNameForUpdate(trainingClassSection, trainingClassPageName);
                }

                if(trainingClassPage != null) {
                    result.setTrainingClassPage(trainingControl.getTrainingClassPageTransfer(getUserVisit(), trainingClassPage));
                } else {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassPageName.name(), trainingClassName, trainingClassSectionName, trainingClassPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return trainingClassPage;
    }

    @Override
    public TrainingClassPage getLockEntity(TrainingClassPage trainingClassPage) {
        return trainingClassPage;
    }

    @Override
    public void fillInResult(EditTrainingClassPageResult result, TrainingClassPage trainingClassPage) {
        TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);

        result.setTrainingClassPage(trainingControl.getTrainingClassPageTransfer(getUserVisit(), trainingClassPage));
    }

    MimeType pageMimeType;
    MimeType introductionMimeType;
    
    @Override
    public void doLock(TrainingClassPageEdit edit, TrainingClassPage trainingClassPage) {
        TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        TrainingClassPageTranslation trainingClassPageTranslation = trainingControl.getTrainingClassPageTranslation(trainingClassPage, getPreferredLanguage());
        TrainingClassPageDetail trainingClassPageDetail = trainingClassPage.getLastDetail();

        edit.setTrainingClassPageName(trainingClassPageDetail.getTrainingClassPageName());
        edit.setSortOrder(trainingClassPageDetail.getSortOrder().toString());

        if(trainingClassPageTranslation != null) {
            pageMimeType = trainingClassPageTranslation.getPageMimeType();

            edit.setDescription(trainingClassPageTranslation.getDescription());
            edit.setPageMimeTypeName(pageMimeType == null? null: pageMimeType.getLastDetail().getMimeTypeName());
            edit.setPage(trainingClassPageTranslation.getPage());
        }
    }

    @Override
    public void canUpdate(TrainingClassPage trainingClassPage) {
        TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        String trainingClassPageName = edit.getTrainingClassPageName();
        TrainingClassPage duplicateTrainingClassPage = trainingControl.getTrainingClassPageByName(trainingClassSection, trainingClassPageName);

        if(duplicateTrainingClassPage != null && !trainingClassPage.equals(duplicateTrainingClassPage)) {
            addExecutionError(ExecutionErrors.DuplicateTrainingClassPageName.name(), trainingClassPageName);
        } else {
            MimeTypeLogic mimeTypeLogic = MimeTypeLogic.getInstance();
            String pageMimeTypeName = edit.getPageMimeTypeName();
            String page = edit.getPage();

            pageMimeType = mimeTypeLogic.checkMimeType(this, pageMimeTypeName, page, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredPageMimeTypeName.name(), ExecutionErrors.MissingRequiredPage.name(),
                    ExecutionErrors.UnknownPageMimeTypeName.name(), ExecutionErrors.UnknownPageMimeTypeUsage.name());
        }
    }
    
    @Override
    public void doUpdate(TrainingClassPage trainingClassPage) {
        TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        PartyPK partyPK = getPartyPK();
        TrainingClassPageDetailValue trainingClassPageDetailValue = trainingControl.getTrainingClassPageDetailValueForUpdate(trainingClassPage);
        TrainingClassPageTranslation trainingClassPageTranslation = trainingControl.getTrainingClassPageTranslationForUpdate(trainingClassPage, getPreferredLanguage());
        String description = edit.getDescription();
        String page = edit.getPage();

        trainingClassPageDetailValue.setTrainingClassPageName(edit.getTrainingClassPageName());
        trainingClassPageDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        trainingControl.updateTrainingClassPageFromValue(trainingClassPageDetailValue, partyPK);

        if(trainingClassPageTranslation == null && (description != null || page != null)) {
            trainingControl.createTrainingClassPageTranslation(trainingClassPage, getPreferredLanguage(), description, pageMimeType, page,
                    partyPK);
        } else if(trainingClassPageTranslation != null && (description == null && page == null)) {
            trainingControl.deleteTrainingClassPageTranslation(trainingClassPageTranslation, partyPK);
        } else if(trainingClassPageTranslation != null && (description != null || page != null)) {
            TrainingClassPageTranslationValue trainingClassPageTranslationValue = trainingControl.getTrainingClassPageTranslationValue(trainingClassPageTranslation);

            trainingClassPageTranslationValue.setDescription(description);
            trainingClassPageTranslationValue.setPageMimeTypePK(pageMimeType == null? null: pageMimeType.getPrimaryKey());
            trainingClassPageTranslationValue.setPage(page);
            trainingControl.updateTrainingClassPageTranslationFromValue(trainingClassPageTranslationValue, partyPK);
        }
    }

}
