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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.AppearanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class AppearanceTransferCache
        extends BaseCoreTransferCache<Appearance, AppearanceTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    boolean includeTextDecorations;
    boolean includeTextTransformations;
    
    /** Creates a new instance of AppearanceTransferCache */
    public AppearanceTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        var options = session.getOptions();
        if(options != null) {
            includeTextDecorations = options.contains(CoreOptions.AppearanceIncludeTextDecorations);
            includeTextTransformations = options.contains(CoreOptions.AppearanceIncludeTextTransformations);
        }
        
        setIncludeEntityInstance(true);
    }

    public AppearanceTransfer getAppearanceTransfer(Appearance appearance) {
        var appearanceTransfer = get(appearance);

        if(appearanceTransfer == null) {
            var appearanceDetail = appearance.getLastDetail();
            var appearanceName = appearanceDetail.getAppearanceName();
            var textColor = appearanceDetail.getTextColor();
            var textColorTransfer = textColor == null ? null : coreControl.getColorTransfer(userVisit, textColor);
            var backgroundColor = appearanceDetail.getBackgroundColor();
            var backgroundColorTransfer = backgroundColor == null ? null : coreControl.getColorTransfer(userVisit, backgroundColor);
            var fontStyle = appearanceDetail.getFontStyle();
            var fontStyleTransfer = fontStyle == null ? null : coreControl.getFontStyleTransfer(userVisit, fontStyle);
            var fontWeight = appearanceDetail.getFontWeight();
            var fontWeightTransfer = fontWeight == null ? null : coreControl.getFontWeightTransfer(userVisit, fontWeight);
            var isDefault = appearanceDetail.getIsDefault();
            var sortOrder = appearanceDetail.getSortOrder();
            var description = coreControl.getBestAppearanceDescription(appearance, getLanguage());

            appearanceTransfer = new AppearanceTransfer(appearanceName, textColorTransfer, backgroundColorTransfer, fontStyleTransfer, fontWeightTransfer,
                    isDefault, sortOrder, description);
            put(appearance, appearanceTransfer);
            
            if(includeTextDecorations) {
                appearanceTransfer.setAppearanceTextDecorations(new ListWrapper<>(coreControl.getAppearanceTextDecorationTransfersByAppearance(userVisit, appearance)));
            }
            
            if(includeTextTransformations) {
                appearanceTransfer.setAppearanceTextTransformations(new ListWrapper<>(coreControl.getAppearanceTextTransformationTransfersByAppearance(userVisit, appearance)));
            }
        }

        return appearanceTransfer;
    }

}
