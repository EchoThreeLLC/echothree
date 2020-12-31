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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentCollectionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.GetContentCollectionResult;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetContentCollectionCommand
        extends BaseSingleEntityCommand<ContentCollection, GetContentCollectionForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentCollectionCommand */
    public GetContentCollectionCommand(UserVisitPK userVisitPK, GetContentCollectionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ContentCollection getEntity() {
        var contentControl = Session.getModelController(ContentControl.class);
        String contentCollectionName = form.getContentCollectionName();
        ContentCollection contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            sendEventUsingNames(contentCollection.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }
        
        return contentCollection;
    }
    
    @Override
    protected BaseResult getTransfer(ContentCollection contentCollection) {
        GetContentCollectionResult result = ContentResultFactory.getGetContentCollectionResult();
        
        if(contentCollection != null) {
            var contentControl = Session.getModelController(ContentControl.class);
    
            result.setContentCollection(contentControl.getContentCollectionTransfer(getUserVisit(), contentCollection));
        }
        
        return result;
    }
    
}
