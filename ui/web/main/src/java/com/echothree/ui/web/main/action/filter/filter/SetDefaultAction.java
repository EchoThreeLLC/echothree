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

package com.echothree.ui.web.main.action.filter.filter;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.form.SetDefaultFilterForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Filter/Filter/SetDefault",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Filter/Filter/Main", redirect = true)
    }
)
public class SetDefaultAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        final String filterKindName = request.getParameter(ParameterConstants.FILTER_KIND_NAME);
        final String filterTypeName = request.getParameter(ParameterConstants.FILTER_TYPE_NAME);
        
        try {
            String filterName = request.getParameter(ParameterConstants.FILTER_NAME);
            SetDefaultFilterForm commandForm = FilterUtil.getHome().getSetDefaultFilterForm();
            
            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setFilterName(filterName);
            
            FilterUtil.getHome().setDefaultFilter(getUserVisitPK(request), commandForm);
            
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<String, String>(2) {{
                put(ParameterConstants.FILTER_KIND_NAME, filterKindName);
                put(ParameterConstants.FILTER_TYPE_NAME, filterTypeName);
            }};
            
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}