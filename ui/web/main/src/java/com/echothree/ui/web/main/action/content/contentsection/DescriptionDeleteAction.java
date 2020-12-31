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

package com.echothree.ui.web.main.action.content.contentsection;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.form.DeleteContentSectionDescriptionForm;
import com.echothree.control.user.content.common.form.GetContentSectionDescriptionForm;
import com.echothree.control.user.content.common.result.GetContentSectionDescriptionResult;
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
    path = "/Content/ContentSection/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "ContentSectionDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentSection/Description", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentsection/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.ContentSectionDescription.name();
    }

    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContentCollectionName(findParameter(request, ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName()));
        actionForm.setParentContentSectionName(findParameter(request, ParameterConstants.PARENT_CONTENT_SECTION_NAME, actionForm.getParentContentSectionName()));
        actionForm.setContentSectionName(findParameter(request, ParameterConstants.CONTENT_SECTION_NAME, actionForm.getContentSectionName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }

    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetContentSectionDescriptionForm commandForm = ContentUtil.getHome().getGetContentSectionDescriptionForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentSectionName(actionForm.getContentSectionName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        CommandResult commandResult = ContentUtil.getHome().getContentSectionDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetContentSectionDescriptionResult result = (GetContentSectionDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CONTENT_SECTION_DESCRIPTION, result.getContentSectionDescription());
        }
    }

    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteContentSectionDescriptionForm commandForm = ContentUtil.getHome().getDeleteContentSectionDescriptionForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentSectionName(actionForm.getContentSectionName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return ContentUtil.getHome().deleteContentSectionDescription(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
        parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, actionForm.getParentContentSectionName());
        parameters.put(ParameterConstants.CONTENT_SECTION_NAME, actionForm.getContentSectionName());
    }
    
}
