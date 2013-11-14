package org.jcs.esjp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class ScrollPanel extends JPanel
{
    public ScrollPanel() {
        super(new BorderLayout());
        setBackground(new Color(53, 66, 90));

        final JToolBar toolBar = new JToolBar("Buscar");
        toolBar.setBackground(new Color(53, 66, 90));
        toolBar.setBorderPainted(false);
        addButtons(toolBar);

        //Set up the scroll pane.
        final MapPanel mappan = new MapPanel();
        mappan.getComponents();

        final JScrollPane scrollPane = new JScrollPane(mappan);
        scrollPane.setPreferredSize(new Dimension(1920, 980));
        scrollPane.setViewportBorder(
                BorderFactory.createLineBorder(Color.black));

        //Put it in this panel.
        add(toolBar, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
        //setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    protected void addButtons(final JToolBar toolBar) {
        JButton button = null;

        //first button
        button = makeNavigationButton("src/main/resources/images/data/search2.png",
                                      "BUSCAR",
                                      "Buscar");
        toolBar.add(button);
    }

    protected JButton makeNavigationButton(final String _imagePath,
                                           final String _actionCommand,
                                           final String _toolTipText) {

        //URL imageURL = ToolBarDemo.class.getResource(imgLocation);
        final ImageIcon imgIcon = new ImageIcon(_imagePath);

        final JButton button = new JButton();
        button.setActionCommand(_actionCommand);
        button.setToolTipText(_toolTipText);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                final SearchDataFrame intFrame = new SearchDataFrame();
                intFrame.setSize(new Dimension(800, 400));
                intFrame.setVisible(true);
            }
        });

        button.setIcon(imgIcon);
        //button.setContentAreaFilled(false);
        button.setFocusPainted(true);
        button.setBorder(BorderFactory.createEmptyBorder());

        return button;
    }
}
