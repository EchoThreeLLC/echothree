// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.tag.common;

import com.echothree.control.user.tag.common.TagRemote;
import com.echothree.control.user.tag.server.TagLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TagUtil {
    
    private static TagLocal cachedLocal = null;
    private static TagRemote cachedRemote = null;
    
    public static TagLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (TagLocal)ctx.lookup("ejb:echothree/echothree-server/TagBean!com.echothree.control.user.tag.server.TagLocal");
        }
        
        return cachedLocal;
    }
    
    public static TagRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (TagRemote)ctx.lookup("ejb:echothree/echothree-server/TagBean!com.echothree.control.user.tag.common.TagRemote");
        }
        
        return cachedRemote;
    }
    
}
