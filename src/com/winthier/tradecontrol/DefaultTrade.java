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

import net.minecraft.server.v1_4_5.MerchantRecipe;
import net.minecraft.server.v1_4_5.MerchantRecipeList;
import org.bukkit.craftbukkit.v1_4_5.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class DefaultTrade extends AbstractTrade {
        private MerchantRecipeList recipes;
        private int index;

        public DefaultTrade(MerchantRecipeList recipes, int index) {
                this.recipes = recipes;
                this.index = index;
        }

        @Override
        @SuppressWarnings("unchecked")
        public MerchantRecipe getRecipe() {
                return (MerchantRecipe)recipes.get(index);
        }

        public int getIndex() {
                return index;
        }
}
