package org.jcs.esjp.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;

import org.jcs.esjp.model.Sector;

public class MainFrame
    extends JFrame
{
    int x;
    int y;

    public MainFrame()
    {
        super("X3 TC");
        final JDesktopPane container = new JDesktopPane()
        {
            @Override
            public Dimension getPreferredSize()
            {
                return new Dimension(5000, 5000);
            }
        };
        x = container.getX();
        y = container.getY();
        container.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(final MouseEvent me)
            {
                x = me.getX();
                y = me.getY();
            }
        });
        container.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(final MouseEvent me)
            {
                me.translatePoint(me.getComponent().getLocation().x - x, me.getComponent().getLocation().y - y);
                container.setLocation(me.getX(), me.getY());
            }

        });

        setContentPane(container);
        // Make dragging a little faster but perhaps uglier.
        container.setDragMode(JDesktopPane.LIVE_DRAG_MODE);

        final Structure structure = new Structure();
        Map<Integer, Map<Integer, Sector>> matrix = new HashMap<Integer, Map<Integer, Sector>>();
        try {
            matrix = structure.init();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        final Integer maxX = structure.getMaxX() + 1;
        final Integer maxY = structure.getMaxY() + 1;

        final GridLayout layout = new GridLayout(maxX, maxY, 12, 12);
        container.setLayout(layout);

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
