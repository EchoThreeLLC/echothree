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

package com.echothree.ui.web.main.action.item.harmonizedtariffschedulecodeunit;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.remote.form.GetHarmonizedTariffScheduleCodeUnitForm;
import com.echothree.control.user.item.remote.result.GetHarmonizedTariffScheduleCodeUnitResult;
import com.echothree.model.control.item.remote.transfer.HarmonizedTariffScheduleCodeUnitTransfer;
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
    path = "/Item/HarmonizedTariffScheduleCodeUnit/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/item/harmonizedtariffschedulecodeunit/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetHarmonizedTariffScheduleCodeUnitForm commandForm = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodeUnitForm();

        commandForm.setHarmonizedTariffScheduleCodeUnitName(request.getParameter(ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT_NAME));
        
        CommandResult commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUnit(getUserVisitPK(request), commandForm);
        HarmonizedTariffScheduleCodeUnitTransfer harmonizedTariffScheduleCodeUnit = null;
        
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetHarmonizedTariffScheduleCodeUnitResult result = (GetHarmonizedTariffScheduleCodeUnitResult)executionResult.getResult();
            
            harmonizedTariffScheduleCodeUnit = result.getHarmonizedTariffScheduleCodeUnit();
        }
        
        if(harmonizedTariffScheduleCodeUnit == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT, harmonizedTariffScheduleCodeUnit);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
