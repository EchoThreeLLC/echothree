// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.geocode;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.result.GetGeoCodeDescriptionResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Configuration/GeoCode/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCode/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocode/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.GeoCodeDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setGeoCodeName(findParameter(request, ParameterConstants.GEO_CODE_NAME, actionForm.getGeoCodeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getGetGeoCodeDescriptionForm();
        
        commandForm.setGeoCodeName(actionForm.getGeoCodeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = GeoUtil.getHome().getGeoCodeDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetGeoCodeDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.GEO_CODE_DESCRIPTION, result.getGeoCodeDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getDeleteGeoCodeDescriptionForm();

        commandForm.setGeoCodeName(actionForm.getGeoCodeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return GeoUtil.getHome().deleteGeoCodeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_NAME, actionForm.getGeoCodeName());
    }
    
}
