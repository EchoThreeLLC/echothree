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

import com.echothree.control.user.training.common.form.DeleteTrainingClassAnswerTranslationForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassAnswer;
import com.echothree.model.data.training.server.entity.TrainingClassAnswerTranslation;
import com.echothree.model.data.training.server.entity.TrainingClassQuestion;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
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

public class DeleteTrainingClassAnswerTranslationCommand
        extends BaseSimpleCommand<DeleteTrainingClassAnswerTranslationForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassAnswer.name(), SecurityRoles.Translation.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassAnswerName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteTrainingClassAnswerTranslationCommand */
    public DeleteTrainingClassAnswerTranslationCommand(UserVisitPK userVisitPK, DeleteTrainingClassAnswerTranslationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        String trainingClassName = form.getTrainingClassName();
        TrainingClass trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            String trainingClassSectionName = form.getTrainingClassSectionName();
            TrainingClassSection trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

            if(trainingClassSection != null) {
                String trainingClassQuestionName = form.getTrainingClassQuestionName();
                TrainingClassQuestion trainingClassQuestion = trainingControl.getTrainingClassQuestionByName(trainingClassSection, trainingClassQuestionName);

                if(trainingClassQuestion != null) {
                    String trainingClassAnswerName = form.getTrainingClassAnswerName();
                    TrainingClassAnswer trainingClassAnswer = trainingControl.getTrainingClassAnswerByName(trainingClassQuestion, trainingClassAnswerName);

                    if(trainingClassAnswer != null) {
                        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                        String languageIsoName = form.getLanguageIsoName();
                        Language language = partyControl.getLanguageByIsoName(languageIsoName);

                        if(language != null) {
                            TrainingClassAnswerTranslation trainingClassAnswerTranslation = trainingControl.getTrainingClassAnswerTranslationForUpdate(trainingClassAnswer, language);

                            if(trainingClassAnswerTranslation != null) {
                                trainingControl.deleteTrainingClassAnswerTranslation(trainingClassAnswerTranslation, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.UnknownTrainingClassAnswerTranslation.name(), trainingClassName, trainingClassSectionName,
                                        trainingClassQuestionName, trainingClassAnswerName, languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTrainingClassAnswerName.name(), trainingClassName, trainingClassSectionName,
                                trainingClassQuestionName, trainingClassAnswerName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassQuestionName.name(), trainingClassName, trainingClassSectionName,
                            trainingClassQuestionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }
        
        return null;
    }
    
}
