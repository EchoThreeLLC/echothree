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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class EntityVisitTransfer
        extends BaseTransfer {

    private EntityInstanceTransfer entityInstance;
    private EntityInstanceTransfer visitedEntityInstance;
    private Long unformattedVisitedTime;
    private String visitedTime;

    /** Creates a new instance of EntityTimeTransfer */
    public EntityVisitTransfer(Long unformattedVisitedTime) {
        this.unformattedVisitedTime = unformattedVisitedTime;
    }

    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }

    @Override
    public void setEntityInstance(final EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }

    public EntityInstanceTransfer getVisitedEntityInstance() {
        return visitedEntityInstance;
    }

    public void setVisitedEntityInstance(final EntityInstanceTransfer visitedEntityInstance) {
        this.visitedEntityInstance = visitedEntityInstance;
    }

    public Long getUnformattedVisitedTime() {
        return unformattedVisitedTime;
    }

    public void setUnformattedVisitedTime(final Long unformattedVisitedTime) {
        this.unformattedVisitedTime = unformattedVisitedTime;
    }

    public String getVisitedTime() {
        return visitedTime;
    }

    public void setVisitedTime(final String visitedTime) {
        this.visitedTime = visitedTime;
    }

}
