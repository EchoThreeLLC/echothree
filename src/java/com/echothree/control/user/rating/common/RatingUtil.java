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

package com.echothree.control.user.rating.common;

import com.echothree.control.user.rating.remote.RatingRemote;
import com.echothree.control.user.rating.server.RatingLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RatingUtil {
    
    private static RatingLocal cachedLocal = null;
    private static RatingRemote cachedRemote = null;
    
    public static RatingLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (RatingLocal)ctx.lookup("ejb:echothree/echothree-server/RatingBean!com.echothree.control.user.rating.server.RatingLocal");
        }
        
        return cachedLocal;
    }
    
    public static RatingRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (RatingRemote)ctx.lookup("ejb:echothree/echothree-server/RatingBean!com.echothree.control.user.rating.remote.RatingRemote");
        }
        
        return cachedRemote;
    }
    
}
