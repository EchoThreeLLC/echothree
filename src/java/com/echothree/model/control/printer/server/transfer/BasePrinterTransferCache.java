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

package com.echothree.model.control.printer.server.transfer;

import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.transfer.BaseTransferCache;

public abstract class BasePrinterTransferCache<K extends BaseEntity, V extends BaseTransfer>
        extends BaseTransferCache<K, V> {
    
    PrinterControl printerControl;
    
    /** Creates a new instance of BasePrinterTransferCache */
    protected BasePrinterTransferCache(UserVisit userVisit, PrinterControl printerControl) {
        super(userVisit);
        
        this.printerControl = printerControl;
    }
    
}
