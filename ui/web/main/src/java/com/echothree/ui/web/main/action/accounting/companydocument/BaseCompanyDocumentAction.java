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

package com.echothree.ui.web.main.action.accounting.companydocument;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.form.GetPartyDocumentForm;
import com.echothree.control.user.document.common.result.GetPartyDocumentResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetCompanyForm;
import com.echothree.control.user.party.common.result.GetCompanyResult;
import com.echothree.model.control.document.common.transfer.PartyDocumentTransfer;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseCompanyDocumentAction<A
        extends ActionForm>
        extends MainBaseAction<A> {

    public static CompanyTransfer setupCompany(HttpServletRequest request, String partyName)
            throws NamingException {
        GetCompanyForm commandForm = PartyUtil.getHome().getGetCompanyForm();
        CompanyTransfer company = null;

        commandForm.setPartyName(partyName);

        CommandResult commandResult = PartyUtil.getHome().getCompany(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCompanyResult result = (GetCompanyResult)executionResult.getResult();

            company = result.getCompany();

            if(company != null) {
                request.setAttribute(AttributeConstants.COMPANY, company);
            }
        }

        return company;
    }

    public static CompanyTransfer setupCompany(HttpServletRequest request)
            throws NamingException {
        return setupCompany(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public static PartyDocumentTransfer setupPartyDocumentTransfer(HttpServletRequest request, String documentName, Set<String> options)
            throws NamingException {
        GetPartyDocumentForm commandForm = DocumentUtil.getHome().getGetPartyDocumentForm();
        PartyDocumentTransfer partyDocument = null;

        commandForm.setDocumentName(documentName);

        if(options != null) {
            commandForm.setOptions(options);
        }

        CommandResult commandResult = DocumentUtil.getHome().getPartyDocument(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPartyDocumentResult result = (GetPartyDocumentResult)executionResult.getResult();

            partyDocument = result.getPartyDocument();

            if(partyDocument != null) {
                request.setAttribute(AttributeConstants.PARTY_DOCUMENT, partyDocument);
            }
        }

        return partyDocument;
    }

    public static PartyDocumentTransfer setupPartyDocumentTransfer(HttpServletRequest request, Set<String> options)
            throws NamingException {
        return setupPartyDocumentTransfer(request, request.getParameter(ParameterConstants.DOCUMENT_NAME), options);
    }

}
