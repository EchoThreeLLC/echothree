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

package com.echothree.control.user.search.common;

import com.echothree.control.user.search.common.SearchRemote;
import com.echothree.control.user.search.server.SearchLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SearchUtil {
    
    private static SearchLocal cachedLocal = null;
    private static SearchRemote cachedRemote = null;
    
    public static SearchLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (SearchLocal)ctx.lookup("ejb:echothree/echothree-server/SearchBean!com.echothree.control.user.search.server.SearchLocal");
        }
        
        return cachedLocal;
    }
    
    public static SearchRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (SearchRemote)ctx.lookup("ejb:echothree/echothree-server/SearchBean!com.echothree.control.user.search.common.SearchRemote");
        }
        
        return cachedRemote;
    }
    
}
