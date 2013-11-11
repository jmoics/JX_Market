package org.jcs.esjp.ui;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jcs.esjp.model.Sector;

public class SectorPanel
    extends JPanel
{
    public SectorPanel(final Sector _sector) {
        //setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        setSize(85, 85);
        final JLabel label = new JLabel(new ImageIcon("src/main/resources/images/argon.png"));
        final Font font = new Font("Tahoma", Font.ITALIC, 9);
        label.setFont(font);
        //label.setText(_sector != null ? _sector.getName() : "Empty");

        //setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(label);
    }
}
