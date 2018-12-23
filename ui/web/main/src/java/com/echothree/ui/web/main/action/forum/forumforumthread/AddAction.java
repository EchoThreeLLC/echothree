// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.forum.forumforumthread;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.form.CreateForumForumThreadForm;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Forum/ForumForumThread/Add",
    mappingClass = SecureActionMapping.class,
    name = "ForumForumThreadAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Forum/ForumForumThread/Main", redirect = true),
        @SproutForward(name = "Form", path = "/forum/forumforumthread/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String forumThreadName = request.getParameter(ParameterConstants.FORUM_THREAD_NAME);
        AddActionForm actionForm = (AddActionForm)form;
        
        if(forumThreadName == null)
            forumThreadName = actionForm.getForumThreadName();
        
        if(wasPost(request)) {
            CreateForumForumThreadForm commandForm = ForumUtil.getHome().getCreateForumForumThreadForm();
            
            commandForm.setForumName(actionForm.getForumChoice());
            commandForm.setForumThreadName(forumThreadName);
            commandForm.setIsDefault(actionForm.getIsDefault().toString());
            commandForm.setSortOrder(actionForm.getSortOrder());
            
            CommandResult commandResult = ForumUtil.getHome().createForumForumThread(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setForumThreadName(forumThreadName);
            actionForm.setSortOrder("1");
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.FORUM_THREAD_NAME, forumThreadName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.FORUM_THREAD_NAME, forumThreadName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
