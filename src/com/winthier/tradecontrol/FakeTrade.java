/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright 2012 StarTux
 *
 * This file is part of TradeControl.
 *
 * TradeControl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TradeControl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TradeControl.  If not, see <http://www.gnu.org/licenses/>.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.winthier.tradecontrol;

import org.bukkit.inventory.ItemStack;
import net.minecraft.server.MerchantRecipe;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

public class FakeTrade extends AbstractTrade {
        private ItemStack buyItem1, buyItem2, sellItem;

        public FakeTrade(ItemStack buyItem1, ItemStack buyItem2, ItemStack sellItem) {
                this.buyItem1 = buyItem1;
                this.buyItem2 = buyItem2;
                this.sellItem = sellItem;
        }

        @Override
        public MerchantRecipe getRecipe() {
                if (buyItem2 == null) {
                        return new MerchantRecipe(new CraftItemStack(buyItem1).getHandle(), new CraftItemStack(sellItem).getHandle());
                } else {
                        return new MerchantRecipe(new CraftItemStack(buyItem1).getHandle(), new CraftItemStack(buyItem2).getHandle(), new CraftItemStack(sellItem).getHandle());
                }
        }
}
