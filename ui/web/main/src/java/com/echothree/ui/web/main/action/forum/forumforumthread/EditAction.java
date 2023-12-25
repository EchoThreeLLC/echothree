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

package com.echothree.ui.web.main.action.forum.forumforumthread;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.edit.ForumForumThreadEdit;
import com.echothree.control.user.forum.common.form.EditForumForumThreadForm;
import com.echothree.control.user.forum.common.result.EditForumForumThreadResult;
import com.echothree.control.user.forum.common.spec.ForumForumThreadSpec;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Forum/ForumForumThread/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ForumForumThreadEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Forum/ForumForumThread/Main", redirect = true),
        @SproutForward(name = "Form", path = "/forum/forumforumthread/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String forumThreadName = request.getParameter(ParameterConstants.FORUM_THREAD_NAME);
        String forumName = request.getParameter(ParameterConstants.FORUM_NAME);
        EditActionForm actionForm = (EditActionForm)form;
        EditForumForumThreadForm commandForm = ForumUtil.getHome().getEditForumForumThreadForm();
        ForumForumThreadSpec spec = ForumUtil.getHome().getForumForumThreadSpec();
        
        if(forumName == null)
            forumName = actionForm.getForumName();
        if(forumThreadName == null)
            forumThreadName = actionForm.getForumThreadName();
        
        commandForm.setSpec(spec);
        spec.setForumName(forumName);
        spec.setForumThreadName(forumThreadName);
        
        if(wasPost(request)) {
            ForumForumThreadEdit edit = ForumUtil.getHome().getForumForumThreadEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setIsDefault(actionForm.getIsDefault().toString());
            edit.setSortOrder(actionForm.getSortOrder());
            
            CommandResult commandResult = ForumUtil.getHome().editForumForumThread(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditForumForumThreadResult result = (EditForumForumThreadResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = ForumUtil.getHome().editForumForumThread(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditForumForumThreadResult result = (EditForumForumThreadResult)executionResult.getResult();
            
            if(result != null) {
                ForumForumThreadEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setForumName(forumName);
                    actionForm.setForumThreadName(forumThreadName);
                    actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                    actionForm.setSortOrder(edit.getSortOrder());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
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