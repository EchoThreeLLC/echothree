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

package com.echothree.ui.web.main.action.employee;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.CreateProfileForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Employee/ProfileAdd",
    mappingClass = SecureActionMapping.class,
    name = "ProfileAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Form", path = "/employee/profileAdd.jsp")
    }
)
public class ProfileAddAction
        extends MainBaseAction<ProfileAddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ProfileAddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        
        if(wasPost(request)) {
            CreateProfileForm commandForm = PartyUtil.getHome().getCreateProfileForm();
            
            commandForm.setNickname(actionForm.getNickname());
            commandForm.setIconName(actionForm.getIconChoice());
            commandForm.setGenderName(actionForm.getGenderChoice());
            commandForm.setMoodName(actionForm.getMoodChoice());
            commandForm.setBirthday(actionForm.getBirthday());
            commandForm.setBirthdayFormatName(actionForm.getBirthdayFormatChoice());
            commandForm.setOccupation(actionForm.getOccupation());
            commandForm.setHobbies(actionForm.getHobbies());
            commandForm.setLocation(actionForm.getLocation());
            commandForm.setBioMimeTypeName(actionForm.getBioMimeTypeChoice());
            commandForm.setBio(actionForm.getBio());
            commandForm.setSignatureMimeTypeName(actionForm.getSignatureMimeTypeChoice());
            commandForm.setSignature(actionForm.getSignature());
            
            CommandResult commandResult = PartyUtil.getHome().createProfile(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.PORTAL;
            }
        } else {
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}