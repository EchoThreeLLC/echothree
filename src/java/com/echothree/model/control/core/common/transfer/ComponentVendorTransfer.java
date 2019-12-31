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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ComponentVendorTransfer
        extends BaseTransfer {
    
    String componentVendorName;
    String description;
    
    /** Creates a new instance of ComponentVendorTransfer */
    public ComponentVendorTransfer(String componentVendorName, String description) {
        this.componentVendorName = componentVendorName;
        this.description = description;
    }
    
    public String getComponentVendorName() {
        return componentVendorName;
    }
    
    public String getDescription() {
        return description;
    }
    
}
