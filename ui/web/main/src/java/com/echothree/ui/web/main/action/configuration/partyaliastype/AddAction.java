// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.partyaliastype;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.CreatePartyAliasTypeForm;
import com.echothree.control.user.party.common.form.GetPartyTypeForm;
import com.echothree.control.user.party.common.result.GetPartyTypeResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/PartyAliasType/Add",
    mappingClass = SecureActionMapping.class,
    name = "PartyAliasTypeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PartyAliasType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/partyaliastype/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyTypeName(findParameter(request, ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName()));
    }

    @Override
    public void setupDefaults(AddActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetPartyTypeForm commandForm = PartyUtil.getHome().getGetPartyTypeForm();

        commandForm.setPartyTypeName(actionForm.getPartyTypeName());

        CommandResult commandResult = PartyUtil.getHome().getPartyType(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyTypeResult result = (GetPartyTypeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_TYPE, result.getPartyType());
    }

    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreatePartyAliasTypeForm commandForm = PartyUtil.getHome().getCreatePartyAliasTypeForm();

        commandForm.setPartyTypeName(actionForm.getPartyTypeName());
        commandForm.setPartyAliasTypeName(actionForm.getPartyAliasTypeName());
        commandForm.setValidationPattern(actionForm.getValidationPattern());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setDescription(actionForm.getDescription());

        return PartyUtil.getHome().createPartyAliasType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName());
    }
    
}
