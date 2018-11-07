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

package com.echothree.ui.web.main.action.configuration.service;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.ServiceEdit;
import com.echothree.control.user.core.common.form.EditServiceForm;
import com.echothree.control.user.core.common.result.EditServiceResult;
import com.echothree.control.user.core.common.spec.ServiceSpec;
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
    path = "/Configuration/Service/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ServiceEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/Service/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/service/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ServiceSpec, ServiceEdit, EditServiceForm, EditServiceResult> {
    
    @Override
    protected ServiceSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        ServiceSpec spec = CoreUtil.getHome().getServiceSpec();
        
        spec.setServiceName(findParameter(request, ParameterConstants.ORIGINAL_SERVICE_NAME, actionForm.getOriginalServiceName()));
        
        return spec;
    }
    
    @Override
    protected ServiceEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        ServiceEdit edit = CoreUtil.getHome().getServiceEdit();

        edit.setServiceName(actionForm.getServiceName());
        edit.setPort(actionForm.getPort());
        edit.setProtocolName(actionForm.getProtocolChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditServiceForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditServiceForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditServiceResult result, ServiceSpec spec, ServiceEdit edit) {
        actionForm.setOriginalServiceName(spec.getServiceName());
        actionForm.setServiceName(edit.getServiceName());
        actionForm.setPort(edit.getPort());
        actionForm.setProtocolChoice(edit.getProtocolName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditServiceForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editService(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditServiceResult result) {
        request.setAttribute(AttributeConstants.SERVICE, result.getService());
    }

}
