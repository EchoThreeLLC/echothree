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

package com.echothree.model.control.scale.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PartyScaleUseTransfer
        extends BaseTransfer {
    
    private PartyTransfer party;
    private ScaleUseTypeTransfer scaleUseType;
    private ScaleTransfer scale;
    
    /** Creates a new instance of PartyScaleUseTransfer */
    public PartyScaleUseTransfer(PartyTransfer party, ScaleUseTypeTransfer scaleUseType,
            ScaleTransfer scale) {
        this.party = party;
        this.scaleUseType = scaleUseType;
        this.scale = scale;
    }
    
    public PartyTransfer getParty() {
        return party;
    }
    
    public void setParty(PartyTransfer party) {
        this.party = party;
    }
    
    public ScaleUseTypeTransfer getScaleUseType() {
        return scaleUseType;
    }
    
    public void setScaleUseType(ScaleUseTypeTransfer scaleUseType) {
        this.scaleUseType = scaleUseType;
    }
    
    public ScaleTransfer getScale() {
        return scale;
    }
    
    public void setScale(ScaleTransfer scale) {
        this.scale = scale;
    }
    
}
