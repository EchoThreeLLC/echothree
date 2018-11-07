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

package com.echothree.ui.web.main.action.warehouse.warehousecontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.form.DeleteContactMechanismForm;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Warehouse/WarehouseContactMechanism/Delete",
    mappingClass = SecureActionMapping.class,
    name = "WarehouseContactMechanismDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Warehouse/WarehouseContactMechanism/Main", redirect = true),
        @SproutForward(name = "Form", path = "/warehouse/warehousecontactmechanism/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.PartyContactMechanism.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setContactMechanismName(findParameter(request, ParameterConstants.CONTACT_MECHANISM_NAME, actionForm.getContactMechanismName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        BaseWarehouseContactMechanismAction.setupPartyContactMechanismTransfer(request, actionForm.getPartyName(), actionForm.getContactMechanismName());
        BaseWarehouseContactMechanismAction.setupWarehouse(request, actionForm.getPartyName());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteContactMechanismForm commandForm = ContactUtil.getHome().getDeleteContactMechanismForm();
        
        commandForm.setContactMechanismName(actionForm.getContactMechanismName());

        return ContactUtil.getHome().deleteContactMechanism(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
}
