// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.view.client.web.taglib.util;

import com.echothree.util.common.form.TransferProperties;

public class TransferPropertiesHolder {

    protected TransferProperties transferProperties = new TransferProperties();
    protected Class clazz = null;

    protected TransferPropertiesHolder previousTransferPropertiesHolder;

    public TransferPropertiesHolder(TransferPropertiesHolder previousTransferPropertiesHolder) {
        this.previousTransferPropertiesHolder = previousTransferPropertiesHolder;
    }

    /**
     * Returns the transferProperties.
     * @return the transferProperties
     */
    public TransferProperties getTransferProperties() {
        return transferProperties;
    }

    /**
     * Returns the previousTransferPropertiesHolder.
     * @return the previousTransferPropertiesHolder
     */
    public TransferPropertiesHolder getPreviousTransferPropertiesHolder() {
        return previousTransferPropertiesHolder;
    }

    /**
     * Returns the clazz.
     * @return the clazz
     */
    public Class getClazz() {
        return clazz;
    }

    /**
     * Sets the clazz.
     * @param clazz the clazz to set
     */
    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

}
