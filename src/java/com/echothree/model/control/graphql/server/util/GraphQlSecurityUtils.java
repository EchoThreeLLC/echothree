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

package com.echothree.model.control.graphql.server.util;

import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.server.control.GraphQlSecurityCommand;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class GraphQlSecurityUtils {

    private static class GraphQlSecurityUtilsHolder {
        static GraphQlSecurityUtils instance = new GraphQlSecurityUtils();
    }
    
    public static GraphQlSecurityUtils getInstance() {
        return GraphQlSecurityUtilsHolder.instance;
    }
    
    private Constructor<?> findConstructor(final Class<? extends GraphQlSecurityCommand> command) {
        Constructor<?> foundCtor = null;
        var allConstructors = command.getDeclaredConstructors();

        // Search all available Constructors for one that has a UserVisitPK as the
        // first parameter, and a type assignable to BaseForm as the second parameter.
        for(var ctor : allConstructors) {
            var pType = ctor.getParameterTypes();

            if(pType.length == 2) {
                if(pType[0] == UserVisitPK.class && BaseForm.class.isAssignableFrom(pType[1])) {
                    foundCtor = ctor;
                    break;
                }
            }
        }

        if(foundCtor == null) {
            throw new RuntimeException("command does not implement the required Constructor");
        }

        return foundCtor;
    }

    public boolean hasAccess(final GraphQlExecutionContext context, final Class<? extends GraphQlSecurityCommand> command, final BaseForm form) {
        boolean hasAccess;

        try {
            var ctor = findConstructor(command); // Search for the Constructor that's required here
            var commandInstance = ctor.newInstance(context.getUserVisitPK(), form);
            var graphQlSecurityCommand = (GraphQlSecurityCommand) commandInstance;

            // Execute the instantiated command's security check function for the current user.
            graphQlSecurityCommand.security();

            // User will have access to this GraphQL field as long as there are no security
            // messages (errors) generated by the command.
            hasAccess = !graphQlSecurityCommand.hasSecurityMessages();
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return hasAccess;
    }
    
}
