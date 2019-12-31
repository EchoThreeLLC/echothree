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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.edit.ScaleEdit;
import com.echothree.control.user.scale.common.edit.ScaleEditFactory;
import com.echothree.control.user.scale.common.form.EditScaleForm;
import com.echothree.control.user.scale.common.result.EditScaleResult;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.control.user.scale.common.spec.ScaleSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.scale.server.ScaleControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.core.server.entity.ServerService;
import com.echothree.model.data.core.server.entity.Service;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.scale.server.entity.Scale;
import com.echothree.model.data.scale.server.entity.ScaleDescription;
import com.echothree.model.data.scale.server.entity.ScaleDetail;
import com.echothree.model.data.scale.server.entity.ScaleType;
import com.echothree.model.data.scale.server.value.ScaleDescriptionValue;
import com.echothree.model.data.scale.server.value.ScaleDetailValue;
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

public class EditScaleCommand
        extends BaseAbstractEditCommand<ScaleSpec, ScaleEdit, EditScaleResult, Scale, Scale> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Scale.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ScaleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ServerName", FieldType.HOST_NAME, true, null, null),
                new FieldDefinition("ServiceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditScaleCommand */
    public EditScaleCommand(UserVisitPK userVisitPK, EditScaleForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditScaleResult getResult() {
        return ScaleResultFactory.getEditScaleResult();
    }

    @Override
    public ScaleEdit getEdit() {
        return ScaleEditFactory.getScaleEdit();
    }

    @Override
    public Scale getEntity(EditScaleResult result) {
        var scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
        Scale scale = null;
        String scaleName = spec.getScaleName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            scale = scaleControl.getScaleByName(scaleName);
        } else { // EditMode.UPDATE
            scale = scaleControl.getScaleByNameForUpdate(scaleName);
        }

        if(scale != null) {
            result.setScale(scaleControl.getScaleTransfer(getUserVisit(), scale));
        } else {
            addExecutionError(ExecutionErrors.UnknownScaleName.name(), scaleName);
        }

        return scale;
    }

    @Override
    public Scale getLockEntity(Scale scale) {
        return scale;
    }

    @Override
    public void fillInResult(EditScaleResult result, Scale scale) {
        var scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);

        result.setScale(scaleControl.getScaleTransfer(getUserVisit(), scale));
    }

    ServerService serverService;

    @Override
    public void doLock(ScaleEdit edit, Scale scale) {
        var scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
        ScaleDescription scaleDescription = scaleControl.getScaleDescription(scale, getPreferredLanguage());
        ScaleDetail scaleDetail = scale.getLastDetail();

        serverService = scaleDetail.getServerService();

        edit.setScaleName(scaleDetail.getScaleName());
        edit.setScaleTypeName(scaleDetail.getScaleType().getLastDetail().getScaleTypeName());
        edit.setServerName(serverService.getServer().getLastDetail().getServerName());
        edit.setServiceName(serverService.getService().getLastDetail().getServiceName());
        edit.setIsDefault(scaleDetail.getIsDefault().toString());
        edit.setSortOrder(scaleDetail.getSortOrder().toString());

        if(scaleDescription != null) {
            edit.setDescription(scaleDescription.getDescription());
        }
    }

    ScaleType scaleType;

    @Override
    public void canUpdate(Scale scale) {
        var scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
        String scaleName = edit.getScaleName();
        Scale duplicateScale = scaleControl.getScaleByName(scaleName);

        if(duplicateScale != null && !scale.equals(duplicateScale)) {
            addExecutionError(ExecutionErrors.DuplicateScaleName.name(), scaleName);
        } else {
            String scaleTypeName = edit.getScaleTypeName();

            scaleType = scaleControl.getScaleTypeByName(scaleTypeName);

            if(scaleType == null) {
                addExecutionError(ExecutionErrors.UnknownScaleTypeName.name(), scaleTypeName);
            } else {
                var coreControl = getCoreControl();
                String serverName = edit.getServerName();
                Server server = coreControl.getServerByName(serverName);

                if(server == null) {
                    addExecutionError(ExecutionErrors.UnknownServerName.name(), serverName);
                } else {
                    String serviceName = edit.getServiceName();
                    Service service = coreControl.getServiceByName(serviceName);

                    if(service == null) {
                        addExecutionError(ExecutionErrors.UnknownServiceName.name(), serviceName);
                    } else {
                        serverService = coreControl.getServerService(server, service);

                        if(serverService == null) {
                            addExecutionError(ExecutionErrors.UnknownServerService.name(), serverName, serviceName);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void doUpdate(Scale scale) {
        var scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
        PartyPK partyPK = getPartyPK();
        ScaleDetailValue scaleDetailValue = scaleControl.getScaleDetailValueForUpdate(scale);
        ScaleDescription scaleDescription = scaleControl.getScaleDescriptionForUpdate(scale, getPreferredLanguage());
        String description = edit.getDescription();

        scaleDetailValue.setScaleName(edit.getScaleName());
        scaleDetailValue.setScaleTypePK(scaleType.getPrimaryKey());
        scaleDetailValue.setServerServicePK(serverService.getPrimaryKey());
        scaleDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        scaleDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        scaleControl.updateScaleFromValue(scaleDetailValue, partyPK);

        if(scaleDescription == null && description != null) {
            scaleControl.createScaleDescription(scale, getPreferredLanguage(), description, partyPK);
        } else if(scaleDescription != null && description == null) {
            scaleControl.deleteScaleDescription(scaleDescription, partyPK);
        } else if(scaleDescription != null && description != null) {
            ScaleDescriptionValue scaleDescriptionValue = scaleControl.getScaleDescriptionValue(scaleDescription);

            scaleDescriptionValue.setDescription(description);
            scaleControl.updateScaleDescriptionFromValue(scaleDescriptionValue, partyPK);
        }
    }

}
