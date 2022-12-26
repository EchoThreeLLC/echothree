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

package com.echothree.model.control.printer.server.transfer;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.printer.common.transfer.PrinterGroupJobTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterGroupTransfer;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.printer.common.workflow.PrinterGroupJobStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.printer.server.entity.PrinterGroupJob;
import com.echothree.model.data.printer.server.entity.PrinterGroupJobDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PrinterGroupJobTransferCache
        extends BasePrinterTransferCache<PrinterGroupJob, PrinterGroupJobTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of PrinterGroupJobTransferCache */
    public PrinterGroupJobTransferCache(UserVisit userVisit, PrinterControl printerControl) {
        super(userVisit, printerControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PrinterGroupJobTransfer getPrinterGroupJobTransfer(PrinterGroupJob printerGroupJob) {
        PrinterGroupJobTransfer printerGroupJobTransfer = get(printerGroupJob);
        
        if(printerGroupJobTransfer == null) {
            PrinterGroupJobDetail printerGroupJobDetail = printerGroupJob.getLastDetail();
            String printerGroupJobName = printerGroupJobDetail.getPrinterGroupJobName();
            PrinterGroupTransfer printerGroupTransfer = printerControl.getPrinterGroupTransfer(userVisit, printerGroupJobDetail.getPrinterGroup());
            DocumentTransfer documentTransfer = documentControl.getDocumentTransfer(userVisit, printerGroupJobDetail.getDocument());
            Integer copies = printerGroupJobDetail.getCopies();
            Integer priority = printerGroupJobDetail.getPriority();
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(printerGroupJob.getPrimaryKey());
            WorkflowEntityStatusTransfer printerGroupJobStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    PrinterGroupJobStatusConstants.Workflow_PRINTER_GROUP_JOB_STATUS, entityInstance);
            
            printerGroupJobTransfer = new PrinterGroupJobTransfer(printerGroupJobName, printerGroupTransfer, documentTransfer, copies, priority,
                    printerGroupJobStatusTransfer);
            put(printerGroupJob, printerGroupJobTransfer);
        }
        
        return printerGroupJobTransfer;
    }
    
}
