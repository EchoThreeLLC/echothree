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

package com.echothree.ui.web.main.action.core.commentusagetype;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.result.GetCommentUsageTypeResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/CommentUsageType/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/commentusagetype/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = CommentUtil.getHome().getGetCommentUsageTypeForm();
        var componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
        var entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
        var commentTypeName = request.getParameter(ParameterConstants.COMMENT_TYPE_NAME);
        var commentUsageTypeName = request.getParameter(ParameterConstants.COMMENT_USAGE_TYPE_NAME);

        commandForm.setEntityTypeName(entityTypeName);
        commandForm.setComponentVendorName(componentVendorName);
        commandForm.setCommentTypeName(commentTypeName);
        commandForm.setCommentUsageTypeName(commentUsageTypeName);

        var commandResult = CommentUtil.getHome().getCommentUsageType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCommentUsageTypeResult)executionResult.getResult();
        var commentUsageType = result.getCommentUsageType();

        if(commentUsageType == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.COMMENT_USAGE_TYPE, commentUsageType);
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }

}
