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

package com.echothree.ui.web.main.action.item.itemdescriptiontype.add;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.remote.form.GetMimeTypeChoicesForm;
import com.echothree.control.user.core.remote.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.remote.form.GetItemDescriptionTypeChoicesForm;
import com.echothree.control.user.item.remote.result.GetItemDescriptionTypeChoicesResult;
import com.echothree.model.control.core.remote.choice.MimeTypeChoicesBean;
import com.echothree.model.control.item.remote.choice.ItemDescriptionTypeChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemDescriptionTypeAddStep2")
public class Step2ActionForm
        extends BaseActionForm {

    private ItemDescriptionTypeChoicesBean parentItemDescriptionTypeChoices;
    private MimeTypeChoicesBean preferredMimeTypeChoices;

    private String itemDescriptionTypeName;
    private String parentItemDescriptionTypeChoice;
    private Boolean useParentIfMissing;
    private String mimeTypeUsageTypeName;
    private Boolean checkContentWebAddress;
    private Boolean includeInIndex;
    private Boolean indexDefault;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    private String minimumHeight;
    private String minimumWidth;
    private String maximumHeight;
    private String maximumWidth;
    private String preferredHeight;
    private String preferredWidth;
    private String preferredMimeTypeChoice;
    private String quality;
    private Boolean scaleFromParent;

    private void setupParentItemDescriptionTypeChoices() {
        if(parentItemDescriptionTypeChoices == null) {
            try {
                GetItemDescriptionTypeChoicesForm form = ItemUtil.getHome().getGetItemDescriptionTypeChoicesForm();

                form.setDefaultItemDescriptionTypeChoice(parentItemDescriptionTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());

                CommandResult commandResult = ItemUtil.getHome().getItemDescriptionTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetItemDescriptionTypeChoicesResult getItemDescriptionTypeChoicesResult = (GetItemDescriptionTypeChoicesResult)executionResult.getResult();
                parentItemDescriptionTypeChoices = getItemDescriptionTypeChoicesResult.getItemDescriptionTypeChoices();

                if(parentItemDescriptionTypeChoice == null) {
                    parentItemDescriptionTypeChoice = parentItemDescriptionTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, parentItemDescriptionTypeChoices remains null, no default
            }
        }
    }

    public void setupPreferredMimeTypeChoices() {
        if(preferredMimeTypeChoices == null) {
            try {
                GetMimeTypeChoicesForm form = CoreUtil.getHome().getGetMimeTypeChoicesForm();

                form.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);
                form.setDefaultMimeTypeChoice(preferredMimeTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());

                CommandResult commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetMimeTypeChoicesResult result = (GetMimeTypeChoicesResult)executionResult.getResult();
                preferredMimeTypeChoices = result.getMimeTypeChoices();

                if(preferredMimeTypeChoice == null) {
                    preferredMimeTypeChoice = preferredMimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, mimeTypeChoices remains null, no default
            }
        }
    }

    public void setItemDescriptionTypeName(String itemDescriptionTypeName) {
        this.itemDescriptionTypeName = itemDescriptionTypeName;
    }

    public String getItemDescriptionTypeName() {
        return itemDescriptionTypeName;
    }

    public List<LabelValueBean> getParentItemDescriptionTypeChoices() {
        List<LabelValueBean> choices = null;

        setupParentItemDescriptionTypeChoices();
        if(parentItemDescriptionTypeChoices != null) {
            choices = convertChoices(parentItemDescriptionTypeChoices);
        }

        return choices;
    }

    public String getParentItemDescriptionTypeChoice() {
        setupParentItemDescriptionTypeChoices();
        return parentItemDescriptionTypeChoice;
    }

    public void setParentItemDescriptionTypeChoice(String parentItemDescriptionTypeChoice) {
        this.parentItemDescriptionTypeChoice = parentItemDescriptionTypeChoice;
    }

    /**
     * @return the useParentIfMissing
     */
    public Boolean getUseParentIfMissing() {
        return useParentIfMissing;
    }

    /**
     * @param useParentIfMissing the useParentIfMissing to set
     */
    public void setUseParentIfMissing(Boolean useParentIfMissing) {
        this.useParentIfMissing = useParentIfMissing;
    }

    public String getMimeTypeUsageTypeName() {
        return mimeTypeUsageTypeName;
    }

    public void setMimeTypeUsageTypeName(String mimeTypeUsageTypeName) {
        this.mimeTypeUsageTypeName = mimeTypeUsageTypeName;
    }

    /**
     * @return the checkContentWebAddress
     */
    public Boolean getCheckContentWebAddress() {
        return checkContentWebAddress;
    }

    /**
     * @param checkContentWebAddress the checkContentWebAddress to set
     */
    public void setCheckContentWebAddress(Boolean checkContentWebAddress) {
        this.checkContentWebAddress = checkContentWebAddress;
    }

    /**
     * @return the includeInIndex
     */
    public Boolean getIncludeInIndex() {
        return includeInIndex;
    }

    /**
     * @param includeInIndex the includeInIndex to set
     */
    public void setIncludeInIndex(Boolean includeInIndex) {
        this.includeInIndex = includeInIndex;
    }

    /**
     * @return the indexDefault
     */
    public Boolean getIndexDefault() {
        return indexDefault;
    }

    /**
     * @param indexDefault the indexDefault to set
     */
    public void setIndexDefault(Boolean indexDefault) {
        this.indexDefault = indexDefault;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @return the minimumHeight
     */
    public String getMinimumHeight() {
        return minimumHeight;
    }

    /**
     * @param minimumHeight the minimumHeight to set
     */
    public void setMinimumHeight(String minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    /**
     * @return the minimumWidth
     */
    public String getMinimumWidth() {
        return minimumWidth;
    }

    /**
     * @param minimumWidth the minimumWidth to set
     */
    public void setMinimumWidth(String minimumWidth) {
        this.minimumWidth = minimumWidth;
    }

    /**
     * @return the maximumHeight
     */
    public String getMaximumHeight() {
        return maximumHeight;
    }

    /**
     * @param maximumHeight the maximumHeight to set
     */
    public void setMaximumHeight(String maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    /**
     * @return the maximumWidth
     */
    public String getMaximumWidth() {
        return maximumWidth;
    }

    /**
     * @param maximumWidth the maximumWidth to set
     */
    public void setMaximumWidth(String maximumWidth) {
        this.maximumWidth = maximumWidth;
    }

    /**
     * @return the preferredHeight
     */
    public String getPreferredHeight() {
        return preferredHeight;
    }

    /**
     * @param preferredHeight the preferredHeight to set
     */
    public void setPreferredHeight(String preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    /**
     * @return the preferredWidth
     */
    public String getPreferredWidth() {
        return preferredWidth;
    }

    /**
     * @param preferredWidth the preferredWidth to set
     */
    public void setPreferredWidth(String preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

    public List<LabelValueBean> getPreferredMimeTypeChoices() {
        List<LabelValueBean> choices = null;

        setupPreferredMimeTypeChoices();
        if(preferredMimeTypeChoices != null)
            choices = convertChoices(preferredMimeTypeChoices);

        return choices;
    }

    public void setPreferredMimeTypeChoice(String preferredMimeTypeChoice) {
        this.preferredMimeTypeChoice = preferredMimeTypeChoice;
    }

    public String getPreferredMimeTypeChoice() {
        setupPreferredMimeTypeChoices();
        return preferredMimeTypeChoice;
    }

    /**
     * @return the quality
     */
    public String getQuality() {
        return quality;
    }

    /**
     * @param quality the quality to set
     */
    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     * @return the scaleFromParent
     */
    public Boolean getScaleFromParent() {
        return scaleFromParent;
    }

    /**
     * @param scaleFromParent the scaleFromParent to set
     */
    public void setScaleFromParent(Boolean scaleFromParent) {
        this.scaleFromParent = scaleFromParent;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        checkContentWebAddress = Boolean.FALSE;
        useParentIfMissing = Boolean.FALSE;
        includeInIndex = Boolean.FALSE;
        indexDefault = Boolean.FALSE;
        isDefault = Boolean.FALSE;
        scaleFromParent = Boolean.FALSE;
    }

}
