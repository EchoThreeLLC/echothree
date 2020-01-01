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

package com.echothree.ui.web.main.action.chain.lettersource;

import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.control.user.letter.common.form.GetLetterSourceDescriptionsForm;
import com.echothree.control.user.letter.common.result.GetLetterSourceDescriptionsResult;
import com.echothree.model.control.letter.common.transfer.LetterSourceTransfer;
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
    path = "/Chain/LetterSource/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/chain/lettersource/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String letterSourceName = request.getParameter(ParameterConstants.LETTER_SOURCE_NAME);
        GetLetterSourceDescriptionsForm commandForm = LetterUtil.getHome().getGetLetterSourceDescriptionsForm();
        
        commandForm.setLetterSourceName(letterSourceName);
        
        CommandResult commandResult = LetterUtil.getHome().getLetterSourceDescriptions(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetLetterSourceDescriptionsResult result = (GetLetterSourceDescriptionsResult)executionResult.getResult();
        LetterSourceTransfer letterSourceTransfer = result.getLetterSource();
        
        request.setAttribute(AttributeConstants.LETTER_SOURCE, letterSourceTransfer);
        request.setAttribute(AttributeConstants.LETTER_SOURCE_NAME, letterSourceTransfer.getLetterSourceName());
        request.setAttribute(AttributeConstants.LETTER_SOURCE_DESCRIPTIONS, result.getLetterSourceDescriptions());
        
        return mapping.findForward(ForwardConstants.DISPLAY);
    }
    
}