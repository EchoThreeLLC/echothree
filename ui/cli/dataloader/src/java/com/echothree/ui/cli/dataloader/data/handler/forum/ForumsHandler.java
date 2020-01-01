// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.forum.common.ForumService;
import com.echothree.control.user.forum.common.form.CreateForumForm;
import com.echothree.control.user.forum.common.form.ForumFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ForumsHandler
        extends BaseHandler {
    ForumService forumService;
    
    /** Creates a new instance of ForumsHandler */
    public ForumsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            forumService = ForumUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("forum")) {
            CreateForumForm form = ForumFormFactory.getCreateForumForm();
            String forumName = null;
            String forumTypeName = null;
            String iconName = null;
            String forumThreadSequenceName = null;
            String forumMessageSequenceName = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("forumName"))
                    forumName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("forumTypeName"))
                    forumTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("iconName"))
                    iconName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("forumThreadSequenceName"))
                    forumThreadSequenceName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("forumMessageSequenceName"))
                    forumMessageSequenceName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            form.setForumName(forumName);
            form.setForumTypeName(forumTypeName);
            form.setIconName(iconName);
            form.setForumThreadSequenceName(forumThreadSequenceName);
            form.setForumMessageSequenceName(forumMessageSequenceName);
            form.setSortOrder(sortOrder);
            
            forumService.createForum(initialDataParser.getUserVisit(), form);
            
            initialDataParser.pushHandler(new ForumHandler(initialDataParser, this, forumName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("forums")) {
            initialDataParser.popHandler();
        }
    }
    
}
