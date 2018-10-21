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

package com.echothree.control.user.employee.common;

import com.echothree.control.user.employee.remote.EmployeeRemote;
import com.echothree.control.user.employee.server.EmployeeLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EmployeeUtil {
    
    private static EmployeeLocal cachedLocal = null;
    private static EmployeeRemote cachedRemote = null;
    
    public static EmployeeLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (EmployeeLocal)ctx.lookup("ejb:echothree/echothree-server/EmployeeBean!com.echothree.control.user.employee.server.EmployeeLocal");
        }
        
        return cachedLocal;
    }
    
    public static EmployeeRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (EmployeeRemote)ctx.lookup("ejb:echothree/echothree-server/EmployeeBean!com.echothree.control.user.employee.remote.EmployeeRemote");
        }
        
        return cachedRemote;
    }
    
}
