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

package com.echothree.model.control.workeffort.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class WorkEffortTypeTransfer
        extends BaseTransfer {
    
    private String workEffortTypeName;
    private EntityTypeTransfer entityType;
    private SequenceTransfer workEffortSequence;
    private Long unformattedScheduledTime;
    private String scheduledTime;
    private Long unformattedEstimatedTimeAllowed;
    private String estimatedTimeAllowed;
    private Long unformattedMaximumTimeAllowed;
    private String maximumTimeAllowed;
    private Integer sortOrder;
    private String description;
    
    private ListWrapper<WorkEffortScopeTransfer> workEffortScopes;
    private ListWrapper<WorkRequirementTypeTransfer> workRequirementTypes;

    /** Creates a new instance of WorkEffortTypeTransfer */
    public WorkEffortTypeTransfer(String workEffortTypeName, EntityTypeTransfer entityType, SequenceTransfer workEffortSequence, Long unformattedScheduledTime,
            String scheduledTime, Long unformattedEstimatedTimeAllowed, String estimatedTimeAllowed, Long unformattedMaximumTimeAllowed,
            String maximumTimeAllowed, Integer sortOrder, String description) {
        this.workEffortTypeName = workEffortTypeName;
        this.entityType = entityType;
        this.workEffortSequence = workEffortSequence;
        this.unformattedScheduledTime = unformattedScheduledTime;
        this.scheduledTime = scheduledTime;
        this.unformattedEstimatedTimeAllowed = unformattedEstimatedTimeAllowed;
        this.estimatedTimeAllowed = estimatedTimeAllowed;
        this.unformattedMaximumTimeAllowed = unformattedMaximumTimeAllowed;
        this.maximumTimeAllowed = maximumTimeAllowed;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the workEffortTypeName
     */
    public String getWorkEffortTypeName() {
        return workEffortTypeName;
    }

    /**
     * @param workEffortTypeName the workEffortTypeName to set
     */
    public void setWorkEffortTypeName(String workEffortTypeName) {
        this.workEffortTypeName = workEffortTypeName;
    }

    /**
     * @return the entityType
     */
    public EntityTypeTransfer getEntityType() {
        return entityType;
    }

    /**
     * @param entityType the entityType to set
     */
    public void setEntityType(EntityTypeTransfer entityType) {
        this.entityType = entityType;
    }

    /**
     * @return the workEffortSequence
     */
    public SequenceTransfer getWorkEffortSequence() {
        return workEffortSequence;
    }

    /**
     * @param workEffortSequence the workEffortSequence to set
     */
    public void setWorkEffortSequence(SequenceTransfer workEffortSequence) {
        this.workEffortSequence = workEffortSequence;
    }

    /**
     * @return the unformattedScheduledTime
     */
    public Long getUnformattedScheduledTime() {
        return unformattedScheduledTime;
    }

    /**
     * @param unformattedScheduledTime the unformattedScheduledTime to set
     */
    public void setUnformattedScheduledTime(Long unformattedScheduledTime) {
        this.unformattedScheduledTime = unformattedScheduledTime;
    }

    /**
     * @return the scheduledTime
     */
    public String getScheduledTime() {
        return scheduledTime;
    }

    /**
     * @param scheduledTime the scheduledTime to set
     */
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /**
     * @return the unformattedEstimatedTimeAllowed
     */
    public Long getUnformattedEstimatedTimeAllowed() {
        return unformattedEstimatedTimeAllowed;
    }

    /**
     * @param unformattedEstimatedTimeAllowed the unformattedEstimatedTimeAllowed to set
     */
    public void setUnformattedEstimatedTimeAllowed(Long unformattedEstimatedTimeAllowed) {
        this.unformattedEstimatedTimeAllowed = unformattedEstimatedTimeAllowed;
    }

    /**
     * @return the estimatedTimeAllowed
     */
    public String getEstimatedTimeAllowed() {
        return estimatedTimeAllowed;
    }

    /**
     * @param estimatedTimeAllowed the estimatedTimeAllowed to set
     */
    public void setEstimatedTimeAllowed(String estimatedTimeAllowed) {
        this.estimatedTimeAllowed = estimatedTimeAllowed;
    }

    /**
     * @return the unformattedMaximumTimeAllowed
     */
    public Long getUnformattedMaximumTimeAllowed() {
        return unformattedMaximumTimeAllowed;
    }

    /**
     * @param unformattedMaximumTimeAllowed the unformattedMaximumTimeAllowed to set
     */
    public void setUnformattedMaximumTimeAllowed(Long unformattedMaximumTimeAllowed) {
        this.unformattedMaximumTimeAllowed = unformattedMaximumTimeAllowed;
    }

    /**
     * @return the maximumTimeAllowed
     */
    public String getMaximumTimeAllowed() {
        return maximumTimeAllowed;
    }

    /**
     * @param maximumTimeAllowed the maximumTimeAllowed to set
     */
    public void setMaximumTimeAllowed(String maximumTimeAllowed) {
        this.maximumTimeAllowed = maximumTimeAllowed;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the workEffortScopes
     */
    public ListWrapper<WorkEffortScopeTransfer> getWorkEffortScopes() {
        return workEffortScopes;
    }

    /**
     * @param workEffortScopes the workEffortScopes to set
     */
    public void setWorkEffortScopes(ListWrapper<WorkEffortScopeTransfer> workEffortScopes) {
        this.workEffortScopes = workEffortScopes;
    }

    /**
     * @return the workRequirementTypes
     */
    public ListWrapper<WorkRequirementTypeTransfer> getWorkRequirementTypes() {
        return workRequirementTypes;
    }

    /**
     * @param workRequirementTypes the workRequirementTypes to set
     */
    public void setWorkRequirementTypes(ListWrapper<WorkRequirementTypeTransfer> workRequirementTypes) {
        this.workRequirementTypes = workRequirementTypes;
    }
    
}
