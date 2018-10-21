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

package com.echothree.control.user.core.remote.result;

import com.echothree.model.control.core.remote.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.remote.transfer.EventTransfer;
import com.echothree.util.remote.command.BaseResult;
import java.util.List;

public interface GetEventsResult
        extends BaseResult {
    
    EntityInstanceTransfer getEntityInstance();
    void setEntityInstance(EntityInstanceTransfer entityInstance);
    
    EntityInstanceTransfer getCreatedByEntityInstance();
    void setCreatedByEntityInstance(EntityInstanceTransfer createdByEntityInstance);
    
    Long getEventCount();
    void setEventCount(Long eventCount);

    List<EventTransfer> getEvents();
    void setEvents(List<EventTransfer> events);
    
}
