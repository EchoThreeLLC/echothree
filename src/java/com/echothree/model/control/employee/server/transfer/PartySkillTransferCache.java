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

package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.employee.common.transfer.PartySkillTransfer;
import com.echothree.model.control.employee.common.transfer.SkillTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.employee.server.entity.PartySkill;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartySkillTransferCache
        extends BaseEmployeeTransferCache<PartySkill, PartySkillTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of PartySkillTransferCache */
    public PartySkillTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public PartySkillTransfer getPartySkillTransfer(PartySkill partySkill) {
        PartySkillTransfer partySkillTransfer = get(partySkill);
        
        if(partySkillTransfer == null) {
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, partySkill.getParty());
            SkillTypeTransfer skillType = employeeControl.getSkillTypeTransfer(userVisit, partySkill.getSkillType());
            
            partySkillTransfer = new PartySkillTransfer(party, skillType);
            put(partySkill, partySkillTransfer);
        }
        
        return partySkillTransfer;
    }
    
}
