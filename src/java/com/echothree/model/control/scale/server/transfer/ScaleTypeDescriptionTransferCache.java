// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.scale.common.transfer.ScaleTypeDescriptionTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleTypeTransfer;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.scale.server.entity.ScaleTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ScaleTypeDescriptionTransferCache
        extends BaseScaleDescriptionTransferCache<ScaleTypeDescription, ScaleTypeDescriptionTransfer> {
    
    /** Creates a new instance of ScaleTypeDescriptionTransferCache */
    public ScaleTypeDescriptionTransferCache(UserVisit userVisit, ScaleControl scaleControl) {
        super(userVisit, scaleControl);
    }
    
    public ScaleTypeDescriptionTransfer getScaleTypeDescriptionTransfer(ScaleTypeDescription scaleTypeDescription) {
        ScaleTypeDescriptionTransfer scaleTypeDescriptionTransfer = get(scaleTypeDescription);
        
        if(scaleTypeDescriptionTransfer == null) {
            ScaleTypeTransfer scaleTypeTransfer = scaleControl.getScaleTypeTransfer(userVisit, scaleTypeDescription.getScaleType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, scaleTypeDescription.getLanguage());
            
            scaleTypeDescriptionTransfer = new ScaleTypeDescriptionTransfer(languageTransfer, scaleTypeTransfer, scaleTypeDescription.getDescription());
            put(scaleTypeDescription, scaleTypeDescriptionTransfer);
        }
        
        return scaleTypeDescriptionTransfer;
    }
    
}
