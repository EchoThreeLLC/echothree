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

package com.echothree.ui.web.main.action.forum.forumgroup;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.form.GetForumGroupForm;
import com.echothree.control.user.forum.common.result.GetForumGroupResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.control.forum.common.transfer.ForumGroupTransfer;
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
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Forum/ForumGroup/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/forum/forumgroup/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetForumGroupForm commandForm = ForumUtil.getHome().getGetForumGroupForm();
        String forumGroupName = request.getParameter(ParameterConstants.FORUM_GROUP_NAME);
        
        commandForm.setForumGroupName(forumGroupName);
        
        Set<String> options = new HashSet<>();
        options.add(ForumOptions.ForumGroupIncludeEntityAttributeGroups);
        options.add(ForumOptions.ForumGroupIncludeTagScopes);
        options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        options.add(CoreOptions.EntityAttributeIncludeValue);
        options.add(CoreOptions.EntityStringAttributeIncludeString);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        commandForm.setOptions(options);

        CommandResult commandResult = ForumUtil.getHome().getForumGroup(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetForumGroupResult result = (GetForumGroupResult)executionResult.getResult();
        ForumGroupTransfer forumGroup = result.getForumGroup();
        
        if(forumGroup == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.FORUM_GROUP, forumGroup);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
