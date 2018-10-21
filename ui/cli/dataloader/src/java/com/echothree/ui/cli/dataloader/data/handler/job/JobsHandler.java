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

package com.echothree.ui.cli.dataloader.data.handler.job;

import com.echothree.control.user.job.common.JobUtil;
import com.echothree.control.user.job.remote.JobService;
import com.echothree.control.user.job.remote.form.CreateJobForm;
import com.echothree.control.user.job.remote.form.JobFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class JobsHandler
        extends BaseHandler {
    JobService jobService;
    
    /** Creates a new instance of JobsHandler */
    public JobsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            jobService = JobUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("job")) {
            CreateJobForm commandForm = JobFormFactory.getCreateJobForm();

            commandForm.set(getAttrsMap(attrs));
            
            jobService.createJob(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new JobHandler(initialDataParser, this, commandForm.getJobName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("jobs")) {
            initialDataParser.popHandler();
        }
    }
    
}
