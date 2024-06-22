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

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.ClearContentCategoryResultsForm;
import com.echothree.util.common.command.CommandResult;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ClearContentCategoryResultsTag
        extends BaseTag {
    
    protected String searchTypeName;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        searchTypeName = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of ClearContentCategoryResultsTag */
    public ClearContentCategoryResultsTag() {
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
            ClearContentCategoryResultsForm commandForm = SearchUtil.getHome().getClearContentCategoryResultsForm();

            commandForm.setSearchTypeName(searchTypeName);
            
            CommandResult commandResult = SearchUtil.getHome().clearContentCategoryResults(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
