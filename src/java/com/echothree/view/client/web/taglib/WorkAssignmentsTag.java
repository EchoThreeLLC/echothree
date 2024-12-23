// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.workrequirement.common.WorkRequirementUtil;
import com.echothree.control.user.workrequirement.common.result.GetWorkAssignmentsResult;
import com.echothree.model.data.workrequirement.common.WorkAssignmentConstants;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class WorkAssignmentsTag
        extends BaseTag {
    
    protected String employeeName;
    protected String partyName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String workAssignmentCount;
    protected String workAssignmentOffset;
    protected String var;
    protected String countVar;
    protected String partyVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        employeeName = null;
        partyName = null;
        options = null;
        transferProperties = null;
        workAssignmentCount = null;
        workAssignmentOffset = null;
        var = null;
        countVar = null;
        partyVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of WorkAssignmentsTag */
    public WorkAssignmentsTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
    
    public void setWorkAssignmentCount(String workAssignmentCount) {
        this.workAssignmentCount = workAssignmentCount;
    }
    
    public void setWorkAssignmentOffset(String workAssignmentOffset) {
        this.workAssignmentOffset = workAssignmentOffset;
    }
    
    public void setVar(String var) {
        this.var = var;
    }
    
    public void setCountVar(String countVar) {
        this.countVar = countVar;
    }

    public void setPartyVar(String partyVar) {
        this.partyVar = partyVar;
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
    public int doStartTag() throws JspException {
        try {
            var commandForm = WorkRequirementUtil.getHome().getGetWorkAssignmentsForm();
            Map<String, Limit> limits = new HashMap<>();
            
            commandForm.setEmployeeName(employeeName);
            commandForm.setPartyName(partyName);
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            if(workAssignmentCount != null || workAssignmentOffset != null) {
                limits.put(WorkAssignmentConstants.ENTITY_TYPE_NAME, new Limit(workAssignmentCount, workAssignmentOffset));
            }
            commandForm.setLimits(limits);

            var commandResult = WorkRequirementUtil.getHome().getWorkAssignments(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetWorkAssignmentsResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getWorkAssignments()), scope);

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getWorkAssignmentCount(), scope);
                }
                
                if(partyVar != null) {
                    pageContext.setAttribute(partyVar, result.getParty(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
