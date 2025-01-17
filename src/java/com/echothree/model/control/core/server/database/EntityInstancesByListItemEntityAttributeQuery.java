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

package com.echothree.model.control.core.server.database;

import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EntityInstancesByListItemEntityAttributeQuery
        extends BaseEntityAttributeQuery<EntityInstancePKResult> {
    
    public EntityInstancesByListItemEntityAttributeQuery() {
        super("SELECT ela_eni_entityinstanceid AS EntityInstancePK "
                + "FROM entitylistitems, entitylistitemdetails, entitylistitemattributes "
                + "WHERE eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ? "
                + "AND eli_entitylistitemid = ela_eli_entitylistitemid AND ela_thrutime = ?");
    }
    
    @Override
    public List<EntityInstancePKResult> execute(final EntityAttribute entityAttribute) {
        return super.execute(entityAttribute, Session.MAX_TIME_LONG);
    }
    
}
