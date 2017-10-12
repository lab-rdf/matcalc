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
package edu.columbia.rdf.matcalc.toolbox.supervised;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jebtk.core.Indexed;
import org.jebtk.core.IndexedInt;
import org.jebtk.core.Properties;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.graphplot.figure.heatmap.legacy.CountGroup;
import org.jebtk.graphplot.figure.heatmap.legacy.CountGroups;
import org.jebtk.graphplot.figure.series.XYSeries;
import org.jebtk.graphplot.figure.series.XYSeriesGroup;
import org.jebtk.graphplot.figure.series.XYSeriesModel;
import org.jebtk.math.MathUtils;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.math.matrix.DoubleMatrix;
import org.jebtk.math.matrix.Matrix;
import org.jebtk.math.matrix.utils.MatrixOperations;
import org.jebtk.math.statistics.FDRType;
import org.jebtk.math.statistics.Statistics;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.Raster32Icon;
import org.jebtk.modern.ribbon.RibbonLargeButton;

import edu.columbia.rdf.matcalc.MainMatCalcWindow;
import edu.columbia.rdf.matcalc.icons.DiffExp32VectorIcon;
import edu.columbia.rdf.matcalc.toolbox.CalcModule;
import edu.columbia.rdf.matcalc.toolbox.core.collapse.CollapseModule;
import edu.columbia.rdf.matcalc.toolbox.core.collapse.CollapseType;
import edu.columbia.rdf.matcalc.toolbox.plot.heatmap.ClusterProperties;
import edu.columbia.rdf.matcalc.toolbox.plot.volcano.VolcanoPlotModule;

// TODO: Auto-generated Javadoc
/**
 * The class OneWayAnovaModule.
 */
public class SupervisedModule extends CalcModule implements ModernClickListener {

	/**
	 * The member parent.
	 */
	private MainMatCalcWindow mParent;

