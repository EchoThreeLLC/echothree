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

package com.echothree.ui.web.main.action.warehouse.locationnameelement;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.form.CreateLocationNameElementForm;
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
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Warehouse/LocationNameElement/Add",
    mappingClass = SecureActionMapping.class,
    name = "LocationNameElementAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Warehouse/LocationNameElement/Main", redirect = true),
        @SproutForward(name = "Form", path = "/warehouse/locationnameelement/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String warehouseName = request.getParameter(ParameterConstants.WAREHOUSE_NAME);
        String locationTypeName = request.getParameter(ParameterConstants.LOCATION_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                AddActionForm actionForm = (AddActionForm)form;
                
                if(wasPost(request)) {
                    CreateLocationNameElementForm commandForm = WarehouseUtil.getHome().getCreateLocationNameElementForm();
                    
                    if(warehouseName == null)
                        warehouseName = actionForm.getWarehouseName();
                    if(locationTypeName == null)
                        locationTypeName = actionForm.getLocationTypeName();
                    
                    commandForm.setWarehouseName(warehouseName);
                    commandForm.setLocationTypeName(locationTypeName);
                    commandForm.setLocationNameElementName(actionForm.getLocationNameElementName());
                    commandForm.setOffset(actionForm.getOffset());
                    commandForm.setLength(actionForm.getLength());
                    commandForm.setValidationPattern(actionForm.getValidationPattern());
                    commandForm.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = WarehouseUtil.getHome().createLocationNameElement(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setWarehouseName(warehouseName);
                    actionForm.setLocationTypeName(locationTypeName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.WAREHOUSE_NAME, warehouseName);
            request.setAttribute(AttributeConstants.LOCATION_TYPE_NAME, locationTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.WAREHOUSE_NAME, warehouseName);
            parameters.put(ParameterConstants.LOCATION_TYPE_NAME, locationTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}