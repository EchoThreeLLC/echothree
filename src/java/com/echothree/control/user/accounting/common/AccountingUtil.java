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

package com.echothree.control.user.accounting.common;

import com.echothree.control.user.accounting.server.AccountingLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.NamingException;

public class AccountingUtil {
    
    private static AccountingLocal cachedLocal = null;
    private static AccountingRemote cachedRemote = null;

    @SuppressWarnings("BanJNDI")
    public static AccountingLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            var ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (AccountingLocal)ctx.lookup("ejb:echothree/echothree-server/AccountingBean!com.echothree.control.user.accounting.server.AccountingLocal");
        }
        
        return cachedLocal;
    }

    @SuppressWarnings("BanJNDI")
    public static AccountingRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            var ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (AccountingRemote)ctx.lookup("ejb:echothree/echothree-server/AccountingBean!com.echothree.control.user.accounting.common.AccountingRemote");
        }
        
        return cachedRemote;
    }
    
}
