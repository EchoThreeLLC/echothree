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

package com.echothree.ui.web.main.action.advertising.use;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.remote.edit.UseEdit;
import com.echothree.control.user.offer.remote.form.EditUseForm;
import com.echothree.control.user.offer.remote.result.EditUseResult;
import com.echothree.control.user.offer.remote.spec.UseSpec;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Advertising/Use/Edit",
    mappingClass = SecureActionMapping.class,
    name = "UseEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/Use/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/use/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String originalUseName = request.getParameter(ParameterConstants.ORIGINAL_USE_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditUseForm commandForm = OfferUtil.getHome().getEditUseForm();
                UseSpec spec = OfferUtil.getHome().getUseSpec();
                
                if(originalUseName == null)
                    originalUseName = actionForm.getOriginalUseName();
                
                commandForm.setSpec(spec);
                spec.setUseName(originalUseName);
                
                if(wasPost(request)) {
                    UseEdit edit = OfferUtil.getHome().getUseEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setUseName(actionForm.getUseName());
                    edit.setUseTypeName(actionForm.getUseTypeChoice());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = OfferUtil.getHome().editUse(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditUseResult result = (EditUseResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = OfferUtil.getHome().editUse(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditUseResult result = (EditUseResult)executionResult.getResult();
                    
                    if(result != null) {
                        UseEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setOriginalUseName(edit.getUseName());
                            actionForm.setUseName(edit.getUseName());
                            actionForm.setUseTypeChoice(edit.getUseTypeName());
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