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

package com.echothree.control.user.test.user.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;

public class PreferredLanguageTest
        extends GraphQlTestCase {

    @Test
    public void setUserVisitPreferredLanguage()
            throws Exception {
        Map<String, Object> setUserVisitPreferredLanguage = executeUsingPost("mutation { setUserVisitPreferredLanguage(input: { languageIsoName: \"de\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(setUserVisitPreferredLanguage, "data.setUserVisitPreferredLanguage.hasErrors"));
    }
}
