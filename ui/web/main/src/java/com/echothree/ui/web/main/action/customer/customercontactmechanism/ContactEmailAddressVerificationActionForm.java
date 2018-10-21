// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.contact.remote.form.GetEmailAddressVerificationChoicesForm;
import com.echothree.control.user.contact.remote.result.GetEmailAddressVerificationChoicesResult;
import com.echothree.model.control.contact.remote.choice.EmailAddressVerificationChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerContactEmailAddressVerification")
public class ContactEmailAddressVerificationActionForm
        extends BaseActionForm {
    
    private EmailAddressVerificationChoicesBean emailAddressVerificationChoices;
    
    private String partyName;
    private String contactMechanismName;
    private String emailAddressVerificationChoice;
    
    public void setupEmailAddressVerificationChoices() {
        if(emailAddressVerificationChoices == null) {
            try {
                GetEmailAddressVerificationChoicesForm form = ContactUtil.getHome().getGetEmailAddressVerificationChoicesForm();
                
                form.setContactMechanismName(contactMechanismName);
                form.setDefaultEmailAddressVerificationChoice(emailAddressVerificationChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = ContactUtil.getHome().getEmailAddressVerificationChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetEmailAddressVerificationChoicesResult result = (GetEmailAddressVerificationChoicesResult)executionResult.getResult();
                emailAddressVerificationChoices = result.getEmailAddressVerificationChoices();
                
                if(emailAddressVerificationChoice == null) {
                    emailAddressVerificationChoice = emailAddressVerificationChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, emailAddressVerificationChoices remains null, no default
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
    
    public String getEmailAddressVerificationChoice() {
        return emailAddressVerificationChoice;
    }
    
    public void setEmailAddressVerificationChoice(String emailAddressVerificationChoice) {
        this.emailAddressVerificationChoice = emailAddressVerificationChoice;
    }
    
    public List<LabelValueBean> getEmailAddressVerificationChoices() {
        List<LabelValueBean> choices = null;
        
        setupEmailAddressVerificationChoices();
        if(emailAddressVerificationChoices != null) {
            choices = convertChoices(emailAddressVerificationChoices);
        }
        
        return choices;
    }

}
