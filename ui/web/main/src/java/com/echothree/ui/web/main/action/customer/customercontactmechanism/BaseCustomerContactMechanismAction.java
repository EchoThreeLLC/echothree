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

package com.echothree.ui.web.main.action.customer.customercontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.remote.form.GetContactMechanismForm;
import com.echothree.control.user.contact.remote.result.GetContactMechanismResult;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.remote.form.GetCustomerForm;
import com.echothree.control.user.customer.remote.result.GetCustomerResult;
import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.remote.form.GetCountryForm;
import com.echothree.control.user.geo.remote.result.GetCountryResult;
import com.echothree.model.control.customer.remote.transfer.CustomerTransfer;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseCustomerContactMechanismAction<A
        extends ActionForm>
        extends MainBaseAction<A> {

    public void setupDefaultCountry(HttpServletRequest request)
            throws NamingException {
        GetCountryForm commandForm =  GeoUtil.getHome().getGetCountryForm();
        
        Set<String> options = new HashSet<>();
        options.add(GeoOptions.CountryIncludeAliases);
        commandForm.setOptions(options);

        CommandResult commandResult = GeoUtil.getHome().getCountry(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetCountryResult result = (GetCountryResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.DEFAULT_COUNTRY, result.getCountry());
    }
    
    public void setupCountry(HttpServletRequest request, String countryName)
            throws NamingException {
        GetCountryForm commandForm =  GeoUtil.getHome().getGetCountryForm();
        
        commandForm.setCountryName(countryName);

        CommandResult commandResult = GeoUtil.getHome().getCountry(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetCountryResult result = (GetCountryResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.COUNTRY, result.getCountry());
    }
    
    public static void setupCustomer(HttpServletRequest request, String partyName)
            throws NamingException {
        GetCustomerForm commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setPartyName(partyName);

        CommandResult commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCustomerResult result = (GetCustomerResult)executionResult.getResult();
            CustomerTransfer customer = result.getCustomer();

            if(customer != null) {
                request.setAttribute(AttributeConstants.CUSTOMER, customer);
            }
        }
    }

    public void setupCustomer(HttpServletRequest request)
            throws NamingException {
        setupCustomer(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public static void setupContactMechanismTransfer(HttpServletRequest request, String contactMechanismName)
            throws NamingException {
        GetContactMechanismForm commandForm = ContactUtil.getHome().getGetContactMechanismForm();

        commandForm.setContactMechanismName(contactMechanismName);

        CommandResult commandResult = ContactUtil.getHome().getContactMechanism(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetContactMechanismResult result = (GetContactMechanismResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CONTACT_MECHANISM, result.getContactMechanism());
    }

    public static void setupPartyContactMechanismTransfer(HttpServletRequest request, String partyName, String contactMechanismName)
            throws NamingException {
        GetContactMechanismForm commandForm = ContactUtil.getHome().getGetContactMechanismForm();

        commandForm.setPartyName(partyName);
        commandForm.setContactMechanismName(contactMechanismName);

        CommandResult commandResult = ContactUtil.getHome().getContactMechanism(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetContactMechanismResult result = (GetContactMechanismResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_CONTACT_MECHANISM, result.getPartyContactMechanism());
    }

}
