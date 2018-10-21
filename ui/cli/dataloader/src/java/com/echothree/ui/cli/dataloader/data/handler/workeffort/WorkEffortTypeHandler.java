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

package com.echothree.ui.cli.dataloader.data.handler.workeffort;

import com.echothree.control.user.workeffort.common.WorkEffortUtil;
import com.echothree.control.user.workeffort.remote.WorkEffortService;
import com.echothree.control.user.workeffort.remote.form.CreateWorkEffortScopeForm;
import com.echothree.control.user.workeffort.remote.form.CreateWorkEffortTypeDescriptionForm;
import com.echothree.control.user.workeffort.remote.form.WorkEffortFormFactory;
import com.echothree.control.user.workrequirement.common.WorkRequirementUtil;
import com.echothree.control.user.workrequirement.remote.WorkRequirementService;
import com.echothree.control.user.workrequirement.remote.form.CreateWorkRequirementTypeForm;
import com.echothree.control.user.workrequirement.remote.form.WorkRequirementFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.data.handler.workrequirement.WorkRequirementTypeHandler;
import java.util.Map;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WorkEffortTypeHandler
        extends BaseHandler {
    WorkEffortService workEffortService;
    WorkRequirementService workRequirementService;
    String workEffortTypeName;
    
    /** Creates a new instance of WorkEffortTypeHandler */
    public WorkEffortTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String workEffortTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            workEffortService = WorkEffortUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        try {
            workRequirementService = WorkRequirementUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        this.workEffortTypeName = workEffortTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("workEffortTypeDescription")) {
            try {
                CreateWorkEffortTypeDescriptionForm commandForm = WorkEffortFormFactory.getCreateWorkEffortTypeDescriptionForm();
                
                commandForm.setWorkEffortTypeName(workEffortTypeName);
                commandForm.set(getAttrsMap(attrs));
                
                workEffortService.createWorkEffortTypeDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("workEffortScope")) {
            try {
                CreateWorkEffortScopeForm commandForm = WorkEffortFormFactory.getCreateWorkEffortScopeForm();
                Map<String, Object> attrsMap = getAttrsMap(attrs);
                
                commandForm.setWorkEffortTypeName(workEffortTypeName);
                commandForm.set(attrsMap);
                
                workEffortService.createWorkEffortScope(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new WorkEffortScopeHandler(initialDataParser, this, workEffortTypeName,
                        (String)attrsMap.get("WorkEffortScopeName")));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("workRequirementType")) {
            try {
                CreateWorkRequirementTypeForm commandForm = WorkRequirementFormFactory.getCreateWorkRequirementTypeForm();
                Map<String, Object> attrsMap = getAttrsMap(attrs);
                
                commandForm.setWorkEffortTypeName(workEffortTypeName);
                commandForm.set(attrsMap);
                
                workRequirementService.createWorkRequirementType(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new WorkRequirementTypeHandler(initialDataParser, this, workEffortTypeName,
                        (String)attrsMap.get("WorkRequirementTypeName")));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("workEffortType")) {
            initialDataParser.popHandler();
        }
    }
    
}
