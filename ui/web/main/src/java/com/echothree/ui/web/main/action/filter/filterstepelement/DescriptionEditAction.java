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

package com.echothree.ui.web.main.action.filter.filterstepelement;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.edit.FilterStepElementDescriptionEdit;
import com.echothree.control.user.filter.common.form.EditFilterStepElementDescriptionForm;
import com.echothree.control.user.filter.common.result.EditFilterStepElementDescriptionResult;
import com.echothree.control.user.filter.common.spec.FilterStepElementDescriptionSpec;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Filter/FilterStepElement/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "FilterStepElementDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Filter/FilterStepElement/Description", redirect = true),
        @SproutForward(name = "Form", path = "/filter/filterstepelement/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String filterKindName = request.getParameter(ParameterConstants.FILTER_KIND_NAME);
        String filterTypeName = request.getParameter(ParameterConstants.FILTER_TYPE_NAME);
        String filterName = request.getParameter(ParameterConstants.FILTER_NAME);
        String filterStepName = request.getParameter(ParameterConstants.FILTER_STEP_NAME);
        String filterStepElementName = request.getParameter(ParameterConstants.FILTER_STEP_ELEMENT_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditFilterStepElementDescriptionForm commandForm = FilterUtil.getHome().getEditFilterStepElementDescriptionForm();
                FilterStepElementDescriptionSpec spec = FilterUtil.getHome().getFilterStepElementDescriptionSpec();
                
                if(filterKindName == null)
                    filterKindName = actionForm.getFilterKindName();
                if(filterTypeName == null)
                    filterTypeName = actionForm.getFilterTypeName();
                if(filterName == null)
                    filterName = actionForm.getFilterName();
                if(filterStepName == null)
                    filterStepName = actionForm.getFilterStepName();
                if(filterStepElementName == null)
                    filterStepElementName = actionForm.getFilterStepElementName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageChoice();
                
                commandForm.setSpec(spec);
                spec.setFilterKindName(filterKindName);
                spec.setFilterTypeName(filterTypeName);
                spec.setFilterName(filterName);
                spec.setFilterStepName(filterStepName);
                spec.setFilterStepElementName(filterStepElementName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    FilterStepElementDescriptionEdit edit = FilterUtil.getHome().getFilterStepElementDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = FilterUtil.getHome().editFilterStepElementDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditFilterStepElementDescriptionResult result = (EditFilterStepElementDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = FilterUtil.getHome().editFilterStepElementDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditFilterStepElementDescriptionResult result = (EditFilterStepElementDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        FilterStepElementDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setFilterKindName(filterKindName);
                            actionForm.setFilterTypeName(filterTypeName);
                            actionForm.setFilterName(filterName);
                            actionForm.setFilterStepName(filterStepName);
                            actionForm.setFilterStepElementName(filterStepElementName);
                            actionForm.setLanguageChoice(languageIsoName);
                            actionForm.setDescription(edit.getDescription());
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
            request.setAttribute(AttributeConstants.FILTER_KIND_NAME, filterKindName);
            request.setAttribute(AttributeConstants.FILTER_TYPE_NAME, filterTypeName);
            request.setAttribute(AttributeConstants.FILTER_NAME, filterName);
            request.setAttribute(AttributeConstants.FILTER_STEP_NAME, filterStepName);
            request.setAttribute(AttributeConstants.FILTER_STEP_ELEMENT_NAME, filterStepElementName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(5);
            
            parameters.put(ParameterConstants.FILTER_KIND_NAME, filterKindName);
            parameters.put(ParameterConstants.FILTER_TYPE_NAME, filterTypeName);
            parameters.put(ParameterConstants.FILTER_NAME, filterName);
            parameters.put(ParameterConstants.FILTER_STEP_NAME, filterStepName);
            parameters.put(ParameterConstants.FILTER_STEP_ELEMENT_NAME, filterStepElementName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}