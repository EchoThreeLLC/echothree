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

package com.echothree.ui.web.main.action.customer.customercontactlist;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.remote.form.GetContactMechanismPurposeChoicesForm;
import com.echothree.control.user.contact.remote.result.GetContactMechanismPurposeChoicesResult;
import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.remote.form.GetContactListChoicesForm;
import com.echothree.control.user.contactlist.remote.result.GetContactListChoicesResult;
import com.echothree.model.control.contact.remote.choice.ContactMechanismPurposeChoicesBean;
import com.echothree.model.control.contactlist.remote.choice.ContactListChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerContactListAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ContactListChoicesBean contactListChoices;
    private ContactMechanismPurposeChoicesBean preferredContactMechanismPurposeChoices;

    private String partyName;
    private String contactListChoice;
    private String preferredContactMechanismPurposeChoice;

    public void setupContactListChoices() {
        if(contactListChoices == null) {
            try {
                GetContactListChoicesForm form = ContactListUtil.getHome().getGetContactListChoicesForm();

                form.setDefaultContactListChoice(contactListChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = ContactListUtil.getHome().getContactListChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetContactListChoicesResult getContactListChoicesResult = (GetContactListChoicesResult)executionResult.getResult();
                contactListChoices = getContactListChoicesResult.getContactListChoices();
                
                if(contactListChoice == null) {
                    contactListChoice = contactListChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, contactListChoices remains null, no default
            }
        }
    }
    
    public void setupPreferredContactMechanismPurposeChoices() {
        if(preferredContactMechanismPurposeChoices == null) {
            try {
                GetContactMechanismPurposeChoicesForm form = ContactUtil.getHome().getGetContactMechanismPurposeChoicesForm();

                form.setDefaultContactMechanismPurposeChoice(preferredContactMechanismPurposeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = ContactUtil.getHome().getContactMechanismPurposeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetContactMechanismPurposeChoicesResult getContactMechanismPurposeChoicesResult = (GetContactMechanismPurposeChoicesResult)executionResult.getResult();
                preferredContactMechanismPurposeChoices = getContactMechanismPurposeChoicesResult.getContactMechanismPurposeChoices();
                
                if(preferredContactMechanismPurposeChoice == null) {
                    preferredContactMechanismPurposeChoice = preferredContactMechanismPurposeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, preferredContactMechanismPurposeChoices remains null, no default
            }
        }
    }
    
    /**
     * @return the partyName
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public List<LabelValueBean> getContactListChoices() {
        List<LabelValueBean> choices = null;
        
        setupContactListChoices();
        if(contactListChoices != null) {
            choices = convertChoices(contactListChoices);
        }
        
        return choices;
    }
    
    public void setContactListChoice(String contactListChoice) {
        this.contactListChoice = contactListChoice;
    }
    
    public String getContactListChoice() {
        setupContactListChoices();
        return contactListChoice;
    }

    public List<LabelValueBean> getPreferredContactMechanismPurposeChoices() {
        List<LabelValueBean> choices = null;
        
        setupPreferredContactMechanismPurposeChoices();
        if(preferredContactMechanismPurposeChoices != null) {
            choices = convertChoices(preferredContactMechanismPurposeChoices);
        }
        
        return choices;
    }
    
    public void setPreferredContactMechanismPurposeChoice(String preferredContactMechanismPurposeChoice) {
        this.preferredContactMechanismPurposeChoice = preferredContactMechanismPurposeChoice;
    }
    
    public String getPreferredContactMechanismPurposeChoice() {
        setupPreferredContactMechanismPurposeChoices();
        return preferredContactMechanismPurposeChoice;
    }

}
