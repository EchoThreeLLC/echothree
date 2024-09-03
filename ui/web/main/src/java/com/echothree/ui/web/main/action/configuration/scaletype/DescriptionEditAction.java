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

package com.echothree.ui.web.main.action.configuration.scaletype;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.edit.ScaleTypeDescriptionEdit;
import com.echothree.control.user.scale.common.form.EditScaleTypeDescriptionForm;
import com.echothree.control.user.scale.common.result.EditScaleTypeDescriptionResult;
import com.echothree.control.user.scale.common.spec.ScaleTypeDescriptionSpec;
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
    path = "/Configuration/ScaleType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ScaleTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/ScaleType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/scaletype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ScaleTypeDescriptionSpec, ScaleTypeDescriptionEdit, EditScaleTypeDescriptionForm, EditScaleTypeDescriptionResult> {
    
    @Override
    protected ScaleTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ScaleUtil.getHome().getScaleTypeDescriptionSpec();
        
        spec.setScaleTypeName(findParameter(request, ParameterConstants.SCALE_TYPE_NAME, actionForm.getScaleTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ScaleTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ScaleUtil.getHome().getScaleTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditScaleTypeDescriptionForm getForm()
            throws NamingException {
        return ScaleUtil.getHome().getEditScaleTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditScaleTypeDescriptionResult result, ScaleTypeDescriptionSpec spec, ScaleTypeDescriptionEdit edit) {
        actionForm.setScaleTypeName(spec.getScaleTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditScaleTypeDescriptionForm commandForm)
            throws Exception {
        return ScaleUtil.getHome().editScaleTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SCALE_TYPE_NAME, actionForm.getScaleTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditScaleTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.SCALE_TYPE_DESCRIPTION, result.getScaleTypeDescription());
    }

}
