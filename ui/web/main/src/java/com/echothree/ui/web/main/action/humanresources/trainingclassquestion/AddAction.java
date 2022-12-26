// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.humanresources.trainingclassquestion;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.form.CreateTrainingClassQuestionForm;
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
    path = "/HumanResources/TrainingClassQuestion/Add",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassQuestionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassQuestion/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclassquestion/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        actionForm.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
    }
    
    @Override
    public void setupDefaults(AddActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
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
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateTrainingClassQuestionForm commandForm = TrainingUtil.getHome().getCreateTrainingClassQuestionForm();

        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        commandForm.setTrainingClassQuestionName(actionForm.getTrainingClassQuestionName());
        commandForm.setAskingRequired(actionForm.getAskingRequired().toString());
        commandForm.setPassingRequired(actionForm.getPassingRequired().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setQuestionMimeTypeName(actionForm.getQuestionMimeTypeChoice());
        commandForm.setQuestion(actionForm.getQuestion());

        return TrainingUtil.getHome().createTrainingClassQuestion(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
    }
    
}
