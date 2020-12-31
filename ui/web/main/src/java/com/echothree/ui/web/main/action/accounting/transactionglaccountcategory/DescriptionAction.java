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

package com.echothree.ui.web.main.action.accounting.transactionglaccountcategory;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.GetTransactionGlAccountCategoryDescriptionsForm;
import com.echothree.control.user.accounting.common.result.GetTransactionGlAccountCategoryDescriptionsResult;
import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountCategoryTransfer;
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
    path = "/Accounting/TransactionGlAccountCategory/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/accounting/transactionglaccountcategory/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        GetTransactionGlAccountCategoryDescriptionsForm commandForm = AccountingUtil.getHome().getGetTransactionGlAccountCategoryDescriptionsForm();

        commandForm.setTransactionTypeName(request.getParameter(ParameterConstants.TRANSACTION_TYPE_NAME));
        commandForm.setTransactionGlAccountCategoryName(request.getParameter(ParameterConstants.TRANSACTION_GL_ACCOUNT_CATEGORY_NAME));

        CommandResult commandResult = AccountingUtil.getHome().getTransactionGlAccountCategoryDescriptions(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetTransactionGlAccountCategoryDescriptionsResult result = (GetTransactionGlAccountCategoryDescriptionsResult) executionResult.getResult();
            TransactionGlAccountCategoryTransfer transactionGlAccountCategoryTransfer = result.getTransactionGlAccountCategory();

            request.setAttribute(AttributeConstants.TRANSACTION_GL_ACCOUNT_CATEGORY, transactionGlAccountCategoryTransfer);
            request.setAttribute(AttributeConstants.TRANSACTION_GL_ACCOUNT_CATEGORY_DESCRIPTIONS, result.getTransactionGlAccountCategoryDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }

        return mapping.findForward(forwardKey);
    }
}
