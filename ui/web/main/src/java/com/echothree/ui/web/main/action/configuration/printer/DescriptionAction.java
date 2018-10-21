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

package com.echothree.ui.web.main.action.configuration.printer;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.remote.form.GetPrinterDescriptionsForm;
import com.echothree.control.user.printer.remote.result.GetPrinterDescriptionsResult;
import com.echothree.model.control.printer.remote.transfer.PrinterTransfer;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/Printer/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/printer/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            String printerGroupName = request.getParameter(ParameterConstants.PRINTER_GROUP_NAME);
            String printerName = request.getParameter(ParameterConstants.PRINTER_NAME);
            GetPrinterDescriptionsForm commandForm = PrinterUtil.getHome().getGetPrinterDescriptionsForm();
            
            commandForm.setPrinterName(printerName);
            
            CommandResult commandResult = PrinterUtil.getHome().getPrinterDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPrinterDescriptionsResult result = (GetPrinterDescriptionsResult)executionResult.getResult();
            PrinterTransfer printerTransfer = result.getPrinter();
            
            request.setAttribute(AttributeConstants.PRINTER_GROUP, printerTransfer.getPrinterGroup());
            request.setAttribute(AttributeConstants.PRINTER, printerTransfer);
            request.setAttribute(AttributeConstants.PRINTER_DESCRIPTIONS, result.getPrinterDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}