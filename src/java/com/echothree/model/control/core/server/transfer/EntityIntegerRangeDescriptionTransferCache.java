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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.EntityIntegerRangeDescriptionTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityIntegerRangeDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<EntityIntegerRangeDescription, EntityIntegerRangeDescriptionTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of EntityIntegerRangeDescriptionTransferCache */
    public EntityIntegerRangeDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public EntityIntegerRangeDescriptionTransfer getEntityIntegerRangeDescriptionTransfer(EntityIntegerRangeDescription entityIntegerRangeDescription, EntityInstance entityInstance) {
        var entityIntegerRangeDescriptionTransfer = get(entityIntegerRangeDescription);
        
        if(entityIntegerRangeDescriptionTransfer == null) {
            var entityIntegerRangeTransfer = coreControl.getEntityIntegerRangeTransfer(userVisit, entityIntegerRangeDescription.getEntityIntegerRange(), entityInstance);
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, entityIntegerRangeDescription.getLanguage());
            
            entityIntegerRangeDescriptionTransfer = new EntityIntegerRangeDescriptionTransfer(languageTransfer, entityIntegerRangeTransfer,
                    entityIntegerRangeDescription.getDescription());
            put(entityIntegerRangeDescription, entityIntegerRangeDescriptionTransfer);
        }
        return entityIntegerRangeDescriptionTransfer;
    }
    
}
