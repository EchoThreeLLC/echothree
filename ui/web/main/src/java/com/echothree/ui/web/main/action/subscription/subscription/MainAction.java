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

package com.echothree.ui.web.main.action.subscription.subscription;

import com.echothree.control.user.subscription.common.SubscriptionUtil;
import com.echothree.control.user.subscription.common.result.GetSubscriptionsResult;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Subscription/Subscription/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/subscription/subscription/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            var commandForm = SubscriptionUtil.getHome().getGetSubscriptionsForm();
            var subscriptionKindName = request.getParameter(ParameterConstants.SUBSCRIPTION_KIND_NAME);
            var subscriptionTypeName = request.getParameter(ParameterConstants.SUBSCRIPTION_TYPE_NAME);
            
            commandForm.setSubscriptionKindName(subscriptionKindName);
            commandForm.setSubscriptionTypeName(subscriptionTypeName);
            
            Set<String> options = new HashSet<>();
            options.add(PartyOptions.PartyIncludeDescription);
            commandForm.setOptions(options);

            var commandResult = SubscriptionUtil.getHome().getSubscriptions(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSubscriptionsResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.SUBSCRIPTION_KIND, result.getSubscriptionKind());
            request.setAttribute(AttributeConstants.SUBSCRIPTION_TYPE, result.getSubscriptionType());
            request.setAttribute(AttributeConstants.SUBSCRIPTIONS, result.getSubscriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
