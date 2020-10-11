// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.club.common.transfer.ClubItemTransfer;
import com.echothree.model.control.club.common.transfer.ClubItemTypeTransfer;
import com.echothree.model.control.club.common.transfer.ClubTransfer;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.club.server.entity.ClubItem;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ClubItemTransferCache
        extends BaseClubTransferCache<ClubItem, ClubItemTransfer> {

    ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
    
    /** Creates a new instance of ClubItemTransferCache */
    public ClubItemTransferCache(UserVisit userVisit, ClubControl clubControl) {
        super(userVisit, clubControl);
    }
    
    public ClubItemTransfer getClubItemTransfer(ClubItem clubItem) {
        ClubItemTransfer clubItemTransfer = get(clubItem);
        
        if(clubItemTransfer == null) {
            ClubTransfer club = clubControl.getClubTransfer(userVisit, clubItem.getClub());
            ClubItemTypeTransfer clubItemType = clubControl.getClubItemTypeTransfer(userVisit, clubItem.getClubItemType());
            ItemTransfer item = itemControl.getItemTransfer(userVisit, clubItem.getItem());
            Long unformattedSubscriptionTime = clubItem.getSubscriptionTime();
            String subscriptionTime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedSubscriptionTime);
            
            clubItemTransfer = new ClubItemTransfer(club, clubItemType, item, unformattedSubscriptionTime, subscriptionTime);
            put(clubItem, clubItemTransfer);
        }
        return clubItemTransfer;
    }
    
}
