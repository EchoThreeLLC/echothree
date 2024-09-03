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

package com.echothree.control.user.customer.common;

import com.echothree.control.user.customer.server.CustomerLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CustomerUtil {
    
    private static CustomerLocal cachedLocal = null;
    private static CustomerRemote cachedRemote = null;

    @SuppressWarnings("BanJNDI")
    public static CustomerLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            var ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (CustomerLocal)ctx.lookup("ejb:echothree/echothree-server/CustomerBean!com.echothree.control.user.customer.server.CustomerLocal");
        }
        
        return cachedLocal;
    }

    @SuppressWarnings("BanJNDI")
    public static CustomerRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            var ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (CustomerRemote)ctx.lookup("ejb:echothree/echothree-server/CustomerBean!com.echothree.control.user.customer.common.CustomerRemote");
        }
        
        return cachedRemote;
    }
    
}
