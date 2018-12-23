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

package com.echothree.ui.web.main.action.item.item.DescriptionAdd;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.GetItemDescriptionTypeForm;
import com.echothree.control.user.item.common.form.GetItemDescriptionTypesForm;
import com.echothree.control.user.item.common.form.GetItemForm;
import com.echothree.control.user.item.common.result.GetItemDescriptionTypesResult;
import com.echothree.control.user.item.common.result.GetItemResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Item/Item/DescriptionAdd/Step1",
    mappingClass = SecureActionMapping.class,
    name = "ItemDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Cancel", path = "/action/Item/Item/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/descriptionAdd/step1.jsp"),
        @SproutForward(name = "Display", path = "/action/Item/Item/DescriptionAdd/Step2", redirect = true)
    }
)
public class Step1Action
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
    }

    private void setupItemTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetItemForm commandForm = ItemUtil.getHome().getGetItemForm();

        commandForm.setItemName(actionForm.getItemName());

        CommandResult commandResult = ItemUtil.getHome().getItem(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetItemResult result = (GetItemResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.ITEM, result.getItem());
    }

    private void setupItemDescriptionTypeTransfers(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetItemDescriptionTypesForm commandForm = ItemUtil.getHome().getGetItemDescriptionTypesForm();
        
        Set<String> options = new HashSet<>();
        options.add(CoreOptions.EntityInstanceIncludeEntityAppearance);
        options.add(CoreOptions.AppearanceIncludeTextDecorations);
        options.add(CoreOptions.AppearanceIncludeTextTransformations);
        commandForm.setOptions(options);
        
        CommandResult commandResult = ItemUtil.getHome().getItemDescriptionTypes(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetItemDescriptionTypesResult result = (GetItemDescriptionTypesResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.ITEM_DESCRIPTION_TYPES, result.getItemDescriptionTypes());
    }

    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        setupItemTransfer(actionForm, request);
        setupItemDescriptionTypeTransfers(actionForm, request);
    }

    @Override
    protected String getCancelForward(DescriptionAddActionForm actionForm) {
        return ForwardConstants.CANCEL;
    }

    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetItemDescriptionTypeForm commandForm = ItemUtil.getHome().getGetItemDescriptionTypeForm();

        commandForm.setItemDescriptionTypeName(actionForm.getItemDescriptionTypeName());

        return ItemUtil.getHome().getItemDescriptionType(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
        parameters.put(ParameterConstants.ITEM_DESCRIPTION_TYPE_NAME, actionForm.getItemDescriptionTypeName());
    }
    
}
