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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetMimeTypeChoicesForm;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.icon.common.IconUtil;
import com.echothree.control.user.icon.common.form.GetIconChoicesForm;
import com.echothree.control.user.icon.common.result.GetIconChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetBirthdayFormatChoicesForm;
import com.echothree.control.user.party.common.form.GetGenderChoicesForm;
import com.echothree.control.user.party.common.result.GetBirthdayFormatChoicesResult;
import com.echothree.control.user.party.common.result.GetGenderChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.common.choice.IconChoicesBean;
import com.echothree.model.control.party.common.choice.BirthdayFormatChoicesBean;
import com.echothree.model.control.party.common.choice.GenderChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerProfileAdd")
public class CustomerProfileAddActionForm
        extends BaseLanguageActionForm {
    
    private IconChoicesBean iconChoices;
    private GenderChoicesBean genderChoices;
    private BirthdayFormatChoicesBean birthdayFormatChoices;
    private MimeTypeChoicesBean bioMimeTypeChoices;
    private MimeTypeChoicesBean signatureMimeTypeChoices;
    
    private String partyName;
    private String customerName;
    private String nickname;
    private String iconChoice;
    private String genderChoice;
    private String birthday;
    private String birthdayFormatChoice;
    private String occupation;
    private String hobbies;
    private String location;
    private String bioMimeTypeChoice;
    private String bio;
    private String signatureMimeTypeChoice;
    private String signature;
    
    private void setupIconChoices()
            throws NamingException {
        if(iconChoices == null) {
            GetIconChoicesForm commandForm = IconUtil.getHome().getGetIconChoicesForm();

            commandForm.setIconUsageTypeName(IconConstants.IconUsageType_PROFILE);
            commandForm.setDefaultIconChoice(iconChoice);
            commandForm.setAllowNullChoice(Boolean.TRUE.toString());

            CommandResult commandResult = IconUtil.getHome().getIconChoices(userVisitPK, commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetIconChoicesResult result = (GetIconChoicesResult)executionResult.getResult();
            iconChoices = result.getIconChoices();

            if(iconChoice == null) {
                iconChoice = iconChoices.getDefaultValue();
            }
        }
    }
    
    private void setupGenderChoices()
            throws NamingException {
        if(genderChoices == null) {
            GetGenderChoicesForm commandForm = PartyUtil.getHome().getGetGenderChoicesForm();

            commandForm.setDefaultGenderChoice(genderChoice);
            commandForm.setAllowNullChoice(Boolean.TRUE.toString());

            CommandResult commandResult = PartyUtil.getHome().getGenderChoices(userVisitPK, commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetGenderChoicesResult result = (GetGenderChoicesResult)executionResult.getResult();
            genderChoices = result.getGenderChoices();

            if(genderChoice == null) {
                genderChoice = genderChoices.getDefaultValue();
            }
        }
    }
    
    private void setupBirthdayFormatChoices()
            throws NamingException {
        if(birthdayFormatChoices == null) {
            GetBirthdayFormatChoicesForm commandForm = PartyUtil.getHome().getGetBirthdayFormatChoicesForm();

            commandForm.setDefaultBirthdayFormatChoice(birthdayFormatChoice);
            commandForm.setAllowNullChoice(Boolean.FALSE.toString());

            CommandResult commandResult = PartyUtil.getHome().getBirthdayFormatChoices(userVisitPK, commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetBirthdayFormatChoicesResult result = (GetBirthdayFormatChoicesResult)executionResult.getResult();
            birthdayFormatChoices = result.getBirthdayFormatChoices();

            if(birthdayFormatChoice == null) {
                birthdayFormatChoice = birthdayFormatChoices.getDefaultValue();
            }
        }
    }

    private void setupBioMimeTypeChoices()
            throws NamingException {
        if(bioMimeTypeChoices == null) {
            GetMimeTypeChoicesForm commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            commandForm.setDefaultMimeTypeChoice(bioMimeTypeChoice);
            commandForm.setAllowNullChoice(Boolean.TRUE.toString());
            commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

            CommandResult commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetMimeTypeChoicesResult result = (GetMimeTypeChoicesResult)executionResult.getResult();
            bioMimeTypeChoices = result.getMimeTypeChoices();

            if(bioMimeTypeChoice == null) {
                bioMimeTypeChoice = bioMimeTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupSignatureMimeTypeChoices()
            throws NamingException {
        if(signatureMimeTypeChoices == null) {
            GetMimeTypeChoicesForm commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            commandForm.setDefaultMimeTypeChoice(signatureMimeTypeChoice);
            commandForm.setAllowNullChoice(Boolean.TRUE.toString());
            commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

            CommandResult commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetMimeTypeChoicesResult result = (GetMimeTypeChoicesResult)executionResult.getResult();
            signatureMimeTypeChoices = result.getMimeTypeChoices();

            if(signatureMimeTypeChoice == null) {
                signatureMimeTypeChoice = signatureMimeTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public List<LabelValueBean> getIconChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupIconChoices();
        if(iconChoices != null) {
            choices = convertChoices(iconChoices);
        }
        
        return choices;
    }
    
    public void setIconChoice(String iconChoice) {
        this.iconChoice = iconChoice;
    }
    
    public String getIconChoice()
            throws NamingException {
        setupIconChoices();
        
        return iconChoice;
    }
    
    public List<LabelValueBean> getGenderChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupGenderChoices();
        if(genderChoices != null) {
            choices = convertChoices(genderChoices);
        }
        
        return choices;
    }
    
    public void setGenderChoice(String genderChoice) {
        this.genderChoice = genderChoice;
    }
    
    public String getGenderChoice()
            throws NamingException {
        setupGenderChoices();
        
        return genderChoice;
    }

    public String getBirthday() {
        return birthday;
    }
    
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    public List<LabelValueBean> getBirthdayFormatChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupBirthdayFormatChoices();
        if(birthdayFormatChoices != null) {
            choices = convertChoices(birthdayFormatChoices);
        }

        return choices;
    }

    public void setBirthdayFormatChoice(String birthdayFormatChoice) {
        this.birthdayFormatChoice = birthdayFormatChoice;
    }

    public String getBirthdayFormatChoice()
            throws NamingException {
        setupBirthdayFormatChoices();

        return birthdayFormatChoice;
    }

    public String getOccupation() {
        return occupation;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public String getHobbies() {
        return hobbies;
    }
    
    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public List<LabelValueBean> getBioMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupBioMimeTypeChoices();
        if(bioMimeTypeChoices != null) {
            choices = convertChoices(bioMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setBioMimeTypeChoice(String bioMimeTypeChoice) {
        this.bioMimeTypeChoice = bioMimeTypeChoice;
    }
    
    public String getBioMimeTypeChoice()
            throws NamingException {
        setupBioMimeTypeChoices();
        
        return bioMimeTypeChoice;
    }
    
    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public List<LabelValueBean> getSignatureMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupSignatureMimeTypeChoices();
        if(signatureMimeTypeChoices != null) {
            choices = convertChoices(signatureMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setSignatureMimeTypeChoice(String signatureMimeTypeChoice) {
        this.signatureMimeTypeChoice = signatureMimeTypeChoice;
    }
    
    public String getSignatureMimeTypeChoice()
            throws NamingException {
        setupSignatureMimeTypeChoices();
        
        return signatureMimeTypeChoice;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
