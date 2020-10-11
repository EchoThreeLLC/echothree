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

package com.echothree.control.user.shipment.server.command;

import com.echothree.control.user.shipment.common.form.GetShipmentTypeShippingMethodsForm;
import com.echothree.control.user.shipment.common.result.GetShipmentTypeShippingMethodsResult;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetShipmentTypeShippingMethodsCommand
        extends BaseSimpleCommand<GetShipmentTypeShippingMethodsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetShipmentTypeShippingMethodsCommand */
    public GetShipmentTypeShippingMethodsCommand(UserVisitPK userVisitPK, GetShipmentTypeShippingMethodsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        GetShipmentTypeShippingMethodsResult result = ShipmentResultFactory.getGetShipmentTypeShippingMethodsResult();
        String shipmentTypeName = form.getShipmentTypeName();
        String shippingMethodName = form.getShippingMethodName();
        int parameterCount = (shipmentTypeName != null? 1: 0) + (shippingMethodName != null? 1: 0);
        
        if(parameterCount == 1) {
            if(shipmentTypeName != null) {
                ShipmentType shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);
                
                if(shipmentType != null) {
                    result.setShipmentType(shipmentControl.getShipmentTypeTransfer(getUserVisit(), shipmentType));
                    result.setShipmentTypeShippingMethods(shipmentControl.getShipmentTypeShippingMethodTransfersByShipmentType(getUserVisit(),
                            shipmentType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
                }
            } else if(shippingMethodName != null) {
                var shippingControl = (ShippingControl)Session.getModelController(ShippingControl.class);
                ShippingMethod shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);
                
                if(shippingMethod != null) {
                    result.setShippingMethod(shippingControl.getShippingMethodTransfer(getUserVisit(), shippingMethod));
                    result.setShipmentTypeShippingMethods(shipmentControl.getShipmentTypeShippingMethodTransfersByShippingMethod(getUserVisit(),
                            shippingMethod));
                } else {
                    addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
