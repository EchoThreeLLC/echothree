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

package com.echothree.ui.web.main.action.configuration.searchtype;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.CreateSearchTypeForm;
import com.echothree.control.user.search.common.form.GetSearchKindForm;
import com.echothree.control.user.search.common.result.GetSearchKindResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
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
    path = "/Configuration/SearchType/Add",
    mappingClass = SecureActionMapping.class,
    name = "SearchTypeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SearchType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/searchtype/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setSearchKindName(findParameter(request, ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName()));
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetSearchKindForm commandForm = SearchUtil.getHome().getGetSearchKindForm();
        
        commandForm.setSearchKindName(actionForm.getSearchKindName());

        CommandResult commandResult = SearchUtil.getHome().getSearchKind(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetSearchKindResult result = (GetSearchKindResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.SEARCH_KIND, result.getSearchKind());
    }
    
    @Override
    public void setupDefaults(AddActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateSearchTypeForm commandForm = SearchUtil.getHome().getCreateSearchTypeForm();

        commandForm.setSearchKindName(actionForm.getSearchKindName());
        commandForm.setSearchTypeName(actionForm.getSearchTypeName());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setDescription(actionForm.getDescription());

        return SearchUtil.getHome().createSearchType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName());
    }
    
}
