// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.BirthdayFormatEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditBirthdayFormatForm;
import com.echothree.control.user.party.common.result.EditBirthdayFormatResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.BirthdayFormatSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.BirthdayFormat;
import com.echothree.model.data.party.server.entity.BirthdayFormatDescription;
import com.echothree.model.data.party.server.entity.BirthdayFormatDetail;
import com.echothree.model.data.party.server.value.BirthdayFormatDescriptionValue;
import com.echothree.model.data.party.server.value.BirthdayFormatDetailValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditBirthdayFormatCommand
        extends BaseAbstractEditCommand<BirthdayFormatSpec, BirthdayFormatEdit, EditBirthdayFormatResult, BirthdayFormat, BirthdayFormat> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.BirthdayFormat.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BirthdayFormatName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BirthdayFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditBirthdayFormatCommand */
    public EditBirthdayFormatCommand(UserVisitPK userVisitPK, EditBirthdayFormatForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditBirthdayFormatResult getResult() {
        return PartyResultFactory.getEditBirthdayFormatResult();
    }

    @Override
    public BirthdayFormatEdit getEdit() {
        return PartyEditFactory.getBirthdayFormatEdit();
    }

    @Override
    public BirthdayFormat getEntity(EditBirthdayFormatResult result) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        BirthdayFormat birthdayFormat = null;
        String birthdayFormatName = spec.getBirthdayFormatName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            birthdayFormat = partyControl.getBirthdayFormatByName(birthdayFormatName);
        } else { // EditMode.UPDATE
            birthdayFormat = partyControl.getBirthdayFormatByNameForUpdate(birthdayFormatName);
        }

        if(birthdayFormat != null) {
            result.setBirthdayFormat(partyControl.getBirthdayFormatTransfer(getUserVisit(), birthdayFormat));
        } else {
            addExecutionError(ExecutionErrors.UnknownBirthdayFormatName.name(), birthdayFormatName);
        }

        return birthdayFormat;
    }

    @Override
    public BirthdayFormat getLockEntity(BirthdayFormat birthdayFormat) {
        return birthdayFormat;
    }

    @Override
    public void fillInResult(EditBirthdayFormatResult result, BirthdayFormat birthdayFormat) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

        result.setBirthdayFormat(partyControl.getBirthdayFormatTransfer(getUserVisit(), birthdayFormat));
    }

    @Override
    public void doLock(BirthdayFormatEdit edit, BirthdayFormat birthdayFormat) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        BirthdayFormatDescription birthdayFormatDescription = partyControl.getBirthdayFormatDescription(birthdayFormat, getPreferredLanguage());
        BirthdayFormatDetail birthdayFormatDetail = birthdayFormat.getLastDetail();

        edit.setBirthdayFormatName(birthdayFormatDetail.getBirthdayFormatName());
        edit.setIsDefault(birthdayFormatDetail.getIsDefault().toString());
        edit.setSortOrder(birthdayFormatDetail.getSortOrder().toString());

        if(birthdayFormatDescription != null) {
            edit.setDescription(birthdayFormatDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(BirthdayFormat birthdayFormat) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String birthdayFormatName = edit.getBirthdayFormatName();
        BirthdayFormat duplicateBirthdayFormat = partyControl.getBirthdayFormatByName(birthdayFormatName);

        if(duplicateBirthdayFormat != null && !birthdayFormat.equals(duplicateBirthdayFormat)) {
            addExecutionError(ExecutionErrors.DuplicateBirthdayFormatName.name(), birthdayFormatName);
        }
    }

    @Override
    public void doUpdate(BirthdayFormat birthdayFormat) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        PartyPK partyPK = getPartyPK();
        BirthdayFormatDetailValue birthdayFormatDetailValue = partyControl.getBirthdayFormatDetailValueForUpdate(birthdayFormat);
        BirthdayFormatDescription birthdayFormatDescription = partyControl.getBirthdayFormatDescriptionForUpdate(birthdayFormat, getPreferredLanguage());
        String description = edit.getDescription();

        birthdayFormatDetailValue.setBirthdayFormatName(edit.getBirthdayFormatName());
        birthdayFormatDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        birthdayFormatDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        partyControl.updateBirthdayFormatFromValue(birthdayFormatDetailValue, partyPK);

        if(birthdayFormatDescription == null && description != null) {
            partyControl.createBirthdayFormatDescription(birthdayFormat, getPreferredLanguage(), description, partyPK);
        } else if(birthdayFormatDescription != null && description == null) {
            partyControl.deleteBirthdayFormatDescription(birthdayFormatDescription, partyPK);
        } else if(birthdayFormatDescription != null && description != null) {
            BirthdayFormatDescriptionValue birthdayFormatDescriptionValue = partyControl.getBirthdayFormatDescriptionValue(birthdayFormatDescription);

            birthdayFormatDescriptionValue.setDescription(description);
            partyControl.updateBirthdayFormatDescriptionFromValue(birthdayFormatDescriptionValue, partyPK);
        }
    }

}
