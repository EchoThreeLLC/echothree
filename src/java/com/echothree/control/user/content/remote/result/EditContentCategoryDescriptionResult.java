// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.content.remote.result;

import com.echothree.control.user.content.remote.edit.ContentCategoryDescriptionEdit;
import com.echothree.model.control.content.remote.transfer.ContentCatalogTransfer;
import com.echothree.model.control.content.remote.transfer.ContentCategoryDescriptionTransfer;
import com.echothree.model.control.content.remote.transfer.ContentCategoryTransfer;
import com.echothree.model.control.content.remote.transfer.ContentCollectionTransfer;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.util.remote.command.BaseEditResult;

public interface EditContentCategoryDescriptionResult
        extends BaseEditResult<ContentCategoryDescriptionEdit> {

    ContentCollectionTransfer getContentCollection();

    void setContentCollection(ContentCollectionTransfer contentCollection);

    ContentCatalogTransfer getContentCatalog();

    void setContentCatalog(ContentCatalogTransfer contentCatalog);

    ContentCategoryTransfer getContentCategory();

    void setContentCategory(ContentCategoryTransfer contentCategory);

    LanguageTransfer getLanguage();

    void setLanguage(LanguageTransfer language);

    ContentCategoryDescriptionTransfer getContentCategoryDescription();

    void setContentCategoryDescription(ContentCategoryDescriptionTransfer contentCategory);

}
