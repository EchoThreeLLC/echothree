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

package com.echothree.model.control.search.server.graphql;

import com.echothree.control.user.search.common.form.GetVendorResultsForm;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.graphql.VendorObject;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("vendor results object")
@GraphQLName("VendorResults")
public class VendorResultsObject
        extends BaseResultsObject<GetVendorResultsForm> {

    public VendorResultsObject(GetVendorResultsForm form) {
        super(ComponentVendors.ECHOTHREE.name(), EntityTypes.Party.name(), SearchKinds.VENDOR.name(), form);
    }

    @GraphQLField
    @GraphQLDescription("vendors")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<VendorObject> getVendors(final DataFetchingEnvironment env) {
        var userVisitSearch = getUserVisitSearch(env);

        if(userVisitSearch == null) {
            return Connections.emptyConnection();
        } else {
            var totalCount = getTotalCount(env);

            try(var objectLimiter = new ObjectLimiter(env, SearchResultConstants.ENTITY_TYPE_NAME, totalCount)) {
                var vendorControl = Session.getModelController(VendorControl.class);
                var vendors = vendorControl.getVendorObjectsFromUserVisitSearch(userVisitSearch);

                return new CountedObjects<>(objectLimiter, vendors);
            }
        }
    }
    
}
