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

package com.echothree.model.control.tag.server.control;

import com.echothree.model.control.tag.server.transfer.TagTransferCaches;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.control.BaseModelControl;

public abstract class BaseTagControl
        extends BaseModelControl {

    /** Creates a new instance of BaseTagControl */
    protected BaseTagControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Tag Transfer Caches
    // --------------------------------------------------------------------------------

    private TagTransferCaches tagTransferCaches;

    public TagTransferCaches getTagTransferCaches(UserVisit userVisit) {
        if(tagTransferCaches == null) {
            tagTransferCaches = new TagTransferCaches(userVisit);
        }

        return tagTransferCaches;
    }

}
