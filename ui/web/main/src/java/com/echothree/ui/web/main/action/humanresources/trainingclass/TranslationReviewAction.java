// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.control.user.training.common.form.GetTrainingClassTranslationForm;
import com.echothree.control.user.training.common.result.GetTrainingClassTranslationResult;
import com.echothree.model.control.training.common.transfer.TrainingClassTranslationTransfer;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/TrainingClass/TranslationReview",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/humanresources/trainingclass/translationReview.jsp")
    }
)
public class TranslationReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        GetTrainingClassTranslationForm commandForm = TrainingUtil.getHome().getGetTrainingClassTranslationForm();

        commandForm.setTrainingClassName(request.getParameter(ParameterConstants.TRAINING_CLASS_NAME));
        commandForm.setLanguageIsoName(request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME));
        
        CommandResult commandResult = TrainingUtil.getHome().getTrainingClassTranslation(getUserVisitPK(request), commandForm);
        TrainingClassTranslationTransfer trainingClassTranslation = null;
        
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetTrainingClassTranslationResult result = (GetTrainingClassTranslationResult)executionResult.getResult();
            
            trainingClassTranslation = result.getTrainingClassTranslation();
        }
        
        if(trainingClassTranslation == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.TRAINING_CLASS_TRANSLATION, trainingClassTranslation);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
