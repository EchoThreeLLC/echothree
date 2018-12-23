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

package com.echothree.ui.web.main.action.payment.paymentmethod;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.form.CreatePaymentMethodDescriptionForm;
import com.echothree.control.user.payment.common.form.GetPaymentMethodForm;
import com.echothree.control.user.payment.common.result.GetPaymentMethodResult;
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
    path = "/Payment/PaymentMethod/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "PaymentMethodDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Payment/PaymentMethod/Description", redirect = true),
        @SproutForward(name = "Form", path = "/payment/paymentmethod/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPaymentMethodName(findParameter(request, ParameterConstants.PAYMENT_METHOD_NAME, actionForm.getPaymentMethodName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetPaymentMethodForm commandForm = PaymentUtil.getHome().getGetPaymentMethodForm();

        commandForm.setPaymentMethodName(actionForm.getPaymentMethodName());
        
        CommandResult commandResult = PaymentUtil.getHome().getPaymentMethod(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPaymentMethodResult result = (GetPaymentMethodResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.PAYMENT_METHOD, result.getPaymentMethod());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreatePaymentMethodDescriptionForm commandForm = PaymentUtil.getHome().getCreatePaymentMethodDescriptionForm();

        commandForm.setPaymentMethodName( actionForm.getPaymentMethodName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return PaymentUtil.getHome().createPaymentMethodDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PAYMENT_METHOD_NAME, actionForm.getPaymentMethodName());
    }
    
}
