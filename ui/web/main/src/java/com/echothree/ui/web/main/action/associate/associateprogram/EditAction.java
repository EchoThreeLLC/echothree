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

package com.echothree.ui.web.main.action.associate.associateprogram;

import com.echothree.control.user.associate.common.AssociateUtil;
import com.echothree.control.user.associate.common.edit.AssociateProgramEdit;
import com.echothree.control.user.associate.common.form.EditAssociateProgramForm;
import com.echothree.control.user.associate.common.result.EditAssociateProgramResult;
import com.echothree.control.user.associate.common.spec.AssociateProgramSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Associate/AssociateProgram/Edit",
    mappingClass = SecureActionMapping.class,
    name = "AssociateProgramEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Associate/AssociateProgram/Main", redirect = true),
        @SproutForward(name = "Form", path = "/associate/associateprogram/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String originalAssociateProgramName = request.getParameter(ParameterConstants.ORIGINAL_ASSOCIATE_PROGRAM_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditAssociateProgramForm commandForm = AssociateUtil.getHome().getEditAssociateProgramForm();
                AssociateProgramSpec spec = AssociateUtil.getHome().getAssociateProgramSpec();
                
                if(originalAssociateProgramName == null)
                    originalAssociateProgramName = actionForm.getOriginalAssociateProgramName();
                
                commandForm.setSpec(spec);
                spec.setAssociateProgramName(originalAssociateProgramName);
                
                if(wasPost(request)) {
                    AssociateProgramEdit edit = AssociateUtil.getHome().getAssociateProgramEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setAssociateProgramName(actionForm.getAssociateProgramName());
                    edit.setAssociateSequenceName(actionForm.getAssociateSequenceChoice());
                    edit.setAssociatePartyContactMechanismSequenceName(actionForm.getAssociatePartyContactMechanismSequenceChoice());
                    edit.setAssociateReferralSequenceName(actionForm.getAssociateReferralSequenceChoice());
                    edit.setItemIndirectSalePercent(actionForm.getItemIndirectSalePercent());
                    edit.setItemDirectSalePercent(actionForm.getItemDirectSalePercent());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = AssociateUtil.getHome().editAssociateProgram(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditAssociateProgramResult result = (EditAssociateProgramResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = AssociateUtil.getHome().editAssociateProgram(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditAssociateProgramResult result = (EditAssociateProgramResult)executionResult.getResult();
                    
                    if(result != null) {
                        AssociateProgramEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setOriginalAssociateProgramName(edit.getAssociateProgramName());
                            actionForm.setAssociateProgramName(edit.getAssociateProgramName());
                            actionForm.setAssociateSequenceChoice(edit.getAssociateSequenceName());
                            actionForm.setAssociatePartyContactMechanismSequenceChoice(edit.getAssociatePartyContactMechanismSequenceName());
                            actionForm.setAssociateReferralSequenceChoice(edit.getAssociateReferralSequenceName());
                            actionForm.setItemIndirectSalePercent(edit.getItemIndirectSalePercent());
                            actionForm.setItemDirectSalePercent(edit.getItemDirectSalePercent());
                            actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            actionForm.setSortOrder(edit.getSortOrder());
                            actionForm.setDescription(edit.getDescription());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}