// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.content.contentpage;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.edit.ContentPageDescriptionEdit;
import com.echothree.control.user.content.common.form.EditContentPageDescriptionForm;
import com.echothree.control.user.content.common.result.EditContentPageDescriptionResult;
import com.echothree.control.user.content.common.spec.ContentPageDescriptionSpec;
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
    path = "/Content/ContentPage/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ContentPageDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentPage/Description", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentpage/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        String contentSectionName = request.getParameter(ParameterConstants.CONTENT_SECTION_NAME);
        String contentPageName = request.getParameter(ParameterConstants.CONTENT_PAGE_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        String parentContentSectionName = request.getParameter(ParameterConstants.PARENT_CONTENT_SECTION_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditContentPageDescriptionForm commandForm = ContentUtil.getHome().getEditContentPageDescriptionForm();
                ContentPageDescriptionSpec spec = ContentUtil.getHome().getContentPageDescriptionSpec();
                
                if(contentCollectionName == null)
                    contentCollectionName = actionForm.getContentCollectionName();
                if(contentSectionName == null)
                    contentSectionName = actionForm.getContentSectionName();
                if(contentPageName == null)
                    contentPageName = actionForm.getContentPageName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                if(parentContentSectionName == null)
                    parentContentSectionName = actionForm.getParentContentSectionName();
                
                commandForm.setSpec(spec);
                spec.setContentCollectionName(contentCollectionName);
                spec.setContentSectionName(contentSectionName);
                spec.setContentPageName(contentPageName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    ContentPageDescriptionEdit edit = ContentUtil.getHome().getContentPageDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = ContentUtil.getHome().editContentPageDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = ContentUtil.getHome().editContentPageDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditContentPageDescriptionResult result = (EditContentPageDescriptionResult)executionResult.getResult();
                    ContentPageDescriptionEdit edit = result.getEdit();
                    
                    actionForm.setContentCollectionName(contentCollectionName);
                    actionForm.setContentSectionName(contentSectionName);
                    actionForm.setContentPageName(contentPageName);
                    actionForm.setLanguageIsoName(languageIsoName);
                    actionForm.setParentContentSectionName(parentContentSectionName);
                    actionForm.setDescription(edit.getDescription());
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            request.setAttribute(AttributeConstants.CONTENT_SECTION_NAME, contentSectionName);
            request.setAttribute(AttributeConstants.CONTENT_PAGE_NAME, contentPageName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
            request.setAttribute(AttributeConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            parameters.put(ParameterConstants.CONTENT_SECTION_NAME, contentSectionName);
            parameters.put(ParameterConstants.CONTENT_PAGE_NAME, contentPageName);
            if(parentContentSectionName != null)
                parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}