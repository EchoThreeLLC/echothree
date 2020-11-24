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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentCategoryItemsForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.GetContentCategoryItemsResult;
import com.echothree.model.control.associate.server.logic.AssociateReferralLogic;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.content.server.factory.ContentCategoryItemFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetContentCategoryItemsCommand
        extends BaseMultipleEntitiesCommand<ContentCategoryItem, GetContentCategoryItemsForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, false, null, null),
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContentCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AssociateProgramName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociateName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociatePartyContactMechanismName", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentCategoryItemsCommand */
    public GetContentCategoryItemsCommand(UserVisitPK userVisitPK, GetContentCategoryItemsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    private ContentCategory contentCategory;
    
    @Override
    protected Collection<ContentCategoryItem> getEntities() {
        String contentWebAddressName = form.getContentWebAddressName();
        String contentCollectionName = form.getContentCollectionName();
        int parameterCount = (contentWebAddressName == null ? 0 : 1) + (contentCollectionName == null ? 0 : 1);
        Collection<ContentCategoryItem> contentCategoryItems = null;

        if(parameterCount == 1) {
            var contentControl = Session.getModelController(ContentControl.class);
            ContentCollection contentCollection = null;

            if(contentWebAddressName != null) {
                ContentWebAddress contentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);

                if(contentWebAddress != null) {
                    contentCollection = contentWebAddress.getLastDetail().getContentCollection();
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentWebAddressName.name(), contentWebAddressName);
                }
            } else {
                contentCollection = contentControl.getContentCollectionByName(contentCollectionName);

                if(contentCollection == null) {
                    addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
                }
            }

            if(!hasExecutionErrors()) {
                String contentCatalogName = form.getContentCatalogName();
                var partyPK = getPartyPK();
                UserVisit userVisit = getUserVisitForUpdate();

                ContentCatalog contentCatalog = contentCatalogName == null ? contentControl.getDefaultContentCatalog(contentCollection)
                        : contentControl.getContentCatalogByName(contentCollection, contentCatalogName);

                if(contentCatalog != null) {
                    String contentCategoryName = form.getContentCategoryName();
                    
                    contentCategory = contentCategoryName == null ? contentControl.getDefaultContentCategory(contentCatalog)
                            : contentControl.getContentCategoryByName(contentCatalog, contentCategoryName);

                    if(contentCategory != null) {
                        AssociateReferralLogic.getInstance().handleAssociateReferral(session, this, form, userVisit, contentCategory.getPrimaryKey(), partyPK);

                        if(!hasExecutionErrors()) {
                            contentCategoryItems = contentControl.getContentCategoryItemsByContentCategory(contentCategory);
                        }
                    } else {
                        if(contentCategoryName == null) {
                            addExecutionError(ExecutionErrors.UnknownDefaultContentCategory.name(),
                                    contentCollection.getLastDetail().getContentCollectionName(), contentCatalogName);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownContentCategoryName.name(),
                                    contentCollection.getLastDetail().getContentCollectionName(), contentCatalogName, contentCategoryName);
                        }
                    }
                } else {
                    if(contentCatalogName == null) {
                        addExecutionError(ExecutionErrors.UnknownDefaultContentCatalog.name(),
                                contentCollection.getLastDetail().getContentCollectionName());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(),
                                contentCollection.getLastDetail().getContentCollectionName(), contentCatalogName);
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return contentCategoryItems;
    }
    
    @Override
    protected BaseResult getTransfers(Collection<ContentCategoryItem> entities) {
        GetContentCategoryItemsResult result = ContentResultFactory.getGetContentCategoryItemsResult();

        if(entities != null) {
            var contentControl = Session.getModelController(ContentControl.class);
                UserVisit userVisit = getUserVisit();

            if(session.hasLimit(ContentCategoryItemFactory.class)) {
                result.setContentCategoryItemCount(contentControl.countContentCategoryItemsByContentCategory(contentCategory));
            }

            result.setContentCategory(contentControl.getContentCategoryTransfer(userVisit, contentCategory));
            result.setContentCategoryItems(contentControl.getContentCategoryItemTransfers(userVisit, entities));
        }

        return result;
    }
        
}
