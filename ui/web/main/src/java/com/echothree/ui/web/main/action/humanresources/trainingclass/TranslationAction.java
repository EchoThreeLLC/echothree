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

package com.echothree.ui.web.main.action.humanresources.trainingclass;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.remote.form.GetTrainingClassTranslationsForm;
import com.echothree.control.user.training.remote.result.GetTrainingClassTranslationsResult;
import com.echothree.model.control.training.remote.transfer.TrainingClassTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/TrainingClass/Translation",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/humanresources/trainingclass/translation.jsp")
    }
)
public class TranslationAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String trainingClassName = request.getParameter(ParameterConstants.TRAINING_CLASS_NAME);
        GetTrainingClassTranslationsForm commandForm = TrainingUtil.getHome().getGetTrainingClassTranslationsForm();

        commandForm.setTrainingClassName(trainingClassName);

        CommandResult commandResult = TrainingUtil.getHome().getTrainingClassTranslations(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetTrainingClassTranslationsResult result = (GetTrainingClassTranslationsResult) executionResult.getResult();
            TrainingClassTransfer trainingClassTransfer = result.getTrainingClass();

            request.setAttribute(AttributeConstants.TRAINING_CLASS, trainingClassTransfer);
            request.setAttribute(AttributeConstants.TRAINING_CLASS_TRANSLATIONS, result.getTrainingClassTranslations());
            forwardKey = ForwardConstants.DISPLAY;
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }

        return mapping.findForward(forwardKey);
    }
    
}
