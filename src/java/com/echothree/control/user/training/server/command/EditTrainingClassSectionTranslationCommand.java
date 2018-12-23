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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.edit.TrainingClassSectionTranslationEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditTrainingClassSectionTranslationForm;
import com.echothree.control.user.training.common.result.EditTrainingClassSectionTranslationResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.TrainingClassSectionTranslationSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.training.server.entity.TrainingClassSectionTranslation;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditTrainingClassSectionTranslationCommand
        extends BaseAbstractEditCommand<TrainingClassSectionTranslationSpec, TrainingClassSectionTranslationEdit, EditTrainingClassSectionTranslationResult, TrainingClassSectionTranslation, TrainingClassSection> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassSection.name(), SecurityRoles.Translation.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null),
                new FieldDefinition("IntroductionMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Introduction", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditTrainingClassSectionTranslationCommand */
    public EditTrainingClassSectionTranslationCommand(UserVisitPK userVisitPK, EditTrainingClassSectionTranslationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTrainingClassSectionTranslationResult getResult() {
        return TrainingResultFactory.getEditTrainingClassSectionTranslationResult();
    }

    @Override
    public TrainingClassSectionTranslationEdit getEdit() {
        return TrainingEditFactory.getTrainingClassSectionTranslationEdit();
    }

    TrainingClass trainingClass;
    
    @Override
    public TrainingClassSectionTranslation getEntity(EditTrainingClassSectionTranslationResult result) {
        TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        TrainingClassSectionTranslation trainingClassSectionTranslation = null;
        String trainingClassName = spec.getTrainingClassName();
        
        trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            String trainingClassSectionName = spec.getTrainingClassSectionName();
            TrainingClassSection trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

            if(trainingClassSection != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        trainingClassSectionTranslation = trainingControl.getTrainingClassSectionTranslation(trainingClassSection, language);
                    } else { // EditMode.UPDATE
                        trainingClassSectionTranslation = trainingControl.getTrainingClassSectionTranslationForUpdate(trainingClassSection, language);
                    }

                    if(trainingClassSectionTranslation == null) {
                        addExecutionError(ExecutionErrors.UnknownTrainingClassSectionTranslation.name(), trainingClassName, trainingClassSectionName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return trainingClassSectionTranslation;
    }

    @Override
    public TrainingClassSection getLockEntity(TrainingClassSectionTranslation trainingClassSectionTranslation) {
        return trainingClassSectionTranslation.getTrainingClassSection();
    }

    @Override
    public void fillInResult(EditTrainingClassSectionTranslationResult result, TrainingClassSectionTranslation trainingClassSectionTranslation) {
        TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);

        result.setTrainingClassSectionTranslation(trainingControl.getTrainingClassSectionTranslationTransfer(getUserVisit(), trainingClassSectionTranslation));
    }

    MimeType overviewMimeType;
    MimeType introductionMimeType;
    
    @Override
    public void doLock(TrainingClassSectionTranslationEdit edit, TrainingClassSectionTranslation trainingClassSectionTranslation) {
        overviewMimeType = trainingClassSectionTranslation.getOverviewMimeType();
        introductionMimeType = trainingClassSectionTranslation.getIntroductionMimeType();
        
        edit.setDescription(trainingClassSectionTranslation.getDescription());
        edit.setOverviewMimeTypeName(overviewMimeType == null? null: overviewMimeType.getLastDetail().getMimeTypeName());
        edit.setOverview(trainingClassSectionTranslation.getOverview());
        edit.setIntroductionMimeTypeName(introductionMimeType == null? null: introductionMimeType.getLastDetail().getMimeTypeName());
        edit.setIntroduction(trainingClassSectionTranslation.getIntroduction());
    }

    @Override
    protected void canUpdate(TrainingClassSectionTranslation trainingClassSectionTranslation) {
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
    
    @Override
    public void doUpdate(TrainingClassSectionTranslation trainingClassSectionTranslation) {
        TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        TrainingClassSectionTranslationValue trainingClassSectionTranslationValue = trainingControl.getTrainingClassSectionTranslationValue(trainingClassSectionTranslation);
        
        trainingClassSectionTranslationValue.setDescription(edit.getDescription());
        trainingClassSectionTranslationValue.setOverviewMimeTypePK(overviewMimeType == null? null: overviewMimeType.getPrimaryKey());
        trainingClassSectionTranslationValue.setOverview(edit.getOverview());
        trainingClassSectionTranslationValue.setIntroductionMimeTypePK(introductionMimeType == null? null: introductionMimeType.getPrimaryKey());
        trainingClassSectionTranslationValue.setIntroduction(edit.getIntroduction());
        
        trainingControl.updateTrainingClassSectionTranslationFromValue(trainingClassSectionTranslationValue, getPartyPK());
    }

}
