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

package com.echothree.ui.web.main.action.chain.letter;

import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.control.user.letter.remote.edit.LetterEdit;
import com.echothree.control.user.letter.remote.form.EditLetterForm;
import com.echothree.control.user.letter.remote.result.EditLetterResult;
import com.echothree.control.user.letter.remote.spec.LetterSpec;
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
    path = "/Chain/Letter/Edit",
    mappingClass = SecureActionMapping.class,
    name = "LetterEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/Letter/Main", redirect = true),
        @SproutForward(name = "Form", path = "/chain/letter/edit.jsp")
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
        String originalLetterName = request.getParameter(ParameterConstants.ORIGINAL_LETTER_NAME);
        EditLetterForm commandForm = LetterUtil.getHome().getEditLetterForm();
        LetterSpec spec = LetterUtil.getHome().getLetterSpec();
        
        if(chainKindName == null) {
            chainKindName = actionForm.getChainKindName();
        }
        
        if(chainTypeName == null) {
            chainTypeName = actionForm.getChainTypeName();
        }
        
        if(originalLetterName == null) {
            originalLetterName = actionForm.getOriginalLetterName();
        }
        
        commandForm.setSpec(spec);
        spec.setLetterName(originalLetterName);
        spec.setChainKindName(chainKindName);
        spec.setChainTypeName(chainTypeName);
        
        if(wasPost(request)) {
            LetterEdit edit = LetterUtil.getHome().getLetterEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setLetterName(actionForm.getLetterName());
            edit.setLetterSourceName(actionForm.getLetterSourceChoice());
            edit.setContactListName(actionForm.getContactListChoice());
            edit.setIsDefault(actionForm.getIsDefault().toString());
            edit.setSortOrder(actionForm.getSortOrder());
            edit.setDescription(actionForm.getDescription());
            
            CommandResult commandResult = LetterUtil.getHome().editLetter(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditLetterResult result = (EditLetterResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = LetterUtil.getHome().editLetter(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditLetterResult result = (EditLetterResult)executionResult.getResult();
            
            if(result != null) {
                LetterEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setChainKindName(chainKindName);
                    actionForm.setChainTypeName(chainTypeName);
                    actionForm.setOriginalLetterName(edit.getLetterName());
                    actionForm.setLetterName(edit.getLetterName());
                    actionForm.setLetterSourceChoice(edit.getLetterSourceName());
                    actionForm.setContactListChoice(edit.getContactListName());
                    actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                    actionForm.setSortOrder(edit.getSortOrder());
                    actionForm.setDescription(edit.getDescription());
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
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.CHAIN_KIND_NAME, chainKindName);
            parameters.put(ParameterConstants.CHAIN_TYPE_NAME, chainTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}