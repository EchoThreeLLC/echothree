// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.warehouse.locationcapacity.Add;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypesForm;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypesResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
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
    path = "/Warehouse/LocationCapacity/Add/Step2",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/warehouse/locationcapacity/add/step2.jsp")
    }
)
public class Step2Action
        extends BaseAddAction {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String warehouseName = request.getParameter(ParameterConstants.WAREHOUSE_NAME);
        String locationName = request.getParameter(ParameterConstants.LOCATION_NAME);
        String unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        GetUnitOfMeasureTypesForm commandForm = UomUtil.getHome().getGetUnitOfMeasureTypesForm();
        
        commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
        
        CommandResult commandResult = UomUtil.getHome().getUnitOfMeasureTypes(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetUnitOfMeasureTypesResult result = (GetUnitOfMeasureTypesResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_KIND, result.getUnitOfMeasureKind());
        request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_TYPES, result.getUnitOfMeasureTypes());
        
        setupLocation(request, warehouseName, locationName);
        
        return mapping.findForward(ForwardConstants.DISPLAY);
    }
    
}
