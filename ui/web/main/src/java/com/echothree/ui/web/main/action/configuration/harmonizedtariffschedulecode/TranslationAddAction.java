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

package com.echothree.ui.web.main.action.configuration.harmonizedtariffschedulecode;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.CreateHarmonizedTariffScheduleCodeTranslationForm;
import com.echothree.control.user.item.common.form.GetHarmonizedTariffScheduleCodeForm;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodeResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/HarmonizedTariffScheduleCode/TranslationAdd",
    mappingClass = SecureActionMapping.class,
    name = "HarmonizedTariffScheduleCodeTranslationAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/HarmonizedTariffScheduleCode/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/harmonizedtariffschedulecode/translationAdd.jsp")
    }
)
public class TranslationAddAction
        extends MainBaseAddAction<TranslationAddActionForm> {

    @Override
    public void setupParameters(TranslationAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setCountryName(findParameter(request, ParameterConstants.COUNTRY_NAME, actionForm.getCountryName()));
        actionForm.setHarmonizedTariffScheduleCodeName(findParameter(request, ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME, actionForm.getHarmonizedTariffScheduleCodeName()));
    }
    
    @Override
    public void setupTransfer(TranslationAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetHarmonizedTariffScheduleCodeForm commandForm = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodeForm();

        commandForm.setCountryName(actionForm.getCountryName());
        commandForm.setHarmonizedTariffScheduleCodeName(actionForm.getHarmonizedTariffScheduleCodeName());
        
        CommandResult commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCode(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetHarmonizedTariffScheduleCodeResult result = (GetHarmonizedTariffScheduleCodeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE, result.getHarmonizedTariffScheduleCode());
        }
    }
    
    @Override
    public CommandResult doAdd(TranslationAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateHarmonizedTariffScheduleCodeTranslationForm commandForm = ItemUtil.getHome().getCreateHarmonizedTariffScheduleCodeTranslationForm();

        commandForm.setCountryName(actionForm.getCountryName());
        commandForm.setHarmonizedTariffScheduleCodeName(actionForm.getHarmonizedTariffScheduleCodeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setOverviewMimeTypeName(actionForm.getOverviewMimeTypeChoice());
        commandForm.setOverview(actionForm.getOverview());

        return ItemUtil.getHome().createHarmonizedTariffScheduleCodeTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COUNTRY_NAME, actionForm.getCountryName());
        parameters.put(ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME, actionForm.getHarmonizedTariffScheduleCodeName());
    }
    
}
