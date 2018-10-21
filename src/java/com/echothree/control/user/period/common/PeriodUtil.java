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

package com.echothree.control.user.period.common;

import com.echothree.control.user.period.remote.PeriodRemote;
import com.echothree.control.user.period.server.PeriodLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PeriodUtil {
    
    private static PeriodLocal cachedLocal = null;
    private static PeriodRemote cachedRemote = null;
    
    public static PeriodLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (PeriodLocal)ctx.lookup("ejb:echothree/echothree-server/PeriodBean!com.echothree.control.user.period.server.PeriodLocal");
        }
        
        return cachedLocal;
    }
    
    public static PeriodRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (PeriodRemote)ctx.lookup("ejb:echothree/echothree-server/PeriodBean!com.echothree.control.user.period.remote.PeriodRemote");
        }
        
        return cachedRemote;
    }
    
}
