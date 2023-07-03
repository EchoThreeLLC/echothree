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
import com.echothree.control.user.party.common.edit.PartyAliasTypeEdit;
import com.echothree.control.user.party.common.form.EditPartyAliasTypeForm;
import com.echothree.control.user.party.common.result.EditPartyAliasTypeResult;
import com.echothree.control.user.party.common.spec.PartyAliasTypeUniversalSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/Configuration/PartyAliasType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "PartyAliasTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PartyAliasType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/partyaliastype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyAliasTypeUniversalSpec, PartyAliasTypeEdit, EditPartyAliasTypeForm, EditPartyAliasTypeResult> {
    
    @Override
    protected PartyAliasTypeUniversalSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PartyUtil.getHome().getPartyAliasTypeUniversalSpec();
        
        spec.setPartyTypeName(findParameter(request, ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName()));
        spec.setPartyAliasTypeName(findParameter(request, ParameterConstants.ORIGINAL_PARTY_ALIAS_TYPE_NAME, actionForm.getOriginalPartyAliasTypeName()));
        
        return spec;
    }
    
    @Override
    protected PartyAliasTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        PartyAliasTypeEdit edit = PartyUtil.getHome().getPartyAliasTypeEdit();

        edit.setPartyAliasTypeName(actionForm.getPartyAliasTypeName());
        edit.setValidationPattern(actionForm.getValidationPattern());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditPartyAliasTypeForm getForm()
            throws NamingException {
        return PartyUtil.getHome().getEditPartyAliasTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyAliasTypeResult result, PartyAliasTypeUniversalSpec spec, PartyAliasTypeEdit edit) {
        actionForm.setPartyTypeName(spec.getPartyTypeName());
        actionForm.setOriginalPartyAliasTypeName(spec.getPartyAliasTypeName());
        actionForm.setPartyAliasTypeName(edit.getPartyAliasTypeName());
        actionForm.setValidationPattern(edit.getValidationPattern());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyAliasTypeForm commandForm)
            throws Exception {
        return PartyUtil.getHome().editPartyAliasType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPartyAliasTypeResult result) {
        request.setAttribute(AttributeConstants.PARTY_ALIAS_TYPE, result.getPartyAliasType());
    }

}
