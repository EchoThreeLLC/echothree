// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.content.server.control;

import com.echothree.model.control.content.common.ContentCategories;
import com.echothree.model.control.content.common.ContentSections;
import com.echothree.model.control.content.common.choice.ContentCatalogChoicesBean;
import com.echothree.model.control.content.common.choice.ContentCategoryChoicesBean;
import com.echothree.model.control.content.common.choice.ContentCollectionChoicesBean;
import com.echothree.model.control.content.common.choice.ContentPageAreaTypeChoicesBean;
import com.echothree.model.control.content.common.choice.ContentPageLayoutChoicesBean;
import com.echothree.model.control.content.common.choice.ContentSectionChoicesBean;
import com.echothree.model.control.content.common.choice.ContentWebAddressChoicesBean;
import com.echothree.model.control.content.common.transfer.ContentCatalogDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentCatalogItemTransfer;
import com.echothree.model.control.content.common.transfer.ContentCatalogTransfer;
import com.echothree.model.control.content.common.transfer.ContentCategoryDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentCategoryItemTransfer;
import com.echothree.model.control.content.common.transfer.ContentCategoryTransfer;
import com.echothree.model.control.content.common.transfer.ContentCollectionDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentCollectionTransfer;
import com.echothree.model.control.content.common.transfer.ContentForumTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageAreaTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageAreaTypeTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageLayoutAreaTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageLayoutDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageLayoutTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageTransfer;
import com.echothree.model.control.content.common.transfer.ContentSectionDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentSectionTransfer;
import com.echothree.model.control.content.common.transfer.ContentWebAddressDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentWebAddressTransfer;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.content.server.transfer.ContentCatalogDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCatalogItemTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCatalogTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCategoryDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCategoryItemTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCategoryTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCollectionDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCollectionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentForumTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageAreaTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageAreaTypeTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageLayoutAreaTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageLayoutDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageLayoutTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageTransferCache;
import com.echothree.model.control.content.server.transfer.ContentSectionDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentSectionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentTransferCaches;
import com.echothree.model.control.content.server.transfer.ContentWebAddressDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentWebAddressTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.content.common.pk.ContentCatalogItemFixedPricePK;
import com.echothree.model.data.content.common.pk.ContentCatalogItemPK;
import com.echothree.model.data.content.common.pk.ContentCatalogItemVariablePricePK;
import com.echothree.model.data.content.common.pk.ContentCatalogPK;
import com.echothree.model.data.content.common.pk.ContentCategoryPK;
import com.echothree.model.data.content.common.pk.ContentCollectionPK;
import com.echothree.model.data.content.common.pk.ContentForumPK;
import com.echothree.model.data.content.common.pk.ContentPageAreaPK;
import com.echothree.model.data.content.common.pk.ContentPageAreaTypePK;
import com.echothree.model.data.content.common.pk.ContentPageLayoutAreaPK;
import com.echothree.model.data.content.common.pk.ContentPageLayoutPK;
import com.echothree.model.data.content.common.pk.ContentPagePK;
import com.echothree.model.data.content.common.pk.ContentSectionPK;
import com.echothree.model.data.content.common.pk.ContentWebAddressPK;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogDescription;
import com.echothree.model.data.content.server.entity.ContentCatalogDetail;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCatalogItemFixedPrice;
import com.echothree.model.data.content.server.entity.ContentCatalogItemVariablePrice;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDescription;
import com.echothree.model.data.content.server.entity.ContentCategoryDetail;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentCollectionDescription;
import com.echothree.model.data.content.server.entity.ContentCollectionDetail;
import com.echothree.model.data.content.server.entity.ContentForum;
import com.echothree.model.data.content.server.entity.ContentForumDetail;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageArea;
import com.echothree.model.data.content.server.entity.ContentPageAreaBlob;
import com.echothree.model.data.content.server.entity.ContentPageAreaClob;
import com.echothree.model.data.content.server.entity.ContentPageAreaDetail;
import com.echothree.model.data.content.server.entity.ContentPageAreaString;
import com.echothree.model.data.content.server.entity.ContentPageAreaType;
import com.echothree.model.data.content.server.entity.ContentPageAreaTypeDescription;
import com.echothree.model.data.content.server.entity.ContentPageAreaUrl;
import com.echothree.model.data.content.server.entity.ContentPageDescription;
import com.echothree.model.data.content.server.entity.ContentPageDetail;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.content.server.entity.ContentPageLayoutAreaDescription;
import com.echothree.model.data.content.server.entity.ContentPageLayoutDescription;
import com.echothree.model.data.content.server.entity.ContentPageLayoutDetail;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.entity.ContentSectionDescription;
import com.echothree.model.data.content.server.entity.ContentSectionDetail;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.content.server.entity.ContentWebAddressDescription;
import com.echothree.model.data.content.server.entity.ContentWebAddressDetail;
import com.echothree.model.data.content.server.entity.ContentWebAddressServer;
import com.echothree.model.data.content.server.factory.ContentCatalogDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentCatalogDetailFactory;
import com.echothree.model.data.content.server.factory.ContentCatalogFactory;
import com.echothree.model.data.content.server.factory.ContentCatalogItemFactory;
import com.echothree.model.data.content.server.factory.ContentCatalogItemFixedPriceFactory;
import com.echothree.model.data.content.server.factory.ContentCatalogItemVariablePriceFactory;
import com.echothree.model.data.content.server.factory.ContentCategoryDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentCategoryDetailFactory;
import com.echothree.model.data.content.server.factory.ContentCategoryFactory;
import com.echothree.model.data.content.server.factory.ContentCategoryItemFactory;
import com.echothree.model.data.content.server.factory.ContentCollectionDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentCollectionDetailFactory;
import com.echothree.model.data.content.server.factory.ContentCollectionFactory;
import com.echothree.model.data.content.server.factory.ContentForumDetailFactory;
import com.echothree.model.data.content.server.factory.ContentForumFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaBlobFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaClobFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaDetailFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaStringFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaTypeDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaTypeFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaUrlFactory;
import com.echothree.model.data.content.server.factory.ContentPageDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentPageDetailFactory;
import com.echothree.model.data.content.server.factory.ContentPageFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutAreaDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutAreaFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutDetailFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutFactory;
import com.echothree.model.data.content.server.factory.ContentSectionDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentSectionDetailFactory;
import com.echothree.model.data.content.server.factory.ContentSectionFactory;
import com.echothree.model.data.content.server.factory.ContentWebAddressDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentWebAddressDetailFactory;
import com.echothree.model.data.content.server.factory.ContentWebAddressFactory;
import com.echothree.model.data.content.server.factory.ContentWebAddressServerFactory;
import com.echothree.model.data.content.server.value.ContentCatalogDescriptionValue;
import com.echothree.model.data.content.server.value.ContentCatalogDetailValue;
import com.echothree.model.data.content.server.value.ContentCatalogItemFixedPriceValue;
import com.echothree.model.data.content.server.value.ContentCatalogItemVariablePriceValue;
import com.echothree.model.data.content.server.value.ContentCategoryDescriptionValue;
import com.echothree.model.data.content.server.value.ContentCategoryDetailValue;
import com.echothree.model.data.content.server.value.ContentCategoryItemValue;
import com.echothree.model.data.content.server.value.ContentCollectionDescriptionValue;
import com.echothree.model.data.content.server.value.ContentCollectionDetailValue;
import com.echothree.model.data.content.server.value.ContentForumDetailValue;
import com.echothree.model.data.content.server.value.ContentPageAreaDetailValue;
import com.echothree.model.data.content.server.value.ContentPageDescriptionValue;
import com.echothree.model.data.content.server.value.ContentPageDetailValue;
import com.echothree.model.data.content.server.value.ContentPageLayoutDescriptionValue;
import com.echothree.model.data.content.server.value.ContentPageLayoutDetailValue;
import com.echothree.model.data.content.server.value.ContentSectionDescriptionValue;
import com.echothree.model.data.content.server.value.ContentSectionDetailValue;
import com.echothree.model.data.content.server.value.ContentWebAddressDescriptionValue;
import com.echothree.model.data.content.server.value.ContentWebAddressDetailValue;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.forum.common.pk.ForumPK;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.common.pk.OfferUsePK;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
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
import java.util.Objects;
import java.util.Set;

