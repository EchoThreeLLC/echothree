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

package com.echothree.model.control.training.server.transfer;

import com.echothree.model.control.training.common.transfer.TrainingClassPageTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassSectionTransfer;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.TrainingClassPage;
import com.echothree.model.data.training.server.entity.TrainingClassPageDetail;
import com.echothree.model.data.training.server.entity.TrainingClassPageTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TrainingClassPageTransferCache
        extends BaseTrainingTransferCache<TrainingClassPage, TrainingClassPageTransfer> {
    
    /** Creates a new instance of TrainingClassPageTransferCache */
    public TrainingClassPageTransferCache(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit, trainingControl);
        
        setIncludeEntityInstance(true);
    }
    
    public TrainingClassPageTransfer getTrainingClassPageTransfer(TrainingClassPage trainingClassPage) {
        TrainingClassPageTransfer trainingClassPageTransfer = get(trainingClassPage);
        
        if(trainingClassPageTransfer == null) {
            TrainingClassPageDetail trainingClassPageDetail = trainingClassPage.getLastDetail();
            TrainingClassSectionTransfer trainingClassSection = trainingControl.getTrainingClassSectionTransfer(userVisit, trainingClassPageDetail.getTrainingClassSection());
            String trainingClassPageName = trainingClassPageDetail.getTrainingClassPageName();
            Integer sortOrder = trainingClassPageDetail.getSortOrder();
            TrainingClassPageTranslation trainingClassPageTranslation = trainingControl.getBestTrainingClassPageTranslation(trainingClassPage, getLanguage());
            String description = trainingClassPageTranslation == null ? trainingClassPageName : trainingClassPageTranslation.getDescription();
            
            trainingClassPageTransfer = new TrainingClassPageTransfer(trainingClassSection, trainingClassPageName, sortOrder, description);
            put(trainingClassPage, trainingClassPageTransfer);
        }
        
        return trainingClassPageTransfer;
    }
    
}
