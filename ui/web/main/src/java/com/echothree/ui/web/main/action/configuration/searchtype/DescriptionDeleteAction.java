// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.searchtype;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.DeleteSearchTypeDescriptionForm;
import com.echothree.control.user.search.common.form.GetSearchTypeDescriptionForm;
import com.echothree.control.user.search.common.result.GetSearchTypeDescriptionResult;
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
    path = "/Configuration/SearchType/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "SearchTypeDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SearchType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/searchtype/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.SearchTypeDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setSearchKindName(findParameter(request, ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName()));
        actionForm.setSearchTypeName(findParameter(request, ParameterConstants.SEARCH_TYPE_NAME, actionForm.getSearchTypeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetSearchTypeDescriptionForm commandForm = SearchUtil.getHome().getGetSearchTypeDescriptionForm();
        
        commandForm.setSearchKindName(actionForm.getSearchKindName());
        commandForm.setSearchTypeName(actionForm.getSearchTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());
        
        CommandResult commandResult = SearchUtil.getHome().getSearchTypeDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetSearchTypeDescriptionResult result = (GetSearchTypeDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.SEARCH_TYPE_DESCRIPTION, result.getSearchTypeDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteSearchTypeDescriptionForm commandForm = SearchUtil.getHome().getDeleteSearchTypeDescriptionForm();

        commandForm.setSearchKindName(actionForm.getSearchKindName());
        commandForm.setSearchTypeName(actionForm.getSearchTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return SearchUtil.getHome().deleteSearchTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName());
        parameters.put(ParameterConstants.SEARCH_TYPE_NAME, actionForm.getSearchTypeName());
    }
    
}
