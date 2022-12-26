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

package com.echothree.control.user.test.queue.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import com.echothree.model.control.queue.common.QueueTypes;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.Test;

public class QueueTypeTest
        extends GraphQlTestCase {

    @Test
    public void queueTypeByQueueTypeNameNoAuth()
            throws Exception {
        var queueTypeBody = executeUsingPost("""
                query {
                    queueType(queueTypeName: "%s") {
                        queueTypeName
                        isDefault
                        sortOrder
                        description
                        id
                        unformattedOldestQueuedEntityTime
                        oldestQueuedEntityTime
                        unformattedLatestQueuedEntityTime
                        latestQueuedEntityTime
                        queuedEntityCount
                    }
                }
                """.formatted(QueueTypes.INDEXING.name()));
        
        assertThat(getMap(queueTypeBody, "data.queueType")).isNull();
    }

    @Test
    public void queueTypeByQueueTypeName()
            throws Exception {
        var loginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: {
                        username: "test e",
                        password: "password",
                        companyName: "TEST_COMPANY",
                        clientMutationId: "1"
                    })
                    {
                        hasErrors
                    }
                }
                """);
        
        assertThat(getBoolean(loginBody, "data.employeeLogin.hasErrors")).isFalse();

        var queueTypeBody = executeUsingPost("""
                query {
                    queueType(queueTypeName: "%s") {
                        queueTypeName
                        isDefault
                        sortOrder
                        description
                        id
                        unformattedOldestQueuedEntityTime
                        oldestQueuedEntityTime
                        unformattedLatestQueuedEntityTime
                        latestQueuedEntityTime
                        queuedEntityCount
                    }
                }
                """.formatted(QueueTypes.INDEXING.name()));

        var queueType = getMap(queueTypeBody, "data.queueType");

        assertThat(queueType).isNotNull();
    }

    @Test
    public void queueTypesNoAuth()
            throws Exception {
        var queueTypesBody = executeUsingPost("""
                query {
                    queueTypes {
                        queueTypeName
                        isDefault
                        sortOrder
                        description
                        id
                        unformattedOldestQueuedEntityTime
                        oldestQueuedEntityTime
                        unformattedLatestQueuedEntityTime
                        latestQueuedEntityTime
                        queuedEntityCount
                    }
                }
                """);
        
        var queueTypes = getList(queueTypesBody, "data.queueTypes");
        assertThat(queueTypes.size() == 0).isTrue();
    }

    @Test
    public void queueTypes()
            throws Exception {
        var loginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: {
                        username: "test e",
                        password: "password",
                        companyName: "TEST_COMPANY",
                        clientMutationId: "1"
                    })
                    {
                        hasErrors
                    }
                }
                """);
        
        assertThat(getBoolean(loginBody, "data.employeeLogin.hasErrors")).isFalse();

        var queueTypesBody = executeUsingPost("""
                query {
                    queueTypes {
                        queueTypeName
                        isDefault
                        sortOrder
                        description
                        id
                        unformattedOldestQueuedEntityTime
                        oldestQueuedEntityTime
                        unformattedLatestQueuedEntityTime
                        latestQueuedEntityTime
                        queuedEntityCount
                    }
                }
                """);
        
        var queueTypes = getList(queueTypesBody, "data.queueTypes");
        assertThat(queueTypes.size() > 0).isTrue();

        boolean foundIndexing = false;
        for(var queueType : queueTypes) {
            if(getString(queueType, "queueTypeName").equals(QueueTypes.INDEXING.name())) {
                foundIndexing = true;
                break;
            }
        }

        assertThat(foundIndexing).isTrue();
    }

}
