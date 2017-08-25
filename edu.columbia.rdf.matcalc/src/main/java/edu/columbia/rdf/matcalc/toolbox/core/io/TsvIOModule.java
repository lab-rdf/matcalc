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
package edu.columbia.rdf.matcalc.toolbox.core.io;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.math.matrix.DynamicMatrixParser;
import org.jebtk.math.matrix.MixedMatrixParser;
import org.jebtk.modern.io.GuiFileExtFilter;
import org.jebtk.modern.io.TsvGuiFileFilter;

import edu.columbia.rdf.matcalc.MainMatCalcWindow;


// TODO: Auto-generated Javadoc
/**
 * Allow users to open and save Text files.
 *
 * @author Antony Holmes Holmes
 */
public class TsvIOModule extends IOModule  {

	/** The Constant TXT_FILTER. */
	private static final GuiFileExtFilter TSV_FILTER = new TsvGuiFileFilter();


	/**
	 * Instantiates a new tsv IO module.
	 */
	public TsvIOModule() {
		registerFileOpenType(TSV_FILTER);
		registerFileSaveType(TSV_FILTER);
	}

	/* (non-Javadoc)
	 * @see org.abh.lib.NameProperty#getName()
	 */
	@Override
	public String getName() {
		return "TSV IO";
	}

	/* (non-Javadoc)
	 * @see org.matcalc.toolbox.CalcModule#openFile(org.matcalc.MainMatCalcWindow, java.nio.file.Path, boolean, int)
	 */
	@Override
	public AnnotationMatrix autoOpenFile(final MainMatCalcWindow window,
			final Path file,
			boolean hasHeader,
			List<String> skipMatches,
			int rowAnnotations,
			String delimiter) throws IOException {

		if (hasHeader) {
			return new MixedMatrixParser(hasHeader, 
					skipMatches, 
					rowAnnotations, 
					delimiter).parse(file);
		} else {
			return new DynamicMatrixParser(hasHeader, 
					skipMatches, 
					rowAnnotations, 
					delimiter).parse(file); //return AnnotationMatrix.parseDynamicMatrix(file, hasHeader, rowAnnotations, '\t');
		}
	}


	/* (non-Javadoc)
	 * @see org.matcalc.toolbox.CalcModule#saveFile(org.matcalc.MainMatCalcWindow, java.nio.file.Path, org.abh.common.math.matrix.AnnotationMatrix)
	 */
	@Override
	public boolean saveFile(final MainMatCalcWindow window,
			final Path file, 
			final AnnotationMatrix m) throws IOException {
		AnnotationMatrix.writeAnnotationMatrix(m, file);

		return true;
	}
}