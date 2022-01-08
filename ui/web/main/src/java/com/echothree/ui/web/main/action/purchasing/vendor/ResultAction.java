// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.web.main.action.purchasing.vendor;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.GetVendorResultsForm;
import com.echothree.control.user.search.common.result.GetVendorResultsResult;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
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
    path = "/Purchasing/Vendor/Result",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/purchasing/vendor/result.jsp")
    }
)
public class ResultAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        GetVendorResultsForm commandForm = SearchUtil.getHome().getGetVendorResultsForm();
        String results = request.getParameter(ParameterConstants.RESULTS);

        commandForm.setSearchTypeName(SearchConstants.SearchType_VENDOR_REVIEW);

        Set<String> options = new HashSet<>();
        options.add(SearchOptions.VendorResultIncludeVendor);
        commandForm.setOptions(options);

        if(results == null) {
            String offsetParameter = request.getParameter((new ParamEncoder("vendorResult").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
            Integer offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * 20;

            Map<String, Limit> limits = new HashMap<>();
            limits.put(SearchResultConstants.ENTITY_TYPE_NAME, new Limit("20", offset == null ? null : offset.toString()));
            commandForm.setLimits(limits);
        }

        CommandResult commandResult = setCommandResultAttribute(request, SearchUtil.getHome().getVendorResults(getUserVisitPK(request), commandForm));
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetVendorResultsResult result = (GetVendorResultsResult)executionResult.getResult();

            var vendorResultCount = result.getVendorResultCount();
            if(vendorResultCount != null) {
                request.setAttribute(AttributeConstants.VENDOR_RESULT_COUNT, vendorResultCount);
            }

            request.setAttribute(AttributeConstants.VENDOR_RESULTS, new ListWrapper<>(result.getVendorResults()));
        }

        return mapping.findForward(ForwardConstants.DISPLAY);
    }
    
}
