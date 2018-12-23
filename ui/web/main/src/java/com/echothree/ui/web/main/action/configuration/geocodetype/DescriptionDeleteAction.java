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

package com.echothree.ui.web.main.action.configuration.geocodetype;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.form.DeleteGeoCodeTypeDescriptionForm;
import com.echothree.control.user.geo.common.form.GetGeoCodeTypeDescriptionForm;
import com.echothree.control.user.geo.common.result.GetGeoCodeTypeDescriptionResult;
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
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/GeoCodeType/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeTypeDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodetype/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.GeoCodeTypeDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setGeoCodeTypeName(findParameter(request, ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetGeoCodeTypeDescriptionForm commandForm = GeoUtil.getHome().getGetGeoCodeTypeDescriptionForm();
        
        commandForm.setGeoCodeTypeName(actionForm.getGeoCodeTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());
        
        CommandResult commandResult = GeoUtil.getHome().getGeoCodeTypeDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetGeoCodeTypeDescriptionResult result = (GetGeoCodeTypeDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.GEO_CODE_TYPE_DESCRIPTION, result.getGeoCodeTypeDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteGeoCodeTypeDescriptionForm commandForm = GeoUtil.getHome().getDeleteGeoCodeTypeDescriptionForm();

        commandForm.setGeoCodeTypeName(actionForm.getGeoCodeTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return GeoUtil.getHome().deleteGeoCodeTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName());
    }
    
}
