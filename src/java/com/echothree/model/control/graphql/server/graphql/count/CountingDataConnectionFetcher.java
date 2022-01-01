// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.graphql.server.graphql.count;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class CountingDataConnectionFetcher<T extends BaseEntityInstanceObject>
        implements CountingConnectionFetcher<T> {

    private final DataFetcher<CountingPaginatedData<T>> simplePaginatedDataDataFetcher;

    public CountingDataConnectionFetcher(DataFetcher<CountingPaginatedData<T>> simplePaginatedDataDataFetcher) {
        this.simplePaginatedDataDataFetcher = simplePaginatedDataDataFetcher;
    }

    @Override
    public CountingConnection<T> get(DataFetchingEnvironment environment) throws Exception {
        return simplePaginatedDataDataFetcher.get(environment);
    }

}
