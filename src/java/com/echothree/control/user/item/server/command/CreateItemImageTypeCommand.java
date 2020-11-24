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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.CreateItemImageTypeForm;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateItemImageTypeCommand
        extends BaseSimpleCommand<CreateItemImageTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemImageType.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemImageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PreferredMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Quality", FieldType.UNSIGNED_INTEGER, false, null, 100L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateItemImageTypeCommand */
    public CreateItemImageTypeCommand(UserVisitPK userVisitPK, CreateItemImageTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        String itemImageTypeName = form.getItemImageTypeName();
        ItemImageType itemImageType = itemControl.getItemImageTypeByName(itemImageTypeName);

        if(itemImageType == null) {
            var coreControl = getCoreControl();
            String preferredMimeTypeName = form.getPreferredMimeTypeName();
            MimeType preferredMimeType = preferredMimeTypeName == null ? null : coreControl.getMimeTypeByName(preferredMimeTypeName);

            if(preferredMimeTypeName == null || preferredMimeType != null) {
                var partyPK = getPartyPK();
                String strQuality = form.getQuality();
                Integer quality = strQuality == null ? null : Integer.valueOf(strQuality);
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();

                itemImageType = itemControl.createItemImageType(itemImageTypeName, preferredMimeType, quality, isDefault, sortOrder, getPartyPK());

                if(description != null) {
                    itemControl.createItemImageTypeDescription(itemImageType, getPreferredLanguage(), description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPreferredMimeTypeName.name(), preferredMimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemImageTypeName.name(), itemImageTypeName);
        }
        
        return null;
    }
    
}
