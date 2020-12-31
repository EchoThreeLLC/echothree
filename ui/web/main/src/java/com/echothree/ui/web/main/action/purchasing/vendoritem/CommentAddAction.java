// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.purchasing.vendoritem;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.form.CreateCommentForm;
import com.echothree.control.user.comment.common.form.GetCommentTypeForm;
import com.echothree.control.user.comment.common.result.GetCommentTypeResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.form.GetVendorItemForm;
import com.echothree.control.user.vendor.common.result.GetVendorItemResult;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.vendor.common.transfer.VendorItemTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Purchasing/VendorItem/CommentAdd",
    mappingClass = SecureActionMapping.class,
    name = "VendorItemCommentAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/VendorItem/Review", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendoritem/commentAdd.jsp")
    }
)
public class CommentAddAction
        extends MainBaseAddAction<CommentAddActionForm> {

    @Override
    public void setupParameters(CommentAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setVendorItemName(findParameter(request, ParameterConstants.VENDOR_ITEM_NAME, actionForm.getVendorItemName()));
        actionForm.setCommentTypeName(findParameter(request, ParameterConstants.COMMENT_TYPE_NAME, actionForm.getCommentTypeName()));
    }
    
    public String getVendorItemEntityRef(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetVendorItemForm commandForm = VendorUtil.getHome().getGetVendorItemForm();
        
        commandForm.setPartyName(actionForm.getPartyName());
        commandForm.setVendorItemName(actionForm.getVendorItemName());
        
        CommandResult commandResult = VendorUtil.getHome().getVendorItem(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetVendorItemResult result = (GetVendorItemResult)executionResult.getResult();
        VendorItemTransfer vendorItem = result.getVendorItem();
        
        request.setAttribute(AttributeConstants.VENDOR_ITEM, vendorItem);
        
        return vendorItem.getEntityInstance().getEntityRef();
    }
    
    public void setupCommentTypeTransfer(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetCommentTypeForm commandForm = CommentUtil.getHome().getGetCommentTypeForm();
        
        commandForm.setComponentVendorName(ComponentVendors.ECHOTHREE.name());
        commandForm.setEntityTypeName(EntityTypes.Party.name());
        commandForm.setCommentTypeName(actionForm.getCommentTypeName());

        CommandResult commandResult = CommentUtil.getHome().getCommentType(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetCommentTypeResult result = (GetCommentTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.COMMENT_TYPE, result.getCommentType());
    }
    
    @Override
    public void setupTransfer(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        if(request.getAttribute(AttributeConstants.VENDOR_ITEM) == null) {
            getVendorItemEntityRef(actionForm, request);
        }
        
        setupCommentTypeTransfer(actionForm, request);
    }
    
    @Override
    public CommandResult doAdd(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateCommentForm commandForm = CommentUtil.getHome().getCreateCommentForm();

        commandForm.setEntityRef(getVendorItemEntityRef(actionForm, request));
        commandForm.setCommentTypeName(actionForm.getCommentTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setMimeTypeName(actionForm.getMimeTypeChoice());
        commandForm.setClobComment(actionForm.getClobComment());

        return CommentUtil.getHome().createComment(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CommentAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
        parameters.put(ParameterConstants.VENDOR_ITEM_NAME, actionForm.getVendorItemName());
    }
    
}
