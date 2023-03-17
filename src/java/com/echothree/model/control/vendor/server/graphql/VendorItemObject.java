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

package com.echothree.model.control.vendor.server.graphql;

import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicyObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicySecurityUtils;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicySecurityUtils;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.entity.VendorItemDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("vendor item object")
@GraphQLName("VendorItem")
public class VendorItemObject
        extends BaseEntityInstanceObject {
    
    private final VendorItem vendorItem; // Always Present
    
    public VendorItemObject(VendorItem vendorItem) {
        super(vendorItem.getPrimaryKey());
        
        this.vendorItem = vendorItem;
    }

    private VendorItemDetail vendorItemDetail; // Optional, use getVendorItemDetail()
    
    private VendorItemDetail getVendorItemDetail() {
        if(vendorItemDetail == null) {
            vendorItemDetail = vendorItem.getLastDetail();
        }
        
        return vendorItemDetail;
    }

    @GraphQLField
    @GraphQLDescription("item")
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getInstance().getHasItemAccess(env) ? new ItemObject(getVendorItemDetail().getItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("vendor")
    public VendorObject getVendor(final DataFetchingEnvironment env) {
        return VendorSecurityUtils.getInstance().getHasVendorAccess(env) ? new VendorObject(getVendorItemDetail().getVendorParty()) : null;
    }

    @GraphQLField
    @GraphQLDescription("vendor item name")
    @GraphQLNonNull
    public String getVendorItemName() {
        return getVendorItemDetail().getVendorItemName();
    }

    @GraphQLField
    @GraphQLDescription("description")
    public String getDescription() {
        return getVendorItemDetail().getDescription();
    }

    @GraphQLField
    @GraphQLDescription("priority")
    @GraphQLNonNull
    public int getPriority() {
        return getVendorItemDetail().getPriority();
    }

    @GraphQLField
    @GraphQLDescription("cancellation policy")
    public CancellationPolicyObject getCancellationPolicy(final DataFetchingEnvironment env) {
        var defaultCancellationPolicy = getVendorItemDetail().getCancellationPolicy();

        return defaultCancellationPolicy == null ? null : CancellationPolicySecurityUtils.getInstance().getHasCancellationPolicyAccess(env) ?
                new CancellationPolicyObject(defaultCancellationPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("return policy")
    public ReturnPolicyObject getReturnPolicy(final DataFetchingEnvironment env) {
        var defaultReturnPolicy = getVendorItemDetail().getReturnPolicy();

        return defaultReturnPolicy == null ? null : ReturnPolicySecurityUtils.getInstance().getHasReturnPolicyAccess(env) ?
                new ReturnPolicyObject(defaultReturnPolicy) : null;
    }

}
