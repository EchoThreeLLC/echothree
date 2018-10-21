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

package com.echothree.ui.web.main.action.uom.unitofmeasuretype;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.remote.edit.UnitOfMeasureTypeVolumeEdit;
import com.echothree.control.user.uom.remote.form.EditUnitOfMeasureTypeVolumeForm;
import com.echothree.control.user.uom.remote.result.EditUnitOfMeasureTypeVolumeResult;
import com.echothree.control.user.uom.remote.spec.UnitOfMeasureTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/UnitOfMeasure/UnitOfMeasureType/VolumeEdit",
    mappingClass = SecureActionMapping.class,
    name = "UnitOfMeasureTypeVolumeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/UnitOfMeasure/UnitOfMeasureType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/uom/unitofmeasuretype/volumeEdit.jsp")
    }
)
public class VolumeEditAction
        extends MainBaseAction<VolumeEditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, VolumeEditActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        String unitOfMeasureTypeName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME);
        
        if(forwardKey == null) {
            VolumeEditActionForm actionForm = form;
            EditUnitOfMeasureTypeVolumeForm commandForm = UomUtil.getHome().getEditUnitOfMeasureTypeVolumeForm();
            UnitOfMeasureTypeSpec spec = UomUtil.getHome().getUnitOfMeasureTypeSpec();
            
            if(unitOfMeasureKindName == null)
                unitOfMeasureKindName = actionForm.getUnitOfMeasureKindName();
            if(unitOfMeasureTypeName == null)
                unitOfMeasureTypeName = actionForm.getUnitOfMeasureTypeName();
            
            commandForm.setSpec(spec);
            spec.setUnitOfMeasureKindName(unitOfMeasureKindName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            
            if(wasPost(request)) {
                UnitOfMeasureTypeVolumeEdit edit = UomUtil.getHome().getUnitOfMeasureTypeVolumeEdit();
                
                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);
                
                edit.setHeightUnitOfMeasureTypeName(actionForm.getHeightUnitOfMeasureTypeChoice());
                edit.setHeight(actionForm.getHeight());
                edit.setWidthUnitOfMeasureTypeName(actionForm.getWidthUnitOfMeasureTypeChoice());
                edit.setWidth(actionForm.getWidth());
                edit.setDepthUnitOfMeasureTypeName(actionForm.getDepthUnitOfMeasureTypeChoice());
                edit.setDepth(actionForm.getDepth());
                
                CommandResult commandResult = UomUtil.getHome().editUnitOfMeasureTypeVolume(getUserVisitPK(request), commandForm);
                
                if(commandResult.hasErrors()) {
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    
                    if(executionResult != null) {
                        EditUnitOfMeasureTypeVolumeResult result = (EditUnitOfMeasureTypeVolumeResult)executionResult.getResult();
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                } else {
                    forwardKey = ForwardConstants.DISPLAY;
                }
            } else {
                commandForm.setEditMode(EditMode.LOCK);
                
                CommandResult commandResult = UomUtil.getHome().editUnitOfMeasureTypeVolume(getUserVisitPK(request), commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                EditUnitOfMeasureTypeVolumeResult result = (EditUnitOfMeasureTypeVolumeResult)executionResult.getResult();
                
                if(result != null) {
                    UnitOfMeasureTypeVolumeEdit edit = result.getEdit();
                    
                    if(edit != null) {
                        actionForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                        actionForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                        actionForm.setHeightUnitOfMeasureTypeChoice(edit.getHeightUnitOfMeasureTypeName());
                        actionForm.setHeight(edit.getHeight());
                        actionForm.setWidthUnitOfMeasureTypeChoice(edit.getWidthUnitOfMeasureTypeName());
                        actionForm.setWidth(edit.getWidth());
                        actionForm.setDepthUnitOfMeasureTypeChoice(edit.getDepthUnitOfMeasureTypeName());
                        actionForm.setDepth(edit.getDepth());
                    }
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            }
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_TYPE_NAME, unitOfMeasureTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, unitOfMeasureTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
