// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.accounting.division;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.DivisionEdit;
import com.echothree.control.user.party.common.form.EditDivisionForm;
import com.echothree.control.user.party.common.result.EditDivisionResult;
import com.echothree.control.user.party.common.spec.DivisionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/Division/Edit",
    mappingClass = SecureActionMapping.class,
    name = "DivisionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/Division/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/division/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String companyName = request.getParameter(ParameterConstants.COMPANY_NAME);
        String originalDivisionName = request.getParameter(ParameterConstants.ORIGINAL_DIVISION_NAME);
        EditDivisionForm commandForm = PartyUtil.getHome().getEditDivisionForm();
        DivisionSpec spec = PartyUtil.getHome().getDivisionSpec();
        
        if(companyName == null)
            companyName = actionForm.getCompanyName();
        if(originalDivisionName == null)
            originalDivisionName = actionForm.getOriginalDivisionName();
        
        commandForm.setSpec(spec);
        spec.setCompanyName(companyName);
        spec.setDivisionName(originalDivisionName);
        
        if(wasPost(request)) {
            boolean wasCancelled = wasCancelled(request);
            
            if(wasCancelled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                DivisionEdit edit = PartyUtil.getHome().getDivisionEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setDivisionName(actionForm.getDivisionName());
                edit.setName(actionForm.getName());
                edit.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
                edit.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
                edit.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
                edit.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());
                edit.setIsDefault(actionForm.getIsDefault().toString());
                edit.setSortOrder(actionForm.getSortOrder());
            }
            
            CommandResult commandResult = PartyUtil.getHome().editDivision(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors() && !wasCancelled) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditDivisionResult result = (EditDivisionResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = PartyUtil.getHome().editDivision(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditDivisionResult result = (EditDivisionResult)executionResult.getResult();
            
            if(result != null) {
                DivisionEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setCompanyName(companyName);
                    actionForm.setOriginalDivisionName(edit.getDivisionName());
                    actionForm.setDivisionName(edit.getDivisionName());
                    actionForm.setName(edit.getName());
                    actionForm.setLanguageChoice(edit.getPreferredLanguageIsoName());
                    actionForm.setCurrencyChoice(edit.getPreferredCurrencyIsoName());
                    actionForm.setTimeZoneChoice(edit.getPreferredJavaTimeZoneName());
                    actionForm.setDateTimeFormatChoice(edit.getPreferredDateTimeFormatName());
                    actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                    actionForm.setSortOrder(edit.getSortOrder());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.COMPANY_NAME, companyName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.COMPANY_NAME, companyName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}