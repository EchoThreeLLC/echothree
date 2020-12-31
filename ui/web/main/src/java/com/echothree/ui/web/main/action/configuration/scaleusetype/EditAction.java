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

package com.echothree.ui.web.main.action.configuration.scaleusetype;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.edit.ScaleUseTypeEdit;
import com.echothree.control.user.scale.common.form.EditScaleUseTypeForm;
import com.echothree.control.user.scale.common.result.EditScaleUseTypeResult;
import com.echothree.control.user.scale.common.spec.ScaleUseTypeSpec;
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
    path = "/Configuration/ScaleUseType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ScaleUseTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/ScaleUseType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/scaleusetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ScaleUseTypeSpec, ScaleUseTypeEdit, EditScaleUseTypeForm, EditScaleUseTypeResult> {
    
    @Override
    protected ScaleUseTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        ScaleUseTypeSpec spec = ScaleUtil.getHome().getScaleUseTypeSpec();
        
        spec.setScaleUseTypeName(findParameter(request, ParameterConstants.ORIGINAL_SCALE_USE_TYPE_NAME, actionForm.getOriginalScaleUseTypeName()));
        
        return spec;
    }
    
    @Override
    protected ScaleUseTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        ScaleUseTypeEdit edit = ScaleUtil.getHome().getScaleUseTypeEdit();

        edit.setScaleUseTypeName(actionForm.getScaleUseTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditScaleUseTypeForm getForm()
            throws NamingException {
        return ScaleUtil.getHome().getEditScaleUseTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditScaleUseTypeResult result, ScaleUseTypeSpec spec, ScaleUseTypeEdit edit) {
        actionForm.setOriginalScaleUseTypeName(spec.getScaleUseTypeName());
        actionForm.setScaleUseTypeName(edit.getScaleUseTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditScaleUseTypeForm commandForm)
            throws Exception {
        return ScaleUtil.getHome().editScaleUseType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditScaleUseTypeResult result) {
        request.setAttribute(AttributeConstants.SCALE_USE_TYPE, result.getScaleUseType());
    }

}
