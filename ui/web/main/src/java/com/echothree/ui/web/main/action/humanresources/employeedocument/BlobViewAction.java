// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.humanresources.employeedocument;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.form.GetPartyDocumentForm;
import com.echothree.control.user.document.common.result.GetPartyDocumentResult;
import com.echothree.model.control.document.common.DocumentOptions;
import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.model.control.document.common.transfer.PartyDocumentTransfer;
import com.echothree.ui.web.main.framework.ByteArrayStreamInfo;
import com.echothree.ui.web.main.framework.MainBaseDownloadAction;
import com.echothree.ui.web.main.framework.MainBaseDownloadAction.StreamInfo;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/EmployeeDocument/BlobView",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    }
)
public class BlobViewAction
        extends MainBaseDownloadAction {
    
    @Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        StreamInfo streamInfo = null;
        String documentName = request.getParameter(ParameterConstants.DOCUMENT_NAME);
        GetPartyDocumentForm commandForm = DocumentUtil.getHome().getGetPartyDocumentForm();

        commandForm.setDocumentName(documentName);

        Set<String> options = new HashSet<>();
        options.add(DocumentOptions.DocumentIncludeBlob);
        commandForm.setOptions(options);

        CommandResult commandResult = DocumentUtil.getHome().getPartyDocument(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyDocumentResult result = (GetPartyDocumentResult)executionResult.getResult();

        PartyDocumentTransfer partyDocument = result.getPartyDocument();

        if(partyDocument != null) {
            DocumentTransfer document = partyDocument.getDocument();
            String mimeType = document.getMimeType().getMimeTypeName();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(document.getBlob().byteArrayValue());

            streamInfo = new ByteArrayStreamInfo(mimeType, byteArrayInputStream, null, null);
        }

        return streamInfo;
    }
    
}
