package org.jcs.esjp.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jcs.esjp.model.Sector;
import org.jcs.esjp.util.Settings;

public class SectorPanel
    extends JPanel
{
    public SectorPanel(final Sector _sector) {
        //setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        setSize(90, 85);
        if (_sector != null && _sector.getName() != null) {
            setLayout(new BorderLayout());
            final JLabel label = new JLabel(new ImageIcon(Settings.RACE2COLOR.get(_sector.getRace().getName())));
            add(label);

            label.setLayout(new FlowLayout());

            final Label txtLb = new Label();
            final Font font = new Font("Tahoma", Font.ITALIC, 7);
            txtLb.setFont(font);
            txtLb.setText(_sector.getName());

            label.add(txtLb);

            //setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
    }
}
