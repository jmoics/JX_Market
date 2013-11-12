package org.jcs.esjp.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import org.jcs.esjp.model.Sector;

public class MapPanel
    extends JPanel
    implements Scrollable,
    MouseMotionListener,
    MouseListener
{
    int x;
    int y;
    private final int maxUnitIncrement = 30;

    public MapPanel()
    {
        // Make dragging a little faster but perhaps uglier.
        buildSectors();
    }

    protected void buildSectors() {
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
        setLayout(layout);

        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (matrix.containsKey(i)) {
                    if (matrix.get(i).containsKey(j)) {
                        final SectorPanel secPan = new SectorPanel(matrix.get(i).get(j));
                        add(secPan);
                    } else {
                        final SectorPanel secPan = new SectorPanel(null);
                        add(secPan);
                    }
                } else {
                    final SectorPanel secPan = new SectorPanel(null);
                    add(secPan);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(3000, 3000);
    }

    @Override
    public void mouseDragged(final MouseEvent e)
    {
        e.translatePoint(e.getComponent().getLocation().x - x, e.getComponent().getLocation().y - y);
        setLocation(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(final MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Dimension getPreferredScrollableViewportSize()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getScrollableUnitIncrement(final Rectangle visibleRect,
                                          final int orientation,
                                          final int direction)
    {
      //Get the current position.
        int currentPosition = 0;
        if (orientation == SwingConstants.HORIZONTAL) {
            currentPosition = visibleRect.x;
        } else {
            currentPosition = visibleRect.y;
        }

        //Return the number of pixels between currentPosition
        //and the nearest tick mark in the indicated direction.
        if (direction < 0) {
            final int newPosition = currentPosition -
                             (currentPosition / maxUnitIncrement)
                              * maxUnitIncrement;
            return (newPosition == 0) ? maxUnitIncrement : newPosition;
        } else {
            return ((currentPosition / maxUnitIncrement) + 1)
                   * maxUnitIncrement
                   - currentPosition;
        }
    }

    @Override
    public int getScrollableBlockIncrement(final Rectangle visibleRect,
                                           final int orientation,
                                           final int direction)
    {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - maxUnitIncrement;
        } else {
            return visibleRect.height - maxUnitIncrement;
        }
    }

    @Override
    public boolean getScrollableTracksViewportWidth()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void mouseClicked(final MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(final MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(final MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(final MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(final MouseEvent e)
    {
        // TODO Auto-generated method stub

    }
}
