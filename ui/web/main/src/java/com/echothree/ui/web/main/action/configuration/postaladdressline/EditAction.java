// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.postaladdressline;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.EditPostalAddressLineResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
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
    path = "/Configuration/PostalAddressLine/Edit",
    mappingClass = SecureActionMapping.class,
    name = "PostalAddressLineEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PostalAddressLine/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/postaladdressline/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var postalAddressFormatName = request.getParameter(ParameterConstants.POSTAL_ADDRESS_FORMAT_NAME);
        var originalPostalAddressLineSortOrder = request.getParameter(ParameterConstants.ORIGINAL_POSTAL_ADDRESS_LINE_SORT_ORDER);
        
        try {
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = ContactUtil.getHome().getEditPostalAddressLineForm();
                var spec = ContactUtil.getHome().getPostalAddressLineSpec();
                
                if(postalAddressFormatName == null)
                    postalAddressFormatName = actionForm.getPostalAddressFormatName();
                if(originalPostalAddressLineSortOrder == null)
                    originalPostalAddressLineSortOrder = actionForm.getOriginalPostalAddressLineSortOrder();
                
                commandForm.setSpec(spec);
                spec.setPostalAddressFormatName(postalAddressFormatName);
                spec.setPostalAddressLineSortOrder(originalPostalAddressLineSortOrder);
                
                if(wasPost(request)) {
                    var edit = ContactUtil.getHome().getPostalAddressLineEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setPostalAddressLineSortOrder(actionForm.getPostalAddressLineSortOrder());
                    edit.setPrefix(actionForm.getPrefix());
                    edit.setAlwaysIncludePrefix(actionForm.getAlwaysIncludePrefix().toString());
                    edit.setSuffix(actionForm.getSuffix());
                    edit.setAlwaysIncludeSuffix(actionForm.getAlwaysIncludeSuffix().toString());
                    edit.setCollapseIfEmpty(actionForm.getCollapseIfEmpty().toString());

                    var commandResult = ContactUtil.getHome().editPostalAddressLine(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditPostalAddressLineResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContactUtil.getHome().editPostalAddressLine(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditPostalAddressLineResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setPostalAddressFormatName(postalAddressFormatName);
                            actionForm.setOriginalPostalAddressLineSortOrder(edit.getPostalAddressLineSortOrder());
                            actionForm.setPostalAddressLineSortOrder(edit.getPostalAddressLineSortOrder());
                            actionForm.setPrefix(edit.getPrefix());
                            actionForm.setAlwaysIncludePrefix(Boolean.valueOf(edit.getAlwaysIncludePrefix()));
                            actionForm.setSuffix(edit.getSuffix());
                            actionForm.setAlwaysIncludeSuffix(Boolean.valueOf(edit.getAlwaysIncludeSuffix()));
                            actionForm.setCollapseIfEmpty(Boolean.valueOf(edit.getCollapseIfEmpty()));
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

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.POSTAL_ADDRESS_FORMAT_NAME, postalAddressFormatName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.POSTAL_ADDRESS_FORMAT_NAME, postalAddressFormatName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}