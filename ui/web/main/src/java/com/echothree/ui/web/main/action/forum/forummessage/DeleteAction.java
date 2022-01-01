// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.web.main.action.forum.forummessage;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.form.DeleteForumMessageForm;
import com.echothree.control.user.forum.common.form.GetForumForm;
import com.echothree.control.user.forum.common.form.GetForumMessageForm;
import com.echothree.control.user.forum.common.result.GetForumMessageResult;
import com.echothree.control.user.forum.common.result.GetForumResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Forum/ForumMessage/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ForumMessageDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Forum/ForumThread/Main", redirect = true),
        @SproutForward(name = "Form", path = "/forum/forummessage/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.ForumMessage.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setForumName(findParameter(request, ParameterConstants.FORUM_NAME, actionForm.getForumName()));
        actionForm.setForumMessageName(findParameter(request, ParameterConstants.FORUM_MESSAGE_NAME, actionForm.getForumMessageName()));
    }
    
    public void setupForumTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetForumForm commandForm = ForumUtil.getHome().getGetForumForm();
        
        commandForm.setForumName(actionForm.getForumName());
        
        CommandResult commandResult = ForumUtil.getHome().getForum(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetForumResult result = (GetForumResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.FORUM, result.getForum());
    }
    
    public void setupForumMessageTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetForumMessageForm commandForm = ForumUtil.getHome().getGetForumMessageForm();
        Set<String> commandOptions = new HashSet<>();
        
        commandForm.setForumMessageName(actionForm.getForumMessageName());
        
        commandOptions.add(ForumOptions.ForumMessageIncludeForumMessageParts);
        commandOptions.add(ForumOptions.ForumMessagePartIncludeString);
        commandForm.setOptions(commandOptions);
        
        CommandResult commandResult = ForumUtil.getHome().getForumMessage(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetForumMessageResult result = (GetForumMessageResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.FORUM_MESSAGE, result.getForumMessage());
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        setupForumTransfer(actionForm, request);
        setupForumMessageTransfer(actionForm, request);
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteForumMessageForm commandForm = ForumUtil.getHome().getDeleteForumMessageForm();

        commandForm.setForumMessageName(actionForm.getForumMessageName());

        return ForumUtil.getHome().deleteForumMessage(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.FORUM_NAME, actionForm.getForumName());
    }
    
}