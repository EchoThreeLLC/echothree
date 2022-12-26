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

package com.echothree.control.user.core.common;

import com.echothree.control.user.core.server.CoreLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CoreUtil {
    
    private static CoreLocal cachedLocal = null;
    private static CoreRemote cachedRemote = null;

    @SuppressWarnings("BanJNDI")
    public static CoreLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (CoreLocal)ctx.lookup("ejb:echothree/echothree-server/CoreBean!com.echothree.control.user.core.server.CoreLocal");
        }
        
        return cachedLocal;
    }

    @SuppressWarnings("BanJNDI")
    public static CoreRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (CoreRemote)ctx.lookup("ejb:echothree/echothree-server/CoreBean!com.echothree.control.user.core.common.CoreRemote");
        }
        
        return cachedRemote;
    }
    
}
