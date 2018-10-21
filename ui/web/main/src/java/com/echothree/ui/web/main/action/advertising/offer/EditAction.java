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

package com.echothree.ui.web.main.action.advertising.offer;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.remote.edit.OfferEdit;
import com.echothree.control.user.offer.remote.form.EditOfferForm;
import com.echothree.control.user.offer.remote.result.EditOfferResult;
import com.echothree.control.user.offer.remote.spec.OfferSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Advertising/Offer/Edit",
    mappingClass = SecureActionMapping.class,
    name = "OfferEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/Offer/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/offer/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String originalOfferName = request.getParameter(ParameterConstants.ORIGINAL_OFFER_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditOfferForm commandForm = OfferUtil.getHome().getEditOfferForm();
                OfferSpec spec = OfferUtil.getHome().getOfferSpec();
                
                if(originalOfferName == null) {
                    originalOfferName = actionForm.getOriginalOfferName();
                }
                
                commandForm.setSpec(spec);
                spec.setOfferName(originalOfferName);
                
                if(wasPost(request)) {
                    OfferEdit edit = OfferUtil.getHome().getOfferEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setOfferName(actionForm.getOfferName());
                    edit.setSalesOrderSequenceName(actionForm.getSalesOrderSequenceChoice());
                    edit.setOfferItemSelectorName(actionForm.getOfferItemSelectorChoice());
                    edit.setOfferItemPriceFilterName(actionForm.getOfferItemPriceFilterChoice());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = OfferUtil.getHome().editOffer(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditOfferResult result = (EditOfferResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = OfferUtil.getHome().editOffer(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditOfferResult result = (EditOfferResult)executionResult.getResult();
                    
                    if(result != null) {
                        OfferEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setOriginalOfferName(edit.getOfferName());
                            actionForm.setOfferName(edit.getOfferName());
                            actionForm.setSalesOrderSequenceChoice(edit.getSalesOrderSequenceName());
                            actionForm.setOfferItemSelectorChoice(edit.getOfferItemSelectorName());
                            actionForm.setOfferItemPriceFilterChoice(edit.getOfferItemPriceFilterName());
                            actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            actionForm.setSortOrder(edit.getSortOrder());
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
        
        return mapping.findForward(forwardKey);
    }
    
}