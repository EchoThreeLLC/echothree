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

package com.echothree.ui.web.main.action.accounting.transactiongroup;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.SetTransactionGroupStatusForm;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.CustomActionForward;
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
    path = "/Accounting/TransactionGroup/Status",
    mappingClass = SecureActionMapping.class,
    name = "TransactionGroupStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/TransactionGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/transactiongroup/status.jsp")
    }
)
public class StatusAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        StatusActionForm actionForm = (StatusActionForm)form;
        String forwardKey;
        String transactionGroupName = request.getParameter(ParameterConstants.TRANSACTION_GROUP_NAME);
        
        try {
            SetTransactionGroupStatusForm commandForm = AccountingUtil.getHome().getSetTransactionGroupStatusForm();
            
            if(transactionGroupName == null)
                transactionGroupName = actionForm.getTransactionGroupName();
            
            if(wasPost(request)) {
                commandForm.setTransactionGroupName(transactionGroupName);
                commandForm.setTransactionGroupStatusChoice(actionForm.getTransactionGroupStatusChoice());
                
                CommandResult commandResult = AccountingUtil.getHome().setTransactionGroupStatus(getUserVisitPK(request), commandForm);
                
                if(commandResult.hasErrors()) {
                    setCommandResultAttribute(request, commandResult);
                    forwardKey = ForwardConstants.FORM;
                } else {
                    forwardKey = ForwardConstants.DISPLAY;
                }
            } else {
                actionForm.setTransactionGroupName(transactionGroupName);
                actionForm.setupTransactionGroupStatusChoices();
                forwardKey = ForwardConstants.FORM;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.TRANSACTION_GROUP_NAME, transactionGroupName);
        }
        
        return customActionForward;
    }
    
}
