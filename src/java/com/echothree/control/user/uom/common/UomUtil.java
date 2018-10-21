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

package com.echothree.control.user.uom.common;

import com.echothree.control.user.uom.remote.UomRemote;
import com.echothree.control.user.uom.server.UomLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class UomUtil {
    
    private static UomLocal cachedLocal = null;
    private static UomRemote cachedRemote = null;
    
    public static UomLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (UomLocal)ctx.lookup("ejb:echothree/echothree-server/UomBean!com.echothree.control.user.uom.server.UomLocal");
        }
        
        return cachedLocal;
    }
    
    public static UomRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (UomRemote)ctx.lookup("ejb:echothree/echothree-server/UomBean!com.echothree.control.user.uom.remote.UomRemote");
        }
        
        return cachedRemote;
    }
    
}
