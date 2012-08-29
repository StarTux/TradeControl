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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class CommandListener implements CommandExecutor {
        private TradeControlPlugin plugin;

        public CommandListener(TradeControlPlugin plugin) {
                this.plugin = plugin;
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String token, String[] args) {
                if (args.length == 0) {
                        sender.sendMessage("Command list: help, reload, scan, list, dump");
                } else if ("help".equals(args[0])) {
                        sender.sendMessage("TradeControl Help");
                        sender.sendMessage("/tc help - display this message");
                        sender.sendMessage("/tc reload - reload the configuration");
                        sender.sendMessage("/tc scan - fix all loaded villagers");
                        sender.sendMessage("/tc list - list all occuring trades");
                        sender.sendMessage("/tc dump - write occuring trades to disk");
                } else if ("scan".equals(args[0])) {
                        sender.sendMessage("TradeControl: Scanning...");
                        int count = 0;
                        for (World world : plugin.getServer().getWorlds()) {
                                for (Entity entity : world.getEntities()) {
                                        if (entity instanceof Villager) {
                                                count += 1;
                                                Player player = null;
                                                if (sender instanceof Player) player = (Player)sender;
                                                Util.fixVillager((Villager)entity, player, plugin);
                                                plugin.getStatistics().record((Villager)entity, player);
                                        }
                                }
                        }
                        sender.sendMessage("TradeControl: Finished scanning " + count + " villagers.");
                } else if ("reload".equals(args[0])) {
                        plugin.reloadConfiguration();
                        sender.sendMessage("TradeControl: Configuration reloaded.");
                } else if ("list".equals(args[0])) {
                        plugin.getStatistics().print(sender);
                } else if ("dump".equals(args[0])) {
                        final String filename = "trades.txt";
                        File dir = plugin.getDataFolder();
                        if (!dir.exists()) dir.mkdir();
                        File f = new File(dir, filename);
                        FileOutputStream out = null;
                        try {
                                out = new FileOutputStream(f);
                        } catch (IOException ioe) {
                                ioe.printStackTrace();
                                sender.sendMessage("TradeControl: Error writing to " + filename + ". See console");
                                return true;
                        }
                        plugin.getStatistics().print(out);
                        sender.sendMessage("TradeControl: Data written to " + filename + ".");
                }
                return true;
        }
}
