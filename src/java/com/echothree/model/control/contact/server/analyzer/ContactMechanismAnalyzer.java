// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.contact.server.analyzer;

import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.index.server.analyzer.WhitespaceLowerCaseAnalyzer;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;

public class ContactMechanismAnalyzer
        extends BasicAnalyzer {
    
    public ContactMechanismAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType, final List<EntityAliasType> entityAliasTypes, final List<EntityAttribute> entityAttributes,
            final List<TagScope> tagScopes) {
        super(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }

    public ContactMechanismAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType) {
        super(eea, language, entityType);
    }
    
    @Override
    protected Map<String, Analyzer> getEntityTypeAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        super.getEntityTypeAnalyzers(fieldAnalyzers);
        
        fieldAnalyzers.put(IndexFields.contactMechanismName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.contactMechanismTypeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.allowSolicitation.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.contactMechanismPurposeNames.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.emailAddress.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.inet4Address.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.url.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.countryGeoCodeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.areaCode.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.telephoneNumber.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.telephoneExtension.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.cityGeoCodeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.countyGeoCodeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.stateGeoCodeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.postalCodeGeoCodeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.isCommercial.name(), new WhitespaceLowerCaseAnalyzer());
        
        return fieldAnalyzers;
    }
    
}
