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

package com.echothree.ui.web.main.action.uom.unitofmeasureequivalent;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.form.DeleteUnitOfMeasureEquivalentForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/UnitOfMeasure/UnitOfMeasureEquivalent/Delete",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/UnitOfMeasure/UnitOfMeasureEquivalent/Main", redirect = true)
    }
)
public class DeleteAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String returnKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        String fromUnitOfMeasureTypeName = request.getParameter(ParameterConstants.FROM_UNIT_OF_MEASURE_TYPE_NAME);
        String toUnitOfMeasureTypeName = request.getParameter(ParameterConstants.TO_UNIT_OF_MEASURE_TYPE_NAME);
        DeleteUnitOfMeasureEquivalentForm commandForm = UomUtil.getHome().getDeleteUnitOfMeasureEquivalentForm();
        
        commandForm.setUnitOfMeasureKindName(returnKindName);
        commandForm.setFromUnitOfMeasureTypeName(fromUnitOfMeasureTypeName);
        commandForm.setToUnitOfMeasureTypeName(toUnitOfMeasureTypeName);
        
        UomUtil.getHome().deleteUnitOfMeasureEquivalent(getUserVisitPK(request), commandForm);
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(ForwardConstants.DISPLAY));
        Map<String, String> parameters = new HashMap<>(1);
        
        parameters.put(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME, returnKindName);
        customActionForward.setParameters(parameters);
        
        return customActionForward;
    }
    
}
