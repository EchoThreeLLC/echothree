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

package com.echothree.ui.web.main.action.customer.customercontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.form.GetTelephoneStatusChoicesForm;
import com.echothree.control.user.contact.common.result.GetTelephoneStatusChoicesResult;
import com.echothree.model.control.contact.common.choice.TelephoneStatusChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerContactTelephoneStatus")
public class ContactTelephoneStatusActionForm
        extends BaseActionForm {
    
    private TelephoneStatusChoicesBean telephoneStatusChoices;
    
    private String partyName;
    private String contactMechanismName;
    private String telephoneStatusChoice;
    
    public void setupTelephoneStatusChoices()
            throws NamingException {
        if(telephoneStatusChoices == null) {
            GetTelephoneStatusChoicesForm form = ContactUtil.getHome().getGetTelephoneStatusChoicesForm();

            form.setContactMechanismName(contactMechanismName);
            form.setDefaultTelephoneStatusChoice(telephoneStatusChoice);
            form.setAllowNullChoice(Boolean.FALSE.toString());

            CommandResult commandResult = ContactUtil.getHome().getTelephoneStatusChoices(userVisitPK, form);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetTelephoneStatusChoicesResult result = (GetTelephoneStatusChoicesResult)executionResult.getResult();
            telephoneStatusChoices = result.getTelephoneStatusChoices();

            if(telephoneStatusChoice == null) {
                telephoneStatusChoice = telephoneStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getContactMechanismName() {
        return contactMechanismName;
    }

    public void setContactMechanismName(String contactMechanismName) {
        this.contactMechanismName = contactMechanismName;
    }
    
    public String getTelephoneStatusChoice() {
        return telephoneStatusChoice;
    }
    
    public void setTelephoneStatusChoice(String telephoneStatusChoice) {
        this.telephoneStatusChoice = telephoneStatusChoice;
    }
    
    public List<LabelValueBean> getTelephoneStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupTelephoneStatusChoices();
        if(telephoneStatusChoices != null) {
            choices = convertChoices(telephoneStatusChoices);
        }
        
        return choices;
    }

}
