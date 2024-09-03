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

import com.echothree.model.control.core.common.transfer.EntityListItemDescriptionTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItemDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityListItemDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<EntityListItemDescription, EntityListItemDescriptionTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of EntityListItemDescriptionTransferCache */
    public EntityListItemDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public EntityListItemDescriptionTransfer getEntityListItemDescriptionTransfer(EntityListItemDescription entityListItemDescription, EntityInstance entityInstance) {
        var entityListItemDescriptionTransfer = get(entityListItemDescription);
        
        if(entityListItemDescriptionTransfer == null) {
            var entityListItemTransfer = coreControl.getEntityListItemTransfer(userVisit, entityListItemDescription.getEntityListItem(), entityInstance);
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, entityListItemDescription.getLanguage());
            
            entityListItemDescriptionTransfer = new EntityListItemDescriptionTransfer(languageTransfer, entityListItemTransfer,
                    entityListItemDescription.getDescription());
            put(entityListItemDescription, entityListItemDescriptionTransfer);
        }
        return entityListItemDescriptionTransfer;
    }
    
}
