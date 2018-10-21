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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.remote.form.GetCarrierServiceChoicesForm;
import com.echothree.control.user.carrier.remote.result.CarrierResultFactory;
import com.echothree.control.user.carrier.remote.result.GetCarrierServiceChoicesResult;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetCarrierServiceChoicesCommand
        extends BaseSimpleCommand<GetCarrierServiceChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierService.name(), SecurityRoles.Choices.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultCarrierServiceChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetCarrierServiceChoicesCommand */
    public GetCarrierServiceChoicesCommand(UserVisitPK userVisitPK, GetCarrierServiceChoicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CarrierControl carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        GetCarrierServiceChoicesResult result = CarrierResultFactory.getGetCarrierServiceChoicesResult();
        String carrierName = form.getCarrierName();
        Carrier carrier = carrierControl.getCarrierByName(carrierName);
        
        if(carrier != null) {
            Party carrierParty = carrier.getParty();
            String defaultCarrierServiceChoice = form.getDefaultCarrierServiceChoice();
            boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            result.setCarrierServiceChoices(carrierControl.getCarrierServiceChoices(defaultCarrierServiceChoice, getPreferredLanguage(), allowNullChoice,
                    carrierParty));
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }
        
        return result;
    }
    
}
