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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.common.edit.PrinterEdit;
import com.echothree.control.user.printer.common.edit.PrinterEditFactory;
import com.echothree.control.user.printer.common.form.EditPrinterForm;
import com.echothree.control.user.printer.common.result.EditPrinterResult;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.control.user.printer.common.spec.PrinterSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.printer.server.entity.Printer;
import com.echothree.model.data.printer.server.entity.PrinterDescription;
import com.echothree.model.data.printer.server.entity.PrinterDetail;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.value.PrinterDescriptionValue;
import com.echothree.model.data.printer.server.value.PrinterDetailValue;
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

public class EditPrinterCommand
        extends BaseAbstractEditCommand<PrinterSpec, PrinterEdit, EditPrinterResult, Printer, Printer> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Printer.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditPrinterCommand */
    public EditPrinterCommand(UserVisitPK userVisitPK, EditPrinterForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPrinterResult getResult() {
        return PrinterResultFactory.getEditPrinterResult();
    }

    @Override
    public PrinterEdit getEdit() {
        return PrinterEditFactory.getPrinterEdit();
    }

    @Override
    public Printer getEntity(EditPrinterResult result) {
        var printerControl = Session.getModelController(PrinterControl.class);
        Printer printer = null;
        String printerName = spec.getPrinterName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            printer = printerControl.getPrinterByName(printerName);
        } else { // EditMode.UPDATE
            printer = printerControl.getPrinterByNameForUpdate(printerName);
        }

        if(printer != null) {
            result.setPrinter(printerControl.getPrinterTransfer(getUserVisit(), printer));
        } else {
            addExecutionError(ExecutionErrors.UnknownPrinterName.name(), printerName);
        }

        return printer;
    }

    @Override
    public Printer getLockEntity(Printer printer) {
        return printer;
    }

    @Override
    public void fillInResult(EditPrinterResult result, Printer printer) {
        var printerControl = Session.getModelController(PrinterControl.class);

        result.setPrinter(printerControl.getPrinterTransfer(getUserVisit(), printer));
    }

    @Override
    public void doLock(PrinterEdit edit, Printer printer) {
        var printerControl = Session.getModelController(PrinterControl.class);
        PrinterDescription printerDescription = printerControl.getPrinterDescription(printer, getPreferredLanguage());
        PrinterDetail printerDetail = printer.getLastDetail();

        edit.setPrinterName(printerDetail.getPrinterName());
        edit.setPrinterGroupName(printerDetail.getPrinterGroup().getLastDetail().getPrinterGroupName());
        edit.setPriority(printerDetail.getPriority().toString());

        if(printerDescription != null) {
            edit.setDescription(printerDescription.getDescription());
        }
    }

    PrinterGroup printerGroup;

    @Override
    public void canUpdate(Printer printer) {
        var printerControl = Session.getModelController(PrinterControl.class);
        String printerName = edit.getPrinterName();
        Printer duplicatePrinter = printerControl.getPrinterByName(printerName);

        if(duplicatePrinter != null && !printer.equals(duplicatePrinter)) {
            addExecutionError(ExecutionErrors.DuplicatePrinterName.name(), printerName);
        } else {
            String printerGroupName = edit.getPrinterGroupName();

            printerGroup = printerControl.getPrinterGroupByName(printerGroupName);

            if(printerGroup == null) {
                addExecutionError(ExecutionErrors.DuplicatePrinterGroupName.name(), printerGroupName);
            }
        }
    }

    @Override
    public void doUpdate(Printer printer) {
        var printerControl = Session.getModelController(PrinterControl.class);
        var partyPK = getPartyPK();
        PrinterDetailValue printerDetailValue = printerControl.getPrinterDetailValueForUpdate(printer);
        PrinterDescription printerDescription = printerControl.getPrinterDescriptionForUpdate(printer, getPreferredLanguage());
        String description = edit.getDescription();

        printerDetailValue.setPrinterName(edit.getPrinterName());
        printerDetailValue.setPrinterGroupPK(printerGroup.getPrimaryKey());
        printerDetailValue.setPriority(Integer.valueOf(edit.getPriority()));

        printerControl.updatePrinterFromValue(printerDetailValue, partyPK);

        if(printerDescription == null && description != null) {
            printerControl.createPrinterDescription(printer, getPreferredLanguage(), description, partyPK);
        } else if(printerDescription != null && description == null) {
            printerControl.deletePrinterDescription(printerDescription, partyPK);
        } else if(printerDescription != null && description != null) {
            PrinterDescriptionValue printerDescriptionValue = printerControl.getPrinterDescriptionValue(printerDescription);

            printerDescriptionValue.setDescription(description);
            printerControl.updatePrinterDescriptionFromValue(printerDescriptionValue, partyPK);
        }
    }

}
