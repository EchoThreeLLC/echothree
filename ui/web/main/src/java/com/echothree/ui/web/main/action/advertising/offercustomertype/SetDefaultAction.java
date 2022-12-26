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

package com.echothree.ui.web.main.action.advertising.offercustomertype;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.form.SetDefaultOfferCustomerTypeForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Advertising/OfferCustomerType/SetDefault",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/OfferCustomerType/Main", redirect = true)
    }
)
public class SetDefaultAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String offerName = request.getParameter(ParameterConstants.OFFER_NAME);
        SetDefaultOfferCustomerTypeForm commandForm = OfferUtil.getHome().getSetDefaultOfferCustomerTypeForm();

        commandForm.setOfferName(offerName);
        commandForm.setCustomerTypeName(request.getParameter(ParameterConstants.CUSTOMER_TYPE_NAME));

        OfferUtil.getHome().setDefaultOfferCustomerType(getUserVisitPK(request), commandForm);

        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(ForwardConstants.DISPLAY));
        Map<String, String> parameters = new HashMap<>(2);

        parameters.put(ParameterConstants.OFFER_NAME, offerName);
        customActionForward.setParameters(parameters);

        return customActionForward;
    }

}
