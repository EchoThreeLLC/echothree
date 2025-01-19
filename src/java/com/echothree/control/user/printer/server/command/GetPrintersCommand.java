// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.common.form.GetPrintersForm;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetPrintersCommand
        extends BaseSimpleCommand<GetPrintersForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of GetPrintersCommand */
    public GetPrintersCommand(UserVisitPK userVisitPK, GetPrintersForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
   @Override
    protected BaseResult execute() {
        var printerControl = Session.getModelController(PrinterControl.class);
       var result = PrinterResultFactory.getGetPrintersResult();
       var printerGroupName = form.getPrinterGroupName();
       var printerGroup = printerControl.getPrinterGroupByName(printerGroupName);
        
        if(printerGroup == null) {
            result.setPrinters(printerControl.getPrinterTransfers(getUserVisit()));
        } else {
            result.setPrinterGroup(printerControl.getPrinterGroupTransfer(getUserVisit(), printerGroup));
            result.setPrinters(printerControl.getPrinterTransfersByPrinterGroup(getUserVisit(), printerGroup));
        }
        
        return result;
    }
    
}
