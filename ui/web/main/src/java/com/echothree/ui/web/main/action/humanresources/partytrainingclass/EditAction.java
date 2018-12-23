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

package com.echothree.ui.web.main.action.humanresources.partytrainingclass;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.edit.PartyTrainingClassEdit;
import com.echothree.control.user.training.common.form.EditPartyTrainingClassForm;
import com.echothree.control.user.training.common.result.EditPartyTrainingClassResult;
import com.echothree.control.user.training.common.spec.PartyTrainingClassSpec;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassTransfer;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import static com.echothree.view.client.web.struts.BaseAction.getUserVisitPK;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/PartyTrainingClass/Edit",
    mappingClass = SecureActionMapping.class,
    name = "PartyTrainingClassEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/PartyTrainingClass/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/partytrainingclass/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyTrainingClassSpec, PartyTrainingClassEdit, EditPartyTrainingClassForm, EditPartyTrainingClassResult> {
    
    @Override
    protected PartyTrainingClassSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        PartyTrainingClassSpec spec = TrainingUtil.getHome().getPartyTrainingClassSpec();
        
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        spec.setPartyTrainingClassName(findParameter(request, ParameterConstants.PARTY_TRAINING_CLASS_NAME, actionForm.getPartyTrainingClassName()));
        
        return spec;
    }
    
    @Override
    protected PartyTrainingClassEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        PartyTrainingClassEdit edit = TrainingUtil.getHome().getPartyTrainingClassEdit();

        edit.setCompletedTime(actionForm.getCompletedTime());
        edit.setValidUntilTime(actionForm.getValidUntilTime());

        return edit;
    }
    
    @Override
    protected EditPartyTrainingClassForm getForm()
            throws NamingException {
        return TrainingUtil.getHome().getEditPartyTrainingClassForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyTrainingClassResult result, PartyTrainingClassSpec spec, PartyTrainingClassEdit edit) {
        actionForm.setPartyTrainingClassName(spec.getPartyTrainingClassName());
        actionForm.setCompletedTime(edit.getCompletedTime());
        actionForm.setValidUntilTime(edit.getValidUntilTime());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyTrainingClassForm commandForm)
            throws Exception {
        return TrainingUtil.getHome().editPartyTrainingClass(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPartyTrainingClassResult result)
            throws NamingException {
        PartyTrainingClassTransfer partyTrainingClass = result.getPartyTrainingClass();
        
        request.setAttribute(AttributeConstants.PARTY_TRAINING_CLASS, partyTrainingClass);
        request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request), partyTrainingClass.getParty().getPartyName(),
                null));
    }

}
