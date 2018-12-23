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

package com.echothree.ui.web.main.action.employee;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetCompanyChoicesForm;
import com.echothree.control.user.party.common.result.GetCompanyChoicesResult;
import com.echothree.model.control.party.common.choice.CompanyChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EmployeeLogin")
public class LoginActionForm
        extends BaseActionForm {
    
    private CompanyChoicesBean companyChoices;
    
    private String username;
    private String password;
    private String companyChoice;
    private String returnUrl;
    
    private void setupCompanyChoices() {
        if(companyChoices == null) {
            try {
                GetCompanyChoicesForm commandForm = PartyUtil.getHome().getGetCompanyChoicesForm();
                
                commandForm.setDefaultCompanyChoice(companyChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = PartyUtil.getHome().getCompanyChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCompanyChoicesResult result = (GetCompanyChoicesResult)executionResult.getResult();
                companyChoices = result.getCompanyChoices();
                
                if(companyChoice == null)
                    companyChoice = companyChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, companyChoices remains null, no default
            }
        }
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    
    public List<LabelValueBean> getCompanyChoices() {
        List<LabelValueBean> choices = null;
        
        setupCompanyChoices();
        if(companyChoices != null) {
            choices = convertChoices(companyChoices);
        }
        
        return choices;
    }
    
    public void setCompanyChoice(String companyChoice) {
        this.companyChoice = companyChoice;
    }
    
    public String getCompanyChoice() {
        setupCompanyChoices();
        return companyChoice;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    
}
