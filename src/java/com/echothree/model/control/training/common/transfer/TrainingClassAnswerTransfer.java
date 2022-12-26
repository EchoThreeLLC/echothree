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

package com.echothree.model.control.training.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class TrainingClassAnswerTransfer
        extends BaseTransfer {
    
    private TrainingClassQuestionTransfer trainingClassQuestion;
    private String trainingClassAnswerName;
    private Boolean isCorrect;
    private Integer sortOrder;
    
    /** Creates a new instance of TrainingClassAnswerTransfer */
    public TrainingClassAnswerTransfer(TrainingClassQuestionTransfer trainingClassQuestion, String trainingClassAnswerName, Boolean isCorrect, Integer sortOrder) {
        this.trainingClassQuestion = trainingClassQuestion;
        this.trainingClassAnswerName = trainingClassAnswerName;
        this.isCorrect = isCorrect;
        this.sortOrder = sortOrder;
    }

    public TrainingClassQuestionTransfer getTrainingClassQuestion() {
        return trainingClassQuestion;
    }

    public void setTrainingClassQuestion(TrainingClassQuestionTransfer trainingClassQuestion) {
        this.trainingClassQuestion = trainingClassQuestion;
    }

    public String getTrainingClassAnswerName() {
        return trainingClassAnswerName;
    }

    public void setTrainingClassAnswerName(String trainingClassAnswerName) {
        this.trainingClassAnswerName = trainingClassAnswerName;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

}
