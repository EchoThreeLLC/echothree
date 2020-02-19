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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.form.CreateGeoCodeAliasForm;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateGeoCodeAliasCommand
        extends BaseSimpleCommand<CreateGeoCodeAliasForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeAlias.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GeoCodeAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateGeoCodeAliasCommand */
    public CreateGeoCodeAliasCommand(UserVisitPK userVisitPK, CreateGeoCodeAliasForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        String geoCodeName = form.getGeoCodeName();
        GeoCode geoCode = geoControl.getGeoCodeByName(geoCodeName);

        if(geoCode != null) {
            GeoCodeType geoCodeType = geoCode.getLastDetail().getGeoCodeType();
            String geoCodeAliasTypeName = form.getGeoCodeAliasTypeName();
            GeoCodeAliasType geoCodeAliasType = geoControl.getGeoCodeAliasTypeByNameForUpdate(geoCodeType, geoCodeAliasTypeName);

            if(geoCodeAliasType != null) {
                GeoCodeScope geoCodeScope = geoCode.getLastDetail().getGeoCodeScope();
                GeoCodeAlias geoCodeAlias = geoControl.getGeoCodeAliasForUpdate(geoCode, geoCodeAliasType);
                String alias = form.getAlias();

                if(geoCodeAlias == null) {
                    geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias);

                    if(geoCodeAlias == null) {
                        String validationPattern = geoCodeAliasType.getLastDetail().getValidationPattern();

                        if(validationPattern != null) {
                            Pattern pattern = Pattern.compile(validationPattern);
                            Matcher m = pattern.matcher(alias);

                            if(!m.matches()) {
                                addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
                            }
                        }

                        if(!hasExecutionErrors()) {
                            geoControl.createGeoCodeAlias(geoCode, geoCodeAliasType, alias, getPartyPK());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateGeoCodeAlias.name(), geoCodeName, geoCodeScope.getLastDetail().getGeoCodeScopeName(), geoCodeAliasTypeName, alias);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateGeoCodeAlias.name(), geoCodeName, geoCodeScope.getLastDetail().getGeoCodeScopeName(), geoCodeAliasTypeName, alias);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGeoCodeAliasTypeName.name(), geoCodeType.getLastDetail().getGeoCodeTypeName(), geoCodeAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
        
        return null;
    }
    
}
