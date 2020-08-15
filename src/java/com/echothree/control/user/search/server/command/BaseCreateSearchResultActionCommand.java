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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.BaseCreateSearchResultActionForm;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import java.util.List;

    public abstract class BaseCreateSearchResultActionCommand<F extends BaseCreateSearchResultActionForm>
        extends BaseSimpleCommand<F> {
    
    /** Creates a new instance of BaseCreateItemSearchResultActionCommand */
    protected BaseCreateSearchResultActionCommand(UserVisitPK userVisitPK, F form, CommandSecurityDefinition COMMAND_SECURITY_DEFINITION,
            List<FieldDefinition> FORM_FIELD_DEFINITIONS) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    protected BaseResult execute(String searchKindName, BaseEntity baseEntity) {
        SearchLogic.getInstance().createSearchResultAction(this, getUserVisit(), searchKindName, form.getSearchTypeName(), form.getSearchResultActionTypeName(),
                baseEntity, getPartyPK());
        
        return null;
    }
    
    @Override
    protected abstract BaseResult execute();
    
}
