// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.remote.form.GetOfferItemPricesForm;
import com.echothree.control.user.offer.remote.result.GetOfferItemPricesResult;
import com.echothree.control.user.offer.remote.result.OfferResultFactory;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetOfferItemPricesCommand
        extends BaseMultipleEntitiesCommand<OfferItemPrice, GetOfferItemPricesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferItemPrice.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetOfferItemPricesCommand */
    public GetOfferItemPricesCommand(UserVisitPK userVisitPK, GetOfferItemPricesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    private Offer offer;
    private Item item;
    private OfferItem offerItem;
    
    @Override
    protected Collection<OfferItemPrice> getEntities() {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        String offerName = form.getOfferName();
        Collection<OfferItemPrice> offerItemPrices = null;
        
        offer = offerControl.getOfferByName(offerName);
        
        if(offer != null) {
            ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            UserVisit userVisit = getUserVisit();
            String itemName = form.getItemName();
           
            item = itemControl.getItemByName(itemName);
            
            if(item != null) {
                offerItem = offerControl.getOfferItem(offer, item);
                
                if(offerItem != null) {
                    offerItemPrices = offerControl.getOfferItemPricesByOfferItem(offerItem);
                } else {
                    addExecutionError(ExecutionErrors.UnknownOfferItem.name(), offer.getLastDetail().getOfferName(),
                            item.getLastDetail().getItemName());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
        
        return offerItemPrices;
    }
    
    @Override
    protected BaseResult getTransfers(Collection<OfferItemPrice> entities) {
        GetOfferItemPricesResult result = OfferResultFactory.getGetOfferItemPricesResult();
        
        if (entities != null) {
            OfferControl offerControl = (OfferControl) Session.getModelController(OfferControl.class);
            ItemControl itemControl = (ItemControl) Session.getModelController(ItemControl.class);
            UserVisit userVisit = getUserVisit();
            
            result.setOffer(offerControl.getOfferTransfer(userVisit, offer));
            result.setItem(itemControl.getItemTransfer(userVisit, item));
            result.setOfferItem(offerControl.getOfferItemTransfer(userVisit, offerItem));
            result.setOfferItemPrices(offerControl.getOfferItemPriceTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
