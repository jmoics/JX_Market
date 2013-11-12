package org.jcs.esjp.ui;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class MainFrame
    extends JFrame
{
    public MainFrame()
    {
        final JComponent newContentPane = new ScrollPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        setContentPane(newContentPane);
    }
}
