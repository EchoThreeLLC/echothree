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
import com.echothree.model.control.scale.common.transfer.ScaleUseTypeDescriptionTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleUseTypeTransfer;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.scale.server.entity.ScaleUseTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ScaleUseTypeDescriptionTransferCache
        extends BaseScaleDescriptionTransferCache<ScaleUseTypeDescription, ScaleUseTypeDescriptionTransfer> {
    
    /** Creates a new instance of ScaleUseTypeDescriptionTransferCache */
    public ScaleUseTypeDescriptionTransferCache(UserVisit userVisit, ScaleControl scaleControl) {
        super(userVisit, scaleControl);
    }
    
    public ScaleUseTypeDescriptionTransfer getScaleUseTypeDescriptionTransfer(ScaleUseTypeDescription scaleUseTypeDescription) {
        ScaleUseTypeDescriptionTransfer scaleUseTypeDescriptionTransfer = get(scaleUseTypeDescription);
        
        if(scaleUseTypeDescriptionTransfer == null) {
            ScaleUseTypeTransfer scaleUseTypeTransfer = scaleControl.getScaleUseTypeTransfer(userVisit, scaleUseTypeDescription.getScaleUseType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, scaleUseTypeDescription.getLanguage());
            
            scaleUseTypeDescriptionTransfer = new ScaleUseTypeDescriptionTransfer(languageTransfer, scaleUseTypeTransfer, scaleUseTypeDescription.getDescription());
            put(scaleUseTypeDescription, scaleUseTypeDescriptionTransfer);
        }
        
        return scaleUseTypeDescriptionTransfer;
    }
    
}
