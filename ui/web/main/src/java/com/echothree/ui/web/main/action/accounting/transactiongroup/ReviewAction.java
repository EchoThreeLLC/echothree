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

package com.echothree.ui.web.main.action.accounting.transactiongroup;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.GetTransactionGroupForm;
import com.echothree.control.user.accounting.common.result.GetTransactionGroupResult;
import com.echothree.model.control.accounting.common.transfer.TransactionGroupTransfer;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/TransactionGroup/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/accounting/transactiongroup/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        
        try {
            GetTransactionGroupForm commandForm = AccountingUtil.getHome().getGetTransactionGroupForm();
            String transactionGroupName = request.getParameter(ParameterConstants.TRANSACTION_GROUP_NAME);
            
            commandForm.setTransactionGroupName(transactionGroupName);
            
            CommandResult commandResult = AccountingUtil.getHome().getTransactionGroup(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetTransactionGroupResult result = (GetTransactionGroupResult)executionResult.getResult();
            TransactionGroupTransfer transactionGroup = result.getTransactionGroup();
            
            if(transactionGroup == null) {
                forwardKey = ForwardConstants.ERROR_404;
            } else {
                request.setAttribute(AttributeConstants.TRANSACTION_GROUP, transactionGroup);
                forwardKey = ForwardConstants.DISPLAY;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
