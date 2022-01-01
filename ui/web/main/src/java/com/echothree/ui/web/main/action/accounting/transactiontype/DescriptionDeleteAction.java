// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.web.main.action.accounting.transactiontype;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.DeleteTransactionTypeDescriptionForm;
import com.echothree.control.user.accounting.common.form.GetTransactionTypeDescriptionForm;
import com.echothree.control.user.accounting.common.result.GetTransactionTypeDescriptionResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Accounting/TransactionType/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "TransactionTypeDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/TransactionType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/transactiontype/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.TransactionTypeDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setTransactionTypeName(findParameter(request, ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetTransactionTypeDescriptionForm commandForm = AccountingUtil.getHome().getGetTransactionTypeDescriptionForm();
        
        commandForm.setTransactionTypeName(actionForm.getTransactionTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());
        
        CommandResult commandResult = AccountingUtil.getHome().getTransactionTypeDescription(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetTransactionTypeDescriptionResult result = (GetTransactionTypeDescriptionResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.TRANSACTION_TYPE_DESCRIPTION, result.getTransactionTypeDescription());
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteTransactionTypeDescriptionForm commandForm = AccountingUtil.getHome().getDeleteTransactionTypeDescriptionForm();

        commandForm.setTransactionTypeName(actionForm.getTransactionTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return AccountingUtil.getHome().deleteTransactionTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName());
    }
    
}
