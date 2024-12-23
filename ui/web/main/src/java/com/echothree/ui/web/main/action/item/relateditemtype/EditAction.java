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

package com.echothree.ui.web.main.action.item.relateditemtype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.RelatedItemTypeEdit;
import com.echothree.control.user.item.common.form.EditRelatedItemTypeForm;
import com.echothree.control.user.item.common.result.EditRelatedItemTypeResult;
import com.echothree.control.user.item.common.spec.RelatedItemTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Item/RelatedItemType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "RelatedItemTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/RelatedItemType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/relateditemtype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, RelatedItemTypeSpec, RelatedItemTypeEdit, EditRelatedItemTypeForm, EditRelatedItemTypeResult> {
    
    @Override
    protected RelatedItemTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getRelatedItemTypeUniversalSpec();
        
        spec.setRelatedItemTypeName(findParameter(request, ParameterConstants.ORIGINAL_RELATED_ITEM_TYPE_NAME, actionForm.getOriginalRelatedItemTypeName()));
        
        return spec;
    }
    
    @Override
    protected RelatedItemTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getRelatedItemTypeEdit();

        edit.setRelatedItemTypeName(actionForm.getRelatedItemTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditRelatedItemTypeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditRelatedItemTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditRelatedItemTypeResult result, RelatedItemTypeSpec spec, RelatedItemTypeEdit edit) {
        actionForm.setOriginalRelatedItemTypeName(spec.getRelatedItemTypeName());
        actionForm.setRelatedItemTypeName(edit.getRelatedItemTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditRelatedItemTypeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editRelatedItemType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditRelatedItemTypeResult result) {
        request.setAttribute(AttributeConstants.RELATED_ITEM_TYPE, result.getRelatedItemType());
    }

}
