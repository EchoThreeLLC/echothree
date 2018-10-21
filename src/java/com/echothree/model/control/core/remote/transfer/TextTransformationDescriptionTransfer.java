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

package com.echothree.model.control.core.remote.transfer;

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class TextTransformationDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private TextTransformationTransfer textTransformation;
    private String description;
    
    /** Creates a new instance of TextTransformationDescriptionTransfer */
    public TextTransformationDescriptionTransfer(LanguageTransfer language, TextTransformationTransfer textTransformation, String description) {
        this.language = language;
        this.textTransformation = textTransformation;
        this.description = description;
    }

    /**
     * @return the language
     */
    public LanguageTransfer getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    /**
     * @return the textTransformation
     */
    public TextTransformationTransfer getTextTransformation() {
        return textTransformation;
    }

    /**
     * @param textTransformation the textTransformation to set
     */
    public void setTextTransformation(TextTransformationTransfer textTransformation) {
        this.textTransformation = textTransformation;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
