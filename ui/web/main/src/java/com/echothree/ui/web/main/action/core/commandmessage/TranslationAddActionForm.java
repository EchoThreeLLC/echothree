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

package com.echothree.ui.web.main.action.core.commandmessage;

import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="CommandMessageTranslationAdd")
public class TranslationAddActionForm
        extends BaseLanguageActionForm {
    
    private String commandMessageTypeName;
    private String commandMessageKey;
    private String translation;
    
    public void setCommandMessageTypeName(String commandMessageTypeName) {
        this.commandMessageTypeName = commandMessageTypeName;
    }
    
    public String getCommandMessageTypeName() {
        return commandMessageTypeName;
    }
    
    public void setCommandMessageKey(String commandMessageKey) {
        this.commandMessageKey = commandMessageKey;
    }
    
    public String getCommandMessageKey() {
        return commandMessageKey;
    }
    
    public String getTranslation() {
        return translation;
    }
    
    public void setTranslation(String translation) {
        this.translation = translation;
    }
    
}
