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

import org.bukkit.plugin.java.JavaPlugin;
import java.util.Random;

public class TradeControlPlugin extends JavaPlugin {
        private static TradeControlPlugin instance;
        private Configuration configuration;
        private PlayerListener playerListener;
        private CommandListener commandListener;
        private Random random;
        private Statistics statistics;

        @Override
        public void onEnable() {
                instance = this;
                configuration = new Configuration(this);
                playerListener = new PlayerListener(this);
                getServer().getPluginManager().registerEvents(playerListener, this);
                commandListener = new CommandListener(this);
                getCommand("tradecontrol").setExecutor(commandListener);
                random = new Random(System.currentTimeMillis());
                statistics = new Statistics(this);
                // for (int i = 0; i < 100; ++i) {
                //         System.out.println("" + randomInt(5, 10));
                // }
        }
        
        public static TradeControlPlugin getInstance() {
                return instance;
        }

        public Configuration getConfiguration() {
                return configuration;
        }

        public PlayerListener getPlayerListener() {
                return playerListener;
        }

        public int randomInt(int min, int max) {
                return random.nextInt(max - min + 1) + min;
        }

        public void reloadConfiguration() {
                configuration = new Configuration(this);
        }

        public Statistics getStatistics() {
                return statistics;
        }
}
