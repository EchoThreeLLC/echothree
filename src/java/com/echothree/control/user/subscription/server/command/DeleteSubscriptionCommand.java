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

package com.echothree.control.user.subscription.server.command;

import com.echothree.control.user.subscription.remote.form.DeleteSubscriptionForm;
import com.echothree.model.control.subscription.server.SubscriptionControl;
import com.echothree.model.data.subscription.server.entity.Subscription;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteSubscriptionCommand
        extends BaseSimpleCommand<DeleteSubscriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SubscriptionName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of DeleteSubscriptionCommand */
    public DeleteSubscriptionCommand(UserVisitPK userVisitPK, DeleteSubscriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        SubscriptionControl subscriptionControl = (SubscriptionControl)Session.getModelController(SubscriptionControl.class);
        String subscriptionName = form.getSubscriptionName();
        Subscription subscription = subscriptionControl.getSubscriptionByNameForUpdate(subscriptionName);
        
        if(subscription != null) {
            subscriptionControl.deleteSubscription(subscription, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownSubscriptionName.name(), subscriptionName);
        }
        
        return null;
    }
    
}
