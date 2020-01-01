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

package com.echothree.ui.web.main.action.chain.lettersource;

import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.control.user.letter.common.edit.LetterSourceEdit;
import com.echothree.control.user.letter.common.form.EditLetterSourceForm;
import com.echothree.control.user.letter.common.result.EditLetterSourceResult;
import com.echothree.control.user.letter.common.spec.LetterSourceSpec;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Chain/LetterSource/Edit",
    mappingClass = SecureActionMapping.class,
    name = "LetterSourceEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/LetterSource/Main", redirect = true),
        @SproutForward(name = "Form", path = "/chain/lettersource/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String originalLetterSourceName = request.getParameter(ParameterConstants.ORIGINAL_LETTER_SOURCE_NAME);
        EditLetterSourceForm commandForm = LetterUtil.getHome().getEditLetterSourceForm();
        LetterSourceSpec spec = LetterUtil.getHome().getLetterSourceSpec();
        
        if(originalLetterSourceName == null) {
            originalLetterSourceName = actionForm.getOriginalLetterSourceName();
        }
        
        commandForm.setSpec(spec);
        spec.setLetterSourceName(originalLetterSourceName);
        
        if(wasPost(request)) {
            LetterSourceEdit edit = LetterUtil.getHome().getLetterSourceEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setLetterSourceName(actionForm.getLetterSourceName());
            edit.setEmailAddressContactMechanismName(actionForm.getEmailAddressContactMechanismChoice());
            edit.setPostalAddressContactMechanismName(actionForm.getPostalAddressContactMechanismChoice());
            edit.setLetterSourceContactMechanismName(actionForm.getLetterSourceContactMechanismChoice());
            edit.setIsDefault(actionForm.getIsDefault().toString());
            edit.setSortOrder(actionForm.getSortOrder());
            edit.setDescription(actionForm.getDescription());
            
            CommandResult commandResult = LetterUtil.getHome().editLetterSource(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditLetterSourceResult result = (EditLetterSourceResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = LetterUtil.getHome().editLetterSource(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditLetterSourceResult result = (EditLetterSourceResult)executionResult.getResult();
            
            if(result != null) {
                LetterSourceEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setPartyName(getUserSession(request).getPartyRelationship().getFromParty().getPartyName());
                    actionForm.setOriginalLetterSourceName(edit.getLetterSourceName());
                    actionForm.setLetterSourceName(edit.getLetterSourceName());
                    actionForm.setEmailAddressContactMechanismChoice(edit.getEmailAddressContactMechanismName());
                    actionForm.setPostalAddressContactMechanismChoice(edit.getPostalAddressContactMechanismName());
                    actionForm.setLetterSourceContactMechanismChoice(edit.getLetterSourceContactMechanismName());
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