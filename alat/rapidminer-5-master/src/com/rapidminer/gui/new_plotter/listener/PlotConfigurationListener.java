/*
 *  RapidMiner
 *
 *  Copyright (C) 2001-2014 by RapidMiner and the contributors
 *
 *  Complete list of developers available at our web site:
 *
 *       http://rapidminer.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.gui.new_plotter.listener;

import com.rapidminer.gui.new_plotter.configuration.PlotConfiguration;
import com.rapidminer.gui.new_plotter.listener.events.PlotConfigurationChangeEvent;

/**
 * 
 * @author Nils Woehler
 *
 */
public interface PlotConfigurationListener {

	/**
	 * 
	 * @param change
	 * @return whether the event has been processed and should be deleted at once. 
	 * If <code>false</code> is returned the listener has to notify the {@link PlotConfiguration} when the event has been processed.
	 */
	public boolean plotConfigurationChanged(PlotConfigurationChangeEvent change);
}