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

package com.echothree.model.control.track.server.transfer;

import com.echothree.model.control.track.server.control.TrackControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class TrackTransferCaches
        extends BaseTransferCaches {
    
    protected TrackControl trackControl;
    
    protected TrackTransferCache trackTransferCache;
    protected TrackDescriptionTransferCache trackDescriptionTransferCache;
    protected UserVisitTrackTransferCache userVisitTrackTransferCache;
    
    /** Creates a new instance of TrackTransferCaches */
    public TrackTransferCaches(UserVisit userVisit, TrackControl trackControl) {
        super(userVisit);
        
        this.trackControl = trackControl;
    }
    
    public TrackTransferCache getTrackTransferCache() {
        if(trackTransferCache == null) {
            trackTransferCache = new TrackTransferCache(userVisit, trackControl);
        }

        return trackTransferCache;
    }

    public TrackDescriptionTransferCache getTrackDescriptionTransferCache() {
        if(trackDescriptionTransferCache == null) {
            trackDescriptionTransferCache = new TrackDescriptionTransferCache(userVisit, trackControl);
        }

        return trackDescriptionTransferCache;
    }

    public UserVisitTrackTransferCache getUserVisitTrackTransferCache() {
        if(userVisitTrackTransferCache == null) {
            userVisitTrackTransferCache = new UserVisitTrackTransferCache(userVisit, trackControl);
        }

        return userVisitTrackTransferCache;
    }

}
