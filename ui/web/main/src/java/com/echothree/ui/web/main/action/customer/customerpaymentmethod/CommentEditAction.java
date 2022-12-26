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

package com.echothree.ui.web.main.action.customer.customerpaymentmethod;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.edit.CommentEdit;
import com.echothree.control.user.comment.common.form.EditCommentForm;
import com.echothree.control.user.comment.common.result.EditCommentResult;
import com.echothree.control.user.comment.common.spec.CommentSpec;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.form.GetCustomerForm;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.form.GetPartyPaymentMethodForm;
import com.echothree.control.user.payment.common.result.GetPartyPaymentMethodResult;
import com.echothree.model.control.comment.common.transfer.CommentTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/Customer/CustomerPaymentMethod/CommentEdit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerPaymentMethodCommentEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerPaymentMethod/Review", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customerpaymentmethod/commentEdit.jsp")
    }
)
public class CommentEditAction
        extends MainBaseEditAction<CommentEditActionForm, CommentSpec, CommentEdit, EditCommentForm, EditCommentResult> {
    
    @Override
    protected CommentSpec getSpec(HttpServletRequest request, CommentEditActionForm actionForm)
            throws NamingException {
        CommentSpec spec = CommentUtil.getHome().getCommentSpec();
        String commentName = request.getParameter(ParameterConstants.COMMENT_NAME);

        if(commentName == null) {
            commentName = actionForm.getCommentName();
        }

        spec.setCommentName(commentName);
        
        return spec;
    }
    
    @Override
    protected CommentEdit getEdit(HttpServletRequest request, CommentEditActionForm actionForm)
            throws NamingException {
        CommentEdit edit = CommentUtil.getHome().getCommentEdit();

        edit.setLanguageIsoName(actionForm.getLanguageChoice());
        edit.setDescription(actionForm.getDescription());
        edit.setMimeTypeName(actionForm.getMimeTypeChoice());
        edit.setClobComment(actionForm.getClobComment());

        return edit;
    }
    
    @Override
    protected EditCommentForm getForm()
            throws NamingException {
        return CommentUtil.getHome().getEditCommentForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, CommentEditActionForm actionForm, EditCommentResult result, CommentSpec spec, CommentEdit edit) {
        actionForm.setPartyName(request.getParameter(ParameterConstants.PARTY_NAME));
        actionForm.setPartyPaymentMethodName(request.getParameter(ParameterConstants.PARTY_PAYMENT_METHOD_NAME));
        actionForm.setCommentName(spec.getCommentName());
        actionForm.setLanguageChoice(edit.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
        actionForm.setMimeTypeChoice(edit.getMimeTypeName());
        actionForm.setClobComment(edit.getClobComment());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCommentForm commandForm)
            throws Exception {
        CommandResult commandResult = CommentUtil.getHome().editComment(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        EditCommentResult result = (EditCommentResult)executionResult.getResult();
        CommentTransfer comment = result.getComment();

        if(comment != null) {
            request.setAttribute(AttributeConstants.COMMENT, result.getComment());
        }
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(CommentEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
        parameters.put(ParameterConstants.PARTY_PAYMENT_METHOD_NAME, actionForm.getPartyPaymentMethodName());
    }
    
    private void setupPartyPaymentMethodTransfer(String partyPaymentMethodName, HttpServletRequest request)
            throws NamingException {
        GetPartyPaymentMethodForm commandForm = PaymentUtil.getHome().getGetPartyPaymentMethodForm();

        commandForm.setPartyPaymentMethodName(partyPaymentMethodName);

        CommandResult commandResult = PaymentUtil.getHome().getPartyPaymentMethod(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyPaymentMethodResult result = (GetPartyPaymentMethodResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_PAYMENT_METHOD, result.getPartyPaymentMethod());
    }

    private void setupCustomerTransfer(String partyName, HttpServletRequest request)
            throws NamingException {
        GetCustomerForm commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setPartyName(partyName);

        CommandResult commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetCustomerResult result = (GetCustomerResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CUSTOMER, result.getCustomer());
    }

    @Override
    public void setupTransfer(CommentEditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        setupPartyPaymentMethodTransfer(actionForm.getPartyPaymentMethodName(), request);
        setupCustomerTransfer(actionForm.getPartyName(), request);
    }
    
}