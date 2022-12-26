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

package com.echothree.view.client.web.struts;

import com.echothree.control.user.party.client.helper.NameSuffixesHelper;
import com.echothree.control.user.party.client.helper.PersonalTitlesHelper;
import com.echothree.model.control.party.common.choice.NameSuffixChoicesBean;
import com.echothree.model.control.party.common.choice.PersonalTitleChoicesBean;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

public class BasePersonActionForm
        extends BasePartyActionForm {
    
    private PersonalTitleChoicesBean personalTitleChoices = null;
    private NameSuffixChoicesBean nameSuffixChoices = null;
    
    private String personalTitleChoice = null;
    private String firstName = null;
    private String middleName = null;
    private String lastName = null;
    private String nameSuffixChoice = null;
    
    /** Creates a new instance of BasePersonActionForm */
    public BasePersonActionForm() {
        super();
    }
    
    private void setupPersonalTitleChoices() {
        if(personalTitleChoices == null) {
            try {
                personalTitleChoices = PersonalTitlesHelper.getInstance().getPersonalTitleChoices(userVisitPK, Boolean.TRUE);

                if(personalTitleChoice == null) {
                    personalTitleChoice = personalTitleChoices == null ? null : personalTitleChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, personalTitleChoices remains null, no default
            }
        }
    }
    
    private void setupNameSuffixChoices() {
        if(nameSuffixChoices == null) {
            try {
                nameSuffixChoices = NameSuffixesHelper.getInstance().getNameSuffixChoices(userVisitPK, Boolean.TRUE);
                
                if(nameSuffixChoice == null) {
                    nameSuffixChoice = nameSuffixChoices == null ? null : nameSuffixChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, nameSuffixChoices remains null, no default
            }
        }
    }
    
    public List<LabelValueBean> getPersonalTitleChoices() {
        List<LabelValueBean> choices = null;
        
        setupPersonalTitleChoices();
        if(personalTitleChoices != null) {
            choices = convertChoices(personalTitleChoices);
        }
        
        return choices;
    }
    
    public void setPersonalTitleChoice(String personalTitleChoice) {
        this.personalTitleChoice = personalTitleChoice;
    }
    
    public String getPersonalTitleChoice() {
        setupPersonalTitleChoices();
        
        return personalTitleChoice;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public List<LabelValueBean> getNameSuffixChoices() {
        List<LabelValueBean> choices = null;
        
        setupNameSuffixChoices();
        if(nameSuffixChoices != null) {
            choices = convertChoices(nameSuffixChoices);
        }
        
        return choices;
    }
    
    public void setNameSuffixChoice(String nameSuffixChoice) {
        this.nameSuffixChoice = nameSuffixChoice;
    }
    
    public String getNameSuffixChoice() {
        setupNameSuffixChoices();
        
        return nameSuffixChoice;
    }
    
}
