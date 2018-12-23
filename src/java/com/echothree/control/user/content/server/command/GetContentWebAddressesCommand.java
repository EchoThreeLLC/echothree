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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentWebAddressesForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.GetContentWebAddressesResult;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetContentWebAddressesCommand
        extends BaseMultipleEntitiesCommand<ContentWebAddress, GetContentWebAddressesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentWebAddress.name(), SecurityRoles.List.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetContentWebAddressesCommand */
    public GetContentWebAddressesCommand(UserVisitPK userVisitPK, GetContentWebAddressesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<ContentWebAddress> getEntities() {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        
        return contentControl.getContentWebAddresses();
    }
    
    @Override
    protected BaseResult getTransfers(Collection<ContentWebAddress> entities) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        GetContentWebAddressesResult result = ContentResultFactory.getGetContentWebAddressesResult();
        
        if(entities != null) {
            result.setContentWebAddresses(contentControl.getContentWebAddressTransfers(getUserVisit(), entities));
        }
        
        return result;
    }
    
}
