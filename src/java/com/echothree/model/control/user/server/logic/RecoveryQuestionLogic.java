// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.user.server.logic;

import com.echothree.model.control.user.common.exception.UnknownRecoveryQuestionNameException;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class RecoveryQuestionLogic
        extends BaseLogic {

    private RecoveryQuestionLogic() {
        super();
    }

    private static class RecoveryQuestionLogicHolder {
        static RecoveryQuestionLogic instance = new RecoveryQuestionLogic();
    }

    public static RecoveryQuestionLogic getInstance() {
        return RecoveryQuestionLogicHolder.instance;
    }
    
    public RecoveryQuestion getRecoveryQuestionByName(final ExecutionErrorAccumulator eea, final String recoveryQuestionName) {
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        RecoveryQuestion recoveryQuestion = userControl.getRecoveryQuestionByName(recoveryQuestionName);

        if(recoveryQuestion == null) {
            handleExecutionError(UnknownRecoveryQuestionNameException.class, eea, ExecutionErrors.UnknownRecoveryQuestionName.name(), recoveryQuestionName);
        }

        return recoveryQuestion;
    }
    
}
