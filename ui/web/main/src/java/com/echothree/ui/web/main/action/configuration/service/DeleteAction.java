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

package com.echothree.ui.web.main.action.configuration.service;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.DeleteServiceForm;
import com.echothree.control.user.core.common.form.GetServiceForm;
import com.echothree.control.user.core.common.result.GetServiceResult;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/Service/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ServiceDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/Service/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/service/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.Service.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setServiceName(findParameter(request, ParameterConstants.SERVICE_NAME, actionForm.getServiceName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetServiceForm commandForm = CoreUtil.getHome().getGetServiceForm();
        
        commandForm.setServiceName(actionForm.getServiceName());
        
        CommandResult commandResult = CoreUtil.getHome().getService(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetServiceResult result = (GetServiceResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.SERVICE, result.getService());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteServiceForm commandForm = CoreUtil.getHome().getDeleteServiceForm();

        commandForm.setServiceName(actionForm.getServiceName());

        return CoreUtil.getHome().deleteService(getUserVisitPK(request), commandForm);
    }
    
}
