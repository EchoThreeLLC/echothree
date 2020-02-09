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

package com.echothree.util.server.persistence;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.data.communication.common.pk.CommunicationEventPK;
import com.echothree.model.data.communication.server.factory.CommunicationEventFactory;
import com.echothree.model.data.contactlist.common.pk.PartyContactListPK;
import com.echothree.model.data.contactlist.server.factory.PartyContactListFactory;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.factory.MimeTypeFactory;
import com.echothree.model.data.forum.common.pk.ForumGroupPK;
import com.echothree.model.data.forum.common.pk.ForumMessagePK;
import com.echothree.model.data.forum.common.pk.ForumPK;
import com.echothree.model.data.forum.common.pk.ForumThreadPK;
import com.echothree.model.data.forum.server.factory.ForumFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageFactory;
import com.echothree.model.data.forum.server.factory.ForumThreadFactory;
import com.echothree.model.data.item.common.pk.ItemDescriptionPK;
import com.echothree.model.data.item.common.pk.ItemPK;
import com.echothree.model.data.item.server.factory.ItemDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.subscription.common.pk.SubscriptionPK;
import com.echothree.model.data.subscription.server.factory.SubscriptionFactory;
import com.echothree.model.data.training.common.pk.PartyTrainingClassPK;
import com.echothree.model.data.training.common.pk.TrainingClassPK;
import com.echothree.model.data.training.server.factory.PartyTrainingClassFactory;
import com.echothree.model.data.training.server.factory.TrainingClassFactory;
import com.echothree.model.data.vendor.common.pk.VendorItemPK;
import com.echothree.model.data.vendor.server.factory.VendorItemFactory;
import com.echothree.util.common.persistence.EntityNames;
import com.echothree.util.common.persistence.EntityNamesConstants;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.translator.ComponentVendorTranslator;
import com.echothree.util.server.persistence.translator.EntityInstanceAndNames;
import com.echothree.util.server.persistence.translator.EntityInstanceTranslator;
import com.echothree.util.server.persistence.translator.InvoiceNameTranslator;
import com.echothree.util.server.persistence.translator.OrderNameTranslator;
import com.echothree.util.server.persistence.translator.PartyNameTranslator;
import com.echothree.util.server.persistence.translator.SequenceTypeTranslator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EntityNamesUtils {
    
    private EntityNamesUtils() {
        super();
    }
    
    private static class EntityNamesUtilsHolder {
        static EntityNamesUtils instance = new EntityNamesUtils();
    }
    
    public static EntityNamesUtils getInstance() {
        return EntityNamesUtilsHolder.instance;
    }

    private static Map<String, ComponentVendorTranslator> componentVendorTranslators = new HashMap<>();

    public static void addComponentVendorTranslator(String componentVendorName, ComponentVendorTranslator componentVendorTranslator) {
        componentVendorTranslators.put(componentVendorName, componentVendorTranslator);
    }

    static {
        Map<String, EntityInstanceTranslator> nameTranslators = new HashMap<>(16);

        nameTranslators.put(EntityTypes.Invoice.name(), new InvoiceNameTranslator());
        nameTranslators.put(EntityTypes.Order.name(), new OrderNameTranslator());
        nameTranslators.put(EntityTypes.Party.name(), new PartyNameTranslator());

        nameTranslators.put(EntityTypes.Item.name(), (final EntityInstance entityInstance) -> {
            var itemDetail = ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ItemPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);
            
            names.put(EntityNamesConstants.Name_ItemName, itemDetail.getItemName());
            
            return new EntityNames(EntityNamesConstants.Target_Item, names);
        });

        nameTranslators.put(EntityTypes.ItemDescription.name(), (final EntityInstance entityInstance) -> {
            var itemDescriptionDetail = ItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ItemDescriptionPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(3);
            
            names.put(EntityNamesConstants.Name_ItemDescriptionTypeName, itemDescriptionDetail.getItemDescriptionType().getLastDetail().getItemDescriptionTypeName());
            names.put(EntityNamesConstants.Name_ItemName, itemDescriptionDetail.getItem().getLastDetail().getItemName());
            names.put(EntityNamesConstants.Name_LanguageIsoName, itemDescriptionDetail.getLanguage().getLanguageIsoName());
            
            return new EntityNames(EntityNamesConstants.Target_ItemDescription, names);
        });

        nameTranslators.put(EntityTypes.ForumGroup.name(), (final EntityInstance entityInstance) -> {
            var forumGroupDetail = ForumGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ForumGroupPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);
            
            names.put(EntityNamesConstants.Name_ForumGroupName, forumGroupDetail.getForumGroupName());
            
            return new EntityNames(EntityNamesConstants.Target_ForumGroup, names);
        });

        nameTranslators.put(EntityTypes.Forum.name(), (final EntityInstance entityInstance) -> {
            var forumDetail = ForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ForumPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);
            
            names.put(EntityNamesConstants.Name_ForumName, forumDetail.getForumName());
            
            return new EntityNames(EntityNamesConstants.Target_Forum, names);
        });

        nameTranslators.put(EntityTypes.ForumMessage.name(), (final EntityInstance entityInstance) -> {
            var forumMessageDetail = ForumMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ForumMessagePK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);
            
            names.put(EntityNamesConstants.Name_ForumMessageName, forumMessageDetail.getForumMessageName());
            
            return new EntityNames(EntityNamesConstants.Target_ForumMessage, names);
        });

        nameTranslators.put(EntityTypes.ForumThread.name(), (final EntityInstance entityInstance) -> {
            var forumThreadDetail = ForumThreadFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ForumThreadPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);
            
            names.put(EntityNamesConstants.Name_ForumThreadName, forumThreadDetail.getForumThreadName());
            
            return new EntityNames(EntityNamesConstants.Target_ForumThread, names);
        });

        nameTranslators.put(EntityTypes.TrainingClass.name(), (final EntityInstance entityInstance) -> {
            var trainingClassDetail = TrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new TrainingClassPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);
            
            names.put(EntityNamesConstants.Name_TrainingClassName, trainingClassDetail.getTrainingClassName());
            
            return new EntityNames(EntityNamesConstants.Target_TrainingClass, names);
        });

        nameTranslators.put(EntityTypes.PartyTrainingClass.name(), (final EntityInstance entityInstance) -> {
            var partyTrainingClassDetail = PartyTrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new PartyTrainingClassPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);
            
            names.put(EntityNamesConstants.Name_PartyTrainingClassName, partyTrainingClassDetail.getPartyTrainingClassName());
            
            return new EntityNames(EntityNamesConstants.Target_PartyTrainingClass, names);
        });
        
        nameTranslators.put(EntityTypes.CommunicationEvent.name(), (final EntityInstance entityInstance) -> {
            var communicationEventDetail = CommunicationEventFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new CommunicationEventPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);
            
            names.put(EntityNamesConstants.Name_CommunicationEventName, communicationEventDetail.getCommunicationEventName());
            
            return new EntityNames(EntityNamesConstants.Target_CommunicationEvent, names);
        });

        nameTranslators.put(EntityTypes.VendorItem.name(), (final EntityInstance entityInstance) -> {
            var vendorItemDetail = VendorItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new VendorItemPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(2);
            
            names.put(EntityNamesConstants.Name_VendorItemName, vendorItemDetail.getVendorItemName());
            names.put(EntityNamesConstants.Name_PartyName, vendorItemDetail.getVendorParty().getLastDetail().getPartyName());
            
            return new EntityNames(EntityNamesConstants.Target_VendorItem, names);
        });

        nameTranslators.put(EntityTypes.PartyContactList.name(), (final EntityInstance entityInstance) -> {
            var partyContactListDetail = PartyContactListFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new PartyContactListPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(2);
            
            names.put(EntityNamesConstants.Name_PartyName, partyContactListDetail.getParty().getLastDetail().getPartyName());
            names.put(EntityNamesConstants.Name_ContactListName, partyContactListDetail.getContactList().getLastDetail().getContactListName());
            
            return new EntityNames(EntityNamesConstants.Target_PartyContactList, names);
        });

        nameTranslators.put(EntityTypes.Subscription.name(), (final EntityInstance entityInstance) -> {
            var subscription = SubscriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new SubscriptionPK(entityInstance.getEntityUniqueId()));
            var names = new MapWrapper<String>(1);
            
            names.put(EntityNamesConstants.Name_SubscriptionName, subscription.getLastDetail().getSubscriptionName());
            
            return new EntityNames(EntityNamesConstants.Target_Subscription, names);
        });

        nameTranslators.put(EntityTypes.MimeType.name(), (final EntityInstance entityInstance) -> {
            var mimeType = MimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new MimeTypePK(entityInstance.getEntityUniqueId()));
            var names = new MapWrapper<String>(1);
            
            names.put(EntityNamesConstants.Name_MimeTypeName, mimeType.getLastDetail().getMimeTypeName());
            
            return new EntityNames(EntityNamesConstants.Target_MimeType, names);
        });

        nameTranslators = Collections.unmodifiableMap(nameTranslators);

        addComponentVendorTranslator(ComponentVendors.ECHOTHREE.name(), new ComponentVendorTranslator(nameTranslators));
    }

    public EntityInstanceAndNames getEntityNames(final EntityInstance entityInstance) {
        EntityNames result = null;
        var entityType = entityInstance.getEntityType();
        var componentVendor = entityType.getLastDetail().getComponentVendor();
        var componentVendorName = componentVendor.getLastDetail().getComponentVendorName();

        ComponentVendorTranslator componentVendorTranslator = componentVendorTranslators.get(componentVendorName);

        if(componentVendorTranslator != null) {
            EntityInstanceTranslator nameTranslator = componentVendorTranslator.getNameTranslators().get(entityType.getLastDetail().getEntityTypeName());

            if(nameTranslator != null) {
                result = nameTranslator.getNames(entityInstance);
            }
        }

        return result == null ? null : new EntityInstanceAndNames(entityInstance, result);
    }
    
    private static Map<String, SequenceTypeTranslator> sequenceTypeTranslators;
    
    static {
        var translators = new HashMap<String, SequenceTypeTranslator>(7);
        
        translators.put(SequenceConstants.SequenceType_PURCHASE_INVOICE, new InvoiceNameTranslator());
        translators.put(SequenceConstants.SequenceType_SALES_INVOICE, new InvoiceNameTranslator());
        translators.put(SequenceConstants.SequenceType_PURCHASE_ORDER, new OrderNameTranslator());
        translators.put(SequenceConstants.SequenceType_SALES_ORDER, new OrderNameTranslator());
        translators.put(SequenceConstants.SequenceType_WISHLIST, new OrderNameTranslator());
        translators.put(SequenceConstants.SequenceType_CUSTOMER, new PartyNameTranslator());
        translators.put(SequenceConstants.SequenceType_EMPLOYEE, new PartyNameTranslator());
        
        sequenceTypeTranslators = Collections.unmodifiableMap(translators);
    }
    
    public EntityInstanceAndNames getEntityNames(final Party party, final SequenceType sequenceType, final String value, final boolean includeEntityInstance) {
        EntityInstanceAndNames result = null;
        var sequenceTypeName = sequenceType.getLastDetail().getSequenceTypeName();
        var sequenceTypeTranslator = sequenceTypeTranslators.get(sequenceTypeName);
        
        if(sequenceTypeTranslator != null) {
            result = sequenceTypeTranslator.getNames(sequenceTypeName, value, includeEntityInstance);
        }
        
        return result;
    }
    
    public EntityInstanceAndNames getEntityNames(final Party party, final String value, final boolean includeEntityInstance) {
        var sequenceType = SequenceTypeLogic.getInstance().identifySequenceType(value);
        
        return sequenceType == null ? null : getEntityNames(party, sequenceType, value, includeEntityInstance);
    }
    
}
