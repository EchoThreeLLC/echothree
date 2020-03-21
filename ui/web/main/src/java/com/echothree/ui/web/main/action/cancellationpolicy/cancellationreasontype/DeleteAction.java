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

package com.echothree.ui.web.main.action.cancellationpolicy.cancellationreasontype;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.form.DeleteCancellationReasonTypeForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/CancellationPolicy/CancellationReasonType/Delete",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/CancellationPolicy/CancellationReasonType/Main", redirect = true)
    }
)
public class DeleteAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        String cancellationKindName = request.getParameter(ParameterConstants.CANCELLATION_KIND_NAME);
        String cancellationReasonName = request.getParameter(ParameterConstants.CANCELLATION_REASON_NAME);
        
        try {
            DeleteCancellationReasonTypeForm commandForm = CancellationPolicyUtil.getHome().getDeleteCancellationReasonTypeForm();
            String cancellationTypeName = request.getParameter(ParameterConstants.CANCELLATION_TYPE_NAME);
            
            commandForm.setCancellationKindName(cancellationKindName);
            commandForm.setCancellationReasonName(cancellationReasonName);
            commandForm.setCancellationTypeName(cancellationTypeName);
            
            CancellationPolicyUtil.getHome().deleteCancellationReasonType(getUserVisitPK(request), commandForm);
            
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.CANCELLATION_KIND_NAME, cancellationKindName);
            parameters.put(ParameterConstants.CANCELLATION_REASON_NAME, cancellationReasonName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}