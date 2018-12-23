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

package com.echothree.ui.web.main.action.selector.selectornode.add;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetEntityAttributesForm;
import com.echothree.control.user.core.common.result.GetEntityAttributesResult;
import com.echothree.model.control.core.common.EntityAttributeTypes;
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
    path = "/Selector/SelectorNode/Add/EntityListItemStep4",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/selector/selectornode/add/entityListItemStep4.jsp")
    }
)
public class EntityListItemStep4Action
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        
        try {
            final String selectorKindName = request.getParameter(ParameterConstants.SELECTOR_KIND_NAME);
            final String selectorTypeName = request.getParameter(ParameterConstants.SELECTOR_TYPE_NAME);
            final String selectorName = request.getParameter(ParameterConstants.SELECTOR_NAME);
            final String selectorNodeTypeName = request.getParameter(ParameterConstants.SELECTOR_NODE_TYPE_NAME);
            final String componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
            final String entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
            GetEntityAttributesForm commandForm = CoreUtil.getHome().getGetEntityAttributesForm();
            
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeTypeNames(EntityAttributeTypes.LISTITEM.name() + ":" + EntityAttributeTypes.MULTIPLELISTITEM.name());
            
            CommandResult commandResult = CoreUtil.getHome().getEntityAttributes(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEntityAttributesResult result = (GetEntityAttributesResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.SELECTOR_KIND_NAME, selectorKindName);
            request.setAttribute(AttributeConstants.SELECTOR_TYPE_NAME, selectorTypeName);
            request.setAttribute(AttributeConstants.SELECTOR_NAME, selectorName);
            request.setAttribute(AttributeConstants.SELECTOR_NODE_TYPE_NAME, selectorNodeTypeName);
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            request.setAttribute(AttributeConstants.ENTITY_TYPE_NAME, entityTypeName);
            request.setAttribute(AttributeConstants.ENTITY_ATTRIBUTES, result.getEntityAttributes());
            
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
