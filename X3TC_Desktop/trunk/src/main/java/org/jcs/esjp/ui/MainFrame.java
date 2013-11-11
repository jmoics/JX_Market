package org.jcs.esjp.ui;

import java.awt.Container;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.jcs.esjp.model.Sector;

public class MainFrame
    extends JFrame
{
    Container container;

    public MainFrame()
    {
        super("X3 TC");
        container = getContentPane();

        final Structure structure = new Structure();
        Map<Integer, Map<Integer, Sector>> matrix = new HashMap<Integer, Map<Integer, Sector>>();
        try {
            matrix = structure.init();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        final Integer maxX = structure.getMaxX() + 1;
        final Integer maxY = structure.getMaxY() + 1;

        container.setLayout(new GridLayout(maxX, maxY + 1, 2, 2));

        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (matrix.containsKey(i)) {
                    if (matrix.get(i).containsKey(j)) {
                        final SectorPanel secPan = new SectorPanel(matrix.get(i).get(j));
                        container.add(secPan);
                    } else {
                        final SectorPanel secPan = new SectorPanel(null);
                        container.add(secPan);
                    }
                } else {
                    final SectorPanel secPan = new SectorPanel(null);
                    container.add(secPan);
                }
            }
        }
    }
}
