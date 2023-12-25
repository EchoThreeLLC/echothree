// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.shipment.common.form.CreateShipmentAliasForm;
import com.echothree.control.user.shipment.server.command.util.ShipmentAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.Shipment;
import com.echothree.model.data.shipment.server.entity.ShipmentAliasType;
import com.echothree.model.data.shipment.server.entity.ShipmentAliasTypeDetail;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateShipmentAliasCommand
        extends BaseSimpleCommand<CreateShipmentAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateShipmentAliasCommand */
    public CreateShipmentAliasCommand(UserVisitPK userVisitPK, CreateShipmentAliasForm form) {
        super(userVisitPK, form, new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(ShipmentAliasUtil.getInstance().getSecurityRoleGroupNameByShipmentTypeSpec(form), SecurityRoles.Create.name())
                        )))
                ))), FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        String shipmentTypeName = form.getShipmentTypeName();
        ShipmentType shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            String shipmentName = form.getShipmentName();
            Shipment shipment = shipmentControl.getShipmentByName(shipmentType, shipmentName);

            if(shipment != null) {
                String shipmentAliasTypeName = form.getShipmentAliasTypeName();
                ShipmentAliasType shipmentAliasType = shipmentControl.getShipmentAliasTypeByName(shipmentType, shipmentAliasTypeName);

                if(shipmentAliasType != null) {
                    ShipmentAliasTypeDetail shipmentAliasTypeDetail = shipmentAliasType.getLastDetail();
                    String validationPattern = shipmentAliasTypeDetail.getValidationPattern();
                    String alias = form.getAlias();

                    if(validationPattern != null) {
                        Pattern pattern = Pattern.compile(validationPattern);
                        Matcher m = pattern.matcher(alias);

                        if(!m.matches()) {
                            addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
                        }
                    }

                    if(!hasExecutionErrors()) {
                        if(shipmentControl.getShipmentAlias(shipment, shipmentAliasType) == null && shipmentControl.getShipmentAliasByAlias(shipmentAliasType, alias) == null) {
                            shipmentControl.createShipmentAlias(shipment, shipmentAliasType, alias, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateShipmentAlias.name(), shipmentTypeName, shipmentName, shipmentAliasTypeName, alias);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownShipmentAliasTypeName.name(), shipmentTypeName, shipmentAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownShipmentName.name(), shipmentTypeName, shipmentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }

        return null;
    }
    
}
