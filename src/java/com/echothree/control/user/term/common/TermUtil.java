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

package com.echothree.control.user.term.common;

import com.echothree.control.user.term.common.TermRemote;
import com.echothree.control.user.term.server.TermLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TermUtil {
    
    private static TermLocal cachedLocal = null;
    private static TermRemote cachedRemote = null;
    
    public static TermLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (TermLocal)ctx.lookup("ejb:echothree/echothree-server/TermBean!com.echothree.control.user.term.server.TermLocal");
        }
        
        return cachedLocal;
    }
    
    public static TermRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (TermRemote)ctx.lookup("ejb:echothree/echothree-server/TermBean!com.echothree.control.user.term.common.TermRemote");
        }
        
        return cachedRemote;
    }
    
}
