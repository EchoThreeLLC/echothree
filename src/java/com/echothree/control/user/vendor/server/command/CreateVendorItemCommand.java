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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.form.CreateVendorItemForm;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.control.vendor.common.workflow.VendorItemStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemAlias;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CreateVendorItemCommand
        extends BaseSimpleCommand<CreateVendorItemForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorItem.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateVendorItemCommand */
    public CreateVendorItemCommand(UserVisitPK userVisitPK, CreateVendorItemForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
        String vendorName = form.getVendorName();
        Vendor vendor = vendorControl.getVendorByName(vendorName);
        
        if(vendor != null) {
            ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            String itemName = form.getItemName();
            Item item = itemControl.getItemByNameThenAlias(itemName);
            
            if(item != null) {
                String vendorItemName = form.getVendorItemName();
                
                if(vendorItemName == null) {
                    ItemAliasType defaultItemAliasType = vendor.getDefaultItemAliasType();
                    
                    if(defaultItemAliasType == null) {
                        addExecutionError(ExecutionErrors.UnknownDefaultItemAliasType.name());
                    } else {
                        ItemUnitOfMeasureType itemUnitOfMeasureType = itemControl.getDefaultItemUnitOfMeasureType(item);
                        
                        if(itemUnitOfMeasureType == null) {
                            addExecutionError(ExecutionErrors.UnknownDefaultItemUnitOfMeasureType.name());
                        } else {
                            UnitOfMeasureType unitOfMeasureType = itemUnitOfMeasureType.getUnitOfMeasureType();
                            List<ItemAlias> itemAliases = itemControl.getItemAliases(item, unitOfMeasureType, defaultItemAliasType);
                            Iterator<ItemAlias> iter = itemAliases.iterator();
                            
                            if(iter.hasNext()) {
                                vendorItemName = iter.next().getAlias();
                            } else {
                                addExecutionError(ExecutionErrors.NoItemAliasesFound.name(), itemName,
                                        unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(),
                                        defaultItemAliasType.getLastDetail().getItemAliasTypeName());
                            }
                        }
                    }
                }
                
                if(!hasExecutionErrors()) {
                    Party vendorParty = vendor.getParty();
                    VendorItem vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorParty, vendorItemName);
                    
                    if(vendorItem == null) {
                        String cancellationPolicyName = form.getCancellationPolicyName();
                        CancellationPolicy cancellationPolicy = null;
                        
                        if(cancellationPolicyName != null) {
                            CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
                            CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationPolicyConstants.CancellationKind_VENDOR_CANCELLATION);
                            
                            cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
                        }
                        
                        if(cancellationPolicyName == null || cancellationPolicy != null) {
                            String returnPolicyName = form.getReturnPolicyName();
                            ReturnPolicy returnPolicy = null;
                            
                            if(returnPolicyName != null) {
                                ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
                                ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnPolicyConstants.ReturnKind_VENDOR_RETURN);
                                
                                returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                            }
                            
                            if(returnPolicyName == null || returnPolicy != null) {
                                CoreControl coreControl = getCoreControl();
                                WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                                String description = form.getDescription();
                                Integer priority = Integer.valueOf(form.getPriority());
                                BasePK createdBy = getPartyPK();
                                
                                vendorItem = vendorControl.createVendorItem(item, vendorParty, vendorItemName, description, priority, cancellationPolicy,
                                        returnPolicy, createdBy);

                                EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(vendorItem.getPrimaryKey());
                                workflowControl.addEntityToWorkflowUsingNames(null, VendorItemStatusConstants.Workflow_VENDOR_ITEM_STATUS,
                                        VendorItemStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);
                            } else {
                                addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationPolicyName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateVendorItemName.name(), vendorName, vendorItemName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
        }
        
        return null;
    }
    
}
