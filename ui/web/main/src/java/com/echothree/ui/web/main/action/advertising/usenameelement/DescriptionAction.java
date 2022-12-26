// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.advertising.usenameelement;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.form.GetUseNameElementDescriptionsForm;
import com.echothree.control.user.offer.common.result.GetUseNameElementDescriptionsResult;
import com.echothree.model.control.offer.common.transfer.UseNameElementTransfer;
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
    path = "/Advertising/UseNameElement/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/advertising/usenameelement/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            String useNameElementName = request.getParameter(ParameterConstants.USE_NAME_ELEMENT_NAME);
            GetUseNameElementDescriptionsForm commandForm = OfferUtil.getHome().getGetUseNameElementDescriptionsForm();
            
            commandForm.setUseNameElementName(useNameElementName);
            
            CommandResult commandResult = OfferUtil.getHome().getUseNameElementDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetUseNameElementDescriptionsResult result = (GetUseNameElementDescriptionsResult)executionResult.getResult();
            UseNameElementTransfer useNameElementTransfer = result.getUseNameElement();
            
            request.setAttribute(AttributeConstants.USE_NAME_ELEMENT, useNameElementTransfer);
            request.setAttribute(AttributeConstants.USE_NAME_ELEMENT_NAME, useNameElementTransfer.getUseNameElementName());
            request.setAttribute(AttributeConstants.USE_NAME_ELEMENT_DESCRIPTIONS, result.getUseNameElementDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}