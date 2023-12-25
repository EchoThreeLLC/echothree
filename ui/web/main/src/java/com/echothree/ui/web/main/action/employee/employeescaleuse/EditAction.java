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

package com.echothree.ui.web.main.action.employee.employeescaleuse;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.edit.PartyScaleUseEdit;
import com.echothree.control.user.scale.common.form.EditPartyScaleUseForm;
import com.echothree.control.user.scale.common.result.EditPartyScaleUseResult;
import com.echothree.control.user.scale.common.spec.PartyScaleUseSpec;
import com.echothree.ui.web.main.action.humanresources.employeescaleuse.BaseEmployeeScaleUseAction;
import com.echothree.ui.web.main.action.humanresources.employeescaleuse.EditActionForm;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Employee/EmployeeScaleUse/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeScaleUseEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Employee/EmployeeScaleUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/employee/employeescaleuse/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyScaleUseSpec, PartyScaleUseEdit, EditPartyScaleUseForm, EditPartyScaleUseResult> {

    @Override
    protected PartyScaleUseSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        PartyScaleUseSpec spec = ScaleUtil.getHome().getPartyScaleUseSpec();

        spec.setScaleUseTypeName(findParameter(request, ParameterConstants.SCALE_USE_TYPE_NAME, actionForm.getScaleUseTypeName()));

        return spec;
    }

    @Override
    protected PartyScaleUseEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        PartyScaleUseEdit edit = ScaleUtil.getHome().getPartyScaleUseEdit();

        edit.setScaleName(actionForm.getScaleChoice());

        return edit;
    }

    @Override
    protected EditPartyScaleUseForm getForm()
            throws NamingException {
        return ScaleUtil.getHome().getEditPartyScaleUseForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyScaleUseResult result, PartyScaleUseSpec spec, PartyScaleUseEdit edit)
            throws NamingException {
        actionForm.setScaleUseTypeName(spec.getScaleUseTypeName());
        actionForm.setScaleChoice(edit.getScaleName());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyScaleUseForm commandForm)
            throws Exception {
        return ScaleUtil.getHome().editPartyScaleUse(getUserVisitPK(request), commandForm);
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPartyScaleUseResult result)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_SCALE_USE, result.getPartyScaleUse());
        BaseEmployeeScaleUseAction.setupEmployee(request, null);
    }
    
}