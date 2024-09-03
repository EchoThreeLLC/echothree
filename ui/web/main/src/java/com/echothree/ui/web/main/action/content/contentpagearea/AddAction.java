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

package com.echothree.ui.web.main.action.content.contentpagearea;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentPageLayoutAreaResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Content/ContentPageArea/Add",
    mappingClass = SecureActionMapping.class,
    name = "ContentPageAreaAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentPageArea/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentpagearea/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<AddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        var contentSectionName = request.getParameter(ParameterConstants.CONTENT_SECTION_NAME);
        var contentPageName = request.getParameter(ParameterConstants.CONTENT_PAGE_NAME);
        var sortOrder = request.getParameter(ParameterConstants.SORT_ORDER);
        var parentContentSectionName = request.getParameter(ParameterConstants.PARENT_CONTENT_SECTION_NAME);

        if(wasPost(request)) {
            var commandForm = ContentUtil.getHome().getCreateContentPageAreaForm();

            if(contentCollectionName == null)
                contentCollectionName = actionForm.getContentCollectionName();
            if(contentSectionName == null)
                contentSectionName = actionForm.getContentSectionName();
            if(contentPageName == null)
                contentPageName = actionForm.getContentPageName();
            if(sortOrder == null)
                sortOrder = actionForm.getSortOrder();
            if(parentContentSectionName == null)
                parentContentSectionName = actionForm.getParentContentSectionName();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);
            commandForm.setSortOrder(sortOrder);
            commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
            commandForm.setMimeTypeName(actionForm.getMimeTypeChoice());
            commandForm.setDescription(actionForm.getDescription());
            commandForm.setContentPageAreaClob(actionForm.getContentPageAreaClob());
            commandForm.setContentPageAreaUrl(actionForm.getContentPageAreaUrl());

            var commandResult = ContentUtil.getHome().createContentPageArea(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setContentCollectionName(contentCollectionName);
            actionForm.setContentSectionName(contentSectionName);
            actionForm.setContentPageName(contentPageName);
            actionForm.setSortOrder(sortOrder);
            actionForm.setParentContentSectionName(parentContentSectionName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            var getContentPageLayoutAreaForm = ContentUtil.getHome().getGetContentPageLayoutAreaForm();

            getContentPageLayoutAreaForm.setContentCollectionName(contentCollectionName);
            getContentPageLayoutAreaForm.setContentSectionName(contentSectionName);
            getContentPageLayoutAreaForm.setContentPageName(contentPageName);
            getContentPageLayoutAreaForm.setSortOrder(sortOrder);

            var commandResult = ContentUtil.getHome().getContentPageLayoutArea(getUserVisitPK(request), getContentPageLayoutAreaForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContentPageLayoutAreaResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            request.setAttribute(AttributeConstants.CONTENT_SECTION_NAME, contentSectionName);
            request.setAttribute(AttributeConstants.CONTENT_PAGE_NAME, contentPageName);
            request.setAttribute(AttributeConstants.SORT_ORDER, sortOrder);
            request.setAttribute(AttributeConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionName);
            request.setAttribute(AttributeConstants.CONTENT_PAGE_LAYOUT_AREA, result.getContentPageLayoutArea());
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(5);
            
            parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            parameters.put(ParameterConstants.CONTENT_SECTION_NAME, contentSectionName);
            parameters.put(ParameterConstants.CONTENT_PAGE_NAME, contentPageName);
            parameters.put(ParameterConstants.SORT_ORDER, sortOrder);
            parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
