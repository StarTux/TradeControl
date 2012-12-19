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

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_4_5.EntityHuman;
import net.minecraft.server.v1_4_5.EntityVillager;
import net.minecraft.server.v1_4_5.MerchantRecipe;
import net.minecraft.server.v1_4_5.MerchantRecipeList;
import org.bukkit.craftbukkit.v1_4_5.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_4_5.entity.CraftVillager;
import org.bukkit.craftbukkit.v1_4_5.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;

public class Util {
        public static List<String> list(Villager villager, Player player) {
                List<String> result = new ArrayList<String>();
                for (Object o : getRecipeList(villager, player)) {
                        MerchantRecipe recipe = (MerchantRecipe)o;
                        String line = printItemStack(recipe.getBuyItem1());
                        if (recipe.hasSecondItem()) {
                                line += " + " + printItemStack(recipe.getBuyItem2());
                        }
                        line += " -> " + printItemStack(recipe.getBuyItem3());
                        result.add(line);
                }
                return result;
        }

        public static MerchantRecipeList getRecipeList(Villager villager, Player player) {
                if (!(villager instanceof CraftVillager)) {
                        return null;
                }
                EntityHuman human = null;
                if (player instanceof CraftHumanEntity) {
                        human = ((CraftHumanEntity)player).getHandle();
                }
                return ((CraftVillager)villager).getHandle().getOffers(human);
        }

        public static String printItemStack(net.minecraft.server.v1_4_5.ItemStack i) {
                return printItemStack(CraftItemStack.asCraftMirror(i));
        }

        public static String printItemStack(ItemStack i) {
                String result = i.getType().name();
                if (i.getDurability() != 0) result += ":" + i.getDurability();
                if (i.getAmount() > 1) result += "x" + i.getAmount();
                return result;
        }

        public static List<Trade> getTrades(MerchantRecipeList recipeList) {
                List<Trade> result = new ArrayList<Trade>(recipeList.size());
                for (int i = 0; i < recipeList.size(); ++i) {
                        result.add(new DefaultTrade(recipeList, i));
                }
                return result;
        }

        public static List<Trade> getTrades(Villager villager, Player player) {
                return getTrades(getRecipeList(villager, player));
        }

        @SuppressWarnings("unchecked")
        public static void fixVillager(Villager villager, Player player, TradeControlPlugin plugin) {
                MerchantRecipeList recipes = getRecipeList(villager, player);
                List<Trade> trades = getTrades(recipes);
                List<MerchantRecipe> newRecipes = new ArrayList<MerchantRecipe>(recipes.size());
                for (Trade trade : trades) {
                        boolean tradeMatched = false;
                        for (TradeFilter filter : plugin.getConfiguration().getFilters()) {
                                if (filter.getPattern().matches(trade)) {
                                        if (filter.getAction() == TradeFilter.Action.DELETE) {
                                                broadcast("delete " + trade, "tradecontrol.notify");
                                                tradeMatched = true;
                                        } else if (filter.getAction() == TradeFilter.Action.REPLACE) {
                                                Trade newTrade = filter.createReplacement();
                                                broadcast("replace " + trade + " with " + newTrade, "tradecontrol.notify");
                                                newRecipes.add(newTrade.getRecipe());
                                                tradeMatched = true;
                                        }
                                }
                        }
                        if (!tradeMatched) newRecipes.add(trade.getRecipe());
                }
                recipes.clear();
                for (MerchantRecipe recipe : newRecipes) {
                        recipes.add(recipe);
                }
        }

        public static void broadcast(String msg, String perm) {
                System.out.println("[TradeControl] " + msg);
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (player.hasPermission(perm)) {
                                player.sendMessage("TradeControl: " + msg);
                        }
                }
        }
}
