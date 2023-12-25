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
import com.echothree.control.user.user.common.form.SetUserVisitPreferredTimeZoneForm;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;

public class PreferredTimeZoneFormatSteps implements En {

    public PreferredTimeZoneFormatSteps() {
        When("^the user sets their preferred time zone to \"([^\"]*)\"$",
                (String javaTimeZoneName) -> {
                    UserService userService = UserUtil.getHome();
                    SetUserVisitPreferredTimeZoneForm userVisitPreferredTimeZoneForm = userService.getSetUserVisitPreferredTimeZoneForm();
                    var persona = CurrentPersona.persona;

                    userVisitPreferredTimeZoneForm.setJavaTimeZoneName(javaTimeZoneName);

                    LastCommandResult.commandResult = userService.setUserVisitPreferredTimeZone(persona.userVisitPK,
                            userVisitPreferredTimeZoneForm);
                });
    }

}
