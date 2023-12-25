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

package com.echothree.ui.web.main.action.uom.unitofmeasurekind;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.form.CreateUnitOfMeasureKindDescriptionForm;
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
    path = "/UnitOfMeasure/UnitOfMeasureKind/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "UnitOfMeasureKindDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/UnitOfMeasure/UnitOfMeasureKind/Description", redirect = true),
        @SproutForward(name = "Form", path = "/uom/unitofmeasurekind/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        
        try {
            if(forwardKey == null) {
                if(wasPost(request)) {
                    DescriptionAddActionForm descriptionAddActionForm = (DescriptionAddActionForm)form;
                    
                    CreateUnitOfMeasureKindDescriptionForm createUnitOfMeasureKindDescriptionForm = UomUtil.getHome().getCreateUnitOfMeasureKindDescriptionForm();
                    
                    if(unitOfMeasureKindName == null)
                        unitOfMeasureKindName = descriptionAddActionForm.getUnitOfMeasureKindName();
                    
                    createUnitOfMeasureKindDescriptionForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                    createUnitOfMeasureKindDescriptionForm.setLanguageIsoName(descriptionAddActionForm.getLanguageChoice());
                    createUnitOfMeasureKindDescriptionForm.setDescription(descriptionAddActionForm.getDescription());
                    
                    CommandResult commandResult = UomUtil.getHome().createUnitOfMeasureKindDescription(getUserVisitPK(request), createUnitOfMeasureKindDescriptionForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else
                    forwardKey = ForwardConstants.FORM;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM) || forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            customActionForward.setParameters(parameters);
            
            request.setAttribute("unitOfMeasureKindName", unitOfMeasureKindName); // TODO: not encoded
        }
        
        return customActionForward;
    }
    
}