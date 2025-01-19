// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.offer.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class UseNameElementTransfer
        extends BaseTransfer {
    
    private String useNameElementName;
    private Integer offset;
    private Integer length;
    private String validationPattern;
    private String description;
    
    /** Creates a new instance of UseNameElementTransfer */
    public UseNameElementTransfer(String useNameElementName, Integer offset, Integer length, String validationPattern, String description) {
        this.useNameElementName = useNameElementName;
        this.offset = offset;
        this.length = length;
        this.validationPattern = validationPattern;
        this.description = description;
    }
    
    public String getUseNameElementName() {
        return useNameElementName;
    }
    
    public void setUseNameElementName(String useNameElementName) {
        this.useNameElementName = useNameElementName;
    }
    
    public Integer getOffset() {
        return offset;
    }
    
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    
    public Integer getLength() {
        return length;
    }
    
    public void setLength(Integer length) {
        this.length = length;
    }
    
    public String getValidationPattern() {
        return validationPattern;
    }
    
    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
