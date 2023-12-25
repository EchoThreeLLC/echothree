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
import com.echothree.control.user.scale.common.form.CreatePartyScaleUseForm;
import com.echothree.ui.web.main.action.humanresources.employeescaleuse.AddActionForm;
import com.echothree.ui.web.main.action.humanresources.employeescaleuse.BaseEmployeeScaleUseAction;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Employee/EmployeeScaleUse/Add",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeScaleUseAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Employee/EmployeeScaleUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/employee/employeescaleuse/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        BaseEmployeeScaleUseAction.setupEmployee(request, null);
    }

    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreatePartyScaleUseForm commandForm = ScaleUtil.getHome().getCreatePartyScaleUseForm();

        commandForm.setScaleUseTypeName(actionForm.getScaleUseTypeChoice());
        commandForm.setScaleName(actionForm.getScaleChoice());

        return ScaleUtil.getHome().createPartyScaleUse(getUserVisitPK(request), commandForm);
    }

}
