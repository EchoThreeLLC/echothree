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

package com.echothree.model.control.accounting.server.control;

import com.echothree.model.control.accounting.common.choice.CurrencyChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountCategoryChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountClassChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlAccountTypeChoicesBean;
import com.echothree.model.control.accounting.common.choice.GlResourceTypeChoicesBean;
import com.echothree.model.control.accounting.common.choice.ItemAccountingCategoryChoicesBean;
import com.echothree.model.control.accounting.common.choice.SymbolPositionChoicesBean;
import com.echothree.model.control.accounting.common.choice.TransactionGroupStatusChoicesBean;
import com.echothree.model.control.accounting.common.transfer.CurrencyDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountCategoryTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountClassDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountClassTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountTypeTransfer;
import com.echothree.model.control.accounting.common.transfer.GlResourceTypeDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlResourceTypeTransfer;
import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryTransfer;
import com.echothree.model.control.accounting.common.transfer.SymbolPositionDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.SymbolPositionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTypeDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTypeTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountCategoryTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGlEntryTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGroupTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTypeDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTypeTransfer;
import static com.echothree.model.control.accounting.common.workflow.TransactionGroupStatusConstants.WorkflowStep_TRANSACTION_GROUP_STATUS_ACTIVE;
import static com.echothree.model.control.accounting.common.workflow.TransactionGroupStatusConstants.Workflow_TRANSACTION_GROUP_STATUS;
import com.echothree.model.control.accounting.server.transfer.AccountingTransferCaches;
import com.echothree.model.control.accounting.server.transfer.CurrencyDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountCategoryDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountCategoryTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountClassDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountClassTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountTypeTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlResourceTypeDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlResourceTypeTransferCache;
import com.echothree.model.control.accounting.server.transfer.ItemAccountingCategoryDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.ItemAccountingCategoryTransferCache;
import com.echothree.model.control.accounting.server.transfer.SymbolPositionDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.SymbolPositionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionEntityRoleTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionEntityRoleTypeDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionEntityRoleTypeTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionGlAccountCategoryDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionGlAccountCategoryTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionGlEntryTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionGroupTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionTypeDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionTypeTransferCache;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.GlAccountCategoryPK;
import com.echothree.model.data.accounting.common.pk.GlAccountClassPK;
import com.echothree.model.data.accounting.common.pk.GlAccountPK;
import com.echothree.model.data.accounting.common.pk.GlAccountTypePK;
import com.echothree.model.data.accounting.common.pk.GlResourceTypePK;
import com.echothree.model.data.accounting.common.pk.ItemAccountingCategoryPK;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.accounting.common.pk.TransactionEntityRoleTypePK;
import com.echothree.model.data.accounting.common.pk.TransactionGlAccountCategoryPK;
import com.echothree.model.data.accounting.common.pk.TransactionTypePK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.CurrencyDescription;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountCategoryDescription;
import com.echothree.model.data.accounting.server.entity.GlAccountCategoryDetail;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.entity.GlAccountClassDescription;
import com.echothree.model.data.accounting.server.entity.GlAccountClassDetail;
import com.echothree.model.data.accounting.server.entity.GlAccountDescription;
import com.echothree.model.data.accounting.server.entity.GlAccountDetail;
import com.echothree.model.data.accounting.server.entity.GlAccountSummary;
import com.echothree.model.data.accounting.server.entity.GlAccountType;
import com.echothree.model.data.accounting.server.entity.GlAccountTypeDescription;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
import com.echothree.model.data.accounting.server.entity.GlResourceTypeDescription;
import com.echothree.model.data.accounting.server.entity.GlResourceTypeDetail;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDescription;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDetail;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.accounting.server.entity.SymbolPositionDescription;
import com.echothree.model.data.accounting.server.entity.SymbolPositionDetail;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionDetail;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRole;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleTypeDescription;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleTypeDetail;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccount;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategoryDescription;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategoryDetail;
import com.echothree.model.data.accounting.server.entity.TransactionGlEntry;
import com.echothree.model.data.accounting.server.entity.TransactionGroup;
import com.echothree.model.data.accounting.server.entity.TransactionGroupDetail;
import com.echothree.model.data.accounting.server.entity.TransactionStatus;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.accounting.server.entity.TransactionTypeDescription;
import com.echothree.model.data.accounting.server.entity.TransactionTypeDetail;
import com.echothree.model.data.accounting.server.factory.CurrencyDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.CurrencyFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountCategoryDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountCategoryDetailFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountCategoryFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountClassDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountClassDetailFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountClassFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountDetailFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountSummaryFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountTypeDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.GlAccountTypeFactory;
import com.echothree.model.data.accounting.server.factory.GlResourceTypeDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.GlResourceTypeDetailFactory;
import com.echothree.model.data.accounting.server.factory.GlResourceTypeFactory;
import com.echothree.model.data.accounting.server.factory.ItemAccountingCategoryDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.ItemAccountingCategoryDetailFactory;
import com.echothree.model.data.accounting.server.factory.ItemAccountingCategoryFactory;
import com.echothree.model.data.accounting.server.factory.SymbolPositionDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.SymbolPositionDetailFactory;
import com.echothree.model.data.accounting.server.factory.SymbolPositionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionEntityRoleFactory;
import com.echothree.model.data.accounting.server.factory.TransactionEntityRoleTypeDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionEntityRoleTypeDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionEntityRoleTypeFactory;
import com.echothree.model.data.accounting.server.factory.TransactionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGlAccountCategoryDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGlAccountCategoryDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGlAccountCategoryFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGlAccountFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGlEntryFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGroupDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionGroupFactory;
import com.echothree.model.data.accounting.server.factory.TransactionStatusFactory;
import com.echothree.model.data.accounting.server.factory.TransactionTypeDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionTypeDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionTypeFactory;
import com.echothree.model.data.accounting.server.value.GlAccountCategoryDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlAccountCategoryDetailValue;
import com.echothree.model.data.accounting.server.value.GlAccountClassDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlAccountClassDetailValue;
import com.echothree.model.data.accounting.server.value.GlAccountDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlAccountDetailValue;
import com.echothree.model.data.accounting.server.value.GlResourceTypeDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlResourceTypeDetailValue;
import com.echothree.model.data.accounting.server.value.ItemAccountingCategoryDescriptionValue;
import com.echothree.model.data.accounting.server.value.ItemAccountingCategoryDetailValue;
import com.echothree.model.data.accounting.server.value.SymbolPositionDescriptionValue;
import com.echothree.model.data.accounting.server.value.SymbolPositionDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionEntityRoleTypeDescriptionValue;
import com.echothree.model.data.accounting.server.value.TransactionEntityRoleTypeDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionGlAccountCategoryDescriptionValue;
import com.echothree.model.data.accounting.server.value.TransactionGlAccountCategoryDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionGlAccountValue;
import com.echothree.model.data.accounting.server.value.TransactionGroupDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionTypeDescriptionValue;
import com.echothree.model.data.accounting.server.value.TransactionTypeDetailValue;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccountingControl
        extends BaseModelControl {
    
    /** Creates a new instance of AccountingControl */
    public AccountingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Accounting Transfer Caches
    // --------------------------------------------------------------------------------
    
    private AccountingTransferCaches accountingTransferCaches;
    
    public AccountingTransferCaches getAccountingTransferCaches(UserVisit userVisit) {
        if(accountingTransferCaches == null) {
            accountingTransferCaches = new AccountingTransferCaches(userVisit, this);
        }
        
        return accountingTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    // Currencies
    // --------------------------------------------------------------------------------
    
    public Currency createCurrency(String currencyIsoName, String symbol, SymbolPosition symbolPosition, Boolean symbolOnListStart,
            Boolean symbolOnListMember, Boolean symbolOnSubtotal, Boolean symbolOnTotal, String groupingSeparator, Integer groupingSize,
            String fractionSeparator, Integer defaultFractionDigits, Integer priceUnitFractionDigits, Integer priceLineFractionDigits,
            Integer costUnitFractionDigits, Integer costLineFractionDigits, String minusSign, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        Currency defaultCurrency = getDefaultCurrencyForUpdate();
        
        if(defaultCurrency == null) {
            isDefault = Boolean.TRUE;
        } else if(isDefault) {
            defaultCurrency.setIsDefault(Boolean.FALSE);
        }
        
        Currency currency = CurrencyFactory.getInstance().create(currencyIsoName, symbol, symbolPosition, symbolOnListStart,
                symbolOnListMember, symbolOnSubtotal, symbolOnTotal, groupingSeparator, groupingSize, fractionSeparator,
                defaultFractionDigits, priceUnitFractionDigits, priceLineFractionDigits, costUnitFractionDigits,
                costLineFractionDigits, minusSign, isDefault, sortOrder);
        
        sendEventUsingNames(currency.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return currency;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Currency */
    public Currency getCurrencyByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        CurrencyPK pk = new CurrencyPK(entityInstance.getEntityUniqueId());
        Currency currency = CurrencyFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return currency;
    }

    public Currency getCurrencyByEntityInstance(EntityInstance entityInstance) {
        return getCurrencyByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Currency getCurrencyByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCurrencyByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public List<Currency> getCurrencies() {
        PreparedStatement ps = CurrencyFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM currencies " +
                "ORDER BY cur_sortorder, cur_currencyisoname");
        
        return CurrencyFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    private Currency getDefaultCurrency(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM currencies " +
                    "WHERE cur_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM currencies " +
                    "WHERE cur_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = CurrencyFactory.getInstance().prepareStatement(query);
        
        return CurrencyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public Currency getDefaultCurrency() {
        return getDefaultCurrency(EntityPermission.READ_ONLY);
    }
    
    public Currency getDefaultCurrencyForUpdate() {
        return getDefaultCurrency(EntityPermission.READ_WRITE);
    }
    
    private Currency getCurrencyByIsoName(String currencyIsoName, EntityPermission entityPermission) {
        Currency currency;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM currencies " +
                        "WHERE cur_currencyisoname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM currencies " +
                        "WHERE cur_currencyisoname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CurrencyFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, currencyIsoName);
            
            currency = CurrencyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return currency;
    }
    
    public Currency getCurrencyByIsoName(String currencyIsoName) {
        return getCurrencyByIsoName(currencyIsoName, EntityPermission.READ_ONLY);
    }
    
    public Currency getCurrencyByIsoNameForUpdate(String currencyIsoName) {
        return getCurrencyByIsoName(currencyIsoName, EntityPermission.READ_WRITE);
    }
    
    public CurrencyChoicesBean getCurrencyChoices(String defaultCurrencyChoice, Language language, boolean allowNullChoice) {
        List<Currency> currencies = getCurrencies();
        int size = currencies.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCurrencyChoice == null) {
                defaultValue = "";
            }
        }
        
        for(Currency currency: currencies) {
            String label = getBestCurrencyDescription(currency, language);
            String value = currency.getCurrencyIsoName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultCurrencyChoice != null && defaultCurrencyChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && currency.getIsDefault()))
                defaultValue = value;
        }
        
        return new CurrencyChoicesBean(labels, values, defaultValue);
    }
    
    public CurrencyTransfer getCurrencyTransfer(UserVisit userVisit, Currency currency) {
        return getAccountingTransferCaches(userVisit).getCurrencyTransferCache().getTransfer(currency);
    }
    
    public List<CurrencyTransfer> getCurrencyTransfers(UserVisit userVisit, Collection<Currency> currencies) {
        List<CurrencyTransfer> currencyTransfers = null;
        
        if(currencies != null) {
            currencyTransfers = new ArrayList<>(currencies.size());
            
            for(Currency currency: currencies) {
                currencyTransfers.add(getAccountingTransferCaches(userVisit).getCurrencyTransferCache().getTransfer(currency));
            }
        }
        
        return currencyTransfers;
    }
    
    public List<CurrencyTransfer> getCurrencyTransfers(UserVisit userVisit) {
        return getCurrencyTransfers(userVisit, getCurrencies());
    }
    
    // --------------------------------------------------------------------------------
    // Currency Descriptions
    // --------------------------------------------------------------------------------
    
    public CurrencyDescription createCurrencyDescription(Currency currency, Language language, String description, BasePK createdBy) {
        CurrencyDescription currencyDescription = CurrencyDescriptionFactory.getInstance().create(currency, language, description);
        
        sendEventUsingNames(currency.getPrimaryKey(), EventTypes.MODIFY.name(), currencyDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return currencyDescription;
    }
    
    public CurrencyDescription getCurrencyDescription(Currency currency, Language language) {
        CurrencyDescription currencyDescription;
        
        try {
            PreparedStatement ps = CurrencyDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM currencydescriptions " +
                    "WHERE curd_cur_currencyid = ? AND curd_lang_languageid = ?");
            
            ps.setLong(1, currency.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            currencyDescription = CurrencyDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return currencyDescription;
    }
    
    private List<CurrencyDescription> getCurrencyDescriptionsByCurrency(Currency currency, EntityPermission entityPermission) {
        List<CurrencyDescription> currencyDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM currencydescriptions, languages " +
                        "WHERE curd_cur_currencyid = ? AND curd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM currencydescriptions " +
                        "WHERE curd_cur_currencyid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CurrencyDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, currency.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            currencyDescriptions = CurrencyDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return currencyDescriptions;
    }
    
    public List<CurrencyDescription> getCurrencyDescriptionsByCurrency(Currency currency) {
        return getCurrencyDescriptionsByCurrency(currency, EntityPermission.READ_ONLY);
    }
    
    public List<CurrencyDescription> getCurrencyDescriptionsByCurrencyForUpdate(Currency currency) {
        return getCurrencyDescriptionsByCurrency(currency, EntityPermission.READ_WRITE);
    }
    
    public String getBestCurrencyDescription(Currency currency, Language language) {
        String description;
        CurrencyDescription currencyDescription = getCurrencyDescription(currency, language);
        
        if(currencyDescription == null && !language.getIsDefault()) {
            currencyDescription = getCurrencyDescription(currency, getPartyControl().getDefaultLanguage());
        }
        
        if(currencyDescription == null) {
            description = currency.getCurrencyIsoName();
        } else {
            description = currencyDescription.getDescription();
        }
        
        return description;
    }
    
    public List<CurrencyDescriptionTransfer> getCurrencyDescriptionTransfers(UserVisit userVisit, Currency currency) {
        List<CurrencyDescription> currencyDescriptions = getCurrencyDescriptionsByCurrency(currency);
        List<CurrencyDescriptionTransfer> currencyDescriptionTransfers = new ArrayList<>(currencyDescriptions.size());
        CurrencyDescriptionTransferCache currencyDescriptionTransferCache = getAccountingTransferCaches(userVisit).getCurrencyDescriptionTransferCache();
        
        currencyDescriptions.stream().forEach((currencyDescription) -> {
            currencyDescriptionTransfers.add(currencyDescriptionTransferCache.getTransfer(currencyDescription));
        });
        
        return currencyDescriptionTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Accounting Categories
    // --------------------------------------------------------------------------------
    
    public ItemAccountingCategory createItemAccountingCategory(String itemAccountingCategoryName,
            ItemAccountingCategory parentItemAccountingCategory, GlAccount inventoryGlAccount, GlAccount salesGlAccount,
            GlAccount returnsGlAccount, GlAccount cogsGlAccount, GlAccount returnsCogsGlAccount, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        ItemAccountingCategory defaultItemAccountingCategory = getDefaultItemAccountingCategory();
        boolean defaultFound = defaultItemAccountingCategory != null;
        
        if(defaultFound && isDefault) {
            ItemAccountingCategoryDetailValue defaultItemAccountingCategoryDetailValue = getDefaultItemAccountingCategoryDetailValueForUpdate();
            
            defaultItemAccountingCategoryDetailValue.setIsDefault(Boolean.FALSE);
            updateItemAccountingCategoryFromValue(defaultItemAccountingCategoryDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ItemAccountingCategory itemAccountingCategory = ItemAccountingCategoryFactory.getInstance().create();
        ItemAccountingCategoryDetail itemAccountingCategoryDetail = ItemAccountingCategoryDetailFactory.getInstance().create(session,
                itemAccountingCategory, itemAccountingCategoryName, parentItemAccountingCategory, inventoryGlAccount,
                salesGlAccount, returnsGlAccount, cogsGlAccount, returnsCogsGlAccount, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        itemAccountingCategory = ItemAccountingCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemAccountingCategory.getPrimaryKey());
        itemAccountingCategory.setActiveDetail(itemAccountingCategoryDetail);
        itemAccountingCategory.setLastDetail(itemAccountingCategoryDetail);
        itemAccountingCategory.store();
        
        sendEventUsingNames(itemAccountingCategory.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return itemAccountingCategory;
    }
    
    private static final Map<EntityPermission, String> getItemAccountingCategoryByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "AND iactgcdt_itemaccountingcategoryname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "AND iactgcdt_itemaccountingcategoryname = ? " +
                "FOR UPDATE");
        getItemAccountingCategoryByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemAccountingCategory getItemAccountingCategoryByName(String itemAccountingCategoryName, EntityPermission entityPermission) {
        return ItemAccountingCategoryFactory.getInstance().getEntityFromQuery(entityPermission, getItemAccountingCategoryByNameQueries, itemAccountingCategoryName);
    }

    public ItemAccountingCategory getItemAccountingCategoryByName(String itemAccountingCategoryName) {
        return getItemAccountingCategoryByName(itemAccountingCategoryName, EntityPermission.READ_ONLY);
    }

    public ItemAccountingCategory getItemAccountingCategoryByNameForUpdate(String itemAccountingCategoryName) {
        return getItemAccountingCategoryByName(itemAccountingCategoryName, EntityPermission.READ_WRITE);
    }

    public ItemAccountingCategoryDetailValue getItemAccountingCategoryDetailValueForUpdate(ItemAccountingCategory itemAccountingCategory) {
        return itemAccountingCategory == null? null: itemAccountingCategory.getLastDetailForUpdate().getItemAccountingCategoryDetailValue().clone();
    }

    public ItemAccountingCategoryDetailValue getItemAccountingCategoryDetailValueByNameForUpdate(String itemAccountingCategoryName) {
        return getItemAccountingCategoryDetailValueForUpdate(getItemAccountingCategoryByNameForUpdate(itemAccountingCategoryName));
    }

    private static final Map<EntityPermission, String> getDefaultItemAccountingCategoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "AND iactgcdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "AND iactgcdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultItemAccountingCategoryQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemAccountingCategory getDefaultItemAccountingCategory(EntityPermission entityPermission) {
        return ItemAccountingCategoryFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultItemAccountingCategoryQueries);
    }

    public ItemAccountingCategory getDefaultItemAccountingCategory() {
        return getDefaultItemAccountingCategory(EntityPermission.READ_ONLY);
    }

    public ItemAccountingCategory getDefaultItemAccountingCategoryForUpdate() {
        return getDefaultItemAccountingCategory(EntityPermission.READ_WRITE);
    }

    public ItemAccountingCategoryDetailValue getDefaultItemAccountingCategoryDetailValueForUpdate() {
        return getDefaultItemAccountingCategoryForUpdate().getLastDetailForUpdate().getItemAccountingCategoryDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getItemAccountingCategoriesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "ORDER BY iactgcdt_sortorder, iactgcdt_itemaccountingcategoryname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid " +
                "FOR UPDATE");
        getItemAccountingCategoriesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemAccountingCategory> getItemAccountingCategories(EntityPermission entityPermission) {
        return ItemAccountingCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemAccountingCategoriesQueries);
    }

    public List<ItemAccountingCategory> getItemAccountingCategories() {
        return getItemAccountingCategories(EntityPermission.READ_ONLY);
    }

    public List<ItemAccountingCategory> getItemAccountingCategoriesForUpdate() {
        return getItemAccountingCategories(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemAccountingCategoriesByParentItemAccountingCategoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid AND iactgcdt_parentitemaccountingcategoryid = ? " +
                "ORDER BY iactgcdt_sortorder, iactgcdt_itemaccountingcategoryname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM itemaccountingcategories, itemaccountingcategorydetails " +
                "WHERE iactgc_activedetailid = iactgcdt_itemaccountingcategorydetailid AND iactgcdt_parentitemaccountingcategoryid = ? " +
                "FOR UPDATE");
        getItemAccountingCategoriesByParentItemAccountingCategoryQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemAccountingCategory> getItemAccountingCategoriesByParentItemAccountingCategory(ItemAccountingCategory parentItemAccountingCategory,
            EntityPermission entityPermission) {
        return ItemAccountingCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemAccountingCategoriesByParentItemAccountingCategoryQueries,
                parentItemAccountingCategory);
    }

    public List<ItemAccountingCategory> getItemAccountingCategoriesByParentItemAccountingCategory(ItemAccountingCategory parentItemAccountingCategory) {
        return getItemAccountingCategoriesByParentItemAccountingCategory(parentItemAccountingCategory, EntityPermission.READ_ONLY);
    }

    public List<ItemAccountingCategory> getItemAccountingCategoriesByParentItemAccountingCategoryForUpdate(ItemAccountingCategory parentItemAccountingCategory) {
        return getItemAccountingCategoriesByParentItemAccountingCategory(parentItemAccountingCategory, EntityPermission.READ_WRITE);
    }

    public ItemAccountingCategoryTransfer getItemAccountingCategoryTransfer(UserVisit userVisit, ItemAccountingCategory itemAccountingCategory) {
        return getAccountingTransferCaches(userVisit).getItemAccountingCategoryTransferCache().getTransfer(itemAccountingCategory);
    }
    
    public List<ItemAccountingCategoryTransfer> getItemAccountingCategoryTransfers(UserVisit userVisit) {
        List<ItemAccountingCategory> itemAccountingCategories = getItemAccountingCategories();
        List<ItemAccountingCategoryTransfer> itemAccountingCategoryTransfers = new ArrayList<>(itemAccountingCategories.size());
        ItemAccountingCategoryTransferCache itemAccountingCategoryTransferCache = getAccountingTransferCaches(userVisit).getItemAccountingCategoryTransferCache();
        
        itemAccountingCategories.stream().forEach((itemAccountingCategory) -> {
            itemAccountingCategoryTransfers.add(itemAccountingCategoryTransferCache.getTransfer(itemAccountingCategory));
        });
        
        return itemAccountingCategoryTransfers;
    }
    
    public ItemAccountingCategoryChoicesBean getItemAccountingCategoryChoices(String defaultItemAccountingCategoryChoice,
            Language language, boolean allowNullChoice) {
        List<ItemAccountingCategory> itemAccountingCategories = getItemAccountingCategories();
        int size = itemAccountingCategories.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemAccountingCategoryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(ItemAccountingCategory itemAccountingCategory: itemAccountingCategories) {
            ItemAccountingCategoryDetail itemAccountingCategoryDetail = itemAccountingCategory.getLastDetail();
            
            String label = getBestItemAccountingCategoryDescription(itemAccountingCategory, language);
            String value = itemAccountingCategoryDetail.getItemAccountingCategoryName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultItemAccountingCategoryChoice != null && defaultItemAccountingCategoryChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemAccountingCategoryDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemAccountingCategoryChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentItemAccountingCategorySafe(ItemAccountingCategory itemAccountingCategory,
            ItemAccountingCategory parentItemAccountingCategory) {
        boolean safe = true;
        
        if(parentItemAccountingCategory != null) {
            Set<ItemAccountingCategory> parentItemAccountingCategories = new HashSet<>();
            
            parentItemAccountingCategories.add(itemAccountingCategory);
            do {
                if(parentItemAccountingCategories.contains(parentItemAccountingCategory)) {
                    safe = false;
                    break;
                }
                
                parentItemAccountingCategories.add(parentItemAccountingCategory);
                parentItemAccountingCategory = parentItemAccountingCategory.getLastDetail().getParentItemAccountingCategory();
            } while(parentItemAccountingCategory != null);
        }
        
        return safe;
    }
    
    private void updateItemAccountingCategoryFromValue(ItemAccountingCategoryDetailValue itemAccountingCategoryDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(itemAccountingCategoryDetailValue.hasBeenModified()) {
            ItemAccountingCategory itemAccountingCategory = ItemAccountingCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemAccountingCategoryDetailValue.getItemAccountingCategoryPK());
            ItemAccountingCategoryDetail itemAccountingCategoryDetail = itemAccountingCategory.getActiveDetailForUpdate();
            
            itemAccountingCategoryDetail.setThruTime(session.START_TIME_LONG);
            itemAccountingCategoryDetail.store();
            
            ItemAccountingCategoryPK itemAccountingCategoryPK = itemAccountingCategoryDetail.getItemAccountingCategoryPK();
            String itemAccountingCategoryName = itemAccountingCategoryDetailValue.getItemAccountingCategoryName();
            ItemAccountingCategoryPK parentItemAccountingCategoryPK = itemAccountingCategoryDetailValue.getParentItemAccountingCategoryPK();
            GlAccountPK inventoryGlAccountPK = itemAccountingCategoryDetailValue.getInventoryGlAccountPK();
            GlAccountPK salesGlAccountPK = itemAccountingCategoryDetailValue.getSalesGlAccountPK();
            GlAccountPK returnsGlAccountPK = itemAccountingCategoryDetailValue.getReturnsCogsGlAccountPK();
            GlAccountPK cogsGlAccountPK = itemAccountingCategoryDetailValue.getCogsGlAccountPK();
            GlAccountPK returnsCogsGlAccountPK = itemAccountingCategoryDetailValue.getReturnsCogsGlAccountPK();
            Boolean isDefault = itemAccountingCategoryDetailValue.getIsDefault();
            Integer sortOrder = itemAccountingCategoryDetailValue.getSortOrder();
            
            if(checkDefault) {
                ItemAccountingCategory defaultItemAccountingCategory = getDefaultItemAccountingCategory();
                boolean defaultFound = defaultItemAccountingCategory != null && !defaultItemAccountingCategory.equals(itemAccountingCategory);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ItemAccountingCategoryDetailValue defaultItemAccountingCategoryDetailValue = getDefaultItemAccountingCategoryDetailValueForUpdate();
                    
                    defaultItemAccountingCategoryDetailValue.setIsDefault(Boolean.FALSE);
                    updateItemAccountingCategoryFromValue(defaultItemAccountingCategoryDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            itemAccountingCategoryDetail = ItemAccountingCategoryDetailFactory.getInstance().create(itemAccountingCategoryPK,
                    itemAccountingCategoryName, parentItemAccountingCategoryPK, inventoryGlAccountPK, salesGlAccountPK,
                    returnsGlAccountPK, cogsGlAccountPK, returnsCogsGlAccountPK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            itemAccountingCategory.setActiveDetail(itemAccountingCategoryDetail);
            itemAccountingCategory.setLastDetail(itemAccountingCategoryDetail);
            
            sendEventUsingNames(itemAccountingCategoryPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateItemAccountingCategoryFromValue(ItemAccountingCategoryDetailValue itemAccountingCategoryDetailValue, BasePK updatedBy) {
        updateItemAccountingCategoryFromValue(itemAccountingCategoryDetailValue, true, updatedBy);
    }
    
    private void deleteItemAccountingCategory(ItemAccountingCategory itemAccountingCategory, boolean checkDefault, BasePK deletedBy) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        ItemAccountingCategoryDetail itemAccountingCategoryDetail = itemAccountingCategory.getLastDetailForUpdate();
        
        deleteItemAccountingCategoriesByParentItemAccountingCategory(itemAccountingCategory, deletedBy);
        deleteItemAccountingCategoryDescriptionsByItemAccountingCategory(itemAccountingCategory, deletedBy);
        inventoryControl.deleteInventoryConditionGlAccountsByItemAccountingCategory(itemAccountingCategory, deletedBy);
        
        itemAccountingCategoryDetail.setThruTime(session.START_TIME_LONG);
        itemAccountingCategory.setActiveDetail(null);
        itemAccountingCategory.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            ItemAccountingCategory defaultItemAccountingCategory = getDefaultItemAccountingCategory();

            if(defaultItemAccountingCategory == null) {
                List<ItemAccountingCategory> itemAccountingCategories = getItemAccountingCategoriesForUpdate();

                if(!itemAccountingCategories.isEmpty()) {
                    Iterator<ItemAccountingCategory> iter = itemAccountingCategories.iterator();
                    if(iter.hasNext()) {
                        defaultItemAccountingCategory = iter.next();
                    }
                    ItemAccountingCategoryDetailValue itemAccountingCategoryDetailValue = defaultItemAccountingCategory.getLastDetailForUpdate().getItemAccountingCategoryDetailValue().clone();

                    itemAccountingCategoryDetailValue.setIsDefault(Boolean.TRUE);
                    updateItemAccountingCategoryFromValue(itemAccountingCategoryDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(itemAccountingCategory.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteItemAccountingCategory(ItemAccountingCategory itemAccountingCategory, BasePK deletedBy) {
        deleteItemAccountingCategory(itemAccountingCategory, true, deletedBy);
    }

    private void deleteItemAccountingCategories(List<ItemAccountingCategory> itemAccountingCategories, boolean checkDefault, BasePK deletedBy) {
        itemAccountingCategories.stream().forEach((itemAccountingCategory) -> {
            deleteItemAccountingCategory(itemAccountingCategory, checkDefault, deletedBy);
        });
    }

    public void deleteItemAccountingCategories(List<ItemAccountingCategory> itemAccountingCategories, BasePK deletedBy) {
        deleteItemAccountingCategories(itemAccountingCategories, true, deletedBy);
    }

    private void deleteItemAccountingCategoriesByParentItemAccountingCategory(ItemAccountingCategory parentItemAccountingCategory, BasePK deletedBy) {
        deleteItemAccountingCategories(getItemAccountingCategoriesByParentItemAccountingCategoryForUpdate(parentItemAccountingCategory), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Accounting Category Descriptions
    // --------------------------------------------------------------------------------
    
    public ItemAccountingCategoryDescription createItemAccountingCategoryDescription(ItemAccountingCategory itemAccountingCategory,
            Language language, String description, BasePK createdBy) {
        ItemAccountingCategoryDescription itemAccountingCategoryDescription = ItemAccountingCategoryDescriptionFactory.getInstance().create(itemAccountingCategory, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(itemAccountingCategory.getPrimaryKey(), EventTypes.MODIFY.name(), itemAccountingCategoryDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return itemAccountingCategoryDescription;
    }
    
    private ItemAccountingCategoryDescription getItemAccountingCategoryDescription(ItemAccountingCategory itemAccountingCategory,
            Language language, EntityPermission entityPermission) {
        ItemAccountingCategoryDescription itemAccountingCategoryDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaccountingcategorydescriptions " +
                        "WHERE iactgcd_iactgc_itemaccountingcategoryid = ? AND iactgcd_lang_languageid = ? AND iactgcd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaccountingcategorydescriptions " +
                        "WHERE iactgcd_iactgc_itemaccountingcategoryid = ? AND iactgcd_lang_languageid = ? AND iactgcd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemAccountingCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemAccountingCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            itemAccountingCategoryDescription = ItemAccountingCategoryDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAccountingCategoryDescription;
    }
    
    public ItemAccountingCategoryDescription getItemAccountingCategoryDescription(ItemAccountingCategory itemAccountingCategory,
            Language language) {
        return getItemAccountingCategoryDescription(itemAccountingCategory, language, EntityPermission.READ_ONLY);
    }
    
    public ItemAccountingCategoryDescription getItemAccountingCategoryDescriptionForUpdate(ItemAccountingCategory itemAccountingCategory,
            Language language) {
        return getItemAccountingCategoryDescription(itemAccountingCategory, language, EntityPermission.READ_WRITE);
    }
    
    public ItemAccountingCategoryDescriptionValue getItemAccountingCategoryDescriptionValue(ItemAccountingCategoryDescription itemAccountingCategoryDescription) {
        return itemAccountingCategoryDescription == null? null: itemAccountingCategoryDescription.getItemAccountingCategoryDescriptionValue().clone();
    }
    
    public ItemAccountingCategoryDescriptionValue getItemAccountingCategoryDescriptionValueForUpdate(ItemAccountingCategory itemAccountingCategory, Language language) {
        return getItemAccountingCategoryDescriptionValue(getItemAccountingCategoryDescriptionForUpdate(itemAccountingCategory, language));
    }
    
    private List<ItemAccountingCategoryDescription> getItemAccountingCategoryDescriptionsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory,
            EntityPermission entityPermission) {
        List<ItemAccountingCategoryDescription> itemAccountingCategoryDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaccountingcategorydescriptions, languages " +
                        "WHERE iactgcd_iactgc_itemaccountingcategoryid = ? AND iactgcd_thrutime = ? AND iactgcd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM itemaccountingcategorydescriptions " +
                        "WHERE iactgcd_iactgc_itemaccountingcategoryid = ? AND iactgcd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ItemAccountingCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemAccountingCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            itemAccountingCategoryDescriptions = ItemAccountingCategoryDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return itemAccountingCategoryDescriptions;
    }
    
    public List<ItemAccountingCategoryDescription> getItemAccountingCategoryDescriptionsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory) {
        return getItemAccountingCategoryDescriptionsByItemAccountingCategory(itemAccountingCategory, EntityPermission.READ_ONLY);
    }
    
    public List<ItemAccountingCategoryDescription> getItemAccountingCategoryDescriptionsByItemAccountingCategoryForUpdate(ItemAccountingCategory itemAccountingCategory) {
        return getItemAccountingCategoryDescriptionsByItemAccountingCategory(itemAccountingCategory, EntityPermission.READ_WRITE);
    }
    
    public String getBestItemAccountingCategoryDescription(ItemAccountingCategory itemAccountingCategory, Language language) {
        String description;
        ItemAccountingCategoryDescription itemAccountingCategoryDescription = getItemAccountingCategoryDescription(itemAccountingCategory, language);
        
        if(itemAccountingCategoryDescription == null && !language.getIsDefault()) {
            itemAccountingCategoryDescription = getItemAccountingCategoryDescription(itemAccountingCategory, getPartyControl().getDefaultLanguage());
        }
        
        if(itemAccountingCategoryDescription == null) {
            description = itemAccountingCategory.getLastDetail().getItemAccountingCategoryName();
        } else {
            description = itemAccountingCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public ItemAccountingCategoryDescriptionTransfer getItemAccountingCategoryDescriptionTransfer(UserVisit userVisit,
            ItemAccountingCategoryDescription itemAccountingCategoryDescription) {
        return getAccountingTransferCaches(userVisit).getItemAccountingCategoryDescriptionTransferCache().getTransfer(itemAccountingCategoryDescription);
    }
    
    public List<ItemAccountingCategoryDescriptionTransfer> getItemAccountingCategoryDescriptionTransfersByItemAccountingCategory(UserVisit userVisit,
            ItemAccountingCategory itemAccountingCategory) {
        List<ItemAccountingCategoryDescription> itemAccountingCategoryDescriptions = getItemAccountingCategoryDescriptionsByItemAccountingCategory(itemAccountingCategory);
        List<ItemAccountingCategoryDescriptionTransfer> itemAccountingCategoryDescriptionTransfers = new ArrayList<>(itemAccountingCategoryDescriptions.size());
        ItemAccountingCategoryDescriptionTransferCache itemAccountingCategoryDescriptionTransferCache = getAccountingTransferCaches(userVisit).getItemAccountingCategoryDescriptionTransferCache();
        
        itemAccountingCategoryDescriptions.stream().forEach((itemAccountingCategoryDescription) -> {
            itemAccountingCategoryDescriptionTransfers.add(itemAccountingCategoryDescriptionTransferCache.getTransfer(itemAccountingCategoryDescription));
        });
        
        return itemAccountingCategoryDescriptionTransfers;
    }
    
    public void updateItemAccountingCategoryDescriptionFromValue(ItemAccountingCategoryDescriptionValue itemAccountingCategoryDescriptionValue,
            BasePK updatedBy) {
        if(itemAccountingCategoryDescriptionValue.hasBeenModified()) {
            ItemAccountingCategoryDescription itemAccountingCategoryDescription = ItemAccountingCategoryDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, itemAccountingCategoryDescriptionValue.getPrimaryKey());
            
            itemAccountingCategoryDescription.setThruTime(session.START_TIME_LONG);
            itemAccountingCategoryDescription.store();
            
            ItemAccountingCategory itemAccountingCategory = itemAccountingCategoryDescription.getItemAccountingCategory();
            Language language = itemAccountingCategoryDescription.getLanguage();
            String description = itemAccountingCategoryDescriptionValue.getDescription();
            
            itemAccountingCategoryDescription = ItemAccountingCategoryDescriptionFactory.getInstance().create(itemAccountingCategory, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(itemAccountingCategory.getPrimaryKey(), EventTypes.MODIFY.name(), itemAccountingCategoryDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteItemAccountingCategoryDescription(ItemAccountingCategoryDescription itemAccountingCategoryDescription,
            BasePK deletedBy) {
        itemAccountingCategoryDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(itemAccountingCategoryDescription.getItemAccountingCategoryPK(), EventTypes.MODIFY.name(), itemAccountingCategoryDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteItemAccountingCategoryDescriptionsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory,
            BasePK deletedBy) {
        List<ItemAccountingCategoryDescription> itemAccountingCategoryDescriptions = getItemAccountingCategoryDescriptionsByItemAccountingCategoryForUpdate(itemAccountingCategory);
        
        itemAccountingCategoryDescriptions.stream().forEach((itemAccountingCategoryDescription) -> {
            deleteItemAccountingCategoryDescription(itemAccountingCategoryDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Types
    // --------------------------------------------------------------------------------
    
    public GlAccountType createGlAccountType(String glAccountTypeName, Boolean isDefault, Integer sortOrder) {
        return GlAccountTypeFactory.getInstance().create(glAccountTypeName, isDefault, sortOrder);
    }
    
    public GlAccountType getGlAccountTypeByName(String glAccountTypeName) {
        GlAccountType glAccountType;
        
        try {
            PreparedStatement ps = GlAccountTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM glaccounttypes " +
                    "WHERE glatyp_glaccounttypename = ?");
            
            ps.setString(1, glAccountTypeName);
            
            glAccountType = GlAccountTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountType;
    }
    
    public List<GlAccountType> getGlAccountTypes() {
        PreparedStatement ps = GlAccountTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM glaccounttypes " +
                "ORDER BY glatyp_sortorder, glatyp_glaccounttypename");
        
        return GlAccountTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public GlAccountTypeChoicesBean getGlAccountTypeChoices(String defaultGlAccountTypeChoice, Language language, boolean allowNullChoice) {
        List<GlAccountType> glAccountTypes = getGlAccountTypes();
        int size = glAccountTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlAccountTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(GlAccountType glAccountType: glAccountTypes) {
            String label = getBestGlAccountTypeDescription(glAccountType, language);
            String value = glAccountType.getGlAccountTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultGlAccountTypeChoice != null && defaultGlAccountTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && glAccountType.getIsDefault()))
                defaultValue = value;
        }
        
        return new GlAccountTypeChoicesBean(labels, values, defaultValue);
    }
    
    public GlAccountTypeTransfer getGlAccountTypeTransfer(UserVisit userVisit, GlAccountType glAccountType) {
        return getAccountingTransferCaches(userVisit).getGlAccountTypeTransferCache().getTransfer(glAccountType);
    }
    
    public List<GlAccountTypeTransfer> getGlAccountTypeTransfers(UserVisit userVisit) {
        List<GlAccountType> glAccountTypes = getGlAccountTypes();
        List<GlAccountTypeTransfer> glAccountTypeTransfers = new ArrayList<>(glAccountTypes.size());
        GlAccountTypeTransferCache glAccountTypeTransferCache = getAccountingTransferCaches(userVisit).getGlAccountTypeTransferCache();
        
        glAccountTypes.stream().forEach((glAccountType) -> {
            glAccountTypeTransfers.add(glAccountTypeTransferCache.getTransfer(glAccountType));
        });
        
        return glAccountTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Type Descriptions
    // --------------------------------------------------------------------------------
    
    public GlAccountTypeDescription createGlAccountTypeDescription(GlAccountType glAccountType, Language language, String description) {
        return GlAccountTypeDescriptionFactory.getInstance().create(glAccountType, language, description);
    }
    
    public GlAccountTypeDescription getGlAccountTypeDescription(GlAccountType glAccountType, Language language) {
        GlAccountTypeDescription glAccountTypeDescription;
        
        try {
            PreparedStatement ps = GlAccountTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM glaccounttypedescriptions " +
                    "WHERE glatypd_glatyp_glaccounttypeid = ? AND glatypd_lang_languageid = ?");
            
            ps.setLong(1, glAccountType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            glAccountTypeDescription = GlAccountTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountTypeDescription;
    }
    
    public String getBestGlAccountTypeDescription(GlAccountType glAccountType, Language language) {
        String description;
        GlAccountTypeDescription glAccountTypeDescription = getGlAccountTypeDescription(glAccountType, language);
        
        if(glAccountTypeDescription == null && !language.getIsDefault()) {
            glAccountTypeDescription = getGlAccountTypeDescription(glAccountType, getPartyControl().getDefaultLanguage());
        }
        
        if(glAccountTypeDescription == null) {
            description = glAccountType.getGlAccountTypeName();
        } else {
            description = glAccountTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Classes
    // --------------------------------------------------------------------------------
    
    public GlAccountClass createGlAccountClass(String glAccountClassName, GlAccountClass parentGlAccountClass, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        GlAccountClass defaultGlAccountClass = getDefaultGlAccountClass();
        boolean defaultFound = defaultGlAccountClass != null;
        
        if(defaultFound && isDefault) {
            GlAccountClassDetailValue defaultGlAccountClassDetailValue = getDefaultGlAccountClassDetailValueForUpdate();
            
            defaultGlAccountClassDetailValue.setIsDefault(Boolean.FALSE);
            updateGlAccountClassFromValue(defaultGlAccountClassDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GlAccountClass glAccountClass = GlAccountClassFactory.getInstance().create();
        GlAccountClassDetail glAccountClassDetail = GlAccountClassDetailFactory.getInstance().create(session,
                glAccountClass, glAccountClassName, parentGlAccountClass, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        glAccountClass = GlAccountClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glAccountClass.getPrimaryKey());
        glAccountClass.setActiveDetail(glAccountClassDetail);
        glAccountClass.setLastDetail(glAccountClassDetail);
        glAccountClass.store();
        
        sendEventUsingNames(glAccountClass.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return glAccountClass;
    }
    
    private static final Map<EntityPermission, String> getGlAccountClassByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "AND glaclsdt_glaccountclassname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "AND glaclsdt_glaccountclassname = ? " +
                "FOR UPDATE");
        getGlAccountClassByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private GlAccountClass getGlAccountClassByName(String glAccountClassName, EntityPermission entityPermission) {
        return GlAccountClassFactory.getInstance().getEntityFromQuery(entityPermission, getGlAccountClassByNameQueries, glAccountClassName);
    }

    public GlAccountClass getGlAccountClassByName(String glAccountClassName) {
        return getGlAccountClassByName(glAccountClassName, EntityPermission.READ_ONLY);
    }

    public GlAccountClass getGlAccountClassByNameForUpdate(String glAccountClassName) {
        return getGlAccountClassByName(glAccountClassName, EntityPermission.READ_WRITE);
    }

    public GlAccountClassDetailValue getGlAccountClassDetailValueForUpdate(GlAccountClass glAccountClass) {
        return glAccountClass == null? null: glAccountClass.getLastDetailForUpdate().getGlAccountClassDetailValue().clone();
    }

    public GlAccountClassDetailValue getGlAccountClassDetailValueByNameForUpdate(String glAccountClassName) {
        return getGlAccountClassDetailValueForUpdate(getGlAccountClassByNameForUpdate(glAccountClassName));
    }

    private static final Map<EntityPermission, String> getDefaultGlAccountClassQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "AND glaclsdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "AND glaclsdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultGlAccountClassQueries = Collections.unmodifiableMap(queryMap);
    }

    private GlAccountClass getDefaultGlAccountClass(EntityPermission entityPermission) {
        return GlAccountClassFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultGlAccountClassQueries);
    }

    public GlAccountClass getDefaultGlAccountClass() {
        return getDefaultGlAccountClass(EntityPermission.READ_ONLY);
    }

    public GlAccountClass getDefaultGlAccountClassForUpdate() {
        return getDefaultGlAccountClass(EntityPermission.READ_WRITE);
    }

    public GlAccountClassDetailValue getDefaultGlAccountClassDetailValueForUpdate() {
        return getDefaultGlAccountClassForUpdate().getLastDetailForUpdate().getGlAccountClassDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getGlAccountClassesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "ORDER BY glaclsdt_sortorder, glaclsdt_glaccountclassname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid " +
                "FOR UPDATE");
        getGlAccountClassesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GlAccountClass> getGlAccountClasses(EntityPermission entityPermission) {
        return GlAccountClassFactory.getInstance().getEntitiesFromQuery(entityPermission, getGlAccountClassesQueries);
    }

    public List<GlAccountClass> getGlAccountClasses() {
        return getGlAccountClasses(EntityPermission.READ_ONLY);
    }

    public List<GlAccountClass> getGlAccountClassesForUpdate() {
        return getGlAccountClasses(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getGlAccountClassesByParentGlAccountClassQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid AND glaclsdt_parentglaccountclassid = ? " +
                "ORDER BY glaclsdt_sortorder, glaclsdt_glaccountclassname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountclasses, glaccountclassdetails " +
                "WHERE glacls_activedetailid = glaclsdt_glaccountclassdetailid AND glaclsdt_parentglaccountclassid = ? " +
                "FOR UPDATE");
        getGlAccountClassesByParentGlAccountClassQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GlAccountClass> getGlAccountClassesByParentGlAccountClass(GlAccountClass parentGlAccountClass,
            EntityPermission entityPermission) {
        return GlAccountClassFactory.getInstance().getEntitiesFromQuery(entityPermission, getGlAccountClassesByParentGlAccountClassQueries,
                parentGlAccountClass);
    }

    public List<GlAccountClass> getGlAccountClassesByParentGlAccountClass(GlAccountClass parentGlAccountClass) {
        return getGlAccountClassesByParentGlAccountClass(parentGlAccountClass, EntityPermission.READ_ONLY);
    }

    public List<GlAccountClass> getGlAccountClassesByParentGlAccountClassForUpdate(GlAccountClass parentGlAccountClass) {
        return getGlAccountClassesByParentGlAccountClass(parentGlAccountClass, EntityPermission.READ_WRITE);
    }

    public GlAccountClassTransfer getGlAccountClassTransfer(UserVisit userVisit, GlAccountClass glAccountClass) {
        return getAccountingTransferCaches(userVisit).getGlAccountClassTransferCache().getTransfer(glAccountClass);
    }
    
    public List<GlAccountClassTransfer> getGlAccountClassTransfers(UserVisit userVisit) {
        List<GlAccountClass> glAccountClasses = getGlAccountClasses();
        List<GlAccountClassTransfer> glAccountClassTransfers = new ArrayList<>(glAccountClasses.size());
        GlAccountClassTransferCache glAccountClassTransferCache = getAccountingTransferCaches(userVisit).getGlAccountClassTransferCache();
        
        glAccountClasses.stream().forEach((glAccountClass) -> {
            glAccountClassTransfers.add(glAccountClassTransferCache.getTransfer(glAccountClass));
        });
        
        return glAccountClassTransfers;
    }
    
    public GlAccountClassChoicesBean getGlAccountClassChoices(String defaultGlAccountClassChoice, Language language,
            boolean allowNullChoice) {
        List<GlAccountClass> glAccountClasses = getGlAccountClasses();
        int size = glAccountClasses.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlAccountClassChoice == null) {
                defaultValue = "";
            }
        }
        
        for(GlAccountClass glAccountClass: glAccountClasses) {
            GlAccountClassDetail glAccountClassDetail = glAccountClass.getLastDetail();
            
            String label = getBestGlAccountClassDescription(glAccountClass, language);
            String value = glAccountClassDetail.getGlAccountClassName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultGlAccountClassChoice != null && defaultGlAccountClassChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && glAccountClassDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GlAccountClassChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentGlAccountClassSafe(GlAccountClass glAccountClass, GlAccountClass parentGlAccountClass) {
        boolean safe = true;
        
        if(parentGlAccountClass != null) {
            Set<GlAccountClass> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(glAccountClass);
            do {
                if(parentItemPurchasingCategories.contains(parentGlAccountClass)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentGlAccountClass);
                parentGlAccountClass = parentGlAccountClass.getLastDetail().getParentGlAccountClass();
            } while(parentGlAccountClass != null);
        }
        
        return safe;
    }
    
    private void updateGlAccountClassFromValue(GlAccountClassDetailValue glAccountClassDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(glAccountClassDetailValue.hasBeenModified()) {
            GlAccountClass glAccountClass = GlAccountClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     glAccountClassDetailValue.getGlAccountClassPK());
            GlAccountClassDetail glAccountClassDetail = glAccountClass.getActiveDetailForUpdate();
            
            glAccountClassDetail.setThruTime(session.START_TIME_LONG);
            glAccountClassDetail.store();
            
            GlAccountClassPK glAccountClassPK = glAccountClassDetail.getGlAccountClassPK();
            String glAccountClassName = glAccountClassDetailValue.getGlAccountClassName();
            GlAccountClassPK parentGlAccountClassPK = glAccountClassDetailValue.getParentGlAccountClassPK();
            Boolean isDefault = glAccountClassDetailValue.getIsDefault();
            Integer sortOrder = glAccountClassDetailValue.getSortOrder();
            
            if(checkDefault) {
                GlAccountClass defaultGlAccountClass = getDefaultGlAccountClass();
                boolean defaultFound = defaultGlAccountClass != null && !defaultGlAccountClass.equals(glAccountClass);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GlAccountClassDetailValue defaultGlAccountClassDetailValue = getDefaultGlAccountClassDetailValueForUpdate();
                    
                    defaultGlAccountClassDetailValue.setIsDefault(Boolean.FALSE);
                    updateGlAccountClassFromValue(defaultGlAccountClassDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            glAccountClassDetail = GlAccountClassDetailFactory.getInstance().create(glAccountClassPK,
                    glAccountClassName, parentGlAccountClassPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            glAccountClass.setActiveDetail(glAccountClassDetail);
            glAccountClass.setLastDetail(glAccountClassDetail);
            
            sendEventUsingNames(glAccountClassPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateGlAccountClassFromValue(GlAccountClassDetailValue glAccountClassDetailValue, BasePK updatedBy) {
        updateGlAccountClassFromValue(glAccountClassDetailValue, true, updatedBy);
    }
    
    private void deleteGlAccountClass(GlAccountClass glAccountClass, boolean checkDefault, BasePK deletedBy) {
        GlAccountClassDetail glAccountClassDetail = glAccountClass.getLastDetailForUpdate();

        deleteGlAccountClassesByParentGlAccountClass(glAccountClass, deletedBy);
        deleteGlAccountsByGlAccountClass(glAccountClass, deletedBy);
        deleteGlAccountClassDescriptionsByGlAccountClass(glAccountClass, deletedBy);
        
        glAccountClassDetail.setThruTime(session.START_TIME_LONG);
        glAccountClass.setActiveDetail(null);
        glAccountClass.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            GlAccountClass defaultGlAccountClass = getDefaultGlAccountClass();

            if(defaultGlAccountClass == null) {
                List<GlAccountClass> glAccountClasses = getGlAccountClassesForUpdate();

                if(!glAccountClasses.isEmpty()) {
                    Iterator<GlAccountClass> iter = glAccountClasses.iterator();
                    if(iter.hasNext()) {
                        defaultGlAccountClass = iter.next();
                    }
                    GlAccountClassDetailValue glAccountClassDetailValue = defaultGlAccountClass.getLastDetailForUpdate().getGlAccountClassDetailValue().clone();

                    glAccountClassDetailValue.setIsDefault(Boolean.TRUE);
                    updateGlAccountClassFromValue(glAccountClassDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(glAccountClass.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteGlAccountClass(GlAccountClass glAccountClass, BasePK deletedBy) {
        deleteGlAccountClass(glAccountClass, true, deletedBy);
    }

    private void deleteGlAccountClasses(List<GlAccountClass> glAccountClasses, boolean checkDefault, BasePK deletedBy) {
        glAccountClasses.stream().forEach((glAccountClass) -> {
            deleteGlAccountClass(glAccountClass, checkDefault, deletedBy);
        });
    }

    public void deleteGlAccountClasses(List<GlAccountClass> glAccountClasses, BasePK deletedBy) {
        deleteGlAccountClasses(glAccountClasses, true, deletedBy);
    }

    private void deleteGlAccountClassesByParentGlAccountClass(GlAccountClass parentGlAccountClass, BasePK deletedBy) {
        deleteGlAccountClasses(getGlAccountClassesByParentGlAccountClassForUpdate(parentGlAccountClass), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Gl Account Class Descriptions
    // --------------------------------------------------------------------------------
    
    public GlAccountClassDescription createGlAccountClassDescription(GlAccountClass glAccountClass, Language language, String description, BasePK createdBy) {
        GlAccountClassDescription glAccountClassDescription = GlAccountClassDescriptionFactory.getInstance().create(glAccountClass, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(glAccountClass.getPrimaryKey(), EventTypes.MODIFY.name(), glAccountClassDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return glAccountClassDescription;
    }
    
    private GlAccountClassDescription getGlAccountClassDescription(GlAccountClass glAccountClass, Language language, EntityPermission entityPermission) {
        GlAccountClassDescription glAccountClassDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountclassdescriptions " +
                        "WHERE glaclsd_glacls_glaccountclassid = ? AND glaclsd_lang_languageid = ? AND glaclsd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountclassdescriptions " +
                        "WHERE glaclsd_glacls_glaccountclassid = ? AND glaclsd_lang_languageid = ? AND glaclsd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountClassDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountClass.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            glAccountClassDescription = GlAccountClassDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountClassDescription;
    }
    
    public GlAccountClassDescription getGlAccountClassDescription(GlAccountClass glAccountClass, Language language) {
        return getGlAccountClassDescription(glAccountClass, language, EntityPermission.READ_ONLY);
    }
    
    public GlAccountClassDescription getGlAccountClassDescriptionForUpdate(GlAccountClass glAccountClass, Language language) {
        return getGlAccountClassDescription(glAccountClass, language, EntityPermission.READ_WRITE);
    }
    
    public GlAccountClassDescriptionValue getGlAccountClassDescriptionValue(GlAccountClassDescription glAccountClassDescription) {
        return glAccountClassDescription == null? null: glAccountClassDescription.getGlAccountClassDescriptionValue().clone();
    }
    
    public GlAccountClassDescriptionValue getGlAccountClassDescriptionValueForUpdate(GlAccountClass glAccountClass, Language language) {
        return getGlAccountClassDescriptionValue(getGlAccountClassDescriptionForUpdate(glAccountClass, language));
    }
    
    private List<GlAccountClassDescription> getGlAccountClassDescriptionsByGlAccountClass(GlAccountClass glAccountClass, EntityPermission entityPermission) {
        List<GlAccountClassDescription> glAccountClassDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountclassdescriptions, languages " +
                        "WHERE glaclsd_glacls_glaccountclassid = ? AND glaclsd_thrutime = ? AND glaclsd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountclassdescriptions " +
                        "WHERE glaclsd_glacls_glaccountclassid = ? AND glaclsd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountClassDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountClass.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            glAccountClassDescriptions = GlAccountClassDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountClassDescriptions;
    }
    
    public List<GlAccountClassDescription> getGlAccountClassDescriptionsByGlAccountClass(GlAccountClass glAccountClass) {
        return getGlAccountClassDescriptionsByGlAccountClass(glAccountClass, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccountClassDescription> getGlAccountClassDescriptionsByGlAccountClassForUpdate(GlAccountClass glAccountClass) {
        return getGlAccountClassDescriptionsByGlAccountClass(glAccountClass, EntityPermission.READ_WRITE);
    }
    
    public String getBestGlAccountClassDescription(GlAccountClass glAccountClass, Language language) {
        String description;
        GlAccountClassDescription glAccountClassDescription = getGlAccountClassDescription(glAccountClass, language);
        
        if(glAccountClassDescription == null && !language.getIsDefault()) {
            glAccountClassDescription = getGlAccountClassDescription(glAccountClass, getPartyControl().getDefaultLanguage());
        }
        
        if(glAccountClassDescription == null) {
            description = glAccountClass.getLastDetail().getGlAccountClassName();
        } else {
            description = glAccountClassDescription.getDescription();
        }
        
        return description;
    }
    
    public GlAccountClassDescriptionTransfer getGlAccountClassDescriptionTransfer(UserVisit userVisit, GlAccountClassDescription glAccountClassDescription) {
        return getAccountingTransferCaches(userVisit).getGlAccountClassDescriptionTransferCache().getTransfer(glAccountClassDescription);
    }
    
    public List<GlAccountClassDescriptionTransfer> getGlAccountClassDescriptionTransfersByGlAccountClass(UserVisit userVisit, GlAccountClass glAccountClass) {
        List<GlAccountClassDescription> glAccountClassDescriptions = getGlAccountClassDescriptionsByGlAccountClass(glAccountClass);
        List<GlAccountClassDescriptionTransfer> glAccountClassDescriptionTransfers = new ArrayList<>(glAccountClassDescriptions.size());
        GlAccountClassDescriptionTransferCache glAccountClassDescriptionTransferCache = getAccountingTransferCaches(userVisit).getGlAccountClassDescriptionTransferCache();
        
        glAccountClassDescriptions.stream().forEach((glAccountClassDescription) -> {
            glAccountClassDescriptionTransfers.add(glAccountClassDescriptionTransferCache.getTransfer(glAccountClassDescription));
        });
        
        return glAccountClassDescriptionTransfers;
    }
    
    public void updateGlAccountClassDescriptionFromValue(GlAccountClassDescriptionValue glAccountClassDescriptionValue, BasePK updatedBy) {
        if(glAccountClassDescriptionValue.hasBeenModified()) {
            GlAccountClassDescription glAccountClassDescription = GlAccountClassDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glAccountClassDescriptionValue.getPrimaryKey());
            
            glAccountClassDescription.setThruTime(session.START_TIME_LONG);
            glAccountClassDescription.store();
            
            GlAccountClass glAccountClass = glAccountClassDescription.getGlAccountClass();
            Language language = glAccountClassDescription.getLanguage();
            String description = glAccountClassDescriptionValue.getDescription();
            
            glAccountClassDescription = GlAccountClassDescriptionFactory.getInstance().create(glAccountClass, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(glAccountClass.getPrimaryKey(), EventTypes.MODIFY.name(), glAccountClassDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteGlAccountClassDescription(GlAccountClassDescription glAccountClassDescription, BasePK deletedBy) {
        glAccountClassDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(glAccountClassDescription.getGlAccountClassPK(), EventTypes.MODIFY.name(), glAccountClassDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteGlAccountClassDescriptionsByGlAccountClass(GlAccountClass glAccountClass, BasePK deletedBy) {
        List<GlAccountClassDescription> glAccountClassDescriptions = getGlAccountClassDescriptionsByGlAccountClassForUpdate(glAccountClass);
        
        glAccountClassDescriptions.stream().forEach((glAccountClassDescription) -> {
            deleteGlAccountClassDescription(glAccountClassDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Categories
    // --------------------------------------------------------------------------------
    
    public GlAccountCategory createGlAccountCategory(String glAccountCategoryName, GlAccountCategory parentGlAccountCategory, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        GlAccountCategory defaultGlAccountCategory = getDefaultGlAccountCategory();
        boolean defaultFound = defaultGlAccountCategory != null;
        
        if(defaultFound && isDefault) {
            GlAccountCategoryDetailValue defaultGlAccountCategoryDetailValue = getDefaultGlAccountCategoryDetailValueForUpdate();
            
            defaultGlAccountCategoryDetailValue.setIsDefault(Boolean.FALSE);
            updateGlAccountCategoryFromValue(defaultGlAccountCategoryDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GlAccountCategory glAccountCategory = GlAccountCategoryFactory.getInstance().create();
        GlAccountCategoryDetail glAccountCategoryDetail = GlAccountCategoryDetailFactory.getInstance().create(session,
                glAccountCategory, glAccountCategoryName, parentGlAccountCategory, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        glAccountCategory = GlAccountCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                glAccountCategory.getPrimaryKey());
        glAccountCategory.setActiveDetail(glAccountCategoryDetail);
        glAccountCategory.setLastDetail(glAccountCategoryDetail);
        glAccountCategory.store();
        
        sendEventUsingNames(glAccountCategory.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return glAccountCategory;
    }
    
    private static final Map<EntityPermission, String> getGlAccountCategoryByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "AND glacdt_glaccountcategoryname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "AND glacdt_glaccountcategoryname = ? " +
                "FOR UPDATE");
        getGlAccountCategoryByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private GlAccountCategory getGlAccountCategoryByName(String glAccountCategoryName, EntityPermission entityPermission) {
        return GlAccountCategoryFactory.getInstance().getEntityFromQuery(entityPermission, getGlAccountCategoryByNameQueries, glAccountCategoryName);
    }

    public GlAccountCategory getGlAccountCategoryByName(String glAccountCategoryName) {
        return getGlAccountCategoryByName(glAccountCategoryName, EntityPermission.READ_ONLY);
    }

    public GlAccountCategory getGlAccountCategoryByNameForUpdate(String glAccountCategoryName) {
        return getGlAccountCategoryByName(glAccountCategoryName, EntityPermission.READ_WRITE);
    }

    public GlAccountCategoryDetailValue getGlAccountCategoryDetailValueForUpdate(GlAccountCategory glAccountCategory) {
        return glAccountCategory == null? null: glAccountCategory.getLastDetailForUpdate().getGlAccountCategoryDetailValue().clone();
    }

    public GlAccountCategoryDetailValue getGlAccountCategoryDetailValueByNameForUpdate(String glAccountCategoryName) {
        return getGlAccountCategoryDetailValueForUpdate(getGlAccountCategoryByNameForUpdate(glAccountCategoryName));
    }

    private static final Map<EntityPermission, String> getDefaultGlAccountCategoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "AND glacdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "AND glacdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultGlAccountCategoryQueries = Collections.unmodifiableMap(queryMap);
    }

    private GlAccountCategory getDefaultGlAccountCategory(EntityPermission entityPermission) {
        return GlAccountCategoryFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultGlAccountCategoryQueries);
    }

    public GlAccountCategory getDefaultGlAccountCategory() {
        return getDefaultGlAccountCategory(EntityPermission.READ_ONLY);
    }

    public GlAccountCategory getDefaultGlAccountCategoryForUpdate() {
        return getDefaultGlAccountCategory(EntityPermission.READ_WRITE);
    }

    public GlAccountCategoryDetailValue getDefaultGlAccountCategoryDetailValueForUpdate() {
        return getDefaultGlAccountCategoryForUpdate().getLastDetailForUpdate().getGlAccountCategoryDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getGlAccountCategoriesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "ORDER BY glacdt_sortorder, glacdt_glaccountcategoryname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid " +
                "FOR UPDATE");
        getGlAccountCategoriesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GlAccountCategory> getGlAccountCategories(EntityPermission entityPermission) {
        return GlAccountCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, getGlAccountCategoriesQueries);
    }

    public List<GlAccountCategory> getGlAccountCategories() {
        return getGlAccountCategories(EntityPermission.READ_ONLY);
    }

    public List<GlAccountCategory> getGlAccountCategoriesForUpdate() {
        return getGlAccountCategories(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getGlAccountCategoriesByParentGlAccountCategoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid AND glacdt_parentglaccountcategoryid = ? " +
                "ORDER BY glacdt_sortorder, glacdt_glaccountcategoryname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccountcategories, glaccountcategorydetails " +
                "WHERE glac_activedetailid = glacdt_glaccountcategorydetailid AND glacdt_parentglaccountcategoryid = ? " +
                "FOR UPDATE");
        getGlAccountCategoriesByParentGlAccountCategoryQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GlAccountCategory> getGlAccountCategoriesByParentGlAccountCategory(GlAccountCategory parentGlAccountCategory,
            EntityPermission entityPermission) {
        return GlAccountCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, getGlAccountCategoriesByParentGlAccountCategoryQueries,
                parentGlAccountCategory);
    }

    public List<GlAccountCategory> getGlAccountCategoriesByParentGlAccountCategory(GlAccountCategory parentGlAccountCategory) {
        return getGlAccountCategoriesByParentGlAccountCategory(parentGlAccountCategory, EntityPermission.READ_ONLY);
    }

    public List<GlAccountCategory> getGlAccountCategoriesByParentGlAccountCategoryForUpdate(GlAccountCategory parentGlAccountCategory) {
        return getGlAccountCategoriesByParentGlAccountCategory(parentGlAccountCategory, EntityPermission.READ_WRITE);
    }

    public GlAccountCategoryTransfer getGlAccountCategoryTransfer(UserVisit userVisit, GlAccountCategory glAccountCategory) {
        return getAccountingTransferCaches(userVisit).getGlAccountCategoryTransferCache().getTransfer(glAccountCategory);
    }
    
    public List<GlAccountCategoryTransfer> getGlAccountCategoryTransfers(UserVisit userVisit) {
        List<GlAccountCategory> glAccountCategories = getGlAccountCategories();
        List<GlAccountCategoryTransfer> glAccountCategoryTransfers = new ArrayList<>(glAccountCategories.size());
        GlAccountCategoryTransferCache glAccountCategoryTransferCache = getAccountingTransferCaches(userVisit).getGlAccountCategoryTransferCache();
        
        glAccountCategories.stream().forEach((glAccountCategory) -> {
            glAccountCategoryTransfers.add(glAccountCategoryTransferCache.getTransfer(glAccountCategory));
        });
        
        return glAccountCategoryTransfers;
    }
    
    public GlAccountCategoryChoicesBean getGlAccountCategoryChoices(String defaultGlAccountCategoryChoice, Language language,
            boolean allowNullChoice) {
        List<GlAccountCategory> glAccountCategories = getGlAccountCategories();
        int size = glAccountCategories.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlAccountCategoryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(GlAccountCategory glAccountCategory: glAccountCategories) {
            GlAccountCategoryDetail glAccountCategoryDetail = glAccountCategory.getLastDetail();
            
            String label = getBestGlAccountCategoryDescription(glAccountCategory, language);
            String value = glAccountCategoryDetail.getGlAccountCategoryName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultGlAccountCategoryChoice != null && defaultGlAccountCategoryChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && glAccountCategoryDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GlAccountCategoryChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentGlAccountCategorySafe(GlAccountCategory glAccountCategory, GlAccountCategory parentGlAccountCategory) {
        boolean safe = true;
        
        if(parentGlAccountCategory != null) {
            Set<GlAccountCategory> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(glAccountCategory);
            do {
                if(parentItemPurchasingCategories.contains(parentGlAccountCategory)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentGlAccountCategory);
                parentGlAccountCategory = parentGlAccountCategory.getLastDetail().getParentGlAccountCategory();
            } while(parentGlAccountCategory != null);
        }
        
        return safe;
    }
    
    private void updateGlAccountCategoryFromValue(GlAccountCategoryDetailValue glAccountCategoryDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(glAccountCategoryDetailValue.hasBeenModified()) {
            GlAccountCategory glAccountCategory = GlAccountCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     glAccountCategoryDetailValue.getGlAccountCategoryPK());
            GlAccountCategoryDetail glAccountCategoryDetail = glAccountCategory.getActiveDetailForUpdate();
            
            glAccountCategoryDetail.setThruTime(session.START_TIME_LONG);
            glAccountCategoryDetail.store();
            
            GlAccountCategoryPK glAccountCategoryPK = glAccountCategoryDetail.getGlAccountCategoryPK();
            String glAccountCategoryName = glAccountCategoryDetailValue.getGlAccountCategoryName();
            GlAccountCategoryPK parentGlAccountCategoryPK = glAccountCategoryDetailValue.getParentGlAccountCategoryPK();
            Boolean isDefault = glAccountCategoryDetailValue.getIsDefault();
            Integer sortOrder = glAccountCategoryDetailValue.getSortOrder();
            
            if(checkDefault) {
                GlAccountCategory defaultGlAccountCategory = getDefaultGlAccountCategory();
                boolean defaultFound = defaultGlAccountCategory != null && !defaultGlAccountCategory.equals(glAccountCategory);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GlAccountCategoryDetailValue defaultGlAccountCategoryDetailValue = getDefaultGlAccountCategoryDetailValueForUpdate();
                    
                    defaultGlAccountCategoryDetailValue.setIsDefault(Boolean.FALSE);
                    updateGlAccountCategoryFromValue(defaultGlAccountCategoryDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            glAccountCategoryDetail = GlAccountCategoryDetailFactory.getInstance().create(glAccountCategoryPK,
                    glAccountCategoryName, parentGlAccountCategoryPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            glAccountCategory.setActiveDetail(glAccountCategoryDetail);
            glAccountCategory.setLastDetail(glAccountCategoryDetail);
            
            sendEventUsingNames(glAccountCategoryPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateGlAccountCategoryFromValue(GlAccountCategoryDetailValue glAccountCategoryDetailValue, BasePK updatedBy) {
        updateGlAccountCategoryFromValue(glAccountCategoryDetailValue, true, updatedBy);
    }
    
    private void deleteGlAccountCategory(GlAccountCategory glAccountCategory, boolean checkDefault, BasePK deletedBy) {
        GlAccountCategoryDetail glAccountCategoryDetail = glAccountCategory.getLastDetailForUpdate();
        
        deleteGlAccountCategoriesByParentGlAccountCategory(glAccountCategory, deletedBy);
        // TODO: deleteTransactionGlAccountCategoriesByGlAccountCategory(glAccountCategory, deletedBy);
        deleteGlAccountsByGlAccountCategory(glAccountCategory, deletedBy);
        deleteGlAccountCategoryDescriptionsByGlAccountCategory(glAccountCategory, deletedBy);
        
        glAccountCategoryDetail.setThruTime(session.START_TIME_LONG);
        glAccountCategory.setActiveDetail(null);
        glAccountCategory.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            GlAccountCategory defaultGlAccountCategory = getDefaultGlAccountCategory();

            if(defaultGlAccountCategory == null) {
                List<GlAccountCategory> glAccountCategories = getGlAccountCategoriesForUpdate();

                if(!glAccountCategories.isEmpty()) {
                    Iterator<GlAccountCategory> iter = glAccountCategories.iterator();
                    if(iter.hasNext()) {
                        defaultGlAccountCategory = iter.next();
                    }
                    GlAccountCategoryDetailValue glAccountCategoryDetailValue = defaultGlAccountCategory.getLastDetailForUpdate().getGlAccountCategoryDetailValue().clone();

                    glAccountCategoryDetailValue.setIsDefault(Boolean.TRUE);
                    updateGlAccountCategoryFromValue(glAccountCategoryDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(glAccountCategory.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteGlAccountCategory(GlAccountCategory glAccountCategory, BasePK deletedBy) {
        deleteGlAccountCategory(glAccountCategory, true, deletedBy);
    }

    private void deleteGlAccountCategories(List<GlAccountCategory> glAccountCategories, boolean checkDefault, BasePK deletedBy) {
        glAccountCategories.stream().forEach((glAccountCategory) -> {
            deleteGlAccountCategory(glAccountCategory, checkDefault, deletedBy);
        });
    }

    public void deleteGlAccountCategories(List<GlAccountCategory> glAccountCategories, BasePK deletedBy) {
        deleteGlAccountCategories(glAccountCategories, true, deletedBy);
    }

    private void deleteGlAccountCategoriesByParentGlAccountCategory(GlAccountCategory parentGlAccountCategory, BasePK deletedBy) {
        deleteGlAccountCategories(getGlAccountCategoriesByParentGlAccountCategoryForUpdate(parentGlAccountCategory), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
    public GlAccountCategoryDescription createGlAccountCategoryDescription(GlAccountCategory glAccountCategory, Language language, String description, BasePK createdBy) {
        GlAccountCategoryDescription glAccountCategoryDescription = GlAccountCategoryDescriptionFactory.getInstance().create(glAccountCategory, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(glAccountCategory.getPrimaryKey(), EventTypes.MODIFY.name(), glAccountCategoryDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return glAccountCategoryDescription;
    }
    
    private GlAccountCategoryDescription getGlAccountCategoryDescription(GlAccountCategory glAccountCategory, Language language, EntityPermission entityPermission) {
        GlAccountCategoryDescription glAccountCategoryDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountcategorydescriptions " +
                        "WHERE glacd_glac_glaccountcategoryid = ? AND glacd_lang_languageid = ? AND glacd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountcategorydescriptions " +
                        "WHERE glacd_glac_glaccountcategoryid = ? AND glacd_lang_languageid = ? AND glacd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            glAccountCategoryDescription = GlAccountCategoryDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountCategoryDescription;
    }
    
    public GlAccountCategoryDescription getGlAccountCategoryDescription(GlAccountCategory glAccountCategory, Language language) {
        return getGlAccountCategoryDescription(glAccountCategory, language, EntityPermission.READ_ONLY);
    }
    
    public GlAccountCategoryDescription getGlAccountCategoryDescriptionForUpdate(GlAccountCategory glAccountCategory, Language language) {
        return getGlAccountCategoryDescription(glAccountCategory, language, EntityPermission.READ_WRITE);
    }
    
    public GlAccountCategoryDescriptionValue getGlAccountCategoryDescriptionValue(GlAccountCategoryDescription glAccountCategoryDescription) {
        return glAccountCategoryDescription == null? null: glAccountCategoryDescription.getGlAccountCategoryDescriptionValue().clone();
    }
    
    public GlAccountCategoryDescriptionValue getGlAccountCategoryDescriptionValueForUpdate(GlAccountCategory glAccountCategory, Language language) {
        return getGlAccountCategoryDescriptionValue(getGlAccountCategoryDescriptionForUpdate(glAccountCategory, language));
    }
    
    private List<GlAccountCategoryDescription> getGlAccountCategoryDescriptionsByGlAccountCategory(GlAccountCategory glAccountCategory, EntityPermission entityPermission) {
        List<GlAccountCategoryDescription> glAccountCategoryDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountcategorydescriptions, languages " +
                        "WHERE glacd_glac_glaccountcategoryid = ? AND glacd_thrutime = ? AND glacd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountcategorydescriptions " +
                        "WHERE glacd_glac_glaccountcategoryid = ? AND glacd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            glAccountCategoryDescriptions = GlAccountCategoryDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountCategoryDescriptions;
    }
    
    public List<GlAccountCategoryDescription> getGlAccountCategoryDescriptionsByGlAccountCategory(GlAccountCategory glAccountCategory) {
        return getGlAccountCategoryDescriptionsByGlAccountCategory(glAccountCategory, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccountCategoryDescription> getGlAccountCategoryDescriptionsByGlAccountCategoryForUpdate(GlAccountCategory glAccountCategory) {
        return getGlAccountCategoryDescriptionsByGlAccountCategory(glAccountCategory, EntityPermission.READ_WRITE);
    }
    
    public String getBestGlAccountCategoryDescription(GlAccountCategory glAccountCategory, Language language) {
        String description;
        GlAccountCategoryDescription glAccountCategoryDescription = getGlAccountCategoryDescription(glAccountCategory, language);
        
        if(glAccountCategoryDescription == null && !language.getIsDefault()) {
            glAccountCategoryDescription = getGlAccountCategoryDescription(glAccountCategory, getPartyControl().getDefaultLanguage());
        }
        
        if(glAccountCategoryDescription == null) {
            description = glAccountCategory.getLastDetail().getGlAccountCategoryName();
        } else {
            description = glAccountCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public GlAccountCategoryDescriptionTransfer getGlAccountCategoryDescriptionTransfer(UserVisit userVisit, GlAccountCategoryDescription glAccountCategoryDescription) {
        return getAccountingTransferCaches(userVisit).getGlAccountCategoryDescriptionTransferCache().getTransfer(glAccountCategoryDescription);
    }
    
    public List<GlAccountCategoryDescriptionTransfer> getGlAccountCategoryDescriptionTransfersByGlAccountCategory(UserVisit userVisit, GlAccountCategory glAccountCategory) {
        List<GlAccountCategoryDescription> glAccountCategoryDescriptions = getGlAccountCategoryDescriptionsByGlAccountCategory(glAccountCategory);
        List<GlAccountCategoryDescriptionTransfer> glAccountCategoryDescriptionTransfers = new ArrayList<>(glAccountCategoryDescriptions.size());
        GlAccountCategoryDescriptionTransferCache glAccountCategoryDescriptionTransferCache = getAccountingTransferCaches(userVisit).getGlAccountCategoryDescriptionTransferCache();
        
        glAccountCategoryDescriptions.stream().forEach((glAccountCategoryDescription) -> {
            glAccountCategoryDescriptionTransfers.add(glAccountCategoryDescriptionTransferCache.getTransfer(glAccountCategoryDescription));
        });
        
        return glAccountCategoryDescriptionTransfers;
    }
    
    public void updateGlAccountCategoryDescriptionFromValue(GlAccountCategoryDescriptionValue glAccountCategoryDescriptionValue, BasePK updatedBy) {
        if(glAccountCategoryDescriptionValue.hasBeenModified()) {
            GlAccountCategoryDescription glAccountCategoryDescription = GlAccountCategoryDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glAccountCategoryDescriptionValue.getPrimaryKey());
            
            glAccountCategoryDescription.setThruTime(session.START_TIME_LONG);
            glAccountCategoryDescription.store();
            
            GlAccountCategory glAccountCategory = glAccountCategoryDescription.getGlAccountCategory();
            Language language = glAccountCategoryDescription.getLanguage();
            String description = glAccountCategoryDescriptionValue.getDescription();
            
            glAccountCategoryDescription = GlAccountCategoryDescriptionFactory.getInstance().create(glAccountCategory, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(glAccountCategory.getPrimaryKey(), EventTypes.MODIFY.name(), glAccountCategoryDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteGlAccountCategoryDescription(GlAccountCategoryDescription glAccountCategoryDescription, BasePK deletedBy) {
        glAccountCategoryDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(glAccountCategoryDescription.getGlAccountCategoryPK(), EventTypes.MODIFY.name(), glAccountCategoryDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteGlAccountCategoryDescriptionsByGlAccountCategory(GlAccountCategory glAccountCategory, BasePK deletedBy) {
        List<GlAccountCategoryDescription> glAccountCategoryDescriptions = getGlAccountCategoryDescriptionsByGlAccountCategoryForUpdate(glAccountCategory);
        
        glAccountCategoryDescriptions.stream().forEach((glAccountCategoryDescription) -> {
            deleteGlAccountCategoryDescription(glAccountCategoryDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Type
    // --------------------------------------------------------------------------------
    
    public GlResourceType createGlResourceType(String glResourceTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        GlResourceType defaultGlResourceType = getDefaultGlResourceType();
        boolean defaultFound = defaultGlResourceType != null;
        
        if(defaultFound && isDefault) {
            GlResourceTypeDetailValue defaultGlResourceTypeDetailValue = getDefaultGlResourceTypeDetailValueForUpdate();
            
            defaultGlResourceTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateGlResourceTypeFromValue(defaultGlResourceTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GlResourceType glResourceType = GlResourceTypeFactory.getInstance().create();
        GlResourceTypeDetail glResourceTypeDetail = GlResourceTypeDetailFactory.getInstance().create(session,
                glResourceType, glResourceTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        glResourceType = GlResourceTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glResourceType.getPrimaryKey());
        glResourceType.setActiveDetail(glResourceTypeDetail);
        glResourceType.setLastDetail(glResourceTypeDetail);
        glResourceType.store();
        
        sendEventUsingNames(glResourceType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return glResourceType;
    }
    
    private GlResourceType getGlResourceTypeByName(String glResourceTypeName, EntityPermission entityPermission) {
        GlResourceType glResourceType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_glresourcetypename = ? AND glrtypdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_glresourcetypename = ? AND glrtypdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlResourceTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, glResourceTypeName);
            ps.setLong(2, Session.MAX_TIME);
            
            glResourceType = GlResourceTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glResourceType;
    }
    
    public GlResourceType getGlResourceTypeByName(String glResourceTypeName) {
        return getGlResourceTypeByName(glResourceTypeName, EntityPermission.READ_ONLY);
    }
    
    public GlResourceType getGlResourceTypeByNameForUpdate(String glResourceTypeName) {
        return getGlResourceTypeByName(glResourceTypeName, EntityPermission.READ_WRITE);
    }
    
    public GlResourceTypeDetailValue getGlResourceTypeDetailValueForUpdate(GlResourceType glResourceType) {
        return glResourceType == null? null: glResourceType.getLastDetailForUpdate().getGlResourceTypeDetailValue().clone();
    }
    
    public GlResourceTypeDetailValue getGlResourceTypeDetailValueByNameForUpdate(String glResourceTypeName) {
        return getGlResourceTypeDetailValueForUpdate(getGlResourceTypeByNameForUpdate(glResourceTypeName));
    }
    
    private GlResourceType getDefaultGlResourceType(EntityPermission entityPermission) {
        GlResourceType glResourceType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_isdefault = 1 AND glrtypdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_isdefault = 1 AND glrtypdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlResourceTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            glResourceType = GlResourceTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glResourceType;
    }
    
    public GlResourceType getDefaultGlResourceType() {
        return getDefaultGlResourceType(EntityPermission.READ_ONLY);
    }
    
    public GlResourceType getDefaultGlResourceTypeForUpdate() {
        return getDefaultGlResourceType(EntityPermission.READ_WRITE);
    }
    
    public GlResourceTypeDetailValue getDefaultGlResourceTypeDetailValueForUpdate() {
        return getDefaultGlResourceTypeForUpdate().getLastDetailForUpdate().getGlResourceTypeDetailValue().clone();
    }
    
    private List<GlResourceType> getGlResourceTypes(EntityPermission entityPermission) {
        List<GlResourceType> glResourceTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_thrutime = ? " +
                        "ORDER BY glrtypdt_sortorder, glrtypdt_glresourcetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypes, glresourcetypedetails " +
                        "WHERE glrtyp_glresourcetypeid = glrtypdt_glrtyp_glresourcetypeid AND glrtypdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlResourceTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            glResourceTypes = GlResourceTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glResourceTypes;
    }
    
    public List<GlResourceType> getGlResourceTypes() {
        return getGlResourceTypes(EntityPermission.READ_ONLY);
    }
    
    public List<GlResourceType> getGlResourceTypesForUpdate() {
        return getGlResourceTypes(EntityPermission.READ_WRITE);
    }
    
    public GlResourceTypeTransfer getGlResourceTypeTransfer(UserVisit userVisit, GlResourceType glResourceType) {
        return getAccountingTransferCaches(userVisit).getGlResourceTypeTransferCache().getTransfer(glResourceType);
    }
    
    public List<GlResourceTypeTransfer> getGlResourceTypeTransfers(UserVisit userVisit) {
        List<GlResourceType> glResourceTypes = getGlResourceTypes();
        List<GlResourceTypeTransfer> glResourceTypeTransfers = new ArrayList<>(glResourceTypes.size());
        GlResourceTypeTransferCache glResourceTypeTransferCache = getAccountingTransferCaches(userVisit).getGlResourceTypeTransferCache();
        
        glResourceTypes.stream().forEach((glResourceType) -> {
            glResourceTypeTransfers.add(glResourceTypeTransferCache.getTransfer(glResourceType));
        });
        
        return glResourceTypeTransfers;
    }
    
    public GlResourceTypeChoicesBean getGlResourceTypeChoices(String defaultGlResourceTypeChoice, Language language,
            boolean allowNullChoice) {
        List<GlResourceType> glResourceTypes = getGlResourceTypes();
        int size = glResourceTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlResourceTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(GlResourceType glResourceType: glResourceTypes) {
            GlResourceTypeDetail glResourceTypeDetail = glResourceType.getLastDetail();
            
            String label = getBestGlResourceTypeDescription(glResourceType, language);
            String value = glResourceTypeDetail.getGlResourceTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultGlResourceTypeChoice != null && defaultGlResourceTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && glResourceTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GlResourceTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateGlResourceTypeFromValue(GlResourceTypeDetailValue glResourceTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(glResourceTypeDetailValue.hasBeenModified()) {
            GlResourceType glResourceType = GlResourceTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     glResourceTypeDetailValue.getGlResourceTypePK());
            GlResourceTypeDetail glResourceTypeDetail = glResourceType.getActiveDetailForUpdate();
            
            glResourceTypeDetail.setThruTime(session.START_TIME_LONG);
            glResourceTypeDetail.store();
            
            GlResourceTypePK glResourceTypePK = glResourceTypeDetail.getGlResourceTypePK();
            String glResourceTypeName = glResourceTypeDetailValue.getGlResourceTypeName();
            Boolean isDefault = glResourceTypeDetailValue.getIsDefault();
            Integer sortOrder = glResourceTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                GlResourceType defaultGlResourceType = getDefaultGlResourceType();
                boolean defaultFound = defaultGlResourceType != null && !defaultGlResourceType.equals(glResourceType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GlResourceTypeDetailValue defaultGlResourceTypeDetailValue = getDefaultGlResourceTypeDetailValueForUpdate();
                    
                    defaultGlResourceTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateGlResourceTypeFromValue(defaultGlResourceTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            glResourceTypeDetail = GlResourceTypeDetailFactory.getInstance().create(glResourceTypePK,
                    glResourceTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            glResourceType.setActiveDetail(glResourceTypeDetail);
            glResourceType.setLastDetail(glResourceTypeDetail);
            
            sendEventUsingNames(glResourceTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateGlResourceTypeFromValue(GlResourceTypeDetailValue glResourceTypeDetailValue, BasePK updatedBy) {
        updateGlResourceTypeFromValue(glResourceTypeDetailValue, true, updatedBy);
    }
    
    public void deleteGlResourceType(GlResourceType glResourceType, BasePK deletedBy) {
        deleteGlAccountsByGlResourceType(glResourceType, deletedBy);
        deleteGlResourceTypeDescriptionsByGlResourceType(glResourceType, deletedBy);
        
        GlResourceTypeDetail glResourceTypeDetail = glResourceType.getLastDetailForUpdate();
        glResourceTypeDetail.setThruTime(session.START_TIME_LONG);
        glResourceTypeDetail.store();
        glResourceType.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        GlResourceType defaultGlResourceType = getDefaultGlResourceType();
        if(defaultGlResourceType == null) {
            List<GlResourceType> glResourceTypes = getGlResourceTypesForUpdate();
            
            if(!glResourceTypes.isEmpty()) {
                Iterator<GlResourceType> iter = glResourceTypes.iterator();
                if(iter.hasNext()) {
                    defaultGlResourceType = iter.next();
                }
                GlResourceTypeDetailValue glResourceTypeDetailValue = defaultGlResourceType.getLastDetailForUpdate().getGlResourceTypeDetailValue().clone();
                
                glResourceTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateGlResourceTypeFromValue(glResourceTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(glResourceType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Resource Type Descriptions
    // --------------------------------------------------------------------------------
    
    public GlResourceTypeDescription createGlResourceTypeDescription(GlResourceType glResourceType, Language language, String description, BasePK createdBy) {
        GlResourceTypeDescription glResourceTypeDescription = GlResourceTypeDescriptionFactory.getInstance().create(glResourceType, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(glResourceType.getPrimaryKey(), EventTypes.MODIFY.name(), glResourceTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return glResourceTypeDescription;
    }
    
    private GlResourceTypeDescription getGlResourceTypeDescription(GlResourceType glResourceType, Language language, EntityPermission entityPermission) {
        GlResourceTypeDescription glResourceTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypedescriptions " +
                        "WHERE glrtypd_glrtyp_glresourcetypeid = ? AND glrtypd_lang_languageid = ? AND glrtypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypedescriptions " +
                        "WHERE glrtypd_glrtyp_glresourcetypeid = ? AND glrtypd_lang_languageid = ? AND glrtypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlResourceTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glResourceType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            glResourceTypeDescription = GlResourceTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glResourceTypeDescription;
    }
    
    public GlResourceTypeDescription getGlResourceTypeDescription(GlResourceType glResourceType, Language language) {
        return getGlResourceTypeDescription(glResourceType, language, EntityPermission.READ_ONLY);
    }
    
    public GlResourceTypeDescription getGlResourceTypeDescriptionForUpdate(GlResourceType glResourceType, Language language) {
        return getGlResourceTypeDescription(glResourceType, language, EntityPermission.READ_WRITE);
    }
    
    public GlResourceTypeDescriptionValue getGlResourceTypeDescriptionValue(GlResourceTypeDescription glResourceTypeDescription) {
        return glResourceTypeDescription == null? null: glResourceTypeDescription.getGlResourceTypeDescriptionValue().clone();
    }
    
    public GlResourceTypeDescriptionValue getGlResourceTypeDescriptionValueForUpdate(GlResourceType glResourceType, Language language) {
        return getGlResourceTypeDescriptionValue(getGlResourceTypeDescriptionForUpdate(glResourceType, language));
    }
    
    private List<GlResourceTypeDescription> getGlResourceTypeDescriptionsByGlResourceType(GlResourceType glResourceType, EntityPermission entityPermission) {
        List<GlResourceTypeDescription> glResourceTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypedescriptions, languages " +
                        "WHERE glrtypd_glrtyp_glresourcetypeid = ? AND glrtypd_thrutime = ? AND glrtypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glresourcetypedescriptions " +
                        "WHERE glrtypd_glrtyp_glresourcetypeid = ? AND glrtypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlResourceTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glResourceType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            glResourceTypeDescriptions = GlResourceTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glResourceTypeDescriptions;
    }
    
    public List<GlResourceTypeDescription> getGlResourceTypeDescriptionsByGlResourceType(GlResourceType glResourceType) {
        return getGlResourceTypeDescriptionsByGlResourceType(glResourceType, EntityPermission.READ_ONLY);
    }
    
    public List<GlResourceTypeDescription> getGlResourceTypeDescriptionsByGlResourceTypeForUpdate(GlResourceType glResourceType) {
        return getGlResourceTypeDescriptionsByGlResourceType(glResourceType, EntityPermission.READ_WRITE);
    }
    
    public String getBestGlResourceTypeDescription(GlResourceType glResourceType, Language language) {
        String description;
        GlResourceTypeDescription glResourceTypeDescription = getGlResourceTypeDescription(glResourceType, language);
        
        if(glResourceTypeDescription == null && !language.getIsDefault()) {
            glResourceTypeDescription = getGlResourceTypeDescription(glResourceType, getPartyControl().getDefaultLanguage());
        }
        
        if(glResourceTypeDescription == null) {
            description = glResourceType.getLastDetail().getGlResourceTypeName();
        } else {
            description = glResourceTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public GlResourceTypeDescriptionTransfer getGlResourceTypeDescriptionTransfer(UserVisit userVisit, GlResourceTypeDescription glResourceTypeDescription) {
        return getAccountingTransferCaches(userVisit).getGlResourceTypeDescriptionTransferCache().getTransfer(glResourceTypeDescription);
    }
    
    public List<GlResourceTypeDescriptionTransfer> getGlResourceTypeDescriptionTransfersByGlResourceType(UserVisit userVisit, GlResourceType glResourceType) {
        List<GlResourceTypeDescription> glResourceTypeDescriptions = getGlResourceTypeDescriptionsByGlResourceType(glResourceType);
        List<GlResourceTypeDescriptionTransfer> glResourceTypeDescriptionTransfers = new ArrayList<>(glResourceTypeDescriptions.size());
        GlResourceTypeDescriptionTransferCache glResourceTypeDescriptionTransferCache = getAccountingTransferCaches(userVisit).getGlResourceTypeDescriptionTransferCache();
        
        glResourceTypeDescriptions.stream().forEach((glResourceTypeDescription) -> {
            glResourceTypeDescriptionTransfers.add(glResourceTypeDescriptionTransferCache.getTransfer(glResourceTypeDescription));
        });
        
        return glResourceTypeDescriptionTransfers;
    }
    
    public void updateGlResourceTypeDescriptionFromValue(GlResourceTypeDescriptionValue glResourceTypeDescriptionValue, BasePK updatedBy) {
        if(glResourceTypeDescriptionValue.hasBeenModified()) {
            GlResourceTypeDescription glResourceTypeDescription = GlResourceTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glResourceTypeDescriptionValue.getPrimaryKey());
            
            glResourceTypeDescription.setThruTime(session.START_TIME_LONG);
            glResourceTypeDescription.store();
            
            GlResourceType glResourceType = glResourceTypeDescription.getGlResourceType();
            Language language = glResourceTypeDescription.getLanguage();
            String description = glResourceTypeDescriptionValue.getDescription();
            
            glResourceTypeDescription = GlResourceTypeDescriptionFactory.getInstance().create(glResourceType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(glResourceType.getPrimaryKey(), EventTypes.MODIFY.name(), glResourceTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteGlResourceTypeDescription(GlResourceTypeDescription glResourceTypeDescription, BasePK deletedBy) {
        glResourceTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(glResourceTypeDescription.getGlResourceTypePK(), EventTypes.MODIFY.name(), glResourceTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteGlResourceTypeDescriptionsByGlResourceType(GlResourceType glResourceType, BasePK deletedBy) {
        List<GlResourceTypeDescription> glResourceTypeDescriptions = getGlResourceTypeDescriptionsByGlResourceTypeForUpdate(glResourceType);
        
        glResourceTypeDescriptions.stream().forEach((glResourceTypeDescription) -> {
            deleteGlResourceTypeDescription(glResourceTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Accounts
    // --------------------------------------------------------------------------------
    
    public GlAccount createGlAccount(String glAccountName, GlAccount parentGlAccount, GlAccountType glAccountType,
            GlAccountClass glAccountClass, GlAccountCategory glAccountCategory, GlResourceType glResourceType, Currency currency,
            Boolean isDefault, BasePK createdBy) {
        
        if(glAccountCategory != null) {
            GlAccount defaultGlAccount = getDefaultGlAccount(glAccountCategory);
            boolean defaultFound = defaultGlAccount != null;
            
            if(defaultFound && isDefault) {
                GlAccountDetailValue defaultGlAccountDetailValue = getDefaultGlAccountDetailValueForUpdate(glAccountCategory);
                
                defaultGlAccountDetailValue.setIsDefault(Boolean.FALSE);
                updateGlAccountFromValue(defaultGlAccountDetailValue, false, createdBy);
            } else if(!defaultFound) {
                isDefault = Boolean.TRUE;
            }
        } else {
            isDefault = null;
        }
        
        GlAccount glAccount = GlAccountFactory.getInstance().create();
        GlAccountDetail glAccountDetail = GlAccountDetailFactory.getInstance().create(glAccount, glAccountName,
                parentGlAccount, glAccountType, glAccountClass, glAccountCategory, glResourceType, currency, isDefault,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        glAccount = GlAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glAccount.getPrimaryKey());
        glAccount.setActiveDetail(glAccountDetail);
        glAccount.setLastDetail(glAccountDetail);
        glAccount.store();
        
        sendEventUsingNames(glAccount.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return glAccount;
    }
    
    private GlAccount getGlAccountByName(String glAccountName, EntityPermission entityPermission) {
        GlAccount glAccount;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glaccountname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glaccountname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, glAccountName);
            
            glAccount = GlAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccount;
    }
    
    public GlAccount getGlAccountByName(String glAccountName) {
        return getGlAccountByName(glAccountName, EntityPermission.READ_ONLY);
    }
    
    public GlAccount getGlAccountByNameForUpdate(String glAccountName) {
        return getGlAccountByName(glAccountName, EntityPermission.READ_WRITE);
    }
    
    public GlAccountDetailValue getGlAccountDetailValueByNameForUpdate(String glAccountName) {
        return getGlAccountDetailValueForUpdate(getGlAccountByNameForUpdate(glAccountName));
    }
    
    private GlAccount getDefaultGlAccount(GlAccountCategoryPK glAccountCategoryPK, EntityPermission entityPermission) {
        GlAccount glAccount;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glac_glaccountcategoryid = ? AND gladt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glac_glaccountcategoryid = ? AND gladt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountCategoryPK.getEntityId());
            
            glAccount = GlAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccount;
    }
    
    private GlAccount getDefaultGlAccount(GlAccountCategory glAccountCategory, EntityPermission entityPermission) {
        return getDefaultGlAccount(glAccountCategory.getPrimaryKey(), entityPermission);
    }
    
    public GlAccount getDefaultGlAccount(GlAccountCategory glAccountCategory) {
        return getDefaultGlAccount(glAccountCategory, EntityPermission.READ_ONLY);
    }
    
    public GlAccount getDefaultGlAccount(GlAccountCategoryPK glAccountCategoryPK) {
        return getDefaultGlAccount(glAccountCategoryPK, EntityPermission.READ_ONLY);
    }
    
    public GlAccount getDefaultGlAccountForUpdate(GlAccountCategory glAccountCategory) {
        return getDefaultGlAccount(glAccountCategory, EntityPermission.READ_WRITE);
    }
    
    public GlAccount getDefaultGlAccountForUpdate(GlAccountCategoryPK glAccountCategoryPK) {
        return getDefaultGlAccount(glAccountCategoryPK, EntityPermission.READ_WRITE);
    }
    
    public GlAccountDetailValue getDefaultGlAccountDetailValueForUpdate(GlAccountCategory glAccountCategory) {
        return getDefaultGlAccountForUpdate(glAccountCategory).getLastDetailForUpdate().getGlAccountDetailValue().clone();
    }
    
    public GlAccountDetailValue getDefaultGlAccountDetailValueForUpdate(GlAccountCategoryPK glAccountCategoryPK) {
        return getDefaultGlAccountForUpdate(glAccountCategoryPK).getLastDetailForUpdate().getGlAccountDetailValue().clone();
    }
    
    public GlAccountDetailValue getGlAccountDetailValueForUpdate(GlAccount glAccount) {
        return glAccount == null? null: glAccount.getLastDetailForUpdate().getGlAccountDetailValue().clone();
    }
    
    private List<GlAccount> getGlAccounts(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM glaccounts, glaccountdetails " +
                    "WHERE gla_activedetailid = gladt_glaccountdetailid " +
                    "ORDER BY gladt_glaccountname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM glaccounts, glaccountdetails " +
                    "WHERE gla_activedetailid = gladt_glaccountdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = GlAccountFactory.getInstance().prepareStatement(query);
        
        return GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<GlAccount> getGlAccounts() {
        return getGlAccounts(EntityPermission.READ_ONLY);
    }
    
    public List<GlAccount> getGlAccountsForUpdate() {
        return getGlAccounts(EntityPermission.READ_WRITE);
    }
    
    private List<GlAccount> getGlAccountsByGlAccountClass(GlAccountClass glAccountClass, EntityPermission entityPermission) {
        List<GlAccount> glAccounts = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glacls_glaccountclassid = ? " +
                        "ORDER BY gladt_glaccountname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glacls_glaccountclassid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountClass.getPrimaryKey().getEntityId());
            
            glAccounts = GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccounts;
    }
    
    public List<GlAccount> getGlAccountsByGlAccountClass(GlAccountClass glAccountClass) {
        return getGlAccountsByGlAccountClass(glAccountClass, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccount> getGlAccountsByGlAccountClassForUpdate(GlAccountClass glAccountClass) {
        return getGlAccountsByGlAccountClass(glAccountClass, EntityPermission.READ_WRITE);
    }
    
    private List<GlAccount> getGlAccountsByGlAccountCategory(GlAccountCategoryPK glAccountCategoryPK, EntityPermission entityPermission) {
        List<GlAccount> glAccounts = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glac_glaccountcategoryid = ? " +
                        "ORDER BY gladt_glaccountname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glac_glaccountcategoryid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccountCategoryPK.getEntityId());
            
            glAccounts = GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccounts;
    }
    
    private List<GlAccount> getGlAccountsByGlAccountCategory(GlAccountCategory glAccountCategory, EntityPermission entityPermission) {
        return getGlAccountsByGlAccountCategory(glAccountCategory.getPrimaryKey(), entityPermission);
    }
    
    public List<GlAccount> getGlAccountsByGlAccountCategory(GlAccountCategory glAccountCategory) {
        return getGlAccountsByGlAccountCategory(glAccountCategory, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccount> getGlAccountsByGlAccountCategory(GlAccountCategoryPK glAccountCategoryPK) {
        return getGlAccountsByGlAccountCategory(glAccountCategoryPK, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccount> getGlAccountsByGlAccountCategoryForUpdate(GlAccountCategory glAccountCategory) {
        return getGlAccountsByGlAccountCategory(glAccountCategory, EntityPermission.READ_WRITE);
    }
    
    public List<GlAccount> getGlAccountsByGlAccountCategoryForUpdate(GlAccountCategoryPK glAccountCategoryPK) {
        return getGlAccountsByGlAccountCategory(glAccountCategoryPK, EntityPermission.READ_WRITE);
    }
    
    private List<GlAccount> getGlAccountsByGlResourceType(GlResourceType glResourceType, EntityPermission entityPermission) {
        List<GlAccount> glAccounts = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glrtyp_glresourcetypeid = ? " +
                        "ORDER BY gladt_glaccountname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccounts, glaccountdetails " +
                        "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_glrtyp_glresourcetypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glResourceType.getPrimaryKey().getEntityId());
            
            glAccounts = GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccounts;
    }
    
    public List<GlAccount> getGlAccountsByGlResourceType(GlResourceType glResourceType) {
        return getGlAccountsByGlResourceType(glResourceType, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccount> getGlAccountsByGlResourceTypeForUpdate(GlResourceType glResourceType) {
        return getGlAccountsByGlResourceType(glResourceType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getGlAccountsByParentGlAccountQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM glaccounts, glaccountdetails " +
                "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_parentglaccountid = ? " +
                "ORDER BY gladt_sortorder, gladt_glaccountname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM glaccounts, glaccountdetails " +
                "WHERE gla_activedetailid = gladt_glaccountdetailid AND gladt_parentglaccountid = ? " +
                "FOR UPDATE");
        getGlAccountsByParentGlAccountQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GlAccount> getGlAccountsByParentGlAccount(GlAccount parentGlAccount,
            EntityPermission entityPermission) {
        return GlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, getGlAccountsByParentGlAccountQueries,
                parentGlAccount);
    }

    public List<GlAccount> getGlAccountsByParentGlAccount(GlAccount parentGlAccount) {
        return getGlAccountsByParentGlAccount(parentGlAccount, EntityPermission.READ_ONLY);
    }

    public List<GlAccount> getGlAccountsByParentGlAccountForUpdate(GlAccount parentGlAccount) {
        return getGlAccountsByParentGlAccount(parentGlAccount, EntityPermission.READ_WRITE);
    }

    public GlAccountTransfer getGlAccountTransfer(UserVisit userVisit, GlAccount glAccount) {
        return getAccountingTransferCaches(userVisit).getGlAccountTransferCache().getTransfer(glAccount);
    }
    
    public List<GlAccountTransfer> getGlAccountTransfers(UserVisit userVisit, List<GlAccount> glAccounts) {
        List<GlAccountTransfer> glAccountTransfers = new ArrayList<>(glAccounts.size());
        GlAccountTransferCache glAccountTransferCache = getAccountingTransferCaches(userVisit).getGlAccountTransferCache();
        
        glAccounts.stream().forEach((glAccount) -> {
            glAccountTransfers.add(glAccountTransferCache.getTransfer(glAccount));
        });
        
        return glAccountTransfers;
    }
    
    public List<GlAccountTransfer> getGlAccountTransfers(UserVisit userVisit) {
        return getGlAccountTransfers(userVisit, getGlAccounts());
    }
    
    public List<GlAccountTransfer> getGlAccountTransfersByGlAccountClass(UserVisit userVisit, GlAccountClass glAccountClass) {
        return getGlAccountTransfers(userVisit, getGlAccountsByGlAccountClass(glAccountClass));
    }
    
    public List<GlAccountTransfer> getGlAccountTransfersByGlAccountCategory(UserVisit userVisit, GlAccountCategory glAccountCategory) {
        return getGlAccountTransfers(userVisit, getGlAccountsByGlAccountCategory(glAccountCategory));
    }
    
    public List<GlAccountTransfer> getGlAccountTransfersByGlResourceType(UserVisit userVisit, GlResourceType glResourceType) {
        return getGlAccountTransfers(userVisit, getGlAccountsByGlResourceType(glResourceType));
    }
    
    public GlAccountChoicesBean getGlAccountChoices(String defaultGlAccountChoice, Language language, boolean allowNullChoice) {
        List<GlAccount> glAccounts = getGlAccounts();
        int size = glAccounts.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlAccountChoice == null) {
                defaultValue = "";
            }
        }
        
        for(GlAccount glAccount: glAccounts) {
            GlAccountDetail glAccountDetail = glAccount.getLastDetail();
            
            String label = getBestGlAccountDescription(glAccount, language);
            String value = glAccountDetail.getGlAccountName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultGlAccountChoice != null && defaultGlAccountChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new GlAccountChoicesBean(labels, values, defaultValue);
    }
    
    public GlAccountChoicesBean getGlAccountChoicesByGlAccountCategory(String defaultGlAccountChoice, Language language,
            GlAccountCategory glAccountCategory, boolean allowNullChoice) {
        List<GlAccount> glAccounts = getGlAccountsByGlAccountCategory(glAccountCategory);
        int size = glAccounts.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGlAccountChoice == null) {
                defaultValue = "";
            }
        }
        
        for(GlAccount glAccount: glAccounts) {
            GlAccountDetail glAccountDetail = glAccount.getLastDetail();
            
            String label = getBestGlAccountDescription(glAccount, language);
            String value = glAccountDetail.getGlAccountName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultGlAccountChoice != null && defaultGlAccountChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && glAccountDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GlAccountChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentGlAccountSafe(GlAccount glAccount, GlAccount parentGlAccount) {
        boolean safe = true;
        
        if(parentGlAccount != null) {
            Set<GlAccount> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(glAccount);
            do {
                if(parentItemPurchasingCategories.contains(parentGlAccount)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentGlAccount);
                parentGlAccount = parentGlAccount.getLastDetail().getParentGlAccount();
            } while(parentGlAccount != null);
        }
        
        return safe;
    }
    
    private void pickDefaultGlAccount(final GlAccountCategoryPK glAccountCategoryPK, final BasePK updatedBy) {
        GlAccount defaultGlAccount = getDefaultGlAccount(glAccountCategoryPK);
            
        if(defaultGlAccount == null) {
            List<GlAccount> glAccounts = getGlAccountsByGlAccountCategoryForUpdate(glAccountCategoryPK);
            
            if(!glAccounts.isEmpty()) {
                Iterator<GlAccount> iter = glAccounts.iterator();
                if(iter.hasNext()) {
                    defaultGlAccount = iter.next();
                }
                GlAccountDetailValue glAccountDetailValue = defaultGlAccount.getLastDetailForUpdate().getGlAccountDetailValue().clone();
                
                glAccountDetailValue.setIsDefault(Boolean.TRUE);
                updateGlAccountFromValue(glAccountDetailValue, false, updatedBy);
            }
        }
    }
    
    private void updateGlAccountFromValue(GlAccountDetailValue glAccountDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(glAccountDetailValue.hasBeenModified()) {
            GlAccount glAccount = GlAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     glAccountDetailValue.getGlAccountPK());
            GlAccountDetail glAccountDetail = glAccount.getActiveDetailForUpdate();
            
            glAccountDetail.setThruTime(session.START_TIME_LONG);
            glAccountDetail.store();
            
            GlAccountPK glAccountPK = glAccountDetail.getGlAccountPK(); // Not updated
            String glAccountName = glAccountDetailValue.getGlAccountName();
            GlAccountPK parentGlAccountPK = glAccountDetailValue.getParentGlAccountPK();
            GlAccountTypePK glAccountTypePK = glAccountDetail.getGlAccountTypePK(); // Not updated
            GlAccountClassPK glAccountClassPK = glAccountDetailValue.getGlAccountClassPK();
            GlAccountCategoryPK glAccountCategoryPK = glAccountDetailValue.getGlAccountCategoryPK();
            GlResourceTypePK glResourceTypePK = glAccountDetailValue.getGlResourceTypePK();
            CurrencyPK currencyPK = glAccountDetail.getCurrencyPK(); // Not updated
            Boolean isDefault = glAccountDetailValue.getIsDefault();
            
            if(checkDefault) {
                if(glAccountCategoryPK != null) {
                    GlAccount defaultGlAccount = getDefaultGlAccount(glAccountCategoryPK);
                    boolean defaultFound = defaultGlAccount != null && !defaultGlAccount.equals(glAccount);
                    
                    if(isDefault && defaultFound) {
                        // If I'm the default, and a default already existed...
                        GlAccountDetailValue defaultGlAccountDetailValue = getDefaultGlAccountDetailValueForUpdate(glAccountCategoryPK);
                        
                        defaultGlAccountDetailValue.setIsDefault(Boolean.FALSE);
                        updateGlAccountFromValue(defaultGlAccountDetailValue, false, updatedBy);
                    } else if(!isDefault && !defaultFound) {
                        // If I'm not the default, and no other default exists...
                        isDefault = Boolean.TRUE;
                    }
                } else {
                    if(glAccountDetail.getIsDefault() != null) {
                        // If it was set, but is now going to be null, then we need to pick a new default...
                        pickDefaultGlAccount(glAccountCategoryPK, updatedBy);
                    } else {
                        isDefault = null;
                    }
                }
            }
            
            glAccountDetail = GlAccountDetailFactory.getInstance().create(glAccountPK, glAccountName,
                    parentGlAccountPK, glAccountTypePK, glAccountClassPK, glAccountCategoryPK, glResourceTypePK, currencyPK,
                    isDefault, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            glAccount.setActiveDetail(glAccountDetail);
            glAccount.setLastDetail(glAccountDetail);
            
            sendEventUsingNames(glAccountPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateGlAccountFromValue(GlAccountDetailValue glAccountDetailValue, BasePK updatedBy) {
        updateGlAccountFromValue(glAccountDetailValue, true, updatedBy);
    }
    
    private void deleteGlAccount(GlAccount glAccount, boolean checkDefault, BasePK deletedBy) {
        FinancialControl financialControl  = (FinancialControl)Session.getModelController(FinancialControl.class);
        
        deleteGlAccountsByParentGlAccount(glAccount, deletedBy);
        deleteTransactionGlAccountByGlAccount(glAccount, deletedBy);
        // TODO: deleteTransactionGlAccountByGlAccount(glAccount, deletedBy);
        deleteGlAccountDescriptionsByGlAccount(glAccount, deletedBy);
        financialControl.deleteFinancialAccountsByGlAccount(glAccount, deletedBy);
        
        GlAccountDetail glAccountDetail = glAccount.getLastDetailForUpdate();
        glAccountDetail.setThruTime(session.START_TIME_LONG);
        glAccount.setActiveDetail(null);
        glAccount.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            GlAccountCategoryPK glAccountCategoryPK = glAccountDetail.getGlAccountCategoryPK();

            if(glAccountCategoryPK != null) {
                pickDefaultGlAccount(glAccountCategoryPK, deletedBy);
            }
        }

        sendEventUsingNames(glAccount.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteGlAccount(GlAccount glAccount, BasePK deletedBy) {
        deleteGlAccount(glAccount, true, deletedBy);
    }

    private void deleteGlAccounts(List<GlAccount> glAccounts, boolean checkDefault, BasePK deletedBy) {
        glAccounts.stream().forEach((glAccount) -> {
            deleteGlAccount(glAccount, checkDefault, deletedBy);
        });
    }

    public void deleteGlAccounts(List<GlAccount> glAccounts, BasePK deletedBy) {
        deleteGlAccounts(glAccounts, true, deletedBy);
    }

    private void deleteGlAccountsByParentGlAccount(GlAccount parentGlAccount, BasePK deletedBy) {
        deleteGlAccounts(getGlAccountsByParentGlAccountForUpdate(parentGlAccount), false, deletedBy);
    }

    public void deleteGlAccountsByGlAccountClass(GlAccountClass glAccountClass, BasePK deletedBy) {
        deleteGlAccounts(getGlAccountsByGlAccountClassForUpdate(glAccountClass), deletedBy);
    }
    
    public void deleteGlAccountsByGlAccountCategory(GlAccountCategory glAccountCategory, BasePK deletedBy) {
        deleteGlAccounts(getGlAccountsByGlAccountCategoryForUpdate(glAccountCategory), deletedBy);
    }
    
    public void deleteGlAccountsByGlResourceType(GlResourceType glResourceType, BasePK deletedBy) {
        deleteGlAccounts(getGlAccountsByGlResourceTypeForUpdate(glResourceType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Descriptions
    // --------------------------------------------------------------------------------
    
    public GlAccountDescription createGlAccountDescription(GlAccount glAccount, Language language, String description, BasePK createdBy) {
        GlAccountDescription glAccountDescription = GlAccountDescriptionFactory.getInstance().create(glAccount, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(glAccount.getPrimaryKey(), EventTypes.MODIFY.name(), glAccountDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return glAccountDescription;
    }
    
    private GlAccountDescription getGlAccountDescription(GlAccount glAccount, Language language, EntityPermission entityPermission) {
        GlAccountDescription glAccountDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountdescriptions " +
                        "WHERE glad_gla_glaccountid = ? AND glad_lang_languageid = ? AND glad_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountdescriptions " +
                        "WHERE glad_gla_glaccountid = ? AND glad_lang_languageid = ? AND glad_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            glAccountDescription = GlAccountDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountDescription;
    }
    
    public GlAccountDescription getGlAccountDescription(GlAccount glAccount, Language language) {
        return getGlAccountDescription(glAccount, language, EntityPermission.READ_ONLY);
    }
    
    public GlAccountDescription getGlAccountDescriptionForUpdate(GlAccount glAccount, Language language) {
        return getGlAccountDescription(glAccount, language, EntityPermission.READ_WRITE);
    }
    
    public GlAccountDescriptionValue getGlAccountDescriptionValue(GlAccountDescription glAccountDescription) {
        return glAccountDescription == null? null: glAccountDescription.getGlAccountDescriptionValue().clone();
    }
    
    public GlAccountDescriptionValue getGlAccountDescriptionValueForUpdate(GlAccount glAccount, Language language) {
        return getGlAccountDescriptionValue(getGlAccountDescriptionForUpdate(glAccount, language));
    }
    
    private List<GlAccountDescription> getGlAccountDescriptionsByGlAccount(GlAccount glAccount, EntityPermission entityPermission) {
        List<GlAccountDescription> glAccountDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountdescriptions, languages " +
                        "WHERE glad_gla_glaccountid = ? AND glad_thrutime = ? AND glad_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountdescriptions " +
                        "WHERE glad_gla_glaccountid = ? AND glad_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            glAccountDescriptions = GlAccountDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountDescriptions;
    }
    
    public List<GlAccountDescription> getGlAccountDescriptionsByGlAccount(GlAccount glAccount) {
        return getGlAccountDescriptionsByGlAccount(glAccount, EntityPermission.READ_ONLY);
    }
    
    public List<GlAccountDescription> getGlAccountDescriptionsByGlAccountForUpdate(GlAccount glAccount) {
        return getGlAccountDescriptionsByGlAccount(glAccount, EntityPermission.READ_WRITE);
    }
    
    public String getBestGlAccountDescription(GlAccount glAccount, Language language) {
        String description;
        GlAccountDescription glAccountDescription = getGlAccountDescription(glAccount, language);
        
        if(glAccountDescription == null && !language.getIsDefault()) {
            glAccountDescription = getGlAccountDescription(glAccount, getPartyControl().getDefaultLanguage());
        }
        
        if(glAccountDescription == null) {
            description = glAccount.getLastDetail().getGlAccountName();
        } else {
            description = glAccountDescription.getDescription();
        }
        
        return description;
    }
    
    public GlAccountDescriptionTransfer getGlAccountDescriptionTransfer(UserVisit userVisit, GlAccountDescription glAccountDescription) {
        return getAccountingTransferCaches(userVisit).getGlAccountDescriptionTransferCache().getTransfer(glAccountDescription);
    }
    
    public List<GlAccountDescriptionTransfer> getGlAccountDescriptionTransfersByGlAccount(UserVisit userVisit, GlAccount glAccount) {
        List<GlAccountDescription> glAccountDescriptions = getGlAccountDescriptionsByGlAccount(glAccount);
        List<GlAccountDescriptionTransfer> glAccountDescriptionTransfers = new ArrayList<>(glAccountDescriptions.size());
        GlAccountDescriptionTransferCache glAccountDescriptionTransferCache = getAccountingTransferCaches(userVisit).getGlAccountDescriptionTransferCache();
        
        glAccountDescriptions.stream().forEach((glAccountDescription) -> {
            glAccountDescriptionTransfers.add(glAccountDescriptionTransferCache.getTransfer(glAccountDescription));
        });
        
        return glAccountDescriptionTransfers;
    }
    
    public void updateGlAccountDescriptionFromValue(GlAccountDescriptionValue glAccountDescriptionValue, BasePK updatedBy) {
        if(glAccountDescriptionValue.hasBeenModified()) {
            GlAccountDescription glAccountDescription = GlAccountDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, glAccountDescriptionValue.getPrimaryKey());
            
            glAccountDescription.setThruTime(session.START_TIME_LONG);
            glAccountDescription.store();
            
            GlAccount glAccount = glAccountDescription.getGlAccount();
            Language language = glAccountDescription.getLanguage();
            String description = glAccountDescriptionValue.getDescription();
            
            glAccountDescription = GlAccountDescriptionFactory.getInstance().create(glAccount, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(glAccount.getPrimaryKey(), EventTypes.MODIFY.name(), glAccountDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteGlAccountDescription(GlAccountDescription glAccountDescription, BasePK deletedBy) {
        glAccountDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(glAccountDescription.getGlAccountPK(), EventTypes.MODIFY.name(), glAccountDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteGlAccountDescriptionsByGlAccount(GlAccount glAccount, BasePK deletedBy) {
        List<GlAccountDescription> glAccountDescriptions = getGlAccountDescriptionsByGlAccountForUpdate(glAccount);
        
        glAccountDescriptions.stream().forEach((glAccountDescription) -> {
            deleteGlAccountDescription(glAccountDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Gl Account Summaries
    // --------------------------------------------------------------------------------
    
    public GlAccountSummary createGlAccountSummary(GlAccount glAccount, Party groupParty, Period period, Long balance) {
        return GlAccountSummaryFactory.getInstance().create(glAccount, groupParty, period, balance);
    }
    
    private GlAccountSummary getGlAccountSummary(GlAccount glAccount, Party groupParty, Period period, EntityPermission entityPermission) {
        GlAccountSummary glAccountSummary;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountsummaries " +
                        "WHERE glasmy_gla_glaccountid = ? AND glasmy_grouppartyid = ? AND glasmy_prd_periodid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM glaccountsummaries " +
                        "WHERE glasmy_gla_glaccountid = ? AND glasmy_grouppartyid = ? AND glasmy_prd_periodid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GlAccountSummaryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, groupParty.getPrimaryKey().getEntityId());
            ps.setLong(3, period.getPrimaryKey().getEntityId());
            
            glAccountSummary = GlAccountSummaryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return glAccountSummary;
    }
    
    public GlAccountSummary getGlAccountSummary(GlAccount glAccount, Party groupParty, Period period) {
        return getGlAccountSummary(glAccount, groupParty, period, EntityPermission.READ_ONLY);
    }
    
    public GlAccountSummary getGlAccountSummaryForUpdate(GlAccount glAccount, Party groupParty, Period period) {
        return getGlAccountSummary(glAccount, groupParty, period, EntityPermission.READ_WRITE);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Types
    // --------------------------------------------------------------------------------
    
    public TransactionType createTransactionType(String transactionTypeName, Integer sortOrder, BasePK createdBy) {
        TransactionType transactionType = TransactionTypeFactory.getInstance().create();
        TransactionTypeDetail transactionTypeDetail = TransactionTypeDetailFactory.getInstance().create(transactionType, transactionTypeName, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        transactionType = TransactionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionType.getPrimaryKey());
        transactionType.setActiveDetail(transactionTypeDetail);
        transactionType.setLastDetail(transactionTypeDetail);
        transactionType.store();
        
        sendEventUsingNames(transactionType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return transactionType;
    }
    
    private TransactionType getTransactionTypeByName(String transactionTypeName, EntityPermission entityPermission) {
        TransactionType transactionType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypes, transactiontypedetails " +
                        "WHERE trxtyp_activedetailid = trxtypdt_transactiontypedetailid AND trxtypdt_transactiontypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypes, transactiontypedetails " +
                        "WHERE trxtyp_activedetailid = trxtypdt_transactiontypedetailid AND trxtypdt_transactiontypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, transactionTypeName);
            
            transactionType = TransactionTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionType;
    }
    
    public TransactionType getTransactionTypeByName(String transactionTypeName) {
        return getTransactionTypeByName(transactionTypeName, EntityPermission.READ_ONLY);
    }
    
    public TransactionType getTransactionTypeByNameForUpdate(String transactionTypeName) {
        return getTransactionTypeByName(transactionTypeName, EntityPermission.READ_WRITE);
    }
    
    public TransactionTypeDetailValue getTransactionTypeDetailValueByNameForUpdate(String transactionTypeName) {
        return getTransactionTypeDetailValueForUpdate(getTransactionTypeByNameForUpdate(transactionTypeName));
    }
    
    public TransactionTypeDetailValue getTransactionTypeDetailValueForUpdate(TransactionType transactionType) {
        return transactionType == null? null: transactionType.getLastDetailForUpdate().getTransactionTypeDetailValue().clone();
    }
    
    private List<TransactionType> getTransactionTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactiontypes, transactiontypedetails " +
                    "WHERE trxtyp_activedetailid = trxtypdt_transactiontypedetailid " +
                    "ORDER BY trxtypdt_transactiontypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactiontypes, transactiontypedetails " +
                    "WHERE trxtyp_activedetailid = trxtypdt_transactiontypedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TransactionTypeFactory.getInstance().prepareStatement(query);
        
        return TransactionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TransactionType> getTransactionTypes() {
        return getTransactionTypes(EntityPermission.READ_ONLY);
    }
    
    public List<TransactionType> getTransactionTypesForUpdate() {
        return getTransactionTypes(EntityPermission.READ_WRITE);
    }
    
    public TransactionTypeTransfer getTransactionTypeTransfer(UserVisit userVisit, TransactionType transactionType) {
        return getAccountingTransferCaches(userVisit).getTransactionTypeTransferCache().getTransfer(transactionType);
    }
    
    public List<TransactionTypeTransfer> getTransactionTypeTransfers(UserVisit userVisit, List<TransactionType> transactionTypes) {
        List<TransactionTypeTransfer> transactionTypeTransfers = new ArrayList<>(transactionTypes.size());
        TransactionTypeTransferCache transactionTypeTransferCache = getAccountingTransferCaches(userVisit).getTransactionTypeTransferCache();
        
        transactionTypes.stream().forEach((transactionType) -> {
            transactionTypeTransfers.add(transactionTypeTransferCache.getTransfer(transactionType));
        });
        
        return transactionTypeTransfers;
    }
    
    public List<TransactionTypeTransfer> getTransactionTypeTransfers(UserVisit userVisit) {
        return getTransactionTypeTransfers(userVisit, getTransactionTypes());
    }
    
    public void updateTransactionTypeFromValue(TransactionTypeDetailValue transactionTypeDetailValue, BasePK updatedBy) {
        TransactionType transactionType = TransactionTypeFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, transactionTypeDetailValue.getTransactionTypePK());
        TransactionTypeDetail transactionTypeDetail = transactionType.getActiveDetailForUpdate();

        transactionTypeDetail.setThruTime(session.START_TIME_LONG);
        transactionTypeDetail.store();

        TransactionTypePK transactionTypePK = transactionTypeDetail.getTransactionTypePK(); // Not updated
        String transactionTypeName = transactionTypeDetailValue.getTransactionTypeName();
        Integer sortOrder = transactionTypeDetailValue.getSortOrder();

        transactionTypeDetail = TransactionTypeDetailFactory.getInstance().create(transactionTypePK, transactionTypeName, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        transactionType.setActiveDetail(transactionTypeDetail);
        transactionType.setLastDetail(transactionTypeDetail);

        sendEventUsingNames(transactionTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }
    
    public void deleteTransactionType(TransactionType transactionType, BasePK deletedBy) {
        deleteTransactionGlAccountCategoriesByTransactionType(transactionType, deletedBy);
        deleteTransactionEntityRoleTypesByTransactionType(transactionType, deletedBy);
        deleteTransactionTypeDescriptionsByTransactionType(transactionType, deletedBy);
        
        TransactionTypeDetail transactionTypeDetail = transactionType.getLastDetailForUpdate();
        transactionTypeDetail.setThruTime(session.START_TIME_LONG);
        transactionType.setActiveDetail(null);
        transactionType.store();
        
        sendEventUsingNames(transactionType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteTransactionTypes(List<TransactionType> transactionTypes, BasePK deletedBy) {
        transactionTypes.stream().forEach((transactionType) -> {
            deleteTransactionType(transactionType, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Type Descriptions
    // --------------------------------------------------------------------------------
    
    public TransactionTypeDescription createTransactionTypeDescription(TransactionType transactionType, Language language, String description, BasePK createdBy) {
        TransactionTypeDescription transactionTypeDescription = TransactionTypeDescriptionFactory.getInstance().create(transactionType, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(transactionType.getPrimaryKey(), EventTypes.MODIFY.name(), transactionTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return transactionTypeDescription;
    }
    
    private TransactionTypeDescription getTransactionTypeDescription(TransactionType transactionType, Language language, EntityPermission entityPermission) {
        TransactionTypeDescription transactionTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypedescriptions " +
                        "WHERE trxtypd_trxtyp_transactiontypeid = ? AND trxtypd_lang_languageid = ? AND trxtypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypedescriptions " +
                        "WHERE trxtypd_trxtyp_transactiontypeid = ? AND trxtypd_lang_languageid = ? AND trxtypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            transactionTypeDescription = TransactionTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionTypeDescription;
    }
    
    public TransactionTypeDescription getTransactionTypeDescription(TransactionType transactionType, Language language) {
        return getTransactionTypeDescription(transactionType, language, EntityPermission.READ_ONLY);
    }
    
    public TransactionTypeDescription getTransactionTypeDescriptionForUpdate(TransactionType transactionType, Language language) {
        return getTransactionTypeDescription(transactionType, language, EntityPermission.READ_WRITE);
    }
    
    public TransactionTypeDescriptionValue getTransactionTypeDescriptionValue(TransactionTypeDescription transactionTypeDescription) {
        return transactionTypeDescription == null? null: transactionTypeDescription.getTransactionTypeDescriptionValue().clone();
    }
    
    public TransactionTypeDescriptionValue getTransactionTypeDescriptionValueForUpdate(TransactionType transactionType, Language language) {
        return getTransactionTypeDescriptionValue(getTransactionTypeDescriptionForUpdate(transactionType, language));
    }
    
    private List<TransactionTypeDescription> getTransactionTypeDescriptionsByTransactionType(TransactionType transactionType, EntityPermission entityPermission) {
        List<TransactionTypeDescription> transactionTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypedescriptions, languages " +
                        "WHERE trxtypd_trxtyp_transactiontypeid = ? AND trxtypd_thrutime = ? AND trxtypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiontypedescriptions " +
                        "WHERE trxtypd_trxtyp_transactiontypeid = ? AND trxtypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionTypeDescriptions = TransactionTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionTypeDescriptions;
    }
    
    public List<TransactionTypeDescription> getTransactionTypeDescriptionsByTransactionType(TransactionType transactionType) {
        return getTransactionTypeDescriptionsByTransactionType(transactionType, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionTypeDescription> getTransactionTypeDescriptionsByTransactionTypeForUpdate(TransactionType transactionType) {
        return getTransactionTypeDescriptionsByTransactionType(transactionType, EntityPermission.READ_WRITE);
    }
    
    public String getBestTransactionTypeDescription(TransactionType transactionType, Language language) {
        String description;
        TransactionTypeDescription transactionTypeDescription = getTransactionTypeDescription(transactionType, language);
        
        if(transactionTypeDescription == null && !language.getIsDefault()) {
            transactionTypeDescription = getTransactionTypeDescription(transactionType, getPartyControl().getDefaultLanguage());
        }
        
        if(transactionTypeDescription == null) {
            description = transactionType.getLastDetail().getTransactionTypeName();
        } else {
            description = transactionTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public TransactionTypeDescriptionTransfer getTransactionTypeDescriptionTransfer(UserVisit userVisit, TransactionTypeDescription transactionTypeDescription) {
        return getAccountingTransferCaches(userVisit).getTransactionTypeDescriptionTransferCache().getTransfer(transactionTypeDescription);
    }
    
    public List<TransactionTypeDescriptionTransfer> getTransactionTypeDescriptionTransfersByTransactionType(UserVisit userVisit, TransactionType transactionType) {
        List<TransactionTypeDescription> transactionTypeDescriptions = getTransactionTypeDescriptionsByTransactionType(transactionType);
        List<TransactionTypeDescriptionTransfer> transactionTypeDescriptionTransfers = new ArrayList<>(transactionTypeDescriptions.size());
        TransactionTypeDescriptionTransferCache transactionTypeDescriptionTransferCache = getAccountingTransferCaches(userVisit).getTransactionTypeDescriptionTransferCache();
        
        transactionTypeDescriptions.stream().forEach((transactionTypeDescription) -> {
            transactionTypeDescriptionTransfers.add(transactionTypeDescriptionTransferCache.getTransfer(transactionTypeDescription));
        });
        
        return transactionTypeDescriptionTransfers;
    }
    
    public void updateTransactionTypeDescriptionFromValue(TransactionTypeDescriptionValue transactionTypeDescriptionValue, BasePK updatedBy) {
        if(transactionTypeDescriptionValue.hasBeenModified()) {
            TransactionTypeDescription transactionTypeDescription = TransactionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionTypeDescriptionValue.getPrimaryKey());
            
            transactionTypeDescription.setThruTime(session.START_TIME_LONG);
            transactionTypeDescription.store();
            
            TransactionType transactionType = transactionTypeDescription.getTransactionType();
            Language language = transactionTypeDescription.getLanguage();
            String description = transactionTypeDescriptionValue.getDescription();
            
            transactionTypeDescription = TransactionTypeDescriptionFactory.getInstance().create(transactionType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(transactionType.getPrimaryKey(), EventTypes.MODIFY.name(), transactionTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteTransactionTypeDescription(TransactionTypeDescription transactionTypeDescription, BasePK deletedBy) {
        transactionTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(transactionTypeDescription.getTransactionTypePK(), EventTypes.MODIFY.name(), transactionTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteTransactionTypeDescriptionsByTransactionType(TransactionType transactionType, BasePK deletedBy) {
        List<TransactionTypeDescription> transactionTypeDescriptions = getTransactionTypeDescriptionsByTransactionTypeForUpdate(transactionType);
        
        transactionTypeDescriptions.stream().forEach((transactionTypeDescription) -> {
            deleteTransactionTypeDescription(transactionTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Categories
    // --------------------------------------------------------------------------------
    
    public TransactionGlAccountCategory createTransactionGlAccountCategory(TransactionType transactionType, String transactionGlAccountCategoryName, GlAccountCategory glAccountCategory,
            Integer sortOrder, BasePK createdBy) {
        TransactionGlAccountCategory transactionGlAccountCategory = TransactionGlAccountCategoryFactory.getInstance().create();
        TransactionGlAccountCategoryDetail transactionGlAccountCategoryDetail = TransactionGlAccountCategoryDetailFactory.getInstance().create(transactionGlAccountCategory, transactionType,
                transactionGlAccountCategoryName, glAccountCategory, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        transactionGlAccountCategory = TransactionGlAccountCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionGlAccountCategory.getPrimaryKey());
        transactionGlAccountCategory.setActiveDetail(transactionGlAccountCategoryDetail);
        transactionGlAccountCategory.setLastDetail(transactionGlAccountCategoryDetail);
        transactionGlAccountCategory.store();
        
        sendEventUsingNames(transactionGlAccountCategory.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return transactionGlAccountCategory;
    }
    
    private TransactionGlAccountCategory getTransactionGlAccountCategoryByName(TransactionType transactionType, String transactionGlAccountCategoryName, EntityPermission entityPermission) {
        TransactionGlAccountCategory transactionGlAccountCategory;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategories, transactionglaccountcategorydetails " +
                        "WHERE trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid AND trxglacdt_trxtyp_transactiontypeid = ? AND trxglacdt_transactionglaccountcategoryname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategories, transactionglaccountcategorydetails " +
                        "WHERE trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid AND trxglacdt_trxtyp_transactiontypeid = ? AND trxglacdt_transactionglaccountcategoryname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionGlAccountCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            ps.setString(2, transactionGlAccountCategoryName);
            
            transactionGlAccountCategory = TransactionGlAccountCategoryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccountCategory;
    }
    
    public TransactionGlAccountCategory getTransactionGlAccountCategoryByName(TransactionType transactionType, String transactionGlAccountCategoryName) {
        return getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName, EntityPermission.READ_ONLY);
    }
    
    public TransactionGlAccountCategory getTransactionGlAccountCategoryByNameForUpdate(TransactionType transactionType, String transactionGlAccountCategoryName) {
        return getTransactionGlAccountCategoryByName(transactionType, transactionGlAccountCategoryName, EntityPermission.READ_WRITE);
    }
    
    public TransactionGlAccountCategoryDetailValue getTransactionGlAccountCategoryDetailValueByNameForUpdate(TransactionType transactionType, String transactionGlAccountCategoryName) {
        return getTransactionGlAccountCategoryDetailValueForUpdate(getTransactionGlAccountCategoryByNameForUpdate(transactionType, transactionGlAccountCategoryName));
    }
    
    public TransactionGlAccountCategoryDetailValue getTransactionGlAccountCategoryDetailValueForUpdate(TransactionGlAccountCategory transactionGlAccountCategory) {
        return transactionGlAccountCategory == null? null: transactionGlAccountCategory.getLastDetailForUpdate().getTransactionGlAccountCategoryDetailValue().clone();
    }
    
    private List<TransactionGlAccountCategory> getTransactionGlAccountCategories(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionglaccountcategories, transactionglaccountcategorydetails " +
                    "WHERE trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid " +
                    "ORDER BY trxglacdt_sortorder, trxglacdt_transactionglaccountcategoryname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionglaccountcategories, transactionglaccountcategorydetails " +
                    "WHERE trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TransactionGlAccountCategoryFactory.getInstance().prepareStatement(query);
        
        return TransactionGlAccountCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TransactionGlAccountCategory> getTransactionGlAccountCategories() {
        return getTransactionGlAccountCategories(EntityPermission.READ_ONLY);
    }
    
    public List<TransactionGlAccountCategory> getTransactionGlAccountCategoriesForUpdate() {
        return getTransactionGlAccountCategories(EntityPermission.READ_WRITE);
    }
    
    private List<TransactionGlAccountCategory> getTransactionGlAccountCategoriesByTransactionType(TransactionType transactionType, EntityPermission entityPermission) {
        List<TransactionGlAccountCategory> transactionGlAccountCategories = null;
        
        try {
            String query = null;
            
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionglaccountcategories, transactionglaccountcategorydetails " +
                    "WHERE trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid AND trxglacdt_trxtyp_transactiontypeid = ? " +
                    "ORDER BY trxglacdt_sortorder, trxglacdt_transactionglaccountcategoryname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionglaccountcategories, transactionglaccountcategorydetails " +
                    "WHERE trxglac_activedetailid = trxglacdt_transactionglaccountcategorydetailid AND trxglacdt_trxtyp_transactiontypeid = ? " +
                    "FOR UPDATE";
        }
            
            PreparedStatement ps = TransactionGlAccountCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            
            transactionGlAccountCategories = TransactionGlAccountCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccountCategories;
    }
    
    public List<TransactionGlAccountCategory> getTransactionGlAccountCategoriesByTransactionType(TransactionType transactionType) {
        return getTransactionGlAccountCategoriesByTransactionType(transactionType, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionGlAccountCategory> getTransactionGlAccountCategoriesByTransactionTypeForUpdate(TransactionType transactionType) {
        return getTransactionGlAccountCategoriesByTransactionType(transactionType, EntityPermission.READ_WRITE);
    }
    
    public TransactionGlAccountCategoryTransfer getTransactionGlAccountCategoryTransfer(UserVisit userVisit, TransactionGlAccountCategory transactionGlAccountCategory) {
        return getAccountingTransferCaches(userVisit).getTransactionGlAccountCategoryTransferCache().getTransfer(transactionGlAccountCategory);
    }
    
    public List<TransactionGlAccountCategoryTransfer> getTransactionGlAccountCategoryTransfers(UserVisit userVisit, List<TransactionGlAccountCategory> transactionGlAccountCategories) {
        List<TransactionGlAccountCategoryTransfer> transactionGlAccountCategoryTransfers = new ArrayList<>(transactionGlAccountCategories.size());
        TransactionGlAccountCategoryTransferCache transactionGlAccountCategoryTransferCache = getAccountingTransferCaches(userVisit).getTransactionGlAccountCategoryTransferCache();
        
        transactionGlAccountCategories.stream().forEach((transactionGlAccountCategory) -> {
            transactionGlAccountCategoryTransfers.add(transactionGlAccountCategoryTransferCache.getTransfer(transactionGlAccountCategory));
        });
        
        return transactionGlAccountCategoryTransfers;
    }
    
    public List<TransactionGlAccountCategoryTransfer> getTransactionGlAccountCategoryTransfers(UserVisit userVisit) {
        return getTransactionGlAccountCategoryTransfers(userVisit, getTransactionGlAccountCategories());
    }
    
    public List<TransactionGlAccountCategoryTransfer> getTransactionGlAccountCategoryTransfersByTransactionType(UserVisit userVisit, TransactionType transactionType) {
        return getTransactionGlAccountCategoryTransfers(userVisit, getTransactionGlAccountCategoriesByTransactionType(transactionType));
    }
    
    public void updateTransactionGlAccountCategoryFromValue(TransactionGlAccountCategoryDetailValue transactionGlAccountCategoryDetailValue, BasePK updatedBy) {
        TransactionGlAccountCategory transactionGlAccountCategory = TransactionGlAccountCategoryFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, transactionGlAccountCategoryDetailValue.getTransactionGlAccountCategoryPK());
        TransactionGlAccountCategoryDetail transactionGlAccountCategoryDetail = transactionGlAccountCategory.getActiveDetailForUpdate();

        transactionGlAccountCategoryDetail.setThruTime(session.START_TIME_LONG);
        transactionGlAccountCategoryDetail.store();

        TransactionGlAccountCategoryPK transactionGlAccountCategoryPK = transactionGlAccountCategoryDetail.getTransactionGlAccountCategoryPK(); // Not updated
        TransactionTypePK transactionTypePK = transactionGlAccountCategoryDetail.getTransactionTypePK(); // Not updated
        String transactionGlAccountCategoryName = transactionGlAccountCategoryDetailValue.getTransactionGlAccountCategoryName();
        GlAccountCategoryPK glAccountCategoryPK = transactionGlAccountCategoryDetailValue.getGlAccountCategoryPK();
        Integer sortOrder = transactionGlAccountCategoryDetailValue.getSortOrder();

        transactionGlAccountCategoryDetail = TransactionGlAccountCategoryDetailFactory.getInstance().create(transactionGlAccountCategoryPK, transactionTypePK,
                transactionGlAccountCategoryName, glAccountCategoryPK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        transactionGlAccountCategory.setActiveDetail(transactionGlAccountCategoryDetail);
        transactionGlAccountCategory.setLastDetail(transactionGlAccountCategoryDetail);

        sendEventUsingNames(transactionGlAccountCategoryPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }
    
    public void deleteTransactionGlAccountCategory(TransactionGlAccountCategory transactionGlAccountCategory, BasePK deletedBy) {
        // TODO: deleteTransactionGlAccountByTransactionGlAccountCategory(transactionGlAccountCategory, deletedBy);
        deleteTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(transactionGlAccountCategory, deletedBy);
        
        TransactionGlAccountCategoryDetail transactionGlAccountCategoryDetail = transactionGlAccountCategory.getLastDetailForUpdate();
        transactionGlAccountCategoryDetail.setThruTime(session.START_TIME_LONG);
        transactionGlAccountCategory.setActiveDetail(null);
        transactionGlAccountCategory.store();
        
        sendEventUsingNames(transactionGlAccountCategory.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteTransactionGlAccountCategories(List<TransactionGlAccountCategory> transactionGlAccountCategories, BasePK deletedBy) {
        transactionGlAccountCategories.stream().forEach((transactionGlAccountCategory) -> {
            deleteTransactionGlAccountCategory(transactionGlAccountCategory, deletedBy);
        });
    }
    
    public void deleteTransactionGlAccountCategoriesByTransactionType(TransactionType transactionType, BasePK deletedBy) {
        deleteTransactionGlAccountCategories(getTransactionGlAccountCategoriesByTransactionTypeForUpdate(transactionType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Category Descriptions
    // --------------------------------------------------------------------------------
    
     public TransactionGlAccountCategoryDescription createTransactionGlAccountCategoryDescription(TransactionGlAccountCategory transactionGlAccountCategory, Language language, String description,
             BasePK createdBy) {
        TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription = TransactionGlAccountCategoryDescriptionFactory.getInstance().create(transactionGlAccountCategory,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(transactionGlAccountCategory.getPrimaryKey(), EventTypes.MODIFY.name(), transactionGlAccountCategoryDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return transactionGlAccountCategoryDescription;
    }
    
    private TransactionGlAccountCategoryDescription getTransactionGlAccountCategoryDescription(TransactionGlAccountCategory transactionGlAccountCategory, Language language, EntityPermission entityPermission) {
        TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategorydescriptions " +
                        "WHERE trxglacd_trxglac_transactionglaccountcategoryid = ? AND trxglacd_lang_languageid = ? AND trxglacd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategorydescriptions " +
                        "WHERE trxglacd_trxglac_transactionglaccountcategoryid = ? AND trxglacd_lang_languageid = ? AND trxglacd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionGlAccountCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionGlAccountCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            transactionGlAccountCategoryDescription = TransactionGlAccountCategoryDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccountCategoryDescription;
    }
    
    public TransactionGlAccountCategoryDescription getTransactionGlAccountCategoryDescription(TransactionGlAccountCategory transactionGlAccountCategory, Language language) {
        return getTransactionGlAccountCategoryDescription(transactionGlAccountCategory, language, EntityPermission.READ_ONLY);
    }
    
    public TransactionGlAccountCategoryDescription getTransactionGlAccountCategoryDescriptionForUpdate(TransactionGlAccountCategory transactionGlAccountCategory, Language language) {
        return getTransactionGlAccountCategoryDescription(transactionGlAccountCategory, language, EntityPermission.READ_WRITE);
    }
    
    public TransactionGlAccountCategoryDescriptionValue getTransactionGlAccountCategoryDescriptionValue(TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription) {
        return transactionGlAccountCategoryDescription == null? null: transactionGlAccountCategoryDescription.getTransactionGlAccountCategoryDescriptionValue().clone();
    }
    
    public TransactionGlAccountCategoryDescriptionValue getTransactionGlAccountCategoryDescriptionValueForUpdate(TransactionGlAccountCategory transactionGlAccountCategory, Language language) {
        return getTransactionGlAccountCategoryDescriptionValue(getTransactionGlAccountCategoryDescriptionForUpdate(transactionGlAccountCategory, language));
    }
    
    private List<TransactionGlAccountCategoryDescription> getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(TransactionGlAccountCategory transactionGlAccountCategory, EntityPermission entityPermission) {
        List<TransactionGlAccountCategoryDescription> transactionGlAccountCategoryDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategorydescriptions, languages " +
                        "WHERE trxglacd_trxglac_transactionglaccountcategoryid = ? AND trxglacd_thrutime = ? AND trxglacd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccountcategorydescriptions " +
                        "WHERE trxglacd_trxglac_transactionglaccountcategoryid = ? AND trxglacd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionGlAccountCategoryDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionGlAccountCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionGlAccountCategoryDescriptions = TransactionGlAccountCategoryDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccountCategoryDescriptions;
    }
    
    public List<TransactionGlAccountCategoryDescription> getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(TransactionGlAccountCategory transactionGlAccountCategory) {
        return getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(transactionGlAccountCategory, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionGlAccountCategoryDescription> getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategoryForUpdate(TransactionGlAccountCategory transactionGlAccountCategory) {
        return getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(transactionGlAccountCategory, EntityPermission.READ_WRITE);
    }
    
    public String getBestTransactionGlAccountCategoryDescription(TransactionGlAccountCategory transactionGlAccountCategory, Language language) {
        String description;
        TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription = getTransactionGlAccountCategoryDescription(transactionGlAccountCategory, language);
        
        if(transactionGlAccountCategoryDescription == null && !language.getIsDefault()) {
            transactionGlAccountCategoryDescription = getTransactionGlAccountCategoryDescription(transactionGlAccountCategory, getPartyControl().getDefaultLanguage());
        }
        
        if(transactionGlAccountCategoryDescription == null) {
            description = transactionGlAccountCategory.getLastDetail().getTransactionGlAccountCategoryName();
        } else {
            description = transactionGlAccountCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public TransactionGlAccountCategoryDescriptionTransfer getTransactionGlAccountCategoryDescriptionTransfer(UserVisit userVisit, TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription) {
        return getAccountingTransferCaches(userVisit).getTransactionGlAccountCategoryDescriptionTransferCache().getTransfer(transactionGlAccountCategoryDescription);
    }
    
    public List<TransactionGlAccountCategoryDescriptionTransfer> getTransactionGlAccountCategoryDescriptionTransfersByTransactionGlAccountCategory(UserVisit userVisit, TransactionGlAccountCategory transactionGlAccountCategory) {
        List<TransactionGlAccountCategoryDescription> transactionGlAccountCategoryDescriptions = getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(transactionGlAccountCategory);
        List<TransactionGlAccountCategoryDescriptionTransfer> transactionGlAccountCategoryDescriptionTransfers = new ArrayList<>(transactionGlAccountCategoryDescriptions.size());
        TransactionGlAccountCategoryDescriptionTransferCache transactionGlAccountCategoryDescriptionTransferCache = getAccountingTransferCaches(userVisit).getTransactionGlAccountCategoryDescriptionTransferCache();
        
        transactionGlAccountCategoryDescriptions.stream().forEach((transactionGlAccountCategoryDescription) -> {
            transactionGlAccountCategoryDescriptionTransfers.add(transactionGlAccountCategoryDescriptionTransferCache.getTransfer(transactionGlAccountCategoryDescription));
        });
        
        return transactionGlAccountCategoryDescriptionTransfers;
    }
    
    public void updateTransactionGlAccountCategoryDescriptionFromValue(TransactionGlAccountCategoryDescriptionValue transactionGlAccountCategoryDescriptionValue, BasePK updatedBy) {
        if(transactionGlAccountCategoryDescriptionValue.hasBeenModified()) {
            TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription = TransactionGlAccountCategoryDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionGlAccountCategoryDescriptionValue.getPrimaryKey());
            
            transactionGlAccountCategoryDescription.setThruTime(session.START_TIME_LONG);
            transactionGlAccountCategoryDescription.store();
            
            TransactionGlAccountCategory transactionGlAccountCategory = transactionGlAccountCategoryDescription.getTransactionGlAccountCategory();
            Language language = transactionGlAccountCategoryDescription.getLanguage();
            String description = transactionGlAccountCategoryDescriptionValue.getDescription();
            
            transactionGlAccountCategoryDescription = TransactionGlAccountCategoryDescriptionFactory.getInstance().create(transactionGlAccountCategory, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(transactionGlAccountCategory.getPrimaryKey(), EventTypes.MODIFY.name(), transactionGlAccountCategoryDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteTransactionGlAccountCategoryDescription(TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription, BasePK deletedBy) {
        transactionGlAccountCategoryDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(transactionGlAccountCategoryDescription.getTransactionGlAccountCategoryPK(), EventTypes.MODIFY.name(), transactionGlAccountCategoryDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategory(TransactionGlAccountCategory transactionGlAccountCategory, BasePK deletedBy) {
        List<TransactionGlAccountCategoryDescription> transactionGlAccountCategoryDescriptions = getTransactionGlAccountCategoryDescriptionsByTransactionGlAccountCategoryForUpdate(transactionGlAccountCategory);
        
        transactionGlAccountCategoryDescriptions.stream().forEach((transactionGlAccountCategoryDescription) -> {
            deleteTransactionGlAccountCategoryDescription(transactionGlAccountCategoryDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Types
    // --------------------------------------------------------------------------------
    
     public TransactionEntityRoleType createTransactionEntityRoleType(TransactionType transactionType, String transactionEntityRoleTypeName, EntityType entityType,
            Integer sortOrder, BasePK createdBy) {
        TransactionEntityRoleType transactionEntityRoleType = TransactionEntityRoleTypeFactory.getInstance().create();
        TransactionEntityRoleTypeDetail transactionEntityRoleTypeDetail = TransactionEntityRoleTypeDetailFactory.getInstance().create(transactionEntityRoleType, transactionType,
                transactionEntityRoleTypeName, entityType, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        transactionEntityRoleType = TransactionEntityRoleTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionEntityRoleType.getPrimaryKey());
        transactionEntityRoleType.setActiveDetail(transactionEntityRoleTypeDetail);
        transactionEntityRoleType.setLastDetail(transactionEntityRoleTypeDetail);
        transactionEntityRoleType.store();
        
        sendEventUsingNames(transactionEntityRoleType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return transactionEntityRoleType;
    }
    
    private TransactionEntityRoleType getTransactionEntityRoleTypeByName(TransactionType transactionType, String transactionEntityRoleTypeName, EntityPermission entityPermission) {
        TransactionEntityRoleType transactionEntityRoleType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypes, transactionentityroletypedetails " +
                        "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_trxtyp_transactiontypeid = ? AND trxertypdt_transactionentityroletypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypes, transactionentityroletypedetails " +
                        "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_trxtyp_transactiontypeid = ? AND trxertypdt_transactionentityroletypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionEntityRoleTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            ps.setString(2, transactionEntityRoleTypeName);
            
            transactionEntityRoleType = TransactionEntityRoleTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoleType;
    }
    
    public TransactionEntityRoleType getTransactionEntityRoleTypeByName(TransactionType transactionType, String transactionEntityRoleTypeName) {
        return getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName, EntityPermission.READ_ONLY);
    }
    
    public TransactionEntityRoleType getTransactionEntityRoleTypeByNameForUpdate(TransactionType transactionType, String transactionEntityRoleTypeName) {
        return getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName, EntityPermission.READ_WRITE);
    }
    
    public TransactionEntityRoleTypeDetailValue getTransactionEntityRoleTypeDetailValueByNameForUpdate(TransactionType transactionType, String transactionEntityRoleTypeName) {
        return getTransactionEntityRoleTypeDetailValueForUpdate(getTransactionEntityRoleTypeByNameForUpdate(transactionType, transactionEntityRoleTypeName));
    }
    
    public TransactionEntityRoleTypeDetailValue getTransactionEntityRoleTypeDetailValueForUpdate(TransactionEntityRoleType transactionEntityRoleType) {
        return transactionEntityRoleType == null? null: transactionEntityRoleType.getLastDetailForUpdate().getTransactionEntityRoleTypeDetailValue().clone();
    }
    
    private List<TransactionEntityRoleType> getTransactionEntityRoleTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionentityroletypes, transactionentityroletypedetails " +
                    "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid " +
                    "ORDER BY trxertypdt_sortorder, trxertypdt_transactionentityroletypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionentityroletypes, transactionentityroletypedetails " +
                    "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TransactionEntityRoleTypeFactory.getInstance().prepareStatement(query);
        
        return TransactionEntityRoleTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TransactionEntityRoleType> getTransactionEntityRoleTypes() {
        return getTransactionEntityRoleTypes(EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRoleType> getTransactionEntityRoleTypesForUpdate() {
        return getTransactionEntityRoleTypes(EntityPermission.READ_WRITE);
    }
    
    private List<TransactionEntityRoleType> getTransactionEntityRoleTypesByTransactionType(TransactionType transactionType, EntityPermission entityPermission) {
        List<TransactionEntityRoleType> transactionEntityRoleTypes = null;
        
        try {
            String query = null;
            
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionentityroletypes, transactionentityroletypedetails " +
                    "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_trxtyp_transactiontypeid = ? " +
                    "ORDER BY trxertypdt_sortorder, trxertypdt_transactionentityroletypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionentityroletypes, transactionentityroletypedetails " +
                    "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_trxtyp_transactiontypeid = ? " +
                    "FOR UPDATE";
        }
            
            PreparedStatement ps = TransactionEntityRoleTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            
            transactionEntityRoleTypes = TransactionEntityRoleTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoleTypes;
    }
    
    public List<TransactionEntityRoleType> getTransactionEntityRoleTypesByTransactionType(TransactionType transactionType) {
        return getTransactionEntityRoleTypesByTransactionType(transactionType, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRoleType> getTransactionEntityRoleTypesByTransactionTypeForUpdate(TransactionType transactionType) {
        return getTransactionEntityRoleTypesByTransactionType(transactionType, EntityPermission.READ_WRITE);
    }
    
    private List<TransactionEntityRoleType> getTransactionEntityRoleTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<TransactionEntityRoleType> transactionEntityRoleTypes = null;
        
        try {
            String query = null;
            
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionentityroletypes, transactionentityroletypedetails, transactions, transactiondetails " +
                    "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_ent_entitytypeid = ? " +
                    "AND trxertypdt_trxtyp_transactiontypeid = trx_transactionid AND trx_lastdetailid = trxdt_transactiondetailid " +
                    "ORDER BY trxertypdt_sortorder, trxertypdt_transactionentityroletypename, trxdt_transactionname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactionentityroletypes, transactionentityroletypedetails " +
                    "WHERE trxertyp_activedetailid = trxertypdt_transactionentityroletypedetailid AND trxertypdt_trxtyp_transactiontypeid = ? " +
                    "FOR UPDATE";
        }
            
            PreparedStatement ps = TransactionEntityRoleTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            transactionEntityRoleTypes = TransactionEntityRoleTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoleTypes;
    }
    
    public List<TransactionEntityRoleType> getTransactionEntityRoleTypesByEntityType(EntityType entityType) {
        return getTransactionEntityRoleTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRoleType> getTransactionEntityRoleTypesByEntityTypeForUpdate(EntityType entityType) {
        return getTransactionEntityRoleTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    public TransactionEntityRoleTypeTransfer getTransactionEntityRoleTypeTransfer(UserVisit userVisit, TransactionEntityRoleType transactionEntityRoleType) {
        return getAccountingTransferCaches(userVisit).getTransactionEntityRoleTypeTransferCache().getTransfer(transactionEntityRoleType);
    }
    
    public List<TransactionEntityRoleTypeTransfer> getTransactionEntityRoleTypeTransfers(UserVisit userVisit, List<TransactionEntityRoleType> transactionEntityRoleTypes) {
        List<TransactionEntityRoleTypeTransfer> transactionEntityRoleTypeTransfers = new ArrayList<>(transactionEntityRoleTypes.size());
        TransactionEntityRoleTypeTransferCache transactionEntityRoleTypeTransferCache = getAccountingTransferCaches(userVisit).getTransactionEntityRoleTypeTransferCache();
        
        transactionEntityRoleTypes.stream().forEach((transactionEntityRoleType) -> {
            transactionEntityRoleTypeTransfers.add(transactionEntityRoleTypeTransferCache.getTransfer(transactionEntityRoleType));
        });
        
        return transactionEntityRoleTypeTransfers;
    }
    
    public List<TransactionEntityRoleTypeTransfer> getTransactionEntityRoleTypeTransfers(UserVisit userVisit) {
        return getTransactionEntityRoleTypeTransfers(userVisit, getTransactionEntityRoleTypes());
    }
    
    public List<TransactionEntityRoleTypeTransfer> getTransactionEntityRoleTypeTransfersByTransactionType(UserVisit userVisit, TransactionType transactionType) {
        return getTransactionEntityRoleTypeTransfers(userVisit, getTransactionEntityRoleTypesByTransactionType(transactionType));
    }
    
    public List<TransactionEntityRoleTypeTransfer> getTransactionEntityRoleTypeTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getTransactionEntityRoleTypeTransfers(userVisit, getTransactionEntityRoleTypesByEntityType(entityType));
    }
    
    public void updateTransactionEntityRoleTypeFromValue(TransactionEntityRoleTypeDetailValue transactionEntityRoleTypeDetailValue, BasePK updatedBy) {
        TransactionEntityRoleType transactionEntityRoleType = TransactionEntityRoleTypeFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, transactionEntityRoleTypeDetailValue.getTransactionEntityRoleTypePK());
        TransactionEntityRoleTypeDetail transactionEntityRoleTypeDetail = transactionEntityRoleType.getActiveDetailForUpdate();

        transactionEntityRoleTypeDetail.setThruTime(session.START_TIME_LONG);
        transactionEntityRoleTypeDetail.store();

        TransactionEntityRoleTypePK transactionEntityRoleTypePK = transactionEntityRoleTypeDetail.getTransactionEntityRoleTypePK(); // Not updated
        TransactionTypePK transactionTypePK = transactionEntityRoleTypeDetail.getTransactionTypePK(); // Not updated
        String transactionEntityRoleTypeName = transactionEntityRoleTypeDetailValue.getTransactionEntityRoleTypeName();
        EntityTypePK entityTypePK = transactionEntityRoleTypeDetailValue.getEntityTypePK();
        Integer sortOrder = transactionEntityRoleTypeDetailValue.getSortOrder();

        transactionEntityRoleTypeDetail = TransactionEntityRoleTypeDetailFactory.getInstance().create(transactionEntityRoleTypePK, transactionTypePK,
                transactionEntityRoleTypeName, entityTypePK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        transactionEntityRoleType.setActiveDetail(transactionEntityRoleTypeDetail);
        transactionEntityRoleType.setLastDetail(transactionEntityRoleTypeDetail);

        sendEventUsingNames(transactionEntityRoleTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }
    
    public void deleteTransactionEntityRoleType(TransactionEntityRoleType transactionEntityRoleType, BasePK deletedBy) {
        deleteTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(transactionEntityRoleType, deletedBy);
        
        TransactionEntityRoleTypeDetail transactionEntityRoleTypeDetail = transactionEntityRoleType.getLastDetailForUpdate();
        transactionEntityRoleTypeDetail.setThruTime(session.START_TIME_LONG);
        transactionEntityRoleType.setActiveDetail(null);
        transactionEntityRoleType.store();
        
        sendEventUsingNames(transactionEntityRoleType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteTransactionEntityRoleTypes(List<TransactionEntityRoleType> transactionEntityRoleTypes, BasePK deletedBy) {
        transactionEntityRoleTypes.stream().forEach((transactionEntityRoleType) -> {
            deleteTransactionEntityRoleType(transactionEntityRoleType, deletedBy);
        });
    }
    
    public void deleteTransactionEntityRoleTypesByTransactionType(TransactionType transactionType, BasePK deletedBy) {
        deleteTransactionEntityRoleTypes(getTransactionEntityRoleTypesByTransactionTypeForUpdate(transactionType), deletedBy);
    }
    
    public void deleteTransactionEntityRoleTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteTransactionEntityRoleTypes(getTransactionEntityRoleTypesByEntityTypeForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    public TransactionEntityRoleTypeDescription createTransactionEntityRoleTypeDescription(TransactionEntityRoleType transactionEntityRoleType, Language language, String description, BasePK createdBy) {
        TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription = TransactionEntityRoleTypeDescriptionFactory.getInstance().create(transactionEntityRoleType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(transactionEntityRoleType.getPrimaryKey(), EventTypes.MODIFY.name(), transactionEntityRoleTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return transactionEntityRoleTypeDescription;
    }
    
    private TransactionEntityRoleTypeDescription getTransactionEntityRoleTypeDescription(TransactionEntityRoleType transactionEntityRoleType, Language language, EntityPermission entityPermission) {
        TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypedescriptions " +
                        "WHERE trxertypd_trxertyp_transactionentityroletypeid = ? AND trxertypd_lang_languageid = ? AND trxertypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypedescriptions " +
                        "WHERE trxertypd_trxertyp_transactionentityroletypeid = ? AND trxertypd_lang_languageid = ? AND trxertypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionEntityRoleTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionEntityRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            transactionEntityRoleTypeDescription = TransactionEntityRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoleTypeDescription;
    }
    
    public TransactionEntityRoleTypeDescription getTransactionEntityRoleTypeDescription(TransactionEntityRoleType transactionEntityRoleType, Language language) {
        return getTransactionEntityRoleTypeDescription(transactionEntityRoleType, language, EntityPermission.READ_ONLY);
    }
    
    public TransactionEntityRoleTypeDescription getTransactionEntityRoleTypeDescriptionForUpdate(TransactionEntityRoleType transactionEntityRoleType, Language language) {
        return getTransactionEntityRoleTypeDescription(transactionEntityRoleType, language, EntityPermission.READ_WRITE);
    }
    
    public TransactionEntityRoleTypeDescriptionValue getTransactionEntityRoleTypeDescriptionValue(TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription) {
        return transactionEntityRoleTypeDescription == null? null: transactionEntityRoleTypeDescription.getTransactionEntityRoleTypeDescriptionValue().clone();
    }
    
    public TransactionEntityRoleTypeDescriptionValue getTransactionEntityRoleTypeDescriptionValueForUpdate(TransactionEntityRoleType transactionEntityRoleType, Language language) {
        return getTransactionEntityRoleTypeDescriptionValue(getTransactionEntityRoleTypeDescriptionForUpdate(transactionEntityRoleType, language));
    }
    
    private List<TransactionEntityRoleTypeDescription> getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(TransactionEntityRoleType transactionEntityRoleType, EntityPermission entityPermission) {
        List<TransactionEntityRoleTypeDescription> transactionEntityRoleTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypedescriptions, languages " +
                        "WHERE trxertypd_trxertyp_transactionentityroletypeid = ? AND trxertypd_thrutime = ? AND trxertypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroletypedescriptions " +
                        "WHERE trxertypd_trxertyp_transactionentityroletypeid = ? AND trxertypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionEntityRoleTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionEntityRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionEntityRoleTypeDescriptions = TransactionEntityRoleTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoleTypeDescriptions;
    }
    
    public List<TransactionEntityRoleTypeDescription> getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(TransactionEntityRoleType transactionEntityRoleType) {
        return getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(transactionEntityRoleType, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRoleTypeDescription> getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleTypeForUpdate(TransactionEntityRoleType transactionEntityRoleType) {
        return getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(transactionEntityRoleType, EntityPermission.READ_WRITE);
    }
    
    public String getBestTransactionEntityRoleTypeDescription(TransactionEntityRoleType transactionEntityRoleType, Language language) {
        String description;
        TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription = getTransactionEntityRoleTypeDescription(transactionEntityRoleType, language);
        
        if(transactionEntityRoleTypeDescription == null && !language.getIsDefault()) {
            transactionEntityRoleTypeDescription = getTransactionEntityRoleTypeDescription(transactionEntityRoleType, getPartyControl().getDefaultLanguage());
        }
        
        if(transactionEntityRoleTypeDescription == null) {
            description = transactionEntityRoleType.getLastDetail().getTransactionEntityRoleTypeName();
        } else {
            description = transactionEntityRoleTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public TransactionEntityRoleTypeDescriptionTransfer getTransactionEntityRoleTypeDescriptionTransfer(UserVisit userVisit, TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription) {
        return getAccountingTransferCaches(userVisit).getTransactionEntityRoleTypeDescriptionTransferCache().getTransfer(transactionEntityRoleTypeDescription);
    }
    
    public List<TransactionEntityRoleTypeDescriptionTransfer> getTransactionEntityRoleTypeDescriptionTransfersByTransactionEntityRoleType(UserVisit userVisit, TransactionEntityRoleType transactionEntityRoleType) {
        List<TransactionEntityRoleTypeDescription> transactionEntityRoleTypeDescriptions = getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(transactionEntityRoleType);
        List<TransactionEntityRoleTypeDescriptionTransfer> transactionEntityRoleTypeDescriptionTransfers = new ArrayList<>(transactionEntityRoleTypeDescriptions.size());
        TransactionEntityRoleTypeDescriptionTransferCache transactionEntityRoleTypeDescriptionTransferCache = getAccountingTransferCaches(userVisit).getTransactionEntityRoleTypeDescriptionTransferCache();
        
        transactionEntityRoleTypeDescriptions.stream().forEach((transactionEntityRoleTypeDescription) -> {
            transactionEntityRoleTypeDescriptionTransfers.add(transactionEntityRoleTypeDescriptionTransferCache.getTransfer(transactionEntityRoleTypeDescription));
        });
        
        return transactionEntityRoleTypeDescriptionTransfers;
    }
    
    public void updateTransactionEntityRoleTypeDescriptionFromValue(TransactionEntityRoleTypeDescriptionValue transactionEntityRoleTypeDescriptionValue, BasePK updatedBy) {
        if(transactionEntityRoleTypeDescriptionValue.hasBeenModified()) {
            TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription = TransactionEntityRoleTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionEntityRoleTypeDescriptionValue.getPrimaryKey());
            
            transactionEntityRoleTypeDescription.setThruTime(session.START_TIME_LONG);
            transactionEntityRoleTypeDescription.store();
            
            TransactionEntityRoleType transactionEntityRoleType = transactionEntityRoleTypeDescription.getTransactionEntityRoleType();
            Language language = transactionEntityRoleTypeDescription.getLanguage();
            String description = transactionEntityRoleTypeDescriptionValue.getDescription();
            
            transactionEntityRoleTypeDescription = TransactionEntityRoleTypeDescriptionFactory.getInstance().create(transactionEntityRoleType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(transactionEntityRoleType.getPrimaryKey(), EventTypes.MODIFY.name(), transactionEntityRoleTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteTransactionEntityRoleTypeDescription(TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription, BasePK deletedBy) {
        transactionEntityRoleTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(transactionEntityRoleTypeDescription.getTransactionEntityRoleTypePK(), EventTypes.MODIFY.name(), transactionEntityRoleTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleType(TransactionEntityRoleType transactionEntityRoleType, BasePK deletedBy) {
        List<TransactionEntityRoleTypeDescription> transactionEntityRoleTypeDescriptions = getTransactionEntityRoleTypeDescriptionsByTransactionEntityRoleTypeForUpdate(transactionEntityRoleType);
        
        transactionEntityRoleTypeDescriptions.stream().forEach((transactionEntityRoleTypeDescription) -> {
            deleteTransactionEntityRoleTypeDescription(transactionEntityRoleTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Accounts
    // --------------------------------------------------------------------------------
    
     public TransactionGlAccount createTransactionGlAccount(TransactionGlAccountCategory transactionGlAccountCategory, GlAccount glAccount, BasePK createdBy) {
        TransactionGlAccount transactionGlAccount = TransactionGlAccountFactory.getInstance().create(transactionGlAccountCategory, glAccount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(transactionGlAccountCategory.getPrimaryKey(), EventTypes.MODIFY.name(), transactionGlAccount.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return transactionGlAccount;
    }
    
    private List<TransactionGlAccount> getTransactionGlAccountsByGlAccount(GlAccount glAccount, EntityPermission entityPermission) {
        List<TransactionGlAccount> transactionGlAccounts = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccounts, transactionglaccountcategories, transactionglaccountcategorydetails, transactiontypes, transactiontypedetails " +
                        "WHERE trxgla_gla_glaccountid = ? AND trxgla_thrutime = ? " +
                        "AND trxgla_trxglac_transactionglaccountcategoryid = trxglac_transactionglaccountcategoryid AND trxglac_lastdetailid = trxglacdt_transactionglaccountcategorydetailid " +
                        "AND trxglacdt_trxtyp_transactiontypeid = trxtyp_transactiontypeid AND trxtyp_lastdetailid = trxtypdt_transactiontypedetailid " +
                        "ORDER BY trxglacdt_sortorder, trxglacdt_transactionglaccountcategoryname, trxtypdt_sortorder, trxtypdt_transactiontypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccounts " +
                        "WHERE trxgla_gla_glaccountid = ? AND trxgla_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionGlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, glAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionGlAccounts = TransactionGlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccounts;
    }
    
    public List<TransactionGlAccount> getTransactionGlAccountsByGlAccount(GlAccount glAccount) {
        return getTransactionGlAccountsByGlAccount(glAccount, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionGlAccount> getTransactionGlAccountsByGlAccountForUpdate(GlAccount glAccount) {
        return getTransactionGlAccountsByGlAccount(glAccount, EntityPermission.READ_WRITE);
    }
    
    private TransactionGlAccount getTransactionGlAccount(TransactionGlAccountCategory transactionGlAccountCategory, EntityPermission entityPermission) {
        TransactionGlAccount transactionGlAccount;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccounts " +
                        "WHERE trxgla_trxglac_transactionglaccountcategoryid = ? AND trxgla_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionglaccounts " +
                        "WHERE trxgla_trxglac_transactionglaccountcategoryid = ? AND trxgla_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionGlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transactionGlAccountCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionGlAccount = TransactionGlAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlAccount;
    }
    
    public TransactionGlAccount getTransactionGlAccount(TransactionGlAccountCategory transactionGlAccountCategory) {
        return getTransactionGlAccount(transactionGlAccountCategory, EntityPermission.READ_ONLY);
    }
    
    public TransactionGlAccount getTransactionGlAccountForUpdate(TransactionGlAccountCategory transactionGlAccountCategory) {
        return getTransactionGlAccount(transactionGlAccountCategory, EntityPermission.READ_WRITE);
    }
    
    public TransactionGlAccountValue getTransactionGlAccountValue(TransactionGlAccount transactionGlAccount) {
        return transactionGlAccount == null? null: transactionGlAccount.getTransactionGlAccountValue().clone();
    }
    
    public TransactionGlAccountValue getTransactionGlAccountValueForUpdate(TransactionGlAccountCategory transactionGlAccountCategory) {
        return getTransactionGlAccountValue(getTransactionGlAccountForUpdate(transactionGlAccountCategory));
    }
    
    public TransactionGlAccountTransfer getTransactionGlAccountTransfer(UserVisit userVisit, TransactionGlAccount transactionGlAccount) {
        return getAccountingTransferCaches(userVisit).getTransactionGlAccountTransferCache().getTransfer(transactionGlAccount);
    }
    
    public void updateTransactionGlAccountFromValue(TransactionGlAccountValue transactionGlAccountValue, BasePK updatedBy) {
        if(transactionGlAccountValue.hasBeenModified()) {
            TransactionGlAccount transactionGlAccount = TransactionGlAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, transactionGlAccountValue.getPrimaryKey());
            
            transactionGlAccount.setThruTime(session.START_TIME_LONG);
            transactionGlAccount.store();
            
            TransactionGlAccountCategoryPK transactionGlAccountCategoryPK = transactionGlAccount.getTransactionGlAccountCategoryPK();
            GlAccountPK glAccountPK = transactionGlAccountValue.getGlAccountPK();
            
            transactionGlAccount = TransactionGlAccountFactory.getInstance().create(transactionGlAccountCategoryPK, glAccountPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(transactionGlAccountCategoryPK, EventTypes.MODIFY.name(), transactionGlAccount.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteTransactionGlAccount(TransactionGlAccount transactionGlAccount, BasePK deletedBy) {
        transactionGlAccount.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(transactionGlAccount.getTransactionGlAccountCategoryPK(), EventTypes.MODIFY.name(), transactionGlAccount.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteTransactionGlAccountByTransactionGlAccountCategory(TransactionGlAccountCategory transactionGlAccountCategory, BasePK deletedBy) {
        TransactionGlAccount transactionGlAccount = getTransactionGlAccount(transactionGlAccountCategory);
        
        if(transactionGlAccount != null) {
            deleteTransactionGlAccount(transactionGlAccount, deletedBy);
        }
    }
    
    public void deleteTransactionGlAccounts(List<TransactionGlAccount> transactionGlAccounts, BasePK deletedBy) {
        transactionGlAccounts.stream().forEach((transactionGlAccount) -> {
            deleteTransactionGlAccount(transactionGlAccount, deletedBy);
        });
    }
    
    public void deleteTransactionGlAccountByGlAccount(GlAccount glAccount, BasePK deletedBy) {
        deleteTransactionGlAccounts(getTransactionGlAccountsByGlAccountForUpdate(glAccount), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Groups
    // --------------------------------------------------------------------------------
    
    public TransactionGroup getActiveTransactionGroup(BasePK createdBy) {
        WorkflowStep workflowStep = getWorkflowControl().getWorkflowStepUsingNames(Workflow_TRANSACTION_GROUP_STATUS,
                WorkflowStep_TRANSACTION_GROUP_STATUS_ACTIVE);
        TransactionGroup transactionGroup = null;
        
        if(workflowStep != null) {
            List<TransactionGroup> transactionGroups = null;
            
            try {
                PreparedStatement ps = TransactionGroupFactory.getInstance().prepareStatement(
                        "SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, " +
                        "transactiongroups, transactiongroupdetails, workflowentitystatuses, entitytimes " +
                        "WHERE trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid " +
                        "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                        "AND ent_activedetailid = entdt_entitytypedetailid " +
                        "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                        "AND entdt_entitytypename = ? " +
                        "AND ent_entitytypeid = eni_ent_entitytypeid AND trxgrp_transactiongroupid = eni_entityuniqueid " +
                        "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                        "AND eni_entityinstanceid = etim_eni_entityinstanceid " +
                        "ORDER BY etim_createdtime DESC");
                
                ps.setString(1, ComponentVendors.ECHOTHREE.name());
                ps.setString(2, EntityTypes.TransactionGroup.name());
                ps.setLong(3, workflowStep.getPrimaryKey().getEntityId());
                ps.setLong(4, Session.MAX_TIME);
                
                transactionGroups = TransactionGroupFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
            
            if(transactionGroups != null && !transactionGroups.isEmpty()) {
                transactionGroup = transactionGroups.iterator().next();
            } else {
                transactionGroup = createTransactionGroup(createdBy);
            }
        }
        
        return transactionGroup;
    }
    
    public TransactionGroup createTransactionGroup(BasePK createdBy) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        Sequence sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.TRANSACTION_GROUP.name());
        String transactionGroupName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        TransactionGroup transactionGroup = createTransactionGroup(transactionGroupName, createdBy);
        
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(transactionGroup);
        getWorkflowControl().addEntityToWorkflowUsingNames(null, Workflow_TRANSACTION_GROUP_STATUS, entityInstance, null, null,createdBy);
        
        return transactionGroup;
    }
    
    public TransactionGroup createTransactionGroup(String transactionGroupName, BasePK createdBy) {
        TransactionGroup transactionGroup = TransactionGroupFactory.getInstance().create();
        TransactionGroupDetail transactionGroupDetail = TransactionGroupDetailFactory.getInstance().create(transactionGroup,
                transactionGroupName, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        transactionGroup = TransactionGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                transactionGroup.getPrimaryKey());
        transactionGroup.setActiveDetail(transactionGroupDetail);
        transactionGroup.setLastDetail(transactionGroupDetail);
        transactionGroup.store();
        
        sendEventUsingNames(transactionGroup.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return transactionGroup;
    }
    
    public TransactionGroupDetailValue getTransactionGroupDetailValueForUpdate(TransactionGroup transactionGroup) {
        return transactionGroup.getLastDetailForUpdate().getTransactionGroupDetailValue().clone();
    }
    
    private TransactionGroup getTransactionGroupByName(String transactionGroupName, EntityPermission entityPermission) {
        TransactionGroup transactionGroup;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiongroups, transactiongroupdetails " +
                        "WHERE trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid AND trxgrpdt_transactiongroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactiongroups, transactiongroupdetails " +
                        "WHERE trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid AND trxgrpdt_transactiongroupname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionGroupFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, transactionGroupName);
            
            transactionGroup = TransactionGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGroup;
    }
    
    public TransactionGroup getTransactionGroupByName(String transactionGroupName) {
        return getTransactionGroupByName(transactionGroupName, EntityPermission.READ_ONLY);
    }
    
    public TransactionGroup getTransactionGroupByNameForUpdate(String transactionGroupName) {
        return getTransactionGroupByName(transactionGroupName, EntityPermission.READ_WRITE);
    }
    
    public TransactionGroupDetailValue getTransactionGroupDetailValueByNameForUpdate(String transactionGroupName) {
        return getTransactionGroupDetailValueForUpdate(getTransactionGroupByNameForUpdate(transactionGroupName));
    }
    
    private List<TransactionGroup> getTransactionGroups(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM transactiongroups, transactiongroupdetails " +
                    "WHERE trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid " +
                    "ORDER BY trxgrpdt_transactiongroupname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM transactiongroups, transactiongroupdetails " +
                    "WHERE trxgrp_activedetailid = trxgrpdt_transactiongroupdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TransactionGroupFactory.getInstance().prepareStatement(query);
        
        return TransactionGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<TransactionGroup> getTransactionGroups() {
        return getTransactionGroups(EntityPermission.READ_ONLY);
    }
    
    public List<TransactionGroup> getTransactionGroupsForUpdate() {
        return getTransactionGroups(EntityPermission.READ_WRITE);
    }
    
    public TransactionGroupTransfer getTransactionGroupTransfer(UserVisit userVisit, TransactionGroup transactionGroup) {
        return getAccountingTransferCaches(userVisit).getTransactionGroupTransferCache().getTransfer(transactionGroup);
    }
    
    public List<TransactionGroupTransfer> getTransactionGroupTransfers(UserVisit userVisit, List<TransactionGroup> transactionGroups) {
        List<TransactionGroupTransfer> transactionGroupTransfers = new ArrayList<>(transactionGroups.size());
        TransactionGroupTransferCache transactionGroupTransferCache = getAccountingTransferCaches(userVisit).getTransactionGroupTransferCache();
        
        transactionGroups.stream().forEach((transactionGroup) -> {
            transactionGroupTransfers.add(transactionGroupTransferCache.getTransfer(transactionGroup));
        });
        
        return transactionGroupTransfers;
    }
    
    public List<TransactionGroupTransfer> getTransactionGroupTransfers(UserVisit userVisit) {
        return getTransactionGroupTransfers(userVisit, getTransactionGroups());
    }
    
    public TransactionGroupStatusChoicesBean getTransactionGroupStatusChoices(String defaultTransactionGroupStatusChoice, Language language, boolean allowNullChoice,
            TransactionGroup transactionGroup, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        TransactionGroupStatusChoicesBean transactionGroupStatusChoicesBean = new TransactionGroupStatusChoicesBean();
        
        if(transactionGroup == null) {
            workflowControl.getWorkflowEntranceChoices(transactionGroupStatusChoicesBean, defaultTransactionGroupStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_TRANSACTION_GROUP_STATUS), partyPK);
        } else {
            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(transactionGroup.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_TRANSACTION_GROUP_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(transactionGroupStatusChoicesBean, defaultTransactionGroupStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return transactionGroupStatusChoicesBean;
    }
    
    public void setTransactionGroupStatus(ExecutionErrorAccumulator eea, TransactionGroup transactionGroup, String transactionGroupStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(transactionGroup);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(Workflow_TRANSACTION_GROUP_STATUS, entityInstance);
        WorkflowDestination workflowDestination = transactionGroupStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), transactionGroupStatusChoice);
        
        if(workflowDestination != null || transactionGroupStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownTransactionGroupStatusChoice.name(), transactionGroupStatusChoice);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Transactions
    // --------------------------------------------------------------------------------
    
    public Transaction createTransaction(Party groupParty, TransactionType transactionType, Long postingTime, BasePK createdBy) {
        return createTransaction(groupParty, getActiveTransactionGroup(createdBy), transactionType, postingTime, createdBy);
    }
    
    public Transaction createTransaction(Party groupParty, TransactionGroup transactionGroup, TransactionType transactionType, Long postingTime,
            BasePK createdBy) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        Sequence sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.TRANSACTION.name());
        String transactionName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createTransaction(transactionName, groupParty, transactionGroup, transactionType, postingTime, createdBy);
    }
    
    public Transaction createTransaction(String transactionName, Party groupParty, TransactionGroup transactionGroup,
            TransactionType transactionType, Long postingTime, BasePK createdBy) {
        Transaction transaction = TransactionFactory.getInstance().create();
        TransactionDetail transactionDetail = TransactionDetailFactory.getInstance().create(transaction, transactionName,
                groupParty, transactionGroup, transactionType, postingTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        transaction = TransactionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                transaction.getPrimaryKey());
        transaction.setActiveDetail(transactionDetail);
        transaction.setLastDetail(transactionDetail);
        transaction.store();
        
        sendEventUsingNames(transaction.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        createTransactionStatus(transaction);
        
        return transaction;
    }
    
    public Transaction getTransactionByName(String transactionName) {
        Transaction transaction;
        
        try {
            PreparedStatement ps = TransactionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM transactions, transactiondetails " +
                    "WHERE trx_activedetailid = trxdt_transactiondetailid AND trxdt_transactionname = ?");
            
            ps.setString(1, transactionName);
            
            transaction = TransactionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transaction;
    }
    
    public List<Transaction> getTransactionsByTransactionGroup(TransactionGroup transactionGroup) {
        List<Transaction> transactions = null;
        
        try {
            PreparedStatement ps = TransactionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM transactions, transactiondetails " +
                    "WHERE trx_activedetailid = trxdt_transactiondetailid AND trxdt_trxgrp_transactiongroupid = ? " +
                    "ORDER BY trxdt_transactionname");
            
            ps.setLong(1, transactionGroup.getPrimaryKey().getEntityId());
            
            transactions = TransactionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactions;
    }
    
    public List<Transaction> getTransactionsByTransactionType(TransactionType transactionType) {
        List<Transaction> transactions = null;
        
        try {
            PreparedStatement ps = TransactionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM transactions, transactiondetails " +
                    "WHERE trx_activedetailid = trxdt_transactiondetailid AND trxdt_trxtyp_transactiontypeid = ? " +
                    "ORDER BY trxdt_transactionname");
            
            ps.setLong(1, transactionType.getPrimaryKey().getEntityId());
            
            transactions = TransactionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactions;
    }
    
    public TransactionTransfer getTransactionTransfer(UserVisit userVisit, Transaction transaction) {
        return getAccountingTransferCaches(userVisit).getTransactionTransferCache().getTransfer(transaction);
    }
    
    public List<TransactionTransfer> getTransactionTransfers(UserVisit userVisit, List<Transaction> transactions) {
        List<TransactionTransfer> transactionTransfers = new ArrayList<>(transactions.size());
        TransactionTransferCache transactionTransferCache = getAccountingTransferCaches(userVisit).getTransactionTransferCache();
        
        transactions.stream().forEach((transaction) -> {
            transactionTransfers.add(transactionTransferCache.getTransfer(transaction));
        });
        
        return transactionTransfers;
    }
    
    public List<TransactionTransfer> getTransactionTransfersByTransactionGroup(UserVisit userVisit, TransactionGroup transactionGroup) {
        return getTransactionTransfers(userVisit, getTransactionsByTransactionGroup(transactionGroup));
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Statuses
    // --------------------------------------------------------------------------------
    
    public TransactionStatus createTransactionStatus(Transaction transaction) {
        return TransactionStatusFactory.getInstance().create(transaction, 0);
    }
    
    private TransactionStatus getTransactionStatus(Transaction transaction, EntityPermission entityPermission) {
        TransactionStatus transactionStatus;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionstatuses " +
                        "WHERE trxst_trx_transactionid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionstatuses " +
                        "WHERE trxst_trx_transactionid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transaction.getPrimaryKey().getEntityId());
            
            transactionStatus = TransactionStatusFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionStatus;
    }
    
    public TransactionStatus getTransactionStatus(Transaction transaction) {
        return getTransactionStatus(transaction, EntityPermission.READ_ONLY);
    }
    
    public TransactionStatus getTransactionStatusForUpdate(Transaction transaction) {
        return getTransactionStatus(transaction, EntityPermission.READ_WRITE);
    }
    
    public void removeTransactionStatusByTransaction(Transaction transaction) {
        TransactionStatus transactionStatus = getTransactionStatusForUpdate(transaction);
        
        if(transactionStatus != null) {
            transactionStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Gl Account Entries
    // --------------------------------------------------------------------------------
    
    public TransactionGlEntry createTransactionGlEntry(Transaction transaction, Integer transactionGlEntrySequence, TransactionGlEntry parentTransactionGlEntry, Party groupParty,
            TransactionGlAccountCategory transactionGlAccountCategory, GlAccount glAccount, Currency originalCurrency, Long originalAmount, Long amount, BasePK createdBy) {
        TransactionGlEntry transactionGlEntry = TransactionGlEntryFactory.getInstance().create(transaction, transactionGlEntrySequence, parentTransactionGlEntry, groupParty,
                transactionGlAccountCategory, glAccount, originalCurrency, originalAmount, amount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(transaction.getPrimaryKey(), EventTypes.MODIFY.name(), transactionGlEntry.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return transactionGlEntry;
    }
    
    public List<TransactionGlEntry> getTransactionGlEntriesByTransaction(Transaction transaction) {
        List<TransactionGlEntry> transactionGlEntries = null;
        
        try {
            PreparedStatement ps = TransactionGlEntryFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM transactionglentries " +
                    "WHERE trxglent_trx_transactionid = ? AND trxglent_thrutime = ? " +
                    "ORDER BY trxglent_transactionglentrysequence");
            
            ps.setLong(1, transaction.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionGlEntries = TransactionGlEntryFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlEntries;
    }
    
    public List<TransactionGlEntry> getTransactionGlEntriesByGlAccount(GlAccount glAccount) {
        List<TransactionGlEntry> transactionGlEntries = null;
        
        try {
            PreparedStatement ps = TransactionGlEntryFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM transactionglentries, transactionglaccounts " +
                    "WHERE trxgla_gla_glaccountid = ? AND trxglent_thrutime = ? " +
                    "AND trxglent_trxgla_transactionglaccountid = trxgla_transactionglaccountid");
            // TODO: Sort by transaction's entity instance's entity times's created time
            
            ps.setLong(1, glAccount.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionGlEntries = TransactionGlEntryFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionGlEntries;
    }
    
    public TransactionGlEntryTransfer getTransactionGlEntryTransfer(UserVisit userVisit, TransactionGlEntry transactionGlEntry) {
        return getAccountingTransferCaches(userVisit).getTransactionGlEntryTransferCache().getTransfer(transactionGlEntry);
    }
    
    public List<TransactionGlEntryTransfer> getTransactionGlEntryTransfers(UserVisit userVisit, List<TransactionGlEntry> transactionGlEntries) {
        List<TransactionGlEntryTransfer> transactionGlEntryTransfers = new ArrayList<>(transactionGlEntries.size());
        TransactionGlEntryTransferCache transactionGlEntryTransferCache = getAccountingTransferCaches(userVisit).getTransactionGlEntryTransferCache();
        
        transactionGlEntries.stream().forEach((transactionGlEntry) -> {
            transactionGlEntryTransfers.add(transactionGlEntryTransferCache.getTransfer(transactionGlEntry));
        });
        
        return transactionGlEntryTransfers;
    }
    
    public List<TransactionGlEntryTransfer> getTransactionGlEntryTransfersByTransaction(UserVisit userVisit, Transaction transaction) {
        return getTransactionGlEntryTransfers(userVisit, getTransactionGlEntriesByTransaction(transaction));
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Entity Roles
    // --------------------------------------------------------------------------------
    
    public TransactionEntityRole createTransactionEntityRole(Transaction transaction, TransactionEntityRoleType transactionEntityRoleType, EntityInstance entityInstance, BasePK createdBy) {
        TransactionEntityRole transactionEntityRole = TransactionEntityRoleFactory.getInstance().create(transaction, transactionEntityRoleType, entityInstance, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(transaction.getPrimaryKey(), EventTypes.MODIFY.name(), transactionEntityRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return transactionEntityRole;
    }
    
    public TransactionEntityRole getTransactionEntityRole(Transaction transaction, TransactionEntityRoleType transactionEntityRoleType) {
        TransactionEntityRole transactionEntityRole;
        
        try {
            PreparedStatement ps = TransactionEntityRoleFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM transactionentityroles " +
                    "WHERE trxer_trx_transactionid = ? AND trxer_trxertyp_transactionentityroletypeid = ? AND trxer_thrutime = ?");
            
            ps.setLong(1, transaction.getPrimaryKey().getEntityId());
            ps.setLong(2, transactionEntityRoleType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            transactionEntityRole = TransactionEntityRoleFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRole;
    }
    
    private List<TransactionEntityRole> getTransactionEntityRolesByTransaction(Transaction transaction, EntityPermission entityPermission) {
        List<TransactionEntityRole> transactionEntityRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroles, transactionentityroletypes, transactionentityroletypedetails " +
                        "WHERE trxer_trx_transactionid = ? AND trxer_thrutime = ? " +
                        "AND trxer_trxertyp_transactionentityroletypeid = trxertyp_transactionentityroletypeid AND trxertyp_lastdetailid = trxertypdt_transactionentityroletypedetailid " +
                        "ORDER BY trxertypdt_sortorder, trxertypdt_transactionentityroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroles " +
                        "WHERE trxer_trx_transactionid = ? AND trxer_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionEntityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, transaction.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionEntityRoles = TransactionEntityRoleFactory.getInstance().getEntitiesFromQuery( entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoles;
    }
    
    public List<TransactionEntityRole> getTransactionEntityRolesByTransaction(Transaction transaction) {
        return getTransactionEntityRolesByTransaction(transaction, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRole> getTransactionEntityRolesByTransactionForUpdate(Transaction transaction) {
        return getTransactionEntityRolesByTransaction(transaction, EntityPermission.READ_WRITE);
    }
    
    private List<TransactionEntityRole> getTransactionEntityRolesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<TransactionEntityRole> transactionEntityRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroles " +
                        "WHERE trxer_eni_entityinstanceid = ? AND trxer_thrutime = ? " +
                        "ORDER BY trxer_transactionentityroleid"; // TODO: this should probably be ordered by something else
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM transactionentityroles " +
                        "WHERE trxer_eni_entityinstanceid = ? AND trxer_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TransactionEntityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            transactionEntityRoles = TransactionEntityRoleFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return transactionEntityRoles;
    }
    
    public List<TransactionEntityRole> getTransactionEntityRolesByEntityInstance(EntityInstance entityInstance) {
        return getTransactionEntityRolesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<TransactionEntityRole> getTransactionEntityRolesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTransactionEntityRolesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public TransactionEntityRoleTransfer getTransactionEntityRoleTransfer(UserVisit userVisit, TransactionEntityRole transactionEntityRole) {
        return getAccountingTransferCaches(userVisit).getTransactionEntityRoleTransferCache().getTransfer(transactionEntityRole);
    }
    
    public List<TransactionEntityRoleTransfer> getTransactionEntityRoleTransfers(UserVisit userVisit, List<TransactionEntityRole> transactionEntityRoles) {
        List<TransactionEntityRoleTransfer> transactionEntityRoleTransfers = new ArrayList<>(transactionEntityRoles.size());
        TransactionEntityRoleTransferCache transactionEntityRoleTransferCache = getAccountingTransferCaches(userVisit).getTransactionEntityRoleTransferCache();
        
        transactionEntityRoles.stream().forEach((transactionEntityRole) -> {
            transactionEntityRoleTransfers.add(transactionEntityRoleTransferCache.getTransfer(transactionEntityRole));
        });
        
        return transactionEntityRoleTransfers;
    }
    
    public List<TransactionEntityRoleTransfer> getTransactionEntityRoleTransfersByTransaction(UserVisit userVisit, Transaction transaction) {
        return getTransactionEntityRoleTransfers(userVisit, getTransactionEntityRolesByTransaction(transaction));
    }
    
    public void deleteTransactionEntityRole(TransactionEntityRole transactionEntityRole, BasePK deletedBy) {
        transactionEntityRole.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(transactionEntityRole.getTransactionPK(), EventTypes.MODIFY.name(), transactionEntityRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteTransactionEntityRoles(List<TransactionEntityRole> transactionEntityRoles, BasePK deletedBy) {
        transactionEntityRoles.stream().forEach((transactionEntityRole) -> {
            deleteTransactionEntityRole(transactionEntityRole, deletedBy);
        });
    }
    
    public void deleteTransactionEntityRolesByTransaction(Transaction transaction, BasePK deletedBy) {
        deleteTransactionEntityRoles(getTransactionEntityRolesByTransactionForUpdate(transaction), deletedBy);
    }
    
    public void deleteTransactionEntityRolesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteTransactionEntityRoles(getTransactionEntityRolesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Symbol Position
    // --------------------------------------------------------------------------------
    
    public SymbolPosition createSymbolPosition(String symbolPositionName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SymbolPosition defaultSymbolPosition = getDefaultSymbolPosition();
        boolean defaultFound = defaultSymbolPosition != null;
        
        if(defaultFound && isDefault) {
            SymbolPositionDetailValue defaultSymbolPositionDetailValue = getDefaultSymbolPositionDetailValueForUpdate();
            
            defaultSymbolPositionDetailValue.setIsDefault(Boolean.FALSE);
            updateSymbolPositionFromValue(defaultSymbolPositionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        SymbolPosition symbolPosition = SymbolPositionFactory.getInstance().create();
        SymbolPositionDetail symbolPositionDetail = SymbolPositionDetailFactory.getInstance().create(session,
                symbolPosition, symbolPositionName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        symbolPosition = SymbolPositionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, symbolPosition.getPrimaryKey());
        symbolPosition.setActiveDetail(symbolPositionDetail);
        symbolPosition.setLastDetail(symbolPositionDetail);
        symbolPosition.store();
        
        sendEventUsingNames(symbolPosition.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return symbolPosition;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SymbolPosition */
    public SymbolPosition getSymbolPositionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        SymbolPositionPK pk = new SymbolPositionPK(entityInstance.getEntityUniqueId());
        SymbolPosition symbolPosition = SymbolPositionFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return symbolPosition;
    }

    public SymbolPosition getSymbolPositionByEntityInstance(EntityInstance entityInstance) {
        return getSymbolPositionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SymbolPosition getSymbolPositionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSymbolPositionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private SymbolPosition getSymbolPositionByName(String symbolPositionName, EntityPermission entityPermission) {
        SymbolPosition symbolPosition;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_symbolpositionname = ? AND symposdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_symbolpositionname = ? AND symposdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SymbolPositionFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, symbolPositionName);
            ps.setLong(2, Session.MAX_TIME);
            
            symbolPosition = SymbolPositionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return symbolPosition;
    }
    
    public SymbolPosition getSymbolPositionByName(String symbolPositionName) {
        return getSymbolPositionByName(symbolPositionName, EntityPermission.READ_ONLY);
    }
    
    public SymbolPosition getSymbolPositionByNameForUpdate(String symbolPositionName) {
        return getSymbolPositionByName(symbolPositionName, EntityPermission.READ_WRITE);
    }
    
    public SymbolPositionDetailValue getSymbolPositionDetailValueForUpdate(SymbolPosition symbolPosition) {
        return symbolPosition == null? null: symbolPosition.getLastDetailForUpdate().getSymbolPositionDetailValue().clone();
    }
    
    public SymbolPositionDetailValue getSymbolPositionDetailValueByNameForUpdate(String symbolPositionName) {
        return getSymbolPositionDetailValueForUpdate(getSymbolPositionByNameForUpdate(symbolPositionName));
    }
    
    private SymbolPosition getDefaultSymbolPosition(EntityPermission entityPermission) {
        SymbolPosition symbolPosition;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_isdefault = 1 AND symposdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_isdefault = 1 AND symposdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SymbolPositionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            symbolPosition = SymbolPositionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return symbolPosition;
    }
    
    public SymbolPosition getDefaultSymbolPosition() {
        return getDefaultSymbolPosition(EntityPermission.READ_ONLY);
    }
    
    public SymbolPosition getDefaultSymbolPositionForUpdate() {
        return getDefaultSymbolPosition(EntityPermission.READ_WRITE);
    }
    
    public SymbolPositionDetailValue getDefaultSymbolPositionDetailValueForUpdate() {
        return getDefaultSymbolPositionForUpdate().getLastDetailForUpdate().getSymbolPositionDetailValue().clone();
    }
    
    private List<SymbolPosition> getSymbolPositions(EntityPermission entityPermission) {
        List<SymbolPosition> symbolPositions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_thrutime = ? " +
                        "ORDER BY symposdt_sortorder, symposdt_symbolpositionname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositions, symbolpositiondetails " +
                        "WHERE sympos_symbolpositionid = symposdt_sympos_symbolpositionid AND symposdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SymbolPositionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            symbolPositions = SymbolPositionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return symbolPositions;
    }
    
    public List<SymbolPosition> getSymbolPositions() {
        return getSymbolPositions(EntityPermission.READ_ONLY);
    }
    
    public List<SymbolPosition> getSymbolPositionsForUpdate() {
        return getSymbolPositions(EntityPermission.READ_WRITE);
    }
    
    public SymbolPositionTransfer getSymbolPositionTransfer(UserVisit userVisit, SymbolPosition symbolPosition) {
        return getAccountingTransferCaches(userVisit).getSymbolPositionTransferCache().getTransfer(symbolPosition);
    }
    
    public List<SymbolPositionTransfer> getSymbolPositionTransfers(UserVisit userVisit, Collection<SymbolPosition> symbolPositions) {
        List<SymbolPositionTransfer> symbolPositionTransfers = new ArrayList<>(symbolPositions.size());
        SymbolPositionTransferCache symbolPositionTransferCache = getAccountingTransferCaches(userVisit).getSymbolPositionTransferCache();
        
        symbolPositions.stream().forEach((symbolPosition) -> {
            symbolPositionTransfers.add(symbolPositionTransferCache.getTransfer(symbolPosition));
        });
        
        return symbolPositionTransfers;
    }
    
    public List<SymbolPositionTransfer> getSymbolPositionTransfers(UserVisit userVisit) {
        return getSymbolPositionTransfers(userVisit, getSymbolPositions());
    }
    
    public SymbolPositionChoicesBean getSymbolPositionChoices(String defaultSymbolPositionChoice, Language language,
            boolean allowNullChoice) {
        List<SymbolPosition> symbolPositions = getSymbolPositions();
        int size = symbolPositions.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSymbolPositionChoice == null) {
                defaultValue = "";
            }
        }
        
        for(SymbolPosition symbolPosition: symbolPositions) {
            SymbolPositionDetail symbolPositionDetail = symbolPosition.getLastDetail();
            
            String label = getBestSymbolPositionDescription(symbolPosition, language);
            String value = symbolPositionDetail.getSymbolPositionName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultSymbolPositionChoice != null && defaultSymbolPositionChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && symbolPositionDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SymbolPositionChoicesBean(labels, values, defaultValue);
    }
    
    private void updateSymbolPositionFromValue(SymbolPositionDetailValue symbolPositionDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(symbolPositionDetailValue.hasBeenModified()) {
            SymbolPosition symbolPosition = SymbolPositionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     symbolPositionDetailValue.getSymbolPositionPK());
            SymbolPositionDetail symbolPositionDetail = symbolPosition.getActiveDetailForUpdate();
            
            symbolPositionDetail.setThruTime(session.START_TIME_LONG);
            symbolPositionDetail.store();
            
            SymbolPositionPK symbolPositionPK = symbolPositionDetail.getSymbolPositionPK();
            String symbolPositionName = symbolPositionDetailValue.getSymbolPositionName();
            Boolean isDefault = symbolPositionDetailValue.getIsDefault();
            Integer sortOrder = symbolPositionDetailValue.getSortOrder();
            
            if(checkDefault) {
                SymbolPosition defaultSymbolPosition = getDefaultSymbolPosition();
                boolean defaultFound = defaultSymbolPosition != null && !defaultSymbolPosition.equals(symbolPosition);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SymbolPositionDetailValue defaultSymbolPositionDetailValue = getDefaultSymbolPositionDetailValueForUpdate();
                    
                    defaultSymbolPositionDetailValue.setIsDefault(Boolean.FALSE);
                    updateSymbolPositionFromValue(defaultSymbolPositionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            symbolPositionDetail = SymbolPositionDetailFactory.getInstance().create(symbolPositionPK,
                    symbolPositionName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            symbolPosition.setActiveDetail(symbolPositionDetail);
            symbolPosition.setLastDetail(symbolPositionDetail);
            
            sendEventUsingNames(symbolPositionPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateSymbolPositionFromValue(SymbolPositionDetailValue symbolPositionDetailValue, BasePK updatedBy) {
        updateSymbolPositionFromValue(symbolPositionDetailValue, true, updatedBy);
    }
    
    public void deleteSymbolPosition(SymbolPosition symbolPosition, BasePK deletedBy) {
        deleteSymbolPositionDescriptionsBySymbolPosition(symbolPosition, deletedBy);
        
        SymbolPositionDetail symbolPositionDetail = symbolPosition.getLastDetailForUpdate();
        symbolPositionDetail.setThruTime(session.START_TIME_LONG);
        symbolPositionDetail.store();
        symbolPosition.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        SymbolPosition defaultSymbolPosition = getDefaultSymbolPosition();
        if(defaultSymbolPosition == null) {
            List<SymbolPosition> symbolPositions = getSymbolPositionsForUpdate();
            
            if(!symbolPositions.isEmpty()) {
                Iterator<SymbolPosition> iter = symbolPositions.iterator();
                if(iter.hasNext()) {
                    defaultSymbolPosition = iter.next();
                }
                SymbolPositionDetailValue symbolPositionDetailValue = defaultSymbolPosition.getLastDetailForUpdate().getSymbolPositionDetailValue().clone();
                
                symbolPositionDetailValue.setIsDefault(Boolean.TRUE);
                updateSymbolPositionFromValue(symbolPositionDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(symbolPosition.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Symbol Position Descriptions
    // --------------------------------------------------------------------------------
    
    public SymbolPositionDescription createSymbolPositionDescription(SymbolPosition symbolPosition, Language language, String description, BasePK createdBy) {
        SymbolPositionDescription symbolPositionDescription = SymbolPositionDescriptionFactory.getInstance().create(symbolPosition, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(symbolPosition.getPrimaryKey(), EventTypes.MODIFY.name(), symbolPositionDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return symbolPositionDescription;
    }
    
    private SymbolPositionDescription getSymbolPositionDescription(SymbolPosition symbolPosition, Language language, EntityPermission entityPermission) {
        SymbolPositionDescription symbolPositionDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositiondescriptions " +
                        "WHERE symposd_sympos_symbolpositionid = ? AND symposd_lang_languageid = ? AND symposd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositiondescriptions " +
                        "WHERE symposd_sympos_symbolpositionid = ? AND symposd_lang_languageid = ? AND symposd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SymbolPositionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, symbolPosition.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            symbolPositionDescription = SymbolPositionDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return symbolPositionDescription;
    }
    
    public SymbolPositionDescription getSymbolPositionDescription(SymbolPosition symbolPosition, Language language) {
        return getSymbolPositionDescription(symbolPosition, language, EntityPermission.READ_ONLY);
    }
    
    public SymbolPositionDescription getSymbolPositionDescriptionForUpdate(SymbolPosition symbolPosition, Language language) {
        return getSymbolPositionDescription(symbolPosition, language, EntityPermission.READ_WRITE);
    }
    
    public SymbolPositionDescriptionValue getSymbolPositionDescriptionValue(SymbolPositionDescription symbolPositionDescription) {
        return symbolPositionDescription == null? null: symbolPositionDescription.getSymbolPositionDescriptionValue().clone();
    }
    
    public SymbolPositionDescriptionValue getSymbolPositionDescriptionValueForUpdate(SymbolPosition symbolPosition, Language language) {
        return getSymbolPositionDescriptionValue(getSymbolPositionDescriptionForUpdate(symbolPosition, language));
    }
    
    private List<SymbolPositionDescription> getSymbolPositionDescriptionsBySymbolPosition(SymbolPosition symbolPosition, EntityPermission entityPermission) {
        List<SymbolPositionDescription> symbolPositionDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositiondescriptions, languages " +
                        "WHERE symposd_sympos_symbolpositionid = ? AND symposd_thrutime = ? AND symposd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM symbolpositiondescriptions " +
                        "WHERE symposd_sympos_symbolpositionid = ? AND symposd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SymbolPositionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, symbolPosition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            symbolPositionDescriptions = SymbolPositionDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return symbolPositionDescriptions;
    }
    
    public List<SymbolPositionDescription> getSymbolPositionDescriptionsBySymbolPosition(SymbolPosition symbolPosition) {
        return getSymbolPositionDescriptionsBySymbolPosition(symbolPosition, EntityPermission.READ_ONLY);
    }
    
    public List<SymbolPositionDescription> getSymbolPositionDescriptionsBySymbolPositionForUpdate(SymbolPosition symbolPosition) {
        return getSymbolPositionDescriptionsBySymbolPosition(symbolPosition, EntityPermission.READ_WRITE);
    }
    
    public String getBestSymbolPositionDescription(SymbolPosition symbolPosition, Language language) {
        String description;
        SymbolPositionDescription symbolPositionDescription = getSymbolPositionDescription(symbolPosition, language);
        
        if(symbolPositionDescription == null && !language.getIsDefault()) {
            symbolPositionDescription = getSymbolPositionDescription(symbolPosition, getPartyControl().getDefaultLanguage());
        }
        
        if(symbolPositionDescription == null) {
            description = symbolPosition.getLastDetail().getSymbolPositionName();
        } else {
            description = symbolPositionDescription.getDescription();
        }
        
        return description;
    }
    
    public SymbolPositionDescriptionTransfer getSymbolPositionDescriptionTransfer(UserVisit userVisit, SymbolPositionDescription symbolPositionDescription) {
        return getAccountingTransferCaches(userVisit).getSymbolPositionDescriptionTransferCache().getTransfer(symbolPositionDescription);
    }
    
    public List<SymbolPositionDescriptionTransfer> getSymbolPositionDescriptionTransfersBySymbolPosition(UserVisit userVisit, SymbolPosition symbolPosition) {
        List<SymbolPositionDescription> symbolPositionDescriptions = getSymbolPositionDescriptionsBySymbolPosition(symbolPosition);
        List<SymbolPositionDescriptionTransfer> symbolPositionDescriptionTransfers = new ArrayList<>(symbolPositionDescriptions.size());
        SymbolPositionDescriptionTransferCache symbolPositionDescriptionTransferCache = getAccountingTransferCaches(userVisit).getSymbolPositionDescriptionTransferCache();
        
        symbolPositionDescriptions.stream().forEach((symbolPositionDescription) -> {
            symbolPositionDescriptionTransfers.add(symbolPositionDescriptionTransferCache.getTransfer(symbolPositionDescription));
        });
        
        return symbolPositionDescriptionTransfers;
    }
    
    public void updateSymbolPositionDescriptionFromValue(SymbolPositionDescriptionValue symbolPositionDescriptionValue, BasePK updatedBy) {
        if(symbolPositionDescriptionValue.hasBeenModified()) {
            SymbolPositionDescription symbolPositionDescription = SymbolPositionDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, symbolPositionDescriptionValue.getPrimaryKey());
            
            symbolPositionDescription.setThruTime(session.START_TIME_LONG);
            symbolPositionDescription.store();
            
            SymbolPosition symbolPosition = symbolPositionDescription.getSymbolPosition();
            Language language = symbolPositionDescription.getLanguage();
            String description = symbolPositionDescriptionValue.getDescription();
            
            symbolPositionDescription = SymbolPositionDescriptionFactory.getInstance().create(symbolPosition, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(symbolPosition.getPrimaryKey(), EventTypes.MODIFY.name(), symbolPositionDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSymbolPositionDescription(SymbolPositionDescription symbolPositionDescription, BasePK deletedBy) {
        symbolPositionDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(symbolPositionDescription.getSymbolPositionPK(), EventTypes.MODIFY.name(), symbolPositionDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteSymbolPositionDescriptionsBySymbolPosition(SymbolPosition symbolPosition, BasePK deletedBy) {
        List<SymbolPositionDescription> symbolPositionDescriptions = getSymbolPositionDescriptionsBySymbolPositionForUpdate(symbolPosition);
        
        symbolPositionDescriptions.stream().forEach((symbolPositionDescription) -> {
            deleteSymbolPositionDescription(symbolPositionDescription, deletedBy);
        });
    }
    
}
