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

package com.echothree.model.control.training.server.transfer;

import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class TrainingTransferCaches
        extends BaseTransferCaches {
    
    protected TrainingControl trainingControl;
    
    protected TrainingClassTransferCache trainingClassTransferCache;
    protected TrainingClassTranslationTransferCache trainingClassTranslationTransferCache;
    protected TrainingClassSectionTransferCache trainingClassSectionTransferCache;
    protected TrainingClassSectionTranslationTransferCache trainingClassSectionTranslationTransferCache;
    protected TrainingClassPageTransferCache trainingClassPageTransferCache;
    protected TrainingClassPageTranslationTransferCache trainingClassPageTranslationTransferCache;
    protected TrainingClassQuestionTransferCache trainingClassQuestionTransferCache;
    protected TrainingClassQuestionTranslationTransferCache trainingClassQuestionTranslationTransferCache;
    protected TrainingClassAnswerTransferCache trainingClassAnswerTransferCache;
    protected TrainingClassAnswerTranslationTransferCache trainingClassAnswerTranslationTransferCache;
    protected PartyTrainingClassTransferCache partyTrainingClassTransferCache;
    protected PartyTrainingClassSessionTransferCache partyTrainingClassSessionTransferCache;
    protected PartyTrainingClassSessionSectionTransferCache partyTrainingClassSessionSectionTransferCache;
    protected PartyTrainingClassSessionPageTransferCache partyTrainingClassSessionPageTransferCache;
    protected PartyTrainingClassSessionQuestionTransferCache partyTrainingClassSessionQuestionTransferCache;
    protected PartyTrainingClassSessionAnswerTransferCache partyTrainingClassSessionAnswerTransferCache;
    
    /** Creates a new instance of TrainingTransferCaches */
    public TrainingTransferCaches(UserVisit userVisit, TrainingControl trainingControl) {
        super(userVisit);
        
        this.trainingControl = trainingControl;
    }
    
    public TrainingClassTransferCache getTrainingClassTransferCache() {
        if(trainingClassTransferCache == null) {
            trainingClassTransferCache = new TrainingClassTransferCache(userVisit, trainingControl);
        }
        
        return trainingClassTransferCache;
    }
    
    public TrainingClassTranslationTransferCache getTrainingClassTranslationTransferCache() {
        if(trainingClassTranslationTransferCache == null) {
            trainingClassTranslationTransferCache = new TrainingClassTranslationTransferCache(userVisit, trainingControl);
        }
        
        return trainingClassTranslationTransferCache;
    }
    
    public TrainingClassSectionTransferCache getTrainingClassSectionTransferCache() {
        if(trainingClassSectionTransferCache == null) {
            trainingClassSectionTransferCache = new TrainingClassSectionTransferCache(userVisit, trainingControl);
        }
        
        return trainingClassSectionTransferCache;
    }
    
    public TrainingClassSectionTranslationTransferCache getTrainingClassSectionTranslationTransferCache() {
        if(trainingClassSectionTranslationTransferCache == null) {
            trainingClassSectionTranslationTransferCache = new TrainingClassSectionTranslationTransferCache(userVisit, trainingControl);
        }
        
        return trainingClassSectionTranslationTransferCache;
    }
    
    public TrainingClassPageTransferCache getTrainingClassPageTransferCache() {
        if(trainingClassPageTransferCache == null) {
            trainingClassPageTransferCache = new TrainingClassPageTransferCache(userVisit, trainingControl);
        }
        
        return trainingClassPageTransferCache;
    }
    
    public TrainingClassPageTranslationTransferCache getTrainingClassPageTranslationTransferCache() {
        if(trainingClassPageTranslationTransferCache == null) {
            trainingClassPageTranslationTransferCache = new TrainingClassPageTranslationTransferCache(userVisit, trainingControl);
        }
        
        return trainingClassPageTranslationTransferCache;
    }
    
    public TrainingClassQuestionTransferCache getTrainingClassQuestionTransferCache() {
        if(trainingClassQuestionTransferCache == null) {
            trainingClassQuestionTransferCache = new TrainingClassQuestionTransferCache(userVisit, trainingControl);
        }
        
        return trainingClassQuestionTransferCache;
    }
    
    public TrainingClassQuestionTranslationTransferCache getTrainingClassQuestionTranslationTransferCache() {
        if(trainingClassQuestionTranslationTransferCache == null) {
            trainingClassQuestionTranslationTransferCache = new TrainingClassQuestionTranslationTransferCache(userVisit, trainingControl);
        }
        
        return trainingClassQuestionTranslationTransferCache;
    }
    
    public TrainingClassAnswerTransferCache getTrainingClassAnswerTransferCache() {
        if(trainingClassAnswerTransferCache == null) {
            trainingClassAnswerTransferCache = new TrainingClassAnswerTransferCache(userVisit, trainingControl);
        }
        
        return trainingClassAnswerTransferCache;
    }
    
    public TrainingClassAnswerTranslationTransferCache getTrainingClassAnswerTranslationTransferCache() {
        if(trainingClassAnswerTranslationTransferCache == null) {
            trainingClassAnswerTranslationTransferCache = new TrainingClassAnswerTranslationTransferCache(userVisit, trainingControl);
        }
        
        return trainingClassAnswerTranslationTransferCache;
    }
    
    public PartyTrainingClassTransferCache getPartyTrainingClassTransferCache() {
        if(partyTrainingClassTransferCache == null) {
            partyTrainingClassTransferCache = new PartyTrainingClassTransferCache(userVisit, trainingControl);
        }
        
        return partyTrainingClassTransferCache;
    }
    
    public PartyTrainingClassSessionTransferCache getPartyTrainingClassSessionTransferCache() {
        if(partyTrainingClassSessionTransferCache == null) {
            partyTrainingClassSessionTransferCache = new PartyTrainingClassSessionTransferCache(userVisit, trainingControl);
        }
        
        return partyTrainingClassSessionTransferCache;
    }
    
    public PartyTrainingClassSessionSectionTransferCache getPartyTrainingClassSessionSectionTransferCache() {
        if(partyTrainingClassSessionSectionTransferCache == null) {
            partyTrainingClassSessionSectionTransferCache = new PartyTrainingClassSessionSectionTransferCache(userVisit, trainingControl);
        }
        
        return partyTrainingClassSessionSectionTransferCache;
    }
    
    public PartyTrainingClassSessionPageTransferCache getPartyTrainingClassSessionPageTransferCache() {
        if(partyTrainingClassSessionPageTransferCache == null) {
            partyTrainingClassSessionPageTransferCache = new PartyTrainingClassSessionPageTransferCache(userVisit, trainingControl);
        }
        
        return partyTrainingClassSessionPageTransferCache;
    }
    
    public PartyTrainingClassSessionQuestionTransferCache getPartyTrainingClassSessionQuestionTransferCache() {
        if(partyTrainingClassSessionQuestionTransferCache == null) {
            partyTrainingClassSessionQuestionTransferCache = new PartyTrainingClassSessionQuestionTransferCache(userVisit, trainingControl);
        }
        
        return partyTrainingClassSessionQuestionTransferCache;
    }
    
    public PartyTrainingClassSessionAnswerTransferCache getPartyTrainingClassSessionAnswerTransferCache() {
        if(partyTrainingClassSessionAnswerTransferCache == null) {
            partyTrainingClassSessionAnswerTransferCache = new PartyTrainingClassSessionAnswerTransferCache(userVisit, trainingControl);
        }
        
        return partyTrainingClassSessionAnswerTransferCache;
    }
    
}
