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

package com.echothree.model.control.item.server.search;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.analysis.ItemAnalyzer;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.search.server.search.BaseSpellCheckEvaluator;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import org.apache.lucene.analysis.Analyzer;

public class ItemSpellCheckEvaluator
        extends BaseSpellCheckEvaluator {
    
    /** Creates a new instance of ItemSearchEvaluator */
    public ItemSpellCheckEvaluator(UserVisit userVisit, Language language, SearchDefaultOperator searchDefaultOperator) {
        super(userVisit, searchDefaultOperator, ComponentVendors.ECHOTHREE.name(), EntityTypes.Item.name(),
                IndexConstants.IndexType_ITEM, language, null);
        
        setField(ItemDescriptionLogic.getInstance().getIndexDefaultItemDescriptionTypeName());
    }

    @Override
    public Analyzer getAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        return new ItemAnalyzer(eea, language, entityType);
    }
    
}
