// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.authentication.common;

import com.echothree.control.user.authentication.common.AuthenticationRemote;
import com.echothree.control.user.authentication.server.AuthenticationLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AuthenticationUtil {
    
    private static AuthenticationLocal cachedLocal = null;
    private static AuthenticationRemote cachedRemote = null;
    
    public static AuthenticationLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (AuthenticationLocal)ctx.lookup("ejb:echothree/echothree-server/AuthenticationBean!com.echothree.control.user.authentication.server.AuthenticationLocal");
        }
        
        return cachedLocal;
    }

    public static AuthenticationRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedRemote = (AuthenticationRemote)ctx.lookup("ejb:echothree/echothree-server/AuthenticationBean!com.echothree.control.user.authentication.common.AuthenticationRemote");
        }
        
        return cachedRemote;
    }
    
}
