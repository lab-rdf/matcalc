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
package edu.columbia.rdf.matcalc.toolbox.stats.ttest.legacy;

import java.text.ParseException;
import java.util.List;

import javax.swing.Box;

import org.jebtk.core.text.TextUtils;
import org.jebtk.graphplot.figure.series.XYSeries;
import org.jebtk.graphplot.figure.series.XYSeriesGroup;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.math.matrix.MatrixGroup;
import org.jebtk.math.statistics.FDRType;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.CheckBox;
import org.jebtk.modern.button.ModernCheckBox;
import org.jebtk.modern.button.ModernCheckSwitch;
import org.jebtk.modern.dialog.ModernDialogMultiCardWindow;
import org.jebtk.modern.dialog.ModernDialogTaskType;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.panel.HExpandBox;
import org.jebtk.modern.panel.VBox;
import org.jebtk.modern.spinner.ModernCompactSpinner;
import org.jebtk.modern.widget.ModernTwoStateWidget;
import org.jebtk.modern.window.ModernWindow;
import org.jebtk.modern.window.WindowWidgetFocusEvents;

import edu.columbia.rdf.matcalc.FDRPanel;
import edu.columbia.rdf.matcalc.FilterPanel;
import edu.columbia.rdf.matcalc.GroupPanel;
import edu.columbia.rdf.matcalc.figure.PlotConstants;
import edu.columbia.rdf.matcalc.toolbox.core.collapse.CollapsePanel;
import edu.columbia.rdf.matcalc.toolbox.core.collapse.CollapseType;

// TODO: Auto-generated Javadoc
/**
 * The class TTestDialog.
 */
public class TTestDialog extends ModernDialogMultiCardWindow {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The member check is log2.
	 */
	private ModernTwoStateWidget mCheckIsLog2 = 
			new ModernCheckSwitch(PlotConstants.MENU_IS_LOG_TRANSFORMED);

	/**
	 * The member check log2.
	 */
	private ModernTwoStateWidget mCheckLog2 = 
			new ModernCheckSwitch(PlotConstants.MENU_LOG_TRANSFORM);

	/**
	 * The member groups.
	 */
	private XYSeriesGroup mGroups;
	
	/**
	 * The member check plot.
	 */
	private CheckBox mCheckPlot = 
			new ModernCheckBox(PlotConstants.MENU_CREATE_PLOT, true);
	

	/**
	 * The check reset.
	 */
	private CheckBox checkReset = 
			new ModernCheckBox(PlotConstants.MENU_RESET_HISTORY);

	/**
	 * The member group panel.
	 */
	private GroupPanel mGroupPanel;
	
	/**
	 * The member collapse panel.
	 */
	private CollapsePanel mCollapsePanel;
	
	/**
	 * The member fdr panel.
	 */
	private FDRPanel mFdrPanel = new FDRPanel();
	
	/**
	 * The member filter panel.
	 */
	private FilterPanel mFilterPanel = new FilterPanel();

	/**
	 * The member matrix.
	 */
	private AnnotationMatrix mMatrix;
	
	
	/**
	 * The member expression field.
	 */
	private ModernCompactSpinner mExpressionField = 
			new ModernCompactSpinner(1, 10000, 1);
	
	/** The m up down P field. */
	private ModernCompactSpinner mUpDownPField = 
			new ModernCompactSpinner(0, 1, 0.05, 0.01);


	/**
	 * Instantiates a new t test dialog.
	 *
	 * @param parent the parent
	 * @param matrix the matrix
	 * @param groups the groups
	 */
	public TTestDialog(ModernWindow parent, 
			AnnotationMatrix matrix,
			XYSeriesGroup groups) {
		super(parent, 
				"T-Test Differential Expression",
				"matcalc.ttest.help.url",
				ModernDialogTaskType.OK_CANCEL);
		
		mGroups = groups;
		mMatrix = matrix;
		
		createUi();

		
		setup();
	}

	/**
	 * Setup.
	 */
	private void setup() {
		addWindowListener(new WindowWidgetFocusEvents(mOkButton));
		
		setSize(640, 420);
		
		UI.centerWindowToScreen(this);
	}
	
	

