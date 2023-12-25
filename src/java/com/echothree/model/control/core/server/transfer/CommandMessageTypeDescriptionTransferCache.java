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

import com.echothree.model.control.core.common.transfer.CommandMessageTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.core.server.entity.CommandMessageTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CommandMessageTypeDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<CommandMessageTypeDescription, CommandMessageTypeDescriptionTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of CommandMessageTypeDescriptionTransferCache */
    public CommandMessageTypeDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public CommandMessageTypeDescriptionTransfer getCommandMessageTypeDescriptionTransfer(CommandMessageTypeDescription commandMessageTypeDescription) {
        CommandMessageTypeDescriptionTransfer commandMessageTypeDescriptionTransfer = get(commandMessageTypeDescription);
        
        if(commandMessageTypeDescriptionTransfer == null) {
            CommandMessageTypeTransfer commandMessageTypeTransfer = coreControl.getCommandMessageTypeTransfer(userVisit, commandMessageTypeDescription.getCommandMessageType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, commandMessageTypeDescription.getLanguage());
            
            commandMessageTypeDescriptionTransfer = new CommandMessageTypeDescriptionTransfer(languageTransfer, commandMessageTypeTransfer, commandMessageTypeDescription.getDescription());
            put(commandMessageTypeDescription, commandMessageTypeDescriptionTransfer);
        }
        
        return commandMessageTypeDescriptionTransfer;
    }
    
}
