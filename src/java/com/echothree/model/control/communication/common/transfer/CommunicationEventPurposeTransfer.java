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

package com.echothree.model.control.communication.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class CommunicationEventPurposeTransfer
        extends BaseTransfer {
    
    private String communicationEventPurposeName;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of CommunicationEventPurposeTransfer */
    public CommunicationEventPurposeTransfer(String communicationEventPurposeName, Boolean isDefault, Integer sortOrder, String description) {
        this.communicationEventPurposeName = communicationEventPurposeName;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getCommunicationEventPurposeName() {
        return communicationEventPurposeName;
    }
    
    public void setCommunicationEventPurposeName(String communicationEventPurposeName) {
        this.communicationEventPurposeName = communicationEventPurposeName;
    }
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
