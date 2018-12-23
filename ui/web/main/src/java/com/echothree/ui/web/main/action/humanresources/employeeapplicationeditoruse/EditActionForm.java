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

package com.echothree.ui.web.main.action.humanresources.employeeapplicationeditoruse;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetApplicationEditorChoicesForm;
import com.echothree.control.user.core.common.result.GetApplicationEditorChoicesResult;
import com.echothree.model.control.core.common.choice.ApplicationEditorChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EmployeeApplicationEditorUseEdit")
public class EditActionForm
        extends BaseActionForm {

    private ApplicationEditorChoicesBean applicationEditorChoices;

    private String partyName;
    private String applicationName;
    private String applicationEditorUseName;
    private String editorChoice;
    private String preferredHeight;
    private String preferredWidth;

    private void setupApplicationEditorChoices() {
        if(applicationEditorChoices == null) {
            try {
                GetApplicationEditorChoicesForm commandForm = CoreUtil.getHome().getGetApplicationEditorChoicesForm();
                
                commandForm.setApplicationName(applicationName);
                commandForm.setDefaultEditorChoice(editorChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = CoreUtil.getHome().getApplicationEditorChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetApplicationEditorChoicesResult getApplicationEditorChoicesResult = (GetApplicationEditorChoicesResult)executionResult.getResult();
                applicationEditorChoices = getApplicationEditorChoicesResult.getApplicationEditorChoices();
                
                if(editorChoice == null) {
                    editorChoice = applicationEditorChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, applicationEditorChoices remains null, no default
            }
        }
    }
    
    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationEditorUseName(String applicationEditorUseName) {
        this.applicationEditorUseName = applicationEditorUseName;
    }

    public String getApplicationEditorUseName() {
        return applicationEditorUseName;
    }

    public List<LabelValueBean> getApplicationEditorChoices() {
        List<LabelValueBean> choices = null;
        
        setupApplicationEditorChoices();
        if(applicationEditorChoices != null) {
            choices = convertChoices(applicationEditorChoices);
        }
        
        return choices;
    }
    
    public void setEditorChoice(String editorChoice) {
        this.editorChoice = editorChoice;
    }
    
    public String getEditorChoice() {
        setupApplicationEditorChoices();
        return editorChoice;
    }
    
    public String getPreferredHeight() {
        return preferredHeight;
    }

    public void setPreferredHeight(String preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    public String getPreferredWidth() {
        return preferredWidth;
    }

    public void setPreferredWidth(String preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

}
