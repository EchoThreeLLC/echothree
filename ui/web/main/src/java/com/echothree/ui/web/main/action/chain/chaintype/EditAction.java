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

package com.echothree.ui.web.main.action.chain.chaintype;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.remote.edit.ChainTypeEdit;
import com.echothree.control.user.chain.remote.form.EditChainTypeForm;
import com.echothree.control.user.chain.remote.result.EditChainTypeResult;
import com.echothree.control.user.chain.remote.spec.ChainTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Chain/ChainType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ChainTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/ChainType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/chain/chaintype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ChainTypeSpec, ChainTypeEdit, EditChainTypeForm, EditChainTypeResult> {
    
    @Override
    protected ChainTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        ChainTypeSpec spec = ChainUtil.getHome().getChainTypeSpec();
        
        spec.setChainKindName(findParameter(request, ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName()));
        spec.setChainTypeName(findParameter(request, ParameterConstants.ORIGINAL_CHAIN_TYPE_NAME, actionForm.getOriginalChainTypeName()));
        
        return spec;
    }
    
    @Override
    protected ChainTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        ChainTypeEdit edit = ChainUtil.getHome().getChainTypeEdit();

        edit.setChainTypeName(actionForm.getChainTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditChainTypeForm getForm()
            throws NamingException {
        return ChainUtil.getHome().getEditChainTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditChainTypeResult result, ChainTypeSpec spec, ChainTypeEdit edit) {
        actionForm.setChainKindName(spec.getChainKindName());
        actionForm.setOriginalChainTypeName(spec.getChainTypeName());
        actionForm.setChainTypeName(edit.getChainTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditChainTypeForm commandForm)
            throws Exception {
        return ChainUtil.getHome().editChainType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditChainTypeResult result) {
        request.setAttribute(AttributeConstants.CHAIN_TYPE, result.getChainType());
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName());
    }
    
}
