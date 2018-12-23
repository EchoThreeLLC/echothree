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

package com.echothree.ui.web.main.action.sequence.sequence;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.form.CreateSequenceDescriptionForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Sequence/Sequence/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "SequenceDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Sequence/Sequence/Description", redirect = true),
        @SproutForward(name = "Form", path = "/sequence/sequence/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String sequenceTypeName = request.getParameter(ParameterConstants.SEQUENCE_TYPE_NAME);
        String sequenceName = request.getParameter(ParameterConstants.SEQUENCE_NAME);
        
        try {
            if(forwardKey == null) {
                if(wasPost(request)) {
                    DescriptionAddActionForm descriptionAddActionForm = (DescriptionAddActionForm)form;
                    
                    CreateSequenceDescriptionForm commandForm = SequenceUtil.getHome().getCreateSequenceDescriptionForm();
                    
                    if(sequenceTypeName == null)
                        sequenceTypeName = descriptionAddActionForm.getSequenceTypeName();
                    if(sequenceName == null)
                        sequenceName = descriptionAddActionForm.getSequenceName();
                    
                    commandForm.setSequenceTypeName(sequenceTypeName);
                    commandForm.setSequenceName(sequenceName);
                    commandForm.setLanguageIsoName(descriptionAddActionForm.getLanguageChoice());
                    commandForm.setDescription(descriptionAddActionForm.getDescription());
                    
                    CommandResult commandResult = SequenceUtil.getHome().createSequenceDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else
                    forwardKey = ForwardConstants.FORM;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM) || forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            parameters.put(ParameterConstants.SEQUENCE_TYPE_NAME, sequenceTypeName);
            parameters.put(ParameterConstants.SEQUENCE_NAME, sequenceName);
            customActionForward.setParameters(parameters);
            
            request.setAttribute("sequenceName", sequenceName); // TODO: not encoded
            request.setAttribute("sequenceTypeName", sequenceTypeName); // TODO: not encoded
        }
        
        return customActionForward;
    }
    
}