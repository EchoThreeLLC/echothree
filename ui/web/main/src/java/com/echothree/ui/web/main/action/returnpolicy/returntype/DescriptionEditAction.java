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

package com.echothree.ui.web.main.action.returnpolicy.returntype;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.edit.ReturnTypeDescriptionEdit;
import com.echothree.control.user.returnpolicy.common.form.EditReturnTypeDescriptionForm;
import com.echothree.control.user.returnpolicy.common.result.EditReturnTypeDescriptionResult;
import com.echothree.control.user.returnpolicy.common.spec.ReturnTypeDescriptionSpec;
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
    path = "/ReturnPolicy/ReturnType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ReturnTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ReturnPolicy/ReturnType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/returnpolicy/returntype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String returnKindName = request.getParameter(ParameterConstants.RETURN_KIND_NAME);
        String returnTypeName = request.getParameter(ParameterConstants.RETURN_TYPE_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditReturnTypeDescriptionForm commandForm = ReturnPolicyUtil.getHome().getEditReturnTypeDescriptionForm();
                ReturnTypeDescriptionSpec spec = ReturnPolicyUtil.getHome().getReturnTypeDescriptionSpec();
                
                if(returnKindName == null)
                    returnKindName = actionForm.getReturnKindName();
                if(returnTypeName == null)
                    returnTypeName = actionForm.getReturnTypeName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setReturnKindName(returnKindName);
                spec.setReturnTypeName(returnTypeName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    ReturnTypeDescriptionEdit edit = ReturnPolicyUtil.getHome().getReturnTypeDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = ReturnPolicyUtil.getHome().editReturnTypeDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditReturnTypeDescriptionResult result = (EditReturnTypeDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = ReturnPolicyUtil.getHome().editReturnTypeDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditReturnTypeDescriptionResult result = (EditReturnTypeDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        ReturnTypeDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setReturnKindName(returnKindName);
                            actionForm.setReturnTypeName(returnTypeName);
                            actionForm.setLanguageIsoName(languageIsoName);
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
            request.setAttribute(AttributeConstants.RETURN_TYPE_NAME, returnTypeName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.RETURN_KIND_NAME, returnKindName);
            parameters.put(ParameterConstants.RETURN_TYPE_NAME, returnTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}