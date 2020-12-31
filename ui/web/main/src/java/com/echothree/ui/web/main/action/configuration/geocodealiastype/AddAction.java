// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.geocodealiastype;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.form.CreateGeoCodeAliasTypeForm;
import com.echothree.control.user.geo.common.form.GetGeoCodeTypeForm;
import com.echothree.control.user.geo.common.result.GetGeoCodeTypeResult;
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
    path = "/Configuration/GeoCodeAliasType/Add",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeAliasTypeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeAliasType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodealiastype/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setGeoCodeTypeName(findParameter(request, ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName()));
    }

    @Override
    public void setupDefaults(AddActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetGeoCodeTypeForm commandForm = GeoUtil.getHome().getGetGeoCodeTypeForm();

        commandForm.setGeoCodeTypeName(actionForm.getGeoCodeTypeName());

        CommandResult commandResult = GeoUtil.getHome().getGeoCodeType(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetGeoCodeTypeResult result = (GetGeoCodeTypeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.GEO_CODE_TYPE, result.getGeoCodeType());
    }

    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateGeoCodeAliasTypeForm commandForm = GeoUtil.getHome().getCreateGeoCodeAliasTypeForm();

        commandForm.setGeoCodeTypeName(actionForm.getGeoCodeTypeName());
        commandForm.setGeoCodeAliasTypeName(actionForm.getGeoCodeAliasTypeName());
        commandForm.setValidationPattern(actionForm.getValidationPattern());
        commandForm.setIsRequired(actionForm.getIsRequired().toString());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setDescription(actionForm.getDescription());

        return GeoUtil.getHome().createGeoCodeAliasType(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName());
    }

}