public class ContentControl
        extends BaseModelControl {
    
    /** Creates a new instance of ContentControl */
    public ContentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ContentTransferCaches contentTransferCaches;
    
    public ContentTransferCaches getContentTransferCaches(UserVisit userVisit) {
        if(contentTransferCaches == null) {
            contentTransferCaches = new ContentTransferCaches(userVisit, this);
        }
        
        return contentTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Types
    // --------------------------------------------------------------------------------
    
    public ContentPageAreaType createContentPageAreaType(String contentPageAreaTypeName, BasePK createdBy) {
        ContentPageAreaType contentPageAreaType = ContentPageAreaTypeFactory.getInstance().create(contentPageAreaTypeName);
        
        sendEventUsingNames(contentPageAreaType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return contentPageAreaType;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.ContentPageAreaType */
    public ContentPageAreaType getContentPageAreaTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentPageAreaTypePK(entityInstance.getEntityUniqueId());

        return ContentPageAreaTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }
    
    public ContentPageAreaType getContentPageAreaTypeByEntityInstance(EntityInstance entityInstance) {
        return getContentPageAreaTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentPageAreaType getContentPageAreaTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentPageAreaTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getContentPageAreaTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contentpageareatypes " +
                "WHERE cntpat_contentpageareatypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contentpageareatypes " +
                "WHERE cntpat_contentpageareatypename = ? " +
                "FOR UPDATE");
        getContentPageAreaTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public ContentPageAreaType getContentPageAreaTypeByName(String contentPageAreaTypeName, EntityPermission entityPermission) {
        return ContentPageAreaTypeFactory.getInstance().getEntityFromQuery(entityPermission, getContentPageAreaTypeByNameQueries,
                contentPageAreaTypeName);
    }
    
    public ContentPageAreaType getContentPageAreaTypeByName(String contentPageAreaTypeName) {
        return getContentPageAreaTypeByName(contentPageAreaTypeName, EntityPermission.READ_ONLY);
    }
    
    public ContentPageAreaType getContentPageAreaTypeByNameForUpdate(String contentPageAreaTypeName) {
        return getContentPageAreaTypeByName(contentPageAreaTypeName, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getContentPageAreaTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contentpageareatypes " +
                "ORDER BY cntpat_contentpageareatypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contentpageareatypes " +
                "FOR UPDATE");
        getContentPageAreaTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContentPageAreaType> getContentPageAreaTypes(EntityPermission entityPermission) {
        return ContentPageAreaTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getContentPageAreaTypesQueries);
    }
    
    public List<ContentPageAreaType> getContentPageAreaTypes() {
        return getContentPageAreaTypes(EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageAreaType> getContentPageAreaTypesForUpdate() {
        return getContentPageAreaTypes(EntityPermission.READ_WRITE);
    }
    
    public ContentPageAreaTypeTransfer getContentPageAreaTypeTransfer(UserVisit userVisit, ContentPageAreaType contentPageAreaType) {
        return getContentTransferCaches(userVisit).getContentPageAreaTypeTransferCache().getTransfer(contentPageAreaType);
    }
    
    public List<ContentPageAreaTypeTransfer> getContentPageAreaTypeTransfers(UserVisit userVisit, Collection<ContentPageAreaType> contentPageAreaTypes) {
        List<ContentPageAreaTypeTransfer> contentPageAreaTypeTransfers = new ArrayList<>(contentPageAreaTypes.size());
        ContentPageAreaTypeTransferCache contentPageAreaTypeTransferCache = getContentTransferCaches(userVisit).getContentPageAreaTypeTransferCache();
        
        contentPageAreaTypes.forEach((contentPageAreaType) ->
                contentPageAreaTypeTransfers.add(contentPageAreaTypeTransferCache.getTransfer(contentPageAreaType))
        );
        
        return contentPageAreaTypeTransfers;
    }
    
    public List<ContentPageAreaTypeTransfer> getContentPageAreaTypeTransfers(UserVisit userVisit) {
        return getContentPageAreaTypeTransfers(userVisit, getContentPageAreaTypes());
    }
    
    public ContentPageAreaTypeChoicesBean getContentPageAreaTypeChoices(String defaultContentPageAreaTypeChoice, Language language,
            boolean allowNullChoice) {
        List<ContentPageAreaType> contentPageAreaTypes = getContentPageAreaTypes();
        var size = contentPageAreaTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultContentPageAreaTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var contentPageAreaType : contentPageAreaTypes) {
            var label = getBestContentPageAreaTypeDescription(contentPageAreaType, language);
            var value = contentPageAreaType.getContentPageAreaTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContentPageAreaTypeChoice == null ? false : defaultContentPageAreaTypeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new ContentPageAreaTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ContentPageAreaTypeDescription createContentPageAreaTypeDescription(ContentPageAreaType contentPageAreaType, Language language,
            String description, BasePK createdBy) {
        ContentPageAreaTypeDescription contentPageAreaTypeDescription = ContentPageAreaTypeDescriptionFactory.getInstance().create(contentPageAreaType,
                language, description);
        
        sendEventUsingNames(contentPageAreaType.getPrimaryKey(), EventTypes.MODIFY, contentPageAreaTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentPageAreaTypeDescription;
    }
    
    public ContentPageAreaTypeDescription getContentPageAreaTypeDescription(ContentPageAreaType contentPageAreaType, Language language) {
        ContentPageAreaTypeDescription contentPageAreaTypeDescription;
        
        try {
            PreparedStatement ps = ContentPageAreaTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contentpageareatypedescriptions " +
                    "WHERE cntpatd_cntpat_contentpageareatypeid = ? AND cntpatd_lang_languageid = ?");
            
            ps.setLong(1, contentPageAreaType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            contentPageAreaTypeDescription = ContentPageAreaTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageAreaTypeDescription;
    }
    
    public String getBestContentPageAreaTypeDescription(ContentPageAreaType contentPageAreaType, Language language) {
        String description;
        ContentPageAreaTypeDescription contentPageAreaTypeDescription = getContentPageAreaTypeDescription(contentPageAreaType, language);
        
        if(contentPageAreaTypeDescription == null && !language.getIsDefault()) {
            contentPageAreaTypeDescription = getContentPageAreaTypeDescription(contentPageAreaType, getPartyControl().getDefaultLanguage());
        }
        
        if(contentPageAreaTypeDescription == null) {
            description = contentPageAreaType.getContentPageAreaTypeName();
        } else {
            description = contentPageAreaTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layouts
    // --------------------------------------------------------------------------------
    
    public ContentPageLayout createContentPageLayout(String contentPageLayoutName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ContentPageLayout defaultContentPageLayout = getDefaultContentPageLayout();
        boolean defaultFound = defaultContentPageLayout != null;
        
        if(defaultFound && isDefault) {
            ContentPageLayoutDetailValue defaultContentPageLayoutDetailValue = getDefaultContentPageLayoutDetailValueForUpdate();
            
            defaultContentPageLayoutDetailValue.setIsDefault(Boolean.FALSE);
            updateContentPageLayoutFromValue(defaultContentPageLayoutDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ContentPageLayout contentPageLayout = ContentPageLayoutFactory.getInstance().create();
        ContentPageLayoutDetail contentPageLayoutDetail = ContentPageLayoutDetailFactory.getInstance().create(session,
                contentPageLayout, contentPageLayoutName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contentPageLayout = ContentPageLayoutFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentPageLayout.getPrimaryKey());
        contentPageLayout.setActiveDetail(contentPageLayoutDetail);
        contentPageLayout.setLastDetail(contentPageLayoutDetail);
        contentPageLayout.store();
        
        sendEventUsingNames(contentPageLayout.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentPageLayout;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.ContentPageLayout */
    public ContentPageLayout getContentPageLayoutByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentPageLayoutPK(entityInstance.getEntityUniqueId());

        return ContentPageLayoutFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ContentPageLayout getContentPageLayoutByEntityInstance(EntityInstance entityInstance) {
        return getContentPageLayoutByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentPageLayout getContentPageLayoutByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentPageLayoutByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getContentPageLayoutByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contentpagelayouts, contentpagelayoutdetails " +
                "WHERE cntpl_contentpagelayoutid = cntpldt_cntpl_contentpagelayoutid AND cntpldt_contentpagelayoutname = ? AND cntpldt_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contentpagelayouts, contentpagelayoutdetails " +
                "WHERE cntpl_contentpagelayoutid = cntpldt_cntpl_contentpagelayoutid AND cntpldt_contentpagelayoutname = ? AND cntpldt_thrutime = ? " +
                "FOR UPDATE");
        getContentPageLayoutByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public ContentPageLayout getContentPageLayoutByName(String contentPageLayoutName, EntityPermission entityPermission) {
        return ContentPageLayoutFactory.getInstance().getEntityFromQuery(entityPermission, getContentPageLayoutByNameQueries,
                contentPageLayoutName, Session.MAX_TIME);
    }
    
    public ContentPageLayout getContentPageLayoutByName(String contentPageLayoutName) {
        return getContentPageLayoutByName(contentPageLayoutName, EntityPermission.READ_ONLY);
    }
    
    public ContentPageLayout getContentPageLayoutByNameForUpdate(String contentPageLayoutName) {
        return getContentPageLayoutByName(contentPageLayoutName, EntityPermission.READ_WRITE);
    }
    
    public ContentPageLayoutDetailValue getContentPageLayoutDetailValueForUpdate(ContentPageLayout contentPageLayout) {
        return contentPageLayout == null? null: contentPageLayout.getLastDetailForUpdate().getContentPageLayoutDetailValue().clone();
    }
    
    public ContentPageLayoutDetailValue getContentPageLayoutDetailValueByNameForUpdate(String contentPageLayoutName) {
        return getContentPageLayoutDetailValueForUpdate(getContentPageLayoutByNameForUpdate(contentPageLayoutName));
    }
    
    private static final Map<EntityPermission, String> getDefaultContentPageLayoutQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contentpagelayouts, contentpagelayoutdetails " +
                "WHERE cntpl_contentpagelayoutid = cntpldt_cntpl_contentpagelayoutid AND cntpldt_isdefault = 1 AND cntpldt_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contentpagelayouts, contentpagelayoutdetails " +
                "WHERE cntpl_contentpagelayoutid = cntpldt_cntpl_contentpagelayoutid AND cntpldt_isdefault = 1 AND cntpldt_thrutime = ? " +
                "FOR UPDATE");
        getDefaultContentPageLayoutQueries = Collections.unmodifiableMap(queryMap);
    }

    public ContentPageLayout getDefaultContentPageLayout(EntityPermission entityPermission) {
        return ContentPageLayoutFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultContentPageLayoutQueries,
                Session.MAX_TIME);
    }
    
    public ContentPageLayout getDefaultContentPageLayout() {
        return getDefaultContentPageLayout(EntityPermission.READ_ONLY);
    }
    
    public ContentPageLayout getDefaultContentPageLayoutForUpdate() {
        return getDefaultContentPageLayout(EntityPermission.READ_WRITE);
    }
    
    public ContentPageLayoutDetailValue getDefaultContentPageLayoutDetailValueForUpdate() {
        return getDefaultContentPageLayoutForUpdate().getLastDetailForUpdate().getContentPageLayoutDetailValue().clone();
    }
    
    private static final Map<EntityPermission, String> getContentPageLayoutsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contentpagelayouts, contentpagelayoutdetails " +
                "WHERE cntpl_contentpagelayoutid = cntpldt_cntpl_contentpagelayoutid AND cntpldt_thrutime = ? " +
                "ORDER BY cntpldt_sortorder, cntpldt_contentpagelayoutname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contentpagelayouts, contentpagelayoutdetails " +
                "WHERE cntpl_contentpagelayoutid = cntpldt_cntpl_contentpagelayoutid AND cntpldt_thrutime = ? " +
                "FOR UPDATE");
        getContentPageLayoutsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContentPageLayout> getContentPageLayouts(EntityPermission entityPermission) {
        return ContentPageLayoutFactory.getInstance().getEntitiesFromQuery(entityPermission, getContentPageLayoutsQueries,
                Session.MAX_TIME);
    }
    
    public List<ContentPageLayout> getContentPageLayouts() {
        return getContentPageLayouts(EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageLayout> getContentPageLayoutsForUpdate() {
        return getContentPageLayouts(EntityPermission.READ_WRITE);
    }
    
    public ContentPageLayoutTransfer getContentPageLayoutTransfer(UserVisit userVisit, ContentPageLayout contentPageLayout) {
        return getContentTransferCaches(userVisit).getContentPageLayoutTransferCache().getTransfer(contentPageLayout);
    }
    
    public List<ContentPageLayoutTransfer> getContentPageLayoutTransfers(UserVisit userVisit, Collection<ContentPageLayout> contentPageLayouts) {
        List<ContentPageLayoutTransfer> contentPageLayoutTransfers = new ArrayList<>(contentPageLayouts.size());
        ContentPageLayoutTransferCache contentPageLayoutTransferCache = getContentTransferCaches(userVisit).getContentPageLayoutTransferCache();
        
        contentPageLayouts.forEach((contentPageLayout) ->
                contentPageLayoutTransfers.add(contentPageLayoutTransferCache.getTransfer(contentPageLayout))
        );
        
        return contentPageLayoutTransfers;
    }
    
    public List<ContentPageLayoutTransfer> getContentPageLayoutTransfers(UserVisit userVisit) {
        return getContentPageLayoutTransfers(userVisit, getContentPageLayouts());
    }
    
    public ContentPageLayoutChoicesBean getContentPageLayoutChoices(String defaultContentPageLayoutChoice, Language language,
            boolean allowNullChoice) {
        List<ContentPageLayout> contentPageLayouts = getContentPageLayouts();
        var size = contentPageLayouts.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultContentPageLayoutChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var contentPageLayout : contentPageLayouts) {
            ContentPageLayoutDetail contentPageLayoutDetail = contentPageLayout.getLastDetail();
            
            var label = getBestContentPageLayoutDescription(contentPageLayout, language);
            var value = contentPageLayoutDetail.getContentPageLayoutName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContentPageLayoutChoice == null ? false : defaultContentPageLayoutChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contentPageLayoutDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ContentPageLayoutChoicesBean(labels, values, defaultValue);
    }
    
    private void updateContentPageLayoutFromValue(ContentPageLayoutDetailValue contentPageLayoutDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(contentPageLayoutDetailValue.hasBeenModified()) {
            ContentPageLayout contentPageLayout = ContentPageLayoutFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contentPageLayoutDetailValue.getContentPageLayoutPK());
            ContentPageLayoutDetail contentPageLayoutDetail = contentPageLayout.getActiveDetailForUpdate();
            
            contentPageLayoutDetail.setThruTime(session.START_TIME_LONG);
            contentPageLayoutDetail.store();
            
            ContentPageLayoutPK contentPageLayoutPK = contentPageLayoutDetail.getContentPageLayoutPK();
            String contentPageLayoutName = contentPageLayoutDetailValue.getContentPageLayoutName();
            Boolean isDefault = contentPageLayoutDetailValue.getIsDefault();
            Integer sortOrder = contentPageLayoutDetailValue.getSortOrder();
            
            if(checkDefault) {
                ContentPageLayout defaultContentPageLayout = getDefaultContentPageLayout();
                boolean defaultFound = defaultContentPageLayout != null && !defaultContentPageLayout.equals(contentPageLayout);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ContentPageLayoutDetailValue defaultContentPageLayoutDetailValue = getDefaultContentPageLayoutDetailValueForUpdate();
                    
                    defaultContentPageLayoutDetailValue.setIsDefault(Boolean.FALSE);
                    updateContentPageLayoutFromValue(defaultContentPageLayoutDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            contentPageLayoutDetail = ContentPageLayoutDetailFactory.getInstance().create(contentPageLayoutPK,
                    contentPageLayoutName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            contentPageLayout.setActiveDetail(contentPageLayoutDetail);
            contentPageLayout.setLastDetail(contentPageLayoutDetail);
            
            sendEventUsingNames(contentPageLayoutPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateContentPageLayoutFromValue(ContentPageLayoutDetailValue contentPageLayoutDetailValue, BasePK updatedBy) {
        updateContentPageLayoutFromValue(contentPageLayoutDetailValue, true, updatedBy);
    }
    
    public void deleteContentPageLayout(ContentPageLayout contentPageLayout, BasePK deletedBy) {
        deleteContentPagesByContentPageLayout(contentPageLayout, deletedBy);
        deleteContentPageLayoutDescriptionsByContentPageLayout(contentPageLayout, deletedBy);
        
        ContentPageLayoutDetail contentPageLayoutDetail = contentPageLayout.getLastDetailForUpdate();
        contentPageLayoutDetail.setThruTime(session.START_TIME_LONG);
        contentPageLayoutDetail.store();
        contentPageLayout.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        ContentPageLayout defaultContentPageLayout = getDefaultContentPageLayout();
        if(defaultContentPageLayout == null) {
            List<ContentPageLayout> contentPageLayouts = getContentPageLayoutsForUpdate();
            
            if(!contentPageLayouts.isEmpty()) {
                Iterator<ContentPageLayout> iter = contentPageLayouts.iterator();
                if(iter.hasNext()) {
                    defaultContentPageLayout = iter.next();
                }
                ContentPageLayoutDetailValue contentPageLayoutDetailValue = Objects.requireNonNull(defaultContentPageLayout).getLastDetailForUpdate().getContentPageLayoutDetailValue().clone();
                
                contentPageLayoutDetailValue.setIsDefault(Boolean.TRUE);
                updateContentPageLayoutFromValue(contentPageLayoutDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(contentPageLayout.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Descriptions
    // --------------------------------------------------------------------------------
    
    public ContentPageLayoutDescription createContentPageLayoutDescription(ContentPageLayout contentPageLayout, Language language, String description, BasePK createdBy) {
        ContentPageLayoutDescription contentPageLayoutDescription = ContentPageLayoutDescriptionFactory.getInstance().create(contentPageLayout, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contentPageLayout.getPrimaryKey(), EventTypes.MODIFY, contentPageLayoutDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentPageLayoutDescription;
    }
    
    private ContentPageLayoutDescription getContentPageLayoutDescription(ContentPageLayout contentPageLayout, Language language, EntityPermission entityPermission) {
        ContentPageLayoutDescription contentPageLayoutDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpagelayoutdescriptions " +
                        "WHERE cntpld_cntpl_contentpagelayoutid = ? AND cntpld_lang_languageid = ? AND cntpld_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpagelayoutdescriptions " +
                        "WHERE cntpld_cntpl_contentpagelayoutid = ? AND cntpld_lang_languageid = ? AND cntpld_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentPageLayoutDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentPageLayout.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            contentPageLayoutDescription = ContentPageLayoutDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageLayoutDescription;
    }
    
    public ContentPageLayoutDescription getContentPageLayoutDescription(ContentPageLayout contentPageLayout, Language language) {
        return getContentPageLayoutDescription(contentPageLayout, language, EntityPermission.READ_ONLY);
    }
    
    public ContentPageLayoutDescription getContentPageLayoutDescriptionForUpdate(ContentPageLayout contentPageLayout, Language language) {
        return getContentPageLayoutDescription(contentPageLayout, language, EntityPermission.READ_WRITE);
    }
    
    public ContentPageLayoutDescriptionValue getContentPageLayoutDescriptionValue(ContentPageLayoutDescription contentPageLayoutDescription) {
        return contentPageLayoutDescription == null? null: contentPageLayoutDescription.getContentPageLayoutDescriptionValue().clone();
    }
    
    public ContentPageLayoutDescriptionValue getContentPageLayoutDescriptionValueForUpdate(ContentPageLayout contentPageLayout, Language language) {
        return getContentPageLayoutDescriptionValue(getContentPageLayoutDescriptionForUpdate(contentPageLayout, language));
    }
    
    private List<ContentPageLayoutDescription> getContentPageLayoutDescriptionsByContentPageLayout(ContentPageLayout contentPageLayout, EntityPermission entityPermission) {
        List<ContentPageLayoutDescription> contentPageLayoutDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpagelayoutdescriptions, languages " +
                        "WHERE cntpld_cntpl_contentpagelayoutid = ? AND cntpld_thrutime = ? AND cntpld_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpagelayoutdescriptions " +
                        "WHERE cntpld_cntpl_contentpagelayoutid = ? AND cntpld_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentPageLayoutDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentPageLayout.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentPageLayoutDescriptions = ContentPageLayoutDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageLayoutDescriptions;
    }
    
    public List<ContentPageLayoutDescription> getContentPageLayoutDescriptionsByContentPageLayout(ContentPageLayout contentPageLayout) {
        return getContentPageLayoutDescriptionsByContentPageLayout(contentPageLayout, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageLayoutDescription> getContentPageLayoutDescriptionsByContentPageLayoutForUpdate(ContentPageLayout contentPageLayout) {
        return getContentPageLayoutDescriptionsByContentPageLayout(contentPageLayout, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentPageLayoutDescription(ContentPageLayout contentPageLayout, Language language) {
        String description;
        ContentPageLayoutDescription contentPageLayoutDescription = getContentPageLayoutDescription(contentPageLayout, language);
        
        if(contentPageLayoutDescription == null && !language.getIsDefault()) {
            contentPageLayoutDescription = getContentPageLayoutDescription(contentPageLayout, getPartyControl().getDefaultLanguage());
        }
        
        if(contentPageLayoutDescription == null) {
            description = contentPageLayout.getLastDetail().getContentPageLayoutName();
        } else {
            description = contentPageLayoutDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentPageLayoutDescriptionTransfer getContentPageLayoutDescriptionTransfer(UserVisit userVisit, ContentPageLayoutDescription contentPageLayoutDescription) {
        return getContentTransferCaches(userVisit).getContentPageLayoutDescriptionTransferCache().getTransfer(contentPageLayoutDescription);
    }
    
    public List<ContentPageLayoutDescriptionTransfer> getContentPageLayoutDescriptionTransfersByContentPageLayout(UserVisit userVisit, ContentPageLayout contentPageLayout) {
        List<ContentPageLayoutDescription> contentPageLayoutDescriptions = getContentPageLayoutDescriptionsByContentPageLayout(contentPageLayout);
        List<ContentPageLayoutDescriptionTransfer> contentPageLayoutDescriptionTransfers = new ArrayList<>(contentPageLayoutDescriptions.size());
        ContentPageLayoutDescriptionTransferCache contentPageLayoutDescriptionTransferCache = getContentTransferCaches(userVisit).getContentPageLayoutDescriptionTransferCache();
        
        contentPageLayoutDescriptions.forEach((contentPageLayoutDescription) ->
                contentPageLayoutDescriptionTransfers.add(contentPageLayoutDescriptionTransferCache.getTransfer(contentPageLayoutDescription))
        );
        
        return contentPageLayoutDescriptionTransfers;
    }
    
    public void updateContentPageLayoutDescriptionFromValue(ContentPageLayoutDescriptionValue contentPageLayoutDescriptionValue, BasePK updatedBy) {
        if(contentPageLayoutDescriptionValue.hasBeenModified()) {
            ContentPageLayoutDescription contentPageLayoutDescription = ContentPageLayoutDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentPageLayoutDescriptionValue.getPrimaryKey());
            
            contentPageLayoutDescription.setThruTime(session.START_TIME_LONG);
            contentPageLayoutDescription.store();
            
            ContentPageLayout contentPageLayout = contentPageLayoutDescription.getContentPageLayout();
            Language language = contentPageLayoutDescription.getLanguage();
            String description = contentPageLayoutDescriptionValue.getDescription();
            
            contentPageLayoutDescription = ContentPageLayoutDescriptionFactory.getInstance().create(contentPageLayout, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contentPageLayout.getPrimaryKey(), EventTypes.MODIFY, contentPageLayoutDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentPageLayoutDescription(ContentPageLayoutDescription contentPageLayoutDescription, BasePK deletedBy) {
        contentPageLayoutDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contentPageLayoutDescription.getContentPageLayoutPK(), EventTypes.MODIFY, contentPageLayoutDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentPageLayoutDescriptionsByContentPageLayout(ContentPageLayout contentPageLayout, BasePK deletedBy) {
        List<ContentPageLayoutDescription> contentPageLayoutDescriptions = getContentPageLayoutDescriptionsByContentPageLayoutForUpdate(contentPageLayout);
        
        contentPageLayoutDescriptions.forEach((contentPageLayoutDescription) -> 
                deleteContentPageLayoutDescription(contentPageLayoutDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Content Page Layout Areas
    // --------------------------------------------------------------------------------
    
    public ContentPageLayoutArea createContentPageLayoutArea(ContentPageLayout contentPageLayout, ContentPageAreaType contentPageAreaType, Boolean showDescriptionField, Integer sortOrder) {
        return ContentPageLayoutAreaFactory.getInstance().create(contentPageLayout, contentPageAreaType, showDescriptionField, sortOrder);
    }
    
    public long countContentPageLayoutAreasByContentPageLayout(ContentPageLayout contentPageLayout) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentpagelayoutareas " +
                "WHERE cntpla_cntpl_contentpagelayoutid = ?",
                contentPageLayout);
    }

    public ContentPageLayoutArea getContentPageLayoutArea(ContentPageLayout contentPageLayout, Integer sortOrder) {
        ContentPageLayoutArea contentPageLayoutArea;
        
        try {
            PreparedStatement ps = ContentPageLayoutAreaFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contentpagelayoutareas " +
                    "WHERE cntpla_cntpl_contentpagelayoutid = ? AND cntpla_sortorder = ?");
            
            ps.setLong(1, contentPageLayout.getPrimaryKey().getEntityId());
            ps.setInt(2, sortOrder);
            
            contentPageLayoutArea = ContentPageLayoutAreaFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageLayoutArea;
    }
    
    public List<ContentPageLayoutArea> getContentPageLayoutAreasByContentPageLayout(ContentPageLayout contentPageLayout) {
        List<ContentPageLayoutArea> contentPageLayoutAreas;
        
        try {
            PreparedStatement ps = ContentPageLayoutAreaFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contentpagelayoutareas " +
                    "WHERE cntpla_cntpl_contentpagelayoutid = ? " +
                    "ORDER BY cntpla_sortorder");
            
            ps.setLong(1, contentPageLayout.getPrimaryKey().getEntityId());
            
            contentPageLayoutAreas = ContentPageLayoutAreaFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageLayoutAreas;
    }
    
    public List<ContentPageLayoutAreaTransfer> getContentPageLayoutAreaTransfers(UserVisit userVisit, Collection<ContentPageLayoutArea> contentPageLayoutAreas) {
        List<ContentPageLayoutAreaTransfer> contentPageLayoutAreaTransfers = new ArrayList<>(contentPageLayoutAreas.size());
        ContentPageLayoutAreaTransferCache contentPageLayoutAreaTransferCache = getContentTransferCaches(userVisit).getContentPageLayoutAreaTransferCache();
        
        contentPageLayoutAreas.forEach((contentPageLayoutArea) ->
                contentPageLayoutAreaTransfers.add(contentPageLayoutAreaTransferCache.getContentPageLayoutAreaTransfer(contentPageLayoutArea))
        );
        
        return contentPageLayoutAreaTransfers;
    }
    
    public List<ContentPageLayoutAreaTransfer> getContentPageLayoutAreaTransfersByContentPageLayout(UserVisit userVisit, ContentPageLayout contentPageLayout) {
        return getContentPageLayoutAreaTransfers(userVisit, getContentPageLayoutAreasByContentPageLayout(contentPageLayout));
    }
    
    public ContentPageLayoutAreaTransfer getContentPageLayoutAreaTransfer(UserVisit userVisit, ContentPageLayoutArea contentPageLayoutArea) {
        return getContentTransferCaches(userVisit).getContentPageLayoutAreaTransferCache().getContentPageLayoutAreaTransfer(contentPageLayoutArea);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Area Descriptions
    // --------------------------------------------------------------------------------
    
    public ContentPageLayoutAreaDescription createContentPageLayoutAreaDescription(ContentPageLayoutArea contentPageLayoutArea, Language language, String description) {
        return ContentPageLayoutAreaDescriptionFactory.getInstance().create(contentPageLayoutArea, language, description);
    }
    
    public ContentPageLayoutAreaDescription getContentPageLayoutAreaDescription(ContentPageLayoutArea contentPageLayoutArea, Language language) {
        ContentPageLayoutAreaDescription contentPageLayoutAreaDescription;
        
        try {
            PreparedStatement ps = ContentPageLayoutAreaDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contentpagelayoutareadescriptions " +
                    "WHERE cntplad_cntpla_contentpagelayoutareaid = ? AND cntplad_lang_languageid = ?");
            
            ps.setLong(1, contentPageLayoutArea.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            contentPageLayoutAreaDescription = ContentPageLayoutAreaDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageLayoutAreaDescription;
    }
    
    public String getBestContentPageLayoutAreaDescription(ContentPageLayoutArea contentPageLayoutArea, Language language) {
        String description;
        ContentPageLayoutAreaDescription contentPageLayoutAreaDescription = getContentPageLayoutAreaDescription(contentPageLayoutArea, language);
        
        if(contentPageLayoutAreaDescription == null && !language.getIsDefault()) {
            contentPageLayoutAreaDescription = getContentPageLayoutAreaDescription(contentPageLayoutArea, getPartyControl().getDefaultLanguage());
        }
        
        if(contentPageLayoutAreaDescription == null) {
            description = contentPageLayoutArea.getSortOrder().toString();
        } else {
            description = contentPageLayoutAreaDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Content Collections
    // --------------------------------------------------------------------------------
    
    public ContentCollection createContentCollection(String contentCollectionName, OfferUse defaultOfferUse, BasePK createdBy) {
        ContentCollection contentCollection = ContentCollectionFactory.getInstance().create();
        ContentCollectionDetail contentCollectionDetail = ContentCollectionDetailFactory.getInstance().create(session,
                contentCollection, contentCollectionName, defaultOfferUse, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contentCollection = ContentCollectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentCollection.getPrimaryKey());
        contentCollection.setActiveDetail(contentCollectionDetail);
        contentCollection.setLastDetail(contentCollectionDetail);
        contentCollection.store();
        
        sendEventUsingNames(contentCollection.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentCollection;
    }

    public long countContentCollectionsByDefaultOfferUse(OfferUse defaultOfferUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM contentcollections, contentcollectiondetails " +
                        "WHERE cntc_activedetailid = cntcdt_contentcollectiondetailid " +
                        "AND cntcdt_defaultofferuseid = ?", defaultOfferUse);
    }

    public List<ContentCollection> getContentCollections() {
        List<ContentCollection> contentCollections;
        
        try {
            PreparedStatement ps = ContentCollectionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contentcollections, contentcollectiondetails " +
                    "WHERE cntc_contentcollectionid = cntcdt_cntc_contentcollectionid AND cntcdt_thrutime = ? " +
                    "ORDER BY cntcdt_contentcollectionname");
            
            ps.setLong(1, Session.MAX_TIME);
            
            contentCollections = ContentCollectionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCollections;
    }
    
    private ContentCollection getContentCollectionByName(String contentCollectionName, EntityPermission entityPermission) {
        ContentCollection contentCollection;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcollections, contentcollectiondetails " +
                        "WHERE cntc_contentcollectionid = cntcdt_cntc_contentcollectionid AND cntcdt_contentcollectionname = ? AND cntcdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcollections, contentcollectiondetails " +
                        "WHERE cntc_contentcollectionid = cntcdt_cntc_contentcollectionid AND cntcdt_contentcollectionname = ? AND cntcdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCollectionFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, contentCollectionName);
            ps.setLong(2, Session.MAX_TIME);
            
            contentCollection = ContentCollectionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCollection;
    }
    
    public ContentCollection getContentCollectionByName(String contentCollectionName) {
        return getContentCollectionByName(contentCollectionName, EntityPermission.READ_ONLY);
    }
    
    public ContentCollection getContentCollectionByNameForUpdate(String contentCollectionName) {
        return getContentCollectionByName(contentCollectionName, EntityPermission.READ_WRITE);
    }
    
    public ContentCollectionDetailValue getContentCollectionDetailValueForUpdate(ContentCollection contentCollection) {
        return contentCollection == null? null: contentCollection.getLastDetailForUpdate().getContentCollectionDetailValue().clone();
    }
    
    public ContentCollectionDetailValue getContentCollectionDetailValueByNameForUpdate(String contentCollectionName) {
        return getContentCollectionDetailValueForUpdate(getContentCollectionByNameForUpdate(contentCollectionName));
    }
    
    public ContentCollectionChoicesBean getContentCollectionChoices(String defaultContentCollectionChoice, Language language) {
        List<ContentCollection> contentCollections = getContentCollections();
        var size = contentCollections.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var contentCollection : contentCollections) {
            var label = getBestContentCollectionDescription(contentCollection, language);
            var value = contentCollection.getLastDetail().getContentCollectionName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContentCollectionChoice != null && defaultContentCollectionChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new ContentCollectionChoicesBean(labels, values, defaultValue);
    }
    
    public ContentCollectionTransfer getContentCollectionTransfer(UserVisit userVisit, ContentCollection contentCollection) {
        return getContentTransferCaches(userVisit).getContentCollectionTransferCache().getContentCollectionTransfer(contentCollection);
    }
    
    public List<ContentCollectionTransfer> getContentCollectionTransfers(UserVisit userVisit, Collection<ContentCollection> contentCollections) {
        List<ContentCollectionTransfer> contentCollectionTransfers = new ArrayList<>(contentCollections.size());
        ContentCollectionTransferCache contentCollectionTransferCache = getContentTransferCaches(userVisit).getContentCollectionTransferCache();
        
        contentCollections.forEach((contentCollection) ->
                contentCollectionTransfers.add(contentCollectionTransferCache.getContentCollectionTransfer(contentCollection))
        );
        
        return contentCollectionTransfers;
    }
    
    public List<ContentCollectionTransfer> getContentCollectionTransfers(UserVisit userVisit) {
        return getContentCollectionTransfers(userVisit, getContentCollections());
    }
    
    public void updateContentCollectionFromValue(ContentCollectionDetailValue contentCollectionDetailValue, BasePK updatedBy) {
        if(contentCollectionDetailValue.hasBeenModified()) {
            ContentCollection contentCollection = ContentCollectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contentCollectionDetailValue.getContentCollectionPK());
            ContentCollectionDetail contentCollectionDetail = contentCollection.getActiveDetailForUpdate();

            contentCollectionDetail.setThruTime(session.START_TIME_LONG);
            contentCollectionDetail.store();

            ContentCollectionPK contentCollectionPK = contentCollectionDetail.getContentCollectionPK();
            String contentCollectionName = contentCollectionDetailValue.getContentCollectionName();
            OfferUsePK defaultOfferUsePK = contentCollectionDetailValue.getDefaultOfferUsePK();

            contentCollectionDetail = ContentCollectionDetailFactory.getInstance().create(contentCollectionPK,
                    contentCollectionName, defaultOfferUsePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            contentCollection.setActiveDetail(contentCollectionDetail);
            contentCollection.setLastDetail(contentCollectionDetail);
            contentCollection.store();

            sendEventUsingNames(contentCollectionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        deleteContentSectionsByContentCollection(contentCollection, deletedBy);
        deleteContentCatalogsByContentCollection(contentCollection, deletedBy);
        deleteContentForumsByContentCollection(contentCollection, deletedBy);
        deleteContentWebAddressesByContentCollection(contentCollection, deletedBy);
        deleteContentCollectionDescriptionsByContentCollection(contentCollection, deletedBy);
        contentCollection.getLastDetailForUpdate().setThruTime(session.START_TIME_LONG);
        contentCollection.setActiveDetail(null);
        
        sendEventUsingNames(contentCollection.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Collection Descriptions
    // --------------------------------------------------------------------------------
    
    public ContentCollectionDescription createContentCollectionDescription(ContentCollection contentCollection, Language language, String description, BasePK createdBy) {
        
        ContentCollectionDescription contentCollectionDescription = ContentCollectionDescriptionFactory.getInstance().create(contentCollection, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contentCollection.getPrimaryKey(), EventTypes.MODIFY, contentCollectionDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentCollectionDescription;
    }
    
    private ContentCollectionDescription getContentCollectionDescription(ContentCollection contentCollection, Language language, EntityPermission entityPermission) {
        ContentCollectionDescription contentCollectionDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcollectiondescriptions " +
                        "WHERE cntcd_cntc_contentcollectionid = ? AND cntcd_lang_languageid = ? AND cntcd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcollectiondescriptions " +
                        "WHERE cntcd_cntc_contentcollectionid = ? AND cntcd_lang_languageid = ? AND cntcd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCollectionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            contentCollectionDescription = ContentCollectionDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCollectionDescription;
    }
    
    public ContentCollectionDescription getContentCollectionDescription(ContentCollection contentCollection, Language language) {
        return getContentCollectionDescription(contentCollection, language, EntityPermission.READ_ONLY);
    }
    
    public ContentCollectionDescription getContentCollectionDescriptionForUpdate(ContentCollection contentCollection, Language language) {
        return getContentCollectionDescription(contentCollection, language, EntityPermission.READ_WRITE);
    }
    
    public ContentCollectionDescriptionValue getContentCollectionDescriptionValue(ContentCollectionDescription contentCollectionDescription) {
        return contentCollectionDescription == null? null: contentCollectionDescription.getContentCollectionDescriptionValue().clone();
    }
    
    public ContentCollectionDescriptionValue getContentCollectionDescriptionValueForUpdate(ContentCollection contentCollection, Language language) {
        return getContentCollectionDescriptionValue(getContentCollectionDescriptionForUpdate(contentCollection, language));
    }
    
    private List<ContentCollectionDescription> getContentCollectionDescriptionsByContentCollection(ContentCollection contentCollection, EntityPermission entityPermission) {
        List<ContentCollectionDescription> contentCollectionDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcollectiondescriptions, languages " +
                        "WHERE cntcd_cntc_contentcollectionid = ? AND cntcd_thrutime = ? AND cntcd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcollectiondescriptions " +
                        "WHERE cntcd_cntc_contentcollectionid = ? AND cntcd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCollectionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCollectionDescriptions = ContentCollectionDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCollectionDescriptions;
    }
    
    public List<ContentCollectionDescription> getContentCollectionDescriptionsByContentCollection(ContentCollection contentCollection) {
        return getContentCollectionDescriptionsByContentCollection(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCollectionDescription> getContentCollectionDescriptionsByContentCollectionForUpdate(ContentCollection contentCollection) {
        return getContentCollectionDescriptionsByContentCollection(contentCollection, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentCollectionDescription(ContentCollection contentCollection, Language language) {
        String description;
        ContentCollectionDescription contentCollectionDescription = getContentCollectionDescription(contentCollection, language);
        
        if(contentCollectionDescription == null && !language.getIsDefault()) {
            contentCollectionDescription = getContentCollectionDescription(contentCollection, getPartyControl().getDefaultLanguage());
        }
        
        if(contentCollectionDescription == null) {
            description = contentCollection.getLastDetail().getContentCollectionName();
        } else {
            description = contentCollectionDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentCollectionDescriptionTransfer getContentCollectionDescriptionTransfer(UserVisit userVisit, ContentCollectionDescription contentCollectionDescription) {
        return getContentTransferCaches(userVisit).getContentCollectionDescriptionTransferCache().getContentCollectionDescriptionTransfer(contentCollectionDescription);
    }
    
    public List<ContentCollectionDescriptionTransfer> getContentCollectionDescriptionTransfers(UserVisit userVisit, ContentCollection contentCollection) {
        List<ContentCollectionDescription> contentCollectionDescriptions = getContentCollectionDescriptionsByContentCollection(contentCollection);
        List<ContentCollectionDescriptionTransfer> contentCollectionDescriptionTransfers = new ArrayList<>(contentCollectionDescriptions.size());
        ContentCollectionDescriptionTransferCache contentCollectionDescriptionTransferCache = getContentTransferCaches(userVisit).getContentCollectionDescriptionTransferCache();
        
        for(var contentCollectionDescription : contentCollectionDescriptions) {
            contentCollectionDescriptionTransfers.add(contentCollectionDescriptionTransferCache.getContentCollectionDescriptionTransfer(contentCollectionDescription));
        }
        
        return contentCollectionDescriptionTransfers;
    }
    
    public void updateContentCollectionDescriptionFromValue(ContentCollectionDescriptionValue contentCollectionDescriptionValue, BasePK updatedBy) {
        if(contentCollectionDescriptionValue.hasBeenModified()) {
            ContentCollectionDescription contentCollectionDescription = ContentCollectionDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentCollectionDescriptionValue.getPrimaryKey());
            
            contentCollectionDescription.setThruTime(session.START_TIME_LONG);
            contentCollectionDescription.store();
            
            ContentCollection contentCollection = contentCollectionDescription.getContentCollection();
            Language language = contentCollectionDescription.getLanguage();
            String description = contentCollectionDescriptionValue.getDescription();
            
            contentCollectionDescription = ContentCollectionDescriptionFactory.getInstance().create(contentCollection, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contentCollection.getPrimaryKey(), EventTypes.MODIFY, contentCollectionDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentCollectionDescription(ContentCollectionDescription contentCollectionDescription, BasePK deletedBy) {
        contentCollectionDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contentCollectionDescription.getContentCollectionPK(), EventTypes.MODIFY, contentCollectionDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentCollectionDescriptionsByContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        List<ContentCollectionDescription> contentCollectionDescriptions = getContentCollectionDescriptionsByContentCollectionForUpdate(contentCollection);
        
        contentCollectionDescriptions.forEach((contentCollectionDescription) -> 
                deleteContentCollectionDescription(contentCollectionDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Content Sections
    // --------------------------------------------------------------------------------
    
    public ContentSection createContentSection(ContentCollection contentCollection, String contentSectionName, ContentSection parentContentSection, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        ContentSection defaultContentSection = getDefaultContentSection(contentCollection);
        boolean defaultFound = defaultContentSection != null;
        
        if(defaultFound) {
            if(defaultContentSection.getLastDetail().getContentSectionName().equals(ContentSections.ROOT.toString())) {
                isDefault = Boolean.TRUE;
            }
        }
        
        if(defaultFound && isDefault) {
            ContentSectionDetailValue defaultContentSectionDetailValue = getDefaultContentSectionDetailValueForUpdate(contentCollection);
            
            defaultContentSectionDetailValue.setIsDefault(Boolean.FALSE);
            updateContentSectionFromValue(defaultContentSectionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ContentSection contentSection = ContentSectionFactory.getInstance().create();
        ContentSectionDetail contentSectionDetail = ContentSectionDetailFactory.getInstance().create(contentSection, contentCollection, contentSectionName, parentContentSection, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contentSection = ContentSectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentSection.getPrimaryKey());
        contentSection.setActiveDetail(contentSectionDetail);
        contentSection.setLastDetail(contentSectionDetail);
        contentSection.store();
        
        sendEventUsingNames(contentSection.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentSection;
    }
    
    public long countContentSectionsByContentCollection(ContentCollection contentCollection) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentsectiondetails " +
                "WHERE cntsdt_cntc_contentcollectionid = ? AND cntsdt_thrutime = ?",
                contentCollection, Session.MAX_TIME);
    }

    private List<ContentSection> getContentSections(ContentCollection contentCollection, EntityPermission entityPermission) {
        List<ContentSection> contentSections;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsections, contentsectiondetails " +
                        "WHERE cnts_contentsectionid = cntsdt_cnts_contentsectionid AND cntsdt_cntc_contentcollectionid = ? AND cntsdt_thrutime = ? " +
                        "ORDER BY cntsdt_sortorder, cntsdt_contentsectionname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsections, contentsectiondetails " +
                        "WHERE cnts_contentsectionid = cntsdt_cnts_contentsectionid AND cntsdt_cntc_contentcollectionid = ? AND cntsdt_thrutime = ? " +
                        "ORDER BY cntsdt_sortorder, cntsdt_contentsectionname " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentSectionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentSections = ContentSectionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentSections;
    }
    
    public List<ContentSection> getContentSections(ContentCollection contentCollection) {
        return getContentSections(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentSection> getContentSectionsForUpdate(ContentCollection contentCollection) {
        return getContentSections(contentCollection, EntityPermission.READ_WRITE);
    }
    
    private List<ContentSection> getContentSectionsByParentContentSection(ContentSection parentContentSection, EntityPermission entityPermission) {
        List<ContentSection> contentSections;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsections, contentsectiondetails " +
                        "WHERE cnts_contentsectionid = cntsdt_cnts_contentsectionid AND cntsdt_parentcontentsectionid = ? AND cntsdt_thrutime = ? " +
                        "ORDER BY cntsdt_sortorder, cntsdt_contentsectionname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsections, contentsectiondetails " +
                        "WHERE cnts_contentsectionid = cntsdt_cnts_contentsectionid AND cntsdt_parentcontentsectionid = ? AND cntsdt_thrutime = ? " +
                        "ORDER BY cntsdt_sortorder, cntsdt_contentsectionname " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentSectionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, parentContentSection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentSections = ContentSectionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentSections;
    }
    
    public List<ContentSection> getContentSectionsByParentContentSection(ContentSection parentContentSection) {
        return getContentSectionsByParentContentSection(parentContentSection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentSection> getContentSectionsByParentContentSectionForUpdate(ContentSection parentContentSection) {
        return getContentSectionsByParentContentSection(parentContentSection, EntityPermission.READ_WRITE);
    }
    
    private ContentSection getContentSectionByName(ContentCollection contentCollection, String contentSectionName, EntityPermission entityPermission) {
        ContentSection contentSection;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsections, contentsectiondetails " +
                        "WHERE cnts_contentsectionid = cntsdt_cnts_contentsectionid AND cntsdt_cntc_contentcollectionid = ? AND cntsdt_contentsectionname = ? AND cntsdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsections, contentsectiondetails " +
                        "WHERE cnts_contentsectionid = cntsdt_cnts_contentsectionid AND cntsdt_cntc_contentcollectionid = ? AND cntsdt_contentsectionname = ? AND cntsdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentSectionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setString(2, contentSectionName);
            ps.setLong(3, Session.MAX_TIME);
            
            contentSection = ContentSectionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentSection;
    }
    
    public ContentSection getContentSectionByName(ContentCollection contentCollection, String contentSectionName) {
        return getContentSectionByName(contentCollection, contentSectionName, EntityPermission.READ_ONLY);
    }
    
    public ContentSection getContentSectionByNameForUpdate(ContentCollection contentCollection, String contentSectionName) {
        return getContentSectionByName(contentCollection, contentSectionName, EntityPermission.READ_WRITE);
    }
    
    public ContentSectionDetailValue getContentSectionDetailValueForUpdate(ContentSection contentSection) {
        return contentSection == null? null: contentSection.getLastDetailForUpdate().getContentSectionDetailValue().clone();
    }
    
    public ContentSectionDetailValue getContentSectionDetailValueByNameForUpdate(ContentCollection contentCollection, String contentSectionName) {
        return getContentSectionDetailValueForUpdate(getContentSectionByNameForUpdate(contentCollection, contentSectionName));
    }
    
    private ContentSection getDefaultContentSection(ContentCollection contentCollection, EntityPermission entityPermission) {
        ContentSection contentSection;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsections, contentsectiondetails " +
                        "WHERE cnts_contentsectionid = cntsdt_cnts_contentsectionid AND cntsdt_cntc_contentcollectionid = ? AND cntsdt_isdefault = 1 AND cntsdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsections, contentsectiondetails " +
                        "WHERE cnts_contentsectionid = cntsdt_cnts_contentsectionid AND cntsdt_cntc_contentcollectionid = ? AND cntsdt_isdefault = 1 AND cntsdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentSectionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentSection = ContentSectionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentSection;
    }
    
    public ContentSection getDefaultContentSection(ContentCollection contentCollection) {
        return getDefaultContentSection(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public ContentSectionDetailValue getDefaultContentSectionDetailValueForUpdate(ContentCollection contentCollection) {
        return getDefaultContentSection(contentCollection, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentSectionDetailValue().clone();
    }
    
    public ContentSectionChoicesBean getContentSectionChoices(ContentCollection contentCollection, String defaultContentSectionChoice, Language language, boolean allowNullChoice) {
        List<ContentSection> contentSections = getContentSections(contentCollection);
        var size = contentSections.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultContentSectionChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var contentSection : contentSections) {
            ContentSectionDetail contentSectionDetail = contentSection.getLastDetail();
            var value = contentSectionDetail.getContentSectionName();
            
            if(!value.equals(ContentSections.ROOT.toString())) {
                var label = getBestContentSectionDescription(contentSection, language);

                labels.add(label == null? value: label);
                values.add(value);

                var usingDefaultChoice = Objects.equals(defaultContentSectionChoice, value);
                if(usingDefaultChoice || (defaultValue == null && contentSectionDetail.getIsDefault())) {
                    defaultValue = value;
                }
            }
        }
        
        return new ContentSectionChoicesBean(labels, values, defaultValue);
    }
    
    public ContentSectionTransfer getContentSectionTransfer(UserVisit userVisit, ContentSection contentSection) {
        return getContentTransferCaches(userVisit).getContentSectionTransferCache().getContentSectionTransfer(contentSection);
    }
    
    public List<ContentSectionTransfer> getContentSectionTransfers(UserVisit userVisit, Collection<ContentSection> contentSections) {
        List<ContentSectionTransfer> contentSectionTransfers = new ArrayList<>(contentSections.size());
        ContentSectionTransferCache contentSectionTransferCache = getContentTransferCaches(userVisit).getContentSectionTransferCache();
        
        contentSections.forEach((contentSection) ->
                contentSectionTransfers.add(contentSectionTransferCache.getContentSectionTransfer(contentSection))
        );
        
        return contentSectionTransfers;
    }
    
    public List<ContentSectionTransfer> getContentSectionTransfers(UserVisit userVisit, ContentCollection contentCollection) {
        return getContentSectionTransfers(userVisit, getContentSections(contentCollection));
    }
    
    public List<ContentSectionTransfer> getContentSectionTransfersByParentContentSection(UserVisit userVisit, ContentSection parentContentSection) {
        return getContentSectionTransfers(userVisit, getContentSectionsByParentContentSection(parentContentSection));
    }
    
    public boolean isParentContentSectionSafe(ContentSection contentSection, ContentSection parentContentSection) {
        boolean safe = true;
        
        if(parentContentSection != null) {
            Set<ContentSection> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(contentSection);
            do {
                if(parentItemPurchasingCategories.contains(parentContentSection)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentContentSection);
                parentContentSection = parentContentSection.getLastDetail().getParentContentSection();
            } while(parentContentSection != null);
        }
        
        return safe;
    }
    
    private void updateContentSectionFromValue(ContentSectionDetailValue contentSectionDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contentSectionDetailValue.hasBeenModified()) {
            ContentSection contentSection = ContentSectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentSectionDetailValue.getContentSectionPK());
            ContentSectionDetail contentSectionDetail = contentSection.getActiveDetailForUpdate();

            contentSectionDetail.setThruTime(session.START_TIME_LONG);
            contentSectionDetail.store();

            ContentSectionPK contentSectionPK = contentSectionDetail.getContentSectionPK();
            ContentCollection contentCollection = contentSectionDetail.getContentCollection();
            ContentCollectionPK contentCollectionPK = contentSectionDetail.getContentCollectionPK();
            String contentSectionName = contentSectionDetailValue.getContentSectionName();
            ContentSectionPK parentContentSectionPK = contentSectionDetailValue.getParentContentSectionPK();
            Boolean isDefault = contentSectionDetailValue.getIsDefault();
            Integer sortOrder = contentSectionDetailValue.getSortOrder();

            if(checkDefault) {
                ContentSection defaultContentSection = getDefaultContentSection(contentCollection);
                boolean defaultFound = defaultContentSection != null && !defaultContentSection.equals(contentSection);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ContentSectionDetailValue defaultContentSectionDetailValue = getDefaultContentSectionDetailValueForUpdate(contentCollection);

                    defaultContentSectionDetailValue.setIsDefault(Boolean.FALSE);
                    updateContentSectionFromValue(defaultContentSectionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            contentSectionDetail = ContentSectionDetailFactory.getInstance().create(contentSectionPK, contentCollectionPK, contentSectionName, parentContentSectionPK, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            contentSection.setActiveDetail(contentSectionDetail);
            contentSection.setLastDetail(contentSectionDetail);
            contentSection.store();

            sendEventUsingNames(contentSectionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateContentSectionFromValue(ContentSectionDetailValue contentSectionDetailValue, BasePK updatedBy) {
        updateContentSectionFromValue(contentSectionDetailValue, true, updatedBy);
    }
    
    private void deleteContentSection(ContentSection contentSection, BasePK deletedBy, boolean recursive) {
        if(recursive) {
            List<ContentSection> subContentSections = getContentSectionsByParentContentSectionForUpdate(contentSection);
            
            subContentSections.forEach((subContentSection) -> {
                deleteContentSection(subContentSection, deletedBy, true);
            });
        }
        
        deleteContentPagesByContentSection(contentSection, deletedBy);
        deleteContentSectionDescriptionsByContentSection(contentSection, deletedBy);
        
        ContentSectionDetail contentSectionDetail = contentSection.getLastDetailForUpdate();
        contentSectionDetail.setThruTime(session.START_TIME_LONG);
        contentSectionDetail.store();
        contentSection.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        ContentCollection contentCollection = contentSectionDetail.getContentCollection();
        ContentSection defaultContentSection = getDefaultContentSection(contentCollection);
        if(defaultContentSection == null) {
            // Try and find a new default from sections on the same level as the one that was deleted
            ContentSection parentContentSection = contentSectionDetail.getParentContentSection();
            List<ContentSection> contentSections = parentContentSection == null? null: getContentSectionsByParentContentSectionForUpdate(parentContentSection);
            
            // If that failed, pick one from the current collection
            if(contentSections == null || contentSections.isEmpty()) {
                contentSections = getContentSectionsForUpdate(contentCollection);
            }
            
            if(!contentSections.isEmpty()) {
                for(Iterator<ContentSection> iter = contentSections.iterator(); iter.hasNext();) {
                    defaultContentSection = iter.next();
                    if(!defaultContentSection.getLastDetail().getContentSectionName().equals(ContentSections.ROOT.toString())) {
                        break;
                    }
                }
                ContentSectionDetailValue contentSectionDetailValue = Objects.requireNonNull(defaultContentSection).getLastDetailForUpdate().getContentSectionDetailValue().clone();
                
                contentSectionDetailValue.setIsDefault(Boolean.TRUE);
                updateContentSectionFromValue(contentSectionDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(contentSection.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentSection(ContentSection contentSection, BasePK deletedBy) {
        deleteContentSection(contentSection, deletedBy, true);
    }
    
    public void deleteContentSectionsByContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        List<ContentSection> contentSections = getContentSectionsForUpdate(contentCollection);
        
        contentSections.forEach((contentSection) -> {
            deleteContentSection(contentSection, deletedBy, false);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Section Descriptions
    // --------------------------------------------------------------------------------
    
    public ContentSectionDescription createContentSectionDescription(ContentSection contentSection, Language language, String description, BasePK createdBy) {
        
        ContentSectionDescription contentSectionDescription = ContentSectionDescriptionFactory.getInstance().create(contentSection, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contentSection.getPrimaryKey(), EventTypes.MODIFY, contentSectionDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentSectionDescription;
    }
    
    private ContentSectionDescription getContentSectionDescription(ContentSection contentSection, Language language, EntityPermission entityPermission) {
        ContentSectionDescription contentSectionDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsectiondescriptions " +
                        "WHERE cntsd_cnts_contentsectionid = ? AND cntsd_lang_languageid = ? AND cntsd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsectiondescriptions " +
                        "WHERE cntsd_cnts_contentsectionid = ? AND cntsd_lang_languageid = ? AND cntsd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentSectionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentSection.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            contentSectionDescription = ContentSectionDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentSectionDescription;
    }
    
    public ContentSectionDescription getContentSectionDescription(ContentSection contentSection, Language language) {
        return getContentSectionDescription(contentSection, language, EntityPermission.READ_ONLY);
    }
    
    public ContentSectionDescription getContentSectionDescriptionForUpdate(ContentSection contentSection, Language language) {
        return getContentSectionDescription(contentSection, language, EntityPermission.READ_WRITE);
    }
    
    public ContentSectionDescriptionValue getContentSectionDescriptionValue(ContentSectionDescription contentSectionDescription) {
        return contentSectionDescription == null? null: contentSectionDescription.getContentSectionDescriptionValue().clone();
    }
    
    public ContentSectionDescriptionValue getContentSectionDescriptionValueForUpdate(ContentSection contentSection, Language language) {
        return getContentSectionDescriptionValue(getContentSectionDescriptionForUpdate(contentSection, language));
    }
    
    private List<ContentSectionDescription> getContentSectionDescriptionsByContentSection(ContentSection contentSection, EntityPermission entityPermission) {
        List<ContentSectionDescription> contentSectionDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsectiondescriptions, languages " +
                        "WHERE cntsd_cnts_contentsectionid = ? AND cntsd_thrutime = ? AND cntsd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentsectiondescriptions " +
                        "WHERE cntsd_cnts_contentsectionid = ? AND cntsd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentSectionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentSection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentSectionDescriptions = ContentSectionDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentSectionDescriptions;
    }
    
    public List<ContentSectionDescription> getContentSectionDescriptionsByContentSection(ContentSection contentSection) {
        return getContentSectionDescriptionsByContentSection(contentSection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentSectionDescription> getContentSectionDescriptionsByContentSectionForUpdate(ContentSection contentSection) {
        return getContentSectionDescriptionsByContentSection(contentSection, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentSectionDescription(ContentSection contentSection, Language language) {
        String description;
        ContentSectionDescription contentSectionDescription = getContentSectionDescription(contentSection, language);
        
        if(contentSectionDescription == null && !language.getIsDefault()) {
            contentSectionDescription = getContentSectionDescription(contentSection, getPartyControl().getDefaultLanguage());
        }
        
        if(contentSectionDescription == null) {
            description = contentSection.getLastDetail().getContentSectionName();
        } else {
            description = contentSectionDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentSectionDescriptionTransfer getContentSectionDescriptionTransfer(UserVisit userVisit, ContentSectionDescription contentSectionDescription) {
        return getContentTransferCaches(userVisit).getContentSectionDescriptionTransferCache().getContentSectionDescriptionTransfer(contentSectionDescription);
    }
    
    public List<ContentSectionDescriptionTransfer> getContentSectionDescriptionTransfers(UserVisit userVisit, ContentSection contentSection) {
        List<ContentSectionDescription> contentSectionDescriptions = getContentSectionDescriptionsByContentSection(contentSection);
        List<ContentSectionDescriptionTransfer> contentSectionDescriptionTransfers = new ArrayList<>(contentSectionDescriptions.size());
        ContentSectionDescriptionTransferCache contentSectionDescriptionTransferCache = getContentTransferCaches(userVisit).getContentSectionDescriptionTransferCache();
            
        contentSectionDescriptions.forEach((contentSectionDescription) ->
                contentSectionDescriptionTransfers.add(contentSectionDescriptionTransferCache.getContentSectionDescriptionTransfer(contentSectionDescription))
        );
        
        return contentSectionDescriptionTransfers;
    }
    
    public void updateContentSectionDescriptionFromValue(ContentSectionDescriptionValue contentSectionDescriptionValue, BasePK updatedBy) {
        if(contentSectionDescriptionValue.hasBeenModified()) {
            ContentSectionDescription contentSectionDescription = ContentSectionDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentSectionDescriptionValue.getPrimaryKey());
            
            contentSectionDescription.setThruTime(session.START_TIME_LONG);
            contentSectionDescription.store();
            
            ContentSection contentSection = contentSectionDescription.getContentSection();
            Language language = contentSectionDescription.getLanguage();
            String description = contentSectionDescriptionValue.getDescription();
            
            contentSectionDescription = ContentSectionDescriptionFactory.getInstance().create(contentSection, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contentSection.getPrimaryKey(), EventTypes.MODIFY, contentSectionDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentSectionDescription(ContentSectionDescription contentSectionDescription, BasePK deletedBy) {
        contentSectionDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contentSectionDescription.getContentSectionPK(), EventTypes.MODIFY, contentSectionDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentSectionDescriptionsByContentSection(ContentSection contentSection, BasePK deletedBy) {
        getContentSectionDescriptionsByContentSectionForUpdate(contentSection).stream().forEach((contentSectionDescription) -> {
            deleteContentSectionDescription(contentSectionDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Pages
    // --------------------------------------------------------------------------------
    
    public ContentPage createContentPage(ContentSection contentSection, String contentPageName, ContentPageLayout contentPageLayout, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        boolean defaultFound = getDefaultContentPage(contentSection) != null;
        
        if(defaultFound && isDefault) {
            ContentPageDetailValue defaultContentPageDetailValue = getDefaultContentPageDetailValueForUpdate(contentSection);
            
            defaultContentPageDetailValue.setIsDefault(Boolean.FALSE);
            updateContentPageFromValue(defaultContentPageDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ContentPage contentPage = ContentPageFactory.getInstance().create();
        ContentPageDetail contentPageDetail = ContentPageDetailFactory.getInstance().create(contentPage, contentSection, contentPageName, contentPageLayout, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contentPage = ContentPageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentPage.getPrimaryKey());
        contentPage.setActiveDetail(contentPageDetail);
        contentPage.setLastDetail(contentPageDetail);
        contentPage.store();
        
        sendEventUsingNames(contentPage.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentPage;
    }
    
    public long countContentPagesByContentSection(ContentSection contentSection) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentpagedetails " +
                "WHERE cntpdt_cnts_contentsectionid = ? AND cntpdt_thrutime = ?",
                contentSection, Session.MAX_TIME);
    }

    private List<ContentPage> getContentPagesByContentSection(ContentSection contentSection, EntityPermission entityPermission) {
        List<ContentPage> contentPages;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpages, contentpagedetails " +
                        "WHERE cntp_contentpageid = cntpdt_cntp_contentpageid AND cntpdt_cnts_contentsectionid = ? AND cntpdt_thrutime = ? " +
                        "ORDER BY cntpdt_sortorder, cntpdt_contentpagename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpages, contentpagedetails " +
                        "WHERE cntp_contentpageid = cntpdt_cntp_contentpageid AND cntpdt_cnts_contentsectionid = ? AND cntpdt_thrutime = ? " +
                        "ORDER BY cntpdt_sortorder, cntpdt_contentpagename " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentPageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentSection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentPages = ContentPageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPages;
    }
    
    public List<ContentPage> getContentPagesByContentSection(ContentSection contentSection) {
        return getContentPagesByContentSection(contentSection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPage> getContentPagesByContentSectionForUpdate(ContentSection contentSection) {
        return getContentPagesByContentSection(contentSection, EntityPermission.READ_WRITE);
    }
    
    private List<ContentPage> getContentPagesByContentPageLayout(ContentPageLayout contentPageLayout, EntityPermission entityPermission) {
        List<ContentPage> contentPages;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpages, contentpagedetails, contentsections, contentsectiondetails " +
                        "WHERE cntp_activedetailid = cntpdt_contentpagedetailid AND cntpdt_cntpl_contentpagelayoutid = ? " +
                        "AND cntpdt_cnts_contentsectionid = cnts_contentsectionid AND cnts_lastdetailid = cntsdt_contentsectiondetailid " +
                        "ORDER BY ccntsdt_sortorder, cntsdt_contentsectionname, ntpdt_sortorder, cntpdt_contentpagename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpages, contentpagedetails " +
                        "WHERE cntp_activedetailid = cntpdt_contentpagedetailid AND cntpdt_cntpl_contentpagelayoutid = ? " +
                        "ORDER BY cntpdt_sortorder, cntpdt_contentpagename " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentPageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentPageLayout.getPrimaryKey().getEntityId());

            contentPages = ContentPageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPages;
    }
    
    public List<ContentPage> getContentPagesByContentPageLayout(ContentPageLayout contentPageLayout) {
        return getContentPagesByContentPageLayout(contentPageLayout, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPage> getContentPagesByContentPageLayoutForUpdate(ContentPageLayout contentPageLayout) {
        return getContentPagesByContentPageLayout(contentPageLayout, EntityPermission.READ_WRITE);
    }
    
    private ContentPage getContentPageByName(ContentSection contentSection, String contentPageName, EntityPermission entityPermission) {
        ContentPage contentPage;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpages, contentpagedetails " +
                        "WHERE cntp_contentpageid = cntpdt_cntp_contentpageid AND cntpdt_cnts_contentsectionid = ? AND cntpdt_contentpagename = ? AND cntpdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpages, contentpagedetails " +
                        "WHERE cntp_contentpageid = cntpdt_cntp_contentpageid AND cntpdt_cnts_contentsectionid = ? AND cntpdt_contentpagename = ? AND cntpdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentPageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentSection.getPrimaryKey().getEntityId());
            ps.setString(2, contentPageName);
            ps.setLong(3, Session.MAX_TIME);
            
            contentPage = ContentPageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPage;
    }
    
    public ContentPage getContentPageByName(ContentSection contentSection, String contentPageName) {
        return getContentPageByName(contentSection, contentPageName, EntityPermission.READ_ONLY);
    }
    
    public ContentPage getContentPageByNameForUpdate(ContentSection contentSection, String contentPageName) {
        return getContentPageByName(contentSection, contentPageName, EntityPermission.READ_WRITE);
    }
    
    public ContentPageDetailValue getContentPageDetailValueForUpdate(ContentPage contentPage) {
        return contentPage == null? null: contentPage.getLastDetailForUpdate().getContentPageDetailValue().clone();
    }
    
    public ContentPageDetailValue getContentPageDetailValueByNameForUpdate(ContentSection contentSection, String contentPageName) {
        return getContentPageDetailValueForUpdate(getContentPageByNameForUpdate(contentSection, contentPageName));
    }
    
    private ContentPage getDefaultContentPage(ContentSection contentSection, EntityPermission entityPermission) {
        ContentPage contentPage;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpages, contentpagedetails " +
                        "WHERE cntp_contentpageid = cntpdt_cntp_contentpageid AND cntpdt_cnts_contentsectionid = ? AND cntpdt_isdefault = 1 AND cntpdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpages, contentpagedetails " +
                        "WHERE cntp_contentpageid = cntpdt_cntp_contentpageid AND cntpdt_cnts_contentsectionid = ? AND cntpdt_isdefault = 1 AND cntpdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentPageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentSection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentPage = ContentPageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPage;
    }
    
    public ContentPage getDefaultContentPage(ContentSection contentSection) {
        return getDefaultContentPage(contentSection, EntityPermission.READ_ONLY);
    }
    
    public ContentPageDetailValue getDefaultContentPageDetailValueForUpdate(ContentSection contentSection) {
        return getDefaultContentPage(contentSection, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentPageDetailValue().clone();
    }
    
    public ContentPageTransfer getContentPageTransfer(UserVisit userVisit, ContentPage contentPage) {
        return getContentTransferCaches(userVisit).getContentPageTransferCache().getContentPageTransfer(contentPage);
    }
    
    public List<ContentPageTransfer> getContentPageTransfers(UserVisit userVisit, Collection<ContentPage> contentPages) {
        List<ContentPageTransfer> contentPageTransfers = new ArrayList<>(contentPages.size());
        ContentPageTransferCache contentPageTransferCache = getContentTransferCaches(userVisit).getContentPageTransferCache();
            
        contentPages.forEach((contentPage) ->
                contentPageTransfers.add(contentPageTransferCache.getContentPageTransfer(contentPage))
        );
        
        return contentPageTransfers;
    }
    
    public List<ContentPageTransfer> getContentPageTransfers(UserVisit userVisit, ContentSection contentSection) {
        return getContentPageTransfers(userVisit, getContentPagesByContentSection(contentSection));
    }
    
    private void updateContentPageFromValue(ContentPageDetailValue contentPageDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contentPageDetailValue.hasBeenModified()) {
            ContentPage contentPage = ContentPageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentPageDetailValue.getContentPagePK());
            ContentPageDetail contentPageDetail = contentPage.getActiveDetailForUpdate();

            contentPageDetail.setThruTime(session.START_TIME_LONG);
            contentPageDetail.store();

            ContentPagePK contentPagePK = contentPage.getPrimaryKey();
            ContentSection contentSection = contentPageDetail.getContentSection();
            ContentSectionPK contentSectionPK = contentSection.getPrimaryKey();
            String contentPageName = contentPageDetailValue.getContentPageName();
            ContentPageLayoutPK contentPageLayoutPK = contentPageDetailValue.getContentPageLayoutPK();
            Boolean isDefault = contentPageDetailValue.getIsDefault();
            Integer sortOrder = contentPageDetailValue.getSortOrder();

            if(checkDefault) {
                ContentPage defaultContentPage = getDefaultContentPage(contentSection);
                boolean defaultFound = defaultContentPage != null && !defaultContentPage.equals(contentPage);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ContentPageDetailValue defaultContentPageDetailValue = getDefaultContentPageDetailValueForUpdate(contentSection);

                    defaultContentPageDetailValue.setIsDefault(Boolean.FALSE);
                    updateContentPageFromValue(defaultContentPageDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            // If the Content Page Layout was modified, then all the Content Page Areas associated with the page
            // are deleted, since there's a good chance that they will not match up correctly with the new layout.
            if(contentPageDetailValue.getContentPageLayoutPKHasBeenModified()) {
                deleteContentPageAreasByContentPage(contentPage, updatedBy);
            }

            contentPageDetail = ContentPageDetailFactory.getInstance().create(contentPagePK, contentSectionPK, contentPageName, contentPageLayoutPK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            contentPage.setActiveDetail(contentPageDetail);
            contentPage.setLastDetail(contentPageDetail);
            contentPage.store();

            sendEventUsingNames(contentPagePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateContentPageFromValue(ContentPageDetailValue contentPageDetailValue, BasePK updatedBy) {
        updateContentPageFromValue(contentPageDetailValue, true, updatedBy);
    }
    
    public void deleteContentPage(ContentPage contentPage, BasePK deletedBy) {
        deleteContentPageAreasByContentPage(contentPage, deletedBy);
        deleteContentPageDescriptionsByContentPage(contentPage, deletedBy);
        
        ContentPageDetail contentPageDetail = contentPage.getLastDetailForUpdate();
        contentPageDetail.setThruTime(session.START_TIME_LONG);
        contentPageDetail.store();
        contentPage.setActiveDetail(null);
        
        ContentSection contentSection = contentPageDetail.getContentSection();
        ContentPage defaultContentPage = getDefaultContentPage(contentSection);
        if(defaultContentPage == null) {
            List<ContentPage> contentPages = getContentPagesByContentSectionForUpdate(contentSection);
            
            if(!contentPages.isEmpty()) {
                defaultContentPage = contentPages.iterator().next();
                ContentPageDetailValue contentPageDetailValue = Objects.requireNonNull(defaultContentPage).getLastDetailForUpdate().getContentPageDetailValue().clone();
                
                contentPageDetailValue.setIsDefault(Boolean.TRUE);
                updateContentPageFromValue(contentPageDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(contentPage.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentPagesByContentSection(ContentSection contentSection, BasePK deletedBy) {
        getContentPagesByContentSectionForUpdate(contentSection).stream().forEach((contentPage) -> {
            deleteContentPage(contentPage, deletedBy);
        });
    }
    
    public void deleteContentPagesByContentPageLayout(ContentPageLayout contentPageLayout, BasePK deletedBy) {
        getContentPagesByContentPageLayoutForUpdate(contentPageLayout).stream().forEach((contentPage) -> {
            deleteContentPage(contentPage, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Descriptions
    // --------------------------------------------------------------------------------
    
    public ContentPageDescription createContentPageDescription(ContentPage contentPage, Language language, String description, BasePK createdBy) {
        ContentPageDescription contentPageDescription = ContentPageDescriptionFactory.getInstance().create(contentPage, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contentPage.getPrimaryKey(), EventTypes.MODIFY, contentPageDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentPageDescription;
    }
    
    private ContentPageDescription getContentPageDescription(ContentPage contentPage, Language language, EntityPermission entityPermission) {
        ContentPageDescription contentPageDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpagedescriptions " +
                        "WHERE cntpd_cntp_contentpageid = ? AND cntpd_lang_languageid = ? AND cntpd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpagedescriptions " +
                        "WHERE cntpd_cntp_contentpageid = ? AND cntpd_lang_languageid = ? AND cntpd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentPageDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentPage.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            contentPageDescription = ContentPageDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageDescription;
    }
    
    public ContentPageDescription getContentPageDescription(ContentPage contentPage, Language language) {
        return getContentPageDescription(contentPage, language, EntityPermission.READ_ONLY);
    }
    
    public ContentPageDescription getContentPageDescriptionForUpdate(ContentPage contentPage, Language language) {
        return getContentPageDescription(contentPage, language, EntityPermission.READ_WRITE);
    }
    
    public ContentPageDescriptionValue getContentPageDescriptionValue(ContentPageDescription contentPageDescription) {
        return contentPageDescription == null? null: contentPageDescription.getContentPageDescriptionValue().clone();
    }
    
    public ContentPageDescriptionValue getContentPageDescriptionValueForUpdate(ContentPage contentPage, Language language) {
        return getContentPageDescriptionValue(getContentPageDescriptionForUpdate(contentPage, language));
    }
    
    private List<ContentPageDescription> getContentPageDescriptionsByContentPage(ContentPage contentPage, EntityPermission entityPermission) {
        List<ContentPageDescription> contentPageDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpagedescriptions, languages " +
                        "WHERE cntpd_cntp_contentpageid = ? AND cntpd_thrutime = ? AND cntpd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpagedescriptions " +
                        "WHERE cntpd_cntp_contentpageid = ? AND cntpd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentPageDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentPage.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentPageDescriptions = ContentPageDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageDescriptions;
    }
    
    public List<ContentPageDescription> getContentPageDescriptionsByContentPage(ContentPage contentPage) {
        return getContentPageDescriptionsByContentPage(contentPage, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageDescription> getContentPageDescriptionsByContentPageForUpdate(ContentPage contentPage) {
        return getContentPageDescriptionsByContentPage(contentPage, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentPageDescription(ContentPage contentPage, Language language) {
        String description;
        ContentPageDescription contentPageDescription = getContentPageDescription(contentPage, language);
        
        if(contentPageDescription == null && !language.getIsDefault()) {
            contentPageDescription = getContentPageDescription(contentPage, getPartyControl().getDefaultLanguage());
        }
        
        if(contentPageDescription == null) {
            description = contentPage.getLastDetail().getContentPageName();
        } else {
            description = contentPageDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentPageDescriptionTransfer getContentPageDescriptionTransfer(UserVisit userVisit, ContentPageDescription contentPageDescription) {
        return getContentTransferCaches(userVisit).getContentPageDescriptionTransferCache().getContentPageDescriptionTransfer(contentPageDescription);
    }
    
    public List<ContentPageDescriptionTransfer> getContentPageDescriptionTransfers(UserVisit userVisit, ContentPage contentPage) {
        List<ContentPageDescription> contentPageDescriptions = getContentPageDescriptionsByContentPage(contentPage);
        List<ContentPageDescriptionTransfer> contentPageDescriptionTransfers = new ArrayList<>(contentPageDescriptions.size());
        ContentPageDescriptionTransferCache contentPageDescriptionTransferCache = getContentTransferCaches(userVisit).getContentPageDescriptionTransferCache();
        
        contentPageDescriptions.forEach((contentPageDescription) ->
                contentPageDescriptionTransfers.add(contentPageDescriptionTransferCache.getContentPageDescriptionTransfer(contentPageDescription))
        );
        
        return contentPageDescriptionTransfers;
    }
    
    public void updateContentPageDescriptionFromValue(ContentPageDescriptionValue contentPageDescriptionValue, BasePK updatedBy) {
        if(contentPageDescriptionValue.hasBeenModified()) {
            ContentPageDescription contentPageDescription = ContentPageDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentPageDescriptionValue.getPrimaryKey());
            
            contentPageDescription.setThruTime(session.START_TIME_LONG);
            contentPageDescription.store();
            
            ContentPage contentPage = contentPageDescription.getContentPage();
            Language language = contentPageDescription.getLanguage();
            String description = contentPageDescriptionValue.getDescription();
            
            contentPageDescription = ContentPageDescriptionFactory.getInstance().create(contentPage, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contentPage.getPrimaryKey(), EventTypes.MODIFY, contentPageDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentPageDescription(ContentPageDescription contentPageDescription, BasePK deletedBy) {
        contentPageDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contentPageDescription.getContentPagePK(), EventTypes.MODIFY, contentPageDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentPageDescriptionsByContentPage(ContentPage contentPage, BasePK deletedBy) {
        getContentPageDescriptionsByContentPageForUpdate(contentPage).stream().forEach((contentPageDescription) -> {
            deleteContentPageDescription(contentPageDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Areas
    // --------------------------------------------------------------------------------
    
    public ContentPageArea createContentPageArea(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language, MimeType mimeType, BasePK createdBy) {
        ContentPageArea contentPageArea = ContentPageAreaFactory.getInstance().create();
        ContentPageAreaDetail contentPageAreaDetail = ContentPageAreaDetailFactory.getInstance().create(contentPageArea, contentPage, contentPageLayoutArea,
                language, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contentPageArea = ContentPageAreaFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentPageArea.getPrimaryKey());
        contentPageArea.setActiveDetail(contentPageAreaDetail);
        contentPageArea.setLastDetail(contentPageAreaDetail);
        contentPageArea.store();
        
        sendEventUsingNames(contentPage.getPrimaryKey(), EventTypes.MODIFY, contentPageArea.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentPageArea;
    }
    
    private List<ContentPageArea> getContentPageAreasByContentPage(ContentPage contentPage, EntityPermission entityPermission) {
        List<ContentPageArea> contentPageAreas;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpageareas, contentpageareadetails, contentpagelayoutareas, languages " +
                        "WHERE cntpa_contentpageareaid = cntpad_cntpa_contentpageareaid AND cntpad_cntp_contentpageid = ? AND cntpad_thrutime = ? " +
                        "AND cntpad_cntpla_contentpagelayoutareaid = cntpla_contentpagelayoutareaid AND cntpad_lang_languageid = lang_languageid " +
                        "ORDER BY cntpla_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentpageareas, contentpageareadetails " +
                        "WHERE cntpa_contentpageareaid = cntpad_cntpa_contentpageareaid AND cntpad_cntp_contentpageid = ? AND cntpad_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentPageAreaFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentPage.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentPageAreas = ContentPageAreaFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageAreas;
    }
    
    public List<ContentPageArea> getContentPageAreasByContentPage(ContentPage contentPage) {
        return getContentPageAreasByContentPage(contentPage, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageArea> getContentPageAreasByContentPageForUpdate(ContentPage contentPage) {
        return getContentPageAreasByContentPage(contentPage, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getContentPageAreasByContentPageQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM contentpageareas, contentpageareadetails "
                + "WHERE cntpa_activedetailid = cntpad_contentpageareadetailid "
                + "AND cntpad_cntp_contentpageid = ? AND cntpad_lang_languageid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM contentpageareas, contentpageareadetails "
                + "WHERE cntpa_activedetailid = cntpad_contentpageareadetailid "
                + "AND cntpad_cntp_contentpageid = ? AND cntpad_lang_languageid = ? "
                + "FOR UPDATE");
        getContentPageAreasByContentPageQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<ContentPageArea> getContentPageAreasByContentPage(ContentPage contentPage, Language language, EntityPermission entityPermission) {
        return ContentPageAreaFactory.getInstance().getEntitiesFromQuery(entityPermission, getContentPageAreasByContentPageQueries,
                contentPage, language);
    }
    
    public List<ContentPageArea> getContentPageAreasByContentPage(ContentPage contentPage, Language language) {
        return getContentPageAreasByContentPage(contentPage, language, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageArea> getContentPageAreasByContentPageForUpdate(ContentPage contentPage, Language language) {
        return getContentPageAreasByContentPage(contentPage, language, EntityPermission.READ_WRITE);
    }
    
    private ContentPageArea getContentPageArea(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language, EntityPermission entityPermission) {
        ContentPageArea contentPageArea;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM contentpageareas, contentpageareadetails "
                        + "WHERE cntpa_activedetailid = cntpad_contentpageareadetailid "
                        + "AND cntpad_cntp_contentpageid = ? AND cntpad_cntpla_contentpagelayoutareaid = ? AND cntpad_lang_languageid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM contentpageareas, contentpageareadetails "
                        + "WHERE cntpa_activedetailid = cntpad_contentpageareadetailid "
                        + "AND cntpad_cntp_contentpageid = ? AND cntpad_cntpla_contentpagelayoutareaid = ? AND cntpad_lang_languageid = ? "
                        + "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentPageAreaFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentPage.getPrimaryKey().getEntityId());
            ps.setLong(2, contentPageLayoutArea.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            
            contentPageArea = ContentPageAreaFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageArea;
    }
    
    public ContentPageArea getContentPageArea(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language) {
        return getContentPageArea(contentPage, contentPageLayoutArea, language, EntityPermission.READ_ONLY);
    }
    
    public ContentPageArea getContentPageAreaForUpdate(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language) {
        return getContentPageArea(contentPage, contentPageLayoutArea, language, EntityPermission.READ_WRITE);
    }
    
    public ContentPageAreaDetailValue getContentPageAreaDetailValueForUpdate(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language) {
        return getContentPageArea(contentPage, contentPageLayoutArea, language, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentPageAreaDetailValue().clone();
    }
    
    public ContentPageAreaDetailValue getContentPageAreaDetailValueForUpdate(ContentPageArea contentPageArea) {
        return contentPageArea.getLastDetailForUpdate().getContentPageAreaDetailValue().clone();
    }
    
    public ContentPageArea getBestContentPageArea(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language) {
        ContentPageArea contentPageArea = getContentPageArea(contentPage, contentPageLayoutArea, language);
        
        if(contentPageArea == null && !language.getIsDefault()) {
            contentPageArea = getContentPageArea(contentPage, contentPageLayoutArea, getPartyControl().getDefaultLanguage());
        }
        
        return contentPageArea;
    }
    
    public ContentPageAreaTransfer getContentPageAreaTransfer(UserVisit userVisit, ContentPageArea contentPageArea) {
        return getContentTransferCaches(userVisit).getContentPageAreaTransferCache().getContentPageAreaTransfer(contentPageArea);
    }
    
    public List<ContentPageAreaTransfer> getContentPageAreaTransfers(UserVisit userVisit, Collection<ContentPageArea> contentPageAreas) {
        List<ContentPageAreaTransfer> contentPageAreaTransfers = new ArrayList<>(contentPageAreas.size());
        ContentPageAreaTransferCache contentPageAreaTransferCache = getContentTransferCaches(userVisit).getContentPageAreaTransferCache();

        contentPageAreas.forEach((contentPageArea) -> {
            contentPageAreaTransfers.add(contentPageAreaTransferCache.getContentPageAreaTransfer(contentPageArea));
        });
        
        return contentPageAreaTransfers;
    }
    
    public List<ContentPageAreaTransfer> getContentPageAreaTransfersByContentPage(UserVisit userVisit, ContentPage contentPage) {
        return getContentPageAreaTransfers(userVisit, getContentPageAreasByContentPage(contentPage));
    }
    
    // TODO: fall back on default Language when a Content Page Area is missing for a specific Layout used on the Page.
    public List<ContentPageAreaTransfer> getContentPageAreaTransfersByContentPage(UserVisit userVisit, ContentPage contentPage, Language language) {
        return getContentPageAreaTransfers(userVisit, getContentPageAreasByContentPage(contentPage, language));
    }
    
    public ContentPageAreaDetail updateContentPageAreaFromValue(ContentPageAreaDetailValue contentPageAreaDetailValue, boolean forceUpdate, BasePK updatedBy) {
        ContentPageAreaDetail contentPageAreaDetail = ContentPageAreaDetailFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentPageAreaDetailValue.getPrimaryKey());
        
        if(forceUpdate || contentPageAreaDetailValue.hasBeenModified()) {
            contentPageAreaDetail.setThruTime(session.START_TIME_LONG);
            contentPageAreaDetail.store();
            
            ContentPageAreaPK contentPageAreaPK = contentPageAreaDetail.getContentPageAreaPK();
            ContentPagePK contentPagePK = contentPageAreaDetail.getContentPagePK();
            ContentPageLayoutAreaPK contentPageLayoutAreaPK = contentPageAreaDetail.getContentPageLayoutAreaPK();
            LanguagePK languagePK = contentPageAreaDetail.getLanguagePK();
            MimeTypePK mimeTypePK = contentPageAreaDetailValue.getMimeTypePK();
            
            contentPageAreaDetail = ContentPageAreaDetailFactory.getInstance().create(contentPageAreaPK, contentPagePK, contentPageLayoutAreaPK,
                    languagePK, mimeTypePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            ContentPageArea contentPageArea = ContentPageAreaFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentPageAreaPK);
            contentPageArea.setActiveDetail(contentPageAreaDetail);
            contentPageArea.setLastDetail(contentPageAreaDetail);
            
            sendEventUsingNames(contentPagePK, EventTypes.MODIFY, contentPageAreaDetailValue.getContentPageAreaPK(), EventTypes.MODIFY, updatedBy);
        }
        
        return contentPageAreaDetail;
    }
    
    public void deleteContentPageArea(ContentPageArea contentPageArea, BasePK deletedBy) {
        ContentPageAreaDetail contentPageAreaDetail = contentPageArea.getLastDetailForUpdate();
        
        contentPageAreaDetail.setThruTime(session.START_TIME_LONG);
        contentPageArea.setActiveDetail(null);
        
        sendEventUsingNames(contentPageAreaDetail.getContentPagePK(), EventTypes.MODIFY, contentPageArea.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContentPageAreasByContentPage(ContentPage contentPage, BasePK deletedBy) {
        getContentPageAreasByContentPageForUpdate(contentPage).stream().forEach((contentPageArea) -> {
            deleteContentPageArea(contentPageArea, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Blobs
    // --------------------------------------------------------------------------------
    
    public ContentPageAreaBlob createContentPageAreaBlob(ContentPageAreaDetail contentPageAreaDetail, ByteArray blob) {
        return ContentPageAreaBlobFactory.getInstance().create(contentPageAreaDetail, blob);
    }
    
    public ContentPageAreaBlob getContentPageAreaBlob(ContentPageAreaDetail contentPageAreaDetail) {
        ContentPageAreaBlob contentPageAreaBlob;
        
        try {
            PreparedStatement ps = ContentPageAreaBlobFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contentpageareablobs " +
                    "WHERE cntpab_cntpad_contentpageareadetailid = ?");
            
            ps.setLong(1, contentPageAreaDetail.getPrimaryKey().getEntityId());
            
            contentPageAreaBlob = ContentPageAreaBlobFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageAreaBlob;
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Clobs
    // --------------------------------------------------------------------------------
    
    public ContentPageAreaClob createContentPageAreaClob(ContentPageAreaDetail contentPageAreaDetail, String clob) {
        return ContentPageAreaClobFactory.getInstance().create(contentPageAreaDetail, clob);
    }
    
    public ContentPageAreaClob getContentPageAreaClob(ContentPageAreaDetail contentPageAreaDetail) {
        ContentPageAreaClob contentPageAreaClob;
        
        try {
            PreparedStatement ps = ContentPageAreaClobFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contentpageareaclobs " +
                    "WHERE cntpac_cntpad_contentpageareadetailid = ?");
            
            ps.setLong(1, contentPageAreaDetail.getPrimaryKey().getEntityId());
            
            contentPageAreaClob = ContentPageAreaClobFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageAreaClob;
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Strings
    // --------------------------------------------------------------------------------
    
    public ContentPageAreaString createContentPageAreaString(ContentPageAreaDetail contentPageAreaDetail, String string) {
        return ContentPageAreaStringFactory.getInstance().create(contentPageAreaDetail, string);
    }
    
    public ContentPageAreaString getContentPageAreaString(ContentPageAreaDetail contentPageAreaDetail) {
        ContentPageAreaString contentPageAreaString;
        
        try {
            PreparedStatement ps = ContentPageAreaStringFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contentpageareastrings " +
                    "WHERE cntpas_cntpad_contentpageareadetailid = ?");
            
            ps.setLong(1, contentPageAreaDetail.getPrimaryKey().getEntityId());
            
            contentPageAreaString = ContentPageAreaStringFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageAreaString;
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Urls
    // --------------------------------------------------------------------------------
    
    public ContentPageAreaUrl createContentPageAreaUrl(ContentPageAreaDetail contentPageAreaDetail, String contentPageAreaURL) {
        return ContentPageAreaUrlFactory.getInstance().create(contentPageAreaDetail, contentPageAreaURL);
    }
    
    public ContentPageAreaUrl getContentPageAreaUrl(ContentPageAreaDetail contentPageAreaDetail) {
        ContentPageAreaUrl contentPageAreaUrl;
        
        try {
            PreparedStatement ps = ContentPageAreaUrlFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM contentpageareaurls " +
                    "WHERE cntpau_cntpad_contentpageareadetailid = ?");
            
            ps.setLong(1, contentPageAreaDetail.getPrimaryKey().getEntityId());
            
            contentPageAreaUrl = ContentPageAreaUrlFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentPageAreaUrl;
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalogs
    // --------------------------------------------------------------------------------
    
    public ContentCatalog createContentCatalog(ContentCollection contentCollection, String contentCatalogName,
            OfferUse defaultOfferUse, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        boolean defaultFound = getDefaultContentCatalog(contentCollection) != null;
        
        if(defaultFound && isDefault) {
            ContentCatalogDetailValue defaultContentCatalogDetailValue = getDefaultContentCatalogDetailValueForUpdate(contentCollection);
            
            defaultContentCatalogDetailValue.setIsDefault(Boolean.FALSE);
            updateContentCatalogFromValue(defaultContentCatalogDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ContentCatalog contentCatalog = ContentCatalogFactory.getInstance().create();
        ContentCatalogDetail contentCatalogDetail = ContentCatalogDetailFactory.getInstance().create(contentCatalog,
                contentCollection, contentCatalogName, defaultOfferUse, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contentCatalog = ContentCatalogFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentCatalog.getPrimaryKey());
        contentCatalog.setActiveDetail(contentCatalogDetail);
        contentCatalog.setLastDetail(contentCatalogDetail);
        contentCatalog.store();
        
        sendEventUsingNames(contentCatalog.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentCatalog;
    }
    
    public long countContentCatalogsByContentCollection(ContentCollection contentCollection) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentcatalogdetails " +
                "WHERE cntctdt_cntc_contentcollectionid = ? AND cntctdt_thrutime = ?",
                contentCollection, Session.MAX_TIME);
    }

    public long countContentCatalogsByDefaultOfferUse(OfferUse defaultOfferUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM contentcatalogs, contentcatalogdetails " +
                        "WHERE cntct_activedetailid = cntctdt_contentcatalogdetailid " +
                        "AND cntctdt_defaultofferuseid = ?", defaultOfferUse);
    }

    private List<ContentCatalog> getContentCatalogs(ContentCollection contentCollection, EntityPermission entityPermission) {
        List<ContentCatalog> contentCatalogs;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogs, contentcatalogdetails " +
                        "WHERE cntct_contentcatalogid = cntctdt_cntct_contentcatalogid AND cntctdt_cntc_contentcollectionid = ? AND cntctdt_thrutime = ? " +
                        "ORDER BY cntctdt_sortorder, cntctdt_contentcatalogname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogs, contentcatalogdetails " +
                        "WHERE cntct_contentcatalogid = cntctdt_cntct_contentcatalogid AND cntctdt_cntc_contentcollectionid = ? AND cntctdt_thrutime = ? " +
                        "ORDER BY cntctdt_sortorder, cntctdt_contentcatalogname " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCatalogFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCatalogs = ContentCatalogFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCatalogs;
    }
    
    public List<ContentCatalog> getContentCatalogs(ContentCollection contentCollection) {
        return getContentCatalogs(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCatalog> getContentCatalogsForUpdate(ContentCollection contentCollection) {
        return getContentCatalogs(contentCollection, EntityPermission.READ_WRITE);
    }
    
    private ContentCatalog getContentCatalogByName(ContentCollection contentCollection, String contentCatalogName, EntityPermission entityPermission) {
        ContentCatalog contentCatalog;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogs, contentcatalogdetails " +
                        "WHERE cntct_contentcatalogid = cntctdt_cntct_contentcatalogid AND cntctdt_cntc_contentcollectionid = ? " +
                        "AND cntctdt_contentcatalogname = ? AND cntctdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogs, contentcatalogdetails " +
                        "WHERE cntct_contentcatalogid = cntctdt_cntct_contentcatalogid AND cntctdt_cntc_contentcollectionid = ? " +
                        "AND cntctdt_contentcatalogname = ? AND cntctdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCatalogFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setString(2, contentCatalogName);
            ps.setLong(3, Session.MAX_TIME);
            
            contentCatalog = ContentCatalogFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCatalog;
    }
    
    public ContentCatalog getContentCatalogByName(ContentCollection contentCollection, String contentCatalogName) {
        return getContentCatalogByName(contentCollection, contentCatalogName, EntityPermission.READ_ONLY);
    }
    
    public ContentCatalog getContentCatalogByNameForUpdate(ContentCollection contentCollection, String contentCatalogName) {
        return getContentCatalogByName(contentCollection, contentCatalogName, EntityPermission.READ_WRITE);
    }
    
    public ContentCatalogDetailValue getContentCatalogDetailValueForUpdate(ContentCatalog contentCatalog) {
        return contentCatalog == null? null: contentCatalog.getLastDetailForUpdate().getContentCatalogDetailValue().clone();
    }
    
    public ContentCatalogDetailValue getContentCatalogDetailValueByNameForUpdate(ContentCollection contentCollection, String contentCatalogName) {
        return getContentCatalogDetailValueForUpdate(getContentCatalogByNameForUpdate(contentCollection, contentCatalogName));
    }
    
    private ContentCatalog getDefaultContentCatalog(ContentCollection contentCollection, EntityPermission entityPermission) {
        ContentCatalog contentCatalog;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogs, contentcatalogdetails " +
                        "WHERE cntct_contentcatalogid = cntctdt_cntct_contentcatalogid AND cntctdt_cntc_contentcollectionid = ? " +
                        "AND cntctdt_isdefault = 1 AND cntctdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogs, contentcatalogdetails " +
                        "WHERE cntct_contentcatalogid = cntctdt_cntct_contentcatalogid AND cntctdt_cntc_contentcollectionid = ? " +
                        "AND cntctdt_isdefault = 1 AND cntctdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCatalogFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCatalog = ContentCatalogFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCatalog;
    }
    
    public ContentCatalog getDefaultContentCatalog(ContentCollection contentCollection) {
        return getDefaultContentCatalog(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public ContentCatalogDetailValue getDefaultContentCatalogDetailValueForUpdate(ContentCollection contentCollection) {
        return getDefaultContentCatalog(contentCollection, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentCatalogDetailValue().clone();
    }
    
    public ContentCatalogChoicesBean getContentCatalogChoices(ContentCollection contentCollection, String defaultContentCatalogChoice, Language language) {
        List<ContentCatalog> contentCatalogs = getContentCatalogs(contentCollection);
        var size = contentCatalogs.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var contentCatalog : contentCatalogs) {
            ContentCatalogDetail contentCatalogDetail = contentCatalog.getLastDetail();
            
            var label = getBestContentCatalogDescription(contentCatalog, language);
            var value = contentCatalogDetail.getContentCatalogName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContentCatalogChoice != null && defaultContentCatalogChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contentCatalogDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new ContentCatalogChoicesBean(labels, values, defaultValue);
    }
    
    public ContentCatalogTransfer getContentCatalogTransfer(UserVisit userVisit, ContentCatalog contentCatalog) {
        return getContentTransferCaches(userVisit).getContentCatalogTransferCache().getContentCatalogTransfer(contentCatalog);
    }
    
    public List<ContentCatalogTransfer> getContentCatalogTransfers(UserVisit userVisit, Collection<ContentCatalog> contentCatalogs) {
        List<ContentCatalogTransfer> contentCatalogTransfers = new ArrayList<>(contentCatalogs.size());
        ContentCatalogTransferCache contentCatalogTransferCache = getContentTransferCaches(userVisit).getContentCatalogTransferCache();

        contentCatalogs.forEach((contentCatalog) ->
                contentCatalogTransfers.add(contentCatalogTransferCache.getContentCatalogTransfer(contentCatalog))
        );
        
        return contentCatalogTransfers;
    }
    
    public List<ContentCatalogTransfer> getContentCatalogTransfers(UserVisit userVisit, ContentCollection contentCollection) {
        return getContentCatalogTransfers(userVisit, getContentCatalogs(contentCollection));
    }
    
    private void updateContentCatalogFromValue(ContentCatalogDetailValue contentCatalogDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contentCatalogDetailValue.hasBeenModified()) {
            ContentCatalog contentCatalog = ContentCatalogFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentCatalogDetailValue.getContentCatalogPK());
            ContentCatalogDetail contentCatalogDetail = contentCatalog.getActiveDetailForUpdate();

            contentCatalogDetail.setThruTime(session.START_TIME_LONG);
            contentCatalogDetail.store();

            ContentCatalogPK contentCatalogPK = contentCatalogDetail.getContentCatalogPK();
            ContentCollection contentCollection = contentCatalogDetail.getContentCollection();
            ContentCollectionPK contentCollectionPK = contentCollection.getPrimaryKey();
            String contentCatalogName = contentCatalogDetailValue.getContentCatalogName();
            OfferUsePK defaultOfferUsePK = contentCatalogDetailValue.getDefaultOfferUsePK();
            Boolean isDefault = contentCatalogDetailValue.getIsDefault();
            Integer sortOrder = contentCatalogDetailValue.getSortOrder();

            if(checkDefault) {
                ContentCatalog defaultContentCatalog = getDefaultContentCatalog(contentCollection);
                boolean defaultFound = defaultContentCatalog != null && !defaultContentCatalog.equals(contentCatalog);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ContentCatalogDetailValue defaultContentCatalogDetailValue = getDefaultContentCatalogDetailValueForUpdate(contentCollection);

                    defaultContentCatalogDetailValue.setIsDefault(Boolean.FALSE);
                    updateContentCatalogFromValue(defaultContentCatalogDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            contentCatalogDetail = ContentCatalogDetailFactory.getInstance().create(contentCatalogPK, contentCollectionPK,
                    contentCatalogName, defaultOfferUsePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            contentCatalog.setActiveDetail(contentCatalogDetail);
            contentCatalog.setLastDetail(contentCatalogDetail);
            contentCatalog.store();

            sendEventUsingNames(contentCatalogPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateContentCatalogFromValue(ContentCatalogDetailValue contentCatalogDetailValue, BasePK updatedBy) {
        updateContentCatalogFromValue(contentCatalogDetailValue, true, updatedBy);
    }
    
    public void deleteContentCatalog(ContentCatalog contentCatalog, BasePK deletedBy) {
        // ContentLogic not used since everything is being deleted from the ContentCatalog anyway.
        // Items are deleted separately to avoid duplicate keye errors due to the pricing for the same item
        // being adjusted multiple times.
        deleteContentCatalogItemsByContentCatalog(contentCatalog, deletedBy);
        deleteContentCategoriesByContentCatalog(contentCatalog, deletedBy);
        deleteContentCatalogDescriptionsByContentCatalog(contentCatalog, deletedBy);
        
        ContentCatalogDetail contentCatalogDetail = contentCatalog.getLastDetailForUpdate();
        contentCatalogDetail.setThruTime(session.START_TIME_LONG);
        contentCatalogDetail.store();
        contentCatalog.setActiveDetail(null);
        
        ContentCollection contentCollection = contentCatalogDetail.getContentCollection();
        ContentCatalog defaultContentCatalog = getDefaultContentCatalog(contentCollection);
        if(defaultContentCatalog == null) {
            List<ContentCatalog> contentCatalogs = getContentCatalogsForUpdate(contentCollection);
            
            if(!contentCatalogs.isEmpty()) {
                defaultContentCatalog = contentCatalogs.iterator().next();
                ContentCatalogDetailValue contentCatalogDetailValue = Objects.requireNonNull(defaultContentCatalog).getLastDetailForUpdate().getContentCatalogDetailValue().clone();
                
                contentCatalogDetailValue.setIsDefault(Boolean.TRUE);
                updateContentCatalogFromValue(contentCatalogDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(contentCatalog.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentCatalogsByContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        getContentCatalogsForUpdate(contentCollection).stream().forEach((contentCatalog) -> {
            deleteContentCatalog(contentCatalog, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Descriptions
    // --------------------------------------------------------------------------------
    
    public ContentCatalogDescription createContentCatalogDescription(ContentCatalog contentCatalog, Language language, String description, BasePK createdBy) {
        
        ContentCatalogDescription contentCatalogDescription = ContentCatalogDescriptionFactory.getInstance().create(contentCatalog, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contentCatalog.getPrimaryKey(), EventTypes.MODIFY, contentCatalogDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentCatalogDescription;
    }
    
    private ContentCatalogDescription getContentCatalogDescription(ContentCatalog contentCatalog, Language language, EntityPermission entityPermission) {
        ContentCatalogDescription contentCatalogDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogdescriptions " +
                        "WHERE cntctd_cntct_contentcatalogid = ? AND cntctd_lang_languageid = ? AND cntctd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogdescriptions " +
                        "WHERE cntctd_cntct_contentcatalogid = ? AND cntctd_lang_languageid = ? AND cntctd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCatalogDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCatalog.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            contentCatalogDescription = ContentCatalogDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCatalogDescription;
    }
    
    public ContentCatalogDescription getContentCatalogDescription(ContentCatalog contentCatalog, Language language) {
        return getContentCatalogDescription(contentCatalog, language, EntityPermission.READ_ONLY);
    }
    
    public ContentCatalogDescription getContentCatalogDescriptionForUpdate(ContentCatalog contentCatalog, Language language) {
        return getContentCatalogDescription(contentCatalog, language, EntityPermission.READ_WRITE);
    }
    
    public ContentCatalogDescriptionValue getContentCatalogDescriptionValue(ContentCatalogDescription contentCatalogDescription) {
        return contentCatalogDescription == null? null: contentCatalogDescription.getContentCatalogDescriptionValue().clone();
    }
    
    public ContentCatalogDescriptionValue getContentCatalogDescriptionValueForUpdate(ContentCatalog contentCatalog, Language language) {
        return getContentCatalogDescriptionValue(getContentCatalogDescriptionForUpdate(contentCatalog, language));
    }
    
    private List<ContentCatalogDescription> getContentCatalogDescriptionsByContentCatalog(ContentCatalog contentCatalog, EntityPermission entityPermission) {
        List<ContentCatalogDescription> contentCatalogDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogdescriptions, languages " +
                        "WHERE cntctd_cntct_contentcatalogid = ? AND cntctd_thrutime = ? AND cntctd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogdescriptions " +
                        "WHERE cntctd_cntct_contentcatalogid = ? AND cntctd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCatalogDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCatalog.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCatalogDescriptions = ContentCatalogDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCatalogDescriptions;
    }
    
    public List<ContentCatalogDescription> getContentCatalogDescriptionsByContentCatalog(ContentCatalog contentCatalog) {
        return getContentCatalogDescriptionsByContentCatalog(contentCatalog, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCatalogDescription> getContentCatalogDescriptionsByContentCatalogForUpdate(ContentCatalog contentCatalog) {
        return getContentCatalogDescriptionsByContentCatalog(contentCatalog, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentCatalogDescription(ContentCatalog contentCatalog, Language language) {
        String description;
        ContentCatalogDescription contentCatalogDescription = getContentCatalogDescription(contentCatalog, language);
        
        if(contentCatalogDescription == null && !language.getIsDefault()) {
            contentCatalogDescription = getContentCatalogDescription(contentCatalog, getPartyControl().getDefaultLanguage());
        }
        
        if(contentCatalogDescription == null) {
            description = contentCatalog.getLastDetail().getContentCatalogName();
        } else {
            description = contentCatalogDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentCatalogDescriptionTransfer getContentCatalogDescriptionTransfer(UserVisit userVisit, ContentCatalogDescription contentCatalogDescription) {
        return getContentTransferCaches(userVisit).getContentCatalogDescriptionTransferCache().getContentCatalogDescriptionTransfer(contentCatalogDescription);
    }
    
    public List<ContentCatalogDescriptionTransfer> getContentCatalogDescriptionTransfers(UserVisit userVisit, ContentCatalog contentCatalog) {
        List<ContentCatalogDescription> contentCatalogDescriptions = getContentCatalogDescriptionsByContentCatalog(contentCatalog);
        List<ContentCatalogDescriptionTransfer> contentCatalogDescriptionTransfers = new ArrayList<>(contentCatalogDescriptions.size());
        ContentCatalogDescriptionTransferCache contentCatalogDescriptionTransferCache = getContentTransferCaches(userVisit).getContentCatalogDescriptionTransferCache();

        contentCatalogDescriptions.forEach((contentCatalogDescription) ->
                contentCatalogDescriptionTransfers.add(contentCatalogDescriptionTransferCache.getContentCatalogDescriptionTransfer(contentCatalogDescription))
        );
        
        return contentCatalogDescriptionTransfers;
    }
    
    public void updateContentCatalogDescriptionFromValue(ContentCatalogDescriptionValue contentCatalogDescriptionValue, BasePK updatedBy) {
        if(contentCatalogDescriptionValue.hasBeenModified()) {
            ContentCatalogDescription contentCatalogDescription = ContentCatalogDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentCatalogDescriptionValue.getPrimaryKey());
            
            contentCatalogDescription.setThruTime(session.START_TIME_LONG);
            contentCatalogDescription.store();
            
            ContentCatalog contentCatalog = contentCatalogDescription.getContentCatalog();
            Language language = contentCatalogDescription.getLanguage();
            String description = contentCatalogDescriptionValue.getDescription();
            
            contentCatalogDescription = ContentCatalogDescriptionFactory.getInstance().create(contentCatalog, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contentCatalog.getPrimaryKey(), EventTypes.MODIFY, contentCatalogDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentCatalogDescription(ContentCatalogDescription contentCatalogDescription, BasePK deletedBy) {
        contentCatalogDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contentCatalogDescription.getContentCatalogPK(), EventTypes.MODIFY, contentCatalogDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentCatalogDescriptionsByContentCatalog(ContentCatalog contentCatalog, BasePK deletedBy) {
        getContentCatalogDescriptionsByContentCatalogForUpdate(contentCatalog).stream().forEach((contentCatalogDescription) -> {
            deleteContentCatalogDescription(contentCatalogDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Items
    // --------------------------------------------------------------------------------
    
    public ContentCatalogItem createContentCatalogItem(ContentCatalog contentCatalog, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency, BasePK createdBy) {
        ContentCatalogItem contentCatalogItem = ContentCatalogItemFactory.getInstance().create(contentCatalog, item,
                inventoryCondition, unitOfMeasureType, currency, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contentCatalog.getPrimaryKey(), EventTypes.MODIFY, contentCatalogItem.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentCatalogItem;
    }
    
    public long countContentCatalogItemsByContentCatalog(ContentCatalog contentCatalog) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentcatalogitems " +
                "WHERE cntcti_cntct_contentcatalogid = ? AND cntcti_thrutime = ?",
                contentCatalog, Session.MAX_TIME);
    }

    private List<ContentCatalogItem> getContentCatalogItemsByContentCatalog(ContentCatalog contentCatalog, EntityPermission entityPermission) {
        List<ContentCatalogItem> contentCatalogItems = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems, items, itemdetails, inventoryconditions, inventoryconditiondetails, unitofmeasuretypes, unitofmeasuretypedetails, currencies " +
                        "WHERE cntcti_cntct_contentcatalogid = ? AND cntcti_thrutime = ? " +
                        "AND cntcti_itm_itemid = itm_lastdetailid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND cntcti_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND cntcti_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND cntcti_cur_currencyid = cur_currencyid " +
                        "ORDER BY itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems " +
                        "WHERE cntcti_cntct_contentcatalogid = ? AND cntcti_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ContentCatalogItemFactory.getInstance().prepareStatement(query);

            ps.setLong(1, contentCatalog.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            contentCatalogItems = ContentCatalogItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contentCatalogItems;
    }

    public List<ContentCatalogItem> getContentCatalogItemsByContentCatalog(ContentCatalog contentCatalog) {
        return getContentCatalogItemsByContentCatalog(contentCatalog, EntityPermission.READ_ONLY);
    }

    public List<ContentCatalogItem> getContentCatalogItemsByContentCatalogForUpdate(ContentCatalog contentCatalog) {
        return getContentCatalogItemsByContentCatalog(contentCatalog, EntityPermission.READ_WRITE);
    }

    private List<ContentCatalogItem> getContentCatalogItemsByContentCatalogAndItem(ContentCatalog contentCatalog, Item item,
            EntityPermission entityPermission) {
        List<ContentCatalogItem> contentCatalogItems = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems, inventoryconditions, inventoryconditiondetails, unitofmeasuretypes, unitofmeasuretypedetails, currencies " +
                        "WHERE cntcti_cntct_contentcatalogid = ? AND cntcti_itm_itemid = ? AND cntcti_thrutime = ? " +
                        "AND cntcti_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND cntcti_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND cntcti_cur_currencyid = cur_currencyid " +
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems " +
                        "WHERE cntcti_cntct_contentcatalogid = ? AND cntcti_itm_itemid = ? AND cntcti_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ContentCatalogItemFactory.getInstance().prepareStatement(query);

            ps.setLong(1, contentCatalog.getPrimaryKey().getEntityId());
            ps.setLong(2, item.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            contentCatalogItems = ContentCatalogItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contentCatalogItems;
    }

    public List<ContentCatalogItem> getContentCatalogItemsByContentCatalogAndItem(ContentCatalog contentCatalog, Item item) {
        return getContentCatalogItemsByContentCatalogAndItem(contentCatalog, item, EntityPermission.READ_ONLY);
    }

    public List<ContentCatalogItem> getContentCatalogItemsByContentCatalogAndItemForUpdate(ContentCatalog contentCatalog, Item item) {
        return getContentCatalogItemsByContentCatalogAndItem(contentCatalog, item, EntityPermission.READ_WRITE);
    }

    private List<ContentCatalogItem> getContentCatalogItemsByItem(Item item, EntityPermission entityPermission) {
        List<ContentCatalogItem> contentCatalogItems;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems, contentcollections, contentcollectiondetails, contentcatalogs, contentcatalogdetails, " +
                        "inventoryconditions, inventoryconditiondetails, unitofmeasuretypes, unitofmeasuretypedetails, currencies " +
                        "WHERE cntcti_itm_itemid = ? AND cntcti_thrutime = ? " +
                        "AND cntcti_cntct_contentcatalogid = cntct_contentcatalogid AND cntct_lastdetailid = cntctdt_contentcatalogdetailid " +
                        "AND cntctdt_cntc_contentcollectionid = cntc_contentcollectionid AND cntc_lastdetailid = cntcdt_contentcollectiondetailid " +
                        "AND cntcti_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND cntcti_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND cntcti_cur_currencyid = cur_currencyid " +
                        "ORDER BY cntcdt_contentcollectionname, cntctdt_sortorder, cntctdt_contentcatalogname, invcondt_sortorder, " +
                        "invcondt_inventoryconditionname, uomtdt_sortorder, uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems " +
                        "WHERE cntcti_itm_itemid = ? AND cntcti_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCatalogItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCatalogItems = ContentCatalogItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCatalogItems;
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByItem(Item item) {
        return getContentCatalogItemsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByItemForUpdate(Item item) {
        return getContentCatalogItemsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ContentCatalogItem> getContentCatalogItemsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        List<ContentCatalogItem> contentCatalogItems;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems, contentcollections, contentcollectiondetails, contentcatalogs, contentcatalogdetails, " +
                        "items, itemdetails, unitofmeasuretypes, unitofmeasuretypedetails, currencies " +
                        "WHERE cntcti_invcon_inventoryconditionid = ? AND cntcti_thrutime = ? " +
                        "AND cntcti_cntct_contentcatalogid = cntct_contentcatalogid AND cntct_lastdetailid = cntctdt_contentcatalogdetailid " +
                        "AND cntctdt_cntc_contentcollectionid = cntc_contentcollectionid AND cntc_lastdetailid = cntcdt_contentcollectiondetailid " +
                        "AND cntcti_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND cntcti_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND cntcti_cur_currencyid = cur_currencyid " +
                        "ORDER BY cntcdt_contentcollectionname, cntctdt_sortorder, cntctdt_contentcatalogname, itmdt_itemname, " +
                        "uomtdt_sortorder, uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems " +
                        "WHERE cntcti_invcon_inventoryconditionid = ? AND cntcti_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCatalogItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCatalogItems = ContentCatalogItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCatalogItems;
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getContentCatalogItemsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getContentCatalogItemsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ContentCatalogItem> getContentCatalogItemsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<ContentCatalogItem> contentCatalogItems;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems, contentcollections, contentcollectiondetails, contentcatalogs, contentcatalogdetails, " +
                        "items, itemdetails, inventoryconditions, inventoryconditiondetails, currencies " +
                        "WHERE cntcti_uomt_unitofmeasuretypeid = ? AND cntcti_thrutime = ? " +
                        "AND cntcti_cntct_contentcatalogid = cntct_contentcatalogid AND cntct_lastdetailid = cntctdt_contentcatalogdetailid " +
                        "AND cntctdt_cntc_contentcollectionid = cntc_contentcollectionid AND cntc_lastdetailid = cntcdt_contentcollectiondetailid " +
                        "AND cntcti_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND cntcti_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND cntcti_cur_currencyid = cur_currencyid " +
                        "ORDER BY cntcdt_contentcollectionname, cntctdt_sortorder, cntctdt_contentcatalogname, itmdt_itemname, " +
                        "invcondt_sortorder, invcondt_inventoryconditionname, cur_sortorder, cur_currencyisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems " +
                        "WHERE cntcti_uomt_unitofmeasuretypeid = ? AND cntcti_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCatalogItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCatalogItems = ContentCatalogItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCatalogItems;
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getContentCatalogItemsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getContentCatalogItemsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private ContentCatalogItem getContentCatalogItem(ContentCatalog contentCatalog, Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        ContentCatalogItem contentCatalogItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems " +
                        "WHERE cntcti_cntct_contentcatalogid = ? AND cntcti_itm_itemid = ? AND cntcti_invcon_inventoryconditionid = ? AND cntcti_uomt_unitofmeasuretypeid = ? " +
                        "AND cntcti_cur_currencyid = ? AND cntcti_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitems " +
                        "WHERE cntcti_cntct_contentcatalogid = ? AND cntcti_itm_itemid = ? AND cntcti_invcon_inventoryconditionid = ? AND cntcti_uomt_unitofmeasuretypeid = ? " +
                        "AND cntcti_cur_currencyid = ? AND cntcti_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCatalogItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCatalog.getPrimaryKey().getEntityId());
            ps.setLong(2, item.getPrimaryKey().getEntityId());
            ps.setLong(3, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(4, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(5, currency.getPrimaryKey().getEntityId());
            ps.setLong(6, Session.MAX_TIME);
            
            contentCatalogItem = ContentCatalogItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCatalogItem;
    }
    
    public ContentCatalogItem getContentCatalogItem(ContentCatalog contentCatalog, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getContentCatalogItem(contentCatalog, item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }
    
    public ContentCatalogItem getContentCatalogItemForUpdate(ContentCatalog contentCatalog, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getContentCatalogItem(contentCatalog, item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }
    
    public List<ContentCatalogItemTransfer> getContentCatalogItemTransfers(UserVisit userVisit, Collection<ContentCatalogItem> contentCatalogItems) {
        List<ContentCatalogItemTransfer> contentCatalogItemTransfers = new ArrayList<>(contentCatalogItems.size());
        ContentCatalogItemTransferCache contentCatalogItemTransferCache = getContentTransferCaches(userVisit).getContentCatalogItemTransferCache();

        contentCatalogItems.forEach((contentCatalogItem) ->
                contentCatalogItemTransfers.add(contentCatalogItemTransferCache.getContentCatalogItemTransfer(contentCatalogItem))
        );

        return contentCatalogItemTransfers;
    }

    public ContentCatalogItemTransfer getContentCatalogItemTransfer(UserVisit userVisit, ContentCatalogItem contentCatalogItem) {
        return getContentTransferCaches(userVisit).getContentCatalogItemTransferCache().getContentCatalogItemTransfer(contentCatalogItem);
    }

    public List<ContentCatalogItemTransfer> getContentCatalogItemTransfers(UserVisit userVisit, ContentCatalog contentCatalog) {
        return getContentCatalogItemTransfers(userVisit, getContentCatalogItemsByContentCatalog(contentCatalog));
    }
    
    public void deleteContentCatalogItem(ContentCatalogItem contentCatalogItem, BasePK deletedBy) {
        Item item = contentCatalogItem.getItem();
        String itemPriceTypeName = item.getLastDetail().getItemPriceType().getItemPriceTypeName();

        deleteContentCategoryItemsByContentCatalogItem(contentCatalogItem, deletedBy);

        if(ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
            ContentCatalogItemFixedPrice contentCatalogItemFixedPrice = getContentCatalogItemFixedPriceForUpdate(contentCatalogItem);
            
            if(contentCatalogItemFixedPrice != null) {
                deleteContentCatalogItemFixedPrice(contentCatalogItemFixedPrice, deletedBy);
            }
        } else if(ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
            ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = getContentCatalogItemVariablePriceForUpdate(contentCatalogItem);

            if(contentCatalogItemVariablePrice != null) {
                deleteContentCatalogItemVariablePrice(contentCatalogItemVariablePrice, deletedBy);
            }
        }
        
        contentCatalogItem.setThruTime(session.START_TIME_LONG);
        contentCatalogItem.store();
        
        sendEventUsingNames(contentCatalogItem.getContentCatalogPK(), EventTypes.MODIFY, contentCatalogItem.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContentCatalogItems(List<ContentCatalogItem> contentCatalogItems, BasePK deletedBy) {
        contentCatalogItems.forEach((contentCatalogItem) -> 
                deleteContentCatalogItem(contentCatalogItem, deletedBy)
        );
    }
    
    public void deleteContentCatalogItemsByContentCatalog(ContentCatalog contentCatalog, BasePK deletedBy) {
        deleteContentCatalogItems(getContentCatalogItemsByContentCatalogForUpdate(contentCatalog), deletedBy);
    }

    public void deleteContentCatalogItemsByItem(Item item, BasePK deletedBy) {
        deleteContentCatalogItems(getContentCatalogItemsByItemForUpdate(item), deletedBy);
    }

    public void deleteContentCatalogItemsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteContentCatalogItems(getContentCatalogItemsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }
    
    public void deleteContentCatalogItemsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteContentCatalogItems(getContentCatalogItemsByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Item Fixed Prices
    // --------------------------------------------------------------------------------

    public ContentCatalogItemFixedPrice createContentCatalogItemFixedPrice(ContentCatalogItem contentCatalogItem, Long unitPrice,
            BasePK createdBy) {
        ContentCatalogItemFixedPrice contentCatalogItemFixedPrice = ContentCatalogItemFixedPriceFactory.getInstance().create(session,
                contentCatalogItem, unitPrice, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(contentCatalogItem.getContentCatalogPK(), EventTypes.MODIFY, contentCatalogItemFixedPrice.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return contentCatalogItemFixedPrice;
    }

    public ContentCatalogItemFixedPriceValue getContentCatalogItemFixedPriceValueByPKForUpdate(ContentCatalogItemFixedPricePK contentCatalogItemFixedPricePK) {
        return ContentCatalogItemFixedPriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                contentCatalogItemFixedPricePK).getContentCatalogItemFixedPriceValue().clone();
    }

    private ContentCatalogItemFixedPrice getContentCatalogItemFixedPrice(ContentCatalogItem contentCatalogItem, EntityPermission entityPermission) {
        ContentCatalogItemFixedPrice contentCatalogItemFixedPrice = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitemfixedprices " +
                        "WHERE cntctifp_cntcti_contentcatalogitemid = ? AND cntctifp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitemfixedprices " +
                        "WHERE cntctifp_cntcti_contentcatalogitemid = ? AND cntctifp_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ContentCatalogItemFixedPriceFactory.getInstance().prepareStatement(query);

            ps.setLong(1, contentCatalogItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            contentCatalogItemFixedPrice = ContentCatalogItemFixedPriceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contentCatalogItemFixedPrice;
    }

    public ContentCatalogItemFixedPrice getContentCatalogItemFixedPrice(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemFixedPrice(contentCatalogItem, EntityPermission.READ_ONLY);
    }

    public ContentCatalogItemFixedPrice getContentCatalogItemFixedPriceForUpdate(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemFixedPrice(contentCatalogItem, EntityPermission.READ_WRITE);
    }

    public ContentCatalogItemFixedPriceValue getContentCatalogItemFixedPriceValue(ContentCatalogItemFixedPrice contentCatalogItemFixedPrice) {
        return contentCatalogItemFixedPrice == null? null: contentCatalogItemFixedPrice.getContentCatalogItemFixedPriceValue().clone();
    }

    public ContentCatalogItemFixedPriceValue getContentCatalogItemFixedPriceValueForUpdate(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemFixedPriceValue(getContentCatalogItemFixedPriceForUpdate(contentCatalogItem));
    }

    public void updateContentCatalogItemFixedPriceFromValue(ContentCatalogItemFixedPriceValue contentCatalogItemFixedPriceValue, BasePK updatedBy) {
        if(contentCatalogItemFixedPriceValue.hasBeenModified()) {
            ContentCatalogItemFixedPrice contentCatalogItemFixedPrice = ContentCatalogItemFixedPriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contentCatalogItemFixedPriceValue.getPrimaryKey());

            contentCatalogItemFixedPrice.setThruTime(session.START_TIME_LONG);
            contentCatalogItemFixedPrice.store();

            ContentCatalogItemPK contentCatalogItemPK = contentCatalogItemFixedPrice.getContentCatalogItemPK();
            Long unitPrice = contentCatalogItemFixedPriceValue.getUnitPrice();

            contentCatalogItemFixedPrice = ContentCatalogItemFixedPriceFactory.getInstance().create(contentCatalogItemPK,
                    unitPrice, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(contentCatalogItemFixedPrice.getContentCatalogItem().getContentCatalogPK(), EventTypes.MODIFY, contentCatalogItemFixedPrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteContentCatalogItemFixedPrice(ContentCatalogItemFixedPrice contentCatalogItemFixedPrice, BasePK deletedBy) {
        contentCatalogItemFixedPrice.setThruTime(session.START_TIME_LONG);
        contentCatalogItemFixedPrice.store();

        sendEventUsingNames(contentCatalogItemFixedPrice.getContentCatalogItem().getContentCatalogPK(), EventTypes.MODIFY, contentCatalogItemFixedPrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Content Catalog Item Variable Prices
    // --------------------------------------------------------------------------------

    public ContentCatalogItemVariablePrice createContentCatalogItemVariablePrice(ContentCatalogItem contentCatalogItem, Long minimumUnitPrice,
            Long maximumUnitPrice, Long unitPriceIncrement, BasePK createdBy) {
        ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = ContentCatalogItemVariablePriceFactory.getInstance().create(session,
                contentCatalogItem, minimumUnitPrice, maximumUnitPrice, unitPriceIncrement, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(contentCatalogItem.getContentCatalogPK(), EventTypes.MODIFY, contentCatalogItemVariablePrice.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return contentCatalogItemVariablePrice;
    }

    public ContentCatalogItemVariablePriceValue getContentCatalogItemVariablePriceValueByPKForUpdate(ContentCatalogItemVariablePricePK contentCatalogItemVariablePricePK) {
        return ContentCatalogItemVariablePriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                contentCatalogItemVariablePricePK).getContentCatalogItemVariablePriceValue().clone();
    }

    private ContentCatalogItemVariablePrice getContentCatalogItemVariablePrice(ContentCatalogItem contentCatalogItem, EntityPermission entityPermission) {
        ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitemvariableprices " +
                        "WHERE cntctivp_cntcti_contentcatalogitemid = ? AND cntctivp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcatalogitemvariableprices " +
                        "WHERE cntctivp_cntcti_contentcatalogitemid = ? AND cntctivp_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ContentCatalogItemVariablePriceFactory.getInstance().prepareStatement(query);

            ps.setLong(1, contentCatalogItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            contentCatalogItemVariablePrice = ContentCatalogItemVariablePriceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contentCatalogItemVariablePrice;
    }

    public ContentCatalogItemVariablePrice getContentCatalogItemVariablePrice(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemVariablePrice(contentCatalogItem, EntityPermission.READ_ONLY);
    }

    public ContentCatalogItemVariablePrice getContentCatalogItemVariablePriceForUpdate(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemVariablePrice(contentCatalogItem, EntityPermission.READ_WRITE);
    }

    public ContentCatalogItemVariablePriceValue getContentCatalogItemVariablePriceValue(ContentCatalogItemVariablePrice contentCatalogItemVariablePrice) {
        return contentCatalogItemVariablePrice == null? null: contentCatalogItemVariablePrice.getContentCatalogItemVariablePriceValue().clone();
    }

    public ContentCatalogItemVariablePriceValue getContentCatalogItemVariablePriceValueForUpdate(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemVariablePriceValue(getContentCatalogItemVariablePriceForUpdate(contentCatalogItem));
    }

    public void updateContentCatalogItemVariablePriceFromValue(ContentCatalogItemVariablePriceValue contentCatalogItemVariablePriceValue, BasePK updatedBy) {
        if(contentCatalogItemVariablePriceValue.hasBeenModified()) {
            ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = ContentCatalogItemVariablePriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contentCatalogItemVariablePriceValue.getPrimaryKey());

            contentCatalogItemVariablePrice.setThruTime(session.START_TIME_LONG);
            contentCatalogItemVariablePrice.store();

            ContentCatalogItemPK contentCatalogItemPK = contentCatalogItemVariablePrice.getContentCatalogItemPK();
            Long maximumUnitPrice = contentCatalogItemVariablePriceValue.getMaximumUnitPrice();
            Long minimumUnitPrice = contentCatalogItemVariablePriceValue.getMinimumUnitPrice();
            Long unitPriceIncrement = contentCatalogItemVariablePriceValue.getUnitPriceIncrement();

            contentCatalogItemVariablePrice = ContentCatalogItemVariablePriceFactory.getInstance().create(contentCatalogItemPK, maximumUnitPrice,
                    minimumUnitPrice, unitPriceIncrement, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(contentCatalogItemVariablePrice.getContentCatalogItem().getContentCatalogPK(), EventTypes.MODIFY, contentCatalogItemVariablePrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteContentCatalogItemVariablePrice(ContentCatalogItemVariablePrice contentCatalogItemVariablePrice, BasePK deletedBy) {
        contentCatalogItemVariablePrice.setThruTime(session.START_TIME_LONG);
        contentCatalogItemVariablePrice.store();

        sendEventUsingNames(contentCatalogItemVariablePrice.getContentCatalogItem().getContentCatalogPK(), EventTypes.MODIFY, contentCatalogItemVariablePrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Content Categories
    // --------------------------------------------------------------------------------
    
    public ContentCategory createContentCategory(ContentCatalog contentCatalog, String contentCategoryName,
            ContentCategory parentContentCategory, OfferUse defaultOfferUse, Selector itemSelector, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        ContentCategory defaultContentCategory = getDefaultContentCategory(contentCatalog);
        boolean defaultFound = defaultContentCategory != null;
        
        if(defaultFound) {
            if(defaultContentCategory.getLastDetail().getContentCategoryName().equals(ContentCategories.ROOT.toString()))
                isDefault = Boolean.TRUE;
        }
        
        if(defaultFound && isDefault) {
            ContentCategoryDetailValue defaultContentCategoryDetailValue = getDefaultContentCategoryDetailValueForUpdate(contentCatalog);
            
            defaultContentCategoryDetailValue.setIsDefault(Boolean.FALSE);
            updateContentCategoryFromValue(defaultContentCategoryDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ContentCategory contentCategory = ContentCategoryFactory.getInstance().create();
        ContentCategoryDetail contentCategoryDetail = ContentCategoryDetailFactory.getInstance().create(contentCategory,
                contentCatalog, contentCategoryName, parentContentCategory, defaultOfferUse, itemSelector, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contentCategory = ContentCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentCategory.getPrimaryKey());
        contentCategory.setActiveDetail(contentCategoryDetail);
        contentCategory.setLastDetail(contentCategoryDetail);
        contentCategory.store();
        
        sendEventUsingNames(contentCategory.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentCategory;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.ContentCategory */
    public ContentCategory getContentCategoryByEntityInstance(EntityInstance entityInstance) {
        ContentCategoryPK pk = new ContentCategoryPK(entityInstance.getEntityUniqueId());
        ContentCategory contentCategory = ContentCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return contentCategory;
    }
    
    public long countContentCategories() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentcategories, contentcategorydetails " +
                "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid");
    }

    public long countContentCategoriesByContentCatalog(ContentCatalog contentCatalog) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentcategories, contentcategorydetails " +
                "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_cntct_contentcatalogid = ?",
                contentCatalog);
    }

    public long countContentCategoriesByParentContentCategory(ContentCategory parentContentCategory) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentcategories, contentcategorydetails " +
                "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_parentcontentcategoryid = ?",
                parentContentCategory);
    }

    public long countContentCategoriesByDefaultOfferUse(OfferUse defaultOfferUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM contentcategories, contentcategorydetails " +
                        "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid " +
                        "AND cntcgdt_defaultofferuseid = ?", defaultOfferUse);
    }

    private List<ContentCategory> getContentCategories(ContentCatalog contentCatalog, EntityPermission entityPermission) {
        List<ContentCategory> contentCategories;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategories, contentcategorydetails " +
                        "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_cntct_contentcatalogid = ? " +
                        "ORDER BY cntcgdt_sortorder, cntcgdt_contentcategoryname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategories, contentcategorydetails " +
                        "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_cntct_contentcatalogid = ? " +
                        "ORDER BY cntcgdt_sortorder, cntcgdt_contentcategoryname " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCatalog.getPrimaryKey().getEntityId());
            
            contentCategories = ContentCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCategories;
    }
    
    public List<ContentCategory> getContentCategories(ContentCatalog contentCatalog) {
        return getContentCategories(contentCatalog, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCategory> getContentCategoriesForUpdate(ContentCatalog contentCatalog) {
        return getContentCategories(contentCatalog, EntityPermission.READ_WRITE);
    }
    
    private List<ContentCategory> getContentCategoriesByParentContentCategory(ContentCategory parentContentCategory, EntityPermission entityPermission) {
        List<ContentCategory> contentCategories = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategories, contentcategorydetails " +
                        "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_parentcontentcategoryid = ? " +
                        "ORDER BY cntcgdt_sortorder, cntcgdt_contentcategoryname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategories, contentcategorydetails " +
                        "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_parentcontentcategoryid = ? " +
                        "ORDER BY cntcgdt_sortorder, cntcgdt_contentcategoryname " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = ContentCategoryFactory.getInstance().prepareStatement(query);

            ps.setLong(1, parentContentCategory.getPrimaryKey().getEntityId());

            contentCategories = ContentCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contentCategories;
    }

    public List<ContentCategory> getContentCategoriesByParentContentCategory(ContentCategory parentContentCategory) {
        return getContentCategoriesByParentContentCategory(parentContentCategory, EntityPermission.READ_ONLY);
    }

    public List<ContentCategory> getContentCategoriesByParentContentCategoryForUpdate(ContentCategory parentContentCategory) {
        return getContentCategoriesByParentContentCategory(parentContentCategory, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getContentCategoriesByDefaultOfferUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contentcategories, contentcategorydetails, contentcatalogs, contentcatalogdetails, contentcollections, contentcollectiondetails " +
                "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_defaultofferuseid = ? " +
                "AND cntcgdt_cntct_contentcatalogid = cntct_contentcatalogid AND cntct_lastdetailid = cntctdt_contentcatalogdetailid " +
                "AND cntctdt_cntc_contentcollectionid = cntc_contentcollectionid AND cntc_lastdetailid = cntcdt_contentcollectiondetailid " +
                "ORDER BY cntcdt_contentcollectionname, cntctdt_sortorder, cntctdt_contentcatalogname, cntcgdt_sortorder, cntcgdt_contentcategoryname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contentcategories, contentcategorydetails " +
                "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_defaultofferuseid = ? " +
                "ORDER BY cntcgdt_sortorder, cntcgdt_contentcategoryname " +
                "FOR UPDATE");
        getContentCategoriesByDefaultOfferUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContentCategory> getContentCategoriesByDefaultOfferUse(OfferUse defaultOfferUse, EntityPermission entityPermission) {
        return ContentCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, getContentCategoriesByDefaultOfferUseQueries,
                defaultOfferUse);
    }

    public List<ContentCategory> getContentCategoriesByDefaultOfferUse(OfferUse defaultOfferUse) {
        return getContentCategoriesByDefaultOfferUse(defaultOfferUse, EntityPermission.READ_ONLY);
    }

    public List<ContentCategory> getContentCategoriesByDefaultOfferUseForUpdate(OfferUse defaultOfferUse) {
        return getContentCategoriesByDefaultOfferUse(defaultOfferUse, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getContentCategoriesByContentCategoryItemSelectorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM contentcategories, contentcategorydetails, contentcatalogs, contentcatalogdetails, contentcollections, contentcollectiondetails " +
                "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_contentcategoryitemselectorid = ? " +
                "AND cntcgdt_cntct_contentcatalogid = cntct_contentcatalogid AND cntct_lastdetailid = cntctdt_contentcatalogdetailid " +
                "AND cntctdt_cntc_contentcollectionid = cntc_contentcollectionid AND cntc_lastdetailid = cntcdt_contentcollectiondetailid " +
                "ORDER BY cntcdt_contentcollectionname, cntctdt_sortorder, cntctdt_contentcatalogname, cntcgdt_sortorder, cntcgdt_contentcategoryname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM contentcategories, contentcategorydetails " +
                "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_contentcategoryitemselectorid = ? " +
                "ORDER BY cntcgdt_sortorder, cntcgdt_contentcategoryname " +
                "FOR UPDATE");
        getContentCategoriesByContentCategoryItemSelectorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ContentCategory> getContentCategoriesByContentCategoryItemSelector(Selector contentCategoryItemSelector, EntityPermission entityPermission) {
        return ContentCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, getContentCategoriesByContentCategoryItemSelectorQueries,
                contentCategoryItemSelector);
    }

    public List<ContentCategory> getContentCategoriesByParentContentCategory(Selector contentCategoryItemSelector) {
        return getContentCategoriesByContentCategoryItemSelector(contentCategoryItemSelector, EntityPermission.READ_ONLY);
    }

    public List<ContentCategory> getContentCategoriesByContentCategoryItemSelectorForUpdate(Selector contentCategoryItemSelector) {
        return getContentCategoriesByContentCategoryItemSelector(contentCategoryItemSelector, EntityPermission.READ_WRITE);
    }

    private ContentCategory getContentCategoryByName(ContentCatalog contentCatalog, String contentCategoryName, EntityPermission entityPermission) {
        ContentCategory contentCategory;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategories, contentcategorydetails " +
                        "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_cntct_contentcatalogid = ? AND cntcgdt_contentcategoryname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategories, contentcategorydetails " +
                        "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_cntct_contentcatalogid = ? AND cntcgdt_contentcategoryname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCatalog.getPrimaryKey().getEntityId());
            ps.setString(2, contentCategoryName);
            
            contentCategory = ContentCategoryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCategory;
    }
    
    public ContentCategory getContentCategoryByName(ContentCatalog contentCatalog, String contentCategoryName) {
        return getContentCategoryByName(contentCatalog, contentCategoryName, EntityPermission.READ_ONLY);
    }
    
    public ContentCategory getContentCategoryByNameForUpdate(ContentCatalog contentCatalog, String contentCategoryName) {
        return getContentCategoryByName(contentCatalog, contentCategoryName, EntityPermission.READ_WRITE);
    }
    
    public ContentCategoryDetailValue getContentCategoryDetailValueForUpdate(ContentCategory contentCategory) {
        return contentCategory == null? null: contentCategory.getLastDetailForUpdate().getContentCategoryDetailValue().clone();
    }
    
    public ContentCategoryDetailValue getContentCategoryDetailValueByNameForUpdate(ContentCatalog contentCatalog, String contentCategoryName) {
        return getContentCategoryDetailValueForUpdate(getContentCategoryByNameForUpdate(contentCatalog, contentCategoryName));
    }
    
    private ContentCategory getDefaultContentCategory(ContentCatalog contentCatalog, EntityPermission entityPermission) {
        ContentCategory contentCategory;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategories, contentcategorydetails " +
                        "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_cntct_contentcatalogid = ? AND cntcgdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategories, contentcategorydetails " +
                        "WHERE cntcg_activedetailid = cntcgdt_contentcategorydetailid AND cntcgdt_cntct_contentcatalogid = ? AND cntcgdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCatalog.getPrimaryKey().getEntityId());
            
            contentCategory = ContentCategoryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCategory;
    }
    
    public ContentCategory getDefaultContentCategory(ContentCatalog contentCatalog) {
        return getDefaultContentCategory(contentCatalog, EntityPermission.READ_ONLY);
    }
    
    public ContentCategoryDetailValue getDefaultContentCategoryDetailValueForUpdate(ContentCatalog contentCatalog) {
        return getDefaultContentCategory(contentCatalog, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentCategoryDetailValue().clone();
    }
    
    public ContentCategoryChoicesBean getContentCategoryChoices(ContentCatalog contentCatalog, String defaultContentCategoryChoice, Language language, boolean allowNullChoice) {
        List<ContentCategory> contentCategorys = getContentCategories(contentCatalog);
        var size = contentCategorys.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultContentCategoryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var contentCategory : contentCategorys) {
            ContentCategoryDetail contentCategoryDetail = contentCategory.getLastDetail();
            var value = contentCategoryDetail.getContentCategoryName();
            
            if(!value.equals(ContentCategories.ROOT.toString())) {
                var label = getBestContentCategoryDescription(contentCategory, language);

                labels.add(label == null? value: label);
                values.add(value);

                var usingDefaultChoice = Objects.equals(defaultContentCategoryChoice, value);
                if(usingDefaultChoice || (defaultValue == null && contentCategoryDetail.getIsDefault())) {
                    defaultValue = value;
                }
            }
        }
        
        return new ContentCategoryChoicesBean(labels, values, defaultValue);
    }
    
    public List<ContentCategoryTransfer> getContentCategoryTransfers(UserVisit userVisit, Collection<ContentCategory> contentCategories) {
        List<ContentCategoryTransfer> contentCategoryTransfers = new ArrayList<>(contentCategories.size());
        ContentCategoryTransferCache contentCategoryTransferCache = getContentTransferCaches(userVisit).getContentCategoryTransferCache();

        contentCategories.forEach((contentCategory) ->
                contentCategoryTransfers.add(contentCategoryTransferCache.getContentCategoryTransfer(contentCategory))
        );
        
        return contentCategoryTransfers;
    }
    
    public ContentCategoryTransfer getContentCategoryTransfer(UserVisit userVisit, ContentCategory contentCategory) {
        return getContentTransferCaches(userVisit).getContentCategoryTransferCache().getContentCategoryTransfer(contentCategory);
    }
    
    public List<ContentCategoryTransfer> getContentCategoryTransfers(UserVisit userVisit, ContentCatalog contentCatalog) {
        return getContentCategoryTransfers(userVisit, getContentCategories(contentCatalog));
    }
    
    public List<ContentCategoryTransfer> getContentCategoryTransfersByParentContentCategory(UserVisit userVisit, ContentCategory parentContentCategory) {
        return getContentCategoryTransfers(userVisit, getContentCategoriesByParentContentCategory(parentContentCategory));
    }
    
    public boolean isParentContentCategorySafe(ContentCategory contentCategory, ContentCategory parentContentCategory) {
        boolean safe = true;
        
        if(parentContentCategory != null) {
            Set<ContentCategory> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(contentCategory);
            do {
                if(parentItemPurchasingCategories.contains(parentContentCategory)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentContentCategory);
                parentContentCategory = parentContentCategory.getLastDetail().getParentContentCategory();
            } while(parentContentCategory != null);
        }
        
        return safe;
    }
    
    private void updateContentCategoryFromValue(ContentCategoryDetailValue contentCategoryDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contentCategoryDetailValue.hasBeenModified()) {
            ContentCategory contentCategory = ContentCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentCategoryDetailValue.getContentCategoryPK());
            ContentCategoryDetail contentCategoryDetail = contentCategory.getActiveDetailForUpdate();

            contentCategoryDetail.setThruTime(session.START_TIME_LONG);
            contentCategoryDetail.store();

            ContentCategoryPK contentCategoryPK = contentCategoryDetail.getContentCategoryPK();
            ContentCatalogPK contentCatalogPK = contentCategoryDetail.getContentCatalogPK();
            String contentCategoryName = contentCategoryDetailValue.getContentCategoryName();
            ContentCategoryPK parentContentCategoryPK = contentCategoryDetailValue.getParentContentCategoryPK();
            OfferUsePK defaultOfferUsePK = contentCategoryDetailValue.getDefaultOfferUsePK();
            SelectorPK itemSelectorPK = contentCategoryDetailValue.getContentCategoryItemSelectorPK();
            Boolean isDefault = contentCategoryDetailValue.getIsDefault();
            Integer sortOrder = contentCategoryDetailValue.getSortOrder();

            if(checkDefault) {
                ContentCatalog contentCatalog = contentCategoryDetail.getContentCatalog();
                ContentCategory defaultContentCategory = getDefaultContentCategory(contentCatalog);
                boolean defaultFound = defaultContentCategory != null && !defaultContentCategory.equals(contentCategory);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ContentCategoryDetailValue defaultContentCategoryDetailValue = getDefaultContentCategoryDetailValueForUpdate(contentCatalog);

                    defaultContentCategoryDetailValue.setIsDefault(Boolean.FALSE);
                    updateContentCategoryFromValue(defaultContentCategoryDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            contentCategoryDetail = ContentCategoryDetailFactory.getInstance().create(contentCategoryPK, contentCatalogPK,
                    contentCategoryName, parentContentCategoryPK, defaultOfferUsePK, itemSelectorPK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            contentCategory.setActiveDetail(contentCategoryDetail);
            contentCategory.setLastDetail(contentCategoryDetail);
            contentCategory.store();

            sendEventUsingNames(contentCategoryPK, EventTypes.MODIFY, null, null, updatedBy);

            if(contentCategoryDetailValue.getDefaultOfferUsePKHasBeenModified()) {
                ContentLogic.getInstance().updateContentCatalogItemPricesByContentCategory(contentCategoryDetail.getContentCategory(), updatedBy);
            }
        }
    }
    
    public void updateContentCategoryFromValue(ContentCategoryDetailValue contentCategoryDetailValue, BasePK updatedBy) {
        updateContentCategoryFromValue(contentCategoryDetailValue, true, updatedBy);
    }
    
    private void deleteContentCategory(ContentCategory contentCategory, BasePK deletedBy, boolean recursive) {
        if(recursive) {
            getContentCategoriesByParentContentCategoryForUpdate(contentCategory).stream().forEach((childContentCategory) -> {
                deleteContentCategory(childContentCategory, deletedBy, true);
            });
        }
        
        deleteContentCategoryItemsByContentCategory(contentCategory, deletedBy);
        deleteContentCategoryDescriptionsByContentCategory(contentCategory, deletedBy);
        
        ContentCategoryDetail contentCategoryDetail = contentCategory.getLastDetailForUpdate();
        contentCategoryDetail.setThruTime(session.START_TIME_LONG);
        contentCategory.setActiveDetail(null);
        contentCategory.store();
        
        // Check for default, and pick one if necessary
        ContentCatalog contentCatalog = contentCategoryDetail.getContentCatalog();
        ContentCategory defaultContentCategory = getDefaultContentCategory(contentCatalog);
        if(defaultContentCategory == null) {
            // Try and find a new default from sections on the same level as the one that was deleted
            ContentCategory parentContentCategory = contentCategoryDetail.getParentContentCategory();
            List<ContentCategory> contentCategories = parentContentCategory == null? null: getContentCategoriesByParentContentCategoryForUpdate(parentContentCategory);
            
            // If that failed, pick one from the current collection
            if(contentCategories == null || contentCategories.isEmpty()) {
                contentCategories = getContentCategoriesForUpdate(contentCatalog);
            }
            
            if(!contentCategories.isEmpty()) {
                for(Iterator<ContentCategory> iter = contentCategories.iterator(); iter.hasNext();) {
                    defaultContentCategory = iter.next();
                    if(!defaultContentCategory.getLastDetail().getContentCategoryName().equals(ContentCategories.ROOT.toString()))
                        break;
                }
                ContentCategoryDetailValue contentCategoryDetailValue = Objects.requireNonNull(defaultContentCategory).getLastDetailForUpdate().getContentCategoryDetailValue().clone();
                
                contentCategoryDetailValue.setIsDefault(Boolean.TRUE);
                updateContentCategoryFromValue(contentCategoryDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(contentCategory.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentCategory(ContentCategory contentCategory, BasePK deletedBy) {
        deleteContentCategory(contentCategory, deletedBy, true);
    }
    
    public void deleteContentCategoriesByContentCatalog(ContentCatalog contentCatalog, BasePK deletedBy) {
        getContentCategoriesForUpdate(contentCatalog).stream().forEach((contentCategory) -> {
            deleteContentCategory(contentCategory, deletedBy, false);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Descriptions
    // --------------------------------------------------------------------------------
    
    public ContentCategoryDescription createContentCategoryDescription(ContentCategory contentCategory, Language language,
            String description, BasePK createdBy) {
        
        ContentCategoryDescription contentCategoryDescription = ContentCategoryDescriptionFactory.getInstance().create(session,
                contentCategory, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contentCategory.getPrimaryKey(), EventTypes.MODIFY, contentCategoryDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentCategoryDescription;
    }
    
    private ContentCategoryDescription getContentCategoryDescription(ContentCategory contentCategory, Language language,
            EntityPermission entityPermission) {
        ContentCategoryDescription contentCategoryDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategorydescriptions " +
                        "WHERE cntcgd_cntcg_contentcategoryid = ? AND cntcgd_lang_languageid = ? AND cntcgd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategorydescriptions " +
                        "WHERE cntcgd_cntcg_contentcategoryid = ? AND cntcgd_lang_languageid = ? AND cntcgd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            contentCategoryDescription = ContentCategoryDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCategoryDescription;
    }
    
    public ContentCategoryDescription getContentCategoryDescription(ContentCategory contentCategory, Language language) {
        return getContentCategoryDescription(contentCategory, language, EntityPermission.READ_ONLY);
    }
    
    public ContentCategoryDescription getContentCategoryDescriptionForUpdate(ContentCategory contentCategory, Language language) {
        return getContentCategoryDescription(contentCategory, language, EntityPermission.READ_WRITE);
    }
    
    public ContentCategoryDescriptionValue getContentCategoryDescriptionValue(ContentCategoryDescription contentCategoryDescription) {
        return contentCategoryDescription == null? null: contentCategoryDescription.getContentCategoryDescriptionValue().clone();
    }
    
    public ContentCategoryDescriptionValue getContentCategoryDescriptionValueForUpdate(ContentCategory contentCategory, Language language) {
        return getContentCategoryDescriptionValue(getContentCategoryDescriptionForUpdate(contentCategory, language));
    }
    
    private List<ContentCategoryDescription> getContentCategoryDescriptionsByContentCategory(ContentCategory contentCategory, EntityPermission entityPermission) {
        List<ContentCategoryDescription> contentCategoryDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategorydescriptions, languages " +
                        "WHERE cntcgd_cntcg_contentcategoryid = ? AND cntcgd_thrutime = ? AND cntcgd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategorydescriptions " +
                        "WHERE cntcgd_cntcg_contentcategoryid = ? AND cntcgd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCategoryDescriptions = ContentCategoryDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCategoryDescriptions;
    }
    
    public List<ContentCategoryDescription> getContentCategoryDescriptionsByContentCategory(ContentCategory contentCategory) {
        return getContentCategoryDescriptionsByContentCategory(contentCategory, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCategoryDescription> getContentCategoryDescriptionsByContentCategoryForUpdate(ContentCategory contentCategory) {
        return getContentCategoryDescriptionsByContentCategory(contentCategory, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentCategoryDescription(ContentCategory contentCategory, Language language) {
        String description;
        ContentCategoryDescription contentCategoryDescription = getContentCategoryDescription(contentCategory, language);
        
        if(contentCategoryDescription == null && !language.getIsDefault()) {
            contentCategoryDescription = getContentCategoryDescription(contentCategory, getPartyControl().getDefaultLanguage());
        }
        
        if(contentCategoryDescription == null) {
            description = contentCategory.getLastDetail().getContentCategoryName();
        } else {
            description = contentCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentCategoryDescriptionTransfer getContentCategoryDescriptionTransfer(UserVisit userVisit, ContentCategoryDescription contentCategoryDescription) {
        return getContentTransferCaches(userVisit).getContentCategoryDescriptionTransferCache().getContentCategoryDescriptionTransfer(contentCategoryDescription);
    }
    
    public List<ContentCategoryDescriptionTransfer> getContentCategoryDescriptionTransfers(UserVisit userVisit, ContentCategory contentCategory) {
        List<ContentCategoryDescription> contentCategoryDescriptions = getContentCategoryDescriptionsByContentCategory(contentCategory);
        List<ContentCategoryDescriptionTransfer> contentCategoryDescriptionTransfers = new ArrayList<>(contentCategoryDescriptions.size());
        ContentCategoryDescriptionTransferCache contentCategoryDescriptionTransferCache = getContentTransferCaches(userVisit).getContentCategoryDescriptionTransferCache();

        contentCategoryDescriptions.forEach((contentCategoryDescription) ->
                contentCategoryDescriptionTransfers.add(contentCategoryDescriptionTransferCache.getContentCategoryDescriptionTransfer(contentCategoryDescription))
        );
        
        return contentCategoryDescriptionTransfers;
    }
    
    public void updateContentCategoryDescriptionFromValue(ContentCategoryDescriptionValue contentCategoryDescriptionValue, BasePK updatedBy) {
        if(contentCategoryDescriptionValue.hasBeenModified()) {
            ContentCategoryDescription contentCategoryDescription = ContentCategoryDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentCategoryDescriptionValue.getPrimaryKey());
            
            contentCategoryDescription.setThruTime(session.START_TIME_LONG);
            contentCategoryDescription.store();
            
            ContentCategory contentCategory = contentCategoryDescription.getContentCategory();
            Language language = contentCategoryDescription.getLanguage();
            String description = contentCategoryDescriptionValue.getDescription();
            
            contentCategoryDescription = ContentCategoryDescriptionFactory.getInstance().create(contentCategory, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contentCategory.getPrimaryKey(), EventTypes.MODIFY, contentCategoryDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentCategoryDescription(ContentCategoryDescription contentCategoryDescription, BasePK deletedBy) {
        contentCategoryDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contentCategoryDescription.getContentCategoryPK(), EventTypes.MODIFY, contentCategoryDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentCategoryDescriptionsByContentCategory(ContentCategory contentCategory, BasePK deletedBy) {
        getContentCategoryDescriptionsByContentCategoryForUpdate(contentCategory).stream().forEach((contentCategoryDescription) -> {
            deleteContentCategoryDescription(contentCategoryDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Items
    // --------------------------------------------------------------------------------
    
    /** Use the function in ContentLogic instead. */
    public ContentCategoryItem createContentCategoryItem(ContentCategory contentCategory, ContentCatalogItem contentCatalogItem,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        ContentCategoryItem defaultContentCategoryItem = getDefaultContentCategoryItem(contentCatalogItem);
        boolean defaultFound = defaultContentCategoryItem != null;
        
        if(defaultFound && isDefault) {
            ContentCategoryItemValue defaultContentCategoryItemValue = getDefaultContentCategoryItemValueForUpdate(contentCatalogItem);
            
            defaultContentCategoryItemValue.setIsDefault(Boolean.FALSE);
            updateContentCategoryItemFromValue(defaultContentCategoryItemValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ContentCategoryItem contentCategoryItem = ContentCategoryItemFactory.getInstance().create(session,
                contentCategory, contentCatalogItem, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contentCategory.getPrimaryKey(), EventTypes.MODIFY, contentCategoryItem.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentCategoryItem;
    }
    
    public long countContentCategoryItemsByContentCategory(ContentCategory contentCategory) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentcategoryitems " +
                "WHERE cntcgi_cntcg_contentcategoryid = ? AND cntcgi_thrutime = ?",
                contentCategory, Session.MAX_TIME);
    }

    private ContentCategoryItem getContentCategoryItem(ContentCategory contentCategory, ContentCatalogItem contentCatalogItem,
            EntityPermission entityPermission) {
        ContentCategoryItem contentCategoryItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategoryitems " +
                        "WHERE cntcgi_cntcg_contentcategoryid = ? AND cntcgi_cntcti_contentcatalogitemid = ? AND cntcgi_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategoryitems " +
                        "WHERE cntcgi_cntcg_contentcategoryid = ? AND cntcgi_cntcti_contentcatalogitemid = ? AND cntcgi_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCategoryItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, contentCatalogItem.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            contentCategoryItem = ContentCategoryItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCategoryItem;
    }
    
    public ContentCategoryItem getContentCategoryItem(ContentCategory contentCategory, ContentCatalogItem contentCatalogItem) {
        return getContentCategoryItem(contentCategory, contentCatalogItem, EntityPermission.READ_ONLY);
    }
    
    public ContentCategoryItem getContentCategoryItemForUpdate(ContentCategory contentCategory, ContentCatalogItem contentCatalogItem) {
        return getContentCategoryItem(contentCategory, contentCatalogItem, EntityPermission.READ_WRITE);
    }
    
    public ContentCategoryItemValue getContentCategoryItemValue(ContentCategoryItem contentCategoryItem) {
        return contentCategoryItem == null? null: contentCategoryItem.getContentCategoryItemValue().clone();
    }
    
    public ContentCategoryItemValue getContentCategoryItemValueForUpdate(ContentCategory contentCategory, ContentCatalogItem contentCatalogItem) {
        return getContentCategoryItemValue(getContentCategoryItemForUpdate(contentCategory, contentCatalogItem));
    }
    
    private ContentCategoryItem getDefaultContentCategoryItem(ContentCatalogItem contentCatalogItem, EntityPermission entityPermission) {
        ContentCategoryItem contentCategoryItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategoryitems " +
                        "WHERE cntcgi_cntcti_contentcatalogitemid = ? AND cntcgi_isdefault = 1 AND cntcgi_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategoryitems " +
                        "WHERE cntcgi_cntcti_contentcatalogitemid = ? AND cntcgi_isdefault = 1 AND cntcgi_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCategoryItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCatalogItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCategoryItem = ContentCategoryItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCategoryItem;
    }
    
    public ContentCategoryItem getDefaultContentCategoryItem(ContentCatalogItem contentCatalogItem) {
        return getDefaultContentCategoryItem(contentCatalogItem, EntityPermission.READ_ONLY);
    }
    
    public ContentCategoryItem getDefaultContentCategoryItemForUpdate(ContentCatalogItem contentCatalogItem) {
        return getDefaultContentCategoryItem(contentCatalogItem, EntityPermission.READ_WRITE);
    }
    
    public ContentCategoryItemValue getDefaultContentCategoryItemValueForUpdate(ContentCatalogItem contentCatalogItem) {
        ContentCategoryItem contentCategoryItem = getDefaultContentCategoryItemForUpdate(contentCatalogItem);
        
        return contentCategoryItem == null? null: contentCategoryItem.getContentCategoryItemValue().clone();
    }
    
    private List<ContentCategoryItem> getContentCategoryItemsByContentCategory(ContentCategory contentCategory, EntityPermission entityPermission) {
        List<ContentCategoryItem> contentCategoryItems;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategoryitems, contentcatalogitems, items, itemdetails, inventoryconditions, inventoryconditiondetails, unitofmeasuretypes, " +
                        "unitofmeasuretypedetails, currencies " +
                        "WHERE cntcgi_cntcg_contentcategoryid = ? AND cntcgi_thrutime = ? " +
                        "AND cntcgi_cntcti_contentcatalogitemid = cntcti_contentcatalogitemid " +
                        "AND cntcti_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "AND cntcti_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "AND cntcti_uomt_unitofmeasuretypeid = uomt_unitofmeasuretypeid AND uomt_lastdetailid = uomtdt_unitofmeasuretypedetailid " +
                        "AND cntcti_cur_currencyid = cur_currencyid " +
                        "ORDER BY cntcgi_sortorder, itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname, uomtdt_sortorder, " +
                        "uomtdt_unitofmeasuretypename, cur_sortorder, cur_currencyisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategoryitems " +
                        "WHERE cntcgi_cntcg_contentcategoryid = ? AND cntcgi_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCategoryItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCategoryItems = ContentCategoryItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCategoryItems;
    }
    
    public List<ContentCategoryItem> getContentCategoryItemsByContentCategory(ContentCategory contentCategory) {
        return getContentCategoryItemsByContentCategory(contentCategory, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCategoryItem> getContentCategoryItemsByContentCategoryForUpdate(ContentCategory contentCategory) {
        return getContentCategoryItemsByContentCategory(contentCategory, EntityPermission.READ_WRITE);
    }
    
    private List<ContentCategoryItem> getContentCategoryItemsByContentCatalogItem(ContentCatalogItem contentCatalogItem, EntityPermission entityPermission) {
        List<ContentCategoryItem> contentCategoryItems;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategoryitems, contentcategories, contentcategorydetails " +
                        "WHERE cntcgi_cntcti_contentcatalogitemid = ? AND cntcgi_thrutime = ? " +
                        "AND cntcgi_cntcg_contentcategoryid = cntcg_contentcategoryid AND cntcg_lastdetailid = cntcgdt_contentcategorydetailid " +
                        "ORDER BY cntcgdt_sortorder, cntcgdt_contentcategoryname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentcategoryitems " +
                        "WHERE cntcgi_cntcti_contentcatalogitemid = ? AND cntcgi_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentCategoryItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCatalogItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentCategoryItems = ContentCategoryItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCategoryItems;
    }
    
    public List<ContentCategoryItem> getContentCategoryItemsByContentCatalogItem(ContentCatalogItem contentCatalogItem) {
        return getContentCategoryItemsByContentCatalogItem(contentCatalogItem, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCategoryItem> getContentCategoryItemsByContentCatalogItemForUpdate(ContentCatalogItem contentCatalogItem) {
        return getContentCategoryItemsByContentCatalogItem(contentCatalogItem, EntityPermission.READ_WRITE);
    }
    
    public ContentCategoryItemTransfer getContentCategoryItemTransfer(UserVisit userVisit, ContentCategoryItem contentCategoryItem) {
        return getContentTransferCaches(userVisit).getContentCategoryItemTransferCache().getContentCategoryItemTransfer(contentCategoryItem);
    }
    
    public List<ContentCategoryItemTransfer> getContentCategoryItemTransfers(UserVisit userVisit, Collection<ContentCategoryItem> contentCategoryItems) {
        List<ContentCategoryItemTransfer> contentCategoryItemTransfers = new ArrayList<>(contentCategoryItems.size());
        ContentCategoryItemTransferCache contentCategoryItemTransferCache = getContentTransferCaches(userVisit).getContentCategoryItemTransferCache();
        
        contentCategoryItems.forEach((contentCategoryItem) ->
                contentCategoryItemTransfers.add(contentCategoryItemTransferCache.getContentCategoryItemTransfer(contentCategoryItem))
        );
        
        return contentCategoryItemTransfers;
    }
    
    public List<ContentCategoryItemTransfer> getContentCategoryItemTransfersByContentCategory(UserVisit userVisit, ContentCategory contentCategory) {
        return getContentCategoryItemTransfers(userVisit, getContentCategoryItemsByContentCategory(contentCategory));
    }
    
    public long countContentCategoryItemsByContentCatalogItem(ContentCatalogItem contentCatalogItem) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentcategoryitems " +
                "WHERE cntcgi_cntcti_contentcatalogitemid = ? AND cntcgi_thrutime = ?",
                contentCatalogItem, Session.MAX_TIME_LONG);
    }

    private void updateContentCategoryItemFromValue(ContentCategoryItemValue contentCategoryItemValue, boolean checkDefault, BasePK updatedBy) {
        if(contentCategoryItemValue.hasBeenModified()) {
            ContentCategoryItem contentCategoryItem = ContentCategoryItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     contentCategoryItemValue.getPrimaryKey());
            
            contentCategoryItem.setThruTime(session.START_TIME_LONG);
            contentCategoryItem.store();
            
            ContentCategoryPK contentCategoryPK = contentCategoryItem.getContentCategoryPK(); // Not Updated
            ContentCatalogItem contentCatalogItem = contentCategoryItem.getContentCatalogItem(); // Not Updated
            ContentCatalogItemPK contentCatalogItemPK = contentCategoryItem.getContentCatalogItemPK(); // Not Updated
            Boolean isDefault = contentCategoryItemValue.getIsDefault();
            Integer sortOrder = contentCategoryItemValue.getSortOrder();
            
            if(checkDefault) {
                ContentCategoryItem defaultContentCategoryItem = getDefaultContentCategoryItem(contentCatalogItem);
                boolean defaultFound = defaultContentCategoryItem != null && !defaultContentCategoryItem.equals(contentCategoryItem);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ContentCategoryItemValue defaultContentCategoryItemValue = getDefaultContentCategoryItemValueForUpdate(contentCatalogItem);
                    
                    defaultContentCategoryItemValue.setIsDefault(Boolean.FALSE);
                    updateContentCategoryItemFromValue(defaultContentCategoryItemValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            contentCategoryItem = ContentCategoryItemFactory.getInstance().create(contentCategoryPK, contentCatalogItemPK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contentCategoryPK, EventTypes.MODIFY, contentCategoryItem.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    /** Use the function in ContentLogic instead. */
    public void updateContentCategoryItemFromValue(ContentCategoryItemValue contentCategoryItemValue, BasePK updatedBy) {
        updateContentCategoryItemFromValue(contentCategoryItemValue, true, updatedBy);
    }
    
    /** Use the function in ContentLogic instead. */
    private void deleteContentCategoryItem(ContentCategoryItem contentCategoryItem, boolean checkDefault, BasePK deletedBy) {
        ContentCatalogItem contentCatalogItem = contentCategoryItem.getContentCatalogItemForUpdate();
        
        contentCategoryItem.setThruTime(session.START_TIME_LONG);
        contentCategoryItem.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            ContentCategoryItem defaultContentCategoryItem = getDefaultContentCategoryItem(contentCatalogItem);
            if(defaultContentCategoryItem == null) {
                List<ContentCategoryItem> contentCategoryItems = getContentCategoryItemsByContentCatalogItemForUpdate(contentCatalogItem);

                if(!contentCategoryItems.isEmpty()) {
                    Iterator<ContentCategoryItem> iter = contentCategoryItems.iterator();
                    if(iter.hasNext()) {
                        defaultContentCategoryItem = iter.next();
                    }
                    ContentCategoryItemValue contentCategoryItemValue = defaultContentCategoryItem.getContentCategoryItemValue().clone();

                    contentCategoryItemValue.setIsDefault(Boolean.TRUE);
                    updateContentCategoryItemFromValue(contentCategoryItemValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(contentCategoryItem.getContentCategoryPK(), EventTypes.MODIFY, contentCategoryItem.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteContentCategoryItem(ContentCategoryItem contentCategoryItem,BasePK deletedBy) {
        deleteContentCategoryItem(contentCategoryItem, true, deletedBy);
    }
    
    public void deleteContentCategoryItemsByContentCategory(ContentCategory contentCategory, BasePK deletedBy) {
        getContentCategoryItemsByContentCategoryForUpdate(contentCategory).stream().forEach((contentCategoryItem) -> {
            ContentLogic.getInstance().deleteContentCategoryItem(contentCategoryItem, deletedBy);
        });
    }

    private void deleteContentCategoryItemsByContentCatalogItem(ContentCatalogItem contentCatalogItem, BasePK deletedBy) {
        getContentCategoryItemsByContentCatalogItemForUpdate(contentCatalogItem).stream().forEach((contentCategoryItem) -> {
            deleteContentCategoryItem(contentCategoryItem, false, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Content Forums
    // --------------------------------------------------------------------------------
    
    public ContentForum createContentForum(ContentCollection contentCollection, Forum forum, Boolean isDefault, BasePK createdBy) {
        ContentForum defaultContentForum = getDefaultContentForum(contentCollection);
        boolean defaultFound = defaultContentForum != null;
        
        if(defaultFound && isDefault) {
            ContentForumDetailValue defaultContentForumDetailValue = getDefaultContentForumDetailValueForUpdate(contentCollection);
            
            defaultContentForumDetailValue.setIsDefault(Boolean.FALSE);
            updateContentForumFromValue(defaultContentForumDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ContentForum contentForum = ContentForumFactory.getInstance().create();
        ContentForumDetail contentForumDetail = ContentForumDetailFactory.getInstance().create(contentForum, contentCollection, forum, isDefault,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contentForum = ContentForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentForum.getPrimaryKey());
        contentForum.setActiveDetail(contentForumDetail);
        contentForum.setLastDetail(contentForumDetail);
        contentForum.store();
        
        sendEventUsingNames(contentForum.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentForum;
    }
    
    private List<ContentForum> getContentForums(ContentCollection contentCollection, EntityPermission entityPermission) {
        List<ContentForum> contentForums;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentforums, contentforumdetails, forums, forumdetails " +
                        "WHERE cntfrm_activedetailid = cntfrmdt_contentforumdetailid AND cntfrmdt_cntc_contentcollectionid = ? " +
                        "AND cntfrmdt_frm_forumid = frm_forumid AND frm_lastdetailid = frmdt_forumdetailid " +
                        "ORDER BY frmdt_sortorder, frmdt_forumname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentforums, contentforumdetails " +
                        "WHERE cntfrm_activedetailid = cntfrmdt_contentforumdetailid AND cntfrmdt_cntc_contentcollectionid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            
            contentForums = ContentForumFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentForums;
    }
    
    public List<ContentForum> getContentForums(ContentCollection contentCollection) {
        return getContentForums(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentForum> getContentForumsForUpdate(ContentCollection contentCollection) {
        return getContentForums(contentCollection, EntityPermission.READ_WRITE);
    }
    
    private ContentForum getContentForum(ContentCollection contentCollection, Forum forum, EntityPermission entityPermission) {
        ContentForum contentForum;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentforums, contentforumdetails " +
                        "WHERE cntfrm_contentforumid = cntfrmdt_cntfrm_contentforumid AND cntfrmdt_cntc_contentcollectionid = ? " +
                        "AND cntfrmdt_frm_forumid = ? AND cntfrmdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentforums, contentforumdetails " +
                        "WHERE cntfrm_contentforumid = cntfrmdt_cntfrm_contentforumid AND cntfrmdt_cntc_contentcollectionid = ? " +
                        "AND cntfrmdt_frm_forumid = ? AND cntfrmdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setLong(2, forum.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            contentForum = ContentForumFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentForum;
    }
    
    public ContentForum getContentForum(ContentCollection contentCollection, Forum forum) {
        return getContentForum(contentCollection, forum, EntityPermission.READ_ONLY);
    }
    
    public ContentForum getContentForumForUpdate(ContentCollection contentCollection, Forum forum) {
        return getContentForum(contentCollection, forum, EntityPermission.READ_WRITE);
    }
    
    public ContentForumDetailValue getContentForumDetailValueForUpdate(ContentCollection contentCollection, Forum forum) {
        ContentForum contentForum = getContentForumForUpdate(contentCollection, forum);
        
        return contentForum == null? null: contentForum.getLastDetailForUpdate().getContentForumDetailValue().clone();
    }
    
    private ContentForum getDefaultContentForum(ContentCollection contentCollection, EntityPermission entityPermission) {
        ContentForum contentForum;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentforums, contentforumdetails " +
                        "WHERE cntfrm_contentforumid = cntfrmdt_cntfrm_contentforumid AND cntfrmdt_cntc_contentcollectionid = ? " +
                        "AND cntfrmdt_isdefault = 1 AND cntfrmdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentforums, contentforumdetails " +
                        "WHERE cntfrm_contentforumid = cntfrmdt_cntfrm_contentforumid AND cntfrmdt_cntc_contentcollectionid = ? " +
                        "AND cntfrmdt_isdefault = 1 AND cntfrmdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentForum = ContentForumFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentForum;
    }
    
    public ContentForum getDefaultContentForum(ContentCollection contentCollection) {
        return getDefaultContentForum(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public ContentForumDetailValue getDefaultContentForumDetailValueForUpdate(ContentCollection contentCollection) {
        return getDefaultContentForum(contentCollection, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentForumDetailValue().clone();
    }
    
    public ContentForumTransfer getContentForumTransfer(UserVisit userVisit, ContentForum contentForum) {
        return getContentTransferCaches(userVisit).getContentForumTransferCache().getContentForumTransfer(contentForum);
    }
    
    public List<ContentForumTransfer> getContentForumTransfers(UserVisit userVisit, Collection<ContentForum> contentForums) {
        List<ContentForumTransfer> contentForumTransfers = new ArrayList<>(contentForums.size());
        ContentForumTransferCache contentForumTransferCache = getContentTransferCaches(userVisit).getContentForumTransferCache();

        contentForums.forEach((contentForum) ->
                contentForumTransfers.add(contentForumTransferCache.getContentForumTransfer(contentForum))
        );
        
        return contentForumTransfers;
    }
    
    public List<ContentForumTransfer> getContentForumTransfers(UserVisit userVisit, ContentCollection contentCollection) {
        return getContentForumTransfers(userVisit, getContentForums(contentCollection));
    }
    
    private void updateContentForumFromValue(ContentForumDetailValue contentForumDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contentForumDetailValue.hasBeenModified()) {
            ContentForum contentForum = ContentForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    contentForumDetailValue.getContentForumPK());
            ContentForumDetail contentForumDetail = contentForum.getActiveDetailForUpdate();

            contentForumDetail.setThruTime(session.START_TIME_LONG);
            contentForumDetail.store();

            ContentForumPK contentForumPK = contentForumDetail.getContentForumPK();
            ContentCollection contentCollection = contentForumDetail.getContentCollection();
            ContentCollectionPK contentCollectionPK = contentForumDetail.getContentCollectionPK();
            ForumPK forumPK = contentForumDetail.getForumPK();
            Boolean isDefault = contentForumDetailValue.getIsDefault();

            if(checkDefault) {
                ContentForum defaultContentForum = getDefaultContentForum(contentCollection);
                boolean defaultFound = defaultContentForum != null && !defaultContentForum.equals(contentForum);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ContentForumDetailValue defaultContentForumDetailValue = getDefaultContentForumDetailValueForUpdate(contentCollection);

                    defaultContentForumDetailValue.setIsDefault(Boolean.FALSE);
                    updateContentForumFromValue(defaultContentForumDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            contentForumDetail = ContentForumDetailFactory.getInstance().create(contentForumPK, contentCollectionPK, forumPK,
                    isDefault, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            contentForum.setActiveDetail(contentForumDetail);
            contentForum.setLastDetail(contentForumDetail);
            contentForum.store();

            sendEventUsingNames(contentForumPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateContentForumFromValue(ContentForumDetailValue contentForumDetailValue, BasePK updatedBy) {
        updateContentForumFromValue(contentForumDetailValue, true, updatedBy);
    }
    
    public void deleteContentForum(ContentForum contentForum, BasePK deletedBy) {
        ContentForumDetail contentForumDetail = contentForum.getLastDetailForUpdate();
        contentForumDetail.setThruTime(session.START_TIME_LONG);
        contentForumDetail.store();
        contentForum.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        ContentCollection contentCollection = contentForumDetail.getContentCollection();
        ContentForum defaultContentForum = getDefaultContentForum(contentCollection);
        if(defaultContentForum == null) {
            List<ContentForum> contentForums = getContentForumsForUpdate(contentCollection);
            
            if(!contentForums.isEmpty()) {
                for(Iterator<ContentForum> iter = contentForums.iterator(); iter.hasNext();) {
                    defaultContentForum = iter.next();
                    break;
                }
                ContentForumDetailValue contentForumDetailValue = Objects.requireNonNull(defaultContentForum).getLastDetailForUpdate().getContentForumDetailValue().clone();
                
                contentForumDetailValue.setIsDefault(Boolean.TRUE);
                updateContentForumFromValue(contentForumDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(contentForum.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentForumsByContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        getContentForumsForUpdate(contentCollection).stream().forEach((contentForum) -> {
            deleteContentForum(contentForum, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Addresses
    // --------------------------------------------------------------------------------
    
    public ContentWebAddress createContentWebAddress(String contentWebAddressName, ContentCollection contentCollection, BasePK createdBy) {
        ContentWebAddress contentWebAddress = ContentWebAddressFactory.getInstance().create();
        ContentWebAddressDetail contentWebAddressDetail = ContentWebAddressDetailFactory.getInstance().create(contentWebAddress, contentWebAddressName, contentCollection, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        contentWebAddress = ContentWebAddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentWebAddress.getPrimaryKey());
        contentWebAddress.setActiveDetail(contentWebAddressDetail);
        contentWebAddress.setLastDetail(contentWebAddressDetail);
        contentWebAddress.store();
        
        sendEventUsingNames(contentWebAddress.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentWebAddress;
    }
    
    public boolean validContentWebAddressName(String contentWebAddressName) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM contentwebaddresses, contentwebaddressdetails " +
                "WHERE cntwa_activedetailid = cntwadt_contentwebaddressdetailid AND cntwadt_contentwebaddressname = ?",
                contentWebAddressName) == 1;
    }

    private List<ContentWebAddress> getContentWebAddresses(EntityPermission entityPermission) {
        List<ContentWebAddress> contentWebAddresses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddresses, contentwebaddressdetails " +
                        "WHERE cntwa_contentwebaddressid = cntwadt_cntwa_contentwebaddressid AND cntwadt_thrutime = ? " +
                        "ORDER BY cntwadt_contentwebaddressname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddresses " +
                        "WHERE cntwadt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentWebAddressFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            contentWebAddresses = ContentWebAddressFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentWebAddresses;
    }
    
    public List<ContentWebAddress> getContentWebAddresses() {
        return getContentWebAddresses(EntityPermission.READ_ONLY);
    }
    
    public List<ContentWebAddress> getContentWebAddressesForUpdate() {
        return getContentWebAddresses(EntityPermission.READ_WRITE);
    }
    
    private ContentWebAddress getContentWebAddressByName(String contentWebAddressName, EntityPermission entityPermission) {
        ContentWebAddress contentWebAddress;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddresses, contentwebaddressdetails " +
                        "WHERE cntwa_contentwebaddressid = cntwadt_cntwa_contentwebaddressid AND cntwadt_contentwebaddressname = ? AND cntwadt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddresses, contentwebaddressdetails " +
                        "WHERE cntwa_contentwebaddressid = cntwadt_cntwa_contentwebaddressid AND cntwadt_contentwebaddressname = ? AND cntwadt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentWebAddressFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, contentWebAddressName);
            ps.setLong(2, Session.MAX_TIME);
            
            contentWebAddress = ContentWebAddressFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentWebAddress;
    }
    
    public ContentWebAddress getContentWebAddressByName(String contentWebAddressName) {
        return getContentWebAddressByName(contentWebAddressName, EntityPermission.READ_ONLY);
    }
    
    public ContentWebAddress getContentWebAddressByNameForUpdate(String contentWebAddressName) {
        return getContentWebAddressByName(contentWebAddressName, EntityPermission.READ_WRITE);
    }
    
    public ContentWebAddressDetailValue getContentWebAddressDetailValueForUpdate(ContentWebAddress contentWebAddress) {
        return contentWebAddress == null? null: contentWebAddress.getLastDetailForUpdate().getContentWebAddressDetailValue().clone();
    }
    
    public ContentWebAddressDetailValue getContentWebAddressDetailValueByNameForUpdate(String contentWebAddressName) {
        return getContentWebAddressDetailValueForUpdate(getContentWebAddressByNameForUpdate(contentWebAddressName));
    }
    
    private List<ContentWebAddress> getContentWebAddressesByContentCollection(ContentCollection contentCollection, EntityPermission entityPermission) {
        List<ContentWebAddress> contentWebAddresses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddresses, contentwebaddressdetails " +
                        "WHERE cntwa_contentwebaddressid = cntwadt_cntwa_contentwebaddressid AND cntwadt_cntc_contentcollectionid = ? AND cntwadt_thrutime = ? " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddresses, contentwebaddressdetails " +
                        "WHERE cntwa_contentwebaddressid = cntwadt_cntwa_contentwebaddressid AND cntwadt_cntc_contentcollectionid = ? AND cntwadt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentWebAddressFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentCollection.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentWebAddresses = ContentWebAddressFactory.getInstance().getEntitiesFromQuery(session,entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentWebAddresses;
    }
    
    public List<ContentWebAddress> getContentWebAddressesByContentCollection(ContentCollection contentCollection) {
        return getContentWebAddressesByContentCollection(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentWebAddress> getContentWebAddressesByContentCollectionForUpdate(ContentCollection contentCollection) {
        return getContentWebAddressesByContentCollection(contentCollection, EntityPermission.READ_WRITE);
    }
    
    public ContentWebAddressChoicesBean getContentWebAddressChoices(String defaultContentWebAddressChoice, Language language) {
        List<ContentWebAddress> contentWebAddresses = getContentWebAddresses();
        var size = contentWebAddresses.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var contentWebAddress : contentWebAddresses) {
            var label = getBestContentWebAddressDescription(contentWebAddress, language);
            var value = contentWebAddress.getLastDetail().getContentWebAddressName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContentWebAddressChoice != null && defaultContentWebAddressChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new ContentWebAddressChoicesBean(labels, values, defaultValue);
    }
    
    public List<ContentWebAddressTransfer> getContentWebAddressTransfers(UserVisit userVisit, Collection<ContentWebAddress> contentWebAddresses) {
        List<ContentWebAddressTransfer> contentWebAddressTransfers = new ArrayList<>(contentWebAddresses.size());
        ContentWebAddressTransferCache contentWebAddressTransferCache = getContentTransferCaches(userVisit).getContentWebAddressTransferCache();

        contentWebAddresses.forEach((contentWebAddress) ->
                contentWebAddressTransfers.add(contentWebAddressTransferCache.getContentWebAddressTransfer(contentWebAddress))
        );
        
        return contentWebAddressTransfers;
    }
    
    public List<ContentWebAddressTransfer> getContentWebAddressTransfers(UserVisit userVisit) {
        return getContentWebAddressTransfers(userVisit, getContentWebAddresses());
    }
    
    public ContentWebAddressTransfer getContentWebAddressTransfer(UserVisit userVisit, ContentWebAddress contentWebAddress) {
        return getContentTransferCaches(userVisit).getContentWebAddressTransferCache().getContentWebAddressTransfer(contentWebAddress);
    }
    
    public void updateContentWebAddressFromValue(ContentWebAddressDetailValue contentWebAddressDetailValue, BasePK updatedBy) {
        if(contentWebAddressDetailValue.hasBeenModified()) {
            ContentWebAddress contentWebAddress = ContentWebAddressFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    contentWebAddressDetailValue.getContentWebAddressPK());
            ContentWebAddressDetail contentWebAddressDetail = contentWebAddress.getActiveDetailForUpdate();
            
            contentWebAddressDetail.setThruTime(session.START_TIME_LONG);
            contentWebAddressDetail.store();
            
            ContentWebAddressPK contentWebAddressPK = contentWebAddressDetail.getContentWebAddressPK();
            String contentWebAddressName = contentWebAddressDetailValue.getContentWebAddressName();
            ContentCollectionPK contentCollectionPK = contentWebAddressDetailValue.getContentCollectionPK();
            
            contentWebAddressDetail = ContentWebAddressDetailFactory.getInstance().create(contentWebAddressPK, contentWebAddressName, contentCollectionPK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            contentWebAddress.setActiveDetail(contentWebAddressDetail);
            contentWebAddress.setLastDetail(contentWebAddressDetail);
            
            sendEventUsingNames(contentWebAddressPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteContentWebAddress(ContentWebAddress contentWebAddress, BasePK deletedBy) {
        deleteContentWebAddressDescriptionsByContentWebAddress(contentWebAddress, deletedBy);
        deleteContentWebAddressServersByContentWebAddress(contentWebAddress, deletedBy);
        contentWebAddress.getLastDetailForUpdate().setThruTime(session.START_TIME_LONG);
        contentWebAddress.setActiveDetail(null);
        
        sendEventUsingNames(contentWebAddress.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentWebAddressesByContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        getContentWebAddressesByContentCollectionForUpdate(contentCollection).stream().forEach((contentWebAddress) -> {
            deleteContentWebAddress(contentWebAddress, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Descriptions
    // --------------------------------------------------------------------------------
    
    public ContentWebAddressDescription createContentWebAddressDescription(ContentWebAddress contentWebAddress, Language language, String description, BasePK createdBy) {
        
        ContentWebAddressDescription contentWebAddressDescription = ContentWebAddressDescriptionFactory.getInstance().create(contentWebAddress, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contentWebAddress.getPrimaryKey(), EventTypes.MODIFY, contentWebAddressDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentWebAddressDescription;
    }
    
    private ContentWebAddressDescription getContentWebAddressDescription(ContentWebAddress contentWebAddress, Language language, EntityPermission entityPermission) {
        ContentWebAddressDescription contentWebAddressDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddressdescriptions " +
                        "WHERE cntwad_cntwa_contentwebaddressid = ? AND cntwad_lang_languageid = ? AND cntwad_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddressdescriptions " +
                        "WHERE cntwad_cntwa_contentwebaddressid = ? AND cntwad_lang_languageid = ? AND cntwad_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentWebAddressDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentWebAddress.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            contentWebAddressDescription = ContentWebAddressDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentWebAddressDescription;
    }
    
    public ContentWebAddressDescription getContentWebAddressDescription(ContentWebAddress contentWebAddress, Language language) {
        return getContentWebAddressDescription(contentWebAddress, language, EntityPermission.READ_ONLY);
    }
    
    public ContentWebAddressDescription getContentWebAddressDescriptionForUpdate(ContentWebAddress contentWebAddress, Language language) {
        return getContentWebAddressDescription(contentWebAddress, language, EntityPermission.READ_WRITE);
    }
    
    public ContentWebAddressDescriptionValue getContentWebAddressDescriptionValue(ContentWebAddressDescription contentWebAddressDescription) {
        return contentWebAddressDescription == null? null: contentWebAddressDescription.getContentWebAddressDescriptionValue().clone();
    }
    
    public ContentWebAddressDescriptionValue getContentWebAddressDescriptionValueForUpdate(ContentWebAddress contentWebAddress, Language language) {
        return getContentWebAddressDescriptionValue(getContentWebAddressDescriptionForUpdate(contentWebAddress, language));
    }
    
    private List<ContentWebAddressDescription> getContentWebAddressDescriptionsByContentWebAddress(ContentWebAddress contentWebAddress, EntityPermission entityPermission) {
        List<ContentWebAddressDescription> contentWebAddressDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddressdescriptions, languages " +
                        "WHERE cntwad_cntwa_contentwebaddressid = ? AND cntwad_thrutime = ? AND cntwad_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddressdescriptions " +
                        "WHERE cntwad_cntwa_contentwebaddressid = ? AND cntwad_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentWebAddressDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentWebAddress.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            contentWebAddressDescriptions = ContentWebAddressDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentWebAddressDescriptions;
    }
    
    public List<ContentWebAddressDescription> getContentWebAddressDescriptionsByContentWebAddress(ContentWebAddress contentWebAddress) {
        return getContentWebAddressDescriptionsByContentWebAddress(contentWebAddress, EntityPermission.READ_ONLY);
    }
    
    public List<ContentWebAddressDescription> getContentWebAddressDescriptionsByContentWebAddressForUpdate(ContentWebAddress contentWebAddress) {
        return getContentWebAddressDescriptionsByContentWebAddress(contentWebAddress, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentWebAddressDescription(ContentWebAddress contentWebAddress, Language language) {
        String description;
        ContentWebAddressDescription contentWebAddressDescription = getContentWebAddressDescription(contentWebAddress, language);
        
        if(contentWebAddressDescription == null && !language.getIsDefault()) {
            contentWebAddressDescription = getContentWebAddressDescription(contentWebAddress, getPartyControl().getDefaultLanguage());
        }
        
        if(contentWebAddressDescription == null) {
            description = contentWebAddress.getLastDetail().getContentWebAddressName();
        } else {
            description = contentWebAddressDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentWebAddressDescriptionTransfer getContentWebAddressDescriptionTransfer(UserVisit userVisit, ContentWebAddressDescription contentWebAddressDescription) {
        return getContentTransferCaches(userVisit).getContentWebAddressDescriptionTransferCache().getContentWebAddressDescriptionTransfer(contentWebAddressDescription);
    }
    
    public List<ContentWebAddressDescriptionTransfer> getContentWebAddressDescriptionTransfers(UserVisit userVisit, ContentWebAddress contentWebAddress) {
        List<ContentWebAddressDescription> contentWebAddressDescriptions = getContentWebAddressDescriptionsByContentWebAddress(contentWebAddress);
        List<ContentWebAddressDescriptionTransfer> contentWebAddressDescriptionTransfers = new ArrayList<>(contentWebAddressDescriptions.size());
        ContentWebAddressDescriptionTransferCache contentWebAddressDescriptionTransferCache = getContentTransferCaches(userVisit).getContentWebAddressDescriptionTransferCache();

        contentWebAddressDescriptions.forEach((contentWebAddressDescription) ->
                contentWebAddressDescriptionTransfers.add(contentWebAddressDescriptionTransferCache.getContentWebAddressDescriptionTransfer(contentWebAddressDescription))
        );
        
        return contentWebAddressDescriptionTransfers;
    }
    
    public void updateContentWebAddressDescriptionFromValue(ContentWebAddressDescriptionValue contentWebAddressDescriptionValue, BasePK updatedBy) {
        if(contentWebAddressDescriptionValue.hasBeenModified()) {
            ContentWebAddressDescription contentWebAddressDescription = ContentWebAddressDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, contentWebAddressDescriptionValue.getPrimaryKey());
            
            contentWebAddressDescription.setThruTime(session.START_TIME_LONG);
            contentWebAddressDescription.store();
            
            ContentWebAddress contentWebAddress = contentWebAddressDescription.getContentWebAddress();
            Language language = contentWebAddressDescription.getLanguage();
            String description = contentWebAddressDescriptionValue.getDescription();
            
            contentWebAddressDescription = ContentWebAddressDescriptionFactory.getInstance().create(contentWebAddress, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(contentWebAddress.getPrimaryKey(), EventTypes.MODIFY, contentWebAddressDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentWebAddressDescription(ContentWebAddressDescription contentWebAddressDescription, BasePK deletedBy) {
        contentWebAddressDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contentWebAddressDescription.getContentWebAddressPK(), EventTypes.MODIFY, contentWebAddressDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentWebAddressDescriptionsByContentWebAddress(ContentWebAddress contentWebAddress, BasePK deletedBy) {
        getContentWebAddressDescriptionsByContentWebAddressForUpdate(contentWebAddress).stream().forEach((contentWebAddressDescription) -> {
            deleteContentWebAddressDescription(contentWebAddressDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Servers
    // --------------------------------------------------------------------------------
    
    public ContentWebAddressServer createContentWebAddressServer(ContentWebAddress contentWebAddress, Server server, BasePK createdBy) {
        
        ContentWebAddressServer contentWebAddressServer = ContentWebAddressServerFactory.getInstance().create(contentWebAddress, server,  session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(contentWebAddress.getPrimaryKey(), EventTypes.MODIFY, contentWebAddressServer.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentWebAddressServer;
    }
    
    private ContentWebAddressServer getContentWebAddressServer(ContentWebAddress contentWebAddress, Server server, EntityPermission entityPermission) {
        ContentWebAddressServer contentWebAddressServer;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddressservers " +
                        "WHERE cntwaserv_cntwa_contentwebaddressid = ? AND cntwaserv_serv_serverid = ? AND cntwaserv_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddressservers " +
                        "WHERE cntwaserv_cntwa_contentwebaddressid = ? AND cntwaserv_serv_serverid = ? AND cntwaserv_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentWebAddressServerFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentWebAddress.getPrimaryKey().getEntityId());
            ps.setLong(2, server.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            contentWebAddressServer = ContentWebAddressServerFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentWebAddressServer;
    }
    
    public ContentWebAddressServer getContentWebAddressServer(ContentWebAddress contentWebAddress, Server server) {
        return getContentWebAddressServer(contentWebAddress, server, EntityPermission.READ_ONLY);
    }
    
    public ContentWebAddressServer getContentWebAddressServerForUpdate(ContentWebAddress contentWebAddress, Server server) {
        return getContentWebAddressServer(contentWebAddress, server, EntityPermission.READ_WRITE);
    }
    
    private List<ContentWebAddressServer> getContentWebAddressServersByContentWebAddress(ContentWebAddress contentWebAddress, EntityPermission entityPermission) {
        List<ContentWebAddressServer> contentWebAddressServers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddressservers, serverdetails " +
                        "WHERE cntwaserv_cntwa_contentwebaddressid = ? AND cntwaserv_thrutime = ? AND cntwaserv_serv_serverid = servd_serv_serverid AND servd_thrutime = ? " +
                        "ORDER BY servd_servername " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM contentwebaddressservers " +
                        "WHERE cntwaserv_cntwa_contentwebaddressid = ? AND cntwaserv_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ContentWebAddressServerFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contentWebAddress.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            contentWebAddressServers = ContentWebAddressServerFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentWebAddressServers;
    }
    
    public List<ContentWebAddressServer> getContentWebAddressServersByContentWebAddress(ContentWebAddress contentWebAddress) {
        return getContentWebAddressServersByContentWebAddress(contentWebAddress, EntityPermission.READ_ONLY);
    }
    
    public List<ContentWebAddressServer> getContentWebAddressServersByContentWebAddressForUpdate(ContentWebAddress contentWebAddress) {
        return getContentWebAddressServersByContentWebAddress(contentWebAddress, EntityPermission.READ_WRITE);
    }
    
    public void deleteContentWebAddressServer(ContentWebAddressServer contentWebAddressServer, BasePK deletedBy) {
        contentWebAddressServer.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(contentWebAddressServer.getContentWebAddressPK(), EventTypes.MODIFY, contentWebAddressServer.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContentWebAddressServersByContentWebAddress(ContentWebAddress contentWebAddress, BasePK deletedBy) {
        getContentWebAddressServersByContentWebAddressForUpdate(contentWebAddress).stream().forEach((contentWebAddressServer) -> {
            deleteContentWebAddressServer(contentWebAddressServer, deletedBy);
        });
    }
    
}
