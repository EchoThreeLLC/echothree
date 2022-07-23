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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.party.common.PartyProperties;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import java.util.Set;

public class LanguageTransferCache
        extends BasePartyTransferCache<Language, LanguageTransfer> {
    
    TransferProperties transferProperties;
    boolean filterLanguageIsoName;
    boolean filterisDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    
    /** Creates a new instance of LanguageTransferCache */
    public LanguageTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(LanguageTransfer.class);
            
            if(properties != null) {
                filterLanguageIsoName = !properties.contains(PartyProperties.LANGUAGE_ISO_NAME);
                filterisDefault = !properties.contains(PartyProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(PartyProperties.SORT_ORDER);
                filterDescription = !properties.contains(PartyProperties.DESCRIPTION);
            }
        }
    }
    
    public LanguageTransfer getLanguageTransfer(Language language) {
        LanguageTransfer languageTransfer = get(language);
        
        if(languageTransfer == null) {
            String languageIsoName = filterLanguageIsoName ? null : language.getLanguageIsoName();
            Boolean isDefault = filterisDefault ? null : language.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : language.getSortOrder();
            String description = filterDescription ? null : partyControl.getBestLanguageDescription(language, getLanguage());
            
            languageTransfer = new LanguageTransfer(languageIsoName, isDefault, sortOrder, description);
            put(language, languageTransfer);
        }
        
        return languageTransfer;
    }
    
}
