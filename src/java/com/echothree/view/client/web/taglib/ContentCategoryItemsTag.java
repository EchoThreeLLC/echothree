// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.form.GetContentCategoryItemsForm;
import com.echothree.control.user.content.common.result.GetContentCategoryItemsResult;
import com.echothree.model.data.content.common.ContentCategoryItemConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ContentCategoryItemsTag
        extends BaseTag {

    protected String contentWebAddressName;
    protected String contentCollectionName;
    protected String contentCatalogName;
    protected String contentCategoryName;
    protected String associateProgramName;
    protected String associateName;
    protected String associateContactMechanismName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String contentCategoryItemOffset;
    protected String contentCategoryItemCount;
    protected String var;
    protected String countVar;
    protected String contentCategoryVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;

    private void init() {
        contentWebAddressName = null;
        contentCollectionName = null;
        contentCatalogName = null;
        contentCategoryName = null;
        associateProgramName = null;
        associateName = null;
        associateContactMechanismName = null;
        options = null;
        transferProperties = null;
        contentCategoryItemOffset = null;
        contentCategoryItemCount = null;
        var = null;
        countVar = null;
        contentCategoryVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }

    /** Creates a new instance of ContentCategoryItemsTag */
    public ContentCategoryItemsTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setContentWebAddressName(String contentWebAddressName) {
        this.contentWebAddressName = contentWebAddressName;
    }

    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }

    public void setContentCatalogName(String contentCatalogName) {
        this.contentCatalogName = contentCatalogName;
    }

    public void setContentCategoryName(String contentCategoryName) {
        this.contentCategoryName = contentCategoryName;
    }

    public void setAssociateProgramName(String associateProgramName) {
        this.associateProgramName = associateProgramName;
    }

    public void setAssociateName(String associateName) {
        this.associateName = associateName;
    }

    public void setAssociateContactMechanismName(String associateContactMechanismName) {
        this.associateContactMechanismName = associateContactMechanismName;
    }

    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }
    
    /**
     * Sets the contentCategoryItemOffset.
     * @param contentCategoryItemOffset the contentCategoryItemOffset to set
     */
    public void setContentCategoryItemOffset(String contentCategoryItemOffset) {
        this.contentCategoryItemOffset = contentCategoryItemOffset;
    }

    /**
     * Sets the contentCategoryItemCount.
     * @param contentCategoryItemCount the contentCategoryItemCount to set
     */
    public void setContentCategoryItemCount(String contentCategoryItemCount) {
        this.contentCategoryItemCount = contentCategoryItemCount;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setContentCategoryVar(String contentCategoryVar) {
        this.contentCategoryVar = contentCategoryVar;
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
            GetContentCategoryItemsForm commandForm = ContentUtil.getHome().getGetContentCategoryItemsForm();
            Map<String, Limit> limits = new HashMap<>();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associateContactMechanismName);

            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);

            if(contentCategoryItemCount != null || contentCategoryItemOffset != null) {
                limits.put(ContentCategoryItemConstants.ENTITY_TYPE_NAME, new Limit(contentCategoryItemCount, contentCategoryItemOffset));
            }
            commandForm.setLimits(limits);

            CommandResult commandResult = ContentUtil.getHome().getContentCategoryItems(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetContentCategoryItemsResult result = (GetContentCategoryItemsResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getContentCategoryItems()), scope);

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getContentCategoryItemCount(), scope);
                }
                
                if(contentCategoryVar != null) {
                    pageContext.setAttribute(contentCategoryVar, result.getContentCategory(), scope);
                }
            }
        } catch(NamingException ne) {
            throw new JspException(ne);
        }

        return SKIP_BODY;
    }
    
}
