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

package com.echothree.model.control.user.server.transfer;

import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.user.remote.transfer.UserLoginPasswordTransfer;
import com.echothree.model.control.user.remote.transfer.UserLoginPasswordTypeTransfer;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.user.server.entity.UserLoginPassword;
import com.echothree.model.data.user.server.entity.UserLoginPasswordString;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UserLoginPasswordTransferCache
        extends BaseUserTransferCache<UserLoginPassword, UserLoginPasswordTransfer> {
    
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

    /** Creates a new instance of UserLoginPasswordTransferCache */
    public UserLoginPasswordTransferCache(UserVisit userVisit, UserControl userControl) {
        super(userVisit, userControl);
    }
    
    public UserLoginPasswordTransfer getUserLoginPasswordTransfer(UserLoginPassword userLoginPassword) {
        UserLoginPasswordTransfer userLoginPasswordTransfer = get(userLoginPassword);
        
        if(userLoginPasswordTransfer == null) {
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, userLoginPassword.getParty());
            UserLoginPasswordTypeTransfer userLoginPasswordType = userControl.getUserLoginPasswordTypeTransfer(userVisit, userLoginPassword.getUserLoginPasswordType());
            String password = null;
            Long unformattedChangedTime = null;
            String changedTime = null;
            Boolean wasReset = null;

            String userLoginPasswordTypeName = userLoginPasswordType.getUserLoginPasswordTypeName();
            if(userLoginPasswordTypeName.equals(UserConstants.UserLoginPasswordType_STRING) ||
                    userLoginPasswordTypeName.equals(UserConstants.UserLoginPasswordType_RECOVERED_STRING)) {
                UserLoginPasswordString userLoginPasswordString = userControl.getUserLoginPasswordString(userLoginPassword);
                String userLoginPasswordEncoderTypeName = userLoginPasswordType.getUserLoginPasswordEncoderType().getUserLoginPasswordEncoderTypeName();

                // Allow only one very carefully checked case where the password will be returned in the TO. Only recovered passwords, and only if they are
                // plain text. Hashed passwords will never be returned.
                if(userLoginPasswordTypeName.equals(UserConstants.UserLoginPasswordType_RECOVERED_STRING) &&
                        userLoginPasswordEncoderTypeName.equals(UserConstants.UserLoginPasswordEncoderType_TEXT)) {
                    password = userLoginPasswordString.getPassword();
                }

                unformattedChangedTime = userLoginPasswordString.getChangedTime();
                changedTime = formatTypicalDateTime(unformattedChangedTime);
                wasReset = userLoginPasswordString.getWasReset();
            }

            userLoginPasswordTransfer = new UserLoginPasswordTransfer(party, userLoginPasswordType, password, unformattedChangedTime, changedTime, wasReset);
            put(userLoginPassword, userLoginPasswordTransfer);
        }
        
        return userLoginPasswordTransfer;
    }
    
}
