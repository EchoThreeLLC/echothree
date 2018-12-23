// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.authentication.server.command;

import com.echothree.control.user.authentication.common.form.VendorLoginForm;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.logic.LockoutPolicyLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.control.user.server.logic.UserLoginLogic;
import com.echothree.model.control.vendor.common.workflow.VendorStatusConstants;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VendorLoginCommand
        extends BaseLoginCommand<VendorLoginForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("Password", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("RemoteInet4Address", FieldType.INET_4_ADDRESS, true, null, null)
                ));
    }
    
    /** Creates a new instance of VendorLoginCommand */
    public VendorLoginCommand(UserVisitPK userVisitPK, VendorLoginForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        UserLogin userLogin = UserLoginLogic.getInstance().getUserLoginByUsername(this, form.getUsername());

        if(!hasExecutionErrors()) {
            Party party = userLogin.getParty();
            PartyDetail partyDetail = party.getLastDetail();
            PartyLogic.getInstance().checkPartyType(this, party, PartyConstants.PartyType_VENDOR);

            if(!hasExecutionErrors()) {
                UserControl userControl = getUserControl();
                UserLoginStatus userLoginStatus = userControl.getUserLoginStatusForUpdate(party);

                if(!WorkflowLogic.getInstance().isEntityInWorkflowSteps(this, VendorStatusConstants.Workflow_VENDOR_STATUS, party,
                        VendorStatusConstants.WorkflowStep_ACTIVE).isEmpty()) {
                    LockoutPolicyLogic.getInstance().checkUserLogin(session, this, party, userLoginStatus);

                    if(!hasExecutionErrors()) {
                        if(checkPasswords(userLoginStatus, form.getPassword(), party, true)) {
                            Integer remoteInet4Address = Integer.valueOf(form.getRemoteInet4Address());

                            successfulLogin(userLoginStatus, party, null, remoteInet4Address);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.VendorNotActive.name(), partyDetail.getPartyName());
                }

                if(hasExecutionErrors()) {
                    unsuccessfulLogin(userLoginStatus);
                }
            }
        }

        return null;
    }
    
}
