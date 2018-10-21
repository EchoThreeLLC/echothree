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

package com.echothree.ui.web.main.action.content.contentcatalog;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.remote.edit.ContentCatalogDescriptionEdit;
import com.echothree.control.user.content.remote.form.EditContentCatalogDescriptionForm;
import com.echothree.control.user.content.remote.result.EditContentCatalogDescriptionResult;
import com.echothree.control.user.content.remote.spec.ContentCatalogDescriptionSpec;
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
    path = "/Content/ContentCatalog/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ContentCatalogDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentCatalog/Description", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentcatalog/descriptionEdit.jsp")
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
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm descriptionEditActionForm = (DescriptionEditActionForm)form;
                EditContentCatalogDescriptionForm commandForm = ContentUtil.getHome().getEditContentCatalogDescriptionForm();
                ContentCatalogDescriptionSpec spec = ContentUtil.getHome().getContentCatalogDescriptionSpec();
                
                if(contentCollectionName == null)
                    contentCollectionName = descriptionEditActionForm.getContentCollectionName();
                if(contentCatalogName == null)
                    contentCatalogName = descriptionEditActionForm.getContentCatalogName();
                if(languageIsoName == null)
                    languageIsoName = descriptionEditActionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setContentCollectionName(contentCollectionName);
                spec.setContentCatalogName(contentCatalogName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    ContentCatalogDescriptionEdit edit = ContentUtil.getHome().getContentCatalogDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(descriptionEditActionForm.getDescription());
                    
                    CommandResult commandResult = ContentUtil.getHome().editContentCatalogDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = ContentUtil.getHome().editContentCatalogDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditContentCatalogDescriptionResult result = (EditContentCatalogDescriptionResult)executionResult.getResult();
                    ContentCatalogDescriptionEdit edit = result.getEdit();
                    
                    descriptionEditActionForm.setContentCollectionName(contentCollectionName);
                    descriptionEditActionForm.setContentCatalogName(contentCatalogName);
                    descriptionEditActionForm.setLanguageIsoName(languageIsoName);
                    descriptionEditActionForm.setDescription(edit.getDescription());
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
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            parameters.put(ParameterConstants.CONTENT_CATALOG_NAME, contentCatalogName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}