// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

public class RepeatHolder {

    private boolean broken = false;

    protected RepeatHolder previousRepeatHolder;

    public RepeatHolder(RepeatHolder previousRepeatTagHolder) {
        this.previousRepeatHolder = previousRepeatTagHolder;
    }

    /**
     * Returns the broken.
     * @return the broken
     */
    public boolean isBroken() {
        return broken;
    }

    /**
     * @param broken the broken to set
     */
    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    /**
     * Returns the previousRepeatHolder.
     * @return the previousRepeatHolder
     */
    public RepeatHolder getPreviousRepeatHolder() {
        return previousRepeatHolder;
    }

}
