// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.filter.common.form;

import com.echothree.util.common.form.BaseForm;

public interface CreateFilterStepDescriptionForm
        extends BaseForm {
    
    String getFilterKindName();
    void setFilterKindName(String filterKindName);
    
    String getFilterTypeName();
    void setFilterTypeName(String filterTypeName);
    
    String getFilterName();
    void setFilterName(String filterName);
    
    String getFilterStepName();
    void setFilterStepName(String filterStepName);
    
    String getLanguageIsoName();
    void setLanguageIsoName(String languageIsoName);
    
    String getDescription();
    void setDescription(String description);
    
}
