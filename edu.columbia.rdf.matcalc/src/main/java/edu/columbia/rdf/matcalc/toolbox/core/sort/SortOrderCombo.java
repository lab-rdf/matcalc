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
package edu.columbia.rdf.matcalc.toolbox.core.sort;

import org.jebtk.modern.combobox.ModernComboBox;

// TODO: Auto-generated Javadoc
/**
 * The Class SortOrderCombo.
 */
public class SortOrderCombo extends ModernComboBox {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new sort order combo.
	 */
	public SortOrderCombo() {
		addMenuItem("Ascending");
		addMenuItem("Descending");
		
		setSelectedIndex(0);
	}

}
