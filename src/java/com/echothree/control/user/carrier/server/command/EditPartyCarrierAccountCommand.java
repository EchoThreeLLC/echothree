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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.edit.CarrierEditFactory;
import com.echothree.control.user.carrier.common.edit.PartyCarrierAccountEdit;
import com.echothree.control.user.carrier.common.form.EditPartyCarrierAccountForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.EditPartyCarrierAccountResult;
import com.echothree.control.user.carrier.common.spec.PartyCarrierAccountSpec;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.PartyCarrierAccount;
import com.echothree.model.data.carrier.server.entity.PartyCarrierAccountDetail;
import com.echothree.model.data.carrier.server.value.PartyCarrierAccountDetailValue;
import com.echothree.model.data.party.server.entity.Party;
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

public class EditPartyCarrierAccountCommand
        extends BaseAbstractEditCommand<PartyCarrierAccountSpec, PartyCarrierAccountEdit, EditPartyCarrierAccountResult, PartyCarrierAccount, PartyCarrierAccount> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyCarrierAccount.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Account", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AlwaysUseThirdPartyBilling", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyCarrierAccountCommand */
    public EditPartyCarrierAccountCommand(UserVisitPK userVisitPK, EditPartyCarrierAccountForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyCarrierAccountResult getResult() {
        return CarrierResultFactory.getEditPartyCarrierAccountResult();
    }

    @Override
    public PartyCarrierAccountEdit getEdit() {
        return CarrierEditFactory.getPartyCarrierAccountEdit();
    }

    @Override
    public PartyCarrierAccount getEntity(EditPartyCarrierAccountResult result) {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        PartyCarrierAccount partyCarrierAccount = null;
        String partyName = spec.getPartyName();
        Party party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
            String carrierName = spec.getCarrierName();
            Carrier carrier = carrierControl.getCarrierByName(carrierName);

            if(carrier != null) {
                Party carrierParty = carrier.getParty();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyCarrierAccount = carrierControl.getPartyCarrierAccount(party, carrierParty);
                } else { // EditMode.UPDATE
                    partyCarrierAccount = carrierControl.getPartyCarrierAccountForUpdate(party, carrierParty);
                }

                if(partyCarrierAccount != null) {
                    result.setPartyCarrierAccount(carrierControl.getPartyCarrierAccountTransfer(getUserVisit(), partyCarrierAccount));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyCarrierAccount.name(), partyName, carrierName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return partyCarrierAccount;
    }

    @Override
    public PartyCarrierAccount getLockEntity(PartyCarrierAccount partyCarrierAccount) {
        return partyCarrierAccount;
    }

    @Override
    public void fillInResult(EditPartyCarrierAccountResult result, PartyCarrierAccount partyCarrierAccount) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);

        result.setPartyCarrierAccount(carrierControl.getPartyCarrierAccountTransfer(getUserVisit(), partyCarrierAccount));
    }

    @Override
    public void doLock(PartyCarrierAccountEdit edit, PartyCarrierAccount partyCarrierAccount) {
        PartyCarrierAccountDetail partyCarrierAccountDetail = partyCarrierAccount.getLastDetail();

        edit.setAccount(partyCarrierAccountDetail.getAccount());
        edit.setAlwaysUseThirdPartyBilling(partyCarrierAccountDetail.getAlwaysUseThirdPartyBilling().toString());
    }

    @Override
    public void doUpdate(PartyCarrierAccount partyCarrierAccount) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        PartyCarrierAccountDetailValue partyCarrierAccountDetailValue = carrierControl.getPartyCarrierAccountDetailValueForUpdate(partyCarrierAccount);

        partyCarrierAccountDetailValue.setAccount(edit.getAccount());
        partyCarrierAccountDetailValue.setAlwaysUseThirdPartyBilling(Boolean.valueOf(edit.getAlwaysUseThirdPartyBilling()));

        carrierControl.updatePartyCarrierAccountFromValue(partyCarrierAccountDetailValue, getPartyPK());
    }

}
