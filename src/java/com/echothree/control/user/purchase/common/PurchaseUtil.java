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

package com.echothree.control.user.purchase.common;

import com.echothree.control.user.purchase.remote.PurchaseRemote;
import com.echothree.control.user.purchase.server.PurchaseLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PurchaseUtil {
    
    private static PurchaseLocal cachedLocal = null;
    private static PurchaseRemote cachedRemote = null;
    
    public static PurchaseLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (PurchaseLocal)ctx.lookup("ejb:echothree/echothree-server/PurchaseBean!com.echothree.control.user.purchase.server.PurchaseLocal");
        }
        
        return cachedLocal;
    }
    
    public static PurchaseRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (PurchaseRemote)ctx.lookup("ejb:echothree/echothree-server/PurchaseBean!com.echothree.control.user.purchase.remote.PurchaseRemote");
        }
        
        return cachedRemote;
    }
    
}
