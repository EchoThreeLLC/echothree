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

package com.echothree.ui.web.main.action.accounting.tax;

import com.echothree.control.user.tax.common.TaxUtil;
import com.echothree.control.user.tax.common.form.CreateTaxDescriptionForm;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/Tax/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "TaxDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/Tax/Description", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/tax/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<DescriptionAddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, DescriptionAddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String taxName = request.getParameter(ParameterConstants.TAX_NAME);
        
        if(wasPost(request)) {
            CreateTaxDescriptionForm commandForm = TaxUtil.getHome().getCreateTaxDescriptionForm();
            
            if(taxName == null)
                taxName = actionForm.getTaxName();
            
            commandForm.setTaxName(taxName);
            commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
            commandForm.setDescription(actionForm.getDescription());
            
            CommandResult commandResult = TaxUtil.getHome().createTaxDescription(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setTaxName(taxName);
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.TAX_NAME, taxName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.TAX_NAME, taxName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}