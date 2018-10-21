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

package com.echothree.model.control.shipment.remote.transfer;

import com.echothree.model.control.sequence.remote.transfer.SequenceTypeTransfer;
import com.echothree.model.control.workflow.remote.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.remote.transfer.WorkflowTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class ShipmentTypeTransfer
        extends BaseTransfer {
    
    private String shipmentTypeName;
    private ShipmentTypeTransfer parentShipmentType;
    private SequenceTypeTransfer shipmentSequenceType;
    private WorkflowTransfer shipmentWorkflow;
    private WorkflowEntranceTransfer shipmentWorkflowEntrance;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ShipmentTypeTransfer */
    public ShipmentTypeTransfer(String shipmentTypeName, ShipmentTypeTransfer parentShipmentType, SequenceTypeTransfer shipmentSequenceType,
            WorkflowTransfer shipmentWorkflow, WorkflowEntranceTransfer shipmentWorkflowEntrance, Boolean isDefault, Integer sortOrder,
            String description) {
        this.shipmentTypeName = shipmentTypeName;
        this.parentShipmentType = parentShipmentType;
        this.shipmentSequenceType = shipmentSequenceType;
        this.shipmentWorkflow = shipmentWorkflow;
        this.shipmentWorkflowEntrance = shipmentWorkflowEntrance;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public String getShipmentTypeName() {
        return shipmentTypeName;
    }

    public void setShipmentTypeName(String shipmentTypeName) {
        this.shipmentTypeName = shipmentTypeName;
    }

    public ShipmentTypeTransfer getParentShipmentType() {
        return parentShipmentType;
    }

    public void setParentShipmentType(ShipmentTypeTransfer parentShipmentType) {
        this.parentShipmentType = parentShipmentType;
    }

    public SequenceTypeTransfer getShipmentSequenceType() {
        return shipmentSequenceType;
    }

    public void setShipmentSequenceType(SequenceTypeTransfer shipmentSequenceType) {
        this.shipmentSequenceType = shipmentSequenceType;
    }

    public WorkflowTransfer getShipmentWorkflow() {
        return shipmentWorkflow;
    }

    public void setShipmentWorkflow(WorkflowTransfer shipmentWorkflow) {
        this.shipmentWorkflow = shipmentWorkflow;
    }

    public WorkflowEntranceTransfer getShipmentWorkflowEntrance() {
        return shipmentWorkflowEntrance;
    }

    public void setShipmentWorkflowEntrance(WorkflowEntranceTransfer shipmentWorkflowEntrance) {
        this.shipmentWorkflowEntrance = shipmentWorkflowEntrance;
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
