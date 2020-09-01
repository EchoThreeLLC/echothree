// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.form.CreateSelectorNodeForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.control.selector.server.logic.SelectorNodeTypeLogic;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorBooleanType;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.selector.server.entity.SelectorNodeType;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateSelectorNodeCommand
        extends BaseSimpleCommand<CreateSelectorNodeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> booleanFormFieldDefinitions;
    private final static List<FieldDefinition> entityListItemFormFieldDefinitions;
    private final static List<FieldDefinition> responsibilityTypeFormFieldDefinitions;
    private final static List<FieldDefinition> skillTypeFormFieldDefinitions;
    private final static List<FieldDefinition> trainingClassFormFieldDefinitions;
    private final static List<FieldDefinition> workflowStepFormFieldDefinitions;
    private final static List<FieldDefinition> itemCategoryFormFieldDefinitions;
    private final static List<FieldDefinition> itemAccountingCategoryFormFieldDefinitions;
    private final static List<FieldDefinition> itemPurchasingCategoryFormFieldDefinitions;
    private final static List<FieldDefinition> paymentMethodFormFieldDefinitions;
    private final static List<FieldDefinition> paymentProcessorFormFieldDefinitions;
    private final static List<FieldDefinition> geoCodeFormFieldDefinitions;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorNodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsRootSelectorNode", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SelectorNodeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Negate", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
        
        booleanFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorBooleanTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LeftSelectorNodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("RightSelectorNodeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        entityListItemFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityListItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        responsibilityTypeFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ResponsibilityTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        skillTypeFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        trainingClassFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        workflowStepFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        itemCategoryFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CheckParents", FieldType.BOOLEAN, true, null, null)
                ));
        
        itemAccountingCategoryFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CheckParents", FieldType.BOOLEAN, true, null, null)
                ));
        
        itemPurchasingCategoryFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CheckParents", FieldType.BOOLEAN, true, null, null)
                ));
        
        paymentMethodFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        paymentProcessorFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        geoCodeFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateSelectorNodeCommand */
    public CreateSelectorNodeCommand(UserVisitPK userVisitPK, CreateSelectorNodeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected ValidationResult validate() {
        Validator validator = new Validator(this);
        ValidationResult validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            SelectorNodeType selectorNodeType = SelectorNodeTypeLogic.getInstance().getSelectorNodeTypeByName(this, form.getSelectorNodeTypeName());
            
            if(!hasExecutionErrors()) {
                switch (selectorNodeType.getSelectorNodeTypeName()) {
                    case SelectorConstants.SelectorNodeType_BOOLEAN:
                        validationResult = validator.validate(form, booleanFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_ENTITY_LIST_ITEM:
                        validationResult = validator.validate(form, entityListItemFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_RESPONSIBILITY_TYPE:
                        validationResult = validator.validate(form, responsibilityTypeFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_SKILL_TYPE:
                        validationResult = validator.validate(form, skillTypeFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_TRAINING_CLASS:
                        validationResult = validator.validate(form, trainingClassFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_WORKFLOW_STEP:
                        validationResult = validator.validate(form, workflowStepFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_ITEM_CATEGORY:
                        validationResult = validator.validate(form, itemCategoryFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_ITEM_ACCOUNTING_CATEGORY:
                        validationResult = validator.validate(form, itemAccountingCategoryFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_ITEM_PURCHASING_CATEGORY:
                        validationResult = validator.validate(form, itemPurchasingCategoryFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_PAYMENT_METHOD:
                        validationResult = validator.validate(form, paymentMethodFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_PAYMENT_PROCESSOR:
                        validationResult = validator.validate(form, paymentProcessorFormFieldDefinitions);
                        break;
                    case SelectorConstants.SelectorNodeType_GEO_CODE:
                        validationResult = validator.validate(form, geoCodeFormFieldDefinitions);
                        break;
                    default:
                        break;
                }
            }
        }
        
        return validationResult;
    }
    
    private abstract class BaseSelectorNodeType {
        SelectorControl selectorControl = null;
        SelectorNodeType selectorNodeType = null;
        
        protected BaseSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            this.selectorControl = selectorControl;
            selectorNodeType = selectorControl.getSelectorNodeTypeByName(selectorNodeTypeName);
            
            if(selectorNodeType == null) {
                addExecutionError(ExecutionErrors.UnknownSelectorNodeTypeName.name(), selectorNodeTypeName);
            }
        }
        
        public abstract void execute(SelectorNode selectorNode, PartyPK partyPK);
    }
    
    private abstract class AccountingSelectorNodeType
        extends BaseSelectorNodeType {
        protected AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);

        protected AccountingSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class CoreSelectorNodeType
        extends BaseSelectorNodeType {
        protected CoreControl coreControl = getCoreControl();

        protected CoreSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class EmployeeSelectorNodeType
        extends BaseSelectorNodeType {
        protected EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);

        protected EmployeeSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class GeoSelectorNodeType
        extends BaseSelectorNodeType {
        protected GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);

        protected GeoSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class ItemSelectorNodeType
        extends BaseSelectorNodeType {
        protected ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        protected ItemSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }

    private abstract class PaymentProcessorSelectorNodeType
            extends BaseSelectorNodeType {
        protected PaymentProcessorControl paymentProcessorControl = (PaymentProcessorControl)Session.getModelController(PaymentProcessorControl.class);

        protected PaymentProcessorSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }

    private abstract class PaymentMethodSelectorNodeType
            extends BaseSelectorNodeType {
        protected PaymentMethodControl paymentMethodControl = (PaymentMethodControl)Session.getModelController(PaymentMethodControl.class);

        protected PaymentMethodSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }

    private abstract class TrainingSelectorNodeType
        extends BaseSelectorNodeType {
        protected TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);

        protected TrainingSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class VendorSelectorNodeType
        extends BaseSelectorNodeType {
        protected VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);

        protected VendorSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class WorkSelectorNodeType
        extends BaseSelectorNodeType {
        protected WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

        protected WorkSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private class BooleanNodeType
        extends BaseSelectorNodeType {
        SelectorBooleanType selectorBooleanType = null;
        SelectorNode leftSelectorNode = null;
        SelectorNode rightSelectorNode = null;
        
        public BooleanNodeType(SelectorControl selectorControl, Selector selector) {
            super(selectorControl, SelectorConstants.SelectorNodeType_BOOLEAN);
            
            if(!hasExecutionErrors()) {
                String selectorBooleanTypeName = form.getSelectorBooleanTypeName();
                selectorBooleanType = selectorControl.getSelectorBooleanTypeByName(selectorBooleanTypeName);
                
                if(selectorBooleanType != null) {
                    String leftSelectorNodeName = form.getLeftSelectorNodeName();
                    leftSelectorNode = selectorControl.getSelectorNodeByName(selector, leftSelectorNodeName);
                    
                    if(leftSelectorNode != null) {
                        String rightSelectorNodeName = form.getRightSelectorNodeName();
                        rightSelectorNode = selectorControl.getSelectorNodeByName(selector, rightSelectorNodeName);
                        
                        if(rightSelectorNode != null) {
                            if(rightSelectorNode.equals(leftSelectorNode)) {
                                addExecutionError(ExecutionErrors.IdenticalLeftAndRightSelectorNodes.name(), leftSelectorNodeName, rightSelectorNodeName);
                            }
                            
                            // TODO: Circular Node Check
                            // TODO: Sensible Root Node Check
                        } else {
                            addExecutionError(ExecutionErrors.UnknownRightSelectorNodeName.name(), rightSelectorNodeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLeftSelectorNodeName.name(), leftSelectorNodeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorBooleanTypeName.name(), selectorBooleanTypeName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeBoolean(selectorNode, selectorBooleanType, leftSelectorNode, rightSelectorNode,
                    partyPK);
        }
    }
    
    private class EntityListItemNodeType
        extends CoreSelectorNodeType {
        EntityListItem entityListItem = null;
        
        public EntityListItemNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_ENTITY_LIST_ITEM);
            
            if(!hasExecutionErrors()) {
                String componentVendorName = form.getComponentVendorName();
                ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
                
                if(componentVendor != null) {
                    String entityTypeName = form.getEntityTypeName();
                    EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
                    
                    if(entityType != null) {
                        String entityAttributeName = form.getEntityAttributeName();
                        EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);
                        
                        if(entityAttribute != null) {
                            String entityListItemName = form.getEntityListItemName();
                            entityListItem = coreControl.getEntityListItemByName(entityAttribute, entityListItemName);
                            
                            if(entityListItem == null) {
                                addExecutionError(ExecutionErrors.UnknownEntityListItemName.name(), entityListItemName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityAttributeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeEntityListItem(selectorNode, entityListItem, partyPK);
        }
    }
    
    private class ResponsibilityNodeType
        extends EmployeeSelectorNodeType {
        private ResponsibilityType responsiblityType = null;
        
        public ResponsibilityNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_RESPONSIBILITY_TYPE);
            
            if(!hasExecutionErrors()) {
                String responsiblityTypeName = form.getResponsibilityTypeName();
                
                responsiblityType = employeeControl.getResponsibilityTypeByName(responsiblityTypeName);
                
                if(responsiblityType == null) {
                    addExecutionError(ExecutionErrors.UnknownResponsibilityTypeName.name(), responsiblityTypeName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeResponsibilityType(selectorNode, responsiblityType, partyPK);
        }
    }
    
    private class SkillNodeType
        extends EmployeeSelectorNodeType {
        private SkillType skillType = null;
        
        public SkillNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_SKILL_TYPE);
            
            if(!hasExecutionErrors()) {
                String skillTypeName = form.getSkillTypeName();
                
                skillType = employeeControl.getSkillTypeByName(skillTypeName);
                
                if(skillType == null) {
                    addExecutionError(ExecutionErrors.UnknownSkillTypeName.name(), skillTypeName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeSkillType(selectorNode, skillType, partyPK);
        }
    }
    
    private class TrainingClassNodeType
        extends TrainingSelectorNodeType {
        private TrainingClass trainingClass = null;
        
        public TrainingClassNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_TRAINING_CLASS);
            
            if(!hasExecutionErrors()) {
                String trainingClassName = form.getTrainingClassName();
                
                trainingClass = trainingControl.getTrainingClassByName(trainingClassName);
                
                if(trainingClass == null) {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeTrainingClass(selectorNode, trainingClass, partyPK);
        }
    }
    
    private class WorkflowStepNodeType
        extends WorkSelectorNodeType {
        private WorkflowStep workflowStep = null;
        
        public WorkflowStepNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_TRAINING_CLASS);
            
            if(!hasExecutionErrors()) {
                String workflowName = form.getWorkflowName();
                Workflow workflow = workflowControl.getWorkflowByName(workflowName);
                
                if(workflow != null) {
                    String workflowStepName = form.getWorkflowStepName();
                    
                    workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);
                    
                    if(workflowStep == null) {
                        addExecutionError(ExecutionErrors.UnknownWorkflowStepName.name(), workflowStepName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeWorkflowStep(selectorNode, workflowStep, partyPK);
        }
    }
    
    private class ItemCategoryNodeType
        extends ItemSelectorNodeType {
        private ItemCategory itemCategory = null;
        
        public ItemCategoryNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_ITEM_CATEGORY);
            
            if(!hasExecutionErrors()) {
                String itemCategoryName = form.getItemCategoryName();
                
                itemCategory = itemControl.getItemCategoryByName(itemCategoryName);
                
                if(itemCategory == null) {
                    addExecutionError(ExecutionErrors.UnknownItemCategoryName.name(), itemCategoryName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            Boolean checkParents = Boolean.valueOf(form.getCheckParents());
            
            selectorControl.createSelectorNodeItemCategory(selectorNode, itemCategory, checkParents, partyPK);
        }
    }
    
    private class ItemAccountingCategoryNodeType
        extends AccountingSelectorNodeType {
        private ItemAccountingCategory itemAccountingCategory = null;
        
        public ItemAccountingCategoryNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_ITEM_ACCOUNTING_CATEGORY);
            
            if(!hasExecutionErrors()) {
                String itemAccountingCategoryName = form.getItemAccountingCategoryName();
                
                itemAccountingCategory = accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName);
                
                if(itemAccountingCategory == null) {
                    addExecutionError(ExecutionErrors.UnknownItemAccountingCategoryName.name(), itemAccountingCategoryName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            Boolean checkParents = Boolean.valueOf(form.getCheckParents());
            
            selectorControl.createSelectorNodeItemAccountingCategory(selectorNode, itemAccountingCategory, checkParents, partyPK);
        }
    }
    
    private class ItemPurchasingCategoryNodeType
        extends VendorSelectorNodeType {
        private ItemPurchasingCategory itemPurchasingCategory = null;
        
        public ItemPurchasingCategoryNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_ITEM_PURCHASING_CATEGORY);
            
            if(!hasExecutionErrors()) {
                String itemPurchasingCategoryName = form.getItemPurchasingCategoryName();
                
                itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName);
                
                if(itemPurchasingCategory == null) {
                    addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            Boolean checkParents = Boolean.valueOf(form.getCheckParents());
            
            selectorControl.createSelectorNodeItemPurchasingCategory(selectorNode, itemPurchasingCategory, checkParents, partyPK);
        }
    }
    
    private class PaymentMethodNodeType
        extends PaymentMethodSelectorNodeType {
        private PaymentMethod paymentMethod = null;
        
        public PaymentMethodNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_PAYMENT_METHOD);
            
            if(!hasExecutionErrors()) {
                String paymentMethodName = form.getPaymentMethodName();
                
                paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);
                
                if(paymentMethod == null) {
                    addExecutionError(ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodePaymentMethod(selectorNode, paymentMethod, partyPK);
        }
    }
    
    private class PaymentProcessorNodeType
        extends PaymentProcessorSelectorNodeType {
        private PaymentProcessor paymentProcessor = null;
        
        public PaymentProcessorNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_PAYMENT_PROCESSOR);
            
            if(!hasExecutionErrors()) {
                String paymentProcessorName = form.getPaymentProcessorName();
                
                paymentProcessor = paymentProcessorControl.getPaymentProcessorByName(paymentProcessorName);
                
                if(paymentProcessor == null) {
                    addExecutionError(ExecutionErrors.UnknownPaymentProcessorName.name(), paymentProcessorName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodePaymentProcessor(selectorNode, paymentProcessor, partyPK);
        }
    }
    
    private class GeoCodeNodeType
        extends GeoSelectorNodeType {
        private GeoCode geoCode = null;
        
        public GeoCodeNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorConstants.SelectorNodeType_PAYMENT_PROCESSOR);
            
            if(!hasExecutionErrors()) {
                String geoCodeName = form.getGeoCodeName();
                String countryName = form.getCountryName();
                int parameterCount = (geoCodeName == null? 0: 1) + (countryName == null? 0: 1);
                
                if(parameterCount == 1) {
                    if(countryName != null) {
                        geoCode = geoControl.getCountryByAlias(countryName);
                        
                        if(geoCode == null) {
                            addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
                        }
                    } else {
                        geoCode = geoControl.getGeoCodeByName(geoCodeName);
                        
                        if(geoCode == null) {
                            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeGeoCode(selectorNode, geoCode, partyPK);
        }
    }
    
    @Override
    protected BaseResult execute() {
        if(!hasExecutionErrors()) {
            var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
            String selectorKindName = form.getSelectorKindName();
            SelectorKind selectorKind = selectorControl.getSelectorKindByName(selectorKindName);

            if(selectorKind != null) {
                String selectorTypeName = form.getSelectorTypeName();
                SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);

                if(selectorType != null) {
                    String selectorName = form.getSelectorName();
                    Selector selector = selectorControl.getSelectorByName(selectorType, selectorName);

                    if(selector != null) {
                        String selectorNodeName = form.getSelectorNodeName();
                        SelectorNode selectorNode = selectorControl.getSelectorNodeByName(selector, selectorNodeName);

                        if(selectorNode == null) {
                            String selectorNodeTypeName = form.getSelectorNodeTypeName();
                            SelectorNodeType selectorNodeType = selectorControl.getSelectorNodeTypeByName(selectorNodeTypeName);

                            if(selectorNodeType != null) {
                                selectorNodeTypeName = selectorNodeType.getSelectorNodeTypeName();
                                
                                if(selectorControl.getSelectorNodeTypeUse(selectorKind, selectorNodeType) != null) {
                                    BaseSelectorNodeType baseSelectorNodeType = null;

                                    switch (selectorNodeTypeName) {
                                        case SelectorConstants.SelectorNodeType_BOOLEAN:
                                            baseSelectorNodeType = new BooleanNodeType(selectorControl, selector);
                                            break;
                                        case SelectorConstants.SelectorNodeType_ENTITY_LIST_ITEM:
                                            baseSelectorNodeType = new EntityListItemNodeType(selectorControl);
                                            break;
                                        case SelectorConstants.SelectorNodeType_RESPONSIBILITY_TYPE:
                                            baseSelectorNodeType = new ResponsibilityNodeType(selectorControl);
                                            break;
                                        case SelectorConstants.SelectorNodeType_SKILL_TYPE:
                                            baseSelectorNodeType = new SkillNodeType(selectorControl);
                                            break;
                                        case SelectorConstants.SelectorNodeType_TRAINING_CLASS:
                                            baseSelectorNodeType = new TrainingClassNodeType(selectorControl);
                                            break;
                                        case SelectorConstants.SelectorNodeType_WORKFLOW_STEP:
                                            baseSelectorNodeType = new WorkflowStepNodeType(selectorControl);
                                            break;
                                        case SelectorConstants.SelectorNodeType_ITEM_CATEGORY:
                                            baseSelectorNodeType = new ItemCategoryNodeType(selectorControl);
                                            break;
                                        case SelectorConstants.SelectorNodeType_ITEM_ACCOUNTING_CATEGORY:
                                            baseSelectorNodeType = new ItemAccountingCategoryNodeType(selectorControl);
                                            break;
                                        case SelectorConstants.SelectorNodeType_ITEM_PURCHASING_CATEGORY:
                                            baseSelectorNodeType = new ItemPurchasingCategoryNodeType(selectorControl);
                                            break;
                                        case SelectorConstants.SelectorNodeType_PAYMENT_METHOD:
                                            baseSelectorNodeType = new PaymentMethodNodeType(selectorControl);
                                            break;
                                        case SelectorConstants.SelectorNodeType_PAYMENT_PROCESSOR:
                                            baseSelectorNodeType = new PaymentProcessorNodeType(selectorControl);
                                            break;
                                        case SelectorConstants.SelectorNodeType_GEO_CODE:
                                            baseSelectorNodeType = new GeoCodeNodeType(selectorControl);
                                            break;
                                        default:
                                            break;
                                    }

                                    if(!hasExecutionErrors()) {
                                        PartyPK partyPK = getPartyPK();
                                        Boolean isRootSelectorNode = Boolean.valueOf(form.getIsRootSelectorNode());
                                        Boolean negate = Boolean.valueOf(form.getNegate());
                                        String description = form.getDescription();

                                        selectorNode = selectorControl.createSelectorNode(selector, selectorNodeName, isRootSelectorNode,
                                                selectorNodeType, negate, partyPK);

                                        baseSelectorNodeType.execute(selectorNode, partyPK);

                                        if(description != null) {
                                            Language language = getPreferredLanguage();

                                            selectorControl.createSelectorNodeDescription(selectorNode, language, description, partyPK);
                                        }
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSelectorNodeTypeUse.name(), selectorKindName, selectorNodeTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateSelectorNodeName.name(), selectorNodeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
            }
        }
        
        return null;
    }
    
}
