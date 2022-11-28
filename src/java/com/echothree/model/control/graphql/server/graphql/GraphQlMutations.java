// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.CreateItemAccountingCategoryResult;
import com.echothree.control.user.accounting.common.result.EditItemAccountingCategoryResult;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.campaign.common.CampaignUtil;
import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.CreateContentPageLayoutResult;
import com.echothree.control.user.content.common.result.EditContentPageLayoutResult;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.CreateEntityAttributeGroupResult;
import com.echothree.control.user.core.common.result.CreateEntityAttributeResult;
import com.echothree.control.user.core.common.result.CreateEntityListItemResult;
import com.echothree.control.user.core.common.result.EditEntityAttributeGroupResult;
import com.echothree.control.user.core.common.result.EditEntityAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityBooleanAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityClobAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityDateAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityEntityAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityGeoPointAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityIntegerAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityListItemAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityListItemResult;
import com.echothree.control.user.core.common.result.EditEntityLongAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityNameAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityStringAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityTimeAttributeResult;
import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.result.CreateFilterAdjustmentResult;
import com.echothree.control.user.filter.common.result.CreateFilterResult;
import com.echothree.control.user.filter.common.result.CreateFilterStepResult;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentAmountResult;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentFixedAmountResult;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentPercentResult;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentResult;
import com.echothree.control.user.filter.common.result.EditFilterResult;
import com.echothree.control.user.filter.common.result.EditFilterStepResult;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.CreateInventoryConditionResult;
import com.echothree.control.user.inventory.common.result.EditInventoryConditionResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.CreateItemAliasTypeResult;
import com.echothree.control.user.item.common.result.CreateItemCategoryResult;
import com.echothree.control.user.item.common.result.CreateItemDescriptionResult;
import com.echothree.control.user.item.common.result.CreateItemDescriptionTypeResult;
import com.echothree.control.user.item.common.result.CreateItemDescriptionTypeUseTypeResult;
import com.echothree.control.user.item.common.result.CreateItemImageTypeResult;
import com.echothree.control.user.item.common.result.CreateItemResult;
import com.echothree.control.user.item.common.result.EditItemAliasResult;
import com.echothree.control.user.item.common.result.EditItemAliasTypeResult;
import com.echothree.control.user.item.common.result.EditItemCategoryResult;
import com.echothree.control.user.item.common.result.EditItemDescriptionResult;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeResult;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeUseTypeResult;
import com.echothree.control.user.item.common.result.EditItemImageTypeResult;
import com.echothree.control.user.item.common.result.EditItemPriceResult;
import com.echothree.control.user.item.common.result.EditItemResult;
import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.CreateOfferItemResult;
import com.echothree.control.user.offer.common.result.CreateOfferNameElementResult;
import com.echothree.control.user.offer.common.result.CreateOfferResult;
import com.echothree.control.user.offer.common.result.CreateOfferUseResult;
import com.echothree.control.user.offer.common.result.CreateUseNameElementResult;
import com.echothree.control.user.offer.common.result.CreateUseResult;
import com.echothree.control.user.offer.common.result.CreateUseTypeResult;
import com.echothree.control.user.offer.common.result.EditOfferItemPriceResult;
import com.echothree.control.user.offer.common.result.EditOfferNameElementResult;
import com.echothree.control.user.offer.common.result.EditOfferResult;
import com.echothree.control.user.offer.common.result.EditOfferUseResult;
import com.echothree.control.user.offer.common.result.EditUseNameElementResult;
import com.echothree.control.user.offer.common.result.EditUseResult;
import com.echothree.control.user.offer.common.result.EditUseTypeResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.CreateCustomerResult;
import com.echothree.control.user.party.common.result.CreateCustomerWithLoginResult;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.CreatePaymentMethodTypeResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorActionTypeResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorResultCodeResult;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorTypeResult;
import com.echothree.control.user.payment.common.result.EditPaymentMethodTypeResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorActionTypeResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorResultCodeResult;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorTypeResult;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.SearchCustomersResult;
import com.echothree.control.user.search.common.result.SearchEmployeesResult;
import com.echothree.control.user.search.common.result.SearchItemsResult;
import com.echothree.control.user.search.common.result.SearchVendorsResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.CreateSelectorResult;
import com.echothree.control.user.selector.common.result.EditSelectorResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.result.CreateSequenceResult;
import com.echothree.control.user.sequence.common.result.CreateSequenceTypeResult;
import com.echothree.control.user.sequence.common.result.EditSequenceResult;
import com.echothree.control.user.sequence.common.result.EditSequenceTypeResult;
import com.echothree.control.user.shipment.common.ShipmentUtil;
import com.echothree.control.user.shipment.common.result.CreateFreeOnBoardResult;
import com.echothree.control.user.shipment.common.result.EditFreeOnBoardResult;
import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.common.result.CreateTagResult;
import com.echothree.control.user.tag.common.result.CreateTagScopeResult;
import com.echothree.control.user.tag.common.result.EditTagResult;
import com.echothree.control.user.tag.common.result.EditTagScopeResult;
import com.echothree.control.user.track.common.TrackUtil;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.result.EditUserLoginResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.CreateItemPurchasingCategoryResult;
import com.echothree.control.user.vendor.common.result.EditItemPurchasingCategoryResult;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.search.server.graphql.SearchCustomersResultObject;
import com.echothree.model.control.search.server.graphql.SearchEmployeesResultObject;
import com.echothree.model.control.search.server.graphql.SearchItemsResultObject;
import com.echothree.model.control.search.server.graphql.SearchVendorsResultObject;
import com.echothree.util.common.command.EditMode;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLID;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.annotationTypes.GraphQLRelayMutation;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;
import javax.naming.NamingException;

