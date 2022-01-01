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

package com.echothree.control.user.uom.common.edit;

import com.echothree.control.user.accounting.common.spec.SymbolPositionSpec;

public interface UnitOfMeasureTypeEdit
        extends UnitOfMeasureTypeDescriptionEdit, SymbolPositionSpec {
    
    String getUnitOfMeasureTypeName();
    void setUnitOfMeasureTypeName(String unitOfMeasureTypeName);
    
    String getSuppressSymbolSeparator();
    void setSuppressSymbolSeparator(String suppressSymbolSeparator);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
