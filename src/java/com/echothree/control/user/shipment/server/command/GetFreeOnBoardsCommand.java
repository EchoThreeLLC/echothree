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

import com.echothree.control.user.shipment.common.form.GetFreeOnBoardsForm;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.result.GetFreeOnBoardsResult;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetFreeOnBoardsCommand
        extends BaseMultipleEntitiesCommand<FreeOnBoard, GetFreeOnBoardsForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetFreeOnBoardsCommand */
    public GetFreeOnBoardsCommand(UserVisitPK userVisitPK, GetFreeOnBoardsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<FreeOnBoard> getEntities() {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        
        return freeOnBoardControl.getFreeOnBoards();
    }
    
    @Override
    protected BaseResult getTransfers(Collection<FreeOnBoard> entities) {
        GetFreeOnBoardsResult result = ShipmentResultFactory.getGetFreeOnBoardsResult();
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        
        result.setFreeOnBoards(freeOnBoardControl.getFreeOnBoardTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}
