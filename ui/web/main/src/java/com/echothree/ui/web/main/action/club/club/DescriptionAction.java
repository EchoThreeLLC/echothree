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

package com.echothree.ui.web.main.action.club.club;

import com.echothree.control.user.club.common.ClubUtil;
import com.echothree.control.user.club.common.form.GetClubDescriptionsForm;
import com.echothree.control.user.club.common.result.GetClubDescriptionsResult;
import com.echothree.model.control.club.common.transfer.ClubTransfer;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Club/Club/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/club/club/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            String clubName = request.getParameter(ParameterConstants.CLUB_NAME);
            GetClubDescriptionsForm commandForm = ClubUtil.getHome().getGetClubDescriptionsForm();
            
            commandForm.setClubName(clubName);
            
            CommandResult commandResult = ClubUtil.getHome().getClubDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetClubDescriptionsResult result = (GetClubDescriptionsResult)executionResult.getResult();
            ClubTransfer clubTransfer = result.getClub();
            
            request.setAttribute(AttributeConstants.CLUB, clubTransfer);
            request.setAttribute(AttributeConstants.CLUB_NAME, clubTransfer.getClubName());
            request.setAttribute(AttributeConstants.CLUB_DESCRIPTIONS, result.getClubDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}