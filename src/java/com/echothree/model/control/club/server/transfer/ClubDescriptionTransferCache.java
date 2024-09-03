// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.club.server.transfer;

import com.echothree.model.control.club.common.transfer.ClubDescriptionTransfer;
import com.echothree.model.control.club.common.transfer.ClubTransfer;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.club.server.entity.ClubDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ClubDescriptionTransferCache
        extends BaseClubDescriptionTransferCache<ClubDescription, ClubDescriptionTransfer> {
    
    /** Creates a new instance of ClubDescriptionTransferCache */
    public ClubDescriptionTransferCache(UserVisit userVisit, ClubControl clubControl) {
        super(userVisit, clubControl);
    }
    
    public ClubDescriptionTransfer getClubDescriptionTransfer(ClubDescription clubDescription) {
        var clubDescriptionTransfer = get(clubDescription);
        
        if(clubDescriptionTransfer == null) {
            var clubTransfer = clubControl.getClubTransfer(userVisit, clubDescription.getClub());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, clubDescription.getLanguage());
            
            clubDescriptionTransfer = new ClubDescriptionTransfer(languageTransfer, clubTransfer, clubDescription.getDescription());
            put(clubDescription, clubDescriptionTransfer);
        }
        
        return clubDescriptionTransfer;
    }
    
}
