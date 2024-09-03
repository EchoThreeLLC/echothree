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

package com.echothree.cucumber.authentication;

import com.echothree.control.user.authentication.common.AuthenticationService;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.cucumber.util.persona.AnonymousPersona;
import com.echothree.cucumber.util.persona.AnonymousPersonas;
import com.echothree.util.common.command.CommandResult;
import io.cucumber.java8.En;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class AnonymousSteps implements En {

    public AnonymousSteps() {
        After(() -> {
                    for(var anonymousPersona : AnonymousPersonas.getPersonaEntries()) {
                        AuthenticationService authenticationService = AuthenticationUtil.getHome();
                        var commandResult = authenticationService.logout(anonymousPersona.getValue().userVisitPK);

                        assertThat(commandResult.hasErrors()).isFalse();
                    }
                });
    }

}
