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

package com.echothree.ui.web.main.action.core.applicationeditor;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetEditorChoicesForm;
import com.echothree.control.user.core.common.result.GetEditorChoicesResult;
import com.echothree.model.control.core.common.choice.EditorChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ApplicationEditorAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private EditorChoicesBean editorChoices;
    
    private String applicationName;
    private String editorChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupEditorChoices()
            throws NamingException {
        if(editorChoices == null) {
            GetEditorChoicesForm commandForm = CoreUtil.getHome().getGetEditorChoicesForm();

            commandForm.setDefaultEditorChoice(editorChoice);
            commandForm.setAllowNullChoice(Boolean.FALSE.toString());

            CommandResult commandResult = CoreUtil.getHome().getEditorChoices(userVisitPK, commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEditorChoicesResult getEditorChoicesResult = (GetEditorChoicesResult)executionResult.getResult();
            editorChoices = getEditorChoicesResult.getEditorChoices();

            if(editorChoice == null) {
                editorChoice = editorChoices.getDefaultValue();
            }
        }
    }
    
    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<LabelValueBean> getEditorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEditorChoices();
        if(editorChoices != null) {
            choices = convertChoices(editorChoices);
        }
        
        return choices;
    }
    
    public void setEditorChoice(String editorChoice) {
        this.editorChoice = editorChoice;
    }
    
    public String getEditorChoice()
            throws NamingException {
        setupEditorChoices();
        return editorChoice;
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
