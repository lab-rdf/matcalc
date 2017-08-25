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
package edu.columbia.rdf.matcalc.toolbox;

import java.util.List;

import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.combobox.ModernComboBox;
import org.jebtk.modern.table.ModernTableModel;

// TODO: Auto-generated Javadoc
/**
 * The class ColumnCombo.
 */
public class ColumnsCombo extends ModernComboBox {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The Constant MAX_COLUMNS. */
	private static final int MAX_COLUMNS = 50;

	/**
	 * Instantiates a new columns combo.
	 */
	public ColumnsCombo() {
		// Do nothing
	}
	
	/**
	 * Instantiates a new columns combo.
	 *
	 * @param m the m
	 */
	public ColumnsCombo(AnnotationMatrix m) {
		setMatrix(m);
	}
	
	/**
	 * Sets the matrix.
	 *
	 * @param m the new matrix
	 */
	public void setMatrix(AnnotationMatrix m) {
		clear();
		
		for (String name : m.getRowAnnotationNames()) {
			addScrollMenuItem(name);
		}
			
		List<String> names = m.getColumnNames();
		
		int maxC = Math.min(m.getColumnCount(), MAX_COLUMNS);
		
		for (int i = 0; i < maxC; ++i) {
			String name = ModernTableModel.getAutoColumnHeading(i);
			
			if (i < names.size()) {
				name += " - " + names.get(i);
			}
			
			addScrollMenuItem(name);
		}
	}
}