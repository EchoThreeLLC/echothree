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

package com.echothree.model.control.uom.remote.transfer;

import com.echothree.util.remote.transfer.BaseTransfer;

public class UnitOfMeasureTypeWeightTransfer
        extends BaseTransfer {
    
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private String weight;
    
    /** Creates a new instance of UnitOfMeasureTypeWeightTransfer */
    public UnitOfMeasureTypeWeightTransfer(UnitOfMeasureTypeTransfer unitOfMeasureType, String weight) {
        this.unitOfMeasureType = unitOfMeasureType;
        this.weight = weight;
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}
