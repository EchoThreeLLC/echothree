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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetAppearanceTextDecorationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetAppearanceTextDecorationResult;
import com.echothree.model.control.core.server.logic.AppearanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.AppearanceTextDecoration;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetAppearanceTextDecorationCommand
        extends BaseSimpleCommand<GetAppearanceTextDecorationForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Appearance.name(), SecurityRoles.AppearanceTextDecoration.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetAppearanceTextDecorationCommand */
    public GetAppearanceTextDecorationCommand(UserVisitPK userVisitPK, GetAppearanceTextDecorationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GetAppearanceTextDecorationResult result = CoreResultFactory.getGetAppearanceTextDecorationResult();
        String appearanceName = form.getAppearanceName();
        Appearance appearance = AppearanceLogic.getInstance().getAppearanceByName(this, appearanceName);
        
        if(!hasExecutionErrors()) {
            String textDecorationName = form.getTextDecorationName();
            TextDecoration textDecoration = AppearanceLogic.getInstance().getTextDecorationByName(this, textDecorationName);
            
            if(!hasExecutionErrors()) {
                var coreControl = getCoreControl();
                AppearanceTextDecoration appearanceTextDecoration = coreControl.getAppearanceTextDecoration(appearance, textDecoration);

                if(appearanceTextDecoration != null) {
                    result.setAppearanceTextDecoration(coreControl.getAppearanceTextDecorationTransfer(getUserVisit(), appearanceTextDecoration));
                } else {
                    addExecutionError(ExecutionErrors.UnknownAppearanceTextDecoration.name(), appearanceName, textDecorationName);
                }
            }
        }

        return result;
    }
    
}
