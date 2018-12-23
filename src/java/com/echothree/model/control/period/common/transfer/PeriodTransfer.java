// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.period.common.transfer;

import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PeriodTransfer
        extends BaseTransfer {
    
    private PeriodKindTransfer periodKind;
    private String periodName;
    private PeriodTransfer parentPeriod;
    private PeriodTypeTransfer periodType;
    private Long unformattedStartTime;
    private String startTime;
    private Long unformattedEndTime;
    private String endTime;
    private String description;
    private WorkflowEntityStatusTransfer status;
    
    /** Creates a new instance of PeriodTransfer */
    public PeriodTransfer(PeriodKindTransfer periodKind, String periodName, PeriodTransfer parentPeriod, PeriodTypeTransfer periodType, Long unformattedStartTime,
            String startTime, Long unformattedEndTime, String endTime, String description, WorkflowEntityStatusTransfer status) {
        this.periodKind = periodKind;
        this.periodName = periodName;
        this.periodType = periodType;
        this.parentPeriod = parentPeriod;
        this.unformattedStartTime = unformattedStartTime;
        this.startTime = startTime;
        this.unformattedEndTime = unformattedEndTime;
        this.endTime = endTime;
        this.description = description;
        this.status = status;
    }

    /**
     * @return the periodKind
     */
    public PeriodKindTransfer getPeriodKind() {
        return periodKind;
    }

    /**
     * @param periodKind the periodKind to set
     */
    public void setPeriodKind(PeriodKindTransfer periodKind) {
        this.periodKind = periodKind;
    }

    /**
     * @return the periodName
     */
    public String getPeriodName() {
        return periodName;
    }

    /**
     * @param periodName the periodName to set
     */
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    /**
     * @return the parentPeriod
     */
    public PeriodTransfer getParentPeriod() {
        return parentPeriod;
    }

    /**
     * @param parentPeriod the parentPeriod to set
     */
    public void setParentPeriod(PeriodTransfer parentPeriod) {
        this.parentPeriod = parentPeriod;
    }

    /**
     * @return the periodType
     */
    public PeriodTypeTransfer getPeriodType() {
        return periodType;
    }

    /**
     * @param periodType the periodType to set
     */
    public void setPeriodType(PeriodTypeTransfer periodType) {
        this.periodType = periodType;
    }

    /**
     * @return the unformattedStartTime
     */
    public Long getUnformattedStartTime() {
        return unformattedStartTime;
    }

    /**
     * @param unformattedStartTime the unformattedStartTime to set
     */
    public void setUnformattedStartTime(Long unformattedStartTime) {
        this.unformattedStartTime = unformattedStartTime;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the unformattedEndTime
     */
    public Long getUnformattedEndTime() {
        return unformattedEndTime;
    }

    /**
     * @param unformattedEndTime the unformattedEndTime to set
     */
    public void setUnformattedEndTime(Long unformattedEndTime) {
        this.unformattedEndTime = unformattedEndTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
     * @return the status
     */
    public WorkflowEntityStatusTransfer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(WorkflowEntityStatusTransfer status) {
        this.status = status;
    }

}
