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

package com.echothree.control.user.party.common.form;

import com.echothree.control.user.user.common.edit.RecoveryAnswerEdit;
import com.echothree.control.user.user.common.edit.UserLoginEdit;

public interface CreateCustomerWithLoginForm
        extends CreateCustomerForm, UserLoginEdit, RecoveryAnswerEdit {
    
    String getPassword1();
    void setPassword1(String password1);
    
    String getPassword2();
    void setPassword2(String password2);
    
}
