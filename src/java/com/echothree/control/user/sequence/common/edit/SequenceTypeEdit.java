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

package com.echothree.control.user.sequence.common.edit;

import com.echothree.control.user.sequence.common.spec.SequenceChecksumTypeSpec;
import com.echothree.control.user.sequence.common.spec.SequenceEncoderTypeSpec;
import com.echothree.control.user.sequence.common.spec.SequenceTypeSpec;

public interface SequenceTypeEdit
        extends SequenceTypeSpec, SequenceEncoderTypeSpec, SequenceChecksumTypeSpec, SequenceTypeDescriptionEdit {
    
    String getPrefix();
    void setPrefix(String prefix);
    
    String getSuffix();
    void setSuffix(String suffix);
    
    String getChunkSize();
    void setChunkSize(String chunkSize);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
