// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.inventory.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory condition object")
@GraphQLName("InventoryCondition")
public class InventoryConditionObject
        extends BaseEntityInstanceObject {
    
    private final InventoryCondition inventoryCondition; // Always Present
    
    public InventoryConditionObject(InventoryCondition inventoryCondition) {
        super(inventoryCondition.getPrimaryKey());
        
        this.inventoryCondition = inventoryCondition;
    }

    private InventoryConditionDetail inventoryConditionDetail; // Optional, use getInventoryConditionDetail()
    
    private InventoryConditionDetail getInventoryConditionDetail() {
        if(inventoryConditionDetail == null) {
            inventoryConditionDetail = inventoryCondition.getLastDetail();
        }
        
        return inventoryConditionDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("inventory condition name")
    @GraphQLNonNull
    public String getInventoryConditionName() {
        return getInventoryConditionDetail().getInventoryConditionName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryConditionDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryConditionDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryControl.getBestInventoryConditionDescription(inventoryCondition, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
