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

import net.minecraft.server.MerchantRecipe;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractTrade implements Trade {
        private boolean delete = false;

        @Override
        public ItemStack[] getBuyItems() {
                ItemStack[] result = new ItemStack[getRecipe().hasSecondItem() ? 2 : 1];
                result[0] = new CraftItemStack(getRecipe().getBuyItem1());
                if (result.length > 1) result[1] = new CraftItemStack(getRecipe().getBuyItem2());
                return result;
        }

        @Override
        public ItemStack getSellItem() {
                return new CraftItemStack(getRecipe().getBuyItem3());
        }

        @Override
        public void markForDeletion() {
                delete = true;
        }

        public boolean isMarkedForDeletion() {
                return delete;
        }

        @Override
        public String toString() {
                String result = Util.printItemStack(new CraftItemStack(getRecipe().getBuyItem1()));
                if (getRecipe().hasSecondItem()) {
                        result += " + " + Util.printItemStack(new CraftItemStack(getRecipe().getBuyItem2()));
                }
                result += " -> " + Util.printItemStack(new CraftItemStack(getRecipe().getBuyItem3()));
                return result;
        }
}
