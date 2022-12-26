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

package com.echothree.control.user.core.common.form;

import com.echothree.control.user.core.common.spec.EntityAttributeSpec;
import com.echothree.control.user.core.common.spec.EntityRefSpec;

public interface GetEntityLongRangeChoicesForm
        extends EntityRefSpec, EntityAttributeSpec {
    
    String getDefaultEntityLongRangeChoice();
    void setDefaultEntityLongRangeChoice(String defaultEntityLongRangeChoice);
    
    String getAllowNullChoice();
    void setAllowNullChoice(String allowNullChoice);
    
}
