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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.offer.common.OfferOptions;
import com.echothree.model.control.offer.common.OfferProperties;
import com.echothree.model.control.offer.common.transfer.OfferTransfer;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferNameElementControl;
import com.echothree.model.control.party.common.transfer.DepartmentTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferDetail;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.transfer.ListWrapperBuilder;
import java.util.Set;

public class OfferTransferCache
        extends BaseOfferTransferCache<Offer, OfferTransfer> {
    
    OfferControl offerControl = Session.getModelController(OfferControl.class);
    FilterControl filterControl = Session.getModelController(FilterControl.class);
    OfferNameElementControl offerNameElementControl = Session.getModelController(OfferNameElementControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    
    boolean includeOfferCustomerTypes;
    boolean includeOfferNameElements;
    
    TransferProperties transferProperties;
    boolean filterOfferName;
    boolean filterSalesOrderSequence;
    boolean filterDepartment;
    boolean filterOfferItemSelector;
    boolean filterOfferItemPriceFilter;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of OfferTransferCache */
    public OfferTransferCache(UserVisit userVisit) {
        super(userVisit);

        var options = session.getOptions();
        if(options != null) {
            includeOfferCustomerTypes = options.contains(OfferOptions.OfferIncludeOfferCustomerTypes);
            includeOfferNameElements = options.contains(OfferOptions.OfferIncludeOfferNameElements);
            setIncludeEntityAttributeGroups(options.contains(OfferOptions.OfferIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(OfferOptions.OfferIncludeTagScopes));
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(OfferTransfer.class);
            
            if(properties != null) {
                filterOfferName = !properties.contains(OfferProperties.OFFER_NAME);
                filterSalesOrderSequence = !properties.contains(OfferProperties.SALES_ORDER_SEQUENCE);
                filterDepartment = !properties.contains(OfferProperties.DEPARTMENT);
                filterOfferItemSelector = !properties.contains(OfferProperties.OFFER_ITEM_SELECTOR);
                filterOfferItemPriceFilter = !properties.contains(OfferProperties.OFFER_ITEM_PRICE_FILTER);
                filterIsDefault = !properties.contains(OfferProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(OfferProperties.SORT_ORDER);
                filterDescription = !properties.contains(OfferProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(OfferProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public OfferTransfer getOfferTransfer(Offer offer) {
        OfferTransfer offerTransfer = get(offer);
        
        if(offerTransfer == null) {
            OfferDetail offerDetail = offer.getLastDetail();
            String offerName = filterOfferName ? null : offerDetail.getOfferName();
            Sequence salesOrderSequence = filterSalesOrderSequence ? null : offerDetail.getSalesOrderSequence();
            SequenceTransfer salesOrderSequenceTransfer = salesOrderSequence == null ? null : sequenceControl.getSequenceTransfer(userVisit, salesOrderSequence);
            Party departmentParty = filterDepartment ? null : offerDetail.getDepartmentParty();
            DepartmentTransfer departmentTransfer = departmentParty == null ? null : partyControl.getDepartmentTransfer(userVisit, offerDetail.getDepartmentParty());
            Selector offerItemSelector = filterOfferItemSelector ? null : offerDetail.getOfferItemSelector();
            SelectorTransfer offerItemSelectorTransfer = offerItemSelector == null ? null : selectorControl.getSelectorTransfer(userVisit, offerItemSelector);
            Filter offerItemPriceFilter = filterOfferItemPriceFilter ? null : offerDetail.getOfferItemPriceFilter();
            FilterTransfer offerItemPriceFilterTransfer = offerItemPriceFilter == null ? null : filterControl.getFilterTransfer(userVisit, offerItemPriceFilter);
            Boolean isDefault = filterIsDefault ? null : offerDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : offerDetail.getSortOrder();
            String description = filterDescription ? null : offerControl.getBestOfferDescription(offer, getLanguage());
            
            offerTransfer = new OfferTransfer(offerName, salesOrderSequenceTransfer, departmentTransfer, offerItemSelectorTransfer,
                    offerItemPriceFilterTransfer, isDefault, sortOrder, description);
            put(offer, offerTransfer);

            if(includeOfferCustomerTypes) {
                offerTransfer.setOfferCustomerTypes(ListWrapperBuilder.getInstance().filter(transferProperties, offerControl.getOfferCustomerTypeTransfersByOffer(userVisit, offer)));
            }

            if(includeOfferNameElements) {
                offerTransfer.setOfferNameElements(ListWrapperBuilder.getInstance().filter(transferProperties, offerNameElementControl.getOfferNameElementTransfers(userVisit)));
            }
        }
        
        return offerTransfer;
    }
    
}
