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

import java.util.List;
import java.util.ArrayList;

public class TradeFilter {
        private List<TradePattern> replacements;
        
        public static enum Action {
                DELETE,
                REPLACE;

                public static Action getAction(String name) {
                        for (Action action : values()) {
                                if (name.equalsIgnoreCase(action.name())) return action;
                        }
                        return null;
                }
        }

        private TradePattern pattern;
        private Action action;

        public TradeFilter(TradePattern pattern, Action action) {
                this.pattern = pattern;
                this.action = action;
        }

        public TradePattern getPattern() {
                return pattern;
        }

        public Action getAction() {
                return action;
        }

        public void setReplacements(List<TradePattern> replacements) {
                this.replacements = new ArrayList<TradePattern>(replacements);
        }

        public TradePattern[] getReplacements() {
                return replacements.toArray(new TradePattern[0]);
        }

        public Trade createReplacement() {
                int i = TradeControlPlugin.getInstance().randomInt(0, replacements.size() - 1);
                return replacements.get(i).create();
        }
}
