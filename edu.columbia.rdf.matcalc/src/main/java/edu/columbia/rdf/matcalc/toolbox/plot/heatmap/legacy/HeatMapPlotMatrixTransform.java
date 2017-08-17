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
package edu.columbia.rdf.matcalc.toolbox.plot.heatmap.legacy;

import java.util.List;

import org.jebtk.core.Properties;
import org.jebtk.graphplot.figure.heatmap.legacy.CountGroups;
import org.jebtk.graphplot.figure.series.XYSeriesModel;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.matcalc.MainMatCalcWindow;
import edu.columbia.rdf.matcalc.PlotMatrixTransform;

// TODO: Auto-generated Javadoc
/**
 * Transform the rows of a matrix.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class HeatMapPlotMatrixTransform extends PlotMatrixTransform {
	
	/**
	 * The member groups.
	 */
	protected XYSeriesModel mGroups;
	
	/**
	 * The member row groups.
	 */
	protected XYSeriesModel mRowGroups;
	
	/**
	 * The member history.
	 */
	protected List<String> mHistory;
	
	/**
	 * The member properties.
	 */
	protected Properties mProperties;

	/** The m count groups. */
	protected CountGroups mCountGroups;

	/**
	 * Instantiates a new heat map plot matrix transform.
	 *
	 * @param parent the parent
	 * @param inputMatrix the input matrix
	 * @param groups the groups
	 * @param rowGroups the row groups
	 * @param countGroups the count groups
	 * @param history the history
	 * @param properties the properties
	 */
	public HeatMapPlotMatrixTransform(MainMatCalcWindow parent,
			AnnotationMatrix inputMatrix, 
			XYSeriesModel groups,
			XYSeriesModel rowGroups,
			CountGroups countGroups,
			List<String> history,
			Properties properties) {
		this(parent, 
				"Create Heat Map Plot", 
				inputMatrix,
				groups,
				rowGroups,
				countGroups,
				history,
				properties);
	}
	
	/**
	 * Instantiates a new heat map plot matrix transform.
	 *
	 * @param parent the parent
	 * @param name the name
	 * @param inputMatrix the input matrix
	 * @param groups the groups
	 * @param rowGroups the row groups
	 * @param countGroups the count groups
	 * @param history the history
	 * @param properties the properties
	 */
	public HeatMapPlotMatrixTransform(ModernWindow parent,
			String name,
			AnnotationMatrix inputMatrix, 
			XYSeriesModel groups,
			XYSeriesModel rowGroups, 
			CountGroups countGroups,
			List<String> history,
			Properties properties) {
		super(parent, name, inputMatrix);
		
		mGroups = groups;
		mRowGroups = rowGroups;
		mCountGroups = countGroups;
		mHistory = history;
		mProperties = properties;
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.apps.matcalc.PlotMatrixTransform#createWindow()
	 */
	@Override
	public ModernWindow createWindow() {
		return new HeatMapPlotWindow(mParent,
				mMatrix,
				mGroups,
				mRowGroups,
				mCountGroups,
				mHistory,
				mProperties);
	}
}
