/**
 * Copyright 2016 Antony Holmes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.columbia.rdf.matcalc.toolbox.core;

import org.jebtk.modern.zoom.ZoomRibbonSection;

import edu.columbia.rdf.matcalc.MainMatCalcWindow;
import edu.columbia.rdf.matcalc.toolbox.CalcModule;


// TODO: Auto-generated Javadoc
/**
 * Can compare a column of values to another list to see what is common and
 * record this in a new column next to the reference column. Useful for
 * doing overlaps and keeping data in a specific order in a table.
 *
 * @author Antony Holmes Holmes
 *
 */
public class ZoomModule extends CalcModule {
	
	/* (non-Javadoc)
	 * @see org.abh.lib.NameProperty#getName()
	 */
	@Override
	public String getName() {
		return "Zoom";
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.apps.matcalc.modules.Module#init(edu.columbia.rdf.apps.matcalc.MainMatCalcWindow)
	 */
	@Override
	public void init(MainMatCalcWindow window) {
		window
		.getRibbon()
		.getToolbar("View")
		.add(new ZoomRibbonSection(window, window.getZoomModel()));

	}
}