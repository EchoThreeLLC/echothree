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

package com.echothree.ui.web.main.action.advertising.offerchaintype;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.edit.OfferChainTypeEdit;
import com.echothree.control.user.offer.common.form.EditOfferChainTypeForm;
import com.echothree.control.user.offer.common.result.EditOfferChainTypeResult;
import com.echothree.control.user.offer.common.spec.OfferChainTypeSpec;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Advertising/OfferChainType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "OfferChainTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/OfferChainType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/offerchaintype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String offerName = request.getParameter(ParameterConstants.OFFER_NAME);
        String chainKindName = request.getParameter(ParameterConstants.CHAIN_KIND_NAME);
        String chainTypeName = request.getParameter(ParameterConstants.CHAIN_TYPE_NAME);
        EditActionForm actionForm = (EditActionForm)form;
        EditOfferChainTypeForm commandForm = OfferUtil.getHome().getEditOfferChainTypeForm();
        OfferChainTypeSpec spec = OfferUtil.getHome().getOfferChainTypeSpec();
        
        if(offerName == null)
            offerName = actionForm.getOfferName();
        if(chainKindName == null)
            chainKindName = actionForm.getChainKindName();
        if(chainTypeName == null)
            chainTypeName = actionForm.getChainTypeName();
        
        commandForm.setSpec(spec);
        spec.setOfferName(offerName);
        spec.setChainKindName(chainKindName);
        spec.setChainTypeName(chainTypeName);
        
        if(wasPost(request)) {
            OfferChainTypeEdit edit = OfferUtil.getHome().getOfferChainTypeEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setChainName(actionForm.getChainChoice());
            
            CommandResult commandResult = OfferUtil.getHome().editOfferChainType(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditOfferChainTypeResult result = (EditOfferChainTypeResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = OfferUtil.getHome().editOfferChainType(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditOfferChainTypeResult result = (EditOfferChainTypeResult)executionResult.getResult();
            
            if(result != null) {
                OfferChainTypeEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setOfferName(offerName);
                    actionForm.setChainKindName(chainKindName);
                    actionForm.setChainTypeName(chainTypeName);
                    actionForm.setChainChoice(edit.getChainName());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.OFFER_NAME, offerName);
            request.setAttribute(AttributeConstants.CHAIN_KIND_NAME, chainKindName);
            request.setAttribute(AttributeConstants.CHAIN_TYPE_NAME, chainTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.OFFER_NAME, offerName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}