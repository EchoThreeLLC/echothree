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

package com.echothree.control.user.item.common;

import com.echothree.control.user.item.common.ItemRemote;
import com.echothree.control.user.item.server.ItemLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ItemUtil {
    
    private static ItemLocal cachedLocal = null;
    private static ItemRemote cachedRemote = null;
    
    public static ItemLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (ItemLocal)ctx.lookup("ejb:echothree/echothree-server/ItemBean!com.echothree.control.user.item.server.ItemLocal");
        }
        
        return cachedLocal;
    }
    
    public static ItemRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (ItemRemote)ctx.lookup("ejb:echothree/echothree-server/ItemBean!com.echothree.control.user.item.common.ItemRemote");
        }
        
        return cachedRemote;
    }
    
}