@GraphQLName("mutation")
public class GraphQlMutations
        extends BaseGraphQl {

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createUserVisitTrack(final DataFetchingEnvironment env,
            @GraphQLName("trackValue") final String trackValue) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = TrackUtil.getHome().getCreateUserVisitTrackForm();

            commandForm.setTrackValue(trackValue);

            var commandResult = TrackUtil.getHome().createUserVisitTrack(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createUserVisitCampaign(final DataFetchingEnvironment env,
            @GraphQLName("campaignValue") final String campaignValue,
            @GraphQLName("campaignSourceValue") final String campaignSourceValue,
            @GraphQLName("campaignMediumValue") final String campaignMediumValue,
            @GraphQLName("campaignTermValue") final String campaignTermValue,
            @GraphQLName("campaignContentValue") final String campaignContentValue) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CampaignUtil.getHome().getCreateUserVisitCampaignForm();

            commandForm.setCampaignValue(campaignValue);
            commandForm.setCampaignSourceValue(campaignSourceValue);
            commandForm.setCampaignMediumValue(campaignMediumValue);
            commandForm.setCampaignTermValue(campaignTermValue);
            commandForm.setCampaignContentValue(campaignContentValue);

            var commandResult = CampaignUtil.getHome().createUserVisitCampaign(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createSelector(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName,
            @GraphQLName("selectorTypeName") @GraphQLNonNull final String selectorTypeName,
            @GraphQLName("selectorName") @GraphQLNonNull final String selectorName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = SelectorUtil.getHome().getCreateSelectorForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSelectorName(selectorName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = SelectorUtil.getHome().createSelector(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateSelectorResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteSelector(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName,
            @GraphQLName("selectorTypeName") @GraphQLNonNull final String selectorTypeName,
            @GraphQLName("selectorName") @GraphQLNonNull final String selectorName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SelectorUtil.getHome().getDeleteSelectorForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorName(selectorName);

            var commandResult = SelectorUtil.getHome().deleteSelector(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editSelector(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName,
            @GraphQLName("selectorTypeName") @GraphQLNonNull final String selectorTypeName,
            @GraphQLName("originalSelectorName") final String originalSelectorName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("selectorName") final String selectorName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = SelectorUtil.getHome().getSelectorUniversalSpec();

            spec.setSelectorKindName(selectorKindName);
            spec.setSelectorTypeName(selectorTypeName);
            spec.setSelectorName(originalSelectorName);
            spec.setUlid(id);

            var commandForm = SelectorUtil.getHome().getEditSelectorForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SelectorUtil.getHome().editSelector(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSelectorResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getSelector().getEntityInstance());

                if(arguments.containsKey("selectorName"))
                    edit.setSelectorName(selectorName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = SelectorUtil.getHome().editSelector(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultSelector")
    public static CommandResultObject setDefaultSelector(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") @GraphQLNonNull final String selectorKindName,
            @GraphQLName("selectorTypeName") @GraphQLNonNull final String selectorTypeName,
            @GraphQLName("selectorName") @GraphQLNonNull final String selectorName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SelectorUtil.getHome().getSetDefaultSelectorForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSelectorName(selectorName);

            var commandResult = SelectorUtil.getHome().setDefaultSelector(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createFilter(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName,
            @GraphQLName("initialFilterAdjustmentName") @GraphQLNonNull final String initialFilterAdjustmentName,
            @GraphQLName("filterItemSelectorName") final String filterItemSelectorName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setFilterName(filterName);
            commandForm.setInitialFilterAdjustmentName(initialFilterAdjustmentName);
            commandForm.setFilterItemSelectorName(filterItemSelectorName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = FilterUtil.getHome().createFilter(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateFilterResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteFilter(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterName(filterName);

            var commandResult = FilterUtil.getHome().deleteFilter(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editFilter(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("originalFilterName") final String originalFilterName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("filterName") final String filterName,
            @GraphQLName("initialFilterAdjustmentName") @GraphQLNonNull final String initialFilterAdjustmentName,
            @GraphQLName("filterItemSelectorName") final String filterItemSelectorName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = FilterUtil.getHome().getFilterUniversalSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterTypeName(filterTypeName);
            spec.setFilterName(originalFilterName);
            spec.setUlid(id);

            var commandForm = FilterUtil.getHome().getEditFilterForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilter(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getFilter().getEntityInstance());

                if(arguments.containsKey("filterName"))
                    edit.setFilterName(filterName);
                if(arguments.containsKey("initialFilterAdjustmentName"))
                    edit.setInitialFilterAdjustmentName(initialFilterAdjustmentName);
                if(arguments.containsKey("filterItemSelectorName"))
                    edit.setFilterItemSelectorName(filterItemSelectorName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilter(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultFilter")
    public static CommandResultObject setDefaultFilter(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getSetDefaultFilterForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setFilterName(filterName);

            var commandResult = FilterUtil.getHome().setDefaultFilter(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createFilterStep(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterStepName") @GraphQLNonNull final String filterStepName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName,
            @GraphQLName("filterItemSelectorName") final String filterItemSelectorName,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterStepForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setFilterName(filterName);
            commandForm.setFilterStepName(filterStepName);
            commandForm.setFilterItemSelectorName(filterItemSelectorName);
            commandForm.setDescription(description);

            var commandResult = FilterUtil.getHome().createFilterStep(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateFilterStepResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteFilterStep(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName,
            @GraphQLName("filterStepName") @GraphQLNonNull final String filterStepName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterStepForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterStepName(filterStepName);
            commandForm.setFilterName(filterName);

            var commandResult = FilterUtil.getHome().deleteFilterStep(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editFilterStep(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterTypeName") @GraphQLNonNull final String filterTypeName,
            @GraphQLName("filterName") @GraphQLNonNull final String filterName,
            @GraphQLName("originalFilterStepName") final String originalFilterStepName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("filterStepName") final String filterStepName,
            @GraphQLName("filterItemSelectorName") final String filterItemSelectorName,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = FilterUtil.getHome().getFilterStepUniversalSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterTypeName(filterTypeName);
            spec.setFilterName(filterName);
            spec.setFilterStepName(originalFilterStepName);
            spec.setUlid(id);

            var commandForm = FilterUtil.getHome().getEditFilterStepForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterStep(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterStepResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getFilterStep().getEntityInstance());

                if(arguments.containsKey("filterStepName"))
                    edit.setFilterStepName(filterStepName);
                if(arguments.containsKey("filterItemSelectorName"))
                    edit.setFilterItemSelectorName(filterItemSelectorName);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterStep(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createFilterAdjustment(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("filterAdjustmentSourceName") @GraphQLNonNull final String filterAdjustmentSourceName,
            @GraphQLName("filterAdjustmentTypeName") final String filterAdjustmentTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterAdjustmentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setFilterAdjustmentSourceName(filterAdjustmentSourceName);
            commandForm.setFilterAdjustmentTypeName(filterAdjustmentTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = FilterUtil.getHome().createFilterAdjustment(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateFilterAdjustmentResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteFilterAdjustment(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterAdjustmentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);

            var commandResult = FilterUtil.getHome().deleteFilterAdjustment(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editFilterAdjustment(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("originalFilterAdjustmentName") final String originalFilterAdjustmentName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("filterAdjustmentName") final String filterAdjustmentName,
            @GraphQLName("filterAdjustmentSourceName") final String filterAdjustmentSourceName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = FilterUtil.getHome().getFilterAdjustmentUniversalSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterAdjustmentName(originalFilterAdjustmentName);
            spec.setUlid(id);

            var commandForm = FilterUtil.getHome().getEditFilterAdjustmentForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterAdjustment(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getFilterAdjustment().getEntityInstance());

                if(arguments.containsKey("filterAdjustmentName"))
                    edit.setFilterAdjustmentName(filterAdjustmentName);
                if(arguments.containsKey("filterAdjustmentSourceName"))
                    edit.setFilterAdjustmentSourceName(filterAdjustmentSourceName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustment(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultFilterAdjustment")
    public static CommandResultObject setDefaultFilterAdjustment(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getSetDefaultFilterAdjustmentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);

            var commandResult = FilterUtil.getHome().setDefaultFilterAdjustment(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createFilterAdjustmentAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("amount") @GraphQLNonNull final String amount) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterAdjustmentAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setAmount(amount);

            var commandResult = FilterUtil.getHome().createFilterAdjustmentAmount(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteFilterAdjustmentAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterAdjustmentAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = FilterUtil.getHome().deleteFilterAdjustmentAmount(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editFilterAdjustmentAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("amount") @GraphQLNonNull final String amount) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = FilterUtil.getHome().getFilterAdjustmentAmountSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterAdjustmentName(filterAdjustmentName);
            spec.setUnitOfMeasureName(unitOfMeasureName);
            spec.setUnitOfMeasureKindName(unitOfMeasureKindName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = FilterUtil.getHome().getEditFilterAdjustmentAmountForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterAdjustmentAmount(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentAmountResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("amount"))
                    edit.setAmount(amount);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustmentAmount(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createFilterAdjustmentFixedAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitAmount") @GraphQLNonNull final String unitAmount) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterAdjustmentFixedAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setUnitAmount(unitAmount);

            var commandResult = FilterUtil.getHome().createFilterAdjustmentFixedAmount(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteFilterAdjustmentFixedAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterAdjustmentFixedAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = FilterUtil.getHome().deleteFilterAdjustmentFixedAmount(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editFilterAdjustmentFixedAmount(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitAmount") @GraphQLNonNull final String unitAmount) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = FilterUtil.getHome().getFilterAdjustmentFixedAmountSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterAdjustmentName(filterAdjustmentName);
            spec.setUnitOfMeasureName(unitOfMeasureName);
            spec.setUnitOfMeasureKindName(unitOfMeasureKindName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = FilterUtil.getHome().getEditFilterAdjustmentFixedAmountForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterAdjustmentFixedAmount(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentFixedAmountResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitAmount"))
                    edit.setUnitAmount(unitAmount);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustmentFixedAmount(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createFilterAdjustmentPercent(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("percent") @GraphQLNonNull final String percent) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getCreateFilterAdjustmentPercentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setPercent(percent);

            var commandResult = FilterUtil.getHome().createFilterAdjustmentPercent(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteFilterAdjustmentPercent(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = FilterUtil.getHome().getDeleteFilterAdjustmentPercentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.setUnitOfMeasureName(unitOfMeasureName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = FilterUtil.getHome().deleteFilterAdjustmentPercent(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editFilterAdjustmentPercent(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") @GraphQLNonNull final String filterKindName,
            @GraphQLName("filterAdjustmentName") @GraphQLNonNull final String filterAdjustmentName,
            @GraphQLName("unitOfMeasureName") final String unitOfMeasureName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("percent") @GraphQLNonNull final String percent) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = FilterUtil.getHome().getFilterAdjustmentPercentSpec();

            spec.setFilterKindName(filterKindName);
            spec.setFilterAdjustmentName(filterAdjustmentName);
            spec.setUnitOfMeasureName(unitOfMeasureName);
            spec.setUnitOfMeasureKindName(unitOfMeasureKindName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = FilterUtil.getHome().getEditFilterAdjustmentPercentForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = FilterUtil.getHome().editFilterAdjustmentPercent(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFilterAdjustmentPercentResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("percent"))
                    edit.setPercent(percent);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = FilterUtil.getHome().editFilterAdjustmentPercent(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createSequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("sequenceName") @GraphQLNonNull final String sequenceName,
            @GraphQLName("chunkSize") final String chunkSize,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("value") @GraphQLNonNull final String value,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = SequenceUtil.getHome().getCreateSequenceForm();

            commandForm.setSequenceName(sequenceName);
            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setChunkSize(chunkSize);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setValue(value);
            commandForm.setDescription(description);

            var commandResult = SequenceUtil.getHome().createSequence(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateSequenceResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteSequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("sequenceName") @GraphQLNonNull final String sequenceName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getDeleteSequenceForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);

            var commandResult = SequenceUtil.getHome().deleteSequence(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editSequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("originalSequenceName") final String originalSequenceName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("sequenceName") final String sequenceName,
            @GraphQLName("chunkSize") final String chunkSize,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = SequenceUtil.getHome().getSequenceUniversalSpec();

            spec.setSequenceTypeName(sequenceTypeName);
            spec.setSequenceName(originalSequenceName);
            spec.setUlid(id);

            var commandForm = SequenceUtil.getHome().getEditSequenceForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SequenceUtil.getHome().editSequence(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSequenceResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getSequence().getEntityInstance());

                if(arguments.containsKey("sequenceName"))
                    edit.setSequenceName(sequenceName);
                if(arguments.containsKey("chunkSize"))
                    edit.setChunkSize(chunkSize);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = SequenceUtil.getHome().editSequence(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultSequence")
    public static CommandResultObject setDefaultSequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("sequenceName") @GraphQLNonNull final String sequenceName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getSetDefaultSequenceForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);

            var commandResult = SequenceUtil.getHome().setDefaultSequence(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setSequenceValue")
    public static CommandResultObject setSequenceValue(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("sequenceName") @GraphQLNonNull final String sequenceName,
            @GraphQLName("value") @GraphQLNonNull final String value) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getSetSequenceValueForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);
            commandForm.setValue(value);

            var commandResult = SequenceUtil.getHome().setSequenceValue(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createSequenceType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName,
            @GraphQLName("prefix") final String prefix,
            @GraphQLName("suffix") final String suffix,
            @GraphQLName("sequenceEncoderTypeName") @GraphQLNonNull final String sequenceEncoderTypeName,
            @GraphQLName("sequenceChecksumTypeName") @GraphQLNonNull final String sequenceChecksumTypeName,
            @GraphQLName("chunkSize") final String chunkSize,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = SequenceUtil.getHome().getCreateSequenceTypeForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setPrefix(prefix);
            commandForm.setSuffix(suffix);
            commandForm.setSequenceEncoderTypeName(sequenceEncoderTypeName);
            commandForm.setSequenceChecksumTypeName(sequenceChecksumTypeName);
            commandForm.setChunkSize(chunkSize);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = SequenceUtil.getHome().createSequenceType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateSequenceTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteSequenceType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getDeleteSequenceTypeForm();

            commandForm.setSequenceTypeName(sequenceTypeName);

            var commandResult = SequenceUtil.getHome().deleteSequenceType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editSequenceType(final DataFetchingEnvironment env,
            @GraphQLName("originalSequenceTypeName") final String originalSequenceTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName,
            @GraphQLName("prefix") final String prefix,
            @GraphQLName("suffix") final String suffix,
            @GraphQLName("sequenceEncoderTypeName") final String sequenceEncoderTypeName,
            @GraphQLName("sequenceChecksumTypeName") final String sequenceChecksumTypeName,
            @GraphQLName("chunkSize") final String chunkSize,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = SequenceUtil.getHome().getSequenceTypeUniversalSpec();

            spec.setSequenceTypeName(originalSequenceTypeName);
            spec.setUlid(id);

            var commandForm = SequenceUtil.getHome().getEditSequenceTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = SequenceUtil.getHome().editSequenceType(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSequenceTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getSequenceType().getEntityInstance());

                if(arguments.containsKey("sequenceTypeName"))
                    edit.setSequenceTypeName(sequenceTypeName);
                if(arguments.containsKey("prefix"))
                    edit.setPrefix(prefix);
                if(arguments.containsKey("suffix"))
                    edit.setSuffix(suffix);
                if(arguments.containsKey("sequenceEncoderTypeName"))
                    edit.setSequenceEncoderTypeName(sequenceEncoderTypeName);
                if(arguments.containsKey("sequenceChecksumTypeName"))
                    edit.setSequenceChecksumTypeName(sequenceChecksumTypeName);
                if(arguments.containsKey("chunkSize"))
                    edit.setChunkSize(chunkSize);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = SequenceUtil.getHome().editSequenceType(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultSequenceType")
    public static CommandResultObject setDefaultSequenceType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") @GraphQLNonNull final String sequenceTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SequenceUtil.getHome().getSetDefaultSequenceTypeForm();

            commandForm.setSequenceTypeName(sequenceTypeName);

            var commandResult = SequenceUtil.getHome().setDefaultSequenceType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createOfferUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName,
            @GraphQLName("salesOrderSequenceName") final String salesOrderSequenceName) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferUseForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);
            commandForm.setSalesOrderSequenceName(salesOrderSequenceName);

            var commandResult = OfferUtil.getHome().createOfferUse(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOfferUseResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteOfferUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferUseForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);

            var commandResult = OfferUtil.getHome().deleteOfferUse(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editOfferUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName,
            @GraphQLName("salesOrderSequenceName") final String salesOrderSequenceName) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getOfferUseSpec();

            spec.setOfferName(offerName);
            spec.setUseName(useName);

            var commandForm = OfferUtil.getHome().getEditOfferUseForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editOfferUse(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOfferUseResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getOfferUse().getEntityInstance());

                if(arguments.containsKey("salesOrderSequenceName"))
                    edit.setSalesOrderSequenceName(salesOrderSequenceName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editOfferUse(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createOffer(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("salesOrderSequenceName") final String salesOrderSequenceName,
            @GraphQLName("companyName") final String companyName,
            @GraphQLName("divisionName") final String divisionName,
            @GraphQLName("departmentName") final String departmentName,
            @GraphQLName("offerItemSelectorName") final String offerItemSelectorName,
            @GraphQLName("offerItemPriceFilterName") final String offerItemPriceFilterName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferForm();

            commandForm.setOfferName(offerName);
            commandForm.setSalesOrderSequenceName(salesOrderSequenceName);
            commandForm.setCompanyName(companyName);
            commandForm.setDivisionName(divisionName);
            commandForm.setDepartmentName(departmentName);
            commandForm.setOfferItemSelectorName(offerItemSelectorName);
            commandForm.setOfferItemPriceFilterName(offerItemPriceFilterName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = OfferUtil.getHome().createOffer(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOfferResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteOffer(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferForm();

            commandForm.setOfferName(offerName);

            var commandResult = OfferUtil.getHome().deleteOffer(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editOffer(final DataFetchingEnvironment env,
            @GraphQLName("originalOfferName") final String originalOfferName,
            //@GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("offerName") final String offerName,
            @GraphQLName("salesOrderSequenceName") final String salesOrderSequenceName,
            @GraphQLName("offerItemSelectorName") final String offerItemSelectorName,
            @GraphQLName("offerItemPriceFilterName") final String offerItemPriceFilterName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getOfferUniversalSpec();

            spec.setOfferName(originalOfferName);
            //spec.setUlid(id);

            var commandForm = OfferUtil.getHome().getEditOfferForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editOffer(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOfferResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getOffer().getEntityInstance());

                if(arguments.containsKey("offerName"))
                    edit.setOfferName(offerName);
                if(arguments.containsKey("salesOrderSequenceName"))
                    edit.setSalesOrderSequenceName(salesOrderSequenceName);
                if(arguments.containsKey("offerItemSelectorName"))
                    edit.setOfferItemSelectorName(offerItemSelectorName);
                if(arguments.containsKey("offerItemPriceFilterName"))
                    edit.setOfferItemPriceFilterName(offerItemPriceFilterName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editOffer(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultOffer")
    public static CommandResultObject setDefaultOffer(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getSetDefaultOfferForm();

            commandForm.setOfferName(offerName);

            var commandResult = OfferUtil.getHome().setDefaultOffer(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createOfferItem(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferItemForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);

            var commandResult = OfferUtil.getHome().createOfferItem(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOfferItemResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteOfferItem(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferItemForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);

            var commandResult = OfferUtil.getHome().deleteOfferItem(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createOfferItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitPrice") final String unitPrice,
            @GraphQLName("minimumUnitPrice") final String minimumUnitPrice,
            @GraphQLName("maximumUnitPrice") final String maximumUnitPrice,
            @GraphQLName("unitPriceIncrement") final String unitPriceIncrement) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferItemPriceForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setUnitPrice(unitPrice);
            commandForm.setMinimumUnitPrice(minimumUnitPrice);
            commandForm.setMaximumUnitPrice(maximumUnitPrice);
            commandForm.setUnitPriceIncrement(unitPriceIncrement);

            var commandResult = OfferUtil.getHome().createOfferItemPrice(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteOfferItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferItemPriceForm();

            commandForm.setOfferName(offerName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = OfferUtil.getHome().deleteOfferItemPrice(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editOfferItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitPrice") final String unitPrice,
            @GraphQLName("minimumUnitPrice") final String minimumUnitPrice,
            @GraphQLName("maximumUnitPrice") final String maximumUnitPrice,
            @GraphQLName("unitPriceIncrement") final String unitPriceIncrement) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = OfferUtil.getHome().getOfferItemPriceSpec();

            spec.setOfferName(offerName);
            spec.setItemName(itemName);
            spec.setInventoryConditionName(inventoryConditionName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = OfferUtil.getHome().getEditOfferItemPriceForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editOfferItemPrice(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOfferItemPriceResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitPrice"))
                    edit.setUnitPrice(unitPrice);
                if(arguments.containsKey("minimumUnitPrice"))
                    edit.setMinimumUnitPrice(minimumUnitPrice);
                if(arguments.containsKey("maximumUnitPrice"))
                    edit.setMaximumUnitPrice(maximumUnitPrice);
                if(arguments.containsKey("unitPriceIncrement"))
                    edit.setUnitPriceIncrement(unitPriceIncrement);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editOfferItemPrice(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createUse(final DataFetchingEnvironment env,
            @GraphQLName("useName") @GraphQLNonNull final String useName,
            @GraphQLName("useTypeName") @GraphQLNonNull final String useTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateUseForm();

            commandForm.setUseName(useName);
            commandForm.setUseTypeName(useTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = OfferUtil.getHome().createUse(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateUseResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteUse(final DataFetchingEnvironment env,
            @GraphQLName("useName") @GraphQLNonNull final String useName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteUseForm();

            commandForm.setUseName(useName);

            var commandResult = OfferUtil.getHome().deleteUse(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editUse(final DataFetchingEnvironment env,
            @GraphQLName("originalUseName") final String originalUseName,
            //@GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("useName") final String useName,
            @GraphQLName("useTypeName") final String useTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getUseUniversalSpec();

            spec.setUseName(originalUseName);
            //spec.setUlid(id);

            var commandForm = OfferUtil.getHome().getEditUseForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editUse(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditUseResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getUse().getEntityInstance());

                if(arguments.containsKey("useName"))
                    edit.setUseName(useName);
                if(arguments.containsKey("useTypeName"))
                    edit.setUseTypeName(useTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editUse(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultUse")
    public static CommandResultObject setDefaultUse(final DataFetchingEnvironment env,
            @GraphQLName("useName") @GraphQLNonNull final String useName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getSetDefaultUseForm();

            commandForm.setUseName(useName);

            var commandResult = OfferUtil.getHome().setDefaultUse(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createOfferNameElement(final DataFetchingEnvironment env,
            @GraphQLName("offerNameElementName") @GraphQLNonNull final String offerNameElementName,
            @GraphQLName("offset") @GraphQLNonNull final String offset,
            @GraphQLName("length") @GraphQLNonNull final String length,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateOfferNameElementForm();

            commandForm.setOfferNameElementName(offerNameElementName);
            commandForm.setOffset(offset);
            commandForm.setLength(length);
            commandForm.setValidationPattern(validationPattern);
            commandForm.setDescription(description);

            var commandResult = OfferUtil.getHome().createOfferNameElement(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateOfferNameElementResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteOfferNameElement(final DataFetchingEnvironment env,
            @GraphQLName("offerNameElementName") @GraphQLNonNull final String offerNameElementName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteOfferNameElementForm();

            commandForm.setOfferNameElementName(offerNameElementName);

            var commandResult = OfferUtil.getHome().deleteOfferNameElement(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editOfferNameElement(final DataFetchingEnvironment env,
            @GraphQLName("originalOfferNameElementName") final String originalOfferNameElementName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("offerNameElementName") final String offerNameElementName,
            @GraphQLName("offset") final String offset,
            @GraphQLName("length") final String length,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getOfferNameElementUniversalSpec();

            spec.setOfferNameElementName(originalOfferNameElementName);
            spec.setUlid(id);

            var commandForm = OfferUtil.getHome().getEditOfferNameElementForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editOfferNameElement(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditOfferNameElementResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getOfferNameElement().getEntityInstance());

                if(arguments.containsKey("offerNameElementName"))
                    edit.setOfferNameElementName(offerNameElementName);
                if(arguments.containsKey("offset"))
                    edit.setOffset(offset);
                if(arguments.containsKey("length"))
                    edit.setLength(length);
                if(arguments.containsKey("validationPattern"))
                    edit.setValidationPattern(validationPattern);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editOfferNameElement(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createUseNameElement(final DataFetchingEnvironment env,
            @GraphQLName("useNameElementName") @GraphQLNonNull final String useNameElementName,
            @GraphQLName("offset") @GraphQLNonNull final String offset,
            @GraphQLName("length") @GraphQLNonNull final String length,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateUseNameElementForm();

            commandForm.setUseNameElementName(useNameElementName);
            commandForm.setOffset(offset);
            commandForm.setLength(length);
            commandForm.setValidationPattern(validationPattern);
            commandForm.setDescription(description);

            var commandResult = OfferUtil.getHome().createUseNameElement(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateUseNameElementResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteUseNameElement(final DataFetchingEnvironment env,
            @GraphQLName("useNameElementName") @GraphQLNonNull final String useNameElementName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteUseNameElementForm();

            commandForm.setUseNameElementName(useNameElementName);

            var commandResult = OfferUtil.getHome().deleteUseNameElement(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editUseNameElement(final DataFetchingEnvironment env,
            @GraphQLName("originalUseNameElementName") final String originalUseNameElementName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("useNameElementName") final String useNameElementName,
            @GraphQLName("offset") final String offset,
            @GraphQLName("length") final String length,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getUseNameElementUniversalSpec();

            spec.setUseNameElementName(originalUseNameElementName);
            spec.setUlid(id);

            var commandForm = OfferUtil.getHome().getEditUseNameElementForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editUseNameElement(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditUseNameElementResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getUseNameElement().getEntityInstance());

                if(arguments.containsKey("useNameElementName"))
                    edit.setUseNameElementName(useNameElementName);
                if(arguments.containsKey("offset"))
                    edit.setOffset(offset);
                if(arguments.containsKey("length"))
                    edit.setLength(length);
                if(arguments.containsKey("validationPattern"))
                    edit.setValidationPattern(validationPattern);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editUseNameElement(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createUseType(final DataFetchingEnvironment env,
            @GraphQLName("useTypeName") @GraphQLNonNull final String useTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = OfferUtil.getHome().getCreateUseTypeForm();

            commandForm.setUseTypeName(useTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = OfferUtil.getHome().createUseType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateUseTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteUseType(final DataFetchingEnvironment env,
            @GraphQLName("useTypeName") @GraphQLNonNull final String useTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getDeleteUseTypeForm();

            commandForm.setUseTypeName(useTypeName);

            var commandResult = OfferUtil.getHome().deleteUseType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editUseType(final DataFetchingEnvironment env,
            @GraphQLName("originalUseTypeName") final String originalUseTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("useTypeName") final String useTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = OfferUtil.getHome().getUseTypeUniversalSpec();

            spec.setUseTypeName(originalUseTypeName);
            spec.setUlid(id);

            var commandForm = OfferUtil.getHome().getEditUseTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = OfferUtil.getHome().editUseType(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditUseTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getUseType().getEntityInstance());

                if(arguments.containsKey("useTypeName"))
                    edit.setUseTypeName(useTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = OfferUtil.getHome().editUseType(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultUseType")
    public static CommandResultObject setDefaultUseType(final DataFetchingEnvironment env,
            @GraphQLName("useTypeName") @GraphQLNonNull final String useTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = OfferUtil.getHome().getSetDefaultUseTypeForm();

            commandForm.setUseTypeName(useTypeName);

            var commandResult = OfferUtil.getHome().setDefaultUseType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createFreeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("freeOnBoardName") @GraphQLNonNull final String freeOnBoardName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = ShipmentUtil.getHome().getCreateFreeOnBoardForm();

            commandForm.setFreeOnBoardName(freeOnBoardName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ShipmentUtil.getHome().createFreeOnBoard(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateFreeOnBoardResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteFreeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("freeOnBoardName") @GraphQLNonNull final String freeOnBoardName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ShipmentUtil.getHome().getDeleteFreeOnBoardForm();

            commandForm.setFreeOnBoardName(freeOnBoardName);

            var commandResult = ShipmentUtil.getHome().deleteFreeOnBoard(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editFreeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("originalFreeOnBoardName") final String originalFreeOnBoardName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("freeOnBoardName") final String freeOnBoardName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = ShipmentUtil.getHome().getFreeOnBoardUniversalSpec();

            spec.setFreeOnBoardName(originalFreeOnBoardName);
            spec.setUlid(id);

            var commandForm = ShipmentUtil.getHome().getEditFreeOnBoardForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ShipmentUtil.getHome().editFreeOnBoard(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditFreeOnBoardResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getFreeOnBoard().getEntityInstance());

                if(arguments.containsKey("freeOnBoardName"))
                    edit.setFreeOnBoardName(freeOnBoardName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ShipmentUtil.getHome().editFreeOnBoard(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultFreeOnBoard")
    public static CommandResultObject setDefaultFreeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("freeOnBoardName") @GraphQLNonNull final String freeOnBoardName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ShipmentUtil.getHome().getSetDefaultFreeOnBoardForm();

            commandForm.setFreeOnBoardName(freeOnBoardName);

            var commandResult = ShipmentUtil.getHome().setDefaultFreeOnBoard(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createPaymentProcessor(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorName") @GraphQLNonNull final String paymentProcessorName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorForm();

            commandForm.setPaymentProcessorName(paymentProcessorName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessor(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deletePaymentProcessor(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorName") @GraphQLNonNull final String paymentProcessorName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorForm();

            commandForm.setPaymentProcessorName(paymentProcessorName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessor(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editPaymentProcessor(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorName") final String originalPaymentProcessorName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("paymentProcessorName") final String paymentProcessorName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = PaymentUtil.getHome().getPaymentProcessorUniversalSpec();

            spec.setPaymentProcessorName(originalPaymentProcessorName);
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessor(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getPaymentProcessor().getEntityInstance());

                if(arguments.containsKey("paymentProcessorName"))
                    edit.setPaymentProcessorName(paymentProcessorName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessor(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createPaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") @GraphQLNonNull final String paymentProcessorTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessorType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deletePaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") @GraphQLNonNull final String paymentProcessorTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editPaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorTypeName") final String originalPaymentProcessorTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = PaymentUtil.getHome().getPaymentProcessorTypeUniversalSpec();

            spec.setPaymentProcessorTypeName(originalPaymentProcessorTypeName);
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorType(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getPaymentProcessorType().getEntityInstance());

                if(arguments.containsKey("paymentProcessorTypeName"))
                    edit.setPaymentProcessorTypeName(paymentProcessorTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessorType(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultPaymentProcessorType")
    public static CommandResultObject setDefaultPaymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") @GraphQLNonNull final String paymentProcessorTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getSetDefaultPaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);

            var commandResult = PaymentUtil.getHome().setDefaultPaymentProcessorType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createPaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") @GraphQLNonNull final String paymentMethodTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = PaymentUtil.getHome().getCreatePaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentMethodType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentMethodTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deletePaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") @GraphQLNonNull final String paymentMethodTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentMethodType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editPaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentMethodTypeName") final String originalPaymentMethodTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("paymentMethodTypeName") final String paymentMethodTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = PaymentUtil.getHome().getPaymentMethodTypeUniversalSpec();

            spec.setPaymentMethodTypeName(originalPaymentMethodTypeName);
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentMethodTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentMethodType(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentMethodTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getPaymentMethodType().getEntityInstance());

                if(arguments.containsKey("paymentMethodTypeName"))
                    edit.setPaymentMethodTypeName(paymentMethodTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentMethodType(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultPaymentMethodType")
    public static CommandResultObject setDefaultPaymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") @GraphQLNonNull final String paymentMethodTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getSetDefaultPaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);

            var commandResult = PaymentUtil.getHome().setDefaultPaymentMethodType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createPaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") @GraphQLNonNull final String paymentProcessorResultCodeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessorResultCode(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorResultCodeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deletePaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") @GraphQLNonNull final String paymentProcessorResultCodeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorResultCode(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editPaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorResultCodeName") final String originalPaymentProcessorResultCodeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("paymentProcessorResultCodeName") final String paymentProcessorResultCodeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = PaymentUtil.getHome().getPaymentProcessorResultCodeUniversalSpec();

            spec.setPaymentProcessorResultCodeName(originalPaymentProcessorResultCodeName);
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorResultCodeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorResultCode(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorResultCodeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getPaymentProcessorResultCode().getEntityInstance());

                if(arguments.containsKey("paymentProcessorResultCodeName"))
                    edit.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessorResultCode(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultPaymentProcessorResultCode")
    public static CommandResultObject setDefaultPaymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") @GraphQLNonNull final String paymentProcessorResultCodeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getSetDefaultPaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);

            var commandResult = PaymentUtil.getHome().setDefaultPaymentProcessorResultCode(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createPaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") @GraphQLNonNull final String paymentProcessorActionTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = PaymentUtil.getHome().createPaymentProcessorActionType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreatePaymentProcessorActionTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deletePaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") @GraphQLNonNull final String paymentProcessorActionTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getDeletePaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);

            var commandResult = PaymentUtil.getHome().deletePaymentProcessorActionType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editPaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("originalPaymentProcessorActionTypeName") final String originalPaymentProcessorActionTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("paymentProcessorActionTypeName") final String paymentProcessorActionTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = PaymentUtil.getHome().getPaymentProcessorActionTypeUniversalSpec();

            spec.setPaymentProcessorActionTypeName(originalPaymentProcessorActionTypeName);
            spec.setUlid(id);

            var commandForm = PaymentUtil.getHome().getEditPaymentProcessorActionTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PaymentUtil.getHome().editPaymentProcessorActionType(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditPaymentProcessorActionTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getPaymentProcessorActionType().getEntityInstance());

                if(arguments.containsKey("paymentProcessorActionTypeName"))
                    edit.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = PaymentUtil.getHome().editPaymentProcessorActionType(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultPaymentProcessorActionType")
    public static CommandResultObject setDefaultPaymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") @GraphQLNonNull final String paymentProcessorActionTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = PaymentUtil.getHome().getSetDefaultPaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);

            var commandResult = PaymentUtil.getHome().setDefaultPaymentProcessorActionType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = InventoryUtil.getHome().getCreateInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = InventoryUtil.getHome().createInventoryCondition(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateInventoryConditionResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = InventoryUtil.getHome().getDeleteInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);

            var commandResult = InventoryUtil.getHome().deleteInventoryCondition(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("originalInventoryConditionName") final String originalInventoryConditionName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = InventoryUtil.getHome().getInventoryConditionUniversalSpec();

            spec.setInventoryConditionName(originalInventoryConditionName);
            spec.setUlid(id);
            
            var commandForm = InventoryUtil.getHome().getEditInventoryConditionForm();
            
            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = InventoryUtil.getHome().editInventoryCondition(getUserVisitPK(env), commandForm);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditInventoryConditionResult)executionResult.getResult();                
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();
                
                commandResultObject.setEntityInstance(result.getInventoryCondition().getEntityInstance());

                if(arguments.containsKey("inventoryConditionName"))
                    edit.setInventoryConditionName(inventoryConditionName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);
                
                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);
                
                commandResult = InventoryUtil.getHome().editInventoryCondition(getUserVisitPK(env), commandForm);
            }
            
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultInventoryCondition")
    public static CommandResultObject setDefaultInventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = InventoryUtil.getHome().getSetDefaultInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);

            var commandResult = InventoryUtil.getHome().setDefaultInventoryCondition(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") @GraphQLNonNull final String contentPageLayoutName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = ContentUtil.getHome().getCreateContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ContentUtil.getHome().createContentPageLayout(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateContentPageLayoutResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") @GraphQLNonNull final String contentPageLayoutName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ContentUtil.getHome().getDeleteContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);

            var commandResult = ContentUtil.getHome().deleteContentPageLayout(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("originalContentPageLayoutName") final String originalContentPageLayoutName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("contentPageLayoutName") final String contentPageLayoutName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = ContentUtil.getHome().getContentPageLayoutUniversalSpec();

            spec.setContentPageLayoutName(originalContentPageLayoutName);
            spec.setUlid(id);
            
            var commandForm = ContentUtil.getHome().getEditContentPageLayoutForm();
            
            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ContentUtil.getHome().editContentPageLayout(getUserVisitPK(env), commandForm);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditContentPageLayoutResult)executionResult.getResult();                
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();
                
                commandResultObject.setEntityInstance(result.getContentPageLayout().getEntityInstance());

                if(arguments.containsKey("contentPageLayoutName"))
                    edit.setContentPageLayoutName(contentPageLayoutName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);
                
                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);
                
                commandResult = ContentUtil.getHome().editContentPageLayout(getUserVisitPK(env), commandForm);
            }
            
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultContentPageLayout")
    public static CommandResultObject setDefaultContentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") @GraphQLNonNull final String contentPageLayoutName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ContentUtil.getHome().getSetDefaultContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);

            var commandResult = ContentUtil.getHome().setDefaultContentPageLayout(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredLanguage")
    public static CommandResultObject setUserVisitPreferredLanguage(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredLanguageForm();

            commandForm.setLanguageIsoName(languageIsoName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredLanguage(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredCurrency")
    public static CommandResultObject setUserVisitPreferredCurrency(final DataFetchingEnvironment env,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredCurrencyForm();

            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredCurrency(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredTimeZone")
    public static CommandResultObject setUserVisitPreferredTimeZone(final DataFetchingEnvironment env,
            @GraphQLName("javaTimeZoneName") @GraphQLNonNull final String javaTimeZoneName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredTimeZoneForm();

            commandForm.setJavaTimeZoneName(javaTimeZoneName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredTimeZone(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setUserVisitPreferredDateTimeFormat")
    public static CommandResultObject setUserVisitPreferredDateTimeFormat(final DataFetchingEnvironment env,
            @GraphQLName("dateTimeFormatName") @GraphQLNonNull final String dateTimeFormatName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = UserUtil.getHome().getSetUserVisitPreferredDateTimeFormatForm();

            commandForm.setDateTimeFormatName(dateTimeFormatName);

            var commandResult = UserUtil.getHome().setUserVisitPreferredDateTimeFormat(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createTagScope(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = TagUtil.getHome().getCreateTagScopeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = TagUtil.getHome().createTagScope(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateTagScopeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteTagScope(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteTagScopeForm();

            commandForm.setTagScopeName(tagScopeName);

            var commandResult = TagUtil.getHome().deleteTagScope(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editTagScope(final DataFetchingEnvironment env,
            @GraphQLName("originalTagScopeName") @GraphQLNonNull final String originalTagScopeName,
            @GraphQLName("tagScopeName") final String tagScopeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = TagUtil.getHome().getTagScopeSpec();

            spec.setTagScopeName(originalTagScopeName);

            var commandForm = TagUtil.getHome().getEditTagScopeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = TagUtil.getHome().editTagScope(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditTagScopeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getTagScope().getEntityInstance());

                if(arguments.containsKey("tagScopeName"))
                    edit.setTagScopeName(tagScopeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = TagUtil.getHome().editTagScope(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultTagScope")
    public static CommandResultObject setDefaultTagScope(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = TagUtil.getHome().getSetDefaultTagScopeForm();

            commandForm.setTagScopeName(tagScopeName);

            var commandResult = TagUtil.getHome().setDefaultTagScope(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createTagScopeEntityType(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = TagUtil.getHome().getCreateTagScopeEntityTypeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var commandResult = TagUtil.getHome().createTagScopeEntityType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteTagScopeEntityType(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("componentVendorName") @GraphQLNonNull final String componentVendorName,
            @GraphQLName("entityTypeName") @GraphQLNonNull final String entityTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteTagScopeEntityTypeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var commandResult = TagUtil.getHome().deleteTagScopeEntityType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createTag(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = TagUtil.getHome().getCreateTagForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().createTag(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateTagResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteTag(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteTagForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().deleteTag(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editTag(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("originalTagName") @GraphQLNonNull final String originalTagName,
            @GraphQLName("tagName") final String tagName) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = TagUtil.getHome().getTagSpec();

            spec.setTagScopeName(tagScopeName);
            spec.setTagName(originalTagName);

            var commandForm = TagUtil.getHome().getEditTagForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = TagUtil.getHome().editTag(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditTagResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getTag().getEntityInstance());

                if(arguments.containsKey("tagName"))
                    edit.setTagName(tagName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = TagUtil.getHome().editTag(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityTag(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = TagUtil.getHome().getCreateEntityTagForm();

            commandForm.setUlid(id);
            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().createEntityTag(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityTag(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id,
            @GraphQLName("tagScopeName") @GraphQLNonNull final String tagScopeName,
            @GraphQLName("tagName") @GraphQLNonNull final String tagName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = TagUtil.getHome().getDeleteEntityTagForm();

            commandForm.setUlid(id);
            commandForm.setTagScopeName(tagScopeName);
            commandForm.setTagName(tagName);

            var commandResult = TagUtil.getHome().deleteEntityTag(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeGroupName") @GraphQLNonNull final String entityAttributeGroupName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityAttributeGroupForm();

            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = CoreUtil.getHome().createEntityAttributeGroup(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateEntityAttributeGroupResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeGroupName") @GraphQLNonNull final String entityAttributeGroupName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityAttributeGroupForm();

            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);

            var commandResult = CoreUtil.getHome().deleteEntityAttributeGroup(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("originalEntityAttributeGroupName") @GraphQLNonNull final String originalEntityAttributeGroupName,
            @GraphQLName("entityAttributeGroupName") final String entityAttributeGroupName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = CoreUtil.getHome().getEntityAttributeGroupSpec();

            spec.setEntityAttributeGroupName(originalEntityAttributeGroupName);

            var commandForm = CoreUtil.getHome().getEditEntityAttributeGroupForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityAttributeGroup(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityAttributeGroupResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getEntityAttributeGroup().getEntityInstance());

                if(arguments.containsKey("entityAttributeGroupName"))
                    edit.setEntityAttributeGroupName(entityAttributeGroupName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityAttributeGroup(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setDefaultEntityAttributeGroup")
    public static CommandResultObject setDefaultEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeGroupName") @GraphQLNonNull final String entityAttributeGroupName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getSetDefaultEntityAttributeGroupForm();

            commandForm.setEntityAttributeGroupName(entityAttributeGroupName);

            var commandResult = CoreUtil.getHome().setDefaultEntityAttributeGroup(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeTypeName") @GraphQLNonNull final String entityAttributeTypeName,
            @GraphQLName("trackRevisions") @GraphQLNonNull final String trackRevisions,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description,
            @GraphQLName("checkContentWebAddress") final String checkContentWebAddress,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("upperRangeIntegerValue") final String upperRangeIntegerValue,
            @GraphQLName("upperLimitIntegerValue") final String upperLimitIntegerValue,
            @GraphQLName("lowerLimitIntegerValue") final String lowerLimitIntegerValue,
            @GraphQLName("lowerRangeIntegerValue") final String lowerRangeIntegerValue,
            @GraphQLName("upperRangeLongValue") final String upperRangeLongValue,
            @GraphQLName("upperLimitLongValue") final String upperLimitLongValue,
            @GraphQLName("lowerLimitLongValue") final String lowerLimitLongValue,
            @GraphQLName("lowerRangeLongValue") final String lowerRangeLongValue,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("entityListItemSequenceName") final String entityListItemSequenceName) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityAttributeForm();

            commandForm.setUlid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeTypeName(entityAttributeTypeName);
            commandForm.setTrackRevisions(trackRevisions);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);
            commandForm.setCheckContentWebAddress(checkContentWebAddress);
            commandForm.setValidationPattern(validationPattern);
            commandForm.setUpperRangeIntegerValue(upperRangeIntegerValue);
            commandForm.setUpperLimitIntegerValue(upperLimitIntegerValue);
            commandForm.setLowerLimitIntegerValue(lowerLimitIntegerValue);
            commandForm.setLowerRangeIntegerValue(lowerRangeIntegerValue);
            commandForm.setUpperRangeLongValue(upperRangeLongValue);
            commandForm.setUpperLimitLongValue(upperLimitLongValue);
            commandForm.setLowerLimitLongValue(lowerLimitLongValue);
            commandForm.setLowerRangeLongValue(lowerRangeLongValue);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setEntityListItemSequenceName(entityListItemSequenceName);

            var commandResult = CoreUtil.getHome().createEntityAttribute(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateEntityAttributeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("originalEntityAttributeName") final String originalEntityAttributeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("trackRevisions") final String trackRevisions,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityAttributeUniversalSpec();

            spec.setUlid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(originalEntityAttributeName);

            var commandForm = CoreUtil.getHome().getEditEntityAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityAttributeName"))
                    edit.setEntityAttributeName(entityAttributeName);
                if(arguments.containsKey("trackRevisions"))
                    edit.setTrackRevisions(trackRevisions);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityAttributeForm();

            commandForm.setUlid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);

            var commandResult = CoreUtil.getHome().deleteEntityAttribute(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createEntityListItem(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityListItemForm();

            commandForm.setUlid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = CoreUtil.getHome().createEntityListItem(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateEntityListItemResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityListItem(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("originalEntityListItemName") final String originalEntityListItemName,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityListItemUniversalSpec();

            spec.setUlid(id);
            spec.setComponentVendorName(componentVendorName);
            spec.setEntityTypeName(entityTypeName);
            spec.setEntityAttributeName(entityAttributeName);
            spec.setEntityListItemName(originalEntityListItemName);

            var commandForm = CoreUtil.getHome().getEditEntityListItemForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityListItem(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityListItemResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityListItemName"))
                    edit.setEntityListItemName(entityListItemName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityListItem(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityListItem(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityListItemName") final String entityListItemName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityListItemForm();

            commandForm.setUlid(id);
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);

            var commandResult = CoreUtil.getHome().deleteEntityListItem(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityListItemAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUlid(entityListItemId);

            var commandResult = CoreUtil.getHome().createEntityListItemAttribute(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityListItemAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeName(entityAttributeName);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityListItemAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityListItemAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityListItemAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityListItemName"))
                    edit.setEntityListItemName(entityListItemName);
                if(arguments.containsKey("entityListItemId"))
                    edit.setEntityListItemUlid(entityListItemId);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityListItemAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityListItemAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            var commandResult = CoreUtil.getHome().deleteEntityListItemAttribute(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityMultipleListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityMultipleListItemAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUlid(entityListItemId);

            var commandResult = CoreUtil.getHome().createEntityMultipleListItemAttribute(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityMultipleListItemAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("entityAttributeId") @GraphQLID final String entityAttributeId,
            @GraphQLName("entityListItemName") final String entityListItemName,
            @GraphQLName("entityListItemId") @GraphQLID final String entityListItemId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityMultipleListItemAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityListItemName(entityListItemName);
            commandForm.setEntityListItemUlid(entityListItemId);

            var commandResult = CoreUtil.getHome().deleteEntityMultipleListItemAttribute(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityBooleanAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("booleanAttribute") @GraphQLNonNull final String booleanAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityBooleanAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setBooleanAttribute(booleanAttribute);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityBooleanAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityBooleanAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("booleanAttribute") @GraphQLNonNull final String booleanAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityBooleanAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityBooleanAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityBooleanAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityBooleanAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("booleanAttribute"))
                    edit.setBooleanAttribute(booleanAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityBooleanAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityBooleanAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityBooleanAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityBooleanAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityIntegerAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("integerAttribute") @GraphQLNonNull final String integerAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityIntegerAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setIntegerAttribute(integerAttribute);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityIntegerAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityIntegerAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("integerAttribute") @GraphQLNonNull final String integerAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityIntegerAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityIntegerAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityIntegerAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityIntegerAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("integerAttribute"))
                    edit.setIntegerAttribute(integerAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityIntegerAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityIntegerAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityIntegerAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityIntegerAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityLongAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("longAttribute") @GraphQLNonNull final String longAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityLongAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLongAttribute(longAttribute);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityLongAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityLongAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("longAttribute") @GraphQLNonNull final String longAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityLongAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityLongAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityLongAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityLongAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("longAttribute"))
                    edit.setLongAttribute(longAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityLongAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityLongAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityLongAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityLongAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityStringAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("stringAttribute") @GraphQLNonNull final String stringAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityStringAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLanguageUlid(languageId);
            commandForm.setStringAttribute(stringAttribute);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityStringAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityStringAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("stringAttribute") @GraphQLNonNull final String stringAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityStringAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);
            spec.setLanguageUlid(languageId);

            var commandForm = CoreUtil.getHome().getEditEntityStringAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityStringAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityStringAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("stringAttribute"))
                    edit.setStringAttribute(stringAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityStringAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityStringAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull final String languageId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityStringAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLanguageUlid(languageId);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityStringAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityClobAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("clobAttribute") @GraphQLNonNull final String clobAttribute,
            @GraphQLName("mimeTypeName") @GraphQLNonNull final String mimeTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityClobAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLanguageUlid(languageId);
            commandForm.setClobAttribute(clobAttribute);
            commandForm.setMimeTypeName(mimeTypeName);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityClobAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityClobAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull @GraphQLID final String languageId,
            @GraphQLName("clobAttribute") final String clobAttribute,
            @GraphQLName("mimeTypeName") final String mimeTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityClobAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);
            spec.setLanguageUlid(languageId);

            var commandForm = CoreUtil.getHome().getEditEntityClobAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityClobAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityClobAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("clobAttribute"))
                    edit.setClobAttribute(clobAttribute);
                if(arguments.containsKey("mimeTypeName"))
                    edit.setMimeTypeName(mimeTypeName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityClobAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityClobAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("languageId") @GraphQLNonNull final String languageId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityClobAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLanguageUlid(languageId);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityClobAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityNameAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("nameAttribute") @GraphQLNonNull final String nameAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityNameAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setNameAttribute(nameAttribute);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityNameAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityNameAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("nameAttribute") @GraphQLNonNull final String nameAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityNameAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityNameAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityNameAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityNameAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("nameAttribute"))
                    edit.setNameAttribute(nameAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityNameAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityNameAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityNameAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityNameAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityDateAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("dateAttribute") @GraphQLNonNull final String dateAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityDateAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setDateAttribute(dateAttribute);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityDateAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityDateAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("dateAttribute") @GraphQLNonNull final String dateAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityDateAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityDateAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityDateAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityDateAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("dateAttribute"))
                    edit.setDateAttribute(dateAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityDateAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityDateAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityDateAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityDateAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityTimeAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("timeAttribute") @GraphQLNonNull final String timeAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityTimeAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setTimeAttribute(timeAttribute);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityTimeAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityTimeAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("timeAttribute") @GraphQLNonNull final String timeAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityTimeAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityTimeAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityTimeAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityTimeAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("timeAttribute"))
                    edit.setTimeAttribute(timeAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityTimeAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityTimeAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityTimeAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityTimeAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityGeoPointAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("latitude") @GraphQLNonNull final String latitude,
            @GraphQLName("longitude") @GraphQLNonNull final String longitude,
            @GraphQLName("elevation") final String elevation,
            @GraphQLName("elevationUnitOfMeasureTypeName") final String elevationUnitOfMeasureTypeName,
            @GraphQLName("altitude") final String altitude,
            @GraphQLName("altitudeUnitOfMeasureTypeName") final String altitudeUnitOfMeasureTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityGeoPointAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setLatitude(latitude);
            commandForm.setLongitude(longitude);
            commandForm.setElevation(elevation);
            commandForm.setElevationUnitOfMeasureTypeName(elevationUnitOfMeasureTypeName);
            commandForm.setAltitude(altitude);
            commandForm.setAltitudeUnitOfMeasureTypeName(altitudeUnitOfMeasureTypeName);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityGeoPointAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityGeoPointAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("latitude") final String latitude,
            @GraphQLName("longitude") final String longitude,
            @GraphQLName("elevation") final String elevation,
            @GraphQLName("elevationUnitOfMeasureTypeName") final String elevationUnitOfMeasureTypeName,
            @GraphQLName("altitude") final String altitude,
            @GraphQLName("altitudeUnitOfMeasureTypeName") final String altitudeUnitOfMeasureTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityGeoPointAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityGeoPointAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityGeoPointAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityGeoPointAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("latitude"))
                    edit.setLatitude(latitude);
                if(arguments.containsKey("longitude"))
                    edit.setLongitude(longitude);
                if(arguments.containsKey("elevation"))
                    edit.setElevation(elevation);
                if(arguments.containsKey("elevationUnitOfMeasureTypeName"))
                    edit.setElevationUnitOfMeasureTypeName(elevationUnitOfMeasureTypeName);
                if(arguments.containsKey("altitude"))
                    edit.setAltitude(altitude);
                if(arguments.containsKey("altitudeUnitOfMeasureTypeName"))
                    edit.setAltitudeUnitOfMeasureTypeName(altitudeUnitOfMeasureTypeName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityGeoPointAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityGeoPointAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityGeoPointAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityGeoPointAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("entityRefAttribute") @GraphQLNonNull final String entityRefAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityEntityAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityRefAttribute(entityRefAttribute);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityEntityAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editEntityEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("entityRefAttribute") @GraphQLNonNull final String entityRefAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = CoreUtil.getHome().getEntityEntityAttributeSpec();

            spec.setUlid(id);
            spec.setEntityAttributeUlid(entityAttributeId);

            var commandForm = CoreUtil.getHome().getEditEntityEntityAttributeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editEntityEntityAttribute(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditEntityEntityAttributeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("entityRefAttribute"))
                    edit.setEntityRefAttribute(entityRefAttribute);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editEntityEntityAttribute(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityEntityAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityEntityAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createEntityCollectionAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("entityRefAttribute") @GraphQLNonNull final String entityRefAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getCreateEntityCollectionAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityRefAttribute(entityRefAttribute);

            commandResultObject.setCommandResult(CoreUtil.getHome().createEntityCollectionAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteEntityCollectionAttribute(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull @GraphQLID final String id,
            @GraphQLName("entityAttributeId") @GraphQLNonNull @GraphQLID final String entityAttributeId,
            @GraphQLName("entityRefAttribute") @GraphQLNonNull final String entityRefAttribute) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getDeleteEntityCollectionAttributeForm();

            commandForm.setUlid(id);
            commandForm.setEntityAttributeUlid(entityAttributeId);
            commandForm.setEntityRefAttribute(entityRefAttribute);

            commandResultObject.setCommandResult(CoreUtil.getHome().deleteEntityCollectionAttribute(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static SearchCustomersResultObject searchCustomers(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("customerTypeName") final String customerTypeName,
            @GraphQLName("firstName") final String firstName,
            @GraphQLName("firstNameSoundex") final String firstNameSoundex,
            @GraphQLName("middleName") final String middleName,
            @GraphQLName("middleNameSoundex") final String middleNameSoundex,
            @GraphQLName("lastName") final String lastName,
            @GraphQLName("lastNameSoundex") final String lastNameSoundex,
            @GraphQLName("name") final String name,
            @GraphQLName("emailAddress") final String emailAddress,
            @GraphQLName("countryName") final String countryName,
            @GraphQLName("areaCode") final String areaCode,
            @GraphQLName("telephoneNumber") final String telephoneNumber,
            @GraphQLName("telephoneExtension") final String telephoneExtension,
            @GraphQLName("customerName") final String customerName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("alias") final String alias,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince) {
        var commandResultObject = new SearchCustomersResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchCustomersForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.setFirstName(firstName);
            commandForm.setFirstNameSoundex(firstNameSoundex);
            commandForm.setMiddleName(middleName);
            commandForm.setMiddleNameSoundex(middleNameSoundex);
            commandForm.setLastName(lastName);
            commandForm.setLastNameSoundex(lastNameSoundex);
            commandForm.setName(name);
            commandForm.setEmailAddress(emailAddress);
            commandForm.setCountryName(countryName);
            commandForm.setAreaCode(areaCode);
            commandForm.setTelephoneNumber(telephoneNumber);
            commandForm.setTelephoneExtension(telephoneExtension);
            commandForm.setCustomerName(customerName);
            commandForm.setPartyName(partyName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setAlias(alias);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);

            var commandResult = SearchUtil.getHome().searchCustomers(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
            commandResultObject.setResult(commandResult.hasErrors() ? null : (SearchCustomersResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject clearCustomerResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearCustomerResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearCustomerResults(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static SearchEmployeesResultObject searchEmployees(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("firstName") final String firstName,
            @GraphQLName("firstNameSoundex") final String firstNameSoundex,
            @GraphQLName("middleName") final String middleName,
            @GraphQLName("middleNameSoundex") final String middleNameSoundex,
            @GraphQLName("lastName") final String lastName,
            @GraphQLName("lastNameSoundex") final String lastNameSoundex,
            @GraphQLName("employeeName") final String employeeName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("alias") final String alias,
            @GraphQLName("employeeStatusChoice") final String employeeStatusChoice,
            @GraphQLName("employeeAvailabilityChoice") final String employeeAvailabilityChoice,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields) {
        var commandResultObject = new SearchEmployeesResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchEmployeesForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setFirstName(firstName);
            commandForm.setFirstNameSoundex(firstNameSoundex);
            commandForm.setMiddleName(middleName);
            commandForm.setMiddleNameSoundex(middleNameSoundex);
            commandForm.setLastName(lastName);
            commandForm.setLastNameSoundex(lastNameSoundex);
            commandForm.setEmployeeName(employeeName);
            commandForm.setPartyName(partyName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setAlias(alias);
            commandForm.setEmployeeStatusChoice(employeeStatusChoice);
            commandForm.setEmployeeAvailabilityChoice(employeeAvailabilityChoice);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);


            var commandResult = SearchUtil.getHome().searchEmployees(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
            commandResultObject.setResult(commandResult.hasErrors() ? null : (SearchEmployeesResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject clearEmployeeResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearEmployeeResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearEmployeeResults(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static SearchItemsResultObject searchItems(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("searchDefaultOperatorName") final String searchDefaultOperatorName,
            @GraphQLName("searchSortDirectionName") final String searchSortDirectionName,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchSortOrderName") final String searchSortOrderName,
            @GraphQLName("itemNameOrAlias") final String itemNameOrAlias,
            @GraphQLName("description") final String description,
            @GraphQLName("itemTypeName") final String itemTypeName,
            @GraphQLName("itemUseTypeName") final String itemUseTypeName,
            @GraphQLName("itemStatusChoice") final String itemStatusChoice,
            @GraphQLName("itemStatusChoices") final String itemStatusChoices,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields,
            @GraphQLName("rememberPreferences") final String rememberPreferences,
            @GraphQLName("searchUseTypeName") final String searchUseTypeName) {
        var commandResultObject = new SearchItemsResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchItemsForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setItemNameOrAlias(itemNameOrAlias);
            commandForm.setDescription(description);
            commandForm.setItemTypeName(itemTypeName);
            commandForm.setItemUseTypeName(itemUseTypeName);
            commandForm.setItemStatusChoice(itemStatusChoice);
            commandForm.setItemStatusChoices(itemStatusChoices);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchItems(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
            commandResultObject.setResult(commandResult.hasErrors() ? null : (SearchItemsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createItemSearchResultAction(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("searchResultActionTypeName") @GraphQLNonNull final String searchResultActionTypeName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SearchUtil.getHome().getCreateItemSearchResultActionForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchResultActionTypeName(searchResultActionTypeName);
            commandForm.setItemName(itemName);

            var commandResult = SearchUtil.getHome().createItemSearchResultAction(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject clearItemResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearItemResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearItemResults(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static SearchVendorsResultObject searchVendors(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName,
            @GraphQLName("firstName") final String firstName,
            @GraphQLName("firstNameSoundex") final String firstNameSoundex,
            @GraphQLName("middleName") final String middleName,
            @GraphQLName("middleNameSoundex") final String middleNameSoundex,
            @GraphQLName("lastName") final String lastName,
            @GraphQLName("lastNameSoundex") final String lastNameSoundex,
            @GraphQLName("name") final String name,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("partyAliasTypeName") final String partyAliasTypeName,
            @GraphQLName("alias") final String alias,
            @GraphQLName("createdSince") final String createdSince,
            @GraphQLName("modifiedSince") final String modifiedSince,
            @GraphQLName("fields") final String fields) {
        var commandResultObject = new SearchVendorsResultObject();

        try {
            var commandForm = SearchUtil.getHome().getSearchVendorsForm();

            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setFirstName(firstName);
            commandForm.setFirstNameSoundex(firstNameSoundex);
            commandForm.setMiddleName(middleName);
            commandForm.setMiddleNameSoundex(middleNameSoundex);
            commandForm.setLastName(lastName);
            commandForm.setLastNameSoundex(lastNameSoundex);
            commandForm.setName(name);
            commandForm.setVendorName(vendorName);
            commandForm.setPartyName(partyName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.setAlias(alias);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);

            var commandResult = SearchUtil.getHome().searchVendors(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
            commandResultObject.setResult(commandResult.hasErrors() ? null : (SearchVendorsResult)commandResult.getExecutionResult().getResult());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject clearVendorResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = SearchUtil.getHome().getClearVendorResultsForm();

            commandForm.setSearchTypeName(searchTypeName);

            var commandResult = SearchUtil.getHome().clearVendorResults(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("username") final String username,
            @GraphQLName("password1") @GraphQLNonNull final String password1,
            @GraphQLName("password2") @GraphQLNonNull final String password2,
            @GraphQLName("recoveryQuestionName") final String recoveryQuestionName,
            @GraphQLName("answer") final String answer) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = UserUtil.getHome().getCreateUserLoginForm();

            commandForm.setPartyName(partyName);
            commandForm.setUlid(id);
            commandForm.setUsername(username);
            commandForm.setPassword1(password1);
            commandForm.setPassword2(password2);
            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setAnswer(answer);

            var commandResult = UserUtil.getHome().createUserLogin(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("username") @GraphQLNonNull final String username) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = PartyUtil.getHome().getPartyUniversalSpec();

            spec.setPartyName(partyName);
            spec.setUlid(id);
            
            var commandForm = UserUtil.getHome().getEditUserLoginForm();
            
            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = UserUtil.getHome().editUserLogin(getUserVisitPK(env), commandForm);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditUserLoginResult)executionResult.getResult();                
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();
                
                if(arguments.containsKey("username"))
                    edit.setUsername(username);
                
                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);
                
                commandResult = UserUtil.getHome().editUserLogin(getUserVisitPK(env), commandForm);
            }
            
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteUserLogin(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("id") @GraphQLID final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = UserUtil.getHome().getDeleteUserLoginForm();

            commandForm.setPartyName(partyName);
            commandForm.setUlid(id);

            commandResultObject.setCommandResult(UserUtil.getHome().deleteUserLogin(getUserVisitPK(env), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createItemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") @GraphQLNonNull final String itemCategoryName,
            @GraphQLName("parentItemCategoryName") final String parentItemCategoryName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setParentItemCategoryName(parentItemCategoryName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemCategory(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemCategoryResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editItemCategory(final DataFetchingEnvironment env,
            @GraphQLName("originalItemCategoryName") final String originalItemCategoryName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("parentItemCategoryName") final String parentItemCategoryName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemCategoryUniversalSpec();

            spec.setItemCategoryName(originalItemCategoryName);
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemCategoryForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemCategory(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemCategoryResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getItemCategory().getEntityInstance());

                if(arguments.containsKey("itemCategoryName"))
                    edit.setItemCategoryName(itemCategoryName);
                if(arguments.containsKey("parentItemCategoryName"))
                    edit.setParentItemCategoryName(parentItemCategoryName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemCategory(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setUlid(id);

            commandResultObject.setCommandResult(ItemUtil.getHome().deleteItemCategory(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createItemAccountingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemAccountingCategoryName") @GraphQLNonNull final String itemAccountingCategoryName,
            @GraphQLName("parentItemAccountingCategoryName") final String parentItemAccountingCategoryName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = AccountingUtil.getHome().getCreateItemAccountingCategoryForm();

            commandForm.setItemAccountingCategoryName(itemAccountingCategoryName);
            commandForm.setParentItemAccountingCategoryName(parentItemAccountingCategoryName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = AccountingUtil.getHome().createItemAccountingCategory(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemAccountingCategoryResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editItemAccountingCategory(final DataFetchingEnvironment env,
            @GraphQLName("originalItemAccountingCategoryName") final String originalItemAccountingCategoryName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemAccountingCategoryName") final String itemAccountingCategoryName,
            @GraphQLName("parentItemAccountingCategoryName") final String parentItemAccountingCategoryName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = AccountingUtil.getHome().getItemAccountingCategoryUniversalSpec();

            spec.setItemAccountingCategoryName(originalItemAccountingCategoryName);
            spec.setUlid(id);

            var commandForm = AccountingUtil.getHome().getEditItemAccountingCategoryForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = AccountingUtil.getHome().editItemAccountingCategory(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemAccountingCategoryResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getItemAccountingCategory().getEntityInstance());

                if(arguments.containsKey("itemAccountingCategoryName"))
                    edit.setItemAccountingCategoryName(itemAccountingCategoryName);
                if(arguments.containsKey("parentItemAccountingCategoryName"))
                    edit.setParentItemAccountingCategoryName(parentItemAccountingCategoryName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = AccountingUtil.getHome().editItemAccountingCategory(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemAccountingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemAccountingCategoryName") final String itemAccountingCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = AccountingUtil.getHome().getDeleteItemAccountingCategoryForm();

            commandForm.setItemAccountingCategoryName(itemAccountingCategoryName);
            commandForm.setUlid(id);

            commandResultObject.setCommandResult(AccountingUtil.getHome().deleteItemAccountingCategory(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createItemPurchasingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemPurchasingCategoryName") @GraphQLNonNull final String itemPurchasingCategoryName,
            @GraphQLName("parentItemPurchasingCategoryName") final String parentItemPurchasingCategoryName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = VendorUtil.getHome().getCreateItemPurchasingCategoryForm();

            commandForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
            commandForm.setParentItemPurchasingCategoryName(parentItemPurchasingCategoryName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = VendorUtil.getHome().createItemPurchasingCategory(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemPurchasingCategoryResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editItemPurchasingCategory(final DataFetchingEnvironment env,
            @GraphQLName("originalItemPurchasingCategoryName") final String originalItemPurchasingCategoryName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemPurchasingCategoryName") final String itemPurchasingCategoryName,
            @GraphQLName("parentItemPurchasingCategoryName") final String parentItemPurchasingCategoryName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = VendorUtil.getHome().getItemPurchasingCategoryUniversalSpec();

            spec.setItemPurchasingCategoryName(originalItemPurchasingCategoryName);
            spec.setUlid(id);

            var commandForm = VendorUtil.getHome().getEditItemPurchasingCategoryForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = VendorUtil.getHome().editItemPurchasingCategory(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemPurchasingCategoryResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getItemPurchasingCategory().getEntityInstance());

                if(arguments.containsKey("itemPurchasingCategoryName"))
                    edit.setItemPurchasingCategoryName(itemPurchasingCategoryName);
                if(arguments.containsKey("parentItemPurchasingCategoryName"))
                    edit.setParentItemPurchasingCategoryName(parentItemPurchasingCategoryName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = VendorUtil.getHome().editItemPurchasingCategory(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemPurchasingCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemPurchasingCategoryName") final String itemPurchasingCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = VendorUtil.getHome().getDeleteItemPurchasingCategoryForm();

            commandForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
            commandForm.setUlid(id);

            commandResultObject.setCommandResult(VendorUtil.getHome().deleteItemPurchasingCategory(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createItemImageType(final DataFetchingEnvironment env,
            @GraphQLName("itemImageTypeName") @GraphQLNonNull final String itemImageTypeName,
            @GraphQLName("preferredMimeTypeName") final String preferredMimeTypeName,
            @GraphQLName("quality") final String quality,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemImageTypeForm();

            commandForm.setItemImageTypeName(itemImageTypeName);
            commandForm.setPreferredMimeTypeName(preferredMimeTypeName);
            commandForm.setQuality(quality);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemImageType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemImageTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editItemImageType(final DataFetchingEnvironment env,
            @GraphQLName("originalItemImageTypeName") final String originalItemImageTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("preferredMimeTypeName") final String preferredMimeTypeName,
            @GraphQLName("quality") final String quality,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemImageTypeUniversalSpec();

            spec.setItemImageTypeName(originalItemImageTypeName);
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemImageTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemImageType(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemImageTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getItemImageType().getEntityInstance());

                if(arguments.containsKey("itemImageTypeName"))
                    edit.setItemImageTypeName(itemImageTypeName);
                if(arguments.containsKey("preferredMimeTypeName"))
                    edit.setPreferredMimeTypeName(preferredMimeTypeName);
                if(arguments.containsKey("quality"))
                    edit.setQuality(quality);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemImageType(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemImageType(final DataFetchingEnvironment env,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemImageTypeForm();

            commandForm.setItemImageTypeName(itemImageTypeName);
            commandForm.setUlid(id);

            commandResultObject.setCommandResult(ItemUtil.getHome().deleteItemImageType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createItemDescriptionTypeUseType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") @GraphQLNonNull final String itemDescriptionTypeUseTypeName,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemDescriptionTypeUseTypeForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemDescriptionTypeUseType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemDescriptionTypeUseTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editItemDescriptionTypeUseType(final DataFetchingEnvironment env,
            @GraphQLName("originalItemDescriptionTypeUseTypeName") final String originalItemDescriptionTypeUseTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemDescriptionTypeUseTypeName") final String itemDescriptionTypeUseTypeName,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemDescriptionTypeUseTypeUniversalSpec();

            spec.setItemDescriptionTypeUseTypeName(originalItemDescriptionTypeUseTypeName);
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemDescriptionTypeUseTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemDescriptionTypeUseType(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemDescriptionTypeUseTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getItemDescriptionTypeUseType().getEntityInstance());

                if(arguments.containsKey("itemDescriptionTypeUseTypeName"))
                    edit.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemDescriptionTypeUseType(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemDescriptionTypeUseType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") final String itemDescriptionTypeUseTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionTypeUseTypeForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setUlid(id);

            commandResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescriptionTypeUseType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createItemDescriptionTypeUse(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") @GraphQLNonNull final String itemDescriptionTypeUseTypeName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemDescriptionTypeUseForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);

            var commandResult = ItemUtil.getHome().createItemDescriptionTypeUse(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemDescriptionTypeUse(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeUseTypeName") @GraphQLNonNull final String itemDescriptionTypeUseTypeName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionTypeUseForm();

            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);

            commandResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescriptionTypeUse(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createItemDescriptionType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName,
            @GraphQLName("parentItemDescriptionTypeName") final String parentItemDescriptionTypeName,
            @GraphQLName("useParentIfMissing") @GraphQLNonNull final String useParentIfMissing,
            @GraphQLName("mimeTypeUsageTypeName") final String mimeTypeUsageTypeName,
            @GraphQLName("checkContentWebAddress") @GraphQLNonNull final String checkContentWebAddress,
            @GraphQLName("includeInIndex") @GraphQLNonNull final String includeInIndex,
            @GraphQLName("indexDefault") @GraphQLNonNull final String indexDefault,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description,
            @GraphQLName("minimumHeight") final String minimumHeight,
            @GraphQLName("minimumWidth") final String minimumWidth,
            @GraphQLName("maximumHeight") final String maximumHeight,
            @GraphQLName("maximumWidth") final String maximumWidth,
            @GraphQLName("preferredHeight") final String preferredHeight,
            @GraphQLName("preferredWidth") final String preferredWidth,
            @GraphQLName("preferredMimeTypeName") final String preferredMimeTypeName,
            @GraphQLName("quality") final String quality,
            @GraphQLName("scaleFromParent") final String scaleFromParent) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemDescriptionTypeForm();

            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setParentItemDescriptionTypeName(parentItemDescriptionTypeName);
            commandForm.setUseParentIfMissing(useParentIfMissing);
            commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);
            commandForm.setCheckContentWebAddress(checkContentWebAddress);
            commandForm.setIncludeInIndex(includeInIndex);
            commandForm.setIndexDefault(indexDefault);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);
            commandForm.setMinimumHeight(minimumHeight);
            commandForm.setMinimumWidth(minimumWidth);
            commandForm.setMaximumHeight(maximumHeight);
            commandForm.setMaximumWidth(maximumWidth);
            commandForm.setPreferredHeight(preferredHeight);
            commandForm.setPreferredWidth(preferredWidth);
            commandForm.setPreferredMimeTypeName(preferredMimeTypeName);
            commandForm.setQuality(quality);
            commandForm.setScaleFromParent(scaleFromParent);

            var commandResult = ItemUtil.getHome().createItemDescriptionType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemDescriptionTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editItemDescriptionType(final DataFetchingEnvironment env,
            @GraphQLName("originalItemDescriptionTypeName") final String originalItemDescriptionTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemDescriptionTypeName") final String itemDescriptionTypeName,
            @GraphQLName("parentItemDescriptionTypeName") final String parentItemDescriptionTypeName,
            @GraphQLName("useParentIfMissing") final String useParentIfMissing,
            @GraphQLName("checkContentWebAddress") final String checkContentWebAddress,
            @GraphQLName("includeInIndex") final String includeInIndex,
            @GraphQLName("indexDefault") final String indexDefault,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description,
            @GraphQLName("minimumHeight") final String minimumHeight,
            @GraphQLName("minimumWidth") final String minimumWidth,
            @GraphQLName("maximumHeight") final String maximumHeight,
            @GraphQLName("maximumWidth") final String maximumWidth,
            @GraphQLName("preferredHeight") final String preferredHeight,
            @GraphQLName("preferredWidth") final String preferredWidth,
            @GraphQLName("preferredMimeTypeName") final String preferredMimeTypeName,
            @GraphQLName("quality") final String quality,
            @GraphQLName("scaleFromParent") final String scaleFromParent) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemDescriptionTypeUniversalSpec();

            spec.setItemDescriptionTypeName(originalItemDescriptionTypeName);
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemDescriptionTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemDescriptionType(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemDescriptionTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getItemDescriptionType().getEntityInstance());

                if(arguments.containsKey("itemDescriptionTypeName"))
                    edit.setItemDescriptionTypeName(itemDescriptionTypeName);
                if(arguments.containsKey("useParentIfMissing"))
                    edit.setUseParentIfMissing(useParentIfMissing);
                if(arguments.containsKey("checkContentWebAddress"))
                    edit.setCheckContentWebAddress(checkContentWebAddress);
                if(arguments.containsKey("includeInIndex"))
                    edit.setIncludeInIndex(includeInIndex);
                if(arguments.containsKey("indexDefault"))
                    edit.setIndexDefault(indexDefault);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);
                if(arguments.containsKey("minimumHeight"))
                    edit.setMinimumHeight(minimumHeight);
                if(arguments.containsKey("minimumWidth"))
                    edit.setMinimumWidth(minimumWidth);
                if(arguments.containsKey("maximumHeight"))
                    edit.setMaximumHeight(maximumHeight);
                if(arguments.containsKey("maximumWidth"))
                    edit.setMaximumWidth(maximumWidth);
                if(arguments.containsKey("preferredHeight"))
                    edit.setPreferredHeight(preferredHeight);
                if(arguments.containsKey("preferredWidth"))
                    edit.setPreferredWidth(preferredWidth);
                if(arguments.containsKey("preferredMimeTypeName"))
                    edit.setPreferredMimeTypeName(preferredMimeTypeName);
                if(arguments.containsKey("quality"))
                    edit.setQuality(quality);
                if(arguments.containsKey("scaleFromParent"))
                    edit.setScaleFromParent(scaleFromParent);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemDescriptionType(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemDescriptionType(final DataFetchingEnvironment env,
            @GraphQLName("itemDescriptionTypeName") final String itemDescriptionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionTypeForm();

            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setUlid(id);

            commandResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescriptionType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createItemAliasType(final DataFetchingEnvironment env,
            @GraphQLName("itemAliasTypeName") @GraphQLNonNull final String itemAliasTypeName,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("itemAliasChecksumTypeName") @GraphQLNonNull final String itemAliasChecksumTypeName,
            @GraphQLName("allowMultiple") @GraphQLNonNull final String allowMultiple,
            @GraphQLName("isDefault") @GraphQLNonNull final String isDefault,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemAliasTypeForm();

            commandForm.setItemAliasTypeName(itemAliasTypeName);
            commandForm.setValidationPattern(validationPattern);
            commandForm.setItemAliasChecksumTypeName(itemAliasChecksumTypeName);
            commandForm.setAllowMultiple(allowMultiple);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = ItemUtil.getHome().createItemAliasType(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemAliasTypeResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editItemAliasType(final DataFetchingEnvironment env,
            @GraphQLName("originalItemAliasTypeName") final String originalItemAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemAliasTypeName") final String itemAliasTypeName,
            @GraphQLName("validationPattern") final String validationPattern,
            @GraphQLName("itemAliasChecksumTypeName") final String itemAliasChecksumTypeName,
            @GraphQLName("allowMultiple") final String allowMultiple,
            @GraphQLName("isDefault") final String isDefault,
            @GraphQLName("sortOrder") final String sortOrder,
            @GraphQLName("description") final String description) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemAliasTypeUniversalSpec();

            spec.setItemAliasTypeName(originalItemAliasTypeName);
            spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemAliasTypeForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemAliasType(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemAliasTypeResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getItemAliasType().getEntityInstance());

                if(arguments.containsKey("itemAliasTypeName"))
                    edit.setItemAliasTypeName(itemAliasTypeName);
                if(arguments.containsKey("validationPattern"))
                    edit.setValidationPattern(validationPattern);
                if(arguments.containsKey("itemAliasChecksumTypeName"))
                    edit.setItemAliasChecksumTypeName(itemAliasChecksumTypeName);
                if(arguments.containsKey("allowMultiple"))
                    edit.setAllowMultiple(allowMultiple);
                if(arguments.containsKey("isDefault"))
                    edit.setIsDefault(isDefault);
                if(arguments.containsKey("sortOrder"))
                    edit.setSortOrder(sortOrder);
                if(arguments.containsKey("description"))
                    edit.setDescription(description);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemAliasType(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemAliasType(final DataFetchingEnvironment env,
            @GraphQLName("itemAliasTypeName") final String itemAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemAliasTypeForm();

            commandForm.setItemAliasTypeName(itemAliasTypeName);
            commandForm.setUlid(id);

            commandResultObject.setCommandResult(ItemUtil.getHome().deleteItemAliasType(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createItemAlias(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("itemAliasTypeName") @GraphQLNonNull final String itemAliasTypeName,
            @GraphQLName("alias") @GraphQLNonNull final String alias) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemAliasForm();

            commandForm.setItemName(itemName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setItemAliasTypeName(itemAliasTypeName);
            commandForm.setAlias(alias);

            var commandResult = ItemUtil.getHome().createItemAlias(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editItemAlias(final DataFetchingEnvironment env,
            @GraphQLName("originalAlias") @GraphQLNonNull final String originalAlias,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("itemAliasTypeName") final String itemAliasTypeName,
            @GraphQLName("alias") final String alias) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = ItemUtil.getHome().getItemAliasSpec();

            spec.setAlias(originalAlias);

            var commandForm = ItemUtil.getHome().getEditItemAliasForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemAlias(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemAliasResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitOfMeasureTypeName"))
                    edit.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                if(arguments.containsKey("itemAliasTypeName"))
                    edit.setItemAliasTypeName(itemAliasTypeName);
                if(arguments.containsKey("alias"))
                    edit.setAlias(alias);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemAlias(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemAlias(final DataFetchingEnvironment env,
            @GraphQLName("alias") @GraphQLID final String alias) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemAliasForm();

            commandForm.setAlias(alias);

            commandResultObject.setCommandResult(ItemUtil.getHome().deleteItemAlias(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createItem(final DataFetchingEnvironment env,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("itemTypeName") @GraphQLNonNull final String itemTypeName,
            @GraphQLName("itemUseTypeName") @GraphQLNonNull final String itemUseTypeName,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("itemAccountingCategoryName") final String itemAccountingCategoryName,
            @GraphQLName("itemPurchasingCategoryName") final String itemPurchasingCategoryName,
            @GraphQLName("companyName") @GraphQLNonNull final String companyName,
            @GraphQLName("itemDeliveryTypeName") final String itemDeliveryTypeName,
            @GraphQLName("itemInventoryTypeName") final String itemInventoryTypeName,
            @GraphQLName("inventorySerialized") final String inventorySerialized,
            @GraphQLName("shippingChargeExempt") @GraphQLNonNull final String shippingChargeExempt,
            @GraphQLName("shippingStartTime") final String shippingStartTime,
            @GraphQLName("shippingEndTime") final String shippingEndTime,
            @GraphQLName("salesOrderStartTime") final String salesOrderStartTime,
            @GraphQLName("salesOrderEndTime") final String salesOrderEndTime,
            @GraphQLName("purchaseOrderStartTime") final String purchaseOrderStartTime,
            @GraphQLName("purchaseOrderEndTime") final String purchaseOrderEndTime,
            @GraphQLName("allowClubDiscounts") @GraphQLNonNull final String allowClubDiscounts,
            @GraphQLName("allowCouponDiscounts") @GraphQLNonNull final String allowCouponDiscounts,
            @GraphQLName("allowAssociatePayments") @GraphQLNonNull final String allowAssociatePayments,
            @GraphQLName("itemStatus") @GraphQLNonNull final String itemStatus,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName,
            @GraphQLName("itemPriceTypeName") @GraphQLNonNull final String itemPriceTypeName,
            @GraphQLName("cancellationPolicyName") final String cancellationPolicyName,
            @GraphQLName("returnPolicyName") final String returnPolicyName) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemForm();

            commandForm.setItemName(itemName);
            commandForm.setItemTypeName(itemTypeName);
            commandForm.setItemUseTypeName(itemUseTypeName);
            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setItemAccountingCategoryName(itemAccountingCategoryName);
            commandForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
            commandForm.setCompanyName(companyName);
            commandForm.setItemDeliveryTypeName(itemDeliveryTypeName);
            commandForm.setItemInventoryTypeName(itemInventoryTypeName);
            commandForm.setInventorySerialized(inventorySerialized);
            commandForm.setShippingChargeExempt(shippingChargeExempt);
            commandForm.setShippingStartTime(shippingStartTime);
            commandForm.setShippingEndTime(shippingEndTime);
            commandForm.setSalesOrderStartTime(salesOrderStartTime);
            commandForm.setSalesOrderEndTime(salesOrderEndTime);
            commandForm.setPurchaseOrderStartTime(purchaseOrderStartTime);
            commandForm.setPurchaseOrderEndTime(purchaseOrderEndTime);
            commandForm.setAllowClubDiscounts(allowClubDiscounts);
            commandForm.setAllowCouponDiscounts(allowCouponDiscounts);
            commandForm.setAllowAssociatePayments(allowAssociatePayments);
            commandForm.setItemStatus(itemStatus);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setItemPriceTypeName(itemPriceTypeName);
            commandForm.setCancellationPolicyName(cancellationPolicyName);
            commandForm.setReturnPolicyName(returnPolicyName);

            var commandResult = ItemUtil.getHome().createItem(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editItem(final DataFetchingEnvironment env,
            @GraphQLName("originalItemName") @GraphQLNonNull final String originalItemName,
            // @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("itemAccountingCategoryName") final String itemAccountingCategoryName,
            @GraphQLName("itemPurchasingCategoryName") final String itemPurchasingCategoryName,
            @GraphQLName("shippingChargeExempt") final String shippingChargeExempt,
            @GraphQLName("shippingStartTime") final String shippingStartTime,
            @GraphQLName("shippingEndTime") final String shippingEndTime,
            @GraphQLName("salesOrderStartTime") final String salesOrderStartTime,
            @GraphQLName("salesOrderEndTime") final String salesOrderEndTime,
            @GraphQLName("purchaseOrderStartTime") final String purchaseOrderStartTime,
            @GraphQLName("purchaseOrderEndTime") final String purchaseOrderEndTime,
            @GraphQLName("allowClubDiscounts") final String allowClubDiscounts,
            @GraphQLName("allowCouponDiscounts") final String allowCouponDiscounts,
            @GraphQLName("allowAssociatePayments") final String allowAssociatePayments,
            @GraphQLName("cancellationPolicyName") final String cancellationPolicyName,
            @GraphQLName("returnPolicyName") final String returnPolicyName) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemUniversalSpec();

            spec.setItemName(originalItemName);
            // spec.setUlid(id);

            var commandForm = ItemUtil.getHome().getEditItemForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItem(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getItem().getEntityInstance());

                if(arguments.containsKey("itemName"))
                    edit.setItemName(itemName);
                if(arguments.containsKey("itemCategoryName"))
                    edit.setItemCategoryName(itemCategoryName);
                if(arguments.containsKey("itemAccountingCategoryName"))
                    edit.setItemAccountingCategoryName(itemAccountingCategoryName);
                if(arguments.containsKey("itemPurchasingCategoryName"))
                    edit.setItemPurchasingCategoryName(itemPurchasingCategoryName);
                if(arguments.containsKey("shippingChargeExempt"))
                    edit.setShippingChargeExempt(shippingChargeExempt);
                if(arguments.containsKey("shippingStartTime"))
                    edit.setShippingStartTime(shippingStartTime);
                if(arguments.containsKey("shippingEndTime"))
                    edit.setShippingEndTime(shippingEndTime);
                if(arguments.containsKey("salesOrderStartTime"))
                    edit.setSalesOrderStartTime(salesOrderStartTime);
                if(arguments.containsKey("salesOrderEndTime"))
                    edit.setSalesOrderEndTime(salesOrderEndTime);
                if(arguments.containsKey("purchaseOrderStartTime"))
                    edit.setPurchaseOrderStartTime(purchaseOrderStartTime);
                if(arguments.containsKey("purchaseOrderEndTime"))
                    edit.setPurchaseOrderEndTime(purchaseOrderEndTime);
                if(arguments.containsKey("allowClubDiscounts"))
                    edit.setAllowClubDiscounts(allowClubDiscounts);
                if(arguments.containsKey("allowCouponDiscounts"))
                    edit.setAllowCouponDiscounts(allowCouponDiscounts);
                if(arguments.containsKey("allowAssociatePayments"))
                    edit.setAllowAssociatePayments(allowAssociatePayments);
                if(arguments.containsKey("cancellationPolicyName"))
                    edit.setCancellationPolicyName(cancellationPolicyName);
                if(arguments.containsKey("returnPolicyName"))
                    edit.setReturnPolicyName(returnPolicyName);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItem(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setItemStatus")
    public static CommandResultObject setItemStatus(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemStatusName,
            @GraphQLName("itemStatusChoice") @GraphQLNonNull final String ItemStatusChoice) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getSetItemStatusForm();

            commandForm.setItemName(itemStatusName);
            commandForm.setItemStatusChoice(ItemStatusChoice);

            commandResultObject.setCommandResult(ItemUtil.getHome().setItemStatus(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createItemDescription(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName,
            @GraphQLName("mimeTypeName") final String mimeTypeName,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("clobDescription") final String clobDescription,
            @GraphQLName("stringDescription") final String stringDescription) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemDescriptionForm();

            commandForm.setItemName(itemName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setMimeTypeName(mimeTypeName);
            commandForm.setItemImageTypeName(itemImageTypeName);
            commandForm.setClobDescription(clobDescription);
            commandForm.setStringDescription(stringDescription);

            var commandResult = ItemUtil.getHome().createItemDescription(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateItemDescriptionResult)commandResult.getExecutionResult().getResult();

                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject editItemDescription(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName,
            @GraphQLName("mimeTypeName") final String mimeTypeName,
            @GraphQLName("itemImageTypeName") final String itemImageTypeName,
            @GraphQLName("clobDescription") final String clobDescription,
            @GraphQLName("stringDescription") final String stringDescription) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var spec = ItemUtil.getHome().getItemDescriptionUniversalSpec();

            spec.setItemName(itemName);
            spec.setItemDescriptionTypeName(itemDescriptionTypeName);
            spec.setLanguageIsoName(languageIsoName);

            var commandForm = ItemUtil.getHome().getEditItemDescriptionForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemDescription(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemDescriptionResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                commandResultObject.setEntityInstance(result.getItemDescription().getEntityInstance());

                if(arguments.containsKey("mimeTypeName"))
                    edit.setMimeTypeName(mimeTypeName);
                if(arguments.containsKey("itemImageTypeName"))
                    edit.setItemImageTypeName(itemImageTypeName);
                if(arguments.containsKey("clobDescription"))
                    edit.setClobDescription(clobDescription);
                if(arguments.containsKey("stringDescription"))
                    edit.setStringDescription(stringDescription);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemDescription(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemDescription(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("itemDescriptionTypeName") @GraphQLNonNull final String itemDescriptionTypeName,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemDescriptionForm();

            commandForm.setItemName(itemName);
            commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
            commandForm.setLanguageIsoName(languageIsoName);

            commandResultObject.setCommandResult(ItemUtil.getHome().deleteItemDescription(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject createItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitPrice") final String unitPrice,
            @GraphQLName("minimumUnitPrice") final String minimumUnitPrice,
            @GraphQLName("maximumUnitPrice") final String maximumUnitPrice,
            @GraphQLName("unitPriceIncrement") final String unitPriceIncrement) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getCreateItemPriceForm();

            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setUnitPrice(unitPrice);
            commandForm.setMinimumUnitPrice(minimumUnitPrice);
            commandForm.setMaximumUnitPrice(maximumUnitPrice);
            commandForm.setUnitPriceIncrement(unitPriceIncrement);

            var commandResult = ItemUtil.getHome().createItemPrice(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject deleteItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = ItemUtil.getHome().getDeleteItemPriceForm();

            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);

            var commandResult = ItemUtil.getHome().deleteItemPrice(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject editItemPrice(final DataFetchingEnvironment env,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") @GraphQLNonNull final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") @GraphQLNonNull final String currencyIsoName,
            @GraphQLName("unitPrice") final String unitPrice,
            @GraphQLName("minimumUnitPrice") final String minimumUnitPrice,
            @GraphQLName("maximumUnitPrice") final String maximumUnitPrice,
            @GraphQLName("unitPriceIncrement") final String unitPriceIncrement) {
        var commandResultObject = new CommandResultObject();

        try {
            var spec = ItemUtil.getHome().getItemPriceSpec();

            spec.setItemName(itemName);
            spec.setInventoryConditionName(inventoryConditionName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            spec.setCurrencyIsoName(currencyIsoName);

            var commandForm = ItemUtil.getHome().getEditItemPriceForm();

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = ItemUtil.getHome().editItemPrice(getUserVisitPK(env), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemPriceResult)executionResult.getResult();
                Map<String, Object> arguments = env.getArgument("input");
                var edit = result.getEdit();

                if(arguments.containsKey("unitPrice"))
                    edit.setUnitPrice(unitPrice);
                if(arguments.containsKey("minimumUnitPrice"))
                    edit.setMinimumUnitPrice(minimumUnitPrice);
                if(arguments.containsKey("maximumUnitPrice"))
                    edit.setMaximumUnitPrice(maximumUnitPrice);
                if(arguments.containsKey("unitPriceIncrement"))
                    edit.setUnitPriceIncrement(unitPriceIncrement);

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = ItemUtil.getHome().editItemPrice(getUserVisitPK(env), commandForm);
            }

            commandResultObject.setCommandResult(commandResult);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }
    
    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject unlockEntity(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getUnlockEntityForm();

            commandForm.setUlid(id);

            commandResultObject.setCommandResult(CoreUtil.getHome().unlockEntity(getUserVisitPK(env), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject lockEntity(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLNonNull final String id) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = CoreUtil.getHome().getLockEntityForm();

            commandForm.setUlid(id);

            commandResultObject.setCommandResult(CoreUtil.getHome().lockEntity(getUserVisitPK(env), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject customerLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getCustomerLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(getRemoteInet4Address(env));

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().customerLogin(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createCustomer(final DataFetchingEnvironment env,
            @GraphQLName("personalTitleId") final String personalTitleId,
            @GraphQLName("firstName") @GraphQLNonNull final String firstName,
            @GraphQLName("lastName") @GraphQLNonNull final String lastName,
            @GraphQLName("nameSuffixId") final String nameSuffixId,
            @GraphQLName("name") final String name,
            @GraphQLName("initialOfferName") final String initialOfferName,
            @GraphQLName("initialUseName") final String initialUseName,
            @GraphQLName("initialSourceName") final String initialSourceName,
            @GraphQLName("emailAddress") final String emailAddress,
            @GraphQLName("allowSolicitation") @GraphQLNonNull final String allowSolicitation) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = PartyUtil.getHome().getCreateCustomerForm();

            commandForm.setPersonalTitleId(personalTitleId);
            commandForm.setFirstName(firstName);
            commandForm.setLastName(lastName);
            commandForm.setNameSuffixId(nameSuffixId);
            commandForm.setName(name);
            commandForm.setInitialOfferName(initialOfferName);
            commandForm.setInitialUseName(initialUseName);
            commandForm.setInitialSourceName(initialSourceName);
            commandForm.setEmailAddress(emailAddress);
            commandForm.setAllowSolicitation(allowSolicitation);

            var commandResult = PartyUtil.getHome().createCustomer(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateCustomerResult)commandResult.getExecutionResult().getResult();
                
                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultWithIdObject createCustomerWithLogin(final DataFetchingEnvironment env,
            @GraphQLName("personalTitleId") final String personalTitleId,
            @GraphQLName("firstName") @GraphQLNonNull final String firstName,
            @GraphQLName("lastName") @GraphQLNonNull final String lastName,
            @GraphQLName("nameSuffixId") final String nameSuffixId,
            @GraphQLName("name") final String name,
            @GraphQLName("initialOfferName") final String initialOfferName,
            @GraphQLName("initialUseName") final String initialUseName,
            @GraphQLName("initialSourceName") final String initialSourceName,
            @GraphQLName("emailAddress") @GraphQLNonNull final String emailAddress,
            @GraphQLName("allowSolicitation") @GraphQLNonNull final String allowSolicitation,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password1") @GraphQLNonNull final String password1,
            @GraphQLName("password2") @GraphQLNonNull final String password2,
            @GraphQLName("recoveryQuestionName") @GraphQLNonNull final String recoveryQuestionName,
            @GraphQLName("answer") @GraphQLNonNull final String answer) {
        var commandResultObject = new CommandResultWithIdObject();

        try {
            var commandForm = PartyUtil.getHome().getCreateCustomerWithLoginForm();

            commandForm.setPersonalTitleId(personalTitleId);
            commandForm.setFirstName(firstName);
            commandForm.setLastName(lastName);
            commandForm.setNameSuffixId(nameSuffixId);
            commandForm.setName(name);
            commandForm.setInitialOfferName(initialOfferName);
            commandForm.setInitialUseName(initialUseName);
            commandForm.setInitialSourceName(initialSourceName);
            commandForm.setEmailAddress(emailAddress);
            commandForm.setAllowSolicitation(allowSolicitation);
            commandForm.setUsername(username);
            commandForm.setPassword1(password1);
            commandForm.setPassword2(password2);
            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setAnswer(answer);

            var commandResult = PartyUtil.getHome().createCustomerWithLogin(getUserVisitPK(env), commandForm);
            commandResultObject.setCommandResult(commandResult);

            if(!commandResult.hasErrors()) {
                var result = (CreateCustomerWithLoginResult)commandResult.getExecutionResult().getResult();
                
                commandResultObject.setEntityInstanceFromEntityRef(result.getEntityRef());
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject employeeLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password,
            @GraphQLName("companyName") @GraphQLNonNull final String companyName) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getEmployeeLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(getRemoteInet4Address(env));
            commandForm.setCompanyName(companyName);

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().employeeLogin(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject vendorLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") @GraphQLNonNull final String username,
            @GraphQLName("password") @GraphQLNonNull final String password) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getVendorLoginForm();

            commandForm.setUsername(username);
            commandForm.setPassword(password);
            commandForm.setRemoteInet4Address(getRemoteInet4Address(env));

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().vendorLogin(getUserVisitPK(env), commandForm));
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    @GraphQLName("setPassword")
    public static CommandResultObject setPassword(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("employeeName") final String employeeName,
            @GraphQLName("customerName") final String customerName,
            @GraphQLName("vendorName") final String vendorName,
            @GraphQLName("oldPassword") final String oldPassword,
            @GraphQLName("newPassword1") @GraphQLNonNull final String newPassword1,
            @GraphQLName("newPassword2") @GraphQLNonNull final String newPassword2) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getSetPasswordForm();

            commandForm.setPartyName(partyName);
            commandForm.setEmployeeName(employeeName);
            commandForm.setCustomerName(customerName);
            commandForm.setVendorName(vendorName);
            commandForm.setOldPassword(oldPassword);
            commandForm.setNewPassword1(newPassword1);
            commandForm.setNewPassword2(newPassword2);

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().setPassword(getUserVisitPK(env), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject recoverPassword(final DataFetchingEnvironment env,
            @GraphQLName("partyName") final String partyName,
            @GraphQLName("username") final String username,
            @GraphQLName("answer") final String answer) {
        var commandResultObject = new CommandResultObject();

        try {
            var commandForm = AuthenticationUtil.getHome().getRecoverPasswordForm();

            commandForm.setPartyName(partyName);
            commandForm.setUsername(username);
            commandForm.setAnswer(answer);

            commandResultObject.setCommandResult(AuthenticationUtil.getHome().recoverPassword(getUserVisitPK(env), commandForm));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject idle(final DataFetchingEnvironment env) {
        var commandResultObject = new CommandResultObject();

        try {
            commandResultObject.setCommandResult(AuthenticationUtil.getHome().idle(getUserVisitPK(env)));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

    @GraphQLField
    @GraphQLRelayMutation
    public static CommandResultObject logout(final DataFetchingEnvironment env) {
        var commandResultObject = new CommandResultObject();

        try {
            commandResultObject.setCommandResult(AuthenticationUtil.getHome().logout(getUserVisitPK(env)));    
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return commandResultObject;
    }

}
