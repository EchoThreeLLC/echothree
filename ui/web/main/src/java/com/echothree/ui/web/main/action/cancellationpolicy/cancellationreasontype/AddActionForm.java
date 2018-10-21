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

package com.echothree.ui.web.main.action.cancellationpolicy.cancellationreasontype;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.remote.form.GetCancellationTypeChoicesForm;
import com.echothree.control.user.cancellationpolicy.remote.result.GetCancellationTypeChoicesResult;
import com.echothree.model.control.cancellationpolicy.remote.choice.CancellationTypeChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CancellationReasonTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CancellationTypeChoicesBean cancellationTypeChoices;
    
    private String cancellationKindName;
    private String cancellationReasonName;
    private String cancellationTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupCancellationTypeChoices() {
        if(cancellationTypeChoices == null) {
            try {
                GetCancellationTypeChoicesForm form = CancellationPolicyUtil.getHome().getGetCancellationTypeChoicesForm();
                
                form.setCancellationKindName(cancellationKindName);
                form.setDefaultCancellationTypeChoice(cancellationTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = CancellationPolicyUtil.getHome().getCancellationTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCancellationTypeChoicesResult getCancellationTypeChoicesResult = (GetCancellationTypeChoicesResult)executionResult.getResult();
                cancellationTypeChoices = getCancellationTypeChoicesResult.getCancellationTypeChoices();
                
                if(cancellationTypeChoice == null) {
                    cancellationTypeChoice = cancellationTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, cancellationTypeChoices remains null, no default
            }
        }
    }
    
    public String getCancellationKindName() {
        return cancellationKindName;
    }
    
    public void setCancellationKindName(String cancellationKindName) {
        this.cancellationKindName = cancellationKindName;
    }
    
    public String getCancellationReasonName() {
        return cancellationReasonName;
    }
    
    public void setCancellationReasonName(String cancellationReasonName) {
        this.cancellationReasonName = cancellationReasonName;
    }
    
    public String getCancellationTypeChoice() {
        setupCancellationTypeChoices();
        
        return cancellationTypeChoice;
    }
    
    public void setCancellationTypeChoice(String cancellationTypeChoice) {
        this.cancellationTypeChoice = cancellationTypeChoice;
    }
    
    public List<LabelValueBean> getCancellationTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupCancellationTypeChoices();
        if(cancellationTypeChoices != null) {
            choices = convertChoices(cancellationTypeChoices);
        }
        
        return choices;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = Boolean.FALSE;
    }
    
}
