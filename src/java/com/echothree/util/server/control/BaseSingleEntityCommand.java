// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.util.server.control;

import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.server.persistence.BaseEntity;
import java.util.List;

public abstract class BaseSingleEntityCommand<BE extends BaseEntity, F extends BaseForm>
        extends BaseSimpleCommand<F>
        implements GraphQlSecurityCommand {
    
    protected BaseSingleEntityCommand(UserVisitPK userVisitPK, F form, CommandSecurityDefinition commandSecurityDefinition,
            List<FieldDefinition> formFieldDefinitions, boolean allowLimits) {
        super(userVisitPK, form, commandSecurityDefinition, formFieldDefinitions, allowLimits);
    }
    
    protected abstract BE getEntity();
    protected abstract BaseResult getTransfer(BE entity);

    public BE runForGraphQl() {
        BE entity = null;
        SecurityResult securityResult = security();

        if(securityResult == null || !securityResult.getHasMessages()) {
            ValidationResult validationResult = validate();

            if((validationResult == null || !validationResult.getHasErrors())) {
                entity = getEntity();
            }
        }
        
        return entity;
    }

    @Override
    public SecurityResult security() {
        return super.security();
    }

    @Override
    protected BaseResult execute() {
        BE entity = getEntity();
        
        return getTransfer(entity);
    }
    
}
