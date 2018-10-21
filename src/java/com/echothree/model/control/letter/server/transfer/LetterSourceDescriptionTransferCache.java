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

package com.echothree.model.control.letter.server.transfer;

import com.echothree.model.control.letter.remote.transfer.LetterSourceDescriptionTransfer;
import com.echothree.model.control.letter.remote.transfer.LetterSourceTransfer;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.letter.server.entity.LetterSourceDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class LetterSourceDescriptionTransferCache
        extends BaseLetterDescriptionTransferCache<LetterSourceDescription, LetterSourceDescriptionTransfer> {
    
    /** Creates a new instance of LetterSourceDescriptionTransferCache */
    public LetterSourceDescriptionTransferCache(UserVisit userVisit, LetterControl letterControl) {
        super(userVisit, letterControl);
    }
    
    public LetterSourceDescriptionTransfer getLetterSourceDescriptionTransfer(LetterSourceDescription letterSourceDescription) {
        LetterSourceDescriptionTransfer letterSourceDescriptionTransfer = get(letterSourceDescription);
        
        if(letterSourceDescriptionTransfer == null) {
            LetterSourceTransfer letterSourceTransfer = letterControl.getLetterSourceTransfer(userVisit, letterSourceDescription.getLetterSource());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, letterSourceDescription.getLanguage());
            
            letterSourceDescriptionTransfer = new LetterSourceDescriptionTransfer(languageTransfer, letterSourceTransfer, letterSourceDescription.getDescription());
            put(letterSourceDescription, letterSourceDescriptionTransfer);
        }
        
        return letterSourceDescriptionTransfer;
    }
    
}
