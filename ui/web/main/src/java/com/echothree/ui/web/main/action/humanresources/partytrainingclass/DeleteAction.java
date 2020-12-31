// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.humanresources.partytrainingclass;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.form.DeletePartyTrainingClassForm;
import com.echothree.control.user.training.common.form.GetPartyTrainingClassForm;
import com.echothree.control.user.training.common.result.GetPartyTrainingClassResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassTransfer;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import static com.echothree.view.client.web.struts.BaseAction.getUserVisitPK;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/PartyTrainingClass/Delete",
    mappingClass = SecureActionMapping.class,
    name = "PartyTrainingClassDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/PartyTrainingClass/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/partytrainingclass/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.PartyTrainingClass.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setPartyTrainingClassName(findParameter(request, ParameterConstants.PARTY_TRAINING_CLASS_NAME, actionForm.getPartyTrainingClassName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetPartyTrainingClassForm commandForm = TrainingUtil.getHome().getGetPartyTrainingClassForm();
        
        commandForm.setPartyTrainingClassName(actionForm.getPartyTrainingClassName());
        
        CommandResult commandResult = TrainingUtil.getHome().getPartyTrainingClass(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyTrainingClassResult result = (GetPartyTrainingClassResult)executionResult.getResult();
        PartyTrainingClassTransfer partyTrainingClass = result.getPartyTrainingClass();
        
        request.setAttribute(AttributeConstants.PARTY_TRAINING_CLASS, partyTrainingClass);
        request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request),
                partyTrainingClass.getParty().getPartyName(), null));
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeletePartyTrainingClassForm commandForm = TrainingUtil.getHome().getDeletePartyTrainingClassForm();

        commandForm.setPartyTrainingClassName(actionForm.getPartyTrainingClassName());

        return TrainingUtil.getHome().deletePartyTrainingClass(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
}
