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

package com.echothree.model.control.rating.server.transfer;

import com.echothree.model.control.rating.common.transfer.RatingTypeDescriptionTransfer;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.rating.server.entity.RatingTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class RatingTypeDescriptionTransferCache
        extends BaseRatingDescriptionTransferCache<RatingTypeDescription, RatingTypeDescriptionTransfer> {
    
    /** Creates a new instance of RatingTypeDescriptionTransferCache */
    public RatingTypeDescriptionTransferCache(UserVisit userVisit, RatingControl ratingControl) {
        super(userVisit, ratingControl);
    }
    
    public RatingTypeDescriptionTransfer getRatingTypeDescriptionTransfer(RatingTypeDescription ratingTypeDescription) {
        var ratingTypeDescriptionTransfer = get(ratingTypeDescription);
        
        if(ratingTypeDescriptionTransfer == null) {
            var ratingTypeTransferCache = ratingControl.getRatingTransferCaches(userVisit).getRatingTypeTransferCache();
            var ratingTypeTransfer = ratingTypeTransferCache.getRatingTypeTransfer(ratingTypeDescription.getRatingType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, ratingTypeDescription.getLanguage());
            
            ratingTypeDescriptionTransfer = new RatingTypeDescriptionTransfer(languageTransfer, ratingTypeTransfer, ratingTypeDescription.getDescription());
            put(ratingTypeDescription, ratingTypeDescriptionTransfer);
        }
        
        return ratingTypeDescriptionTransfer;
    }
    
}
