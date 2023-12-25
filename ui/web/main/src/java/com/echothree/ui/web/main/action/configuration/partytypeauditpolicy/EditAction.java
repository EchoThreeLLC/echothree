// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.partytypeauditpolicy;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.PartyTypeAuditPolicyEdit;
import com.echothree.control.user.party.common.form.EditPartyTypeAuditPolicyForm;
import com.echothree.control.user.party.common.result.EditPartyTypeAuditPolicyResult;
import com.echothree.control.user.party.common.spec.PartyTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/PartyTypeAuditPolicy/Edit",
    mappingClass = SecureActionMapping.class,
    name = "PartyTypeAuditPolicyEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PartyType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/partytypeauditpolicy/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String partyTypeName = request.getParameter(ParameterConstants.PARTY_TYPE_NAME);
        EditPartyTypeAuditPolicyForm commandForm = PartyUtil.getHome().getEditPartyTypeAuditPolicyForm();
        PartyTypeSpec spec = PartyUtil.getHome().getPartyTypeSpec();
        
        if(partyTypeName == null) {
            partyTypeName = actionForm.getPartyTypeName();
        }
        
        commandForm.setSpec(spec);
        spec.setPartyTypeName(partyTypeName);
        
        if(wasPost(request)) {
            boolean wasCanceled = wasCanceled(request);
            
            if(wasCanceled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                PartyTypeAuditPolicyEdit edit = PartyUtil.getHome().getPartyTypeAuditPolicyEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setAuditCommands(actionForm.getAuditCommands().toString());
                edit.setRetainUserVisitsTime(actionForm.getRetainUserVisitsTime());
                edit.setRetainUserVisitsTimeUnitOfMeasureTypeName(actionForm.getRetainUserVisitsTimeUnitOfMeasureTypeChoice());
            }
            
            CommandResult commandResult = PartyUtil.getHome().editPartyTypeAuditPolicy(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors() && !wasCanceled) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditPartyTypeAuditPolicyResult result = (EditPartyTypeAuditPolicyResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = PartyUtil.getHome().editPartyTypeAuditPolicy(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditPartyTypeAuditPolicyResult result = (EditPartyTypeAuditPolicyResult)executionResult.getResult();
            
            if(result != null) {
                PartyTypeAuditPolicyEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setPartyTypeName(partyTypeName);
                    actionForm.setAuditCommands(Boolean.valueOf(edit.getAuditCommands()));
                    actionForm.setRetainUserVisitsTime(edit.getRetainUserVisitsTime());
                    actionForm.setRetainUserVisitsTimeUnitOfMeasureTypeChoice(edit.getRetainUserVisitsTimeUnitOfMeasureTypeName());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
