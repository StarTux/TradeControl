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

public class DefaultTradePattern implements TradePattern {
        private ItemPattern buy1, buy2, sell;

        public DefaultTradePattern(ItemPattern buy1, ItemPattern buy2, ItemPattern sell) {
                this.buy1 = buy1;
                this.buy2 = buy2;
                this.sell = sell;
        }

        /**
         * Construst a TradePattern from a Trade, setting maximum
         * amounts to the actual stack sizes of the Trade.
         */
        public DefaultTradePattern(Trade trade) {
                ItemStack buy1 = trade.getBuyItems()[0];
                ItemStack buy2 = trade.getBuyItems().length > 1 ? trade.getBuyItems()[1] : null;
                ItemStack sell = trade.getSellItem();
                this.buy1 = new ItemPattern(buy1, buy1.getAmount(), buy1.getAmount());
                if (buy2 != null) this.buy2 = new ItemPattern(buy2, buy2.getAmount(), buy2.getAmount());
                this.sell = new ItemPattern(sell, sell.getAmount(), sell.getAmount());
        }

        @Override
        public boolean matches(Trade trade) {
                int length = buy2 == null ? 1 : 2;
                return trade.getBuyItems().length == length && buy1.matches(trade.getBuyItems()[0]) && (buy2 == null || buy2.matches(trade.getBuyItems()[1])) && sell.matches(trade.getSellItem());
        }

        @Override
        public boolean matchesIgnoreAmounts(Trade trade) {
                int length = buy2 == null? 1 : 2;
                return trade.getBuyItems().length == length && buy1.matchesIgnoreAmounts(trade.getBuyItems()[0]) && (buy2 == null || buy2.matchesIgnoreAmounts(trade.getBuyItems()[1])) && sell.matchesIgnoreAmounts(trade.getSellItem());
        }

        @Override
        public ItemPattern[] getBuyItems() {
                ItemPattern[] result = new ItemPattern[buy2 == null ? 1 : 2];
                result[0] = buy1;
                if (result.length > 1) result[1] = buy2;
                return result;
        }

        @Override
        public ItemPattern getSellItem() {
                return sell;
        }

        @Override
        public boolean hasSecondItem() {
                return buy2 != null;
        }

        @Override
        public Trade create() {
                return new FakeTrade(buy1.create(), buy2 == null ? null : buy2.create(), sell.create());
        }

        @Override
        public boolean isSimilar(TradePattern other) {
                if (other == null) return false;
                ItemPattern[] myBuy = getBuyItems();
                ItemPattern[] otherBuy = other.getBuyItems();
                if (myBuy.length != otherBuy.length) return false;
                for (int i = 0; i < myBuy.length; ++i) {
                        if (!myBuy[i].isSimilar(otherBuy[i])) return false;
                }
                if (!getSellItem().isSimilar(other.getSellItem())) return false;
                return true;
        }

        @Override
        public String toString() {
                String result = "" + buy1;
                if (hasSecondItem()) result += " + " + buy2;
                result += " -> " + sell;
                return result;
        }
}
