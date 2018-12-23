// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.forum.server.command;

import com.echothree.control.user.forum.common.form.CreateForumMessageAttachmentDescriptionForm;
import com.echothree.control.user.forum.common.result.ForumResultFactory;
import com.echothree.control.user.forum.common.result.GetForumMessageAttachmentDescriptionsResult;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachment;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachmentDescription;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateForumMessageAttachmentDescriptionCommand
        extends BaseSimpleCommand<CreateForumMessageAttachmentDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumMessageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumMessageAttachmentSequence", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateForumMessageAttachmentDescriptionCommand */
    public CreateForumMessageAttachmentDescriptionCommand(UserVisitPK userVisitPK, CreateForumMessageAttachmentDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        ForumControl forumControl = (ForumControl)Session.getModelController(ForumControl.class);
        GetForumMessageAttachmentDescriptionsResult result = ForumResultFactory.getGetForumMessageAttachmentDescriptionsResult();
        String forumMessageName = form.getForumMessageName();
        ForumMessage forumMessage = forumControl.getForumMessageByNameForUpdate(forumMessageName);

        if(forumMessage != null) {
            Integer forumMessageAttachmentSequence = Integer.valueOf(form.getForumMessageAttachmentSequence());
            ForumMessageAttachment forumMessageAttachment = forumControl.getForumMessageAttachmentBySequence(forumMessage, forumMessageAttachmentSequence);

            if(forumMessageAttachment != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = form.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    ForumMessageAttachmentDescription forumMessageAttachmentDescription = forumControl.getForumMessageAttachmentDescription(forumMessageAttachment, language);

                    if(forumMessageAttachmentDescription == null) {
                        String description = form.getDescription();

                        forumControl.createForumMessageAttachmentDescription(forumMessageAttachment, language, description, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateForumMessageAttachmentDescription.name(), forumMessageName,
                                forumMessageAttachmentSequence.toString(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownForumMessageAttachment.name(), forumMessageName, forumMessageAttachmentSequence.toString());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumMessageName.name(), forumMessageName);
        }
        
        return null;
    }
    
}
