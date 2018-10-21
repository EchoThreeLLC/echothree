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

package com.echothree.ui.web.main.action.cancellationpolicy.cancellationpolicy;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.remote.form.GetMimeTypeChoicesForm;
import com.echothree.control.user.core.remote.result.GetMimeTypeChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.remote.choice.MimeTypeChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CancellationPolicyAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private MimeTypeChoicesBean policyMimeTypeChoices;

    private String cancellationKindName;
    private String cancellationPolicyName;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    private String policyMimeTypeChoice;
    private String policy;
    
    private void setupPolicyMimeTypeChoices() {
        if(policyMimeTypeChoices == null) {
            try {
                GetMimeTypeChoicesForm commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

                commandForm.setDefaultMimeTypeChoice(policyMimeTypeChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

                CommandResult commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetMimeTypeChoicesResult result = (GetMimeTypeChoicesResult)executionResult.getResult();
                policyMimeTypeChoices = result.getMimeTypeChoices();

                if(policyMimeTypeChoice == null) {
                    policyMimeTypeChoice = policyMimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, policyMimeTypeChoices remains null, no default
            }
        }
    }

    public void setCancellationKindName(String cancellationKindName) {
        this.cancellationKindName = cancellationKindName;
    }
    
    public String getCancellationKindName() {
        return cancellationKindName;
    }
    
    public void setCancellationPolicyName(String cancellationPolicyName) {
        this.cancellationPolicyName = cancellationPolicyName;
    }
    
    public String getCancellationPolicyName() {
        return cancellationPolicyName;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<LabelValueBean> getPolicyMimeTypeChoices() {
        List<LabelValueBean> choices = null;

        setupPolicyMimeTypeChoices();
        if(policyMimeTypeChoices != null) {
            choices = convertChoices(policyMimeTypeChoices);
        }

        return choices;
    }

    public void setPolicyMimeTypeChoice(String policyMimeTypeChoice) {
        this.policyMimeTypeChoice = policyMimeTypeChoice;
    }

    public String getPolicyMimeTypeChoice() {
        setupPolicyMimeTypeChoices();

        return policyMimeTypeChoice;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = Boolean.FALSE;
    }
    
}
