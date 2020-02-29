// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.purchasing.vendoritemcost;

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.edit.VendorItemCostEdit;
import com.echothree.control.user.vendor.common.form.EditVendorItemCostForm;
import com.echothree.control.user.vendor.common.result.EditVendorItemCostResult;
import com.echothree.control.user.vendor.common.spec.VendorItemCostSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/VendorItemCost/Edit",
    mappingClass = SecureActionMapping.class,
    name = "VendorItemCostEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/VendorItemCost/Main", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendoritemcost/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String vendorName = request.getParameter(ParameterConstants.VENDOR_NAME);
        String vendorItemName = request.getParameter(ParameterConstants.VENDOR_ITEM_NAME);
        String inventoryConditionName = request.getParameter(ParameterConstants.INVENTORY_CONDITION_NAME);
        String unitOfMeasureTypeName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditVendorItemCostForm commandForm = VendorUtil.getHome().getEditVendorItemCostForm();
                VendorItemCostSpec spec = VendorUtil.getHome().getVendorItemCostSpec();
                
                if(vendorName == null)
                    vendorName = actionForm.getVendorName();
                if(vendorItemName == null)
                    vendorItemName = actionForm.getVendorItemName();
                if(inventoryConditionName == null)
                    inventoryConditionName = actionForm.getInventoryConditionName();
                if(unitOfMeasureTypeName == null)
                    unitOfMeasureTypeName = actionForm.getUnitOfMeasureTypeName();
                
                commandForm.setSpec(spec);
                spec.setVendorName(vendorName);
                spec.setVendorItemName(vendorItemName);
                spec.setInventoryConditionName(inventoryConditionName);
                spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                
                if(wasPost(request)) {
                    VendorItemCostEdit edit = VendorUtil.getHome().getVendorItemCostEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setUnitCost(actionForm.getUnitCost());
                    
                    CommandResult commandResult = VendorUtil.getHome().editVendorItemCost(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditVendorItemCostResult result = (EditVendorItemCostResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = VendorUtil.getHome().editVendorItemCost(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditVendorItemCostResult result = (EditVendorItemCostResult)executionResult.getResult();
                    
                    if(result != null) {
                        VendorItemCostEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setVendorName(vendorName);
                            actionForm.setVendorItemName(vendorItemName);
                            actionForm.setInventoryConditionName(inventoryConditionName);
                            actionForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                            actionForm.setUnitCost(edit.getUnitCost());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.VENDOR_NAME, vendorName);
            request.setAttribute(AttributeConstants.VENDOR_ITEM_NAME, vendorItemName);
            request.setAttribute(AttributeConstants.INVENTORY_CONDITION_NAME, inventoryConditionName);
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_TYPE_NAME, unitOfMeasureTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(4);
            
            parameters.put(ParameterConstants.VENDOR_NAME, vendorName);
            parameters.put(ParameterConstants.VENDOR_ITEM_NAME, vendorItemName);
            parameters.put(ParameterConstants.INVENTORY_CONDITION_NAME, inventoryConditionName);
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, unitOfMeasureTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}