// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.content.contentcategory;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.edit.ContentCategoryDescriptionEdit;
import com.echothree.control.user.content.common.form.EditContentCategoryDescriptionForm;
import com.echothree.control.user.content.common.result.EditContentCategoryDescriptionResult;
import com.echothree.control.user.content.common.spec.ContentCategoryDescriptionSpec;
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
    path = "/Content/ContentCategory/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ContentCategoryDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentCategory/Description", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentcategory/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        String contentCatalogName = request.getParameter(ParameterConstants.CONTENT_CATALOG_NAME);
        String contentCategoryName = request.getParameter(ParameterConstants.CONTENT_CATEGORY_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        String parentContentCategoryName = request.getParameter(ParameterConstants.PARENT_CONTENT_CATEGORY_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditContentCategoryDescriptionForm commandForm = ContentUtil.getHome().getEditContentCategoryDescriptionForm();
                ContentCategoryDescriptionSpec spec = ContentUtil.getHome().getContentCategoryDescriptionSpec();
                
                if(contentCollectionName == null)
                    contentCollectionName = actionForm.getContentCollectionName();
                if(contentCatalogName == null)
                    contentCatalogName = actionForm.getContentCatalogName();
                if(contentCategoryName == null)
                    contentCategoryName = actionForm.getContentCategoryName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                if(parentContentCategoryName == null)
                    parentContentCategoryName = actionForm.getParentContentCategoryName();
                
                commandForm.setSpec(spec);
                spec.setContentCollectionName(contentCollectionName);
                spec.setContentCatalogName(contentCatalogName);
                spec.setContentCategoryName(contentCategoryName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    ContentCategoryDescriptionEdit edit = ContentUtil.getHome().getContentCategoryDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = ContentUtil.getHome().editContentCategoryDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = ContentUtil.getHome().editContentCategoryDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditContentCategoryDescriptionResult result = (EditContentCategoryDescriptionResult)executionResult.getResult();
                    ContentCategoryDescriptionEdit edit = result.getEdit();
                    
                    actionForm.setContentCollectionName(contentCollectionName);
                    actionForm.setContentCatalogName(contentCatalogName);
                    actionForm.setContentCategoryName(contentCategoryName);
                    actionForm.setLanguageIsoName(languageIsoName);
                    actionForm.setParentContentCategoryName(parentContentCategoryName);
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
            request.setAttribute(AttributeConstants.CONTENT_CATALOG_NAME, contentCatalogName);
            request.setAttribute(AttributeConstants.CONTENT_CATEGORY_NAME, contentCategoryName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
            if(parentContentCategoryName != null)
                request.setAttribute(AttributeConstants.PARENT_CONTENT_CATEGORY_NAME, parentContentCategoryName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(4);
            
            parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            parameters.put(ParameterConstants.CONTENT_CATALOG_NAME, contentCatalogName);
            parameters.put(ParameterConstants.CONTENT_CATEGORY_NAME, contentCategoryName);
            if(parentContentCategoryName != null)
                parameters.put(ParameterConstants.PARENT_CONTENT_CATEGORY_NAME, parentContentCategoryName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}