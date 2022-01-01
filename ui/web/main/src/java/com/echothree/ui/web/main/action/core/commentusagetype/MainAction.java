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

package com.echothree.ui.web.main.action.core.commentusagetype;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.form.GetCommentUsageTypesForm;
import com.echothree.control.user.comment.common.result.GetCommentUsageTypesResult;
import com.echothree.model.control.comment.common.transfer.CommentTypeTransfer;
import com.echothree.model.control.core.common.transfer.ComponentVendorTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
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
    path = "/Core/CommentUsageType/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/commentusagetype/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            String componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
            String entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
            String commentTypeName = request.getParameter(ParameterConstants.COMMENT_TYPE_NAME);
            GetCommentUsageTypesForm commandForm = CommentUtil.getHome().getGetCommentUsageTypesForm();
            
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setCommentTypeName(commentTypeName);
            
            CommandResult commandResult = CommentUtil.getHome().getCommentUsageTypes(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCommentUsageTypesResult result = (GetCommentUsageTypesResult)executionResult.getResult();
            ComponentVendorTransfer componentVendorTransfer = result.getComponentVendor();
            EntityTypeTransfer entityTypeTransfer = result.getEntityType();
            CommentTypeTransfer commentTypeTransfer = result.getCommentType();
            
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR, componentVendorTransfer);
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR_NAME, componentVendorTransfer.getComponentVendorName());
            request.setAttribute(AttributeConstants.ENTITY_TYPE, entityTypeTransfer);
            request.setAttribute(AttributeConstants.ENTITY_TYPE_NAME, entityTypeTransfer.getEntityTypeName());
            request.setAttribute(AttributeConstants.COMMENT_TYPE, commentTypeTransfer);
            request.setAttribute(AttributeConstants.COMMENT_TYPE_NAME, commentTypeTransfer.getCommentTypeName());
            request.setAttribute(AttributeConstants.COMMENT_USAGE_TYPES, result.getCommentUsageTypes());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}