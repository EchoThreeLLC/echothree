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

package com.echothree.ui.web.main.action.configuration.geocodetype;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.result.GetGeoCodeTypeDescriptionsResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Configuration/GeoCodeType/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/geocodetype/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var geoCodeTypeName = request.getParameter(ParameterConstants.GEO_CODE_TYPE_NAME);
        var commandForm = GeoUtil.getHome().getGetGeoCodeTypeDescriptionsForm();

        commandForm.setGeoCodeTypeName(geoCodeTypeName);

        var commandResult = GeoUtil.getHome().getGeoCodeTypeDescriptions(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetGeoCodeTypeDescriptionsResult) executionResult.getResult();
            var geoCodeTypeTransfer = result.getGeoCodeType();

            request.setAttribute(AttributeConstants.GEO_CODE_TYPE, geoCodeTypeTransfer);
            request.setAttribute(AttributeConstants.GEO_CODE_TYPE_DESCRIPTIONS, result.getGeoCodeTypeDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }

        return mapping.findForward(forwardKey);
    }
    
}
