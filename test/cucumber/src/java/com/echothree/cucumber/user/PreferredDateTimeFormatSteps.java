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

package com.echothree.cucumber.user;

import com.echothree.control.user.user.common.UserService;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.form.SetUserVisitPreferredDateTimeFormatForm;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;

public class PreferredDateTimeFormatSteps implements En {

    public PreferredDateTimeFormatSteps() {
        When("^the user sets their preferred date time format to \"([^\"]*)\"$",
                (String dateTimeFormatName) -> {
                    UserService userService = UserUtil.getHome();
                    SetUserVisitPreferredDateTimeFormatForm userVisitPreferredDateTimeFormatForm = userService.getSetUserVisitPreferredDateTimeFormatForm();
                    var persona = CurrentPersona.persona;

                    userVisitPreferredDateTimeFormatForm.setDateTimeFormatName(dateTimeFormatName);

                    LastCommandResult.commandResult = userService.setUserVisitPreferredDateTimeFormat(persona.userVisitPK,
                            userVisitPreferredDateTimeFormatForm);
                });
    }

}
