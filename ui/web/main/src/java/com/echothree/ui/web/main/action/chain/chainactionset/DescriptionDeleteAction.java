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

package com.echothree.ui.web.main.action.chain.chainactionset;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.form.DeleteChainActionSetDescriptionForm;
import com.echothree.control.user.chain.common.form.GetChainActionSetDescriptionForm;
import com.echothree.control.user.chain.common.result.GetChainActionSetDescriptionResult;
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
    path = "/Chain/ChainActionSet/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "ChainActionSetDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/ChainActionSet/Description", redirect = true),
        @SproutForward(name = "Form", path = "/chain/chainactionset/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.ChainActionSetDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setChainKindName(findParameter(request, ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName()));
        actionForm.setChainTypeName(findParameter(request, ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName()));
        actionForm.setChainName(findParameter(request, ParameterConstants.CHAIN_NAME, actionForm.getChainName()));
        actionForm.setChainActionSetName(findParameter(request, ParameterConstants.CHAIN_ACTION_SET_NAME, actionForm.getChainActionSetName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetChainActionSetDescriptionForm commandForm = ChainUtil.getHome().getGetChainActionSetDescriptionForm();
        
        commandForm.setChainKindName(actionForm.getChainKindName());
        commandForm.setChainTypeName(actionForm.getChainTypeName());
        commandForm.setChainName(actionForm.getChainName());
        commandForm.setChainActionSetName(actionForm.getChainActionSetName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());
        
        CommandResult commandResult = ChainUtil.getHome().getChainActionSetDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetChainActionSetDescriptionResult result = (GetChainActionSetDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CHAIN_ACTION_SET_DESCRIPTION, result.getChainActionSetDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteChainActionSetDescriptionForm commandForm = ChainUtil.getHome().getDeleteChainActionSetDescriptionForm();

        commandForm.setChainKindName(actionForm.getChainKindName());
        commandForm.setChainTypeName(actionForm.getChainTypeName());
        commandForm.setChainName(actionForm.getChainName());
        commandForm.setChainActionSetName(actionForm.getChainActionSetName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return ChainUtil.getHome().deleteChainActionSetDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName());
        parameters.put(ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName());
        parameters.put(ParameterConstants.CHAIN_NAME, actionForm.getChainName());
        parameters.put(ParameterConstants.CHAIN_ACTION_SET_NAME, actionForm.getChainActionSetName());
    }
    
}
