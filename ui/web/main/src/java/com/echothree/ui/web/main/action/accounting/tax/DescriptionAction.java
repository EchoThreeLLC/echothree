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

package com.echothree.ui.web.main.action.accounting.tax;

import com.echothree.control.user.tax.common.TaxUtil;
import com.echothree.control.user.tax.remote.form.GetTaxDescriptionsForm;
import com.echothree.control.user.tax.remote.result.GetTaxDescriptionsResult;
import com.echothree.model.control.tax.remote.transfer.TaxTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
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
    path = "/Accounting/Tax/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/accounting/tax/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String taxName = request.getParameter(ParameterConstants.TAX_NAME);
        GetTaxDescriptionsForm commandForm = TaxUtil.getHome().getGetTaxDescriptionsForm();
        
        commandForm.setTaxName(taxName);
        
        CommandResult commandResult = TaxUtil.getHome().getTaxDescriptions(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetTaxDescriptionsResult result = (GetTaxDescriptionsResult)executionResult.getResult();
        TaxTransfer taxTransfer = result.getTax();
        
        request.setAttribute(AttributeConstants.TAX, taxTransfer);
        request.setAttribute(AttributeConstants.TAX_NAME, taxTransfer.getTaxName());
        request.setAttribute(AttributeConstants.TAX_DESCRIPTIONS, result.getTaxDescriptions());
        
        return mapping.findForward(ForwardConstants.DISPLAY);
    }
    
}