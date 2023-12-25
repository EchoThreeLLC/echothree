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

package com.echothree.model.control.communication.server.transfer;

import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class CommunicationTransferCaches
        extends BaseTransferCaches {
    
    protected CommunicationControl communicationControl;
    
    protected CommunicationEventRoleTypeTransferCache communicationEventRoleTypeTransferCache;
    protected CommunicationEventTypeTransferCache communicationEventTypeTransferCache;
    protected CommunicationEventPurposeTransferCache communicationEventPurposeTransferCache;
    protected CommunicationEventPurposeDescriptionTransferCache communicationEventPurposeDescriptionTransferCache;
    protected CommunicationEventTransferCache communicationEventTransferCache;
    protected CommunicationEventRoleTransferCache communicationEventRoleTransferCache;
    protected CommunicationSourceTypeTransferCache communicationSourceTypeTransferCache;
    protected CommunicationSourceTransferCache communicationSourceTransferCache;
    protected CommunicationSourceDescriptionTransferCache communicationSourceDescriptionTransferCache;
    protected CommunicationEmailSourceTransferCache communicationEmailSourceTransferCache;
    
    /** Creates a new instance of CommunicationTransferCaches */
    public CommunicationTransferCaches(UserVisit userVisit, CommunicationControl communicationControl) {
        super(userVisit);
        
        this.communicationControl = communicationControl;
    }
    
    public CommunicationEventRoleTypeTransferCache getCommunicationEventRoleTypeTransferCache() {
        if(communicationEventRoleTypeTransferCache == null)
            communicationEventRoleTypeTransferCache = new CommunicationEventRoleTypeTransferCache(userVisit, communicationControl);
        
        return communicationEventRoleTypeTransferCache;
    }
    
    public CommunicationEventTypeTransferCache getCommunicationEventTypeTransferCache() {
        if(communicationEventTypeTransferCache == null)
            communicationEventTypeTransferCache = new CommunicationEventTypeTransferCache(userVisit, communicationControl);
        
        return communicationEventTypeTransferCache;
    }
    
    public CommunicationEventPurposeTransferCache getCommunicationEventPurposeTransferCache() {
        if(communicationEventPurposeTransferCache == null)
            communicationEventPurposeTransferCache = new CommunicationEventPurposeTransferCache(userVisit, communicationControl);
        
        return communicationEventPurposeTransferCache;
    }
    
    public CommunicationEventPurposeDescriptionTransferCache getCommunicationEventPurposeDescriptionTransferCache() {
        if(communicationEventPurposeDescriptionTransferCache == null)
            communicationEventPurposeDescriptionTransferCache = new CommunicationEventPurposeDescriptionTransferCache(userVisit, communicationControl);
        
        return communicationEventPurposeDescriptionTransferCache;
    }
    
    public CommunicationEventTransferCache getCommunicationEventTransferCache() {
        if(communicationEventTransferCache == null)
            communicationEventTransferCache = new CommunicationEventTransferCache(userVisit, communicationControl);
        
        return communicationEventTransferCache;
    }
    
    public CommunicationEventRoleTransferCache getCommunicationEventRoleTransferCache() {
        if(communicationEventRoleTransferCache == null)
            communicationEventRoleTransferCache = new CommunicationEventRoleTransferCache(userVisit, communicationControl);
        
        return communicationEventRoleTransferCache;
    }
    
    public CommunicationSourceTypeTransferCache getCommunicationSourceTypeTransferCache() {
        if(communicationSourceTypeTransferCache == null)
            communicationSourceTypeTransferCache = new CommunicationSourceTypeTransferCache(userVisit, communicationControl);
        
        return communicationSourceTypeTransferCache;
    }
    
    public CommunicationSourceTransferCache getCommunicationSourceTransferCache() {
        if(communicationSourceTransferCache == null)
            communicationSourceTransferCache = new CommunicationSourceTransferCache(userVisit, communicationControl);
        
        return communicationSourceTransferCache;
    }
    
    public CommunicationSourceDescriptionTransferCache getCommunicationSourceDescriptionTransferCache() {
        if(communicationSourceDescriptionTransferCache == null)
            communicationSourceDescriptionTransferCache = new CommunicationSourceDescriptionTransferCache(userVisit, communicationControl);
        
        return communicationSourceDescriptionTransferCache;
    }
    
    public CommunicationEmailSourceTransferCache getCommunicationEmailSourceTransferCache() {
        if(communicationEmailSourceTransferCache == null)
            communicationEmailSourceTransferCache = new CommunicationEmailSourceTransferCache(userVisit, communicationControl);
        
        return communicationEmailSourceTransferCache;
    }
    
}
