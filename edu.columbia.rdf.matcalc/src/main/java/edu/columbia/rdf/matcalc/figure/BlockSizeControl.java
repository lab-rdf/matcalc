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
package edu.columbia.rdf.matcalc.figure;

import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.geom.IntDim;
import org.jebtk.graphplot.figure.heatmap.legacy.MatrixPlotElement;
import org.jebtk.modern.UI;
import org.jebtk.modern.panel.HExpandBox;
import org.jebtk.modern.panel.VBox;
import org.jebtk.modern.spinner.ModernCompactSpinner;

// TODO: Auto-generated Javadoc
/**
 * The class AspectRatioControl.
 */
public class BlockSizeControl extends VBox {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The member aspect field.
	 */
	private ModernCompactSpinner mXField = 
			new ModernCompactSpinner(1, 100, 100);
	
	/** The m Y field. */
	private ModernCompactSpinner mYField = 
			new ModernCompactSpinner(1, 100, 100);
	
	/**
	 * Instantiates a new aspect ratio control.
	 */
	public BlockSizeControl() {
		this(MatrixPlotElement.DEFAULT_BLOCK);
	}
	
	/**
	 * Instantiates a new aspect ratio control.
	 *
	 * @param aspectRatio the aspect ratio
	 */
	public BlockSizeControl(IntDim aspectRatio) {
		
		add(new HExpandBox("Width", mXField));
		add(UI.createVGap(5));
		add(new HExpandBox("Height", mYField));
		
		mXField.setValue(aspectRatio.getW());
		mYField.setValue(aspectRatio.getH());
		
		setAlignmentY(TOP_ALIGNMENT);
	}
	
	/**
	 * Adds the click listener.
	 *
	 * @param l the l
	 */
	public void addChangeListener(ChangeListener l) {
		mXField.addChangeListener(l);
		mYField.addChangeListener(l);
	}

	/**
	 * Gets the aspect ratio.
	 *
	 * @return the aspect ratio
	 */
	public IntDim getBlockSize() {
		return new IntDim(mXField.getIntValue(), mYField.getIntValue());
	}

}