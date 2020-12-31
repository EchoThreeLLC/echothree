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

package com.echothree.ui.web.main.action.content.contentpagearea;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.form.GetContentPageAreasForm;
import com.echothree.control.user.content.common.form.GetContentPageLayoutAreasForm;
import com.echothree.control.user.content.common.result.GetContentPageAreasResult;
import com.echothree.control.user.content.common.result.GetContentPageLayoutAreasResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
    path = "/Content/ContentPageArea/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/content/contentpagearea/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            GetContentPageAreasForm getContentPageAreasForm = ContentUtil.getHome().getGetContentPageAreasForm();
            GetContentPageLayoutAreasForm getContentPageLayoutAreasForm = ContentUtil.getHome().getGetContentPageLayoutAreasForm();
            String contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
            String contentSectionName = request.getParameter(ParameterConstants.CONTENT_SECTION_NAME);
            String contentPageName = request.getParameter(ParameterConstants.CONTENT_PAGE_NAME);
            String parentContentSectionName = request.getParameter(ParameterConstants.PARENT_CONTENT_SECTION_NAME);
            
            getContentPageAreasForm.setContentCollectionName(contentCollectionName);
            getContentPageAreasForm.setContentSectionName(contentSectionName);
            getContentPageAreasForm.setContentPageName(contentPageName);
            
            CommandResult commandResult = ContentUtil.getHome().getContentPageAreas(getUserVisitPK(request), getContentPageAreasForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetContentPageAreasResult getContentPageAreasResult = (GetContentPageAreasResult)executionResult.getResult();
            
            getContentPageLayoutAreasForm.setContentCollectionName(contentCollectionName);
            getContentPageLayoutAreasForm.setContentSectionName(contentSectionName);
            getContentPageLayoutAreasForm.setContentPageName(contentPageName);
            
            commandResult = ContentUtil.getHome().getContentPageLayoutAreas(getUserVisitPK(request), getContentPageLayoutAreasForm);
            executionResult = commandResult.getExecutionResult();
            GetContentPageLayoutAreasResult getContentPageLayoutAreasResult = (GetContentPageLayoutAreasResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            request.setAttribute(AttributeConstants.CONTENT_SECTION_NAME, contentSectionName);
            request.setAttribute(AttributeConstants.CONTENT_PAGE_NAME, contentPageName);
            request.setAttribute(AttributeConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionName);
            request.setAttribute(AttributeConstants.CONTENT_PAGE_AREAS, getContentPageAreasResult.getContentPageAreas());
            request.setAttribute(AttributeConstants.CONTENT_PAGE_LAYOUT_AREAS, getContentPageLayoutAreasResult.getContentPageLayoutAreas());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}