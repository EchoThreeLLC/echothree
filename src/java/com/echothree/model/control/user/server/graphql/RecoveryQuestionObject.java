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

package com.echothree.model.control.user.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.RecoveryQuestionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("recovery question object")
@GraphQLName("RecoveryQuestion")
public class RecoveryQuestionObject
        extends BaseEntityInstanceObject {
    
    private final RecoveryQuestion recoveryQuestion; // Always Present
    
    public RecoveryQuestionObject(RecoveryQuestion recoveryQuestion) {
        super(recoveryQuestion.getPrimaryKey());
        
        this.recoveryQuestion = recoveryQuestion;
    }

    private RecoveryQuestionDetail recoveryQuestionDetail; // Optional, use getRecoveryQuestionDetail()
    
    private RecoveryQuestionDetail getRecoveryQuestionDetail() {
        if(recoveryQuestionDetail == null) {
            recoveryQuestionDetail = recoveryQuestion.getLastDetail();
        }
        
        return recoveryQuestionDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("recovery question name")
    @GraphQLNonNull
    public String getRecoveryQuestionName() {
        return getRecoveryQuestionDetail().getRecoveryQuestionName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getRecoveryQuestionDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getRecoveryQuestionDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return userControl.getBestRecoveryQuestionDescription(recoveryQuestion, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
