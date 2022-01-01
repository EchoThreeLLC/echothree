// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.security.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.security.server.entity.SecurityRoleGroupDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("security role group object")
@GraphQLName("SecurityRoleGroup")
public class SecurityRoleGroupObject
        extends BaseEntityInstanceObject {
    
    private final SecurityRoleGroup securityRoleGroup; // Always Present
    
    public SecurityRoleGroupObject(SecurityRoleGroup securityRoleGroup) {
        super(securityRoleGroup.getPrimaryKey());
        
        this.securityRoleGroup = securityRoleGroup;
    }

    private SecurityRoleGroupDetail securityRoleGroupDetail; // Optional, use getSecurityRoleGroupDetail()
    
    private SecurityRoleGroupDetail getSecurityRoleGroupDetail() {
        if(securityRoleGroupDetail == null) {
            securityRoleGroupDetail = securityRoleGroup.getLastDetail();
        }
        
        return securityRoleGroupDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("security role group name")
    @GraphQLNonNull
    public String getSecurityRoleGroupName() {
        return getSecurityRoleGroupDetail().getSecurityRoleGroupName();
    }

    @GraphQLField
    @GraphQLDescription("parent item category")
    public SecurityRoleGroupObject getParentSecurityRoleGroup() {
        SecurityRoleGroup parentSecurityRoleGroup = getSecurityRoleGroupDetail().getParentSecurityRoleGroup();

        return parentSecurityRoleGroup == null ? null : new SecurityRoleGroupObject(parentSecurityRoleGroup);
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSecurityRoleGroupDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSecurityRoleGroupDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return securityControl.getBestSecurityRoleGroupDescription(securityRoleGroup, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

}
