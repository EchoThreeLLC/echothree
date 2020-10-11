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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.edit.LetterSourceEdit;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismAlias;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;

public class LetterSourceCommandUtil {
    
    private LetterSourceCommandUtil() {
        super();
    }
    
    private static class LetterSourceCommandUtilHolder {
        static LetterSourceCommandUtil instance = new LetterSourceCommandUtil();
    }
    
    public static LetterSourceCommandUtil getInstance() {
        return LetterSourceCommandUtilHolder.instance;
    }
    
    public PartyContactMechanism getEmailAddressContactMechanism(ExecutionErrorAccumulator eea,
            LetterSourceEdit edit, ContactControl contactControl, Party companyParty) {
        PartyContactMechanism partyContactMechanism = null;
        String emailAddressContactMechanismName = edit.getEmailAddressContactMechanismName();
        String emailAddressContactMechanismAliasTypeName = edit.getEmailAddressContactMechanismAliasTypeName();
        String alias = edit.getEmailAddressContactMechanismAlias();
        int parameterCount = (emailAddressContactMechanismName != null && emailAddressContactMechanismAliasTypeName == null && alias == null? 1: 0)
                + (emailAddressContactMechanismName == null && emailAddressContactMechanismAliasTypeName != null && alias != null? 1: 0);
        
        if(parameterCount == 1) {
            ContactMechanism emailAddressContactMechanism = null;
            
            if(emailAddressContactMechanismName != null) {
                emailAddressContactMechanism = contactControl.getContactMechanismByName(emailAddressContactMechanismName);
                
                if(emailAddressContactMechanism == null) {
                    eea.addExecutionError(ExecutionErrors.UnknownEmailAddressContactMechanismName.name(), emailAddressContactMechanismName);
                }
            } else {
                ContactMechanismAliasType emailAddressContactMechanismAliasType = contactControl.getContactMechanismAliasTypeByName(emailAddressContactMechanismAliasTypeName);
                
                if(emailAddressContactMechanismAliasType != null) {
                    ContactMechanismAlias emailAddressContactMechanismAlias = contactControl.getContactMechanismAliasByAlias(emailAddressContactMechanismAliasType, alias);
                    
                    if(emailAddressContactMechanismAlias != null) {
                        emailAddressContactMechanism = emailAddressContactMechanismAlias.getContactMechanism();
                    } else {
                        eea.addExecutionError(ExecutionErrors.UnknownEmailAddressContactMechanismAlias.name(), emailAddressContactMechanismAliasTypeName, alias);
                    }
                } else {
                    eea.addExecutionError(ExecutionErrors.UnknownEmailAddressContactMechanismAliasTypeName.name(), emailAddressContactMechanismAliasTypeName);
                }
            }
            
            if(emailAddressContactMechanism != null) {
                String contactMechanismTypeName = emailAddressContactMechanism.getLastDetail().getContactMechanismType().getContactMechanismTypeName();
                
                if(contactMechanismTypeName.equals(ContactMechanismTypes.EMAIL_ADDRESS.name())) {
                    partyContactMechanism = contactControl.getPartyContactMechanism(companyParty, emailAddressContactMechanism);
                    
                    if(partyContactMechanism == null) {
                        eea.addExecutionError(ExecutionErrors.UnknownEmailAddressPartyContactMechanism.name());
                    }
                } else {
                    eea.addExecutionError(ExecutionErrors.InvalidContactMechanismType.name());
                }
            }
        } else {
            eea.addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return partyContactMechanism;
    }
    
    public PartyContactMechanism getPostalAddressContactMechanism(ExecutionErrorAccumulator eea,
            LetterSourceEdit edit, ContactControl contactControl, Party companyParty) {
        PartyContactMechanism partyContactMechanism = null;
        String postalAddressContactMechanismName = edit.getPostalAddressContactMechanismName();
        String postalAddressContactMechanismAliasTypeName = edit.getPostalAddressContactMechanismAliasTypeName();
        String alias = edit.getPostalAddressContactMechanismAlias();
        int parameterCount = (postalAddressContactMechanismName != null && postalAddressContactMechanismAliasTypeName == null && alias == null? 1: 0)
                + (postalAddressContactMechanismName == null && postalAddressContactMechanismAliasTypeName != null && alias != null? 1: 0);
        
        if(parameterCount == 1) {
            ContactMechanism postalAddressContactMechanism = null;
            
            if(postalAddressContactMechanismName != null) {
                postalAddressContactMechanism = contactControl.getContactMechanismByName(postalAddressContactMechanismName);
                
                if(postalAddressContactMechanism == null) {
                    eea.addExecutionError(ExecutionErrors.UnknownPostalAddressContactMechanismName.name(), postalAddressContactMechanismName);
                }
            } else {
                ContactMechanismAliasType postalAddressContactMechanismAliasType = contactControl.getContactMechanismAliasTypeByName(postalAddressContactMechanismAliasTypeName);
                
                if(postalAddressContactMechanismAliasType != null) {
                    ContactMechanismAlias postalAddressContactMechanismAlias = contactControl.getContactMechanismAliasByAlias(postalAddressContactMechanismAliasType, alias);
                    
                    if(postalAddressContactMechanismAlias != null) {
                        postalAddressContactMechanism = postalAddressContactMechanismAlias.getContactMechanism();
                    } else {
                        eea.addExecutionError(ExecutionErrors.UnknownPostalAddressContactMechanismAlias.name(), postalAddressContactMechanismAliasTypeName, alias);
                    }
                } else {
                    eea.addExecutionError(ExecutionErrors.UnknownPostalAddressContactMechanismAliasTypeName.name(), postalAddressContactMechanismAliasTypeName);
                }
            }
            
            if(postalAddressContactMechanism != null) {
                String contactMechanismTypeName = postalAddressContactMechanism.getLastDetail().getContactMechanismType().getContactMechanismTypeName();
                
                if(contactMechanismTypeName.equals(ContactMechanismTypes.POSTAL_ADDRESS.name())) {
                    partyContactMechanism = contactControl.getPartyContactMechanism(companyParty, postalAddressContactMechanism);
                    
                    if(partyContactMechanism == null) {
                        eea.addExecutionError(ExecutionErrors.UnknownEmailAddressPartyContactMechanism.name());
                    }
                } else {
                    eea.addExecutionError(ExecutionErrors.InvalidContactMechanismType.name());
                }
            }
        } else {
            eea.addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return partyContactMechanism;
    }
    
    public PartyContactMechanism getLetterSourceContactMechanism(ExecutionErrorAccumulator eea,
            LetterSourceEdit edit, ContactControl contactControl, Party companyParty) {
        PartyContactMechanism partyContactMechanism = null;
        String letterSourceContactMechanismName = edit.getLetterSourceContactMechanismName();
        String letterSourceContactMechanismAliasTypeName = edit.getLetterSourceContactMechanismAliasTypeName();
        String alias = edit.getLetterSourceContactMechanismAlias();
        int parameterCount = (letterSourceContactMechanismName != null && letterSourceContactMechanismAliasTypeName == null && alias == null? 1: 0)
                + (letterSourceContactMechanismName == null && letterSourceContactMechanismAliasTypeName != null && alias != null? 1: 0);
        
        if(parameterCount == 1) {
            ContactMechanism letterSourceContactMechanism = null;
            
            if(letterSourceContactMechanismName != null) {
                letterSourceContactMechanism = contactControl.getContactMechanismByName(letterSourceContactMechanismName);
                
                if(letterSourceContactMechanism == null) {
                    eea.addExecutionError(ExecutionErrors.UnknownLetterSourceContactMechanismName.name(), letterSourceContactMechanismName);
                }
            } else {
                ContactMechanismAliasType letterSourceContactMechanismAliasType = contactControl.getContactMechanismAliasTypeByName(letterSourceContactMechanismAliasTypeName);
                
                if(letterSourceContactMechanismAliasType != null) {
                    ContactMechanismAlias letterSourceContactMechanismAlias = contactControl.getContactMechanismAliasByAlias(letterSourceContactMechanismAliasType, alias);
                    
                    if(letterSourceContactMechanismAlias != null) {
                        letterSourceContactMechanism = letterSourceContactMechanismAlias.getContactMechanism();
                    } else {
                        eea.addExecutionError(ExecutionErrors.UnknownLetterSourceContactMechanismAlias.name(), letterSourceContactMechanismAliasTypeName, alias);
                    }
                } else {
                    eea.addExecutionError(ExecutionErrors.UnknownLetterSourceContactMechanismAliasTypeName.name(), letterSourceContactMechanismAliasTypeName);
                }
            }
            
            if(letterSourceContactMechanism != null) {
                String contactMechanismTypeName = letterSourceContactMechanism.getLastDetail().getContactMechanismType().getContactMechanismTypeName();
                
                if(contactMechanismTypeName.equals(ContactMechanismTypes.WEB_ADDRESS.name())) {
                    partyContactMechanism = contactControl.getPartyContactMechanism(companyParty, letterSourceContactMechanism);
                    
                    if(partyContactMechanism == null) {
                        eea.addExecutionError(ExecutionErrors.UnknownEmailAddressPartyContactMechanism.name());
                    }
                } else {
                    eea.addExecutionError(ExecutionErrors.InvalidContactMechanismType.name());
                }
            }
        } else {
            eea.addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return partyContactMechanism;
    }
    
}
