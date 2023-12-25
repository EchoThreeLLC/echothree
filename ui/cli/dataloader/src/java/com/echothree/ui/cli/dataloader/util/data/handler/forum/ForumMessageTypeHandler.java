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

package com.echothree.ui.cli.dataloader.util.data.handler.forum;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.ForumService;
import com.echothree.control.user.forum.common.form.CreateForumMessageTypeDescriptionForm;
import com.echothree.control.user.forum.common.form.CreateForumMessageTypePartTypeForm;
import com.echothree.control.user.forum.common.form.ForumFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ForumMessageTypeHandler
        extends BaseHandler {
    
    ForumService forumService;
    String forumMessageTypeName;
    
    /** Creates a new instance of ForumMessageTypeHandler */
    public ForumMessageTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String forumMessageTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            forumService = ForumUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.forumMessageTypeName = forumMessageTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("forumMessageTypeDescription")) {
            CreateForumMessageTypeDescriptionForm commandForm = ForumFormFactory.getCreateForumMessageTypeDescriptionForm();

            commandForm.setForumMessageTypeName(forumMessageTypeName);
            commandForm.set(getAttrsMap(attrs));

            forumService.createForumMessageTypeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("forumMessageTypePartType")) {
            CreateForumMessageTypePartTypeForm commandForm = ForumFormFactory.getCreateForumMessageTypePartTypeForm();

            commandForm.setForumMessageTypeName(forumMessageTypeName);
            commandForm.set(getAttrsMap(attrs));

            forumService.createForumMessageTypePartType(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("forumMessageType")) {
            initialDataParser.popHandler();
        }
    }
    
}
