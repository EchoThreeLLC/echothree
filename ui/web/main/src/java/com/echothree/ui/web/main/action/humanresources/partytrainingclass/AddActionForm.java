// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.humanresources.partytrainingclass;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.form.GetTrainingClassChoicesForm;
import com.echothree.control.user.training.common.result.GetTrainingClassChoicesResult;
import com.echothree.model.control.training.common.choice.TrainingClassChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PartyTrainingClassAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private TrainingClassChoicesBean trainingClassChoices;
    
    private String partyName;
    private String trainingClassChoice;
    private String completedTime;
    private String validUntilTime;
    
    public void setupTrainingClassChoices() {
        if(trainingClassChoices == null) {
            try {
                GetTrainingClassChoicesForm form = TrainingUtil.getHome().getGetTrainingClassChoicesForm();
                
                form.setDefaultTrainingClassChoice(trainingClassChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = TrainingUtil.getHome().getTrainingClassChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetTrainingClassChoicesResult result = (GetTrainingClassChoicesResult)executionResult.getResult();
                trainingClassChoices = result.getTrainingClassChoices();
                
                if(trainingClassChoice == null)
                    trainingClassChoice = trainingClassChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, trainingClassChoices remains null, no default
            }
        }
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public String getTrainingClassChoice() {
        return trainingClassChoice;
    }
    
    public void setTrainingClassChoice(String trainingClassChoice) {
        this.trainingClassChoice = trainingClassChoice;
    }
    
    public List<LabelValueBean> getTrainingClassChoices() {
        List<LabelValueBean> choices = null;
        
        setupTrainingClassChoices();
        if(trainingClassChoices != null)
            choices = convertChoices(trainingClassChoices);
        
        return choices;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getValidUntilTime() {
        return validUntilTime;
    }

    public void setValidUntilTime(String validUntilTime) {
        this.validUntilTime = validUntilTime;
    }
    
}
