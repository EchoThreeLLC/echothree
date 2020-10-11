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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.UnlockEntityForm;
import com.echothree.model.control.core.server.control.EntityLockControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UnlockEntityCommand
        extends BaseSimpleCommand<UnlockEntityForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of UnlockEntityCommand */
    public UnlockEntityCommand(UserVisitPK userVisitPK, UnlockEntityForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, null);

        if(!hasExecutionErrors()) {
            var partyPK = getPartyPK();
            
            if(partyPK != null) {
                var entityLockControl = (EntityLockControl)Session.getModelController(EntityLockControl.class);
                BasePK basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

                if(entityLockControl.lockEntityForUpdate(basePK, getPartyPK())) {
                    entityLockControl.unlockEntity(basePK, partyPK);
                } else {
                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UserVisitHasNoParty.name());
            }
        }

        return null;
    }
    
}
