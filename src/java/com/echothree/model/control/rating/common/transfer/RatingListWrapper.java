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

package com.echothree.model.control.rating.common.transfer;

import com.echothree.util.common.transfer.ListWrapper;
import java.util.List;

public class RatingListWrapper
        extends ListWrapper<RatingTransfer> {
    
    private RatingTypeTransfer ratingType;
    
    /** Creates a new instance of RatingListWrapper */
    public RatingListWrapper(RatingTypeTransfer ratingType, List<RatingTransfer> ratings) {
        super(ratings);
        
        this.ratingType = ratingType;
    }
    
    public RatingTypeTransfer getRatingType() {
        return ratingType;
    }
    
    public void setRatingType(RatingTypeTransfer ratingType) {
        this.ratingType = ratingType;
    }
    
}
