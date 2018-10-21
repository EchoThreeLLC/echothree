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

package com.echothree.ui.web.main.action.core.entityblobattribute;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.remote.form.GetEntityBlobAttributeForm;
import com.echothree.control.user.core.remote.result.GetEntityBlobAttributeResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.remote.transfer.EntityBlobAttributeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
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
    path = "/Core/EntityBlobAttribute/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/entityblobattribute/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetEntityBlobAttributeForm commandForm = CoreUtil.getHome().getGetEntityBlobAttributeForm();
        String entityAttributeName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME);

        commandForm.setEntityRef(request.getParameter(ParameterConstants.ENTITY_REF));
        commandForm.setEntityAttributeName(entityAttributeName);
        commandForm.setLanguageIsoName(request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME));
        
        Set<String> options = new HashSet<>();
        options.add(CoreOptions.EntityBlobAttributeIncludeBlob);
        commandForm.setOptions(options);

        CommandResult commandResult = CoreUtil.getHome().getEntityBlobAttribute(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEntityBlobAttributeResult result = (GetEntityBlobAttributeResult)executionResult.getResult();
            EntityBlobAttributeTransfer entityBlobAttribute = result.getEntityBlobAttribute();

            if(entityBlobAttribute != null) {
                String contextPath = request.getContextPath();
                
                request.setAttribute(AttributeConstants.ENTITY_BLOB_ATTRIBUTE, entityBlobAttribute);
                request.setAttribute(AttributeConstants.ENTITY_ATTRIBUTE_NAME, entityAttributeName);
                request.setAttribute(AttributeConstants.RETURN_URL, (contextPath.length() == 0 ? "" : contextPath + "/") + request.getParameter(ParameterConstants.RETURN_URL));
                forwardKey = ForwardConstants.DISPLAY;
            }
        }
        
        return mapping.findForward(forwardKey == null ? ForwardConstants.ERROR_404 : forwardKey);
    }
    
}
