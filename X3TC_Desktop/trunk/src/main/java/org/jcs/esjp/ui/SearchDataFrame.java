package org.jcs.esjp.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;

import org.jcs.esjp.model.Sector;
import org.jcs.esjp.model.StructureAbstract;
import org.jcs.esjp.model.StructureFactory;
import org.jcs.esjp.model.StructureNormal;
import org.jcs.esjp.util.Settings;

public class SearchDataFrame
    extends JFrame implements ActionListener
{
    MapPanel mappan;
    JComboBox<ObjectPosition> galaxyCombo;

    public SearchDataFrame(final MapPanel _mappan)
    {
        super();
        this.mappan = _mappan;
        final JSplitPane container = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.setContentPane(container);

        final JSplitPane structPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        structPane.add(getGalaxyPane());
        structPane.add(getOtherPanel());

        container.add(structPane);
        container.add(getObjectsPanel());
    }

    protected JComponent getGalaxyPane()
    {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        buildGalaxyDropDown(null);

        final JPanel radioPan = new JPanel(new GridLayout(1, 0));
        radioPan.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        final JRadioButton sectorButton = new JRadioButton("Sector");
        sectorButton.setActionCommand(Settings.SearchSettings.SECTOR.getKey());
        sectorButton.addActionListener(this);
        sectorButton.setSelected(true);

        final JRadioButton dockButton = new JRadioButton("Docks");
        dockButton.setActionCommand(Settings.SearchSettings.DOCK.getKey());
        dockButton.addActionListener(this);
        dockButton.setSelected(true);

        final JRadioButton factButton = new JRadioButton("Fabrica");
        factButton.setActionCommand(Settings.SearchSettings.FACTORY.getKey());
        factButton.addActionListener(this);
        factButton.setSelected(true);

        /*
         * final ButtonGroup group = new ButtonGroup(); group.add(sectorButton); group.add(dockButton);
         * group.add(factButton);
         */

        radioPan.add(sectorButton);
        radioPan.add(dockButton);
        radioPan.add(factButton);

        final JButton searchButton = new JButton("Buscar");
        searchButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(final MouseEvent e)
            {
                final ObjectPosition item = (ObjectPosition) galaxyCombo.getSelectedItem();
                mappan.updateGalaxyMap(item);
            }

        });

        panel.add(galaxyCombo, BorderLayout.PAGE_START);
        panel.add(radioPan, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.PAGE_END);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return panel;
    }

    protected void buildGalaxyDropDown(final Set<String> _setFilter) {
        final Collection<ObjectPosition> lst = getGalaxyData(_setFilter).values();
        if (galaxyCombo != null) {
            galaxyCombo.removeAllItems();
            for (final ObjectPosition objPos : lst) {
                galaxyCombo.addItem(objPos);
            }
        } else {
            galaxyCombo = new JComboBox<ObjectPosition>(lst.toArray(new ObjectPosition[0]));
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Component[] components = ((JRadioButton)e.getSource()).getParent().getComponents();
        final Set<String> setFilter = new HashSet<String>();
        for (final Component component : components) {
            final JRadioButton radioButton = (JRadioButton) component;
            if (radioButton.isSelected()) {
                setFilter.add(radioButton.getActionCommand());
            }
        }
        buildGalaxyDropDown(setFilter);
    }

    protected Map<String, ObjectPosition> getGalaxyData(final Set<String> _setFilter)
    {
        final Map<String, ObjectPosition> mapPos = new TreeMap<String, ObjectPosition>();
        final Map<Integer, Map<Integer, Sector>> matrix = mappan.getMatrix();
        for (final Entry<Integer, Map<Integer, Sector>> entry : matrix.entrySet()) {
            final Integer posX = entry.getKey();
            for (final Entry<Integer, Sector> entry2 : entry.getValue().entrySet()) {
                final Sector sector = entry2.getValue();
                final Integer posY = entry2.getKey();
                final Integer position = posX * mappan.getMaxY() + posY;
                if (_setFilter == null || _setFilter.contains(Settings.SearchSettings.SECTOR.getKey())) {
                    System.out.println("Sector --> " + sector.getName());
                    final String nameObj = sector.getName();
                    final ObjectPosition secPos = new ObjectPosition(nameObj);
                    secPos.getObject2Position().put(sector, position);
                    mapPos.put(nameObj, secPos);
                }
                for (final StructureAbstract struc : sector.getLstStruct()) {
                    final String nameObj = struc.getName().split("[(]")[0];
                    //final ObjectPosition strucPos = new ObjectPosition(struc, position);
                    if (struc instanceof StructureNormal) {
                        if (_setFilter == null || _setFilter.contains(Settings.SearchSettings.DOCK.getKey())) {
                            System.out.println("Normal --> " + struc.getName());
                            if (!mapPos.containsKey(nameObj)) {
                                final ObjectPosition strucPos = new ObjectPosition(nameObj);
                                strucPos.getObject2Position().put(struc, position);
                                mapPos.put(nameObj, strucPos);
                            } else {
                                final ObjectPosition secPos = mapPos.get(nameObj);
                                secPos.getObject2Position().put(struc, position);
                            }
                        }
                    } else if (struc instanceof StructureFactory) {
                        if (_setFilter == null || _setFilter.contains(Settings.SearchSettings.FACTORY.getKey())) {
                            System.out.println("Factory --> " + struc.getName());
                            if (!mapPos.containsKey(nameObj)) {
                                final ObjectPosition strucPos = new ObjectPosition(nameObj);
                                strucPos.getObject2Position().put(struc, position);
                                mapPos.put(nameObj, strucPos);
                            } else {
                                final ObjectPosition secPos = mapPos.get(nameObj);
                                secPos.getObject2Position().put(struc, position);
                            }
                        }
                    }
                }
            }
        }

        return mapPos;
    }

    protected List<ObjectPosition> getObjectsData()
    {
        final List<ObjectPosition> list = new ArrayList<ObjectPosition>();
        /*final Map<Integer, Map<Integer, Sector>> matrix = mappan.getMatrix();
        for (final Entry<Integer, Map<Integer, Sector>> entry : matrix.entrySet()) {
            final Integer posX = entry.getKey();
            for (final Entry<Integer, Sector> entry2 : entry.getValue().entrySet()) {
                final Integer posY = entry2.getKey();
                final Sector sector = entry2.getValue();
                final int position = posX * mappan.getMaxY() + posY;
                for (final StructureAbstract struc : sector.getLstStruct()) {
                    if (struc instanceof StructureNormal) {
                        for (final ObjectSale objSale : ((StructureNormal) struc).getObjSale()) {
                            final ObjectPosition objPos = new ObjectPosition(objSale, position);
                            list.add(objPos);
                        }
                    } else if (struc instanceof StructureFactory) {
                        for (final ObjectSale objSale : ((StructureFactory) struc).getObjSale()) {
                            final ObjectPosition objPos = new ObjectPosition(objSale, position);
                            list.add(objPos);
                        }
                        for (final ObjectPurchase objPur : ((StructureFactory) struc).getObjPurch()) {
                            final ObjectPosition objPos = new ObjectPosition(objPur, position);
                            list.add(objPos);
                        }
                    }
                }
            }
        }

        orderObjectPositionList(list);*/

        return list;
    }

    protected JComponent getOtherPanel()
    {
        final JPanel panel = new JPanel();
        return panel;
    }

    protected JComponent getObjectsPanel()
    {
        final JPanel objectsPane = new JPanel();
        return objectsPane;
    }

    public class ObjectPosition
    {
        private String name;
        private final Map<Object, Integer> object2Position;

        public ObjectPosition(final String _name)
        {
            this.name = _name;
            this.object2Position = new HashMap<Object, Integer>();
        }

        public String getName()
        {
            return name;
        }

        public void setName(final String name)
        {
            this.name = name;
        }

        public Map<Object, Integer> getObject2Position()
        {
            return object2Position;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }
}
