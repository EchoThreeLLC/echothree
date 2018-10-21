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

package com.echothree.ui.web.main.action.humanresources.trainingclasssection;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.remote.form.DeleteTrainingClassSectionForm;
import com.echothree.control.user.training.remote.form.GetTrainingClassSectionForm;
import com.echothree.control.user.training.remote.result.GetTrainingClassSectionResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/TrainingClassSection/Delete",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassSectionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassSection/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclasssection/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.TrainingClassSection.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        actionForm.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetTrainingClassSectionForm commandForm = TrainingUtil.getHome().getGetTrainingClassSectionForm();
        
        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        
        CommandResult commandResult = TrainingUtil.getHome().getTrainingClassSection(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetTrainingClassSectionResult result = (GetTrainingClassSectionResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.TRAINING_CLASS_SECTION, result.getTrainingClassSection());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteTrainingClassSectionForm commandForm = TrainingUtil.getHome().getDeleteTrainingClassSectionForm();

        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());

        return TrainingUtil.getHome().deleteTrainingClassSection(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
    }
    
}
