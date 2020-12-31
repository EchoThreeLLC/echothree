// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.shipment.common.form.GetFreeOnBoardForm;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.result.GetFreeOnBoardResult;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.control.shipment.server.logic.FreeOnBoardLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetFreeOnBoardCommand
        extends BaseSingleEntityCommand<FreeOnBoard, GetFreeOnBoardForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FreeOnBoardName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetFreeOnBoardCommand */
    public GetFreeOnBoardCommand(UserVisitPK userVisitPK, GetFreeOnBoardForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected FreeOnBoard getEntity() {
        FreeOnBoard freeOnBoard = FreeOnBoardLogic.getInstance().getFreeOnBoardByUniversalSpec(this, form, true);

        if(freeOnBoard != null) {
            sendEventUsingNames(freeOnBoard.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        }

        return freeOnBoard;
    }
    
    @Override
    protected BaseResult getTransfer(FreeOnBoard freeOnBoard) {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        GetFreeOnBoardResult result = ShipmentResultFactory.getGetFreeOnBoardResult();

        if(freeOnBoard != null) {
            result.setFreeOnBoard(freeOnBoardControl.getFreeOnBoardTransfer(getUserVisit(), freeOnBoard));
        }

        return result;
    }
    
}
