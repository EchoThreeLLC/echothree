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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.form.CreateCarrierServiceDescriptionForm;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.carrier.server.entity.CarrierServiceDescription;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCarrierServiceDescriptionCommand
        extends BaseSimpleCommand<CreateCarrierServiceDescriptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierService.name(), SecurityRoles.Description.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateCarrierServiceDescriptionCommand */
    public CreateCarrierServiceDescriptionCommand(UserVisitPK userVisitPK, CreateCarrierServiceDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CarrierControl carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        String carrierName = form.getCarrierName();
        Carrier carrier = carrierControl.getCarrierByName(carrierName);
        
        if(carrier != null) {
            Party carrierParty = carrier.getParty();
            String carrierServiceName = form.getCarrierServiceName();
            CarrierService carrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);
            
            if(carrierService != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = form.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    CarrierServiceDescription carrierServiceDescription = carrierControl.getCarrierServiceDescription(carrierService, language);
                    
                    if(carrierServiceDescription == null) {
                        String description = form.getDescription();
                        
                        carrierControl.createCarrierServiceDescription(carrierService, language, description, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateCarrierServiceDescription.name(), carrierName, carrierServiceName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierServiceName.name(), carrierName, carrierServiceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }
        
        return null;
    }
    
}
