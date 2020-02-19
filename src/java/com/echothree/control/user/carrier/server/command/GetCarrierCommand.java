// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.carrier.common.form.GetCarrierForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.GetCarrierResult;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
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

public class GetCarrierCommand
        extends BaseSimpleCommand<GetCarrierForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Carrier.name(), SecurityRoles.Review.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetCarrierCommand */
    public GetCarrierCommand(UserVisitPK userVisitPK, GetCarrierForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetCarrierResult result = CarrierResultFactory.getGetCarrierResult();
        String carrierName = form.getCarrierName();
        String partyName = form.getPartyName();
        int parameterCount = (carrierName == null? 0: 1) + (partyName == null? 0: 1);
        
        if(parameterCount == 1) {
            var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
            Carrier carrier = null;
            
            if(carrierName != null) {
                carrier = carrierControl.getCarrierByName(carrierName);
                
                if(carrier == null) {
                    addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
                }
            } else {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                Party party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    PartyType partyType = partyControl.getPartyTypeByName(PartyTypes.CARRIER.name());
                    
                    if(party.getLastDetail().getPartyType().equals(partyType)) {
                        carrier = carrierControl.getCarrier(party);
                    } else {
                        addExecutionError(ExecutionErrors.InvalidPartyType.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            }
            
            if(!hasExecutionErrors()) {
                result.setCarrier(carrierControl.getCarrierTransfer(getUserVisit(), carrier));
                
                sendEventUsingNames(carrier.getPartyPK(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
