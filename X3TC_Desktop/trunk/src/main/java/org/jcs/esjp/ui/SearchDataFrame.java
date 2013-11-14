package org.jcs.esjp.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class SearchDataFrame extends JFrame
{
    public SearchDataFrame() {
        final JSplitPane container = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.setContentPane(container);

        final JSplitPane structPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        structPane.add(getGalaxyPane());
        structPane.add(getOtherPanel());

        container.add(structPane);
        container.add(getObjectsPanel());
    }

    protected JComponent getGalaxyPane() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        final JComboBox<Object> galaxyCombo = new JComboBox<>();

        //galaxyCombo.addItem(item);
        return panel;
    }

    protected List<Object> getGalaxyData() {
        final List<Object> list = new ArrayList<Object>();
        return list;
    }

    protected JComponent getOtherPanel() {
        final JPanel panel = new JPanel();
        return panel;
    }

    protected JComponent getObjectsPanel() {
        final JPanel objectsPane = new JPanel();
        return objectsPane;
    }
}
