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

import net.minecraft.server.v1_4_5.MerchantRecipeList;
import net.minecraft.server.v1_4_5.MerchantRecipe;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import java.util.List;
import java.util.ArrayList;

public class PlayerListener implements Listener {
        private TradeControlPlugin plugin;

        public PlayerListener(TradeControlPlugin plugin) {
                this.plugin = plugin;
        }

        @EventHandler
        @SuppressWarnings("unchecked")
        public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
                if (event.getRightClicked() instanceof Villager) {
                        // display debug information
                        // int num = 0;
                        // for (String line : Util.list((Villager)event.getRightClicked(), event.getPlayer())) {
                        //         event.getPlayer().sendMessage("" + (++num) + " " + line);
                        // }
                        // record statistics
                        plugin.getStatistics().record((Villager)event.getRightClicked(), event.getPlayer());
                        // replace trades according to config
                        Util.fixVillager((Villager)event.getRightClicked(), event.getPlayer(), plugin);
                }
        }
}
