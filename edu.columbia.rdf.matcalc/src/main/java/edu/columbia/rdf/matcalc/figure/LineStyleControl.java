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

import java.text.ParseException;

import javax.swing.Box;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.graphplot.figure.properties.LineProperties;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.CheckBox;
import org.jebtk.modern.button.ModernCheckBox;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.color.ColorSwatchButton;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.spinner.ModernCompactSpinner;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernWindow;

// TODO: Auto-generated Javadoc
/**
 * The class LineStyleControl.
 */
public class LineStyleControl extends HBox implements ModernClickListener {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The member check box.
	 */
	private CheckBox mCheckBox;
	
	/**
	 * The color button.
	 */
	private ColorSwatchButton mColorButton;

	/**
	 * The member line style.
	 */
	private LineProperties mLineStyle;

	/**
	 * The dashed button.
	 */
	private StokeStyleButton mStrokeButton;
	
	/** The m text width. */
	private ModernCompactSpinner mTextWidth = 
			new ModernCompactSpinner(1, 1000, 1);
	
	/**
	 * Instantiates a new line style control.
	 *
	 * @param parent the parent
	 * @param lineStyle the line style
	 */
	public LineStyleControl(ModernWindow parent,
			LineProperties lineStyle) {
		this(parent, "Line", lineStyle);
	}
	
	/**
	 * Instantiates a new line style control.
	 *
	 * @param parent the parent
	 * @param name the name
	 * @param lineStyle the line style
	 */
	public LineStyleControl(ModernWindow parent,
			String name, 
			LineProperties lineStyle) {

		mLineStyle = lineStyle;
		
		mCheckBox = new ModernCheckBox(name);
		mCheckBox.setSelected(lineStyle.getVisible());
		
		mColorButton = new ColorSwatchButton(parent, lineStyle.getColor());
		
		mStrokeButton = new StokeStyleButton(parent, lineStyle.getStrokeStyle());
		
		add(mCheckBox);
		add(Box.createHorizontalGlue());
		add(mTextWidth);
		add(ModernPanel.createHGap());
		add(mColorButton);
		add(ModernPanel.createHGap());
		add(mStrokeButton);
		
		UI.setSize(mTextWidth, ModernWidget.TINY_SIZE);
		
		mTextWidth.setValue(lineStyle.getWidth());
		
		mCheckBox.addClickListener(this);
		mColorButton.addClickListener(this);
		mStrokeButton.addClickListener(this);
		
		mLineStyle.addChangeListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent e) {
				mColorButton.setSelectedColor(mLineStyle.getColor());
			}});
		
		mTextWidth.addChangeListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent e) {
				try {
					setStroke();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}});
		
		/*
		mTextWidth.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						setStroke();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}});
			*/
	}
	
	/**
	 * Sets the stroke.
	 *
	 * @throws ParseException the parse exception
	 */
	public void setStroke() throws ParseException {
		mLineStyle.setColor(mColorButton.getSelectedColor());
		mLineStyle.setVisible(mCheckBox.isSelected());
		mLineStyle.setStroke(mStrokeButton.getSelectedStroke(), 
				mTextWidth.getIntValue());
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.event.ModernClickListener#clicked(org.abh.common.ui.ui.event.ModernClickEvent)
	 */
	@Override
	public void clicked(ModernClickEvent e) {
		try {
			setStroke();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

}