	/**
	 * Creates the ui.
	 */
	private final void createUi() {
		Box box = VBox.create();
		
		//sectionHeader("Data Transform", box);
		
		box.add(mCheckIsLog2);
		box.add(UI.createVGap(5));
		box.add(mCheckIsLog2);
		box.add(UI.createVGap(5));
		box.add(mCheckLog2);
		box.add(UI.createVGap(5));
		
		box.add(new HExpandBox("Minimum Expression", mExpressionField));

		//midSectionHeader("Groups", box);
		
		box.add(UI.createVGap(20));
		
		mGroupPanel = new GroupPanel(mGroups);
		
		box.add(mGroupPanel);
		
		addTab("Input", box);
		
		box = VBox.create();
		
		//sectionHeader("Filter Options", box);
		box.add(mFilterPanel);
		
		addTab("Filter", box);
		
		box = VBox.create();
		
		//sectionHeader("Collapse Options", box);
		mCollapsePanel = new CollapsePanel(mMatrix, mGroups);
		
		box.add(mCollapsePanel);
		
		addTab("Collapse", box);
		
		box = VBox.create();
		
		//sectionHeader("Multiple Comparison Options", box);
		box.add(mFdrPanel);
		
		//midSectionHeader("Differential Expression Options", box);
		
		box.add(UI.createVGap(5));
		
		box.add(new HExpandBox("Up/down p-value", mUpDownPField));
		
		addTab("Statistics", box);

		mTabsModel.changeTab(0);
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.dialog.ModernDialogTaskWindow#clicked(org.abh.common.ui.event.ModernClickEvent)
	 */
	@Override
	public void clicked(ModernClickEvent e) {
		if (e.getSource().equals(mOkButton)) {
			
			// Check we have enough samples
			
			XYSeries g1 = getGroup1();
			
			List<Integer> i1 = MatrixGroup.findColumnIndices(mMatrix, g1);
			
			if (i1.size() < 3) {
				ModernMessageDialog.createWarningDialog(mParent, 
						"There must be at least 3 samples in " + g1.getName());
				
				return;
			}
			
			XYSeries g2 = getGroup2();
			
			List<Integer> i2 = MatrixGroup.findColumnIndices(mMatrix, g2);
			
			if (i2.size() < 3) {
				ModernMessageDialog.createWarningDialog(mParent, 
						"There must be at least 3 samples in " + g2.getName());
				
				return;
			}
			
			
		}
		
		super.clicked(e);
	}

	/**
	 * Gets the group1.
	 *
	 * @return the group1
	 */
	public XYSeries getGroup1() {
		return mGroupPanel.getGroup1();
	}
	
	/**
	 * Gets the group2.
	 *
	 * @return the group2
	 */
	public XYSeries getGroup2() {
		return mGroupPanel.getGroup2();
	}
	
	/**
	 * Gets the equal variance.
	 *
	 * @return the equal variance
	 */
	public boolean getEqualVariance() {
		return mGroupPanel.getEqualVariance();
	}

	/**
	 * Gets the max p.
	 *
	 * @return the max p
	 */
	public double getMaxP() {
		return mFdrPanel.getMaxP();
	}
	
	/**
	 * Gets the up down P.
	 *
	 * @return the up down P
	 */
	public double getUpDownP() {
		return mUpDownPField.getValue();
	}
	
	/**
	 * Gets the FDR type.
	 *
	 * @return the FDR type
	 */
	public FDRType getFDRType() {
		return mFdrPanel.getFDRType();
	}
	
	/**
	 * Gets the creates the plot.
	 *
	 * @return the creates the plot
	 */
	public boolean getCreatePlot() {
		return mCheckPlot.isSelected();
	}

	/**
	 * Gets the log2 transform.
	 *
	 * @return the log2 transform
	 */
	public boolean getLog2Transform() {
		return mCheckLog2.isSelected();
	}
	
	/**
	 * Gets the checks if is log2 transformed.
	 *
	 * @return the checks if is log2 transformed
	 */
	public boolean getIsLog2Transformed() {
		return mCheckIsLog2.isSelected();
	}
	
	/**
	 * Gets the min exp.
	 *
	 * @return the min exp
	 * @throws ParseException the parse exception
	 */
	public double getMinExp() throws ParseException {
		return TextUtils.parseDouble(mExpressionField.getText()); //TextUtils.parseDouble(expressionField.getText());
	}
	
	/**
	 * Gets the min zscore.
	 *
	 * @return the min zscore
	 */
	public double getMinZscore() {
		return mFilterPanel.getMinZscore(); //TextUtils.parseDouble(mZScoreField.getText());
	}
	
	/**
	 * Gets the top genes.
	 *
	 * @return the top genes
	 */
	public int getTopGenes() {
		return mFilterPanel.getTopGenes(); //TextUtils.parseDouble(mZScoreField.getText());
	}
	
	/**
	 * Gets the pos z.
	 *
	 * @return the pos z
	 */
	public boolean getPosZ() {
		return mFilterPanel.getPosZ();
	}
	
	/**
	 * Gets the neg z.
	 *
	 * @return the neg z
	 */
	public boolean getNegZ() {
		return mFilterPanel.getNegZ();
	}
	
	/**
	 * Gets the reset.
	 *
	 * @return the reset
	 */
	public boolean getReset() {
		return checkReset.isSelected();
	}

	/**
	 * Gets the collapse type.
	 *
	 * @return the collapse type
	 */
	public CollapseType getCollapseType() {
		return mCollapsePanel.getCollapseType();
	}

	/**
	 * Gets the collapse name.
	 *
	 * @return the collapse name
	 */
	public String getCollapseName() {
		return mCollapsePanel.getCollapseName();
	}

	/**
	 * Gets the min fold change.
	 *
	 * @return the min fold change
	 */
	public double getMinFoldChange() {
		return mFilterPanel.getMinFoldChange();
	}
}
