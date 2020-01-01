// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.protocol;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.ProtocolDescriptionEdit;
import com.echothree.control.user.core.common.form.EditProtocolDescriptionForm;
import com.echothree.control.user.core.common.result.EditProtocolDescriptionResult;
import com.echothree.control.user.core.common.spec.ProtocolDescriptionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/Configuration/Protocol/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ProtocolDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/Protocol/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/protocol/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ProtocolDescriptionSpec, ProtocolDescriptionEdit, EditProtocolDescriptionForm, EditProtocolDescriptionResult> {
    
    @Override
    protected ProtocolDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        ProtocolDescriptionSpec spec = CoreUtil.getHome().getProtocolDescriptionSpec();
        
        spec.setProtocolName(findParameter(request, ParameterConstants.PROTOCOL_NAME, actionForm.getProtocolName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ProtocolDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        ProtocolDescriptionEdit edit = CoreUtil.getHome().getProtocolDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditProtocolDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditProtocolDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditProtocolDescriptionResult result, ProtocolDescriptionSpec spec, ProtocolDescriptionEdit edit) {
        actionForm.setProtocolName(spec.getProtocolName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditProtocolDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editProtocolDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PROTOCOL_NAME, actionForm.getProtocolName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditProtocolDescriptionResult result) {
        request.setAttribute(AttributeConstants.PROTOCOL_DESCRIPTION, result.getProtocolDescription());
    }

}
