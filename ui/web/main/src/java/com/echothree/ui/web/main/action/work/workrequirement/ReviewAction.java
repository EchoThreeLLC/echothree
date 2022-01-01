// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.web.main.action.work.workrequirement;

import com.echothree.control.user.workrequirement.common.WorkRequirementUtil;
import com.echothree.control.user.workrequirement.common.form.GetWorkRequirementForm;
import com.echothree.control.user.workrequirement.common.result.GetWorkRequirementResult;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.workrequirement.common.WorkRequirementOptions;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Work/WorkRequirement/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/work/workrequirement/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetWorkRequirementForm commandForm = WorkRequirementUtil.getHome().getGetWorkRequirementForm();

        commandForm.setWorkRequirementName(request.getParameter(ParameterConstants.WORK_REQUIREMENT_NAME));

        Set<String> options = new HashSet<>();
        options.add(PartyOptions.PartyIncludeDescription);
        options.add(WorkRequirementOptions.WorkRequirementIncludeWorkAssignments);
        options.add(WorkRequirementOptions.WorkRequirementIncludeWorkTimes);
        commandForm.setOptions(options);

        CommandResult commandResult = WorkRequirementUtil.getHome().getWorkRequirement(getUserVisitPK(request), commandForm);
        WorkRequirementTransfer workRequirement = null;

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetWorkRequirementResult result = (GetWorkRequirementResult)executionResult.getResult();

            workRequirement = result.getWorkRequirement();
        }

        if(workRequirement == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.WORK_REQUIREMENT, workRequirement);
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }

}
