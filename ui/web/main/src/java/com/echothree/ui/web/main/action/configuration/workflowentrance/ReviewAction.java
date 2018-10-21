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

package com.echothree.ui.web.main.action.configuration.workflowentrance;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.remote.form.GetWorkflowEntranceForm;
import com.echothree.control.user.workflow.remote.result.GetWorkflowEntranceResult;
import com.echothree.model.control.workflow.remote.transfer.WorkflowEntranceTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/WorkflowEntrance/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/workflowentrance/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetWorkflowEntranceForm commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceForm();

        commandForm.setWorkflowName(request.getParameter(ParameterConstants.WORKFLOW_NAME));
        commandForm.setWorkflowEntranceName(request.getParameter(ParameterConstants.WORKFLOW_ENTRANCE_NAME));

        CommandResult commandResult = WorkflowUtil.getHome().getWorkflowEntrance(getUserVisitPK(request), commandForm);
        WorkflowEntranceTransfer workflowEntrance = null;

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetWorkflowEntranceResult result = (GetWorkflowEntranceResult)executionResult.getResult();

            workflowEntrance = result.getWorkflowEntrance();
        }

        if(workflowEntrance == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.WORKFLOW_ENTRANCE, workflowEntrance);
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }

}
