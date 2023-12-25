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

package com.echothree.util.common.form;

import com.echothree.util.common.transfer.Limit;
import java.util.Map;
import java.util.Set;

public interface BaseForm {
    
    void set(String key, Object value);
    void set(Map<String, Object> map);
    Object get(String key);
    Map<String, Object> get();
    
    void setFormName(String key);
    String getFormName();
    
    void setPreferredClobMimeTypeName(String preferredClobMimeTypeName);
    String getPreferredClobMimeTypeName();
    
    void setOptions(Set<String> options);
    Set<String> getOptions();
    
    void setTransferProperties(TransferProperties transferProperties);
    TransferProperties getTransferProperties();
    
    void setLimits(Map<String, Limit> limits);
    Map<String, Limit> getLimits();

    void reset();

    @Override
    String toString();
    
}
