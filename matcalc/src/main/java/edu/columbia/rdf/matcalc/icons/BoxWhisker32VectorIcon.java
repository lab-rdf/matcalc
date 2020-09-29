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
package edu.columbia.rdf.matcalc.icons;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jebtk.core.Props;
import org.jebtk.modern.graphics.icons.ModernVectorIcon;

/**
 * The class BoxWhisker32VectorIcon.
 */
public class BoxWhisker32VectorIcon extends ModernVectorIcon {

  /**
   * The constant WIDTH.
   */
  private static final int WIDTH = 15;

  /**
   * The constant HEIGHT.
   */
  private static final int HEIGHT = 24;

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.lib.ui.modern.icons.ModernIcon#drawForeground(java.awt.Graphics2D,
   * java.awt.Rectangle)
   */
  @Override
<<<<<<< HEAD:matcalc/src/main/java/edu/columbia/rdf/matcalc/icons/BoxWhisker32VectorIcon.java
  public void drawIcon(Graphics2D g2, int x, int y, int w, int h, Props params) {
=======
  public void drawIcon(Graphics2D g2,
      int x,
      int y,
      int w,
      int h,
      Props props) {
>>>>>>> 0c6b302bf0a6d2eac18dbec9d0d2195af92c4605:edu.columbia.rdf.matcalc/src/main/java/edu/columbia/rdf/matcalc/icons/BoxWhisker32VectorIcon.java

    int x1 = x + (w - WIDTH) / 2;
    int y1 = y + (h - HEIGHT) / 2;

    int c = x + w / 2;

    g2.setColor(Color.BLACK);

    g2.drawLine(x1, y1, x1 + WIDTH, y1);

    g2.drawLine(c, y1, c, y1 + HEIGHT);

    g2.drawLine(x1, y1 + HEIGHT, x1 + WIDTH, y1 + HEIGHT);

    g2.setColor(Color.WHITE);

    c = y + (h - WIDTH) / 2;

    g2.fillRect(x1, c, WIDTH, WIDTH);

    g2.setColor(Color.BLACK);

    g2.drawRect(x1, c, WIDTH, WIDTH);

    c = y + h / 2;

    g2.drawLine(x1, y1 + c, x1 + WIDTH, y1 + c);
  }
}
