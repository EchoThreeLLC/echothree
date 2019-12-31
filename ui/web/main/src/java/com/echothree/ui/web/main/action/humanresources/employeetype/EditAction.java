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

package com.echothree.ui.web.main.action.humanresources.employeetype;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.edit.EmployeeTypeEdit;
import com.echothree.control.user.employee.common.form.EditEmployeeTypeForm;
import com.echothree.control.user.employee.common.result.EditEmployeeTypeResult;
import com.echothree.control.user.employee.common.spec.EmployeeTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/EmployeeType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/EmployeeType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/employeetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String originalEmployeeTypeName = request.getParameter(ParameterConstants.ORIGINAL_EMPLOYEE_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditEmployeeTypeForm commandForm = EmployeeUtil.getHome().getEditEmployeeTypeForm();
                EmployeeTypeSpec spec = EmployeeUtil.getHome().getEmployeeTypeSpec();
                
                if(originalEmployeeTypeName == null)
                    originalEmployeeTypeName = actionForm.getOriginalEmployeeTypeName();
                
                commandForm.setSpec(spec);
                spec.setEmployeeTypeName(originalEmployeeTypeName);
                
                if(wasPost(request)) {
                    EmployeeTypeEdit edit = EmployeeUtil.getHome().getEmployeeTypeEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setEmployeeTypeName(actionForm.getEmployeeTypeName());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = EmployeeUtil.getHome().editEmployeeType(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditEmployeeTypeResult result = (EditEmployeeTypeResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = EmployeeUtil.getHome().editEmployeeType(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditEmployeeTypeResult result = (EditEmployeeTypeResult)executionResult.getResult();
                    
                    if(result != null) {
                        EmployeeTypeEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setOriginalEmployeeTypeName(edit.getEmployeeTypeName());
                            actionForm.setEmployeeTypeName(edit.getEmployeeTypeName());
                            actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            actionForm.setSortOrder(edit.getSortOrder());
                            actionForm.setDescription(edit.getDescription());
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
        
        return mapping.findForward(forwardKey);
    }
    
}