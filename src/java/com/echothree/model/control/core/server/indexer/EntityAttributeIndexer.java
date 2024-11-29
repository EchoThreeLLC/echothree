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

package com.echothree.model.control.core.server.indexer;

import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFieldVariations;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.core.server.analyzer.EntityAttributeAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class EntityAttributeIndexer
        extends BaseIndexer<EntityAttribute> {
    
    /** Creates a new instance of EntityAttributeIndexer */
    public EntityAttributeIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new EntityAttributeAnalyzer(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }
    
    @Override
    protected EntityAttribute getEntity(final EntityInstance entityInstance) {
        return coreControl.getEntityAttributeByEntityInstance(entityInstance);
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final EntityAttribute entityAttribute) {
        var entityAttributeDetail = entityAttribute.getLastDetail();
        var entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
        var description = coreControl.getBestEntityAttributeDescription(entityAttribute, language);

        var document = newDocumentWithEntityInstanceFields(entityInstance, entityAttribute.getPrimaryKey());

        document.add(new Field(IndexFields.componentVendorName.name(),
                entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexFields.componentVendorName.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                new BytesRef(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName())));
        document.add(new Field(IndexFields.entityTypeName.name(),
                entityTypeDetail.getEntityTypeName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexFields.entityTypeName.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                new BytesRef(entityTypeDetail.getEntityTypeName())));
        document.add(new Field(IndexFields.entityAttributeName.name(),
                entityAttributeDetail.getEntityAttribute().getLastDetail().getEntityAttributeName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexFields.entityAttributeName.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                new BytesRef(entityAttributeDetail.getEntityAttribute().getLastDetail().getEntityAttributeName())));
        document.add(new Field(IndexFields.entityAttributeTypeName.name(),
                entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new Field(IndexFields.trackRevisions.name(), entityAttributeDetail.getTrackRevisions().toString(), FieldTypes.NOT_STORED_NOT_TOKENIZED));

        if(description != null) {
            document.add(new Field(IndexFields.description.name(), description, FieldTypes.NOT_STORED_TOKENIZED));
            document.add(new SortedDocValuesField(IndexFields.description.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                    new BytesRef(description)));
        }

        return document;
    }

}
