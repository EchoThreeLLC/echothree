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

package com.echothree.ui.web.main.action.cancellationpolicy.cancellationpolicyreason;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.form.CreateCancellationPolicyReasonForm;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/CancellationPolicy/CancellationPolicyReason/Add",
    mappingClass = SecureActionMapping.class,
    name = "CancellationPolicyReasonAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/CancellationPolicy/CancellationPolicyReason/Main", redirect = true),
        @SproutForward(name = "Form", path = "/cancellationpolicy/cancellationpolicyreason/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String cancellationKindName = request.getParameter(ParameterConstants.CANCELLATION_KIND_NAME);
        String cancellationPolicyName = request.getParameter(ParameterConstants.CANCELLATION_POLICY_NAME);
        
        try {
            if(forwardKey == null) {
                AddActionForm actionForm = (AddActionForm)form;
                
                if(cancellationKindName == null)
                    cancellationKindName = actionForm.getCancellationKindName();
                if(cancellationPolicyName == null)
                    cancellationPolicyName = actionForm.getCancellationPolicyName();
                
                if(wasPost(request)) {
                    CreateCancellationPolicyReasonForm commandForm = CancellationPolicyUtil.getHome().getCreateCancellationPolicyReasonForm();
                    
                    commandForm.setCancellationKindName(cancellationKindName);
                    commandForm.setCancellationPolicyName(cancellationPolicyName);
                    commandForm.setCancellationReasonName(actionForm.getCancellationReasonChoice());
                    commandForm.setIsDefault(actionForm.getIsDefault().toString());
                    commandForm.setSortOrder(actionForm.getSortOrder());
                    
                    CommandResult commandResult = CancellationPolicyUtil.getHome().createCancellationPolicyReason(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setCancellationKindName(cancellationKindName);
                    actionForm.setCancellationPolicyName(cancellationPolicyName);
                    actionForm.setSortOrder("1");
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CANCELLATION_KIND_NAME, cancellationKindName);
            request.setAttribute(AttributeConstants.CANCELLATION_POLICY_NAME, cancellationPolicyName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.CANCELLATION_KIND_NAME, cancellationKindName);
            parameters.put(ParameterConstants.CANCELLATION_POLICY_NAME, cancellationPolicyName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
