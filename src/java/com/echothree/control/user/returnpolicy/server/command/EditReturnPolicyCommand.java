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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.remote.edit.ReturnPolicyEdit;
import com.echothree.control.user.returnpolicy.remote.edit.ReturnPolicyEditFactory;
import com.echothree.control.user.returnpolicy.remote.form.EditReturnPolicyForm;
import com.echothree.control.user.returnpolicy.remote.result.EditReturnPolicyResult;
import com.echothree.control.user.returnpolicy.remote.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.remote.spec.ReturnPolicySpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyTranslation;
import com.echothree.model.data.returnpolicy.server.value.ReturnPolicyDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnPolicyTranslationValue;
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

public class EditReturnPolicyCommand
        extends BaseAbstractEditCommand<ReturnPolicySpec, ReturnPolicyEdit, EditReturnPolicyResult, ReturnPolicy, ReturnPolicy> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnPolicy.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("PolicyMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Policy", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditReturnPolicyCommand */
    public EditReturnPolicyCommand(UserVisitPK userVisitPK, EditReturnPolicyForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditReturnPolicyResult getResult() {
        return ReturnPolicyResultFactory.getEditReturnPolicyResult();
    }

    @Override
    public ReturnPolicyEdit getEdit() {
        return ReturnPolicyEditFactory.getReturnPolicyEdit();
    }

    ReturnKind returnKind;

    @Override
    public ReturnPolicy getEntity(EditReturnPolicyResult result) {
        ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        ReturnPolicy returnPolicy = null;
        String returnKindName = spec.getReturnKindName();

        returnKind = returnPolicyControl.getReturnKindByName(returnKindName);

        if(returnKind != null) {
            String returnPolicyName = spec.getReturnPolicyName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
            } else { // EditMode.UPDATE
                returnPolicy = returnPolicyControl.getReturnPolicyByNameForUpdate(returnKind, returnPolicyName);
            }

            if(returnPolicy != null) {
                result.setReturnPolicy(returnPolicyControl.getReturnPolicyTransfer(getUserVisit(), returnPolicy));
            } else {
                addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnKindName, returnPolicyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }

        return returnPolicy;
    }

    @Override
    public ReturnPolicy getLockEntity(ReturnPolicy returnPolicy) {
        return returnPolicy;
    }

    @Override
    public void fillInResult(EditReturnPolicyResult result, ReturnPolicy returnPolicy) {
        ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);

        result.setReturnPolicy(returnPolicyControl.getReturnPolicyTransfer(getUserVisit(), returnPolicy));
    }

    MimeType policyMimeType;

    @Override
    public void doLock(ReturnPolicyEdit edit, ReturnPolicy returnPolicy) {
        ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        ReturnPolicyTranslation returnPolicyTranslation = returnPolicyControl.getReturnPolicyTranslation(returnPolicy, getPreferredLanguage());
        ReturnPolicyDetail returnPolicyDetail = returnPolicy.getLastDetail();

        edit.setReturnPolicyName(returnPolicyDetail.getReturnPolicyName());
        edit.setIsDefault(returnPolicyDetail.getIsDefault().toString());
        edit.setSortOrder(returnPolicyDetail.getSortOrder().toString());

        if(returnPolicyTranslation != null) {
            policyMimeType = returnPolicyTranslation.getPolicyMimeType();

            edit.setDescription(returnPolicyTranslation.getDescription());
            edit.setPolicyMimeTypeName(policyMimeType == null? null: policyMimeType.getLastDetail().getMimeTypeName());
            edit.setPolicy(returnPolicyTranslation.getPolicy());
        }
    }

    @Override
    public void canUpdate(ReturnPolicy returnPolicy) {
        ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        String returnPolicyName = edit.getReturnPolicyName();
        ReturnPolicy duplicateReturnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);

        if(duplicateReturnPolicy != null && !returnPolicy.equals(duplicateReturnPolicy)) {
            addExecutionError(ExecutionErrors.DuplicateReturnPolicyName.name(), returnPolicyName);
        } else {
            MimeTypeLogic mimeTypeLogic = MimeTypeLogic.getInstance();
            String policyMimeTypeName = edit.getPolicyMimeTypeName();
            String policy = edit.getPolicy();

            policyMimeType = mimeTypeLogic.checkMimeType(this, policyMimeTypeName, policy, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredPolicyMimeTypeName.name(), ExecutionErrors.MissingRequiredPolicy.name(),
                    ExecutionErrors.UnknownPolicyMimeTypeName.name(), ExecutionErrors.UnknownPolicyMimeTypeUsage.name());
        }
    }

    @Override
    public void doUpdate(ReturnPolicy returnPolicy) {
        ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        PartyPK partyPK = getPartyPK();
        ReturnPolicyDetailValue returnPolicyDetailValue = returnPolicyControl.getReturnPolicyDetailValueForUpdate(returnPolicy);
        ReturnPolicyTranslation returnPolicyTranslation = returnPolicyControl.getReturnPolicyTranslationForUpdate(returnPolicy, getPreferredLanguage());
        String description = edit.getDescription();
        String policy = edit.getPolicy();

        returnPolicyDetailValue.setReturnPolicyName(edit.getReturnPolicyName());
        returnPolicyDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        returnPolicyDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        returnPolicyControl.updateReturnPolicyFromValue(returnPolicyDetailValue, partyPK);

        if(returnPolicyTranslation == null && (description != null || policy != null)) {
            returnPolicyControl.createReturnPolicyTranslation(returnPolicy, getPreferredLanguage(), description, policyMimeType, policy,
                    partyPK);
        } else if(returnPolicyTranslation != null && (description == null && policy == null)) {
            returnPolicyControl.deleteReturnPolicyTranslation(returnPolicyTranslation, partyPK);
        } else if(returnPolicyTranslation != null && (description != null || policy != null)) {
            ReturnPolicyTranslationValue returnPolicyTranslationValue = returnPolicyControl.getReturnPolicyTranslationValue(returnPolicyTranslation);

            returnPolicyTranslationValue.setDescription(description);
            returnPolicyTranslationValue.setPolicyMimeTypePK(policyMimeType == null? null: policyMimeType.getPrimaryKey());
            returnPolicyTranslationValue.setPolicy(policy);
            returnPolicyControl.updateReturnPolicyTranslationFromValue(returnPolicyTranslationValue, partyPK);
        }
    }

}
