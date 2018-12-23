// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.shipping.shippingmethod;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetMimeTypeChoicesForm;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetLanguageChoicesForm;
import com.echothree.control.user.party.common.result.GetLanguageChoicesResult;
import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.party.common.choice.LanguageChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ShippingMethodCommentAdd")
public class CommentAddActionForm
        extends BaseActionForm {
    
    private LanguageChoicesBean languageChoices;
    private MimeTypeChoicesBean mimeTypeChoices;
    
    private String shippingMethodName;
    private String commentName;
    private String languageChoice;
    private String description;
    private String mimeTypeChoice;
    private String clobComment;
    
    private void setupLanguageChoices() {
        if(languageChoices == null) {
            try {
                GetLanguageChoicesForm commandForm = PartyUtil.getHome().getGetLanguageChoicesForm();

                commandForm.setDefaultLanguageChoice(languageChoice);
                commandForm.setAllowNullChoice(Boolean.toString(commentName == null));

                CommandResult commandResult = PartyUtil.getHome().getLanguageChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetLanguageChoicesResult getLanguageChoicesResult = (GetLanguageChoicesResult)executionResult.getResult();
                languageChoices = getLanguageChoicesResult.getLanguageChoices();

                if(languageChoice == null) {
                    languageChoice = languageChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, languageChoices remains null, no default
            }
        }
    }
    
    private void setupMimeTypeChoices() {
        if(mimeTypeChoices == null) {
            try {
                GetMimeTypeChoicesForm commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

                commandForm.setDefaultMimeTypeChoice(mimeTypeChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());
                if(commentName == null) {
                    commandForm.setComponentVendorName(ComponentVendors.ECHOTHREE.name());
                    commandForm.setEntityTypeName(EntityTypes.ShippingMethod.name());
                    commandForm.setCommentTypeName(CommentConstants.CommentType_SHIPPING_METHOD);
                } else {
                    commandForm.setCommentName(commentName);
                }

                CommandResult commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetMimeTypeChoicesResult result = (GetMimeTypeChoicesResult)executionResult.getResult();
                mimeTypeChoices = result.getMimeTypeChoices();

                if(mimeTypeChoice == null) {
                    mimeTypeChoice = mimeTypeChoices.getDefaultValue();
                }
            } catch(NamingException ne) {
                // failed, mimeTypeChoices remains null, no default
            }
        }
    }

    public void setShippingMethodName(String shippingMethodName) {
        this.shippingMethodName = shippingMethodName;
    }
    
    public String getShippingMethodName() {
        return shippingMethodName;
    }
    
    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }
    
    public String getCommentName() {
        return commentName;
    }
    
    public List<LabelValueBean> getLanguageChoices() {
        List<LabelValueBean> choices = null;
        
        setupLanguageChoices();
        if(languageChoices != null) {
            choices = convertChoices(languageChoices);
        }
        
        return choices;
    }
    
    public void setLanguageChoice(String languageChoice) {
        this.languageChoice = languageChoice;
    }
    
    public String getLanguageChoice() {
        setupLanguageChoices();
        
        return languageChoice;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<LabelValueBean> getMimeTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupMimeTypeChoices();
        if(mimeTypeChoices != null) {
            choices = convertChoices(mimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setMimeTypeChoice(String mimeTypeChoice) {
        this.mimeTypeChoice = mimeTypeChoice;
    }
    
    public String getMimeTypeChoice() {
        setupMimeTypeChoices();
        
        return mimeTypeChoice;
    }

    public String getClobComment() {
        return clobComment;
    }

    public void setClobComment(String clobComment) {
        this.clobComment = clobComment;
    }
    
}
