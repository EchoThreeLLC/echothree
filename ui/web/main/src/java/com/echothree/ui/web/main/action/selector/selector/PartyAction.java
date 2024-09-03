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

package com.echothree.ui.web.main.action.selector.selector;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.form.GetSelectorPartiesForm;
import com.echothree.control.user.selector.common.result.GetSelectorPartiesResult;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Selector/Selector/Party",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/selector/selector/party.jsp")
    }
)
public class PartyAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorPartiesForm();
            var selectorKindName = request.getParameter(ParameterConstants.SELECTOR_KIND_NAME);
            var selectorTypeName = request.getParameter(ParameterConstants.SELECTOR_TYPE_NAME);
            var selectorName = request.getParameter(ParameterConstants.SELECTOR_NAME);
            
            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSelectorName(selectorName);

            var commandResult = SelectorUtil.getHome().getSelectorParties(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSelectorPartiesResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.SELECTOR_KIND, result.getSelectorKind());
            request.setAttribute(AttributeConstants.SELECTOR_TYPE, result.getSelectorType());
            request.setAttribute(AttributeConstants.SELECTOR, result.getSelector());
            request.setAttribute(AttributeConstants.SELECTOR_PARTIES, result.getSelectorParties());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}