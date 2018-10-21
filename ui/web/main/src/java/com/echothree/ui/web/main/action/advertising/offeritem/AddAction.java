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

package com.echothree.ui.web.main.action.advertising.offeritem;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.remote.form.CreateOfferItemForm;
import com.echothree.control.user.offer.remote.form.GetOfferForm;
import com.echothree.control.user.offer.remote.result.GetOfferResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Advertising/OfferItem/Add",
    mappingClass = SecureActionMapping.class,
    name = "OfferItemAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/OfferItem/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/offeritem/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setOfferName(findParameter(request, ParameterConstants.OFFER_NAME, actionForm.getOfferName()));
    }

    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetOfferForm commandForm = OfferUtil.getHome().getGetOfferForm();

        commandForm.setOfferName(actionForm.getOfferName());

        CommandResult commandResult = OfferUtil.getHome().getOffer(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetOfferResult result = (GetOfferResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.OFFER, result.getOffer());
    }

    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateOfferItemForm commandForm = OfferUtil.getHome().getCreateOfferItemForm();

        commandForm.setOfferName(actionForm.getOfferName());
        commandForm.setItemName(actionForm.getItemName());

        return OfferUtil.getHome().createOfferItem(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.OFFER_NAME, actionForm.getOfferName());
    }

}
