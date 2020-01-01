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

package com.echothree.ui.web.main.action.accounting.divisioncontactmechanism.contactmechanismadd;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetContactMechanismTypesResult;
import com.echothree.ui.web.main.action.accounting.divisioncontactmechanism.BaseDivisionContactMechanismAction;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
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
    path = "/Accounting/DivisionContactMechanism/ContactMechanismAdd/Step1",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/accounting/divisioncontactmechanism/contactmechanismadd/step1.jsp")
    }
)
public class Step1Action
        extends BaseDivisionContactMechanismAction {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CommandResult commandResult = ContactUtil.getHome().getContactMechanismTypes(getUserVisitPK(request), null);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetContactMechanismTypesResult result = (GetContactMechanismTypesResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CONTACT_MECHANISM_TYPES, result.getContactMechanismTypes());

        setupDivision(request);
        setupDefaultCountry(request);
        
        return mapping.findForward(ForwardConstants.DISPLAY);
    }

}
