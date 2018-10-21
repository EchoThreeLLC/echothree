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

package com.echothree.ui.cli.dataloader.data.handler.forum;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.remote.ForumService;
import com.echothree.control.user.forum.remote.form.CreateForumTypeDescriptionForm;
import com.echothree.control.user.forum.remote.form.CreateForumTypeMessageTypeForm;
import com.echothree.control.user.forum.remote.form.ForumFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ForumTypeHandler
        extends BaseHandler {
    ForumService forumService;
    String forumTypeName;
    
    /** Creates a new instance of ForumTypeHandler */
    public ForumTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String forumTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            forumService = ForumUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.forumTypeName = forumTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("forumTypeDescription")) {
            String languageIsoName = null;
            String description = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                CreateForumTypeDescriptionForm form = ForumFormFactory.getCreateForumTypeDescriptionForm();
                
                form.setForumTypeName(forumTypeName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                forumService.createForumTypeDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("forumTypeMessageType")) {
            String forumMessageTypeName = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("forumMessageTypeName"))
                    forumMessageTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateForumTypeMessageTypeForm form = ForumFormFactory.getCreateForumTypeMessageTypeForm();
                
                form.setForumTypeName(forumTypeName);
                form.setForumMessageTypeName(forumMessageTypeName);
                form.setIsDefault(isDefault);
                form.setSortOrder(sortOrder);
                
                forumService.createForumTypeMessageType(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("forumType")) {
            initialDataParser.popHandler();
        }
    }
    
}
