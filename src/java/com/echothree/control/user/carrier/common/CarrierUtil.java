// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.carrier.common;

import com.echothree.control.user.carrier.server.CarrierLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.NamingException;

public class CarrierUtil {
    
    private static CarrierLocal cachedLocal = null;
    private static CarrierRemote cachedRemote = null;

    @SuppressWarnings("BanJNDI")
    public static CarrierLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            var ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (CarrierLocal)ctx.lookup("ejb:echothree/echothree-server/CarrierBean!com.echothree.control.user.carrier.server.CarrierLocal");
        }
        
        return cachedLocal;
    }

    @SuppressWarnings("BanJNDI")
    public static CarrierRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            var ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (CarrierRemote)ctx.lookup("ejb:echothree/echothree-server/CarrierBean!com.echothree.control.user.carrier.common.CarrierRemote");
        }
        
        return cachedRemote;
    }
    
}
