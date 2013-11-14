package org.jcs.esjp.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

import org.jcs.esjp.model.Sector;
import org.jcs.esjp.util.Settings;

public class SectorPanel
    extends JDesktopPane implements MouseInputListener
{
    private Image bgImage;
    private final Sector sector;

    public SectorPanel(final Sector _sector) {
        super();
        sector = _sector;
        setOpaque(false);

        setSize(85, 85);
        setLayout(null);
        setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        if (_sector != null && _sector.getName() != null) {
            final Image image = new ImageIcon(Settings.RACE2COLOR.get(_sector.getRace().getName())).getImage();
            setBackgroundImage(image);

            final Font font = new Font("Tahoma", Font.BOLD, 8);

            final JLabel txtLb = new JLabel("<html>" + _sector.getName() + "</html>");
            final Insets insets = getInsets();
            final Dimension size = txtLb.getPreferredSize();
            txtLb.setBounds(5 + insets.left, 45 + insets.top, 80, size.height * 2);
            txtLb.setOpaque(false);
            txtLb.setForeground(new Color(255, 255, 255));
            txtLb.setFont(font);

            addMouseListener(this);
            add(txtLb);
            //setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
    }

    public Sector getSector()
    {
        return this.sector;
    }

    public void setBackgroundImage(final Image bgImage) {
        this.bgImage = bgImage;
       }

    @Override
    public void paint(final Graphics g)
    {
        // Pintamos la imagen de fondo...
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, null);
        }
        // Y pintamos el resto de cosas que pueda tener el panel
        super.paint(g);

    }

    @Override
    public void mouseClicked(final MouseEvent e)
    {
        if (e.getClickCount() == 2) {
            final SectorDataFrame intFrame = new SectorDataFrame(((SectorPanel) e.getSource()).getSector());
            intFrame.setSize(new Dimension(800, 950));
            intFrame.setVisible(true);
        }
    }

    @Override
    public void mousePressed(final MouseEvent e)
    {
        // TODO Auto-generated method stub

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

    @Override
    public void mouseDragged(final MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(final MouseEvent e)
    {
        // TODO Auto-generated method stub

    }
}
