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

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class Statistics {
        private TradeControlPlugin plugin;
        private List<TradePattern> data = new ArrayList<TradePattern>();

        public Statistics(TradeControlPlugin plugin) {
                this.plugin = plugin;
        }

        public TradePattern find(Trade pivot) {
                for (TradePattern pattern : data) {
                        if (pattern.matchesIgnoreAmounts(pivot)) {
                                return pattern;
                        }
                }
                return null;
        }

        public void record(Villager villager) {
                record(villager, null);
        }

        public void record(Villager villager, Player player) {
                for (Trade trade : Util.getTrades(villager, player)) {
                        record(trade);
                }
        }

        public void record(Trade trade) {
                TradePattern pattern = find(trade);
                if (pattern == null) {
                        pattern = new DefaultTradePattern(trade);
                        data.add(pattern);
                } else {
                        pattern.getBuyItems()[0].adjustAmounts(trade.getBuyItems()[0]);
                        if (pattern.hasSecondItem()) pattern.getBuyItems()[1].adjustAmounts(trade.getBuyItems()[1]);
                        pattern.getSellItem().adjustAmounts(trade.getSellItem());
                }
        }

        public void print(CommandSender sender) {
                sender.sendMessage("TradeControl: " + data.size() + " entries");
                for (TradePattern pattern : data) {
                        sender.sendMessage("" + pattern);
                }
        }

        public void print(OutputStream out) {
                PrintStream ps = new PrintStream(out);
                for (TradePattern pattern : data) {
                        ps.println(pattern);
                }
        }
}
