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

package com.echothree.model.control.scale.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleDescriptionTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleTransfer;
import com.echothree.model.control.scale.server.ScaleControl;
import com.echothree.model.data.scale.server.entity.ScaleDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ScaleDescriptionTransferCache
        extends BaseScaleDescriptionTransferCache<ScaleDescription, ScaleDescriptionTransfer> {
    
    /** Creates a new instance of ScaleDescriptionTransferCache */
    public ScaleDescriptionTransferCache(UserVisit userVisit, ScaleControl scaleControl) {
        super(userVisit, scaleControl);
    }
    
    public ScaleDescriptionTransfer getScaleDescriptionTransfer(ScaleDescription scaleDescription) {
        ScaleDescriptionTransfer scaleDescriptionTransfer = get(scaleDescription);
        
        if(scaleDescriptionTransfer == null) {
            ScaleTransfer scaleTransfer = scaleControl.getScaleTransfer(userVisit, scaleDescription.getScale());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, scaleDescription.getLanguage());
            
            scaleDescriptionTransfer = new ScaleDescriptionTransfer(languageTransfer, scaleTransfer, scaleDescription.getDescription());
            put(scaleDescription, scaleDescriptionTransfer);
        }
        
        return scaleDescriptionTransfer;
    }
    
}
