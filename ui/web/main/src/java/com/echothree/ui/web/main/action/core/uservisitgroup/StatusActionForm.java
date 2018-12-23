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

package com.echothree.ui.web.main.action.core.uservisitgroup;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.form.GetUserVisitGroupStatusChoicesForm;
import com.echothree.control.user.user.common.result.GetUserVisitGroupStatusChoicesResult;
import com.echothree.model.control.user.common.choice.UserVisitGroupStatusChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="UserVisitGroupStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private UserVisitGroupStatusChoicesBean userVisitGroupStatusChoices;
    
    private String userVisitGroupName;
    private String userVisitGroupStatusChoice;
    
    public void setupUserVisitGroupStatusChoices() {
        if(userVisitGroupStatusChoices == null) {
            try {
                GetUserVisitGroupStatusChoicesForm form = UserUtil.getHome().getGetUserVisitGroupStatusChoicesForm();
                
                form.setUserVisitGroupName(userVisitGroupName);
                form.setDefaultUserVisitGroupStatusChoice(userVisitGroupStatusChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = UserUtil.getHome().getUserVisitGroupStatusChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUserVisitGroupStatusChoicesResult result = (GetUserVisitGroupStatusChoicesResult)executionResult.getResult();
                userVisitGroupStatusChoices = result.getUserVisitGroupStatusChoices();
                
                if(userVisitGroupStatusChoice == null) {
                    userVisitGroupStatusChoice = userVisitGroupStatusChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, userVisitGroupStatusChoices remains null, no default
            }
        }
    }
    
    public String getUserVisitGroupName() {
        return userVisitGroupName;
    }
    
    public void setUserVisitGroupName(String userVisitGroupName) {
        this.userVisitGroupName = userVisitGroupName;
    }
    
    public String getUserVisitGroupStatusChoice() {
        return userVisitGroupStatusChoice;
    }
    
    public void setUserVisitGroupStatusChoice(String userVisitGroupStatusChoice) {
        this.userVisitGroupStatusChoice = userVisitGroupStatusChoice;
    }
    
    public List<LabelValueBean> getUserVisitGroupStatusChoices() {
        List<LabelValueBean> choices = null;
        
        setupUserVisitGroupStatusChoices();
        if(userVisitGroupStatusChoices != null) {
            choices = convertChoices(userVisitGroupStatusChoices);
        }
        
        return choices;
    }
    
}
