package org.jcs.esjp.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.jcs.esjp.model.ObjectAbstract;
import org.jcs.esjp.model.ObjectPurchase;
import org.jcs.esjp.model.ObjectSale;
import org.jcs.esjp.model.Sector;
import org.jcs.esjp.model.StructureAbstract;
import org.jcs.esjp.model.StructureFactory;
import org.jcs.esjp.model.StructureNormal;
import org.jcs.esjp.util.Settings;

public class SearchDataFrame
    extends JFrame implements ItemListener
{
    MapPanel mappan;
    JComboBox<ObjectPosition> galaxyCombo;
    JComboBox<ObjectPosition> objectsCombo;
    Font font;

    public SearchDataFrame(final MapPanel _mappan)
    {
        super();
        font = new Font("SansSerif", Font.PLAIN, 10);
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

        final JCheckBox sectorButton = new JCheckBox("Sector");
        sectorButton.setFont(font);
        sectorButton.setActionCommand(Settings.SearchSettings.SECTOR.getKey());
        sectorButton.setSelected(true);
        sectorButton.addItemListener(this);

        final JCheckBox dockButton = new JCheckBox("Docks");
        dockButton.setFont(font);
        dockButton.setActionCommand(Settings.SearchSettings.DOCK.getKey());
        dockButton.setSelected(true);
        dockButton.addItemListener(this);

        final JCheckBox factButton = new JCheckBox("Fabrica");
        factButton.setFont(font);
        factButton.setActionCommand(Settings.SearchSettings.FACTORY.getKey());
        factButton.setSelected(true);
        factButton.addItemListener(this);

        /*
         * final ButtonGroup group = new ButtonGroup(); group.add(sectorButton); group.add(dockButton);
         * group.add(factButton);
         */

        radioPan.add(sectorButton);
        radioPan.add(dockButton);
        radioPan.add(factButton);

        final JButton searchButton = new JButton("Buscar");
        searchButton.setFont(font);
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
            galaxyCombo.setFont(font);
        }
    }

    @Override
    public void itemStateChanged(final ItemEvent e)
    {
        final Component[] components = ((JCheckBox) e.getSource()).getParent().getComponents();
        final String actionComm = ((JCheckBox)e.getSource()).getActionCommand();
        if (Settings.SearchSettings.FACTORY.getKey().equals(actionComm)
                        || Settings.SearchSettings.DOCK.getKey().equals(actionComm)
                        || Settings.SearchSettings.SECTOR.getKey().equals(actionComm)) {
            final Set<String> setFilter = new HashSet<String>();
            for (final Component component : components) {
                final JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    setFilter.add(checkBox.getActionCommand());
                }
            }
            buildGalaxyDropDown(setFilter);
        } else {
            final Map<String, Integer> mapFilter = new HashMap<String, Integer>();
            for (final Component component : components) {
                final JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    //mapFilter.add(checkBox.getActionCommand());
                }
            }
            buildObjectsDropDown(mapFilter);
        }
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

    protected JComponent getOtherPanel()
    {
        final JPanel panel = new JPanel();
        return panel;
    }

    protected JComponent getObjectsPanel()
    {
        final JPanel objectsPane = new JPanel();
        objectsPane.setLayout(new BorderLayout());

        buildObjectsDropDown(null);

        final JPanel filterPanel = new JPanel();
        //filterPanel.setBounds(0, 0, 400, 200);
        filterPanel.setLayout(new GridLayout(4, 2));

        final JCheckBox minQuantity = new JCheckBox("MIN Cantidad");
        minQuantity.setFont(font);
        minQuantity.setActionCommand(Settings.SearchSettings.MINQUANTITY.getKey());
        minQuantity.addItemListener(this);
        final JTextField txtQuantity = new JTextField("1");
        txtQuantity.setFont(font);
        filterPanel.add(minQuantity);
        filterPanel.add(txtQuantity);

        final JCheckBox percentage = new JCheckBox("No < que x%");
        percentage.setFont(font);
        percentage.setActionCommand(Settings.SearchSettings.NOMINORPERCENT.getKey());
        percentage.addItemListener(this);
        final JTextField txtNotMinorPer = new JTextField("100");
        txtNotMinorPer.setFont(font);
        filterPanel.add(percentage);
        filterPanel.add(txtNotMinorPer);

        final JCheckBox minPrice = new JCheckBox("MIN Price");
        minPrice.setFont(font);
        minPrice.setActionCommand(Settings.SearchSettings.MINPRICE.getKey());
        minPrice.addItemListener(this);
        final JTextField txtMinPrice = new JTextField("0");
        txtMinPrice.setFont(font);
        filterPanel.add(minPrice);
        filterPanel.add(txtMinPrice);

        final JCheckBox maxPrice = new JCheckBox("MAX Price");
        maxPrice.setFont(font);
        maxPrice.setActionCommand(Settings.SearchSettings.MAXPRICE.getKey());
        maxPrice.addItemListener(this);
        final JTextField txtMaxPrice = new JTextField("10000");
        txtMaxPrice.setFont(font);
        filterPanel.add(maxPrice);
        filterPanel.add(txtMaxPrice);

        final JButton searchButton = new JButton("Buscar");
        searchButton.setFont(font);
        searchButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(final MouseEvent e)
            {
                final ObjectPosition item = (ObjectPosition) objectsCombo.getSelectedItem();
                mappan.updateGalaxyMap(item);
            }

        });

        objectsPane.add(objectsCombo, BorderLayout.PAGE_START);
        objectsPane.add(filterPanel, BorderLayout.CENTER);
        objectsPane.add(searchButton, BorderLayout.PAGE_END);
        objectsPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        buildGalaxyDropDown(null);
        return objectsPane;
    }

    protected void buildObjectsDropDown(final Map<String, Integer> _mapFilter) {
        final Collection<ObjectPosition> lst = getObjectsData(_mapFilter).values();
        if (objectsCombo != null) {
            objectsCombo.removeAllItems();
            for (final ObjectPosition objPos : lst) {
                objectsCombo.addItem(objPos);
            }
        } else {
            objectsCombo = new JComboBox<ObjectPosition>(lst.toArray(new ObjectPosition[0]));
            objectsCombo.setFont(font);
        }
    }

    protected Map<String, ObjectPosition> getObjectsData(final Map<String, Integer> _mapFilter)
    {
        final Map<String, ObjectPosition> mapPos = new TreeMap<String, ObjectPosition>();
        final Map<Integer, Map<Integer, Sector>> matrix = mappan.getMatrix();
        for (final Entry<Integer, Map<Integer, Sector>> entry : matrix.entrySet()) {
            final Integer posX = entry.getKey();
            for (final Entry<Integer, Sector> entry2 : entry.getValue().entrySet()) {
                final Sector sector = entry2.getValue();
                final Integer posY = entry2.getKey();
                final Integer position = posX * mappan.getMaxY() + posY;
                for (final StructureAbstract struc : sector.getLstStruct()) {
                    if (struc instanceof StructureNormal) {
                        for (final ObjectSale objSale : ((StructureNormal) struc).getObjSale()) {
                            if (_mapFilter == null || analizeObjectsFilter(objSale, _mapFilter)) {
                                if (!mapPos.containsKey(objSale.getName())) {
                                    final ObjectPosition objPos = new ObjectPosition(objSale.getName());
                                    objPos.getObject2Position().put(struc, position);
                                    mapPos.put(objSale.getName(), objPos);
                                } else {
                                    final ObjectPosition secPos = mapPos.get(objSale.getName());
                                    secPos.getObject2Position().put(struc, position);
                                }
                            }
                        }
                    } else if (struc instanceof StructureFactory) {
                        for (final ObjectSale objSale : ((StructureFactory) struc).getObjSale()) {
                            if (_mapFilter == null || analizeObjectsFilter(objSale, _mapFilter)) {
                                if (!mapPos.containsKey(objSale.getName())) {
                                    final ObjectPosition objPos = new ObjectPosition(objSale.getName());
                                    objPos.getObject2Position().put(struc, position);
                                    mapPos.put(objSale.getName(), objPos);
                                } else {
                                    final ObjectPosition secPos = mapPos.get(objSale.getName());
                                    secPos.getObject2Position().put(struc, position);
                                }
                            }
                        }
                        for (final ObjectPurchase objPur : ((StructureFactory) struc).getObjPurch()) {
                            if (_mapFilter == null || analizeObjectsFilter(objPur, _mapFilter)) {
                                if (!mapPos.containsKey(objPur.getName())) {
                                    final ObjectPosition objPos = new ObjectPosition(objPur.getName());
                                    objPos.getObject2Position().put(struc, position);
                                    mapPos.put(objPur.getName(), objPos);
                                } else {
                                    final ObjectPosition secPos = mapPos.get(objPur.getName());
                                    secPos.getObject2Position().put(struc, position);
                                }
                            }
                        }
                    }
                }
            }
        }

        return mapPos;
    }

    protected boolean analizeObjectsFilter(final ObjectAbstract _object,
                                           final Map<String, Integer> _mapFilter) {
        boolean valid = true;

        for (final Entry<String, Integer> entry : _mapFilter.entrySet()) {
            final String key = entry.getKey();
            final Integer value = entry.getValue();
            if (key.equals(Settings.SearchSettings.MINQUANTITY.getKey())) {
                if (_object.getQuantity() < value) {
                    valid = false;
                }
            } else if (key.equals(Settings.SearchSettings.NOMINORPERCENT.getKey())) {
                /*if (_object.getQuantity() < value) {
                    valid = false;
                }*/
            } else if (key.equals(Settings.SearchSettings.MINPRICE.getKey())) {
                if (_object.getPrice() < value) {
                    valid = false;
                }
            } else if (key.equals(Settings.SearchSettings.MAXPRICE.getKey())) {
                if (_object.getPrice() > value) {
                    valid = false;
                }
            }
        }
        return valid;
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
