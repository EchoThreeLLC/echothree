// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class EntityAttributeTransferCache
        extends BaseCoreTransferCache<EntityAttribute, EntityAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    
    boolean includeValue;
    boolean includeEntityListItems;
    boolean includeEntityListItemsCount;
    boolean includeEntityAttributeEntityTypes;
    boolean includeEntityAttributeEntityTypesCount;

    TransferProperties transferProperties;
    boolean filterEntityType;
    boolean filterEntityAttributeName;
    boolean filterEntityAttributeType;
    boolean filterTrackRevisions;
    boolean filterCheckContentWebAddress;
    boolean filterValidationPattern;
    boolean filterEntityListItemSequence;
    boolean filterUnitOfMeasureType;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;

    /** Creates a new instance of EntityAttributeTransferCache */
    public EntityAttributeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        var options = session.getOptions();
        if(options != null) {
            includeValue = options.contains(CoreOptions.EntityAttributeIncludeValue);
            includeEntityListItems = options.contains(CoreOptions.EntityAttributeIncludeEntityListItems);
            includeEntityListItemsCount = options.contains(CoreOptions.EntityAttributeIncludeEntityListItemsCount);
            includeEntityAttributeEntityTypes = options.contains(CoreOptions.EntityAttributeIncludeEntityAttributeEntityTypes);
            includeEntityAttributeEntityTypesCount = options.contains(CoreOptions.EntityAttributeIncludeEntityAttributeEntityTypesCount);
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(EntityAttributeTransfer.class);
            
            if(properties != null) {
                filterEntityType = !properties.contains(CoreProperties.ENTITY_TYPE);
                filterEntityAttributeName = !properties.contains(CoreProperties.ENTITY_ATTRIBUTE_NAME);
                filterEntityAttributeType = !properties.contains(CoreProperties.ENTITY_ATTRIBUTE_TYPE);
                filterTrackRevisions = !properties.contains(CoreProperties.TRACK_REVISIONS);
                filterCheckContentWebAddress = !properties.contains(CoreProperties.CHECK_CONTENT_WEB_ADDRESS);
                filterValidationPattern = !properties.contains(CoreProperties.VALIDATION_PATTERN);
                filterEntityListItemSequence = !properties.contains(CoreProperties.ENTITY_LIST_ITEM_SEQUENCE);
                filterUnitOfMeasureType = !properties.contains(CoreProperties.UNIT_OF_MEASURE_TYPE);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public EntityAttributeTransfer getEntityAttributeTransfer(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var entityAttributeTransfer = get(entityAttribute);
        
        if(entityAttributeTransfer == null) {
            var entityAttributeDetail = entityAttribute.getLastDetail();
            var entityTypeTransfer = filterEntityType ? null : coreControl.getEntityTypeTransfer(userVisit, entityAttributeDetail.getEntityType());
            var entityAttributeName = filterEntityAttributeName ? null : entityAttributeDetail.getEntityAttributeName();
            var entityAttributeType = entityAttributeDetail.getEntityAttributeType();
            var entityAttributeTypeTransfer = filterEntityAttributeType ? null : coreControl.getEntityAttributeTypeTransfer(userVisit, entityAttributeType);
            var trackRevisions = filterTrackRevisions ? null : entityAttributeDetail.getTrackRevisions();
            var sortOrder = filterSortOrder ? null : entityAttributeDetail.getSortOrder();
            var description = filterDescription ? null : coreControl.getBestEntityAttributeDescription(entityAttribute, getLanguage());
            
            entityAttributeTransfer = new EntityAttributeTransfer(entityTypeTransfer, entityAttributeTypeTransfer, entityAttributeName,
                    trackRevisions, sortOrder, description);

            var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();
            switch(EntityAttributeTypes.valueOf(entityAttributeTypeName)) {
                case BLOB:
                    if(!filterCheckContentWebAddress) {
                        var entityAttributeBlob = coreControl.getEntityAttributeBlob(entityAttribute);
                        
                        entityAttributeTransfer.setCheckContentWebAddress(entityAttributeBlob.getCheckContentWebAddress());
                    }
                    break;
                case STRING:
                    if(!filterValidationPattern) {
                        var entityAttributeString = coreControl.getEntityAttributeString(entityAttribute);
                        
                        if(entityAttributeString != null) {
                            entityAttributeTransfer.setValidationPattern(filterValidationPattern ? null : entityAttributeString.getValidationPattern());
                        }
                    }
                    break;
                case INTEGER: {
                    // TODO
                    
                    if(!filterUnitOfMeasureType) {
                        var entityAttributeNumeric = coreControl.getEntityAttributeNumeric(entityAttribute);
                        
                        if(entityAttributeNumeric != null) {
                            if(!filterUnitOfMeasureType) {
                                var unitOfMeasureType = entityAttributeNumeric.getUnitOfMeasureType();
                                
                                entityAttributeTransfer.setUnitOfMeasureType(unitOfMeasureType == null ? null : uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType));
                            }
                        }
                    }
                }
                break;
                case LONG: {
                    // TODO
                    
                    if(!filterUnitOfMeasureType) {
                        var entityAttributeNumeric = coreControl.getEntityAttributeNumeric(entityAttribute);
                        
                        if(entityAttributeNumeric != null) {
                            if(!filterUnitOfMeasureType) {
                                var unitOfMeasureType = entityAttributeNumeric.getUnitOfMeasureType();
                                
                                entityAttributeTransfer.setUnitOfMeasureType(unitOfMeasureType == null ? null : uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType));
                            }
                        }
                    }
                }
                break;
                case LISTITEM:
                case MULTIPLELISTITEM:
                    if(!filterEntityListItemSequence) {
                        var entityAttributeListItem = coreControl.getEntityAttributeListItem(entityAttribute);
                        
                        if(entityAttributeListItem != null) {
                            if(!filterEntityListItemSequence) {
                                var entityListItemSequence = entityAttributeListItem.getEntityListItemSequence();
                                
                                entityAttributeTransfer.setEntityListItemSequence(entityListItemSequence == null ? null : sequenceControl.getSequenceTransfer(userVisit, entityListItemSequence));
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            
            if(entityInstance == null) {
                put(entityAttribute, entityAttributeTransfer);
            } else {
                setupEntityInstance(entityAttribute, null, entityAttributeTransfer);
            }
            
            if(includeValue) {
                if(entityInstance != null) {
                    if(entityAttributeTypeName.equals(EntityAttributeTypes.BOOLEAN.name())) {
                        var entityBooleanAttribute = coreControl.getEntityBooleanAttribute(entityAttribute, entityInstance);
                        
                        entityAttributeTransfer.setEntityBooleanAttribute(entityBooleanAttribute == null ? null : coreControl.getEntityBooleanAttributeTransfer(userVisit, entityBooleanAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.NAME.name())) {
                        var entityNameAttribute = coreControl.getEntityNameAttribute(entityAttribute, entityInstance);
                        
                        entityAttributeTransfer.setEntityNameAttribute(entityNameAttribute == null ? null : coreControl.getEntityNameAttributeTransfer(userVisit, entityNameAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
                        var entityIntegerAttribute = coreControl.getEntityIntegerAttribute(entityAttribute, entityInstance);
                        
                        entityAttributeTransfer.setEntityIntegerAttribute(entityIntegerAttribute == null ? null : coreControl.getEntityIntegerAttributeTransfer(userVisit, entityIntegerAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                        var entityLongAttribute = coreControl.getEntityLongAttribute(entityAttribute, entityInstance);
                        
                        entityAttributeTransfer.setEntityLongAttribute(entityLongAttribute == null ? null : coreControl.getEntityLongAttributeTransfer(userVisit, entityLongAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.STRING.name())) {
                        var entityStringAttribute = coreControl.getBestEntityStringAttribute(entityAttribute, entityInstance, getLanguage());
                        
                        entityAttributeTransfer.setEntityStringAttribute(entityStringAttribute == null ? null : coreControl.getEntityStringAttributeTransfer(userVisit, entityStringAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
                        var entityGeoPointAttribute = coreControl.getEntityGeoPointAttribute(entityAttribute, entityInstance);
                        
                        entityAttributeTransfer.setEntityGeoPointAttribute(entityGeoPointAttribute == null ? null : coreControl.getEntityGeoPointAttributeTransfer(userVisit, entityGeoPointAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                        var entityBlobAttribute = coreControl.getBestEntityBlobAttribute(entityAttribute, entityInstance, getLanguage());
                        
                        entityAttributeTransfer.setEntityBlobAttribute(entityBlobAttribute == null ? null : coreControl.getEntityBlobAttributeTransfer(userVisit, entityBlobAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                        var entityClobAttribute = coreControl.getBestEntityClobAttribute(entityAttribute, entityInstance, getLanguage());
                        
                        entityAttributeTransfer.setEntityClobAttribute(entityClobAttribute == null ? null : coreControl.getEntityClobAttributeTransfer(userVisit, entityClobAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.ENTITY.name())) {
                        var entityEntityAttribute = coreControl.getEntityEntityAttribute(entityAttribute, entityInstance);
                        
                        entityAttributeTransfer.setEntityEntityAttribute(entityEntityAttribute == null ? null : coreControl.getEntityEntityAttributeTransfer(userVisit, entityEntityAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.COLLECTION.name())) {
                        entityAttributeTransfer.setEntityCollectionAttributes(new ListWrapper<>(coreControl.getEntityCollectionAttributeTransfers(userVisit, entityAttribute, entityInstance)));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())) {
                        var entityDateAttribute = coreControl.getEntityDateAttribute(entityAttribute, entityInstance);
                        
                        entityAttributeTransfer.setEntityDateAttribute(entityDateAttribute == null ? null : coreControl.getEntityDateAttributeTransfer(userVisit, entityDateAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())) {
                        var entityTimeAttribute = coreControl.getEntityTimeAttribute(entityAttribute, entityInstance);
                        
                        entityAttributeTransfer.setEntityTimeAttribute(entityTimeAttribute == null ? null : coreControl.getEntityTimeAttributeTransfer(userVisit, entityTimeAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                        var entityListItemAttribute = coreControl.getEntityListItemAttribute(entityAttribute, entityInstance);
                        
                        entityAttributeTransfer.setEntityListItemAttribute(entityListItemAttribute == null ? null : coreControl.getEntityListItemAttributeTransfer(userVisit, entityListItemAttribute, entityInstance));
                    } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                        entityAttributeTransfer.setEntityMultipleListItemAttributes(new ListWrapper<>(coreControl.getEntityMultipleListItemAttributeTransfers(userVisit, entityAttribute, entityInstance)));
                    }
                } else {
                    getLog().error("entityInstance is null");
                }
            }

            if(includeEntityListItemsCount) {
                entityAttributeTransfer.setEntityListItemsCount(coreControl.countEntityListItems(entityAttribute));
            }

            if(includeEntityListItems) {
                entityAttributeTransfer.setEntityListItems(new ListWrapper<>(coreControl.getEntityListItemTransfersByEntityAttribute(userVisit, entityAttribute, entityInstance)));
            }

            if(includeEntityAttributeEntityTypesCount) {
                entityAttributeTransfer.setEntityAttributeEntityTypesCount(coreControl.countEntityAttributeEntityTypesByEntityAttribute(entityAttribute));
            }

            if(includeEntityAttributeEntityTypes) {
                entityAttributeTransfer.setEntityAttributeEntityTypes(new ListWrapper<>(coreControl.getEntityAttributeEntityTypeTransfersByEntityAttribute(userVisit, entityAttribute, entityInstance)));
            }
        }
        return entityAttributeTransfer;
    }
    
}
