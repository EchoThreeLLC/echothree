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

package com.echothree.ui.web.main.action.accounting.transactionentityroletype;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.GetTransactionEntityRoleTypeDescriptionsForm;
import com.echothree.control.user.accounting.common.result.GetTransactionEntityRoleTypeDescriptionsResult;
import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTypeTransfer;
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
    path = "/Accounting/TransactionEntityRoleType/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/accounting/transactionentityroletype/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        GetTransactionEntityRoleTypeDescriptionsForm commandForm = AccountingUtil.getHome().getGetTransactionEntityRoleTypeDescriptionsForm();

        commandForm.setTransactionTypeName(request.getParameter(ParameterConstants.TRANSACTION_TYPE_NAME));
        commandForm.setTransactionEntityRoleTypeName(request.getParameter(ParameterConstants.TRANSACTION_ENTITY_ROLE_TYPE_NAME));

        CommandResult commandResult = AccountingUtil.getHome().getTransactionEntityRoleTypeDescriptions(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetTransactionEntityRoleTypeDescriptionsResult result = (GetTransactionEntityRoleTypeDescriptionsResult) executionResult.getResult();
            TransactionEntityRoleTypeTransfer transactionEntityRoleTypeTransfer = result.getTransactionEntityRoleType();

            request.setAttribute(AttributeConstants.TRANSACTION_ENTITY_ROLE_TYPE, transactionEntityRoleTypeTransfer);
            request.setAttribute(AttributeConstants.TRANSACTION_ENTITY_ROLE_TYPE_DESCRIPTIONS, result.getTransactionEntityRoleTypeDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }

        return mapping.findForward(forwardKey);
    }
}
