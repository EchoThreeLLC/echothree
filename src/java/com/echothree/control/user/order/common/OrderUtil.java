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

package com.echothree.control.user.order.common;

import com.echothree.control.user.order.server.OrderLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class OrderUtil {
    
    private static OrderLocal cachedLocal = null;
    private static OrderRemote cachedRemote = null;

    @SuppressWarnings("BanJNDI")
    public static OrderLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (OrderLocal)ctx.lookup("ejb:echothree/echothree-server/OrderBean!com.echothree.control.user.order.server.OrderLocal");
        }
        
        return cachedLocal;
    }

    @SuppressWarnings("BanJNDI")
    public static OrderRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (OrderRemote)ctx.lookup("ejb:echothree/echothree-server/OrderBean!com.echothree.control.user.order.common.OrderRemote");
        }
        
        return cachedRemote;
    }
    
}
