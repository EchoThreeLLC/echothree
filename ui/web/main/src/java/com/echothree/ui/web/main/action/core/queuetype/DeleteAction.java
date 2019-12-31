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

package com.echothree.ui.web.main.action.core.queuetype;

import com.echothree.control.user.queue.common.QueueUtil;
import com.echothree.control.user.queue.common.form.DeleteQueueTypeForm;
import com.echothree.control.user.queue.common.form.GetQueueTypeForm;
import com.echothree.control.user.queue.common.result.GetQueueTypeResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Core/QueueType/Delete",
    mappingClass = SecureActionMapping.class,
    name = "QueueTypeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/QueueType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/queuetype/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.QueueType.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setQueueTypeName(findParameter(request, ParameterConstants.QUEUE_TYPE_NAME, actionForm.getQueueTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetQueueTypeForm commandForm = QueueUtil.getHome().getGetQueueTypeForm();
        
        commandForm.setQueueTypeName(actionForm.getQueueTypeName());
        
        CommandResult commandResult = QueueUtil.getHome().getQueueType(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetQueueTypeResult result = (GetQueueTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.QUEUE_TYPE, result.getQueueType());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteQueueTypeForm commandForm = QueueUtil.getHome().getDeleteQueueTypeForm();

        commandForm.setQueueTypeName(actionForm.getQueueTypeName());

        return QueueUtil.getHome().deleteQueueType(getUserVisitPK(request), commandForm);
    }
    
}
