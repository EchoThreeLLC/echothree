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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("mutation result object")
@GraphQLName("MutationResult")
public class MutationResultObject {
    
    private CommandResultObject commandResultObject; // Always Present
    
    public void setCommandResult(CommandResult commandResult) {
        this.commandResultObject = new CommandResultObject(commandResult);
    }

    @GraphQLField
    @GraphQLDescription("command result")
    @GraphQLNonNull
    public CommandResultObject getCommandResult() {
        return commandResultObject;
    }

}
