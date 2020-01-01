// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.comment.common.form.CreateCommentUsageTypeDescriptionForm;
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
    path = "/Core/CommentUsageType/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "CoreCommentUsageTypeDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/CommentUsageType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/commentusagetype/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
        String entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
        String commentTypeName = request.getParameter(ParameterConstants.COMMENT_TYPE_NAME);
        String commentUsageTypeName = request.getParameter(ParameterConstants.COMMENT_USAGE_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionAddActionForm actionForm = (DescriptionAddActionForm)form;
                
                if(wasPost(request)) {
                    CreateCommentUsageTypeDescriptionForm commandForm = CommentUtil.getHome().getCreateCommentUsageTypeDescriptionForm();
                    
                    if(componentVendorName == null)
                        componentVendorName = actionForm.getComponentVendorName();
                    if(entityTypeName == null)
                        entityTypeName = actionForm.getEntityTypeName();
                    if(commentTypeName == null)
                        commentTypeName = actionForm.getCommentTypeName();
                    if(commentUsageTypeName == null)
                        commentUsageTypeName = actionForm.getCommentUsageTypeName();
                    
                    commandForm.setComponentVendorName(componentVendorName);
                    commandForm.setEntityTypeName(entityTypeName);
                    commandForm.setCommentTypeName(commentTypeName);
                    commandForm.setCommentUsageTypeName(commentUsageTypeName);
                    commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
                    commandForm.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = CommentUtil.getHome().createCommentUsageTypeDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setComponentVendorName(componentVendorName);
                    actionForm.setEntityTypeName(entityTypeName);
                    actionForm.setCommentTypeName(commentTypeName);
                    actionForm.setCommentUsageTypeName(commentUsageTypeName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            request.setAttribute(AttributeConstants.ENTITY_TYPE_NAME, entityTypeName);
            request.setAttribute(AttributeConstants.COMMENT_TYPE_NAME, commentTypeName);
            request.setAttribute(AttributeConstants.COMMENT_USAGE_TYPE_NAME, commentUsageTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(4);
            
            parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            parameters.put(ParameterConstants.ENTITY_TYPE_NAME, entityTypeName);
            parameters.put(ParameterConstants.COMMENT_TYPE_NAME, commentTypeName);
            parameters.put(ParameterConstants.COMMENT_USAGE_TYPE_NAME, commentUsageTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}