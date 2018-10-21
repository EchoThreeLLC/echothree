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

package com.echothree.ui.web.main.action.configuration.recoveryquestion;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.remote.edit.RecoveryQuestionDescriptionEdit;
import com.echothree.control.user.user.remote.form.EditRecoveryQuestionDescriptionForm;
import com.echothree.control.user.user.remote.result.EditRecoveryQuestionDescriptionResult;
import com.echothree.control.user.user.remote.spec.RecoveryQuestionDescriptionSpec;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/RecoveryQuestion/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "RecoveryQuestionDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/RecoveryQuestion/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/recoveryquestion/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String recoveryQuestionName = request.getParameter(ParameterConstants.RECOVERY_QUESTION_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditRecoveryQuestionDescriptionForm commandForm = UserUtil.getHome().getEditRecoveryQuestionDescriptionForm();
                RecoveryQuestionDescriptionSpec spec = UserUtil.getHome().getRecoveryQuestionDescriptionSpec();
                
                if(recoveryQuestionName == null)
                    recoveryQuestionName = actionForm.getRecoveryQuestionName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setRecoveryQuestionName(recoveryQuestionName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    RecoveryQuestionDescriptionEdit edit = UserUtil.getHome().getRecoveryQuestionDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = UserUtil.getHome().editRecoveryQuestionDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditRecoveryQuestionDescriptionResult result = (EditRecoveryQuestionDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = UserUtil.getHome().editRecoveryQuestionDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditRecoveryQuestionDescriptionResult result = (EditRecoveryQuestionDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        RecoveryQuestionDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setRecoveryQuestionName(recoveryQuestionName);
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
            request.setAttribute(AttributeConstants.RECOVERY_QUESTION_NAME, recoveryQuestionName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.RECOVERY_QUESTION_NAME, recoveryQuestionName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}