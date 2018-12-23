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

package com.echothree.ui.web.main.action.returnpolicy.returnreasontype;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.edit.ReturnReasonTypeEdit;
import com.echothree.control.user.returnpolicy.common.form.EditReturnReasonTypeForm;
import com.echothree.control.user.returnpolicy.common.result.EditReturnReasonTypeResult;
import com.echothree.control.user.returnpolicy.common.spec.ReturnReasonTypeSpec;
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
    path = "/ReturnPolicy/ReturnReasonType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ReturnReasonTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ReturnPolicy/ReturnReasonType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/returnpolicy/returnreasontype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String returnKindName = request.getParameter(ParameterConstants.RETURN_KIND_NAME);
        String returnReasonName = request.getParameter(ParameterConstants.RETURN_REASON_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditReturnReasonTypeForm commandForm = ReturnPolicyUtil.getHome().getEditReturnReasonTypeForm();
                ReturnReasonTypeSpec spec = ReturnPolicyUtil.getHome().getReturnReasonTypeSpec();
                String returnTypeName = request.getParameter(ParameterConstants.RETURN_TYPE_NAME);
                
                if(returnKindName == null)
                    returnKindName = actionForm.getReturnKindName();
                if(returnReasonName == null)
                    returnReasonName = actionForm.getReturnReasonName();
                if(returnTypeName == null)
                    returnTypeName = actionForm.getReturnTypeName();
                
                commandForm.setSpec(spec);
                spec.setReturnKindName(returnKindName);
                spec.setReturnReasonName(returnReasonName);
                spec.setReturnTypeName(returnTypeName);
                
                if(wasPost(request)) {
                    ReturnReasonTypeEdit edit = ReturnPolicyUtil.getHome().getReturnReasonTypeEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    
                    CommandResult commandResult = ReturnPolicyUtil.getHome().editReturnReasonType(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditReturnReasonTypeResult result = (EditReturnReasonTypeResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = ReturnPolicyUtil.getHome().editReturnReasonType(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditReturnReasonTypeResult result = (EditReturnReasonTypeResult)executionResult.getResult();
                    
                    if(result != null) {
                        ReturnReasonTypeEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setReturnKindName(returnKindName);
                            actionForm.setReturnReasonName(returnReasonName);
                            actionForm.setReturnTypeName(returnTypeName);
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
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.RETURN_KIND_NAME, returnKindName);
            request.setAttribute(AttributeConstants.RETURN_REASON_NAME, returnReasonName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.RETURN_KIND_NAME, returnKindName);
            parameters.put(ParameterConstants.RETURN_REASON_NAME, returnReasonName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}