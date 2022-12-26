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

package com.echothree.control.user.workflow.common;

import com.echothree.control.user.workflow.server.WorkflowLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class WorkflowUtil {
    
    private static WorkflowLocal cachedLocal = null;
    private static WorkflowRemote cachedRemote = null;

    @SuppressWarnings("BanJNDI")
    public static WorkflowLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (WorkflowLocal)ctx.lookup("ejb:echothree/echothree-server/WorkflowBean!com.echothree.control.user.workflow.server.WorkflowLocal");
        }
        
        return cachedLocal;
    }

    @SuppressWarnings("BanJNDI")
    public static WorkflowRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (WorkflowRemote)ctx.lookup("ejb:echothree/echothree-server/WorkflowBean!com.echothree.control.user.workflow.common.WorkflowRemote");
        }
        
        return cachedRemote;
    }
    
}
