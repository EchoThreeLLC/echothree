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

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.echothree.view.client.web.taglib;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.remote.form.GetPartyContactListForm;
import com.echothree.control.user.contactlist.remote.result.GetPartyContactListResult;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.util.remote.form.TransferProperties;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class PartyContactListTag
        extends BaseTag {
    
    protected String partyName;
    protected String contactListName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String var;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        partyName = null;
        contactListName = null;
        options = null;
        transferProperties = null;
        var = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of PartyContactListTag */
    public PartyContactListTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public void setContactListName(String contactListName) {
        this.contactListName = contactListName;
    }

    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }
    
    public void setVar(String var) {
        this.var = var;
    }
    
    public void setCommandResultVar(String commandResultVar) {
        this.commandResultVar = commandResultVar;
    }

    public void setScope(String scope) {
        this.scope = translateScope(scope);
    }
    
    public void setLogErrors(Boolean logErrors) {
        this.logErrors = logErrors;
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        try {
            GetPartyContactListForm commandForm = ContactListUtil.getHome().getGetPartyContactListForm();
            
            commandForm.setPartyName(partyName);
            commandForm.setContactListName(contactListName);
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            CommandResult commandResult = ContactListUtil.getHome().getPartyContactList(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPartyContactListResult result = (GetPartyContactListResult)executionResult.getResult();

                pageContext.setAttribute(var, result.getPartyContactList(), scope);
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
