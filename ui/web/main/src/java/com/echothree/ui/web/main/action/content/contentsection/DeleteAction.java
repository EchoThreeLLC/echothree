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
import com.echothree.control.user.content.common.form.DeleteContentSectionForm;
import com.echothree.control.user.content.common.form.GetContentSectionForm;
import com.echothree.control.user.content.common.result.GetContentSectionResult;
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
    path = "/Content/ContentSection/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ContentSectionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentSection/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentsection/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.ContentSection.name();
    }

    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContentCollectionName(findParameter(request, ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName()));
        actionForm.setParentContentSectionName(findParameter(request, ParameterConstants.PARENT_CONTENT_SECTION_NAME, actionForm.getParentContentSectionName()));
        actionForm.setContentSectionName(findParameter(request, ParameterConstants.CONTENT_SECTION_NAME, actionForm.getContentSectionName()));
    }

    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetContentSectionForm commandForm = ContentUtil.getHome().getGetContentSectionForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentSectionName(actionForm.getContentSectionName());

        CommandResult commandResult = ContentUtil.getHome().getContentSection(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetContentSectionResult result = (GetContentSectionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CONTENT_SECTION, result.getContentSection());
        }
    }

    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteContentSectionForm commandForm = ContentUtil.getHome().getDeleteContentSectionForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentSectionName(actionForm.getContentSectionName());

        return ContentUtil.getHome().deleteContentSection(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
        parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, actionForm.getParentContentSectionName());
    }

}
