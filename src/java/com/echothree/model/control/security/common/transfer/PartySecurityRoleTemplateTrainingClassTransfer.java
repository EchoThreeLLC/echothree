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

package com.echothree.model.control.security.common.transfer;

import com.echothree.model.control.training.common.transfer.TrainingClassTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PartySecurityRoleTemplateTrainingClassTransfer
        extends BaseTransfer {
    
    private PartySecurityRoleTemplateTransfer partySecurityRoleTemplate;
    private TrainingClassTransfer trainingClass;
    
    /** Creates a new instance of PartySecurityRoleTemplateTrainingClassTransfer */
    public PartySecurityRoleTemplateTrainingClassTransfer(PartySecurityRoleTemplateTransfer partySecurityRoleTemplate, TrainingClassTransfer trainingClass) {
        this.partySecurityRoleTemplate = partySecurityRoleTemplate;
        this.trainingClass = trainingClass;
    }

    public PartySecurityRoleTemplateTransfer getPartySecurityRoleTemplate() {
        return partySecurityRoleTemplate;
    }

    public void setPartySecurityRoleTemplate(PartySecurityRoleTemplateTransfer partySecurityRoleTemplate) {
        this.partySecurityRoleTemplate = partySecurityRoleTemplate;
    }

    public TrainingClassTransfer getTrainingClass() {
        return trainingClass;
    }

    public void setTrainingClass(TrainingClassTransfer trainingClass) {
        this.trainingClass = trainingClass;
    }
    
}
