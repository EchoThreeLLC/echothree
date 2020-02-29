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

package com.echothree.ui.web.main.action.humanresources.trainingclasssection;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.form.CreateTrainingClassSectionTranslationForm;
import com.echothree.control.user.training.common.form.GetTrainingClassSectionForm;
import com.echothree.control.user.training.common.result.GetTrainingClassSectionResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/TrainingClassSection/TranslationAdd",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassSectionTranslationAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassSection/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclasssection/translationAdd.jsp")
    }
)
public class TranslationAddAction
        extends MainBaseAddAction<TranslationAddActionForm> {

    @Override
    public void setupParameters(TranslationAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        actionForm.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
    }
    
    @Override
    public void setupTransfer(TranslationAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetTrainingClassSectionForm commandForm = TrainingUtil.getHome().getGetTrainingClassSectionForm();

        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        
        CommandResult commandResult = TrainingUtil.getHome().getTrainingClassSection(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetTrainingClassSectionResult result = (GetTrainingClassSectionResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.TRAINING_CLASS_SECTION, result.getTrainingClassSection());
        }
    }
    
    @Override
    public CommandResult doAdd(TranslationAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateTrainingClassSectionTranslationForm commandForm = TrainingUtil.getHome().getCreateTrainingClassSectionTranslationForm();

        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setOverviewMimeTypeName(actionForm.getOverviewMimeTypeChoice());
        commandForm.setOverview(actionForm.getOverview());
        commandForm.setIntroductionMimeTypeName(actionForm.getIntroductionMimeTypeChoice());
        commandForm.setIntroduction(actionForm.getIntroduction());

        return TrainingUtil.getHome().createTrainingClassSectionTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
    }
    
}
