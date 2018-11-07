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

package com.echothree.util.server.persistence.valuecache;

import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.BaseValue;

public class NullValueCacheImpl
        implements ValueCache {

    @Override
    public void put(BaseValue baseValue) {
        // Nothing.
    }

    @Override
    public BaseValue get(BasePK basePK) {
        return null;
    }

    @Override
    public void remove(BasePK basePK) {
        // Nothing.
    }

}
