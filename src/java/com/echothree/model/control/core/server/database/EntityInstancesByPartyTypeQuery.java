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

package com.echothree.model.control.core.server.database;

import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.List;

public class EntityInstancesByPartyTypeQuery
        extends BaseDatabaseQuery<EntityInstanceResult> {
    
    public EntityInstancesByPartyTypeQuery() {
        super("SELECT eni_entityinstanceid AS EntityInstancePK "
                + "FROM entityinstances, parties, partydetails "
                + "WHERE eni_ent_entitytypeid = ? "
                + "AND eni_entityuniqueid = par_partyid AND par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ?",
                EntityPermission.READ_ONLY);
    }
    
    public List<EntityInstanceResult> execute(final EntityType entityType, final PartyType partyType) {
        return super.execute(entityType, partyType);
    }
    
}
