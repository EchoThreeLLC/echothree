// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.communication.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CommunicationSourceDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private CommunicationSourceTransfer communicationSource;
    private String description;
    
    /** Creates a new instance of CommunicationSourceDescriptionTransfer */
    public CommunicationSourceDescriptionTransfer(LanguageTransfer language, CommunicationSourceTransfer communicationSource, String description) {
        this.language = language;
        this.communicationSource = communicationSource;
        this.description = description;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public CommunicationSourceTransfer getCommunicationSource() {
        return communicationSource;
    }
    
    public void setCommunicationSource(CommunicationSourceTransfer communicationSource) {
        this.communicationSource = communicationSource;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
