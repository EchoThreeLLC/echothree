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

package com.echothree.ui.web.main.action.core.applicationeditoruse;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetApplicationEditorUseDescriptionsForm;
import com.echothree.control.user.core.common.result.GetApplicationEditorUseDescriptionsResult;
import com.echothree.model.control.core.common.transfer.ApplicationEditorUseTransfer;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/ApplicationEditorUse/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/applicationeditoruse/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        GetApplicationEditorUseDescriptionsForm commandForm = CoreUtil.getHome().getGetApplicationEditorUseDescriptionsForm();

        commandForm.setApplicationName(request.getParameter(ParameterConstants.APPLICATION_NAME));
        commandForm.setApplicationEditorUseName(request.getParameter(ParameterConstants.APPLICATION_EDITOR_USE_NAME));

        CommandResult commandResult = CoreUtil.getHome().getApplicationEditorUseDescriptions(getUserVisitPK(request), commandForm);
        GetApplicationEditorUseDescriptionsResult result = null;
        ApplicationEditorUseTransfer applicationEditorUse = null;
        
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            
            result = (GetApplicationEditorUseDescriptionsResult) executionResult.getResult();
            applicationEditorUse = result.getApplicationEditorUse();
        }
        
        if(applicationEditorUse == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.APPLICATION_EDITOR_USE, applicationEditorUse);
            request.setAttribute(AttributeConstants.APPLICATION_EDITOR_USE_DESCRIPTIONS, result.getApplicationEditorUseDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }
    
}
