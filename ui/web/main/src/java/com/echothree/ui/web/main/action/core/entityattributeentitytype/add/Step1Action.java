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

package com.echothree.ui.web.main.action.core.entityattributeentitytype.add;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetComponentVendorsResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
    path = "/Core/EntityAttributeEntityType/Add/Step1",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Form", path = "/core/entityattributeentitytype/add/step1.jsp")
    }
)
public class Step1Action
        extends BaseAddAction {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CommandResult commandResult = CoreUtil.getHome().getComponentVendors(getUserVisitPK(request), null);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetComponentVendorsResult result = (GetComponentVendorsResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.COMPONENT_VENDORS, result.getComponentVendors());
        
        setupEntityAttributeTransfer(request, request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME),
                request.getParameter(ParameterConstants.ENTITY_TYPE_NAME), request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME));
        
        return mapping.findForward(ForwardConstants.FORM);
    }
    
}
