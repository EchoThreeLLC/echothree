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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.GetOffersForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.factory.OfferFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetOffersCommand
        extends BasePaginatedMultipleEntitiesCommand<Offer, GetOffersForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Offer.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetOffersCommand */
    public GetOffersCommand(UserVisitPK userVisitPK, GetOffersForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var offerControl = Session.getModelController(OfferControl.class);

        return offerControl.countOffers();
    }

    @Override
    protected Collection<Offer> getEntities() {
        var offerControl = Session.getModelController(OfferControl.class);
        
        return offerControl.getOffers();
    }
    
    @Override
    protected BaseResult getResult(Collection<Offer> entities) {
        var result = OfferResultFactory.getGetOffersResult();

        if(entities != null) {
            var offerControl = Session.getModelController(OfferControl.class);

            if(session.hasLimit(OfferFactory.class)) {
                result.setOfferCount(getTotalEntities());
            }

            result.setOffers(offerControl.getOfferTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
