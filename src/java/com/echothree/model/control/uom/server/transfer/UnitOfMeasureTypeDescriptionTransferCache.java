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

package com.echothree.model.control.uom.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeDescriptionTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UnitOfMeasureTypeDescriptionTransferCache
        extends BaseUomDescriptionTransferCache<UnitOfMeasureTypeDescription, UnitOfMeasureTypeDescriptionTransfer> {
    
    /** Creates a new instance of UnitOfMeasureTypeDescriptionTransferCache */
    public UnitOfMeasureTypeDescriptionTransferCache(UserVisit userVisit, UomControl uomControl) {
        super(userVisit, uomControl);
    }
    
    public UnitOfMeasureTypeDescriptionTransfer getUnitOfMeasureTypeDescriptionTransfer(UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        UnitOfMeasureTypeDescriptionTransfer unitOfMeasureTypeDescriptionTransfer = get(unitOfMeasureTypeDescription);
        
        if(unitOfMeasureTypeDescriptionTransfer == null) {
            UnitOfMeasureTypeTransferCache unitOfMeasureTypeTransferCache = uomControl.getUomTransferCaches(userVisit).getUnitOfMeasureTypeTransferCache();
            UnitOfMeasureTypeTransfer unitOfMeasureTypeTransfer = unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(unitOfMeasureTypeDescription.getUnitOfMeasureType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, unitOfMeasureTypeDescription.getLanguage());
            
            unitOfMeasureTypeDescriptionTransfer = new UnitOfMeasureTypeDescriptionTransfer(languageTransfer,
                    unitOfMeasureTypeTransfer, unitOfMeasureTypeDescription.getSingularDescription(),
                    unitOfMeasureTypeDescription.getPluralDescription(), unitOfMeasureTypeDescription.getSymbol());
            put(unitOfMeasureTypeDescription, unitOfMeasureTypeDescriptionTransfer);
        }
        
        return unitOfMeasureTypeDescriptionTransfer;
    }
    
}
