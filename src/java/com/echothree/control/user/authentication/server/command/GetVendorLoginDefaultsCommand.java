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

package com.echothree.control.user.authentication.server.command;

import com.echothree.control.user.authentication.remote.form.AuthenticationFormFactory;
import com.echothree.control.user.authentication.remote.form.GetVendorLoginDefaultsForm;
import com.echothree.control.user.authentication.remote.form.VendorLoginForm;
import com.echothree.control.user.authentication.remote.result.AuthenticationResultFactory;
import com.echothree.control.user.authentication.remote.result.GetVendorLoginDefaultsResult;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetVendorLoginDefaultsCommand
        extends BaseSimpleCommand<GetVendorLoginDefaultsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetVendorLoginDefaultsCommand */
    public GetVendorLoginDefaultsCommand(UserVisitPK userVisitPK, GetVendorLoginDefaultsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetVendorLoginDefaultsResult result = AuthenticationResultFactory.getGetVendorLoginDefaultsResult();
        UserControl userControl = getUserControl();
        UserSession userSession = userControl.getUserSessionByUserVisit(getUserVisit());
        String username = null;
        
        if(userSession != null) {
            Party party = userSession.getParty();
            
            if(party != null) {
                if(party.getLastDetail().getPartyType().getPartyTypeName().equals(PartyConstants.PartyType_VENDOR)) {
                    UserLogin userLogin = userControl.getUserLogin(party);
                    
                    username = userLogin.getUsername();
                }
            }
        }
        
        VendorLoginForm vendorLoginForm = AuthenticationFormFactory.getVendorLoginForm();
        vendorLoginForm.setUsername(username);
        result.setVendorLoginForm(vendorLoginForm);
        
        return result;
    }
    
}
