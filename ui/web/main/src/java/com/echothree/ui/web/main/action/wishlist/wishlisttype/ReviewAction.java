// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.wishlist.wishlisttype;

import com.echothree.control.user.wishlist.common.WishlistUtil;
import com.echothree.control.user.wishlist.common.form.GetWishlistTypeForm;
import com.echothree.control.user.wishlist.common.result.GetWishlistTypeResult;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypePriorityTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Wishlist/WishlistType/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/wishlist/wishlisttype/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            String wishlistTypeName = request.getParameter(ParameterConstants.WISHLIST_TYPE_NAME);
            GetWishlistTypeForm commandForm = WishlistUtil.getHome().getGetWishlistTypeForm();
            
            commandForm.setWishlistTypeName(wishlistTypeName);
            
            CommandResult commandResult = WishlistUtil.getHome().getWishlistType(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetWishlistTypeResult result = (GetWishlistTypeResult)executionResult.getResult();
            WishlistTypeTransfer wishlistTypeTransfer = result.getWishlistType();
            List<WishlistTypePriorityTransfer> wishlistTypePriorities = result.getWishlistTypePriorities();
            
            request.setAttribute(AttributeConstants.WISHLIST_TYPE, wishlistTypeTransfer);
            request.setAttribute(AttributeConstants.WISHLIST_TYPE_PRIORITIES, wishlistTypePriorities.isEmpty()? null: wishlistTypePriorities);
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}