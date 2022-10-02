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

package com.echothree.util.server.string;

import com.echothree.model.data.core.server.entity.EntityInstance;

public class EntityInstanceUtils {

    private EntityInstanceUtils() {
        super();
    }
    
    private static class EntityInstanceUtilsHolder {
        static EntityInstanceUtils instance = new EntityInstanceUtils();
    }
    
    public static EntityInstanceUtils getInstance() {
        return EntityInstanceUtilsHolder.instance;
    }

    public String getEntityRefByEntityInstance(final EntityInstance entityInstance) {
        if(entityInstance == null) {
            return "(null)";
        } else {
            final var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

            return entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName()
                    + "." + entityTypeDetail.getEntityTypeName() + "." + entityInstance.getEntityUniqueId();
        }
    }

}
