// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
// Copyright 1999-2004 The Apache Software Foundation.
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

package com.echothree.view.client.web.taglib;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetDepartmentResult;
import com.echothree.util.common.form.TransferProperties;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class DepartmentTag
        extends BaseTag {
    
    protected String companyName;
    protected String divisionName;
    protected String departmentName;
    protected String partyName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String var;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        companyName = null;
        divisionName = null;
        departmentName = null;
        partyName = null;
        options = null;
        transferProperties = null;
        var = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of DepartmentTag */
    public DepartmentTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
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
            var commandForm = PartyUtil.getHome().getGetDepartmentForm();
            
            commandForm.setCompanyName(companyName);
            commandForm.setDivisionName(divisionName);
            commandForm.setDepartmentName(departmentName);
            commandForm.setPartyName(partyName);
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);

            var commandResult = PartyUtil.getHome().getDepartment(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetDepartmentResult)executionResult.getResult();

                pageContext.setAttribute(var, result.getDepartment(), scope);
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
