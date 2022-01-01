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

package com.echothree.util.common.string;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.Collections;
import java.util.Map;

public class GraphQlUtils {
    
    private GraphQlUtils() {
        super();
    }
    
    private static class GraphQlUtilsHolder {
        static GraphQlUtils instance = new GraphQlUtils();
    }
    
    public static GraphQlUtils getInstance() {
        return GraphQlUtilsHolder.instance;
    }

    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .create();

    public String toJson(Map<String, Object> map)  {        
        return GSON.toJson(map);
    }
    
    public Map<String, Object> toMap(String json) {
        var typeToken = new TypeToken<Map<String, Object>>() {
        };
        
        Map<String, Object> map = GSON.fromJson(json, typeToken.getType());
        
        return map == null ? Collections.emptyMap() : map;
    }
    
}
