package org.jcs.esjp.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jcs.esjp.model.Sector;
import org.jcs.esjp.util.Settings;

public class SectorPanel
    extends JPanel
{
    private Image bgImage;

    public SectorPanel(final Sector _sector) {
        super();
        this.setOpaque(false);

        setSize(90, 85);
        if (_sector != null && _sector.getName() != null) {

            final Image image = new ImageIcon(Settings.RACE2COLOR.get(_sector.getRace().getName())).getImage();
            setBackgroundImage(image);

            final Font font = new Font("Tahoma", Font.ITALIC, 7);
            final JLabel txtLb = new JLabel();
            txtLb.setOpaque(false);
            txtLb.setForeground(new Color(255, 255, 255));
            txtLb.setFont(font);
            txtLb.setText(_sector.getName());

            add(txtLb);
            //setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
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
}
