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

import com.echothree.control.user.core.common.form.GetMimeTypeChoicesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.document.server.DocumentControl;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.data.comment.server.entity.Comment;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumForumThread;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
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

public class GetMimeTypeChoicesCommand
        extends BaseSimpleCommand<GetMimeTypeChoicesForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DefaultMimeTypeChoice", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumMessageName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CommentName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DocumentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DocumentName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of GetMimeTypeChoicesCommand */
    public GetMimeTypeChoicesCommand(UserVisitPK userVisitPK, GetMimeTypeChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        GetMimeTypeChoicesResult result = CoreResultFactory.getGetMimeTypeChoicesResult();
        String mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();
        String itemDescriptionTypeName = form.getItemDescriptionTypeName();
        String forumName = form.getForumName();
        String forumMessageName = form.getForumMessageName();
        String componentVendorName = form.getComponentVendorName();
        String entityTypeName = form.getEntityTypeName();
        String commentTypeName = form.getCommentTypeName();
        String commentName = form.getCommentName();
        String documentTypeName = form.getDocumentTypeName();
        String documentName = form.getDocumentName();
        int parameterCount = (mimeTypeUsageTypeName != null? 1: 0) + (itemDescriptionTypeName != null? 1: 0) + (forumName != null? 1: 0)
                + (forumMessageName != null? 1: 0) + (componentVendorName != null && entityTypeName != null && commentTypeName != null? 1: 0)
                + (commentName != null? 1: 0) + (documentTypeName != null? 1: 0) + (documentName != null? 1: 0);

        if(parameterCount == 1) {
        String defaultMimeTypeChoice = form.getDefaultMimeTypeChoice();
        boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
        
            if(mimeTypeUsageTypeName != null || itemDescriptionTypeName != null || documentTypeName != null || documentName != null) {
                MimeTypeUsageType mimeTypeUsageType = null;
        
                if(mimeTypeUsageTypeName != null) {
                    mimeTypeUsageType = coreControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);

                    if(mimeTypeUsageType == null) {
                        addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
                    }
                } else if(itemDescriptionTypeName != null) {
                    var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
                    ItemDescriptionType itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);

                    if(itemDescriptionType != null) {
                        mimeTypeUsageType = itemDescriptionType.getLastDetail().getMimeTypeUsageType();

                        if(mimeTypeUsageType == null) {
                            addExecutionError(ExecutionErrors.InvalidItemDescriptionType.name(), itemDescriptionTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
                    }
                } else if(documentTypeName != null || documentName != null) {
                    var documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);

                    if(documentTypeName != null) {
                        DocumentType documentType = documentControl.getDocumentTypeByName(documentTypeName);

                        if(documentType != null) {
                            mimeTypeUsageType = documentType.getLastDetail().getMimeTypeUsageType();

                            if(mimeTypeUsageType == null) {
                                addExecutionError(ExecutionErrors.InvalidDocumentType.name(), documentTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownDocumentTypeName.name(), documentTypeName);
                        }
                    } else if(documentName != null) {
                        Document document = documentControl.getDocumentByName(documentName);

                        if(document != null) {
                            mimeTypeUsageType = document.getLastDetail().getDocumentType().getLastDetail().getMimeTypeUsageType();

                            if(mimeTypeUsageType == null) {
                                addExecutionError(ExecutionErrors.InvalidDocument.name(), documentName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownDocumentName.name(), documentName);
                        }
                    }
                }

                if(mimeTypeUsageType != null) {
                    result.setMimeTypeChoices(coreControl.getMimeTypeChoices(mimeTypeUsageType, defaultMimeTypeChoice,
                            getPreferredLanguage(), allowNullChoice));
                }
            } else if(forumName != null || forumMessageName != null) {
                var forumControl = (ForumControl)Session.getModelController(ForumControl.class);

                if(forumName != null) {
                    Forum forum = forumControl.getForumByName(forumName);

                    if(forum != null) {
                        result.setMimeTypeChoices(forumControl.getForumMimeTypeChoices(forum, defaultMimeTypeChoice,
                                getPreferredLanguage(), allowNullChoice));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
                    }
                } else if(forumMessageName != null) {
                    ForumMessage forumMessage = forumControl.getForumMessageByName(forumMessageName);

                    if(forumMessage != null) {
                        ForumThread forumThread = forumMessage.getLastDetail().getForumThread();
                        ForumForumThread forumForumThread = forumControl.getDefaultForumForumThread(forumThread);

                        result.setMimeTypeChoices(forumControl.getForumMimeTypeChoices(forumForumThread.getForum(),
                                defaultMimeTypeChoice, getPreferredLanguage(), allowNullChoice));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownForumMessageName.name(), forumMessageName);
                    }
                }
            } else if((componentVendorName != null && entityTypeName != null && commentTypeName != null) || commentName != null) {
                var commentControl = (CommentControl)Session.getModelController(CommentControl.class);
                MimeTypeUsageType mimeTypeUsageType = null;

                if(commentName != null) {
                    Comment comment = commentControl.getCommentByName(commentName);

                    if(comment != null) {
                        mimeTypeUsageType = comment.getLastDetail().getCommentType().getLastDetail().getMimeTypeUsageType();
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommentName.name(), commentName);
                    }
                } else {
                    ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);

                    if(componentVendor != null) {
                        EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);

                        if(entityType != null) {
                            CommentType commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);

                            if(commentType != null) {
                                mimeTypeUsageType = commentType.getLastDetail().getMimeTypeUsageType();
                            } else {
                                addExecutionError(ExecutionErrors.UnknownCommentTypeName.name(), commentTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                    }
                }

                if(mimeTypeUsageType != null) {
                    result.setMimeTypeChoices(coreControl.getMimeTypeChoices(mimeTypeUsageType, defaultMimeTypeChoice, getPreferredLanguage(), allowNullChoice));
                } else {
                    addExecutionError(ExecutionErrors.InvalidCommentType.name(), commentTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }
    
}
