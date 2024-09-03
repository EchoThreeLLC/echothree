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

package com.echothree.ui.web.main.action.shipping.shipmenttypeshippingmethod;

import com.echothree.control.user.shipment.common.ShipmentUtil;
import com.echothree.control.user.shipment.common.edit.ShipmentTypeShippingMethodEdit;
import com.echothree.control.user.shipment.common.form.EditShipmentTypeShippingMethodForm;
import com.echothree.control.user.shipment.common.result.EditShipmentTypeShippingMethodResult;
import com.echothree.control.user.shipment.common.spec.ShipmentTypeShippingMethodSpec;
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
    path = "/Shipping/ShipmentTypeShippingMethod/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ShipmentTypeShippingMethodEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Shipping/ShipmentTypeShippingMethod/Main", redirect = true),
        @SproutForward(name = "Form", path = "/shipping/shipmenttypeshippingmethod/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var shipmentTypeName = request.getParameter(ParameterConstants.SHIPMENT_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = ShipmentUtil.getHome().getEditShipmentTypeShippingMethodForm();
                var spec = ShipmentUtil.getHome().getShipmentTypeShippingMethodSpec();
                var shippingMethodName = request.getParameter(ParameterConstants.SHIPPING_METHOD_NAME);
                
                if(shipmentTypeName == null)
                    shipmentTypeName = actionForm.getShipmentTypeName();
                if(shippingMethodName == null)
                    shippingMethodName = actionForm.getShippingMethodName();
                
                commandForm.setSpec(spec);
                spec.setShipmentTypeName(shipmentTypeName);
                spec.setShippingMethodName(shippingMethodName);
                
                if(wasPost(request)) {
                    var edit = ShipmentUtil.getHome().getShipmentTypeShippingMethodEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());

                    var commandResult = ShipmentUtil.getHome().editShipmentTypeShippingMethod(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditShipmentTypeShippingMethodResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ShipmentUtil.getHome().editShipmentTypeShippingMethod(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditShipmentTypeShippingMethodResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setShipmentTypeName(shipmentTypeName);
                            actionForm.setShippingMethodName(shippingMethodName);
                            actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            actionForm.setSortOrder(edit.getSortOrder());
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

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.SHIPMENT_TYPE_NAME, shipmentTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.SHIPMENT_TYPE_NAME, shipmentTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}