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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.remote.form.GetUnitOfMeasureKindUseTypesForm;
import com.echothree.control.user.uom.remote.result.GetUnitOfMeasureKindUseTypesResult;
import com.echothree.control.user.uom.remote.result.UomResultFactory;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetUnitOfMeasureKindUseTypesCommand
        extends BaseMultipleEntitiesCommand<UnitOfMeasureKindUseType, GetUnitOfMeasureKindUseTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetUnitOfMeasureKindUseTypesCommand */
    public GetUnitOfMeasureKindUseTypesCommand(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<UnitOfMeasureKindUseType> getEntities() {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        
        return uomControl.getUnitOfMeasureKindUseTypes();
    }
    
    @Override
    protected BaseResult getTransfers(Collection<UnitOfMeasureKindUseType> entities) {
        GetUnitOfMeasureKindUseTypesResult result = UomResultFactory.getGetUnitOfMeasureKindUseTypesResult();
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        
        result.setUnitOfMeasureKindUseTypes(uomControl.getUnitOfMeasureKindUseTypeTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}
