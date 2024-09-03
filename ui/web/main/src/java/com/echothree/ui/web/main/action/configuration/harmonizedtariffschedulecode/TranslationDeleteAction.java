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

package com.echothree.ui.web.main.action.configuration.harmonizedtariffschedulecode;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodeTranslationResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/HarmonizedTariffScheduleCode/TranslationDelete",
    mappingClass = SecureActionMapping.class,
    name = "HarmonizedTariffScheduleCodeTranslationDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/HarmonizedTariffScheduleCode/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/harmonizedtariffschedulecode/translationDelete.jsp")
    }
)
public class TranslationDeleteAction
        extends MainBaseDeleteAction<TranslationDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.HarmonizedTariffScheduleCodeTranslation.name();
    }
    
    @Override
    public void setupParameters(TranslationDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setCountryName(findParameter(request, ParameterConstants.COUNTRY_NAME, actionForm.getCountryName()));
        actionForm.setHarmonizedTariffScheduleCodeName(findParameter(request, ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME, actionForm.getHarmonizedTariffScheduleCodeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(TranslationDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodeTranslationForm();
        
        commandForm.setCountryName(actionForm.getCountryName());
        commandForm.setHarmonizedTariffScheduleCodeName(actionForm.getHarmonizedTariffScheduleCodeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCodeTranslation(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetHarmonizedTariffScheduleCodeTranslationResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_TRANSLATION, result.getHarmonizedTariffScheduleCodeTranslation());
        }
    }
    
    @Override
    public CommandResult doDelete(TranslationDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getDeleteHarmonizedTariffScheduleCodeTranslationForm();

        commandForm.setCountryName(actionForm.getCountryName());
        commandForm.setHarmonizedTariffScheduleCodeName(actionForm.getHarmonizedTariffScheduleCodeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return ItemUtil.getHome().deleteHarmonizedTariffScheduleCodeTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COUNTRY_NAME, actionForm.getCountryName());
        parameters.put(ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME, actionForm.getHarmonizedTariffScheduleCodeName());
    }
    
}
