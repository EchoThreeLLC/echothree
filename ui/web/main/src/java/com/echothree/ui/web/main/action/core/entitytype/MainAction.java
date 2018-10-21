// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.entitytype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.remote.form.GetEntityTypesForm;
import com.echothree.control.user.core.remote.result.GetEntityTypesResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.data.core.common.EntityTypeConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.util.remote.transfer.Limit;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@SproutAction(
    path = "/Core/EntityType/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/entitytype/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        GetEntityTypesForm commandForm = CoreUtil.getHome().getGetEntityTypesForm();

        commandForm.setComponentVendorName(request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME));

        Set<String> options = new HashSet<>();
        options.add(CoreOptions.EntityTypeIncludeIndexTypesCount);
        commandForm.setOptions(options);

        String offsetParameter = request.getParameter((new ParamEncoder("entityType").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
        Integer offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * 20;

        Map<String, Limit> limits = new HashMap<>();
        limits.put(EntityTypeConstants.ENTITY_TYPE_NAME, new Limit("20", offset == null ? null : offset.toString()));
        commandForm.setLimits(limits);

        CommandResult commandResult = CoreUtil.getHome().getEntityTypes(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEntityTypesResult result = (GetEntityTypesResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.COMPONENT_VENDOR, result.getComponentVendor());
            request.setAttribute(AttributeConstants.ENTITY_TYPE_COUNT, Integer.valueOf(result.getEntityTypeCount().toString()));
            request.setAttribute(AttributeConstants.ENTITY_TYPES, result.getEntityTypes());
            forwardKey = ForwardConstants.DISPLAY;
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }

        return mapping.findForward(forwardKey);
    }

}
