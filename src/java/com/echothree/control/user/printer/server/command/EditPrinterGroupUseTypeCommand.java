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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.remote.edit.PrinterEditFactory;
import com.echothree.control.user.printer.remote.edit.PrinterGroupUseTypeEdit;
import com.echothree.control.user.printer.remote.form.EditPrinterGroupUseTypeForm;
import com.echothree.control.user.printer.remote.result.EditPrinterGroupUseTypeResult;
import com.echothree.control.user.printer.remote.result.PrinterResultFactory;
import com.echothree.control.user.printer.remote.spec.PrinterGroupUseTypeSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseType;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseTypeDescription;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseTypeDetail;
import com.echothree.model.data.printer.server.value.PrinterGroupUseTypeDescriptionValue;
import com.echothree.model.data.printer.server.value.PrinterGroupUseTypeDetailValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPrinterGroupUseTypeCommand
        extends BaseAbstractEditCommand<PrinterGroupUseTypeSpec, PrinterGroupUseTypeEdit, EditPrinterGroupUseTypeResult, PrinterGroupUseType, PrinterGroupUseType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PrinterGroupUseType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditPrinterGroupUseTypeCommand */
    public EditPrinterGroupUseTypeCommand(UserVisitPK userVisitPK, EditPrinterGroupUseTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPrinterGroupUseTypeResult getResult() {
        return PrinterResultFactory.getEditPrinterGroupUseTypeResult();
    }

    @Override
    public PrinterGroupUseTypeEdit getEdit() {
        return PrinterEditFactory.getPrinterGroupUseTypeEdit();
    }

    @Override
    public PrinterGroupUseType getEntity(EditPrinterGroupUseTypeResult result) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        PrinterGroupUseType printerGroupUseType = null;
        String printerGroupUseTypeName = spec.getPrinterGroupUseTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(printerGroupUseTypeName);
        } else { // EditMode.UPDATE
            printerGroupUseType = printerControl.getPrinterGroupUseTypeByNameForUpdate(printerGroupUseTypeName);
        }

        if(printerGroupUseType != null) {
            result.setPrinterGroupUseType(printerControl.getPrinterGroupUseTypeTransfer(getUserVisit(), printerGroupUseType));
        } else {
            addExecutionError(ExecutionErrors.UnknownPrinterGroupUseTypeName.name(), printerGroupUseTypeName);
        }

        return printerGroupUseType;
    }

    @Override
    public PrinterGroupUseType getLockEntity(PrinterGroupUseType printerGroupUseType) {
        return printerGroupUseType;
    }

    @Override
    public void fillInResult(EditPrinterGroupUseTypeResult result, PrinterGroupUseType printerGroupUseType) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);

        result.setPrinterGroupUseType(printerControl.getPrinterGroupUseTypeTransfer(getUserVisit(), printerGroupUseType));
    }

    @Override
    public void doLock(PrinterGroupUseTypeEdit edit, PrinterGroupUseType printerGroupUseType) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        PrinterGroupUseTypeDescription printerGroupUseTypeDescription = printerControl.getPrinterGroupUseTypeDescription(printerGroupUseType, getPreferredLanguage());
        PrinterGroupUseTypeDetail printerGroupUseTypeDetail = printerGroupUseType.getLastDetail();

        edit.setPrinterGroupUseTypeName(printerGroupUseTypeDetail.getPrinterGroupUseTypeName());
        edit.setIsDefault(printerGroupUseTypeDetail.getIsDefault().toString());
        edit.setSortOrder(printerGroupUseTypeDetail.getSortOrder().toString());

        if(printerGroupUseTypeDescription != null) {
            edit.setDescription(printerGroupUseTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(PrinterGroupUseType printerGroupUseType) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        String printerGroupUseTypeName = edit.getPrinterGroupUseTypeName();
        PrinterGroupUseType duplicatePrinterGroupUseType = printerControl.getPrinterGroupUseTypeByName(printerGroupUseTypeName);

        if(duplicatePrinterGroupUseType != null && !printerGroupUseType.equals(duplicatePrinterGroupUseType)) {
            addExecutionError(ExecutionErrors.DuplicatePrinterGroupUseTypeName.name(), printerGroupUseTypeName);
        }
    }

    @Override
    public void doUpdate(PrinterGroupUseType printerGroupUseType) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        PartyPK partyPK = getPartyPK();
        PrinterGroupUseTypeDetailValue printerGroupUseTypeDetailValue = printerControl.getPrinterGroupUseTypeDetailValueForUpdate(printerGroupUseType);
        PrinterGroupUseTypeDescription printerGroupUseTypeDescription = printerControl.getPrinterGroupUseTypeDescriptionForUpdate(printerGroupUseType, getPreferredLanguage());
        String description = edit.getDescription();

        printerGroupUseTypeDetailValue.setPrinterGroupUseTypeName(edit.getPrinterGroupUseTypeName());
        printerGroupUseTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        printerGroupUseTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        printerControl.updatePrinterGroupUseTypeFromValue(printerGroupUseTypeDetailValue, partyPK);

        if(printerGroupUseTypeDescription == null && description != null) {
            printerControl.createPrinterGroupUseTypeDescription(printerGroupUseType, getPreferredLanguage(), description, partyPK);
        } else if(printerGroupUseTypeDescription != null && description == null) {
            printerControl.deletePrinterGroupUseTypeDescription(printerGroupUseTypeDescription, partyPK);
        } else if(printerGroupUseTypeDescription != null && description != null) {
            PrinterGroupUseTypeDescriptionValue printerGroupUseTypeDescriptionValue = printerControl.getPrinterGroupUseTypeDescriptionValue(printerGroupUseTypeDescription);

            printerGroupUseTypeDescriptionValue.setDescription(description);
            printerControl.updatePrinterGroupUseTypeDescriptionFromValue(printerGroupUseTypeDescriptionValue, partyPK);
        }
    }

}
