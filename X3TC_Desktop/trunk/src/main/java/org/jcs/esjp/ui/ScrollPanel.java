package org.jcs.esjp.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ScrollPanel extends JPanel
{
    public ScrollPanel() {
        setBackground(new Color(53, 66, 90));

        //Set up the scroll pane.
        final MapPanel mappan = new MapPanel();
        final JScrollPane scrollPane = new JScrollPane(mappan);
        scrollPane.setPreferredSize(new Dimension(1920, 980));
        scrollPane.setViewportBorder(
                BorderFactory.createLineBorder(Color.black));

        //Put it in this panel.
        add(scrollPane);
        //setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

}
