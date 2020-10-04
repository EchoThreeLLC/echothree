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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.edit.DocumentEditFactory;
import com.echothree.control.user.document.common.edit.PartyDocumentEdit;
import com.echothree.control.user.document.common.form.EditPartyDocumentForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.control.user.document.common.result.EditPartyDocumentResult;
import com.echothree.control.user.document.common.spec.DocumentSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.document.server.DocumentControl;
import com.echothree.model.control.document.server.logic.DocumentLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.document.server.entity.DocumentBlob;
import com.echothree.model.data.document.server.entity.DocumentClob;
import com.echothree.model.data.document.server.entity.DocumentDescription;
import com.echothree.model.data.document.server.entity.PartyDocument;
import com.echothree.model.data.document.server.value.DocumentBlobValue;
import com.echothree.model.data.document.server.value.DocumentClobValue;
import com.echothree.model.data.document.server.value.DocumentDescriptionValue;
import com.echothree.model.data.document.server.value.DocumentDetailValue;
import com.echothree.model.data.document.server.value.PartyDocumentValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPartyDocumentCommand
        extends BaseAbstractEditCommand<DocumentSpec, PartyDocumentEdit, EditPartyDocumentResult, PartyDocument, Document> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PartyDocument.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("Clob", FieldType.STRING, false, 1L, null)
                ));
    }
    
    /** Creates a new instance of EditPartyDocumentCommand */
    public EditPartyDocumentCommand(UserVisitPK userVisitPK, EditPartyDocumentForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartyDocumentResult getResult() {
        return DocumentResultFactory.getEditPartyDocumentResult();
    }

    @Override
    public PartyDocumentEdit getEdit() {
        return DocumentEditFactory.getPartyDocumentEdit();
    }

    @Override
    public PartyDocument getEntity(EditPartyDocumentResult result) {
        var documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
        PartyDocument partyDocument = null;
        String documentName = spec.getDocumentName();
        Document document = documentControl.getDocumentByName(documentName);

        if(document != null) {
            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                partyDocument = documentControl.getPartyDocument(document);
            } else { // EditMode.UPDATE
                partyDocument = documentControl.getPartyDocumentForUpdate(document);
            }

            if(partyDocument != null) {
                result.setPartyDocument(documentControl.getPartyDocumentTransfer(getUserVisit(), partyDocument));
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyDocument.name(), documentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownDocumentName.name(), documentName);
        }

        return partyDocument;
    }

    @Override
    public Document getLockEntity(PartyDocument partyDocument) {
        return partyDocument.getDocument();
    }

    @Override
    public void fillInResult(EditPartyDocumentResult result, PartyDocument partyDocument) {
        var documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);

        result.setPartyDocument(documentControl.getPartyDocumentTransfer(getUserVisit(), partyDocument));
    }

    MimeType mimeType;

    @Override
    public void doLock(PartyDocumentEdit edit, PartyDocument partyDocument) {
        var documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
        Document document = partyDocument.getDocument();
        DocumentDescription documentDescription = documentControl.getDocumentDescription(document, getPreferredLanguage());

        mimeType = document.getLastDetail().getMimeType();

        edit.setMimeTypeName(mimeType.getLastDetail().getMimeTypeName());
        edit.setIsDefault(partyDocument.getIsDefault().toString());
        edit.setSortOrder(partyDocument.getSortOrder().toString());

        if(mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(EntityAttributeTypes.CLOB.name())) {
            DocumentClob documentClob = documentControl.getDocumentClob(document);

            edit.setClob(documentClob.getClob());
        }

        if(documentDescription != null) {
            edit.setDescription(documentDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(PartyDocument partyDocument) {
        var coreControl = getCoreControl();
        String mimeTypeName = edit.getMimeTypeName();

        mimeType = coreControl.getMimeTypeByName(mimeTypeName);

        if(mimeType != null) {
            MimeTypeUsageType mimeTypeUsageType = partyDocument.getDocument().getLastDetail().getDocumentType().getLastDetail().getMimeTypeUsageType();
            MimeTypeUsage mimeTypeUsage = mimeTypeUsageType == null ? null : coreControl.getMimeTypeUsage(mimeType, mimeTypeUsageType);

            if(mimeTypeUsageType == null || mimeTypeUsage != null) {
                String entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

                if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                    ByteArray blob = edit.getBlob();

                    if(blob == null) {
                        addExecutionError(ExecutionErrors.MissingBlob.name());
                    }
                } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                    String clob = edit.getClob();

                    if(clob == null) {
                        addExecutionError(ExecutionErrors.MissingClob.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownMimeTypeUsage.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
        }
    }

    @Override
    public void doUpdate(PartyDocument partyDocument) {
        var documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
        PartyDocumentValue partyDocumentValue = documentControl.getPartyDocumentValueForUpdate(partyDocument);
        Document document = partyDocument.getDocument();
        DocumentDetailValue documentDetailValue = documentControl.getDocumentDetailValueForUpdate(document);
        ByteArray blob = edit.getBlob();
        String clob = edit.getClob();
        Integer pages = DocumentLogic.getInstance().getPages(mimeType, blob, clob);
        var partyPK = getPartyPK();

        partyDocumentValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        partyDocumentValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        documentControl.updatePartyDocumentFromValue(partyDocumentValue, partyPK);

        documentDetailValue.setMimeTypePK(mimeType.getPrimaryKey());
        documentDetailValue.setPages(pages);

        documentControl.updateDocumentFromValue(documentDetailValue, partyPK);
        
        doLobUpdate(document, blob, clob);
        doDescriptionUpdate(document);
    }

    private void doLobUpdate(Document document, ByteArray blob, String clob) {
        var documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
        DocumentBlob documentBlob = null;
        DocumentClob documentClob = null;
        String oldEntityAttributeTypeName = document.getLastDetail().getMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        String entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        var partyPK = getPartyPK();

        if(oldEntityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            documentBlob = documentControl.getDocumentBlobForUpdate(document);
        } else if(oldEntityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            documentClob = documentControl.getDocumentClobForUpdate(document);
        }
        
        if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            if(documentClob != null) {
                documentControl.deleteDocumentClob(documentClob, partyPK);
                documentControl.createDocumentBlob(document, blob, partyPK);
            } else {
                DocumentBlobValue documentBlobValue = documentControl.getDocumentBlobValue(documentBlob);
                documentBlobValue.setBlob(blob);
                documentControl.updateDocumentBlobFromValue(documentBlobValue, partyPK);
            }
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            if(documentBlob != null) {
                documentControl.deleteDocumentBlob(documentBlob, partyPK);
                documentControl.createDocumentClob(document, clob, partyPK);
            } else {
                DocumentClobValue documentClobValue = documentControl.getDocumentClobValue(documentClob);
                documentClobValue.setClob(clob);
                documentControl.updateDocumentClobFromValue(documentClobValue, partyPK);
            }
        }
    }

    private void doDescriptionUpdate(Document document) {
        var documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
        DocumentDescription documentDescription = documentControl.getDocumentDescriptionForUpdate(document, getPreferredLanguage());
        String description = edit.getDescription();
        var partyPK = getPartyPK();

        if(documentDescription == null && description != null) {
            documentControl.createDocumentDescription(document, getPreferredLanguage(), description, partyPK);
        } else {
            if(documentDescription != null && description == null) {
                documentControl.deleteDocumentDescription(documentDescription, partyPK);
            } else {
                if(documentDescription != null && description != null) {
                    DocumentDescriptionValue documentDescriptionValue = documentControl.getDocumentDescriptionValue(documentDescription);
                    documentDescriptionValue.setDescription(description);
                    documentControl.updateDocumentDescriptionFromValue(documentDescriptionValue, partyPK);
                }
            }
        }
    }

}
