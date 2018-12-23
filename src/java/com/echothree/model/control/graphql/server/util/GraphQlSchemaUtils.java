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

package com.echothree.model.control.graphql.server.util;

import graphql.annotations.processor.GraphQLAnnotations;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

public class GraphQlSchemaUtils {
    
    private GraphQLSchema readOnlySchema;
    private GraphQLSchema schema;

    private GraphQlSchemaUtils() {
        buildSchema();
    }
    
    private static class GraphQlUtilsHolder {
        static GraphQlSchemaUtils instance = new GraphQlSchemaUtils();
    }
    
    public static GraphQlSchemaUtils getInstance() {
        return GraphQlUtilsHolder.instance;
    }
    
    private void buildSchema() {
        // graphql-java-annotations changed its default behavior in 5.3 to never
        // prettify field names, meaning that if one was named "getId" it would
        // treat it literally as being named "getId" instead of "id." Setting this
        // flag to true, introduced in 5.4,  returns to the previous behavior.
        // https://github.com/graphql-java/graphql-java-annotations/pull/182
        GraphQLAnnotations.getInstance()
                .getObjectHandler().getTypeRetriever().getGraphQLFieldRetriever().setAlwaysPrettify(true);
        
        GraphQLObjectType query = GraphQLAnnotations.object(GraphQlQueries.class);
        GraphQLObjectType mutation = GraphQLAnnotations.object(GraphQlMutations.class);

        readOnlySchema = GraphQLSchema
                .newSchema()
                .query(query)
                .build();

        schema = GraphQLSchema
                .newSchema()
                .query(query)
                .mutation(mutation)
                .build();
    }

    public GraphQLSchema getReadOnlySchema() {
        return readOnlySchema;
    }

    public GraphQLSchema getSchema() {
        return schema;
    }

}
