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

package com.echothree.control.user.contact.common;

import com.echothree.control.user.contact.common.ContactRemote;
import com.echothree.control.user.contact.server.ContactLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContactUtil {
    
    private static ContactLocal cachedLocal = null;
    private static ContactRemote cachedRemote = null;
    
    public static ContactLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (ContactLocal)ctx.lookup("ejb:echothree/echothree-server/ContactBean!com.echothree.control.user.contact.server.ContactLocal");
        }
        
        return cachedLocal;
    }
    
    public static ContactRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (ContactRemote)ctx.lookup("ejb:echothree/echothree-server/ContactBean!com.echothree.control.user.contact.common.ContactRemote");
        }
        
        return cachedRemote;
    }
    
}
