// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.associate.associateprogram;

import com.echothree.control.user.associate.common.AssociateUtil;
import com.echothree.control.user.associate.common.result.GetAssociateProgramDescriptionsResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Associate/AssociateProgram/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/associate/associateprogram/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            var associateProgramName = request.getParameter(ParameterConstants.ASSOCIATE_PROGRAM_NAME);
            var commandForm = AssociateUtil.getHome().getGetAssociateProgramDescriptionsForm();
            
            commandForm.setAssociateProgramName(associateProgramName);

            var commandResult = AssociateUtil.getHome().getAssociateProgramDescriptions(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetAssociateProgramDescriptionsResult)executionResult.getResult();
            var associateProgramTransfer = result.getAssociateProgram();
            
            request.setAttribute(AttributeConstants.ASSOCIATE_PROGRAM, associateProgramTransfer);
            request.setAttribute(AttributeConstants.ASSOCIATE_PROGRAM_NAME, associateProgramTransfer.getAssociateProgramName());
            request.setAttribute(AttributeConstants.ASSOCIATE_PROGRAM_DESCRIPTIONS, result.getAssociateProgramDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}