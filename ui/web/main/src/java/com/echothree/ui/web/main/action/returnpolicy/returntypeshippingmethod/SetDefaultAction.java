// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.returnpolicy.returntypeshippingmethod;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
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
    path = "/ReturnPolicy/ReturnTypeShippingMethod/SetDefault",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ReturnPolicy/ReturnTypeShippingMethod/Main", redirect = true)
    }
)
public class SetDefaultAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        var returnKindName = request.getParameter(ParameterConstants.RETURN_KIND_NAME);
        var returnTypeName = request.getParameter(ParameterConstants.RETURN_TYPE_NAME);
        
        try {
            var commandForm = ReturnPolicyUtil.getHome().getSetDefaultReturnTypeShippingMethodForm();
            var shippingMethodName = request.getParameter(ParameterConstants.SHIPPING_METHOD_NAME);
            
            commandForm.setReturnKindName(returnKindName);
            commandForm.setReturnTypeName(returnTypeName);
            commandForm.setShippingMethodName(shippingMethodName);
            
            ReturnPolicyUtil.getHome().setDefaultReturnTypeShippingMethod(getUserVisitPK(request), commandForm);
            
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.RETURN_KIND_NAME, returnKindName);
            parameters.put(ParameterConstants.RETURN_TYPE_NAME, returnTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}