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
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.enchantments.Enchantment;

public class ItemPattern {
        private ItemStack item;
        private int minAmount;
        private int maxAmount;

        public ItemPattern(ItemStack item, int minAmount, int maxAmount) {
                this.item = item.clone();
                this.minAmount = minAmount;
                this.maxAmount = maxAmount;
        }

        public ItemPattern(ItemStack item) {
                this(item, 0, Integer.MAX_VALUE);
        }

        public void setMinAmount(int min) {
                minAmount = min;
        }

        public void setMaxAmount(int max) {
                maxAmount = max;
        }

        public int getMinAmount() {
                return minAmount;
        }

        public int getMaxAmount() {
                return maxAmount;
        }

        public boolean matches(ItemStack other) {
                if (item.getType() != other.getType()) return false;
                if (item.getDurability() != other.getDurability()) return false;
                if (minAmount > other.getAmount()) return false;
                if (maxAmount < other.getAmount()) return false;
                if (!compareEnchantments(item, other)) return false;
                return true;
        }

        public boolean matchesIgnoreAmounts(ItemStack other) {
                if (item.getType() != other.getType()) return false;
                if (item.getDurability() != other.getDurability()) return false;
                if (!compareEnchantments(item, other)) return false;
                return true;
        }

        public boolean isSimilar(ItemPattern other) {
                if (other == null) return false;
                if (item.getType() != other.item.getType()) return false;
                if (item.getDurability() != other.item.getDurability()) return false;
                if (!compareEnchantments(item, other.item)) return false;
                return true;
        }

        public static boolean compareEnchantments(ItemStack a, ItemStack b) {
                Map<Enchantment, Integer> enchA = a.getEnchantments();
                Map<Enchantment, Integer> enchB = b.getEnchantments();
                if (enchA.size() != enchB.size()) return false;
                for (Map.Entry<Enchantment, Integer> entry : enchA.entrySet()) {
                        if (!entry.getValue().equals(enchB.get(entry.getKey()))) return false;
                }
                return true;
        }

        public ItemStack create() {
                ItemStack result = item.clone();
                result.setAmount(TradeControlPlugin.getInstance().randomInt(minAmount, maxAmount));
                return result;
        }

        public void adjustAmounts(ItemStack item) {
                minAmount = Math.min(minAmount, item.getAmount());
                maxAmount = Math.max(maxAmount, item.getAmount());
        }
        
        @Override
        public String toString() {
                String result = item.getType().name();
                if (item.getDurability() != 0) result += ":" + item.getDurability();
                if (minAmount == maxAmount) {
                        if (minAmount > 1) {
                                result += "x" + minAmount;
                        }
                } else {
                        result += "{" + minAmount + ".." + maxAmount + "}";
                }
                return result;
        }
}
