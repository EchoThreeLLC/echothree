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

package com.echothree.ui.web.main.action.shipping.carriercontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.form.GetPostalAddressStatusChoicesForm;
import com.echothree.control.user.contact.common.result.GetPostalAddressStatusChoicesResult;
import com.echothree.model.control.contact.common.choice.PostalAddressStatusChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CarrierContactPostalAddressStatus")
public class ContactPostalAddressStatusActionForm
        extends BaseActionForm {
    
    private PostalAddressStatusChoicesBean postalAddressStatusChoices;
    
    private String partyName;
    private String contactMechanismName;
    private String postalAddressStatusChoice;
    
    public void setupPostalAddressStatusChoices() {
        if(postalAddressStatusChoices == null) {
            try {
                GetPostalAddressStatusChoicesForm form = ContactUtil.getHome().getGetPostalAddressStatusChoicesForm();
                
                form.setContactMechanismName(contactMechanismName);
                form.setDefaultPostalAddressStatusChoice(postalAddressStatusChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = ContactUtil.getHome().getPostalAddressStatusChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPostalAddressStatusChoicesResult result = (GetPostalAddressStatusChoicesResult)executionResult.getResult();
                postalAddressStatusChoices = result.getPostalAddressStatusChoices();
                
                if(postalAddressStatusChoice == null) {
                    postalAddressStatusChoice = postalAddressStatusChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, postalAddressStatusChoices remains null, no default
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
    
    public String getPostalAddressStatusChoice() {
        return postalAddressStatusChoice;
    }
    
    public void setPostalAddressStatusChoice(String postalAddressStatusChoice) {
        this.postalAddressStatusChoice = postalAddressStatusChoice;
    }
    
    public List<LabelValueBean> getPostalAddressStatusChoices() {
        List<LabelValueBean> choices = null;
        
        setupPostalAddressStatusChoices();
        if(postalAddressStatusChoices != null) {
            choices = convertChoices(postalAddressStatusChoices);
        }
        
        return choices;
    }

}
