// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.workflow.common.exception;

import com.echothree.util.common.message.Message;

public class UnknownDefaultWorkflowException
        extends BaseWorkflowException {

    /** Creates a new instance of UnknownDefaultWorkflowException */
    public UnknownDefaultWorkflowException() {
        super();
    }

    /** Creates a new instance of UnknownDefaultWorkflowException */
    public UnknownDefaultWorkflowException(String message) {
        super(message);
    }

    /** Creates a new instance of UnknownDefaultWorkflowException */
    public UnknownDefaultWorkflowException(Throwable cause) {
        super(cause);
    }

    /** Creates a new instance of UnknownDefaultWorkflowException */
    public UnknownDefaultWorkflowException(Message message) {
        super(message);
    }

}
