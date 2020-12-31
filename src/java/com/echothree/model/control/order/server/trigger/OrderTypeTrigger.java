// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.order.server.trigger;

import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public interface OrderTypeTrigger {

    public void handleTrigger(final Session session, final ExecutionErrorAccumulator eea, final WorkflowEntityStatus workflowEntityStatus,
            final Order order, final PartyPK triggeredBy);
    
}
