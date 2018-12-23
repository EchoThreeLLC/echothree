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

package com.echothree.control.user.comment.server.command;

import com.echothree.control.user.comment.common.edit.CommentEdit;
import com.echothree.control.user.comment.common.edit.CommentEditFactory;
import com.echothree.control.user.comment.common.form.EditCommentForm;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.control.user.comment.common.result.EditCommentResult;
import com.echothree.control.user.comment.common.spec.CommentSpec;
import com.echothree.model.control.comment.server.CommentControl;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.comment.server.entity.Comment;
import com.echothree.model.data.comment.server.entity.CommentBlob;
import com.echothree.model.data.comment.server.entity.CommentClob;
import com.echothree.model.data.comment.server.entity.CommentDetail;
import com.echothree.model.data.comment.server.entity.CommentString;
import com.echothree.model.data.comment.server.value.CommentBlobValue;
import com.echothree.model.data.comment.server.value.CommentClobValue;
import com.echothree.model.data.comment.server.value.CommentDetailValue;
import com.echothree.model.data.comment.server.value.CommentStringValue;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditCommentCommand
        extends BaseEditCommand<CommentSpec, CommentEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("CommentName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("ClobComment", FieldType.STRING, false, 1L, null),
                new FieldDefinition("StringComment", FieldType.STRING, false, 1L, 512L)
                // BlobComment is not validated
        ));
    }
    
    /** Creates a new instance of EditCommentCommand */
    public EditCommentCommand(UserVisitPK userVisitPK, EditCommentForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    protected void updateComment(CommentControl commentControl, Comment comment, Language language, String description, MimeType mimeType, BasePK updatedBy, ByteArray blob, String clob,
            String string) {
        if(lockEntityForUpdate(comment)) {
            try {
                CommentDetailValue commentDetailValue = commentControl.getCommentDetailValueForUpdate(comment);
                
                commentDetailValue.setLanguagePK(language.getPrimaryKey());
                commentDetailValue.setDescription(description);
                commentDetailValue.setMimeTypePK(mimeType == null? null: mimeType.getPrimaryKey());
                commentControl.updateCommentFromValue(commentDetailValue, updatedBy);
                
                CommentBlob commentBlob = commentControl.getCommentBlobForUpdate(comment);
                
                if(commentBlob != null) {
                    if(blob == null) {
                        commentControl.deleteCommentBlob(commentBlob, updatedBy);
                    } else {
                        CommentBlobValue commentBlobValue = commentControl.getCommentBlobValue(commentBlob);
                        
                        commentBlobValue.setBlob(blob);
                        commentControl.updateCommentBlobFromValue(commentBlobValue, updatedBy);
                    }
                } else if(blob != null) {
                    commentControl.createCommentBlob(comment, blob, updatedBy);
                }
                
                CommentClob commentClob = commentControl.getCommentClobForUpdate(comment);
                
                if(commentClob != null) {
                    if(clob == null) {
                        commentControl.deleteCommentClob(commentClob, updatedBy);
                    } else {
                        CommentClobValue commentClobValue = commentControl.getCommentClobValue(commentClob);
                        
                        commentClobValue.setClob(clob);
                        commentControl.updateCommentClobFromValue(commentClobValue, updatedBy);
                    }
                } else if(clob != null) {
                    commentControl.createCommentClob(comment, clob, updatedBy);
                }
                
                CommentString commentString = commentControl.getCommentStringForUpdate(comment);
                
                if(commentString != null) {
                    if(string == null) {
                        commentControl.deleteCommentString(commentString, updatedBy);
                    } else {
                        CommentStringValue commentStringValue = commentControl.getCommentStringValue(commentString);
                        
                        commentStringValue.setString(string);
                        commentControl.updateCommentStringFromValue(commentStringValue, updatedBy);
                    }
                } else if(string != null) {
                    commentControl.createCommentString(comment, string, updatedBy);
                }
            } finally {
                unlockEntity(comment);
            }
        } else {
            addExecutionError(ExecutionErrors.EntityLockStale.name());
        }
    }
    
    @Override
    protected BaseResult execute() {
        CommentControl commentControl = (CommentControl)Session.getModelController(CommentControl.class);
        EditCommentResult result = CommentResultFactory.getEditCommentResult();
        String commentName = spec.getCommentName();
        Comment comment = commentControl.getCommentByName(commentName);

        if(comment != null) {
            if(editMode.equals(EditMode.LOCK)) {
                if(lockEntity(comment)) {
                    CommentDetail commentDetail = comment.getLastDetail();
                    CommentEdit edit = CommentEditFactory.getCommentEdit();
                    MimeType mimeType = commentDetail.getMimeType();

                    result.setEdit(edit);

                    edit.setLanguageIsoName(commentDetail.getLanguage().getLanguageIsoName());
                    edit.setDescription(commentDetail.getDescription());
                    edit.setMimeTypeName(mimeType == null ? null : mimeType.getLastDetail().getMimeTypeName());

                    if(mimeType == null) {
                        CommentString commentString = commentControl.getCommentString(comment);

                        if(commentString != null) {
                            edit.setStringComment(commentString.getString());
                        }
                    } else {
                        String entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

                        // EntityAttributeTypes.BLOB.name() does not return anything in edit
                        if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                            CommentClob commentClob = commentControl.getCommentClob(comment);

                            if(commentClob != null) {
                                edit.setClobComment(commentClob.getClob());
                            }
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }

                result.setComment(commentControl.getCommentTransfer(getUserVisit(), comment));
                result.setEntityLock(getEntityLockTransfer(comment));
            } else if(editMode.equals(EditMode.ABANDON)) {
                unlockEntity(comment);
            } else if(editMode.equals(EditMode.UPDATE)) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = edit.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    BasePK updatedBy = getPartyPK();
                    String description = edit.getDescription();
                    String mimeTypeName = edit.getMimeTypeName();
                    MimeTypeUsageType mimeTypeUsageType = comment.getLastDetail().getCommentType().getLastDetail().getMimeTypeUsageType();

                    if(mimeTypeName == null) {
                        if(mimeTypeUsageType == null) {
                            String string = edit.getStringComment();

                            if(string != null) {
                                updateComment(commentControl, comment, language, description, null, updatedBy, null, null, string);
                            } else {
                                addExecutionError(ExecutionErrors.MissingCommentString.name());
                            }
                        } else {
                            // No mimeTypeName was supplied, but yet we required a MimeTypeUsageType
                            addExecutionError(ExecutionErrors.InvalidMimeType.name());
                        }
                    } else {
                        CoreControl coreControl = getCoreControl();
                        MimeType mimeType = coreControl.getMimeTypeByName(mimeTypeName);

                        if(mimeType != null) {
                            if(mimeTypeUsageType != null) {
                                MimeTypeUsage mimeTypeUsage = coreControl.getMimeTypeUsage(mimeType, mimeTypeUsageType);

                                if(mimeTypeUsage != null) {
                                    EntityAttributeType entityAttributeType = mimeType.getLastDetail().getEntityAttributeType();
                                    String entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();

                                    if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                                        ByteArray blob = edit.getBlobComment();

                                        if(blob != null) {
                                            updateComment(commentControl, comment, language, description, mimeType, updatedBy, blob, null, null);
                                        } else {
                                            addExecutionError(ExecutionErrors.MissingCommentBlob.name());
                                        }
                                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                                        String clob = edit.getClobComment();

                                        if(clob != null) {
                                            updateComment(commentControl, comment, language, description, mimeType, updatedBy, null, clob, null);
                                        } else {
                                            addExecutionError(ExecutionErrors.MissingCommentClob.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(),
                                                entityAttributeTypeName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownMimeTypeUsage.name());
                                }
                            } else {
                                // mimeTypeName was supplied, and there shouldn't be one
                                addExecutionError(ExecutionErrors.InvalidMimeType.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
                
                if(hasExecutionErrors()) {
                    result.setComment(commentControl.getCommentTransfer(getUserVisit(), comment));
                    result.setEntityLock(getEntityLockTransfer(comment));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommentName.name(), commentName);
        }

        return result;
    }
    
}
