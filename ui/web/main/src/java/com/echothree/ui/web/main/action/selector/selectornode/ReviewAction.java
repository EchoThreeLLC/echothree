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

package com.echothree.ui.web.main.action.selector.selectornode;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.form.GetSelectorNodeForm;
import com.echothree.control.user.selector.common.result.GetSelectorNodeResult;
import com.echothree.model.control.selector.common.transfer.SelectorNodeTransfer;
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
    path = "/Selector/SelectorNode/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/selector/selectornode/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        
        try {
            GetSelectorNodeForm commandForm = SelectorUtil.getHome().getGetSelectorNodeForm();
            String selectorKindName = request.getParameter(ParameterConstants.SELECTOR_KIND_NAME);
            String selectorTypeName = request.getParameter(ParameterConstants.SELECTOR_TYPE_NAME);
            String selectorName = request.getParameter(ParameterConstants.SELECTOR_NAME);
            String selectorNodeName = request.getParameter(ParameterConstants.SELECTOR_NODE_NAME);
            
            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSelectorName(selectorName);
            commandForm.setSelectorNodeName(selectorNodeName);
            
            CommandResult commandResult = SelectorUtil.getHome().getSelectorNode(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetSelectorNodeResult result = (GetSelectorNodeResult)executionResult.getResult();
            SelectorNodeTransfer selectorNode = result.getSelectorNode();
            
            if(selectorNode == null) {
                forwardKey = ForwardConstants.ERROR_404;
            } else {
                request.setAttribute(AttributeConstants.SELECTOR_NODE, selectorNode);
                forwardKey = ForwardConstants.DISPLAY;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}