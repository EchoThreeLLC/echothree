// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data.handler.workflow;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.WorkflowService;
import com.echothree.control.user.workflow.common.form.CreateWorkflowStepDescriptionForm;
import com.echothree.control.user.workflow.common.form.WorkflowFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WorkflowStepHandler
        extends BaseHandler {
    WorkflowService workflowService;
    String workflowName;
    String workflowStepName;
    
    /** Creates a new instance of WorkflowStepHandler */
    public WorkflowStepHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String workflowName, String workflowStepName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            workflowService = WorkflowUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.workflowName = workflowName;
        this.workflowStepName = workflowStepName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("workflowStepDescription")) {
            CreateWorkflowStepDescriptionForm commandForm = WorkflowFormFactory.getCreateWorkflowStepDescriptionForm();
            
            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.set(getAttrsMap(attrs));
            
            workflowService.createWorkflowStepDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("workflowStep")) {
            initialDataParser.popHandler();
        }
    }
    
}
