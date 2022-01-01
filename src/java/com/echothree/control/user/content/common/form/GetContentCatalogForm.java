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

package com.echothree.control.user.content.common.form;

import com.echothree.control.user.associate.common.spec.AssociatePartyContactMechanismSpec;
import com.echothree.control.user.content.common.spec.ContentCatalogSpec;
import com.echothree.control.user.content.common.spec.ContentWebAddressSpec;

public interface GetContentCatalogForm
        extends ContentWebAddressSpec, ContentCatalogSpec, AssociatePartyContactMechanismSpec {
    
    // Nothing additional beyond ContentWebAddressSpec, ContentCatalogSpec, AssociatePartyContactMechanismSpec
    
}
