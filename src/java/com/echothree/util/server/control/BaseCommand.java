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

package com.echothree.util.server.control;

import com.echothree.control.user.party.common.spec.PartySpec;
import com.echothree.model.control.core.common.CommandMessageTypes;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.user.server.logic.UserSessionLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.Command;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.user.server.entity.UserVisitStatus;
import com.echothree.model.data.user.server.factory.UserVisitFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.message.SecurityMessages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionWarningAccumulator;
import com.echothree.util.server.message.MessageUtils;
import com.echothree.util.server.message.SecurityMessageAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;
import com.echothree.util.server.persistence.ThreadUtils;
import com.google.common.base.Charsets;
import java.util.List;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseCommand
        implements ExecutionWarningAccumulator, ExecutionErrorAccumulator, SecurityMessageAccumulator {

    private Log log = null;

    private UserVisitPK userVisitPK;
    private final CommandSecurityDefinition commandSecurityDefinition;

    private ThreadUtils.PreservedState preservedState;
    protected Session session;

    private UserVisit userVisit = null;
    private UserSession userSession = null;
    private Party party = null;
    private Messages executionWarnings = null;
    private Messages executionErrors = null;
    private Messages securityMessages = null;
    private String componentVendorName = null;
    private String commandName = null;
    private UserControl userControl = null;
    private boolean checkPasswordVerifiedTime = true;
    private boolean updateLastCommandTime = true;
    private boolean logCommand = true;

    protected BaseCommand(UserVisitPK userVisitPK, CommandSecurityDefinition commandSecurityDefinition) {
        if(ControlDebugFlags.LogBaseCommands) {
            getLog().info("BaseCommand()");
        }

        this.userVisitPK = userVisitPK;
        this.commandSecurityDefinition = commandSecurityDefinition;
    }

    protected final Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }
        
        return log;
    }
    
    private void setupNames() {
        Class c = this.getClass();
        String className = c.getName();
        int nameOffset = className.lastIndexOf('.');
        
        componentVendorName = ComponentVendors.ECHOTHREE.name();
        commandName = new String(className.getBytes(Charsets.UTF_8), nameOffset + 1, className.length() - nameOffset - 8, Charsets.UTF_8);
    }
    
    public String getComponentVendorName() {
        if(componentVendorName == null) {
            setupNames();
        }
        
        return componentVendorName;
    }
    
    public String getCommandName() {
        if(commandName == null) {
            setupNames();
        }
        
        return commandName;
    }
    
    public Party getCompanyParty() {
        Party companyParty = null;
        PartyRelationship partyRelationship = userSession.getPartyRelationship();
        
        if(partyRelationship != null) {
            companyParty = partyRelationship.getFromParty();
        }
        
        return companyParty;
    }
    
    public PartyPK getPartyPK() {
        if(party == null) {
            getParty();
        }
        
        return party == null? null: party.getPrimaryKey();
    }
    
    public Party getParty() {
        if(party == null) {
            party = getUserControl().getPartyFromUserVisitPK(userVisitPK);
        }
        
        return party;
    }
    
    public PartyType getPartyType() {
        PartyType partyType = null;
        
        if(getParty() != null) {
            partyType = party.getLastDetail().getPartyType();
        }
        
        return partyType;
    }
    
    public String getPartyTypeName() {
        PartyType partyType = getPartyType();
        String partyTypeName = partyType == null ? null : partyType.getPartyTypeName();

        return partyTypeName;
    }
    
    public UserVisitPK getUserVisitPK() {
        return userVisitPK;
    }

    public void setUserVisitPK(UserVisitPK userVisitPK) {
        this.userVisitPK = userVisitPK;
        userVisit = null;
    }

    private UserVisit getUserVisit(EntityPermission entityPermission) {
        if(userVisitPK != null) {
            if(userVisit == null) {
                userVisit = UserVisitFactory.getInstance().getEntityFromPK(entityPermission, userVisitPK);
            } else {
                if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                    if(!userVisit.getEntityPermission().equals(EntityPermission.READ_WRITE)) {
                        userVisit = UserVisitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, userVisitPK);
                    }
                }
            }
        }

        return userVisit;
    }
    
    public UserVisit getUserVisit() {
        return getUserVisit(EntityPermission.READ_ONLY);
    }
    
    public UserVisit getUserVisitForUpdate() {
        return getUserVisit(EntityPermission.READ_WRITE);
    }
    
    public UserSession getUserSession() {
        return userSession;
    }
    
    public Session getSession() {
        return session;
    }
    
    public UserControl getUserControl() {
        if(userControl == null) {
            userControl = Session.getModelController(UserControl.class);
        }
        
        return userControl;
    }
    
    public Language getPreferredLanguage() {
        return getUserControl().getPreferredLanguageFromUserVisit(getUserVisit());
    }
    
    public Language getPreferredLanguage(Party party) {
        return getUserControl().getPreferredLanguageFromParty(party);
    }
    
    public Currency getPreferredCurrency() {
        return getUserControl().getPreferredCurrencyFromUserVisit(getUserVisit());
    }
    
    public Currency getPreferredCurrency(Party party) {
        return getUserControl().getPreferredCurrencyFromParty(party);
    }
    
    public TimeZone getPreferredTimeZone() {
        return getUserControl().getPreferredTimeZoneFromUserVisit(getUserVisit());
    }
    
    public TimeZone getPreferredTimeZone(Party party) {
        return getUserControl().getPreferredTimeZoneFromParty(party);
    }
    
    public DateTimeFormat getPreferredDateTimeFormat() {
        return getUserControl().getPreferredDateTimeFormatFromUserVisit(getUserVisit());
    }
    
    public DateTimeFormat getPreferredDateTimeFormat(Party party) {
        return getUserControl().getPreferredDateTimeFormatFromParty(party);
    }
    
    public boolean getCheckPasswordVerifiedTime() {
        return checkPasswordVerifiedTime;
    }

    public void setCheckPasswordVerifiedTime(boolean checkPasswordVerifiedTime) {
        this.checkPasswordVerifiedTime = checkPasswordVerifiedTime;
    }

    public boolean getUpdateLastCommandTime() {
        return updateLastCommandTime;
    }

    public void setUpdateLastCommandTime(boolean updateLastCommandTime) {
        this.updateLastCommandTime = updateLastCommandTime;
    }

    public boolean getLogCommand() {
        return logCommand;
    }

    public void setLogCommand(boolean logCommand) {
        this.logCommand = logCommand;
    }

    private void checkUserVisit() {
        if(getUserVisit() != null) {
            userSession = getUserControl().getUserSessionByUserVisit(userVisit);

            if(userSession != null && checkPasswordVerifiedTime) {
                Long passwordVerifiedTime = userSession.getPasswordVerifiedTime();

                if(passwordVerifiedTime != null) {
                    long timeSinceLastCommand = session.START_TIME - userVisit.getLastCommandTime();

                    // If it has been > 15 minutes since their last command, invalidate the UserSession.
                    if(timeSinceLastCommand > 15 * 60 * 1000) {
                        userSession = UserSessionLogic.getInstance().invalidateUserSession(userSession);
                    }
                }
            }
        }
    }

    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return commandSecurityDefinition;
    }
    
    // Returns true if everything passes.
    protected boolean checkCommandSecurityDefinition() {
        boolean passed = true;
        CommandSecurityDefinition myCommandSecurityDefinition = getCommandSecurityDefinition();
        
        if(myCommandSecurityDefinition != null) {
            String partyTypeName = getParty() == null ? null : party.getLastDetail().getPartyType().getPartyTypeName();
            boolean foundPartyType = false;
            boolean foundPartySecurityRole = false;

            for(PartyTypeDefinition partyTypeDefinition : myCommandSecurityDefinition.getPartyTypeDefinitions()) {
                if(partyTypeName == null) {
                    if(partyTypeDefinition.getPartyTypeName() == null) {
                        foundPartyType = true;
                        foundPartySecurityRole = true;
                        break;
                    }
                } else {
                    if(partyTypeDefinition.getPartyTypeName().equals(partyTypeName)) {
                        List<SecurityRoleDefinition> securityRoleDefinitions = partyTypeDefinition.getSecurityRoleDefinitions();

                        if(securityRoleDefinitions == null) {
                            foundPartySecurityRole = true;
                        } else {
                            SecurityRoleLogic securityRoleLogic = SecurityRoleLogic.getInstance();

                            for(SecurityRoleDefinition securityRoleDefinition : securityRoleDefinitions) {
                                String securityRoleGroupName = securityRoleDefinition.getSecurityRoleGroupName();
                                String securityRoleName = securityRoleDefinition.getSecurityRoleName();

                                if(securityRoleGroupName != null && securityRoleName != null) {
                                    foundPartySecurityRole = securityRoleLogic.hasSecurityRoleUsingNames(this, party, securityRoleGroupName,
                                            securityRoleName);
                                }

                                if(foundPartySecurityRole) {
                                    break;
                                }
                            }
                        }

                        foundPartyType = true;
                        break;
                    }
                }
            }

            if(!foundPartyType || !foundPartySecurityRole) {
                passed = false;
            }
        }
        
        return passed;
    }

    // Returns true if everything passes.
    protected boolean checkOptionalSecurityRoles() {
        return true;
    }

    protected SecurityResult security() {
        if(!(checkCommandSecurityDefinition() && checkOptionalSecurityRoles())) {
            addSecurityMessage(SecurityMessages.InsufficientSecurity.name());
        }

        return securityMessages == null ? null : new SecurityResult(securityMessages);
    }
    
    @Override
    public void addSecurityMessage(Message message) {
        if(securityMessages == null) {
            securityMessages = new Messages();
        }
        
        securityMessages.add(Messages.SECURITY_MESSAGE, message);
    }
    
    @Override
    public void addSecurityMessage(String key, Object... values) {
        addSecurityMessage(new Message(key, values));
    }
    
    @Override
    public Messages getSecurityMessages() {
        return securityMessages;
    }
    
    @Override
    public boolean hasSecurityMessages() {
        return securityMessages == null ? false : securityMessages.size(Messages.SECURITY_MESSAGE) != 0;
    }
    
    protected ValidationResult validate() {
        if(ControlDebugFlags.LogBaseCommands) {
            log.info("validate()");
        }
        
        return null;
    }
    
    protected abstract BaseResult execute();
    
    @Override
    public void addExecutionWarning(Message message) {
        if(executionWarnings == null) {
            executionWarnings = new Messages();
        }
        
        executionWarnings.add(Messages.EXECUTION_WARNING, message);
    }
    
    @Override
    public void addExecutionWarning(String key, Object... values) {
        addExecutionWarning(new Message(key, values));
    }
    
    @Override
    public Messages getExecutionWarnings() {
        return executionWarnings;
    }
    
    @Override
    public boolean hasExecutionWarnings() {
        return executionWarnings == null ? false : executionWarnings.size(Messages.EXECUTION_WARNING) != 0;
    }
    
    @Override
    public void addExecutionError(Message message) {
        if(executionErrors == null) {
            executionErrors = new Messages();
        }
        
        executionErrors.add(Messages.EXECUTION_ERROR, message);
    }
    
    @Override
    public void addExecutionError(String key, Object... values) {
        addExecutionError(new Message(key, values));
    }
    
    @Override
    public Messages getExecutionErrors() {
        return executionErrors;
    }
    
    @Override
    public boolean hasExecutionErrors() {
        return executionErrors == null ? false : executionErrors.size(Messages.EXECUTION_ERROR) != 0;
    }
    
    protected BaseResult getBaseResultAfterErrors() {
        return null;
    }

    public final Future<CommandResult> runAsync() {
        return new AsyncResult<>(run());
    }

    protected void setupSession() {
        preservedState = ThreadUtils.preserveState();
        session = ThreadSession.currentSession();
    }

    protected void teardownSession() {
        ThreadUtils.close();
        session = null;

        ThreadUtils.restoreState(preservedState);
        preservedState = null;
    }

    public final CommandResult run()
            throws BaseException {
        if(ControlDebugFlags.LogBaseCommands) {
            log.info(">>> run()");
        }

        setupSession();

        SecurityResult securityResult;
        ValidationResult validationResult = null;
        ExecutionResult executionResult;
        CommandResult commandResult;

        try {
            BaseResult baseResult = null;

//            if(LicenseCheckLogic.getInstance().permitExecution(session)) {
                checkUserVisit();
                securityResult = security();

                if(securityResult == null || !securityResult.getHasMessages()) {
                    validationResult = validate();

                    if(validationResult == null || !validationResult.getHasErrors()) {
                        baseResult = execute();
                    }
                }
//            } else {
//                addExecutionError(ExecutionErrors.LicenseCheckFailed.name());
//            }

            executionResult = new ExecutionResult(executionWarnings, executionErrors, baseResult == null ? getBaseResultAfterErrors() : baseResult);

            // Don't waste time getting the preferredLanguage if we don't need to.
            if((securityResult != null && securityResult.getHasMessages())
                    || (executionResult.getHasWarnings() || executionResult.getHasErrors())
                    || (validationResult != null && validationResult.getHasErrors())) {
                Language preferredLanguage = getPreferredLanguage();

                if(securityResult != null) {
                    MessageUtils.getInstance().fillInMessages(preferredLanguage, CommandMessageTypes.Security.name(), securityResult.getSecurityMessages());
                }

                MessageUtils.getInstance().fillInMessages(preferredLanguage, CommandMessageTypes.Warning.name(), executionResult.getExecutionWarnings());
                MessageUtils.getInstance().fillInMessages(preferredLanguage, CommandMessageTypes.Error.name(), executionResult.getExecutionErrors());

                if(validationResult != null) {
                    MessageUtils.getInstance().fillInMessages(preferredLanguage, CommandMessageTypes.Validation.name(), validationResult.getValidationMessages());
                }
            }

            if(updateLastCommandTime) {
                if(getUserVisitForUpdate() == null) {
                    getLog().error("Command not logged, unknown userVisit");
                } else {
                    userVisit.setLastCommandTime(Math.max(session.START_TIME, userVisit.getLastCommandTime()));

                    // TODO: Check PartyTypeAuditPolicy to see if the command should be logged
                    if(logCommand) {
                        ComponentVendor componentVendor = getCoreControl().getComponentVendorByName(getComponentVendorName());

                        if(componentVendor != null) {
                            getCommandName();
                            getParty(); // TODO: should only use if UserSession.PasswordVerifiedTime != null

                            if(ControlDebugFlags.CheckCommandNameLength) {
                                if(commandName.length() > 80) {
                                    getLog().error("commandName legnth > 80 characters, " + commandName);
                                    commandName = commandName.substring(0, 79);
                                }
                            }

                            Command command = coreControl.getCommandByName(componentVendor, commandName);

                            if(command == null) {
                                command = coreControl.createCommand(componentVendor, commandName, 1, party == null ? null : party.getPrimaryKey());
                            }

                            if(command != null) {
                                UserVisitStatus userVisitStatus = userControl.getUserVisitStatusForUpdate(userVisit);

                                if(userVisitStatus != null) {
                                    Integer userVisitCommandSequence = userVisitStatus.getUserVisitCommandSequence() + 1;
                                    Boolean hadSecurityErrors = securityResult == null ? null : securityResult.getHasMessages();
                                    Boolean hadValidationErrors = validationResult == null ? null : validationResult.getHasErrors();
                                    Boolean hasExecutionErrors = executionResult.getHasErrors();

                                    userVisitStatus.setUserVisitCommandSequence(userVisitCommandSequence);

                                    getUserControl().createUserVisitCommand(userVisit, userVisitCommandSequence, party, command, session.START_TIME_LONG,
                                            System.currentTimeMillis(), hadSecurityErrors, hadValidationErrors, hasExecutionErrors);
                                } else {
                                    getLog().error("Command not logged, unknown userVisitStatus for " + userVisit.getPrimaryKey());
                                }
                            } else {
                                getLog().error("Command not logged, unknown (and could not create) commandName = " + commandName);
                            }
                        } else {
                            getLog().error("Command not logged, unknown componentVendorName = " + componentVendorName);
                        }
                    }
                }
            }
        } finally {
            teardownSession();
        }

        // The Session for this Thread must NOT be utilized by anything after teardownSession() has been called.
        commandResult = new CommandResult(securityResult, validationResult, executionResult);

        if(commandResult.hasSecurityMessages() || commandResult.hasValidationErrors()) {
            getLog().info("commandResult = " + commandResult);
        }

        if(ControlDebugFlags.LogBaseCommands) {
            if(commandResult.hasExecutionErrors()) {
                log.info("<<< run(), returning executionResult = " + commandResult.getExecutionResult());
            } else {
                log.info("<<< run()");
            }
        }

        return commandResult;
    }

    // --------------------------------------------------------------------------------
    //   Security Utilities
    // --------------------------------------------------------------------------------

    protected SecurityResult selfOnly(PartySpec spec) {
        var hasInsufficientSecurity = false;
        var partyTypeName = getPartyType().getPartyTypeName();

        if(partyTypeName.equals(PartyTypes.CUSTOMER.name()) || partyTypeName.equals(PartyTypes.VENDOR.name())) {
            if(spec.getPartyName() != null) {
                hasInsufficientSecurity = true;
            }
        }

        return hasInsufficientSecurity ? getInsufficientSecurityResult() : null;
    }

    protected SecurityResult getInsufficientSecurityResult() {
        return new SecurityResult(new Messages().add(Messages.SECURITY_MESSAGE, new Message(SecurityMessages.InsufficientSecurity.name())));
    }

    // --------------------------------------------------------------------------------
    //   Event Utilities
    // --------------------------------------------------------------------------------
    
    private CoreControl coreControl = null;
    
    protected CoreControl getCoreControl() {
        if(coreControl == null) {
            coreControl = Session.getModelController(CoreControl.class);
        }
        
        return coreControl;
    }
    
    protected EntityInstance getEntityInstanceByBasePK(BasePK pk) {
        return getCoreControl().getEntityInstanceByBasePK(pk);
    }
    
    protected Event sendEventUsingNames(BasePK entityInstancePK, String eventTypeName, BasePK relatedPK, String relatedEventTypeName,
            BasePK createdByPK) {
        EntityInstance entityInstance = getEntityInstanceByBasePK(entityInstancePK);
        EntityInstance relatedEntityInstance = relatedPK == null? null: getEntityInstanceByBasePK(relatedPK);
        
        return sendEventUsingNames(entityInstance, eventTypeName, relatedEntityInstance, relatedEventTypeName, createdByPK);
    }
    
    protected Event sendEventUsingNames(EntityInstance entityInstance, String eventTypeName, BasePK relatedPK, String relatedEventTypeName,
            BasePK createdByPK) {
        EntityInstance relatedEntityInstance = relatedPK == null? null: getEntityInstanceByBasePK(relatedPK);
        
        return sendEventUsingNames(entityInstance, eventTypeName, relatedEntityInstance, relatedEventTypeName, createdByPK);
    }
    
    protected Event sendEventUsingNames(EntityInstance entityInstance, String eventTypeName, EntityInstance relatedEntityInstance,
            String relatedEventTypeName, BasePK createdByPK) {
        Event event = null;
        
        if(createdByPK != null) {
            event = getCoreControl().sendEventUsingNames(entityInstance, eventTypeName, relatedEntityInstance, relatedEventTypeName,
                createdByPK);
        }
        
        return event;
    }
    
    // --------------------------------------------------------------------------------
    //   Option Utilities
    // --------------------------------------------------------------------------------

    /** This should only be called an override of setupSession(). After that, TransferCaches may have cached knowledge
     * that specific options were set.
     * @param option The option to remove.
     */
    protected void removeOption(String option) {
        session.getOptions().remove(option);
    }

    // --------------------------------------------------------------------------------
    //   Transfer Property Utilities
    // --------------------------------------------------------------------------------

    /** This should only be called an override of setupSession(). After that, TransferCaches may have cached knowledge
     * that specific properties were filtered.
     * @param clazz The Class whose properties should be examined.
     * @param property The property to remove.
     */
    protected void removeFilteredTransferProperty(Class clazz, String property) {
        var transferProperties = session.getTransferProperties();

        if(transferProperties != null) {
            var properties = transferProperties.getProperties(clazz);

            if(properties != null) {
                properties.remove(property);
            }
        }
    }
    
}
