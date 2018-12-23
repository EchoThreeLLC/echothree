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

package com.echothree.ui.web.main.action.chain.chainkind;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.edit.ChainKindEdit;
import com.echothree.control.user.chain.common.form.EditChainKindForm;
import com.echothree.control.user.chain.common.result.EditChainKindResult;
import com.echothree.control.user.chain.common.spec.ChainKindSpec;
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
    path = "/Chain/ChainKind/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ChainKindEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/ChainKind/Main", redirect = true),
        @SproutForward(name = "Form", path = "/chain/chainkind/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ChainKindSpec, ChainKindEdit, EditChainKindForm, EditChainKindResult> {
    
    @Override
    protected ChainKindSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        ChainKindSpec spec = ChainUtil.getHome().getChainKindSpec();
        
        spec.setChainKindName(findParameter(request, ParameterConstants.ORIGINAL_CHAIN_KIND_NAME, actionForm.getOriginalChainKindName()));
        
        return spec;
    }
    
    @Override
    protected ChainKindEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        ChainKindEdit edit = ChainUtil.getHome().getChainKindEdit();

        edit.setChainKindName(actionForm.getChainKindName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditChainKindForm getForm()
            throws NamingException {
        return ChainUtil.getHome().getEditChainKindForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditChainKindResult result, ChainKindSpec spec, ChainKindEdit edit) {
        actionForm.setOriginalChainKindName(spec.getChainKindName());
        actionForm.setChainKindName(edit.getChainKindName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditChainKindForm commandForm)
            throws Exception {
        return ChainUtil.getHome().editChainKind(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditChainKindResult result) {
        request.setAttribute(AttributeConstants.CHAIN_KIND, result.getChainKind());
    }

}
