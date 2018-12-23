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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.common.edit.PrinterEditFactory;
import com.echothree.control.user.printer.common.edit.PrinterGroupDescriptionEdit;
import com.echothree.control.user.printer.common.form.EditPrinterGroupDescriptionForm;
import com.echothree.control.user.printer.common.result.EditPrinterGroupDescriptionResult;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.control.user.printer.common.spec.PrinterGroupDescriptionSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.entity.PrinterGroupDescription;
import com.echothree.model.data.printer.server.value.PrinterGroupDescriptionValue;
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

public class EditPrinterGroupDescriptionCommand
        extends BaseAbstractEditCommand<PrinterGroupDescriptionSpec, PrinterGroupDescriptionEdit, EditPrinterGroupDescriptionResult, PrinterGroupDescription, PrinterGroup> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PrinterGroup.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditPrinterGroupDescriptionCommand */
    public EditPrinterGroupDescriptionCommand(UserVisitPK userVisitPK, EditPrinterGroupDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPrinterGroupDescriptionResult getResult() {
        return PrinterResultFactory.getEditPrinterGroupDescriptionResult();
    }

    @Override
    public PrinterGroupDescriptionEdit getEdit() {
        return PrinterEditFactory.getPrinterGroupDescriptionEdit();
    }

    @Override
    public PrinterGroupDescription getEntity(EditPrinterGroupDescriptionResult result) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        PrinterGroupDescription printerGroupDescription = null;
        String printerGroupName = spec.getPrinterGroupName();
        PrinterGroup printerGroup = printerControl.getPrinterGroupByName(printerGroupName);

        if(printerGroup != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    printerGroupDescription = printerControl.getPrinterGroupDescription(printerGroup, language);
                } else { // EditMode.UPDATE
                    printerGroupDescription = printerControl.getPrinterGroupDescriptionForUpdate(printerGroup, language);
                }

                if(printerGroupDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownPrinterGroupDescription.name(), printerGroupName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPrinterGroupName.name(), printerGroupName);
        }

        return printerGroupDescription;
    }

    @Override
    public PrinterGroup getLockEntity(PrinterGroupDescription printerGroupDescription) {
        return printerGroupDescription.getPrinterGroup();
    }

    @Override
    public void fillInResult(EditPrinterGroupDescriptionResult result, PrinterGroupDescription printerGroupDescription) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);

        result.setPrinterGroupDescription(printerControl.getPrinterGroupDescriptionTransfer(getUserVisit(), printerGroupDescription));
    }

    @Override
    public void doLock(PrinterGroupDescriptionEdit edit, PrinterGroupDescription printerGroupDescription) {
        edit.setDescription(printerGroupDescription.getDescription());
    }

    @Override
    public void doUpdate(PrinterGroupDescription printerGroupDescription) {
        PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        PrinterGroupDescriptionValue printerGroupDescriptionValue = printerControl.getPrinterGroupDescriptionValue(printerGroupDescription);

        printerGroupDescriptionValue.setDescription(edit.getDescription());

        printerControl.updatePrinterGroupDescriptionFromValue(printerGroupDescriptionValue, getPartyPK());
    }

}
