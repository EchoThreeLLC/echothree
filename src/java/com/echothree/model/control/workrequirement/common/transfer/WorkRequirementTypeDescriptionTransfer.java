// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.workrequirement.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class WorkRequirementTypeDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private WorkRequirementTypeTransfer workRequirementType;
    private String description;
    
    /** Creates a new instance of WorkRequirementTypeDescriptionTransfer */
    public WorkRequirementTypeDescriptionTransfer(LanguageTransfer language, WorkRequirementTypeTransfer workRequirementType, String description) {
        this.language = language;
        this.workRequirementType = workRequirementType;
        this.description = description;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public WorkRequirementTypeTransfer getWorkRequirementType() {
        return workRequirementType;
    }
    
    public void setWorkRequirementType(WorkRequirementTypeTransfer workRequirementType) {
        this.workRequirementType = workRequirementType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
