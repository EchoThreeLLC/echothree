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

package com.echothree.model.control.shipment.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class FreeOnBoardDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private FreeOnBoardTransfer freeOnBoard;
    private String description;
    
    /** Creates a new instance of FreeOnBoardDescriptionTransfer */
    public FreeOnBoardDescriptionTransfer(LanguageTransfer language, FreeOnBoardTransfer freeOnBoard, String description) {
        this.language = language;
        this.freeOnBoard = freeOnBoard;
        this.description = description;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public FreeOnBoardTransfer getFreeOnBoard() {
        return freeOnBoard;
    }
    
    public void setFreeOnBoard(FreeOnBoardTransfer freeOnBoard) {
        this.freeOnBoard = freeOnBoard;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
