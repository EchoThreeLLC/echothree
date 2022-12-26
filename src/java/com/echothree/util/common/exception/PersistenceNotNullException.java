// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.util.common.exception;

import com.echothree.util.common.message.Message;

public class PersistenceNotNullException
        extends PersistenceException {
    
    /** Creates a new instance of PersistenceNotNullException */
    public PersistenceNotNullException() {
        super();
    }
    
    /** Creates a new instance of PersistenceNotNullException */
    public PersistenceNotNullException(String message) {
        super(message);
    }
    
    /** Creates a new instance of PersistenceNotNullException */
    public PersistenceNotNullException(Throwable cause) {
        super(cause);
    }
    
    /** Creates a new instance of PersistenceNotNullException */
    public PersistenceNotNullException(Message message) {
        super(message);
    }

}
