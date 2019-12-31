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

package com.echothree.ui.web.main.action.returnpolicy.returnreason;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.edit.ReturnReasonEdit;
import com.echothree.control.user.returnpolicy.common.form.EditReturnReasonForm;
import com.echothree.control.user.returnpolicy.common.result.EditReturnReasonResult;
import com.echothree.control.user.returnpolicy.common.spec.ReturnReasonSpec;
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
    path = "/ReturnPolicy/ReturnReason/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ReturnReasonEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ReturnPolicy/ReturnReason/Main", redirect = true),
        @SproutForward(name = "Form", path = "/returnpolicy/returnreason/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String returnKindName = request.getParameter(ParameterConstants.RETURN_KIND_NAME);
        String originalReturnReasonName = request.getParameter(ParameterConstants.ORIGINAL_RETURN_REASON_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditReturnReasonForm commandForm = ReturnPolicyUtil.getHome().getEditReturnReasonForm();
                ReturnReasonSpec spec = ReturnPolicyUtil.getHome().getReturnReasonSpec();
                
                if(returnKindName == null)
                    returnKindName = actionForm.getReturnKindName();
                if(originalReturnReasonName == null)
                    originalReturnReasonName = actionForm.getOriginalReturnReasonName();
                
                commandForm.setSpec(spec);
                spec.setReturnKindName(returnKindName);
                spec.setReturnReasonName(originalReturnReasonName);
                
                if(wasPost(request)) {
                    ReturnReasonEdit edit = ReturnPolicyUtil.getHome().getReturnReasonEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setReturnReasonName(actionForm.getReturnReasonName());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = ReturnPolicyUtil.getHome().editReturnReason(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditReturnReasonResult result = (EditReturnReasonResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = ReturnPolicyUtil.getHome().editReturnReason(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditReturnReasonResult result = (EditReturnReasonResult)executionResult.getResult();
                    
                    if(result != null) {
                        ReturnReasonEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setReturnKindName(returnKindName);
                            actionForm.setOriginalReturnReasonName(edit.getReturnReasonName());
                            actionForm.setReturnReasonName(edit.getReturnReasonName());
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
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.RETURN_KIND_NAME, returnKindName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.RETURN_KIND_NAME, returnKindName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}