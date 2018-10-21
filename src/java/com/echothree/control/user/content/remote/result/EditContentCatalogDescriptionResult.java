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

import com.echothree.control.user.content.remote.edit.ContentCatalogDescriptionEdit;
import com.echothree.model.control.content.remote.transfer.ContentCatalogDescriptionTransfer;
import com.echothree.model.control.content.remote.transfer.ContentCatalogTransfer;
import com.echothree.model.control.content.remote.transfer.ContentCollectionTransfer;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.util.remote.command.BaseEditResult;

public interface EditContentCatalogDescriptionResult
        extends BaseEditResult<ContentCatalogDescriptionEdit> {

    ContentCollectionTransfer getContentCollection();

    void setContentCollection(ContentCollectionTransfer contentCollection);

    ContentCatalogTransfer getContentCatalog();

    void setContentCatalog(ContentCatalogTransfer contentCatalog);

    LanguageTransfer getLanguage();

    void setLanguage(LanguageTransfer language);

    ContentCatalogDescriptionTransfer getContentCatalogDescription();

    void setContentCatalogDescription(ContentCatalogDescriptionTransfer contentCatalog);

}
