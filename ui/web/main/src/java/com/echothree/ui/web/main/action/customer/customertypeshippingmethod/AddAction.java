// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.customer.customertypeshippingmethod;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.form.CreateCustomerTypeShippingMethodForm;
import com.echothree.control.user.customer.common.form.GetCustomerTypeForm;
import com.echothree.control.user.customer.common.result.GetCustomerTypeResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Customer/CustomerTypeShippingMethod/Add",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeShippingMethodAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerTypeShippingMethod/Main", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customertypeshippingmethod/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setCustomerTypeName(findParameter(request, ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName()));
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetCustomerTypeForm commandForm = CustomerUtil.getHome().getGetCustomerTypeForm();

        commandForm.setCustomerTypeName(actionForm.getCustomerTypeName());
        
        CommandResult commandResult = CustomerUtil.getHome().getCustomerType(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCustomerTypeResult result = (GetCustomerTypeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.CUSTOMER_TYPE, result.getCustomerType());
        }
    }
    
    @Override
    public void setupDefaults(AddActionForm actionForm)
            throws NamingException {
        actionForm.setDefaultSelectionPriority("1");
        actionForm.setSortOrder("1");
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateCustomerTypeShippingMethodForm commandForm = CustomerUtil.getHome().getCreateCustomerTypeShippingMethodForm();

        commandForm.setCustomerTypeName(actionForm.getCustomerTypeName());
        commandForm.setShippingMethodName(actionForm.getShippingMethodChoice());
        commandForm.setDefaultSelectionPriority(actionForm.getDefaultSelectionPriority());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());

        return CustomerUtil.getHome().createCustomerTypeShippingMethod(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName());
    }
    
}
