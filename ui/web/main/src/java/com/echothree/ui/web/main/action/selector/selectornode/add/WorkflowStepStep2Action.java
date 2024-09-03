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

package com.echothree.ui.web.main.action.selector.selectornode.add;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowsResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Selector/SelectorNode/Add/WorkflowStepStep2",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/selector/selectornode/add/workflowStepStep2.jsp")
    }
)
public class WorkflowStepStep2Action
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        
        try {
            final var selectorKindName = request.getParameter(ParameterConstants.SELECTOR_KIND_NAME);
            final var selectorTypeName = request.getParameter(ParameterConstants.SELECTOR_TYPE_NAME);
            final var selectorName = request.getParameter(ParameterConstants.SELECTOR_NAME);
            final var selectorNodeTypeName = request.getParameter(ParameterConstants.SELECTOR_NODE_TYPE_NAME);
            var commandForm = WorkflowUtil.getHome().getGetWorkflowsForm();
            
            commandForm.setSelectorKindName(selectorKindName);

            var commandResult = WorkflowUtil.getHome().getWorkflows(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWorkflowsResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.SELECTOR_KIND_NAME, selectorKindName);
            request.setAttribute(AttributeConstants.SELECTOR_TYPE_NAME, selectorTypeName);
            request.setAttribute(AttributeConstants.SELECTOR_NAME, selectorName);
            request.setAttribute(AttributeConstants.SELECTOR_NODE_TYPE_NAME, selectorNodeTypeName);
            request.setAttribute(AttributeConstants.WORKFLOWS, result.getWorkflows());
            
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
