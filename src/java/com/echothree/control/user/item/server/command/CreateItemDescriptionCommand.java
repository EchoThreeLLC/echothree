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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.CreateItemDescriptionForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic.ImageDimensions;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemImageDescriptionType;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateItemDescriptionCommand
        extends BaseSimpleCommand<CreateItemDescriptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescription.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("ItemImageTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ClobDescription", FieldType.STRING, false, 1L, null),
                new FieldDefinition("StringDescription", FieldType.STRING, false, 1L, 512L)
                ));
    }
    
    /** Creates a new instance of CreateItemDescriptionCommand */
    public CreateItemDescriptionCommand(UserVisitPK userVisitPK, CreateItemDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    protected void createItemDescription(ItemControl itemControl, ItemDescriptionType itemDescriptionType, Item item, Language language, MimeType mimeType,
            BasePK createdBy, ByteArray blobDescription, String clobDescription, String stringDescription, MimeTypeUsageType mimeTypeUsageType) {
        ItemImageType itemImageType = null;
        ImageDimensions imageDimensions = null;

        if(mimeTypeUsageType != null) {
            String mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                String itemImageTypeName = form.getItemImageTypeName();

                if(itemImageTypeName != null) {
                    itemImageType = itemControl.getItemImageTypeByName(itemImageTypeName);

                    if(itemImageType != null) {
                        // Only works for images coming in as BLOBs.
                        if(blobDescription != null) {
                            imageDimensions = ItemDescriptionLogic.getInstance().getImageDimensions(mimeType.getLastDetail().getMimeTypeName(), blobDescription);
                        }

                        if(imageDimensions == null) {
                            addExecutionError(ExecutionErrors.InvalidImage.name());
                        } else {
                            ItemImageDescriptionType itemImageDescriptionType = itemControl.getItemImageDescriptionType(itemDescriptionType);

                            if(itemImageDescriptionType != null) {
                                Integer minimumHeight = itemImageDescriptionType.getMinimumHeight();
                                Integer minimumWidth = itemImageDescriptionType.getMinimumWidth();
                                Integer maximumHeight = itemImageDescriptionType.getMaximumHeight();
                                Integer maximumWidth = itemImageDescriptionType.getMaximumWidth();
                                Integer imageHeight = imageDimensions.getHeight();
                                Integer imageWidth = imageDimensions.getWidth();

                                if(minimumHeight != null && imageHeight < minimumHeight) {
                                    addExecutionError(ExecutionErrors.ImageHeightLessThanMinimum.name(), imageHeight.toString(), minimumHeight.toString());
                                }

                                if(minimumWidth != null && imageWidth < minimumWidth) {
                                    addExecutionError(ExecutionErrors.ImageWidthLessThanMinimum.name(), imageWidth.toString(), minimumWidth.toString());
                                }

                                if(maximumHeight != null && imageHeight > maximumHeight) {
                                    addExecutionError(ExecutionErrors.ImageHeightGreaterThanMaximum.name(), imageHeight.toString(), maximumHeight.toString());
                                }

                                if(maximumWidth != null && imageWidth > maximumWidth) {
                                    addExecutionError(ExecutionErrors.ImageWidthGreaterThanMaximum.name(), imageWidth.toString(), maximumWidth.toString());
                                }
                            }
                        }
                    } else {
                      addExecutionError(ExecutionErrors.UnknownItemImageTypeName.name(), itemImageTypeName);
                    }
                } else {
                      addExecutionError(ExecutionErrors.MissingRequiredItemImageTypeName.name());
                }
            }
        }

        if(!hasExecutionErrors()) {
            ItemDescription itemDescription = itemControl.createItemDescription(itemDescriptionType, item, language, mimeType,
                    createdBy);

            if(blobDescription != null) {
                itemControl.createItemBlobDescription(itemDescription, blobDescription, createdBy);
            } else if(clobDescription != null) {
                itemControl.createItemClobDescription(itemDescription, clobDescription, createdBy);
            } else if(stringDescription != null) {
                itemControl.createItemStringDescription(itemDescription, stringDescription, createdBy);
            }

            if(imageDimensions != null) {
                itemControl.createItemImageDescription(itemDescription, itemImageType, imageDimensions.getHeight(), imageDimensions.getWidth(), Boolean.FALSE,
                        createdBy);

                ItemDescriptionLogic.getInstance().deleteItemImageDescriptionChildren(itemDescription, createdBy);
            }
        }
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            String itemDescriptionTypeName = form.getItemDescriptionTypeName();
            ItemDescriptionType itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);
            
            if(itemDescriptionType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                String languageIsoName = form.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    BasePK createdBy = getPartyPK();
                    ItemDescription itemDescription = itemControl.getItemDescription(itemDescriptionType, item, language);
                    
                    if(itemDescription == null) {
                        String mimeTypeName = form.getMimeTypeName();
                        
                        if(mimeTypeName == null) {
                            if(itemDescriptionType.getLastDetail().getMimeTypeUsageType() == null) {
                                String stringDescription = form.getStringDescription();
                                
                                if(stringDescription != null) {
                                    createItemDescription(itemControl, itemDescriptionType, item, language, null, createdBy, null,
                                            null, stringDescription, null);
                                } else {
                                    addExecutionError(ExecutionErrors.MissingStringDescription.name());
                                }
                            } else {
                                // No mimeTypeName was supplied, but yet we required a MimeTypeUsageType
                                addExecutionError(ExecutionErrors.InvalidMimeType.name());
                            }
                        } else {
                            var coreControl = getCoreControl();
                            MimeType mimeType = coreControl.getMimeTypeByName(mimeTypeName);
                            
                            if(mimeType != null) {
                                MimeTypeUsageType mimeTypeUsageType = itemDescriptionType.getLastDetail().getMimeTypeUsageType();
                                
                                if(mimeTypeUsageType != null) {
                                    MimeTypeUsage mimeTypeUsage = coreControl.getMimeTypeUsage(mimeType, mimeTypeUsageType);
                                    
                                    if(mimeTypeUsage != null) {
                                        EntityAttributeType entityAttributeType = mimeType.getLastDetail().getEntityAttributeType();
                                        String entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();
                                        
                                        if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                                            ByteArray blobDescription = form.getBlobDescription();
                                            
                                            if(blobDescription != null) {
                                                createItemDescription(itemControl, itemDescriptionType, item, language, mimeType,
                                                        createdBy, blobDescription, null, null, mimeTypeUsageType);
                                            } else {
                                                addExecutionError(ExecutionErrors.MissingBlobDescription.name());
                                            }
                                        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                                            String clobDescription = form.getClobDescription();
                                            
                                            if(clobDescription != null) {
                                                createItemDescription(itemControl, itemDescriptionType, item, language, mimeType,
                                                        createdBy, null, clobDescription, null, mimeTypeUsageType);
                                            } else {
                                                addExecutionError(ExecutionErrors.MissingClobDescription.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
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
                        addExecutionError(ExecutionErrors.DuplicateItemDescription.name(), itemName, itemDescriptionTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return null;
    }
    
}
