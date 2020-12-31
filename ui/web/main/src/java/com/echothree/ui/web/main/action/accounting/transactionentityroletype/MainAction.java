// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.accounting.transactionentityroletype;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.GetTransactionEntityRoleTypesForm;
import com.echothree.control.user.accounting.common.result.GetTransactionEntityRoleTypesResult;
import com.echothree.model.control.accounting.common.transfer.TransactionTypeTransfer;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/TransactionEntityRoleType/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/accounting/transactionentityroletype/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String transactionTypeName = request.getParameter(ParameterConstants.TRANSACTION_TYPE_NAME);
        GetTransactionEntityRoleTypesForm commandForm = AccountingUtil.getHome().getGetTransactionEntityRoleTypesForm();

        commandForm.setTransactionTypeName(transactionTypeName);

        CommandResult commandResult = AccountingUtil.getHome().getTransactionEntityRoleTypes(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetTransactionEntityRoleTypesResult result = (GetTransactionEntityRoleTypesResult)executionResult.getResult();
        TransactionTypeTransfer transactionTypeTransfer = result.getTransactionType();

        request.setAttribute(AttributeConstants.TRANSACTION_TYPE, transactionTypeTransfer);
        request.setAttribute(AttributeConstants.TRANSACTION_ENTITY_ROLE_TYPES, result.getTransactionEntityRoleTypes());
        
        return mapping.findForward(ForwardConstants.DISPLAY);
    }
    
}