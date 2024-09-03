// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.form.GetTrainingClassPageForm;
import com.echothree.control.user.training.common.result.GetTrainingClassPageResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassSessionLogic;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSession;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionPage;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionStatus;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassPage;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
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

public class GetTrainingClassPageCommand
        extends BaseSimpleCommand<GetTrainingClassPageForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassPage.name(), SecurityRoles.Review.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTrainingClassName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetTrainingClassPageCommand */
    public GetTrainingClassPageCommand(UserVisitPK userVisitPK, GetTrainingClassPageForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var result = TrainingResultFactory.getGetTrainingClassPageResult();
        var trainingClassName = form.getTrainingClassName();
        var trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            var trainingClassSectionName = form.getTrainingClassSectionName();
            var trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

            if(trainingClassSection != null) {
                var trainingClassPageName = form.getTrainingClassPageName();
                var trainingClassPage = trainingControl.getTrainingClassPageByName(trainingClassSection, trainingClassPageName);

                if(trainingClassPage != null) {
                    var partyTrainingClassName = form.getPartyTrainingClassName();
                    var partyTrainingClassSessionStatus = partyTrainingClassName == null ? null
                            : PartyTrainingClassSessionLogic.getInstance().getLatestPartyTrainingClassSessionStatusForUpdate(this, partyTrainingClassName);
                    
                    if(!hasExecutionErrors()) {
                        var partyTrainingClassSession = partyTrainingClassSessionStatus == null ? null
                                : partyTrainingClassSessionStatus.getPartyTrainingClassSession();
                        
                        // Verify that the TrainingClass from above is same as the one being used by the PartyTrainingClassSession.
                        if(partyTrainingClassSession != null) {
                            if(!trainingClass.equals(partyTrainingClassSession.getLastDetail().getPartyTrainingClass().getLastDetail().getTrainingClass())) {
                                addExecutionError(ExecutionErrors.InvalidPartyTrainingClass.name(), partyTrainingClassName);
                            }
                        }
                        
                        if(!hasExecutionErrors()) {
                            var userVisit = getUserVisit();
                            var partyPK = getPartyPK();

                            result.setTrainingClassPage(trainingControl.getTrainingClassPageTransfer(userVisit, trainingClassPage));

                            sendEvent(trainingClassPage.getPrimaryKey(), EventTypes.READ, null, null, partyPK);

                            if(partyTrainingClassSessionStatus != null) {
                                var partyTrainingClassSessionPage = trainingControl.createPartyTrainingClassSessionPage(partyTrainingClassSession,
                                        trainingClassPage, session.START_TIME_LONG, null, partyPK);

                                PartyTrainingClassSessionLogic.getInstance().updatePartyTrainingClassSessionStatus(session, partyTrainingClassSessionStatus,
                                        null, partyTrainingClassSessionPage, null);
                                
                                result.setPartyTrainingClassSessionPage(trainingControl.getPartyTrainingClassSessionPageTransfer(userVisit, partyTrainingClassSessionPage));
                            }
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassPageName.name(), trainingClassName, trainingClassSectionName, trainingClassPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return result;
    }
    
}
