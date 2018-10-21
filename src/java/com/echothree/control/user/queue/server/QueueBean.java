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

package com.echothree.control.user.queue.server;

import com.echothree.control.user.queue.remote.QueueRemote;
import com.echothree.control.user.queue.remote.form.*;
import com.echothree.control.user.queue.server.command.*;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.remote.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class QueueBean
        extends QueueFormsImpl
        implements QueueRemote, QueueLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "QueueBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Queue Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createQueueType(UserVisitPK userVisitPK, CreateQueueTypeForm form) {
        return new CreateQueueTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getQueueTypeChoices(UserVisitPK userVisitPK, GetQueueTypeChoicesForm form) {
        return new GetQueueTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getQueueType(UserVisitPK userVisitPK, GetQueueTypeForm form) {
        return new GetQueueTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getQueueTypes(UserVisitPK userVisitPK, GetQueueTypesForm form) {
        return new GetQueueTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultQueueType(UserVisitPK userVisitPK, SetDefaultQueueTypeForm form) {
        return new SetDefaultQueueTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editQueueType(UserVisitPK userVisitPK, EditQueueTypeForm form) {
        return new EditQueueTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteQueueType(UserVisitPK userVisitPK, DeleteQueueTypeForm form) {
        return new DeleteQueueTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Queue Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createQueueTypeDescription(UserVisitPK userVisitPK, CreateQueueTypeDescriptionForm form) {
        return new CreateQueueTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getQueueTypeDescription(UserVisitPK userVisitPK, GetQueueTypeDescriptionForm form) {
        return new GetQueueTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getQueueTypeDescriptions(UserVisitPK userVisitPK, GetQueueTypeDescriptionsForm form) {
        return new GetQueueTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editQueueTypeDescription(UserVisitPK userVisitPK, EditQueueTypeDescriptionForm form) {
        return new EditQueueTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteQueueTypeDescription(UserVisitPK userVisitPK, DeleteQueueTypeDescriptionForm form) {
        return new DeleteQueueTypeDescriptionCommand(userVisitPK, form).run();
    }
    
}
