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

package com.echothree.model.control.payment.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("payment processor action type object")
@GraphQLName("PaymentMethodType")
public class PaymentMethodTypeObject
        extends BaseEntityInstanceObject {
    
    private final PaymentMethodType paymentMethodType; // Always Present
    
    public PaymentMethodTypeObject(PaymentMethodType paymentMethodType) {
        super(paymentMethodType.getPrimaryKey());
        
        this.paymentMethodType = paymentMethodType;
    }

    private PaymentMethodTypeDetail paymentMethodTypeDetail; // Optional, use getPaymentMethodTypeDetail()
    
    private PaymentMethodTypeDetail getPaymentMethodTypeDetail() {
        if(paymentMethodTypeDetail == null) {
            paymentMethodTypeDetail = paymentMethodType.getLastDetail();
        }
        
        return paymentMethodTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("payment processor action type name")
    @GraphQLNonNull
    public String getPaymentMethodTypeName() {
        return getPaymentMethodTypeDetail().getPaymentMethodTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPaymentMethodTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPaymentMethodTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return paymentMethodTypeControl.getBestPaymentMethodTypeDescription(paymentMethodType, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
