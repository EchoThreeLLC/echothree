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

package com.echothree.model.control.security.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleDescriptionTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleTransfer;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRoleDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SecurityRoleDescriptionTransferCache
        extends BaseSecurityDescriptionTransferCache<SecurityRoleDescription, SecurityRoleDescriptionTransfer> {
    
    /** Creates a new instance of SecurityRoleDescriptionTransferCache */
    public SecurityRoleDescriptionTransferCache(UserVisit userVisit, SecurityControl securityControl) {
        super(userVisit, securityControl);
    }
    
    public SecurityRoleDescriptionTransfer getSecurityRoleDescriptionTransfer(SecurityRoleDescription securityRoleDescription) {
        SecurityRoleDescriptionTransfer securityRoleDescriptionTransfer = get(securityRoleDescription);
        
        if(securityRoleDescriptionTransfer == null) {
            SecurityRoleTransferCache securityRoleTransferCache = securityControl.getSecurityTransferCaches(userVisit).getSecurityRoleTransferCache();
            SecurityRoleTransfer securityRoleTransfer = securityRoleTransferCache.getSecurityRoleTransfer(securityRoleDescription.getSecurityRole());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, securityRoleDescription.getLanguage());
            
            securityRoleDescriptionTransfer = new SecurityRoleDescriptionTransfer(languageTransfer, securityRoleTransfer, securityRoleDescription.getDescription());
            put(securityRoleDescription, securityRoleDescriptionTransfer);
        }
        
        return securityRoleDescriptionTransfer;
    }
    
}
