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

package com.echothree.util.server.string;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;

public abstract class BaseIdUtils {

    protected Long getEntityInstanceCreatedTime(EntityInstance entityInstance) {
        var coreControl = Session.getModelController(CoreControl.class);
        var entityTime = coreControl.getEntityTime(entityInstance);

        if(entityTime == null) {
            var session = ThreadSession.currentSession();

            entityTime = coreControl.createEntityTime(entityInstance, session.START_TIME, null, null);
        }

        return entityTime.getCreatedTime();
    }

}
