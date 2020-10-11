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

package com.echothree.control.user.shipping.server.command;

import com.echothree.control.user.shipping.common.form.GetShippingMethodCarrierServicesForm;
import com.echothree.control.user.shipping.common.result.GetShippingMethodCarrierServicesResult;
import com.echothree.control.user.shipping.common.result.ShippingResultFactory;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
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

public class GetShippingMethodCarrierServicesCommand
        extends BaseSimpleCommand<GetShippingMethodCarrierServicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShippingMethodCarrierService.name(), SecurityRoles.List.name())
                        )))
                )));
    }

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetShippingMethodCarrierServicesCommand */
    public GetShippingMethodCarrierServicesCommand(UserVisitPK userVisitPK, GetShippingMethodCarrierServicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var shippingControl = (ShippingControl)Session.getModelController(ShippingControl.class);
        GetShippingMethodCarrierServicesResult result = ShippingResultFactory.getGetShippingMethodCarrierServicesResult();
        String shippingMethodName = form.getShippingMethodName();
        String carrierName = form.getCarrierName();
        String carrierServiceName = form.getCarrierServiceName();
        
        if(shippingMethodName != null && carrierName == null && carrierServiceName == null) {
            ShippingMethod shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);
            
            if(shippingMethod != null) {
                result.setShippingMethod(shippingControl.getShippingMethodTransfer(getUserVisit(), shippingMethod));
                result.setShippingMethodCarrierServices(shippingControl.getShippingMethodCarrierServiceTransfersByShippingMethod(getUserVisit(),
                        shippingMethod));
            } else {
                addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
            }
        } else if(shippingMethodName == null && carrierName != null && carrierServiceName != null) {
            var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
            Carrier carrier = carrierControl.getCarrierByName(carrierName);
            
            if(carrier != null) {
                Party carrierParty = carrier.getParty();
                CarrierService carrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);
                
                if(carrierService != null) {
                    result.setCarrierService(carrierControl.getCarrierServiceTransfer(getUserVisit(), carrierService));
                    result.setShippingMethodCarrierServices(shippingControl.getShippingMethodCarrierServiceTransfersByCarrierService(getUserVisit(),
                            carrierService));
                } else {
                    addExecutionError(ExecutionErrors.UnknownCarrierServiceName.name(), carrierServiceName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
            }
            
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
