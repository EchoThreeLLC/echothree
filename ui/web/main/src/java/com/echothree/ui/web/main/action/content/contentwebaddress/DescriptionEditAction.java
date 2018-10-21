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

package com.echothree.ui.web.main.action.content.contentwebaddress;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.remote.edit.ContentWebAddressDescriptionEdit;
import com.echothree.control.user.content.remote.form.EditContentWebAddressDescriptionForm;
import com.echothree.control.user.content.remote.result.EditContentWebAddressDescriptionResult;
import com.echothree.control.user.content.remote.spec.ContentWebAddressDescriptionSpec;
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
    path = "/Content/ContentWebAddress/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ContentWebAddressDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentWebAddress/Description", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentwebaddress/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String contentWebAddressName = request.getParameter(ParameterConstants.CONTENT_WEB_ADDRESS_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditContentWebAddressDescriptionForm commandForm = ContentUtil.getHome().getEditContentWebAddressDescriptionForm();
                ContentWebAddressDescriptionSpec spec = ContentUtil.getHome().getContentWebAddressDescriptionSpec();
                
                if(contentWebAddressName == null)
                    contentWebAddressName = actionForm.getContentWebAddressName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setContentWebAddressName(contentWebAddressName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    ContentWebAddressDescriptionEdit edit = ContentUtil.getHome().getContentWebAddressDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = ContentUtil.getHome().editContentWebAddressDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = ContentUtil.getHome().editContentWebAddressDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditContentWebAddressDescriptionResult result = (EditContentWebAddressDescriptionResult)executionResult.getResult();
                    ContentWebAddressDescriptionEdit edit = result.getEdit();
                    
                    actionForm.setContentWebAddressName(contentWebAddressName);
                    actionForm.setLanguageIsoName(languageIsoName);
                    actionForm.setDescription(edit.getDescription());
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CONTENT_WEB_ADDRESS_NAME, contentWebAddressName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.CONTENT_WEB_ADDRESS_NAME, contentWebAddressName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}