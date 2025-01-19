// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.entityblobattribute;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.persistence.type.ByteArray;
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
    path = "/Core/EntityBlobAttribute/Add",
    mappingClass = SecureActionMapping.class,
    name = "EntityBlobAttributeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Form", path = "/core/entityblobattribute/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var returnUrl = request.getParameter(ParameterConstants.RETURN_URL);
        var entityRef = request.getParameter(ParameterConstants.ENTITY_REF);
        var entityAttributeName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME);
        var actionForm = (AddActionForm)form;

        if(wasPost(request)) {
            var commandForm = CoreUtil.getHome().getCreateEntityBlobAttributeForm();
            var blobAttribute = actionForm.getBlobAttribute();

            if(entityRef == null)
                entityRef = actionForm.getEntityRef();
            if(entityAttributeName == null)
                entityAttributeName = actionForm.getEntityAttributeName();
            if(returnUrl == null)
                returnUrl = actionForm.getReturnUrl();

            commandForm.setEntityRef(entityRef);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
            commandForm.setMimeTypeName(blobAttribute.getContentType());
            commandForm.setBlobAttribute(new ByteArray(blobAttribute.getFileData()));

            var commandResult = CoreUtil.getHome().createEntityBlobAttribute(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            }
        } else {
            actionForm.setEntityRef(entityRef);
            actionForm.setEntityAttributeName(entityAttributeName);
            actionForm.setReturnUrl(returnUrl);
            forwardKey = ForwardConstants.FORM;
        }
        
        return forwardKey == null ? new ActionForward(returnUrl, true) : mapping.findForward(forwardKey);
    }
    
}
