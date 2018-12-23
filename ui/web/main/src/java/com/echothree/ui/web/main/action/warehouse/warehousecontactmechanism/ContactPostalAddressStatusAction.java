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

package com.echothree.ui.web.main.action.warehouse.warehousecontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.form.SetPostalAddressStatusForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Warehouse/WarehouseContactMechanism/ContactPostalAddressStatus",
    mappingClass = SecureActionMapping.class,
    name = "WarehouseContactPostalAddressStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Warehouse/WarehouseContactMechanism/Main", redirect = true),
        @SproutForward(name = "Form", path = "/warehouse/warehousecontactmechanism/contactPostalAddressStatus.jsp")
    }
)
public class ContactPostalAddressStatusAction
        extends BaseWarehouseContactMechanismAction<ContactPostalAddressStatusActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ContactPostalAddressStatusActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        String contactMechanismName = request.getParameter(ParameterConstants.CONTACT_MECHANISM_NAME);

        if(partyName == null) {
            partyName = actionForm.getPartyName();
        }
        if(contactMechanismName == null) {
            contactMechanismName = actionForm.getContactMechanismName();
        }

        if(wasPost(request)) {
            SetPostalAddressStatusForm commandForm = ContactUtil.getHome().getSetPostalAddressStatusForm();
            commandForm.setContactMechanismName(contactMechanismName);
            commandForm.setPostalAddressStatusChoice(actionForm.getPostalAddressStatusChoice());

            CommandResult commandResult = ContactUtil.getHome().setPostalAddressStatus(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setPartyName(partyName);
            actionForm.setContactMechanismName(contactMechanismName);
            forwardKey = ForwardConstants.FORM;
        }

        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupWarehouse(request, partyName);
            setupPartyContactMechanismTransfer(request, partyName, contactMechanismName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);

            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            customActionForward.setParameters(parameters);
        }

        return customActionForward;
    }

}
