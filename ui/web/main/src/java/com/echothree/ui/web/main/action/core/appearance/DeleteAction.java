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

package com.echothree.ui.web.main.action.core.appearance;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.DeleteAppearanceForm;
import com.echothree.control.user.core.common.form.GetAppearanceForm;
import com.echothree.control.user.core.common.result.GetAppearanceResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Core/Appearance/Delete",
    mappingClass = SecureActionMapping.class,
    name = "AppearanceDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Appearance/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/appearance/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.Appearance.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setAppearanceName(findParameter(request, ParameterConstants.APPEARANCE_NAME, actionForm.getAppearanceName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetAppearanceForm commandForm = CoreUtil.getHome().getGetAppearanceForm();
        
        commandForm.setAppearanceName(actionForm.getAppearanceName());
        
        CommandResult commandResult = CoreUtil.getHome().getAppearance(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetAppearanceResult result = (GetAppearanceResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.APPEARANCE, result.getAppearance());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteAppearanceForm commandForm = CoreUtil.getHome().getDeleteAppearanceForm();

        commandForm.setAppearanceName(actionForm.getAppearanceName());

        return CoreUtil.getHome().deleteAppearance(getUserVisitPK(request), commandForm);
    }
    
}
