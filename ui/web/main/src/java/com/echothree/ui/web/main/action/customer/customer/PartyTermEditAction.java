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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.remote.edit.PartyTermEdit;
import com.echothree.control.user.term.remote.form.EditPartyTermForm;
import com.echothree.control.user.term.remote.result.EditPartyTermResult;
import com.echothree.control.user.term.remote.spec.PartyTermSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Customer/Customer/PartyTermEdit",
    mappingClass = SecureActionMapping.class,
    name = "PartyTermEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/Customer/Review", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customer/partyTermEdit.jsp")
    }
)
public class PartyTermEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String customerName = request.getParameter(ParameterConstants.CUSTOMER_NAME);
        
        try {
            String partyName = request.getParameter(ParameterConstants.PARTY_NAME);
            String currencyIsoName = request.getParameter(ParameterConstants.CURRENCY_ISO_NAME);
            
            if(forwardKey == null) {
                PartyTermEditActionForm actionForm = (PartyTermEditActionForm)form;
                EditPartyTermForm commandForm = TermUtil.getHome().getEditPartyTermForm();
                PartyTermSpec spec = TermUtil.getHome().getPartyTermSpec();
                
                if(partyName == null)
                    partyName = actionForm.getPartyName();
                if(customerName == null)
                    customerName = actionForm.getCustomerName();
                
                commandForm.setSpec(spec);
                spec.setPartyName(partyName);
                
                if(wasPost(request)) {
                    PartyTermEdit edit = TermUtil.getHome().getPartyTermEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setTermName(actionForm.getTermChoice());
                    edit.setTaxable(actionForm.getTaxable().toString());
                    
                    CommandResult commandResult = TermUtil.getHome().editPartyTerm(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditPartyTermResult result = (EditPartyTermResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = TermUtil.getHome().editPartyTerm(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditPartyTermResult result = (EditPartyTermResult)executionResult.getResult();
                    
                    if(result != null) {
                        PartyTermEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setPartyName(partyName);
                            actionForm.setCustomerName(customerName);
                            actionForm.setTermChoice(edit.getTermName());
                            actionForm.setTaxable(Boolean.valueOf(edit.getTaxable()));
                        }

                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CUSTOMER_NAME, customerName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.CUSTOMER_NAME, customerName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}