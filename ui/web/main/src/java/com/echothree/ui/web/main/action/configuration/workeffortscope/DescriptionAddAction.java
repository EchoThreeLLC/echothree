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

package com.echothree.ui.web.main.action.configuration.workeffortscope;

import com.echothree.control.user.workeffort.common.WorkEffortUtil;
import com.echothree.control.user.workeffort.common.form.CreateWorkEffortScopeDescriptionForm;
import com.echothree.control.user.workeffort.common.form.GetWorkEffortScopeForm;
import com.echothree.control.user.workeffort.common.result.GetWorkEffortScopeResult;
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
    path = "/Configuration/WorkEffortScope/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "WorkEffortScopeDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkEffortScope/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workeffortscope/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setWorkEffortTypeName(findParameter(request, ParameterConstants.WORK_EFFORT_TYPE_NAME, actionForm.getWorkEffortTypeName()));
        actionForm.setWorkEffortScopeName(findParameter(request, ParameterConstants.WORK_EFFORT_SCOPE_NAME, actionForm.getWorkEffortScopeName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetWorkEffortScopeForm commandForm = WorkEffortUtil.getHome().getGetWorkEffortScopeForm();

        commandForm.setWorkEffortTypeName(actionForm.getWorkEffortTypeName());
        commandForm.setWorkEffortScopeName(actionForm.getWorkEffortScopeName());
        
        CommandResult commandResult = WorkEffortUtil.getHome().getWorkEffortScope(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetWorkEffortScopeResult result = (GetWorkEffortScopeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.WORK_EFFORT_SCOPE, result.getWorkEffortScope());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateWorkEffortScopeDescriptionForm commandForm = WorkEffortUtil.getHome().getCreateWorkEffortScopeDescriptionForm();

        commandForm.setWorkEffortTypeName(actionForm.getWorkEffortTypeName());
        commandForm.setWorkEffortScopeName(actionForm.getWorkEffortScopeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return WorkEffortUtil.getHome().createWorkEffortScopeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORK_EFFORT_TYPE_NAME, actionForm.getWorkEffortTypeName());
        parameters.put(ParameterConstants.WORK_EFFORT_SCOPE_NAME, actionForm.getWorkEffortScopeName());
    }
    
}
