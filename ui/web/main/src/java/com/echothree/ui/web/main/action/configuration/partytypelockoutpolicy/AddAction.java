// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.partytypelockoutpolicy;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.CreatePartyTypeLockoutPolicyForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
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
    path = "/Configuration/PartyTypeLockoutPolicy/Add",
    mappingClass = SecureActionMapping.class,
    name = "PartyTypeLockoutPolicyAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PartyType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/partytypelockoutpolicy/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String partyTypeName = request.getParameter(ParameterConstants.PARTY_TYPE_NAME);
        AddActionForm actionForm = (AddActionForm)form;
        
        if(wasPost(request)) {
            CreatePartyTypeLockoutPolicyForm commandForm = PartyUtil.getHome().getCreatePartyTypeLockoutPolicyForm();
            
            if(partyTypeName == null)
                partyTypeName = actionForm.getPartyTypeName();
            
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setLockoutFailureCount(actionForm.getLockoutFailureCount());
            commandForm.setResetFailureCountTime(actionForm.getResetFailureCountTime());
            commandForm.setResetFailureCountTimeUnitOfMeasureTypeName(actionForm.getResetFailureCountTimeUnitOfMeasureTypeChoice());
            commandForm.setManualLockoutReset(actionForm.getManualLockoutReset().toString());
            commandForm.setLockoutInactiveTime(actionForm.getLockoutInactiveTime());
            commandForm.setLockoutInactiveTimeUnitOfMeasureTypeName(actionForm.getLockoutInactiveTimeUnitOfMeasureTypeChoice());
            
            CommandResult commandResult = PartyUtil.getHome().createPartyTypeLockoutPolicy(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setPartyTypeName(partyTypeName);
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}