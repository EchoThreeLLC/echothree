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

package com.echothree.ui.web.main.action.cancellationpolicy.cancellationpolicy;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyEdit;
import com.echothree.control.user.cancellationpolicy.common.form.EditCancellationPolicyForm;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationPolicyResult;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationPolicySpec;
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
    path = "/CancellationPolicy/CancellationPolicy/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CancellationPolicyEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/CancellationPolicy/CancellationPolicy/Main", redirect = true),
        @SproutForward(name = "Form", path = "/cancellationpolicy/cancellationpolicy/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String cancellationKindName = request.getParameter(ParameterConstants.CANCELLATION_KIND_NAME);
        String originalCancellationPolicyName = request.getParameter(ParameterConstants.ORIGINAL_CANCELLATION_POLICY_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditCancellationPolicyForm commandForm = CancellationPolicyUtil.getHome().getEditCancellationPolicyForm();
                CancellationPolicySpec spec = CancellationPolicyUtil.getHome().getCancellationPolicySpec();
                
                if(cancellationKindName == null)
                    cancellationKindName = actionForm.getCancellationKindName();
                if(originalCancellationPolicyName == null)
                    originalCancellationPolicyName = actionForm.getOriginalCancellationPolicyName();
                
                commandForm.setSpec(spec);
                spec.setCancellationKindName(cancellationKindName);
                spec.setCancellationPolicyName(originalCancellationPolicyName);
                
                if(wasPost(request)) {
                    CancellationPolicyEdit edit = CancellationPolicyUtil.getHome().getCancellationPolicyEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setCancellationPolicyName(actionForm.getCancellationPolicyName());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());
                    edit.setPolicyMimeTypeName(actionForm.getPolicyMimeTypeChoice());
                    edit.setPolicy(actionForm.getPolicy());
                    
                    CommandResult commandResult = CancellationPolicyUtil.getHome().editCancellationPolicy(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditCancellationPolicyResult result = (EditCancellationPolicyResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = CancellationPolicyUtil.getHome().editCancellationPolicy(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditCancellationPolicyResult result = (EditCancellationPolicyResult)executionResult.getResult();
                    
                    if(result != null) {
                        CancellationPolicyEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setCancellationKindName(cancellationKindName);
                            actionForm.setOriginalCancellationPolicyName(edit.getCancellationPolicyName());
                            actionForm.setCancellationPolicyName(edit.getCancellationPolicyName());
                            actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            actionForm.setSortOrder(edit.getSortOrder());
                            actionForm.setDescription(edit.getDescription());
                            actionForm.setPolicyMimeTypeChoice(edit.getPolicyMimeTypeName());
                            actionForm.setPolicy(edit.getPolicy());
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
            request.setAttribute(AttributeConstants.CANCELLATION_KIND_NAME, cancellationKindName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.CANCELLATION_KIND_NAME, cancellationKindName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}