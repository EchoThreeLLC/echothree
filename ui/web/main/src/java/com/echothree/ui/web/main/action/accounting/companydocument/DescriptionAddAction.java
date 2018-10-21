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

package com.echothree.ui.web.main.action.accounting.companydocument;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.remote.form.CreateDocumentDescriptionForm;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Accounting/CompanyDocument/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "CompanyDocumentDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/CompanyDocument/Description", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/companydocument/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setDocumentName(findParameter(request, ParameterConstants.DOCUMENT_NAME, actionForm.getDocumentName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        BaseCompanyDocumentAction.setupCompany(request, actionForm.getPartyName());
        BaseCompanyDocumentAction.setupPartyDocumentTransfer(request, actionForm.getDocumentName(), null);
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateDocumentDescriptionForm commandForm = DocumentUtil.getHome().getCreateDocumentDescriptionForm();

        commandForm.setDocumentName( actionForm.getDocumentName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return DocumentUtil.getHome().createDocumentDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
        parameters.put(ParameterConstants.DOCUMENT_NAME, actionForm.getDocumentName());
    }
    
}
