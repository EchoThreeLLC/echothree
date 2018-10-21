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

import com.echothree.control.user.training.remote.form.CreateTrainingClassForm;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.workeffort.common.workeffort.TrainingConstants;
import com.echothree.model.control.workeffort.server.WorkEffortControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateTrainingClassCommand
        extends BaseSimpleCommand<CreateTrainingClassForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClass.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EstimatedReadingTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("EstimatedReadingTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReadingTimeAllowed", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ReadingTimeAllowedUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EstimatedTestingTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("EstimatedTestingTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("TestingTimeAllowed", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("TestingTimeAllowedUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RequiredCompletionTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("RequiredCompletionTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkEffortScopeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultPercentageToPass", FieldType.FRACTIONAL_PERCENT, false, null, null),
                new FieldDefinition("OverallQuestionCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("TestingValidityTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("TestingValidityTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ExpiredRetentionTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ExpiredRetentionTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AlwaysReassignOnExpiration", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null),
                new FieldDefinition("IntroductionMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Introduction", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateTrainingClassCommand */
    public CreateTrainingClassCommand(UserVisitPK userVisitPK, CreateTrainingClassForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        String trainingClassName = form.getTrainingClassName();
        TrainingClass trainingClass = trainingControl.getTrainingClassByName(trainingClassName);
        
        if(trainingClass == null) {
            MimeTypeLogic mimeTypeLogic = MimeTypeLogic.getInstance();
            String overview = form.getOverview();
            MimeType overviewMimeType = mimeTypeLogic.checkMimeType(this, form.getOverviewMimeTypeName(), overview, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                    ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());

            if(!hasExecutionErrors()) {
                String introduction = form.getIntroduction();
                MimeType introductionMimeType = mimeTypeLogic.checkMimeType(this, form.getIntroductionMimeTypeName(), introduction, MimeTypeUsageTypes.TEXT.name(),
                        ExecutionErrors.MissingRequiredIntroductionMimeTypeName.name(), ExecutionErrors.MissingRequiredIntroduction.name(),
                        ExecutionErrors.UnknownIntroductionMimeTypeName.name(), ExecutionErrors.UnknownIntroductionMimeTypeUsage.name());

                if(!hasExecutionErrors()) {
                    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
                    Long estimatedReadingTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                            form.getEstimatedReadingTime(), form.getEstimatedReadingTimeUnitOfMeasureTypeName(),
                            null, ExecutionErrors.MissingRequiredEstimatedReadingTime.name(), null, ExecutionErrors.MissingRequiredEstimatedReadingTimeUnitOfMeasureTypeName.name(),
                            null, ExecutionErrors.UnknownEstimatedReadingTimeUnitOfMeasureTypeName.name());

                    if(!hasExecutionErrors()) {
                        Long readingTimeAllowed = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                form.getReadingTimeAllowed(), form.getReadingTimeAllowedUnitOfMeasureTypeName(),
                                null, ExecutionErrors.MissingRequiredReadingTimeAllowed.name(), null, ExecutionErrors.MissingRequiredReadingTimeAllowedUnitOfMeasureTypeName.name(),
                                null, ExecutionErrors.UnknownReadingTimeAllowedUnitOfMeasureTypeName.name());

                        if(!hasExecutionErrors()) {
                            Long estimatedTestingTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                    form.getEstimatedTestingTime(), form.getEstimatedTestingTimeUnitOfMeasureTypeName(),
                                    null, ExecutionErrors.MissingRequiredEstimatedTestingTime.name(), null, ExecutionErrors.MissingRequiredEstimatedTestingTimeUnitOfMeasureTypeName.name(),
                                    null, ExecutionErrors.UnknownEstimatedTestingTimeUnitOfMeasureTypeName.name());

                            if(!hasExecutionErrors()) {
                                Long testingTimeAllowed = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                        form.getTestingTimeAllowed(), form.getTestingTimeAllowedUnitOfMeasureTypeName(),
                                        null, ExecutionErrors.MissingRequiredTestingTimeAllowed.name(), null, ExecutionErrors.MissingRequiredTestingTimeAllowedUnitOfMeasureTypeName.name(),
                                        null, ExecutionErrors.UnknownTestingTimeAllowedUnitOfMeasureTypeName.name());

                                if(!hasExecutionErrors()) {
                                    Long requiredCompletionTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                            form.getRequiredCompletionTime(), form.getRequiredCompletionTimeUnitOfMeasureTypeName(),
                                            null, ExecutionErrors.MissingRequiredRequiredCompletionTime.name(), null, ExecutionErrors.MissingRequiredRequiredCompletionTimeUnitOfMeasureTypeName.name(),
                                            null, ExecutionErrors.UnknownRequiredCompletionTimeUnitOfMeasureTypeName.name());

                                    if(!hasExecutionErrors()) {
                                        String workEffortScopeName = form.getWorkEffortScopeName();
                                        WorkEffortScope workEffortScope = null;

                                        if(workEffortScopeName != null) {
                                            WorkEffortControl workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);
                                            WorkEffortType workEffortType = workEffortControl.getWorkEffortTypeByName(TrainingConstants.WorkEffortType_TRAINING);

                                            if(workEffortType != null) {
                                                workEffortScope = workEffortControl.getWorkEffortScopeByName(workEffortType, workEffortScopeName);

                                                if(workEffortScope == null) {
                                                    addExecutionError(ExecutionErrors.UnknownWorkEffortScopeName.name(), TrainingConstants.WorkEffortType_TRAINING,
                                                            workEffortScopeName);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), TrainingConstants.WorkEffortType_TRAINING);
                                            }
                                        }

                                        if(!hasExecutionErrors()) {
                                            Long testingValidityTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                                    form.getTestingValidityTime(), form.getTestingValidityTimeUnitOfMeasureTypeName(),
                                                    null, ExecutionErrors.MissingRequiredTestingValidityTime.name(), null, ExecutionErrors.MissingRequiredTestingValidityTimeUnitOfMeasureTypeName.name(),
                                                    null, ExecutionErrors.UnknownTestingValidityTimeUnitOfMeasureTypeName.name());

                                            if(!hasExecutionErrors()) {
                                                Long expiredRetentionTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                                        form.getExpiredRetentionTime(), form.getExpiredRetentionTimeUnitOfMeasureTypeName(),
                                                        null, ExecutionErrors.MissingRequiredExpiredRetentionTime.name(), null, ExecutionErrors.MissingRequiredExpiredRetentionTimeUnitOfMeasureTypeName.name(),
                                                        null, ExecutionErrors.UnknownExpiredRetentionTimeUnitOfMeasureTypeName.name());

                                                if(!hasExecutionErrors()) {
                                                    PartyPK createdBy = getPartyPK();
                                                    String strDefaultPercentageToPass = form.getDefaultPercentageToPass();
                                                    Integer defaultPercentageToPass = strDefaultPercentageToPass == null ? null : Integer.valueOf(strDefaultPercentageToPass);
                                                    String strOverallQuestionCount = form.getOverallQuestionCount();
                                                    Integer overallQuestionCount = strOverallQuestionCount == null ? null : Integer.valueOf(strOverallQuestionCount);
                                                    Boolean alwaysReassignOnExpiration = Boolean.valueOf(form.getAlwaysReassignOnExpiration());
                                                    Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                                                    Integer sortOrder = Integer.valueOf(form.getSortOrder());
                                                    String description = form.getDescription();

                                                    trainingClass = trainingControl.createTrainingClass(trainingClassName, estimatedReadingTime,
                                                            readingTimeAllowed, estimatedTestingTime, testingTimeAllowed, requiredCompletionTime,
                                                            workEffortScope, defaultPercentageToPass, overallQuestionCount, testingValidityTime,
                                                            expiredRetentionTime, alwaysReassignOnExpiration, isDefault, sortOrder, createdBy);

                                                    if(description != null || overview != null || introduction != null) {
                                                        trainingControl.createTrainingClassTranslation(trainingClass, getPreferredLanguage(), description,
                                                                overviewMimeType, overview, introductionMimeType, introduction, createdBy);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateTrainingClassName.name(), trainingClassName);
        }
        
        return null;
    }
    
}
