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

package com.echothree.ui.web.main.action.purchasing.vendordocument;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.form.GetPartyDocumentForm;
import com.echothree.control.user.document.common.result.GetPartyDocumentResult;
import com.echothree.model.control.document.common.transfer.PartyDocumentTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/VendorDocument/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/purchasing/vendordocument/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetPartyDocumentForm commandForm = DocumentUtil.getHome().getGetPartyDocumentForm();
        String partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        String documentName = request.getParameter(ParameterConstants.DOCUMENT_NAME);

        commandForm.setDocumentName(documentName);

        CommandResult commandResult = DocumentUtil.getHome().getPartyDocument(getUserVisitPK(request), commandForm);
        PartyDocumentTransfer partyDocument = null;

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPartyDocumentResult result = (GetPartyDocumentResult)executionResult.getResult();
            partyDocument = result.getPartyDocument();
        }

        if(partyDocument == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.PARTY_DOCUMENT, partyDocument);
            BaseVendorDocumentAction.setupVendor(request, partyName);
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }
    
}