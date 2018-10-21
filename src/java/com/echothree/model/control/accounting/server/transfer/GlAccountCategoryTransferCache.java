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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.remote.transfer.GlAccountCategoryTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountCategoryDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GlAccountCategoryTransferCache
        extends BaseAccountingTransferCache<GlAccountCategory, GlAccountCategoryTransfer> {

    /** Creates a new instance of GlAccountCategoryTransferCache */
    public GlAccountCategoryTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
        
        setIncludeEntityInstance(true);
    }

    @Override
    public GlAccountCategoryTransfer getTransfer(GlAccountCategory glAccountCategory) {
        GlAccountCategoryTransfer glAccountCategoryTransfer = get(glAccountCategory);

        if(glAccountCategoryTransfer == null) {
            GlAccountCategoryDetail glAccountCategoryDetail = glAccountCategory.getLastDetail();
            String glAccountCategoryName = glAccountCategoryDetail.getGlAccountCategoryName();
            GlAccountCategory parentGlAccountCategory = glAccountCategoryDetail.getParentGlAccountCategory();
            GlAccountCategoryTransfer parentGlAccountCategoryTransfer = parentGlAccountCategory == null ? null : getTransfer(parentGlAccountCategory);
            Boolean isDefault = glAccountCategoryDetail.getIsDefault();
            Integer sortOrder = glAccountCategoryDetail.getSortOrder();
            String description = accountingControl.getBestGlAccountCategoryDescription(glAccountCategory, getLanguage());

            glAccountCategoryTransfer = new GlAccountCategoryTransfer(glAccountCategoryName, parentGlAccountCategoryTransfer, isDefault, sortOrder, description);
            put(glAccountCategory, glAccountCategoryTransfer);
        }

        return glAccountCategoryTransfer;
    }

}
