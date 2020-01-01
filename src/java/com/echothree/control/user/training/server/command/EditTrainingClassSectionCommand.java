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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.edit.TrainingClassSectionEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditTrainingClassSectionForm;
import com.echothree.control.user.training.common.result.EditTrainingClassSectionResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.TrainingClassSectionSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.training.server.entity.TrainingClassSectionDetail;
import com.echothree.model.data.training.server.entity.TrainingClassSectionTranslation;
import com.echothree.model.data.training.server.value.TrainingClassSectionDetailValue;
import com.echothree.model.data.training.server.value.TrainingClassSectionTranslationValue;
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
import com.echothree.util.server.string.PercentUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditTrainingClassSectionCommand
        extends BaseAbstractEditCommand<TrainingClassSectionSpec, TrainingClassSectionEdit, EditTrainingClassSectionResult, TrainingClassSection, TrainingClassSection> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassSection.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PercentageToPass", FieldType.FRACTIONAL_PERCENT, false, null, null),
                new FieldDefinition("QuestionCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null),
                new FieldDefinition("IntroductionMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Introduction", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditTrainingClassSectionCommand */
    public EditTrainingClassSectionCommand(UserVisitPK userVisitPK, EditTrainingClassSectionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTrainingClassSectionResult getResult() {
        return TrainingResultFactory.getEditTrainingClassSectionResult();
    }

    @Override
    public TrainingClassSectionEdit getEdit() {
        return TrainingEditFactory.getTrainingClassSectionEdit();
    }

    TrainingClass trainingClass;
    
    @Override
    public TrainingClassSection getEntity(EditTrainingClassSectionResult result) {
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        TrainingClassSection trainingClassSection = null;
        String trainingClassName = spec.getTrainingClassName();

        trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            String trainingClassSectionName = spec.getTrainingClassSectionName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);
            } else { // EditMode.UPDATE
                trainingClassSection = trainingControl.getTrainingClassSectionByNameForUpdate(trainingClass, trainingClassSectionName);
            }

            if(trainingClassSection != null) {
                result.setTrainingClassSection(trainingControl.getTrainingClassSectionTransfer(getUserVisit(), trainingClassSection));
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return trainingClassSection;
    }

    @Override
    public TrainingClassSection getLockEntity(TrainingClassSection trainingClassSection) {
        return trainingClassSection;
    }

    @Override
    public void fillInResult(EditTrainingClassSectionResult result, TrainingClassSection trainingClassSection) {
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);

        result.setTrainingClassSection(trainingControl.getTrainingClassSectionTransfer(getUserVisit(), trainingClassSection));
    }

    MimeType overviewMimeType;
    MimeType introductionMimeType;
    
    @Override
    public void doLock(TrainingClassSectionEdit edit, TrainingClassSection trainingClassSection) {
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        TrainingClassSectionTranslation trainingClassSectionTranslation = trainingControl.getTrainingClassSectionTranslation(trainingClassSection, getPreferredLanguage());
        TrainingClassSectionDetail trainingClassSectionDetail = trainingClassSection.getLastDetail();
        Integer questionCount = trainingClassSectionDetail.getQuestionCount();

        edit.setTrainingClassSectionName(trainingClassSectionDetail.getTrainingClassSectionName());
        edit.setPercentageToPass(PercentUtils.getInstance().formatFractionalPercent(trainingClassSectionDetail.getPercentageToPass()));
        edit.setQuestionCount(questionCount == null? null: questionCount.toString());
        edit.setSortOrder(trainingClassSectionDetail.getSortOrder().toString());

        if(trainingClassSectionTranslation != null) {
            overviewMimeType = trainingClassSectionTranslation.getOverviewMimeType();
            introductionMimeType = trainingClassSectionTranslation.getIntroductionMimeType();

            edit.setDescription(trainingClassSectionTranslation.getDescription());
            edit.setOverviewMimeTypeName(overviewMimeType == null? null: overviewMimeType.getLastDetail().getMimeTypeName());
            edit.setOverview(trainingClassSectionTranslation.getOverview());
            edit.setIntroductionMimeTypeName(introductionMimeType == null? null: introductionMimeType.getLastDetail().getMimeTypeName());
            edit.setIntroduction(trainingClassSectionTranslation.getIntroduction());
        }
    }

    @Override
    public void canUpdate(TrainingClassSection trainingClassSection) {
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        String trainingClassSectionName = edit.getTrainingClassSectionName();
        TrainingClassSection duplicateTrainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

        if(duplicateTrainingClassSection != null && !trainingClassSection.equals(duplicateTrainingClassSection)) {
            addExecutionError(ExecutionErrors.DuplicateTrainingClassSectionName.name(), trainingClassSectionName);
        } else {
            MimeTypeLogic mimeTypeLogic = MimeTypeLogic.getInstance();
            String overviewMimeTypeName = edit.getOverviewMimeTypeName();
            String overview = edit.getOverview();

            overviewMimeType = mimeTypeLogic.checkMimeType(this, overviewMimeTypeName, overview, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                    ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());

            if(!hasExecutionErrors()) {
                String introductionMimeTypeName = edit.getIntroductionMimeTypeName();
                String introduction = edit.getIntroduction();

                introductionMimeType = mimeTypeLogic.checkMimeType(this, introductionMimeTypeName, introduction, MimeTypeUsageTypes.TEXT.name(),
                        ExecutionErrors.MissingRequiredIntroductionMimeTypeName.name(), ExecutionErrors.MissingRequiredIntroduction.name(),
                        ExecutionErrors.UnknownIntroductionMimeTypeName.name(), ExecutionErrors.UnknownIntroductionMimeTypeUsage.name());
            }
        }
    }
    
    @Override
    public void doUpdate(TrainingClassSection trainingClassSection) {
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        PartyPK partyPK = getPartyPK();
        TrainingClassSectionDetailValue trainingClassSectionDetailValue = trainingControl.getTrainingClassSectionDetailValueForUpdate(trainingClassSection);
        TrainingClassSectionTranslation trainingClassSectionTranslation = trainingControl.getTrainingClassSectionTranslationForUpdate(trainingClassSection, getPreferredLanguage());
        String percentageToPass = edit.getPercentageToPass();
        String questionCount = edit.getQuestionCount();
        String description = edit.getDescription();
        String overview = edit.getOverview();
        String introduction = edit.getIntroduction();

        trainingClassSectionDetailValue.setTrainingClassSectionName(edit.getTrainingClassSectionName());
        trainingClassSectionDetailValue.setPercentageToPass(percentageToPass == null ? null : Integer.valueOf(percentageToPass));
        trainingClassSectionDetailValue.setQuestionCount(questionCount == null ? null : Integer.valueOf(questionCount));
        trainingClassSectionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        trainingControl.updateTrainingClassSectionFromValue(trainingClassSectionDetailValue, partyPK);

        if(trainingClassSectionTranslation == null && (description != null || overview != null || introduction != null)) {
            trainingControl.createTrainingClassSectionTranslation(trainingClassSection, getPreferredLanguage(), description, overviewMimeType, overview,
                    introductionMimeType, introduction, partyPK);
        } else if(trainingClassSectionTranslation != null && (description == null && overview == null && introduction == null)) {
            trainingControl.deleteTrainingClassSectionTranslation(trainingClassSectionTranslation, partyPK);
        } else if(trainingClassSectionTranslation != null && (description != null || overview != null || introduction != null)) {
            TrainingClassSectionTranslationValue trainingClassSectionTranslationValue = trainingControl.getTrainingClassSectionTranslationValue(trainingClassSectionTranslation);

            trainingClassSectionTranslationValue.setDescription(description);
            trainingClassSectionTranslationValue.setOverviewMimeTypePK(overviewMimeType == null? null: overviewMimeType.getPrimaryKey());
            trainingClassSectionTranslationValue.setOverview(overview);
            trainingClassSectionTranslationValue.setIntroductionMimeTypePK(introductionMimeType == null? null: introductionMimeType.getPrimaryKey());
            trainingClassSectionTranslationValue.setIntroduction(introduction);
            trainingControl.updateTrainingClassSectionTranslationFromValue(trainingClassSectionTranslationValue, partyPK);
        }
    }

}
