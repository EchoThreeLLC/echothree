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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.AppearanceChoicesBean;
import com.echothree.model.control.core.common.choice.ApplicationChoicesBean;
import com.echothree.model.control.core.common.choice.ApplicationEditorChoicesBean;
import com.echothree.model.control.core.common.choice.ApplicationEditorUseChoicesBean;
import com.echothree.model.control.core.common.choice.BaseEncryptionKeyStatusChoicesBean;
import com.echothree.model.control.core.common.choice.ColorChoicesBean;
import com.echothree.model.control.core.common.choice.CommandMessageTypeChoicesBean;
import com.echothree.model.control.core.common.choice.EditorChoicesBean;
import com.echothree.model.control.core.common.choice.EntityAttributeGroupChoicesBean;
import com.echothree.model.control.core.common.choice.EntityAttributeTypeChoicesBean;
import com.echothree.model.control.core.common.choice.EntityIntegerRangeChoicesBean;
import com.echothree.model.control.core.common.choice.EntityListItemChoicesBean;
import com.echothree.model.control.core.common.choice.EntityLongRangeChoicesBean;
import com.echothree.model.control.core.common.choice.EventGroupStatusChoicesBean;
import com.echothree.model.control.core.common.choice.FontStyleChoicesBean;
import com.echothree.model.control.core.common.choice.FontWeightChoicesBean;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.core.common.choice.MimeTypeUsageTypeChoicesBean;
import com.echothree.model.control.core.common.choice.ProtocolChoicesBean;
import com.echothree.model.control.core.common.choice.ServerChoicesBean;
import com.echothree.model.control.core.common.choice.ServiceChoicesBean;
import com.echothree.model.control.core.common.choice.TextDecorationChoicesBean;
import com.echothree.model.control.core.common.choice.TextTransformationChoicesBean;
import com.echothree.model.control.core.common.transfer.AppearanceDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.AppearanceTextDecorationTransfer;
import com.echothree.model.control.core.common.transfer.AppearanceTextTransformationTransfer;
import com.echothree.model.control.core.common.transfer.AppearanceTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationEditorTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationEditorUseDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationEditorUseTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationTransfer;
import com.echothree.model.control.core.common.transfer.BaseEncryptionKeyTransfer;
import com.echothree.model.control.core.common.transfer.CacheEntryDependencyTransfer;
import com.echothree.model.control.core.common.transfer.CacheEntryTransfer;
import com.echothree.model.control.core.common.transfer.ColorDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ColorTransfer;
import com.echothree.model.control.core.common.transfer.CommandDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTranslationTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.CommandMessageTypeTransfer;
import com.echothree.model.control.core.common.transfer.CommandTransfer;
import com.echothree.model.control.core.common.transfer.ComponentVendorTransfer;
import com.echothree.model.control.core.common.transfer.EditorDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EditorTransfer;
import com.echothree.model.control.core.common.transfer.EntityAppearanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeEntityAttributeGroupTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeEntityTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBlobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBooleanAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityClobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityCollectionAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityDateAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityEntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityGeoPointAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerRangeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerRangeTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongRangeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongRangeTransfer;
import com.echothree.model.control.core.common.transfer.EntityMultipleListItemAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityNameAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityStringAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.common.transfer.EventGroupTransfer;
import com.echothree.model.control.core.common.transfer.EventTransfer;
import com.echothree.model.control.core.common.transfer.EventTypeTransfer;
import com.echothree.model.control.core.common.transfer.FontStyleDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.FontStyleTransfer;
import com.echothree.model.control.core.common.transfer.FontWeightDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.FontWeightTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeFileExtensionTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.model.control.core.common.transfer.PartyApplicationEditorUseTransfer;
import com.echothree.model.control.core.common.transfer.PartyEntityTypeTransfer;
import com.echothree.model.control.core.common.transfer.ProtocolDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ProtocolTransfer;
import com.echothree.model.control.core.common.transfer.ServerDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ServerServiceTransfer;
import com.echothree.model.control.core.common.transfer.ServerTransfer;
import com.echothree.model.control.core.common.transfer.ServiceDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ServiceTransfer;
import com.echothree.model.control.core.common.transfer.TextDecorationDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.TextDecorationTransfer;
import com.echothree.model.control.core.common.transfer.TextTransformationDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.TextTransformationTransfer;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.WorkflowDestination_BASE_ENCRYPTION_KEY_STATUS_ACTIVE_TO_INACTIVE;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.Workflow_BASE_ENCRYPTION_KEY_STATUS;
import static com.echothree.model.control.core.common.workflow.EventGroupStatusConstants.WorkflowStep_EVENT_GROUP_STATUS_ACTIVE;
import static com.echothree.model.control.core.common.workflow.EventGroupStatusConstants.Workflow_EVENT_GROUP_STATUS;
import com.echothree.model.control.core.server.CoreDebugFlags;
import com.echothree.model.control.core.server.transfer.AppearanceDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.AppearanceTextDecorationTransferCache;
import com.echothree.model.control.core.server.transfer.AppearanceTextTransformationTransferCache;
import com.echothree.model.control.core.server.transfer.AppearanceTransferCache;
import com.echothree.model.control.core.server.transfer.ApplicationDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ApplicationEditorTransferCache;
import com.echothree.model.control.core.server.transfer.ApplicationEditorUseDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ApplicationEditorUseTransferCache;
import com.echothree.model.control.core.server.transfer.ApplicationTransferCache;
import com.echothree.model.control.core.server.transfer.BaseEncryptionKeyTransferCache;
import com.echothree.model.control.core.server.transfer.CacheEntryDependencyTransferCache;
import com.echothree.model.control.core.server.transfer.CacheEntryTransferCache;
import com.echothree.model.control.core.server.transfer.ColorDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ColorTransferCache;
import com.echothree.model.control.core.server.transfer.CommandDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTranslationTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTypeTransferCache;
import com.echothree.model.control.core.server.transfer.CommandTransferCache;
import com.echothree.model.control.core.server.transfer.CoreTransferCaches;
import com.echothree.model.control.core.server.transfer.EditorDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EditorTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAppearanceTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeEntityAttributeGroupTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeEntityTypeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeGroupDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeGroupTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityCollectionAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityIntegerRangeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityIntegerRangeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityListItemDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityListItemTransferCache;
import com.echothree.model.control.core.server.transfer.EntityLongRangeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityLongRangeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityMultipleListItemAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityTypeTransferCache;
import com.echothree.model.control.core.server.transfer.EventGroupTransferCache;
import com.echothree.model.control.core.server.transfer.EventTransferCache;
import com.echothree.model.control.core.server.transfer.FontStyleDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.FontStyleTransferCache;
import com.echothree.model.control.core.server.transfer.FontWeightDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.FontWeightTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeFileExtensionTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeUsageTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeUsageTypeTransferCache;
import com.echothree.model.control.core.server.transfer.PartyApplicationEditorUseTransferCache;
import com.echothree.model.control.core.server.transfer.PartyEntityTypeTransferCache;
import com.echothree.model.control.core.server.transfer.ProtocolDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ProtocolTransferCache;
import com.echothree.model.control.core.server.transfer.ServerDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ServerServiceTransferCache;
import com.echothree.model.control.core.server.transfer.ServerTransferCache;
import com.echothree.model.control.core.server.transfer.ServiceDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ServiceTransferCache;
import com.echothree.model.control.core.server.transfer.TextDecorationDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.TextDecorationTransferCache;
import com.echothree.model.control.core.server.transfer.TextTransformationDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.TextTransformationTransferCache;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.queue.common.QueueConstants;
import com.echothree.model.control.queue.common.exception.UnknownQueueTypeNameException;
import com.echothree.model.control.queue.server.logic.QueuedEntityLogic;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.core.common.pk.AppearancePK;
import com.echothree.model.data.core.common.pk.ApplicationEditorPK;
import com.echothree.model.data.core.common.pk.ApplicationEditorUsePK;
import com.echothree.model.data.core.common.pk.ApplicationPK;
import com.echothree.model.data.core.common.pk.CacheEntryPK;
import com.echothree.model.data.core.common.pk.ColorPK;
import com.echothree.model.data.core.common.pk.CommandMessagePK;
import com.echothree.model.data.core.common.pk.CommandMessageTypePK;
import com.echothree.model.data.core.common.pk.CommandPK;
import com.echothree.model.data.core.common.pk.ComponentVendorPK;
import com.echothree.model.data.core.common.pk.EditorPK;
import com.echothree.model.data.core.common.pk.EntityAttributeGroupPK;
import com.echothree.model.data.core.common.pk.EntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityAttributeTypePK;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.EntityIntegerRangePK;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.common.pk.EntityLongRangePK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.common.pk.FontStylePK;
import com.echothree.model.data.core.common.pk.FontWeightPK;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.common.pk.PartyApplicationEditorUsePK;
import com.echothree.model.data.core.common.pk.ProtocolPK;
import com.echothree.model.data.core.common.pk.ServerPK;
import com.echothree.model.data.core.common.pk.ServicePK;
import com.echothree.model.data.core.common.pk.TextDecorationPK;
import com.echothree.model.data.core.common.pk.TextTransformationPK;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.AppearanceDescription;
import com.echothree.model.data.core.server.entity.AppearanceDetail;
import com.echothree.model.data.core.server.entity.AppearanceTextDecoration;
import com.echothree.model.data.core.server.entity.AppearanceTextTransformation;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationDescription;
import com.echothree.model.data.core.server.entity.ApplicationDetail;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorDetail;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.entity.ApplicationEditorUseDescription;
import com.echothree.model.data.core.server.entity.ApplicationEditorUseDetail;
import com.echothree.model.data.core.server.entity.BaseEncryptionKey;
import com.echothree.model.data.core.server.entity.CacheBlobEntry;
import com.echothree.model.data.core.server.entity.CacheClobEntry;
import com.echothree.model.data.core.server.entity.CacheEntry;
import com.echothree.model.data.core.server.entity.CacheEntryDependency;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.ColorDescription;
import com.echothree.model.data.core.server.entity.ColorDetail;
import com.echothree.model.data.core.server.entity.Command;
import com.echothree.model.data.core.server.entity.CommandDescription;
import com.echothree.model.data.core.server.entity.CommandDetail;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageDetail;
import com.echothree.model.data.core.server.entity.CommandMessageTranslation;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.core.server.entity.CommandMessageTypeDescription;
import com.echothree.model.data.core.server.entity.CommandMessageTypeDetail;
import com.echothree.model.data.core.server.entity.Component;
import com.echothree.model.data.core.server.entity.ComponentDetail;
import com.echothree.model.data.core.server.entity.ComponentStage;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.model.data.core.server.entity.ComponentVersion;
import com.echothree.model.data.core.server.entity.Editor;
import com.echothree.model.data.core.server.entity.EditorDescription;
import com.echothree.model.data.core.server.entity.EditorDetail;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeBlob;
import com.echothree.model.data.core.server.entity.EntityAttributeDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityType;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDetail;
import com.echothree.model.data.core.server.entity.EntityAttributeInteger;
import com.echothree.model.data.core.server.entity.EntityAttributeListItem;
import com.echothree.model.data.core.server.entity.EntityAttributeLong;
import com.echothree.model.data.core.server.entity.EntityAttributeNumeric;
import com.echothree.model.data.core.server.entity.EntityAttributeString;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityAttributeTypeDescription;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanAttribute;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityCollectionAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityEncryptionKey;
import com.echothree.model.data.core.server.entity.EntityEntityAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDescription;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDetail;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemDescription;
import com.echothree.model.data.core.server.entity.EntityListItemDetail;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import com.echothree.model.data.core.server.entity.EntityLongRangeDescription;
import com.echothree.model.data.core.server.entity.EntityLongRangeDetail;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDescription;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.entity.EntityVisit;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.core.server.entity.EventGroup;
import com.echothree.model.data.core.server.entity.EventGroupDetail;
import com.echothree.model.data.core.server.entity.EventSubscriber;
import com.echothree.model.data.core.server.entity.EventSubscriberDetail;
import com.echothree.model.data.core.server.entity.EventSubscriberEntityInstance;
import com.echothree.model.data.core.server.entity.EventSubscriberEntityType;
import com.echothree.model.data.core.server.entity.EventSubscriberEventType;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.model.data.core.server.entity.EventTypeDescription;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontStyleDescription;
import com.echothree.model.data.core.server.entity.FontStyleDetail;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.FontWeightDescription;
import com.echothree.model.data.core.server.entity.FontWeightDetail;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeDescription;
import com.echothree.model.data.core.server.entity.MimeTypeDetail;
import com.echothree.model.data.core.server.entity.MimeTypeFileExtension;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageTypeDescription;
import com.echothree.model.data.core.server.entity.PartyApplicationEditorUse;
import com.echothree.model.data.core.server.entity.PartyApplicationEditorUseDetail;
import com.echothree.model.data.core.server.entity.PartyEntityType;
import com.echothree.model.data.core.server.entity.Protocol;
import com.echothree.model.data.core.server.entity.ProtocolDescription;
import com.echothree.model.data.core.server.entity.ProtocolDetail;
import com.echothree.model.data.core.server.entity.QueuedEvent;
import com.echothree.model.data.core.server.entity.QueuedSubscriberEvent;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.core.server.entity.ServerDescription;
import com.echothree.model.data.core.server.entity.ServerDetail;
import com.echothree.model.data.core.server.entity.ServerService;
import com.echothree.model.data.core.server.entity.Service;
import com.echothree.model.data.core.server.entity.ServiceDescription;
import com.echothree.model.data.core.server.entity.ServiceDetail;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextDecorationDescription;
import com.echothree.model.data.core.server.entity.TextDecorationDetail;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.model.data.core.server.entity.TextTransformationDescription;
import com.echothree.model.data.core.server.entity.TextTransformationDetail;
import com.echothree.model.data.core.server.factory.AppearanceDescriptionFactory;
import com.echothree.model.data.core.server.factory.AppearanceDetailFactory;
import com.echothree.model.data.core.server.factory.AppearanceFactory;
import com.echothree.model.data.core.server.factory.AppearanceTextDecorationFactory;
import com.echothree.model.data.core.server.factory.AppearanceTextTransformationFactory;
import com.echothree.model.data.core.server.factory.ApplicationDescriptionFactory;
import com.echothree.model.data.core.server.factory.ApplicationDetailFactory;
import com.echothree.model.data.core.server.factory.ApplicationEditorDetailFactory;
import com.echothree.model.data.core.server.factory.ApplicationEditorFactory;
import com.echothree.model.data.core.server.factory.ApplicationEditorUseDescriptionFactory;
import com.echothree.model.data.core.server.factory.ApplicationEditorUseDetailFactory;
import com.echothree.model.data.core.server.factory.ApplicationEditorUseFactory;
import com.echothree.model.data.core.server.factory.ApplicationFactory;
import com.echothree.model.data.core.server.factory.BaseEncryptionKeyFactory;
import com.echothree.model.data.core.server.factory.CacheBlobEntryFactory;
import com.echothree.model.data.core.server.factory.CacheClobEntryFactory;
import com.echothree.model.data.core.server.factory.CacheEntryDependencyFactory;
import com.echothree.model.data.core.server.factory.CacheEntryFactory;
import com.echothree.model.data.core.server.factory.ColorDescriptionFactory;
import com.echothree.model.data.core.server.factory.ColorDetailFactory;
import com.echothree.model.data.core.server.factory.ColorFactory;
import com.echothree.model.data.core.server.factory.CommandDescriptionFactory;
import com.echothree.model.data.core.server.factory.CommandDetailFactory;
import com.echothree.model.data.core.server.factory.CommandFactory;
import com.echothree.model.data.core.server.factory.CommandMessageDetailFactory;
import com.echothree.model.data.core.server.factory.CommandMessageFactory;
import com.echothree.model.data.core.server.factory.CommandMessageTranslationFactory;
import com.echothree.model.data.core.server.factory.CommandMessageTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.CommandMessageTypeDetailFactory;
import com.echothree.model.data.core.server.factory.CommandMessageTypeFactory;
import com.echothree.model.data.core.server.factory.ComponentDetailFactory;
import com.echothree.model.data.core.server.factory.ComponentFactory;
import com.echothree.model.data.core.server.factory.ComponentStageFactory;
import com.echothree.model.data.core.server.factory.ComponentVendorDetailFactory;
import com.echothree.model.data.core.server.factory.ComponentVendorFactory;
import com.echothree.model.data.core.server.factory.ComponentVersionFactory;
import com.echothree.model.data.core.server.factory.EditorDescriptionFactory;
import com.echothree.model.data.core.server.factory.EditorDetailFactory;
import com.echothree.model.data.core.server.factory.EditorFactory;
import com.echothree.model.data.core.server.factory.EntityAppearanceFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeBlobFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeEntityAttributeGroupFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeEntityTypeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeIntegerFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeListItemFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeLongFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeNumericFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeStringFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeTypeFactory;
import com.echothree.model.data.core.server.factory.EntityBlobAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityBooleanAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityClobAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityCollectionAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityDateAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityEncryptionKeyFactory;
import com.echothree.model.data.core.server.factory.EntityEntityAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityGeoPointAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerRangeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerRangeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerRangeFactory;
import com.echothree.model.data.core.server.factory.EntityListItemAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityListItemDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityListItemDetailFactory;
import com.echothree.model.data.core.server.factory.EntityListItemFactory;
import com.echothree.model.data.core.server.factory.EntityLongAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityLongRangeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityLongRangeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityLongRangeFactory;
import com.echothree.model.data.core.server.factory.EntityMultipleListItemAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityNameAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityStringAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityTimeAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityTimeFactory;
import com.echothree.model.data.core.server.factory.EntityTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityTypeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityTypeFactory;
import com.echothree.model.data.core.server.factory.EntityVisitFactory;
import com.echothree.model.data.core.server.factory.EventFactory;
import com.echothree.model.data.core.server.factory.EventGroupDetailFactory;
import com.echothree.model.data.core.server.factory.EventGroupFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberDetailFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberEntityInstanceFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberEntityTypeFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberEventTypeFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberFactory;
import com.echothree.model.data.core.server.factory.EventTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EventTypeFactory;
import com.echothree.model.data.core.server.factory.FontStyleDescriptionFactory;
import com.echothree.model.data.core.server.factory.FontStyleDetailFactory;
import com.echothree.model.data.core.server.factory.FontStyleFactory;
import com.echothree.model.data.core.server.factory.FontWeightDescriptionFactory;
import com.echothree.model.data.core.server.factory.FontWeightDetailFactory;
import com.echothree.model.data.core.server.factory.FontWeightFactory;
import com.echothree.model.data.core.server.factory.MimeTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.MimeTypeDetailFactory;
import com.echothree.model.data.core.server.factory.MimeTypeFactory;
import com.echothree.model.data.core.server.factory.MimeTypeFileExtensionFactory;
import com.echothree.model.data.core.server.factory.MimeTypeUsageFactory;
import com.echothree.model.data.core.server.factory.MimeTypeUsageTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.MimeTypeUsageTypeFactory;
import com.echothree.model.data.core.server.factory.PartyApplicationEditorUseDetailFactory;
import com.echothree.model.data.core.server.factory.PartyApplicationEditorUseFactory;
import com.echothree.model.data.core.server.factory.PartyEntityTypeFactory;
import com.echothree.model.data.core.server.factory.ProtocolDescriptionFactory;
import com.echothree.model.data.core.server.factory.ProtocolDetailFactory;
import com.echothree.model.data.core.server.factory.ProtocolFactory;
import com.echothree.model.data.core.server.factory.QueuedEventFactory;
import com.echothree.model.data.core.server.factory.QueuedSubscriberEventFactory;
import com.echothree.model.data.core.server.factory.ServerDescriptionFactory;
import com.echothree.model.data.core.server.factory.ServerDetailFactory;
import com.echothree.model.data.core.server.factory.ServerFactory;
import com.echothree.model.data.core.server.factory.ServerServiceFactory;
import com.echothree.model.data.core.server.factory.ServiceDescriptionFactory;
import com.echothree.model.data.core.server.factory.ServiceDetailFactory;
import com.echothree.model.data.core.server.factory.ServiceFactory;
import com.echothree.model.data.core.server.factory.TextDecorationDescriptionFactory;
import com.echothree.model.data.core.server.factory.TextDecorationDetailFactory;
import com.echothree.model.data.core.server.factory.TextDecorationFactory;
import com.echothree.model.data.core.server.factory.TextTransformationDescriptionFactory;
import com.echothree.model.data.core.server.factory.TextTransformationDetailFactory;
import com.echothree.model.data.core.server.factory.TextTransformationFactory;
import com.echothree.model.data.core.server.value.AppearanceDescriptionValue;
import com.echothree.model.data.core.server.value.AppearanceDetailValue;
import com.echothree.model.data.core.server.value.AppearanceTextDecorationValue;
import com.echothree.model.data.core.server.value.AppearanceTextTransformationValue;
import com.echothree.model.data.core.server.value.ApplicationDescriptionValue;
import com.echothree.model.data.core.server.value.ApplicationDetailValue;
import com.echothree.model.data.core.server.value.ApplicationEditorDetailValue;
import com.echothree.model.data.core.server.value.ApplicationEditorUseDescriptionValue;
import com.echothree.model.data.core.server.value.ApplicationEditorUseDetailValue;
import com.echothree.model.data.core.server.value.CacheEntryDependencyValue;
import com.echothree.model.data.core.server.value.ColorDescriptionValue;
import com.echothree.model.data.core.server.value.ColorDetailValue;
import com.echothree.model.data.core.server.value.CommandDescriptionValue;
import com.echothree.model.data.core.server.value.CommandDetailValue;
import com.echothree.model.data.core.server.value.CommandMessageDetailValue;
import com.echothree.model.data.core.server.value.CommandMessageTranslationValue;
import com.echothree.model.data.core.server.value.CommandMessageTypeDescriptionValue;
import com.echothree.model.data.core.server.value.CommandMessageTypeDetailValue;
import com.echothree.model.data.core.server.value.ComponentVendorDetailValue;
import com.echothree.model.data.core.server.value.EditorDescriptionValue;
import com.echothree.model.data.core.server.value.EditorDetailValue;
import com.echothree.model.data.core.server.value.EntityAppearanceValue;
import com.echothree.model.data.core.server.value.EntityAttributeBlobValue;
import com.echothree.model.data.core.server.value.EntityAttributeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeEntityAttributeGroupValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeIntegerValue;
import com.echothree.model.data.core.server.value.EntityAttributeListItemValue;
import com.echothree.model.data.core.server.value.EntityAttributeLongValue;
import com.echothree.model.data.core.server.value.EntityAttributeNumericValue;
import com.echothree.model.data.core.server.value.EntityAttributeStringValue;
import com.echothree.model.data.core.server.value.EntityBlobAttributeValue;
import com.echothree.model.data.core.server.value.EntityBooleanAttributeValue;
import com.echothree.model.data.core.server.value.EntityClobAttributeValue;
import com.echothree.model.data.core.server.value.EntityDateAttributeValue;
import com.echothree.model.data.core.server.value.EntityEntityAttributeValue;
import com.echothree.model.data.core.server.value.EntityGeoPointAttributeValue;
import com.echothree.model.data.core.server.value.EntityIntegerAttributeValue;
import com.echothree.model.data.core.server.value.EntityIntegerRangeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityIntegerRangeDetailValue;
import com.echothree.model.data.core.server.value.EntityListItemAttributeValue;
import com.echothree.model.data.core.server.value.EntityListItemDescriptionValue;
import com.echothree.model.data.core.server.value.EntityListItemDetailValue;
import com.echothree.model.data.core.server.value.EntityLongAttributeValue;
import com.echothree.model.data.core.server.value.EntityLongRangeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityLongRangeDetailValue;
import com.echothree.model.data.core.server.value.EntityNameAttributeValue;
import com.echothree.model.data.core.server.value.EntityStringAttributeValue;
import com.echothree.model.data.core.server.value.EntityTimeAttributeValue;
import com.echothree.model.data.core.server.value.EntityTypeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityTypeDetailValue;
import com.echothree.model.data.core.server.value.EventGroupDetailValue;
import com.echothree.model.data.core.server.value.FontStyleDescriptionValue;
import com.echothree.model.data.core.server.value.FontStyleDetailValue;
import com.echothree.model.data.core.server.value.FontWeightDescriptionValue;
import com.echothree.model.data.core.server.value.FontWeightDetailValue;
import com.echothree.model.data.core.server.value.MimeTypeDescriptionValue;
import com.echothree.model.data.core.server.value.MimeTypeDetailValue;
import com.echothree.model.data.core.server.value.PartyApplicationEditorUseDetailValue;
import com.echothree.model.data.core.server.value.PartyEntityTypeValue;
import com.echothree.model.data.core.server.value.ProtocolDescriptionValue;
import com.echothree.model.data.core.server.value.ProtocolDetailValue;
import com.echothree.model.data.core.server.value.ServerDescriptionValue;
import com.echothree.model.data.core.server.value.ServerDetailValue;
import com.echothree.model.data.core.server.value.ServerServiceValue;
import com.echothree.model.data.core.server.value.ServiceDescriptionValue;
import com.echothree.model.data.core.server.value.ServiceDetailValue;
import com.echothree.model.data.core.server.value.TextDecorationDescriptionValue;
import com.echothree.model.data.core.server.value.TextDecorationDetailValue;
import com.echothree.model.data.core.server.value.TextTransformationDescriptionValue;
import com.echothree.model.data.core.server.value.TextTransformationDetailValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureTypePK;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BaseKey;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import com.echothree.util.server.string.GuidUtils;
import com.echothree.util.server.string.KeyUtils;
import com.echothree.util.server.string.UlidUtils;
import com.google.common.base.Splitter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CoreControl
        extends BaseModelControl {
    
    /** Creates a new instance of CoreControl */
    public CoreControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Core Transfer Caches
    // --------------------------------------------------------------------------------
    
    private CoreTransferCaches coreTransferCaches;
    
    public CoreTransferCaches getCoreTransferCaches(UserVisit userVisit) {
        if(coreTransferCaches == null) {
            coreTransferCaches = new CoreTransferCaches(userVisit, this);
        }
        
        return coreTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Component Vendors
    // --------------------------------------------------------------------------------
    
    public ComponentVendor createComponentVendor(String componentVendorName, String description, BasePK createdBy) {
        ComponentVendor componentVendor = ComponentVendorFactory.getInstance().create();
        ComponentVendorDetail componentVendorDetail = ComponentVendorDetailFactory.getInstance().create(componentVendor,
                componentVendorName, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        componentVendor = ComponentVendorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                componentVendor.getPrimaryKey());
        componentVendor.setActiveDetail(componentVendorDetail);
        componentVendor.setLastDetail(componentVendorDetail);
        componentVendor.store();
        
        sendEventUsingNames(componentVendor.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return componentVendor;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.ComponentVendor */
    public ComponentVendor getComponentVendorByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ComponentVendorPK(entityInstance.getEntityUniqueId());
        var componentVendor = ComponentVendorFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return componentVendor;
    }

    public ComponentVendor getComponentVendorByEntityInstance(EntityInstance entityInstance) {
        return getComponentVendorByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ComponentVendor getComponentVendorByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getComponentVendorByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ComponentVendor getComponentVendorByName(String componentVendorName, EntityPermission entityPermission) {
        ComponentVendor componentVendor = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails " +
                        "WHERE cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails " +
                        "WHERE cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ComponentVendorFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, componentVendorName);
            
            componentVendor = ComponentVendorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return componentVendor;
    }
    
    public ComponentVendor getComponentVendorByName(String componentVendorName) {
        return getComponentVendorByName(componentVendorName, EntityPermission.READ_ONLY);
    }
    
    public ComponentVendor getComponentVendorByNameForUpdate(String componentVendorName) {
        return getComponentVendorByName(componentVendorName, EntityPermission.READ_WRITE);
    }

    public ComponentVendorDetailValue getComponentVendorDetailValueForUpdate(ComponentVendor componentVendor) {
        return componentVendor == null? null: componentVendor.getLastDetailForUpdate().getComponentVendorDetailValue().clone();
    }
    
    public ComponentVendorDetailValue getComponentVendorDetailValueByNameForUpdate(String componentVendorName) {
        return getComponentVendorDetailValueForUpdate(getComponentVendorByNameForUpdate(componentVendorName));
    }
    
    private Map<String, ComponentVendor> componentVendorCache = new HashMap<>();
    
    public ComponentVendor getComponentVendorByNameFromCache(String componentVendorName) {
        ComponentVendor componentVendor = componentVendorCache.get(componentVendorName);
        
        if(componentVendor == null) {
            componentVendor = getComponentVendorByName(componentVendorName);
            
            if(componentVendor != null) {
                componentVendorCache.put(componentVendorName, componentVendor);
            }
        }
        
        return componentVendor;
    }
    
    public List<ComponentVendor> getComponentVendors() {
        PreparedStatement ps = ComponentVendorFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM componentvendors, componentvendordetails " +
                "WHERE cvnd_activedetailid = cvndd_componentvendordetailid " +
                "ORDER BY cvndd_componentvendorname");
        
        return ComponentVendorFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ComponentVendorTransfer getComponentVendorTransfer(UserVisit userVisit, ComponentVendor componentVendor) {
        return getCoreTransferCaches(userVisit).getComponentVendorTransferCache().getComponentVendorTransfer(componentVendor);
    }

    public List<ComponentVendorTransfer> getComponentVendorTransfers(UserVisit userVisit, Collection<ComponentVendor> componentVendors) {
        var componentVendorTransfers = new ArrayList<ComponentVendorTransfer>(componentVendors.size());
        var componentVendorTransferCache = getCoreTransferCaches(userVisit).getComponentVendorTransferCache();

        componentVendors.stream().forEach((componentVendor) -> {
            componentVendorTransfers.add(componentVendorTransferCache.getComponentVendorTransfer(componentVendor));
        });

        return componentVendorTransfers;
    }

    public List<ComponentVendorTransfer> getComponentVendorTransfers(UserVisit userVisit) {
        return getComponentVendorTransfers(userVisit, getComponentVendors());
    }

    public void updateComponentVendorFromValue(ComponentVendorDetailValue componentVendorDetailValue, BasePK updatedBy) {
        if(componentVendorDetailValue.hasBeenModified()) {
            ComponentVendor componentVendor = ComponentVendorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     componentVendorDetailValue.getComponentVendorPK());
            ComponentVendorDetail componentVendorDetail = componentVendor.getActiveDetailForUpdate();
            
            componentVendorDetail.setThruTime(session.START_TIME_LONG);
            componentVendorDetail.store();
            
            ComponentVendorPK componentVendorPK = componentVendorDetail.getComponentVendorPK();
            String componentVendorName = componentVendorDetailValue.getComponentVendorName();
            String description = componentVendorDetailValue.getDescription();
            
            componentVendorDetail = ComponentVendorDetailFactory.getInstance().create(componentVendorPK,
                    componentVendorName, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            componentVendor.setActiveDetail(componentVendorDetail);
            componentVendor.setLastDetail(componentVendorDetail);
            
            sendEventUsingNames(componentVendorPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteComponentVendor(ComponentVendor componentVendor, BasePK deletedBy) {
        deleteEntityTypesByComponentVendor(componentVendor, deletedBy);
        
        ComponentVendorDetail componentVendorDetail = componentVendor.getLastDetailForUpdate();
        componentVendorDetail.setThruTime(session.START_TIME_LONG);
        componentVendor.setActiveDetail(null);
        componentVendor.store();
        
        sendEventUsingNames(componentVendor.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Types
    // --------------------------------------------------------------------------------
    
    public EntityType createEntityType(ComponentVendor componentVendor, String entityTypeName, Boolean keepAllHistory, Long lockTimeout, Integer sortOrder,
            BasePK createdBy) {
        EntityType entityType = EntityTypeFactory.getInstance().create();
        EntityTypeDetail entityTypeDetail = EntityTypeDetailFactory.getInstance().create(entityType, componentVendor, entityTypeName, keepAllHistory,
                lockTimeout, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityType = EntityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityType.getPrimaryKey());
        entityType.setActiveDetail(entityTypeDetail);
        entityType.setLastDetail(entityTypeDetail);
        entityType.store();
        
        sendEventUsingNames(entityType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return entityType;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.EntityType */
    public EntityType getEntityTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityTypePK(entityInstance.getEntityUniqueId());
        var entityType = EntityTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return entityType;
    }

    public EntityType getEntityTypeByEntityInstance(EntityInstance entityInstance) {
        return getEntityTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityType getEntityTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public long countEntityTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitytypes, entitytypedetails " +
                "WHERE ent_activedetailid = entdt_entitytypedetailid");
    }

    public long countEntityTypesByComponentVendor(ComponentVendor componentVendor) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitytypes, entitytypedetails " +
                "WHERE ent_activedetailid = entdt_entitytypedetailid AND entdt_cvnd_componentvendorid = ?",
                componentVendor);
    }

    public EntityType getEntityTypeByName(ComponentVendor componentVendor, String entityTypeName, EntityPermission entityPermission) {
        EntityType entityType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypes, entitytypedetails " +
                        "WHERE ent_activedetailid = entdt_entitytypedetailid " +
                        "AND entdt_cvnd_componentvendorid = ? AND entdt_entitytypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypes, entitytypedetails " +
                        "WHERE ent_activedetailid = entdt_entitytypedetailid " +
                        "AND entdt_cvnd_componentvendorid = ? AND entdt_entitytypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, componentVendor.getPrimaryKey().getEntityId());
            ps.setString(2, entityTypeName);
            
            entityType = EntityTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityType;
    }
    
    public EntityType getEntityTypeByName(ComponentVendor componentVendor, String entityTypeName) {
        return getEntityTypeByName(componentVendor, entityTypeName, EntityPermission.READ_ONLY);
    }
    
    public EntityType getEntityTypeByNameForUpdate(ComponentVendor componentVendor, String entityTypeName) {
        return getEntityTypeByName(componentVendor, entityTypeName, EntityPermission.READ_WRITE);
    }
    
    public EntityTypeDetailValue getEntityTypeDetailValueForUpdate(EntityType entityType) {
        return entityType == null? null: entityType.getLastDetailForUpdate().getEntityTypeDetailValue().clone();
    }
    
    public EntityTypeDetailValue getEntityTypeDetailValueByNameForUpdate(ComponentVendor componentVendor, String entityTypeName) {
        return getEntityTypeDetailValueForUpdate(getEntityTypeByNameForUpdate(componentVendor, entityTypeName));
    }
    
    private Map<ComponentVendor, Map<String, EntityType>> entityTypeCache = new HashMap<>();
    
    public EntityType getEntityTypeByNameFromCache(ComponentVendor componentVendor, String entityTypeName) {
        Map<String, EntityType> cacheByCompnentVendor = entityTypeCache.get(componentVendor);
        
        if(cacheByCompnentVendor == null) {
            cacheByCompnentVendor = new HashMap<>();
            entityTypeCache.put(componentVendor, cacheByCompnentVendor);
        }
        
        EntityType entityType = cacheByCompnentVendor.get(entityTypeName);
        
        if(entityType == null) {
            entityType = getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                cacheByCompnentVendor.put(entityTypeName, entityType);
            }
        }
        
        return entityType;
    }
    
    private List<EntityType> getEntityTypesByComponentVendor(ComponentVendor componentVendor, EntityPermission entityPermission) {
        List<EntityType> entityTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitytypes, entitytypedetails "
                        + "WHERE ent_activedetailid = entdt_entitytypedetailid "
                        + "AND entdt_cvnd_componentvendorid = ? "
                        + "ORDER BY entdt_sortorder, entdt_entitytypename "
                        + "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitytypes, entitytypedetails "
                        + "WHERE ent_activedetailid = entdt_entitytypedetailid "
                        + "AND entdt_cvnd_componentvendorid = ? "
                        + "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, componentVendor.getPrimaryKey().getEntityId());
            
            entityTypes = EntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTypes;
    }
    
    public List<EntityType> getEntityTypesByComponentVendor(ComponentVendor componentVendor) {
        return getEntityTypesByComponentVendor(componentVendor, EntityPermission.READ_ONLY);
    }
    
    public List<EntityType> getEntityTypesByComponentVendorForUpdate(ComponentVendor componentVendor) {
        return getEntityTypesByComponentVendor(componentVendor, EntityPermission.READ_WRITE);
    }
    
    public List<EntityType> getEntityTypes() {
        PreparedStatement ps = EntityTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ "
                + "FROM entitytypes, entitytypedetails "
                + "WHERE ent_activedetailid = entdt_entitytypedetailid "
                + "ORDER BY entdt_sortorder, entdt_entitytypename "
                + "_LIMIT_");

        return EntityTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public EntityTypeTransfer getEntityTypeTransfer(UserVisit userVisit, EntityType entityType) {
        return getCoreTransferCaches(userVisit).getEntityTypeTransferCache().getEntityTypeTransfer(entityType);
    }
    
    public List<EntityTypeTransfer> getEntityTypeTransfers(UserVisit userVisit, Collection<EntityType> entityTypes) {
        List<EntityTypeTransfer> entityTypeTransfers = new ArrayList<>(entityTypes.size());
        EntityTypeTransferCache entityTypeTransferCache = getCoreTransferCaches(userVisit).getEntityTypeTransferCache();
        
        entityTypes.stream().forEach((entityType) -> {
            entityTypeTransfers.add(entityTypeTransferCache.getEntityTypeTransfer(entityType));
        });
        
        return entityTypeTransfers;
    }
    
    public List<EntityTypeTransfer> getEntityTypeTransfers(UserVisit userVisit) {
        return getEntityTypeTransfers(userVisit, getEntityTypes());
    }
    
    public List<EntityTypeTransfer> getEntityTypeTransfersByComponentVendor(UserVisit userVisit, ComponentVendor componentVendor) {
        return getEntityTypeTransfers(userVisit, getEntityTypesByComponentVendor(componentVendor));
    }
    
    public void updateEntityTypeFromValue(EntityTypeDetailValue entityTypeDetailValue, BasePK updatedBy) {
        if(entityTypeDetailValue.hasBeenModified()) {
            EntityType entityType = EntityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityTypeDetailValue.getEntityTypePK());
            EntityTypeDetail entityTypeDetail = entityType.getActiveDetailForUpdate();
            
            entityTypeDetail.setThruTime(session.START_TIME_LONG);
            entityTypeDetail.store();
            
            EntityTypePK entityTypePK = entityTypeDetail.getEntityTypePK();
            ComponentVendorPK componentVendorPK = entityTypeDetail.getComponentVendorPK(); // Not updated
            String entityTypeName = entityTypeDetailValue.getEntityTypeName();
            Boolean keepAllHistory = entityTypeDetailValue.getKeepAllHistory();
            Long lockTimeout = entityTypeDetailValue.getLockTimeout();
            Integer sortOrder = entityTypeDetailValue.getSortOrder();
            
            entityTypeDetail = EntityTypeDetailFactory.getInstance().create(entityTypePK, componentVendorPK, entityTypeName, keepAllHistory, lockTimeout,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            entityType.setActiveDetail(entityTypeDetail);
            entityType.setLastDetail(entityTypeDetail);
            
            sendEventUsingNames(entityTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteEntityType(EntityType entityType, BasePK deletedBy) {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        var commentControl = (CommentControl)Session.getModelController(CommentControl.class);
        var indexControl = (IndexControl)Session.getModelController(IndexControl.class);
        var messageControl = (MessageControl)Session.getModelController(MessageControl.class);
        var ratingControl = (RatingControl)Session.getModelController(RatingControl.class);
        var tagControl = (TagControl)Session.getModelController(TagControl.class);
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        
        deleteEntityTypeDescriptionsByEntityType(entityType, deletedBy);
        deleteEntityAttributesByEntityType(entityType, deletedBy);
        deleteEntityAttributeEntityTypesByAllowedEntityType(entityType, deletedBy);
        accountingControl.deleteTransactionEntityRoleTypesByEntityType(entityType, deletedBy);
        batchControl.deleteBatchTypeEntityTypesByEntityType(entityType, deletedBy);
        commentControl.deleteCommentTypesByEntityType(entityType, deletedBy);
        indexControl.deleteIndexTypesByEntityType(entityType, deletedBy);
        messageControl.deleteMessageTypesByEntityType(entityType, deletedBy);
        ratingControl.deleteRatingTypesByEntityType(entityType, deletedBy);
        tagControl.deleteTagScopeEntityTypesByEntityType(entityType, deletedBy);
        workflowControl.deleteWorkflowEntityTypesByEntityType(entityType, deletedBy);
        
        EntityTypeDetail entityTypeDetail = entityType.getLastDetailForUpdate();
        entityTypeDetail.setThruTime(session.START_TIME_LONG);
        entityType.setActiveDetail(null);
        entityType.store();
        
        sendEventUsingNames(entityType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteEntityTypesByComponentVendor(ComponentVendor componentVendor, BasePK deletedBy) {
        List<EntityType> entityTypes = getEntityTypesByComponentVendorForUpdate(componentVendor);
        
        entityTypes.stream().forEach((entityType) -> {
            deleteEntityType(entityType, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Type Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityTypeDescription createEntityTypeDescription(EntityType entityType, Language language, String description,
            BasePK createdBy) {
        EntityTypeDescription entityTypeDescription = EntityTypeDescriptionFactory.getInstance().create(entityType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityType.getPrimaryKey(), EventTypes.MODIFY.name(), entityTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityTypeDescription;
    }
    
    private EntityTypeDescription getEntityTypeDescription(EntityType entityType, Language language,
            EntityPermission entityPermission) {
        EntityTypeDescription entityTypeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypedescriptions " +
                        "WHERE entd_ent_entitytypeid = ? AND entd_lang_languageid = ? AND entd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypedescriptions " +
                        "WHERE entd_ent_entitytypeid = ? AND entd_lang_languageid = ? AND entd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityTypeDescription = EntityTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTypeDescription;
    }
    
    public EntityTypeDescription getEntityTypeDescription(EntityType entityType, Language language) {
        return getEntityTypeDescription(entityType, language, EntityPermission.READ_ONLY);
    }
    
    public EntityTypeDescription getEntityTypeDescriptionForUpdate(EntityType entityType, Language language) {
        return getEntityTypeDescription(entityType, language, EntityPermission.READ_WRITE);
    }
    
    public EntityTypeDescriptionValue getEntityTypeDescriptionValue(EntityTypeDescription entityTypeDescription) {
        return entityTypeDescription == null? null: entityTypeDescription.getEntityTypeDescriptionValue().clone();
    }
    
    public EntityTypeDescriptionValue getEntityTypeDescriptionValueForUpdate(EntityType entityType, Language language) {
        return getEntityTypeDescriptionValue(getEntityTypeDescriptionForUpdate(entityType, language));
    }
    
    private List<EntityTypeDescription> getEntityTypeDescriptionsByEntityType(EntityType entityType,
            EntityPermission entityPermission) {
        List<EntityTypeDescription> entityTypeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypedescriptions, languages " +
                        "WHERE entd_ent_entitytypeid = ? AND entd_thrutime = ? AND entd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytypedescriptions " +
                        "WHERE entd_ent_entitytypeid = ? AND entd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTypeDescriptions = EntityTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTypeDescriptions;
    }
    
    public List<EntityTypeDescription> getEntityTypeDescriptionsByEntityType(EntityType entityType) {
        return getEntityTypeDescriptionsByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityTypeDescription> getEntityTypeDescriptionsByEntityTypeForUpdate(EntityType entityType) {
        return getEntityTypeDescriptionsByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityTypeDescription(EntityType entityType, Language language) {
        String description;
        EntityTypeDescription entityTypeDescription = getEntityTypeDescription(entityType, language);
        
        if(entityTypeDescription == null && !language.getIsDefault()) {
            entityTypeDescription = getEntityTypeDescription(entityType, getPartyControl().getDefaultLanguage());
        }
        
        if(entityTypeDescription == null) {
            description = entityType.getLastDetail().getEntityTypeName();
        } else {
            description = entityTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityTypeDescriptionTransfer getEntityTypeDescriptionTransfer(UserVisit userVisit, EntityTypeDescription entityTypeDescription) {
        return getCoreTransferCaches(userVisit).getEntityTypeDescriptionTransferCache().getEntityTypeDescriptionTransfer(entityTypeDescription);
    }
    
    public List<EntityTypeDescriptionTransfer> getEntityTypeDescriptionTransfersByEntityType(UserVisit userVisit,
            EntityType entityType) {
        List<EntityTypeDescription> entityTypeDescriptions = getEntityTypeDescriptionsByEntityType(entityType);
        List<EntityTypeDescriptionTransfer> entityTypeDescriptionTransfers = new ArrayList<>(entityTypeDescriptions.size());
        EntityTypeDescriptionTransferCache entityTypeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityTypeDescriptionTransferCache();
        
        entityTypeDescriptions.stream().forEach((entityTypeDescription) -> {
            entityTypeDescriptionTransfers.add(entityTypeDescriptionTransferCache.getEntityTypeDescriptionTransfer(entityTypeDescription));
        });
        
        return entityTypeDescriptionTransfers;
    }
    
    public void updateEntityTypeDescriptionFromValue(EntityTypeDescriptionValue entityTypeDescriptionValue, BasePK updatedBy) {
        if(entityTypeDescriptionValue.hasBeenModified()) {
            EntityTypeDescription entityTypeDescription = EntityTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityTypeDescriptionValue.getPrimaryKey());
            
            entityTypeDescription.setThruTime(session.START_TIME_LONG);
            entityTypeDescription.store();
            
            EntityType entityType = entityTypeDescription.getEntityType();
            Language language = entityTypeDescription.getLanguage();
            String description = entityTypeDescriptionValue.getDescription();
            
            entityTypeDescription = EntityTypeDescriptionFactory.getInstance().create(entityType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityType.getPrimaryKey(), EventTypes.MODIFY.name(), entityTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityTypeDescription(EntityTypeDescription entityTypeDescription, BasePK deletedBy) {
        entityTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityTypeDescription.getEntityTypePK(), EventTypes.MODIFY.name(), entityTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityTypeDescriptionsByEntityType(EntityType entityType, BasePK deletedBy) {
        List<EntityTypeDescription> entityTypeDescriptions = getEntityTypeDescriptionsByEntityTypeForUpdate(entityType);
        
        entityTypeDescriptions.stream().forEach((entityTypeDescription) -> {
            deleteEntityTypeDescription(entityTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Commands
    // --------------------------------------------------------------------------------
    
    public Command createCommand(ComponentVendor componentVendor, String commandName, Integer sortOrder, BasePK createdBy) {
        Command command = CommandFactory.getInstance().create();
        CommandDetail commandDetail = CommandDetailFactory.getInstance().create(command, componentVendor,
                commandName, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        command = CommandFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, command.getPrimaryKey());
        command.setActiveDetail(commandDetail);
        command.setLastDetail(commandDetail);
        command.store();
        
        sendEventUsingNames(command.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return command;
    }
    
    private Command getCommandByName(ComponentVendor componentVendor, String commandName, EntityPermission entityPermission) {
        Command command = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commands, commanddetails " +
                        "WHERE cmd_activedetailid = cmddt_commanddetailid " +
                        "AND cmddt_cvnd_componentvendorid = ? AND cmddt_commandname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commands, commanddetails " +
                        "WHERE cmd_activedetailid = cmddt_commanddetailid " +
                        "AND cmddt_cvnd_componentvendorid = ? AND cmddt_commandname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, componentVendor.getPrimaryKey().getEntityId());
            ps.setString(2, commandName);
            
            command = CommandFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return command;
    }
    
    public Command getCommandByName(ComponentVendor componentVendor, String commandName) {
        return getCommandByName(componentVendor, commandName, EntityPermission.READ_ONLY);
    }
    
    public Command getCommandByNameForUpdate(ComponentVendor componentVendor, String commandName) {
        return getCommandByName(componentVendor, commandName, EntityPermission.READ_WRITE);
    }
    
    public CommandDetailValue getCommandDetailValueForUpdate(Command command) {
        return command == null? null: command.getLastDetailForUpdate().getCommandDetailValue().clone();
    }
    
    public CommandDetailValue getCommandDetailValueByNameForUpdate(ComponentVendor componentVendor, String commandName) {
        return getCommandDetailValueForUpdate(getCommandByNameForUpdate(componentVendor, commandName));
    }
    
    private Map<ComponentVendor, Map<String, Command>> commandCache = new HashMap<>();
    
    public Command getCommandByNameFromCache(ComponentVendor componentVendor, String commandName) {
        Map<String, Command> cacheByCompnentVendor = commandCache.get(componentVendor);
        
        if(cacheByCompnentVendor == null) {
            cacheByCompnentVendor = new HashMap<>();
            commandCache.put(componentVendor, cacheByCompnentVendor);
        }
        
        Command command = cacheByCompnentVendor.get(commandName);
        
        if(command == null) {
            command = getCommandByName(componentVendor, commandName);
            
            if(command != null) {
                cacheByCompnentVendor.put(commandName, command);
            }
        }
        
        return command;
    }
    
    private List<Command> getCommandsByComponentVendor(ComponentVendor componentVendor, EntityPermission entityPermission) {
        List<Command> commands = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commands, commanddetails " +
                        "WHERE cmd_activedetailid = cmddt_commanddetailid " +
                        "AND cmddt_cvnd_componentvendorid = ? " +
                        "ORDER BY cmddt_sortorder, cmddt_commandname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commands, commanddetails " +
                        "WHERE cmd_activedetailid = cmddt_commanddetailid " +
                        "AND cmddt_cvnd_componentvendorid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, componentVendor.getPrimaryKey().getEntityId());
            
            commands = CommandFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commands;
    }
    
    public List<Command> getCommandsByComponentVendor(ComponentVendor componentVendor) {
        return getCommandsByComponentVendor(componentVendor, EntityPermission.READ_ONLY);
    }
    
    public List<Command> getCommandsByComponentVendorForUpdate(ComponentVendor componentVendor) {
        return getCommandsByComponentVendor(componentVendor, EntityPermission.READ_WRITE);
    }
    
    public List<Command> getCommands() {
        PreparedStatement ps = CommandFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM commands, commanddetails " +
                "WHERE cmd_activedetailid = cmddt_commanddetailid " +
                "ORDER BY cmddt_sortorder, cmddt_commandname");
        
        return CommandFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public CommandTransfer getCommandTransfer(UserVisit userVisit, Command command) {
        return getCoreTransferCaches(userVisit).getCommandTransferCache().getCommandTransfer(command);
    }
    
    private List<CommandTransfer> getCommandTransfers(UserVisit userVisit, List<Command> commands) {
        List<CommandTransfer> commandTransfers = new ArrayList<>(commands.size());
        CommandTransferCache commandTransferCache = getCoreTransferCaches(userVisit).getCommandTransferCache();
        
        commands.stream().forEach((command) -> {
            commandTransfers.add(commandTransferCache.getCommandTransfer(command));
        });
        
        return commandTransfers;
    }
    
    public List<CommandTransfer> getCommandTransfers(UserVisit userVisit) {
        return getCommandTransfers(userVisit, getCommands());
    }
    
    public List<CommandTransfer> getCommandTransfersByComponentVendor(UserVisit userVisit, ComponentVendor componentVendor) {
        return getCommandTransfers(userVisit, getCommandsByComponentVendor(componentVendor));
    }
    
    public void updateCommandFromValue(CommandDetailValue commandDetailValue, BasePK updatedBy) {
        if(commandDetailValue.hasBeenModified()) {
            Command command = CommandFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     commandDetailValue.getCommandPK());
            CommandDetail commandDetail = command.getActiveDetailForUpdate();
            
            commandDetail.setThruTime(session.START_TIME_LONG);
            commandDetail.store();
            
            CommandPK commandPK = commandDetail.getCommandPK();
            ComponentVendorPK componentVendorPK = commandDetail.getComponentVendorPK(); // Not updated
            String commandName = commandDetailValue.getCommandName();
            Integer sortOrder = commandDetailValue.getSortOrder();
            
            commandDetail = CommandDetailFactory.getInstance().create(commandPK, componentVendorPK, commandName,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            command.setActiveDetail(commandDetail);
            command.setLastDetail(commandDetail);
            
            sendEventUsingNames(commandPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteCommand(Command command, BasePK deletedBy) {
        deleteCommandDescriptionsByCommand(command, deletedBy);
        
        CommandDetail commandDetail = command.getLastDetailForUpdate();
        commandDetail.setThruTime(session.START_TIME_LONG);
        command.setActiveDetail(null);
        command.store();
        
        sendEventUsingNames(command.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteCommandsByComponentVendor(ComponentVendor componentVendor, BasePK deletedBy) {
        List<Command> commands = getCommandsByComponentVendorForUpdate(componentVendor);
        
        commands.stream().forEach((command) -> {
            deleteCommand(command, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Command Descriptions
    // --------------------------------------------------------------------------------
    
    public CommandDescription createCommandDescription(Command command, Language language, String description,
            BasePK createdBy) {
        CommandDescription commandDescription = CommandDescriptionFactory.getInstance().create(command,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(command.getPrimaryKey(), EventTypes.MODIFY.name(), commandDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return commandDescription;
    }
    
    private CommandDescription getCommandDescription(Command command, Language language,
            EntityPermission entityPermission) {
        CommandDescription commandDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commanddescriptions " +
                        "WHERE cmdd_cmd_commandid = ? AND cmdd_lang_languageid = ? AND cmdd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commanddescriptions " +
                        "WHERE cmdd_cmd_commandid = ? AND cmdd_lang_languageid = ? AND cmdd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, command.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            commandDescription = CommandDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commandDescription;
    }
    
    public CommandDescription getCommandDescription(Command command, Language language) {
        return getCommandDescription(command, language, EntityPermission.READ_ONLY);
    }
    
    public CommandDescription getCommandDescriptionForUpdate(Command command, Language language) {
        return getCommandDescription(command, language, EntityPermission.READ_WRITE);
    }
    
    public CommandDescriptionValue getCommandDescriptionValue(CommandDescription commandDescription) {
        return commandDescription == null? null: commandDescription.getCommandDescriptionValue().clone();
    }
    
    public CommandDescriptionValue getCommandDescriptionValueForUpdate(Command command, Language language) {
        return getCommandDescriptionValue(getCommandDescriptionForUpdate(command, language));
    }
    
    private List<CommandDescription> getCommandDescriptionsByCommand(Command command,
            EntityPermission entityPermission) {
        List<CommandDescription> commandDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commanddescriptions, languages " +
                        "WHERE cmdd_cmd_commandid = ? AND cmdd_thrutime = ? AND cmdd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commanddescriptions " +
                        "WHERE cmdd_cmd_commandid = ? AND cmdd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, command.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            commandDescriptions = CommandDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commandDescriptions;
    }
    
    public List<CommandDescription> getCommandDescriptionsByCommand(Command command) {
        return getCommandDescriptionsByCommand(command, EntityPermission.READ_ONLY);
    }
    
    public List<CommandDescription> getCommandDescriptionsByCommandForUpdate(Command command) {
        return getCommandDescriptionsByCommand(command, EntityPermission.READ_WRITE);
    }
    
    public String getBestCommandDescription(Command command, Language language) {
        String description;
        CommandDescription commandDescription = getCommandDescription(command, language);
        
        if(commandDescription == null && !language.getIsDefault()) {
            commandDescription = getCommandDescription(command, getPartyControl().getDefaultLanguage());
        }
        
        if(commandDescription == null) {
            description = command.getLastDetail().getCommandName();
        } else {
            description = commandDescription.getDescription();
        }
        
        return description;
    }
    
    public CommandDescriptionTransfer getCommandDescriptionTransfer(UserVisit userVisit, CommandDescription commandDescription) {
        return getCoreTransferCaches(userVisit).getCommandDescriptionTransferCache().getCommandDescriptionTransfer(commandDescription);
    }
    
    public List<CommandDescriptionTransfer> getCommandDescriptionTransfersByCommand(UserVisit userVisit,
            Command command) {
        List<CommandDescription> commandDescriptions = getCommandDescriptionsByCommand(command);
        List<CommandDescriptionTransfer> commandDescriptionTransfers = new ArrayList<>(commandDescriptions.size());
        CommandDescriptionTransferCache commandDescriptionTransferCache = getCoreTransferCaches(userVisit).getCommandDescriptionTransferCache();
        
        commandDescriptions.stream().forEach((commandDescription) -> {
            commandDescriptionTransfers.add(commandDescriptionTransferCache.getCommandDescriptionTransfer(commandDescription));
        });
        
        return commandDescriptionTransfers;
    }
    
    public void updateCommandDescriptionFromValue(CommandDescriptionValue commandDescriptionValue, BasePK updatedBy) {
        if(commandDescriptionValue.hasBeenModified()) {
            CommandDescription commandDescription = CommandDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     commandDescriptionValue.getPrimaryKey());
            
            commandDescription.setThruTime(session.START_TIME_LONG);
            commandDescription.store();
            
            Command command = commandDescription.getCommand();
            Language language = commandDescription.getLanguage();
            String description = commandDescriptionValue.getDescription();
            
            commandDescription = CommandDescriptionFactory.getInstance().create(command, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(command.getPrimaryKey(), EventTypes.MODIFY.name(), commandDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteCommandDescription(CommandDescription commandDescription, BasePK deletedBy) {
        commandDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(commandDescription.getCommandPK(), EventTypes.MODIFY.name(), commandDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteCommandDescriptionsByCommand(Command command, BasePK deletedBy) {
        List<CommandDescription> commandDescriptions = getCommandDescriptionsByCommandForUpdate(command);
        
        commandDescriptions.stream().forEach((commandDescription) -> {
            deleteCommandDescription(commandDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Types
    // --------------------------------------------------------------------------------
    
    public CommandMessageType createCommandMessageType(String commandMessageTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        CommandMessageType defaultCommandMessageType = getDefaultCommandMessageType();
        boolean defaultFound = defaultCommandMessageType != null;
        
        if(defaultFound && isDefault) {
            CommandMessageTypeDetailValue defaultCommandMessageTypeDetailValue = getDefaultCommandMessageTypeDetailValueForUpdate();
            
            defaultCommandMessageTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateCommandMessageTypeFromValue(defaultCommandMessageTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        CommandMessageType commandMessageType = CommandMessageTypeFactory.getInstance().create();
        CommandMessageTypeDetail commandMessageTypeDetail = CommandMessageTypeDetailFactory.getInstance().create(commandMessageType,
                commandMessageTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        commandMessageType = CommandMessageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                commandMessageType.getPrimaryKey());
        commandMessageType.setActiveDetail(commandMessageTypeDetail);
        commandMessageType.setLastDetail(commandMessageTypeDetail);
        commandMessageType.store();
        
        sendEventUsingNames(commandMessageType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return commandMessageType;
    }
    
    private List<CommandMessageType> getCommandMessageTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM commandmessagetypes, commandmessagetypedetails " +
                    "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid " +
                    "ORDER BY cmdmssgtydt_sortorder, cmdmssgtydt_commandmessagetypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM commandmessagetypes, commandmessagetypedetails " +
                    "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = CommandMessageTypeFactory.getInstance().prepareStatement(query);
        
        return CommandMessageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<CommandMessageType> getCommandMessageTypes() {
        return getCommandMessageTypes(EntityPermission.READ_ONLY);
    }
    
    public List<CommandMessageType> getCommandMessageTypesForUpdate() {
        return getCommandMessageTypes(EntityPermission.READ_WRITE);
    }
    
    private CommandMessageType getDefaultCommandMessageType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM commandmessagetypes, commandmessagetypedetails " +
                    "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid AND cmdmssgtydt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM commandmessagetypes, commandmessagetypedetails " +
                    "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid AND cmdmssgtydt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = CommandMessageTypeFactory.getInstance().prepareStatement(query);
        
        return CommandMessageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public CommandMessageType getDefaultCommandMessageType() {
        return getDefaultCommandMessageType(EntityPermission.READ_ONLY);
    }
    
    public CommandMessageType getDefaultCommandMessageTypeForUpdate() {
        return getDefaultCommandMessageType(EntityPermission.READ_WRITE);
    }
    
    public CommandMessageTypeDetailValue getDefaultCommandMessageTypeDetailValueForUpdate() {
        return getDefaultCommandMessageTypeForUpdate().getLastDetailForUpdate().getCommandMessageTypeDetailValue().clone();
    }
    
    private CommandMessageType getCommandMessageTypeByName(String commandMessageTypeName, EntityPermission entityPermission) {
        CommandMessageType commandMessageType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypes, commandmessagetypedetails " +
                        "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid AND cmdmssgtydt_commandmessagetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypes, commandmessagetypedetails " +
                        "WHERE cmdmssgty_activedetailid = cmdmssgtydt_commandmessagetypedetailid AND cmdmssgtydt_commandmessagetypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandMessageTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, commandMessageTypeName);
            
            commandMessageType = CommandMessageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commandMessageType;
    }
    
    public CommandMessageType getCommandMessageTypeByName(String commandMessageTypeName) {
        return getCommandMessageTypeByName(commandMessageTypeName, EntityPermission.READ_ONLY);
    }
    
    public CommandMessageType getCommandMessageTypeByNameForUpdate(String commandMessageTypeName) {
        return getCommandMessageTypeByName(commandMessageTypeName, EntityPermission.READ_WRITE);
    }
    
    public CommandMessageTypeDetailValue getCommandMessageTypeDetailValueForUpdate(CommandMessageType commandMessageType) {
        return commandMessageType == null? null: commandMessageType.getLastDetailForUpdate().getCommandMessageTypeDetailValue().clone();
    }
    
    public CommandMessageTypeDetailValue getCommandMessageTypeDetailValueByNameForUpdate(String commandMessageTypeName) {
        return getCommandMessageTypeDetailValueForUpdate(getCommandMessageTypeByNameForUpdate(commandMessageTypeName));
    }
    
    public CommandMessageTypeChoicesBean getCommandMessageTypeChoices(String defaultCommandMessageTypeChoice, Language language,
            boolean allowNullChoice) {
        List<CommandMessageType> commandMessageTypes = getCommandMessageTypes();
        int size = commandMessageTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCommandMessageTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(CommandMessageType commandMessageType: commandMessageTypes) {
            CommandMessageTypeDetail commandMessageTypeDetail = commandMessageType.getLastDetail();
            String label = getBestCommandMessageTypeDescription(commandMessageType, language);
            String value = commandMessageTypeDetail.getCommandMessageTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultCommandMessageTypeChoice == null? false: defaultCommandMessageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && commandMessageTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CommandMessageTypeChoicesBean(labels, values, defaultValue);
    }
    
    public CommandMessageTypeTransfer getCommandMessageTypeTransfer(UserVisit userVisit, CommandMessageType commandMessageType) {
        return getCoreTransferCaches(userVisit).getCommandMessageTypeTransferCache().getCommandMessageTypeTransfer(commandMessageType);
    }
    
    public List<CommandMessageTypeTransfer> getCommandMessageTypeTransfers(UserVisit userVisit) {
        List<CommandMessageType> commandMessageTypes = getCommandMessageTypes();
        List<CommandMessageTypeTransfer> commandMessageTypeTransfers = new ArrayList<>(commandMessageTypes.size());
        CommandMessageTypeTransferCache commandMessageTypeTransferCache = getCoreTransferCaches(userVisit).getCommandMessageTypeTransferCache();
        
        commandMessageTypes.stream().forEach((commandMessageType) -> {
            commandMessageTypeTransfers.add(commandMessageTypeTransferCache.getCommandMessageTypeTransfer(commandMessageType));
        });
        
        return commandMessageTypeTransfers;
    }
    
    private void updateCommandMessageTypeFromValue(CommandMessageTypeDetailValue commandMessageTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(commandMessageTypeDetailValue.hasBeenModified()) {
            CommandMessageType commandMessageType = CommandMessageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     commandMessageTypeDetailValue.getCommandMessageTypePK());
            CommandMessageTypeDetail commandMessageTypeDetail = commandMessageType.getActiveDetailForUpdate();
            
            commandMessageTypeDetail.setThruTime(session.START_TIME_LONG);
            commandMessageTypeDetail.store();
            
            CommandMessageTypePK commandMessageTypePK = commandMessageTypeDetail.getCommandMessageTypePK();
            String commandMessageTypeName = commandMessageTypeDetailValue.getCommandMessageTypeName();
            Boolean isDefault = commandMessageTypeDetailValue.getIsDefault();
            Integer sortOrder = commandMessageTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                CommandMessageType defaultCommandMessageType = getDefaultCommandMessageType();
                boolean defaultFound = defaultCommandMessageType != null && !defaultCommandMessageType.equals(commandMessageType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    CommandMessageTypeDetailValue defaultCommandMessageTypeDetailValue = getDefaultCommandMessageTypeDetailValueForUpdate();
                    
                    defaultCommandMessageTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateCommandMessageTypeFromValue(defaultCommandMessageTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            commandMessageTypeDetail = CommandMessageTypeDetailFactory.getInstance().create(commandMessageTypePK, commandMessageTypeName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            commandMessageType.setActiveDetail(commandMessageTypeDetail);
            commandMessageType.setLastDetail(commandMessageTypeDetail);
            
            sendEventUsingNames(commandMessageTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateCommandMessageTypeFromValue(CommandMessageTypeDetailValue commandMessageTypeDetailValue, BasePK updatedBy) {
        updateCommandMessageTypeFromValue(commandMessageTypeDetailValue, true, updatedBy);
    }
    
    public void deleteCommandMessageType(CommandMessageType commandMessageType, BasePK deletedBy) {
        deleteCommandMessagesByCommandMessageType(commandMessageType, deletedBy);
        deleteCommandMessageTypeDescriptionsByCommandMessageType(commandMessageType, deletedBy);
        
        CommandMessageTypeDetail commandMessageTypeDetail = commandMessageType.getLastDetailForUpdate();
        commandMessageTypeDetail.setThruTime(session.START_TIME_LONG);
        commandMessageType.setActiveDetail(null);
        commandMessageType.store();
        
        // Check for default, and pick one if necessary
        CommandMessageType defaultCommandMessageType = getDefaultCommandMessageType();
        if(defaultCommandMessageType == null) {
            List<CommandMessageType> commandMessageTypes = getCommandMessageTypesForUpdate();
            
            if(!commandMessageTypes.isEmpty()) {
                Iterator<CommandMessageType> iter = commandMessageTypes.iterator();
                if(iter.hasNext()) {
                    defaultCommandMessageType = iter.next();
                }
                CommandMessageTypeDetailValue commandMessageTypeDetailValue = defaultCommandMessageType.getLastDetailForUpdate().getCommandMessageTypeDetailValue().clone();
                
                commandMessageTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateCommandMessageTypeFromValue(commandMessageTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(commandMessageType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    public CommandMessageTypeDescription createCommandMessageTypeDescription(CommandMessageType commandMessageType,
            Language language, String description, BasePK createdBy) {
        CommandMessageTypeDescription commandMessageTypeDescription = CommandMessageTypeDescriptionFactory.getInstance().create(commandMessageType,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(commandMessageType.getPrimaryKey(), EventTypes.MODIFY.name(), commandMessageTypeDescription.getPrimaryKey(),
                null, createdBy);
        
        return commandMessageTypeDescription;
    }
    
    private CommandMessageTypeDescription getCommandMessageTypeDescription(CommandMessageType commandMessageType, Language language,
            EntityPermission entityPermission) {
        CommandMessageTypeDescription commandMessageTypeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypedescriptions " +
                        "WHERE cmdmssgtyd_cmdmssgty_commandmessagetypeid = ? AND cmdmssgtyd_lang_languageid = ? AND cmdmssgtyd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypedescriptions " +
                        "WHERE cmdmssgtyd_cmdmssgty_commandmessagetypeid = ? AND cmdmssgtyd_lang_languageid = ? AND cmdmssgtyd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandMessageTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commandMessageType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            commandMessageTypeDescription = CommandMessageTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commandMessageTypeDescription;
    }
    
    public CommandMessageTypeDescription getCommandMessageTypeDescription(CommandMessageType commandMessageType, Language language) {
        return getCommandMessageTypeDescription(commandMessageType, language, EntityPermission.READ_ONLY);
    }
    
    public CommandMessageTypeDescription getCommandMessageTypeDescriptionForUpdate(CommandMessageType commandMessageType, Language language) {
        return getCommandMessageTypeDescription(commandMessageType, language, EntityPermission.READ_WRITE);
    }
    
    public CommandMessageTypeDescriptionValue getCommandMessageTypeDescriptionValue(CommandMessageTypeDescription commandMessageTypeDescription) {
        return commandMessageTypeDescription == null? null: commandMessageTypeDescription.getCommandMessageTypeDescriptionValue().clone();
    }
    
    public CommandMessageTypeDescriptionValue getCommandMessageTypeDescriptionValueForUpdate(CommandMessageType commandMessageType, Language language) {
        return getCommandMessageTypeDescriptionValue(getCommandMessageTypeDescriptionForUpdate(commandMessageType, language));
    }
    
    private List<CommandMessageTypeDescription> getCommandMessageTypeDescriptionsByCommandMessageType(CommandMessageType commandMessageType,
            EntityPermission entityPermission) {
        List<CommandMessageTypeDescription> commandMessageTypeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypedescriptions, languages " +
                        "WHERE cmdmssgtyd_cmdmssgty_commandmessagetypeid = ? AND cmdmssgtyd_thrutime = ? AND cmdmssgtyd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetypedescriptions " +
                        "WHERE cmdmssgtyd_cmdmssgty_commandmessagetypeid = ? AND cmdmssgtyd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandMessageTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commandMessageType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            commandMessageTypeDescriptions = CommandMessageTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commandMessageTypeDescriptions;
    }
    
    public List<CommandMessageTypeDescription> getCommandMessageTypeDescriptionsByCommandMessageType(CommandMessageType commandMessageType) {
        return getCommandMessageTypeDescriptionsByCommandMessageType(commandMessageType, EntityPermission.READ_ONLY);
    }
    
    public List<CommandMessageTypeDescription> getCommandMessageTypeDescriptionsByCommandMessageTypeForUpdate(CommandMessageType commandMessageType) {
        return getCommandMessageTypeDescriptionsByCommandMessageType(commandMessageType, EntityPermission.READ_WRITE);
    }
    
    public String getBestCommandMessageTypeDescription(CommandMessageType commandMessageType, Language language) {
        String description;
        CommandMessageTypeDescription commandMessageTypeDescription = getCommandMessageTypeDescription(commandMessageType, language);
        
        if(commandMessageTypeDescription == null && !language.getIsDefault()) {
            commandMessageTypeDescription = getCommandMessageTypeDescription(commandMessageType, getPartyControl().getDefaultLanguage());
        }
        
        if(commandMessageTypeDescription == null) {
            description = commandMessageType.getLastDetail().getCommandMessageTypeName();
        } else {
            description = commandMessageTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public CommandMessageTypeDescriptionTransfer getCommandMessageTypeDescriptionTransfer(UserVisit userVisit, CommandMessageTypeDescription commandMessageTypeDescription) {
        return getCoreTransferCaches(userVisit).getCommandMessageTypeDescriptionTransferCache().getCommandMessageTypeDescriptionTransfer(commandMessageTypeDescription);
    }
    
    public List<CommandMessageTypeDescriptionTransfer> getCommandMessageTypeDescriptionTransfers(UserVisit userVisit, CommandMessageType commandMessageType) {
        List<CommandMessageTypeDescription> commandMessageTypeDescriptions = getCommandMessageTypeDescriptionsByCommandMessageType(commandMessageType);
        List<CommandMessageTypeDescriptionTransfer> commandMessageTypeDescriptionTransfers = new ArrayList<>(commandMessageTypeDescriptions.size());
            CommandMessageTypeDescriptionTransferCache commandMessageTypeDescriptionTransferCache = getCoreTransferCaches(userVisit).getCommandMessageTypeDescriptionTransferCache();
        
            commandMessageTypeDescriptions.stream().forEach((commandMessageTypeDescription) -> {
                commandMessageTypeDescriptionTransfers.add(commandMessageTypeDescriptionTransferCache.getCommandMessageTypeDescriptionTransfer(commandMessageTypeDescription));
        });
        
        return commandMessageTypeDescriptionTransfers;
    }
    
    public void updateCommandMessageTypeDescriptionFromValue(CommandMessageTypeDescriptionValue commandMessageTypeDescriptionValue, BasePK updatedBy) {
        if(commandMessageTypeDescriptionValue.hasBeenModified()) {
            CommandMessageTypeDescription commandMessageTypeDescription = CommandMessageTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     commandMessageTypeDescriptionValue.getPrimaryKey());
            
            commandMessageTypeDescription.setThruTime(session.START_TIME_LONG);
            commandMessageTypeDescription.store();
            
            CommandMessageType commandMessageType = commandMessageTypeDescription.getCommandMessageType();
            Language language = commandMessageTypeDescription.getLanguage();
            String description = commandMessageTypeDescriptionValue.getDescription();
            
            commandMessageTypeDescription = CommandMessageTypeDescriptionFactory.getInstance().create(commandMessageType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(commandMessageType.getPrimaryKey(), EventTypes.MODIFY.name(), commandMessageTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteCommandMessageTypeDescription(CommandMessageTypeDescription commandMessageTypeDescription, BasePK deletedBy) {
        commandMessageTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(commandMessageTypeDescription.getCommandMessageTypePK(), EventTypes.MODIFY.name(),
                commandMessageTypeDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteCommandMessageTypeDescriptionsByCommandMessageType(CommandMessageType commandMessageType, BasePK deletedBy) {
        List<CommandMessageTypeDescription> commandMessageTypeDescriptions = getCommandMessageTypeDescriptionsByCommandMessageTypeForUpdate(commandMessageType);
        
        commandMessageTypeDescriptions.stream().forEach((commandMessageTypeDescription) -> {
            deleteCommandMessageTypeDescription(commandMessageTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Command Messages
    // --------------------------------------------------------------------------------
    
    public CommandMessage createCommandMessage(CommandMessageType commandMessageType, String commandMessageKey, BasePK createdBy) {
        CommandMessage commandMessage = CommandMessageFactory.getInstance().create();
        CommandMessageDetail commandMessageDetail = CommandMessageDetailFactory.getInstance().create(commandMessage,
                commandMessageType, commandMessageKey, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        commandMessage = CommandMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                commandMessage.getPrimaryKey());
        commandMessage.setActiveDetail(commandMessageDetail);
        commandMessage.setLastDetail(commandMessageDetail);
        commandMessage.store();
        
        sendEventUsingNames(commandMessage.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return commandMessage;
    }
    
    private CommandMessage getCommandMessageByKey(CommandMessageType commandMessageType, String commandMessageKey, EntityPermission entityPermission) {
        CommandMessage commandMessage = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessages, commandmessagedetails " +
                        "WHERE cmdmssg_activedetailid = cmdmssgdt_commandmessagedetailid " +
                        "AND cmdmssgdt_cmdmssgty_commandmessagetypeid = ? AND cmdmssgdt_commandmessagekey = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessages, commandmessagedetails " +
                        "WHERE cmdmssg_activedetailid = cmdmssgdt_commandmessagedetailid " +
                        "AND cmdmssgdt_cmdmssgty_commandmessagetypeid = ? AND cmdmssgdt_commandmessagekey = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandMessageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commandMessageType.getPrimaryKey().getEntityId());
            ps.setString(2, commandMessageKey);
            
            commandMessage = CommandMessageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commandMessage;
    }
    
    public CommandMessage getCommandMessageByKey(CommandMessageType commandMessageType, String commandMessageKey) {
        return getCommandMessageByKey(commandMessageType, commandMessageKey, EntityPermission.READ_ONLY);
    }
    
    public CommandMessage getCommandMessageByKeyForUpdate(CommandMessageType commandMessageType, String commandMessageKey) {
        return getCommandMessageByKey(commandMessageType, commandMessageKey, EntityPermission.READ_WRITE);
    }
    
    public CommandMessageDetailValue getCommandMessageDetailValueForUpdate(CommandMessage commandMessage) {
        return commandMessage == null? null: commandMessage.getLastDetailForUpdate().getCommandMessageDetailValue().clone();
    }
    
    public CommandMessageDetailValue getCommandMessageDetailValueByKeyForUpdate(CommandMessageType commandMessageType, String commandMessageKey) {
        return getCommandMessageDetailValueForUpdate(getCommandMessageByKeyForUpdate(commandMessageType, commandMessageKey));
    }
    
    private List<CommandMessage> getCommandMessagesByCommandMessageType(CommandMessageType commandMessageType, EntityPermission entityPermission) {
        List<CommandMessage> commandMessages = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessages, commandmessagedetails " +
                        "WHERE cmdmssg_activedetailid = cmdmssgdt_commandmessagedetailid " +
                        "AND cmdmssgdt_cmdmssgty_commandmessagetypeid = ? " +
                        "ORDER BY cmdmssgdt_commandmessagekey";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessages, commandmessagedetails " +
                        "WHERE cmdmssg_activedetailid = cmdmssgdt_commandmessagedetailid " +
                        "AND cmdmssgdt_cmdmssgty_commandmessagetypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandMessageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commandMessageType.getPrimaryKey().getEntityId());
            
            commandMessages = CommandMessageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commandMessages;
    }
    
    public List<CommandMessage> getCommandMessagesByCommandMessageType(CommandMessageType commandMessageType) {
        return getCommandMessagesByCommandMessageType(commandMessageType, EntityPermission.READ_ONLY);
    }
    
    public List<CommandMessage> getCommandMessagesByCommandMessageTypeForUpdate(CommandMessageType commandMessageType) {
        return getCommandMessagesByCommandMessageType(commandMessageType, EntityPermission.READ_WRITE);
    }
    
    public CommandMessageTransfer getCommandMessageTransfer(UserVisit userVisit, CommandMessage commandMessage) {
        return getCoreTransferCaches(userVisit).getCommandMessageTransferCache().getCommandMessageTransfer(commandMessage);
    }
    
    private List<CommandMessageTransfer> getCommandMessageTransfers(UserVisit userVisit, List<CommandMessage> commandMessages) {
        List<CommandMessageTransfer> commandMessageTransfers = new ArrayList<>(commandMessages.size());
        CommandMessageTransferCache commandMessageTransferCache = getCoreTransferCaches(userVisit).getCommandMessageTransferCache();
        
        commandMessages.stream().forEach((commandMessage) -> {
            commandMessageTransfers.add(commandMessageTransferCache.getCommandMessageTransfer(commandMessage));
        });
        
        return commandMessageTransfers;
    }
    
    public List<CommandMessageTransfer> getCommandMessageTransfers(UserVisit userVisit, CommandMessageType commandMessageType) {
        return getCommandMessageTransfers(userVisit, getCommandMessagesByCommandMessageType(commandMessageType));
    }
    
    public void updateCommandMessageFromValue(CommandMessageDetailValue commandMessageDetailValue, BasePK updatedBy) {
        if(commandMessageDetailValue.hasBeenModified()) {
            CommandMessage commandMessage = CommandMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     commandMessageDetailValue.getCommandMessagePK());
            CommandMessageDetail commandMessageDetail = commandMessage.getActiveDetailForUpdate();
            
            commandMessageDetail.setThruTime(session.START_TIME_LONG);
            commandMessageDetail.store();
            
            CommandMessagePK commandMessagePK = commandMessageDetail.getCommandMessagePK();
            CommandMessageTypePK commandMessageTypePK = commandMessageDetail.getCommandMessageTypePK(); // Not updated
            String commandMessageKey = commandMessageDetailValue.getCommandMessageKey();
            
            commandMessageDetail = CommandMessageDetailFactory.getInstance().create(commandMessagePK, commandMessageTypePK, commandMessageKey,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            commandMessage.setActiveDetail(commandMessageDetail);
            commandMessage.setLastDetail(commandMessageDetail);
            
            sendEventUsingNames(commandMessagePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteCommandMessage(CommandMessage commandMessage, BasePK deletedBy) {
        deleteCommandMessageTranslationsByCommandMessage(commandMessage, deletedBy);
        
        CommandMessageDetail commandMessageDetail = commandMessage.getLastDetailForUpdate();
        commandMessageDetail.setThruTime(session.START_TIME_LONG);
        commandMessage.setActiveDetail(null);
        commandMessage.store();
        
        sendEventUsingNames(commandMessage.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteCommandMessages(List<CommandMessage> commandMessages, BasePK deletedBy) {
        commandMessages.stream().forEach((commandMessage) -> {
            deleteCommandMessage(commandMessage, deletedBy);
        });
    }
    
    public void deleteCommandMessagesByCommandMessageType(CommandMessageType commandMessageType, BasePK deletedBy) {
        deleteCommandMessages(getCommandMessagesByCommandMessageTypeForUpdate(commandMessageType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Strings
    // --------------------------------------------------------------------------------
    
    public CommandMessageTranslation createCommandMessageTranslation(CommandMessage commandMessage, Language language, String translation, BasePK createdBy) {
        CommandMessageTranslation commandMessageTranslation = CommandMessageTranslationFactory.getInstance().create(commandMessage, language, translation,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(commandMessageTranslation.getCommandMessagePK(), EventTypes.MODIFY.name(), commandMessageTranslation.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return commandMessageTranslation;
    }
    
    private List<CommandMessageTranslation> getCommandMessageTranslationsByCommandMessage(CommandMessage commandMessage, EntityPermission entityPermission) {
        List<CommandMessageTranslation> commandMessageTranslations = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetranslations, languages " +
                        "WHERE cmdmssgtr_cmdmssg_commandMessageid = ? AND cmdmssgtr_thrutime = ? " +
                        "AND cmdmssgtr_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetranslations " +
                        "WHERE cmdmssgtr_cmdmssg_commandMessageid = ? AND cmdmssgtr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandMessageTranslationFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commandMessage.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            commandMessageTranslations = CommandMessageTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commandMessageTranslations;
    }
    
    public List<CommandMessageTranslation> getCommandMessageTranslationsByCommandMessage(CommandMessage commandMessage) {
        return getCommandMessageTranslationsByCommandMessage(commandMessage, EntityPermission.READ_ONLY);
    }
    
    public List<CommandMessageTranslation> getCommandMessageTranslationsByCommandMessageForUpdate(CommandMessage commandMessage) {
        return getCommandMessageTranslationsByCommandMessage(commandMessage, EntityPermission.READ_WRITE);
    }
    
    public CommandMessageTranslation getBestCommandMessageTranslation(CommandMessage commandMessage, Language language) {
        CommandMessageTranslation commandMessageTranslation = getCommandMessageTranslation(commandMessage, language);
        
        if(commandMessageTranslation == null && !language.getIsDefault()) {
            commandMessageTranslation = getCommandMessageTranslation(commandMessage, getPartyControl().getDefaultLanguage());
        }
        
        return commandMessageTranslation;
    }
    
    private CommandMessageTranslation getCommandMessageTranslation(CommandMessage commandMessage, Language language, EntityPermission entityPermission) {
        CommandMessageTranslation commandMessageTranslation = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetranslations " +
                        "WHERE cmdmssgtr_cmdmssg_commandmessageid = ? AND cmdmssgtr_lang_languageid = ? AND cmdmssgtr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commandmessagetranslations " +
                        "WHERE cmdmssgtr_cmdmssg_commandmessageid = ? AND cmdmssgtr_lang_languageid = ? AND cmdmssgtr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommandMessageTranslationFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commandMessage.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            commandMessageTranslation = CommandMessageTranslationFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commandMessageTranslation;
    }
    
    public CommandMessageTranslation getCommandMessageTranslation(CommandMessage commandMessage, Language language) {
        return getCommandMessageTranslation(commandMessage, language, EntityPermission.READ_ONLY);
    }
    
    public CommandMessageTranslation getCommandMessageTranslationForUpdate(CommandMessage commandMessage, Language language) {
        return getCommandMessageTranslation(commandMessage, language, EntityPermission.READ_WRITE);
    }
    
    public CommandMessageTranslationValue getCommandMessageTranslationValue(CommandMessageTranslation commandMessageTranslation) {
        return commandMessageTranslation == null? null: commandMessageTranslation.getCommandMessageTranslationValue().clone();
    }
    
    public CommandMessageTranslationValue getCommandMessageTranslationValueForUpdate(CommandMessage commandMessage, Language language) {
        CommandMessageTranslation commandMessageTranslation = getCommandMessageTranslationForUpdate(commandMessage, language);
        
        return commandMessageTranslation == null? null: getCommandMessageTranslationValue(commandMessageTranslation);
    }
    
    public List<CommandMessageTranslationTransfer> getCommandMessageTranslationTransfers(UserVisit userVisit, List<CommandMessageTranslation> commandMessageTranslations) {
        List<CommandMessageTranslationTransfer> commandMessageTranslationTransfers = new ArrayList<>(commandMessageTranslations.size());
        CommandMessageTranslationTransferCache commandMessageTranslationTransferCache = getCoreTransferCaches(userVisit).getCommandMessageTranslationTransferCache();
        
        commandMessageTranslations.stream().forEach((commandMessageTranslation) -> {
            commandMessageTranslationTransfers.add(commandMessageTranslationTransferCache.getCommandMessageTranslationTransfer(commandMessageTranslation));
        });
        
        return commandMessageTranslationTransfers;
    }
    
    public List<CommandMessageTranslationTransfer> getCommandMessageTranslationTransfersByCommandMessage(UserVisit userVisit, CommandMessage commandMessage) {
        return getCommandMessageTranslationTransfers(userVisit, getCommandMessageTranslationsByCommandMessage(commandMessage));
    }

    public CommandMessageTranslationTransfer getCommandMessageTranslationTransfer(UserVisit userVisit, CommandMessageTranslation commandMessageTranslation) {
        return getCoreTransferCaches(userVisit).getCommandMessageTranslationTransferCache().getCommandMessageTranslationTransfer(commandMessageTranslation);
    }

    public void updateCommandMessageTranslationFromValue(CommandMessageTranslationValue commandMessageTranslationValue, BasePK updatedBy) {
        if(commandMessageTranslationValue.hasBeenModified()) {
            CommandMessageTranslation commandMessageTranslation = CommandMessageTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    commandMessageTranslationValue.getPrimaryKey());
            
            commandMessageTranslation.setThruTime(session.START_TIME_LONG);
            commandMessageTranslation.store();
            
            CommandMessagePK commandMessagePK = commandMessageTranslation.getCommandMessagePK(); // Not updated
            LanguagePK languagePK = commandMessageTranslation.getLanguagePK(); // Not updated
            String translation = commandMessageTranslationValue.getTranslation();
            
            commandMessageTranslation = CommandMessageTranslationFactory.getInstance().create(commandMessagePK, languagePK, translation, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(commandMessagePK, EventTypes.MODIFY.name(), commandMessageTranslation.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteCommandMessageTranslation(CommandMessageTranslation commandMessageTranslation, BasePK deletedBy) {
        commandMessageTranslation.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(commandMessageTranslation.getCommandMessagePK(), EventTypes.MODIFY.name(), commandMessageTranslation.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteCommandMessageTranslations(List<CommandMessageTranslation> commandMessageTranslations, BasePK deletedBy) {
        commandMessageTranslations.stream().forEach((commandMessageTranslation) -> {
            deleteCommandMessageTranslation(commandMessageTranslation, deletedBy);
        });
    }
    
    public void deleteCommandMessageTranslationsByCommandMessage(CommandMessage commandMessage, BasePK deletedBy) {
        deleteCommandMessageTranslations(getCommandMessageTranslationsByCommandMessageForUpdate(commandMessage), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Instances
    // --------------------------------------------------------------------------------
    
    public EntityInstance createEntityInstance(EntityType entityType, Long entityUniqueId) {
        return EntityInstanceFactory.getInstance().create(entityType, entityUniqueId, null, null, null);
    }
    
    public boolean verifyEntityInstance(final EntityInstance entityInstance, final String componentVendorName, final String entityTypeName) {
        boolean result = true;
        EntityTypeDetail entityTypeDetail = entityInstance.getEntityType().getLastDetail();
        
        if(entityTypeDetail.getEntityTypeName().equals(entityTypeName)) {
            if(!entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName().equals(componentVendorName)) {
                result = false;
            }
        } else {
            result = false;
        }
        
        return result;
    }

    public long countEntityInstances() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityinstances");
    }

    public long countEntityInstancesByEntityType(EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityinstances " +
                "WHERE eni_ent_entitytypeid = ?",
                entityType);
    }

    public List<EntityInstance> getEntityInstancesByEntityType(EntityType entityType) {
        List<EntityInstance> entityInstances = null;
        
        try {
            PreparedStatement ps = EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityinstances " +
                    "WHERE eni_ent_entitytypeid = ? " +
                    "ORDER BY eni_entityuniqueid");
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            entityInstances = EntityInstanceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityInstances;
    }
    
    private EntityInstance getEntityInstance(EntityType entityType, Long entityUniqueId, EntityPermission entityPermission) {
        EntityInstance entityInstance = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_ent_entitytypeid = ? AND eni_entityuniqueid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_ent_entitytypeid = ? AND eni_entityuniqueid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityInstanceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, entityUniqueId);
            
            entityInstance = EntityInstanceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityInstance;
    }
    
    public EntityInstance getEntityInstance(EntityType entityType, Long entityUniqueId) {
        return getEntityInstance(entityType, entityUniqueId, EntityPermission.READ_ONLY);
    }
    
    public EntityInstance getEntityInstanceForUpdate(EntityType entityType, Long entityUniqueId) {
        return getEntityInstance(entityType, entityUniqueId, EntityPermission.READ_WRITE);
    }
    

    private EntityInstance getEntityInstanceByKey(String key, EntityPermission entityPermission) {
        EntityInstance entityInstance = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_key = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_key = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityInstanceFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, key);
            
            entityInstance = EntityInstanceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityInstance;
    }
    
    public EntityInstance getEntityInstanceByKey(String key) {
        return getEntityInstanceByKey(key, EntityPermission.READ_ONLY);
    }
    
    public EntityInstance getEntityInstanceByKeyForUpdate(String key) {
        return getEntityInstanceByKey(key, EntityPermission.READ_WRITE);
    }
    
    public EntityInstance ensureKeyForEntityInstance(EntityInstance entityInstance, boolean forceRegeneration) {
        var key = entityInstance.getKey();
        
        if(key == null || forceRegeneration) {
            // Convert to READ_WRITE if necessary...
            if(entityInstance.getEntityPermission().equals(EntityPermission.READ_ONLY)) {
                entityInstance = EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityInstance.getPrimaryKey());
            }
            
            // Keep generating keys until a unique one is found...
            EntityInstance duplicateEntityInstance;
            do {
                key = KeyUtils.getInstance().generateKey();
                duplicateEntityInstance = getEntityInstanceByKey(key);
            } while(duplicateEntityInstance != null);
            
            // Store it immediately in order to decrease the odds of another thread choosing the same key...
            entityInstance.setKey(key);
            entityInstance.store();
        }
        
        return entityInstance;
    }
    
    private EntityInstance getEntityInstanceByGuid(String guid, EntityPermission entityPermission) {
        EntityInstance entityInstance = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_guid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_guid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityInstanceFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, guid);
            
            entityInstance = EntityInstanceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityInstance;
    }
    
    public EntityInstance getEntityInstanceByGuid(String guid) {
        return getEntityInstanceByGuid(guid, EntityPermission.READ_ONLY);
    }
    
    public EntityInstance getEntityInstanceByGuidForUpdate(String guid) {
        return getEntityInstanceByGuid(guid, EntityPermission.READ_WRITE);
    }
    
    public EntityInstance ensureGuidForEntityInstance(EntityInstance entityInstance, boolean forceRegeneration) {
        var guid = entityInstance.getGuid();
        
        if(guid == null || forceRegeneration) {
            // Convert to READ_WRITE if necessary...
            if(entityInstance.getEntityPermission().equals(EntityPermission.READ_ONLY)) {
                entityInstance = EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityInstance.getPrimaryKey());
            }
            
            // Keep generating GUIDs until a unique one is found...
            EntityInstance duplicateEntityInstance;
            do {
                guid = GuidUtils.getInstance().generateGuid();
                duplicateEntityInstance = getEntityInstanceByGuid(guid);
            } while(duplicateEntityInstance != null);
            
            // Store it immediately in order to decrease the odds of another thread choosing the same GUID...
            entityInstance.setGuid(guid);
            entityInstance.store();
        }
        
        return entityInstance;
    }
    
    private EntityInstance getEntityInstanceByUlid(String ulid, EntityPermission entityPermission) {
        EntityInstance entityInstance = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_ulid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_ulid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityInstanceFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, ulid);
            
            entityInstance = EntityInstanceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityInstance;
    }
    
    public EntityInstance getEntityInstanceByUlid(String ulid) {
        return getEntityInstanceByUlid(ulid, EntityPermission.READ_ONLY);
    }
    
    public EntityInstance getEntityInstanceByUlidForUpdate(String ulid) {
        return getEntityInstanceByUlid(ulid, EntityPermission.READ_WRITE);
    }
    
    public EntityInstance ensureUlidForEntityInstance(EntityInstance entityInstance, boolean forceRegeneration) {
        var ulid = entityInstance.getUlid();
        
        if(ulid == null || forceRegeneration) {
            // Convert to READ_WRITE if necessary...
            if(entityInstance.getEntityPermission().equals(EntityPermission.READ_ONLY)) {
                entityInstance = EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityInstance.getPrimaryKey());
            }
            
            // Keep generating ULIDs until a unique one is found...
            EntityInstance duplicateEntityInstance;
            do {
                ulid = UlidUtils.getInstance().generateUlid(entityInstance);
                duplicateEntityInstance = getEntityInstanceByUlid(ulid);
            } while(duplicateEntityInstance != null);
            
            // Store it immediately in order to decrease the odds of another thread choosing the same ULID...
            entityInstance.setUlid(ulid);
            entityInstance.store();
        }
        
        return entityInstance;
    }
    
    public EntityInstanceTransfer getEntityInstanceTransfer(UserVisit userVisit, EntityInstance entityInstance, boolean includeEntityAppearance,
            boolean includeNames, boolean includeKey, boolean includeGuid, boolean includeUlid) {
        return getCoreTransferCaches(userVisit).getEntityInstanceTransferCache().getEntityInstanceTransfer(entityInstance, includeEntityAppearance,
                includeNames, includeKey, includeGuid, includeUlid);
    }
    
    public EntityInstanceTransfer getEntityInstanceTransfer(UserVisit userVisit, BaseEntity baseEntity, boolean includeEntityAppearance, boolean includeNames,
            boolean includeKey, boolean includeGuid, boolean includeUlid) {
        return getEntityInstanceTransfer(userVisit, getEntityInstanceByBasePK(baseEntity.getPrimaryKey()), includeEntityAppearance, includeNames, includeKey,
                includeGuid, includeUlid);
    }

    public List<EntityInstanceTransfer> getEntityInstanceTransfers(UserVisit userVisit, Collection<EntityInstance> entityInstances,
            boolean includeEntityAppearance, boolean includeNames, boolean includeKey, boolean includeGuid, boolean includeUlid) {
        var entityInstanceTransfers = new ArrayList<EntityInstanceTransfer>(entityInstances.size());
        var entityInstanceTransferCache = getCoreTransferCaches(userVisit).getEntityInstanceTransferCache();

        entityInstances.stream().forEach((entityInstance) -> {
            entityInstanceTransfers.add(entityInstanceTransferCache.getEntityInstanceTransfer(entityInstance, includeEntityAppearance, includeNames,
                    includeKey, includeGuid, includeUlid));
        });

        return entityInstanceTransfers;
    }

    public List<EntityInstanceTransfer> getEntityInstanceTransfersByEntityType(UserVisit userVisit, EntityType entityType,
            boolean includeEntityAppearance, boolean includeNames, boolean includeKey, boolean includeGuid, boolean includeUlid) {
        return getEntityInstanceTransfers(userVisit, getEntityInstancesByEntityType(entityType), includeEntityAppearance,
                includeNames, includeKey, includeGuid, includeUlid);
    }

    /** Gets an EntityInstance for BasePK, creating it if necessary. Overrides function from BaseModelControl.
     * Some errors from this function are normal during the initial load of data into the database.
     */
    private EntityInstance getEntityInstanceByBasePK(BasePK pk, EntityPermission entityPermission) {
        EntityInstance entityInstance = null;
        
        if(CoreDebugFlags.LogEntityInstanceResolution) {
            getLog().info(">>> getEntityInstanceByBasePK(pk=" + pk + ")");
        }
        
        if(pk != null) {
            String componentVendorName = pk.getComponentVendorName();
            ComponentVendor componentVendor = getComponentVendorByNameFromCache(componentVendorName);
            
            if(CoreDebugFlags.LogEntityInstanceResolution) {
                getLog().info("--- componentVendor = " + componentVendor);
            }
            
            if(componentVendor != null) {
                String entityTypeName = pk.getEntityTypeName();
                EntityType entityType = getEntityTypeByNameFromCache(componentVendor, entityTypeName);
                
                if(CoreDebugFlags.LogEntityInstanceResolution) {
                    getLog().info("--- entityType = " + entityType);
                }
                
                if(entityType != null) {
                    Long entityUniqueId = pk.getEntityId();
                    
                    if(CoreDebugFlags.LogEntityInstanceResolution) {
                        getLog().info("--- entityUniqueId = " + entityUniqueId);
                    }
                    
                    entityInstance = getEntityInstance(entityType, entityUniqueId, entityPermission);
                    if(entityInstance == null) {
                        entityInstance = createEntityInstance(entityType, entityUniqueId);
                        
                        if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                            // Convert to R/W
                            entityInstance = EntityInstanceFactory.getInstance().getEntityFromPK(session,
                                    EntityPermission.READ_WRITE, entityInstance.getPrimaryKey());
                        }
                    }
                    
                    if(CoreDebugFlags.LogEntityInstanceResolution) {
                        getLog().info("--- entityInstance = " + entityInstance);
                    }
                } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                    getLog().error("getEntityInstanceByBasePK: unknown entityTypeName \"" + componentVendorName + "." + entityTypeName + "\"");
                }
            } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                getLog().error("getEntityInstanceByBasePK: unknown componentVendorName \"" + componentVendorName + "\"");
            }
        } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
            getLog().error("getEntityInstanceByBasePK: PK was null");
        }
        
        if(CoreDebugFlags.LogEntityInstanceResolution) {
            getLog().info("<<< entityInstance=" + entityInstance);
        }
        
        return entityInstance;
    }
    
    @Override
    public EntityInstance getEntityInstanceByBasePK(BasePK pk) {
        return getEntityInstanceByBasePK(pk, EntityPermission.READ_ONLY);
    }
    
    public EntityInstance getEntityInstanceByBasePKForUpdate(BasePK pk) {
        return getEntityInstanceByBasePK(pk, EntityPermission.READ_WRITE);
    }
    
    /** This function handles data passed back from a client. Because of this, missing entity instances
     * are not automatically created if they do not exist. Do not trust what the user is telling us.
     */
    private EntityInstance getEntityInstanceByEntityRef(String entityRef, EntityPermission entityPermission) {
        EntityInstance entityInstance = null;
        
        if(entityRef != null) {
            String[] entityRefParts = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(entityRef).toArray(new String[0]);
            
            if(entityRefParts.length == 3) {
                String componentVendorName = entityRefParts[0];
                ComponentVendor componentVendor = getComponentVendorByNameFromCache(componentVendorName);
                
                if(componentVendor != null) {
                    String entityTypeName = entityRefParts[1];
                    EntityType entityType = getEntityTypeByNameFromCache(componentVendor, entityTypeName);
                    
                    if(entityType != null) {
                        Long entityUniqueId = Long.valueOf(entityRefParts[2]);
                        
                        entityInstance = getEntityInstance(entityType, entityUniqueId, entityPermission);
                        
                        if(CoreDebugFlags.LogUnresolvedEntityInstances && entityInstance == null) {
                            getLog().error("getEntityInstanceByEntityRef: unknown entityUniqueId \"" + componentVendorName + "." + entityTypeName + "." + entityUniqueId + "\"");
                        }
                    } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                        getLog().error("getEntityInstanceByEntityRef: unknown entityTypeName \"" + componentVendorName + "." + entityTypeName + "\"");
                    }
                } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                    getLog().error("getEntityInstanceByEntityRef: unknown componentVendorName \"" + componentVendorName + "\"");
                }
            } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                getLog().error("getEntityInstanceByEntityRef: entityRef not valid");
            }
        } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
            getLog().error("getEntityInstanceByEntityRef: entityRef was null");
        }
        
        return entityInstance;
    }
    
    public EntityInstance getEntityInstanceByEntityRef(String entityRef) {
        return getEntityInstanceByEntityRef(entityRef, EntityPermission.READ_ONLY);
    }
    
    public EntityInstance getEntityInstanceByEntityRefForUpdate(String entityRef) {
        return getEntityInstanceByEntityRef(entityRef, EntityPermission.READ_WRITE);
    }

    public EntityInstance getEntityInstanceByPK(EntityInstancePK entityInstancePK) {
        return EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, entityInstancePK);
    }

    /** This function is a little odd. It doesn't actually delete the Entity Instance, rather, it cleans up all the
     * entities scattered through several components that depend on them.
     */
    public void deleteEntityInstanceDependencies(EntityInstance entityInstance, BasePK deletedBy) {
        ChainControl chainControl = ((ChainControl)Session.getModelController(ChainControl.class));
        SearchControl searchControl = ((SearchControl)Session.getModelController(SearchControl.class));
        var securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);

        ((AccountingControl)Session.getModelController(AccountingControl.class)).deleteTransactionEntityRolesByEntityInstance(entityInstance, deletedBy);
        ((AssociateControl)Session.getModelController(AssociateControl.class)).deleteAssociateReferralsByTargetEntityInstance(entityInstance, deletedBy);
        ((BatchControl)Session.getModelController(BatchControl.class)).deleteBatchEntitiesByEntityInstance(entityInstance, deletedBy);
        ((CommentControl)Session.getModelController(CommentControl.class)).deleteCommentsByEntityInstance(entityInstance, deletedBy);
        ((MessageControl)Session.getModelController(MessageControl.class)).deleteEntityMessagesByEntityInstance(entityInstance, deletedBy);
        ((RatingControl)Session.getModelController(RatingControl.class)).deleteRatingsByEntityInstance(entityInstance, deletedBy);
        searchControl.removeSearchResultsByEntityInstance(entityInstance);
        searchControl.removeCachedExecutedSearchResultsByEntityInstance(entityInstance);
        searchControl.deleteSearchResultActionsByEntityInstance(entityInstance, deletedBy);
        securityControl.deletePartyEntitySecurityRolesByEntityInstance(entityInstance, deletedBy);
        ((TagControl)Session.getModelController(TagControl.class)).deleteEntityTagsByEntityInstance(entityInstance, deletedBy);
        getWorkflowControl().deleteWorkflowEntityStatusesByEntityInstance(entityInstance, deletedBy);
        ((WorkEffortControl)Session.getModelController(WorkEffortControl.class)).deleteWorkEffortsByOwningEntityInstance(entityInstance, deletedBy);

        // If an EntityInstance is in a role for a ChainInstance, then that ChainInstance should be deleted. Because an individual
        // EntityInstance may be in more than one role, the list of ChainInstances needs to be deduplicated.
        Set<ChainInstance> chainInstances = new HashSet<>();
        chainControl.getChainInstanceEntityRolesByEntityInstanceForUpdate(entityInstance).stream().forEach((chainInstanceEntityRole) -> {
            chainInstances.add(chainInstanceEntityRole.getChainInstanceForUpdate());
        });
        chainControl.deleteChainInstances(chainInstances, deletedBy);

        deleteEntityAttributesByEntityInstance(entityInstance, deletedBy);
    }
    
    public void removeEntityInstance(EntityInstance entityInstance) {
        entityInstance.remove();
    }
    
    public void removeEntityInstanceByBasePK(BasePK pk) {
        removeEntityInstance(getEntityInstanceByBasePKForUpdate(pk));
    }
    
    public void removeEntityInstanceByEntityRef(String entityRef) {
        removeEntityInstance(getEntityInstanceByEntityRefForUpdate(entityRef));
    }
    
    // --------------------------------------------------------------------------------
    //   Event Types
    // --------------------------------------------------------------------------------
    
    public EventType createEventType(String eventTypeName, Boolean updateCreatedTime, Boolean updateModifiedTime, Boolean updateDeletedTime,
            Boolean updateVisitedTime, Boolean queueToSubscribers, Boolean keepHistory, Integer maximumHistory) {
        EventType eventType = EventTypeFactory.getInstance().create(eventTypeName, updateCreatedTime, updateModifiedTime, updateDeletedTime,
                updateVisitedTime, queueToSubscribers, keepHistory, maximumHistory);
        
        return eventType;
    }
    
    public EventType getEventTypeByName(String eventTypeName) {
        EventType eventType = null;
        
        try {
            PreparedStatement ps = EventTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM eventtypes " +
                    "WHERE evty_eventtypename = ?");
            
            ps.setString(1, eventTypeName);
            
            eventType = EventTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventType;
    }
    
    public List<EventType> getEventTypes() {
        PreparedStatement ps = EventTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM eventtypes " +
                "ORDER BY evty_eventtypename");
        
        return EventTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public EventTypeTransfer getEventTypeTransfer(UserVisit userVisit, EventType eventType) {
        return getCoreTransferCaches(userVisit).getEventTypeTransferCache().getEventTypeTransfer(eventType);
    }
    
    // --------------------------------------------------------------------------------
    //   Event Type Descriptions
    // --------------------------------------------------------------------------------
    
    public EventTypeDescription createEventTypeDescription(EventType eventType, Language language, String description) {
        EventTypeDescription eventTypeDescription = EventTypeDescriptionFactory.getInstance().create(eventType, language, description);
        
        return eventTypeDescription;
    }
    
    public EventTypeDescription getEventTypeDescription(EventType eventType, Language language) {
        EventTypeDescription eventTypeDescription = null;
        
        try {
            PreparedStatement ps = EventTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM eventtypedescriptions " +
                    "WHERE evtyd_evty_eventtypeid = ? AND evtyd_lang_languageid = ?");
            
            ps.setLong(1, eventType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            eventTypeDescription = EventTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventTypeDescription;
    }
    
    public String getBestEventTypeDescription(EventType eventType, Language language) {
        String description;
        EventTypeDescription eventTypeDescription = getEventTypeDescription(eventType, language);
        
        if(eventTypeDescription == null && !language.getIsDefault()) {
            eventTypeDescription = getEventTypeDescription(eventType, getPartyControl().getDefaultLanguage());
        }
        
        if(eventTypeDescription == null) {
            description = eventType.getEventTypeName();
        } else {
            description = eventTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Component Stages
    // --------------------------------------------------------------------------------
    
    public ComponentStage createComponentStage(String componentStageName, String description, Integer relativeAge) {
        ComponentStage componentStage = ComponentStageFactory.getInstance().create(componentStageName, description, relativeAge);
        
        return componentStage;
    }
    
    public ComponentStage getComponentStageByName(String componentStageName) {
        ComponentStage componentStage = null;
        
        try {
            PreparedStatement ps = ComponentStageFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM componentstages " +
                    "WHERE cstg_componentstagename = ?");
            
            ps.setString(1, componentStageName);
            
            componentStage = ComponentStageFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return componentStage;
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Types
    // --------------------------------------------------------------------------------
    
    public EntityAttributeType createEntityAttributeType(String entityAttributeTypeName) {
        EntityAttributeType entityAttributeType = EntityAttributeTypeFactory.getInstance().create(entityAttributeTypeName);
        
        return entityAttributeType;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.EntityAttributeType */
    public EntityAttributeType getEntityAttributeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityAttributeTypePK pk = new EntityAttributeTypePK(entityInstance.getEntityUniqueId());
        EntityAttributeType entityAttributeType = EntityAttributeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return entityAttributeType;
    }

    public EntityAttributeType getEntityAttributeTypeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttributeType getEntityAttributeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeType getEntityAttributeTypeByName(String entityAttributeTypeName) {
        EntityAttributeType entityAttributeType = null;
        
        try {
            PreparedStatement ps = EntityAttributeTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityattributetypes " +
                    "WHERE enat_entityattributetypename = ?");
            
            ps.setString(1, entityAttributeTypeName);
            
            entityAttributeType = EntityAttributeTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeType;
    }
    
    public List<EntityAttributeType> getEntityAttributeTypes() {
        PreparedStatement ps = EntityAttributeTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM entityattributetypes " +
                "ORDER BY enat_entityattributetypename");
        
        return EntityAttributeTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public EntityAttributeTypeTransfer getEntityAttributeTypeTransfer(UserVisit userVisit, EntityAttributeType entityAttributeType) {
        return getCoreTransferCaches(userVisit).getEntityAttributeTypeTransferCache().getEntityAttributeTypeTransfer(entityAttributeType);
    }
    
    public List<EntityAttributeTypeTransfer> getEntityAttributeTypeTransfers(UserVisit userVisit, Collection<EntityAttributeType> entityAttributeTypes) {
        List<EntityAttributeTypeTransfer> entityAttributeTypeTransfers = null;
        
        if(entityAttributeTypes != null) {
            entityAttributeTypeTransfers = new ArrayList<>(entityAttributeTypes.size());
            
            for(EntityAttributeType entityAttributeType: entityAttributeTypes) {
                entityAttributeTypeTransfers.add(getCoreTransferCaches(userVisit).getEntityAttributeTypeTransferCache().getEntityAttributeTypeTransfer(entityAttributeType));
            }
        }
        
        return entityAttributeTypeTransfers;
    }
    
    public List<EntityAttributeTypeTransfer> getEntityAttributeTypeTransfers(UserVisit userVisit) {
        return getEntityAttributeTypeTransfers(userVisit, getEntityAttributeTypes());
    }
    
    public EntityAttributeTypeChoicesBean getEntityAttributeTypeChoices(String defaultEntityAttributeTypeChoice, Language language) {
        List<EntityAttributeType> entityAttributeTypes = getEntityAttributeTypes();
        int size = entityAttributeTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        for(EntityAttributeType entityAttributeType: entityAttributeTypes) {
            String label = getBestEntityAttributeTypeDescription(entityAttributeType, language);
            String value = entityAttributeType.getEntityAttributeTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultEntityAttributeTypeChoice == null? false: defaultEntityAttributeTypeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new EntityAttributeTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityAttributeTypeDescription createEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language, String description) {
        EntityAttributeTypeDescription entityAttributeTypeDescription = EntityAttributeTypeDescriptionFactory.getInstance().create(entityAttributeType, language, description);
        
        return entityAttributeTypeDescription;
    }
    
    public EntityAttributeTypeDescription getEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language) {
        EntityAttributeTypeDescription entityAttributeTypeDescription = null;
        
        try {
            PreparedStatement ps = EntityAttributeTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityattributetypedescriptions " +
                    "WHERE enatd_enat_entityattributetypeid = ? AND enatd_lang_languageid = ?");
            
            ps.setLong(1, entityAttributeType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            entityAttributeTypeDescription = EntityAttributeTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeTypeDescription;
    }
    
    public String getBestEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language) {
        String description;
        EntityAttributeTypeDescription entityAttributeTypeDescription = getEntityAttributeTypeDescription(entityAttributeType, language);
        
        if(entityAttributeTypeDescription == null && !language.getIsDefault()) {
            entityAttributeTypeDescription = getEntityAttributeTypeDescription(entityAttributeType, getPartyControl().getDefaultLanguage());
        }
        
        if(entityAttributeTypeDescription == null) {
            description = entityAttributeType.getEntityAttributeTypeName();
        } else {
            description = entityAttributeTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Times
    // --------------------------------------------------------------------------------
    
    public EntityTime createEntityTime(EntityInstance entityInstance, Long createdTime, Long modifiedTime, Long deletedTime) {
        return EntityTimeFactory.getInstance().create(entityInstance, createdTime, modifiedTime, deletedTime);
        
    }
    
    public EntityTime getEntityTime(EntityInstance entityInstance) {
        EntityTime entityTime = null;
        
        try {
            PreparedStatement ps = EntityTimeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimes " +
                    "WHERE etim_eni_entityinstanceid = ?");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            
            entityTime = EntityTimeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTime;
    }
    
    public EntityTime getEntityTimeForUpdate(EntityInstance entityInstance) {
        EntityTime entityTime = null;
        
        try {
            PreparedStatement ps = EntityTimeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimes " +
                    "WHERE etim_eni_entityinstanceid = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            
            entityTime = EntityTimeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTime;
    }
    
    public static final Integer negativeOne = -1;
    
    public static final String selectEntityTimesByEntityType = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "ORDER BY etim_createdtime";
    
    public static final String selectEntityTimesByEntityTypeWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "ORDER BY etim_createdtime " +
            "LIMIT ?";
    
    public List<EntityTime> getEntityTimesByEntityType(EntityType entityType) {
        return getEntityTimesByEntityTypeWithLimit(entityType, negativeOne);
    }
    
    public List<EntityTime> getEntityTimesByEntityTypeWithLimit(EntityType entityType, Integer limit) {
        List<EntityTime> entityTimes = null;
        
        try {
            int intLimit = limit;
            PreparedStatement ps = EntityTimeFactory.getInstance().prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityType: selectEntityTimesByEntityTypeWithLimit);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            if(intLimit != -1) {
                ps.setInt(2, intLimit);
            }
            
            entityTimes = EntityTimeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimes;
    }
    
    public static final String selectEntityTimesByEntityTypeCreatedAfter = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_createdtime IS NOT NULL AND etim_createdtime > ? " +
            "ORDER BY etim_createdtime";
    public static final String selectEntityTimesByEntityTypeCreatedAfterWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_createdtime IS NOT NULL AND etim_createdtime > ? " +
            "ORDER BY etim_createdtime " +
            "LIMIT ?";
    
    public List<EntityTime> getEntityTimesByEntityTypeCreatedAfter(EntityType entityType, Long createdTime) {
        return getEntityTimesByEntityTypeCreatedAfterWithLimit(entityType, createdTime, negativeOne);
    }
    
    public List<EntityTime> getEntityTimesByEntityTypeCreatedAfterWithLimit(EntityType entityType, Long createdTime, Integer limit) {
        List<EntityTime> entityTimes = null;
        
        try {
            int intLimit = limit;
            PreparedStatement ps = EntityTimeFactory.getInstance().prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityTypeCreatedAfter: selectEntityTimesByEntityTypeCreatedAfterWithLimit);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, createdTime);
            if(intLimit != -1) {
                ps.setInt(3, intLimit);
            }
            
            entityTimes = EntityTimeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimes;
    }
    
    public static final String selectEntityTimesByEntityTypeModifiedAfter = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_modifiedtime IS NOT NULL AND etim_modifiedtime > ? " +
            "ORDER BY etim_modifiedtime";
    public static final String selectEntityTimesByEntityTypeModifiedAfterWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_modifiedtime IS NOT NULL AND etim_modifiedtime > ? " +
            "ORDER BY etim_modifiedtime " +
            "LIMIT ?";
    
    public List<EntityTime> getEntityTimesByEntityTypeModifiedAfter(EntityType entityType, Long modifiedTime) {
        return getEntityTimesByEntityTypeModifiedAfterWithLimit(entityType, modifiedTime, negativeOne);
    }
    
    public List<EntityTime> getEntityTimesByEntityTypeModifiedAfterWithLimit(EntityType entityType, Long modifiedTime, Integer limit) {
        List<EntityTime> entityTimes = null;
        
        try {
            int intLimit = limit;
            PreparedStatement ps = EntityTimeFactory.getInstance().prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityTypeModifiedAfter: selectEntityTimesByEntityTypeModifiedAfterWithLimit);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, modifiedTime);
            if(intLimit != -1) {
                ps.setInt(3, intLimit);
            }
            
            entityTimes = EntityTimeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimes;
    }
    
    public static final String selectEntityTimesByEntityTypeDeletedAfter = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_deletedtime IS NOT NULL AND etim_deletedtime > ? " +
            "ORDER BY etim_deletedtime";
    public static final String selectEntityTimesByEntityTypeDeletedAfterWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_deletedtime IS NOT NULL AND etim_deletedtime > ? " +
            "ORDER BY etim_deletedtime " +
            "LIMIT ?";
    
    public List<EntityTime> getEntityTimesByEntityTypeDeletedAfter(EntityType entityType, Long deletedTime) {
        return getEntityTimesByEntityTypeDeletedAfterWithLimit(entityType, deletedTime, negativeOne);
    }
    
    public List<EntityTime> getEntityTimesByEntityTypeDeletedAfterWithLimit(EntityType entityType, Long deletedTime, Integer limit) {
        List<EntityTime> entityTimes = null;
        
        try {
            int intLimit = limit;
            PreparedStatement ps = EntityTimeFactory.getInstance().prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityTypeDeletedAfter: selectEntityTimesByEntityTypeDeletedAfterWithLimit);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, deletedTime);
            if(intLimit != -1) {
                ps.setInt(3, intLimit);
            }
            
            entityTimes = EntityTimeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimes;
    }
    
    public EntityTimeTransfer getEntityTimeTransfer(UserVisit userVisit, EntityTime entityTime) {
        return getCoreTransferCaches(userVisit).getEntityTimeTransferCache().getEntityTimeTransfer(entityTime);
    }
    
    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------
    
    boolean alreadyCreatingEventGroup = false;
    
    public EventGroup getActiveEventGroup(BasePK createdBy) {
        EventGroup eventGroup = null;
        
        if(!alreadyCreatingEventGroup) {
            WorkflowStep workflowStep = getWorkflowControl().getWorkflowStepUsingNames(Workflow_EVENT_GROUP_STATUS,
                    WorkflowStep_EVENT_GROUP_STATUS_ACTIVE);

            if(workflowStep != null) {
                List<EventGroup> eventGroups = null;

                try {
                    PreparedStatement ps = EventGroupFactory.getInstance().prepareStatement(
                            "SELECT _ALL_ " +
                            "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, " +
                            "eventgroups, eventgroupdetails, workflowentitystatuses, entitytimes " +
                            "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid " +
                            "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                            "AND ent_activedetailid = entdt_entitytypedetailid " +
                            "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                            "AND entdt_entitytypename = ? " +
                            "AND ent_entitytypeid = eni_ent_entitytypeid AND evgrp_eventgroupid = eni_entityuniqueid " +
                            "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                            "AND eni_entityinstanceid = etim_eni_entityinstanceid " +
                            "ORDER BY etim_createdtime DESC");

                    ps.setString(1, ComponentVendors.ECHOTHREE.name());
                    ps.setString(2, EntityTypes.EventGroup.name());
                    ps.setLong(3, workflowStep.getPrimaryKey().getEntityId());
                    ps.setLong(4, Session.MAX_TIME);

                    eventGroups = EventGroupFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }

                if(eventGroups != null && !eventGroups.isEmpty()) {
                    eventGroup = eventGroups.iterator().next();
                } else {
                    alreadyCreatingEventGroup = true;
                    eventGroup = createEventGroup(createdBy);
                    alreadyCreatingEventGroup = false;
                }
            }
        }
        
        return eventGroup;
    }
    
    public EventGroup createEventGroup(BasePK createdBy) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        var workflowControl = getWorkflowControl();
        EventGroup eventGroup = null;
        Workflow workflow = workflowControl.getWorkflowByName(Workflow_EVENT_GROUP_STATUS);
        
        if(workflow != null) {
            WorkflowEntrance workflowEntrance = workflowControl.getDefaultWorkflowEntrance(workflow);
            
            if(workflowEntrance != null && (workflowControl.countWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance) > 0)) {
                Sequence sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.EVENT_GROUP.name());
                String eventGroupName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
                
                eventGroup = createEventGroup(eventGroupName, createdBy);

                EntityInstance entityInstance = getEntityInstanceByBaseEntity(eventGroup);
                getWorkflowControl().addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
            }
        }
        
        return eventGroup;
    }
    
    public EventGroup createEventGroup(String eventGroupName, BasePK createdBy) {
        EventGroup eventGroup = EventGroupFactory.getInstance().create();
        EventGroupDetail eventGroupDetail = EventGroupDetailFactory.getInstance().create(eventGroup, eventGroupName,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        eventGroup = EventGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                eventGroup.getPrimaryKey());
        eventGroup.setActiveDetail(eventGroupDetail);
        eventGroup.setLastDetail(eventGroupDetail);
        eventGroup.store();
        
        sendEventUsingNames(eventGroup.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return eventGroup;
    }
    
    public EventGroupDetailValue getEventGroupDetailValueForUpdate(EventGroup eventGroup) {
        return eventGroup.getLastDetailForUpdate().getEventGroupDetailValue().clone();
    }
    
    private EventGroup getEventGroupByName(String eventGroupName, EntityPermission entityPermission) {
        EventGroup eventGroup = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventgroups, eventgroupdetails " +
                        "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid AND evgrpdt_eventgroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventgroups, eventgroupdetails " +
                        "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid AND evgrpdt_eventgroupname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EventGroupFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, eventGroupName);
            
            eventGroup = EventGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventGroup;
    }
    
    public EventGroup getEventGroupByName(String eventGroupName) {
        return getEventGroupByName(eventGroupName, EntityPermission.READ_ONLY);
    }
    
    public EventGroup getEventGroupByNameForUpdate(String eventGroupName) {
        return getEventGroupByName(eventGroupName, EntityPermission.READ_WRITE);
    }
    
    public EventGroupDetailValue getEventGroupDetailValueByNameForUpdate(String eventGroupName) {
        return getEventGroupDetailValueForUpdate(getEventGroupByNameForUpdate(eventGroupName));
    }
    
    private List<EventGroup> getEventGroups(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM eventgroups, eventgroupdetails " +
                    "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid " +
                    "ORDER BY evgrpdt_eventgroupname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM eventgroups, eventgroupdetails " +
                    "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = EventGroupFactory.getInstance().prepareStatement(query);
        
        return EventGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<EventGroup> getEventGroups() {
        return getEventGroups(EntityPermission.READ_ONLY);
    }
    
    public List<EventGroup> getEventGroupsForUpdate() {
        return getEventGroups(EntityPermission.READ_WRITE);
    }
    
    public EventGroupTransfer getEventGroupTransfer(UserVisit userVisit, EventGroup eventGroup) {
        return getCoreTransferCaches(userVisit).getEventGroupTransferCache().getEventGroupTransfer(eventGroup);
    }
    
    public List<EventGroupTransfer> getEventGroupTransfers(UserVisit userVisit, List<EventGroup> eventGroups) {
        List<EventGroupTransfer> eventGroupTransfers = new ArrayList<>(eventGroups.size());
        EventGroupTransferCache eventGroupTransferCache = getCoreTransferCaches(userVisit).getEventGroupTransferCache();
        
        eventGroups.stream().forEach((eventGroup) -> {
            eventGroupTransfers.add(eventGroupTransferCache.getEventGroupTransfer(eventGroup));
        });
        
        return eventGroupTransfers;
    }
    
    public List<EventGroupTransfer> getEventGroupTransfers(UserVisit userVisit) {
        return getEventGroupTransfers(userVisit, getEventGroups());
    }
    
    public EventGroupStatusChoicesBean getEventGroupStatusChoices(String defaultEventGroupStatusChoice, Language language, boolean allowNullChoice,
            EventGroup eventGroup, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        EventGroupStatusChoicesBean eventGroupStatusChoicesBean = new EventGroupStatusChoicesBean();
        
        if(eventGroup == null) {
            workflowControl.getWorkflowEntranceChoices(eventGroupStatusChoicesBean, defaultEventGroupStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_EVENT_GROUP_STATUS), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(eventGroup.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_EVENT_GROUP_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(eventGroupStatusChoicesBean, defaultEventGroupStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return eventGroupStatusChoicesBean;
    }
    
    public void setEventGroupStatus(ExecutionErrorAccumulator eea, EventGroup eventGroup, String eventGroupStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(eventGroup);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(Workflow_EVENT_GROUP_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = eventGroupStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), eventGroupStatusChoice);
        
        if(workflowDestination != null || eventGroupStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEventGroupStatusChoice.name(), eventGroupStatusChoice);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Events
    // --------------------------------------------------------------------------------
    
    public Event createEvent(EventGroup eventGroup, Long eventTime, EntityInstance entityInstance, EventType eventType,
            EntityInstance relatedEntityInstance, EventType relatedEventType, EntityInstance createdBy) {
        Integer eventTimeSequence = session.getNextEventTimeSequence(entityInstance.getPrimaryKey());

        return EventFactory.getInstance().create(eventGroup, eventTime, eventTimeSequence, entityInstance, eventType, relatedEntityInstance, relatedEventType,
                createdBy);
    }
    
    public long countEventsByEventGroup(EventGroup eventGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM events " +
                "WHERE ev_evgrp_eventgroupid = ?",
                eventGroup);
    }

    public long countEventsByEntityInstance(EntityInstance entityInstance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM events " +
                "WHERE ev_eni_entityinstanceid = ?",
                entityInstance);
    }

    public long countEventsByEventType(EventType eventType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM events " +
                "WHERE ev_evty_eventtypeid = ?",
                eventType);
    }

    public long countEventsByCreatedBy(EntityInstance createdBy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM events " +
                "WHERE ev_createdbyid = ?",
                createdBy);
    }

    private static final Map<EntityPermission, String> getEventsByEventGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM events "
                + "WHERE ev_evgrp_eventgroupid = ? "
                + "ORDER BY ev_eventtime, ev_eventtimesequence "
                + "_LIMIT_");
        getEventsByEventGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEventGroup(EventGroup eventGroup) {
        return EventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByEventGroupQueries,
                eventGroup);
    }
    
    private static final Map<EntityPermission, String> getEventsByEntityInstanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM events "
                + "WHERE ev_eni_entityinstanceid = ? "
                + "ORDER BY ev_eventtime, ev_eventtimesequence "
                + "_LIMIT_");
        getEventsByEntityInstanceQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEntityInstance(EntityInstance entityInstance) {
        return EventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByEntityInstanceQueries,
                entityInstance);
    }
    
    private static final Map<EntityPermission, String> getEventsByEventTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM events "
                + "WHERE ev_eni_entityinstanceid = ? "
                + "ORDER BY ev_eventtime, ev_eventtimesequence "
                + "_LIMIT_");
        getEventsByEventTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEventType(EventType eventType) {
        return EventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByEventTypeQueries,
                eventType);
    }
    
    private static final Map<EntityPermission, String> getEventsByCreatedByQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM events "
                + "WHERE ev_createdbyid = ? "
                + "ORDER BY ev_eventtime, ev_eventtimesequence "
                + "_LIMIT_");
        getEventsByCreatedByQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByCreatedBy(EntityInstance createdBy) {
        return EventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByCreatedByQueries,
                createdBy);
    }
    
    private static final Map<EntityPermission, String> getEventsByEntityInstanceAndEventTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM events "
                + "WHERE ev_eni_entityinstanceid = ? AND ev_evty_eventtypeid = ? "
                + "ORDER BY ev_eventtime "
                + "FOR UPDATE");
        getEventsByEntityInstanceAndEventTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEntityInstanceAndEventTypeForUpdate(EntityInstance entityInstance, EventType eventType) {
        return EventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, getEventsByEntityInstanceAndEventTypeQueries,
                entityInstance, eventType);
    }
    
    public List<EventTransfer> getEventTransfers(UserVisit userVisit, List<Event> events) {
        List<EventTransfer> eventTransfers = new ArrayList<>(events.size());
        EventTransferCache eventTransferCache = getCoreTransferCaches(userVisit).getEventTransferCache();
        
        events.stream().forEach((event) -> {
            eventTransfers.add(eventTransferCache.getEventTransfer(event));
        });
        
        return eventTransfers;
    }
    
    public List<EventTransfer> getEventTransfersByEntityInstance(UserVisit userVisit, EventGroup eventGroup) {
        return getEventTransfers(userVisit, getEventsByEventGroup(eventGroup));
    }
    
    public List<EventTransfer> getEventTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
        return getEventTransfers(userVisit, getEventsByEntityInstance(entityInstance));
    }
    
    public List<EventTransfer> getEventTransfersByEntityInstance(UserVisit userVisit, EventType eventType) {
        return getEventTransfers(userVisit, getEventsByEventType(eventType));
    }
    
    public List<EventTransfer> getEventTransfersByCreatedBy(UserVisit userVisit, EntityInstance createdBy) {
        return getEventTransfers(userVisit, getEventsByCreatedBy(createdBy));
    }
    
    public void removeEvent(Event event) {
        event.remove();
    }
    
    // --------------------------------------------------------------------------------
    //   Queued Events
    // --------------------------------------------------------------------------------
    
    public QueuedEvent createQueuedEvent(Event event) {
        return QueuedEventFactory.getInstance().create(event);
    }
    
    public List<QueuedEvent> getQueuedEventsForUpdate() {
        PreparedStatement ps = QueuedEventFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM queuedevents " +
                "FOR UPDATE");

        return QueuedEventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
    }
    
    public void removeQueuedEvent(QueuedEvent queuedEvent) {
        queuedEvent.remove();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Visits
    // --------------------------------------------------------------------------------
    
    public EntityVisit createEntityVisit(EntityInstance entityInstance, EntityInstance visitedEntityInstance) {
        return EntityVisitFactory.getInstance().create(entityInstance, visitedEntityInstance, session.START_TIME_LONG);
    }
    
    public EntityVisit getEntityVisit(EntityInstance entityInstance, EntityInstance visitedEntityInstance) {
        EntityVisit entityVisit = null;
        
        try {
            PreparedStatement ps = EntityVisitFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityvisits " +
                    "WHERE evis_eni_entityinstanceid = ? AND evis_visitedentityinstanceid = ?");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, visitedEntityInstance.getPrimaryKey().getEntityId());
            
            entityVisit = EntityVisitFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityVisit;
    }
    
    public EntityVisit getEntityVisitForUpdate(EntityInstance entityInstance, EntityInstance visitedEntityInstance) {
        EntityVisit entityVisit = null;
        
        try {
            PreparedStatement ps = EntityVisitFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityvisits " +
                    "WHERE evis_eni_entityinstanceid = ? AND evis_visitedentityinstanceid = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, visitedEntityInstance.getPrimaryKey().getEntityId());
            
            entityVisit = EntityVisitFactory.getInstance().getEntityFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityVisit;
    }
    
    // --------------------------------------------------------------------------------
    //   Cache Entries
    // --------------------------------------------------------------------------------

    public CacheEntry createCacheEntry(String cacheEntryKey, MimeType mimeType, Long createdTime, Long validUntilTime, String clob, ByteArray blob,
            Set<String> entityRefs) {
        CacheEntry cacheEntry = createCacheEntry(cacheEntryKey, mimeType, createdTime, validUntilTime);
        String entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            createCacheClobEntry(cacheEntry, clob);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            createCacheBlobEntry(cacheEntry, blob);
        }

        if(entityRefs != null) {
            createCacheEntryDependencies(cacheEntry, entityRefs);
        }

        return cacheEntry;
    }

    public CacheEntry createCacheEntry(String cacheEntryKey, MimeType mimeType, Long createdTime, Long validUntilTime) {
        return CacheEntryFactory.getInstance().create(cacheEntryKey, mimeType, createdTime, validUntilTime);
    }

    public long countCacheEntries() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM cacheentries");
    }

    private static final Map<EntityPermission, String> getCacheEntryByCacheEntryKeyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cacheentries " +
                "WHERE cent_cacheentrykey = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cacheentries " +
                "WHERE cent_cacheentrykey = ? " +
                "FOR UPDATE");
        getCacheEntryByCacheEntryKeyQueries = Collections.unmodifiableMap(queryMap);
    }

    private CacheEntry getCacheEntryByCacheEntryKey(String cacheEntryKey, EntityPermission entityPermission) {
        return CacheEntryFactory.getInstance().getEntityFromQuery(entityPermission, getCacheEntryByCacheEntryKeyQueries,
                cacheEntryKey);
    }

    public CacheEntry getCacheEntryByCacheEntryKey(String cacheEntryKey) {
        return getCacheEntryByCacheEntryKey(cacheEntryKey, EntityPermission.READ_ONLY);
    }

    public CacheEntry getCacheEntryByCacheEntryKeyForUpdate(String cacheEntryKey) {
        return getCacheEntryByCacheEntryKey(cacheEntryKey, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCacheEntriesByCacheEntryKeyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cacheentries " +
                "ORDER BY cent_cacheentrykey " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cacheentries " +
                "FOR UPDATE");
        getCacheEntriesByCacheEntryKeyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CacheEntry> getCacheEntries(EntityPermission entityPermission) {
        return CacheEntryFactory.getInstance().getEntitiesFromQuery(entityPermission, getCacheEntriesByCacheEntryKeyQueries);
    }

    public List<CacheEntry> getCacheEntries() {
        return getCacheEntries(EntityPermission.READ_ONLY);
    }

    public List<CacheEntry> getCacheEntriesForUpdate() {
        return getCacheEntries(EntityPermission.READ_WRITE);
    }

    public CacheEntryTransfer getCacheEntryTransfer(UserVisit userVisit, CacheEntry cacheEntry) {
        return getCoreTransferCaches(userVisit).getCacheEntryTransferCache().getCacheEntryTransfer(cacheEntry);
    }

    public CacheEntryTransfer getCacheEntryTransferByCacheEntryKey(UserVisit userVisit, String cacheEntryKey) {
        CacheEntry cacheEntry = getCacheEntryByCacheEntryKey(cacheEntryKey);
        CacheEntryTransfer cacheEntryTransfer = null;

        if(cacheEntry != null) {
            Long validUntilTime = cacheEntry.getValidUntilTime();
            
            if(validUntilTime != null && validUntilTime < session.START_TIME) {
                removeCacheEntry(cacheEntry);
            } else {
                cacheEntryTransfer = getCacheEntryTransfer(userVisit, cacheEntry);
            }
        }

        return cacheEntryTransfer;
    }

    public List<CacheEntryTransfer> getCacheEntryTransfers(UserVisit userVisit, List<CacheEntry> cacheEntries) {
        List<CacheEntryTransfer> cacheEntryTransfers = new ArrayList<>(cacheEntries.size());
        CacheEntryTransferCache cacheEntryTransferCache = getCoreTransferCaches(userVisit).getCacheEntryTransferCache();

        cacheEntries.stream().forEach((cacheEntry) -> {
            cacheEntryTransfers.add(cacheEntryTransferCache.getCacheEntryTransfer(cacheEntry));
        });

        return cacheEntryTransfers;
    }

    public List<CacheEntryTransfer> getCacheEntryTransfers(UserVisit userVisit) {
        return getCacheEntryTransfers(userVisit, getCacheEntries());
    }

    public void removeCacheEntry(CacheEntry cacheEntry) {
        cacheEntry.remove();
    }
    
    public void removeCacheEntries(List<CacheEntry> cacheEntries) {
        cacheEntries.stream().forEach((cacheEntry) -> {
            removeCacheEntry(cacheEntry);
        });
    }
    
    public void removeCacheEntries() {
        removeCacheEntries(getCacheEntriesForUpdate());
    }

    private List<CacheEntryPK> getCacheEntryPKsByEntityInstance(final EntityInstance entityInstance) {
        final CacheEntryFactory instance = CacheEntryFactory.getInstance();
        
        return instance.getPKsFromQueryAsList(
                instance.prepareStatement(
                        "SELECT _PK_ "
                        + "FROM cacheentrydependencies, cacheentries "
                        + "WHERE centd_eni_entityinstanceid = ? "
                        + "AND centd_cent_cacheentryid = cent_cacheentryid"),
                entityInstance);
    }

    public void removeCacheEntriesByEntityInstance(final EntityInstance entityInstance) {
        CacheEntryFactory.getInstance().remove(getCacheEntryPKsByEntityInstance(entityInstance));
    }

    // --------------------------------------------------------------------------------
    //   Cache Clob Entries
    // --------------------------------------------------------------------------------

    public CacheClobEntry createCacheClobEntry(CacheEntry cacheEntry, String clob) {
        return CacheClobEntryFactory.getInstance().create(cacheEntry, clob);
    }

    private static final Map<EntityPermission, String> getCacheClobEntryByCacheEntryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cacheclobentries " +
                "WHERE ccent_cent_cacheentryid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cacheclobentries " +
                "WHERE ccent_cent_cacheentryid = ? " +
                "FOR UPDATE");
        getCacheClobEntryByCacheEntryQueries = Collections.unmodifiableMap(queryMap);
    }

    private CacheClobEntry getCacheClobEntryByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        return CacheClobEntryFactory.getInstance().getEntityFromQuery(entityPermission, getCacheClobEntryByCacheEntryQueries,
                cacheEntry);
    }

    public CacheClobEntry getCacheClobEntryByCacheEntry(CacheEntry cacheEntry) {
        return getCacheClobEntryByCacheEntry(cacheEntry, EntityPermission.READ_ONLY);
    }

    public CacheClobEntry getCacheClobEntryByCacheEntryForUpdate(CacheEntry cacheEntry) {
        return getCacheClobEntryByCacheEntry(cacheEntry, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Cache Blob Entries
    // --------------------------------------------------------------------------------

    public CacheBlobEntry createCacheBlobEntry(CacheEntry cacheEntry, ByteArray blob) {
        return CacheBlobEntryFactory.getInstance().create(cacheEntry, blob);
    }

    private static final Map<EntityPermission, String> getCacheBlobEntryByCacheEntryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cacheblobentries " +
                "WHERE ccent_cent_cacheentryid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cacheblobentries " +
                "WHERE ccent_cent_cacheentryid = ? " +
                "FOR UPDATE");
        getCacheBlobEntryByCacheEntryQueries = Collections.unmodifiableMap(queryMap);
    }

    private CacheBlobEntry getCacheBlobEntryByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        return CacheBlobEntryFactory.getInstance().getEntityFromQuery(entityPermission, getCacheBlobEntryByCacheEntryQueries,
                cacheEntry);
    }

    public CacheBlobEntry getCacheBlobEntryByCacheEntry(CacheEntry cacheEntry) {
        return getCacheBlobEntryByCacheEntry(cacheEntry, EntityPermission.READ_ONLY);
    }

    public CacheBlobEntry getCacheBlobEntryByCacheEntryForUpdate(CacheEntry cacheEntry) {
        return getCacheBlobEntryByCacheEntry(cacheEntry, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // --------------------------------------------------------------------------------
    
    public void createCacheEntryDependencies(CacheEntry cacheEntry, Set<String> entityRefs) {
        List<CacheEntryDependencyValue> cacheEntryDependencyValues = new ArrayList<>(entityRefs.size());

        for(String entityRef : entityRefs) {
            EntityInstance entityInstance = getEntityInstanceByEntityRef(entityRef);

            if(entityInstance != null) {
                cacheEntryDependencyValues.add(new CacheEntryDependencyValue(cacheEntry.getPrimaryKey(), entityInstance.getPrimaryKey()));
            }
        }
        
        CacheEntryDependencyFactory.getInstance().create(cacheEntryDependencyValues);
    }

    public CacheEntryDependency createCacheEntryDependency(CacheEntry cacheEntry, EntityInstance entityInstance) {
        return CacheEntryDependencyFactory.getInstance().create(cacheEntry, entityInstance);
    }

    private static final Map<EntityPermission, String> getCacheEntryDependenciesByEntityInstanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM cacheentrydependencies, cacheentries "
                + "WHERE centd_eni_entityinstanceid = ? "
                + "AND centd_cent_cacheentryid = cent_cacheentryid "
                + "ORDER BY cent_cacheentrykey");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cacheentrydependencies "
                + "WHERE centd_eni_entityinstanceid = ? "
                + "FOR UPDATE");
        getCacheEntryDependenciesByEntityInstanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        return CacheEntryDependencyFactory.getInstance().getEntitiesFromQuery(entityPermission, getCacheEntryDependenciesByEntityInstanceQueries,
                entityInstance);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstance(EntityInstance entityInstance) {
        return getCacheEntryDependenciesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCacheEntryDependenciesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCacheEntryDependenciesByCacheEntryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM cacheentrydependencies, entityinstances, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                + "WHERE centd_cent_cacheentryid = ? "
                + "AND centd_eni_entityinstanceid = eni_entityinstanceid "
                + "AND eni_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                + "ORDER BY cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename, eni_entityuniqueid");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cacheentrydependencies "
                + "WHERE centd_cent_cacheentryid = ? "
                + "FOR UPDATE");
        getCacheEntryDependenciesByCacheEntryQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        return CacheEntryDependencyFactory.getInstance().getEntitiesFromQuery(entityPermission, getCacheEntryDependenciesByCacheEntryQueries,
                cacheEntry);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntry(CacheEntry cacheEntry) {
        return getCacheEntryDependenciesByCacheEntry(cacheEntry, EntityPermission.READ_ONLY);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntryForUpdate(CacheEntry cacheEntry) {
        return getCacheEntryDependenciesByCacheEntry(cacheEntry, EntityPermission.READ_WRITE);
    }

    public CacheEntryDependencyTransfer getCacheEntryDependencyTransfer(UserVisit userVisit, CacheEntryDependency cacheEntryDependency) {
        return getCoreTransferCaches(userVisit).getCacheEntryDependencyTransferCache().getCacheEntryDependencyTransfer(cacheEntryDependency);
    }

    public List<CacheEntryDependencyTransfer> getCacheEntryDependencyTransfers(UserVisit userVisit, List<CacheEntryDependency> cacheEntries) {
        List<CacheEntryDependencyTransfer> cacheEntryDependencyTransfers = new ArrayList<>(cacheEntries.size());
        CacheEntryDependencyTransferCache cacheEntryDependencyTransferCache = getCoreTransferCaches(userVisit).getCacheEntryDependencyTransferCache();

        cacheEntries.stream().forEach((cacheEntryDependency) -> {
            cacheEntryDependencyTransfers.add(cacheEntryDependencyTransferCache.getCacheEntryDependencyTransfer(cacheEntryDependency));
        });

        return cacheEntryDependencyTransfers;
    }

    public List<CacheEntryDependencyTransfer> getCacheEntryDependencyTransfersByCacheEntry(UserVisit userVisit, CacheEntry cacheEntry) {
        return getCacheEntryDependencyTransfers(userVisit, getCacheEntryDependenciesByCacheEntry(cacheEntry));
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Groups
    // --------------------------------------------------------------------------------
    
    public EntityAttributeGroup createEntityAttributeGroup(String entityAttributeGroupName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        EntityAttributeGroup defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
        boolean defaultFound = defaultEntityAttributeGroup != null;
        
        if(defaultFound && isDefault) {
            EntityAttributeGroupDetailValue defaultEntityAttributeGroupDetailValue = getDefaultEntityAttributeGroupDetailValueForUpdate();
            
            defaultEntityAttributeGroupDetailValue.setIsDefault(Boolean.FALSE);
            updateEntityAttributeGroupFromValue(defaultEntityAttributeGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        EntityAttributeGroup entityAttributeGroup = EntityAttributeGroupFactory.getInstance().create();
        EntityAttributeGroupDetail entityAttributeGroupDetail = EntityAttributeGroupDetailFactory.getInstance().create(entityAttributeGroup,
                entityAttributeGroupName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                entityAttributeGroup.getPrimaryKey());
        entityAttributeGroup.setActiveDetail(entityAttributeGroupDetail);
        entityAttributeGroup.setLastDetail(entityAttributeGroupDetail);
        entityAttributeGroup.store();
        
        sendEventUsingNames(entityAttributeGroup.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return entityAttributeGroup;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.EntityAttributeGroup */
    public EntityAttributeGroup getEntityAttributeGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityAttributeGroupPK pk = new EntityAttributeGroupPK(entityInstance.getEntityUniqueId());
        EntityAttributeGroup entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return entityAttributeGroup;
    }

    public EntityAttributeGroup getEntityAttributeGroupByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getEntityAttributeGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttributeGroup> getEntityAttributeGroups(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid " +
                    "ORDER BY enagpdt_sortorder, enagpdt_entityattributegroupname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
        
        return EntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<EntityAttributeGroup> getEntityAttributeGroups() {
        return getEntityAttributeGroups(EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeGroup> getEntityAttributeGroupsForUpdate() {
        return getEntityAttributeGroups(EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttributeGroup> getEntityAttributeGroupsByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<EntityAttributeGroup> entityAttributeGroups = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributegroups, entityattributegroupdetails, entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "AND enagp_entityattributegroupid = enaenagp_enagp_entityattributegroupid AND enaenagp_ena_entityattributeid = ena_entityattributeid AND enaenagp_thrutime = ? "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "GROUP BY enagp_entityattributegroupid "
                        + "ORDER BY enagpdt_sortorder, enagpdt_entityattributegroupname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributegroups, entityattributegroupdetails, entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "AND enagp_entityattributegroupid = enaenagp_enagp_entityattributegroupid AND enaenagp_ena_entityattributeid = ena_entityattributeid AND enaenagp_thrutime = ? "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "GROUP BY enagp_entityattributegroupid "
                        + "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            ps.setLong(2, entityType.getPrimaryKey().getEntityId());
            
            entityAttributeGroups = EntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeGroups;
    }
    
    public List<EntityAttributeGroup> getEntityAttributeGroupsByEntityType(EntityType entityType) {
        return getEntityAttributeGroupsByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeGroup> getEntityAttributeGroupsByEntityTypeForUpdate(EntityType entityType) {
        return getEntityAttributeGroupsByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    private EntityAttributeGroup getDefaultEntityAttributeGroup(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
        
        return EntityAttributeGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public EntityAttributeGroup getDefaultEntityAttributeGroup() {
        return getDefaultEntityAttributeGroup(EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeGroup getDefaultEntityAttributeGroupForUpdate() {
        return getDefaultEntityAttributeGroup(EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeGroupDetailValue getDefaultEntityAttributeGroupDetailValueForUpdate() {
        return getDefaultEntityAttributeGroupForUpdate().getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();
    }
    
    private EntityAttributeGroup getEntityAttributeGroupByName(String entityAttributeGroupName, EntityPermission entityPermission) {
        EntityAttributeGroup entityAttributeGroup = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroups, entityattributegroupdetails " +
                        "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_entityattributegroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroups, entityattributegroupdetails " +
                        "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_entityattributegroupname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, entityAttributeGroupName);
            
            entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeGroup;
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByName(String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(entityAttributeGroupName, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByNameForUpdate(String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(entityAttributeGroupName, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeGroupDetailValue getEntityAttributeGroupDetailValueForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return entityAttributeGroup == null? null: entityAttributeGroup.getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();
    }
    
    public EntityAttributeGroupDetailValue getEntityAttributeGroupDetailValueByNameForUpdate(String entityAttributeGroupName) {
        return getEntityAttributeGroupDetailValueForUpdate(getEntityAttributeGroupByNameForUpdate(entityAttributeGroupName));
    }
    
    public EntityAttributeGroupChoicesBean getEntityAttributeGroupChoices(String defaultEntityAttributeGroupChoice, Language language,
            boolean allowNullChoice) {
        List<EntityAttributeGroup> entityAttributeGroups = getEntityAttributeGroups();
        int size = entityAttributeGroups.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityAttributeGroupChoice == null) {
                defaultValue = "";
            }
        }
        
        for(EntityAttributeGroup entityAttributeGroup: entityAttributeGroups) {
            EntityAttributeGroupDetail entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();
            String label = getBestEntityAttributeGroupDescription(entityAttributeGroup, language);
            String value = entityAttributeGroupDetail.getEntityAttributeGroupName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultEntityAttributeGroupChoice == null? false: defaultEntityAttributeGroupChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityAttributeGroupDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityAttributeGroupChoicesBean(labels, values, defaultValue);
    }
    
    public EntityAttributeGroupTransfer getEntityAttributeGroupTransfer(UserVisit userVisit, EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeGroupTransferCache().getEntityAttributeGroupTransfer(entityAttributeGroup, entityInstance);
    }
    
    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfers(UserVisit userVisit, List<EntityAttributeGroup> entityAttributeGroups, EntityInstance entityInstance) {
        List<EntityAttributeGroupTransfer> entityAttributeGroupTransfers = new ArrayList<>(entityAttributeGroups.size());
        EntityAttributeGroupTransferCache entityAttributeGroupTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeGroupTransferCache();
        
        entityAttributeGroups.stream().forEach((entityAttributeGroup) -> {
            entityAttributeGroupTransfers.add(entityAttributeGroupTransferCache.getEntityAttributeGroupTransfer(entityAttributeGroup, entityInstance));
        });
        
        return entityAttributeGroupTransfers;
    }
    
    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfers(UserVisit userVisit, EntityInstance entityInstance) {
        return getEntityAttributeGroupTransfers(userVisit, getEntityAttributeGroups(), entityInstance);
    }
    
    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfersByEntityType(UserVisit userVisit, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAttributeGroupTransfers(userVisit, getEntityAttributeGroupsByEntityType(entityType), entityInstance);
    }
    
    private void updateEntityAttributeGroupFromValue(EntityAttributeGroupDetailValue entityAttributeGroupDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityAttributeGroupDetailValue.hasBeenModified()) {
            EntityAttributeGroup entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeGroupDetailValue.getEntityAttributeGroupPK());
            EntityAttributeGroupDetail entityAttributeGroupDetail = entityAttributeGroup.getActiveDetailForUpdate();
            
            entityAttributeGroupDetail.setThruTime(session.START_TIME_LONG);
            entityAttributeGroupDetail.store();
            
            EntityAttributeGroupPK entityAttributeGroupPK = entityAttributeGroupDetail.getEntityAttributeGroupPK();
            String entityAttributeGroupName = entityAttributeGroupDetailValue.getEntityAttributeGroupName();
            Boolean isDefault = entityAttributeGroupDetailValue.getIsDefault();
            Integer sortOrder = entityAttributeGroupDetailValue.getSortOrder();
            
            if(checkDefault) {
                EntityAttributeGroup defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
                boolean defaultFound = defaultEntityAttributeGroup != null && !defaultEntityAttributeGroup.equals(entityAttributeGroup);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    EntityAttributeGroupDetailValue defaultEntityAttributeGroupDetailValue = getDefaultEntityAttributeGroupDetailValueForUpdate();
                    
                    defaultEntityAttributeGroupDetailValue.setIsDefault(Boolean.FALSE);
                    updateEntityAttributeGroupFromValue(defaultEntityAttributeGroupDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            entityAttributeGroupDetail = EntityAttributeGroupDetailFactory.getInstance().create(entityAttributeGroupPK, entityAttributeGroupName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            entityAttributeGroup.setActiveDetail(entityAttributeGroupDetail);
            entityAttributeGroup.setLastDetail(entityAttributeGroupDetail);
            
            sendEventUsingNames(entityAttributeGroupPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateEntityAttributeGroupFromValue(EntityAttributeGroupDetailValue entityAttributeGroupDetailValue, BasePK updatedBy) {
        updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, true, updatedBy);
    }
    
    public void deleteEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        deleteEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, deletedBy);
        deleteEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, deletedBy);
        
        EntityAttributeGroupDetail entityAttributeGroupDetail = entityAttributeGroup.getLastDetailForUpdate();
        entityAttributeGroupDetail.setThruTime(session.START_TIME_LONG);
        entityAttributeGroup.setActiveDetail(null);
        entityAttributeGroup.store();
        
        // Check for default, and pick one if necessary
        EntityAttributeGroup defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
        if(defaultEntityAttributeGroup == null) {
            List<EntityAttributeGroup> entityAttributeGroups = getEntityAttributeGroupsForUpdate();
            
            if(!entityAttributeGroups.isEmpty()) {
                Iterator<EntityAttributeGroup> iter = entityAttributeGroups.iterator();
                if(iter.hasNext()) {
                    defaultEntityAttributeGroup = iter.next();
                }
                EntityAttributeGroupDetailValue entityAttributeGroupDetailValue = defaultEntityAttributeGroup.getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();
                
                entityAttributeGroupDetailValue.setIsDefault(Boolean.TRUE);
                updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(entityAttributeGroup.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityAttributeGroupDescription createEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language, String description,
            BasePK createdBy) {
        EntityAttributeGroupDescription entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().create(entityAttributeGroup,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeGroupDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityAttributeGroupDescription;
    }
    
    private EntityAttributeGroupDescription getEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language, EntityPermission entityPermission) {
        EntityAttributeGroupDescription entityAttributeGroupDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_lang_languageid = ? AND enagpd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_lang_languageid = ? AND enagpd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeGroupDescription;
    }
    
    public EntityAttributeGroupDescription getEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescription(entityAttributeGroup, language, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeGroupDescription getEntityAttributeGroupDescriptionForUpdate(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescription(entityAttributeGroup, language, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeGroupDescriptionValue getEntityAttributeGroupDescriptionValue(EntityAttributeGroupDescription entityAttributeGroupDescription) {
        return entityAttributeGroupDescription == null? null: entityAttributeGroupDescription.getEntityAttributeGroupDescriptionValue().clone();
    }
    
    public EntityAttributeGroupDescriptionValue getEntityAttributeGroupDescriptionValueForUpdate(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescriptionValue(getEntityAttributeGroupDescriptionForUpdate(entityAttributeGroup, language));
    }
    
    private List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, EntityPermission entityPermission) {
        List<EntityAttributeGroupDescription> entityAttributeGroupDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions, languages " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_thrutime = ? " +
                        "AND enagpd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeGroupDescriptions = EntityAttributeGroupDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeGroupDescriptions;
    }
    
    public List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroupForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language) {
        String description;
        EntityAttributeGroupDescription entityAttributeGroupDescription = getEntityAttributeGroupDescription(entityAttributeGroup, language);
        
        if(entityAttributeGroupDescription == null && !language.getIsDefault()) {
            entityAttributeGroupDescription = getEntityAttributeGroupDescription(entityAttributeGroup, getPartyControl().getDefaultLanguage());
        }
        
        if(entityAttributeGroupDescription == null) {
            description = entityAttributeGroup.getLastDetail().getEntityAttributeGroupName();
        } else {
            description = entityAttributeGroupDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityAttributeGroupDescriptionTransfer getEntityAttributeGroupDescriptionTransfer(UserVisit userVisit, EntityAttributeGroupDescription entityAttributeGroupDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeGroupDescriptionTransferCache().getEntityAttributeGroupDescriptionTransfer(entityAttributeGroupDescription, entityInstance);
    }
    
    public List<EntityAttributeGroupDescriptionTransfer> getEntityAttributeGroupDescriptionTransfers(UserVisit userVisit, EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        List<EntityAttributeGroupDescription> entityAttributeGroupDescriptions = getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup);
        List<EntityAttributeGroupDescriptionTransfer> entityAttributeGroupDescriptionTransfers = new ArrayList<>(entityAttributeGroupDescriptions.size());
        EntityAttributeGroupDescriptionTransferCache entityAttributeGroupDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeGroupDescriptionTransferCache();
        
        entityAttributeGroupDescriptions.stream().forEach((entityAttributeGroupDescription) -> {
            entityAttributeGroupDescriptionTransfers.add(entityAttributeGroupDescriptionTransferCache.getEntityAttributeGroupDescriptionTransfer(entityAttributeGroupDescription, entityInstance));
        });
        
        return entityAttributeGroupDescriptionTransfers;
    }
    
    public void updateEntityAttributeGroupDescriptionFromValue(EntityAttributeGroupDescriptionValue entityAttributeGroupDescriptionValue, BasePK updatedBy) {
        if(entityAttributeGroupDescriptionValue.hasBeenModified()) {
            EntityAttributeGroupDescription entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeGroupDescriptionValue.getPrimaryKey());
            
            entityAttributeGroupDescription.setThruTime(session.START_TIME_LONG);
            entityAttributeGroupDescription.store();
            
            EntityAttributeGroup entityAttributeGroup = entityAttributeGroupDescription.getEntityAttributeGroup();
            Language language = entityAttributeGroupDescription.getLanguage();
            String description = entityAttributeGroupDescriptionValue.getDescription();
            
            entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().create(entityAttributeGroup, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeGroupDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityAttributeGroupDescription(EntityAttributeGroupDescription entityAttributeGroupDescription, BasePK deletedBy) {
        entityAttributeGroupDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityAttributeGroupDescription.getEntityAttributeGroupPK(), EventTypes.MODIFY.name(), entityAttributeGroupDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        List<EntityAttributeGroupDescription> entityAttributeGroupDescriptions = getEntityAttributeGroupDescriptionsByEntityAttributeGroupForUpdate(entityAttributeGroup);
        
        entityAttributeGroupDescriptions.stream().forEach((entityAttributeGroupDescription) -> {
            deleteEntityAttributeGroupDescription(entityAttributeGroupDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attributes
    // --------------------------------------------------------------------------------
    
    public EntityAttribute createEntityAttribute(EntityType entityType, String entityAttributeName,
            EntityAttributeType entityAttributeType, Boolean trackRevisions, Integer sortOrder, BasePK createdBy) {
        EntityAttribute entityAttribute = EntityAttributeFactory.getInstance().create();
        EntityAttributeDetail entityAttributeDetail = EntityAttributeDetailFactory.getInstance().create(entityAttribute, entityType,
                entityAttributeName, entityAttributeType, trackRevisions, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                entityAttribute.getPrimaryKey());
        entityAttribute.setActiveDetail(entityAttributeDetail);
        entityAttribute.setLastDetail(entityAttributeDetail);
        entityAttribute.store();
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return entityAttribute;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.EntityAttribute */
    public EntityAttribute getEntityAttributeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityAttributePK pk = new EntityAttributePK(entityInstance.getEntityUniqueId());
        EntityAttribute entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return entityAttribute;
    }

    public EntityAttribute getEntityAttributeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttribute getEntityAttributeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute getEntityAttributeByPK(EntityAttributePK pk) {
        return EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }
    
    public EntityAttribute getEntityAttributeByName(EntityType entityType, String entityAttributeName, EntityPermission entityPermission) {
        EntityAttribute entityAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_entityattributename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_entityattributename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setString(2, entityAttributeName);
            
            entityAttribute = EntityAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttribute;
    }
    
    public EntityAttribute getEntityAttributeByName(EntityType entityType, String entityAttributeName) {
        return getEntityAttributeByName(entityType, entityAttributeName, EntityPermission.READ_ONLY);
    }
    
    public EntityAttribute getEntityAttributeByNameForUpdate(EntityType entityType, String entityAttributeName) {
        return getEntityAttributeByName(entityType, entityAttributeName, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeDetailValue getEntityAttributeDetailValueForUpdate(EntityAttribute entityAttribute) {
        return entityAttribute == null? null: entityAttribute.getLastDetailForUpdate().getEntityAttributeDetailValue().clone();
    }
    
    public EntityAttributeDetailValue getEntityAttributeDetailValueByNameForUpdate(EntityType entityType, String entityAttributeName) {
        return getEntityAttributeDetailValueForUpdate(getEntityAttributeByNameForUpdate(entityType, entityAttributeName));
    }
    
    private List<EntityAttribute> getEntityAttributesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<EntityAttribute> entityAttributes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? " +
                        "ORDER BY enadt_sortorder, enadt_entityattributename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            entityAttributes = EntityAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributes;
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityType(EntityType entityType) {
        return getEntityAttributesByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeForUpdate(EntityType entityType) {
        return getEntityAttributesByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType,
            EntityAttributeType entityAttributeType, EntityPermission entityPermission) {
        List<EntityAttribute> entityAttributes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_enat_entityattributetypeid = ? " +
                        "ORDER BY enadt_sortorder, enadt_entityattributename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_enat_entityattributetypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, entityAttributeType.getPrimaryKey().getEntityId());
            
            entityAttributes = EntityAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributes;
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType,
            EntityAttributeType entityAttributeType) {
        return getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeTypeForUpdate(EntityType entityType,
            EntityAttributeType entityAttributeType) {
        return getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType, EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttribute> getEntityAttributesByEntityAttributeGroupAndEntityType(EntityAttributeGroup entityAttributeGroup, EntityType entityType,
            EntityPermission entityPermission) {
        List<EntityAttribute> entityAttributes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_ena_entityattributeid = ena_entityattributeid "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "ORDER BY enaenagp_sortorder, enadt_sortorder, enadt_entityattributename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_ena_entityattributeid = ena_entityattributeid "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, entityType.getPrimaryKey().getEntityId());
            
            entityAttributes = EntityAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributes;
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityAttributeGroupAndEntityType(EntityAttributeGroup entityAttributeGroup, EntityType entityType) {
        return getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityAttributeGroupAndEntityTypeForUpdate(EntityAttributeGroup entityAttributeGroup, EntityType entityType) {
        return getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeTransfer getEntityAttributeTransfer(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeTransferCache().getEntityAttributeTransfer(entityAttribute, entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfers(UserVisit userVisit, Collection<EntityAttribute> entityAttributes, EntityInstance entityInstance) {
        List<EntityAttributeTransfer> entityAttributeTransfers = new ArrayList<>(entityAttributes.size());
        EntityAttributeTransferCache entityAttributeTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeTransferCache();
        
        entityAttributes.stream().forEach((entityAttribute) -> {
            entityAttributeTransfers.add(entityAttributeTransferCache.getEntityAttributeTransfer(entityAttribute, entityInstance));
        });
        
        return entityAttributeTransfers;
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfersByEntityType(UserVisit userVisit, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAttributeTransfers(userVisit, getEntityAttributesByEntityType(entityType), entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfersByEntityTypeAndEntityAttributeType(UserVisit userVisit, EntityType entityType,
            EntityAttributeType entityAttributeType, EntityInstance entityInstance) {
        return getEntityAttributeTransfers(userVisit, getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType), entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfersByEntityAttributeGroupAndEntityType(UserVisit userVisit,
            EntityAttributeGroup entityAttributeGroup, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAttributeTransfers(userVisit, getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType),
                entityInstance);
    }
    
    public void updateEntityAttributeFromValue(EntityAttributeDetailValue entityAttributeDetailValue, BasePK updatedBy) {
        if(entityAttributeDetailValue.hasBeenModified()) {
            EntityAttribute entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeDetailValue.getEntityAttributePK());
            EntityAttributeDetail entityAttributeDetail = entityAttribute.getActiveDetailForUpdate();
            
            entityAttributeDetail.setThruTime(session.START_TIME_LONG);
            entityAttributeDetail.store();
            
            EntityAttributePK entityAttributePK = entityAttributeDetail.getEntityAttributePK(); // Not updated
            EntityTypePK entityTypePK = entityAttributeDetail.getEntityTypePK(); // Not updated
            String entityAttributeName = entityAttributeDetailValue.getEntityAttributeName();
            EntityAttributeTypePK entityAttributeTypePK = entityAttributeDetail.getEntityAttributeTypePK(); // Not updated
            Boolean trackRevisions = entityAttributeDetailValue.getTrackRevisions();
            Integer sortOrder = entityAttributeDetailValue.getSortOrder();
            
            entityAttributeDetail = EntityAttributeDetailFactory.getInstance().create(entityAttributePK, entityTypePK,
                    entityAttributeName, entityAttributeTypePK, trackRevisions, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            entityAttribute.setActiveDetail(entityAttributeDetail);
            entityAttribute.setLastDetail(entityAttributeDetail);
            
            sendEventUsingNames(entityAttributePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        EntityAttributeDetail entityAttributeDetail = entityAttribute.getLastDetailForUpdate();
        String entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();
        EntityAttributeTypes entityAttributeType = EntityAttributeTypes.valueOf(entityAttributeTypeName);
        
        switch(entityAttributeType) {
            case BOOLEAN:
                deleteEntityBooleanAttributesByEntityAttribute(entityAttribute, deletedBy);
                break;
            case NAME:
                deleteEntityNameAttributesByEntityAttribute(entityAttribute, deletedBy);
                break;
            case INTEGER:
            case LONG:
                deleteEntityAttributeNumericByEntityAttribute(entityAttribute, deletedBy);

                switch(entityAttributeType) {
                    case INTEGER:
                        deleteEntityAttributeIntegerByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityIntegerRangesByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityIntegerAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    case LONG:
                        deleteEntityAttributeLongByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityLongRangesByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityLongAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    default:
                        break;
                }
                break;
            case STRING:
                deleteEntityAttributeStringByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityStringAttributesByEntityAttribute(entityAttribute, deletedBy);
                break;
            case GEOPOINT:
                deleteEntityGeoPointAttributesByEntityAttribute(entityAttribute, deletedBy);
                break;
            case BLOB:
                deleteEntityAttributeBlobByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityBlobAttributesByEntityAttribute(entityAttribute, deletedBy);
                break;
            case CLOB:
                deleteEntityClobAttributesByEntityAttribute(entityAttribute, deletedBy);
                break;
            case DATE:
                deleteEntityDateAttributesByEntityAttribute(entityAttribute, deletedBy);
                break;
            case TIME:
                deleteEntityTimeAttributesByEntityAttribute(entityAttribute, deletedBy);
                break;
            case LISTITEM:
            case MULTIPLELISTITEM:
                deleteEntityAttributeListItemByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityListItemsByEntityAttribute(entityAttribute, deletedBy);
                break;
            case ENTITY:
            case COLLECTION:
                deleteEntityAttributeEntityTypesByEntityAttribute(entityAttribute, deletedBy);

                switch(entityAttributeType) {
                    case ENTITY:
                        deleteEntityEntityAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    case COLLECTION:
                        deleteEntityCollectionAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    default:
                        break;
                }
                break;
        }
        
        deleteEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, deletedBy);
        deleteEntityAttributeDescriptionsByEntityAttribute(entityAttribute, deletedBy);
        
        if(entityAttribute.getEntityPermission().equals(EntityPermission.READ_ONLY)) {
            // Convert to R/W
            entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityAttribute.getPrimaryKey());
        }
        
        entityAttributeDetail.setThruTime(session.START_TIME_LONG);
        entityAttribute.setActiveDetail(null);
        entityAttribute.store();
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteEntityAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        List<EntityAttribute> entityAttributes = getEntityAttributesByEntityType(entityInstance.getEntityType());
        
        entityAttributes.stream().map((entityAttribute) -> entityAttribute.getLastDetailForUpdate().getEntityAttributeType().getEntityAttributeTypeName()).forEach((entityAttributeTypeName) -> {
            if(entityAttributeTypeName.equals(EntityAttributeTypes.BOOLEAN.name())) {
                deleteEntityBooleanAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.NAME.name())) {
                deleteEntityNameAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
                deleteEntityIntegerAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                deleteEntityLongAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.STRING.name())) {
                deleteEntityStringAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
                deleteEntityGeoPointAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                deleteEntityBlobAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                deleteEntityClobAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.ENTITY.name())) {
                deleteEntityEntityAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.COLLECTION.name())) {
                deleteEntityCollectionAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())) {
                deleteEntityDateAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())) {
                deleteEntityTimeAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                deleteEntityListItemAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                deleteEntityMultipleListItemAttributesByEntityInstance(entityInstance, deletedBy);
            }
        });
    }
    
    public void deleteEntityAttributes(List<EntityAttribute> entityAttributes, BasePK deletedBy) {
        entityAttributes.stream().forEach((entityAttribute) -> {
            deleteEntityAttribute(entityAttribute, deletedBy);
        });
    }
    
    public void deleteEntityAttributesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteEntityAttributes(getEntityAttributesByEntityTypeForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityAttributeDescription createEntityAttributeDescription(EntityAttribute entityAttribute, Language language,
            String description, BasePK createdBy) {
        EntityAttributeDescription entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().create(session,
                entityAttribute, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityAttributeDescription;
    }
    
    private EntityAttributeDescription getEntityAttributeDescription(EntityAttribute entityAttribute, Language language,
            EntityPermission entityPermission) {
        EntityAttributeDescription entityAttributeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_lang_languageid = ? AND enad_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_lang_languageid = ? AND enad_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeDescription;
    }
    
    public EntityAttributeDescription getEntityAttributeDescription(EntityAttribute entityAttribute, Language language) {
        return getEntityAttributeDescription(entityAttribute, language, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeDescription getEntityAttributeDescriptionForUpdate(EntityAttribute entityAttribute, Language language) {
        return getEntityAttributeDescription(entityAttribute, language, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeDescriptionValue getEntityAttributeDescriptionValue(EntityAttributeDescription entityAttributeDescription) {
        return entityAttributeDescription == null? null: entityAttributeDescription.getEntityAttributeDescriptionValue().clone();
    }
    
    public EntityAttributeDescriptionValue getEntityAttributeDescriptionValueForUpdate(EntityAttribute entityAttribute, Language language) {
        return getEntityAttributeDescriptionValue(getEntityAttributeDescriptionForUpdate(entityAttribute, language));
    }
    
    private List<EntityAttributeDescription> getEntityAttributeDescriptionsByEntityAttribute(EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        List<EntityAttributeDescription> entityAttributeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions, languages " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_thrutime = ? AND enad_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeDescriptions = EntityAttributeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeDescriptions;
    }
    
    public List<EntityAttributeDescription> getEntityAttributeDescriptionsByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeDescriptionsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeDescription> getEntityAttributeDescriptionsByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeDescriptionsByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityAttributeDescription(EntityAttribute entityAttribute, Language language) {
        String description;
        EntityAttributeDescription entityAttributeDescription = getEntityAttributeDescription(entityAttribute, language);
        
        if(entityAttributeDescription == null && !language.getIsDefault()) {
            entityAttributeDescription = getEntityAttributeDescription(entityAttribute, getPartyControl().getDefaultLanguage());
        }
        
        if(entityAttributeDescription == null) {
            description = entityAttribute.getLastDetail().getEntityAttributeName();
        } else {
            description = entityAttributeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityAttributeDescriptionTransfer getEntityAttributeDescriptionTransfer(UserVisit userVisit, EntityAttributeDescription entityAttributeDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeDescriptionTransferCache().getEntityAttributeDescriptionTransfer(entityAttributeDescription, entityInstance);
    }
    
    public List<EntityAttributeDescriptionTransfer> getEntityAttributeDescriptionTransfersByEntityAttribute(UserVisit userVisit,
            EntityAttribute entityAttribute, EntityInstance entityInstance) {
        List<EntityAttributeDescription> entityAttributeDescriptions = getEntityAttributeDescriptionsByEntityAttribute(entityAttribute);
        List<EntityAttributeDescriptionTransfer> entityAttributeDescriptionTransfers = new ArrayList<>(entityAttributeDescriptions.size());
        EntityAttributeDescriptionTransferCache entityAttributeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeDescriptionTransferCache();
        
        entityAttributeDescriptions.stream().forEach((entityAttributeDescription) -> {
            entityAttributeDescriptionTransfers.add(entityAttributeDescriptionTransferCache.getEntityAttributeDescriptionTransfer(entityAttributeDescription, entityInstance));
        });
        
        return entityAttributeDescriptionTransfers;
    }
    
    public void updateEntityAttributeDescriptionFromValue(EntityAttributeDescriptionValue entityAttributeDescriptionValue, BasePK updatedBy) {
        if(entityAttributeDescriptionValue.hasBeenModified()) {
            EntityAttributeDescription entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeDescriptionValue.getPrimaryKey());
            
            entityAttributeDescription.setThruTime(session.START_TIME_LONG);
            entityAttributeDescription.store();
            
            EntityAttribute entityAttribute = entityAttributeDescription.getEntityAttribute();
            Language language = entityAttributeDescription.getLanguage();
            String description = entityAttributeDescriptionValue.getDescription();
            
            entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().create(entityAttribute, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityAttributeDescription(EntityAttributeDescription entityAttributeDescription, BasePK deletedBy) {
        entityAttributeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityAttributeDescription.getEntityAttributePK(), EventTypes.MODIFY.name(), entityAttributeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityAttributeDescriptionsByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        List<EntityAttributeDescription> entityAttributeDescriptions = getEntityAttributeDescriptionsByEntityAttributeForUpdate(entityAttribute);
        
        entityAttributeDescriptions.stream().forEach((entityAttributeDescription) -> {
            deleteEntityAttributeDescription(entityAttributeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Blobs
    // --------------------------------------------------------------------------------
    
    public EntityAttributeBlob createEntityAttributeBlob(EntityAttribute entityAttribute, Boolean checkContentWebAddress,
            BasePK createdBy) {
        EntityAttributeBlob entityAttributeBlob = EntityAttributeBlobFactory.getInstance().create(session,
                entityAttribute, checkContentWebAddress, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeBlob.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityAttributeBlob;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeBlobQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeblobs "
                + "WHERE enab_ena_entityattributeid = ? AND enab_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeblobs "
                + "WHERE enab_ena_entityattributeid = ? AND enab_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeBlobQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeBlob getEntityAttributeBlob(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeBlobFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeBlobQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeBlob getEntityAttributeBlob(EntityAttribute entityAttribute) {
        return getEntityAttributeBlob(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeBlob getEntityAttributeBlobForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeBlob(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeBlobValue getEntityAttributeBlobValue(EntityAttributeBlob entityAttributeBlob) {
        return entityAttributeBlob == null? null: entityAttributeBlob.getEntityAttributeBlobValue().clone();
    }
    
    public EntityAttributeBlobValue getEntityAttributeBlobValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeBlobValue(getEntityAttributeBlobForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeBlobFromValue(EntityAttributeBlobValue entityAttributeBlobValue, BasePK updatedBy) {
        if(entityAttributeBlobValue.hasBeenModified()) {
            EntityAttributeBlob entityAttributeBlob = EntityAttributeBlobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeBlobValue.getPrimaryKey());
            
            entityAttributeBlob.setThruTime(session.START_TIME_LONG);
            entityAttributeBlob.store();
            
            EntityAttribute entityAttribute = entityAttributeBlob.getEntityAttribute();
            Boolean checkContentWebAddress = entityAttributeBlobValue.getCheckContentWebAddress();
            
            entityAttributeBlob = EntityAttributeBlobFactory.getInstance().create(entityAttribute, checkContentWebAddress,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeBlob.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityAttributeBlob(EntityAttributeBlob entityAttributeBlob, BasePK deletedBy) {
        entityAttributeBlob.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityAttributeBlob.getEntityAttributePK(), EventTypes.MODIFY.name(), entityAttributeBlob.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityAttributeBlobByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        EntityAttributeBlob entityAttributeBlob = getEntityAttributeBlobForUpdate(entityAttribute);
        
        if(entityAttributeBlob != null) {
            deleteEntityAttributeBlob(entityAttributeBlob, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Strings
    // --------------------------------------------------------------------------------
    
    public EntityAttributeString createEntityAttributeString(EntityAttribute entityAttribute, String validationPattern,
            BasePK createdBy) {
        EntityAttributeString entityAttributeString = EntityAttributeStringFactory.getInstance().create(session,
                entityAttribute, validationPattern, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeString.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityAttributeString;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeStringQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributestrings "
                + "WHERE enas_ena_entityattributeid = ? AND enas_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributestrings "
                + "WHERE enas_ena_entityattributeid = ? AND enas_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeStringQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeString getEntityAttributeString(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeStringFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeStringQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeString getEntityAttributeString(EntityAttribute entityAttribute) {
        return getEntityAttributeString(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeString getEntityAttributeStringForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeString(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeStringValue getEntityAttributeStringValue(EntityAttributeString entityAttributeString) {
        return entityAttributeString == null? null: entityAttributeString.getEntityAttributeStringValue().clone();
    }
    
    public EntityAttributeStringValue getEntityAttributeStringValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeStringValue(getEntityAttributeStringForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeStringFromValue(EntityAttributeStringValue entityAttributeStringValue, BasePK updatedBy) {
        if(entityAttributeStringValue.hasBeenModified()) {
            EntityAttributeString entityAttributeString = EntityAttributeStringFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeStringValue.getPrimaryKey());
            
            entityAttributeString.setThruTime(session.START_TIME_LONG);
            entityAttributeString.store();
            
            EntityAttribute entityAttribute = entityAttributeString.getEntityAttribute();
            String validationPattern = entityAttributeStringValue.getValidationPattern();
            
            entityAttributeString = EntityAttributeStringFactory.getInstance().create(entityAttribute, validationPattern,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeString.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityAttributeString(EntityAttributeString entityAttributeString, BasePK deletedBy) {
        entityAttributeString.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityAttributeString.getEntityAttributePK(), EventTypes.MODIFY.name(), entityAttributeString.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityAttributeStringByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        EntityAttributeString entityAttributeString = getEntityAttributeStringForUpdate(entityAttribute);
        
        if(entityAttributeString != null) {
            deleteEntityAttributeString(entityAttributeString, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Integers
    // --------------------------------------------------------------------------------
    
    public EntityAttributeInteger createEntityAttributeInteger(EntityAttribute entityAttribute, Integer upperRangeIntegerValue,
            Integer upperLimitIntegerValue, Integer lowerLimitIntegerValue, Integer lowerRangeIntegerValue, BasePK createdBy) {
        EntityAttributeInteger entityAttributeInteger = EntityAttributeIntegerFactory.getInstance().create(session,
                entityAttribute, upperRangeIntegerValue, upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeInteger.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityAttributeInteger;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeIntegerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeintegers "
                + "WHERE enai_ena_entityattributeid = ? AND enai_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeintegers "
                + "WHERE enai_ena_entityattributeid = ? AND enai_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeIntegerQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeInteger getEntityAttributeInteger(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeIntegerFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeIntegerQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeInteger getEntityAttributeInteger(EntityAttribute entityAttribute) {
        return getEntityAttributeInteger(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeInteger getEntityAttributeIntegerForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeInteger(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeIntegerValue getEntityAttributeIntegerValue(EntityAttributeInteger entityAttributeInteger) {
        return entityAttributeInteger == null? null: entityAttributeInteger.getEntityAttributeIntegerValue().clone();
    }
    
    public EntityAttributeIntegerValue getEntityAttributeIntegerValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeIntegerValue(getEntityAttributeIntegerForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeIntegerFromValue(EntityAttributeIntegerValue entityAttributeIntegerValue, BasePK updatedBy) {
        if(entityAttributeIntegerValue.hasBeenModified()) {
            EntityAttributeInteger entityAttributeInteger = EntityAttributeIntegerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeIntegerValue.getPrimaryKey());
            
            entityAttributeInteger.setThruTime(session.START_TIME_LONG);
            entityAttributeInteger.store();
            
            EntityAttribute entityAttribute = entityAttributeInteger.getEntityAttribute();
            Integer upperRangeIntegerValue = entityAttributeIntegerValue.getUpperRangeIntegerValue();
            Integer upperLimitIntegerValue = entityAttributeIntegerValue.getUpperLimitIntegerValue();
            Integer lowerLimitIntegerValue = entityAttributeIntegerValue.getLowerLimitIntegerValue();
            Integer lowerRangeIntegerValue = entityAttributeIntegerValue.getLowerRangeIntegerValue();
            
            entityAttributeInteger = EntityAttributeIntegerFactory.getInstance().create(entityAttribute, upperRangeIntegerValue,
                    upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeInteger.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityAttributeInteger(EntityAttributeInteger entityAttributeInteger, BasePK deletedBy) {
        entityAttributeInteger.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityAttributeInteger.getEntityAttributePK(), EventTypes.MODIFY.name(), entityAttributeInteger.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityAttributeIntegerByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        EntityAttributeInteger entityAttributeInteger = getEntityAttributeIntegerForUpdate(entityAttribute);
        
        if(entityAttributeInteger != null) {
            deleteEntityAttributeInteger(entityAttributeInteger, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Longs
    // --------------------------------------------------------------------------------
    
    public EntityAttributeLong createEntityAttributeLong(EntityAttribute entityAttribute, Long upperRangeLongValue,
            Long upperLimitLongValue, Long lowerLimitLongValue, Long lowerRangeLongValue, BasePK createdBy) {
        EntityAttributeLong entityAttributeLong = EntityAttributeLongFactory.getInstance().create(session,
                entityAttribute, upperRangeLongValue, upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeLong.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityAttributeLong;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeLongQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributelongs "
                + "WHERE enal_ena_entityattributeid = ? AND enal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributelongs "
                + "WHERE enal_ena_entityattributeid = ? AND enal_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeLongQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeLong getEntityAttributeLong(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeLongFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeLongQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeLong getEntityAttributeLong(EntityAttribute entityAttribute) {
        return getEntityAttributeLong(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeLong getEntityAttributeLongForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeLong(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeLongValue getEntityAttributeLongValue(EntityAttributeLong entityAttributeLong) {
        return entityAttributeLong == null? null: entityAttributeLong.getEntityAttributeLongValue().clone();
    }
    
    public EntityAttributeLongValue getEntityAttributeLongValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeLongValue(getEntityAttributeLongForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeLongFromValue(EntityAttributeLongValue entityAttributeLongValue, BasePK updatedBy) {
        if(entityAttributeLongValue.hasBeenModified()) {
            EntityAttributeLong entityAttributeLong = EntityAttributeLongFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeLongValue.getPrimaryKey());
            
            entityAttributeLong.setThruTime(session.START_TIME_LONG);
            entityAttributeLong.store();
            
            EntityAttribute entityAttribute = entityAttributeLong.getEntityAttribute();
            Long upperRangeLongValue = entityAttributeLongValue.getUpperRangeLongValue();
            Long upperLimitLongValue = entityAttributeLongValue.getUpperLimitLongValue();
            Long lowerLimitLongValue = entityAttributeLongValue.getLowerLimitLongValue();
            Long lowerRangeLongValue = entityAttributeLongValue.getLowerRangeLongValue();
            
            entityAttributeLong = EntityAttributeLongFactory.getInstance().create(entityAttribute, upperRangeLongValue,
                    upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeLong.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityAttributeLong(EntityAttributeLong entityAttributeLong, BasePK deletedBy) {
        entityAttributeLong.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityAttributeLong.getEntityAttributePK(), EventTypes.MODIFY.name(), entityAttributeLong.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityAttributeLongByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        EntityAttributeLong entityAttributeLong = getEntityAttributeLongForUpdate(entityAttribute);
        
        if(entityAttributeLong != null) {
            deleteEntityAttributeLong(entityAttributeLong, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Numerics
    // --------------------------------------------------------------------------------
    
    public EntityAttributeNumeric createEntityAttributeNumeric(EntityAttribute entityAttribute, UnitOfMeasureType unitOfMeasureType,
            BasePK createdBy) {
        EntityAttributeNumeric entityAttributeNumeric = EntityAttributeNumericFactory.getInstance().create(session,
                entityAttribute, unitOfMeasureType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeNumeric.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityAttributeNumeric;
    }
    
    public long countEntityAttributeNumericsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributenumerics " +
                "WHERE enan_uomt_unitofmeasuretypeid = ? AND enan_thrutime = ?",
                unitOfMeasureType, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getEntityAttributeNumericQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributenumerics "
                + "WHERE enan_ena_entityattributeid = ? AND enan_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributenumerics "
                + "WHERE enan_ena_entityattributeid = ? AND enan_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeNumericQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeNumeric getEntityAttributeNumeric(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeNumericFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeNumericQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeNumeric getEntityAttributeNumeric(EntityAttribute entityAttribute) {
        return getEntityAttributeNumeric(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeNumeric getEntityAttributeNumericForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeNumeric(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeNumericValue getEntityAttributeNumericValue(EntityAttributeNumeric entityAttributeNumeric) {
        return entityAttributeNumeric == null? null: entityAttributeNumeric.getEntityAttributeNumericValue().clone();
    }
    
    public EntityAttributeNumericValue getEntityAttributeNumericValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeNumericValue(getEntityAttributeNumericForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeNumericFromValue(EntityAttributeNumericValue entityAttributeNumericValue, BasePK updatedBy) {
        if(entityAttributeNumericValue.hasBeenModified()) {
            EntityAttributeNumeric entityAttributeNumeric = EntityAttributeNumericFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeNumericValue.getPrimaryKey());
            
            entityAttributeNumeric.setThruTime(session.START_TIME_LONG);
            entityAttributeNumeric.store();
            
            EntityAttributePK entityAttributePK = entityAttributeNumeric.getEntityAttributePK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = entityAttributeNumericValue.getUnitOfMeasureTypePK();
            
            entityAttributeNumeric = EntityAttributeNumericFactory.getInstance().create(entityAttributePK, unitOfMeasureTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityAttributePK, EventTypes.MODIFY.name(), entityAttributeNumeric.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityAttributeNumeric(EntityAttributeNumeric entityAttributeNumeric, BasePK deletedBy) {
        entityAttributeNumeric.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityAttributeNumeric.getEntityAttributePK(), EventTypes.MODIFY.name(), entityAttributeNumeric.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityAttributeNumericByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        EntityAttributeNumeric entityAttributeNumeric = getEntityAttributeNumericForUpdate(entityAttribute);
        
        if(entityAttributeNumeric != null) {
            deleteEntityAttributeNumeric(entityAttributeNumeric, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute ListItems
    // --------------------------------------------------------------------------------
    
    public EntityAttributeListItem createEntityAttributeListItem(EntityAttribute entityAttribute, Sequence entityListItemSequence,
            BasePK createdBy) {
        EntityAttributeListItem entityAttributeListItem = EntityAttributeListItemFactory.getInstance().create(session,
                entityAttribute, entityListItemSequence, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeListItem.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityAttributeListItem;
    }
    
    public long countEntityAttributeListItemsByEntityListItemSequence(Sequence entityListItemSequence) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributelistitems " +
                "WHERE enali_entitylistitemsequenceid = ? AND enali_thrutime = ?",
                entityListItemSequence, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getEntityAttributeListItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributelistitems "
                + "WHERE enali_entitylistitemsequenceid = ? AND enali_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributelistitems "
                + "WHERE enali_entitylistitemsequenceid = ? AND enali_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeListItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeListItem getEntityAttributeListItem(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeListItemFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeListItemQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeListItem getEntityAttributeListItem(EntityAttribute entityAttribute) {
        return getEntityAttributeListItem(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeListItem getEntityAttributeListItemForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeListItem(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeListItemValue getEntityAttributeListItemValue(EntityAttributeListItem entityAttributeListItem) {
        return entityAttributeListItem == null? null: entityAttributeListItem.getEntityAttributeListItemValue().clone();
    }
    
    public EntityAttributeListItemValue getEntityAttributeListItemValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeListItemValue(getEntityAttributeListItemForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeListItemFromValue(EntityAttributeListItemValue entityAttributeListItemValue, BasePK updatedBy) {
        if(entityAttributeListItemValue.hasBeenModified()) {
            EntityAttributeListItem entityAttributeListItem = EntityAttributeListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeListItemValue.getPrimaryKey());
            
            entityAttributeListItem.setThruTime(session.START_TIME_LONG);
            entityAttributeListItem.store();
            
            EntityAttributePK entityAttributePK = entityAttributeListItem.getEntityAttributePK();
            SequencePK entityListItemSequencePK = entityAttributeListItemValue.getEntityListItemSequencePK();
            
            entityAttributeListItem = EntityAttributeListItemFactory.getInstance().create(entityAttributePK, entityListItemSequencePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityAttributePK, EventTypes.MODIFY.name(), entityAttributeListItem.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityAttributeListItem(EntityAttributeListItem entityAttributeListItem, BasePK deletedBy) {
        entityAttributeListItem.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityAttributeListItem.getEntityAttributePK(), EventTypes.MODIFY.name(), entityAttributeListItem.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityAttributeListItemByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        EntityAttributeListItem entityAttributeListItem = getEntityAttributeListItemForUpdate(entityAttribute);
        
        if(entityAttributeListItem != null) {
            deleteEntityAttributeListItem(entityAttributeListItem, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // --------------------------------------------------------------------------------
    
    public EntityAttributeEntityAttributeGroup createEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute,
            EntityAttributeGroup entityAttributeGroup, Integer sortOrder, BasePK createdBy) {
        EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().create(session,
                entityAttribute, entityAttributeGroup, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityAttributeEntityAttributeGroup;
    }
    
    private EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute,
            EntityAttributeGroup entityAttributeGroup, EntityPermission entityPermission) {
        EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributeentityattributegroups " +
                        "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributeentityattributegroups " +
                        "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeEntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeEntityAttributeGroup;
    }
    
    public EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroupForUpdate(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeEntityAttributeGroupValue getEntityAttributeEntityAttributeGroupValue(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup) {
        return entityAttributeEntityAttributeGroup == null? null: entityAttributeEntityAttributeGroup.getEntityAttributeEntityAttributeGroupValue().clone();
    }
    
    public EntityAttributeEntityAttributeGroupValue getEntityAttributeEntityAttributeGroupValueForUpdate(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupValue(getEntityAttributeEntityAttributeGroupForUpdate(entityAttribute, entityAttributeGroup));
    }
    
    private List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        List<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributegroups, entityattributegroupdetails "
                        + "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_enagp_entityattributegroupid = enagp_entityattributegroupid AND enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "ORDER BY enaenagp_sortorder, enagpdt_sortorder, enagpdt_entityattributegroupname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups "
                        + "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ? "
                        + "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeEntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeEntityAttributeGroups = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeEntityAttributeGroups;
    }
    
    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup,
            EntityPermission entityPermission) {
        List<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributegroups, entityattributegroupdetails "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_enagp_entityattributegroupid = enagp_entityattributegroupid AND enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "ORDER BY enaenagp_sortorder, enagpdt_sortorder, enagpdt_entityattributegroupname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityAttributeEntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeEntityAttributeGroups = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeEntityAttributeGroups;
    }
    
    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroupForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeEntityAttributeGroupTransfer getEntityAttributeEntityAttributeGroupTransfer(UserVisit userVisit, EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeEntityAttributeGroupTransferCache().getEntityAttributeEntityAttributeGroupTransfer(entityAttributeEntityAttributeGroup, entityInstance);
    }
    
    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfers(UserVisit userVisit,
            List<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups, EntityInstance entityInstance) {
        List<EntityAttributeEntityAttributeGroupTransfer> entityAttributeEntityAttributeGroupTransfers = new ArrayList<>(entityAttributeEntityAttributeGroups.size());
        EntityAttributeEntityAttributeGroupTransferCache entityAttributeEntityAttributeGroupTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeEntityAttributeGroupTransferCache();
        
        entityAttributeEntityAttributeGroups.stream().forEach((entityAttributeEntityAttributeGroup) -> {
            entityAttributeEntityAttributeGroupTransfers.add(entityAttributeEntityAttributeGroupTransferCache.getEntityAttributeEntityAttributeGroupTransfer(entityAttributeEntityAttributeGroup, entityInstance));
        });
        
        return entityAttributeEntityAttributeGroupTransfers;
    }
    
    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfersByEntityAttribute(UserVisit userVisit,
            EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityAttributeEntityAttributeGroupTransfers(userVisit, getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute), entityInstance);
    }
    
    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfersByEntityAttributeGroup(UserVisit userVisit,
            EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        return getEntityAttributeEntityAttributeGroupTransfers(userVisit, getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup), entityInstance);
    }
    
    public void updateEntityAttributeEntityAttributeGroupFromValue(EntityAttributeEntityAttributeGroupValue entityAttributeEntityAttributeGroupValue, BasePK updatedBy) {
        if(entityAttributeEntityAttributeGroupValue.hasBeenModified()) {
            EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeEntityAttributeGroupValue.getPrimaryKey());
            
            entityAttributeEntityAttributeGroup.setThruTime(session.START_TIME_LONG);
            entityAttributeEntityAttributeGroup.store();
            
            EntityAttribute entityAttribute = entityAttributeEntityAttributeGroup.getEntityAttribute();
            EntityAttributeGroup entityAttributeGroup = entityAttributeEntityAttributeGroup.getEntityAttributeGroup();
            Integer sortOrder = entityAttributeEntityAttributeGroupValue.getSortOrder();
            
            entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().create(entityAttribute, entityAttributeGroup,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityAttributeEntityAttributeGroup(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, BasePK deletedBy) {
        entityAttributeEntityAttributeGroup.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityAttributeEntityAttributeGroup.getEntityAttributePK(), EventTypes.MODIFY.name(), entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        List<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups = getEntityAttributeEntityAttributeGroupsByEntityAttributeForUpdate(entityAttribute);
        
        entityAttributeEntityAttributeGroups.stream().forEach((entityAttributeEntityAttributeGroup) -> {
            deleteEntityAttributeEntityAttributeGroup(entityAttributeEntityAttributeGroup, deletedBy);
        });
    }
    
    public void deleteEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        List<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups = getEntityAttributeEntityAttributeGroupsByEntityAttributeGroupForUpdate(entityAttributeGroup);
        
        entityAttributeEntityAttributeGroups.stream().forEach((entityAttributeEntityAttributeGroup) -> {
            deleteEntityAttributeEntityAttributeGroup(entityAttributeEntityAttributeGroup, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Entity List Items
    // --------------------------------------------------------------------------------
    
    public EntityListItem createEntityListItem(EntityAttribute entityAttribute, String entityListItemName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        EntityListItem defaultEntityListItem = getDefaultEntityListItem(entityAttribute);
        boolean defaultFound = defaultEntityListItem != null;
        
        if(defaultFound && isDefault) {
            EntityListItemDetailValue defaultEntityListItemDetailValue = getDefaultEntityListItemDetailValueForUpdate(entityAttribute);
            
            defaultEntityListItemDetailValue.setIsDefault(Boolean.FALSE);
            updateEntityListItemFromValue(defaultEntityListItemDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        EntityListItem entityListItem = EntityListItemFactory.getInstance().create();
        EntityListItemDetail entityListItemDetail = EntityListItemDetailFactory.getInstance().create(entityListItem,
                entityAttribute, entityListItemName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityListItem.getPrimaryKey());
        entityListItem.setActiveDetail(entityListItemDetail);
        entityListItem.setLastDetail(entityListItemDetail);
        entityListItem.store();
        
        sendEventUsingNames(entityListItem.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return entityListItem;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.EntityListItem */
    public EntityListItem getEntityListItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityListItemPK pk = new EntityListItemPK(entityInstance.getEntityUniqueId());
        EntityListItem entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return entityListItem;
    }

    public EntityListItem getEntityListItemByEntityInstance(EntityInstance entityInstance) {
        return getEntityListItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityListItem getEntityListItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityListItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityListItem getEntityListItemByPK(EntityListItemPK pk) {
        return EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }
    
    public long countEntityListItems() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitylistitems, entitylistitemdetails " +
                "WHERE eli_activedetailid = elidt_entitylistitemdetailid");
    }

    private EntityListItem getDefaultEntityListItem(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityListItem entityListItem = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityListItem = EntityListItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItem;
    }
    
    public EntityListItem getDefaultEntityListItem(EntityAttribute entityAttribute) {
        return getDefaultEntityListItem(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityListItem getDefaultEntityListItemForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityListItem(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemDetailValue getDefaultEntityListItemDetailValueForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityListItemForUpdate(entityAttribute).getLastDetailForUpdate().getEntityListItemDetailValue().clone();
    }
    
    public EntityListItem getEntityListItemByName(EntityAttribute entityAttribute, String entityListItemName, EntityPermission entityPermission) {
        EntityListItem entityListItem = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_entitylistitemname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_entitylistitemname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityListItemName);
            
            entityListItem = EntityListItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItem;
    }
    
    public EntityListItem getEntityListItemByName(EntityAttribute entityAttribute, String entityListItemName) {
        return getEntityListItemByName(entityAttribute, entityListItemName, EntityPermission.READ_ONLY);
    }
    
    public EntityListItem getEntityListItemByNameForUpdate(EntityAttribute entityAttribute, String entityListItemName) {
        return getEntityListItemByName(entityAttribute, entityListItemName, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemDetailValue getEntityListItemDetailValueForUpdate(EntityListItem entityListItem) {
        return entityListItem == null? null: entityListItem.getLastDetailForUpdate().getEntityListItemDetailValue().clone();
    }
    
    public EntityListItemDetailValue getEntityListItemDetailValueByNameForUpdate(EntityAttribute entityAttribute, String entityListItemName) {
        return getEntityListItemDetailValueForUpdate(getEntityListItemByNameForUpdate(entityAttribute, entityListItemName));
    }
    
    private List<EntityListItem> getEntityListItems(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityListItem> entityListItems = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ? " +
                        "ORDER BY elidt_sortorder, elidt_entitylistitemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityListItems = EntityListItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItems;
    }
    
    public List<EntityListItem> getEntityListItems(EntityAttribute entityAttribute) {
        return getEntityListItems(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityListItem> getEntityListItemsForUpdate(EntityAttribute entityAttribute) {
        return getEntityListItems(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityListItems(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitylistitems, entitylistitemdetails " +
                "WHERE eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ?",
                entityAttribute);
    }

    public EntityListItemTransfer getEntityListItemTransfer(UserVisit userVisit, EntityListItem entityListItem, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityListItemTransferCache().getEntityListItemTransfer(entityListItem, entityInstance);
    }
    
    public List<EntityListItemTransfer> getEntityListItemTransfers(UserVisit userVisit, Collection<EntityListItem> entityListItems, EntityInstance entityInstance) {
        List<EntityListItemTransfer> entityListItemTransfers = new ArrayList<>(entityListItems.size());
        EntityListItemTransferCache entityListItemTransferCache = getCoreTransferCaches(userVisit).getEntityListItemTransferCache();

        entityListItems.stream().forEach((entityListItem) -> {
            entityListItemTransfers.add(entityListItemTransferCache.getEntityListItemTransfer(entityListItem, entityInstance));
        });

        return entityListItemTransfers;
    }

    public List<EntityListItemTransfer> getEntityListItemTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemTransfers(userVisit, getEntityListItems(entityAttribute), entityInstance);
    }

    private void updateEntityListItemFromValue(EntityListItemDetailValue entityListItemDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityListItemDetailValue.hasBeenModified()) {
            EntityListItem entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityListItemDetailValue.getEntityListItemPK());
            EntityListItemDetail entityListItemDetail = entityListItem.getActiveDetailForUpdate();
            
            entityListItemDetail.setThruTime(session.START_TIME_LONG);
            entityListItemDetail.store();
            
            EntityListItemPK entityListItemPK = entityListItemDetail.getEntityListItemPK(); // Not updated
            EntityAttribute entityAttribute = entityListItemDetail.getEntityAttribute(); // Not updated
            String entityListItemName = entityListItemDetailValue.getEntityListItemName();
            Boolean isDefault = entityListItemDetailValue.getIsDefault();
            Integer sortOrder = entityListItemDetailValue.getSortOrder();
            
            if(checkDefault) {
                EntityListItem defaultEntityListItem = getDefaultEntityListItem(entityAttribute);
                boolean defaultFound = defaultEntityListItem != null && !defaultEntityListItem.equals(entityListItem);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    EntityListItemDetailValue defaultEntityListItemDetailValue = getDefaultEntityListItemDetailValueForUpdate(entityAttribute);
                    
                    defaultEntityListItemDetailValue.setIsDefault(Boolean.FALSE);
                    updateEntityListItemFromValue(defaultEntityListItemDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            entityListItemDetail = EntityListItemDetailFactory.getInstance().create(entityListItemPK,
                    entityAttribute.getPrimaryKey(), entityListItemName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            entityListItem.setActiveDetail(entityListItemDetail);
            entityListItem.setLastDetail(entityListItemDetail);
            
            sendEventUsingNames(entityListItemPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateEntityListItemFromValue(EntityListItemDetailValue entityListItemDetailValue, BasePK updatedBy) {
        updateEntityListItemFromValue(entityListItemDetailValue, true, updatedBy);
    }
    
    public EntityListItemChoicesBean getEntityListItemChoices(String defaultEntityListItemChoice, Language language,
            boolean allowNullChoice, EntityAttribute entityAttribute) {
        List<EntityListItem> entityListItems = getEntityListItems(entityAttribute);
        int size = entityListItems.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityListItemChoice == null) {
                defaultValue = "";
            }
        }
        
        for(EntityListItem entityListItem: entityListItems) {
            EntityListItemDetail entityListItemDetail = entityListItem.getLastDetail();
            String label = getBestEntityListItemDescription(entityListItem, language);
            String value = entityListItemDetail.getEntityListItemName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultEntityListItemChoice == null? false: defaultEntityListItemChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityListItemDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityListItemChoicesBean(labels, values, defaultValue);
    }
    
    private void deleteEntityListItem(EntityListItem entityListItem, boolean checkDefault, BasePK deletedBy) {
        EntityListItemDetail entityListItemDetail = entityListItem.getLastDetailForUpdate();
        String entityAttributeTypeName = entityListItemDetail.getEntityAttribute().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        
        if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
            deleteEntityListItemAttributesByEntityListItem(entityListItem, deletedBy);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            deleteEntityMultipleListItemAttributesByEntityListItem(entityListItem, deletedBy);
        }
        
        deleteEntityListItemDescriptionsByEntityListItem(entityListItem, deletedBy);
        
        entityListItemDetail.setThruTime(session.START_TIME_LONG);
        entityListItem.setActiveDetail(null);
        entityListItem.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            EntityAttribute entityAttribute = entityListItemDetail.getEntityAttribute();
            EntityListItem defaultEntityListItem = getDefaultEntityListItem(entityAttribute);
            if(defaultEntityListItem == null) {
                List<EntityListItem> entityListItems = getEntityListItemsForUpdate(entityAttribute);

                if(!entityListItems.isEmpty()) {
                    Iterator<EntityListItem> iter = entityListItems.iterator();
                    if(iter.hasNext()) {
                        defaultEntityListItem = iter.next();
                    }
                    EntityListItemDetailValue entityListItemDetailValue = defaultEntityListItem.getLastDetailForUpdate().getEntityListItemDetailValue().clone();

                    entityListItemDetailValue.setIsDefault(Boolean.TRUE);
                    updateEntityListItemFromValue(entityListItemDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(entityListItem.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityListItem(entityListItem, true, deletedBy);
    }

    public void deleteEntityListItemsByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        List<EntityListItem> entityListItems = getEntityListItemsForUpdate(entityAttribute);
        
        entityListItems.stream().forEach((entityListItem) -> {
            deleteEntityListItem(entityListItem, false, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityListItemDescription createEntityListItemDescription(EntityListItem entityListItem, Language language, String description, BasePK createdBy) {
        EntityListItemDescription entityListItemDescription = EntityListItemDescriptionFactory.getInstance().create(entityListItem, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityListItem.getPrimaryKey(), EventTypes.MODIFY.name(), entityListItemDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityListItemDescription;
    }
    
    private EntityListItemDescription getEntityListItemDescription(EntityListItem entityListItem, Language language, EntityPermission entityPermission) {
        EntityListItemDescription entityListItemDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_lang_languageid = ? AND elid_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_lang_languageid = ? AND elid_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityListItemDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityListItemDescription = EntityListItemDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemDescription;
    }
    
    public EntityListItemDescription getEntityListItemDescription(EntityListItem entityListItem, Language language) {
        return getEntityListItemDescription(entityListItem, language, EntityPermission.READ_ONLY);
    }
    
    public EntityListItemDescription getEntityListItemDescriptionForUpdate(EntityListItem entityListItem, Language language) {
        return getEntityListItemDescription(entityListItem, language, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemDescriptionValue getEntityListItemDescriptionValue(EntityListItemDescription entityListItemDescription) {
        return entityListItemDescription == null? null: entityListItemDescription.getEntityListItemDescriptionValue().clone();
    }
    
    public EntityListItemDescriptionValue getEntityListItemDescriptionValueForUpdate(EntityListItem entityListItem, Language language) {
        return getEntityListItemDescriptionValue(getEntityListItemDescriptionForUpdate(entityListItem, language));
    }
    
    private List<EntityListItemDescription> getEntityListItemDescriptionsByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityListItemDescription> entityListItemDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions, languages " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_thrutime = ? AND elid_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityListItemDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemDescriptions = EntityListItemDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemDescriptions;
    }
    
    public List<EntityListItemDescription> getEntityListItemDescriptionsByEntityListItem(EntityListItem entityListItem) {
        return getEntityListItemDescriptionsByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityListItemDescription> getEntityListItemDescriptionsByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityListItemDescriptionsByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityListItemDescription(EntityListItem entityListItem, Language language) {
        String description;
        EntityListItemDescription entityListItemDescription = getEntityListItemDescription(entityListItem, language);
        
        if(entityListItemDescription == null && !language.getIsDefault()) {
            entityListItemDescription = getEntityListItemDescription(entityListItem, getPartyControl().getDefaultLanguage());
        }
        
        if(entityListItemDescription == null) {
            description = entityListItem.getLastDetail().getEntityListItemName();
        } else {
            description = entityListItemDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityListItemDescriptionTransfer getEntityListItemDescriptionTransfer(UserVisit userVisit, EntityListItemDescription entityListItemDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityListItemDescriptionTransferCache().getEntityListItemDescriptionTransfer(entityListItemDescription, entityInstance);
    }
    
    public List<EntityListItemDescriptionTransfer> getEntityListItemDescriptionTransfersByEntityListItem(UserVisit userVisit, EntityListItem entityListItem, EntityInstance entityInstance) {
        List<EntityListItemDescription> entityListItemDescriptions = getEntityListItemDescriptionsByEntityListItem(entityListItem);
        List<EntityListItemDescriptionTransfer> entityListItemDescriptionTransfers = new ArrayList<>(entityListItemDescriptions.size());
        EntityListItemDescriptionTransferCache entityListItemDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityListItemDescriptionTransferCache();
        
        entityListItemDescriptions.stream().forEach((entityListItemDescription) -> {
            entityListItemDescriptionTransfers.add(entityListItemDescriptionTransferCache.getEntityListItemDescriptionTransfer(entityListItemDescription, entityInstance));
        });
        
        return entityListItemDescriptionTransfers;
    }
    
    public void updateEntityListItemDescriptionFromValue(EntityListItemDescriptionValue entityListItemDescriptionValue, BasePK updatedBy) {
        if(entityListItemDescriptionValue.hasBeenModified()) {
            EntityListItemDescription entityListItemDescription = EntityListItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityListItemDescriptionValue.getPrimaryKey());
            
            entityListItemDescription.setThruTime(session.START_TIME_LONG);
            entityListItemDescription.store();
            
            EntityListItem entityListItem = entityListItemDescription.getEntityListItem();
            Language language = entityListItemDescription.getLanguage();
            String description = entityListItemDescriptionValue.getDescription();
            
            entityListItemDescription = EntityListItemDescriptionFactory.getInstance().create(entityListItem, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityListItem.getPrimaryKey(), EventTypes.MODIFY.name(), entityListItemDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityListItemDescription(EntityListItemDescription entityListItemDescription, BasePK deletedBy) {
        entityListItemDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityListItemDescription.getEntityListItemPK(), EventTypes.MODIFY.name(), entityListItemDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityListItemDescriptionsByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        List<EntityListItemDescription> entityListItemDescriptions = getEntityListItemDescriptionsByEntityListItemForUpdate(entityListItem);
        
        entityListItemDescriptions.stream().forEach((entityListItemDescription) -> {
            deleteEntityListItemDescription(entityListItemDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Integer Ranges
    // --------------------------------------------------------------------------------
    
    public EntityIntegerRange createEntityIntegerRange(EntityAttribute entityAttribute, String entityIntegerRangeName, Integer minimumIntegerValue,
            Integer maximumIntegerValue, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        EntityIntegerRange defaultEntityIntegerRange = getDefaultEntityIntegerRange(entityAttribute);
        boolean defaultFound = defaultEntityIntegerRange != null;
        
        if(defaultFound && isDefault) {
            EntityIntegerRangeDetailValue defaultEntityIntegerRangeDetailValue = getDefaultEntityIntegerRangeDetailValueForUpdate(entityAttribute);
            
            defaultEntityIntegerRangeDetailValue.setIsDefault(Boolean.FALSE);
            updateEntityIntegerRangeFromValue(defaultEntityIntegerRangeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        EntityIntegerRange entityIntegerRange = EntityIntegerRangeFactory.getInstance().create();
        EntityIntegerRangeDetail entityIntegerRangeDetail = EntityIntegerRangeDetailFactory.getInstance().create(entityIntegerRange, entityAttribute,
                entityIntegerRangeName, minimumIntegerValue, maximumIntegerValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityIntegerRange.getPrimaryKey());
        entityIntegerRange.setActiveDetail(entityIntegerRangeDetail);
        entityIntegerRange.setLastDetail(entityIntegerRangeDetail);
        entityIntegerRange.store();
        
        sendEventUsingNames(entityIntegerRange.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return entityIntegerRange;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.EntityIntegerRange */
    public EntityIntegerRange getEntityIntegerRangeByEntityInstance(EntityInstance entityInstance) {
        EntityIntegerRangePK pk = new EntityIntegerRangePK(entityInstance.getEntityUniqueId());
        EntityIntegerRange entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return entityIntegerRange;
    }
    
    private EntityIntegerRange getDefaultEntityIntegerRange(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityIntegerRange entityIntegerRange = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityIntegerRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRange;
    }
    
    public EntityIntegerRange getDefaultEntityIntegerRange(EntityAttribute entityAttribute) {
        return getDefaultEntityIntegerRange(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerRange getDefaultEntityIntegerRangeForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityIntegerRange(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerRangeDetailValue getDefaultEntityIntegerRangeDetailValueForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityIntegerRangeForUpdate(entityAttribute).getLastDetailForUpdate().getEntityIntegerRangeDetailValue().clone();
    }
    
    private EntityIntegerRange getEntityIntegerRangeByName(EntityAttribute entityAttribute, String entityIntegerRangeName, EntityPermission entityPermission) {
        EntityIntegerRange entityIntegerRange = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_entityintegerrangename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_entityintegerrangename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityIntegerRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityIntegerRangeName);
            
            entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRange;
    }
    
    public EntityIntegerRange getEntityIntegerRangeByName(EntityAttribute entityAttribute, String entityIntegerRangeName) {
        return getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerRange getEntityIntegerRangeByNameForUpdate(EntityAttribute entityAttribute, String entityIntegerRangeName) {
        return getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerRangeDetailValue getEntityIntegerRangeDetailValueForUpdate(EntityIntegerRange entityIntegerRange) {
        return entityIntegerRange == null? null: entityIntegerRange.getLastDetailForUpdate().getEntityIntegerRangeDetailValue().clone();
    }
    
    public EntityIntegerRangeDetailValue getEntityIntegerRangeDetailValueByNameForUpdate(EntityAttribute entityAttribute, String entityIntegerRangeName) {
        return getEntityIntegerRangeDetailValueForUpdate(getEntityIntegerRangeByNameForUpdate(entityAttribute, entityIntegerRangeName));
    }
    
    private List<EntityIntegerRange> getEntityIntegerRanges(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityIntegerRange> entityIntegerRanges = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid AND enirdt_ena_entityattributeid = ? " +
                        "ORDER BY enirdt_sortorder, enirdt_entityintegerrangename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid AND enirdt_ena_entityattributeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityIntegerRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityIntegerRanges = EntityIntegerRangeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRanges;
    }
    
    public List<EntityIntegerRange> getEntityIntegerRanges(EntityAttribute entityAttribute) {
        return getEntityIntegerRanges(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityIntegerRange> getEntityIntegerRangesForUpdate(EntityAttribute entityAttribute) {
        return getEntityIntegerRanges(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityIntegerRanges(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityintegerranges, entityintegerrangedetails " +
                "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid AND enirdt_ena_entityattributeid = ?",
                entityAttribute);
    }

    public EntityIntegerRangeTransfer getEntityIntegerRangeTransfer(UserVisit userVisit, EntityIntegerRange entityIntegerRange, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityIntegerRangeTransferCache().getEntityIntegerRangeTransfer(entityIntegerRange, entityInstance);
    }
    
    public List<EntityIntegerRangeTransfer> getEntityIntegerRangeTransfersByEntityAttribute(UserVisit userVisit, List<EntityIntegerRange> entityIntegerRanges, EntityInstance entityInstance) {
        List<EntityIntegerRangeTransfer> entityIntegerRangeTransfers = new ArrayList<>(entityIntegerRanges.size());
        EntityIntegerRangeTransferCache entityIntegerRangeTransferCache = getCoreTransferCaches(userVisit).getEntityIntegerRangeTransferCache();

        entityIntegerRanges.stream().forEach((entityIntegerRange) -> {
            entityIntegerRangeTransfers.add(entityIntegerRangeTransferCache.getEntityIntegerRangeTransfer(entityIntegerRange, entityInstance));
        });

        return entityIntegerRangeTransfers;
    }

    public List<EntityIntegerRangeTransfer> getEntityIntegerRangeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerRangeTransfersByEntityAttribute(userVisit, getEntityIntegerRanges(entityAttribute), entityInstance);
    }

    private void updateEntityIntegerRangeFromValue(EntityIntegerRangeDetailValue entityIntegerRangeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityIntegerRangeDetailValue.hasBeenModified()) {
            EntityIntegerRange entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityIntegerRangeDetailValue.getEntityIntegerRangePK());
            EntityIntegerRangeDetail entityIntegerRangeDetail = entityIntegerRange.getActiveDetailForUpdate();
            
            entityIntegerRangeDetail.setThruTime(session.START_TIME_LONG);
            entityIntegerRangeDetail.store();
            
            EntityIntegerRangePK entityIntegerRangePK = entityIntegerRangeDetail.getEntityIntegerRangePK(); // Not updated
            EntityAttribute entityAttribute = entityIntegerRangeDetail.getEntityAttribute(); // Not updated
            String entityIntegerRangeName = entityIntegerRangeDetailValue.getEntityIntegerRangeName();
            Integer minimumIntegerValue = entityIntegerRangeDetailValue.getMinimumIntegerValue();
            Integer maximumIntegerValue = entityIntegerRangeDetailValue.getMaximumIntegerValue();
            Boolean isDefault = entityIntegerRangeDetailValue.getIsDefault();
            Integer sortOrder = entityIntegerRangeDetailValue.getSortOrder();
            
            if(checkDefault) {
                EntityIntegerRange defaultEntityIntegerRange = getDefaultEntityIntegerRange(entityAttribute);
                boolean defaultFound = defaultEntityIntegerRange != null && !defaultEntityIntegerRange.equals(entityIntegerRange);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    EntityIntegerRangeDetailValue defaultEntityIntegerRangeDetailValue = getDefaultEntityIntegerRangeDetailValueForUpdate(entityAttribute);
                    
                    defaultEntityIntegerRangeDetailValue.setIsDefault(Boolean.FALSE);
                    updateEntityIntegerRangeFromValue(defaultEntityIntegerRangeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            entityIntegerRangeDetail = EntityIntegerRangeDetailFactory.getInstance().create(entityIntegerRangePK, entityAttribute.getPrimaryKey(), entityIntegerRangeName,
                    minimumIntegerValue, maximumIntegerValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            entityIntegerRange.setActiveDetail(entityIntegerRangeDetail);
            entityIntegerRange.setLastDetail(entityIntegerRangeDetail);
            
            sendEventUsingNames(entityIntegerRangePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateEntityIntegerRangeFromValue(EntityIntegerRangeDetailValue entityIntegerRangeDetailValue, BasePK updatedBy) {
        updateEntityIntegerRangeFromValue(entityIntegerRangeDetailValue, true, updatedBy);
    }
    
    public EntityIntegerRangeChoicesBean getEntityIntegerRangeChoices(String defaultEntityIntegerRangeChoice, Language language,
            boolean allowNullChoice, EntityAttribute entityAttribute) {
        List<EntityIntegerRange> entityIntegerRanges = getEntityIntegerRanges(entityAttribute);
        int size = entityIntegerRanges.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityIntegerRangeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(EntityIntegerRange entityIntegerRange: entityIntegerRanges) {
            EntityIntegerRangeDetail entityIntegerRangeDetail = entityIntegerRange.getLastDetail();
            String label = getBestEntityIntegerRangeDescription(entityIntegerRange, language);
            String value = entityIntegerRangeDetail.getEntityIntegerRangeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultEntityIntegerRangeChoice == null? false: defaultEntityIntegerRangeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityIntegerRangeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityIntegerRangeChoicesBean(labels, values, defaultValue);
    }
    
    private void deleteEntityIntegerRange(EntityIntegerRange entityIntegerRange, boolean checkDefault, BasePK deletedBy) {
        EntityIntegerRangeDetail entityIntegerRangeDetail = entityIntegerRange.getLastDetailForUpdate();
        
        deleteEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange, deletedBy);
        
        entityIntegerRangeDetail.setThruTime(session.START_TIME_LONG);
        entityIntegerRange.setActiveDetail(null);
        entityIntegerRange.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            EntityAttribute entityAttribute = entityIntegerRangeDetail.getEntityAttribute();
            EntityIntegerRange defaultEntityIntegerRange = getDefaultEntityIntegerRange(entityAttribute);
            if(defaultEntityIntegerRange == null) {
                List<EntityIntegerRange> entityIntegerRanges = getEntityIntegerRangesForUpdate(entityAttribute);

                if(!entityIntegerRanges.isEmpty()) {
                    Iterator<EntityIntegerRange> iter = entityIntegerRanges.iterator();
                    if(iter.hasNext()) {
                        defaultEntityIntegerRange = iter.next();
                    }
                    EntityIntegerRangeDetailValue entityIntegerRangeDetailValue = defaultEntityIntegerRange.getLastDetailForUpdate().getEntityIntegerRangeDetailValue().clone();

                    entityIntegerRangeDetailValue.setIsDefault(Boolean.TRUE);
                    updateEntityIntegerRangeFromValue(entityIntegerRangeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(entityIntegerRange.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteEntityIntegerRange(EntityIntegerRange entityIntegerRange, BasePK deletedBy) {
        deleteEntityIntegerRange(entityIntegerRange, true, deletedBy);
    }

    public void deleteEntityIntegerRangesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        List<EntityIntegerRange> entityIntegerRanges = getEntityIntegerRangesForUpdate(entityAttribute);
        
        entityIntegerRanges.stream().forEach((entityIntegerRange) -> {
            deleteEntityIntegerRange(entityIntegerRange, false, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityIntegerRangeDescription createEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language, String description, BasePK createdBy) {
        EntityIntegerRangeDescription entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().create(entityIntegerRange, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityIntegerRange.getPrimaryKey(), EventTypes.MODIFY.name(), entityIntegerRangeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityIntegerRangeDescription;
    }
    
    private EntityIntegerRangeDescription getEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language, EntityPermission entityPermission) {
        EntityIntegerRangeDescription entityIntegerRangeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_lang_languageid = ? AND enird_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_lang_languageid = ? AND enird_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityIntegerRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityIntegerRange.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRangeDescription;
    }
    
    public EntityIntegerRangeDescription getEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language) {
        return getEntityIntegerRangeDescription(entityIntegerRange, language, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerRangeDescription getEntityIntegerRangeDescriptionForUpdate(EntityIntegerRange entityIntegerRange, Language language) {
        return getEntityIntegerRangeDescription(entityIntegerRange, language, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerRangeDescriptionValue getEntityIntegerRangeDescriptionValue(EntityIntegerRangeDescription entityIntegerRangeDescription) {
        return entityIntegerRangeDescription == null? null: entityIntegerRangeDescription.getEntityIntegerRangeDescriptionValue().clone();
    }
    
    public EntityIntegerRangeDescriptionValue getEntityIntegerRangeDescriptionValueForUpdate(EntityIntegerRange entityIntegerRange, Language language) {
        return getEntityIntegerRangeDescriptionValue(getEntityIntegerRangeDescriptionForUpdate(entityIntegerRange, language));
    }
    
    private List<EntityIntegerRangeDescription> getEntityIntegerRangeDescriptionsByEntityIntegerRange(EntityIntegerRange entityIntegerRange, EntityPermission entityPermission) {
        List<EntityIntegerRangeDescription> entityIntegerRangeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions, languages " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_thrutime = ? AND enird_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityIntegerRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityIntegerRange.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerRangeDescriptions = EntityIntegerRangeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRangeDescriptions;
    }
    
    public List<EntityIntegerRangeDescription> getEntityIntegerRangeDescriptionsByEntityIntegerRange(EntityIntegerRange entityIntegerRange) {
        return getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange, EntityPermission.READ_ONLY);
    }
    
    public List<EntityIntegerRangeDescription> getEntityIntegerRangeDescriptionsByEntityIntegerRangeForUpdate(EntityIntegerRange entityIntegerRange) {
        return getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language) {
        String description;
        EntityIntegerRangeDescription entityIntegerRangeDescription = getEntityIntegerRangeDescription(entityIntegerRange, language);
        
        if(entityIntegerRangeDescription == null && !language.getIsDefault()) {
            entityIntegerRangeDescription = getEntityIntegerRangeDescription(entityIntegerRange, getPartyControl().getDefaultLanguage());
        }
        
        if(entityIntegerRangeDescription == null) {
            description = entityIntegerRange.getLastDetail().getEntityIntegerRangeName();
        } else {
            description = entityIntegerRangeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityIntegerRangeDescriptionTransfer getEntityIntegerRangeDescriptionTransfer(UserVisit userVisit, EntityIntegerRangeDescription entityIntegerRangeDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityIntegerRangeDescriptionTransferCache().getEntityIntegerRangeDescriptionTransfer(entityIntegerRangeDescription, entityInstance);
    }
    
    public List<EntityIntegerRangeDescriptionTransfer> getEntityIntegerRangeDescriptionTransfersByEntityIntegerRange(UserVisit userVisit, EntityIntegerRange entityIntegerRange, EntityInstance entityInstance) {
        List<EntityIntegerRangeDescription> entityIntegerRangeDescriptions = getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange);
        List<EntityIntegerRangeDescriptionTransfer> entityIntegerRangeDescriptionTransfers = new ArrayList<>(entityIntegerRangeDescriptions.size());
        EntityIntegerRangeDescriptionTransferCache entityIntegerRangeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityIntegerRangeDescriptionTransferCache();
        
        entityIntegerRangeDescriptions.stream().forEach((entityIntegerRangeDescription) -> {
            entityIntegerRangeDescriptionTransfers.add(entityIntegerRangeDescriptionTransferCache.getEntityIntegerRangeDescriptionTransfer(entityIntegerRangeDescription, entityInstance));
        });
        
        return entityIntegerRangeDescriptionTransfers;
    }
    
    public void updateEntityIntegerRangeDescriptionFromValue(EntityIntegerRangeDescriptionValue entityIntegerRangeDescriptionValue, BasePK updatedBy) {
        if(entityIntegerRangeDescriptionValue.hasBeenModified()) {
            EntityIntegerRangeDescription entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityIntegerRangeDescriptionValue.getPrimaryKey());
            
            entityIntegerRangeDescription.setThruTime(session.START_TIME_LONG);
            entityIntegerRangeDescription.store();
            
            EntityIntegerRange entityIntegerRange = entityIntegerRangeDescription.getEntityIntegerRange();
            Language language = entityIntegerRangeDescription.getLanguage();
            String description = entityIntegerRangeDescriptionValue.getDescription();
            
            entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().create(entityIntegerRange, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityIntegerRange.getPrimaryKey(), EventTypes.MODIFY.name(), entityIntegerRangeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityIntegerRangeDescription(EntityIntegerRangeDescription entityIntegerRangeDescription, BasePK deletedBy) {
        entityIntegerRangeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityIntegerRangeDescription.getEntityIntegerRangePK(), EventTypes.MODIFY.name(), entityIntegerRangeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityIntegerRangeDescriptionsByEntityIntegerRange(EntityIntegerRange entityIntegerRange, BasePK deletedBy) {
        List<EntityIntegerRangeDescription> entityIntegerRangeDescriptions = getEntityIntegerRangeDescriptionsByEntityIntegerRangeForUpdate(entityIntegerRange);
        
        entityIntegerRangeDescriptions.stream().forEach((entityIntegerRangeDescription) -> {
            deleteEntityIntegerRangeDescription(entityIntegerRangeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Long Ranges
    // --------------------------------------------------------------------------------
    
    public EntityLongRange createEntityLongRange(EntityAttribute entityAttribute, String entityLongRangeName, Long minimumLongValue, Long maximumLongValue,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        EntityLongRange defaultEntityLongRange = getDefaultEntityLongRange(entityAttribute);
        boolean defaultFound = defaultEntityLongRange != null;
        
        if(defaultFound && isDefault) {
            EntityLongRangeDetailValue defaultEntityLongRangeDetailValue = getDefaultEntityLongRangeDetailValueForUpdate(entityAttribute);
            
            defaultEntityLongRangeDetailValue.setIsDefault(Boolean.FALSE);
            updateEntityLongRangeFromValue(defaultEntityLongRangeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        EntityLongRange entityLongRange = EntityLongRangeFactory.getInstance().create();
        EntityLongRangeDetail entityLongRangeDetail = EntityLongRangeDetailFactory.getInstance().create(entityLongRange, entityAttribute, entityLongRangeName,
                minimumLongValue, maximumLongValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityLongRange.getPrimaryKey());
        entityLongRange.setActiveDetail(entityLongRangeDetail);
        entityLongRange.setLastDetail(entityLongRangeDetail);
        entityLongRange.store();
        
        sendEventUsingNames(entityLongRange.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return entityLongRange;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.EntityLongRange */
    public EntityLongRange getEntityLongRangeByEntityInstance(EntityInstance entityInstance) {
        EntityLongRangePK pk = new EntityLongRangePK(entityInstance.getEntityUniqueId());
        EntityLongRange entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return entityLongRange;
    }
    
    private EntityLongRange getDefaultEntityLongRange(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityLongRange entityLongRange = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityLongRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRange;
    }
    
    public EntityLongRange getDefaultEntityLongRange(EntityAttribute entityAttribute) {
        return getDefaultEntityLongRange(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityLongRange getDefaultEntityLongRangeForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityLongRange(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityLongRangeDetailValue getDefaultEntityLongRangeDetailValueForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityLongRangeForUpdate(entityAttribute).getLastDetailForUpdate().getEntityLongRangeDetailValue().clone();
    }
    
    private EntityLongRange getEntityLongRangeByName(EntityAttribute entityAttribute, String entityLongRangeName, EntityPermission entityPermission) {
        EntityLongRange entityLongRange = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_entitylongrangename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_entitylongrangename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityLongRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityLongRangeName);
            
            entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRange;
    }
    
    public EntityLongRange getEntityLongRangeByName(EntityAttribute entityAttribute, String entityLongRangeName) {
        return getEntityLongRangeByName(entityAttribute, entityLongRangeName, EntityPermission.READ_ONLY);
    }
    
    public EntityLongRange getEntityLongRangeByNameForUpdate(EntityAttribute entityAttribute, String entityLongRangeName) {
        return getEntityLongRangeByName(entityAttribute, entityLongRangeName, EntityPermission.READ_WRITE);
    }
    
    public EntityLongRangeDetailValue getEntityLongRangeDetailValueForUpdate(EntityLongRange entityLongRange) {
        return entityLongRange == null? null: entityLongRange.getLastDetailForUpdate().getEntityLongRangeDetailValue().clone();
    }
    
    public EntityLongRangeDetailValue getEntityLongRangeDetailValueByNameForUpdate(EntityAttribute entityAttribute, String entityLongRangeName) {
        return getEntityLongRangeDetailValueForUpdate(getEntityLongRangeByNameForUpdate(entityAttribute, entityLongRangeName));
    }
    
    private List<EntityLongRange> getEntityLongRanges(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityLongRange> entityLongRanges = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid AND enlrdt_ena_entityattributeid = ? " +
                        "ORDER BY enlrdt_sortorder, enlrdt_entitylongrangename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid AND enlrdt_ena_entityattributeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityLongRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityLongRanges = EntityLongRangeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRanges;
    }
    
    public List<EntityLongRange> getEntityLongRanges(EntityAttribute entityAttribute) {
        return getEntityLongRanges(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityLongRange> getEntityLongRangesForUpdate(EntityAttribute entityAttribute) {
        return getEntityLongRanges(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityLongRanges(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitylongranges, entitylongrangedetails " +
                "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid AND enlrdt_ena_entityattributeid = ?",
                entityAttribute);
    }

    public EntityLongRangeTransfer getEntityLongRangeTransfer(UserVisit userVisit, EntityLongRange entityLongRange, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityLongRangeTransferCache().getEntityLongRangeTransfer(entityLongRange, entityInstance);
    }
    
    public List<EntityLongRangeTransfer> getEntityLongRangeTransfersByEntityAttribute(UserVisit userVisit, List<EntityLongRange> entityLongRanges, EntityInstance entityInstance) {
        List<EntityLongRangeTransfer> entityLongRangeTransfers = new ArrayList<>(entityLongRanges.size());
        EntityLongRangeTransferCache entityLongRangeTransferCache = getCoreTransferCaches(userVisit).getEntityLongRangeTransferCache();

        entityLongRanges.stream().forEach((entityLongRange) -> {
            entityLongRangeTransfers.add(entityLongRangeTransferCache.getEntityLongRangeTransfer(entityLongRange, entityInstance));
        });

        return entityLongRangeTransfers;
    }

    public List<EntityLongRangeTransfer> getEntityLongRangeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongRangeTransfersByEntityAttribute(userVisit, getEntityLongRanges(entityAttribute), entityInstance);
    }

    private void updateEntityLongRangeFromValue(EntityLongRangeDetailValue entityLongRangeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityLongRangeDetailValue.hasBeenModified()) {
            EntityLongRange entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityLongRangeDetailValue.getEntityLongRangePK());
            EntityLongRangeDetail entityLongRangeDetail = entityLongRange.getActiveDetailForUpdate();
            
            entityLongRangeDetail.setThruTime(session.START_TIME_LONG);
            entityLongRangeDetail.store();
            
            EntityLongRangePK entityLongRangePK = entityLongRangeDetail.getEntityLongRangePK(); // Not updated
            EntityAttribute entityAttribute = entityLongRangeDetail.getEntityAttribute(); // Not updated
            String entityLongRangeName = entityLongRangeDetailValue.getEntityLongRangeName();
            Long minimumLongValue = entityLongRangeDetailValue.getMinimumLongValue();
            Long maximumLongValue = entityLongRangeDetailValue.getMaximumLongValue();
            Boolean isDefault = entityLongRangeDetailValue.getIsDefault();
            Integer sortOrder = entityLongRangeDetailValue.getSortOrder();
            
            if(checkDefault) {
                EntityLongRange defaultEntityLongRange = getDefaultEntityLongRange(entityAttribute);
                boolean defaultFound = defaultEntityLongRange != null && !defaultEntityLongRange.equals(entityLongRange);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    EntityLongRangeDetailValue defaultEntityLongRangeDetailValue = getDefaultEntityLongRangeDetailValueForUpdate(entityAttribute);
                    
                    defaultEntityLongRangeDetailValue.setIsDefault(Boolean.FALSE);
                    updateEntityLongRangeFromValue(defaultEntityLongRangeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            entityLongRangeDetail = EntityLongRangeDetailFactory.getInstance().create(entityLongRangePK, entityAttribute.getPrimaryKey(), entityLongRangeName,
                    minimumLongValue, maximumLongValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            entityLongRange.setActiveDetail(entityLongRangeDetail);
            entityLongRange.setLastDetail(entityLongRangeDetail);
            
            sendEventUsingNames(entityLongRangePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateEntityLongRangeFromValue(EntityLongRangeDetailValue entityLongRangeDetailValue, BasePK updatedBy) {
        updateEntityLongRangeFromValue(entityLongRangeDetailValue, true, updatedBy);
    }
    
    public EntityLongRangeChoicesBean getEntityLongRangeChoices(String defaultEntityLongRangeChoice, Language language,
            boolean allowNullChoice, EntityAttribute entityAttribute) {
        List<EntityLongRange> entityLongRanges = getEntityLongRanges(entityAttribute);
        int size = entityLongRanges.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityLongRangeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(EntityLongRange entityLongRange: entityLongRanges) {
            EntityLongRangeDetail entityLongRangeDetail = entityLongRange.getLastDetail();
            String label = getBestEntityLongRangeDescription(entityLongRange, language);
            String value = entityLongRangeDetail.getEntityLongRangeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultEntityLongRangeChoice == null? false: defaultEntityLongRangeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityLongRangeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityLongRangeChoicesBean(labels, values, defaultValue);
    }
    
    private void deleteEntityLongRange(EntityLongRange entityLongRange, boolean checkDefault, BasePK deletedBy) {
        EntityLongRangeDetail entityLongRangeDetail = entityLongRange.getLastDetailForUpdate();
        
        deleteEntityLongRangeDescriptionsByEntityLongRange(entityLongRange, deletedBy);
        
        entityLongRangeDetail.setThruTime(session.START_TIME_LONG);
        entityLongRange.setActiveDetail(null);
        entityLongRange.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            EntityAttribute entityAttribute = entityLongRangeDetail.getEntityAttribute();
            EntityLongRange defaultEntityLongRange = getDefaultEntityLongRange(entityAttribute);
            if(defaultEntityLongRange == null) {
                List<EntityLongRange> entityLongRanges = getEntityLongRangesForUpdate(entityAttribute);

                if(!entityLongRanges.isEmpty()) {
                    Iterator<EntityLongRange> iter = entityLongRanges.iterator();
                    if(iter.hasNext()) {
                        defaultEntityLongRange = iter.next();
                    }
                    EntityLongRangeDetailValue entityLongRangeDetailValue = defaultEntityLongRange.getLastDetailForUpdate().getEntityLongRangeDetailValue().clone();

                    entityLongRangeDetailValue.setIsDefault(Boolean.TRUE);
                    updateEntityLongRangeFromValue(entityLongRangeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(entityLongRange.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteEntityLongRange(EntityLongRange entityLongRange, BasePK deletedBy) {
        deleteEntityLongRange(entityLongRange, true, deletedBy);
    }

    public void deleteEntityLongRangesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        List<EntityLongRange> entityLongRanges = getEntityLongRangesForUpdate(entityAttribute);
        
        entityLongRanges.stream().forEach((entityLongRange) -> {
            deleteEntityLongRange(entityLongRange, false, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityLongRangeDescription createEntityLongRangeDescription(EntityLongRange entityLongRange, Language language, String description, BasePK createdBy) {
        EntityLongRangeDescription entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().create(entityLongRange, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityLongRange.getPrimaryKey(), EventTypes.MODIFY.name(), entityLongRangeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityLongRangeDescription;
    }
    
    private EntityLongRangeDescription getEntityLongRangeDescription(EntityLongRange entityLongRange, Language language, EntityPermission entityPermission) {
        EntityLongRangeDescription entityLongRangeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_lang_languageid = ? AND enlrd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_lang_languageid = ? AND enlrd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityLongRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityLongRange.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRangeDescription;
    }
    
    public EntityLongRangeDescription getEntityLongRangeDescription(EntityLongRange entityLongRange, Language language) {
        return getEntityLongRangeDescription(entityLongRange, language, EntityPermission.READ_ONLY);
    }
    
    public EntityLongRangeDescription getEntityLongRangeDescriptionForUpdate(EntityLongRange entityLongRange, Language language) {
        return getEntityLongRangeDescription(entityLongRange, language, EntityPermission.READ_WRITE);
    }
    
    public EntityLongRangeDescriptionValue getEntityLongRangeDescriptionValue(EntityLongRangeDescription entityLongRangeDescription) {
        return entityLongRangeDescription == null? null: entityLongRangeDescription.getEntityLongRangeDescriptionValue().clone();
    }
    
    public EntityLongRangeDescriptionValue getEntityLongRangeDescriptionValueForUpdate(EntityLongRange entityLongRange, Language language) {
        return getEntityLongRangeDescriptionValue(getEntityLongRangeDescriptionForUpdate(entityLongRange, language));
    }
    
    private List<EntityLongRangeDescription> getEntityLongRangeDescriptionsByEntityLongRange(EntityLongRange entityLongRange, EntityPermission entityPermission) {
        List<EntityLongRangeDescription> entityLongRangeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions, languages " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_thrutime = ? AND enlrd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityLongRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityLongRange.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongRangeDescriptions = EntityLongRangeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRangeDescriptions;
    }
    
    public List<EntityLongRangeDescription> getEntityLongRangeDescriptionsByEntityLongRange(EntityLongRange entityLongRange) {
        return getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange, EntityPermission.READ_ONLY);
    }
    
    public List<EntityLongRangeDescription> getEntityLongRangeDescriptionsByEntityLongRangeForUpdate(EntityLongRange entityLongRange) {
        return getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityLongRangeDescription(EntityLongRange entityLongRange, Language language) {
        String description;
        EntityLongRangeDescription entityLongRangeDescription = getEntityLongRangeDescription(entityLongRange, language);
        
        if(entityLongRangeDescription == null && !language.getIsDefault()) {
            entityLongRangeDescription = getEntityLongRangeDescription(entityLongRange, getPartyControl().getDefaultLanguage());
        }
        
        if(entityLongRangeDescription == null) {
            description = entityLongRange.getLastDetail().getEntityLongRangeName();
        } else {
            description = entityLongRangeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityLongRangeDescriptionTransfer getEntityLongRangeDescriptionTransfer(UserVisit userVisit, EntityLongRangeDescription entityLongRangeDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityLongRangeDescriptionTransferCache().getEntityLongRangeDescriptionTransfer(entityLongRangeDescription, entityInstance);
    }
    
    public List<EntityLongRangeDescriptionTransfer> getEntityLongRangeDescriptionTransfersByEntityLongRange(UserVisit userVisit, EntityLongRange entityLongRange, EntityInstance entityInstance) {
        List<EntityLongRangeDescription> entityLongRangeDescriptions = getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange);
        List<EntityLongRangeDescriptionTransfer> entityLongRangeDescriptionTransfers = new ArrayList<>(entityLongRangeDescriptions.size());
        EntityLongRangeDescriptionTransferCache entityLongRangeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityLongRangeDescriptionTransferCache();
        
        entityLongRangeDescriptions.stream().forEach((entityLongRangeDescription) -> {
            entityLongRangeDescriptionTransfers.add(entityLongRangeDescriptionTransferCache.getEntityLongRangeDescriptionTransfer(entityLongRangeDescription, entityInstance));
        });
        
        return entityLongRangeDescriptionTransfers;
    }
    
    public void updateEntityLongRangeDescriptionFromValue(EntityLongRangeDescriptionValue entityLongRangeDescriptionValue, BasePK updatedBy) {
        if(entityLongRangeDescriptionValue.hasBeenModified()) {
            EntityLongRangeDescription entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityLongRangeDescriptionValue.getPrimaryKey());
            
            entityLongRangeDescription.setThruTime(session.START_TIME_LONG);
            entityLongRangeDescription.store();
            
            EntityLongRange entityLongRange = entityLongRangeDescription.getEntityLongRange();
            Language language = entityLongRangeDescription.getLanguage();
            String description = entityLongRangeDescriptionValue.getDescription();
            
            entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().create(entityLongRange, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityLongRange.getPrimaryKey(), EventTypes.MODIFY.name(), entityLongRangeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityLongRangeDescription(EntityLongRangeDescription entityLongRangeDescription, BasePK deletedBy) {
        entityLongRangeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityLongRangeDescription.getEntityLongRangePK(), EventTypes.MODIFY.name(), entityLongRangeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityLongRangeDescriptionsByEntityLongRange(EntityLongRange entityLongRange, BasePK deletedBy) {
        List<EntityLongRangeDescription> entityLongRangeDescriptions = getEntityLongRangeDescriptionsByEntityLongRangeForUpdate(entityLongRange);
        
        entityLongRangeDescriptions.stream().forEach((entityLongRangeDescription) -> {
            deleteEntityLongRangeDescription(entityLongRangeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Components
    // --------------------------------------------------------------------------------
    
    public Component createComponent(ComponentVendor componentVendor, String componentName, String description, BasePK createdBy) {
        Component component = ComponentFactory.getInstance().create();
        ComponentDetail componentDetail = ComponentDetailFactory.getInstance().create(componentVendor, component,
                componentName, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        component = ComponentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, component.getPrimaryKey());
        component.setActiveDetail(componentDetail);
        component.setLastDetail(componentDetail);
        component.store();
        
        return component;
    }
    
    public Component getComponentByName(ComponentVendor componentVendor, String componentName) {
        Component component = null;
        
        try {
            PreparedStatement ps = ComponentFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM components, componentdetails " +
                    "WHERE cpnt_componentid = cpntd_cpnt_componentid AND cpntd_cvnd_componentvendorid = ? " +
                    "AND cpntd_componentname = ? AND cpntd_thrutime = ?");
            
            ps.setLong(1, componentVendor.getPrimaryKey().getEntityId());
            ps.setString(2, componentName);
            ps.setLong(3, Session.MAX_TIME);
            
            component = ComponentFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return component;
    }
    
    // --------------------------------------------------------------------------------
    //   Component Versions
    // --------------------------------------------------------------------------------
    
    public ComponentVersion createComponentVersion(Component component, Integer majorRevision, Integer minorRevision,
            ComponentStage componentStage, Integer buildNumber,
            BasePK createdBy) {
        
        return ComponentVersionFactory.getInstance().create(component, majorRevision, minorRevision, componentStage,
                buildNumber, session.START_TIME_LONG, Session.MAX_TIME_LONG);
    }
    
    public ComponentVersion getComponentVersion(Component component) {
        ComponentVersion componentVersion = null;
        
        try {
            PreparedStatement ps = ComponentVersionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM componentversions " +
                    "WHERE cvrs_cpnt_componentid = ? AND cvrs_thrutime = ?");
            
            ps.setLong(1, component.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            componentVersion = ComponentVersionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return componentVersion;
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------
    
    public MimeTypeUsageType createMimeTypeUsageType(String mimeTypeUsageTypeName, Boolean isDefault, Integer sortOrder) {
        return MimeTypeUsageTypeFactory.getInstance().create(mimeTypeUsageTypeName, isDefault, sortOrder);
    }
    
    public List<MimeTypeUsageType> getMimeTypeUsageTypes() {
        PreparedStatement ps = MimeTypeUsageTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM mimetypeusagetypes " +
                "ORDER BY mtyput_sortorder, mtyput_mimetypeusagetypename");
        
        return MimeTypeUsageTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public MimeTypeUsageType getMimeTypeUsageTypeByName(String mimeTypeUsageTypeName) {
        MimeTypeUsageType mimeTypeUsageType = null;
        
        try {
            PreparedStatement ps = MimeTypeUsageTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM mimetypeusagetypes " +
                    "WHERE mtyput_mimetypeusagetypename = ?");
            
            ps.setString(1, mimeTypeUsageTypeName);
            
            mimeTypeUsageType = MimeTypeUsageTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeUsageType;
    }
    
    public MimeTypeUsageTypeChoicesBean getMimeTypeUsageTypeChoices(String defaultMimeTypeUsageTypeChoice, Language language,
            boolean allowNullChoice) {
        List<MimeTypeUsageType> mimeTypeUsageTypes = getMimeTypeUsageTypes();
        int size = mimeTypeUsageTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultMimeTypeUsageTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(MimeTypeUsageType mimeTypeUsageType: mimeTypeUsageTypes) {
            String label = getBestMimeTypeUsageTypeDescription(mimeTypeUsageType, language);
            String value = mimeTypeUsageType.getMimeTypeUsageTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultMimeTypeUsageTypeChoice == null? false: defaultMimeTypeUsageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && mimeTypeUsageType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new MimeTypeUsageTypeChoicesBean(labels, values, defaultValue);
    }
    
    public MimeTypeUsageTypeTransfer getMimeTypeUsageTypeTransfer(UserVisit userVisit, MimeTypeUsageType mimeTypeUsageType) {
        return getCoreTransferCaches(userVisit).getMimeTypeUsageTypeTransferCache().getMimeTypeUsageTypeTransfer(mimeTypeUsageType);
    }

    public List<MimeTypeUsageTypeTransfer> getMimeTypeUsageTypeTransfers(UserVisit userVisit, Collection<MimeTypeUsageType> mimeTypeUsageTypes) {
        List<MimeTypeUsageTypeTransfer> mimeTypeUsageTypeTransfers = new ArrayList<>(mimeTypeUsageTypes.size());
        MimeTypeUsageTypeTransferCache mimeTypeUsageTypeTransferCache = getCoreTransferCaches(userVisit).getMimeTypeUsageTypeTransferCache();

        mimeTypeUsageTypes.stream().forEach((mimeTypeUsageType) -> {
            mimeTypeUsageTypeTransfers.add(mimeTypeUsageTypeTransferCache.getMimeTypeUsageTypeTransfer(mimeTypeUsageType));
        });

        return mimeTypeUsageTypeTransfers;
    }

    public List<MimeTypeUsageTypeTransfer> getMimeTypeUsageTypeTransfers(UserVisit userVisit) {
        return getMimeTypeUsageTypeTransfers(userVisit, getMimeTypeUsageTypes());
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    public MimeTypeUsageTypeDescription createMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language, String description) {
        return MimeTypeUsageTypeDescriptionFactory.getInstance().create(mimeTypeUsageType, language, description);
    }
    
    public MimeTypeUsageTypeDescription getMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language) {
        MimeTypeUsageTypeDescription mimeTypeUsageTypeDescription = null;
        
        try {
            PreparedStatement ps = MimeTypeUsageTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM mimetypeusagetypedescriptions " +
                    "WHERE mtyputd_mtyput_mimetypeusagetypeid = ? AND mtyputd_lang_languageid = ?");
            
            ps.setLong(1, mimeTypeUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            mimeTypeUsageTypeDescription = MimeTypeUsageTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeUsageTypeDescription;
    }
    
    public String getBestMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language) {
        String description;
        MimeTypeUsageTypeDescription mimeTypeUsageTypeDescription = getMimeTypeUsageTypeDescription(mimeTypeUsageType, language);
        
        if(mimeTypeUsageTypeDescription == null && !language.getIsDefault()) {
            mimeTypeUsageTypeDescription = getMimeTypeUsageTypeDescription(mimeTypeUsageType, getPartyControl().getDefaultLanguage());
        }
        
        if(mimeTypeUsageTypeDescription == null) {
            description = mimeTypeUsageType.getMimeTypeUsageTypeName();
        } else {
            description = mimeTypeUsageTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Types
    // --------------------------------------------------------------------------------

    public MimeType createMimeType(String mimeTypeName, EntityAttributeType entityAttributeType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        MimeType defaultMimeType = getDefaultMimeType();
        boolean defaultFound = defaultMimeType != null;

        if(defaultFound && isDefault) {
            MimeTypeDetailValue defaultMimeTypeDetailValue = getDefaultMimeTypeDetailValueForUpdate();

            defaultMimeTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateMimeTypeFromValue(defaultMimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        MimeType mimeType = MimeTypeFactory.getInstance().create();
        MimeTypeDetail mimeTypeDetail = MimeTypeDetailFactory.getInstance().create(mimeType, mimeTypeName, entityAttributeType, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        mimeType = MimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                mimeType.getPrimaryKey());
        mimeType.setActiveDetail(mimeTypeDetail);
        mimeType.setLastDetail(mimeTypeDetail);
        mimeType.store();

        sendEventUsingNames(mimeType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return mimeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.MimeType */
    public MimeType getMimeTypeByEntityInstance(EntityInstance entityInstance) {
        MimeTypePK pk = new MimeTypePK(entityInstance.getEntityUniqueId());
        MimeType mimeType = MimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return mimeType;
    }

    private static final Map<EntityPermission, String> getMimeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM mimetypes, mimetypedetails " +
                "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid " +
                "AND mtypdt_mimetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM mimetypes, mimetypedetails " +
                "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid " +
                "AND mtypdt_mimetypename = ? " +
                "FOR UPDATE");
        getMimeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private MimeType getMimeTypeByName(String mimeTypeName, EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getMimeTypeByNameQueries, mimeTypeName);
    }

    public MimeType getMimeTypeByName(String mimeTypeName) {
        return getMimeTypeByName(mimeTypeName, EntityPermission.READ_ONLY);
    }

    public MimeType getMimeTypeByNameForUpdate(String mimeTypeName) {
        return getMimeTypeByName(mimeTypeName, EntityPermission.READ_WRITE);
    }

    public MimeTypeDetailValue getMimeTypeDetailValueForUpdate(MimeType mimeType) {
        return mimeType == null? null: mimeType.getLastDetailForUpdate().getMimeTypeDetailValue().clone();
    }

    public MimeTypeDetailValue getMimeTypeDetailValueByNameForUpdate(String mimeTypeName) {
        return getMimeTypeDetailValueForUpdate(getMimeTypeByNameForUpdate(mimeTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultMimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtypdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultMimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private MimeType getDefaultMimeType(EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultMimeTypeQueries);
    }

    public MimeType getDefaultMimeType() {
        return getDefaultMimeType(EntityPermission.READ_ONLY);
    }

    public MimeType getDefaultMimeTypeForUpdate() {
        return getDefaultMimeType(EntityPermission.READ_WRITE);
    }

    public MimeTypeDetailValue getDefaultMimeTypeDetailValueForUpdate() {
        return getDefaultMimeTypeForUpdate().getLastDetailForUpdate().getMimeTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getMimeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "FOR UPDATE");
        getMimeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeType> getMimeTypes(EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypesQueries);
    }

    public List<MimeType> getMimeTypes() {
        return getMimeTypes(EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesForUpdate() {
        return getMimeTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getMimeTypesByEntityAttributeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtypdt_enat_entityattributetypeid = ? "
                + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtypdt_enat_entityattributetypeid = ?"
                + "FOR UPDATE");
        getMimeTypesByEntityAttributeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeType> getMimeTypesByEntityAttributeType(EntityAttributeType entityAttributeType, EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypesByEntityAttributeTypeQueries,
                entityAttributeType);
    }

    public List<MimeType> getMimeTypesByEntityAttributeType(EntityAttributeType entityAttributeType) {
        return getMimeTypesByEntityAttributeType(entityAttributeType, EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesByEntityAttributeTypeForUpdate(EntityAttributeType entityAttributeType) {
        return getMimeTypesByEntityAttributeType(entityAttributeType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getMimeTypesByMimeTypeUsageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails, mimetypeusages "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtyp_mimetypeid = mtypu_mtyp_mimetypeid AND mtypu_mtyput_mimetypeusagetypeid = ? "
                + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails, mimetypeusages "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtyp_mimetypeid = mtypu_mtyp_mimetypeid AND mtypu_mtyput_mimetypeusagetypeid = ? "
                + "FOR UPDATE");
        getMimeTypesByMimeTypeUsageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeType> getMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType, EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypesByMimeTypeUsageTypeQueries,
                mimeTypeUsageType);
    }

    public List<MimeType> getMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypesByMimeTypeUsageType(mimeTypeUsageType, EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesByMimeTypeUsageTypeForUpdate(MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypesByMimeTypeUsageType(mimeTypeUsageType, EntityPermission.READ_WRITE);
    }

    public MimeTypeTransfer getMimeTypeTransfer(UserVisit userVisit, MimeType mimeType) {
        return getCoreTransferCaches(userVisit).getMimeTypeTransferCache().getMimeTypeTransfer(mimeType);
    }

    public List<MimeTypeTransfer> getMimeTypeTransfers(UserVisit userVisit, Collection<MimeType> mimeTypes) {
        List<MimeTypeTransfer> mimeTypeTransfers = new ArrayList<>(mimeTypes.size());
        MimeTypeTransferCache mimeTypeTransferCache = getCoreTransferCaches(userVisit).getMimeTypeTransferCache();

        mimeTypes.stream().forEach((mimeType) -> {
            mimeTypeTransfers.add(mimeTypeTransferCache.getMimeTypeTransfer(mimeType));
        });

        return mimeTypeTransfers;
    }

    public List<MimeTypeTransfer> getMimeTypeTransfers(UserVisit userVisit) {
        return getMimeTypeTransfers(userVisit, getMimeTypes());
    }

    public List<MimeTypeTransfer> getMimeTypeTransfersByEntityAttributeType(UserVisit userVisit,
            EntityAttributeType entityAttributeType) {
        return getMimeTypeTransfers(userVisit, getMimeTypesByEntityAttributeType(entityAttributeType));
    }

    public List<MimeTypeTransfer> getMimeTypeTransfersByMimeTypeUsageType(UserVisit userVisit,
            MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypeTransfers(userVisit, getMimeTypesByMimeTypeUsageType(mimeTypeUsageType));
    }

    public MimeTypeChoicesBean getMimeTypeChoices(String defaultMimeTypeChoice, Language language, boolean allowNullChoice) {
        List<MimeType> mimeTypes = getMimeTypes();
        int size = mimeTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultMimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(MimeType mimeType: mimeTypes) {
            MimeTypeDetail mimeTypeDetail = mimeType.getLastDetail();

            String label = getBestMimeTypeDescription(mimeType, language);
            String value = mimeTypeDetail.getMimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultMimeTypeChoice == null? false: defaultMimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && mimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new MimeTypeChoicesBean(labels, values, defaultValue);
    }

    public MimeTypeChoicesBean getMimeTypeChoices(MimeTypeUsageType mimeTypeUsageType, String defaultMimeTypeChoice, Language language,
            boolean allowNullChoice) {
        List<MimeType> mimeTypes = getMimeTypesByMimeTypeUsageType(mimeTypeUsageType);
        int size = mimeTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultMimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(MimeType mimeType: mimeTypes) {
            MimeTypeDetail mimeTypeDetail = mimeType.getLastDetail();

            String label = getBestMimeTypeDescription(mimeType, language);
            String value = mimeTypeDetail.getMimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultMimeTypeChoice == null? false: defaultMimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && mimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new MimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateMimeTypeFromValue(MimeTypeDetailValue mimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(mimeTypeDetailValue.hasBeenModified()) {
            MimeType mimeType = MimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     mimeTypeDetailValue.getMimeTypePK());
            MimeTypeDetail mimeTypeDetail = mimeType.getActiveDetailForUpdate();

            mimeTypeDetail.setThruTime(session.START_TIME_LONG);
            mimeTypeDetail.store();

            MimeTypePK mimeTypePK = mimeTypeDetail.getMimeTypePK(); // Not updated
            String mimeTypeName = mimeTypeDetailValue.getMimeTypeName();
            EntityAttributeTypePK entityAttributeTypePK = mimeTypeDetail.getEntityAttributeTypePK(); // Not updated
            Boolean isDefault = mimeTypeDetailValue.getIsDefault();
            Integer sortOrder = mimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                MimeType defaultMimeType = getDefaultMimeType();
                boolean defaultFound = defaultMimeType != null && !defaultMimeType.equals(mimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    MimeTypeDetailValue defaultMimeTypeDetailValue = getDefaultMimeTypeDetailValueForUpdate();

                    defaultMimeTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateMimeTypeFromValue(defaultMimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            mimeTypeDetail = MimeTypeDetailFactory.getInstance().create(mimeTypePK, mimeTypeName, entityAttributeTypePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            mimeType.setActiveDetail(mimeTypeDetail);
            mimeType.setLastDetail(mimeTypeDetail);

            sendEventUsingNames(mimeTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateMimeTypeFromValue(MimeTypeDetailValue mimeTypeDetailValue, BasePK updatedBy) {
        updateMimeTypeFromValue(mimeTypeDetailValue, true, updatedBy);
    }

    public void deleteMimeType(MimeType mimeType, BasePK deletedBy) {
        deleteMimeTypeDescriptionsByMimeType(mimeType, deletedBy);

        MimeTypeDetail mimeTypeDetail = mimeType.getLastDetailForUpdate();
        mimeTypeDetail.setThruTime(session.START_TIME_LONG);
        mimeType.setActiveDetail(null);
        mimeType.store();

        // Check for default, and pick one if necessary
        MimeType defaultMimeType = getDefaultMimeType();
        if(defaultMimeType == null) {
            List<MimeType> mimeTypes = getMimeTypesForUpdate();

            if(!mimeTypes.isEmpty()) {
                Iterator<MimeType> iter = mimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultMimeType = iter.next();
                }
                MimeTypeDetailValue mimeTypeDetailValue = defaultMimeType.getLastDetailForUpdate().getMimeTypeDetailValue().clone();

                mimeTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateMimeTypeFromValue(mimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(mimeType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Descriptions
    // --------------------------------------------------------------------------------

    public MimeTypeDescription createMimeTypeDescription(MimeType mimeType,
            Language language, String description, BasePK createdBy) {
        MimeTypeDescription mimeTypeDescription = MimeTypeDescriptionFactory.getInstance().create(mimeType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(mimeType.getPrimaryKey(), EventTypes.MODIFY.name(), mimeTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return mimeTypeDescription;
    }

    private static final Map<EntityPermission, String> getMimeTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypedescriptions "
                + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_lang_languageid = ? AND mtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypedescriptions "
                + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_lang_languageid = ? AND mtypd_thrutime = ? "
                + "FOR UPDATE");
        getMimeTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private MimeTypeDescription getMimeTypeDescription(MimeType mimeType,
            Language language, EntityPermission entityPermission) {
        return MimeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getMimeTypeDescriptionQueries,
                mimeType, language, Session.MAX_TIME);
    }

    public MimeTypeDescription getMimeTypeDescription(MimeType mimeType, Language language) {
        return getMimeTypeDescription(mimeType, language, EntityPermission.READ_ONLY);
    }

    public MimeTypeDescription getMimeTypeDescriptionForUpdate(MimeType mimeType, Language language) {
        return getMimeTypeDescription(mimeType, language, EntityPermission.READ_WRITE);
    }

    public MimeTypeDescriptionValue getMimeTypeDescriptionValue(MimeTypeDescription mimeTypeDescription) {
        return mimeTypeDescription == null? null: mimeTypeDescription.getMimeTypeDescriptionValue().clone();
    }

    public MimeTypeDescriptionValue getMimeTypeDescriptionValueForUpdate(MimeType mimeType, Language language) {
        return getMimeTypeDescriptionValue(getMimeTypeDescriptionForUpdate(mimeType, language));
    }

    private static final Map<EntityPermission, String> getMimeTypeDescriptionsByMimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypedescriptions, languages "
                + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_thrutime = ? AND mtypd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypedescriptions "
                + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_thrutime = ? "
                + "FOR UPDATE");
        getMimeTypeDescriptionsByMimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeTypeDescription> getMimeTypeDescriptionsByMimeType(MimeType mimeType,
            EntityPermission entityPermission) {
        return MimeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypeDescriptionsByMimeTypeQueries,
                mimeType, Session.MAX_TIME);
    }

    public List<MimeTypeDescription> getMimeTypeDescriptionsByMimeType(MimeType mimeType) {
        return getMimeTypeDescriptionsByMimeType(mimeType, EntityPermission.READ_ONLY);
    }

    public List<MimeTypeDescription> getMimeTypeDescriptionsByMimeTypeForUpdate(MimeType mimeType) {
        return getMimeTypeDescriptionsByMimeType(mimeType, EntityPermission.READ_WRITE);
    }

    public String getBestMimeTypeDescription(MimeType mimeType, Language language) {
        String description;
        MimeTypeDescription mimeTypeDescription = getMimeTypeDescription(mimeType, language);

        if(mimeTypeDescription == null && !language.getIsDefault()) {
            mimeTypeDescription = getMimeTypeDescription(mimeType, getPartyControl().getDefaultLanguage());
        }

        if(mimeTypeDescription == null) {
            description = mimeType.getLastDetail().getMimeTypeName();
        } else {
            description = mimeTypeDescription.getDescription();
        }

        return description;
    }

    public MimeTypeDescriptionTransfer getMimeTypeDescriptionTransfer(UserVisit userVisit, MimeTypeDescription mimeTypeDescription) {
        return getCoreTransferCaches(userVisit).getMimeTypeDescriptionTransferCache().getMimeTypeDescriptionTransfer(mimeTypeDescription);
    }

    public List<MimeTypeDescriptionTransfer> getMimeTypeDescriptionTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        List<MimeTypeDescription> mimeTypeDescriptions = getMimeTypeDescriptionsByMimeType(mimeType);
        List<MimeTypeDescriptionTransfer> mimeTypeDescriptionTransfers = new ArrayList<>(mimeTypeDescriptions.size());
        MimeTypeDescriptionTransferCache mimeTypeDescriptionTransferCache = getCoreTransferCaches(userVisit).getMimeTypeDescriptionTransferCache();

        mimeTypeDescriptions.stream().forEach((mimeTypeDescription) -> {
            mimeTypeDescriptionTransfers.add(mimeTypeDescriptionTransferCache.getMimeTypeDescriptionTransfer(mimeTypeDescription));
        });

        return mimeTypeDescriptionTransfers;
    }

    public void updateMimeTypeDescriptionFromValue(MimeTypeDescriptionValue mimeTypeDescriptionValue, BasePK updatedBy) {
        if(mimeTypeDescriptionValue.hasBeenModified()) {
            MimeTypeDescription mimeTypeDescription = MimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    mimeTypeDescriptionValue.getPrimaryKey());

            mimeTypeDescription.setThruTime(session.START_TIME_LONG);
            mimeTypeDescription.store();

            MimeType mimeType = mimeTypeDescription.getMimeType();
            Language language = mimeTypeDescription.getLanguage();
            String description = mimeTypeDescriptionValue.getDescription();

            mimeTypeDescription = MimeTypeDescriptionFactory.getInstance().create(mimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(mimeType.getPrimaryKey(), EventTypes.MODIFY.name(), mimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteMimeTypeDescription(MimeTypeDescription mimeTypeDescription, BasePK deletedBy) {
        mimeTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(mimeTypeDescription.getMimeTypePK(), EventTypes.MODIFY.name(), mimeTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteMimeTypeDescriptionsByMimeType(MimeType mimeType, BasePK deletedBy) {
        List<MimeTypeDescription> mimeTypeDescriptions = getMimeTypeDescriptionsByMimeTypeForUpdate(mimeType);

        mimeTypeDescriptions.stream().forEach((mimeTypeDescription) -> {
            deleteMimeTypeDescription(mimeTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------
    
    public MimeTypeUsage createMimeTypeUsage(MimeType mimeType, MimeTypeUsageType mimeTypeUsageType) {
        return MimeTypeUsageFactory.getInstance().create(mimeType, mimeTypeUsageType);
    }
    
    public MimeTypeUsage getMimeTypeUsage(MimeType mimeType, MimeTypeUsageType mimeTypeUsageType) {
        MimeTypeUsage mimeTypeUsage = null;
        
        try {
            PreparedStatement ps = MimeTypeUsageFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM mimetypeusages " +
                    "WHERE mtypu_mtyp_mimetypeid = ? AND mtypu_mtyput_mimetypeusagetypeid = ?");
            
            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());
            ps.setLong(2, mimeTypeUsageType.getPrimaryKey().getEntityId());
            
            mimeTypeUsage = MimeTypeUsageFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeUsage;
    }
    
    public List<MimeTypeUsage> getMimeTypeUsagesByMimeType(MimeType mimeType) {
        List<MimeTypeUsage> mimeTypeUsages = null;
        
        try {
            PreparedStatement ps = MimeTypeUsageFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                    + "FROM mimetypeusages, mimetypes, mimetypedetails "
                    + "WHERE mtypu_mtyp_mimetypeid = ? "
                    + "AND mtypu_mtyp_mimetypeid = mtyp_mimetypeid AND mtyp_lastdetailid = mtypdt_mimetypedetailid "
                    + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename");
            
            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());
            
            mimeTypeUsages = MimeTypeUsageFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeUsages;
    }
    
    public MimeTypeUsageTransfer getMimeTypeUsageTransfer(UserVisit userVisit, MimeTypeUsage mimeTypeUsage) {
        return getCoreTransferCaches(userVisit).getMimeTypeUsageTransferCache().getMimeTypeUsageTransfer(mimeTypeUsage);
    }
    
    public List<MimeTypeUsageTransfer> getMimeTypeUsageTransfersByMimeType(UserVisit userVisit, Collection<MimeTypeUsage> mimeTypeUsages) {
        List<MimeTypeUsageTransfer> mimeTypeUsageTransfers = new ArrayList<>(mimeTypeUsages.size());
        MimeTypeUsageTransferCache mimeTypeUsageTransferCache = getCoreTransferCaches(userVisit).getMimeTypeUsageTransferCache();
        
        mimeTypeUsages.stream().forEach((mimeTypeUsage) -> {
            mimeTypeUsageTransfers.add(mimeTypeUsageTransferCache.getMimeTypeUsageTransfer(mimeTypeUsage));
        });
        
        return mimeTypeUsageTransfers;
    }
    
    public List<MimeTypeUsageTransfer> getMimeTypeUsageTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        return getMimeTypeUsageTransfersByMimeType(userVisit, getMimeTypeUsagesByMimeType(mimeType));
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------
    
    public MimeTypeFileExtension createMimeTypeFileExtension(MimeType mimeType, String fileExtension, Boolean isDefault) {
        return MimeTypeFileExtensionFactory.getInstance().create(mimeType, fileExtension, isDefault);
    }
    
    public MimeTypeFileExtension getDefaultMimeTypeFileExtension(MimeType mimeType) {
        MimeTypeFileExtension mimeTypeFileExtension = null;
        
        try {
            PreparedStatement ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM mimetypefileextensions " +
                    "WHERE mtypfe_mtyp_mimetypeid = ? AND mtypfe_isdefault = 1");
            
            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());
            
            mimeTypeFileExtension = MimeTypeFileExtensionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeFileExtension;
    }
    
    public MimeTypeFileExtension getMimeTypeFileExtension(String fileExtension) {
        MimeTypeFileExtension mimeTypeFileExtension = null;
        
        try {
            PreparedStatement ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM mimetypefileextensions " +
                    "WHERE mtypfe_fileextension = ?");
            
            ps.setString(1, fileExtension);
            
            mimeTypeFileExtension = MimeTypeFileExtensionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeFileExtension;
    }
    
    public List<MimeTypeFileExtension> getMimeTypeFileExtensions() {
        PreparedStatement ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM mimetypefileextensions " +
                "ORDER BY mtypfe_fileextension");
        
        return MimeTypeFileExtensionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public List<MimeTypeFileExtension> getMimeTypeFileExtensionsByMimeType(MimeType mimeType) {
        PreparedStatement ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                "SELECT _ALL_ "
                + "FROM mimetypefileextensions "
                + "WHERE mtypfe_mtyp_mimetypeid = ? "
                + "ORDER BY mtypfe_fileextension");
        
        return MimeTypeFileExtensionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps,
                mimeType);
    }
    
    public MimeTypeFileExtensionTransfer getMimeTypeFileExtensionTransfer(UserVisit userVisit, MimeTypeFileExtension mimeTypeFileExtension) {
        return getCoreTransferCaches(userVisit).getMimeTypeFileExtensionTransferCache().getMimeTypeFileExtensionTransfer(mimeTypeFileExtension);
    }
    
    public List<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensionTransfers(UserVisit userVisit, Collection<MimeTypeFileExtension> mimeTypeFileExtensions) {
        List<MimeTypeFileExtensionTransfer> mimeTypeFileExtensionTransfers = new ArrayList<>(mimeTypeFileExtensions.size());
        MimeTypeFileExtensionTransferCache mimeTypeFileExtensionTransferCache = getCoreTransferCaches(userVisit).getMimeTypeFileExtensionTransferCache();
        
        mimeTypeFileExtensions.stream().forEach((mimeTypeFileExtension) -> {
            mimeTypeFileExtensionTransfers.add(mimeTypeFileExtensionTransferCache.getMimeTypeFileExtensionTransfer(mimeTypeFileExtension));
        });
        
        return mimeTypeFileExtensionTransfers;
    }
    
    public List<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensionTransfers(UserVisit userVisit) {
        return getMimeTypeFileExtensionTransfers(userVisit, getMimeTypeFileExtensions());
    }
    
    public List<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensionTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        return getMimeTypeFileExtensionTransfers(userVisit, getMimeTypeFileExtensionsByMimeType(mimeType));
    }
    
    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    public Protocol createProtocol(String protocolName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Protocol defaultProtocol = getDefaultProtocol();
        boolean defaultFound = defaultProtocol != null;

        if(defaultFound && isDefault) {
            ProtocolDetailValue defaultProtocolDetailValue = getDefaultProtocolDetailValueForUpdate();

            defaultProtocolDetailValue.setIsDefault(Boolean.FALSE);
            updateProtocolFromValue(defaultProtocolDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Protocol protocol = ProtocolFactory.getInstance().create();
        ProtocolDetail protocolDetail = ProtocolDetailFactory.getInstance().create(protocol, protocolName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        protocol = ProtocolFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                protocol.getPrimaryKey());
        protocol.setActiveDetail(protocolDetail);
        protocol.setLastDetail(protocolDetail);
        protocol.store();

        sendEventUsingNames(protocol.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return protocol;
    }

    private static final Map<EntityPermission, String> getProtocolByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "AND protdt_protocolname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "AND protdt_protocolname = ? " +
                "FOR UPDATE");
        getProtocolByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Protocol getProtocolByName(String protocolName, EntityPermission entityPermission) {
        return ProtocolFactory.getInstance().getEntityFromQuery(entityPermission, getProtocolByNameQueries, protocolName);
    }

    public Protocol getProtocolByName(String protocolName) {
        return getProtocolByName(protocolName, EntityPermission.READ_ONLY);
    }

    public Protocol getProtocolByNameForUpdate(String protocolName) {
        return getProtocolByName(protocolName, EntityPermission.READ_WRITE);
    }

    public ProtocolDetailValue getProtocolDetailValueForUpdate(Protocol protocol) {
        return protocol == null? null: protocol.getLastDetailForUpdate().getProtocolDetailValue().clone();
    }

    public ProtocolDetailValue getProtocolDetailValueByNameForUpdate(String protocolName) {
        return getProtocolDetailValueForUpdate(getProtocolByNameForUpdate(protocolName));
    }

    private static final Map<EntityPermission, String> getDefaultProtocolQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "AND protdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "AND protdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultProtocolQueries = Collections.unmodifiableMap(queryMap);
    }

    private Protocol getDefaultProtocol(EntityPermission entityPermission) {
        return ProtocolFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultProtocolQueries);
    }

    public Protocol getDefaultProtocol() {
        return getDefaultProtocol(EntityPermission.READ_ONLY);
    }

    public Protocol getDefaultProtocolForUpdate() {
        return getDefaultProtocol(EntityPermission.READ_WRITE);
    }

    public ProtocolDetailValue getDefaultProtocolDetailValueForUpdate() {
        return getDefaultProtocolForUpdate().getLastDetailForUpdate().getProtocolDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getProtocolsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "ORDER BY protdt_sortorder, protdt_protocolname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "FOR UPDATE");
        getProtocolsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Protocol> getProtocols(EntityPermission entityPermission) {
        return ProtocolFactory.getInstance().getEntitiesFromQuery(entityPermission, getProtocolsQueries);
    }

    public List<Protocol> getProtocols() {
        return getProtocols(EntityPermission.READ_ONLY);
    }

    public List<Protocol> getProtocolsForUpdate() {
        return getProtocols(EntityPermission.READ_WRITE);
    }

   public ProtocolTransfer getProtocolTransfer(UserVisit userVisit, Protocol protocol) {
        return getCoreTransferCaches(userVisit).getProtocolTransferCache().getProtocolTransfer(protocol);
    }

    public List<ProtocolTransfer> getProtocolTransfers(UserVisit userVisit) {
        List<Protocol> protocols = getProtocols();
        List<ProtocolTransfer> protocolTransfers = new ArrayList<>(protocols.size());
        ProtocolTransferCache protocolTransferCache = getCoreTransferCaches(userVisit).getProtocolTransferCache();

        protocols.stream().forEach((protocol) -> {
            protocolTransfers.add(protocolTransferCache.getProtocolTransfer(protocol));
        });

        return protocolTransfers;
    }

    public ProtocolChoicesBean getProtocolChoices(String defaultProtocolChoice, Language language, boolean allowNullChoice) {
        List<Protocol> protocols = getProtocols();
        int size = protocols.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultProtocolChoice == null) {
                defaultValue = "";
            }
        }

        for(Protocol protocol: protocols) {
            ProtocolDetail protocolDetail = protocol.getLastDetail();

            String label = getBestProtocolDescription(protocol, language);
            String value = protocolDetail.getProtocolName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultProtocolChoice == null? false: defaultProtocolChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && protocolDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ProtocolChoicesBean(labels, values, defaultValue);
    }

    private void updateProtocolFromValue(ProtocolDetailValue protocolDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(protocolDetailValue.hasBeenModified()) {
            Protocol protocol = ProtocolFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     protocolDetailValue.getProtocolPK());
            ProtocolDetail protocolDetail = protocol.getActiveDetailForUpdate();

            protocolDetail.setThruTime(session.START_TIME_LONG);
            protocolDetail.store();

            ProtocolPK protocolPK = protocolDetail.getProtocolPK(); // Not updated
            String protocolName = protocolDetailValue.getProtocolName();
            Boolean isDefault = protocolDetailValue.getIsDefault();
            Integer sortOrder = protocolDetailValue.getSortOrder();

            if(checkDefault) {
                Protocol defaultProtocol = getDefaultProtocol();
                boolean defaultFound = defaultProtocol != null && !defaultProtocol.equals(protocol);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ProtocolDetailValue defaultProtocolDetailValue = getDefaultProtocolDetailValueForUpdate();

                    defaultProtocolDetailValue.setIsDefault(Boolean.FALSE);
                    updateProtocolFromValue(defaultProtocolDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            protocolDetail = ProtocolDetailFactory.getInstance().create(protocolPK, protocolName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            protocol.setActiveDetail(protocolDetail);
            protocol.setLastDetail(protocolDetail);

            sendEventUsingNames(protocolPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateProtocolFromValue(ProtocolDetailValue protocolDetailValue, BasePK updatedBy) {
        updateProtocolFromValue(protocolDetailValue, true, updatedBy);
    }

    private void deleteProtocol(Protocol protocol, boolean checkDefault, BasePK deletedBy) {
        ProtocolDetail protocolDetail = protocol.getLastDetailForUpdate();

        deleteServicesByProtocol(protocol, deletedBy);
        deleteProtocolDescriptionsByProtocol(protocol, deletedBy);

        protocolDetail.setThruTime(session.START_TIME_LONG);
        protocol.setActiveDetail(null);
        protocol.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            Protocol defaultProtocol = getDefaultProtocol();

            if(defaultProtocol == null) {
                List<Protocol> protocols = getProtocolsForUpdate();

                if(!protocols.isEmpty()) {
                    Iterator<Protocol> iter = protocols.iterator();
                    if(iter.hasNext()) {
                        defaultProtocol = iter.next();
                    }
                    ProtocolDetailValue protocolDetailValue = defaultProtocol.getLastDetailForUpdate().getProtocolDetailValue().clone();

                    protocolDetailValue.setIsDefault(Boolean.TRUE);
                    updateProtocolFromValue(protocolDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(protocol.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteProtocol(Protocol protocol, BasePK deletedBy) {
        deleteProtocol(protocol, true, deletedBy);
    }

    private void deleteProtocols(List<Protocol> protocols, boolean checkDefault, BasePK deletedBy) {
        protocols.stream().forEach((protocol) -> {
            deleteProtocol(protocol, checkDefault, deletedBy);
        });
    }

    public void deleteProtocols(List<Protocol> protocols, BasePK deletedBy) {
        deleteProtocols(protocols, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    public ProtocolDescription createProtocolDescription(Protocol protocol, Language language, String description, BasePK createdBy) {
        ProtocolDescription protocolDescription = ProtocolDescriptionFactory.getInstance().create(protocol, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(protocol.getPrimaryKey(), EventTypes.MODIFY.name(), protocolDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return protocolDescription;
    }

    private static final Map<EntityPermission, String> getProtocolDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM protocoldescriptions " +
                "WHERE protd_prot_protocolid = ? AND protd_lang_languageid = ? AND protd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM protocoldescriptions " +
                "WHERE protd_prot_protocolid = ? AND protd_lang_languageid = ? AND protd_thrutime = ? " +
                "FOR UPDATE");
        getProtocolDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ProtocolDescription getProtocolDescription(Protocol protocol, Language language, EntityPermission entityPermission) {
        return ProtocolDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getProtocolDescriptionQueries,
                protocol, language, Session.MAX_TIME);
    }

    public ProtocolDescription getProtocolDescription(Protocol protocol, Language language) {
        return getProtocolDescription(protocol, language, EntityPermission.READ_ONLY);
    }

    public ProtocolDescription getProtocolDescriptionForUpdate(Protocol protocol, Language language) {
        return getProtocolDescription(protocol, language, EntityPermission.READ_WRITE);
    }

    public ProtocolDescriptionValue getProtocolDescriptionValue(ProtocolDescription protocolDescription) {
        return protocolDescription == null? null: protocolDescription.getProtocolDescriptionValue().clone();
    }

    public ProtocolDescriptionValue getProtocolDescriptionValueForUpdate(Protocol protocol, Language language) {
        return getProtocolDescriptionValue(getProtocolDescriptionForUpdate(protocol, language));
    }

    private static final Map<EntityPermission, String> getProtocolDescriptionsByProtocolQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM protocoldescriptions, languages " +
                "WHERE protd_prot_protocolid = ? AND protd_thrutime = ? AND protd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM protocoldescriptions " +
                "WHERE protd_prot_protocolid = ? AND protd_thrutime = ? " +
                "FOR UPDATE");
        getProtocolDescriptionsByProtocolQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ProtocolDescription> getProtocolDescriptionsByProtocol(Protocol protocol, EntityPermission entityPermission) {
        return ProtocolDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getProtocolDescriptionsByProtocolQueries,
                protocol, Session.MAX_TIME);
    }

    public List<ProtocolDescription> getProtocolDescriptionsByProtocol(Protocol protocol) {
        return getProtocolDescriptionsByProtocol(protocol, EntityPermission.READ_ONLY);
    }

    public List<ProtocolDescription> getProtocolDescriptionsByProtocolForUpdate(Protocol protocol) {
        return getProtocolDescriptionsByProtocol(protocol, EntityPermission.READ_WRITE);
    }

    public String getBestProtocolDescription(Protocol protocol, Language language) {
        String description;
        ProtocolDescription protocolDescription = getProtocolDescription(protocol, language);

        if(protocolDescription == null && !language.getIsDefault()) {
            protocolDescription = getProtocolDescription(protocol, getPartyControl().getDefaultLanguage());
        }

        if(protocolDescription == null) {
            description = protocol.getLastDetail().getProtocolName();
        } else {
            description = protocolDescription.getDescription();
        }

        return description;
    }

    public ProtocolDescriptionTransfer getProtocolDescriptionTransfer(UserVisit userVisit, ProtocolDescription protocolDescription) {
        return getCoreTransferCaches(userVisit).getProtocolDescriptionTransferCache().getProtocolDescriptionTransfer(protocolDescription);
    }

    public List<ProtocolDescriptionTransfer> getProtocolDescriptionTransfersByProtocol(UserVisit userVisit, Protocol protocol) {
        List<ProtocolDescription> protocolDescriptions = getProtocolDescriptionsByProtocol(protocol);
        List<ProtocolDescriptionTransfer> protocolDescriptionTransfers = new ArrayList<>(protocolDescriptions.size());
        ProtocolDescriptionTransferCache protocolDescriptionTransferCache = getCoreTransferCaches(userVisit).getProtocolDescriptionTransferCache();

        protocolDescriptions.stream().forEach((protocolDescription) -> {
            protocolDescriptionTransfers.add(protocolDescriptionTransferCache.getProtocolDescriptionTransfer(protocolDescription));
        });

        return protocolDescriptionTransfers;
    }

    public void updateProtocolDescriptionFromValue(ProtocolDescriptionValue protocolDescriptionValue, BasePK updatedBy) {
        if(protocolDescriptionValue.hasBeenModified()) {
            ProtocolDescription protocolDescription = ProtocolDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    protocolDescriptionValue.getPrimaryKey());

            protocolDescription.setThruTime(session.START_TIME_LONG);
            protocolDescription.store();

            Protocol protocol = protocolDescription.getProtocol();
            Language language = protocolDescription.getLanguage();
            String description = protocolDescriptionValue.getDescription();

            protocolDescription = ProtocolDescriptionFactory.getInstance().create(protocol, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(protocol.getPrimaryKey(), EventTypes.MODIFY.name(), protocolDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteProtocolDescription(ProtocolDescription protocolDescription, BasePK deletedBy) {
        protocolDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(protocolDescription.getProtocolPK(), EventTypes.MODIFY.name(), protocolDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteProtocolDescriptionsByProtocol(Protocol protocol, BasePK deletedBy) {
        List<ProtocolDescription> protocolDescriptions = getProtocolDescriptionsByProtocolForUpdate(protocol);

        protocolDescriptions.stream().forEach((protocolDescription) -> {
            deleteProtocolDescription(protocolDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    public Service createService(String serviceName, Integer port, Protocol protocol, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Service defaultService = getDefaultService();
        boolean defaultFound = defaultService != null;

        if(defaultFound && isDefault) {
            ServiceDetailValue defaultServiceDetailValue = getDefaultServiceDetailValueForUpdate();

            defaultServiceDetailValue.setIsDefault(Boolean.FALSE);
            updateServiceFromValue(defaultServiceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Service service = ServiceFactory.getInstance().create();
        ServiceDetail serviceDetail = ServiceDetailFactory.getInstance().create(service, serviceName, port, protocol, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        service = ServiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                service.getPrimaryKey());
        service.setActiveDetail(serviceDetail);
        service.setLastDetail(serviceDetail);
        service.store();

        sendEventUsingNames(service.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return service;
    }

    private static final Map<EntityPermission, String> getServiceByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_servicename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_servicename = ? " +
                "FOR UPDATE");
        getServiceByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Service getServiceByName(String serviceName, EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntityFromQuery(entityPermission, getServiceByNameQueries, serviceName);
    }

    public Service getServiceByName(String serviceName) {
        return getServiceByName(serviceName, EntityPermission.READ_ONLY);
    }

    public Service getServiceByNameForUpdate(String serviceName) {
        return getServiceByName(serviceName, EntityPermission.READ_WRITE);
    }

    public ServiceDetailValue getServiceDetailValueForUpdate(Service service) {
        return service == null? null: service.getLastDetailForUpdate().getServiceDetailValue().clone();
    }

    public ServiceDetailValue getServiceDetailValueByNameForUpdate(String serviceName) {
        return getServiceDetailValueForUpdate(getServiceByNameForUpdate(serviceName));
    }

    private static final Map<EntityPermission, String> getDefaultServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private Service getDefaultService(EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultServiceQueries);
    }

    public Service getDefaultService() {
        return getDefaultService(EntityPermission.READ_ONLY);
    }

    public Service getDefaultServiceForUpdate() {
        return getDefaultService(EntityPermission.READ_WRITE);
    }

    public ServiceDetailValue getDefaultServiceDetailValueForUpdate() {
        return getDefaultServiceForUpdate().getLastDetailForUpdate().getServiceDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getServicesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "ORDER BY srvdt_sortorder, srvdt_servicename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "FOR UPDATE");
        getServicesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Service> getServices(EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServicesQueries);
    }

    public List<Service> getServices() {
        return getServices(EntityPermission.READ_ONLY);
    }

    public List<Service> getServicesForUpdate() {
        return getServices(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getServicesByProtocolQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_prot_protocolid = ? " +
                "ORDER BY srvdt_sortorder, srvdt_servicename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_prot_protocolid = ? " +
                "FOR UPDATE");
        getServicesByProtocolQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Service> getServicesByProtocol(Protocol protocol, EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServicesByProtocolQueries,
                protocol);
    }

    public List<Service> getServicesByProtocol(Protocol protocol) {
        return getServicesByProtocol(protocol, EntityPermission.READ_ONLY);
    }

    public List<Service> getServicesByProtocolForUpdate(Protocol protocol) {
        return getServicesByProtocol(protocol, EntityPermission.READ_WRITE);
    }

   public ServiceTransfer getServiceTransfer(UserVisit userVisit, Service service) {
        return getCoreTransferCaches(userVisit).getServiceTransferCache().getServiceTransfer(service);
    }

    public List<ServiceTransfer> getServiceTransfers(UserVisit userVisit) {
        List<Service> services = getServices();
        List<ServiceTransfer> serviceTransfers = new ArrayList<>(services.size());
        ServiceTransferCache serviceTransferCache = getCoreTransferCaches(userVisit).getServiceTransferCache();

        services.stream().forEach((service) -> {
            serviceTransfers.add(serviceTransferCache.getServiceTransfer(service));
        });

        return serviceTransfers;
    }

    public ServiceChoicesBean getServiceChoices(String defaultServiceChoice, Language language, boolean allowNullChoice) {
        List<Service> services = getServices();
        int size = services.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultServiceChoice == null) {
                defaultValue = "";
            }
        }

        for(Service service: services) {
            ServiceDetail serviceDetail = service.getLastDetail();

            String label = getBestServiceDescription(service, language);
            String value = serviceDetail.getServiceName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultServiceChoice == null? false: defaultServiceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && serviceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ServiceChoicesBean(labels, values, defaultValue);
    }

    private void updateServiceFromValue(ServiceDetailValue serviceDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(serviceDetailValue.hasBeenModified()) {
            Service service = ServiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     serviceDetailValue.getServicePK());
            ServiceDetail serviceDetail = service.getActiveDetailForUpdate();

            serviceDetail.setThruTime(session.START_TIME_LONG);
            serviceDetail.store();

            ServicePK servicePK = serviceDetail.getServicePK(); // Not updated
            String serviceName = serviceDetailValue.getServiceName();
            Integer port = serviceDetailValue.getPort();
            ProtocolPK protocolPK = serviceDetailValue.getProtocolPK();
            Boolean isDefault = serviceDetailValue.getIsDefault();
            Integer sortOrder = serviceDetailValue.getSortOrder();

            if(checkDefault) {
                Service defaultService = getDefaultService();
                boolean defaultFound = defaultService != null && !defaultService.equals(service);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ServiceDetailValue defaultServiceDetailValue = getDefaultServiceDetailValueForUpdate();

                    defaultServiceDetailValue.setIsDefault(Boolean.FALSE);
                    updateServiceFromValue(defaultServiceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            serviceDetail = ServiceDetailFactory.getInstance().create(servicePK, serviceName, port, protocolPK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            service.setActiveDetail(serviceDetail);
            service.setLastDetail(serviceDetail);

            sendEventUsingNames(servicePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateServiceFromValue(ServiceDetailValue serviceDetailValue, BasePK updatedBy) {
        updateServiceFromValue(serviceDetailValue, true, updatedBy);
    }

    private void deleteService(Service service, boolean checkDefault, BasePK deletedBy) {
        ServiceDetail serviceDetail = service.getLastDetailForUpdate();

        deleteServerServicesByService(service, deletedBy);
        deleteServiceDescriptionsByService(service, deletedBy);

        serviceDetail.setThruTime(session.START_TIME_LONG);
        service.setActiveDetail(null);
        service.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            Service defaultService = getDefaultService();

            if(defaultService == null) {
                List<Service> services = getServicesForUpdate();

                if(!services.isEmpty()) {
                    Iterator<Service> iter = services.iterator();
                    if(iter.hasNext()) {
                        defaultService = iter.next();
                    }
                    ServiceDetailValue serviceDetailValue = defaultService.getLastDetailForUpdate().getServiceDetailValue().clone();

                    serviceDetailValue.setIsDefault(Boolean.TRUE);
                    updateServiceFromValue(serviceDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(service.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteService(Service service, BasePK deletedBy) {
        deleteService(service, true, deletedBy);
    }

    private void deleteServices(List<Service> services, boolean checkDefault, BasePK deletedBy) {
        services.stream().forEach((service) -> {
            deleteService(service, checkDefault, deletedBy);
        });
    }

    public void deleteServices(List<Service> services, BasePK deletedBy) {
        deleteServices(services, true, deletedBy);
    }

    public void deleteServicesByProtocol(Protocol protocol, BasePK deletedBy) {
        deleteServices(getServicesByProtocol(protocol), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    public ServiceDescription createServiceDescription(Service service, Language language, String description, BasePK createdBy) {
        ServiceDescription serviceDescription = ServiceDescriptionFactory.getInstance().create(service, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(service.getPrimaryKey(), EventTypes.MODIFY.name(), serviceDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return serviceDescription;
    }

    private static final Map<EntityPermission, String> getServiceDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM servicedescriptions " +
                "WHERE srvd_srv_serviceid = ? AND srvd_lang_languageid = ? AND srvd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM servicedescriptions " +
                "WHERE srvd_srv_serviceid = ? AND srvd_lang_languageid = ? AND srvd_thrutime = ? " +
                "FOR UPDATE");
        getServiceDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ServiceDescription getServiceDescription(Service service, Language language, EntityPermission entityPermission) {
        return ServiceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getServiceDescriptionQueries,
                service, language, Session.MAX_TIME);
    }

    public ServiceDescription getServiceDescription(Service service, Language language) {
        return getServiceDescription(service, language, EntityPermission.READ_ONLY);
    }

    public ServiceDescription getServiceDescriptionForUpdate(Service service, Language language) {
        return getServiceDescription(service, language, EntityPermission.READ_WRITE);
    }

    public ServiceDescriptionValue getServiceDescriptionValue(ServiceDescription serviceDescription) {
        return serviceDescription == null? null: serviceDescription.getServiceDescriptionValue().clone();
    }

    public ServiceDescriptionValue getServiceDescriptionValueForUpdate(Service service, Language language) {
        return getServiceDescriptionValue(getServiceDescriptionForUpdate(service, language));
    }

    private static final Map<EntityPermission, String> getServiceDescriptionsByServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM servicedescriptions, languages " +
                "WHERE srvd_srv_serviceid = ? AND srvd_thrutime = ? AND srvd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM servicedescriptions " +
                "WHERE srvd_srv_serviceid = ? AND srvd_thrutime = ? " +
                "FOR UPDATE");
        getServiceDescriptionsByServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServiceDescription> getServiceDescriptionsByService(Service service, EntityPermission entityPermission) {
        return ServiceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getServiceDescriptionsByServiceQueries,
                service, Session.MAX_TIME);
    }

    public List<ServiceDescription> getServiceDescriptionsByService(Service service) {
        return getServiceDescriptionsByService(service, EntityPermission.READ_ONLY);
    }

    public List<ServiceDescription> getServiceDescriptionsByServiceForUpdate(Service service) {
        return getServiceDescriptionsByService(service, EntityPermission.READ_WRITE);
    }

    public String getBestServiceDescription(Service service, Language language) {
        String description;
        ServiceDescription serviceDescription = getServiceDescription(service, language);

        if(serviceDescription == null && !language.getIsDefault()) {
            serviceDescription = getServiceDescription(service, getPartyControl().getDefaultLanguage());
        }

        if(serviceDescription == null) {
            description = service.getLastDetail().getServiceName();
        } else {
            description = serviceDescription.getDescription();
        }

        return description;
    }

    public ServiceDescriptionTransfer getServiceDescriptionTransfer(UserVisit userVisit, ServiceDescription serviceDescription) {
        return getCoreTransferCaches(userVisit).getServiceDescriptionTransferCache().getServiceDescriptionTransfer(serviceDescription);
    }

    public List<ServiceDescriptionTransfer> getServiceDescriptionTransfersByService(UserVisit userVisit, Service service) {
        List<ServiceDescription> serviceDescriptions = getServiceDescriptionsByService(service);
        List<ServiceDescriptionTransfer> serviceDescriptionTransfers = new ArrayList<>(serviceDescriptions.size());
        ServiceDescriptionTransferCache serviceDescriptionTransferCache = getCoreTransferCaches(userVisit).getServiceDescriptionTransferCache();

        serviceDescriptions.stream().forEach((serviceDescription) -> {
            serviceDescriptionTransfers.add(serviceDescriptionTransferCache.getServiceDescriptionTransfer(serviceDescription));
        });

        return serviceDescriptionTransfers;
    }

    public void updateServiceDescriptionFromValue(ServiceDescriptionValue serviceDescriptionValue, BasePK updatedBy) {
        if(serviceDescriptionValue.hasBeenModified()) {
            ServiceDescription serviceDescription = ServiceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    serviceDescriptionValue.getPrimaryKey());

            serviceDescription.setThruTime(session.START_TIME_LONG);
            serviceDescription.store();

            Service service = serviceDescription.getService();
            Language language = serviceDescription.getLanguage();
            String description = serviceDescriptionValue.getDescription();

            serviceDescription = ServiceDescriptionFactory.getInstance().create(service, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(service.getPrimaryKey(), EventTypes.MODIFY.name(), serviceDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteServiceDescription(ServiceDescription serviceDescription, BasePK deletedBy) {
        serviceDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(serviceDescription.getServicePK(), EventTypes.MODIFY.name(), serviceDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteServiceDescriptionsByService(Service service, BasePK deletedBy) {
        List<ServiceDescription> serviceDescriptions = getServiceDescriptionsByServiceForUpdate(service);

        serviceDescriptions.stream().forEach((serviceDescription) -> {
            deleteServiceDescription(serviceDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    public Server createServer(String serverName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Server defaultServer = getDefaultServer();
        boolean defaultFound = defaultServer != null;

        if(defaultFound && isDefault) {
            ServerDetailValue defaultServerDetailValue = getDefaultServerDetailValueForUpdate();

            defaultServerDetailValue.setIsDefault(Boolean.FALSE);
            updateServerFromValue(defaultServerDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Server server = ServerFactory.getInstance().create();
        ServerDetail serverDetail = ServerDetailFactory.getInstance().create(server, serverName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        server = ServerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, server.getPrimaryKey());
        server.setActiveDetail(serverDetail);
        server.setLastDetail(serverDetail);
        server.store();

        sendEventUsingNames(server.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return server;
    }

    private static final Map<EntityPermission, String> getServerByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "AND servdt_servername = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "AND servdt_servername = ? " +
                "FOR UPDATE");
        getServerByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Server getServerByName(String serverName, EntityPermission entityPermission) {
        return ServerFactory.getInstance().getEntityFromQuery(entityPermission, getServerByNameQueries, serverName);
    }

    public Server getServerByName(String serverName) {
        return getServerByName(serverName, EntityPermission.READ_ONLY);
    }

    public Server getServerByNameForUpdate(String serverName) {
        return getServerByName(serverName, EntityPermission.READ_WRITE);
    }

    public ServerDetailValue getServerDetailValueForUpdate(Server server) {
        return server == null? null: server.getLastDetailForUpdate().getServerDetailValue().clone();
    }

    public ServerDetailValue getServerDetailValueByNameForUpdate(String serverName) {
        return getServerDetailValueForUpdate(getServerByNameForUpdate(serverName));
    }

    private static final Map<EntityPermission, String> getDefaultServerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "AND servdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "AND servdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultServerQueries = Collections.unmodifiableMap(queryMap);
    }

    private Server getDefaultServer(EntityPermission entityPermission) {
        return ServerFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultServerQueries);
    }

    public Server getDefaultServer() {
        return getDefaultServer(EntityPermission.READ_ONLY);
    }

    public Server getDefaultServerForUpdate() {
        return getDefaultServer(EntityPermission.READ_WRITE);
    }

    public ServerDetailValue getDefaultServerDetailValueForUpdate() {
        return getDefaultServerForUpdate().getLastDetailForUpdate().getServerDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getServersQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "ORDER BY servdt_sortorder, servdt_servername " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "FOR UPDATE");
        getServersQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Server> getServers(EntityPermission entityPermission) {
        return ServerFactory.getInstance().getEntitiesFromQuery(entityPermission, getServersQueries);
    }

    public List<Server> getServers() {
        return getServers(EntityPermission.READ_ONLY);
    }

    public List<Server> getServersForUpdate() {
        return getServers(EntityPermission.READ_WRITE);
    }

   public ServerTransfer getServerTransfer(UserVisit userVisit, Server server) {
        return getCoreTransferCaches(userVisit).getServerTransferCache().getServerTransfer(server);
    }

    public List<ServerTransfer> getServerTransfers(UserVisit userVisit) {
        List<Server> servers = getServers();
        List<ServerTransfer> serverTransfers = new ArrayList<>(servers.size());
        ServerTransferCache serverTransferCache = getCoreTransferCaches(userVisit).getServerTransferCache();

        servers.stream().forEach((server) -> {
            serverTransfers.add(serverTransferCache.getServerTransfer(server));
        });

        return serverTransfers;
    }

    public ServerChoicesBean getServerChoices(String defaultServerChoice, Language language, boolean allowNullChoice) {
        List<Server> servers = getServers();
        int size = servers.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultServerChoice == null) {
                defaultValue = "";
            }
        }

        for(Server server: servers) {
            ServerDetail serverDetail = server.getLastDetail();

            String label = getBestServerDescription(server, language);
            String value = serverDetail.getServerName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultServerChoice == null? false: defaultServerChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && serverDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ServerChoicesBean(labels, values, defaultValue);
    }

    private void updateServerFromValue(ServerDetailValue serverDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(serverDetailValue.hasBeenModified()) {
            Server server = ServerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     serverDetailValue.getServerPK());
            ServerDetail serverDetail = server.getActiveDetailForUpdate();

            serverDetail.setThruTime(session.START_TIME_LONG);
            serverDetail.store();

            ServerPK serverPK = serverDetail.getServerPK(); // Not updated
            String serverName = serverDetailValue.getServerName();
            Boolean isDefault = serverDetailValue.getIsDefault();
            Integer sortOrder = serverDetailValue.getSortOrder();

            if(checkDefault) {
                Server defaultServer = getDefaultServer();
                boolean defaultFound = defaultServer != null && !defaultServer.equals(server);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ServerDetailValue defaultServerDetailValue = getDefaultServerDetailValueForUpdate();

                    defaultServerDetailValue.setIsDefault(Boolean.FALSE);
                    updateServerFromValue(defaultServerDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            serverDetail = ServerDetailFactory.getInstance().create(serverPK, serverName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            server.setActiveDetail(serverDetail);
            server.setLastDetail(serverDetail);

            sendEventUsingNames(serverPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateServerFromValue(ServerDetailValue serverDetailValue, BasePK updatedBy) {
        updateServerFromValue(serverDetailValue, true, updatedBy);
    }

    private void deleteServer(Server server, boolean checkDefault, BasePK deletedBy) {
        ServerDetail serverDetail = server.getLastDetailForUpdate();

        deleteServerServicesByServer(server, deletedBy);
        deleteServerDescriptionsByServer(server, deletedBy);

        serverDetail.setThruTime(session.START_TIME_LONG);
        server.setActiveDetail(null);
        server.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            Server defaultServer = getDefaultServer();

            if(defaultServer == null) {
                List<Server> servers = getServersForUpdate();

                if(!servers.isEmpty()) {
                    Iterator<Server> iter = servers.iterator();
                    if(iter.hasNext()) {
                        defaultServer = iter.next();
                    }
                    ServerDetailValue serverDetailValue = defaultServer.getLastDetailForUpdate().getServerDetailValue().clone();

                    serverDetailValue.setIsDefault(Boolean.TRUE);
                    updateServerFromValue(serverDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(server.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteServer(Server server, BasePK deletedBy) {
        deleteServer(server, true, deletedBy);
    }

    private void deleteServers(List<Server> servers, boolean checkDefault, BasePK deletedBy) {
        servers.stream().forEach((server) -> {
            deleteServer(server, checkDefault, deletedBy);
        });
    }

    public void deleteServers(List<Server> servers, BasePK deletedBy) {
        deleteServers(servers, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    public ServerDescription createServerDescription(Server server, Language language, String description, BasePK createdBy) {
        ServerDescription serverDescription = ServerDescriptionFactory.getInstance().create(server, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(server.getPrimaryKey(), EventTypes.MODIFY.name(), serverDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return serverDescription;
    }

    private static final Map<EntityPermission, String> getServerDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM serverdescriptions " +
                "WHERE servd_serv_serverid = ? AND servd_lang_languageid = ? AND servd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM serverdescriptions " +
                "WHERE servd_serv_serverid = ? AND servd_lang_languageid = ? AND servd_thrutime = ? " +
                "FOR UPDATE");
        getServerDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ServerDescription getServerDescription(Server server, Language language, EntityPermission entityPermission) {
        return ServerDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getServerDescriptionQueries,
                server, language, Session.MAX_TIME);
    }

    public ServerDescription getServerDescription(Server server, Language language) {
        return getServerDescription(server, language, EntityPermission.READ_ONLY);
    }

    public ServerDescription getServerDescriptionForUpdate(Server server, Language language) {
        return getServerDescription(server, language, EntityPermission.READ_WRITE);
    }

    public ServerDescriptionValue getServerDescriptionValue(ServerDescription serverDescription) {
        return serverDescription == null? null: serverDescription.getServerDescriptionValue().clone();
    }

    public ServerDescriptionValue getServerDescriptionValueForUpdate(Server server, Language language) {
        return getServerDescriptionValue(getServerDescriptionForUpdate(server, language));
    }

    private static final Map<EntityPermission, String> getServerDescriptionsByServerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM serverdescriptions, languages " +
                "WHERE servd_serv_serverid = ? AND servd_thrutime = ? AND servd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM serverdescriptions " +
                "WHERE servd_serv_serverid = ? AND servd_thrutime = ? " +
                "FOR UPDATE");
        getServerDescriptionsByServerQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServerDescription> getServerDescriptionsByServer(Server server, EntityPermission entityPermission) {
        return ServerDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getServerDescriptionsByServerQueries,
                server, Session.MAX_TIME);
    }

    public List<ServerDescription> getServerDescriptionsByServer(Server server) {
        return getServerDescriptionsByServer(server, EntityPermission.READ_ONLY);
    }

    public List<ServerDescription> getServerDescriptionsByServerForUpdate(Server server) {
        return getServerDescriptionsByServer(server, EntityPermission.READ_WRITE);
    }

    public String getBestServerDescription(Server server, Language language) {
        String description;
        ServerDescription serverDescription = getServerDescription(server, language);

        if(serverDescription == null && !language.getIsDefault()) {
            serverDescription = getServerDescription(server, getPartyControl().getDefaultLanguage());
        }

        if(serverDescription == null) {
            description = server.getLastDetail().getServerName();
        } else {
            description = serverDescription.getDescription();
        }

        return description;
    }

    public ServerDescriptionTransfer getServerDescriptionTransfer(UserVisit userVisit, ServerDescription serverDescription) {
        return getCoreTransferCaches(userVisit).getServerDescriptionTransferCache().getServerDescriptionTransfer(serverDescription);
    }

    public List<ServerDescriptionTransfer> getServerDescriptionTransfersByServer(UserVisit userVisit, Server server) {
        List<ServerDescription> serverDescriptions = getServerDescriptionsByServer(server);
        List<ServerDescriptionTransfer> serverDescriptionTransfers = new ArrayList<>(serverDescriptions.size());
        ServerDescriptionTransferCache serverDescriptionTransferCache = getCoreTransferCaches(userVisit).getServerDescriptionTransferCache();

        serverDescriptions.stream().forEach((serverDescription) -> {
            serverDescriptionTransfers.add(serverDescriptionTransferCache.getServerDescriptionTransfer(serverDescription));
        });

        return serverDescriptionTransfers;
    }

    public void updateServerDescriptionFromValue(ServerDescriptionValue serverDescriptionValue, BasePK updatedBy) {
        if(serverDescriptionValue.hasBeenModified()) {
            ServerDescription serverDescription = ServerDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    serverDescriptionValue.getPrimaryKey());

            serverDescription.setThruTime(session.START_TIME_LONG);
            serverDescription.store();

            Server server = serverDescription.getServer();
            Language language = serverDescription.getLanguage();
            String description = serverDescriptionValue.getDescription();

            serverDescription = ServerDescriptionFactory.getInstance().create(server, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(server.getPrimaryKey(), EventTypes.MODIFY.name(), serverDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteServerDescription(ServerDescription serverDescription, BasePK deletedBy) {
        serverDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(serverDescription.getServerPK(), EventTypes.MODIFY.name(), serverDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteServerDescriptionsByServer(Server server, BasePK deletedBy) {
        List<ServerDescription> serverDescriptions = getServerDescriptionsByServerForUpdate(server);

        serverDescriptions.stream().forEach((serverDescription) -> {
            deleteServerDescription(serverDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    public ServerService createServerService(Server server, Service service, BasePK createdBy) {
        ServerService serverService = ServerServiceFactory.getInstance().create(server, service, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(server.getPrimaryKey(), EventTypes.MODIFY.name(), serverService.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return serverService;
    }

    private static final Map<EntityPermission, String> getServerServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM serverservices " +
                "WHERE servsrv_serv_serverid = ? AND servsrv_srv_serviceid = ? AND servsrv_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM serverservices " +
                "WHERE servsrv_serv_serverid = ? AND servsrv_srv_serviceid = ? AND servsrv_thrutime = ? " +
                "FOR UPDATE");
        getServerServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private ServerService getServerService(Server server, Service service, EntityPermission entityPermission) {
        return ServerServiceFactory.getInstance().getEntityFromQuery(entityPermission, getServerServiceQueries,
                server, service, Session.MAX_TIME);
    }

    public ServerService getServerService(Server server, Service service) {
        return getServerService(server, service, EntityPermission.READ_ONLY);
    }

    public ServerService getServerServiceForUpdate(Server server, Service service) {
        return getServerService(server, service, EntityPermission.READ_WRITE);
    }

    public ServerServiceValue getServerServiceValue(ServerService serverService) {
        return serverService == null? null: serverService.getServerServiceValue().clone();
    }

    public ServerServiceValue getServerServiceValueForUpdate(Server server, Service service) {
        return getServerServiceValue(getServerServiceForUpdate(server, service));
    }

    private static final Map<EntityPermission, String> getServerServicesByServerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM serverservices, services, servicedetails " +
                "WHERE servsrv_serv_serverid = ? AND servsrv_thrutime = ? " +
                "AND servsrv_srv_serviceid = srv_serviceid AND srv_lastdetailid = srvdt_servicedetailid " +
                "ORDER BY srvdt_sortorder, srvdt_servicename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM serverservices " +
                "WHERE servsrv_serv_serverid = ? AND servsrv_thrutime = ? " +
                "FOR UPDATE");
        getServerServicesByServerQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServerService> getServerServicesByServer(Server server, EntityPermission entityPermission) {
        return ServerServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServerServicesByServerQueries,
                server, Session.MAX_TIME);
    }

    public List<ServerService> getServerServicesByServer(Server server) {
        return getServerServicesByServer(server, EntityPermission.READ_ONLY);
    }

    public List<ServerService> getServerServicesByServerForUpdate(Server server) {
        return getServerServicesByServer(server, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getServerServicesByServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM serverservices, services, servicedetails " +
                "WHERE servsrv_srv_serviceid = ? AND servsrv_thrutime = ? " +
                "AND servsrv_serv_serverid = serv_serverid AND serv_lastdetailid = servdt_serverdetailid " +
                "ORDER BY servdt_sortorder, servdt_servername");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM serverservices " +
                "WHERE servsrv_srv_serviceid = ? AND servsrv_thrutime = ? " +
                "FOR UPDATE");
        getServerServicesByServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServerService> getServerServicesByService(Service service, EntityPermission entityPermission) {
        return ServerServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServerServicesByServiceQueries,
                service, Session.MAX_TIME);
    }

    public List<ServerService> getServerServicesByService(Service service) {
        return getServerServicesByService(service, EntityPermission.READ_ONLY);
    }

    public List<ServerService> getServerServicesByServiceForUpdate(Service service) {
        return getServerServicesByService(service, EntityPermission.READ_WRITE);
    }

    public ServerServiceTransfer getServerServiceTransfer(UserVisit userVisit, ServerService serverService) {
        return getCoreTransferCaches(userVisit).getServerServiceTransferCache().getServerServiceTransfer(serverService);
    }

    public List<ServerServiceTransfer> getServerServiceTransfersByServer(UserVisit userVisit, Server server) {
        List<ServerService> serverServices = getServerServicesByServer(server);
        List<ServerServiceTransfer> serverServiceTransfers = new ArrayList<>(serverServices.size());
        ServerServiceTransferCache serverServiceTransferCache = getCoreTransferCaches(userVisit).getServerServiceTransferCache();

        serverServices.stream().forEach((serverService) -> {
            serverServiceTransfers.add(serverServiceTransferCache.getServerServiceTransfer(serverService));
        });

        return serverServiceTransfers;
    }

    public void deleteServerService(ServerService serverService, BasePK deletedBy) {
        var scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
        
        scaleControl.deleteScalesByServerService(serverService, deletedBy);

        serverService.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(serverService.getServerPK(), EventTypes.MODIFY.name(), serverService.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteServerServices(List<ServerService> serverServices, BasePK deletedBy) {
        serverServices.stream().forEach((serverService) -> {
            deleteServerService(serverService, deletedBy);
        });
    }

    public void deleteServerServicesByServer(Server server, BasePK deletedBy) {
        deleteServerServices(getServerServicesByServerForUpdate(server), deletedBy);
    }

    public void deleteServerServicesByService(Service service, BasePK deletedBy) {
        deleteServerServices(getServerServicesByServiceForUpdate(service), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------
    
    public EntityBooleanAttribute createEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Boolean booleanAttribute, BasePK createdBy) {
        EntityBooleanAttribute entityBooleanAttribute = EntityBooleanAttributeFactory.getInstance().create(entityAttribute,
                entityInstance, booleanAttribute,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityBooleanAttribute;
    }
    
    private EntityBooleanAttribute getEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityBooleanAttribute entityBooleanAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitybooleanattributes " +
                        "WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ? AND enbla_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitybooleanattributes " +
                        "WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ? AND enbla_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityBooleanAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityBooleanAttribute = EntityBooleanAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttribute;
    }
    
    public EntityBooleanAttribute getEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityBooleanAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityBooleanAttribute getEntityBooleanAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityBooleanAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityBooleanAttributeValue getEntityBooleanAttributeValueForUpdate(EntityBooleanAttribute entityBooleanAttribute) {
        return entityBooleanAttribute == null? null: entityBooleanAttribute.getEntityBooleanAttributeValue().clone();
    }
    
    public EntityBooleanAttributeValue getEntityBooleanAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityBooleanAttributeValueForUpdate(getEntityBooleanAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityBooleanAttribute> getEntityBooleanAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityBooleanAttribute> entityBooleanAttributes;
        
        try {
            PreparedStatement ps = EntityBooleanAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitybooleanattributes " +
                    "WHERE enbla_ena_entityattributeid = ? AND enbla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBooleanAttributes = EntityBooleanAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttributes;
    }
    
    public List<EntityBooleanAttribute> getEntityBooleanAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityBooleanAttribute> entityBooleanAttributes;
        
        try {
            PreparedStatement ps = EntityBooleanAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitybooleanattributes " +
                    "WHERE enbla_eni_entityinstanceid = ? AND enbla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBooleanAttributes = EntityBooleanAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttributes;
    }
    
    public EntityBooleanAttributeTransfer getEntityBooleanAttributeTransfer(UserVisit userVisit, EntityBooleanAttribute entityBooleanAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityBooleanAttributeTransferCache().getEntityBooleanAttributeTransfer(entityBooleanAttribute, entityInstance);
    }
    
    public void updateEntityBooleanAttributeFromValue(EntityBooleanAttributeValue entityBooleanAttributeValue, BasePK updatedBy) {
        if(entityBooleanAttributeValue.hasBeenModified()) {
            EntityBooleanAttribute entityBooleanAttribute = EntityBooleanAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityBooleanAttributeValue);
            EntityAttribute entityAttribute = entityBooleanAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityBooleanAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBooleanAttribute.setThruTime(session.START_TIME_LONG);
                entityBooleanAttribute.store();
            } else {
                entityBooleanAttribute.remove();
            }
            
            EntityBooleanAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityBooleanAttributeValue.getBooleanAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityBooleanAttribute(EntityBooleanAttribute entityBooleanAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityBooleanAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityBooleanAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBooleanAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityBooleanAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityBooleanAttributes(List<EntityBooleanAttribute> entityBooleanAttributes, BasePK deletedBy) {
        entityBooleanAttributes.stream().forEach((entityBooleanAttribute) -> {
            deleteEntityBooleanAttribute(entityBooleanAttribute, deletedBy);
        });
    }
    
    public void deleteEntityBooleanAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityBooleanAttributes(getEntityBooleanAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityBooleanAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityBooleanAttributes(getEntityBooleanAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------
    
    public EntityDateAttribute createEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Integer dateAttribute, BasePK createdBy) {
        EntityDateAttribute entityDateAttribute = EntityDateAttributeFactory.getInstance().create(entityAttribute, entityInstance, dateAttribute, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityDateAttribute;
    }
    
    private EntityDateAttribute getEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityDateAttribute entityDateAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitydateattributes " +
                        "WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ? AND enda_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitydateattributes " +
                        "WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ? AND enda_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityDateAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityDateAttribute = EntityDateAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttribute;
    }
    
    public EntityDateAttribute getEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityDateAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityDateAttribute getEntityDateAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityDateAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityDateAttributeValue getEntityDateAttributeValueForUpdate(EntityDateAttribute entityDateAttribute) {
        return entityDateAttribute == null? null: entityDateAttribute.getEntityDateAttributeValue().clone();
    }
    
    public EntityDateAttributeValue getEntityDateAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityDateAttributeValueForUpdate(getEntityDateAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityDateAttribute> getEntityDateAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityDateAttribute> entityDateAttributes;
        
        try {
            PreparedStatement ps = EntityDateAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitydateattributes " +
                    "WHERE enda_ena_entityattributeid = ? AND enda_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityDateAttributes = EntityDateAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttributes;
    }
    
    public List<EntityDateAttribute> getEntityDateAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityDateAttribute> entityDateAttributes;
        
        try {
            PreparedStatement ps = EntityDateAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitydateattributes " +
                    "WHERE enda_eni_entityinstanceid = ? AND enda_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityDateAttributes = EntityDateAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttributes;
    }
    
    public EntityDateAttributeTransfer getEntityDateAttributeTransfer(UserVisit userVisit, EntityDateAttribute entityDateAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityDateAttributeTransferCache().getEntityDateAttributeTransfer(entityDateAttribute, entityInstance);
    }
    
    public void updateEntityDateAttributeFromValue(EntityDateAttributeValue entityDateAttributeValue, BasePK updatedBy) {
        if(entityDateAttributeValue.hasBeenModified()) {
            EntityDateAttribute entityDateAttribute = EntityDateAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityDateAttributeValue);
            EntityAttribute entityAttribute = entityDateAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityDateAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityDateAttribute.setThruTime(session.START_TIME_LONG);
                entityDateAttribute.store();
            } else {
                entityDateAttribute.remove();
            }
            
            EntityDateAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityDateAttributeValue.getDateAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityDateAttribute(EntityDateAttribute entityDateAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityDateAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityDateAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityDateAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityDateAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityDateAttributes(List<EntityDateAttribute> entityDateAttributes, BasePK deletedBy) {
        entityDateAttributes.stream().forEach((entityDateAttribute) -> {
            deleteEntityDateAttribute(entityDateAttribute, deletedBy);
        });
    }
    
    public void deleteEntityDateAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityDateAttributes(getEntityDateAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityDateAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityDateAttributes(getEntityDateAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------
    
    public EntityIntegerAttribute createEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer integerAttribute, BasePK createdBy) {
        EntityIntegerAttribute entityIntegerAttribute = EntityIntegerAttributeFactory.getInstance().create(entityAttribute,
                entityInstance, integerAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityIntegerAttribute;
    }
    
    private EntityIntegerAttribute getEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityIntegerAttribute entityIntegerAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerattributes " +
                        "WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ? AND enia_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerattributes " +
                        "WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ? AND enia_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityIntegerAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityIntegerAttribute = EntityIntegerAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttribute;
    }
    
    public EntityIntegerAttribute getEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerAttribute getEntityIntegerAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerAttributeValue getEntityIntegerAttributeValueForUpdate(EntityIntegerAttribute entityIntegerAttribute) {
        return entityIntegerAttribute == null? null: entityIntegerAttribute.getEntityIntegerAttributeValue().clone();
    }
    
    public EntityIntegerAttributeValue getEntityIntegerAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerAttributeValueForUpdate(getEntityIntegerAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityIntegerAttribute> getEntityIntegerAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityIntegerAttribute> entityIntegerAttributes;
        
        try {
            PreparedStatement ps = EntityIntegerAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityintegerattributes " +
                    "WHERE enia_ena_entityattributeid = ? AND enia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerAttributes = EntityIntegerAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttributes;
    }
    
    public List<EntityIntegerAttribute> getEntityIntegerAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityIntegerAttribute> entityIntegerAttributes;
        
        try {
            PreparedStatement ps = EntityIntegerAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityintegerattributes " +
                    "WHERE enia_eni_entityinstanceid = ? AND enia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerAttributes = EntityIntegerAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttributes;
    }
    
    public EntityIntegerAttributeTransfer getEntityIntegerAttributeTransfer(UserVisit userVisit, EntityIntegerAttribute entityIntegerAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityIntegerAttributeTransferCache().getEntityIntegerAttributeTransfer(entityIntegerAttribute, entityInstance);
    }
    
    public void updateEntityIntegerAttributeFromValue(EntityIntegerAttributeValue entityIntegerAttributeValue, BasePK updatedBy) {
        if(entityIntegerAttributeValue.hasBeenModified()) {
            EntityIntegerAttribute entityIntegerAttribute = EntityIntegerAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityIntegerAttributeValue);
            EntityAttribute entityAttribute = entityIntegerAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityIntegerAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityIntegerAttribute.setThruTime(session.START_TIME_LONG);
                entityIntegerAttribute.store();
            } else {
                entityIntegerAttribute.remove();
            }
            
            EntityIntegerAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityIntegerAttributeValue.getIntegerAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    public void deleteEntityIntegerAttribute(EntityIntegerAttribute entityIntegerAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityIntegerAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityIntegerAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityIntegerAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityIntegerAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityIntegerAttributes(List<EntityIntegerAttribute> entityIntegerAttributes, BasePK deletedBy) {
        entityIntegerAttributes.stream().forEach((entityIntegerAttribute) -> {
            deleteEntityIntegerAttribute(entityIntegerAttribute, deletedBy);
        });
    }
    
    public void deleteEntityIntegerAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityIntegerAttributes(getEntityIntegerAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityIntegerAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityIntegerAttributes(getEntityIntegerAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------
    
    public EntityListItemAttribute createEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        EntityListItemAttribute entityListItemAttribute = EntityListItemAttributeFactory.getInstance().create(session,
                entityAttribute, entityInstance, entityListItem, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityListItemAttribute;
    }
    
    private EntityListItemAttribute getEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityListItemAttribute entityListItemAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemattributes " +
                        "WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ? AND ela_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemattributes " +
                        "WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ? AND ela_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityListItemAttribute = EntityListItemAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemAttribute;
    }
    
    public EntityListItemAttribute getEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityListItemAttribute getEntityListItemAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemAttributeValue getEntityListItemAttributeValueForUpdate(EntityListItemAttribute entityListItemAttribute) {
        return entityListItemAttribute == null? null: entityListItemAttribute.getEntityListItemAttributeValue().clone();
    }
    
    public EntityListItemAttributeValue getEntityListItemAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemAttributeValueForUpdate(getEntityListItemAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    private List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityListItemAttribute> entityListItemAttributes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitylistitemattributes, entityinstances "
                        + "WHERE ela_eli_entitylistitemid = ? AND ela_thrutime = ? "
                        + "AND ela_eni_entityinstanceid = eni_entityinstanceid "
                        + "ORDER BY eni_entityuniqueid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitylistitemattributes "
                        + "WHERE ela_eli_entitylistitemid = ? AND ela_thrutime = ? "
                        + "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemAttributes = EntityListItemAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemAttributes;
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItem(EntityListItem entityListItem) {
        return getEntityListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityListItemAttribute> entityListItemAttributes = null;
        
        try {
            PreparedStatement ps = EntityListItemAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylistitemattributes " +
                    "WHERE ela_eni_entityinstanceid = ? AND ela_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemAttributes = EntityListItemAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemAttributes;
    }
    
    public EntityListItemAttributeTransfer getEntityListItemAttributeTransfer(UserVisit userVisit, EntityListItemAttribute entityListItemAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityListItemAttributeTransferCache().getEntityListItemAttributeTransfer(entityListItemAttribute, entityInstance);
    }
    
    public void updateEntityListItemAttributeFromValue(EntityListItemAttributeValue entityListItemAttributeValue, BasePK updatedBy) {
        if(entityListItemAttributeValue.hasBeenModified()) {
            EntityListItemAttribute entityListItemAttribute = EntityListItemAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityListItemAttributeValue);
            EntityAttribute entityAttribute = entityListItemAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityListItemAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityListItemAttribute.setThruTime(session.START_TIME_LONG);
                entityListItemAttribute.store();
            } else {
                entityListItemAttribute.remove();
            }
            
            EntityListItemAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), entityListItemAttributeValue.getEntityListItemPK(),
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityListItemAttribute(EntityListItemAttribute entityListItemAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityListItemAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityListItemAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityListItemAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityListItemAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityListItemAttributes(List<EntityListItemAttribute> entityListItemAttributes, BasePK deletedBy) {
        entityListItemAttributes.stream().forEach((entityListItemAttribute) -> {
            deleteEntityListItemAttribute(entityListItemAttribute, deletedBy);
        });
    }
    
    public void deleteEntityListItemAttributesByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityListItemAttributes(getEntityListItemAttributesByEntityListItemForUpdate(entityListItem), deletedBy);
    }
    
    public void deleteEntityListItemAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityListItemAttributes(getEntityListItemAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------
    
    public EntityLongAttribute createEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Long longAttribute, BasePK createdBy) {
        EntityLongAttribute entityLongAttribute = EntityLongAttributeFactory.getInstance().create(entityAttribute,
                entityInstance, longAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityLongAttribute;
    }
    
    private EntityLongAttribute getEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityLongAttribute entityLongAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongattributes " +
                        "WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ? AND enla_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongattributes " +
                        "WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ? AND enla_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityLongAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityLongAttribute = EntityLongAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttribute;
    }
    
    public EntityLongAttribute getEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityLongAttribute getEntityLongAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityLongAttributeValue getEntityLongAttributeValueForUpdate(EntityLongAttribute entityLongAttribute) {
        return entityLongAttribute == null? null: entityLongAttribute.getEntityLongAttributeValue().clone();
    }
    
    public EntityLongAttributeValue getEntityLongAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongAttributeValueForUpdate(getEntityLongAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityLongAttribute> getEntityLongAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityLongAttribute> entityLongAttributes;
        
        try {
            PreparedStatement ps = EntityLongAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylongattributes " +
                    "WHERE enla_ena_entityattributeid = ? AND enla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongAttributes = EntityLongAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttributes;
    }
    
    public List<EntityLongAttribute> getEntityLongAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityLongAttribute> entityLongAttributes;
        
        try {
            PreparedStatement ps = EntityLongAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylongattributes " +
                    "WHERE enla_eni_entityinstanceid = ? AND enla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongAttributes = EntityLongAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttributes;
    }
    
    public EntityLongAttributeTransfer getEntityLongAttributeTransfer(UserVisit userVisit, EntityLongAttribute entityLongAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityLongAttributeTransferCache().getEntityLongAttributeTransfer(entityLongAttribute, entityInstance);
    }
    
    public void updateEntityLongAttributeFromValue(EntityLongAttributeValue entityLongAttributeValue, BasePK updatedBy) {
        if(entityLongAttributeValue.hasBeenModified()) {
            EntityLongAttribute entityLongAttribute = EntityLongAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityLongAttributeValue);
            EntityAttribute entityAttribute = entityLongAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityLongAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityLongAttribute.setThruTime(session.START_TIME_LONG);
                entityLongAttribute.store();
            } else {
                entityLongAttribute.remove();
            }
            
            EntityLongAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityLongAttributeValue.getLongAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityLongAttribute(EntityLongAttribute entityLongAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityLongAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityLongAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityLongAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityLongAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityLongAttributes(List<EntityLongAttribute> entityLongAttributes, BasePK deletedBy) {
        entityLongAttributes.stream().forEach((entityLongAttribute) -> {
            deleteEntityLongAttribute(entityLongAttribute, deletedBy);
        });
    }
    
    public void deleteEntityLongAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityLongAttributes(getEntityLongAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityLongAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityLongAttributes(getEntityLongAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------
    
    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(EntityAttribute entityAttribute,
            EntityInstance entityInstance, EntityListItem entityListItem, BasePK createdBy) {
        EntityMultipleListItemAttribute entityMultipleListItemAttribute = EntityMultipleListItemAttributeFactory.getInstance().create(session,
                entityAttribute, entityInstance, entityListItem, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityMultipleListItemAttribute;
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes = null;
        
        try {
            PreparedStatement ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                    + "FROM entitymultiplelistitemattributes, entitylistitems, entitylistitemdetails "
                    + "WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_thrutime = ? "
                    + "AND emlia_eli_entitylistitemid = eli_entitylistitemid AND eli_lastdetailid = elidt_entitylistitemdetailid "
                    + "ORDER BY elidt_sortorder, elidt_entitylistitemname");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = EntityMultipleListItemAttributeFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttributes;
    }
    
    private EntityMultipleListItemAttribute getEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, EntityPermission entityPermission) {
        EntityMultipleListItemAttribute entityMultipleListItemAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymultiplelistitemattributes " +
                        "WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_eli_entitylistitemid = ? " +
                        "AND emlia_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymultiplelistitemattributes " +
                        "WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_eli_entitylistitemid = ? " +
                        "AND emlia_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityMultipleListItemAttribute = EntityMultipleListItemAttributeFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttribute;
    }
    
    public EntityMultipleListItemAttribute getEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem) {
        return getEntityMultipleListItemAttribute(entityAttribute, entityInstance, entityListItem, EntityPermission.READ_ONLY);
    }
    
    public EntityMultipleListItemAttribute getEntityMultipleListItemAttributeForUpdate(EntityAttribute entityAttribute,
            EntityInstance entityInstance, EntityListItem entityListItem) {
        return getEntityMultipleListItemAttribute(entityAttribute, entityInstance, entityListItem, EntityPermission.READ_WRITE);
    }
    
    private List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitymultiplelistitemattributes, entityinstances "
                        + "WHERE emlia_eli_entitylistitemid = ? AND emlia_thrutime = ? "
                        + "AND emlia_eni_entityinstanceid = eni_entityinstanceid "
                        + "ORDER BY eni_entityuniqueid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitymultiplelistitemattributes "
                        + "WHERE emlia_eli_entitylistitemid = ? AND emlia_thrutime = ? "
                        + "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = EntityMultipleListItemAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttributes;
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItem(EntityListItem entityListItem) {
        return getEntityMultipleListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityMultipleListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes = null;
        
        try {
            PreparedStatement ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitymultiplelistitemattributes " +
                    "WHERE emlia_eni_entityinstanceid = ? AND emlia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = EntityMultipleListItemAttributeFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttributes;
    }
    
    public EntityMultipleListItemAttributeTransfer getEntityMultipleListItemAttributeTransfer(UserVisit userVisit, EntityMultipleListItemAttribute entityMultipleListItemAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityMultipleListItemAttributeTransferCache().getEntityMultipleListItemAttributeTransfer(entityMultipleListItemAttribute, entityInstance);
    }
    
    public List<EntityMultipleListItemAttributeTransfer> getEntityMultipleListItemAttributeTransfers(UserVisit userVisit, List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes, EntityInstance entityInstance) {
        List<EntityMultipleListItemAttributeTransfer> entityMultipleListItemAttributeTransfers = new ArrayList<>(entityMultipleListItemAttributes.size());
        EntityMultipleListItemAttributeTransferCache entityMultipleListItemAttributeTransferCache = getCoreTransferCaches(userVisit).getEntityMultipleListItemAttributeTransferCache();
        
        entityMultipleListItemAttributes.stream().forEach((entityMultipleListItemAttribute) -> {
            entityMultipleListItemAttributeTransfers.add(entityMultipleListItemAttributeTransferCache.getEntityMultipleListItemAttributeTransfer(entityMultipleListItemAttribute, entityInstance));
        });
        
        return entityMultipleListItemAttributeTransfers;
    }
    
    public List<EntityMultipleListItemAttributeTransfer> getEntityMultipleListItemAttributeTransfers(UserVisit userVisit, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityMultipleListItemAttributeTransfers(userVisit, getEntityMultipleListItemAttributes(entityAttribute, entityInstance), entityInstance);
    }
    
    public void deleteEntityMultipleListItemAttribute(EntityMultipleListItemAttribute entityMultipleListItemAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityMultipleListItemAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityMultipleListItemAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityMultipleListItemAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityMultipleListItemAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityMultipleListItemAttributes(List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes, BasePK deletedBy) {
        entityMultipleListItemAttributes.stream().forEach((entityMultipleListItemAttribute) -> {
            deleteEntityMultipleListItemAttribute(entityMultipleListItemAttribute, deletedBy);
        });
    }
    
    public void deleteEntityMultipleListItemAttributesByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityMultipleListItemAttributes(getEntityMultipleListItemAttributesByEntityListItemForUpdate(entityListItem), deletedBy);
    }
    
    public void deleteEntityMultipleListItemAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityMultipleListItemAttributes(getEntityMultipleListItemAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    public EntityNameAttribute createEntityNameAttribute(EntityAttribute entityAttribute, String nameAttribute,
            EntityInstance entityInstance, BasePK createdBy) {
        EntityNameAttribute entityNameAttribute = EntityNameAttributeFactory.getInstance().create(entityAttribute,
                nameAttribute, entityInstance, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityNameAttribute;
    }
    
    private EntityNameAttribute getEntityNameAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityNameAttribute entityNameAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitynameattributes " +
                        "WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ? AND enna_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitynameattributes " +
                        "WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ? AND enna_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityNameAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityNameAttribute = EntityNameAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttribute;
    }
    
    public EntityNameAttribute getEntityNameAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityNameAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityNameAttribute getEntityNameAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityNameAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityNameAttributeValue getEntityNameAttributeValueForUpdate(EntityNameAttribute entityNameAttribute) {
        return entityNameAttribute == null? null: entityNameAttribute.getEntityNameAttributeValue().clone();
    }
    
    public EntityNameAttributeValue getEntityNameAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityNameAttributeValueForUpdate(getEntityNameAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityNameAttribute> entityNameAttributes = null;
        
        try {
            PreparedStatement ps = EntityNameAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_ena_entityattributeid = ? AND enna_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityNameAttributes = EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityNameAttribute> entityNameAttributes = null;
        
        try {
            PreparedStatement ps = EntityNameAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_eni_entityinstanceid = ? AND enna_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityNameAttributes = EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }
    
    public EntityNameAttributeTransfer getEntityNameAttributeTransfer(UserVisit userVisit, EntityNameAttribute entityNameAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityNameAttributeTransferCache().getEntityNameAttributeTransfer(entityNameAttribute, entityInstance);
    }
    
    public void updateEntityNameAttributeFromValue(EntityNameAttributeValue entityNameAttributeValue, BasePK updatedBy) {
        if(entityNameAttributeValue.hasBeenModified()) {
            EntityNameAttribute entityNameAttribute = EntityNameAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityNameAttributeValue);
            EntityAttribute entityAttribute = entityNameAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityNameAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityNameAttribute.setThruTime(session.START_TIME_LONG);
                entityNameAttribute.store();
            } else {
                entityNameAttribute.remove();
            }
            
            EntityNameAttributeFactory.getInstance().create(entityAttribute, entityNameAttributeValue.getNameAttribute(), entityInstance, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityNameAttribute(EntityNameAttribute entityNameAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityNameAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityNameAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityNameAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityNameAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityNameAttributes(List<EntityNameAttribute> entityNameAttributes, BasePK deletedBy) {
        entityNameAttributes.stream().forEach((entityNameAttribute) -> {
            deleteEntityNameAttribute(entityNameAttribute, deletedBy);
        });
    }
    
    public void deleteEntityNameAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityNameAttributes(getEntityNameAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityNameAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityNameAttributes(getEntityNameAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByName(EntityAttribute entityAttribute, String nameAttribute) {
        List<EntityNameAttribute> entityNameAttributes = null;
        
        try {
            PreparedStatement ps = EntityNameAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_ena_entityattributeid = ? AND enna_nameattribute = ? AND enna_thrutime = ?");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, nameAttribute);
            ps.setLong(3, Session.MAX_TIME);
            
            entityNameAttributes = EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }
    
    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------
    
    public EntityStringAttribute createEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, String stringAttribute, BasePK createdBy) {
        EntityStringAttribute entityStringAttribute = EntityStringAttributeFactory.getInstance().create(entityAttribute,
                entityInstance, language, stringAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityStringAttribute;
    }
    
    private EntityStringAttribute getEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        EntityStringAttribute entityStringAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringattributes " +
                        "WHERE ensa_ena_entityattributeid = ? AND ensa_eni_entityinstanceid = ? AND ensa_lang_languageid = ? AND ensa_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringattributes " +
                        "WHERE ensa_ena_entityattributeid = ? AND ensa_eni_entityinstanceid = ? AND ensa_lang_languageid = ? AND ensa_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityStringAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityStringAttribute = EntityStringAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttribute;
    }
    
    public EntityStringAttribute getEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityStringAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_ONLY);
    }
    
    public EntityStringAttribute getEntityStringAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityStringAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }
    
    public EntityStringAttribute getBestEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        EntityStringAttribute entityStringAttribute = getEntityStringAttribute(entityAttribute, entityInstance, language);
        
        if(entityStringAttribute == null && !language.getIsDefault()) {
            entityStringAttribute = getEntityStringAttribute(entityAttribute, entityInstance, getPartyControl().getDefaultLanguage());
        }
        
        return entityStringAttribute;
    }
    
    public EntityStringAttributeValue getEntityStringAttributeValueForUpdate(EntityStringAttribute entityStringAttribute) {
        return entityStringAttribute == null? null: entityStringAttribute.getEntityStringAttributeValue().clone();
    }
    
    public EntityStringAttributeValue getEntityStringAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityStringAttributeValueForUpdate(getEntityStringAttributeForUpdate(entityAttribute, entityInstance, language));
    }
    
    public List<EntityStringAttribute> getEntityStringAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityStringAttribute> entityStringAttributes = null;
        
        try {
            PreparedStatement ps = EntityStringAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitystringattributes " +
                    "WHERE ensa_ena_entityattributeid = ? AND ensa_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityStringAttributes = EntityStringAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttributes;
    }
    
    public List<EntityStringAttribute> getEntityStringAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityStringAttribute> entityStringAttributes = null;
        
        try {
            PreparedStatement ps = EntityStringAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitystringattributes " +
                    "WHERE ensa_eni_entityinstanceid = ? AND ensa_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityStringAttributes = EntityStringAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttributes;
    }
    
    public EntityStringAttributeTransfer getEntityStringAttributeTransfer(UserVisit userVisit, EntityStringAttribute entityStringAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityStringAttributeTransferCache().getEntityStringAttributeTransfer(entityStringAttribute, entityInstance);
    }
    
    public void updateEntityStringAttributeFromValue(EntityStringAttributeValue entityStringAttributeValue, BasePK updatedBy) {
        if(entityStringAttributeValue.hasBeenModified()) {
            EntityStringAttribute entityStringAttribute = EntityStringAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityStringAttributeValue);
            EntityAttribute entityAttribute = entityStringAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityStringAttribute.getEntityInstance();
            Language language = entityStringAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityStringAttribute.setThruTime(session.START_TIME_LONG);
                entityStringAttribute.store();
            } else {
                entityStringAttribute.remove();
            }
            
            EntityStringAttributeFactory.getInstance().create(entityAttribute, entityInstance, language, entityStringAttributeValue.getStringAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityStringAttribute(EntityStringAttribute entityStringAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityStringAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityStringAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityStringAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityStringAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityStringAttributes(List<EntityStringAttribute> entityStringAttributes, BasePK deletedBy) {
        entityStringAttributes.stream().forEach((entityStringAttribute) -> {
            deleteEntityStringAttribute(entityStringAttribute, deletedBy);
        });
    }
    
    public void deleteEntityStringAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityStringAttributes(getEntityStringAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityStringAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityStringAttributes(getEntityStringAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------
    
    public EntityGeoPointAttribute createEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer latitude, Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        EntityGeoPointAttribute entityGeoPointAttribute = EntityGeoPointAttributeFactory.getInstance().create(entityAttribute, entityInstance, latitude,
                longitude, elevation, altitude, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityGeoPointAttribute;
    }
    
    private EntityGeoPointAttribute getEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityGeoPointAttribute entityGeoPointAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitygeopointattributes " +
                        "WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ? AND engeopnta_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitygeopointattributes " +
                        "WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ? AND engeopnta_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityGeoPointAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityGeoPointAttribute = EntityGeoPointAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttribute;
    }
    
    public EntityGeoPointAttribute getEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityGeoPointAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityGeoPointAttribute getEntityGeoPointAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityGeoPointAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityGeoPointAttributeValue getEntityGeoPointAttributeValueForUpdate(EntityGeoPointAttribute entityGeoPointAttribute) {
        return entityGeoPointAttribute == null? null: entityGeoPointAttribute.getEntityGeoPointAttributeValue().clone();
    }
    
    public EntityGeoPointAttributeValue getEntityGeoPointAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityGeoPointAttributeValueForUpdate(getEntityGeoPointAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityGeoPointAttribute> getEntityGeoPointAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityGeoPointAttribute> entityGeoPointAttributes;
        
        try {
            PreparedStatement ps = EntityGeoPointAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitygeopointattributes " +
                    "WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityGeoPointAttributes = EntityGeoPointAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttributes;
    }
    
    public List<EntityGeoPointAttribute> getEntityGeoPointAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityGeoPointAttribute> entityGeoPointAttributes;
        
        try {
            PreparedStatement ps = EntityGeoPointAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitygeopointattributes " +
                    "WHERE engeopnta_eni_entityinstanceid = ? AND engeopnta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityGeoPointAttributes = EntityGeoPointAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttributes;
    }
    
    public EntityGeoPointAttributeTransfer getEntityGeoPointAttributeTransfer(UserVisit userVisit, EntityGeoPointAttribute entityGeoPointAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityGeoPointAttributeTransferCache().getEntityGeoPointAttributeTransfer(entityGeoPointAttribute, entityInstance);
    }
    
    public void updateEntityGeoPointAttributeFromValue(EntityGeoPointAttributeValue entityGeoPointAttributeValue, BasePK updatedBy) {
        if(entityGeoPointAttributeValue.hasBeenModified()) {
            EntityGeoPointAttribute entityGeoPointAttribute = EntityGeoPointAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityGeoPointAttributeValue);
            EntityAttribute entityAttribute = entityGeoPointAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityGeoPointAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityGeoPointAttribute.setThruTime(session.START_TIME_LONG);
                entityGeoPointAttribute.store();
            } else {
                entityGeoPointAttribute.remove();
            }
            
            EntityGeoPointAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityGeoPointAttributeValue.getLatitude(),
                    entityGeoPointAttributeValue.getLongitude(), entityGeoPointAttributeValue.getElevation(), entityGeoPointAttributeValue.getAltitude(),
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityGeoPointAttribute(EntityGeoPointAttribute entityGeoPointAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityGeoPointAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityGeoPointAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityGeoPointAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityGeoPointAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityGeoPointAttributes(List<EntityGeoPointAttribute> entityGeoPointAttributes, BasePK deletedBy) {
        entityGeoPointAttributes.stream().forEach((entityGeoPointAttribute) -> {
            deleteEntityGeoPointAttribute(entityGeoPointAttribute, deletedBy);
        });
    }
    
    public void deleteEntityGeoPointAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityGeoPointAttributes(getEntityGeoPointAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityGeoPointAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityGeoPointAttributes(getEntityGeoPointAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------
    
    public EntityTimeAttribute createEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Long timeAttribute, BasePK createdBy) {
        EntityTimeAttribute entityTimeAttribute = EntityTimeAttributeFactory.getInstance().create(entityAttribute, entityInstance, timeAttribute, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityTimeAttribute;
    }
    
    private EntityTimeAttribute getEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityTimeAttribute entityTimeAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytimeattributes " +
                        "WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ? AND enta_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytimeattributes " +
                        "WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ? AND enta_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityTimeAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityTimeAttribute = EntityTimeAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttribute;
    }
    
    public EntityTimeAttribute getEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityTimeAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityTimeAttribute getEntityTimeAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityTimeAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityTimeAttributeValue getEntityTimeAttributeValueForUpdate(EntityTimeAttribute entityTimeAttribute) {
        return entityTimeAttribute == null? null: entityTimeAttribute.getEntityTimeAttributeValue().clone();
    }
    
    public EntityTimeAttributeValue getEntityTimeAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityTimeAttributeValueForUpdate(getEntityTimeAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityTimeAttribute> getEntityTimeAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityTimeAttribute> entityTimeAttributes = null;
        
        try {
            PreparedStatement ps = EntityTimeAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimeattributes " +
                    "WHERE enta_ena_entityattributeid = ? AND enta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTimeAttributes = EntityTimeAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttributes;
    }
    
    public List<EntityTimeAttribute> getEntityTimeAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityTimeAttribute> entityTimeAttributes = null;
        
        try {
            PreparedStatement ps = EntityTimeAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimeattributes " +
                    "WHERE enta_eni_entityinstanceid = ? AND enta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTimeAttributes = EntityTimeAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttributes;
    }
    
    public EntityTimeAttributeTransfer getEntityTimeAttributeTransfer(UserVisit userVisit, EntityTimeAttribute entityTimeAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityTimeAttributeTransferCache().getEntityTimeAttributeTransfer(entityTimeAttribute, entityInstance);
    }
    
    public void updateEntityTimeAttributeFromValue(EntityTimeAttributeValue entityTimeAttributeValue, BasePK updatedBy) {
        if(entityTimeAttributeValue.hasBeenModified()) {
            EntityTimeAttribute entityTimeAttribute = EntityTimeAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityTimeAttributeValue);
            EntityAttribute entityAttribute = entityTimeAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityTimeAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityTimeAttribute.setThruTime(session.START_TIME_LONG);
                entityTimeAttribute.store();
            } else {
                entityTimeAttribute.remove();
            }
            
            EntityTimeAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityTimeAttributeValue.getTimeAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityTimeAttribute(EntityTimeAttribute entityTimeAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityTimeAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityTimeAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityTimeAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityTimeAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityTimeAttributes(List<EntityTimeAttribute> entityTimeAttributes, BasePK deletedBy) {
        entityTimeAttributes.stream().forEach((entityTimeAttribute) -> {
            deleteEntityTimeAttribute(entityTimeAttribute, deletedBy);
        });
    }
    
    public void deleteEntityTimeAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityTimeAttributes(getEntityTimeAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityTimeAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityTimeAttributes(getEntityTimeAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    public EntityBlobAttribute createEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, ByteArray blobAttribute, MimeType mimeType, BasePK createdBy) {
        EntityBlobAttribute entityBlobAttribute = EntityBlobAttributeFactory.getInstance().create(entityAttribute,
                entityInstance, language, blobAttribute, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityBlobAttribute;
    }
    
    private EntityBlobAttribute getEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        EntityBlobAttribute entityBlobAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityblobattributes " +
                        "WHERE enba_ena_entityattributeid = ? AND enba_eni_entityinstanceid = ? AND enba_lang_languageid = ? " +
                        "AND enba_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityblobattributes " +
                        "WHERE enba_ena_entityattributeid = ? AND enba_eni_entityinstanceid = ? AND enba_lang_languageid = ? " +
                        "AND enba_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityBlobAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityBlobAttribute = EntityBlobAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttribute;
    }
    
    public EntityBlobAttribute getEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityBlobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_ONLY);
    }
    
    public EntityBlobAttribute getEntityBlobAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityBlobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }
    
    public EntityBlobAttribute getBestEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        EntityBlobAttribute entityBlobAttribute = getEntityBlobAttribute(entityAttribute, entityInstance, language);
        
        if(entityBlobAttribute == null && !language.getIsDefault()) {
            entityBlobAttribute = getEntityBlobAttribute(entityAttribute, entityInstance, getPartyControl().getDefaultLanguage());
        }
        
        return entityBlobAttribute;
    }
    
    public EntityBlobAttributeValue getEntityBlobAttributeValueForUpdate(EntityBlobAttribute entityBlobAttribute) {
        return entityBlobAttribute == null? null: entityBlobAttribute.getEntityBlobAttributeValue().clone();
    }
    
    public EntityBlobAttributeValue getEntityBlobAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityBlobAttributeValueForUpdate(getEntityBlobAttributeForUpdate(entityAttribute, entityInstance, language));
    }
    
    public List<EntityBlobAttribute> getEntityBlobAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityBlobAttribute> entityBlobAttributes;
        
        try {
            PreparedStatement ps = EntityBlobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityblobattributes " +
                    "WHERE enba_ena_entityattributeid = ? AND enba_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBlobAttributes = EntityBlobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttributes;
    }
    
    public List<EntityBlobAttribute> getEntityBlobAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityBlobAttribute> entityBlobAttributes;
        
        try {
            PreparedStatement ps = EntityBlobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityblobattributes " +
                    "WHERE enba_eni_entityinstanceid = ? AND enba_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBlobAttributes = EntityBlobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttributes;
    }
    
    public EntityBlobAttributeTransfer getEntityBlobAttributeTransfer(UserVisit userVisit, EntityBlobAttribute entityBlobAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityBlobAttributeTransferCache().getEntityBlobAttributeTransfer(entityBlobAttribute, entityInstance);
    }
    
    public void updateEntityBlobAttributeFromValue(EntityBlobAttributeValue entityBlobAttributeValue, BasePK updatedBy) {
        if(entityBlobAttributeValue.hasBeenModified()) {
            EntityBlobAttribute entityBlobAttribute = EntityBlobAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityBlobAttributeValue);
            EntityAttribute entityAttribute = entityBlobAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityBlobAttribute.getEntityInstance();
            Language language = entityBlobAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBlobAttribute.setThruTime(session.START_TIME_LONG);
                entityBlobAttribute.store();
            } else {
                entityBlobAttribute.remove();
            }
            
            EntityBlobAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), language.getPrimaryKey(),
                    entityBlobAttributeValue.getBlobAttribute(), entityBlobAttributeValue.getMimeTypePK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityBlobAttribute(EntityBlobAttribute entityBlobAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityBlobAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityBlobAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBlobAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityBlobAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityBlobAttributes(List<EntityBlobAttribute> entityBlobAttributes, BasePK deletedBy) {
        entityBlobAttributes.stream().forEach((entityBlobAttribute) -> {
            deleteEntityBlobAttribute(entityBlobAttribute, deletedBy);
        });
    }
    
    public void deleteEntityBlobAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityBlobAttributes(getEntityBlobAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityBlobAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityBlobAttributes(getEntityBlobAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    public EntityClobAttribute createEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, String clobAttribute, MimeType mimeType, BasePK createdBy) {
        EntityClobAttribute entityClobAttribute = EntityClobAttributeFactory.getInstance().create(entityAttribute,
                entityInstance, language, clobAttribute, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityClobAttribute;
    }
    
    private EntityClobAttribute getEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        EntityClobAttribute entityClobAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityclobattributes " +
                        "WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ? AND enca_lang_languageid = ? " +
                        "AND enca_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityclobattributes " +
                        "WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ? AND enca_lang_languageid = ? " +
                        "AND enca_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityClobAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityClobAttribute = EntityClobAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttribute;
    }
    
    public EntityClobAttribute getEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityClobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_ONLY);
    }
    
    public EntityClobAttribute getEntityClobAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityClobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }
    
    public EntityClobAttribute getBestEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        EntityClobAttribute entityClobAttribute = getEntityClobAttribute(entityAttribute, entityInstance, language);
        
        if(entityClobAttribute == null && !language.getIsDefault()) {
            entityClobAttribute = getEntityClobAttribute(entityAttribute, entityInstance, getPartyControl().getDefaultLanguage());
        }
        
        return entityClobAttribute;
    }
    
    public EntityClobAttributeValue getEntityClobAttributeValueForUpdate(EntityClobAttribute entityClobAttribute) {
        return entityClobAttribute == null? null: entityClobAttribute.getEntityClobAttributeValue().clone();
    }
    
    public EntityClobAttributeValue getEntityClobAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityClobAttributeValueForUpdate(getEntityClobAttributeForUpdate(entityAttribute, entityInstance, language));
    }
    
    public List<EntityClobAttribute> getEntityClobAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityClobAttribute> entityClobAttributes;
        
        try {
            PreparedStatement ps = EntityClobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityclobattributes " +
                    "WHERE enca_ena_entityattributeid = ? AND enca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityClobAttributes = EntityClobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttributes;
    }
    
    public List<EntityClobAttribute> getEntityClobAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityClobAttribute> entityClobAttributes;
        
        try {
            PreparedStatement ps = EntityClobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityclobattributes " +
                    "WHERE enca_eni_entityinstanceid = ? AND enca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityClobAttributes = EntityClobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttributes;
    }
    
    public EntityClobAttributeTransfer getEntityClobAttributeTransfer(UserVisit userVisit, EntityClobAttribute entityClobAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityClobAttributeTransferCache().getEntityClobAttributeTransfer(entityClobAttribute, entityInstance);
    }
    
    public void updateEntityClobAttributeFromValue(EntityClobAttributeValue entityClobAttributeValue, BasePK updatedBy) {
        if(entityClobAttributeValue.hasBeenModified()) {
            EntityClobAttribute entityClobAttribute = EntityClobAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityClobAttributeValue);
            EntityAttribute entityAttribute = entityClobAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityClobAttribute.getEntityInstance();
            Language language = entityClobAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityClobAttribute.setThruTime(session.START_TIME_LONG);
                entityClobAttribute.store();
            } else {
                entityClobAttribute.remove();
            }
            
            EntityClobAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), language.getPrimaryKey(),
                    entityClobAttributeValue.getClobAttribute(), entityClobAttributeValue.getMimeTypePK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityClobAttribute(EntityClobAttribute entityClobAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityClobAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityClobAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityClobAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityClobAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityClobAttributes(List<EntityClobAttribute> entityClobAttributes, BasePK deletedBy) {
        entityClobAttributes.stream().forEach((entityClobAttribute) -> {
            deleteEntityClobAttribute(entityClobAttribute, deletedBy);
        });
    }
    
    public void deleteEntityClobAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityClobAttributes(getEntityClobAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityClobAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityClobAttributes(getEntityClobAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    public EntityAttributeEntityType createEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType, BasePK createdBy) {
        EntityAttributeEntityType entityAttributeEntityType = EntityAttributeEntityTypeFactory.getInstance().create(entityAttribute, allowedEntityType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), entityAttributeEntityType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityAttributeEntityType;
    }
    
    public long countEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ?",
                entityAttribute, Session.MAX_TIME);
    }

    public long countEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_allowedentitytypeid = ? AND enaent_thrutime = ?",
                allowedEntityType, Session.MAX_TIME);
    }

    public boolean entityAttributeEntityTypeExists(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return 1 == session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_allowedentitytypeid = ? AND enaent_thrutime = ?",
                entityAttribute, allowedEntityType, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getEntityAttributeEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_allowedentitytypeid = ? AND enaent_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_allowedentitytypeid = ? AND enaent_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeEntityType getEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType, EntityPermission entityPermission) {
        return EntityAttributeEntityTypeFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeEntityTypeQueries,
                entityAttribute, allowedEntityType, Session.MAX_TIME_LONG);
    }

    public EntityAttributeEntityType getEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return getEntityAttributeEntityType(entityAttribute, allowedEntityType, EntityPermission.READ_ONLY);
    }

    public EntityAttributeEntityType getEntityAttributeEntityTypeForUpdate(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return getEntityAttributeEntityType(entityAttribute, allowedEntityType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getEntityAttributeEntityTypesByEntityAttributeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ? "
                + "AND enaent_allowedentitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                + "ORDER BY entdt_sortorder, entdt_entitytypename, cvndd_componentvendorname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeEntityTypesByEntityAttributeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getEntityAttributeEntityTypesByEntityAttributeQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityTypesByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityTypesByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeEntityTypesByAllowedEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes, entityattributes, entityattributedetails "
                + "WHERE enaent_allowedentitytypeid = ? AND enaent_thrutime = ? "
                + "AND enaent_ena_entityattributeid = ena_entityattributeid AND ena_lastdetailid = enadt_entityAttributedetailid "
                + "ORDER BY enadt_sortorder, enadt_entityAttributename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeEntityTypesByAllowedEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType, EntityPermission entityPermission) {
        return EntityAttributeEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getEntityAttributeEntityTypesByAllowedEntityTypeQueries,
                allowedEntityType, Session.MAX_TIME_LONG);
    }

    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType) {
        return getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityTypeForUpdate(EntityType allowedEntityType) {
        return getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType, EntityPermission.READ_WRITE);
    }

    public EntityAttributeEntityTypeTransfer getEntityAttributeEntityTypeTransfer(UserVisit userVisit, EntityAttributeEntityType entityAttributeEntityType, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeEntityTypeTransferCache().getEntityAttributeEntityTypeTransfer(entityAttributeEntityType, entityInstance);
    }
    
    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfers(UserVisit userVisit, List<EntityAttributeEntityType> entityAttributeEntityTypes, EntityInstance entityInstance) {
        List<EntityAttributeEntityTypeTransfer> entityAttributeEntityTypeTransfers = new ArrayList<>(entityAttributeEntityTypes.size());
        EntityAttributeEntityTypeTransferCache entityAttributeEntityTypeTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeEntityTypeTransferCache();

        entityAttributeEntityTypes.stream().forEach((entityAttributeEntityType) -> {
            entityAttributeEntityTypeTransfers.add(entityAttributeEntityTypeTransferCache.getEntityAttributeEntityTypeTransfer(entityAttributeEntityType, entityInstance));
        });

        return entityAttributeEntityTypeTransfers;
    }

    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityAttributeEntityTypeTransfers(userVisit, getEntityAttributeEntityTypesByEntityAttribute(entityAttribute), entityInstance);
    }

    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfersByAllowedEntityType(UserVisit userVisit, EntityType allowedEntityType, EntityInstance entityInstance) {
        return getEntityAttributeEntityTypeTransfers(userVisit, getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType), entityInstance);
    }

    public void deleteEntityAttributeEntityType(EntityAttributeEntityType entityAttributeEntityType, BasePK deletedBy) {
        entityAttributeEntityType.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityAttributeEntityType.getEntityAttributePK(), EventTypes.MODIFY.name(), entityAttributeEntityType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityAttributeEntityTypes(List<EntityAttributeEntityType> entityAttributeEntityTypes, BasePK deletedBy) {
        entityAttributeEntityTypes.stream().forEach((entityAttributeEntityType) -> {
            deleteEntityAttributeEntityType(entityAttributeEntityType, deletedBy);
        });
    }
    
    public void deleteEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityAttributeEntityTypes(getEntityAttributeEntityTypesByEntityAttributeForUpdate(entityAttribute),  deletedBy);
    }

    public void deleteEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType, BasePK deletedBy) {
        deleteEntityAttributeEntityTypes(getEntityAttributeEntityTypesByAllowedEntityTypeForUpdate(allowedEntityType),  deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    public EntityEntityAttribute createEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, BasePK createdBy) {
        EntityEntityAttribute entityEntityAttribute = EntityEntityAttributeFactory.getInstance().create(entityAttribute, entityInstance,
                entityInstanceAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityEntityAttribute;
    }
    
    private EntityEntityAttribute getEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityEntityAttribute entityEntityAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityentityattributes " +
                        "WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ? " +
                        "AND eea_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityentityattributes " +
                        "WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ? " +
                        "AND eea_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityEntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityEntityAttribute = EntityEntityAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttribute;
    }
    
    public EntityEntityAttribute getEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityEntityAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityEntityAttribute getEntityEntityAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityEntityAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityEntityAttributeValue getEntityEntityAttributeValueForUpdate(EntityEntityAttribute entityEntityAttribute) {
        return entityEntityAttribute == null? null: entityEntityAttribute.getEntityEntityAttributeValue().clone();
    }
    
    public EntityEntityAttributeValue getEntityEntityAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityEntityAttributeValueForUpdate(getEntityEntityAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            PreparedStatement ps = EntityEntityAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_eni_entityinstanceid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            PreparedStatement ps = EntityEntityAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_eni_entityinstanceid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityInstanceAttributeForUpdate(EntityInstance entityInstanceAttribute) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            PreparedStatement ps = EntityEntityAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_entityinstanceattributeid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public EntityEntityAttributeTransfer getEntityEntityAttributeTransfer(UserVisit userVisit, EntityEntityAttribute entityEntityAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityEntityAttributeTransferCache().getEntityEntityAttributeTransfer(entityEntityAttribute, entityInstance);
    }
    
    public void updateEntityEntityAttributeFromValue(EntityEntityAttributeValue entityEntityAttributeValue, BasePK updatedBy) {
        if(entityEntityAttributeValue.hasBeenModified()) {
            EntityEntityAttribute entityEntityAttribute = EntityEntityAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityEntityAttributeValue);
            EntityAttribute entityAttribute = entityEntityAttribute.getEntityAttribute();
            EntityInstance entityInstance = entityEntityAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityEntityAttribute.setThruTime(session.START_TIME_LONG);
                entityEntityAttribute.store();
            } else {
                entityEntityAttribute.remove();
            }
            
            EntityEntityAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(),
                    entityEntityAttributeValue.getEntityInstanceAttributePK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteEntityEntityAttribute(EntityEntityAttribute entityEntityAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityEntityAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityEntityAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityEntityAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityEntityAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityEntityAttributes(List<EntityEntityAttribute> entityEntityAttributes, BasePK deletedBy) {
        entityEntityAttributes.stream().forEach((entityEntityAttribute) -> {
            deleteEntityEntityAttribute(entityEntityAttribute, deletedBy);
        });
    }
    
    public void deleteEntityEntityAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityEntityAttributes(getEntityEntityAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityEntityAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityEntityAttributes(getEntityEntityAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
        deleteEntityEntityAttributes(getEntityEntityAttributesByEntityInstanceAttributeForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------
    
    public EntityCollectionAttribute createEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, BasePK createdBy) {
        EntityCollectionAttribute entityCollectionAttribute = EntityCollectionAttributeFactory.getInstance().create(entityAttribute, entityInstance,
                entityInstanceAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityCollectionAttribute;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        List<EntityCollectionAttribute> entityCollectionAttributes = null;
        
        try {
            PreparedStatement ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                    + "FROM entitycollectionattributes, entityinstances, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                    + "WHERE eca_ena_entityattributeid = ? AND eca_eni_entityinstanceid = ? AND eca_thrutime = ? "
                    + "AND eca_entityinstanceattributeid = eni_entityinstanceid "
                    + "AND eni_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                    + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                    + "ORDER BY cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename, eni_entityuniqueid");

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    private EntityCollectionAttribute getEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, EntityPermission entityPermission) {
        EntityCollectionAttribute entityCollectionAttribute = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitycollectionattributes " +
                        "WHERE eca_ena_entityattributeid = ? AND eca_eni_entityinstanceid = ? AND eca_entityinstanceattributeid = ? " +
                        "AND eca_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitycollectionattributes " +
                        "WHERE eca_ena_entityattributeid = ? AND eca_eni_entityinstanceid = ? AND eca_entityinstanceattributeid = ? " +
                        "AND eca_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityCollectionAttribute = EntityCollectionAttributeFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttribute;
    }
    
    public EntityCollectionAttribute getEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute) {
        return getEntityCollectionAttribute(entityAttribute, entityInstance, entityInstanceAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityCollectionAttribute getEntityCollectionAttributeForUpdate(EntityAttribute entityAttribute,
            EntityInstance entityInstance, EntityInstance entityInstanceAttribute) {
        return getEntityCollectionAttribute(entityAttribute, entityInstance, entityInstanceAttribute, EntityPermission.READ_WRITE);
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            PreparedStatement ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_eni_entityinstanceid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            PreparedStatement ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_eni_entityinstanceid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityInstanceAttributeForUpdate(EntityInstance entityInstanceAttribute) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            PreparedStatement ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_entityinstanceattributeid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public EntityCollectionAttributeTransfer getEntityCollectionAttributeTransfer(UserVisit userVisit, EntityCollectionAttribute entityCollectionAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityCollectionAttributeTransferCache().getEntityCollectionAttributeTransfer(entityCollectionAttribute, entityInstance);
    }
    
    public List<EntityCollectionAttributeTransfer> getEntityCollectionAttributeTransfers(UserVisit userVisit, List<EntityCollectionAttribute> entityCollectionAttributes, EntityInstance entityInstance) {
        List<EntityCollectionAttributeTransfer> entityCollectionAttributeTransfers = new ArrayList<>(entityCollectionAttributes.size());
        EntityCollectionAttributeTransferCache entityCollectionAttributeTransferCache = getCoreTransferCaches(userVisit).getEntityCollectionAttributeTransferCache();
        
        entityCollectionAttributes.stream().forEach((entityCollectionAttribute) -> {
            entityCollectionAttributeTransfers.add(entityCollectionAttributeTransferCache.getEntityCollectionAttributeTransfer(entityCollectionAttribute, entityInstance));
        });
        
        return entityCollectionAttributeTransfers;
    }
    
    public List<EntityCollectionAttributeTransfer> getEntityCollectionAttributeTransfers(UserVisit userVisit, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityCollectionAttributeTransfers(userVisit, getEntityCollectionAttributes(entityAttribute, entityInstance), entityInstance);
    }
    
    public void deleteEntityCollectionAttribute(EntityCollectionAttribute entityCollectionAttribute, BasePK deletedBy) {
        EntityAttribute entityAttribute = entityCollectionAttribute.getEntityAttribute();
        EntityInstance entityInstance = entityCollectionAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityCollectionAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityCollectionAttribute.remove();
        }
        
        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAttribute.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteEntityCollectionAttributes(List<EntityCollectionAttribute> entityCollectionAttributes, BasePK deletedBy) {
        entityCollectionAttributes.stream().forEach((entityCollectionAttribute) -> {
            deleteEntityCollectionAttribute(entityCollectionAttribute, deletedBy);
        });
    }
    
    public void deleteEntityCollectionAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityCollectionAttributes(getEntityCollectionAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityCollectionAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityCollectionAttributes(getEntityCollectionAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
        deleteEntityCollectionAttributes(getEntityCollectionAttributesByEntityInstanceAttributeForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Base Encryption Keys
    // --------------------------------------------------------------------------------
    
    public BaseEncryptionKey createBaseEncryptionKey(ExecutionErrorAccumulator eea, BaseKey baseKey1, BaseKey baseKey2, PartyPK createdBy) {
        BaseEncryptionKey activeBaseEncryptionKey = getActiveBaseEncryptionKey();
        BaseEncryptionKey baseEncryptionKey = null;
        
        if(activeBaseEncryptionKey != null) {
            setBaseEncryptionKeyStatus(eea, activeBaseEncryptionKey, WorkflowDestination_BASE_ENCRYPTION_KEY_STATUS_ACTIVE_TO_INACTIVE, createdBy);
        }
        
        if(!eea.hasExecutionErrors()) {
            var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
            Sequence sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.BASE_ENCRYPTION_KEY.name());
            String baseEncryptionKeyName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
            String sha1Hash = Sha1Utils.getInstance().encode(baseKey1, baseKey2);
            baseEncryptionKey = createBaseEncryptionKey(baseEncryptionKeyName, sha1Hash, createdBy);

            EntityInstance entityInstance = getEntityInstanceByBaseEntity(baseEncryptionKey);
            getWorkflowControl().addEntityToWorkflowUsingNames(null, Workflow_BASE_ENCRYPTION_KEY_STATUS, entityInstance, null, null,createdBy);
        }
        
        return baseEncryptionKey;
    }
    
    public BaseEncryptionKey createBaseEncryptionKey(String baseEncryptionKeyName, String sha1Hash, BasePK createdBy) {
        BaseEncryptionKey baseEncryptionKey = BaseEncryptionKeyFactory.getInstance().create(baseEncryptionKeyName,
                sha1Hash);
        
        sendEventUsingNames(baseEncryptionKey.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return baseEncryptionKey;
    }
    
    private List<BaseEncryptionKey> getBaseEncryptionKeys(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM baseencryptionkeys " +
                    "ORDER BY bek_baseencryptionkeyname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM baseencryptionkeys " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query);
        
        return BaseEncryptionKeyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<BaseEncryptionKey> getBaseEncryptionKeys() {
        return getBaseEncryptionKeys(EntityPermission.READ_ONLY);
    }
    
    public List<BaseEncryptionKey> getBaseEncryptionKeysForUpdate() {
        return getBaseEncryptionKeys(EntityPermission.READ_WRITE);
    }
    
    private BaseEncryptionKey getActiveBaseEncryptionKey(EntityPermission entityPermission) {
        WorkflowStep workflowStep = getWorkflowControl().getWorkflowStepUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS,
                WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE);
        BaseEncryptionKey baseEncryptionKey = null;
        
        if(workflowStep != null) {
            List<BaseEncryptionKey> baseEncryptionKeys = null;
            
            try {
                StringBuilder query = new StringBuilder("SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, " +
                        "baseencryptionkeys, workflowentitystatuses, entitytimes " +
                        "WHERE cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                        "AND ent_activedetailid = entdt_entitytypedetailid " +
                        "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                        "AND entdt_entitytypename = ? " +
                        "AND ent_entitytypeid = eni_ent_entitytypeid AND bek_baseencryptionkeyid = eni_entityuniqueid " +
                        "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                        "AND eni_entityinstanceid = etim_eni_entityinstanceid ");
                
                if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                    query.append("ORDER BY etim_createdtime DESC");
                } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                    query.append("FOR UPDATE");
                }
                
                PreparedStatement ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query.toString());
                
                ps.setString(1, ComponentVendors.ECHOTHREE.name());
                ps.setString(2, EntityTypes.BaseEncryptionKey.name());
                ps.setLong(3, workflowStep.getPrimaryKey().getEntityId());
                ps.setLong(4, Session.MAX_TIME);
                
                baseEncryptionKeys = BaseEncryptionKeyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
            
            if(!baseEncryptionKeys.isEmpty()) {
                baseEncryptionKey = baseEncryptionKeys.iterator().next();
            }
        }
        
        return baseEncryptionKey;
    }
    
    public BaseEncryptionKey getActiveBaseEncryptionKey() {
        return getActiveBaseEncryptionKey(EntityPermission.READ_ONLY);
    }
    
    public BaseEncryptionKey getActiveBaseEncryptionKeyForUpdate() {
        return getActiveBaseEncryptionKey(EntityPermission.READ_WRITE);
    }
    
    private BaseEncryptionKey getBaseEncryptionKeyByName(String baseEncryptionKeyName, EntityPermission entityPermission) {
        BaseEncryptionKey baseEncryptionKey = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_baseencryptionkeyname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_baseencryptionkeyname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, baseEncryptionKeyName);
            
            baseEncryptionKey = BaseEncryptionKeyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return baseEncryptionKey;
    }
    
    public BaseEncryptionKey getBaseEncryptionKeyByName(String baseEncryptionKeyName) {
        return getBaseEncryptionKeyByName(baseEncryptionKeyName, EntityPermission.READ_ONLY);
    }
    
    public BaseEncryptionKey getBaseEncryptionKeyByNameForUpdate(String baseEncryptionKeyName) {
        return getBaseEncryptionKeyByName(baseEncryptionKeyName, EntityPermission.READ_WRITE);
    }
    
    private BaseEncryptionKey getBaseEncryptionKeyBySha1Hash(String sha1Hash, EntityPermission entityPermission) {
        BaseEncryptionKey baseEncryptionKey = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_sha1hash = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_sha1hash = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, sha1Hash);
            
            baseEncryptionKey = BaseEncryptionKeyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return baseEncryptionKey;
    }
    
    public BaseEncryptionKey getBaseEncryptionKeyBySha1Hash(String sha1Hash) {
        return getBaseEncryptionKeyBySha1Hash(sha1Hash, EntityPermission.READ_ONLY);
    }
    
    public BaseEncryptionKey getBaseEncryptionKeyBySha1HashForUpdate(String sha1Hash) {
        return getBaseEncryptionKeyBySha1Hash(sha1Hash, EntityPermission.READ_WRITE);
    }
    
    public BaseEncryptionKeyTransfer getBaseEncryptionKeyTransfer(UserVisit userVisit, BaseEncryptionKey baseEncryptionKey) {
        return getCoreTransferCaches(userVisit).getBaseEncryptionKeyTransferCache().getBaseEncryptionKeyTransfer(baseEncryptionKey);
    }
    
    public BaseEncryptionKeyTransfer getActiveBaseEncryptionKeyTransfer(UserVisit userVisit) {
        return getCoreTransferCaches(userVisit).getBaseEncryptionKeyTransferCache().getBaseEncryptionKeyTransfer(getActiveBaseEncryptionKey());
    }
    
    public List<BaseEncryptionKeyTransfer> getBaseEncryptionKeyTransfers(UserVisit userVisit) {
        List<BaseEncryptionKey> baseEncryptionKeys = getBaseEncryptionKeys();
        List<BaseEncryptionKeyTransfer> baseEncryptionKeyTransfers = new ArrayList<>(baseEncryptionKeys.size());
        BaseEncryptionKeyTransferCache baseEncryptionKeyTransferCache = getCoreTransferCaches(userVisit).getBaseEncryptionKeyTransferCache();
        
        baseEncryptionKeys.stream().forEach((baseEncryptionKey) -> {
            baseEncryptionKeyTransfers.add(baseEncryptionKeyTransferCache.getBaseEncryptionKeyTransfer(baseEncryptionKey));
        });
        
        return baseEncryptionKeyTransfers;
    }
    
    public BaseEncryptionKeyStatusChoicesBean getBaseEncryptionKeyStatusChoices(String defaultBaseEncryptionKeyStatusChoice,
            Language language, boolean allowNullChoice, BaseEncryptionKey baseEncryptionKey, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        BaseEncryptionKeyStatusChoicesBean baseEncryptionKeyStatusChoicesBean = new BaseEncryptionKeyStatusChoicesBean();
        
        if(baseEncryptionKey == null) {
            workflowControl.getWorkflowEntranceChoices(baseEncryptionKeyStatusChoicesBean, defaultBaseEncryptionKeyStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_BASE_ENCRYPTION_KEY_STATUS), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(baseEncryptionKey.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(baseEncryptionKeyStatusChoicesBean, defaultBaseEncryptionKeyStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return baseEncryptionKeyStatusChoicesBean;
    }
    
    public void setBaseEncryptionKeyStatus(ExecutionErrorAccumulator eea, BaseEncryptionKey baseEncryptionKey, String baseEncryptionKeyStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(baseEncryptionKey);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = baseEncryptionKeyStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), baseEncryptionKeyStatusChoice);
        
        if(workflowDestination != null || baseEncryptionKeyStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownBaseEncryptionKeyStatusChoice.name(), baseEncryptionKeyStatusChoice);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Encryption Keys
    // --------------------------------------------------------------------------------
    
    public EntityEncryptionKey createEntityEncryptionKey(String entityEncryptionKeyName, Boolean isExternal, String secretKey, String initializationVector) {
        return EntityEncryptionKeyFactory.getInstance().create(entityEncryptionKeyName, isExternal, secretKey, initializationVector);
    }
    
    private EntityEncryptionKey getEntityEncryptionKeyByName(String entityEncryptionKeyName, EntityPermission entityPermission) {
        EntityEncryptionKey entityEncryptionKey = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityencryptionkeys " +
                        "WHERE eek_entityencryptionkeyname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityencryptionkeys " +
                        "WHERE eek_entityencryptionkeyname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityEncryptionKeyFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, entityEncryptionKeyName);
            
            entityEncryptionKey = EntityEncryptionKeyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEncryptionKey;
    }
    
    public EntityEncryptionKey getEntityEncryptionKeyByName(String entityEncryptionKeyName) {
        return getEntityEncryptionKeyByName(entityEncryptionKeyName, EntityPermission.READ_ONLY);
    }
    
    public EntityEncryptionKey getEntityEncryptionKeyByNameForUpdate(String entityEncryptionKeyName) {
        return getEntityEncryptionKeyByName(entityEncryptionKeyName, EntityPermission.READ_WRITE);
    }
    
    public List<EntityEncryptionKey> getEntityEncryptionKeysForUpdate() {
        PreparedStatement ps = EntityEncryptionKeyFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM entityencryptionkeys " +
                "FOR UPDATE");
        
        return EntityEncryptionKeyFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
    }
    
    public int countEntityEncryptionKeys() {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM entityencryptionkeys");
    }
    
    // --------------------------------------------------------------------------------
    //   Event Subscribers
    // --------------------------------------------------------------------------------
    
    public EventSubscriber createEventSubscriber(EntityInstance entityInstance, String description, Integer sortOrder,
            BasePK createdBy) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.EVENT_SUBSCRIBER.name());
        Sequence sequence = sequenceControl.getDefaultSequence(sequenceType);
        String eventSubscriberName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createEventSubscriber(eventSubscriberName, entityInstance, description, sortOrder, createdBy);
    }
    
    public EventSubscriber createEventSubscriber(String eventSubscriberName, EntityInstance entityInstance, String description,
            Integer sortOrder, BasePK createdBy) {
        EventSubscriber eventSubscriber = EventSubscriberFactory.getInstance().create();
        EventSubscriberDetail eventSubscriberDetail = EventSubscriberDetailFactory.getInstance().create(session,
                eventSubscriber, eventSubscriberName, entityInstance, description, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        eventSubscriber = EventSubscriberFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                eventSubscriber.getPrimaryKey());
        eventSubscriber.setActiveDetail(eventSubscriberDetail);
        eventSubscriber.setLastDetail(eventSubscriberDetail);
        eventSubscriber.store();
        
        sendEventUsingNames(eventSubscriber.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return eventSubscriber;
    }
    
    private EventSubscriber getEventSubscriberByName(String eventSubscriberName, EntityPermission entityPermission) {
        EventSubscriber eventSubscriber = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eventsubscribername = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eventsubscribername = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EventSubscriberFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, eventSubscriberName);
            
            eventSubscriber = EventSubscriberFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventSubscriber;
    }
    
    public EventSubscriber getEventSubscriberByName(String eventSubscriberName) {
        return getEventSubscriberByName(eventSubscriberName, EntityPermission.READ_ONLY);
    }
    
    public EventSubscriber getEventSubscriberByNameForUpdate(String eventSubscriberName) {
        return getEventSubscriberByName(eventSubscriberName, EntityPermission.READ_WRITE);
    }
    
    private List<EventSubscriber> getEventSubscribersByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<EventSubscriber> eventSubscribers = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eni_entityinstanceid = ? " +
                        "ORDER BY evsdt_sortorder, evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eni_entityinstanceid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EventSubscriberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            
            eventSubscribers = EventSubscriberFactory.getInstance().getEntitiesFromQuery(entityPermission,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventSubscribers;
    }
    
    public List<EventSubscriber> getEventSubscribersByEntityInstance(EntityInstance entityInstance) {
        return getEventSubscribersByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<EventSubscriber> getEventSubscribersByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEventSubscribersByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
//    public EventSubscriberTransfer getEventSubscriberTransfer(UserVisit userVisit, EventSubscriber eventSubscriber) {
//        return getPaymentTransferCaches(userVisit).getEventSubscriberTransferCache().getEventSubscriberTransfer(eventSubscriber);
//    }
//    
//    public List<EventSubscriberTransfer> getEventSubscriberTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
//        List<EventSubscriber> eventSubscribers = getEventSubscribersByEntityInstance(entityInstance);
//        List<EventSubscriberTransfer> eventSubscriberTransfers = new ArrayList<EventSubscriberTransfer>(eventSubscribers.size());
//        EventSubscriberTransferCache eventSubscriberTransferCache = getPaymentTransferCaches(userVisit).getEventSubscriberTransferCache();
//        
//        for(EventSubscriber eventSubscriber: eventSubscribers) {
//            eventSubscriberTransfers.add(eventSubscriberTransferCache.getEventSubscriberTransfer(eventSubscriber));
//        }
//        
//        return eventSubscriberTransfers;
//    }
    
    public void deleteEventSubscriber(EventSubscriber eventSubscriber, BasePK deletedBy) {
        removeQueuedSubscriberEventsByEventSubscriber(eventSubscriber);
        
        EventSubscriberDetail eventSubscriberDetail = eventSubscriber.getLastDetailForUpdate();
        eventSubscriberDetail.setThruTime(session.START_TIME_LONG);
        eventSubscriber.setActiveDetail(null);
        eventSubscriber.store();
        
        sendEventUsingNames(eventSubscriber.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteEventSubscribers(List<EventSubscriber> eventSubscribers, BasePK deletedBy) {
        eventSubscribers.stream().forEach((eventSubscriber) -> {
            deleteEventSubscriber(eventSubscriber, deletedBy);
        });
    }
    
    public void deleteEventSubscribersByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEventSubscribers(getEventSubscribersByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Queued Events
    // --------------------------------------------------------------------------------
    
    public QueuedSubscriberEvent createQueuedSubscriberEvent(EventSubscriber eventSubscriber, Event event) {
        return QueuedSubscriberEventFactory.getInstance().create(eventSubscriber, event);
    }
    
    private List<QueuedSubscriberEvent> getQueuedSubscriberEventsByEventSubscriber(EventSubscriber eventSubscriber,
            EntityPermission entityPermission) {
        List<QueuedSubscriberEvent> queuedSubscriberEvents = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedsubscriberevents, events " +
                        "WHERE qsev_evs_eventsubscriberid = ? AND qsev_ev_eventid = ev_eventid " +
                        "ORDER BY ev_eventtime";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedsubscriberevents " +
                        "WHERE qsev_evs_eventsubscriberid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = QueuedSubscriberEventFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, eventSubscriber.getPrimaryKey().getEntityId());
            
            queuedSubscriberEvents = QueuedSubscriberEventFactory.getInstance().getEntitiesFromQuery(entityPermission,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return queuedSubscriberEvents;
    }
    
    public List<QueuedSubscriberEvent> getQueuedSubscriberEventsByEventSubscriber(EventSubscriber eventSubscriber) {
        return getQueuedSubscriberEventsByEventSubscriber(eventSubscriber, EntityPermission.READ_ONLY);
    }
    
    public List<QueuedSubscriberEvent> getQueuedSubscriberEventsByEventSubscriberForUpdate(EventSubscriber eventSubscriber) {
        return getQueuedSubscriberEventsByEventSubscriber(eventSubscriber, EntityPermission.READ_WRITE);
    }
    
    public void removeQueuedSubscriberEvent(QueuedSubscriberEvent queuedSubscriberEvent) {
        queuedSubscriberEvent.remove();
    }
    
    public void removeQueuedSubscriberEvents(List<QueuedSubscriberEvent> queuedSubscriberEvents) {
        queuedSubscriberEvents.stream().forEach((queuedSubscriberEvent) -> {
            removeQueuedSubscriberEvent(queuedSubscriberEvent);
        });
    }
    
    public void removeQueuedSubscriberEventsByEventSubscriber(EventSubscriber eventSubscriber) {
        removeQueuedSubscriberEvents(getQueuedSubscriberEventsByEventSubscriberForUpdate(eventSubscriber));
    }
    
    // --------------------------------------------------------------------------------
    //   Event Subscriber Event Types
    // --------------------------------------------------------------------------------
    
    public EventSubscriberEventType createEventSubscriberEventType(EventSubscriber eventSubscriber, EventType eventType,
            BasePK createdBy) {
        EventSubscriberEventType eventSubscriberEventType = EventSubscriberEventTypeFactory.getInstance().create(session,
                eventSubscriber, eventType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(eventSubscriber.getPrimaryKey(), EventTypes.MODIFY.name(), eventSubscriberEventType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return eventSubscriberEventType;
    }
    
    private List<EventSubscriberEventType> getEventSubscriberEventTypes(EventType eventType, EntityPermission entityPermission) {
        List<EventSubscriberEventType> eventSubscriberEventTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribereventtypes, eventsubscribers, eventsubscriberdetails " +
                        "WHERE evsevt_evty_eventtypeid = ? AND evsevt_thrutime = ? " +
                        "AND evsevt_evs_eventsubscriberid = evs_eventsubscriberid AND evs_lastdetailid = evsdt_eventsubscriberdetailid " +
                        "ORDER BY evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribereventtypes " +
                        "WHERE evsevt_evty_eventtypeid = ? AND evsevt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EventSubscriberEventTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, eventType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            eventSubscriberEventTypes = EventSubscriberEventTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventSubscriberEventTypes;
    }
    
    public List<EventSubscriberEventType> getEventSubscriberEventTypes(EventType eventType) {
        return getEventSubscriberEventTypes(eventType, EntityPermission.READ_ONLY);
    }
    
    public List<EventSubscriberEventType> getEventSubscriberEventTypesForUpdate(EventType eventType) {
        return getEventSubscriberEventTypes(eventType, EntityPermission.READ_WRITE);
    }
    
    // --------------------------------------------------------------------------------
    //   Event Subscriber Entity Types
    // --------------------------------------------------------------------------------
    
    public EventSubscriberEntityType createEventSubscriberEntityType(EventSubscriber eventSubscriber, EntityType entityType,
            EventType eventType, BasePK createdBy) {
        EventSubscriberEntityType eventSubscriberEntityType = EventSubscriberEntityTypeFactory.getInstance().create(session,
                eventSubscriber, entityType, eventType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(eventSubscriber.getPrimaryKey(), EventTypes.MODIFY.name(), eventSubscriberEntityType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return eventSubscriberEntityType;
    }
    
    private List<EventSubscriberEntityType> getEventSubscriberEntityTypes(EntityType entityType, EventType eventType, 
            EntityPermission entityPermission) {
        List<EventSubscriberEntityType> eventSubscriberEntityTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentitytypes, eventsubscribers, eventsubscriberdetails " +
                        "WHERE evset_ent_entitytypeid = ? AND evset_evty_eventtypeid = ? AND evset_thrutime = ? " +
                        "AND evset_evs_eventsubscriberid = evs_eventsubscriberid AND evs_lastdetailid = evsdt_eventsubscriberdetailid " +
                        "ORDER BY evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentitytypes " +
                        "WHERE evset_ent_entitytypeid = ? AND evset_evty_eventtypeid = ? AND evset_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EventSubscriberEntityTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, eventType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            eventSubscriberEntityTypes = EventSubscriberEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventSubscriberEntityTypes;
    }
    
    public List<EventSubscriberEntityType> getEventSubscriberEntityTypes(EntityType entityType, EventType eventType) {
        return getEventSubscriberEntityTypes(entityType, eventType, EntityPermission.READ_ONLY);
    }
    
    public List<EventSubscriberEntityType> getEventSubscriberEntityTypesForUpdate(EntityType entityType, EventType eventType) {
        return getEventSubscriberEntityTypes(entityType, eventType, EntityPermission.READ_WRITE);
    }
    
    // --------------------------------------------------------------------------------
    //   Event Subscriber Entity Instances
    // --------------------------------------------------------------------------------
    
    public EventSubscriberEntityInstance createEventSubscriberEntityInstance(EventSubscriber eventSubscriber,
            EntityInstance entityInstance, EventType eventType, BasePK createdBy) {
        EventSubscriberEntityInstance eventSubscriberEntityInstance = EventSubscriberEntityInstanceFactory.getInstance().create(session,
                eventSubscriber, entityInstance, eventType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(eventSubscriber.getPrimaryKey(), EventTypes.MODIFY.name(), eventSubscriberEntityInstance.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return eventSubscriberEntityInstance;
    }
    
    private List<EventSubscriberEntityInstance> getEventSubscriberEntityInstances(EntityInstance entityInstance, 
            EventType eventType, EntityPermission entityPermission) {
        List<EventSubscriberEntityInstance> eventSubscriberEntityInstances = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentityinstances, eventsubscribers, eventsubscriberdetails " +
                        "WHERE evsei_eni_entityinstanceid = ? AND evsei_evty_eventtypeid = ? AND evsei_thrutime = ? " +
                        "AND evsei_evs_eventsubscriberid = evs_eventsubscriberid AND evs_lastdetailid = evsdt_eventsubscriberdetailid " +
                        "ORDER BY evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentityinstances " +
                        "WHERE evsei_eni_entityinstanceid = ? AND evsei_evty_eventtypeid = ? AND evsei_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EventSubscriberEntityInstanceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, eventType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            eventSubscriberEntityInstances = EventSubscriberEntityInstanceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventSubscriberEntityInstances;
    }
    
    public List<EventSubscriberEntityInstance> getEventSubscriberEntityInstances(EntityInstance entityInstance, EventType eventType) {
        return getEventSubscriberEntityInstances(entityInstance, eventType, EntityPermission.READ_ONLY);
    }
    
    public List<EventSubscriberEntityInstance> getEventSubscriberEntityInstancesForUpdate(EntityInstance entityInstance, EventType eventType) {
        return getEventSubscriberEntityInstances(entityInstance, eventType, EntityPermission.READ_WRITE);
    }
    
    // --------------------------------------------------------------------------------
    //   Utilities
    // --------------------------------------------------------------------------------
    
    private void queueEntityInstanceToIndexing(EntityInstance entityInstance) {
        var indexControl = (IndexControl)Session.getModelController(IndexControl.class);

        if(indexControl.isEntityTypeUsedByIndexTypes(entityInstance.getEntityType())) {
            try {
                QueuedEntityLogic.getInstance().createQueuedEntityUsingNames(null, QueueConstants.QueueType_INDEXING, entityInstance);
            } catch(UnknownQueueTypeNameException uqtne) {
                // This will be thrown early in the setup process because the QueueType has not yet been created.
                // Log as an informational message, but otherwise ignore it.
                getLog().info(uqtne.getMessage());
            }
        }
    }
    
    public Event sendEvent(EntityInstance entityInstance, EventType eventType, EntityInstance relatedEntityInstance,
            EventType relatedEventType, BasePK createdByPK) {
        Long eventTime = session.START_TIME_LONG;
        Event event = null;
        EntityInstance createdByEntityInstance = createdByPK == null ? null : getEntityInstanceByBasePK(createdByPK);
        EntityTime entityTime = getEntityTimeForUpdate(entityInstance);
        
        if(CoreDebugFlags.LogSentEvents) {
            getLog().info("entityInstance = " + entityInstance
                    + ", eventType = " + eventType.getEventTypeName()
                    + ", relatedEntityInstance = " + relatedEntityInstance
                    + ", relatedEventType = " + relatedEventType == null ? "(null)" : relatedEventType.getEventTypeName()
                    + ", createdByEntityInstance = " + createdByEntityInstance);
        }
        
        if(entityTime == null) {
            // Initially created read-only, convert to read/write. If we're in sendEvent(...),
            // we need an EntityTime. If there wasn't one previously, we do the best we can,
            // using the eventTIme as its createdTime.
            createEntityTime(entityInstance, eventTime, null, null);
            entityTime = getEntityTimeForUpdate(entityInstance);
        }
        
        if(eventType.getUpdateCreatedTime()) {
            entityTime.setCreatedTime(eventTime);
        }
        
        boolean clearCache = false;
        if(eventType.getUpdateModifiedTime()) {
            entityTime.setModifiedTime(eventTime);
            clearCache = true;
        }

        if(eventType.getUpdateDeletedTime()) {
            deleteEntityInstanceDependencies(entityInstance, createdByPK);
            entityTime.setDeletedTime(eventTime);
            clearCache = true;
        }
        
        if(clearCache) {
            removeCacheEntriesByEntityInstance(entityInstance);
        }
        
        queueEntityInstanceToIndexing(entityInstance);
        
        boolean suppressEvent = false;
        if(eventType.getUpdateVisitedTime() && createdByEntityInstance != null) {
            EntityVisit entityVisit = getEntityVisitForUpdate(createdByEntityInstance, entityInstance);
            
            if(entityVisit == null) {
                createEntityVisit(createdByEntityInstance, entityInstance);
            } else {
                Long modifiedTime = entityTime.getModifiedTime();
                
                // Prefer the real modified time, but if that's null (meaning it has never been modified),
                // fall back to the created time.
                if(modifiedTime == null) {
                    modifiedTime = entityTime.getCreatedTime();
                }
                
                if(entityVisit.getVisitedTime() >= modifiedTime && !entityInstance.getEntityType().getLastDetail().getKeepAllHistory()) {
                    suppressEvent = true;
                }
                
                entityVisit.setVisitedTime(eventTime);
            }
        }
        
        if(eventType.getKeepHistory() && !suppressEvent) {
            Integer maximumHistory = eventType.getMaximumHistory();
            EventGroup eventgroup = createdByPK == null? null: getActiveEventGroup(createdByPK);
            
            event = createEvent(eventgroup, eventTime, entityInstance, eventType, relatedEntityInstance, relatedEventType, createdByEntityInstance);
            
            if(eventType.getQueueToSubscribers()) {
                createQueuedEvent(event);
            }
            
            if(maximumHistory != null) {
                List<Event> events = getEventsByEntityInstanceAndEventTypeForUpdate(entityInstance, eventType);
                int eventCountToRemove = events.size() - maximumHistory;
                
                if(eventCountToRemove > 0) {
                    Iterator<Event> i = events.iterator();
                    
                    for(int j = 0; j < eventCountToRemove; j++) {
                        removeEvent(i.next());
                    }
                }
            }
        }
        
        return event;
    }
    
    @Override
    public Event sendEventUsingNames(BasePK entityInstancePK, String eventTypeName, BasePK relatedPK, String relatedEventTypeName, BasePK createdByPK) {
        Event event = null;
        EntityInstance entityInstance = getEntityInstanceByBasePK(entityInstancePK);
        
        if(entityInstance == null) {
            getLog().error("sendEventUsingNames: getEntityInstanceByBasePK failed on " + entityInstancePK.toString());
        } else {
            EntityInstance relatedEntityInstance = relatedPK == null? null: getEntityInstanceByBasePK(relatedPK);
            
            event = sendEventUsingNames(entityInstance, eventTypeName, relatedEntityInstance, relatedEventTypeName, createdByPK);
        }
        
        return event;
    }
    
    @Override
    public Event sendEventUsingNames(EntityInstance entityInstance, String eventTypeName, BasePK relatedPK, String relatedEventTypeName, BasePK createdByPK) {
        EntityInstance relatedEntityInstance = relatedPK == null? null: getEntityInstanceByBasePK(relatedPK);
        
        return sendEventUsingNames(entityInstance, eventTypeName, relatedEntityInstance, relatedEventTypeName, createdByPK);
    }
    
    @Override
    public Event sendEventUsingNames(EntityInstance entityInstance, String eventTypeName, EntityInstance relatedEntityInstance, String relatedEventTypeName, BasePK createdByPK) {
        EventType eventType = getEventTypeByName(eventTypeName);
        EventType relatedEventType = relatedEventTypeName == null? null: getEventTypeByName(relatedEventTypeName);
        
        return sendEvent(entityInstance, eventType, relatedEntityInstance, relatedEventType, createdByPK);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Entity Types
    // --------------------------------------------------------------------------------
    
    public PartyEntityType createPartyEntityType(Party party, EntityType entityType, Boolean confirmDelete, BasePK createdBy) {
        PartyEntityType partyEntityType = PartyEntityTypeFactory.getInstance().create(party, entityType, confirmDelete, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyEntityType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return partyEntityType;
    }

    private PartyEntityType getPartyEntityType(Party party, EntityType entityType, EntityPermission entityPermission) {
        PartyEntityType partyEntityType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes " +
                        "WHERE pent_par_partyid = ? AND pent_ent_entitytypeid = ? AND pent_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes " +
                        "WHERE pent_par_partyid = ? AND pent_ent_entitytypeid = ? AND pent_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyEntityTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, entityType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            partyEntityType = PartyEntityTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyEntityType;
    }

    public PartyEntityType getPartyEntityType(Party party, EntityType entityType) {
        return getPartyEntityType(party, entityType, EntityPermission.READ_ONLY);
    }

    public PartyEntityType getPartyEntityTypeForUpdate(Party party, EntityType entityType) {
        return getPartyEntityType(party, entityType, EntityPermission.READ_WRITE);
    }

    public PartyEntityTypeValue getPartyEntityTypeValue(PartyEntityType partyEntityType) {
        return partyEntityType == null ? null : partyEntityType.getPartyEntityTypeValue().clone();
    }

    public PartyEntityTypeValue getPartyEntityTypeValueForUpdate(Party party, EntityType entityType) {
        return getPartyEntityTypeValue(getPartyEntityTypeForUpdate(party, entityType));
    }

    private List<PartyEntityType> getPartyEntityTypesByParty(Party party, EntityPermission entityPermission) {
        List<PartyEntityType> partyEntityTypes = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes, entitytypes, entitytypedetails, componentvendors, componentvendordetails " +
                        "WHERE pent_par_partyid = ? AND pent_thrutime = ? " +
                        "AND pent_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid " +
                        "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid " +
                        "ORDER BY entdt_sortorder, entdt_entitytypename, cvndd_componentvendorname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes " +
                        "WHERE pent_par_partyid = ? AND pent_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyEntityTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            partyEntityTypes = PartyEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyEntityTypes;
    }

    public List<PartyEntityType> getPartyEntityTypesByParty(Party party) {
        return getPartyEntityTypesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyEntityType> getPartyEntityTypesByPartyForUpdate(Party party) {
        return getPartyEntityTypesByParty(party, EntityPermission.READ_WRITE);
    }

    public PartyEntityTypeTransfer getPartyEntityTypeTransfer(UserVisit userVisit, PartyEntityType partyEntityType) {
        return getCoreTransferCaches(userVisit).getPartyEntityTypeTransferCache().getPartyEntityTypeTransfer(partyEntityType);
    }

    public List<PartyEntityTypeTransfer> getPartyEntityTypeTransfersByParty(UserVisit userVisit, Party party) {
        List<PartyEntityType> partyEntityTypes = getPartyEntityTypesByParty(party);
        List<PartyEntityTypeTransfer> partyEntityTypeTransfers = new ArrayList<>(partyEntityTypes.size());
        PartyEntityTypeTransferCache partyEntityTypeTransferCache = getCoreTransferCaches(userVisit).getPartyEntityTypeTransferCache();

        partyEntityTypes.stream().forEach((partyEntityType) -> {
            partyEntityTypeTransfers.add(partyEntityTypeTransferCache.getPartyEntityTypeTransfer(partyEntityType));
        });

        return partyEntityTypeTransfers;
    }

    public void updatePartyEntityTypeFromValue(PartyEntityTypeValue partyEntityTypeValue, BasePK updatedBy) {
        if(partyEntityTypeValue.hasBeenModified()) {
            PartyEntityType partyEntityType = PartyEntityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyEntityTypeValue.getPrimaryKey());

            partyEntityType.setThruTime(session.START_TIME_LONG);
            partyEntityType.store();

            PartyPK partyPK = partyEntityType.getPartyPK(); // Not updated
            EntityTypePK entityTypePK = partyEntityType.getEntityTypePK(); // Not updated
            Boolean confirmDelete = partyEntityTypeValue.getConfirmDelete();

            partyEntityType = PartyEntityTypeFactory.getInstance().create(partyPK, entityTypePK, confirmDelete, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), partyEntityType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deletePartyEntityType(PartyEntityType partyEntityType, BasePK deletedBy) {
        partyEntityType.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(partyEntityType.getPartyPK(), EventTypes.MODIFY.name(), partyEntityType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deletePartyEntityTypesByParty(Party party, BasePK deletedBy) {
        getPartyEntityTypesByPartyForUpdate(party).stream().forEach((partyEntityType) -> {
            deletePartyEntityType(partyEntityType, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Applications
    // --------------------------------------------------------------------------------

    public Application createApplication(String applicationName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Application defaultApplication = getDefaultApplication();
        boolean defaultFound = defaultApplication != null;

        if(defaultFound && isDefault) {
            ApplicationDetailValue defaultApplicationDetailValue = getDefaultApplicationDetailValueForUpdate();

            defaultApplicationDetailValue.setIsDefault(Boolean.FALSE);
            updateApplicationFromValue(defaultApplicationDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Application application = ApplicationFactory.getInstance().create();
        ApplicationDetail applicationDetail = ApplicationDetailFactory.getInstance().create(application, applicationName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        application = ApplicationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, application.getPrimaryKey());
        application.setActiveDetail(applicationDetail);
        application.setLastDetail(applicationDetail);
        application.store();

        sendEventUsingNames(application.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return application;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Application */
    public Application getApplicationByEntityInstance(EntityInstance entityInstance) {
        ApplicationPK pk = new ApplicationPK(entityInstance.getEntityUniqueId());
        Application application = ApplicationFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return application;
    }

    private static final Map<EntityPermission, String> getApplicationByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM applications, applicationdetails " +
                "WHERE appl_activedetailid = appldt_applicationdetailid " +
                "AND appldt_applicationname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM applications, applicationdetails " +
                "WHERE appl_activedetailid = appldt_applicationdetailid " +
                "AND appldt_applicationname = ? " +
                "FOR UPDATE");
        getApplicationByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Application getApplicationByName(String applicationName, EntityPermission entityPermission) {
        return ApplicationFactory.getInstance().getEntityFromQuery(entityPermission, getApplicationByNameQueries, applicationName);
    }

    public Application getApplicationByName(String applicationName) {
        return getApplicationByName(applicationName, EntityPermission.READ_ONLY);
    }

    public Application getApplicationByNameForUpdate(String applicationName) {
        return getApplicationByName(applicationName, EntityPermission.READ_WRITE);
    }

    public ApplicationDetailValue getApplicationDetailValueForUpdate(Application application) {
        return application == null? null: application.getLastDetailForUpdate().getApplicationDetailValue().clone();
    }

    public ApplicationDetailValue getApplicationDetailValueByNameForUpdate(String applicationName) {
        return getApplicationDetailValueForUpdate(getApplicationByNameForUpdate(applicationName));
    }

    private static final Map<EntityPermission, String> getDefaultApplicationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM applications, applicationdetails " +
                "WHERE appl_activedetailid = appldt_applicationdetailid " +
                "AND appldt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM applications, applicationdetails " +
                "WHERE appl_activedetailid = appldt_applicationdetailid " +
                "AND appldt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultApplicationQueries = Collections.unmodifiableMap(queryMap);
    }

    private Application getDefaultApplication(EntityPermission entityPermission) {
        return ApplicationFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultApplicationQueries);
    }

    public Application getDefaultApplication() {
        return getDefaultApplication(EntityPermission.READ_ONLY);
    }

    public Application getDefaultApplicationForUpdate() {
        return getDefaultApplication(EntityPermission.READ_WRITE);
    }

    public ApplicationDetailValue getDefaultApplicationDetailValueForUpdate() {
        return getDefaultApplicationForUpdate().getLastDetailForUpdate().getApplicationDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getApplicationsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM applications, applicationdetails " +
                "WHERE appl_activedetailid = appldt_applicationdetailid " +
                "ORDER BY appldt_sortorder, appldt_applicationname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM applications, applicationdetails " +
                "WHERE appl_activedetailid = appldt_applicationdetailid " +
                "FOR UPDATE");
        getApplicationsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Application> getApplications(EntityPermission entityPermission) {
        return ApplicationFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationsQueries);
    }

    public List<Application> getApplications() {
        return getApplications(EntityPermission.READ_ONLY);
    }

    public List<Application> getApplicationsForUpdate() {
        return getApplications(EntityPermission.READ_WRITE);
    }

   public ApplicationTransfer getApplicationTransfer(UserVisit userVisit, Application application) {
        return getCoreTransferCaches(userVisit).getApplicationTransferCache().getApplicationTransfer(application);
    }

    public List<ApplicationTransfer> getApplicationTransfers(UserVisit userVisit) {
        List<Application> applications = getApplications();
        List<ApplicationTransfer> applicationTransfers = new ArrayList<>(applications.size());
        ApplicationTransferCache applicationTransferCache = getCoreTransferCaches(userVisit).getApplicationTransferCache();

        applications.stream().forEach((application) -> {
            applicationTransfers.add(applicationTransferCache.getApplicationTransfer(application));
        });

        return applicationTransfers;
    }

    public ApplicationChoicesBean getApplicationChoices(String defaultApplicationChoice, Language language, boolean allowNullChoice) {
        List<Application> applications = getApplications();
        int size = applications.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultApplicationChoice == null) {
                defaultValue = "";
            }
        }

        for(Application application: applications) {
            ApplicationDetail applicationDetail = application.getLastDetail();

            String label = getBestApplicationDescription(application, language);
            String value = applicationDetail.getApplicationName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultApplicationChoice == null? false: defaultApplicationChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && applicationDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ApplicationChoicesBean(labels, values, defaultValue);
    }

    private void updateApplicationFromValue(ApplicationDetailValue applicationDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(applicationDetailValue.hasBeenModified()) {
            Application application = ApplicationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     applicationDetailValue.getApplicationPK());
            ApplicationDetail applicationDetail = application.getActiveDetailForUpdate();

            applicationDetail.setThruTime(session.START_TIME_LONG);
            applicationDetail.store();

            ApplicationPK applicationPK = applicationDetail.getApplicationPK(); // Not updated
            String applicationName = applicationDetailValue.getApplicationName();
            Boolean isDefault = applicationDetailValue.getIsDefault();
            Integer sortOrder = applicationDetailValue.getSortOrder();

            if(checkDefault) {
                Application defaultApplication = getDefaultApplication();
                boolean defaultFound = defaultApplication != null && !defaultApplication.equals(application);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ApplicationDetailValue defaultApplicationDetailValue = getDefaultApplicationDetailValueForUpdate();

                    defaultApplicationDetailValue.setIsDefault(Boolean.FALSE);
                    updateApplicationFromValue(defaultApplicationDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            applicationDetail = ApplicationDetailFactory.getInstance().create(applicationPK, applicationName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            application.setActiveDetail(applicationDetail);
            application.setLastDetail(applicationDetail);

            sendEventUsingNames(applicationPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateApplicationFromValue(ApplicationDetailValue applicationDetailValue, BasePK updatedBy) {
        updateApplicationFromValue(applicationDetailValue, true, updatedBy);
    }

    private void deleteApplication(Application application, boolean checkDefault, BasePK deletedBy) {
        ApplicationDetail applicationDetail = application.getLastDetailForUpdate();

        deleteApplicationDescriptionsByApplication(application, deletedBy);
        deleteApplicationEditorsByApplication(application, deletedBy);
        deleteApplicationEditorUsesByApplication(application, deletedBy);

        applicationDetail.setThruTime(session.START_TIME_LONG);
        application.setActiveDetail(null);
        application.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            Application defaultApplication = getDefaultApplication();

            if(defaultApplication == null) {
                List<Application> applications = getApplicationsForUpdate();

                if(!applications.isEmpty()) {
                    Iterator<Application> iter = applications.iterator();
                    if(iter.hasNext()) {
                        defaultApplication = iter.next();
                    }
                    ApplicationDetailValue applicationDetailValue = defaultApplication.getLastDetailForUpdate().getApplicationDetailValue().clone();

                    applicationDetailValue.setIsDefault(Boolean.TRUE);
                    updateApplicationFromValue(applicationDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(application.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteApplication(Application application, BasePK deletedBy) {
        deleteApplication(application, true, deletedBy);
    }

    private void deleteApplications(List<Application> applications, boolean checkDefault, BasePK deletedBy) {
        applications.stream().forEach((application) -> {
            deleteApplication(application, checkDefault, deletedBy);
        });
    }

    public void deleteApplications(List<Application> applications, BasePK deletedBy) {
        deleteApplications(applications, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Application Descriptions
    // --------------------------------------------------------------------------------

    public ApplicationDescription createApplicationDescription(Application application, Language language, String description, BasePK createdBy) {
        ApplicationDescription applicationDescription = ApplicationDescriptionFactory.getInstance().create(application, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(application.getPrimaryKey(), EventTypes.MODIFY.name(), applicationDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return applicationDescription;
    }

    private static final Map<EntityPermission, String> getApplicationDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM applicationdescriptions " +
                "WHERE appld_appl_applicationid = ? AND appld_lang_languageid = ? AND appld_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM applicationdescriptions " +
                "WHERE appld_appl_applicationid = ? AND appld_lang_languageid = ? AND appld_thrutime = ? " +
                "FOR UPDATE");
        getApplicationDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationDescription getApplicationDescription(Application application, Language language, EntityPermission entityPermission) {
        return ApplicationDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getApplicationDescriptionQueries,
                application, language, Session.MAX_TIME);
    }

    public ApplicationDescription getApplicationDescription(Application application, Language language) {
        return getApplicationDescription(application, language, EntityPermission.READ_ONLY);
    }

    public ApplicationDescription getApplicationDescriptionForUpdate(Application application, Language language) {
        return getApplicationDescription(application, language, EntityPermission.READ_WRITE);
    }

    public ApplicationDescriptionValue getApplicationDescriptionValue(ApplicationDescription applicationDescription) {
        return applicationDescription == null? null: applicationDescription.getApplicationDescriptionValue().clone();
    }

    public ApplicationDescriptionValue getApplicationDescriptionValueForUpdate(Application application, Language language) {
        return getApplicationDescriptionValue(getApplicationDescriptionForUpdate(application, language));
    }

    private static final Map<EntityPermission, String> getApplicationDescriptionsByApplicationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM applicationdescriptions, languages " +
                "WHERE appld_appl_applicationid = ? AND appld_thrutime = ? AND appld_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM applicationdescriptions " +
                "WHERE appld_appl_applicationid = ? AND appld_thrutime = ? " +
                "FOR UPDATE");
        getApplicationDescriptionsByApplicationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationDescription> getApplicationDescriptionsByApplication(Application application, EntityPermission entityPermission) {
        return ApplicationDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationDescriptionsByApplicationQueries,
                application, Session.MAX_TIME);
    }

    public List<ApplicationDescription> getApplicationDescriptionsByApplication(Application application) {
        return getApplicationDescriptionsByApplication(application, EntityPermission.READ_ONLY);
    }

    public List<ApplicationDescription> getApplicationDescriptionsByApplicationForUpdate(Application application) {
        return getApplicationDescriptionsByApplication(application, EntityPermission.READ_WRITE);
    }

    public String getBestApplicationDescription(Application application, Language language) {
        String description;
        ApplicationDescription applicationDescription = getApplicationDescription(application, language);

        if(applicationDescription == null && !language.getIsDefault()) {
            applicationDescription = getApplicationDescription(application, getPartyControl().getDefaultLanguage());
        }

        if(applicationDescription == null) {
            description = application.getLastDetail().getApplicationName();
        } else {
            description = applicationDescription.getDescription();
        }

        return description;
    }

    public ApplicationDescriptionTransfer getApplicationDescriptionTransfer(UserVisit userVisit, ApplicationDescription applicationDescription) {
        return getCoreTransferCaches(userVisit).getApplicationDescriptionTransferCache().getApplicationDescriptionTransfer(applicationDescription);
    }

    public List<ApplicationDescriptionTransfer> getApplicationDescriptionTransfersByApplication(UserVisit userVisit, Application application) {
        List<ApplicationDescription> applicationDescriptions = getApplicationDescriptionsByApplication(application);
        List<ApplicationDescriptionTransfer> applicationDescriptionTransfers = new ArrayList<>(applicationDescriptions.size());
        ApplicationDescriptionTransferCache applicationDescriptionTransferCache = getCoreTransferCaches(userVisit).getApplicationDescriptionTransferCache();

        applicationDescriptions.stream().forEach((applicationDescription) -> {
            applicationDescriptionTransfers.add(applicationDescriptionTransferCache.getApplicationDescriptionTransfer(applicationDescription));
        });

        return applicationDescriptionTransfers;
    }

    public void updateApplicationDescriptionFromValue(ApplicationDescriptionValue applicationDescriptionValue, BasePK updatedBy) {
        if(applicationDescriptionValue.hasBeenModified()) {
            ApplicationDescription applicationDescription = ApplicationDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    applicationDescriptionValue.getPrimaryKey());

            applicationDescription.setThruTime(session.START_TIME_LONG);
            applicationDescription.store();

            Application application = applicationDescription.getApplication();
            Language language = applicationDescription.getLanguage();
            String description = applicationDescriptionValue.getDescription();

            applicationDescription = ApplicationDescriptionFactory.getInstance().create(application, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(application.getPrimaryKey(), EventTypes.MODIFY.name(), applicationDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteApplicationDescription(ApplicationDescription applicationDescription, BasePK deletedBy) {
        applicationDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(applicationDescription.getApplicationPK(), EventTypes.MODIFY.name(), applicationDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteApplicationDescriptionsByApplication(Application application, BasePK deletedBy) {
        List<ApplicationDescription> applicationDescriptions = getApplicationDescriptionsByApplicationForUpdate(application);

        applicationDescriptions.stream().forEach((applicationDescription) -> {
            deleteApplicationDescription(applicationDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Editors
    // --------------------------------------------------------------------------------

    public Editor createEditor(String editorName, Boolean hasDimensions, Integer minimumHeight, Integer minimumWidth, Integer maximumHeight,
            Integer maximumWidth, Integer defaultHeight, Integer defaultWidth, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Editor defaultEditor = getDefaultEditor();
        boolean defaultFound = defaultEditor != null;

        if(defaultFound && isDefault) {
            EditorDetailValue defaultEditorDetailValue = getDefaultEditorDetailValueForUpdate();

            defaultEditorDetailValue.setIsDefault(Boolean.FALSE);
            updateEditorFromValue(defaultEditorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Editor editor = EditorFactory.getInstance().create();
        EditorDetail editorDetail = EditorDetailFactory.getInstance().create(editor, editorName, hasDimensions, minimumHeight, minimumWidth, maximumHeight,
                maximumWidth, defaultHeight, defaultWidth, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        editor = EditorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, editor.getPrimaryKey());
        editor.setActiveDetail(editorDetail);
        editor.setLastDetail(editorDetail);
        editor.store();

        sendEventUsingNames(editor.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return editor;
    }

    private static final Map<EntityPermission, String> getEditorByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM editors, editordetails " +
                "WHERE edtr_activedetailid = edtrdt_editordetailid " +
                "AND edtrdt_editorname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM editors, editordetails " +
                "WHERE edtr_activedetailid = edtrdt_editordetailid " +
                "AND edtrdt_editorname = ? " +
                "FOR UPDATE");
        getEditorByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Editor getEditorByName(String editorName, EntityPermission entityPermission) {
        return EditorFactory.getInstance().getEntityFromQuery(entityPermission, getEditorByNameQueries, editorName);
    }

    public Editor getEditorByName(String editorName) {
        return getEditorByName(editorName, EntityPermission.READ_ONLY);
    }

    public Editor getEditorByNameForUpdate(String editorName) {
        return getEditorByName(editorName, EntityPermission.READ_WRITE);
    }

    public EditorDetailValue getEditorDetailValueForUpdate(Editor editor) {
        return editor == null? null: editor.getLastDetailForUpdate().getEditorDetailValue().clone();
    }

    public EditorDetailValue getEditorDetailValueByNameForUpdate(String editorName) {
        return getEditorDetailValueForUpdate(getEditorByNameForUpdate(editorName));
    }

    private static final Map<EntityPermission, String> getDefaultEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM editors, editordetails " +
                "WHERE edtr_activedetailid = edtrdt_editordetailid " +
                "AND edtrdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM editors, editordetails " +
                "WHERE edtr_activedetailid = edtrdt_editordetailid " +
                "AND edtrdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private Editor getDefaultEditor(EntityPermission entityPermission) {
        return EditorFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultEditorQueries);
    }

    public Editor getDefaultEditor() {
        return getDefaultEditor(EntityPermission.READ_ONLY);
    }

    public Editor getDefaultEditorForUpdate() {
        return getDefaultEditor(EntityPermission.READ_WRITE);
    }

    public EditorDetailValue getDefaultEditorDetailValueForUpdate() {
        return getDefaultEditorForUpdate().getLastDetailForUpdate().getEditorDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getEditorsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM editors, editordetails " +
                "WHERE edtr_activedetailid = edtrdt_editordetailid " +
                "ORDER BY edtrdt_sortorder, edtrdt_editorname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM editors, editordetails " +
                "WHERE edtr_activedetailid = edtrdt_editordetailid " +
                "FOR UPDATE");
        getEditorsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Editor> getEditors(EntityPermission entityPermission) {
        return EditorFactory.getInstance().getEntitiesFromQuery(entityPermission, getEditorsQueries);
    }

    public List<Editor> getEditors() {
        return getEditors(EntityPermission.READ_ONLY);
    }

    public List<Editor> getEditorsForUpdate() {
        return getEditors(EntityPermission.READ_WRITE);
    }

   public EditorTransfer getEditorTransfer(UserVisit userVisit, Editor editor) {
        return getCoreTransferCaches(userVisit).getEditorTransferCache().getEditorTransfer(editor);
    }

    public List<EditorTransfer> getEditorTransfers(UserVisit userVisit) {
        List<Editor> editors = getEditors();
        List<EditorTransfer> editorTransfers = new ArrayList<>(editors.size());
        EditorTransferCache editorTransferCache = getCoreTransferCaches(userVisit).getEditorTransferCache();

        editors.stream().forEach((editor) -> {
            editorTransfers.add(editorTransferCache.getEditorTransfer(editor));
        });

        return editorTransfers;
    }

    public EditorChoicesBean getEditorChoices(String defaultEditorChoice, Language language, boolean allowNullChoice) {
        List<Editor> editors = getEditors();
        int size = editors.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultEditorChoice == null) {
                defaultValue = "";
            }
        }

        for(Editor editor: editors) {
            EditorDetail editorDetail = editor.getLastDetail();

            String label = getBestEditorDescription(editor, language);
            String value = editorDetail.getEditorName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultEditorChoice == null? false: defaultEditorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && editorDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new EditorChoicesBean(labels, values, defaultValue);
    }

    private void updateEditorFromValue(EditorDetailValue editorDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(editorDetailValue.hasBeenModified()) {
            Editor editor = EditorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     editorDetailValue.getEditorPK());
            EditorDetail editorDetail = editor.getActiveDetailForUpdate();

            editorDetail.setThruTime(session.START_TIME_LONG);
            editorDetail.store();

            EditorPK editorPK = editorDetail.getEditorPK(); // Not updated
            String editorName = editorDetailValue.getEditorName();
            Boolean hasDimensions = editorDetailValue.getHasDimensions();
            Integer minimumHeight = editorDetailValue.getMinimumHeight();
            Integer minimumWidth = editorDetailValue.getMinimumWidth();
            Integer maximumHeight = editorDetailValue.getMaximumHeight();
            Integer maximumWidth = editorDetailValue.getMaximumWidth();
            Integer defaultHeight = editorDetailValue.getDefaultHeight();
            Integer defaultWidth = editorDetailValue.getDefaultWidth();
            Boolean isDefault = editorDetailValue.getIsDefault();
            Integer sortOrder = editorDetailValue.getSortOrder();

            if(checkDefault) {
                Editor defaultEditor = getDefaultEditor();
                boolean defaultFound = defaultEditor != null && !defaultEditor.equals(editor);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    EditorDetailValue defaultEditorDetailValue = getDefaultEditorDetailValueForUpdate();

                    defaultEditorDetailValue.setIsDefault(Boolean.FALSE);
                    updateEditorFromValue(defaultEditorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            editorDetail = EditorDetailFactory.getInstance().create(editorPK, editorName, hasDimensions, minimumHeight, minimumWidth, maximumHeight,
                    maximumWidth, defaultHeight, defaultWidth, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            editor.setActiveDetail(editorDetail);
            editor.setLastDetail(editorDetail);

            sendEventUsingNames(editorPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateEditorFromValue(EditorDetailValue editorDetailValue, BasePK updatedBy) {
        updateEditorFromValue(editorDetailValue, true, updatedBy);
    }

    private void deleteEditor(Editor editor, boolean checkDefault, BasePK deletedBy) {
        EditorDetail editorDetail = editor.getLastDetailForUpdate();

        deleteEditorDescriptionsByEditor(editor, deletedBy);
        deleteApplicationEditorsByEditor(editor, deletedBy);

        editorDetail.setThruTime(session.START_TIME_LONG);
        editor.setActiveDetail(null);
        editor.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            Editor defaultEditor = getDefaultEditor();

            if(defaultEditor == null) {
                List<Editor> editors = getEditorsForUpdate();

                if(!editors.isEmpty()) {
                    Iterator<Editor> iter = editors.iterator();
                    if(iter.hasNext()) {
                        defaultEditor = iter.next();
                    }
                    EditorDetailValue editorDetailValue = defaultEditor.getLastDetailForUpdate().getEditorDetailValue().clone();

                    editorDetailValue.setIsDefault(Boolean.TRUE);
                    updateEditorFromValue(editorDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(editor.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteEditor(Editor editor, BasePK deletedBy) {
        deleteEditor(editor, true, deletedBy);
    }

    private void deleteEditors(List<Editor> editors, boolean checkDefault, BasePK deletedBy) {
        editors.stream().forEach((editor) -> {
            deleteEditor(editor, checkDefault, deletedBy);
        });
    }

    public void deleteEditors(List<Editor> editors, BasePK deletedBy) {
        deleteEditors(editors, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Editor Descriptions
    // --------------------------------------------------------------------------------

    public EditorDescription createEditorDescription(Editor editor, Language language, String description, BasePK createdBy) {
        EditorDescription editorDescription = EditorDescriptionFactory.getInstance().create(editor, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(editor.getPrimaryKey(), EventTypes.MODIFY.name(), editorDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return editorDescription;
    }

    private static final Map<EntityPermission, String> getEditorDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM editordescriptions " +
                "WHERE edtrd_edtr_editorid = ? AND edtrd_lang_languageid = ? AND edtrd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM editordescriptions " +
                "WHERE edtrd_edtr_editorid = ? AND edtrd_lang_languageid = ? AND edtrd_thrutime = ? " +
                "FOR UPDATE");
        getEditorDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private EditorDescription getEditorDescription(Editor editor, Language language, EntityPermission entityPermission) {
        return EditorDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getEditorDescriptionQueries,
                editor, language, Session.MAX_TIME);
    }

    public EditorDescription getEditorDescription(Editor editor, Language language) {
        return getEditorDescription(editor, language, EntityPermission.READ_ONLY);
    }

    public EditorDescription getEditorDescriptionForUpdate(Editor editor, Language language) {
        return getEditorDescription(editor, language, EntityPermission.READ_WRITE);
    }

    public EditorDescriptionValue getEditorDescriptionValue(EditorDescription editorDescription) {
        return editorDescription == null? null: editorDescription.getEditorDescriptionValue().clone();
    }

    public EditorDescriptionValue getEditorDescriptionValueForUpdate(Editor editor, Language language) {
        return getEditorDescriptionValue(getEditorDescriptionForUpdate(editor, language));
    }

    private static final Map<EntityPermission, String> getEditorDescriptionsByEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM editordescriptions, languages " +
                "WHERE edtrd_edtr_editorid = ? AND edtrd_thrutime = ? AND edtrd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM editordescriptions " +
                "WHERE edtrd_edtr_editorid = ? AND edtrd_thrutime = ? " +
                "FOR UPDATE");
        getEditorDescriptionsByEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<EditorDescription> getEditorDescriptionsByEditor(Editor editor, EntityPermission entityPermission) {
        return EditorDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getEditorDescriptionsByEditorQueries,
                editor, Session.MAX_TIME);
    }

    public List<EditorDescription> getEditorDescriptionsByEditor(Editor editor) {
        return getEditorDescriptionsByEditor(editor, EntityPermission.READ_ONLY);
    }

    public List<EditorDescription> getEditorDescriptionsByEditorForUpdate(Editor editor) {
        return getEditorDescriptionsByEditor(editor, EntityPermission.READ_WRITE);
    }

    public String getBestEditorDescription(Editor editor, Language language) {
        String description;
        EditorDescription editorDescription = getEditorDescription(editor, language);

        if(editorDescription == null && !language.getIsDefault()) {
            editorDescription = getEditorDescription(editor, getPartyControl().getDefaultLanguage());
        }

        if(editorDescription == null) {
            description = editor.getLastDetail().getEditorName();
        } else {
            description = editorDescription.getDescription();
        }

        return description;
    }

    public EditorDescriptionTransfer getEditorDescriptionTransfer(UserVisit userVisit, EditorDescription editorDescription) {
        return getCoreTransferCaches(userVisit).getEditorDescriptionTransferCache().getEditorDescriptionTransfer(editorDescription);
    }

    public List<EditorDescriptionTransfer> getEditorDescriptionTransfersByEditor(UserVisit userVisit, Editor editor) {
        List<EditorDescription> editorDescriptions = getEditorDescriptionsByEditor(editor);
        List<EditorDescriptionTransfer> editorDescriptionTransfers = new ArrayList<>(editorDescriptions.size());
        EditorDescriptionTransferCache editorDescriptionTransferCache = getCoreTransferCaches(userVisit).getEditorDescriptionTransferCache();

        editorDescriptions.stream().forEach((editorDescription) -> {
            editorDescriptionTransfers.add(editorDescriptionTransferCache.getEditorDescriptionTransfer(editorDescription));
        });

        return editorDescriptionTransfers;
    }

    public void updateEditorDescriptionFromValue(EditorDescriptionValue editorDescriptionValue, BasePK updatedBy) {
        if(editorDescriptionValue.hasBeenModified()) {
            EditorDescription editorDescription = EditorDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    editorDescriptionValue.getPrimaryKey());

            editorDescription.setThruTime(session.START_TIME_LONG);
            editorDescription.store();

            Editor editor = editorDescription.getEditor();
            Language language = editorDescription.getLanguage();
            String description = editorDescriptionValue.getDescription();

            editorDescription = EditorDescriptionFactory.getInstance().create(editor, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(editor.getPrimaryKey(), EventTypes.MODIFY.name(), editorDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteEditorDescription(EditorDescription editorDescription, BasePK deletedBy) {
        editorDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(editorDescription.getEditorPK(), EventTypes.MODIFY.name(), editorDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteEditorDescriptionsByEditor(Editor editor, BasePK deletedBy) {
        List<EditorDescription> editorDescriptions = getEditorDescriptionsByEditorForUpdate(editor);

        editorDescriptions.stream().forEach((editorDescription) -> {
            deleteEditorDescription(editorDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Application Editors
    // --------------------------------------------------------------------------------

    public ApplicationEditor createApplicationEditor(Application application, Editor editor, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ApplicationEditor defaultApplicationEditor = getDefaultApplicationEditor(application);
        boolean defaultFound = defaultApplicationEditor != null;

        if(defaultFound && isDefault) {
            ApplicationEditorDetailValue defaultApplicationEditorDetailValue = getDefaultApplicationEditorDetailValueForUpdate(application);

            defaultApplicationEditorDetailValue.setIsDefault(Boolean.FALSE);
            updateApplicationEditorFromValue(defaultApplicationEditorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ApplicationEditor applicationEditor = ApplicationEditorFactory.getInstance().create();
        ApplicationEditorDetail applicationEditorDetail = ApplicationEditorDetailFactory.getInstance().create(applicationEditor, application, editor, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        applicationEditor = ApplicationEditorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, applicationEditor.getPrimaryKey());
        applicationEditor.setActiveDetail(applicationEditorDetail);
        applicationEditor.setLastDetail(applicationEditorDetail);
        applicationEditor.store();

        sendEventUsingNames(applicationEditor.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return applicationEditor;
    }

    private static final Map<EntityPermission, String> getApplicationEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM applicationeditors, applicationeditordetails "
                + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid "
                + "AND appledtrdt_appl_applicationid = ? AND appledtrdt_edtr_editorid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM applicationeditors, applicationeditordetails "
                + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid "
                + "AND appledtrdt_appl_applicationid = ? AND appledtrdt_edtr_editorid = ? "
                + "FOR UPDATE");
        getApplicationEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationEditor getApplicationEditor(Application application, Editor editor, EntityPermission entityPermission) {
        return ApplicationEditorFactory.getInstance().getEntityFromQuery(entityPermission, getApplicationEditorQueries,
                application, editor);
    }

    public ApplicationEditor getApplicationEditor(Application application, Editor editor) {
        return getApplicationEditor(application, editor, EntityPermission.READ_ONLY);
    }

    public ApplicationEditor getApplicationEditorForUpdate(Application application, Editor editor) {
        return getApplicationEditor(application, editor, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorDetailValue getApplicationEditorDetailValueForUpdate(ApplicationEditor applicationEditor) {
        return applicationEditor == null? null: applicationEditor.getLastDetailForUpdate().getApplicationEditorDetailValue().clone();
    }

    public ApplicationEditorDetailValue getApplicationEditorDetailValueForUpdate(Application application, Editor editor) {
        return getApplicationEditorDetailValueForUpdate(getApplicationEditorForUpdate(application, editor));
    }

    private static final Map<EntityPermission, String> getDefaultApplicationEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM applicationeditors, applicationeditordetails "
                + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid "
                + "AND appledtrdt_appl_applicationid = ? AND appledtrdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM applicationeditors, applicationeditordetails "
                + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid "
                + "AND appledtrdt_appl_applicationid = ? AND appledtrdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultApplicationEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationEditor getDefaultApplicationEditor(Application application, EntityPermission entityPermission) {
        return ApplicationEditorFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultApplicationEditorQueries,
                application);
    }

    public ApplicationEditor getDefaultApplicationEditor(Application application) {
        return getDefaultApplicationEditor(application, EntityPermission.READ_ONLY);
    }

    public ApplicationEditor getDefaultApplicationEditorForUpdate(Application application) {
        return getDefaultApplicationEditor(application, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorDetailValue getDefaultApplicationEditorDetailValueForUpdate(Application application) {
        return getDefaultApplicationEditorForUpdate(application).getLastDetailForUpdate().getApplicationEditorDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getApplicationEditorsByApplicationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM applicationeditors, applicationeditordetails, editors, editordetails "
                + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid AND appledtrdt_appl_applicationid = ? "
                + "AND appledtrdt_edtr_editorid = edtr_editorid AND edtr_lastdetailid = edtrdt_editordetailid "
                + "ORDER BY edtrdt_sortorder, edtrdt_editorname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM applicationeditors, applicationeditordetails "
                + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid AND appledtrdt_appl_applicationid = ? "
                + "FOR UPDATE");
        getApplicationEditorsByApplicationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationEditor> getApplicationEditorsByApplication(Application application, EntityPermission entityPermission) {
        return ApplicationEditorFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationEditorsByApplicationQueries,
                application);
    }

    public List<ApplicationEditor> getApplicationEditorsByApplication(Application application) {
        return getApplicationEditorsByApplication(application, EntityPermission.READ_ONLY);
    }

    public List<ApplicationEditor> getApplicationEditorsByApplicationForUpdate(Application application) {
        return getApplicationEditorsByApplication(application, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getApplicationEditorsByEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM applicationeditors, applicationeditordetails, applications, applicationdetails "
                + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid AND appledtrdt_edtr_editorid = ? "
                + "AND appledtrdt_appl_applicationid = appl_applicationid AND appl_lastdetailid = appldt_applicationdetailid "
                + "ORDER BY appldt_sortorder, appldt_applicationname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM applicationeditors, applicationeditordetails "
                + "WHERE appledtr_activedetailid = appledtrdt_applicationeditordetailid AND appledtrdt_edtr_editorid = ? "
                + "FOR UPDATE");
        getApplicationEditorsByEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationEditor> getApplicationEditorsByEditor(Editor editor, EntityPermission entityPermission) {
        return ApplicationEditorFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationEditorsByEditorQueries,
                editor);
    }

    public List<ApplicationEditor> getApplicationEditorsByEditor(Editor editor) {
        return getApplicationEditorsByEditor(editor, EntityPermission.READ_ONLY);
    }

    public List<ApplicationEditor> getApplicationEditorsByEditorForUpdate(Editor editor) {
        return getApplicationEditorsByEditor(editor, EntityPermission.READ_WRITE);
    }

   public ApplicationEditorTransfer getApplicationEditorTransfer(UserVisit userVisit, ApplicationEditor applicationEditor) {
        return getCoreTransferCaches(userVisit).getApplicationEditorTransferCache().getApplicationEditorTransfer(applicationEditor);
    }

    public List<ApplicationEditorTransfer> getApplicationEditorTransfers(List<ApplicationEditor> applicationEditors, UserVisit userVisit) {
        List<ApplicationEditorTransfer> applicationEditorTransfers = new ArrayList<>(applicationEditors.size());
        ApplicationEditorTransferCache applicationEditorTransferCache = getCoreTransferCaches(userVisit).getApplicationEditorTransferCache();

        applicationEditors.stream().forEach((applicationEditor) -> {
            applicationEditorTransfers.add(applicationEditorTransferCache.getApplicationEditorTransfer(applicationEditor));
        });

        return applicationEditorTransfers;
    }

    public List<ApplicationEditorTransfer> getApplicationEditorTransfersByApplication(UserVisit userVisit, Application application) {
        return getApplicationEditorTransfers(getApplicationEditorsByApplication(application), userVisit);
    }
    
    public List<ApplicationEditorTransfer> getApplicationEditorTransfersByEditor(UserVisit userVisit, Editor editor) {
        return getApplicationEditorTransfers(getApplicationEditorsByEditor(editor), userVisit);
    }
    
    public ApplicationEditorChoicesBean getApplicationEditorChoices(String defaultApplicationEditorChoice, Language language, boolean allowNullChoice,
            Application application) {
        List<ApplicationEditor> applicationEditors = getApplicationEditorsByApplication(application);
        int size = applicationEditors.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultApplicationEditorChoice == null) {
                defaultValue = "";
            }
        }

        for(ApplicationEditor applicationEditor: applicationEditors) {
            ApplicationEditorDetail applicationEditorDetail = applicationEditor.getLastDetail();
            Editor editor = applicationEditorDetail.getEditor();

            String label = getBestEditorDescription(editor, language);
            String value = editor.getLastDetail().getEditorName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultApplicationEditorChoice == null? false: defaultApplicationEditorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && applicationEditorDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ApplicationEditorChoicesBean(labels, values, defaultValue);
    }

    private void updateApplicationEditorFromValue(ApplicationEditorDetailValue applicationEditorDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(applicationEditorDetailValue.hasBeenModified()) {
            ApplicationEditor applicationEditor = ApplicationEditorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     applicationEditorDetailValue.getApplicationEditorPK());
            ApplicationEditorDetail applicationEditorDetail = applicationEditor.getActiveDetailForUpdate();

            applicationEditorDetail.setThruTime(session.START_TIME_LONG);
            applicationEditorDetail.store();

            ApplicationEditorPK applicationEditorPK = applicationEditorDetail.getApplicationEditorPK(); // Not updated
            Application application = applicationEditorDetail.getApplication();
            ApplicationPK applicationPK = application.getPrimaryKey(); // Not updated
            EditorPK editorPK = applicationEditorDetail.getEditorPK(); // Not updated
            Boolean isDefault = applicationEditorDetailValue.getIsDefault();
            Integer sortOrder = applicationEditorDetailValue.getSortOrder();

            if(checkDefault) {
                ApplicationEditor defaultApplicationEditor = getDefaultApplicationEditor(application);
                boolean defaultFound = defaultApplicationEditor != null && !defaultApplicationEditor.equals(applicationEditor);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ApplicationEditorDetailValue defaultApplicationEditorDetailValue = getDefaultApplicationEditorDetailValueForUpdate(application);

                    defaultApplicationEditorDetailValue.setIsDefault(Boolean.FALSE);
                    updateApplicationEditorFromValue(defaultApplicationEditorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            applicationEditorDetail = ApplicationEditorDetailFactory.getInstance().create(applicationEditorPK, applicationPK, editorPK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            applicationEditor.setActiveDetail(applicationEditorDetail);
            applicationEditor.setLastDetail(applicationEditorDetail);

            sendEventUsingNames(applicationEditorPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateApplicationEditorFromValue(ApplicationEditorDetailValue applicationEditorDetailValue, BasePK updatedBy) {
        updateApplicationEditorFromValue(applicationEditorDetailValue, true, updatedBy);
    }

    private void deleteApplicationEditor(ApplicationEditor applicationEditor, boolean checkDefault, BasePK deletedBy) {
        ApplicationEditorDetail applicationEditorDetail = applicationEditor.getLastDetailForUpdate();
        Application application = applicationEditorDetail.getApplication();

        deleteApplicationEditorUsesByDefaultApplicationEditor(applicationEditor, deletedBy);
        deletePartyApplicationEditorUsesByApplicationEditor(applicationEditor, deletedBy);
        
        applicationEditorDetail.setThruTime(session.START_TIME_LONG);
        applicationEditor.setActiveDetail(null);
        applicationEditor.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            ApplicationEditor defaultApplicationEditor = getDefaultApplicationEditor(application);

            if(defaultApplicationEditor == null) {
                List<ApplicationEditor> applicationEditors = getApplicationEditorsByApplicationForUpdate(application);

                if(!applicationEditors.isEmpty()) {
                    Iterator<ApplicationEditor> iter = applicationEditors.iterator();
                    if(iter.hasNext()) {
                        defaultApplicationEditor = iter.next();
                    }
                    ApplicationEditorDetailValue applicationEditorDetailValue = defaultApplicationEditor.getLastDetailForUpdate().getApplicationEditorDetailValue().clone();

                    applicationEditorDetailValue.setIsDefault(Boolean.TRUE);
                    updateApplicationEditorFromValue(applicationEditorDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(applicationEditor.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteApplicationEditor(ApplicationEditor applicationEditor, BasePK deletedBy) {
        deleteApplicationEditor(applicationEditor, true, deletedBy);
    }

    private void deleteApplicationEditors(List<ApplicationEditor> applicationEditors, boolean checkDefault, BasePK deletedBy) {
        applicationEditors.stream().forEach((applicationEditor) -> {
            deleteApplicationEditor(applicationEditor, checkDefault, deletedBy);
        });
    }

    public void deleteApplicationEditors(List<ApplicationEditor> applicationEditors, BasePK deletedBy) {
        deleteApplicationEditors(applicationEditors, true, deletedBy);
    }
    
    public void deleteApplicationEditorsByApplication(Application application, BasePK deletedBy) {
        deleteApplicationEditors(getApplicationEditorsByApplicationForUpdate(application), false, deletedBy);
    }
    
    public void deleteApplicationEditorsByEditor(Editor editor, BasePK deletedBy) {
        deleteApplicationEditors(getApplicationEditorsByEditorForUpdate(editor), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Application Editor Uses
    // --------------------------------------------------------------------------------

    public ApplicationEditorUse createApplicationEditorUse(Application application, String applicationEditorUseName, ApplicationEditor defaultApplicationEditor,
            Integer defaultHeight, Integer defaultWidth, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ApplicationEditorUse defaultApplicationEditorUse = getDefaultApplicationEditorUse(application);
        boolean defaultFound = defaultApplicationEditorUse != null;

        if(defaultFound && isDefault) {
            ApplicationEditorUseDetailValue defaultApplicationEditorUseDetailValue = getDefaultApplicationEditorUseDetailValueForUpdate(application);

            defaultApplicationEditorUseDetailValue.setIsDefault(Boolean.FALSE);
            updateApplicationEditorUseFromValue(defaultApplicationEditorUseDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        ApplicationEditorUse applicationEditorUse = ApplicationEditorUseFactory.getInstance().create();
        ApplicationEditorUseDetail applicationEditorUseDetail = ApplicationEditorUseDetailFactory.getInstance().create(applicationEditorUse, application,
                applicationEditorUseName, defaultApplicationEditor, defaultHeight, defaultWidth, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        applicationEditorUse = ApplicationEditorUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, applicationEditorUse.getPrimaryKey());
        applicationEditorUse.setActiveDetail(applicationEditorUseDetail);
        applicationEditorUse.setLastDetail(applicationEditorUseDetail);
        applicationEditorUse.store();

        sendEventUsingNames(applicationEditorUse.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return applicationEditorUse;
    }

    private static final Map<EntityPermission, String> getApplicationEditorUseByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM applicationeditoruses, applicationeditorusedetails "
                + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid "
                + "AND appledtrusedt_appl_applicationid = ? AND appledtrusedt_applicationeditorusename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM applicationeditoruses, applicationeditorusedetails "
                + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid "
                + "AND appledtrusedt_appl_applicationid = ? AND appledtrusedt_applicationeditorusename = ? "
                + "FOR UPDATE");
        getApplicationEditorUseByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationEditorUse getApplicationEditorUseByName(Application application, String applicationEditorUseName, EntityPermission entityPermission) {
        return ApplicationEditorUseFactory.getInstance().getEntityFromQuery(entityPermission, getApplicationEditorUseByNameQueries,
                application, applicationEditorUseName);
    }

    public ApplicationEditorUse getApplicationEditorUseByName(Application application, String applicationEditorUseName) {
        return getApplicationEditorUseByName(application, applicationEditorUseName, EntityPermission.READ_ONLY);
    }

    public ApplicationEditorUse getApplicationEditorUseByNameForUpdate(Application application, String applicationEditorUseName) {
        return getApplicationEditorUseByName(application, applicationEditorUseName, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorUseDetailValue getApplicationEditorUseDetailValueForUpdate(ApplicationEditorUse applicationEditorUse) {
        return applicationEditorUse == null ? null : applicationEditorUse.getLastDetailForUpdate().getApplicationEditorUseDetailValue().clone();
    }

    public ApplicationEditorUseDetailValue getApplicationEditorUseDetailValueByNameForUpdate(Application application, String applicationEditorUseName) {
        return getApplicationEditorUseDetailValueForUpdate(getApplicationEditorUseByNameForUpdate(application, applicationEditorUseName));
    }

    private static final Map<EntityPermission, String> getDefaultApplicationEditorUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM applicationeditoruses, applicationeditorusedetails "
                + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid "
                + "AND appledtrusedt_appl_applicationid = ? AND appledtrusedt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM applicationeditoruses, applicationeditorusedetails "
                + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid "
                + "AND appledtrusedt_appl_applicationid = ? AND appledtrusedt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultApplicationEditorUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationEditorUse getDefaultApplicationEditorUse(Application application, EntityPermission entityPermission) {
        return ApplicationEditorUseFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultApplicationEditorUseQueries,
                application);
    }

    public ApplicationEditorUse getDefaultApplicationEditorUse(Application application) {
        return getDefaultApplicationEditorUse(application, EntityPermission.READ_ONLY);
    }

    public ApplicationEditorUse getDefaultApplicationEditorUseForUpdate(Application application) {
        return getDefaultApplicationEditorUse(application, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorUseDetailValue getDefaultApplicationEditorUseDetailValueForUpdate(Application application) {
        return getDefaultApplicationEditorUseForUpdate(application).getLastDetailForUpdate().getApplicationEditorUseDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getApplicationEditorUsesByApplicationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM applicationeditoruses, applicationeditorusedetails "
                + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid AND appledtrusedt_appl_applicationid = ? "
                + "ORDER BY appledtrusedt_sortorder, appledtrusedt_applicationeditorusename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM applicationeditoruses, applicationeditorusedetails "
                + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid AND appledtrusedt_appl_applicationid = ? "
                + "FOR UPDATE");
        getApplicationEditorUsesByApplicationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationEditorUse> getApplicationEditorUsesByApplication(Application application, EntityPermission entityPermission) {
        return ApplicationEditorUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationEditorUsesByApplicationQueries,
                application);
    }

    public List<ApplicationEditorUse> getApplicationEditorUsesByApplication(Application application) {
        return getApplicationEditorUsesByApplication(application, EntityPermission.READ_ONLY);
    }

    public List<ApplicationEditorUse> getApplicationEditorUsesByApplicationForUpdate(Application application) {
        return getApplicationEditorUsesByApplication(application, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getApplicationEditorUsesByDefaultApplicationEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM applicationeditoruses, applicationeditorusedetails, applications, applicationdetails "
                + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid AND appledtrusedt_defaultapplicationeditorid = ? "
                + "AND appledtrusedt_appl_applicationid = appl_applicationid AND appl_lastdetailid = appldt_applicationdetailid "
                + "ORDER BY appledtrusedt_sortorder, appledtrusedt_applicationeditorusename, appldt_sortorder, appldt_applicationname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM applicationeditoruses, applicationeditorusedetails "
                + "WHERE appledtruse_activedetailid = appledtrusedt_applicationeditorusedetailid AND appledtrusedt_defaultapplicationeditorid = ? "
                + "FOR UPDATE");
        getApplicationEditorUsesByDefaultApplicationEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationEditorUse> getApplicationEditorUsesByDefaultApplicationEditor(ApplicationEditor defaultApplicationEditor,
            EntityPermission entityPermission) {
        return ApplicationEditorUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationEditorUsesByDefaultApplicationEditorQueries,
                defaultApplicationEditor);
    }

    public List<ApplicationEditorUse> getApplicationEditorUsesByDefaultApplicationEditor(ApplicationEditor defaultApplicationEditor) {
        return getApplicationEditorUsesByDefaultApplicationEditor(defaultApplicationEditor, EntityPermission.READ_ONLY);
    }

    public List<ApplicationEditorUse> getApplicationEditorUsesByDefaultApplicationEditorForUpdate(ApplicationEditor defaultApplicationEditor) {
        return getApplicationEditorUsesByDefaultApplicationEditor(defaultApplicationEditor, EntityPermission.READ_WRITE);
    }

   public ApplicationEditorUseTransfer getApplicationEditorUseTransfer(UserVisit userVisit, ApplicationEditorUse applicationEditorUse) {
        return getCoreTransferCaches(userVisit).getApplicationEditorUseTransferCache().getApplicationEditorUseTransfer(applicationEditorUse);
    }

    public List<ApplicationEditorUseTransfer> getApplicationEditorUseTransfers(List<ApplicationEditorUse> applicationEditorUses, UserVisit userVisit) {
        List<ApplicationEditorUseTransfer> applicationEditorUseTransfers = new ArrayList<>(applicationEditorUses.size());
        ApplicationEditorUseTransferCache applicationEditorUseTransferCache = getCoreTransferCaches(userVisit).getApplicationEditorUseTransferCache();

        applicationEditorUses.stream().forEach((applicationEditorUse) -> {
            applicationEditorUseTransfers.add(applicationEditorUseTransferCache.getApplicationEditorUseTransfer(applicationEditorUse));
        });

        return applicationEditorUseTransfers;
    }

    public List<ApplicationEditorUseTransfer> getApplicationEditorUseTransfersByApplication(UserVisit userVisit, Application application) {
        return getApplicationEditorUseTransfers(getApplicationEditorUsesByApplication(application), userVisit);
    }
    
    public List<ApplicationEditorUseTransfer> getApplicationEditorUseTransfersByDefaultApplicationEditor(UserVisit userVisit, ApplicationEditor defaultApplicationEditor) {
        return getApplicationEditorUseTransfers(getApplicationEditorUsesByDefaultApplicationEditor(defaultApplicationEditor), userVisit);
    }
    
    public ApplicationEditorUseChoicesBean getApplicationEditorUseChoices(String defaultApplicationEditorUseChoice, Language language, boolean allowNullChoice,
            Application application) {
        List<ApplicationEditorUse> applicationEditorUses = getApplicationEditorUsesByApplication(application);
        int size = applicationEditorUses.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultApplicationEditorUseChoice == null) {
                defaultValue = "";
            }
        }

        for(ApplicationEditorUse applicationEditorUse: applicationEditorUses) {
            ApplicationEditorUseDetail applicationEditorUseDetail = applicationEditorUse.getLastDetail();

            String label = getBestApplicationEditorUseDescription(applicationEditorUse, language);
            String value = applicationEditorUseDetail.getApplicationEditorUseName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultApplicationEditorUseChoice == null? false: defaultApplicationEditorUseChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && applicationEditorUseDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ApplicationEditorUseChoicesBean(labels, values, defaultValue);
    }

    private void updateApplicationEditorUseFromValue(ApplicationEditorUseDetailValue applicationEditorUseDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(applicationEditorUseDetailValue.hasBeenModified()) {
            ApplicationEditorUse applicationEditorUse = ApplicationEditorUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     applicationEditorUseDetailValue.getApplicationEditorUsePK());
            ApplicationEditorUseDetail applicationEditorUseDetail = applicationEditorUse.getActiveDetailForUpdate();

            applicationEditorUseDetail.setThruTime(session.START_TIME_LONG);
            applicationEditorUseDetail.store();

            ApplicationEditorUsePK applicationEditorUsePK = applicationEditorUseDetail.getApplicationEditorUsePK(); // Not updated
            Application application = applicationEditorUseDetail.getApplication();
            ApplicationPK applicationPK = application.getPrimaryKey(); // Not updated
            String applicationEditorUseName = applicationEditorUseDetailValue.getApplicationEditorUseName();
            ApplicationEditorPK defaultApplicationEditorPK = applicationEditorUseDetailValue.getDefaultApplicationEditorPK();
            Integer defaultHeight = applicationEditorUseDetailValue.getDefaultHeight();
            Integer defaultWidth = applicationEditorUseDetailValue.getDefaultWidth();
            Boolean isDefault = applicationEditorUseDetailValue.getIsDefault();
            Integer sortOrder = applicationEditorUseDetailValue.getSortOrder();

            if(checkDefault) {
                ApplicationEditorUse defaultApplicationEditorUse = getDefaultApplicationEditorUse(application);
                boolean defaultFound = defaultApplicationEditorUse != null && !defaultApplicationEditorUse.equals(applicationEditorUse);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ApplicationEditorUseDetailValue defaultApplicationEditorUseDetailValue = getDefaultApplicationEditorUseDetailValueForUpdate(application);

                    defaultApplicationEditorUseDetailValue.setIsDefault(Boolean.FALSE);
                    updateApplicationEditorUseFromValue(defaultApplicationEditorUseDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            applicationEditorUseDetail = ApplicationEditorUseDetailFactory.getInstance().create(applicationEditorUsePK, applicationPK, applicationEditorUseName,
                    defaultApplicationEditorPK, defaultHeight, defaultWidth, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            applicationEditorUse.setActiveDetail(applicationEditorUseDetail);
            applicationEditorUse.setLastDetail(applicationEditorUseDetail);

            sendEventUsingNames(applicationEditorUsePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateApplicationEditorUseFromValue(ApplicationEditorUseDetailValue applicationEditorUseDetailValue, BasePK updatedBy) {
        updateApplicationEditorUseFromValue(applicationEditorUseDetailValue, true, updatedBy);
    }

    private void deleteApplicationEditorUse(ApplicationEditorUse applicationEditorUse, boolean checkDefault, BasePK deletedBy) {
        ApplicationEditorUseDetail applicationEditorUseDetail = applicationEditorUse.getLastDetailForUpdate();
        Application application = applicationEditorUseDetail.getApplication();

        deleteApplicationEditorUseDescriptionsByApplicationEditorUse(applicationEditorUse, deletedBy);
        deletePartyApplicationEditorUsesByParty(applicationEditorUse, deletedBy);
        
        applicationEditorUseDetail.setThruTime(session.START_TIME_LONG);
        applicationEditorUse.setActiveDetail(null);
        applicationEditorUse.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            ApplicationEditorUse defaultApplicationEditorUse = getDefaultApplicationEditorUse(application);

            if(defaultApplicationEditorUse == null) {
                List<ApplicationEditorUse> applicationEditorUses = getApplicationEditorUsesByApplicationForUpdate(application);

                if(!applicationEditorUses.isEmpty()) {
                    Iterator<ApplicationEditorUse> iter = applicationEditorUses.iterator();
                    if(iter.hasNext()) {
                        defaultApplicationEditorUse = iter.next();
                    }
                    ApplicationEditorUseDetailValue applicationEditorUseDetailValue = defaultApplicationEditorUse.getLastDetailForUpdate().getApplicationEditorUseDetailValue().clone();

                    applicationEditorUseDetailValue.setIsDefault(Boolean.TRUE);
                    updateApplicationEditorUseFromValue(applicationEditorUseDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(applicationEditorUse.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteApplicationEditorUse(ApplicationEditorUse applicationEditorUse, BasePK deletedBy) {
        deleteApplicationEditorUse(applicationEditorUse, true, deletedBy);
    }

    private void deleteApplicationEditorUses(List<ApplicationEditorUse> applicationEditorUses, boolean checkDefault, BasePK deletedBy) {
        applicationEditorUses.stream().forEach((applicationEditorUse) -> {
            deleteApplicationEditorUse(applicationEditorUse, checkDefault, deletedBy);
        });
    }

    public void deleteApplicationEditorUses(List<ApplicationEditorUse> applicationEditorUses, BasePK deletedBy) {
        deleteApplicationEditorUses(applicationEditorUses, true, deletedBy);
    }
    
    public void deleteApplicationEditorUsesByApplication(Application application, BasePK deletedBy) {
        deleteApplicationEditorUses(getApplicationEditorUsesByApplicationForUpdate(application), false, deletedBy);
    }
    
    public void deleteApplicationEditorUsesByDefaultApplicationEditor(ApplicationEditor defaultApplicationEditor, BasePK deletedBy) {
        deleteApplicationEditorUses(getApplicationEditorUsesByDefaultApplicationEditorForUpdate(defaultApplicationEditor), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Application Editor Use Descriptions
    // --------------------------------------------------------------------------------

    public ApplicationEditorUseDescription createApplicationEditorUseDescription(ApplicationEditorUse applicationEditorUse, Language language,
            String description, BasePK createdBy) {
        ApplicationEditorUseDescription applicationEditorUseDescription = ApplicationEditorUseDescriptionFactory.getInstance().create(applicationEditorUse,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(applicationEditorUse.getPrimaryKey(), EventTypes.MODIFY.name(), applicationEditorUseDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return applicationEditorUseDescription;
    }

    private static final Map<EntityPermission, String> getApplicationEditorUseDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM applicationeditorusedescriptions " +
                "WHERE appledtrused_appledtruse_applicationeditoruseid = ? AND appledtrused_lang_languageid = ? AND appledtrused_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM applicationeditorusedescriptions " +
                "WHERE appledtrused_appledtruse_applicationeditoruseid = ? AND appledtrused_lang_languageid = ? AND appledtrused_thrutime = ? " +
                "FOR UPDATE");
        getApplicationEditorUseDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ApplicationEditorUseDescription getApplicationEditorUseDescription(ApplicationEditorUse applicationEditorUse, Language language, EntityPermission entityPermission) {
        return ApplicationEditorUseDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getApplicationEditorUseDescriptionQueries,
                applicationEditorUse, language, Session.MAX_TIME);
    }

    public ApplicationEditorUseDescription getApplicationEditorUseDescription(ApplicationEditorUse applicationEditorUse, Language language) {
        return getApplicationEditorUseDescription(applicationEditorUse, language, EntityPermission.READ_ONLY);
    }

    public ApplicationEditorUseDescription getApplicationEditorUseDescriptionForUpdate(ApplicationEditorUse applicationEditorUse, Language language) {
        return getApplicationEditorUseDescription(applicationEditorUse, language, EntityPermission.READ_WRITE);
    }

    public ApplicationEditorUseDescriptionValue getApplicationEditorUseDescriptionValue(ApplicationEditorUseDescription applicationEditorUseDescription) {
        return applicationEditorUseDescription == null? null: applicationEditorUseDescription.getApplicationEditorUseDescriptionValue().clone();
    }

    public ApplicationEditorUseDescriptionValue getApplicationEditorUseDescriptionValueForUpdate(ApplicationEditorUse applicationEditorUse, Language language) {
        return getApplicationEditorUseDescriptionValue(getApplicationEditorUseDescriptionForUpdate(applicationEditorUse, language));
    }

    private static final Map<EntityPermission, String> getApplicationEditorUseDescriptionsByApplicationEditorUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM applicationeditorusedescriptions, languages " +
                "WHERE appledtrused_appledtruse_applicationeditoruseid = ? AND appledtrused_thrutime = ? AND appledtrused_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM applicationeditorusedescriptions " +
                "WHERE appledtrused_appledtruse_applicationeditoruseid = ? AND appledtrused_thrutime = ? " +
                "FOR UPDATE");
        getApplicationEditorUseDescriptionsByApplicationEditorUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ApplicationEditorUseDescription> getApplicationEditorUseDescriptionsByApplicationEditorUse(ApplicationEditorUse applicationEditorUse, EntityPermission entityPermission) {
        return ApplicationEditorUseDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getApplicationEditorUseDescriptionsByApplicationEditorUseQueries,
                applicationEditorUse, Session.MAX_TIME);
    }

    public List<ApplicationEditorUseDescription> getApplicationEditorUseDescriptionsByApplicationEditorUse(ApplicationEditorUse applicationEditorUse) {
        return getApplicationEditorUseDescriptionsByApplicationEditorUse(applicationEditorUse, EntityPermission.READ_ONLY);
    }

    public List<ApplicationEditorUseDescription> getApplicationEditorUseDescriptionsByApplicationEditorUseForUpdate(ApplicationEditorUse applicationEditorUse) {
        return getApplicationEditorUseDescriptionsByApplicationEditorUse(applicationEditorUse, EntityPermission.READ_WRITE);
    }

    public String getBestApplicationEditorUseDescription(ApplicationEditorUse applicationEditorUse, Language language) {
        String description;
        ApplicationEditorUseDescription applicationEditorUseDescription = getApplicationEditorUseDescription(applicationEditorUse, language);

        if(applicationEditorUseDescription == null && !language.getIsDefault()) {
            applicationEditorUseDescription = getApplicationEditorUseDescription(applicationEditorUse, getPartyControl().getDefaultLanguage());
        }

        if(applicationEditorUseDescription == null) {
            description = applicationEditorUse.getLastDetail().getApplicationEditorUseName();
        } else {
            description = applicationEditorUseDescription.getDescription();
        }

        return description;
    }

    public ApplicationEditorUseDescriptionTransfer getApplicationEditorUseDescriptionTransfer(UserVisit userVisit, ApplicationEditorUseDescription applicationEditorUseDescription) {
        return getCoreTransferCaches(userVisit).getApplicationEditorUseDescriptionTransferCache().getApplicationEditorUseDescriptionTransfer(applicationEditorUseDescription);
    }

    public List<ApplicationEditorUseDescriptionTransfer> getApplicationEditorUseDescriptionTransfersByApplicationEditorUse(UserVisit userVisit, ApplicationEditorUse applicationEditorUse) {
        List<ApplicationEditorUseDescription> applicationEditorUseDescriptions = getApplicationEditorUseDescriptionsByApplicationEditorUse(applicationEditorUse);
        List<ApplicationEditorUseDescriptionTransfer> applicationEditorUseDescriptionTransfers = new ArrayList<>(applicationEditorUseDescriptions.size());
        ApplicationEditorUseDescriptionTransferCache applicationEditorUseDescriptionTransferCache = getCoreTransferCaches(userVisit).getApplicationEditorUseDescriptionTransferCache();

        applicationEditorUseDescriptions.stream().forEach((applicationEditorUseDescription) -> {
            applicationEditorUseDescriptionTransfers.add(applicationEditorUseDescriptionTransferCache.getApplicationEditorUseDescriptionTransfer(applicationEditorUseDescription));
        });

        return applicationEditorUseDescriptionTransfers;
    }

    public void updateApplicationEditorUseDescriptionFromValue(ApplicationEditorUseDescriptionValue applicationEditorUseDescriptionValue, BasePK updatedBy) {
        if(applicationEditorUseDescriptionValue.hasBeenModified()) {
            ApplicationEditorUseDescription applicationEditorUseDescription = ApplicationEditorUseDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    applicationEditorUseDescriptionValue.getPrimaryKey());

            applicationEditorUseDescription.setThruTime(session.START_TIME_LONG);
            applicationEditorUseDescription.store();

            ApplicationEditorUse applicationEditorUse = applicationEditorUseDescription.getApplicationEditorUse();
            Language language = applicationEditorUseDescription.getLanguage();
            String description = applicationEditorUseDescriptionValue.getDescription();

            applicationEditorUseDescription = ApplicationEditorUseDescriptionFactory.getInstance().create(applicationEditorUse, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(applicationEditorUse.getPrimaryKey(), EventTypes.MODIFY.name(), applicationEditorUseDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteApplicationEditorUseDescription(ApplicationEditorUseDescription applicationEditorUseDescription, BasePK deletedBy) {
        applicationEditorUseDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(applicationEditorUseDescription.getApplicationEditorUsePK(), EventTypes.MODIFY.name(), applicationEditorUseDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteApplicationEditorUseDescriptionsByApplicationEditorUse(ApplicationEditorUse applicationEditorUse, BasePK deletedBy) {
        List<ApplicationEditorUseDescription> applicationEditorUseDescriptions = getApplicationEditorUseDescriptionsByApplicationEditorUseForUpdate(applicationEditorUse);

        applicationEditorUseDescriptions.stream().forEach((applicationEditorUseDescription) -> {
            deleteApplicationEditorUseDescription(applicationEditorUseDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------

    public PartyApplicationEditorUse createPartyApplicationEditorUse(Party party, ApplicationEditorUse applicationEditorUse,
            ApplicationEditor applicationEditor, Integer preferredHeight, Integer preferredWidth, BasePK createdBy) {
        PartyApplicationEditorUse partyApplicationEditorUse = PartyApplicationEditorUseFactory.getInstance().create();
        PartyApplicationEditorUseDetail partyApplicationEditorUseDetail = PartyApplicationEditorUseDetailFactory.getInstance().create(partyApplicationEditorUse,
                party, applicationEditorUse, applicationEditor, preferredHeight, preferredWidth, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        partyApplicationEditorUse = PartyApplicationEditorUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyApplicationEditorUse.getPrimaryKey());
        partyApplicationEditorUse.setActiveDetail(partyApplicationEditorUseDetail);
        partyApplicationEditorUse.setLastDetail(partyApplicationEditorUseDetail);
        partyApplicationEditorUse.store();

        sendEventUsingNames(partyApplicationEditorUse.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return partyApplicationEditorUse;
    }

    private static final Map<EntityPermission, String> getPartyApplicationEditorUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails "
                + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid "
                + "AND parappledtrusedt_par_partyid = ? AND parappledtrusedt_appledtruse_applicationeditoruseid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails "
                + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid "
                + "AND parappledtrusedt_par_partyid = ? AND parappledtrusedt_appledtruse_applicationeditoruseid = ? "
                + "FOR UPDATE");
        getPartyApplicationEditorUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyApplicationEditorUse getPartyApplicationEditorUse(Party party, ApplicationEditorUse applicationEditorUse, EntityPermission entityPermission) {
        return PartyApplicationEditorUseFactory.getInstance().getEntityFromQuery(entityPermission, getPartyApplicationEditorUseQueries,
                party, applicationEditorUse);
    }

    public PartyApplicationEditorUse getPartyApplicationEditorUse(Party party, ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUse(party, applicationEditorUse, EntityPermission.READ_ONLY);
    }

    public PartyApplicationEditorUse getPartyApplicationEditorUseForUpdate(Party party, ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUse(party, applicationEditorUse, EntityPermission.READ_WRITE);
    }

    public PartyApplicationEditorUseDetailValue getPartyApplicationEditorUseDetailValueForUpdate(PartyApplicationEditorUse partyApplicationEditorUse) {
        return partyApplicationEditorUse == null? null: partyApplicationEditorUse.getLastDetailForUpdate().getPartyApplicationEditorUseDetailValue().clone();
    }

    public PartyApplicationEditorUseDetailValue getPartyApplicationEditorUseDetailValueForUpdate(Party party, ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUseDetailValueForUpdate(getPartyApplicationEditorUseForUpdate(party, applicationEditorUse));
    }

    private static final Map<EntityPermission, String> getPartyApplicationEditorUsesByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails, applicationeditoruses, applicationeditorusedetails "
                + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_par_partyid = ? "
                + "AND parappledtrusedt_appledtruse_applicationeditoruseid = appledtruse_applicationeditoruseid AND appledtruse_lastdetailid = appledtrusedt_applicationeditorusedetailid "
                + "ORDER BY appledtrusedt_sortorder, appledtrusedt_applicationeditorusename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails "
                + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_par_partyid = ? "
                + "FOR UPDATE");
        getPartyApplicationEditorUsesByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByParty(Party party, EntityPermission entityPermission) {
        return PartyApplicationEditorUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyApplicationEditorUsesByPartyQueries,
                party);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByParty(Party party) {
        return getPartyApplicationEditorUsesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByPartyForUpdate(Party party) {
        return getPartyApplicationEditorUsesByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyApplicationEditorUsesByApplicationEditorUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails, parties, partydetails "
                + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_appledtruse_applicationeditoruseid = ? "
                + "AND parappledtrusedt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                + "ORDER BY pardt_partyname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails, parties, partydetails "
                + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_appledtruse_applicationeditoruseid = ? "
                + "FOR UPDATE");
        getPartyApplicationEditorUsesByApplicationEditorUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorUse(ApplicationEditorUse applicationEditorUse,
            EntityPermission entityPermission) {
        return PartyApplicationEditorUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyApplicationEditorUsesByApplicationEditorUseQueries,
                applicationEditorUse);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorUse(ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUsesByApplicationEditorUse(applicationEditorUse, EntityPermission.READ_ONLY);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorUseForUpdate(ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUsesByApplicationEditorUse(applicationEditorUse, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyApplicationEditorUsesByApplicationEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails, parties, partydetails, applicationeditoruses, applicationeditorusedetails "
                + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_appledtr_applicationeditorid = ? "
                + "AND parappledtrusedt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                + "AND parappledtrusedt_appledtruse_applicationeditoruseid = appledtruse_applicationeditoruseid AND appledtruse_lastdetailid = appledtrusedt_applicationeditorusedetailid "
                + "ORDER BY pardt_partyname, appledtrusedt_sortorder, appledtrusedt_applicationeditorusename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails "
                + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_appledtr_applicationeditorid = ? "
                + "FOR UPDATE");
        getPartyApplicationEditorUsesByApplicationEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditor(ApplicationEditor applicationEditor,
            EntityPermission entityPermission) {
        return PartyApplicationEditorUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyApplicationEditorUsesByApplicationEditorQueries,
                applicationEditor);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditor(ApplicationEditor applicationEditor) {
        return getPartyApplicationEditorUsesByApplicationEditor(applicationEditor, EntityPermission.READ_ONLY);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorForUpdate(ApplicationEditor applicationEditor) {
        return getPartyApplicationEditorUsesByApplicationEditor(applicationEditor, EntityPermission.READ_WRITE);
    }

   public PartyApplicationEditorUseTransfer getPartyApplicationEditorUseTransfer(UserVisit userVisit, PartyApplicationEditorUse partyApplicationEditorUse) {
        return getCoreTransferCaches(userVisit).getPartyApplicationEditorUseTransferCache().getPartyApplicationEditorUseTransfer(partyApplicationEditorUse);
    }

    public List<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUseTransfers(List<PartyApplicationEditorUse> partyApplicationEditorUses, UserVisit userVisit) {
        List<PartyApplicationEditorUseTransfer> partyApplicationEditorUseTransfers = new ArrayList<>(partyApplicationEditorUses.size());
        PartyApplicationEditorUseTransferCache partyApplicationEditorUseTransferCache = getCoreTransferCaches(userVisit).getPartyApplicationEditorUseTransferCache();

        partyApplicationEditorUses.stream().forEach((partyApplicationEditorUse) -> {
            partyApplicationEditorUseTransfers.add(partyApplicationEditorUseTransferCache.getPartyApplicationEditorUseTransfer(partyApplicationEditorUse));
        });

        return partyApplicationEditorUseTransfers;
    }

    public List<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUseTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyApplicationEditorUseTransfers(getPartyApplicationEditorUsesByParty(party), userVisit);
    }
    
    public List<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUseTransfersByApplicationEditorUse(UserVisit userVisit, ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUseTransfers(getPartyApplicationEditorUsesByApplicationEditorUse(applicationEditorUse), userVisit);
    }
    
    public List<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUseTransfersByApplicationEditor(UserVisit userVisit, ApplicationEditor applicationEditor) {
        return getPartyApplicationEditorUseTransfers(getPartyApplicationEditorUsesByApplicationEditor(applicationEditor), userVisit);
    }
    
    public void updatePartyApplicationEditorUseFromValue(PartyApplicationEditorUseDetailValue partyApplicationEditorUseDetailValue, BasePK updatedBy) {
        if(partyApplicationEditorUseDetailValue.hasBeenModified()) {
            PartyApplicationEditorUse partyApplicationEditorUse = PartyApplicationEditorUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyApplicationEditorUseDetailValue.getPartyApplicationEditorUsePK());
            PartyApplicationEditorUseDetail partyApplicationEditorUseDetail = partyApplicationEditorUse.getActiveDetailForUpdate();

            partyApplicationEditorUseDetail.setThruTime(session.START_TIME_LONG);
            partyApplicationEditorUseDetail.store();

            PartyApplicationEditorUsePK partyApplicationEditorUsePK = partyApplicationEditorUseDetail.getPartyApplicationEditorUsePK(); // Not updated
            PartyPK partyPK = partyApplicationEditorUseDetail.getPartyPK(); // Not updated
            ApplicationEditorUsePK applicationEditorUsePK = partyApplicationEditorUseDetail.getApplicationEditorUsePK(); // Not updated
            ApplicationEditorPK applicationEditorPK = partyApplicationEditorUseDetailValue.getApplicationEditorPK();
            Integer preferredHeight = partyApplicationEditorUseDetailValue.getPreferredHeight();
            Integer preferredWidth = partyApplicationEditorUseDetailValue.getPreferredWidth();
            
            partyApplicationEditorUseDetail = PartyApplicationEditorUseDetailFactory.getInstance().create(partyApplicationEditorUsePK, partyPK,
                    applicationEditorUsePK, applicationEditorPK, preferredHeight, preferredWidth, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            partyApplicationEditorUse.setActiveDetail(partyApplicationEditorUseDetail);
            partyApplicationEditorUse.setLastDetail(partyApplicationEditorUseDetail);

            sendEventUsingNames(partyApplicationEditorUsePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void deletePartyApplicationEditorUse(PartyApplicationEditorUse partyApplicationEditorUse, BasePK deletedBy) {
        PartyApplicationEditorUseDetail partyApplicationEditorUseDetail = partyApplicationEditorUse.getLastDetailForUpdate();

        partyApplicationEditorUseDetail.setThruTime(session.START_TIME_LONG);
        partyApplicationEditorUse.setActiveDetail(null);
        partyApplicationEditorUse.store();

        sendEventUsingNames(partyApplicationEditorUse.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deletePartyApplicationEditorUses(List<PartyApplicationEditorUse> partyApplicationEditorUses, BasePK deletedBy) {
        partyApplicationEditorUses.stream().forEach((partyApplicationEditorUse) -> {
            deletePartyApplicationEditorUse(partyApplicationEditorUse, deletedBy);
        });
    }

    public void deletePartyApplicationEditorUsesByParty(Party party, BasePK deletedBy) {
        deletePartyApplicationEditorUses(getPartyApplicationEditorUsesByPartyForUpdate(party), deletedBy);
    }

    public void deletePartyApplicationEditorUsesByParty(ApplicationEditorUse applicationEditorUse, BasePK deletedBy) {
        deletePartyApplicationEditorUses(getPartyApplicationEditorUsesByApplicationEditorUseForUpdate(applicationEditorUse), deletedBy);
    }

    public void deletePartyApplicationEditorUsesByApplicationEditor(ApplicationEditor applicationEditor, BasePK deletedBy) {
        deletePartyApplicationEditorUses(getPartyApplicationEditorUsesByApplicationEditorForUpdate(applicationEditor), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------

    public Color createColor(String colorName, Integer red, Integer green, Integer blue, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Color defaultColor = getDefaultColor();
        boolean defaultFound = defaultColor != null;

        if(defaultFound && isDefault) {
            ColorDetailValue defaultColorDetailValue = getDefaultColorDetailValueForUpdate();

            defaultColorDetailValue.setIsDefault(Boolean.FALSE);
            updateColorFromValue(defaultColorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Color color = ColorFactory.getInstance().create();
        ColorDetail colorDetail = ColorDetailFactory.getInstance().create(color, colorName, red, green, blue, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        color = ColorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, color.getPrimaryKey());
        color.setActiveDetail(colorDetail);
        color.setLastDetail(colorDetail);
        color.store();

        sendEventUsingNames(color.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return color;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Color */
    public Color getColorByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        ColorPK pk = new ColorPK(entityInstance.getEntityUniqueId());
        Color entity = ColorFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return entity;
    }

    public Color getColorByEntityInstance(EntityInstance entityInstance) {
        return getColorByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Color getColorByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getColorByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getColorByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM colors, colordetails " +
                "WHERE clr_activedetailid = clrdt_colordetailid " +
                "AND clrdt_colorname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM colors, colordetails " +
                "WHERE clr_activedetailid = clrdt_colordetailid " +
                "AND clrdt_colorname = ? " +
                "FOR UPDATE");
        getColorByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Color getColorByName(String colorName, EntityPermission entityPermission) {
        return ColorFactory.getInstance().getEntityFromQuery(entityPermission, getColorByNameQueries, colorName);
    }

    public Color getColorByName(String colorName) {
        return getColorByName(colorName, EntityPermission.READ_ONLY);
    }

    public Color getColorByNameForUpdate(String colorName) {
        return getColorByName(colorName, EntityPermission.READ_WRITE);
    }

    public ColorDetailValue getColorDetailValueForUpdate(Color color) {
        return color == null? null: color.getLastDetailForUpdate().getColorDetailValue().clone();
    }

    public ColorDetailValue getColorDetailValueByNameForUpdate(String colorName) {
        return getColorDetailValueForUpdate(getColorByNameForUpdate(colorName));
    }

    private static final Map<EntityPermission, String> getDefaultColorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM colors, colordetails " +
                "WHERE clr_activedetailid = clrdt_colordetailid " +
                "AND clrdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM colors, colordetails " +
                "WHERE clr_activedetailid = clrdt_colordetailid " +
                "AND clrdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultColorQueries = Collections.unmodifiableMap(queryMap);
    }

    private Color getDefaultColor(EntityPermission entityPermission) {
        return ColorFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultColorQueries);
    }

    public Color getDefaultColor() {
        return getDefaultColor(EntityPermission.READ_ONLY);
    }

    public Color getDefaultColorForUpdate() {
        return getDefaultColor(EntityPermission.READ_WRITE);
    }

    public ColorDetailValue getDefaultColorDetailValueForUpdate() {
        return getDefaultColorForUpdate().getLastDetailForUpdate().getColorDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getColorsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM colors, colordetails " +
                "WHERE clr_activedetailid = clrdt_colordetailid " +
                "ORDER BY clrdt_sortorder, clrdt_colorname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM colors, colordetails " +
                "WHERE clr_activedetailid = clrdt_colordetailid " +
                "FOR UPDATE");
        getColorsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Color> getColors(EntityPermission entityPermission) {
        return ColorFactory.getInstance().getEntitiesFromQuery(entityPermission, getColorsQueries);
    }

    public List<Color> getColors() {
        return getColors(EntityPermission.READ_ONLY);
    }

    public List<Color> getColorsForUpdate() {
        return getColors(EntityPermission.READ_WRITE);
    }

   public ColorTransfer getColorTransfer(UserVisit userVisit, Color color) {
        return getCoreTransferCaches(userVisit).getColorTransferCache().getColorTransfer(color);
    }

    public List<ColorTransfer> getColorTransfers(UserVisit userVisit, Collection<Color> entities) {
        List<ColorTransfer> transfers = new ArrayList<>(entities.size());
        ColorTransferCache transferCache = getCoreTransferCaches(userVisit).getColorTransferCache();
        
        entities.stream().forEach((entity) -> {
            transfers.add(transferCache.getColorTransfer(entity));
        });
        
        return transfers;
    }
    
    public List<ColorTransfer> getColorTransfers(UserVisit userVisit) {
        return getColorTransfers(userVisit, getColors());
    }

    public ColorChoicesBean getColorChoices(String defaultColorChoice, Language language, boolean allowNullChoice) {
        List<Color> colors = getColors();
        int size = colors.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultColorChoice == null) {
                defaultValue = "";
            }
        }

        for(Color color: colors) {
            ColorDetail colorDetail = color.getLastDetail();

            String label = getBestColorDescription(color, language);
            String value = colorDetail.getColorName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultColorChoice == null? false: defaultColorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && colorDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ColorChoicesBean(labels, values, defaultValue);
    }

    private void updateColorFromValue(ColorDetailValue colorDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(colorDetailValue.hasBeenModified()) {
            Color color = ColorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     colorDetailValue.getColorPK());
            ColorDetail colorDetail = color.getActiveDetailForUpdate();

            colorDetail.setThruTime(session.START_TIME_LONG);
            colorDetail.store();

            ColorPK colorPK = colorDetail.getColorPK(); // Not updated
            String colorName = colorDetailValue.getColorName();
            Integer red = colorDetailValue.getRed();
            Integer green = colorDetailValue.getGreen();
            Integer blue = colorDetailValue.getBlue();
            Boolean isDefault = colorDetailValue.getIsDefault();
            Integer sortOrder = colorDetailValue.getSortOrder();

            if(checkDefault) {
                Color defaultColor = getDefaultColor();
                boolean defaultFound = defaultColor != null && !defaultColor.equals(color);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ColorDetailValue defaultColorDetailValue = getDefaultColorDetailValueForUpdate();

                    defaultColorDetailValue.setIsDefault(Boolean.FALSE);
                    updateColorFromValue(defaultColorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            colorDetail = ColorDetailFactory.getInstance().create(colorPK, colorName, red, green, blue, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            color.setActiveDetail(colorDetail);
            color.setLastDetail(colorDetail);

            sendEventUsingNames(colorPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateColorFromValue(ColorDetailValue colorDetailValue, BasePK updatedBy) {
        updateColorFromValue(colorDetailValue, true, updatedBy);
    }

    private void deleteColor(Color color, boolean checkDefault, BasePK deletedBy) {
        ColorDetail colorDetail = color.getLastDetailForUpdate();

        deleteAppearancesByColor(color, deletedBy);
        deleteColorDescriptionsByColor(color, deletedBy);

        colorDetail.setThruTime(session.START_TIME_LONG);
        color.setActiveDetail(null);
        color.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            Color defaultColor = getDefaultColor();

            if(defaultColor == null) {
                List<Color> colors = getColorsForUpdate();

                if(!colors.isEmpty()) {
                    Iterator<Color> iter = colors.iterator();
                    if(iter.hasNext()) {
                        defaultColor = iter.next();
                    }
                    ColorDetailValue colorDetailValue = defaultColor.getLastDetailForUpdate().getColorDetailValue().clone();

                    colorDetailValue.setIsDefault(Boolean.TRUE);
                    updateColorFromValue(colorDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(color.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteColor(Color color, BasePK deletedBy) {
        deleteColor(color, true, deletedBy);
    }

    private void deleteColors(List<Color> colors, boolean checkDefault, BasePK deletedBy) {
        colors.stream().forEach((color) -> {
            deleteColor(color, checkDefault, deletedBy);
        });
    }

    public void deleteColors(List<Color> colors, BasePK deletedBy) {
        deleteColors(colors, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Color Descriptions
    // --------------------------------------------------------------------------------

    public ColorDescription createColorDescription(Color color, Language language, String description, BasePK createdBy) {
        ColorDescription colorDescription = ColorDescriptionFactory.getInstance().create(color, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(color.getPrimaryKey(), EventTypes.MODIFY.name(), colorDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return colorDescription;
    }

    private static final Map<EntityPermission, String> getColorDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM colordescriptions " +
                "WHERE clrd_clr_colorid = ? AND clrd_lang_languageid = ? AND clrd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM colordescriptions " +
                "WHERE clrd_clr_colorid = ? AND clrd_lang_languageid = ? AND clrd_thrutime = ? " +
                "FOR UPDATE");
        getColorDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ColorDescription getColorDescription(Color color, Language language, EntityPermission entityPermission) {
        return ColorDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getColorDescriptionQueries,
                color, language, Session.MAX_TIME);
    }

    public ColorDescription getColorDescription(Color color, Language language) {
        return getColorDescription(color, language, EntityPermission.READ_ONLY);
    }

    public ColorDescription getColorDescriptionForUpdate(Color color, Language language) {
        return getColorDescription(color, language, EntityPermission.READ_WRITE);
    }

    public ColorDescriptionValue getColorDescriptionValue(ColorDescription colorDescription) {
        return colorDescription == null? null: colorDescription.getColorDescriptionValue().clone();
    }

    public ColorDescriptionValue getColorDescriptionValueForUpdate(Color color, Language language) {
        return getColorDescriptionValue(getColorDescriptionForUpdate(color, language));
    }

    private static final Map<EntityPermission, String> getColorDescriptionsByColorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM colordescriptions, languages " +
                "WHERE clrd_clr_colorid = ? AND clrd_thrutime = ? AND clrd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM colordescriptions " +
                "WHERE clrd_clr_colorid = ? AND clrd_thrutime = ? " +
                "FOR UPDATE");
        getColorDescriptionsByColorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ColorDescription> getColorDescriptionsByColor(Color color, EntityPermission entityPermission) {
        return ColorDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getColorDescriptionsByColorQueries,
                color, Session.MAX_TIME);
    }

    public List<ColorDescription> getColorDescriptionsByColor(Color color) {
        return getColorDescriptionsByColor(color, EntityPermission.READ_ONLY);
    }

    public List<ColorDescription> getColorDescriptionsByColorForUpdate(Color color) {
        return getColorDescriptionsByColor(color, EntityPermission.READ_WRITE);
    }

    public String getBestColorDescription(Color color, Language language) {
        String description;
        ColorDescription colorDescription = getColorDescription(color, language);

        if(colorDescription == null && !language.getIsDefault()) {
            colorDescription = getColorDescription(color, getPartyControl().getDefaultLanguage());
        }

        if(colorDescription == null) {
            description = color.getLastDetail().getColorName();
        } else {
            description = colorDescription.getDescription();
        }

        return description;
    }

    public ColorDescriptionTransfer getColorDescriptionTransfer(UserVisit userVisit, ColorDescription colorDescription) {
        return getCoreTransferCaches(userVisit).getColorDescriptionTransferCache().getColorDescriptionTransfer(colorDescription);
    }

    public List<ColorDescriptionTransfer> getColorDescriptionTransfersByColor(UserVisit userVisit, Color color) {
        List<ColorDescription> colorDescriptions = getColorDescriptionsByColor(color);
        List<ColorDescriptionTransfer> colorDescriptionTransfers = new ArrayList<>(colorDescriptions.size());
        ColorDescriptionTransferCache colorDescriptionTransferCache = getCoreTransferCaches(userVisit).getColorDescriptionTransferCache();

        colorDescriptions.stream().forEach((colorDescription) -> {
            colorDescriptionTransfers.add(colorDescriptionTransferCache.getColorDescriptionTransfer(colorDescription));
        });

        return colorDescriptionTransfers;
    }

    public void updateColorDescriptionFromValue(ColorDescriptionValue colorDescriptionValue, BasePK updatedBy) {
        if(colorDescriptionValue.hasBeenModified()) {
            ColorDescription colorDescription = ColorDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    colorDescriptionValue.getPrimaryKey());

            colorDescription.setThruTime(session.START_TIME_LONG);
            colorDescription.store();

            Color color = colorDescription.getColor();
            Language language = colorDescription.getLanguage();
            String description = colorDescriptionValue.getDescription();

            colorDescription = ColorDescriptionFactory.getInstance().create(color, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(color.getPrimaryKey(), EventTypes.MODIFY.name(), colorDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteColorDescription(ColorDescription colorDescription, BasePK deletedBy) {
        colorDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(colorDescription.getColorPK(), EventTypes.MODIFY.name(), colorDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteColorDescriptionsByColor(Color color, BasePK deletedBy) {
        List<ColorDescription> colorDescriptions = getColorDescriptionsByColorForUpdate(color);

        colorDescriptions.stream().forEach((colorDescription) -> {
            deleteColorDescription(colorDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Font Styles
    // --------------------------------------------------------------------------------

    public FontStyle createFontStyle(String fontStyleName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        FontStyle defaultFontStyle = getDefaultFontStyle();
        boolean defaultFound = defaultFontStyle != null;

        if(defaultFound && isDefault) {
            FontStyleDetailValue defaultFontStyleDetailValue = getDefaultFontStyleDetailValueForUpdate();

            defaultFontStyleDetailValue.setIsDefault(Boolean.FALSE);
            updateFontStyleFromValue(defaultFontStyleDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        FontStyle fontStyle = FontStyleFactory.getInstance().create();
        FontStyleDetail fontStyleDetail = FontStyleDetailFactory.getInstance().create(fontStyle, fontStyleName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        fontStyle = FontStyleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, fontStyle.getPrimaryKey());
        fontStyle.setActiveDetail(fontStyleDetail);
        fontStyle.setLastDetail(fontStyleDetail);
        fontStyle.store();

        sendEventUsingNames(fontStyle.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return fontStyle;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.FontStyle */
    public FontStyle getFontStyleByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        FontStylePK pk = new FontStylePK(entityInstance.getEntityUniqueId());
        FontStyle entity = FontStyleFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return entity;
    }

    public FontStyle getFontStyleByEntityInstance(EntityInstance entityInstance) {
        return getFontStyleByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FontStyle getFontStyleByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFontStyleByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getFontStyleByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM fontstyles, fontstyledetails " +
                "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                "AND fntstyldt_fontstylename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM fontstyles, fontstyledetails " +
                "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                "AND fntstyldt_fontstylename = ? " +
                "FOR UPDATE");
        getFontStyleByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontStyle getFontStyleByName(String fontStyleName, EntityPermission entityPermission) {
        return FontStyleFactory.getInstance().getEntityFromQuery(entityPermission, getFontStyleByNameQueries, fontStyleName);
    }

    public FontStyle getFontStyleByName(String fontStyleName) {
        return getFontStyleByName(fontStyleName, EntityPermission.READ_ONLY);
    }

    public FontStyle getFontStyleByNameForUpdate(String fontStyleName) {
        return getFontStyleByName(fontStyleName, EntityPermission.READ_WRITE);
    }

    public FontStyleDetailValue getFontStyleDetailValueForUpdate(FontStyle fontStyle) {
        return fontStyle == null? null: fontStyle.getLastDetailForUpdate().getFontStyleDetailValue().clone();
    }

    public FontStyleDetailValue getFontStyleDetailValueByNameForUpdate(String fontStyleName) {
        return getFontStyleDetailValueForUpdate(getFontStyleByNameForUpdate(fontStyleName));
    }

    private static final Map<EntityPermission, String> getDefaultFontStyleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM fontstyles, fontstyledetails " +
                "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                "AND fntstyldt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM fontstyles, fontstyledetails " +
                "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                "AND fntstyldt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultFontStyleQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontStyle getDefaultFontStyle(EntityPermission entityPermission) {
        return FontStyleFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultFontStyleQueries);
    }

    public FontStyle getDefaultFontStyle() {
        return getDefaultFontStyle(EntityPermission.READ_ONLY);
    }

    public FontStyle getDefaultFontStyleForUpdate() {
        return getDefaultFontStyle(EntityPermission.READ_WRITE);
    }

    public FontStyleDetailValue getDefaultFontStyleDetailValueForUpdate() {
        return getDefaultFontStyleForUpdate().getLastDetailForUpdate().getFontStyleDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getFontStylesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM fontstyles, fontstyledetails " +
                "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                "ORDER BY fntstyldt_sortorder, fntstyldt_fontstylename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM fontstyles, fontstyledetails " +
                "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                "FOR UPDATE");
        getFontStylesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FontStyle> getFontStyles(EntityPermission entityPermission) {
        return FontStyleFactory.getInstance().getEntitiesFromQuery(entityPermission, getFontStylesQueries);
    }

    public List<FontStyle> getFontStyles() {
        return getFontStyles(EntityPermission.READ_ONLY);
    }

    public List<FontStyle> getFontStylesForUpdate() {
        return getFontStyles(EntityPermission.READ_WRITE);
    }

   public FontStyleTransfer getFontStyleTransfer(UserVisit userVisit, FontStyle fontStyle) {
        return getCoreTransferCaches(userVisit).getFontStyleTransferCache().getFontStyleTransfer(fontStyle);
    }

    public List<FontStyleTransfer> getFontStyleTransfers(UserVisit userVisit, Collection<FontStyle> entities) {
        List<FontStyleTransfer> transfers = new ArrayList<>(entities.size());
        FontStyleTransferCache transferCache = getCoreTransferCaches(userVisit).getFontStyleTransferCache();
        
        entities.stream().forEach((entity) -> {
            transfers.add(transferCache.getFontStyleTransfer(entity));
        });
        
        return transfers;
    }
    
    public List<FontStyleTransfer> getFontStyleTransfers(UserVisit userVisit) {
        return getFontStyleTransfers(userVisit, getFontStyles());
    }

    public FontStyleChoicesBean getFontStyleChoices(String defaultFontStyleChoice, Language language, boolean allowNullChoice) {
        List<FontStyle> fontStyles = getFontStyles();
        int size = fontStyles.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultFontStyleChoice == null) {
                defaultValue = "";
            }
        }

        for(FontStyle fontStyle: fontStyles) {
            FontStyleDetail fontStyleDetail = fontStyle.getLastDetail();

            String label = getBestFontStyleDescription(fontStyle, language);
            String value = fontStyleDetail.getFontStyleName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultFontStyleChoice == null? false: defaultFontStyleChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && fontStyleDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new FontStyleChoicesBean(labels, values, defaultValue);
    }

    private void updateFontStyleFromValue(FontStyleDetailValue fontStyleDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(fontStyleDetailValue.hasBeenModified()) {
            FontStyle fontStyle = FontStyleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     fontStyleDetailValue.getFontStylePK());
            FontStyleDetail fontStyleDetail = fontStyle.getActiveDetailForUpdate();

            fontStyleDetail.setThruTime(session.START_TIME_LONG);
            fontStyleDetail.store();

            FontStylePK fontStylePK = fontStyleDetail.getFontStylePK(); // Not updated
            String fontStyleName = fontStyleDetailValue.getFontStyleName();
            Boolean isDefault = fontStyleDetailValue.getIsDefault();
            Integer sortOrder = fontStyleDetailValue.getSortOrder();

            if(checkDefault) {
                FontStyle defaultFontStyle = getDefaultFontStyle();
                boolean defaultFound = defaultFontStyle != null && !defaultFontStyle.equals(fontStyle);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    FontStyleDetailValue defaultFontStyleDetailValue = getDefaultFontStyleDetailValueForUpdate();

                    defaultFontStyleDetailValue.setIsDefault(Boolean.FALSE);
                    updateFontStyleFromValue(defaultFontStyleDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            fontStyleDetail = FontStyleDetailFactory.getInstance().create(fontStylePK, fontStyleName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            fontStyle.setActiveDetail(fontStyleDetail);
            fontStyle.setLastDetail(fontStyleDetail);

            sendEventUsingNames(fontStylePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateFontStyleFromValue(FontStyleDetailValue fontStyleDetailValue, BasePK updatedBy) {
        updateFontStyleFromValue(fontStyleDetailValue, true, updatedBy);
    }

    private void deleteFontStyle(FontStyle fontStyle, boolean checkDefault, BasePK deletedBy) {
        FontStyleDetail fontStyleDetail = fontStyle.getLastDetailForUpdate();

        deleteAppearancesByFontStyle(fontStyle, deletedBy);
        deleteFontStyleDescriptionsByFontStyle(fontStyle, deletedBy);

        fontStyleDetail.setThruTime(session.START_TIME_LONG);
        fontStyle.setActiveDetail(null);
        fontStyle.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            FontStyle defaultFontStyle = getDefaultFontStyle();

            if(defaultFontStyle == null) {
                List<FontStyle> fontStyles = getFontStylesForUpdate();

                if(!fontStyles.isEmpty()) {
                    Iterator<FontStyle> iter = fontStyles.iterator();
                    if(iter.hasNext()) {
                        defaultFontStyle = iter.next();
                    }
                    FontStyleDetailValue fontStyleDetailValue = defaultFontStyle.getLastDetailForUpdate().getFontStyleDetailValue().clone();

                    fontStyleDetailValue.setIsDefault(Boolean.TRUE);
                    updateFontStyleFromValue(fontStyleDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(fontStyle.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteFontStyle(FontStyle fontStyle, BasePK deletedBy) {
        deleteFontStyle(fontStyle, true, deletedBy);
    }

    private void deleteFontStyles(List<FontStyle> fontStyles, boolean checkDefault, BasePK deletedBy) {
        fontStyles.stream().forEach((fontStyle) -> {
            deleteFontStyle(fontStyle, checkDefault, deletedBy);
        });
    }

    public void deleteFontStyles(List<FontStyle> fontStyles, BasePK deletedBy) {
        deleteFontStyles(fontStyles, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Font Style Descriptions
    // --------------------------------------------------------------------------------

    public FontStyleDescription createFontStyleDescription(FontStyle fontStyle, Language language, String description, BasePK createdBy) {
        FontStyleDescription fontStyleDescription = FontStyleDescriptionFactory.getInstance().create(fontStyle, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(fontStyle.getPrimaryKey(), EventTypes.MODIFY.name(), fontStyleDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return fontStyleDescription;
    }

    private static final Map<EntityPermission, String> getFontStyleDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM fontstyledescriptions " +
                "WHERE fntstyld_fntstyl_fontstyleid = ? AND fntstyld_lang_languageid = ? AND fntstyld_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM fontstyledescriptions " +
                "WHERE fntstyld_fntstyl_fontstyleid = ? AND fntstyld_lang_languageid = ? AND fntstyld_thrutime = ? " +
                "FOR UPDATE");
        getFontStyleDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontStyleDescription getFontStyleDescription(FontStyle fontStyle, Language language, EntityPermission entityPermission) {
        return FontStyleDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFontStyleDescriptionQueries,
                fontStyle, language, Session.MAX_TIME);
    }

    public FontStyleDescription getFontStyleDescription(FontStyle fontStyle, Language language) {
        return getFontStyleDescription(fontStyle, language, EntityPermission.READ_ONLY);
    }

    public FontStyleDescription getFontStyleDescriptionForUpdate(FontStyle fontStyle, Language language) {
        return getFontStyleDescription(fontStyle, language, EntityPermission.READ_WRITE);
    }

    public FontStyleDescriptionValue getFontStyleDescriptionValue(FontStyleDescription fontStyleDescription) {
        return fontStyleDescription == null? null: fontStyleDescription.getFontStyleDescriptionValue().clone();
    }

    public FontStyleDescriptionValue getFontStyleDescriptionValueForUpdate(FontStyle fontStyle, Language language) {
        return getFontStyleDescriptionValue(getFontStyleDescriptionForUpdate(fontStyle, language));
    }

    private static final Map<EntityPermission, String> getFontStyleDescriptionsByFontStyleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM fontstyledescriptions, languages " +
                "WHERE fntstyld_fntstyl_fontstyleid = ? AND fntstyld_thrutime = ? AND fntstyld_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM fontstyledescriptions " +
                "WHERE fntstyld_fntstyl_fontstyleid = ? AND fntstyld_thrutime = ? " +
                "FOR UPDATE");
        getFontStyleDescriptionsByFontStyleQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FontStyleDescription> getFontStyleDescriptionsByFontStyle(FontStyle fontStyle, EntityPermission entityPermission) {
        return FontStyleDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFontStyleDescriptionsByFontStyleQueries,
                fontStyle, Session.MAX_TIME);
    }

    public List<FontStyleDescription> getFontStyleDescriptionsByFontStyle(FontStyle fontStyle) {
        return getFontStyleDescriptionsByFontStyle(fontStyle, EntityPermission.READ_ONLY);
    }

    public List<FontStyleDescription> getFontStyleDescriptionsByFontStyleForUpdate(FontStyle fontStyle) {
        return getFontStyleDescriptionsByFontStyle(fontStyle, EntityPermission.READ_WRITE);
    }

    public String getBestFontStyleDescription(FontStyle fontStyle, Language language) {
        String description;
        FontStyleDescription fontStyleDescription = getFontStyleDescription(fontStyle, language);

        if(fontStyleDescription == null && !language.getIsDefault()) {
            fontStyleDescription = getFontStyleDescription(fontStyle, getPartyControl().getDefaultLanguage());
        }

        if(fontStyleDescription == null) {
            description = fontStyle.getLastDetail().getFontStyleName();
        } else {
            description = fontStyleDescription.getDescription();
        }

        return description;
    }

    public FontStyleDescriptionTransfer getFontStyleDescriptionTransfer(UserVisit userVisit, FontStyleDescription fontStyleDescription) {
        return getCoreTransferCaches(userVisit).getFontStyleDescriptionTransferCache().getFontStyleDescriptionTransfer(fontStyleDescription);
    }

    public List<FontStyleDescriptionTransfer> getFontStyleDescriptionTransfersByFontStyle(UserVisit userVisit, FontStyle fontStyle) {
        List<FontStyleDescription> fontStyleDescriptions = getFontStyleDescriptionsByFontStyle(fontStyle);
        List<FontStyleDescriptionTransfer> fontStyleDescriptionTransfers = new ArrayList<>(fontStyleDescriptions.size());
        FontStyleDescriptionTransferCache fontStyleDescriptionTransferCache = getCoreTransferCaches(userVisit).getFontStyleDescriptionTransferCache();

        fontStyleDescriptions.stream().forEach((fontStyleDescription) -> {
            fontStyleDescriptionTransfers.add(fontStyleDescriptionTransferCache.getFontStyleDescriptionTransfer(fontStyleDescription));
        });

        return fontStyleDescriptionTransfers;
    }

    public void updateFontStyleDescriptionFromValue(FontStyleDescriptionValue fontStyleDescriptionValue, BasePK updatedBy) {
        if(fontStyleDescriptionValue.hasBeenModified()) {
            FontStyleDescription fontStyleDescription = FontStyleDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    fontStyleDescriptionValue.getPrimaryKey());

            fontStyleDescription.setThruTime(session.START_TIME_LONG);
            fontStyleDescription.store();

            FontStyle fontStyle = fontStyleDescription.getFontStyle();
            Language language = fontStyleDescription.getLanguage();
            String description = fontStyleDescriptionValue.getDescription();

            fontStyleDescription = FontStyleDescriptionFactory.getInstance().create(fontStyle, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(fontStyle.getPrimaryKey(), EventTypes.MODIFY.name(), fontStyleDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteFontStyleDescription(FontStyleDescription fontStyleDescription, BasePK deletedBy) {
        fontStyleDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(fontStyleDescription.getFontStylePK(), EventTypes.MODIFY.name(), fontStyleDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteFontStyleDescriptionsByFontStyle(FontStyle fontStyle, BasePK deletedBy) {
        List<FontStyleDescription> fontStyleDescriptions = getFontStyleDescriptionsByFontStyleForUpdate(fontStyle);

        fontStyleDescriptions.stream().forEach((fontStyleDescription) -> {
            deleteFontStyleDescription(fontStyleDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Font Weights
    // --------------------------------------------------------------------------------

    public FontWeight createFontWeight(String fontWeightName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        FontWeight defaultFontWeight = getDefaultFontWeight();
        boolean defaultFound = defaultFontWeight != null;

        if(defaultFound && isDefault) {
            FontWeightDetailValue defaultFontWeightDetailValue = getDefaultFontWeightDetailValueForUpdate();

            defaultFontWeightDetailValue.setIsDefault(Boolean.FALSE);
            updateFontWeightFromValue(defaultFontWeightDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        FontWeight fontWeight = FontWeightFactory.getInstance().create();
        FontWeightDetail fontWeightDetail = FontWeightDetailFactory.getInstance().create(fontWeight, fontWeightName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        fontWeight = FontWeightFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, fontWeight.getPrimaryKey());
        fontWeight.setActiveDetail(fontWeightDetail);
        fontWeight.setLastDetail(fontWeightDetail);
        fontWeight.store();

        sendEventUsingNames(fontWeight.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return fontWeight;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.FontWeight */
    public FontWeight getFontWeightByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        FontWeightPK pk = new FontWeightPK(entityInstance.getEntityUniqueId());
        FontWeight entity = FontWeightFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return entity;
    }

    public FontWeight getFontWeightByEntityInstance(EntityInstance entityInstance) {
        return getFontWeightByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FontWeight getFontWeightByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFontWeightByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getFontWeightByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM fontweights, fontweightdetails " +
                "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                "AND fntwghtdt_fontweightname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM fontweights, fontweightdetails " +
                "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                "AND fntwghtdt_fontweightname = ? " +
                "FOR UPDATE");
        getFontWeightByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontWeight getFontWeightByName(String fontWeightName, EntityPermission entityPermission) {
        return FontWeightFactory.getInstance().getEntityFromQuery(entityPermission, getFontWeightByNameQueries, fontWeightName);
    }

    public FontWeight getFontWeightByName(String fontWeightName) {
        return getFontWeightByName(fontWeightName, EntityPermission.READ_ONLY);
    }

    public FontWeight getFontWeightByNameForUpdate(String fontWeightName) {
        return getFontWeightByName(fontWeightName, EntityPermission.READ_WRITE);
    }

    public FontWeightDetailValue getFontWeightDetailValueForUpdate(FontWeight fontWeight) {
        return fontWeight == null? null: fontWeight.getLastDetailForUpdate().getFontWeightDetailValue().clone();
    }

    public FontWeightDetailValue getFontWeightDetailValueByNameForUpdate(String fontWeightName) {
        return getFontWeightDetailValueForUpdate(getFontWeightByNameForUpdate(fontWeightName));
    }

    private static final Map<EntityPermission, String> getDefaultFontWeightQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM fontweights, fontweightdetails " +
                "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                "AND fntwghtdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM fontweights, fontweightdetails " +
                "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                "AND fntwghtdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultFontWeightQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontWeight getDefaultFontWeight(EntityPermission entityPermission) {
        return FontWeightFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultFontWeightQueries);
    }

    public FontWeight getDefaultFontWeight() {
        return getDefaultFontWeight(EntityPermission.READ_ONLY);
    }

    public FontWeight getDefaultFontWeightForUpdate() {
        return getDefaultFontWeight(EntityPermission.READ_WRITE);
    }

    public FontWeightDetailValue getDefaultFontWeightDetailValueForUpdate() {
        return getDefaultFontWeightForUpdate().getLastDetailForUpdate().getFontWeightDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getFontWeightsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM fontweights, fontweightdetails " +
                "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                "ORDER BY fntwghtdt_sortorder, fntwghtdt_fontweightname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM fontweights, fontweightdetails " +
                "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                "FOR UPDATE");
        getFontWeightsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FontWeight> getFontWeights(EntityPermission entityPermission) {
        return FontWeightFactory.getInstance().getEntitiesFromQuery(entityPermission, getFontWeightsQueries);
    }

    public List<FontWeight> getFontWeights() {
        return getFontWeights(EntityPermission.READ_ONLY);
    }

    public List<FontWeight> getFontWeightsForUpdate() {
        return getFontWeights(EntityPermission.READ_WRITE);
    }

   public FontWeightTransfer getFontWeightTransfer(UserVisit userVisit, FontWeight fontWeight) {
        return getCoreTransferCaches(userVisit).getFontWeightTransferCache().getFontWeightTransfer(fontWeight);
    }

    public List<FontWeightTransfer> getFontWeightTransfers(UserVisit userVisit, Collection<FontWeight> entities) {
        List<FontWeightTransfer> transfers = new ArrayList<>(entities.size());
        FontWeightTransferCache transferCache = getCoreTransferCaches(userVisit).getFontWeightTransferCache();
        
        entities.stream().forEach((entity) -> {
            transfers.add(transferCache.getFontWeightTransfer(entity));
        });
        
        return transfers;
    }
    
    public List<FontWeightTransfer> getFontWeightTransfers(UserVisit userVisit) {
        return getFontWeightTransfers(userVisit, getFontWeights());
    }

    public FontWeightChoicesBean getFontWeightChoices(String defaultFontWeightChoice, Language language, boolean allowNullChoice) {
        List<FontWeight> fontWeights = getFontWeights();
        int size = fontWeights.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultFontWeightChoice == null) {
                defaultValue = "";
            }
        }

        for(FontWeight fontWeight: fontWeights) {
            FontWeightDetail fontWeightDetail = fontWeight.getLastDetail();

            String label = getBestFontWeightDescription(fontWeight, language);
            String value = fontWeightDetail.getFontWeightName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultFontWeightChoice == null? false: defaultFontWeightChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && fontWeightDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new FontWeightChoicesBean(labels, values, defaultValue);
    }

    private void updateFontWeightFromValue(FontWeightDetailValue fontWeightDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(fontWeightDetailValue.hasBeenModified()) {
            FontWeight fontWeight = FontWeightFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     fontWeightDetailValue.getFontWeightPK());
            FontWeightDetail fontWeightDetail = fontWeight.getActiveDetailForUpdate();

            fontWeightDetail.setThruTime(session.START_TIME_LONG);
            fontWeightDetail.store();

            FontWeightPK fontWeightPK = fontWeightDetail.getFontWeightPK(); // Not updated
            String fontWeightName = fontWeightDetailValue.getFontWeightName();
            Boolean isDefault = fontWeightDetailValue.getIsDefault();
            Integer sortOrder = fontWeightDetailValue.getSortOrder();

            if(checkDefault) {
                FontWeight defaultFontWeight = getDefaultFontWeight();
                boolean defaultFound = defaultFontWeight != null && !defaultFontWeight.equals(fontWeight);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    FontWeightDetailValue defaultFontWeightDetailValue = getDefaultFontWeightDetailValueForUpdate();

                    defaultFontWeightDetailValue.setIsDefault(Boolean.FALSE);
                    updateFontWeightFromValue(defaultFontWeightDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            fontWeightDetail = FontWeightDetailFactory.getInstance().create(fontWeightPK, fontWeightName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            fontWeight.setActiveDetail(fontWeightDetail);
            fontWeight.setLastDetail(fontWeightDetail);

            sendEventUsingNames(fontWeightPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateFontWeightFromValue(FontWeightDetailValue fontWeightDetailValue, BasePK updatedBy) {
        updateFontWeightFromValue(fontWeightDetailValue, true, updatedBy);
    }

    private void deleteFontWeight(FontWeight fontWeight, boolean checkDefault, BasePK deletedBy) {
        FontWeightDetail fontWeightDetail = fontWeight.getLastDetailForUpdate();

        deleteAppearancesByFontWeight(fontWeight, deletedBy);
        deleteFontWeightDescriptionsByFontWeight(fontWeight, deletedBy);

        fontWeightDetail.setThruTime(session.START_TIME_LONG);
        fontWeight.setActiveDetail(null);
        fontWeight.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            FontWeight defaultFontWeight = getDefaultFontWeight();

            if(defaultFontWeight == null) {
                List<FontWeight> fontWeights = getFontWeightsForUpdate();

                if(!fontWeights.isEmpty()) {
                    Iterator<FontWeight> iter = fontWeights.iterator();
                    if(iter.hasNext()) {
                        defaultFontWeight = iter.next();
                    }
                    FontWeightDetailValue fontWeightDetailValue = defaultFontWeight.getLastDetailForUpdate().getFontWeightDetailValue().clone();

                    fontWeightDetailValue.setIsDefault(Boolean.TRUE);
                    updateFontWeightFromValue(fontWeightDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(fontWeight.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteFontWeight(FontWeight fontWeight, BasePK deletedBy) {
        deleteFontWeight(fontWeight, true, deletedBy);
    }

    private void deleteFontWeights(List<FontWeight> fontWeights, boolean checkDefault, BasePK deletedBy) {
        fontWeights.stream().forEach((fontWeight) -> {
            deleteFontWeight(fontWeight, checkDefault, deletedBy);
        });
    }

    public void deleteFontWeights(List<FontWeight> fontWeights, BasePK deletedBy) {
        deleteFontWeights(fontWeights, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Font Weight Descriptions
    // --------------------------------------------------------------------------------

    public FontWeightDescription createFontWeightDescription(FontWeight fontWeight, Language language, String description, BasePK createdBy) {
        FontWeightDescription fontWeightDescription = FontWeightDescriptionFactory.getInstance().create(fontWeight, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(fontWeight.getPrimaryKey(), EventTypes.MODIFY.name(), fontWeightDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return fontWeightDescription;
    }

    private static final Map<EntityPermission, String> getFontWeightDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM fontweightdescriptions " +
                "WHERE fntwghtd_fntwght_fontweightid = ? AND fntwghtd_lang_languageid = ? AND fntwghtd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM fontweightdescriptions " +
                "WHERE fntwghtd_fntwght_fontweightid = ? AND fntwghtd_lang_languageid = ? AND fntwghtd_thrutime = ? " +
                "FOR UPDATE");
        getFontWeightDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontWeightDescription getFontWeightDescription(FontWeight fontWeight, Language language, EntityPermission entityPermission) {
        return FontWeightDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFontWeightDescriptionQueries,
                fontWeight, language, Session.MAX_TIME);
    }

    public FontWeightDescription getFontWeightDescription(FontWeight fontWeight, Language language) {
        return getFontWeightDescription(fontWeight, language, EntityPermission.READ_ONLY);
    }

    public FontWeightDescription getFontWeightDescriptionForUpdate(FontWeight fontWeight, Language language) {
        return getFontWeightDescription(fontWeight, language, EntityPermission.READ_WRITE);
    }

    public FontWeightDescriptionValue getFontWeightDescriptionValue(FontWeightDescription fontWeightDescription) {
        return fontWeightDescription == null? null: fontWeightDescription.getFontWeightDescriptionValue().clone();
    }

    public FontWeightDescriptionValue getFontWeightDescriptionValueForUpdate(FontWeight fontWeight, Language language) {
        return getFontWeightDescriptionValue(getFontWeightDescriptionForUpdate(fontWeight, language));
    }

    private static final Map<EntityPermission, String> getFontWeightDescriptionsByFontWeightQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM fontweightdescriptions, languages " +
                "WHERE fntwghtd_fntwght_fontweightid = ? AND fntwghtd_thrutime = ? AND fntwghtd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM fontweightdescriptions " +
                "WHERE fntwghtd_fntwght_fontweightid = ? AND fntwghtd_thrutime = ? " +
                "FOR UPDATE");
        getFontWeightDescriptionsByFontWeightQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FontWeightDescription> getFontWeightDescriptionsByFontWeight(FontWeight fontWeight, EntityPermission entityPermission) {
        return FontWeightDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFontWeightDescriptionsByFontWeightQueries,
                fontWeight, Session.MAX_TIME);
    }

    public List<FontWeightDescription> getFontWeightDescriptionsByFontWeight(FontWeight fontWeight) {
        return getFontWeightDescriptionsByFontWeight(fontWeight, EntityPermission.READ_ONLY);
    }

    public List<FontWeightDescription> getFontWeightDescriptionsByFontWeightForUpdate(FontWeight fontWeight) {
        return getFontWeightDescriptionsByFontWeight(fontWeight, EntityPermission.READ_WRITE);
    }

    public String getBestFontWeightDescription(FontWeight fontWeight, Language language) {
        String description;
        FontWeightDescription fontWeightDescription = getFontWeightDescription(fontWeight, language);

        if(fontWeightDescription == null && !language.getIsDefault()) {
            fontWeightDescription = getFontWeightDescription(fontWeight, getPartyControl().getDefaultLanguage());
        }

        if(fontWeightDescription == null) {
            description = fontWeight.getLastDetail().getFontWeightName();
        } else {
            description = fontWeightDescription.getDescription();
        }

        return description;
    }

    public FontWeightDescriptionTransfer getFontWeightDescriptionTransfer(UserVisit userVisit, FontWeightDescription fontWeightDescription) {
        return getCoreTransferCaches(userVisit).getFontWeightDescriptionTransferCache().getFontWeightDescriptionTransfer(fontWeightDescription);
    }

    public List<FontWeightDescriptionTransfer> getFontWeightDescriptionTransfersByFontWeight(UserVisit userVisit, FontWeight fontWeight) {
        List<FontWeightDescription> fontWeightDescriptions = getFontWeightDescriptionsByFontWeight(fontWeight);
        List<FontWeightDescriptionTransfer> fontWeightDescriptionTransfers = new ArrayList<>(fontWeightDescriptions.size());
        FontWeightDescriptionTransferCache fontWeightDescriptionTransferCache = getCoreTransferCaches(userVisit).getFontWeightDescriptionTransferCache();

        fontWeightDescriptions.stream().forEach((fontWeightDescription) -> {
            fontWeightDescriptionTransfers.add(fontWeightDescriptionTransferCache.getFontWeightDescriptionTransfer(fontWeightDescription));
        });

        return fontWeightDescriptionTransfers;
    }

    public void updateFontWeightDescriptionFromValue(FontWeightDescriptionValue fontWeightDescriptionValue, BasePK updatedBy) {
        if(fontWeightDescriptionValue.hasBeenModified()) {
            FontWeightDescription fontWeightDescription = FontWeightDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    fontWeightDescriptionValue.getPrimaryKey());

            fontWeightDescription.setThruTime(session.START_TIME_LONG);
            fontWeightDescription.store();

            FontWeight fontWeight = fontWeightDescription.getFontWeight();
            Language language = fontWeightDescription.getLanguage();
            String description = fontWeightDescriptionValue.getDescription();

            fontWeightDescription = FontWeightDescriptionFactory.getInstance().create(fontWeight, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(fontWeight.getPrimaryKey(), EventTypes.MODIFY.name(), fontWeightDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteFontWeightDescription(FontWeightDescription fontWeightDescription, BasePK deletedBy) {
        fontWeightDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(fontWeightDescription.getFontWeightPK(), EventTypes.MODIFY.name(), fontWeightDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteFontWeightDescriptionsByFontWeight(FontWeight fontWeight, BasePK deletedBy) {
        List<FontWeightDescription> fontWeightDescriptions = getFontWeightDescriptionsByFontWeightForUpdate(fontWeight);

        fontWeightDescriptions.stream().forEach((fontWeightDescription) -> {
            deleteFontWeightDescription(fontWeightDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Text Decorations
    // --------------------------------------------------------------------------------

    public TextDecoration createTextDecoration(String textDecorationName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        TextDecoration defaultTextDecoration = getDefaultTextDecoration();
        boolean defaultFound = defaultTextDecoration != null;

        if(defaultFound && isDefault) {
            TextDecorationDetailValue defaultTextDecorationDetailValue = getDefaultTextDecorationDetailValueForUpdate();

            defaultTextDecorationDetailValue.setIsDefault(Boolean.FALSE);
            updateTextDecorationFromValue(defaultTextDecorationDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        TextDecoration textDecoration = TextDecorationFactory.getInstance().create();
        TextDecorationDetail textDecorationDetail = TextDecorationDetailFactory.getInstance().create(textDecoration, textDecorationName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        textDecoration = TextDecorationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, textDecoration.getPrimaryKey());
        textDecoration.setActiveDetail(textDecorationDetail);
        textDecoration.setLastDetail(textDecorationDetail);
        textDecoration.store();

        sendEventUsingNames(textDecoration.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return textDecoration;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.TextDecoration */
    public TextDecoration getTextDecorationByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        TextDecorationPK pk = new TextDecorationPK(entityInstance.getEntityUniqueId());
        TextDecoration entity = TextDecorationFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return entity;
    }

    public TextDecoration getTextDecorationByEntityInstance(EntityInstance entityInstance) {
        return getTextDecorationByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TextDecoration getTextDecorationByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTextDecorationByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getTextDecorationByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM textdecorations, textdecorationdetails " +
                "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                "AND txtdcrtndt_textdecorationname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM textdecorations, textdecorationdetails " +
                "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                "AND txtdcrtndt_textdecorationname = ? " +
                "FOR UPDATE");
        getTextDecorationByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextDecoration getTextDecorationByName(String textDecorationName, EntityPermission entityPermission) {
        return TextDecorationFactory.getInstance().getEntityFromQuery(entityPermission, getTextDecorationByNameQueries, textDecorationName);
    }

    public TextDecoration getTextDecorationByName(String textDecorationName) {
        return getTextDecorationByName(textDecorationName, EntityPermission.READ_ONLY);
    }

    public TextDecoration getTextDecorationByNameForUpdate(String textDecorationName) {
        return getTextDecorationByName(textDecorationName, EntityPermission.READ_WRITE);
    }

    public TextDecorationDetailValue getTextDecorationDetailValueForUpdate(TextDecoration textDecoration) {
        return textDecoration == null? null: textDecoration.getLastDetailForUpdate().getTextDecorationDetailValue().clone();
    }

    public TextDecorationDetailValue getTextDecorationDetailValueByNameForUpdate(String textDecorationName) {
        return getTextDecorationDetailValueForUpdate(getTextDecorationByNameForUpdate(textDecorationName));
    }

    private static final Map<EntityPermission, String> getDefaultTextDecorationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM textdecorations, textdecorationdetails " +
                "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                "AND txtdcrtndt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM textdecorations, textdecorationdetails " +
                "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                "AND txtdcrtndt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultTextDecorationQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextDecoration getDefaultTextDecoration(EntityPermission entityPermission) {
        return TextDecorationFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultTextDecorationQueries);
    }

    public TextDecoration getDefaultTextDecoration() {
        return getDefaultTextDecoration(EntityPermission.READ_ONLY);
    }

    public TextDecoration getDefaultTextDecorationForUpdate() {
        return getDefaultTextDecoration(EntityPermission.READ_WRITE);
    }

    public TextDecorationDetailValue getDefaultTextDecorationDetailValueForUpdate() {
        return getDefaultTextDecorationForUpdate().getLastDetailForUpdate().getTextDecorationDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getTextDecorationsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM textdecorations, textdecorationdetails " +
                "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                "ORDER BY txtdcrtndt_sortorder, txtdcrtndt_textdecorationname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM textdecorations, textdecorationdetails " +
                "WHERE txtdcrtn_activedetailid = txtdcrtndt_textdecorationdetailid " +
                "FOR UPDATE");
        getTextDecorationsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TextDecoration> getTextDecorations(EntityPermission entityPermission) {
        return TextDecorationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTextDecorationsQueries);
    }

    public List<TextDecoration> getTextDecorations() {
        return getTextDecorations(EntityPermission.READ_ONLY);
    }

    public List<TextDecoration> getTextDecorationsForUpdate() {
        return getTextDecorations(EntityPermission.READ_WRITE);
    }

   public TextDecorationTransfer getTextDecorationTransfer(UserVisit userVisit, TextDecoration textDecoration) {
        return getCoreTransferCaches(userVisit).getTextDecorationTransferCache().getTextDecorationTransfer(textDecoration);
    }

    public List<TextDecorationTransfer> getTextDecorationTransfers(UserVisit userVisit, Collection<TextDecoration> entities) {
        List<TextDecorationTransfer> transfers = new ArrayList<>(entities.size());
        TextDecorationTransferCache transferCache = getCoreTransferCaches(userVisit).getTextDecorationTransferCache();
        
        entities.stream().forEach((entity) -> {
            transfers.add(transferCache.getTextDecorationTransfer(entity));
        });
        
        return transfers;
    }
    
    public List<TextDecorationTransfer> getTextDecorationTransfers(UserVisit userVisit) {
        return getTextDecorationTransfers(userVisit, getTextDecorations());
    }

    public TextDecorationChoicesBean getTextDecorationChoices(String defaultTextDecorationChoice, Language language, boolean allowNullChoice) {
        List<TextDecoration> textDecorations = getTextDecorations();
        int size = textDecorations.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultTextDecorationChoice == null) {
                defaultValue = "";
            }
        }

        for(TextDecoration textDecoration: textDecorations) {
            TextDecorationDetail textDecorationDetail = textDecoration.getLastDetail();

            String label = getBestTextDecorationDescription(textDecoration, language);
            String value = textDecorationDetail.getTextDecorationName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultTextDecorationChoice == null? false: defaultTextDecorationChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && textDecorationDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new TextDecorationChoicesBean(labels, values, defaultValue);
    }

    private void updateTextDecorationFromValue(TextDecorationDetailValue textDecorationDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(textDecorationDetailValue.hasBeenModified()) {
            TextDecoration textDecoration = TextDecorationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     textDecorationDetailValue.getTextDecorationPK());
            TextDecorationDetail textDecorationDetail = textDecoration.getActiveDetailForUpdate();

            textDecorationDetail.setThruTime(session.START_TIME_LONG);
            textDecorationDetail.store();

            TextDecorationPK textDecorationPK = textDecorationDetail.getTextDecorationPK(); // Not updated
            String textDecorationName = textDecorationDetailValue.getTextDecorationName();
            Boolean isDefault = textDecorationDetailValue.getIsDefault();
            Integer sortOrder = textDecorationDetailValue.getSortOrder();

            if(checkDefault) {
                TextDecoration defaultTextDecoration = getDefaultTextDecoration();
                boolean defaultFound = defaultTextDecoration != null && !defaultTextDecoration.equals(textDecoration);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    TextDecorationDetailValue defaultTextDecorationDetailValue = getDefaultTextDecorationDetailValueForUpdate();

                    defaultTextDecorationDetailValue.setIsDefault(Boolean.FALSE);
                    updateTextDecorationFromValue(defaultTextDecorationDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            textDecorationDetail = TextDecorationDetailFactory.getInstance().create(textDecorationPK, textDecorationName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            textDecoration.setActiveDetail(textDecorationDetail);
            textDecoration.setLastDetail(textDecorationDetail);

            sendEventUsingNames(textDecorationPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateTextDecorationFromValue(TextDecorationDetailValue textDecorationDetailValue, BasePK updatedBy) {
        updateTextDecorationFromValue(textDecorationDetailValue, true, updatedBy);
    }

    private void deleteTextDecoration(TextDecoration textDecoration, boolean checkDefault, BasePK deletedBy) {
        TextDecorationDetail textDecorationDetail = textDecoration.getLastDetailForUpdate();

        deleteAppearanceTextDecorationsByTextDecoration(textDecoration, deletedBy);
        deleteTextDecorationDescriptionsByTextDecoration(textDecoration, deletedBy);

        textDecorationDetail.setThruTime(session.START_TIME_LONG);
        textDecoration.setActiveDetail(null);
        textDecoration.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            TextDecoration defaultTextDecoration = getDefaultTextDecoration();

            if(defaultTextDecoration == null) {
                List<TextDecoration> textDecorations = getTextDecorationsForUpdate();

                if(!textDecorations.isEmpty()) {
                    Iterator<TextDecoration> iter = textDecorations.iterator();
                    if(iter.hasNext()) {
                        defaultTextDecoration = iter.next();
                    }
                    TextDecorationDetailValue textDecorationDetailValue = defaultTextDecoration.getLastDetailForUpdate().getTextDecorationDetailValue().clone();

                    textDecorationDetailValue.setIsDefault(Boolean.TRUE);
                    updateTextDecorationFromValue(textDecorationDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(textDecoration.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteTextDecoration(TextDecoration textDecoration, BasePK deletedBy) {
        deleteTextDecoration(textDecoration, true, deletedBy);
    }

    private void deleteTextDecorations(List<TextDecoration> textDecorations, boolean checkDefault, BasePK deletedBy) {
        textDecorations.stream().forEach((textDecoration) -> {
            deleteTextDecoration(textDecoration, checkDefault, deletedBy);
        });
    }

    public void deleteTextDecorations(List<TextDecoration> textDecorations, BasePK deletedBy) {
        deleteTextDecorations(textDecorations, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Text Decoration Descriptions
    // --------------------------------------------------------------------------------

    public TextDecorationDescription createTextDecorationDescription(TextDecoration textDecoration, Language language, String description, BasePK createdBy) {
        TextDecorationDescription textDecorationDescription = TextDecorationDescriptionFactory.getInstance().create(textDecoration, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(textDecoration.getPrimaryKey(), EventTypes.MODIFY.name(), textDecorationDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return textDecorationDescription;
    }

    private static final Map<EntityPermission, String> getTextDecorationDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM textdecorationdescriptions " +
                "WHERE txtdcrtnd_txtdcrtn_textdecorationid = ? AND txtdcrtnd_lang_languageid = ? AND txtdcrtnd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM textdecorationdescriptions " +
                "WHERE txtdcrtnd_txtdcrtn_textdecorationid = ? AND txtdcrtnd_lang_languageid = ? AND txtdcrtnd_thrutime = ? " +
                "FOR UPDATE");
        getTextDecorationDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextDecorationDescription getTextDecorationDescription(TextDecoration textDecoration, Language language, EntityPermission entityPermission) {
        return TextDecorationDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getTextDecorationDescriptionQueries,
                textDecoration, language, Session.MAX_TIME);
    }

    public TextDecorationDescription getTextDecorationDescription(TextDecoration textDecoration, Language language) {
        return getTextDecorationDescription(textDecoration, language, EntityPermission.READ_ONLY);
    }

    public TextDecorationDescription getTextDecorationDescriptionForUpdate(TextDecoration textDecoration, Language language) {
        return getTextDecorationDescription(textDecoration, language, EntityPermission.READ_WRITE);
    }

    public TextDecorationDescriptionValue getTextDecorationDescriptionValue(TextDecorationDescription textDecorationDescription) {
        return textDecorationDescription == null? null: textDecorationDescription.getTextDecorationDescriptionValue().clone();
    }

    public TextDecorationDescriptionValue getTextDecorationDescriptionValueForUpdate(TextDecoration textDecoration, Language language) {
        return getTextDecorationDescriptionValue(getTextDecorationDescriptionForUpdate(textDecoration, language));
    }

    private static final Map<EntityPermission, String> getTextDecorationDescriptionsByTextDecorationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM textdecorationdescriptions, languages " +
                "WHERE txtdcrtnd_txtdcrtn_textdecorationid = ? AND txtdcrtnd_thrutime = ? AND txtdcrtnd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM textdecorationdescriptions " +
                "WHERE txtdcrtnd_txtdcrtn_textdecorationid = ? AND txtdcrtnd_thrutime = ? " +
                "FOR UPDATE");
        getTextDecorationDescriptionsByTextDecorationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TextDecorationDescription> getTextDecorationDescriptionsByTextDecoration(TextDecoration textDecoration, EntityPermission entityPermission) {
        return TextDecorationDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getTextDecorationDescriptionsByTextDecorationQueries,
                textDecoration, Session.MAX_TIME);
    }

    public List<TextDecorationDescription> getTextDecorationDescriptionsByTextDecoration(TextDecoration textDecoration) {
        return getTextDecorationDescriptionsByTextDecoration(textDecoration, EntityPermission.READ_ONLY);
    }

    public List<TextDecorationDescription> getTextDecorationDescriptionsByTextDecorationForUpdate(TextDecoration textDecoration) {
        return getTextDecorationDescriptionsByTextDecoration(textDecoration, EntityPermission.READ_WRITE);
    }

    public String getBestTextDecorationDescription(TextDecoration textDecoration, Language language) {
        String description;
        TextDecorationDescription textDecorationDescription = getTextDecorationDescription(textDecoration, language);

        if(textDecorationDescription == null && !language.getIsDefault()) {
            textDecorationDescription = getTextDecorationDescription(textDecoration, getPartyControl().getDefaultLanguage());
        }

        if(textDecorationDescription == null) {
            description = textDecoration.getLastDetail().getTextDecorationName();
        } else {
            description = textDecorationDescription.getDescription();
        }

        return description;
    }

    public TextDecorationDescriptionTransfer getTextDecorationDescriptionTransfer(UserVisit userVisit, TextDecorationDescription textDecorationDescription) {
        return getCoreTransferCaches(userVisit).getTextDecorationDescriptionTransferCache().getTextDecorationDescriptionTransfer(textDecorationDescription);
    }

    public List<TextDecorationDescriptionTransfer> getTextDecorationDescriptionTransfersByTextDecoration(UserVisit userVisit, TextDecoration textDecoration) {
        List<TextDecorationDescription> textDecorationDescriptions = getTextDecorationDescriptionsByTextDecoration(textDecoration);
        List<TextDecorationDescriptionTransfer> textDecorationDescriptionTransfers = new ArrayList<>(textDecorationDescriptions.size());
        TextDecorationDescriptionTransferCache textDecorationDescriptionTransferCache = getCoreTransferCaches(userVisit).getTextDecorationDescriptionTransferCache();

        textDecorationDescriptions.stream().forEach((textDecorationDescription) -> {
            textDecorationDescriptionTransfers.add(textDecorationDescriptionTransferCache.getTextDecorationDescriptionTransfer(textDecorationDescription));
        });

        return textDecorationDescriptionTransfers;
    }

    public void updateTextDecorationDescriptionFromValue(TextDecorationDescriptionValue textDecorationDescriptionValue, BasePK updatedBy) {
        if(textDecorationDescriptionValue.hasBeenModified()) {
            TextDecorationDescription textDecorationDescription = TextDecorationDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    textDecorationDescriptionValue.getPrimaryKey());

            textDecorationDescription.setThruTime(session.START_TIME_LONG);
            textDecorationDescription.store();

            TextDecoration textDecoration = textDecorationDescription.getTextDecoration();
            Language language = textDecorationDescription.getLanguage();
            String description = textDecorationDescriptionValue.getDescription();

            textDecorationDescription = TextDecorationDescriptionFactory.getInstance().create(textDecoration, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(textDecoration.getPrimaryKey(), EventTypes.MODIFY.name(), textDecorationDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteTextDecorationDescription(TextDecorationDescription textDecorationDescription, BasePK deletedBy) {
        textDecorationDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(textDecorationDescription.getTextDecorationPK(), EventTypes.MODIFY.name(), textDecorationDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteTextDecorationDescriptionsByTextDecoration(TextDecoration textDecoration, BasePK deletedBy) {
        List<TextDecorationDescription> textDecorationDescriptions = getTextDecorationDescriptionsByTextDecorationForUpdate(textDecoration);

        textDecorationDescriptions.stream().forEach((textDecorationDescription) -> {
            deleteTextDecorationDescription(textDecorationDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Text Transformations
    // --------------------------------------------------------------------------------

    public TextTransformation createTextTransformation(String textTransformationName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        TextTransformation defaultTextTransformation = getDefaultTextTransformation();
        boolean defaultFound = defaultTextTransformation != null;

        if(defaultFound && isDefault) {
            TextTransformationDetailValue defaultTextTransformationDetailValue = getDefaultTextTransformationDetailValueForUpdate();

            defaultTextTransformationDetailValue.setIsDefault(Boolean.FALSE);
            updateTextTransformationFromValue(defaultTextTransformationDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        TextTransformation textTransformation = TextTransformationFactory.getInstance().create();
        TextTransformationDetail textTransformationDetail = TextTransformationDetailFactory.getInstance().create(textTransformation, textTransformationName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        textTransformation = TextTransformationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, textTransformation.getPrimaryKey());
        textTransformation.setActiveDetail(textTransformationDetail);
        textTransformation.setLastDetail(textTransformationDetail);
        textTransformation.store();

        sendEventUsingNames(textTransformation.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return textTransformation;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.TextTransformation */
    public TextTransformation getTextTransformationByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        TextTransformationPK pk = new TextTransformationPK(entityInstance.getEntityUniqueId());
        TextTransformation entity = TextTransformationFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return entity;
    }

    public TextTransformation getTextTransformationByEntityInstance(EntityInstance entityInstance) {
        return getTextTransformationByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TextTransformation getTextTransformationByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTextTransformationByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getTextTransformationByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM texttransformations, texttransformationdetails " +
                "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                "AND txttrnsdt_texttransformationname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM texttransformations, texttransformationdetails " +
                "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                "AND txttrnsdt_texttransformationname = ? " +
                "FOR UPDATE");
        getTextTransformationByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextTransformation getTextTransformationByName(String textTransformationName, EntityPermission entityPermission) {
        return TextTransformationFactory.getInstance().getEntityFromQuery(entityPermission, getTextTransformationByNameQueries, textTransformationName);
    }

    public TextTransformation getTextTransformationByName(String textTransformationName) {
        return getTextTransformationByName(textTransformationName, EntityPermission.READ_ONLY);
    }

    public TextTransformation getTextTransformationByNameForUpdate(String textTransformationName) {
        return getTextTransformationByName(textTransformationName, EntityPermission.READ_WRITE);
    }

    public TextTransformationDetailValue getTextTransformationDetailValueForUpdate(TextTransformation textTransformation) {
        return textTransformation == null? null: textTransformation.getLastDetailForUpdate().getTextTransformationDetailValue().clone();
    }

    public TextTransformationDetailValue getTextTransformationDetailValueByNameForUpdate(String textTransformationName) {
        return getTextTransformationDetailValueForUpdate(getTextTransformationByNameForUpdate(textTransformationName));
    }

    private static final Map<EntityPermission, String> getDefaultTextTransformationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM texttransformations, texttransformationdetails " +
                "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                "AND txttrnsdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM texttransformations, texttransformationdetails " +
                "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                "AND txttrnsdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultTextTransformationQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextTransformation getDefaultTextTransformation(EntityPermission entityPermission) {
        return TextTransformationFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultTextTransformationQueries);
    }

    public TextTransformation getDefaultTextTransformation() {
        return getDefaultTextTransformation(EntityPermission.READ_ONLY);
    }

    public TextTransformation getDefaultTextTransformationForUpdate() {
        return getDefaultTextTransformation(EntityPermission.READ_WRITE);
    }

    public TextTransformationDetailValue getDefaultTextTransformationDetailValueForUpdate() {
        return getDefaultTextTransformationForUpdate().getLastDetailForUpdate().getTextTransformationDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getTextTransformationsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM texttransformations, texttransformationdetails " +
                "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                "ORDER BY txttrnsdt_sortorder, txttrnsdt_texttransformationname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM texttransformations, texttransformationdetails " +
                "WHERE txttrns_activedetailid = txttrnsdt_texttransformationdetailid " +
                "FOR UPDATE");
        getTextTransformationsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TextTransformation> getTextTransformations(EntityPermission entityPermission) {
        return TextTransformationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTextTransformationsQueries);
    }

    public List<TextTransformation> getTextTransformations() {
        return getTextTransformations(EntityPermission.READ_ONLY);
    }

    public List<TextTransformation> getTextTransformationsForUpdate() {
        return getTextTransformations(EntityPermission.READ_WRITE);
    }

   public TextTransformationTransfer getTextTransformationTransfer(UserVisit userVisit, TextTransformation textTransformation) {
        return getCoreTransferCaches(userVisit).getTextTransformationTransferCache().getTextTransformationTransfer(textTransformation);
    }

    public List<TextTransformationTransfer> getTextTransformationTransfers(UserVisit userVisit, Collection<TextTransformation> entities) {
        List<TextTransformationTransfer> transfers = new ArrayList<>(entities.size());
        TextTransformationTransferCache transferCache = getCoreTransferCaches(userVisit).getTextTransformationTransferCache();
        
        entities.stream().forEach((entity) -> {
            transfers.add(transferCache.getTextTransformationTransfer(entity));
        });
        
        return transfers;
    }
    
    public List<TextTransformationTransfer> getTextTransformationTransfers(UserVisit userVisit) {
        return getTextTransformationTransfers(userVisit, getTextTransformations());
    }

    public TextTransformationChoicesBean getTextTransformationChoices(String defaultTextTransformationChoice, Language language, boolean allowNullChoice) {
        List<TextTransformation> textTransformations = getTextTransformations();
        int size = textTransformations.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultTextTransformationChoice == null) {
                defaultValue = "";
            }
        }

        for(TextTransformation textTransformation: textTransformations) {
            TextTransformationDetail textTransformationDetail = textTransformation.getLastDetail();

            String label = getBestTextTransformationDescription(textTransformation, language);
            String value = textTransformationDetail.getTextTransformationName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultTextTransformationChoice == null? false: defaultTextTransformationChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && textTransformationDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new TextTransformationChoicesBean(labels, values, defaultValue);
    }

    private void updateTextTransformationFromValue(TextTransformationDetailValue textTransformationDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(textTransformationDetailValue.hasBeenModified()) {
            TextTransformation textTransformation = TextTransformationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     textTransformationDetailValue.getTextTransformationPK());
            TextTransformationDetail textTransformationDetail = textTransformation.getActiveDetailForUpdate();

            textTransformationDetail.setThruTime(session.START_TIME_LONG);
            textTransformationDetail.store();

            TextTransformationPK textTransformationPK = textTransformationDetail.getTextTransformationPK(); // Not updated
            String textTransformationName = textTransformationDetailValue.getTextTransformationName();
            Boolean isDefault = textTransformationDetailValue.getIsDefault();
            Integer sortOrder = textTransformationDetailValue.getSortOrder();

            if(checkDefault) {
                TextTransformation defaultTextTransformation = getDefaultTextTransformation();
                boolean defaultFound = defaultTextTransformation != null && !defaultTextTransformation.equals(textTransformation);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    TextTransformationDetailValue defaultTextTransformationDetailValue = getDefaultTextTransformationDetailValueForUpdate();

                    defaultTextTransformationDetailValue.setIsDefault(Boolean.FALSE);
                    updateTextTransformationFromValue(defaultTextTransformationDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            textTransformationDetail = TextTransformationDetailFactory.getInstance().create(textTransformationPK, textTransformationName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            textTransformation.setActiveDetail(textTransformationDetail);
            textTransformation.setLastDetail(textTransformationDetail);

            sendEventUsingNames(textTransformationPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateTextTransformationFromValue(TextTransformationDetailValue textTransformationDetailValue, BasePK updatedBy) {
        updateTextTransformationFromValue(textTransformationDetailValue, true, updatedBy);
    }

    private void deleteTextTransformation(TextTransformation textTransformation, boolean checkDefault, BasePK deletedBy) {
        TextTransformationDetail textTransformationDetail = textTransformation.getLastDetailForUpdate();

        deleteAppearanceTextTransformationsByTextTransformation(textTransformation, deletedBy);
        deleteTextTransformationDescriptionsByTextTransformation(textTransformation, deletedBy);

        textTransformationDetail.setThruTime(session.START_TIME_LONG);
        textTransformation.setActiveDetail(null);
        textTransformation.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            TextTransformation defaultTextTransformation = getDefaultTextTransformation();

            if(defaultTextTransformation == null) {
                List<TextTransformation> textTransformations = getTextTransformationsForUpdate();

                if(!textTransformations.isEmpty()) {
                    Iterator<TextTransformation> iter = textTransformations.iterator();
                    if(iter.hasNext()) {
                        defaultTextTransformation = iter.next();
                    }
                    TextTransformationDetailValue textTransformationDetailValue = defaultTextTransformation.getLastDetailForUpdate().getTextTransformationDetailValue().clone();

                    textTransformationDetailValue.setIsDefault(Boolean.TRUE);
                    updateTextTransformationFromValue(textTransformationDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(textTransformation.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteTextTransformation(TextTransformation textTransformation, BasePK deletedBy) {
        deleteTextTransformation(textTransformation, true, deletedBy);
    }

    private void deleteTextTransformations(List<TextTransformation> textTransformations, boolean checkDefault, BasePK deletedBy) {
        textTransformations.stream().forEach((textTransformation) -> {
            deleteTextTransformation(textTransformation, checkDefault, deletedBy);
        });
    }

    public void deleteTextTransformations(List<TextTransformation> textTransformations, BasePK deletedBy) {
        deleteTextTransformations(textTransformations, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Text Transformation Descriptions
    // --------------------------------------------------------------------------------

    public TextTransformationDescription createTextTransformationDescription(TextTransformation textTransformation, Language language, String description, BasePK createdBy) {
        TextTransformationDescription textTransformationDescription = TextTransformationDescriptionFactory.getInstance().create(textTransformation, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(textTransformation.getPrimaryKey(), EventTypes.MODIFY.name(), textTransformationDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return textTransformationDescription;
    }

    private static final Map<EntityPermission, String> getTextTransformationDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM texttransformationdescriptions " +
                "WHERE txttrnsd_txttrns_texttransformationid = ? AND txttrnsd_lang_languageid = ? AND txttrnsd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM texttransformationdescriptions " +
                "WHERE txttrnsd_txttrns_texttransformationid = ? AND txttrnsd_lang_languageid = ? AND txttrnsd_thrutime = ? " +
                "FOR UPDATE");
        getTextTransformationDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private TextTransformationDescription getTextTransformationDescription(TextTransformation textTransformation, Language language, EntityPermission entityPermission) {
        return TextTransformationDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getTextTransformationDescriptionQueries,
                textTransformation, language, Session.MAX_TIME);
    }

    public TextTransformationDescription getTextTransformationDescription(TextTransformation textTransformation, Language language) {
        return getTextTransformationDescription(textTransformation, language, EntityPermission.READ_ONLY);
    }

    public TextTransformationDescription getTextTransformationDescriptionForUpdate(TextTransformation textTransformation, Language language) {
        return getTextTransformationDescription(textTransformation, language, EntityPermission.READ_WRITE);
    }

    public TextTransformationDescriptionValue getTextTransformationDescriptionValue(TextTransformationDescription textTransformationDescription) {
        return textTransformationDescription == null? null: textTransformationDescription.getTextTransformationDescriptionValue().clone();
    }

    public TextTransformationDescriptionValue getTextTransformationDescriptionValueForUpdate(TextTransformation textTransformation, Language language) {
        return getTextTransformationDescriptionValue(getTextTransformationDescriptionForUpdate(textTransformation, language));
    }

    private static final Map<EntityPermission, String> getTextTransformationDescriptionsByTextTransformationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM texttransformationdescriptions, languages " +
                "WHERE txttrnsd_txttrns_texttransformationid = ? AND txttrnsd_thrutime = ? AND txttrnsd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM texttransformationdescriptions " +
                "WHERE txttrnsd_txttrns_texttransformationid = ? AND txttrnsd_thrutime = ? " +
                "FOR UPDATE");
        getTextTransformationDescriptionsByTextTransformationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TextTransformationDescription> getTextTransformationDescriptionsByTextTransformation(TextTransformation textTransformation, EntityPermission entityPermission) {
        return TextTransformationDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getTextTransformationDescriptionsByTextTransformationQueries,
                textTransformation, Session.MAX_TIME);
    }

    public List<TextTransformationDescription> getTextTransformationDescriptionsByTextTransformation(TextTransformation textTransformation) {
        return getTextTransformationDescriptionsByTextTransformation(textTransformation, EntityPermission.READ_ONLY);
    }

    public List<TextTransformationDescription> getTextTransformationDescriptionsByTextTransformationForUpdate(TextTransformation textTransformation) {
        return getTextTransformationDescriptionsByTextTransformation(textTransformation, EntityPermission.READ_WRITE);
    }

    public String getBestTextTransformationDescription(TextTransformation textTransformation, Language language) {
        String description;
        TextTransformationDescription textTransformationDescription = getTextTransformationDescription(textTransformation, language);

        if(textTransformationDescription == null && !language.getIsDefault()) {
            textTransformationDescription = getTextTransformationDescription(textTransformation, getPartyControl().getDefaultLanguage());
        }

        if(textTransformationDescription == null) {
            description = textTransformation.getLastDetail().getTextTransformationName();
        } else {
            description = textTransformationDescription.getDescription();
        }

        return description;
    }

    public TextTransformationDescriptionTransfer getTextTransformationDescriptionTransfer(UserVisit userVisit, TextTransformationDescription textTransformationDescription) {
        return getCoreTransferCaches(userVisit).getTextTransformationDescriptionTransferCache().getTextTransformationDescriptionTransfer(textTransformationDescription);
    }

    public List<TextTransformationDescriptionTransfer> getTextTransformationDescriptionTransfersByTextTransformation(UserVisit userVisit, TextTransformation textTransformation) {
        List<TextTransformationDescription> textTransformationDescriptions = getTextTransformationDescriptionsByTextTransformation(textTransformation);
        List<TextTransformationDescriptionTransfer> textTransformationDescriptionTransfers = new ArrayList<>(textTransformationDescriptions.size());
        TextTransformationDescriptionTransferCache textTransformationDescriptionTransferCache = getCoreTransferCaches(userVisit).getTextTransformationDescriptionTransferCache();

        textTransformationDescriptions.stream().forEach((textTransformationDescription) -> {
            textTransformationDescriptionTransfers.add(textTransformationDescriptionTransferCache.getTextTransformationDescriptionTransfer(textTransformationDescription));
        });

        return textTransformationDescriptionTransfers;
    }

    public void updateTextTransformationDescriptionFromValue(TextTransformationDescriptionValue textTransformationDescriptionValue, BasePK updatedBy) {
        if(textTransformationDescriptionValue.hasBeenModified()) {
            TextTransformationDescription textTransformationDescription = TextTransformationDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    textTransformationDescriptionValue.getPrimaryKey());

            textTransformationDescription.setThruTime(session.START_TIME_LONG);
            textTransformationDescription.store();

            TextTransformation textTransformation = textTransformationDescription.getTextTransformation();
            Language language = textTransformationDescription.getLanguage();
            String description = textTransformationDescriptionValue.getDescription();

            textTransformationDescription = TextTransformationDescriptionFactory.getInstance().create(textTransformation, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(textTransformation.getPrimaryKey(), EventTypes.MODIFY.name(), textTransformationDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteTextTransformationDescription(TextTransformationDescription textTransformationDescription, BasePK deletedBy) {
        textTransformationDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(textTransformationDescription.getTextTransformationPK(), EventTypes.MODIFY.name(), textTransformationDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteTextTransformationDescriptionsByTextTransformation(TextTransformation textTransformation, BasePK deletedBy) {
        List<TextTransformationDescription> textTransformationDescriptions = getTextTransformationDescriptionsByTextTransformationForUpdate(textTransformation);

        textTransformationDescriptions.stream().forEach((textTransformationDescription) -> {
            deleteTextTransformationDescription(textTransformationDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------

    public Appearance createAppearance(String appearanceName, Color textColor, Color backgroundColor, FontStyle fontStyle, FontWeight fontWeight,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Appearance defaultAppearance = getDefaultAppearance();
        boolean defaultFound = defaultAppearance != null;

        if(defaultFound && isDefault) {
            AppearanceDetailValue defaultAppearanceDetailValue = getDefaultAppearanceDetailValueForUpdate();

            defaultAppearanceDetailValue.setIsDefault(Boolean.FALSE);
            updateAppearanceFromValue(defaultAppearanceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Appearance appearance = AppearanceFactory.getInstance().create();
        AppearanceDetail appearanceDetail = AppearanceDetailFactory.getInstance().create(appearance, appearanceName, textColor, backgroundColor, fontStyle,
                fontWeight, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        appearance = AppearanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, appearance.getPrimaryKey());
        appearance.setActiveDetail(appearanceDetail);
        appearance.setLastDetail(appearanceDetail);
        appearance.store();

        sendEventUsingNames(appearance.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return appearance;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Appearance */
    public Appearance getAppearanceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new AppearancePK(entityInstance.getEntityUniqueId());
        var appearance = AppearanceFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return appearance;
    }

    public Appearance getAppearanceByEntityInstance(EntityInstance entityInstance) {
        return getAppearanceByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Appearance getAppearanceByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getAppearanceByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearanceByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                "AND apprncdt_appearancename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                "AND apprncdt_appearancename = ? " +
                "FOR UPDATE");
        getAppearanceByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public Appearance getAppearanceByName(String appearanceName, EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntityFromQuery(entityPermission, getAppearanceByNameQueries, appearanceName);
    }

    public Appearance getAppearanceByName(String appearanceName) {
        return getAppearanceByName(appearanceName, EntityPermission.READ_ONLY);
    }

    public Appearance getAppearanceByNameForUpdate(String appearanceName) {
        return getAppearanceByName(appearanceName, EntityPermission.READ_WRITE);
    }

    public AppearanceDetailValue getAppearanceDetailValueForUpdate(Appearance appearance) {
        return appearance == null? null: appearance.getLastDetailForUpdate().getAppearanceDetailValue().clone();
    }

    public AppearanceDetailValue getAppearanceDetailValueByNameForUpdate(String appearanceName) {
        return getAppearanceDetailValueForUpdate(getAppearanceByNameForUpdate(appearanceName));
    }

    private static final Map<EntityPermission, String> getDefaultAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                "AND apprncdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                "AND apprncdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private Appearance getDefaultAppearance(EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultAppearanceQueries);
    }

    public Appearance getDefaultAppearance() {
        return getDefaultAppearance(EntityPermission.READ_ONLY);
    }

    public Appearance getDefaultAppearanceForUpdate() {
        return getDefaultAppearance(EntityPermission.READ_WRITE);
    }

    public AppearanceDetailValue getDefaultAppearanceDetailValueForUpdate() {
        return getDefaultAppearanceForUpdate().getLastDetailForUpdate().getAppearanceDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getAppearancesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                "ORDER BY apprncdt_sortorder, apprncdt_appearancename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                "FOR UPDATE");
        getAppearancesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Appearance> getAppearances(EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearancesQueries);
    }

    public List<Appearance> getAppearances() {
        return getAppearances(EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesForUpdate() {
        return getAppearances(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearancesByTextColorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_textcolorid = ? " +
                "ORDER BY apprncdt_sortorder, apprncdt_appearancename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_textcolorid = ? " +
                "FOR UPDATE");
        getAppearancesByTextColorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Appearance> getAppearancesByTextColor(Color textColor, EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearancesByTextColorQueries,
                textColor);
    }

    public List<Appearance> getAppearancesByTextColor(Color textColor) {
        return getAppearancesByTextColor(textColor, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByTextColorForUpdate(Color textColor) {
        return getAppearancesByTextColor(textColor, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearancesByBackgroundColorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_backgroundcolorid = ? " +
                "ORDER BY apprncdt_sortorder, apprncdt_appearancename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_backgroundcolorid = ? " +
                "FOR UPDATE");
        getAppearancesByBackgroundColorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Appearance> getAppearancesByBackgroundColor(Color backgroundColor, EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearancesByBackgroundColorQueries,
                backgroundColor);
    }

    public List<Appearance> getAppearancesByBackgroundColor(Color backgroundColor) {
        return getAppearancesByBackgroundColor(backgroundColor, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByBackgroundColorForUpdate(Color backgroundColor) {
        return getAppearancesByBackgroundColor(backgroundColor, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearancesByFontStyleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_fntstyl_fontstyleid = ? " +
                "ORDER BY apprncdt_sortorder, apprncdt_appearancename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_fntstyl_fontstyleid = ? " +
                "FOR UPDATE");
        getAppearancesByFontStyleQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Appearance> getAppearancesByFontStyle(FontStyle fontStyle, EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearancesByFontStyleQueries,
                fontStyle);
    }

    public List<Appearance> getAppearancesByFontStyle(FontStyle fontStyle) {
        return getAppearancesByFontStyle(fontStyle, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByFontStyleForUpdate(FontStyle fontStyle) {
        return getAppearancesByFontStyle(fontStyle, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearancesByFontWeightQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_fntwght_fontweightid = ? " +
                "ORDER BY apprncdt_sortorder, apprncdt_appearancename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM appearances, appearancedetails " +
                "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_fntwght_fontweightid = ? " +
                "FOR UPDATE");
        getAppearancesByFontWeightQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Appearance> getAppearancesByFontWeight(FontWeight fontWeight, EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearancesByFontWeightQueries,
                fontWeight);
    }

    public List<Appearance> getAppearancesByFontWeight(FontWeight fontWeight) {
        return getAppearancesByFontWeight(fontWeight, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByFontWeightForUpdate(FontWeight fontWeight) {
        return getAppearancesByFontWeight(fontWeight, EntityPermission.READ_WRITE);
    }

   public AppearanceTransfer getAppearanceTransfer(UserVisit userVisit, Appearance appearance) {
        return getCoreTransferCaches(userVisit).getAppearanceTransferCache().getAppearanceTransfer(appearance);
    }

    public List<AppearanceTransfer> getAppearanceTransfers(UserVisit userVisit, Collection<Appearance> appearances) {
        List<AppearanceTransfer> appearanceTransfers = new ArrayList<>(appearances.size());
        AppearanceTransferCache appearanceTransferCache = getCoreTransferCaches(userVisit).getAppearanceTransferCache();

        appearances.stream().forEach((appearance) -> {
            appearanceTransfers.add(appearanceTransferCache.getAppearanceTransfer(appearance));
        });

        return appearanceTransfers;
    }

    public List<AppearanceTransfer> getAppearanceTransfers(UserVisit userVisit) {
        return getAppearanceTransfers(userVisit, getAppearances());
    }

    public AppearanceChoicesBean getAppearanceChoices(String defaultAppearanceChoice, Language language, boolean allowNullChoice) {
        List<Appearance> appearances = getAppearances();
        int size = appearances.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultAppearanceChoice == null) {
                defaultValue = "";
            }
        }

        for(Appearance appearance: appearances) {
            AppearanceDetail appearanceDetail = appearance.getLastDetail();

            String label = getBestAppearanceDescription(appearance, language);
            String value = appearanceDetail.getAppearanceName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultAppearanceChoice == null? false: defaultAppearanceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && appearanceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new AppearanceChoicesBean(labels, values, defaultValue);
    }

    private void updateAppearanceFromValue(AppearanceDetailValue appearanceDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(appearanceDetailValue.hasBeenModified()) {
            Appearance appearance = AppearanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     appearanceDetailValue.getAppearancePK());
            AppearanceDetail appearanceDetail = appearance.getActiveDetailForUpdate();

            appearanceDetail.setThruTime(session.START_TIME_LONG);
            appearanceDetail.store();

            AppearancePK appearancePK = appearanceDetail.getAppearancePK(); // Not updated
            String appearanceName = appearanceDetailValue.getAppearanceName();
            ColorPK textColorPK = appearanceDetailValue.getTextColorPK();
            ColorPK backgroundColorPK = appearanceDetailValue.getBackgroundColorPK();
            FontStylePK fontStylePK = appearanceDetailValue.getFontStylePK();
            FontWeightPK fontWeightPK = appearanceDetailValue.getFontWeightPK();
            Boolean isDefault = appearanceDetailValue.getIsDefault();
            Integer sortOrder = appearanceDetailValue.getSortOrder();

            if(checkDefault) {
                Appearance defaultAppearance = getDefaultAppearance();
                boolean defaultFound = defaultAppearance != null && !defaultAppearance.equals(appearance);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    AppearanceDetailValue defaultAppearanceDetailValue = getDefaultAppearanceDetailValueForUpdate();

                    defaultAppearanceDetailValue.setIsDefault(Boolean.FALSE);
                    updateAppearanceFromValue(defaultAppearanceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            appearanceDetail = AppearanceDetailFactory.getInstance().create(appearancePK, appearanceName, textColorPK, backgroundColorPK, fontStylePK,
                    fontWeightPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            appearance.setActiveDetail(appearanceDetail);
            appearance.setLastDetail(appearanceDetail);

            sendEventUsingNames(appearancePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateAppearanceFromValue(AppearanceDetailValue appearanceDetailValue, BasePK updatedBy) {
        updateAppearanceFromValue(appearanceDetailValue, true, updatedBy);
    }

    private void deleteAppearance(Appearance appearance, boolean checkDefault, BasePK deletedBy) {
        AppearanceDetail appearanceDetail = appearance.getLastDetailForUpdate();

        deleteAppearanceTextDecorationsByAppearance(appearance, deletedBy);
        deleteAppearanceTextTransformationsByAppearance(appearance, deletedBy);
        deleteAppearanceDescriptionsByAppearance(appearance, deletedBy);

        appearanceDetail.setThruTime(session.START_TIME_LONG);
        appearance.setActiveDetail(null);
        appearance.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            Appearance defaultAppearance = getDefaultAppearance();

            if(defaultAppearance == null) {
                List<Appearance> appearances = getAppearancesForUpdate();

                if(!appearances.isEmpty()) {
                    Iterator<Appearance> iter = appearances.iterator();
                    if(iter.hasNext()) {
                        defaultAppearance = iter.next();
                    }
                    AppearanceDetailValue appearanceDetailValue = defaultAppearance.getLastDetailForUpdate().getAppearanceDetailValue().clone();

                    appearanceDetailValue.setIsDefault(Boolean.TRUE);
                    updateAppearanceFromValue(appearanceDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(appearance.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteAppearance(Appearance appearance, BasePK deletedBy) {
        deleteAppearance(appearance, true, deletedBy);
    }

    private void deleteAppearances(List<Appearance> appearances, boolean checkDefault, BasePK deletedBy) {
        appearances.stream().forEach((appearance) -> {
            deleteAppearance(appearance, checkDefault, deletedBy);
        });
    }

    public void deleteAppearances(List<Appearance> appearances, BasePK deletedBy) {
        deleteAppearances(appearances, true, deletedBy);
    }

    public void deleteAppearancesByTextColor(Color textColor, BasePK deletedBy) {
        deleteAppearances(getAppearancesByTextColorForUpdate(textColor), deletedBy);
    }
    
    public void deleteAppearancesByBackgroundColor(Color backgroundColor, BasePK deletedBy) {
        deleteAppearances(getAppearancesByBackgroundColorForUpdate(backgroundColor), deletedBy);
    }
    
    public void deleteAppearancesByColor(Color color, BasePK deletedBy) {
        deleteAppearancesByTextColor(color, deletedBy);
        deleteAppearancesByBackgroundColor(color, deletedBy);
    }
    
    public void deleteAppearancesByFontStyle(FontStyle fontStyle, BasePK deletedBy) {
        deleteAppearances(getAppearancesByFontStyleForUpdate(fontStyle), deletedBy);
    }
    
    public void deleteAppearancesByFontWeight(FontWeight fontWeight, BasePK deletedBy) {
        deleteAppearances(getAppearancesByFontWeightForUpdate(fontWeight), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Appearance Descriptions
    // --------------------------------------------------------------------------------

    public AppearanceDescription createAppearanceDescription(Appearance appearance, Language language, String description, BasePK createdBy) {
        AppearanceDescription appearanceDescription = AppearanceDescriptionFactory.getInstance().create(appearance, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(appearance.getPrimaryKey(), EventTypes.MODIFY.name(), appearanceDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return appearanceDescription;
    }

    private static final Map<EntityPermission, String> getAppearanceDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM appearancedescriptions " +
                "WHERE apprncd_apprnc_appearanceid = ? AND apprncd_lang_languageid = ? AND apprncd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM appearancedescriptions " +
                "WHERE apprncd_apprnc_appearanceid = ? AND apprncd_lang_languageid = ? AND apprncd_thrutime = ? " +
                "FOR UPDATE");
        getAppearanceDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private AppearanceDescription getAppearanceDescription(Appearance appearance, Language language, EntityPermission entityPermission) {
        return AppearanceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getAppearanceDescriptionQueries,
                appearance, language, Session.MAX_TIME);
    }

    public AppearanceDescription getAppearanceDescription(Appearance appearance, Language language) {
        return getAppearanceDescription(appearance, language, EntityPermission.READ_ONLY);
    }

    public AppearanceDescription getAppearanceDescriptionForUpdate(Appearance appearance, Language language) {
        return getAppearanceDescription(appearance, language, EntityPermission.READ_WRITE);
    }

    public AppearanceDescriptionValue getAppearanceDescriptionValue(AppearanceDescription appearanceDescription) {
        return appearanceDescription == null? null: appearanceDescription.getAppearanceDescriptionValue().clone();
    }

    public AppearanceDescriptionValue getAppearanceDescriptionValueForUpdate(Appearance appearance, Language language) {
        return getAppearanceDescriptionValue(getAppearanceDescriptionForUpdate(appearance, language));
    }

    private static final Map<EntityPermission, String> getAppearanceDescriptionsByAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM appearancedescriptions, languages " +
                "WHERE apprncd_apprnc_appearanceid = ? AND apprncd_thrutime = ? AND apprncd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM appearancedescriptions " +
                "WHERE apprncd_apprnc_appearanceid = ? AND apprncd_thrutime = ? " +
                "FOR UPDATE");
        getAppearanceDescriptionsByAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AppearanceDescription> getAppearanceDescriptionsByAppearance(Appearance appearance, EntityPermission entityPermission) {
        return AppearanceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearanceDescriptionsByAppearanceQueries,
                appearance, Session.MAX_TIME);
    }

    public List<AppearanceDescription> getAppearanceDescriptionsByAppearance(Appearance appearance) {
        return getAppearanceDescriptionsByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<AppearanceDescription> getAppearanceDescriptionsByAppearanceForUpdate(Appearance appearance) {
        return getAppearanceDescriptionsByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    public String getBestAppearanceDescription(Appearance appearance, Language language) {
        String description;
        AppearanceDescription appearanceDescription = getAppearanceDescription(appearance, language);

        if(appearanceDescription == null && !language.getIsDefault()) {
            appearanceDescription = getAppearanceDescription(appearance, getPartyControl().getDefaultLanguage());
        }

        if(appearanceDescription == null) {
            description = appearance.getLastDetail().getAppearanceName();
        } else {
            description = appearanceDescription.getDescription();
        }

        return description;
    }

    public AppearanceDescriptionTransfer getAppearanceDescriptionTransfer(UserVisit userVisit, AppearanceDescription appearanceDescription) {
        return getCoreTransferCaches(userVisit).getAppearanceDescriptionTransferCache().getAppearanceDescriptionTransfer(appearanceDescription);
    }

    public List<AppearanceDescriptionTransfer> getAppearanceDescriptionTransfersByAppearance(UserVisit userVisit, Appearance appearance) {
        List<AppearanceDescription> appearanceDescriptions = getAppearanceDescriptionsByAppearance(appearance);
        List<AppearanceDescriptionTransfer> appearanceDescriptionTransfers = new ArrayList<>(appearanceDescriptions.size());
        AppearanceDescriptionTransferCache appearanceDescriptionTransferCache = getCoreTransferCaches(userVisit).getAppearanceDescriptionTransferCache();

        appearanceDescriptions.stream().forEach((appearanceDescription) -> {
            appearanceDescriptionTransfers.add(appearanceDescriptionTransferCache.getAppearanceDescriptionTransfer(appearanceDescription));
        });

        return appearanceDescriptionTransfers;
    }

    public void updateAppearanceDescriptionFromValue(AppearanceDescriptionValue appearanceDescriptionValue, BasePK updatedBy) {
        if(appearanceDescriptionValue.hasBeenModified()) {
            AppearanceDescription appearanceDescription = AppearanceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    appearanceDescriptionValue.getPrimaryKey());

            appearanceDescription.setThruTime(session.START_TIME_LONG);
            appearanceDescription.store();

            Appearance appearance = appearanceDescription.getAppearance();
            Language language = appearanceDescription.getLanguage();
            String description = appearanceDescriptionValue.getDescription();

            appearanceDescription = AppearanceDescriptionFactory.getInstance().create(appearance, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(appearance.getPrimaryKey(), EventTypes.MODIFY.name(), appearanceDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteAppearanceDescription(AppearanceDescription appearanceDescription, BasePK deletedBy) {
        appearanceDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(appearanceDescription.getAppearancePK(), EventTypes.MODIFY.name(), appearanceDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteAppearanceDescriptionsByAppearance(Appearance appearance, BasePK deletedBy) {
        List<AppearanceDescription> appearanceDescriptions = getAppearanceDescriptionsByAppearanceForUpdate(appearance);

        appearanceDescriptions.stream().forEach((appearanceDescription) -> {
            deleteAppearanceDescription(appearanceDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Appearance Text Decorations
    // --------------------------------------------------------------------------------

    public AppearanceTextDecoration createAppearanceTextDecoration(Appearance appearance, TextDecoration textDecoration, BasePK createdBy) {
        AppearanceTextDecoration appearanceTextDecoration = AppearanceTextDecorationFactory.getInstance().create(appearance, textDecoration,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(appearance.getPrimaryKey(), EventTypes.MODIFY.name(), appearanceTextDecoration.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return appearanceTextDecoration;
    }

    private static final Map<EntityPermission, String> getAppearanceTextDecorationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM appearancetextdecorations "
                + "WHERE apprntxtdcrtn_apprnc_appearanceid = ? AND apprntxtdcrtn_txtdcrtn_textdecorationid = ? AND apprntxtdcrtn_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM appearancetextdecorations "
                + "WHERE apprntxtdcrtn_apprnc_appearanceid = ? AND apprntxtdcrtn_txtdcrtn_textdecorationid = ? AND apprntxtdcrtn_thrutime = ? "
                + "FOR UPDATE");
        getAppearanceTextDecorationQueries = Collections.unmodifiableMap(queryMap);
    }

    private AppearanceTextDecoration getAppearanceTextDecoration(Appearance appearance, TextDecoration textDecoration, EntityPermission entityPermission) {
        return AppearanceTextDecorationFactory.getInstance().getEntityFromQuery(entityPermission, getAppearanceTextDecorationQueries,
                appearance, textDecoration, Session.MAX_TIME);
    }

    public AppearanceTextDecoration getAppearanceTextDecoration(Appearance appearance, TextDecoration textDecoration) {
        return getAppearanceTextDecoration(appearance, textDecoration, EntityPermission.READ_ONLY);
    }

    public AppearanceTextDecoration getAppearanceTextDecorationForUpdate(Appearance appearance, TextDecoration textDecoration) {
        return getAppearanceTextDecoration(appearance, textDecoration, EntityPermission.READ_WRITE);
    }

    public AppearanceTextDecorationValue getAppearanceTextDecorationValue(AppearanceTextDecoration appearanceTextDecoration) {
        return appearanceTextDecoration == null? null: appearanceTextDecoration.getAppearanceTextDecorationValue().clone();
    }

    public AppearanceTextDecorationValue getAppearanceTextDecorationValueForUpdate(Appearance appearance, TextDecoration textDecoration) {
        return getAppearanceTextDecorationValue(getAppearanceTextDecorationForUpdate(appearance, textDecoration));
    }

    private static final Map<EntityPermission, String> getAppearanceTextDecorationsByAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM appearancetextdecorations, textdecorations, textdecorationdetails "
                + "WHERE apprntxtdcrtn_apprnc_appearanceid = ? AND apprntxtdcrtn_thrutime = ? "
                + "AND apprntxtdcrtn_txtdcrtn_textdecorationid = txtdcrtn_textdecorationid AND txtdcrtn_lastdetailid = txtdcrtndt_textdecorationdetailid "
                + "ORDER BY txtdcrtndt_sortorder, txtdcrtndt_textdecorationname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM appearancetextdecorations "
                + "WHERE apprntxtdcrtn_apprnc_appearanceid = ? AND apprntxtdcrtn_thrutime = ? "
                + "FOR UPDATE");
        getAppearanceTextDecorationsByAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AppearanceTextDecoration> getAppearanceTextDecorationsByAppearance(Appearance appearance, EntityPermission entityPermission) {
        return AppearanceTextDecorationFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearanceTextDecorationsByAppearanceQueries,
                appearance, Session.MAX_TIME);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByAppearance(Appearance appearance) {
        return getAppearanceTextDecorationsByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByAppearanceForUpdate(Appearance appearance) {
        return getAppearanceTextDecorationsByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearanceTextDecorationsByTextDecorationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM appearancetextdecorations, appearances, appearancedetails "
                + "WHERE apprntxtdcrtn_txtdcrtn_textdecorationid = ? AND apprntxtdcrtn_thrutime = ? "
                + "AND apprntxttrns_apprnc_appearanceid = apprnc_appearanceid AND apprnc_lastdetailid = apprncdt_appearancedetailid "
                + "ORDER BY apprncdt_sortorder, apprncdt_appearancename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM appearancetextdecorations "
                + "WHERE apprncd_apprnc_appearanceid = ? AND apprntxtdcrtn_thrutime = ? "
                + "FOR UPDATE");
        getAppearanceTextDecorationsByTextDecorationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AppearanceTextDecoration> getAppearanceTextDecorationsByTextDecoration(TextDecoration textDecoration, EntityPermission entityPermission) {
        return AppearanceTextDecorationFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearanceTextDecorationsByTextDecorationQueries,
                textDecoration, Session.MAX_TIME);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByTextDecoration(TextDecoration textDecoration) {
        return getAppearanceTextDecorationsByTextDecoration(textDecoration, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByTextDecorationForUpdate(TextDecoration textDecoration) {
        return getAppearanceTextDecorationsByTextDecoration(textDecoration, EntityPermission.READ_WRITE);
    }

    public AppearanceTextDecorationTransfer getAppearanceTextDecorationTransfer(UserVisit userVisit, AppearanceTextDecoration appearanceTextDecoration) {
        return getCoreTransferCaches(userVisit).getAppearanceTextDecorationTransferCache().getAppearanceTextDecorationTransfer(appearanceTextDecoration);
    }

    public List<AppearanceTextDecorationTransfer> getAppearanceTextDecorationTransfers(UserVisit userVisit, List<AppearanceTextDecoration> appearanceTextDecorations) {
        List<AppearanceTextDecorationTransfer> appearanceTextDecorationTransfers = new ArrayList<>(appearanceTextDecorations.size());
        AppearanceTextDecorationTransferCache appearanceTextDecorationTransferCache = getCoreTransferCaches(userVisit).getAppearanceTextDecorationTransferCache();

        appearanceTextDecorations.stream().forEach((appearanceTextDecoration) -> {
            appearanceTextDecorationTransfers.add(appearanceTextDecorationTransferCache.getAppearanceTextDecorationTransfer(appearanceTextDecoration));
        });

        return appearanceTextDecorationTransfers;
    }

    public List<AppearanceTextDecorationTransfer> getAppearanceTextDecorationTransfersByAppearance(UserVisit userVisit, Appearance appearance) {
        return getAppearanceTextDecorationTransfers(userVisit, getAppearanceTextDecorationsByAppearance(appearance));
    }

    public List<AppearanceTextDecorationTransfer> getAppearanceTextDecorationTransfersByTextDecoration(UserVisit userVisit, TextDecoration textDecoration) {
        return getAppearanceTextDecorationTransfers(userVisit, getAppearanceTextDecorationsByTextDecoration(textDecoration));
    }

    public void deleteAppearanceTextDecoration(AppearanceTextDecoration appearanceTextDecoration, BasePK deletedBy) {
        appearanceTextDecoration.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(appearanceTextDecoration.getAppearancePK(), EventTypes.MODIFY.name(), appearanceTextDecoration.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteAppearanceTextDecorationsByAppearance(List<AppearanceTextDecoration> appearanceTextDecorations, BasePK deletedBy) {
        appearanceTextDecorations.stream().forEach((appearanceTextDecoration) -> {
            deleteAppearanceTextDecoration(appearanceTextDecoration, deletedBy);
        });
    }

    public void deleteAppearanceTextDecorationsByAppearance(Appearance appearance, BasePK deletedBy) {
        deleteAppearanceTextDecorationsByAppearance(getAppearanceTextDecorationsByAppearanceForUpdate(appearance), deletedBy);
    }

    public void deleteAppearanceTextDecorationsByTextDecoration(TextDecoration textDecoration, BasePK deletedBy) {
        deleteAppearanceTextDecorationsByAppearance(getAppearanceTextDecorationsByTextDecorationForUpdate(textDecoration), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Appearance Text Transformations
    // --------------------------------------------------------------------------------

    public AppearanceTextTransformation createAppearanceTextTransformation(Appearance appearance, TextTransformation textTransformation, BasePK createdBy) {
        AppearanceTextTransformation appearanceTextTransformation = AppearanceTextTransformationFactory.getInstance().create(appearance, textTransformation,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(appearance.getPrimaryKey(), EventTypes.MODIFY.name(), appearanceTextTransformation.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return appearanceTextTransformation;
    }

    private static final Map<EntityPermission, String> getAppearanceTextTransformationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM appearancetexttransformations "
                + "WHERE apprntxttrns_apprnc_appearanceid = ? AND apprntxttrns_txttrns_texttransformationid = ? AND apprntxttrns_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM appearancetexttransformations "
                + "WHERE apprntxttrns_apprnc_appearanceid = ? AND apprntxttrns_txttrns_texttransformationid = ? AND apprntxttrns_thrutime = ? "
                + "FOR UPDATE");
        getAppearanceTextTransformationQueries = Collections.unmodifiableMap(queryMap);
    }

    private AppearanceTextTransformation getAppearanceTextTransformation(Appearance appearance, TextTransformation textTransformation, EntityPermission entityPermission) {
        return AppearanceTextTransformationFactory.getInstance().getEntityFromQuery(entityPermission, getAppearanceTextTransformationQueries,
                appearance, textTransformation, Session.MAX_TIME);
    }

    public AppearanceTextTransformation getAppearanceTextTransformation(Appearance appearance, TextTransformation textTransformation) {
        return getAppearanceTextTransformation(appearance, textTransformation, EntityPermission.READ_ONLY);
    }

    public AppearanceTextTransformation getAppearanceTextTransformationForUpdate(Appearance appearance, TextTransformation textTransformation) {
        return getAppearanceTextTransformation(appearance, textTransformation, EntityPermission.READ_WRITE);
    }

    public AppearanceTextTransformationValue getAppearanceTextTransformationValue(AppearanceTextTransformation appearanceTextTransformation) {
        return appearanceTextTransformation == null? null: appearanceTextTransformation.getAppearanceTextTransformationValue().clone();
    }

    public AppearanceTextTransformationValue getAppearanceTextTransformationValueForUpdate(Appearance appearance, TextTransformation textTransformation) {
        return getAppearanceTextTransformationValue(getAppearanceTextTransformationForUpdate(appearance, textTransformation));
    }

    private static final Map<EntityPermission, String> getAppearanceTextTransformationsByAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM appearancetexttransformations, texttransformations, texttransformationdetails "
                + "WHERE apprntxttrns_apprnc_appearanceid = ? AND apprntxttrns_thrutime = ? "
                + "AND apprntxttrns_txttrns_texttransformationid = txttrns_texttransformationid AND txttrns_lastdetailid = txttrnsdt_texttransformationdetailid "
                + "ORDER BY txttrnsdt_sortorder, txttrnsdt_texttransformationname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM appearancetexttransformations "
                + "WHERE apprntxttrns_apprnc_appearanceid = ? AND apprntxttrns_thrutime = ? "
                + "FOR UPDATE");
        getAppearanceTextTransformationsByAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AppearanceTextTransformation> getAppearanceTextTransformationsByAppearance(Appearance appearance, EntityPermission entityPermission) {
        return AppearanceTextTransformationFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearanceTextTransformationsByAppearanceQueries,
                appearance, Session.MAX_TIME);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByAppearance(Appearance appearance) {
        return getAppearanceTextTransformationsByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByAppearanceForUpdate(Appearance appearance) {
        return getAppearanceTextTransformationsByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearanceTextTransformationsByTextTransformationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM appearancetexttransformations, appearances, appearancedetails "
                + "WHERE apprntxttrns_txttrns_texttransformationid = ? AND apprntxttrns_thrutime = ? "
                + "AND apprntxttrns_apprnc_appearanceid = apprnc_appearanceid AND apprnc_lastdetailid = apprncdt_appearancedetailid "
                + "ORDER BY apprncdt_sortorder, apprncdt_appearancename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM apprntxttrns_txttrns_texttransformationid "
                + "WHERE apprncd_apprnc_appearanceid = ? AND apprntxttrns_thrutime = ? "
                + "FOR UPDATE");
        getAppearanceTextTransformationsByTextTransformationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AppearanceTextTransformation> getAppearanceTextTransformationsByTextTransformation(TextTransformation textTransformation, EntityPermission entityPermission) {
        return AppearanceTextTransformationFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearanceTextTransformationsByTextTransformationQueries,
                textTransformation, Session.MAX_TIME);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByTextTransformation(TextTransformation textTransformation) {
        return getAppearanceTextTransformationsByTextTransformation(textTransformation, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByTextTransformationForUpdate(TextTransformation textTransformation) {
        return getAppearanceTextTransformationsByTextTransformation(textTransformation, EntityPermission.READ_WRITE);
    }

    public AppearanceTextTransformationTransfer getAppearanceTextTransformationTransfer(UserVisit userVisit, AppearanceTextTransformation appearanceTextTransformation) {
        return getCoreTransferCaches(userVisit).getAppearanceTextTransformationTransferCache().getAppearanceTextTransformationTransfer(appearanceTextTransformation);
    }

    public List<AppearanceTextTransformationTransfer> getAppearanceTextTransformationTransfers(UserVisit userVisit, List<AppearanceTextTransformation> appearanceTextTransformations) {
        List<AppearanceTextTransformationTransfer> appearanceTextTransformationTransfers = new ArrayList<>(appearanceTextTransformations.size());
        AppearanceTextTransformationTransferCache appearanceTextTransformationTransferCache = getCoreTransferCaches(userVisit).getAppearanceTextTransformationTransferCache();

        appearanceTextTransformations.stream().forEach((appearanceTextTransformation) -> {
            appearanceTextTransformationTransfers.add(appearanceTextTransformationTransferCache.getAppearanceTextTransformationTransfer(appearanceTextTransformation));
        });

        return appearanceTextTransformationTransfers;
    }

    public List<AppearanceTextTransformationTransfer> getAppearanceTextTransformationTransfersByAppearance(UserVisit userVisit, Appearance appearance) {
        return getAppearanceTextTransformationTransfers(userVisit, getAppearanceTextTransformationsByAppearance(appearance));
    }

    public List<AppearanceTextTransformationTransfer> getAppearanceTextTransformationTransfersByTextTransformation(UserVisit userVisit, TextTransformation textTransformation) {
        return getAppearanceTextTransformationTransfers(userVisit, getAppearanceTextTransformationsByTextTransformation(textTransformation));
    }

    public void deleteAppearanceTextTransformation(AppearanceTextTransformation appearanceTextTransformation, BasePK deletedBy) {
        appearanceTextTransformation.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(appearanceTextTransformation.getAppearancePK(), EventTypes.MODIFY.name(), appearanceTextTransformation.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteAppearanceTextTransformationsByAppearance(List<AppearanceTextTransformation> appearanceTextTransformations, BasePK deletedBy) {
        appearanceTextTransformations.stream().forEach((appearanceTextTransformation) -> {
            deleteAppearanceTextTransformation(appearanceTextTransformation, deletedBy);
        });
    }

    public void deleteAppearanceTextTransformationsByAppearance(Appearance appearance, BasePK deletedBy) {
        deleteAppearanceTextTransformationsByAppearance(getAppearanceTextTransformationsByAppearanceForUpdate(appearance), deletedBy);
    }

    public void deleteAppearanceTextTransformationsByTextTransformation(TextTransformation textTransformation, BasePK deletedBy) {
        deleteAppearanceTextTransformationsByAppearance(getAppearanceTextTransformationsByTextTransformationForUpdate(textTransformation), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Appearances
    // --------------------------------------------------------------------------------

    public EntityAppearance createEntityAppearance(EntityInstance entityInstance, Appearance appearance, BasePK createdBy) {
        EntityAppearance entityAppearance = EntityAppearanceFactory.getInstance().create(entityInstance, appearance, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAppearance.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return entityAppearance;
    }

    private static final Map<EntityPermission, String> getEntityAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM entityappearances " +
                "WHERE eniapprnc_eni_entityinstanceid = ? AND eniapprnc_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM entityappearances " +
                "WHERE eniapprnc_eni_entityinstanceid = ? AND eniapprnc_thrutime = ? " +
                "FOR UPDATE");
        getEntityAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAppearance getEntityAppearance(EntityInstance entityInstance, EntityPermission entityPermission) {
        return EntityAppearanceFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAppearanceQueries,
                entityInstance, Session.MAX_TIME);
    }

    public EntityAppearance getEntityAppearance(EntityInstance entityInstance) {
        return getEntityAppearance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAppearance getEntityAppearanceForUpdate(EntityInstance entityInstance) {
        return getEntityAppearance(entityInstance, EntityPermission.READ_WRITE);
    }

    public EntityAppearanceValue getEntityAppearanceValue(EntityAppearance entityAppearance) {
        return entityAppearance == null? null: entityAppearance.getEntityAppearanceValue().clone();
    }

    public EntityAppearanceValue getEntityAppearanceValueForUpdate(EntityInstance entityInstance) {
        return getEntityAppearanceValue(getEntityAppearanceForUpdate(entityInstance));
    }

    private static final Map<EntityPermission, String> getEntityAppearancesByAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityappearances, entityinstances, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                + "WHERE eniapprnc_apprnc_appearanceid = ? AND eniapprnc_thrutime = ? "
                + "AND eniapprnc_eni_entityinstanceid = eni_entityinstanceid "
                + "AND eni_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                + "ORDER BY cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename, eni_entityuniqueid");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityappearances "
                + "WHERE eniapprnc_apprnc_appearanceid = ? AND eniapprnc_thrutime = ? "
                + "FOR UPDATE");
        getEntityAppearancesByAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<EntityAppearance> getEntityAppearancesByAppearance(Appearance appearance, EntityPermission entityPermission) {
        return EntityAppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getEntityAppearancesByAppearanceQueries,
                appearance, Session.MAX_TIME);
    }

    public List<EntityAppearance> getEntityAppearancesByAppearance(Appearance appearance) {
        return getEntityAppearancesByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<EntityAppearance> getEntityAppearancesByAppearanceForUpdate(Appearance appearance) {
        return getEntityAppearancesByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    public EntityAppearanceTransfer getEntityAppearanceTransfer(UserVisit userVisit, EntityAppearance entityAppearance) {
        return getCoreTransferCaches(userVisit).getEntityAppearanceTransferCache().getEntityAppearanceTransfer(entityAppearance);
    }

    public List<EntityAppearanceTransfer> getEntityAppearanceTransfersByAppearance(UserVisit userVisit, Appearance appearance) {
        List<EntityAppearance> entityAppearances = getEntityAppearancesByAppearance(appearance);
        List<EntityAppearanceTransfer> entityAppearanceTransfers = new ArrayList<>(entityAppearances.size());
        EntityAppearanceTransferCache entityAppearanceTransferCache = getCoreTransferCaches(userVisit).getEntityAppearanceTransferCache();

        entityAppearances.stream().forEach((entityAppearance) -> {
            entityAppearanceTransfers.add(entityAppearanceTransferCache.getEntityAppearanceTransfer(entityAppearance));
        });

        return entityAppearanceTransfers;
    }

    public void updateEntityAppearanceFromValue(EntityAppearanceValue entityAppearanceValue, BasePK updatedBy) {
        if(entityAppearanceValue.hasBeenModified()) {
            EntityAppearance entityAppearance = EntityAppearanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAppearanceValue.getPrimaryKey());

            entityAppearance.setThruTime(session.START_TIME_LONG);
            entityAppearance.store();

            EntityInstance entityInstance = entityAppearance.getEntityInstance(); // Not updated.
            AppearancePK appearancePK = entityAppearanceValue.getAppearancePK();

            entityAppearance = EntityAppearanceFactory.getInstance().create(entityInstance.getPrimaryKey(), appearancePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(entityInstance, EventTypes.MODIFY.name(), entityAppearance.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteEntityAppearance(EntityAppearance entityAppearance, BasePK deletedBy) {
        entityAppearance.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(entityAppearance.getEntityInstance(), EventTypes.MODIFY.name(), entityAppearance.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteEntityAppearancesByAppearance(Appearance appearance, BasePK deletedBy) {
        List<EntityAppearance> entityAppearances = getEntityAppearancesByAppearanceForUpdate(appearance);

        entityAppearances.stream().forEach((entityAppearance) -> {
            deleteEntityAppearance(entityAppearance, deletedBy);
        });
    }

}
