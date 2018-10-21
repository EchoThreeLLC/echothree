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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.remote.transfer.TextTransformationDescriptionTransfer;
import com.echothree.model.control.core.remote.transfer.TextTransformationTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.core.server.entity.TextTransformationDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TextTransformationDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<TextTransformationDescription, TextTransformationDescriptionTransfer> {
    
    /** Creates a new instance of TextTransformationDescriptionTransferCache */
    public TextTransformationDescriptionTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public TextTransformationDescriptionTransfer getTextTransformationDescriptionTransfer(TextTransformationDescription textTransformationDescription) {
        TextTransformationDescriptionTransfer textTransformationDescriptionTransfer = get(textTransformationDescription);
        
        if(textTransformationDescriptionTransfer == null) {
            TextTransformationTransfer textTransformationTransfer = coreControl.getTextTransformationTransfer(userVisit, textTransformationDescription.getTextTransformation());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, textTransformationDescription.getLanguage());
            
            textTransformationDescriptionTransfer = new TextTransformationDescriptionTransfer(languageTransfer, textTransformationTransfer, textTransformationDescription.getDescription());
            put(textTransformationDescription, textTransformationDescriptionTransfer);
        }
        return textTransformationDescriptionTransfer;
    }
    
}
