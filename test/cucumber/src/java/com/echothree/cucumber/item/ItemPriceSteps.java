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

package com.echothree.cucumber.item;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.CreateItemResult;
import com.echothree.control.user.item.common.result.EditItemPriceResult;
import com.echothree.control.user.item.common.result.EditItemResult;
import com.echothree.cucumber.BasePersona;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.cucumber.user.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import javax.naming.NamingException;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemPriceSteps implements En {

    public ItemPriceSteps() {
        When("^the user begins entering a new item price$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNull();
                    assertThat(persona.deleteItemPriceForm).isNull();
                    assertThat(persona.itemPriceSpec).isNull();

                    persona.createItemPriceForm = ItemUtil.getHome().getCreateItemPriceForm();
                });

        When("^the user adds the new item price$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();

                    var itemService = ItemUtil.getHome();
                    var createItemPriceForm = itemService.getCreateItemPriceForm();

                    createItemPriceForm.set(persona.createItemPriceForm.get());

                    var commandResult = itemService.createItemPrice(persona.userVisitPK, createItemPriceForm);

                    LastCommandResult.commandResult = commandResult;

                    persona.createItemPriceForm = null;
                });

        When("^the user begins deleting an entity list item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNull();
                    assertThat(persona.deleteItemPriceForm).isNull();
                    assertThat(persona.entityListItemSpec).isNull();

                    persona.deleteItemPriceForm = ItemUtil.getHome().getDeleteItemPriceForm();
                });

        When("^the user deletes the entity list item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemPriceForm = persona.deleteItemPriceForm;

                    assertThat(deleteItemPriceForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemPrice(persona.userVisitPK, deleteItemPriceForm);

                    persona.deleteItemPriceForm = null;
                });

        When("^the user begins specifying an entity list item to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNull();
                    assertThat(persona.deleteItemPriceForm).isNull();
                    assertThat(persona.entityListItemSpec).isNull();

                    persona.itemPriceSpec = ItemUtil.getHome().getItemPriceSpec();
                });


        // Edit goes here, alter everything that follows to fill in correct one.


        When("^the user sets the item price's item to ([^\"]*)$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();

                    persona.createItemPriceForm.setItemName(itemName);
                });

        When("^the user sets the item price's item to the last item added$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();
                    assertThat(persona.lastItemName).isNotNull();

                    persona.createItemPriceForm.setItemName(persona.lastItemName);
                });

        When("^the user sets the item price's inventory condition to ([^\"]*)$",
                (String inventoryConditionName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();

                    persona.createItemPriceForm.setInventoryConditionName(inventoryConditionName);
                });

        When("^the user sets the item price's unit of measure type to ([^\"]*)$",
                (String unitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();

                    persona.createItemPriceForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                });

        When("^the user sets the item price's currency to ([^\"]*)$",
                (String currencyIsoName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();

                    persona.createItemPriceForm.setCurrencyIsoName(currencyIsoName);
                });

        When("^the user sets the item price's unit price to ([^\"]*)$",
                (String unitPrice) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();

                    persona.createItemPriceForm.setUnitPrice(unitPrice);
                });

        When("^the user sets the item price's minimum unit price to ([^\"]*)$",
                (String minimumUnitPrice) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();

                    persona.createItemPriceForm.setMinimumUnitPrice(minimumUnitPrice);
                });

        When("^the user sets the item price's maximum unit price to ([^\"]*)$",
                (String maximumUnitPrice) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();

                    persona.createItemPriceForm.setMaximumUnitPrice(maximumUnitPrice);
                });

        When("^the user sets the item price's unit price increment to ([^\"]*)$",
                (String unitPriceIncrement) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();

                    persona.createItemPriceForm.setUnitPriceIncrement(unitPriceIncrement);
                });
    }

}
