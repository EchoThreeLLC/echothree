// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.chain.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ChainInstanceStatusTransfer
        extends BaseTransfer {

    private ChainInstanceTransfer chainInstance;
    private ChainActionSetTransfer nextChainActionSet;
    private Long unformattedNextChainActionSetTime;
    private String nextChainActionSetTime;
    private Integer queuedLetterSequence;

    /** Creates a new instance of ChainInstanceStatusTransfer */
    public ChainInstanceStatusTransfer(ChainInstanceTransfer chainInstance, ChainActionSetTransfer nextChainActionSet, Long unformattedNextChainActionSetTime,
            String nextChainActionSetTime, Integer queuedLetterSequence) {
        this.chainInstance = chainInstance;
        this.nextChainActionSet = nextChainActionSet;
        this.unformattedNextChainActionSetTime = unformattedNextChainActionSetTime;
        this.nextChainActionSetTime = nextChainActionSetTime;
        this.queuedLetterSequence = queuedLetterSequence;
    }

    /**
     * @return the chainInstance
     */
    public ChainInstanceTransfer getChainInstance() {
        return chainInstance;
    }

    /**
     * @param chainInstance the chainInstance to set
     */
    public void setChainInstance(ChainInstanceTransfer chainInstance) {
        this.chainInstance = chainInstance;
    }

    /**
     * @return the nextChainActionSet
     */
    public ChainActionSetTransfer getNextChainActionSet() {
        return nextChainActionSet;
    }

    /**
     * @param nextChainActionSet the nextChainActionSet to set
     */
    public void setNextChainActionSet(ChainActionSetTransfer nextChainActionSet) {
        this.nextChainActionSet = nextChainActionSet;
    }

    /**
     * @return the unformattedNextChainActionSetTime
     */
    public Long getUnformattedNextChainActionSetTime() {
        return unformattedNextChainActionSetTime;
    }

    /**
     * @param unformattedNextChainActionSetTime the unformattedNextChainActionSetTime to set
     */
    public void setUnformattedNextChainActionSetTime(Long unformattedNextChainActionSetTime) {
        this.unformattedNextChainActionSetTime = unformattedNextChainActionSetTime;
    }

    /**
     * @return the nextChainActionSetTime
     */
    public String getNextChainActionSetTime() {
        return nextChainActionSetTime;
    }

    /**
     * @param nextChainActionSetTime the nextChainActionSetTime to set
     */
    public void setNextChainActionSetTime(String nextChainActionSetTime) {
        this.nextChainActionSetTime = nextChainActionSetTime;
    }

    /**
     * @return the queuedLetterSequence
     */
    public Integer getQueuedLetterSequence() {
        return queuedLetterSequence;
    }

    /**
     * @param queuedLetterSequence the queuedLetterSequence to set
     */
    public void setQueuedLetterSequence(Integer queuedLetterSequence) {
        this.queuedLetterSequence = queuedLetterSequence;
    }

}