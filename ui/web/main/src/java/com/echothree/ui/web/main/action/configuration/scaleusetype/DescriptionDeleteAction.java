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

package com.echothree.ui.web.main.action.configuration.scaleusetype;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.form.DeleteScaleUseTypeDescriptionForm;
import com.echothree.control.user.scale.common.form.GetScaleUseTypeDescriptionForm;
import com.echothree.control.user.scale.common.result.GetScaleUseTypeDescriptionResult;
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
    path = "/Configuration/ScaleUseType/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "ScaleUseTypeDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/ScaleUseType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/scaleusetype/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.ScaleUseTypeDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setScaleUseTypeName(findParameter(request, ParameterConstants.SCALE_USE_TYPE_NAME, actionForm.getScaleUseTypeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetScaleUseTypeDescriptionForm commandForm = ScaleUtil.getHome().getGetScaleUseTypeDescriptionForm();
        
        commandForm.setScaleUseTypeName(actionForm.getScaleUseTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());
        
        CommandResult commandResult = ScaleUtil.getHome().getScaleUseTypeDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetScaleUseTypeDescriptionResult result = (GetScaleUseTypeDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.SCALE_USE_TYPE_DESCRIPTION, result.getScaleUseTypeDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteScaleUseTypeDescriptionForm commandForm = ScaleUtil.getHome().getDeleteScaleUseTypeDescriptionForm();

        commandForm.setScaleUseTypeName(actionForm.getScaleUseTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return ScaleUtil.getHome().deleteScaleUseTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SCALE_USE_TYPE_NAME, actionForm.getScaleUseTypeName());
    }
    
}
