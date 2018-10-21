// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.accounting.glaccount;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.remote.form.CreateGlAccountForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/GlAccount/Add",
    mappingClass = SecureActionMapping.class,
    name = "GlAccountAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/GlAccount/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/glaccount/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<AddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        
        if(wasPost(request)) {
            CreateGlAccountForm commandForm = AccountingUtil.getHome().getCreateGlAccountForm();
            
            commandForm.setGlAccountName(actionForm.getGlAccountName());
            commandForm.setParentGlAccountName(actionForm.getParentGlAccountChoice());
            commandForm.setGlAccountTypeName(actionForm.getGlAccountTypeChoice());
            commandForm.setGlAccountClassName(actionForm.getGlAccountClassChoice());
            commandForm.setGlAccountCategoryName(actionForm.getGlAccountCategoryChoice());
            commandForm.setGlResourceTypeName(actionForm.getGlResourceTypeChoice());
            commandForm.setCurrencyIsoName(actionForm.getCurrencyChoice());
            commandForm.setIsDefault(actionForm.getIsDefault().toString());
            commandForm.setDescription(actionForm.getDescription());
            
            CommandResult commandResult = AccountingUtil.getHome().createGlAccount(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}