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

package com.echothree.ui.web.main.action.customer.customerdocument;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.form.DeleteDocumentDescriptionForm;
import com.echothree.control.user.document.common.form.GetDocumentDescriptionForm;
import com.echothree.control.user.document.common.result.GetDocumentDescriptionResult;
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
    path = "/Customer/CustomerDocument/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "CustomerDocumentDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerDocument/Description", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customerdocument/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.DocumentDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setDocumentName(findParameter(request, ParameterConstants.DOCUMENT_NAME, actionForm.getDocumentName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetDocumentDescriptionForm commandForm = DocumentUtil.getHome().getGetDocumentDescriptionForm();
        
        commandForm.setDocumentName(actionForm.getDocumentName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());
        
        CommandResult commandResult = DocumentUtil.getHome().getDocumentDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetDocumentDescriptionResult result = (GetDocumentDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.DOCUMENT_DESCRIPTION, result.getDocumentDescription());
        }
        
        BaseCustomerDocumentAction.setupCustomer(request, actionForm.getPartyName());
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteDocumentDescriptionForm commandForm = DocumentUtil.getHome().getDeleteDocumentDescriptionForm();

        commandForm.setDocumentName(actionForm.getDocumentName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return DocumentUtil.getHome().deleteDocumentDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
        parameters.put(ParameterConstants.DOCUMENT_NAME, actionForm.getDocumentName());
    }
    
}
