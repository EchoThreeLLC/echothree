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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.remote.form.GetCacheEntryForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.GetCacheEntryResult;
import com.echothree.model.control.core.remote.transfer.CacheEntryTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetCacheEntryCommand
        extends BaseSimpleCommand<GetCacheEntryForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CacheEntryKey", FieldType.STRING, true, 1L, 200L)
                ));
    }
    
    /** Creates a new instance of GetCacheEntryCommand */
    public GetCacheEntryCommand(UserVisitPK userVisitPK, GetCacheEntryForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        GetCacheEntryResult result = CoreResultFactory.getGetCacheEntryResult();
        String cacheEntryKey = form.getCacheEntryKey();
        CacheEntryTransfer cacheEntryTransfer = coreControl.getCacheEntryTransferByCacheEntryKey(getUserVisit(), cacheEntryKey);

        if(cacheEntryTransfer != null) {
            result.setCacheEntry(cacheEntryTransfer);
        } else {
            addExecutionError(ExecutionErrors.UnknownCacheEntryKey.name(), cacheEntryKey);
        }
        
        return result;
    }
    
}
