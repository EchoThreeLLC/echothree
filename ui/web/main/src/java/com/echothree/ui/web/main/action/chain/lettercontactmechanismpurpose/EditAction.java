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

package com.echothree.ui.web.main.action.chain.lettercontactmechanismpurpose;

import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.control.user.letter.common.edit.LetterContactMechanismPurposeEdit;
import com.echothree.control.user.letter.common.form.EditLetterContactMechanismPurposeForm;
import com.echothree.control.user.letter.common.result.EditLetterContactMechanismPurposeResult;
import com.echothree.control.user.letter.common.spec.LetterContactMechanismPurposeSpec;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Chain/LetterContactMechanismPurpose/Edit",
    mappingClass = SecureActionMapping.class,
    name = "LetterContactMechanismPurposeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/LetterContactMechanismPurpose/Main", redirect = true),
        @SproutForward(name = "Form", path = "/chain/lettercontactmechanismpurpose/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String chainKindName = request.getParameter(ParameterConstants.CHAIN_KIND_NAME);
        String chainTypeName = request.getParameter(ParameterConstants.CHAIN_TYPE_NAME);
        String letterName = request.getParameter(ParameterConstants.LETTER_NAME);
        String priority = request.getParameter(ParameterConstants.PRIORITY);
        EditLetterContactMechanismPurposeForm commandForm = LetterUtil.getHome().getEditLetterContactMechanismPurposeForm();
        LetterContactMechanismPurposeSpec spec = LetterUtil.getHome().getLetterContactMechanismPurposeSpec();
        
        if(chainKindName == null) {
            chainKindName = actionForm.getChainKindName();
        }
        
        if(chainTypeName == null) {
            chainTypeName = actionForm.getChainTypeName();
        }
        
        if(letterName == null) {
            letterName = actionForm.getLetterName();
        }
        
        if(priority == null) {
            priority = actionForm.getPriority();
        }
        
        commandForm.setSpec(spec);
        spec.setChainKindName(chainKindName);
        spec.setChainTypeName(chainTypeName);
        spec.setLetterName(letterName);
        spec.setPriority(priority);
        
        if(wasPost(request)) {
            LetterContactMechanismPurposeEdit edit = LetterUtil.getHome().getLetterContactMechanismPurposeEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setContactMechanismPurposeName(actionForm.getContactMechanismPurposeChoice());
            
            CommandResult commandResult = LetterUtil.getHome().editLetterContactMechanismPurpose(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditLetterContactMechanismPurposeResult result = (EditLetterContactMechanismPurposeResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = LetterUtil.getHome().editLetterContactMechanismPurpose(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditLetterContactMechanismPurposeResult result = (EditLetterContactMechanismPurposeResult)executionResult.getResult();
            
            if(result != null) {
                LetterContactMechanismPurposeEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setChainKindName(chainKindName);
                    actionForm.setChainTypeName(chainTypeName);
                    actionForm.setLetterName(letterName);
                    actionForm.setPriority(priority);
                    actionForm.setContactMechanismPurposeChoice(edit.getContactMechanismPurposeName());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CHAIN_KIND_NAME, chainKindName);
            request.setAttribute(AttributeConstants.CHAIN_TYPE_NAME, chainTypeName);
            request.setAttribute(AttributeConstants.LETTER_NAME, letterName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.CHAIN_KIND_NAME, chainKindName);
            parameters.put(ParameterConstants.CHAIN_TYPE_NAME, chainTypeName);
            parameters.put(ParameterConstants.LETTER_NAME, letterName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}