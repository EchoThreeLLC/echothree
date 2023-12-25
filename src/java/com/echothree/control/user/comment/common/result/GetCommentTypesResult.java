// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.comment.common.result;

import com.echothree.model.control.comment.common.transfer.CommentTypeTransfer;
import com.echothree.model.control.core.common.transfer.ComponentVendorTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetCommentTypesResult
        extends BaseResult {
    
    ComponentVendorTransfer getComponentVendor();
    void setComponentVendor(ComponentVendorTransfer componentVendor);
    
    EntityTypeTransfer getEntityType();
    void setEntityType(EntityTypeTransfer entityType);
    
    List<CommentTypeTransfer> getCommentTypes();
    void setCommentTypes(List<CommentTypeTransfer> commentTypes);
    
}
