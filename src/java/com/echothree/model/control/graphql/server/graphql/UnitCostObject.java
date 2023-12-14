// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.util.server.string.AmountUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("unit cost object")
@GraphQLName("UnitCost")
public class UnitCostObject
        extends BaseCurrencyObject {

    private final Long unformattedUnitCost; // Optional

    public UnitCostObject(Currency currency, Long unformattedUnitCost) {
        super(currency);

        this.unformattedUnitCost = unformattedUnitCost;
    }

    @GraphQLField
    @GraphQLDescription("unformatted unit cost")
    @GraphQLNonNull
    public Long getUnformattedUnitCost() {
        return unformattedUnitCost;
    }

    @GraphQLField
    @GraphQLDescription("unit cost")
    public String getUnitCost() {
        return unformattedUnitCost == null ? null :
                AmountUtils.getInstance().formatCostUnit(currency, unformattedUnitCost);
    }

}
