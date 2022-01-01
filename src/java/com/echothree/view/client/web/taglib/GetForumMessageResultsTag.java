// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.GetForumMessageResultsForm;
import com.echothree.control.user.search.common.result.GetForumMessageResultsResult;
import com.echothree.model.data.forum.common.ForumMessageConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetForumMessageResultsTag
        extends BaseTag {
    
    protected String searchTypeName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String forumMessageCount;
    protected String forumMessageOffset;
    protected String forumMessageResultCount;
    protected String forumMessageResultOffset;
    protected String var;
    protected String countVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        searchTypeName = null;
        options = null;
        transferProperties = null;
        forumMessageCount = null;
        forumMessageOffset = null;
        forumMessageResultCount = null;
        forumMessageResultOffset = null;
        var = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of GetForumMessageResultsTag */
    public GetForumMessageResultsTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setSearchTypeName(String searchTypeName) {
        this.searchTypeName = searchTypeName;
    }
    
    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }
    
    public void setForumMessageCount(String forumMessageCount) {
        this.forumMessageCount = forumMessageCount;
    }
    
    public void setForumMessageOffset(String forumMessageOffset) {
        this.forumMessageOffset = forumMessageOffset;
    }
    
    public void setForumMessageResultCount(String forumMessageResultCount) {
        this.forumMessageResultCount = forumMessageResultCount;
    }
    
    public void setForumMessageResultOffset(String forumMessageResultOffset) {
        this.forumMessageResultOffset = forumMessageResultOffset;
    }
    
    public void setVar(String var) {
        this.var = var;
    }
    
    public void setCountVar(String countVar) {
        this.countVar = countVar;
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
            GetForumMessageResultsForm commandForm = SearchUtil.getHome().getGetForumMessageResultsForm();
            Map<String, Limit> limits = new HashMap<>();
            
            commandForm.setSearchTypeName(searchTypeName);
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            if(forumMessageCount != null || forumMessageOffset != null) {
                limits.put(ForumMessageConstants.ENTITY_TYPE_NAME, new Limit(forumMessageCount, forumMessageOffset));
            }
            if(forumMessageResultCount != null || forumMessageResultOffset != null) {
                limits.put(SearchResultConstants.ENTITY_TYPE_NAME, new Limit(forumMessageResultCount, forumMessageResultOffset));
            }
            commandForm.setLimits(limits);
            
            CommandResult commandResult = SearchUtil.getHome().getForumMessageResults(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetForumMessageResultsResult result = (GetForumMessageResultsResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getForumMessageResults()), scope);

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getForumMessageResultCount(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
