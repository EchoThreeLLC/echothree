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

package com.echothree.model.control.batch.common.exception;

import com.echothree.util.common.message.Message;

public class UnknownBatchAliasTypeNameException
        extends BaseBatchException {
    
    /** Creates a new instance of UnknownBatchAliasTypeNameException */
    public UnknownBatchAliasTypeNameException() {
        super();
    }
    
    /** Creates a new instance of UnknownBatchAliasTypeNameException */
    public UnknownBatchAliasTypeNameException(String message) {
        super(message);
    }
    
    /** Creates a new instance of UnknownBatchAliasTypeNameException */
    public UnknownBatchAliasTypeNameException(Throwable cause) {
        super(cause);
    }
    
    /** Creates a new instance of UnknownBatchAliasTypeNameException */
    public UnknownBatchAliasTypeNameException(Message message) {
        super(message);
    }

}
