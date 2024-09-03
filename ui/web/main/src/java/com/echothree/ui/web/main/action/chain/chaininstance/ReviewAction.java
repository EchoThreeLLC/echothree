// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.chain.chaininstance;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.form.GetChainInstanceForm;
import com.echothree.control.user.chain.common.result.GetChainInstanceResult;
import com.echothree.model.control.chain.common.ChainOptions;
import com.echothree.model.control.chain.common.transfer.ChainInstanceTransfer;
import com.echothree.model.control.core.common.CoreOptions;
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
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Chain/ChainInstance/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/chain/chaininstance/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var commandForm = ChainUtil.getHome().getGetChainInstanceForm();
        
        commandForm.setChainInstanceName(request.getParameter(ParameterConstants.CHAIN_INSTANCE_NAME));
        
        Set<String> options = new HashSet<>();
        options.add(ChainOptions.ChainInstanceIncludeChainInstanceStatus);
        options.add(ChainOptions.ChainInstanceIncludeChainInstanceEntityRoles);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        commandForm.setOptions(options);

        var commandResult = ChainUtil.getHome().getChainInstance(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetChainInstanceResult)executionResult.getResult();
        var chainInstance = result.getChainInstance();
        
        if(chainInstance == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.CHAIN_INSTANCE, result.getChainInstance());
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}