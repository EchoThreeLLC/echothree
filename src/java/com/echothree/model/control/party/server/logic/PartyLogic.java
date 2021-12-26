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

package com.echothree.model.control.party.server.logic;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.exception.InvalidPartyTypeException;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.common.exception.UnknownPartyTypeNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.factory.PartyFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class PartyLogic
        extends BaseLogic {
    
    private PartyLogic() {
        super();
    }
    
    private static class PartyLogicHolder {
        static PartyLogic instance = new PartyLogic();
    }
    
    public static PartyLogic getInstance() {
        return PartyLogicHolder.instance;
    }
    
    /** Assume that the entityInstance passed to this function is an ECHOTHREE.Party. */
    public Party getPartyFromEntityInstance(final EntityInstance entityInstance) {
        PartyPK pk = new PartyPK(entityInstance.getEntityUniqueId());
        
        return PartyFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public boolean isPartyType(final Party party, final String ... partyTypeNames) {
        String partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
        boolean found = false;
        int length = partyTypeNames.length;

        for(int i = 0 ; i < length ; i++) {
            if(partyTypeName.equals(partyTypeNames[i])) {
                found = true;
                break;
            }
        }

        return found;
    }

    public String checkPartyType(final ExecutionErrorAccumulator eea, final Party party, final String ... partyTypeNames) {
        String partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();
        boolean found = false;
        int length = partyTypeNames.length;

        for(int i = 0 ; i < length ; i++) {
            if(partyTypeName.equals(partyTypeNames[i])) {
                found = true;
                break;
            }
        }

        if(!found) {
            handleExecutionError(InvalidPartyTypeException.class, eea, ExecutionErrors.InvalidPartyType.name(), partyTypeName);
        }

        return partyTypeName;
    }

    public PartyType getPartyTypeByName(final ExecutionErrorAccumulator eea, final String partyTypeName) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);

        if(partyType == null) {
            handleExecutionError(UnknownPartyTypeNameException.class, eea, ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }

        return partyType;
    }
    
    public Party getPartyByName(final ExecutionErrorAccumulator eea, final String partyName,
            final EntityPermission entityPermission) {
        var partyControl = Session.getModelController(PartyControl.class);
        Party party = partyControl.getPartyByName(partyName, entityPermission);

        if(party == null) {
            handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return party;
    }
    
    public Party getPartyByName(final ExecutionErrorAccumulator eea, final String partyName) {
        return getPartyByName(eea, partyName, EntityPermission.READ_ONLY);
    }
    
    public Party getPartyByNameForUpdate(final ExecutionErrorAccumulator eea, final String partyName) {
        return getPartyByName(eea, partyName, EntityPermission.READ_WRITE);
    }

    /**
     * Attempt to locate the Party currently executing a command by first taking a look to see if a PartyName
     * parameter was supplied, and if it wasn't, get the Party that's associated with the UserVisit that was
     * supplied. The result may still be null if there is no user logged in. This is useful for commands that
     * require CUSTOMERs and VENDORSs to supply a null PartyName, but EMPLOYEEs are allowed to specify any
     * PartyName.
     *
     * @param eea An optional parameter specifying the ExecutionErrorAccumulator
     * @param userVisitPK A required parameters specifying the UserVisit
     * @param partyName An optional parameter giving a PartyName
     * @return A possibly non-null parameter giving the Party
     */
    public Party getParty(final ExecutionErrorAccumulator eea, final UserVisitPK userVisitPK, final String partyName) {
        Party party;

        if(partyName == null) {
            var userControl = (UserControl)Session.getModelController(UserControl.class);

            party = userControl.getPartyFromUserVisitPK(userVisitPK);
        } else {
            party = getPartyByName(eea, partyName);
        }

        return party;
    }

    public Party getPartyByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        Party party = null;
        
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHOTHREE.name(), EntityTypes.Party.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            var partyControl = Session.getModelController(PartyControl.class);
            
            party = partyControl.getPartyByEntityInstance(entityInstance, entityPermission);
        }

        return party;
    }
    
    public Party getPartyByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getPartyByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public Party getPartyByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getPartyByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }
        
    public Party getPartyByName(final ExecutionErrorAccumulator eea, final String partyName, final String ... partyTypeNames) {
        Party party = getPartyByName(eea, partyName);

        if(party != null) {
            checkPartyType(eea, party, partyTypeNames);
        }

        return party;
    }
    
}
