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

package com.echothree.util.server.persistence;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import org.infinispan.Cache;

@Singleton
public class SecurityCacheBean {

    @Resource(lookup = "java:app/infinispan/EchoThree/Security")
    Cache<String, Object> cache;

    public Cache<String, Object> getCache() {
        return cache;
    }

}
