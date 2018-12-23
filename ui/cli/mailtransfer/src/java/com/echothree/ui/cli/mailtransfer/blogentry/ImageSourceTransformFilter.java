// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.cli.mailtransfer.blogentry;

import com.echothree.ui.cli.mailtransfer.blogentry.BlogEntryTransfer.CollectedParts.CapturedMessageAttachment;
import java.util.Map;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.cyberneko.html.filters.DefaultFilter;

public class ImageSourceTransformFilter
        extends DefaultFilter {

    String cmsServlet;
    String forumMessageName;
    Map<String, CapturedMessageAttachment> capturedMessageAttachmentsByCid;

    ImageSourceTransformFilter(String cmsServlet, String forumMessageName, Map<String, CapturedMessageAttachment> capturedMessageAttachmentsByCid) {
        super();

        this.cmsServlet = cmsServlet;
        this.forumMessageName = forumMessageName;
        this.capturedMessageAttachmentsByCid = capturedMessageAttachmentsByCid;
    }

    @Override
    public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs)
            throws XNIException {
        String eName = element.rawname.toLowerCase();

        if(eName.equals("img")) {
            int attributeCount = attributes.getLength();

            for(int i = 0; i < attributeCount; i++) {
                String aname = attributes.getQName(i).toLowerCase();

                if(aname.toLowerCase().equals("src")) {
                    String imgSrc = attributes.getValue(i);

                    if(imgSrc.startsWith("cid:") && imgSrc.length() > 4) {
                        String contentId = "<" + imgSrc.substring(4) + ">";
                        CapturedMessageAttachment capturedMessageAttachment = capturedMessageAttachmentsByCid.get(contentId);
                        String transformedUrl = "/" + cmsServlet + "/action/ForumMessageAttachment?ForumMessageName=" + forumMessageName
                                + "&ForumMessageAttachmentSequence=" + capturedMessageAttachment.forumMessageAttachmentSequence;

                        attributes.setValue(i, transformedUrl);
                    }
                }
            }
        }

        super.emptyElement(element, attributes, augs);
    }

}
