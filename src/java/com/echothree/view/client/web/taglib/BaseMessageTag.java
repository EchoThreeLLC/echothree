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

package com.echothree.view.client.web.taglib;

import com.echothree.util.remote.message.Message;
import org.apache.struts.Globals;

public class BaseMessageTag
        extends BaseTag {
    
    /**
     * The name of the scripting variable to be exposed.
     */
    protected String id = null;
    
    /**
     * The request attribute key for our error messages (if any).
     */
    protected String commandResultVar = TagConstants.CommandResultName;
    
    /**
     * The servlet context attribute key for our resources.
     */
    protected String bundle = null;
    
    /**
     * The session attribute key for our locale.
     */
    protected String locale = Globals.LOCALE_KEY;
    
    @Override
    public String getId() {
        return (this.id);
    }
    
    @Override
    public void setId(String id) {
        this.id = id;
    }
    
    public String getCommandResultVar() {
        return commandResultVar;
    }
    
    public void setCommandResultVar(String commandResultVar) {
        this.commandResultVar = commandResultVar;
    }
    
    public String getBundle() {
        return (this.bundle);
    }
    
    public void setBundle(String bundle) {
        this.bundle = bundle;
    }
    
    public String getLocale() {
        return (this.locale);
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }
    
    public void setMessageAttribute(Message message) {
        String value = message.getMessage();
        
        if(value == null) {
            pageContext.removeAttribute(id);
        } else {
            pageContext.setAttribute(id, value);
        }
    }
    
    @Override
    public void release() {
        super.release();
        
        id = null;
        commandResultVar = TagConstants.CommandResultName;
        bundle = null;
        locale = Globals.LOCALE_KEY;
    }
    
}