	/* (non-Javadoc)
	 * @see org.abh.lib.NameProperty#getName()
	 */
	@Override
	public String getName() {
		return "Supervised";
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.apps.matcalc.modules.Module#init(edu.columbia.rdf.apps.matcalc.MainMatCalcWindow)
	 */
	@Override
	public void init(MainMatCalcWindow window) {
		mParent = window;

		RibbonLargeButton button = new RibbonLargeButton("Supervised", 
				new Raster32Icon(new DiffExp32VectorIcon()),
				"Supervised Classification",
				"Supervised classification.");
		button.addClickListener(this);

		mParent.getRibbon().getToolbar("Classification").getSection("Classifier").add(button);
	}

	/* (non-Javadoc)
	 * @see org.abh.lib.ui.modern.event.ModernClickListener#clicked(org.abh.lib.ui.modern.event.ModernClickEvent)
	 */
	@Override
	public void clicked(ModernClickEvent e) {
		try {
			classification();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Ttest.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	private void classification() throws IOException, ParseException {
		classification(new ClusterProperties());
	}

	/**
	 * Ttest.
	 *
	 * @param properties the properties
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	private void classification(Properties properties) throws IOException, ParseException {
		DataFrame m = mParent.getCurrentMatrix();

		if (m == null) {
			return;
		}

		XYSeriesModel groups = XYSeriesModel.create(mParent.getGroups());

		if (groups.getCount() == 0) {
			MainMatCalcWindow.createGroupWarningDialog(mParent);

			return;
		}

		XYSeriesModel rowGroups = XYSeriesModel.create(mParent.getRowGroups());

		SupervisedDialog dialog = 
				new SupervisedDialog(mParent, m, mParent.getGroups());

		dialog.setVisible(true);

		if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
			return;
		}

		// We are only interested in the opened matrix
		// without transformations.

		if (dialog.getReset()) {
			mParent.resetHistory();
		}

		XYSeries g1 = dialog.getGroup1(); // new Group("g1");
		XYSeries g2 = dialog.getGroup2(); // new Group("g2");

		double minFold = dialog.getMinFoldChange();
		double alpha = dialog.getMaxP();
		double minZ = dialog.getMinZscore();
		boolean posZ = dialog.getPosZ();
		boolean negZ = dialog.getNegZ();

		int topGenes = dialog.getTopGenes();

		boolean isLog2 = dialog.getIsLog2Transformed();
		boolean log2Data = dialog.getLog2Transform();
		PlotType plotType = dialog.getCreatePlot();

		FDRType fdrType = dialog.getFDRType();

		CollapseType collapseType = dialog.getCollapseType();

		String collapseName = dialog.getCollapseName();

		double classificationAlpha = dialog.getUpDownP(); //0.05;

		TestType testType = dialog.getTest();




		switch(plotType) {
		case VOLCANO:
			VolcanoPlotModule.volcanoPlot(mParent,
					m, 
					alpha,
					TestType.TTEST_UNEQUAL_VARIANCE,
					fdrType, 
					g1, 
					g2, 
					!dialog.getIsLog2Transformed() || dialog.getLog2Transform(), 
					true);

			break;
		default:
			statTest(m, 
					alpha,
					classificationAlpha,
					minFold,
					minZ,
					posZ,
					negZ,
					topGenes,
					collapseType,
					collapseName,
					testType,
					fdrType, 
					g1, 
					g2,
					groups,
					rowGroups,
					isLog2,
					log2Data, 
					plotType,
					properties);
		}



	}

	/**
	 * Ttest.
	 *
	 * @param m the m
	 * @param minExp the min exp
	 * @param alpha the alpha
	 * @param classificationAlpha the classification alpha
	 * @param minFold the min fold
	 * @param minZ the min z
	 * @param posZ the pos z
	 * @param negZ the neg z
	 * @param topGenes the top genes
	 * @param collapseType the collapse type
	 * @param rowAnnotation the row annotation
	 * @param fdrType the fdr type
	 * @param g1 the g1
	 * @param g2 the g2
	 * @param groups the groups
	 * @param rowGroups the row groups
	 * @param isLog2Data the is log2 data
	 * @param log2Data the log2 data
	 * @param equalVariance the equal variance
	 * @param plotType the plot
	 * @param properties the properties
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public void statTest(DataFrame m,
			double alpha,
			double classificationAlpha,
			double minFold,
			double minZ,
			boolean posZ,
			boolean negZ,
			int topGenes,
			CollapseType collapseType,
			String rowAnnotation,
			TestType test,
			FDRType fdrType,
			XYSeries g1, 
			XYSeries g2,
			XYSeriesModel groups,
			XYSeriesModel rowGroups,
			boolean isLog2Data,
			boolean log2Data,
			PlotType plotType,
			Properties properties) throws IOException, ParseException {

		XYSeriesGroup comparisonGroups = new XYSeriesGroup();
		comparisonGroups.add(g1);
		comparisonGroups.add(g2);

		//DataFrame colFilteredM = 
		//		mParent.addToHistory("Extract grouped columns", 
		//				AnnotatableMatrix.copyInnerColumns(m, comparisonGroups));


		DataFrame colFilteredM = m;

		//
		// Remove bad gene symbols
		//

		//DataFrame mCleaned = AnnotatableMatrix.copyRows(mColumnFiltered, 
		//		rowAnnotationName, 
		//		"^---$", 
		//		false);

		//mParent.addToHistory("Filter Bad Gene Symbols", mCleaned);

		//
		// Log the matrix
		//

		DataFrame log2M;

		if (log2Data) {
			log2M = mParent.addToHistory("log2(1 + data)",
					MatrixOperations.log2(MatrixOperations.add(colFilteredM, 1)));
		} else {
			log2M = colFilteredM;
		}

		//
		// P-values
		//

		List<Double> pValues = getP(log2M, g1, g2,test);

		DataFrame pValuesM = new DataFrame(log2M);

		pValuesM.setNumRowAnnotations("P-value", pValues);

		//System.err.println("p " + pValues);
		//System.err.println("p2 " + Arrays.toString(pValuesM.getRowAnnotationValues("P-value")));

		// Set the p-values of genes with bad names to NaN so they can be
		// excluded from analysis
		//DataFrame.setAnnotation(mpvalues,
		//		"P-value",
		//		DataFrame.matchRows(mpvalues, rowAnnotation, BLANK_ROW_REGEX),
		//		Double.NaN);

		mParent.addToHistory("Add P-values", pValuesM);

		//
		// Fold Changes
		//

		List<Double> foldChanges;

		if (isLog2Data || log2Data) {
			foldChanges = DoubleMatrix.logFoldChange(pValuesM, g1, g2);
		} else {
			foldChanges = DoubleMatrix.foldChange(pValuesM, g1, g2);
		}

		// filter by fold changes
		// filter by fdr

		String name = isLog2Data || log2Data ? "Log2 Fold Change" : "Fold Change";

		DataFrame foldChangesM = new DataFrame(pValuesM);
		foldChangesM.setNumRowAnnotations(name, foldChanges);

		mParent.addToHistory(name, foldChangesM);


		//
		// Group means
		//

		DataFrame meansM = new DataFrame(foldChangesM);

		meansM.setNumRowAnnotations(g1.getName() + " mean", 
				DoubleMatrix.means(foldChangesM, g1));
		meansM.setNumRowAnnotations(g2.getName() + " mean", 
				DoubleMatrix.means(foldChangesM, g2));

		mParent.addToHistory("Group Means", meansM);

		//
		// Fold change filter
		//

		double posMinFold = minFold;
		double negMinFold = posMinFold;

		if (minFold > 0) {
			if (isLog2Data || log2Data) {
				negMinFold = -negMinFold;
			} else {
				negMinFold = 1.0 / negMinFold;
			}
		}

		List<Indexed<Integer, Double>> pFoldIndices = 
				Statistics.outOfRange(foldChanges, negMinFold, posMinFold);

		name = isLog2Data || log2Data ? "Log2 Fold Change Filter" : "Fold Change Filter";

		DataFrame foldFilterM = DataFrame.copyRows(meansM, 
				IndexedInt.indices(pFoldIndices));



		mParent.addToHistory(name, foldFilterM);

		//
		// Collapse rows
		//

		DataFrame collapsedM = CollapseModule.collapse(foldFilterM,
				rowAnnotation,
				g1,
				g2,
				collapseType,
				mParent);

		double[] fdr = Statistics.fdr(collapsedM.getRowAnnotationValues("P-value"), 
				fdrType);

		DataFrame mfdr = new DataFrame(collapsedM);
		mfdr.setNumRowAnnotations("FDR", fdr);

		mParent.addToHistory("FDR", mfdr);

		//DataFrame mfdr = addFlowItem("False discovery rate", 
		//		new RowDataFrameView(mcollapsed, 
		//				"FDR", 
		//				ArrayUtils.toObjects(fdr)));



		// filter by fdr
		List<Indexed<Integer, Double>> pValueIndices = 
				Statistics.threshold(fdr, alpha);

		DataFrame fdrFilteredM = 
				DataFrame.copyRows(mfdr, IndexedInt.indices(pValueIndices));
		mParent.addToHistory("False discovery filter", fdrFilteredM);

		//DataFrame mfdrfiltered = addFlowItem("False discovery filter", 
		//		new RowFilterMatrixView(mfdr, 
		//				IndexedValueInt.indices(pValueIndices)));


		List<Double> zscores = 
				DoubleMatrix.diffGroupZScores(fdrFilteredM, g1, g2);

		DataFrame zscoresM = new DataFrame(fdrFilteredM);

		zscoresM.setNumRowAnnotations("Z-score", zscores);
		mParent.addToHistory("Z-score", zscoresM);


		//DataFrame mzscores = addFlowItem("Add row z-scores", 
		//		new RowDataFrameView(mfdrfiltered, 
		//				"Z-score", 
		//				ArrayUtils.toObjects(zscores)));

		// Lets give a default classification to each row based on a p-value of 0.05 and
		// a zscore > 1

		List<String> classifications = new ArrayList<String>();

		Matrix im = zscoresM.getMatrix();

		for (int i = 0; i < im.getRowCount(); ++i) {
			String classification = "not_expressed";

			if (MatrixOperations.sumRow(im, i) > 0) {
				classification = "not_moving";
			}

			double zscore = zscoresM.getRowAnnotationValue("Z-score", i);
			double p = zscoresM.getRowAnnotationValue("FDR", i);

			//if (p <= 0.05) {
			if (p <= classificationAlpha) {	
				if (zscore > 0) {
					classification = "up";
				} else if (zscore < 0) {
					classification = "down";
				} else {
					// do nothing
				}
			}

			classifications.add(classification);
		}

		String comparison = g1.getName() + "vs" + g2.getName() + " (p <= " + classificationAlpha + ")";

		DataFrame classM = new DataFrame(zscoresM);

		classM.setTextRowAnnotations(comparison, classifications);

		mParent.addToHistory("Add row classification", classM);


		//DataFrame mclassification = addFlowItem("Add row classification", 
		//		new RowDataFrameView(mzscores, 
		//				comparison, 
		//				ArrayUtils.toObjects(classifications)));

		List<Indexed<Integer, Double>> zscoresIndexed = 
				IndexedInt.index(zscores);

		List<Indexed<Integer, Double>> posZScores;

		if (posZ) {
			posZScores = CollectionUtils.reverseSort(CollectionUtils.subList(zscoresIndexed, 
					MathUtils.ge(zscoresIndexed, minZ)));
		} else {
			posZScores = Collections.emptyList(); //new ArrayList<Indexed<Integer, Double>>();
		}

		List<Indexed<Integer, Double>> negZScores;

		if (negZ) {
			negZScores = CollectionUtils.sort(CollectionUtils.subList(zscoresIndexed, 
					MathUtils.lt(zscoresIndexed, -minZ)));
		} else {
			negZScores = Collections.emptyList(); //new ArrayList<Indexed<Integer, Double>>();
		}

		// Filter for top genes if necessary

		List<Integer> ui = Indexed.indices(posZScores);
		List<Integer> li = Indexed.indices(negZScores);

		if (topGenes != -1) {
			ui = CollectionUtils.head(ui, topGenes);
			li = CollectionUtils.head(li, topGenes);
		}


		// Now make a list of the new zscores in the correct order,
		// positive decreasing, negative, decreasing
		//List<IndexedValue<Integer, Double>> sortedZscores = 
		//		CollectionUtils.append(posZScores, negZScores);

		// Put the zscores in order

		List<Integer> indices = CollectionUtils.append(ui, li); // IndexedValue.indices(sortedZscores);

		DataFrame mDeltaSorted = 
				DataFrame.copyRows(classM, indices);

		mParent.addToHistory("Sort by row z-score", mDeltaSorted);

		//DataFrame mDeltaSorted = addFlowItem("Sort by row z-score", 
		//		new RowFilterMatrixView(mclassification, indices));

		DataFrame mNormalized = 
				MatrixOperations.groupZScore(mDeltaSorted, comparisonGroups);

		mParent.addToHistory("Normalize expression within groups", mNormalized);

		//DataFrame mNormalized = addFlowItem("Normalize expression within groups", 
		//		new GroupZScoreMatrixView(mDeltaSorted, groups));



		//DataFrame mMinMax = addFlowItem("Min/max threshold", 
		//		"min: " + Plot.MIN_STD + ", max: "+ Plot.MAX_STD,
		//		new MinMaxBoundedMatrixView(mNormalized, 
		//				Plot.MIN_STD, 
		//				Plot.MAX_STD));

		//DataFrame mStandardized = 
		//		addFlowItem("Row normalize", new RowNormalizedMatrixView(mMinMax));

		List<String> history = mParent.getTransformationHistory();

		CountGroups countGroups = new CountGroups()
				.add(new CountGroup("up", 0, ui.size() - 1))
				.add(new CountGroup("down", ui.size(), indices.size() - 1));

		if (plotType != PlotType.NONE) {
			mParent.addToHistory(new HeatMapMatrixTransform(mParent, 
					mNormalized, 
					groups,
					comparisonGroups,
					rowGroups,
					countGroups,
					history, 
					properties));
		}

		// Add a reference at the end so that it is easy for users to find
		// the matrix they probably want the most
		mParent.addToHistory("Results", mDeltaSorted);
	}

	public static List<Double> getP(DataFrame m, 
			XYSeries g1, 
			XYSeries g2,
			TestType test) {
		List<Double> pValues;

		switch (test) {
		case MANN_WHITNEY:
			pValues = MatrixOperations.mannWhitney(m, g1, g2);
			break;
		default:
			pValues = MatrixOperations.tTest(m, 
					g1, 
					g2, 
					test == TestType.TTEST_EQUAL_VARIANCE);
		}

		return pValues;
	}
}
