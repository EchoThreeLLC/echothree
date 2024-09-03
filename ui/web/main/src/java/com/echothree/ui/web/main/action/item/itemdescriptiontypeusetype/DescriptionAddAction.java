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

package com.echothree.ui.web.main.action.item.itemdescriptiontypeusetype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.CreateItemDescriptionTypeUseTypeDescriptionForm;
import com.echothree.control.user.item.common.form.GetItemDescriptionTypeUseTypeForm;
import com.echothree.control.user.item.common.result.GetItemDescriptionTypeUseTypeResult;
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
    path = "/Item/ItemDescriptionTypeUseType/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "ItemDescriptionTypeUseTypeDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemDescriptionTypeUseType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemdescriptiontypeusetype/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setItemDescriptionTypeUseTypeName(findParameter(request, ParameterConstants.ITEM_DESCRIPTION_TYPE_USE_TYPE_NAME, actionForm.getItemDescriptionTypeUseTypeName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetItemDescriptionTypeUseTypeForm();

        commandForm.setItemDescriptionTypeUseTypeName(actionForm.getItemDescriptionTypeUseTypeName());

        var commandResult = ItemUtil.getHome().getItemDescriptionTypeUseType(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemDescriptionTypeUseTypeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.ITEM_DESCRIPTION_TYPE_USE_TYPE, result.getItemDescriptionTypeUseType());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getCreateItemDescriptionTypeUseTypeDescriptionForm();

        commandForm.setItemDescriptionTypeUseTypeName( actionForm.getItemDescriptionTypeUseTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return ItemUtil.getHome().createItemDescriptionTypeUseTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_DESCRIPTION_TYPE_USE_TYPE_NAME, actionForm.getItemDescriptionTypeUseTypeName());
    }
    
}
