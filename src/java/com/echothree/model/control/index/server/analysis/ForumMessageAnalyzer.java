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

package com.echothree.model.control.index.server.analysis;

import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;

public class ForumMessageAnalyzer
        extends BasicAnalyzer {
    
    public ForumMessageAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType, final List<EntityAliasType> entityAliasTypes, final List<EntityAttribute> entityAttributes,
            final List<TagScope> tagScopes) {
        super(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }

    public ForumMessageAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType) {
        super(eea, language, entityType);
    }
    
    @Override
    protected Map<String, Analyzer> getEntityTypeAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        super.getEntityTypeAnalyzers(fieldAnalyzers);
        
        fieldAnalyzers.put(IndexFields.forumNames.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.forumMessageName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.forumThreadName.name(), new WhitespaceLowerCaseAnalyzer());
        
        return fieldAnalyzers;
    }
    
}
