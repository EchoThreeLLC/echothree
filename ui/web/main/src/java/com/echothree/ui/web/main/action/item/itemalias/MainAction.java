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

package com.echothree.ui.web.main.action.item.itemalias;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.GetItemAliasesForm;
import com.echothree.control.user.item.common.result.GetItemAliasesResult;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
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
    path = "/Item/ItemAlias/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/item/itemalias/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetItemAliasesForm commandForm = ItemUtil.getHome().getGetItemAliasesForm();
        String itemName = request.getParameter(ParameterConstants.ITEM_NAME);

        commandForm.setItemName(itemName);

        CommandResult commandResult = ItemUtil.getHome().getItemAliases(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetItemAliasesResult result = (GetItemAliasesResult)executionResult.getResult();
        ItemTransfer item = result.getItem();

        if(item != null) {
            request.setAttribute(AttributeConstants.ITEM, result.getItem());
            request.setAttribute(AttributeConstants.ITEM_ALIASES, result.getItemAliases());

            forwardKey = ForwardConstants.DISPLAY;
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }

        return mapping.findForward(forwardKey);
    }

}
