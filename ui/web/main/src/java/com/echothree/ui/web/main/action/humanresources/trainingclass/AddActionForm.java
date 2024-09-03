// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.humanresources.trainingclass;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetMimeTypeChoicesForm;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypeChoicesForm;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.control.user.workeffort.common.WorkEffortUtil;
import com.echothree.control.user.workeffort.common.form.GetWorkEffortScopeChoicesForm;
import com.echothree.control.user.workeffort.common.result.GetWorkEffortScopeChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.model.control.workeffort.common.workeffort.TrainingConstants;
import com.echothree.model.control.workeffort.common.choice.WorkEffortScopeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="TrainingClassAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean estimatedReadingTimeUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean readingTimeAllowedUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean estimatedTestingTimeUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean testingTimeAllowedUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean requiredCompletionTimeUnitOfMeasureTypeChoices;
    private WorkEffortScopeChoicesBean workEffortScopeChoices;
    private UnitOfMeasureTypeChoicesBean testingValidityTimeUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean expiredRetentionTimeUnitOfMeasureTypeChoices;
    private MimeTypeChoicesBean overviewMimeTypeChoices;
    private MimeTypeChoicesBean introductionMimeTypeChoices;
    
    private String trainingClassName;
    private String estimatedReadingTime;
    private String estimatedReadingTimeUnitOfMeasureTypeChoice;
    private String readingTimeAllowed;
    private String readingTimeAllowedUnitOfMeasureTypeChoice;
    private String estimatedTestingTime;
    private String estimatedTestingTimeUnitOfMeasureTypeChoice;
    private String testingTimeAllowed;
    private String testingTimeAllowedUnitOfMeasureTypeChoice;
    private String requiredCompletionTime;
    private String requiredCompletionTimeUnitOfMeasureTypeChoice;
    private String workEffortScopeChoice;
    private String defaultPercentageToPass;
    private String overallQuestionCount;
    private String testingValidityTime;
    private String testingValidityTimeUnitOfMeasureTypeChoice;
    private String expiredRetentionTime;
    private String expiredRetentionTimeUnitOfMeasureTypeChoice;
    private Boolean alwaysReassignOnExpiration;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    private String overviewMimeTypeChoice;
    private String overview;
    private String introductionMimeTypeChoice;
    private String introduction;
    
    private void setupEstimatedReadingTimeUnitOfMeasureTypeChoices() {
        if(estimatedReadingTimeUnitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(estimatedReadingTimeUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                estimatedReadingTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(estimatedReadingTimeUnitOfMeasureTypeChoice == null) {
                    estimatedReadingTimeUnitOfMeasureTypeChoice = estimatedReadingTimeUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }
    
    private void setupReadingTimeAllowedUnitOfMeasureTypeChoices() {
        if(readingTimeAllowedUnitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(readingTimeAllowedUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                readingTimeAllowedUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(readingTimeAllowedUnitOfMeasureTypeChoice == null) {
                    readingTimeAllowedUnitOfMeasureTypeChoice = readingTimeAllowedUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }
    
    private void setupEstimatedTestingTimeUnitOfMeasureTypeChoices() {
        if(estimatedTestingTimeUnitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(estimatedTestingTimeUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                estimatedTestingTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(estimatedTestingTimeUnitOfMeasureTypeChoice == null) {
                    estimatedTestingTimeUnitOfMeasureTypeChoice = estimatedTestingTimeUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }
    
    private void setupTestingTimeAllowedUnitOfMeasureTypeChoices() {
        if(testingTimeAllowedUnitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

                form.setDefaultUnitOfMeasureTypeChoice(testingTimeAllowedUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                testingTimeAllowedUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

                if(testingTimeAllowedUnitOfMeasureTypeChoice == null) {
                    testingTimeAllowedUnitOfMeasureTypeChoice = testingTimeAllowedUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }

    private void setupRequiredCompletionTimeUnitOfMeasureTypeChoices() {
        if(requiredCompletionTimeUnitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

                form.setDefaultUnitOfMeasureTypeChoice(requiredCompletionTimeUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                requiredCompletionTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

                if(requiredCompletionTimeUnitOfMeasureTypeChoice == null) {
                    requiredCompletionTimeUnitOfMeasureTypeChoice = requiredCompletionTimeUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }

    public void setupWorkEffortScopeChoices() {
        if(workEffortScopeChoices == null) {
            try {
                var form = WorkEffortUtil.getHome().getGetWorkEffortScopeChoicesForm();

                form.setWorkEffortTypeName(TrainingConstants.WorkEffortType_TRAINING);
                form.setDefaultWorkEffortScopeChoice(workEffortScopeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());

                var commandResult = WorkEffortUtil.getHome().getWorkEffortScopeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetWorkEffortScopeChoicesResult)executionResult.getResult();
                workEffortScopeChoices = result.getWorkEffortScopeChoices();

                if(workEffortScopeChoice == null) {
                    workEffortScopeChoice = workEffortScopeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, workEffortScopeChoices remains null, no default
            }
        }
    }

    private void setupTestingValidityTimeUnitOfMeasureTypeChoices() {
        if(testingValidityTimeUnitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(testingValidityTimeUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                testingValidityTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(testingValidityTimeUnitOfMeasureTypeChoice == null) {
                    testingValidityTimeUnitOfMeasureTypeChoice = testingValidityTimeUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }
    
    private void setupExpiredRetentionTimeUnitOfMeasureTypeChoices() {
        if(expiredRetentionTimeUnitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(expiredRetentionTimeUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                expiredRetentionTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(expiredRetentionTimeUnitOfMeasureTypeChoice == null) {
                    expiredRetentionTimeUnitOfMeasureTypeChoice = expiredRetentionTimeUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }
    
     private void setupOverviewMimeTypeChoices() {
        if(overviewMimeTypeChoices == null) {
            try {
                var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();
                
                commandForm.setDefaultMimeTypeChoice(overviewMimeTypeChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

                var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetMimeTypeChoicesResult)executionResult.getResult();
                overviewMimeTypeChoices = result.getMimeTypeChoices();
                
                if(overviewMimeTypeChoice == null) {
                    overviewMimeTypeChoice = overviewMimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, overviewMimeTypeChoices remains null, no default
            }
        }
    }
    
    private void setupIntroductionMimeTypeChoices() {
        if(introductionMimeTypeChoices == null) {
            try {
                var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();
                
                commandForm.setDefaultMimeTypeChoice(introductionMimeTypeChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

                var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetMimeTypeChoicesResult)executionResult.getResult();
                introductionMimeTypeChoices = result.getMimeTypeChoices();
                
                if(introductionMimeTypeChoice == null) {
                    introductionMimeTypeChoice = introductionMimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, introductionMimeTypeChoices remains null, no default
            }
        }
    }
    
   public void setTrainingClassName(String trainingClassName) {
        this.trainingClassName = trainingClassName;
    }
    
    public String getTrainingClassName() {
        return trainingClassName;
    }
    
    public String getEstimatedReadingTime() {
        return estimatedReadingTime;
    }
    
    public void setEstimatedReadingTime(String estimatedReadingTime) {
        this.estimatedReadingTime = estimatedReadingTime;
    }
    
    public List<LabelValueBean> getEstimatedReadingTimeUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupEstimatedReadingTimeUnitOfMeasureTypeChoices();
        if(estimatedReadingTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(estimatedReadingTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getEstimatedReadingTimeUnitOfMeasureTypeChoice() {
        setupEstimatedReadingTimeUnitOfMeasureTypeChoices();
        return estimatedReadingTimeUnitOfMeasureTypeChoice;
    }
    
    public void setEstimatedReadingTimeUnitOfMeasureTypeChoice(String estimatedReadingTimeUnitOfMeasureTypeChoice) {
        this.estimatedReadingTimeUnitOfMeasureTypeChoice = estimatedReadingTimeUnitOfMeasureTypeChoice;
    }
    
    public String getReadingTimeAllowed() {
        return readingTimeAllowed;
    }
    
    public void setReadingTimeAllowed(String readingTimeAllowed) {
        this.readingTimeAllowed = readingTimeAllowed;
    }
    
    public List<LabelValueBean> getReadingTimeAllowedUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupReadingTimeAllowedUnitOfMeasureTypeChoices();
        if(readingTimeAllowedUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(readingTimeAllowedUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getReadingTimeAllowedUnitOfMeasureTypeChoice() {
        setupReadingTimeAllowedUnitOfMeasureTypeChoices();
        return readingTimeAllowedUnitOfMeasureTypeChoice;
    }
    
    public void setReadingTimeAllowedUnitOfMeasureTypeChoice(String readingTimeAllowedUnitOfMeasureTypeChoice) {
        this.readingTimeAllowedUnitOfMeasureTypeChoice = readingTimeAllowedUnitOfMeasureTypeChoice;
    }
    
    public String getEstimatedTestingTime() {
        return estimatedTestingTime;
    }
    
    public void setEstimatedTestingTime(String estimatedTestingTime) {
        this.estimatedTestingTime = estimatedTestingTime;
    }
    
    public List<LabelValueBean> getEstimatedTestingTimeUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupEstimatedTestingTimeUnitOfMeasureTypeChoices();
        if(estimatedTestingTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(estimatedTestingTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getEstimatedTestingTimeUnitOfMeasureTypeChoice() {
        setupEstimatedTestingTimeUnitOfMeasureTypeChoices();
        return estimatedTestingTimeUnitOfMeasureTypeChoice;
    }
    
    public void setEstimatedTestingTimeUnitOfMeasureTypeChoice(String estimatedTestingTimeUnitOfMeasureTypeChoice) {
        this.estimatedTestingTimeUnitOfMeasureTypeChoice = estimatedTestingTimeUnitOfMeasureTypeChoice;
    }
    
    public String getTestingTimeAllowed() {
        return testingTimeAllowed;
    }

    public void setTestingTimeAllowed(String testingTimeAllowed) {
        this.testingTimeAllowed = testingTimeAllowed;
    }

    public List<LabelValueBean> getTestingTimeAllowedUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;

        setupTestingTimeAllowedUnitOfMeasureTypeChoices();
        if(testingTimeAllowedUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(testingTimeAllowedUnitOfMeasureTypeChoices);
        }

        return choices;
    }

    public String getTestingTimeAllowedUnitOfMeasureTypeChoice() {
        setupTestingTimeAllowedUnitOfMeasureTypeChoices();
        return testingTimeAllowedUnitOfMeasureTypeChoice;
    }

    public void setTestingTimeAllowedUnitOfMeasureTypeChoice(String testingTimeAllowedUnitOfMeasureTypeChoice) {
        this.testingTimeAllowedUnitOfMeasureTypeChoice = testingTimeAllowedUnitOfMeasureTypeChoice;
    }
    
    public String getRequiredCompletionTime() {
        return requiredCompletionTime;
    }

    public void setRequiredCompletionTime(String requiredCompletionTime) {
        this.requiredCompletionTime = requiredCompletionTime;
    }

    public List<LabelValueBean> getRequiredCompletionTimeUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;

        setupRequiredCompletionTimeUnitOfMeasureTypeChoices();
        if(requiredCompletionTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(requiredCompletionTimeUnitOfMeasureTypeChoices);
        }

        return choices;
    }

    public String getRequiredCompletionTimeUnitOfMeasureTypeChoice() {
        setupRequiredCompletionTimeUnitOfMeasureTypeChoices();
        return requiredCompletionTimeUnitOfMeasureTypeChoice;
    }

    public void setRequiredCompletionTimeUnitOfMeasureTypeChoice(String requiredCompletionTimeUnitOfMeasureTypeChoice) {
        this.requiredCompletionTimeUnitOfMeasureTypeChoice = requiredCompletionTimeUnitOfMeasureTypeChoice;
    }

    public List<LabelValueBean> getWorkEffortScopeChoices() {
        List<LabelValueBean> choices = null;

        setupWorkEffortScopeChoices();
        if(workEffortScopeChoices != null) {
            choices = convertChoices(workEffortScopeChoices);
        }

        return choices;
    }

    public String getWorkEffortScopeChoice() {
        setupWorkEffortScopeChoices();
        return workEffortScopeChoice;
    }

    public void setWorkEffortScopeChoice(String workEffortScopeChoice) {
        this.workEffortScopeChoice = workEffortScopeChoice;
    }

    public String getDefaultPercentageToPass() {
        return defaultPercentageToPass;
    }

    public void setDefaultPercentageToPass(String defaultPercentageToPass) {
        this.defaultPercentageToPass = defaultPercentageToPass;
    }

    public String getOverallQuestionCount() {
        return overallQuestionCount;
    }

    public void setOverallQuestionCount(String overallQuestionCount) {
        this.overallQuestionCount = overallQuestionCount;
    }
    
    public String getTestingValidityTime() {
        return testingValidityTime;
    }
    
    public void setTestingValidityTime(String testingValidityTime) {
        this.testingValidityTime = testingValidityTime;
    }
    
    public List<LabelValueBean> getTestingValidityTimeUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupTestingValidityTimeUnitOfMeasureTypeChoices();
        if(testingValidityTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(testingValidityTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getTestingValidityTimeUnitOfMeasureTypeChoice() {
        setupTestingValidityTimeUnitOfMeasureTypeChoices();
        return testingValidityTimeUnitOfMeasureTypeChoice;
    }
    
    public void setTestingValidityTimeUnitOfMeasureTypeChoice(String testingValidityTimeUnitOfMeasureTypeChoice) {
        this.testingValidityTimeUnitOfMeasureTypeChoice = testingValidityTimeUnitOfMeasureTypeChoice;
    }
    
    public String getExpiredRetentionTime() {
        return expiredRetentionTime;
    }
    
    public void setExpiredRetentionTime(String expiredRetentionTime) {
        this.expiredRetentionTime = expiredRetentionTime;
    }
    
    public List<LabelValueBean> getExpiredRetentionTimeUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupExpiredRetentionTimeUnitOfMeasureTypeChoices();
        if(expiredRetentionTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(expiredRetentionTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getExpiredRetentionTimeUnitOfMeasureTypeChoice() {
        setupExpiredRetentionTimeUnitOfMeasureTypeChoices();
        return expiredRetentionTimeUnitOfMeasureTypeChoice;
    }
    
    public void setExpiredRetentionTimeUnitOfMeasureTypeChoice(String expiredRetentionTimeUnitOfMeasureTypeChoice) {
        this.expiredRetentionTimeUnitOfMeasureTypeChoice = expiredRetentionTimeUnitOfMeasureTypeChoice;
    }
    
    public Boolean getAlwaysReassignOnExpiration() {
        return alwaysReassignOnExpiration;
    }

    public void setAlwaysReassignOnExpiration(Boolean alwaysReassignOnExpiration) {
        this.alwaysReassignOnExpiration = alwaysReassignOnExpiration;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<LabelValueBean> getOverviewMimeTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupOverviewMimeTypeChoices();
        if(overviewMimeTypeChoices != null) {
            choices = convertChoices(overviewMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setOverviewMimeTypeChoice(String overviewMimeTypeChoice) {
        this.overviewMimeTypeChoice = overviewMimeTypeChoice;
    }
    
    public String getOverviewMimeTypeChoice() {
        setupOverviewMimeTypeChoices();
        
        return overviewMimeTypeChoice;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public void setOverview(String overview) {
        this.overview = overview;
    }
    
    public List<LabelValueBean> getIntroductionMimeTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupIntroductionMimeTypeChoices();
        if(introductionMimeTypeChoices != null) {
            choices = convertChoices(introductionMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setIntroductionMimeTypeChoice(String introductionMimeTypeChoice) {
        this.introductionMimeTypeChoice = introductionMimeTypeChoice;
    }
    
    public String getIntroductionMimeTypeChoice() {
        setupIntroductionMimeTypeChoices();
        
        return introductionMimeTypeChoice;
    }
    
    public String getIntroduction() {
        return introduction;
    }
    
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        alwaysReassignOnExpiration = Boolean.FALSE;
        isDefault = Boolean.FALSE;
    }

}
