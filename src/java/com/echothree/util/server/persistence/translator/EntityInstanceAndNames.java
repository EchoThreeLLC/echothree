// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.util.server.persistence.translator;

import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.persistence.EntityNames;

public class EntityInstanceAndNames {

    private final EntityInstance entityInstance;
    private final EntityNames entityNames;
    
    public EntityInstanceAndNames(EntityInstance entityInstance, EntityNames entityNames) {
        this.entityInstance = entityInstance;
        this.entityNames = entityNames;
    }

    /**
     * Returns the entityInstance.
     * @return the entityInstance
     */
    public EntityInstance getEntityInstance() {
        return entityInstance;
    }

    /**
     * Returns the entityNames.
     * @return the entityNames
     */
    public EntityNames getEntityNames() {
        return entityNames;
    }

}
