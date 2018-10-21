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

package com.echothree.ui.web.main.action.purchasing.vendorcontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.remote.edit.ContactWebAddressEdit;
import com.echothree.control.user.contact.remote.form.EditContactWebAddressForm;
import com.echothree.control.user.contact.remote.result.EditContactWebAddressResult;
import com.echothree.control.user.contact.remote.spec.PartyContactMechanismSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.remote.command.ExecutionResult;
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
    path = "/Purchasing/VendorContactMechanism/ContactWebAddressEdit",
    mappingClass = SecureActionMapping.class,
    name = "VendorContactWebAddressEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/VendorContactMechanism/Main", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendorcontactmechanism/contactWebAddressEdit.jsp")
    }
)
public class ContactWebAddressEditAction
        extends BaseVendorContactMechanismAction<ContactWebAddressEditActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ContactWebAddressEditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        String contactMechanismName = request.getParameter(ParameterConstants.CONTACT_MECHANISM_NAME);
        EditContactWebAddressForm commandForm = ContactUtil.getHome().getEditContactWebAddressForm();
        PartyContactMechanismSpec spec = ContactUtil.getHome().getPartyContactMechanismSpec();

        if(partyName == null) {
            partyName = actionForm.getPartyName();
        }
        if(contactMechanismName == null) {
            contactMechanismName = actionForm.getContactMechanismName();
        }

        commandForm.setSpec(spec);
        spec.setPartyName(partyName);
        spec.setContactMechanismName(contactMechanismName);

        if(wasPost(request)) {
            boolean wasCancelled = wasCancelled(request);

            if(wasCancelled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                ContactWebAddressEdit edit = ContactUtil.getHome().getContactWebAddressEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setUrl(actionForm.getUrl());
                edit.setDescription(actionForm.getDescription());
            }

            CommandResult commandResult = ContactUtil.getHome().editContactWebAddress(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors() && !wasCancelled) {
                ExecutionResult executionResult = commandResult.getExecutionResult();

                if(executionResult != null) {
                    EditContactWebAddressResult result = (EditContactWebAddressResult)executionResult.getResult();

                    request.setAttribute(AttributeConstants.CONTACT_MECHANISM, result.getContactMechanism());
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }

                setCommandResultAttribute(request, commandResult);

                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            CommandResult commandResult = ContactUtil.getHome().editContactWebAddress(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditContactWebAddressResult result = (EditContactWebAddressResult)executionResult.getResult();

            if(result != null) {
                ContactWebAddressEdit edit = result.getEdit();

                if(edit != null) {
                    actionForm.setPartyName(partyName);
                    actionForm.setContactMechanismName(contactMechanismName);
                    actionForm.setUrl(edit.getUrl());
                    actionForm.setDescription(edit.getDescription());
                }

                request.setAttribute(AttributeConstants.CONTACT_MECHANISM, result.getContactMechanism());
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }

            setCommandResultAttribute(request, commandResult);

            forwardKey = ForwardConstants.FORM;
        }

        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupVendor(request, partyName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);

            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            customActionForward.setParameters(parameters);
        }

        return customActionForward;
    }

}