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

package com.echothree.ui.web.main.action.humanresources.trainingclasssection;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.form.GetTrainingClassSectionTranslationForm;
import com.echothree.control.user.training.common.result.GetTrainingClassSectionTranslationResult;
import com.echothree.model.control.training.common.transfer.TrainingClassSectionTranslationTransfer;
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
    path = "/HumanResources/TrainingClassSection/TranslationReview",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/humanresources/trainingclasssection/translationReview.jsp")
    }
)
public class TranslationReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetTrainingClassSectionTranslationForm commandForm = TrainingUtil.getHome().getGetTrainingClassSectionTranslationForm();

        commandForm.setTrainingClassName(request.getParameter(ParameterConstants.TRAINING_CLASS_NAME));
        commandForm.setTrainingClassSectionName(request.getParameter(ParameterConstants.TRAINING_CLASS_SECTION_NAME));
        commandForm.setLanguageIsoName(request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME));
        
        CommandResult commandResult = TrainingUtil.getHome().getTrainingClassSectionTranslation(getUserVisitPK(request), commandForm);
        TrainingClassSectionTranslationTransfer trainingClassSectionTranslation = null;
        
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetTrainingClassSectionTranslationResult result = (GetTrainingClassSectionTranslationResult)executionResult.getResult();
            
            trainingClassSectionTranslation = result.getTrainingClassSectionTranslation();
        }
        
        if(trainingClassSectionTranslation == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.TRAINING_CLASS_SECTION_TRANSLATION, trainingClassSectionTranslation);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
