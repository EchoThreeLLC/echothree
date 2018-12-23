// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.contact.common.transfer.ContactPostalAddressTransfer;
import com.echothree.util.common.string.ContactPostalAddressUtils;
import com.echothree.util.common.transfer.ListWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ContactPostalAddressTag
        extends BaseTag {

    protected ContactPostalAddressTransfer contactPostalAddress;
    protected String var;
    protected int scope;

    private void init() {
        contactPostalAddress = null;
        var = null;
        scope = PageContext.PAGE_SCOPE;
    }

    /** Creates a new instance of ContactPostalAddressTag */
    public ContactPostalAddressTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setContactPostalAddress(ContactPostalAddressTransfer contactPostalAddress) {
        this.contactPostalAddress = contactPostalAddress;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setScope(String scope) {
        this.scope = translateScope(scope);
    }

    @Override
    public int doStartTag()
            throws JspException {
        pageContext.setAttribute(var, new ListWrapper<>(ContactPostalAddressUtils.getInstance().formatContactPostalAddress(contactPostalAddress)), scope);

        return SKIP_BODY;
    }

}
