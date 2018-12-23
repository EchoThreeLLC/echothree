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

package com.echothree.ui.web.main.action.humanresources.trainingclasspage;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.form.DeleteTrainingClassPageForm;
import com.echothree.control.user.training.common.form.GetTrainingClassPageForm;
import com.echothree.control.user.training.common.result.GetTrainingClassPageResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/HumanResources/TrainingClassPage/Delete",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassPageDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassPage/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclasspage/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.TrainingClassPage.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        actionForm.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
        actionForm.setTrainingClassPageName(findParameter(request, ParameterConstants.TRAINING_CLASS_PAGE_NAME, actionForm.getTrainingClassPageName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetTrainingClassPageForm commandForm = TrainingUtil.getHome().getGetTrainingClassPageForm();
        
        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        commandForm.setTrainingClassPageName(actionForm.getTrainingClassPageName());
        
        CommandResult commandResult = TrainingUtil.getHome().getTrainingClassPage(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetTrainingClassPageResult result = (GetTrainingClassPageResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.TRAINING_CLASS_PAGE, result.getTrainingClassPage());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteTrainingClassPageForm commandForm = TrainingUtil.getHome().getDeleteTrainingClassPageForm();

        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        commandForm.setTrainingClassPageName(actionForm.getTrainingClassPageName());

        return TrainingUtil.getHome().deleteTrainingClassPage(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
    }
    
}
