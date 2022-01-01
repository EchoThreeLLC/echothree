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

package com.echothree.model.control.training.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class TrainingClassQuestionTranslationTransfer
        extends BaseTransfer {
    
    private TrainingClassQuestionTransfer trainingClassQuestion;
    private LanguageTransfer language;
    private MimeTypeTransfer questionMimeType;
    private String question;
    
    /** Creates a new instance of TrainingClassQuestionTranslationTransfer */
    public TrainingClassQuestionTranslationTransfer(TrainingClassQuestionTransfer trainingClassQuestion, LanguageTransfer language,
            MimeTypeTransfer questionMimeType, String question) {
        this.trainingClassQuestion = trainingClassQuestion;
        this.language = language;
        this.questionMimeType = questionMimeType;
        this.question = question;
    }

    public TrainingClassQuestionTransfer getTrainingClassQuestion() {
        return trainingClassQuestion;
    }

    public void setTrainingClassQuestion(TrainingClassQuestionTransfer trainingClassQuestion) {
        this.trainingClassQuestion = trainingClassQuestion;
    }

    public LanguageTransfer getLanguage() {
        return language;
    }

    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    public MimeTypeTransfer getQuestionMimeType() {
        return questionMimeType;
    }

    public void setQuestionMimeType(MimeTypeTransfer questionMimeType) {
        this.questionMimeType = questionMimeType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
