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

package com.echothree.ui.web.main.action.payment.paymentmethod;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.form.CreateCommentForm;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.form.GetPaymentMethodForm;
import com.echothree.control.user.payment.common.result.GetPaymentMethodResult;
import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
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
    path = "/Payment/PaymentMethod/CommentAdd",
    mappingClass = SecureActionMapping.class,
    name = "PaymentMethodCommentAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Payment/PaymentMethod/Review", redirect = true),
        @SproutForward(name = "Form", path = "/payment/paymentmethod/commentAdd.jsp")
    }
)
public class CommentAddAction
        extends MainBaseAddAction<CommentAddActionForm> {

    @Override
    public void setupParameters(CommentAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPaymentMethodName(findParameter(request, ParameterConstants.PAYMENT_METHOD_NAME, actionForm.getPaymentMethodName()));
    }
    
    public String getPaymentMethodEntityRef(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetPaymentMethodForm commandForm = PaymentUtil.getHome().getGetPaymentMethodForm();
        
        commandForm.setPaymentMethodName(actionForm.getPaymentMethodName());
        
        CommandResult commandResult = PaymentUtil.getHome().getPaymentMethod(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPaymentMethodResult result = (GetPaymentMethodResult)executionResult.getResult();
        PaymentMethodTransfer paymentMethod = result.getPaymentMethod();
        
        request.setAttribute(AttributeConstants.PAYMENT_METHOD, paymentMethod);
        
        return paymentMethod.getEntityInstance().getEntityRef();
    }
    
    @Override
    public void setupTransfer(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        if(request.getAttribute(AttributeConstants.PAYMENT_METHOD) == null) {
            getPaymentMethodEntityRef(actionForm, request);
        }
    }
    
    @Override
    public CommandResult doAdd(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateCommentForm commandForm = CommentUtil.getHome().getCreateCommentForm();

        commandForm.setEntityRef(getPaymentMethodEntityRef(actionForm, request));
        commandForm.setCommentTypeName(CommentConstants.CommentType_PAYMENT_METHOD);
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setMimeTypeName(actionForm.getMimeTypeChoice());
        commandForm.setClobComment(actionForm.getClobComment());

        return CommentUtil.getHome().createComment(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CommentAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PAYMENT_METHOD_NAME, actionForm.getPaymentMethodName());
    }
    
}
