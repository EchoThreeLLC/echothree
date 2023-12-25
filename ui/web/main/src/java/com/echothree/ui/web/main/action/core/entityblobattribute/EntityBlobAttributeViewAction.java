// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.entityblobattribute;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetEntityBlobAttributeForm;
import com.echothree.control.user.core.common.result.GetEntityBlobAttributeResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.EntityBlobAttributeTransfer;
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
    path = "/Core/EntityBlobAttribute/EntityBlobAttributeView",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    }
)
public class EntityBlobAttributeViewAction
        extends MainBaseDownloadAction {
    
    @Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        StreamInfo streamInfo = null;
        GetEntityBlobAttributeForm commandForm = CoreUtil.getHome().getGetEntityBlobAttributeForm();

        commandForm.setEntityRef(request.getParameter(ParameterConstants.ENTITY_REF));
        commandForm.setEntityAttributeName(request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME));
        commandForm.setLanguageIsoName(request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME));
        commandForm.setReferrer(request.getHeader("Referer"));

        Set<String> options = new HashSet<>();
        options.add(CoreOptions.EntityBlobAttributeIncludeBlob);
        commandForm.setOptions(options);

        CommandResult commandResult = CoreUtil.getHome().getEntityBlobAttribute(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEntityBlobAttributeResult result = (GetEntityBlobAttributeResult)executionResult.getResult();

            EntityBlobAttributeTransfer itemAttribute = result.getEntityBlobAttribute();

            if(itemAttribute != null) {
                String mimeType = itemAttribute.getMimeType().getMimeTypeName();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(itemAttribute.getBlobAttribute().byteArrayValue());

                streamInfo = new ByteArrayStreamInfo(mimeType, byteArrayInputStream, null, null);
            }
        }

        return streamInfo;
    }

}
