// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.event;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.EntityAppearanceEdit;
import com.echothree.control.user.core.common.form.EditEntityAppearanceForm;
import com.echothree.control.user.core.common.result.EditEntityAppearanceResult;
import com.echothree.control.user.core.common.spec.EntityRefSpec;
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
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/Event/EntityAppearanceEdit",
    mappingClass = SecureActionMapping.class,
    name = "EntityAppearanceEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Event/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/event/entityAppearanceEdit.jsp")
    }
)
public class EntityAppearanceEditAction
        extends MainBaseAction<EntityAppearanceEditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EntityAppearanceEditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String entityRef = request.getParameter(ParameterConstants.ENTITY_REF);
        EditEntityAppearanceForm commandForm = CoreUtil.getHome().getEditEntityAppearanceForm();
        EntityRefSpec spec = CoreUtil.getHome().getEntityRefSpec();

        if(entityRef == null)
            entityRef = actionForm.getEntityRef();

        commandForm.setSpec(spec);
        spec.setEntityRef(entityRef);

        if(wasPost(request)) {
            EntityAppearanceEdit edit = CoreUtil.getHome().getEntityAppearanceEdit();

            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);

            edit.setAppearanceName(actionForm.getAppearanceChoice());

            CommandResult commandResult = CoreUtil.getHome().editEntityAppearance(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();

                if(executionResult != null) {
                    EditEntityAppearanceResult result = (EditEntityAppearanceResult)executionResult.getResult();

                    if(result != null) {
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                }

                setCommandResultAttribute(request, commandResult);

                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            CommandResult commandResult = CoreUtil.getHome().editEntityAppearance(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditEntityAppearanceResult result = (EditEntityAppearanceResult)executionResult.getResult();

            if(result != null) {
                EntityAppearanceEdit edit = result.getEdit();

                if(edit != null) {
                    actionForm.setEntityRef(entityRef);
                    actionForm.setAppearanceChoice(edit.getAppearanceName());
                }

                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }

            setCommandResultAttribute(request, commandResult);

            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.ENTITY_REF, entityRef);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.ENTITY_REF, entityRef);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
