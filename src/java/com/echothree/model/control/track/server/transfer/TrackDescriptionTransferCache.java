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

package com.echothree.model.control.track.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.track.common.transfer.TrackDescriptionTransfer;
import com.echothree.model.control.track.common.transfer.TrackTransfer;
import com.echothree.model.control.track.server.control.TrackControl;
import com.echothree.model.data.track.server.entity.TrackDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TrackDescriptionTransferCache
        extends BaseTrackDescriptionTransferCache<TrackDescription, TrackDescriptionTransfer> {
    
    /** Creates a new instance of TrackDescriptionTransferCache */
    public TrackDescriptionTransferCache(UserVisit userVisit, TrackControl trackControl) {
        super(userVisit, trackControl);
    }
    
    public TrackDescriptionTransfer getTrackDescriptionTransfer(TrackDescription trackDescription) {
        TrackDescriptionTransfer trackDescriptionTransfer = get(trackDescription);
        
        if(trackDescriptionTransfer == null) {
            TrackTransfer trackTransfer = trackControl.getTrackTransfer(userVisit, trackDescription.getTrack());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, trackDescription.getLanguage());
            
            trackDescriptionTransfer = new TrackDescriptionTransfer(languageTransfer, trackTransfer, trackDescription.getDescription());
            put(trackDescription, trackDescriptionTransfer);
        }
        return trackDescriptionTransfer;
    }
    
}
