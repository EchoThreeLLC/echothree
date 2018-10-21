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

package com.echothree.ui.web.main.action.uom.unitofmeasurekind;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.remote.edit.UnitOfMeasureKindEdit;
import com.echothree.control.user.uom.remote.form.EditUnitOfMeasureKindForm;
import com.echothree.control.user.uom.remote.result.EditUnitOfMeasureKindResult;
import com.echothree.control.user.uom.remote.spec.UnitOfMeasureKindSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.remote.command.ExecutionResult;
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
    path = "/UnitOfMeasure/UnitOfMeasureKind/Edit",
    mappingClass = SecureActionMapping.class,
    name = "UnitOfMeasureKindEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/UnitOfMeasure/UnitOfMeasureKind/Main", redirect = true),
        @SproutForward(name = "Form", path = "/uom/unitofmeasurekind/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String originalUnitOfMeasureKindName = request.getParameter(ParameterConstants.ORIGINAL_UNIT_OF_MEASURE_KIND_NAME);
        EditActionForm actionForm = (EditActionForm)form;
        EditUnitOfMeasureKindForm commandForm = UomUtil.getHome().getEditUnitOfMeasureKindForm();
        UnitOfMeasureKindSpec spec = UomUtil.getHome().getUnitOfMeasureKindSpec();
        
        if(originalUnitOfMeasureKindName == null)
            originalUnitOfMeasureKindName = actionForm.getOriginalUnitOfMeasureKindName();
        
        commandForm.setSpec(spec);
        spec.setUnitOfMeasureKindName(originalUnitOfMeasureKindName);
        
        if(wasPost(request)) {
            UnitOfMeasureKindEdit edit = UomUtil.getHome().getUnitOfMeasureKindEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setUnitOfMeasureKindName(actionForm.getUnitOfMeasureKindName());
            edit.setFractionDigits(actionForm.getFractionDigits());
            edit.setIsDefault(actionForm.getIsDefault().toString());
            edit.setSortOrder(actionForm.getSortOrder());
            edit.setDescription(actionForm.getDescription());
            
            CommandResult commandResult = UomUtil.getHome().editUnitOfMeasureKind(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditUnitOfMeasureKindResult result = (EditUnitOfMeasureKindResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = UomUtil.getHome().editUnitOfMeasureKind(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditUnitOfMeasureKindResult result = (EditUnitOfMeasureKindResult)executionResult.getResult();
            
            if(result != null) {
                UnitOfMeasureKindEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setOriginalUnitOfMeasureKindName(edit.getUnitOfMeasureKindName());
                    actionForm.setUnitOfMeasureKindName(edit.getUnitOfMeasureKindName());
                    actionForm.setFractionDigits(edit.getFractionDigits());
                    actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                    actionForm.setSortOrder(edit.getSortOrder());
                    actionForm.setDescription(edit.getDescription());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}