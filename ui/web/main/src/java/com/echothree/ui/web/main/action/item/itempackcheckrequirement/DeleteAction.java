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

package com.echothree.ui.web.main.action.item.itempackcheckrequirement;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.DeleteItemPackCheckRequirementForm;
import com.echothree.control.user.item.common.form.GetItemPackCheckRequirementForm;
import com.echothree.control.user.item.common.result.GetItemPackCheckRequirementResult;
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
    path = "/Item/ItemPackCheckRequirement/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ItemPackCheckRequirementDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemPackCheckRequirement/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itempackcheckrequirement/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.ItemPackCheckRequirement.name();
    }

    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        actionForm.setUnitOfMeasureTypeName(findParameter(request, ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, actionForm.getUnitOfMeasureTypeName()));
    }

    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetItemPackCheckRequirementForm commandForm = ItemUtil.getHome().getGetItemPackCheckRequirementForm();

        commandForm.setItemName(actionForm.getItemName());
        commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeName());

        CommandResult commandResult = ItemUtil.getHome().getItemPackCheckRequirement(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetItemPackCheckRequirementResult result = (GetItemPackCheckRequirementResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.ITEM_PACK_CHECK_REQUIREMENT, result.getItemPackCheckRequirement());
    }

    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteItemPackCheckRequirementForm commandForm = ItemUtil.getHome().getDeleteItemPackCheckRequirementForm();

        commandForm.setItemName(actionForm.getItemName());
        commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeName());

        return ItemUtil.getHome().deleteItemPackCheckRequirement(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

}
