// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.term.server.transfer;

import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class TermTransferCaches
        extends BaseTransferCaches {
    
    protected TermControl termControl;
    
    protected TermTypeTransferCache termTypeTransferCache;
    protected TermTransferCache termTransferCache;
    protected TermDescriptionTransferCache termDescriptionTransferCache;
    protected PartyTermTransferCache partyTermTransferCache;
    protected CustomerTypeCreditLimitTransferCache customerTypeCreditLimitTransferCache;
    protected PartyCreditLimitTransferCache partyCreditLimitTransferCache;
    
    /** Creates a new instance of TermTransferCaches */
    public TermTransferCaches(UserVisit userVisit, TermControl termControl) {
        super(userVisit);
        
        this.termControl = termControl;
    }
    
    public TermTypeTransferCache getTermTypeTransferCache() {
        if(termTypeTransferCache == null)
            termTypeTransferCache = new TermTypeTransferCache(userVisit, termControl);
        
        return termTypeTransferCache;
    }
    
    public TermTransferCache getTermTransferCache() {
        if(termTransferCache == null)
            termTransferCache = new TermTransferCache(userVisit, termControl);
        
        return termTransferCache;
    }
    
    public TermDescriptionTransferCache getTermDescriptionTransferCache() {
        if(termDescriptionTransferCache == null)
            termDescriptionTransferCache = new TermDescriptionTransferCache(userVisit, termControl);
        
        return termDescriptionTransferCache;
    }
    
    public PartyTermTransferCache getPartyTermTransferCache() {
        if(partyTermTransferCache == null)
            partyTermTransferCache = new PartyTermTransferCache(userVisit, termControl);
        
        return partyTermTransferCache;
    }
    
    public CustomerTypeCreditLimitTransferCache getCustomerTypeCreditLimitTransferCache() {
        if(customerTypeCreditLimitTransferCache == null)
            customerTypeCreditLimitTransferCache = new CustomerTypeCreditLimitTransferCache(userVisit, termControl);
        
        return customerTypeCreditLimitTransferCache;
    }
    
    public PartyCreditLimitTransferCache getPartyCreditLimitTransferCache() {
        if(partyCreditLimitTransferCache == null)
            partyCreditLimitTransferCache = new PartyCreditLimitTransferCache(userVisit, termControl);
        
        return partyCreditLimitTransferCache;
    }
    
}
