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

package com.echothree.ui.cli.dataloader.util.data.handler.cancellationpolicy;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyService;
import com.echothree.control.user.cancellationpolicy.common.form.CancellationPolicyFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CancellationKindHandler
        extends BaseHandler {
    CancellationPolicyService cancellationPolicyService;
    String cancellationKindName;
    
    /** Creates a new instance of CancellationKindHandler */
    public CancellationKindHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String cancellationKindName) {
        super(initialDataParser, parentHandler);
        
        try {
            cancellationPolicyService = CancellationPolicyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.cancellationKindName = cancellationKindName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("cancellationKindDescription")) {
            String languageIsoName = null;
            String description = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var commandForm = CancellationPolicyFormFactory.getCreateCancellationKindDescriptionForm();
                
                commandForm.setCancellationKindName(cancellationKindName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                checkCommandResult(cancellationPolicyService.createCancellationKindDescription(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }else if(localName.equals("cancellationPolicy")) {
            String cancellationPolicyName = null;
            String isDefault = null;
            String sortOrder = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("cancellationPolicyName"))
                    cancellationPolicyName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var commandForm = CancellationPolicyFormFactory.getCreateCancellationPolicyForm();
                
                commandForm.setCancellationKindName(cancellationKindName);
                commandForm.setCancellationPolicyName(cancellationPolicyName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                checkCommandResult(cancellationPolicyService.createCancellationPolicy(initialDataParser.getUserVisit(), commandForm));
                
                initialDataParser.pushHandler(new CancellationPolicyHandler(initialDataParser, this, cancellationKindName, cancellationPolicyName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("cancellationReason")) {
            String cancellationReasonName = null;
            String isDefault = null;
            String sortOrder = null;
            String description = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("cancellationReasonName"))
                    cancellationReasonName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var commandForm = CancellationPolicyFormFactory.getCreateCancellationReasonForm();
                
                commandForm.setCancellationKindName(cancellationKindName);
                commandForm.setCancellationReasonName(cancellationReasonName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                commandForm.setDescription(description);
                
                checkCommandResult(cancellationPolicyService.createCancellationReason(initialDataParser.getUserVisit(), commandForm));
                
                initialDataParser.pushHandler(new CancellationReasonHandler(initialDataParser, this, cancellationKindName, cancellationReasonName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("cancellationType")) {
            String cancellationTypeName = null;
            String cancellationSequenceName = null;
            String isDefault = null;
            String sortOrder = null;
            String description = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("cancellationTypeName"))
                    cancellationTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("cancellationSequenceName"))
                    cancellationSequenceName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var commandForm = CancellationPolicyFormFactory.getCreateCancellationTypeForm();
                
                commandForm.setCancellationKindName(cancellationKindName);
                commandForm.setCancellationTypeName(cancellationTypeName);
                commandForm.setCancellationSequenceName(cancellationSequenceName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                commandForm.setDescription(description);
                
                checkCommandResult(cancellationPolicyService.createCancellationType(initialDataParser.getUserVisit(), commandForm));
                
                initialDataParser.pushHandler(new CancellationTypeHandler(initialDataParser, this, cancellationKindName, cancellationTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("cancellationKind")) {
            initialDataParser.popHandler();
        }
    }
    
}
