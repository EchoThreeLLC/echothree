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

package com.echothree.ui.web.main.action.forum.forum;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.form.CreateForumDescriptionForm;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
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
    path = "/Forum/Forum/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "ForumDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Forum/Forum/Description", redirect = true),
        @SproutForward(name = "Form", path = "/forum/forum/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String forumName = request.getParameter(ParameterConstants.FORUM_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionAddActionForm actionForm = (DescriptionAddActionForm)form;
                
                if(wasPost(request)) {
                    CreateForumDescriptionForm commandForm = ForumUtil.getHome().getCreateForumDescriptionForm();
                    
                    if(forumName == null)
                        forumName = actionForm.getForumName();
                    
                    commandForm.setForumName(forumName);
                    commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
                    commandForm.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = ForumUtil.getHome().createForumDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setForumName(forumName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.FORUM_NAME, forumName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            parameters.put(ParameterConstants.FORUM_NAME, forumName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}