package org.jcs.esjp.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jcs.esjp.model.ObjectAbstract;
import org.jcs.esjp.model.ObjectPurchase;
import org.jcs.esjp.model.ObjectSale;
import org.jcs.esjp.model.Sector;
import org.jcs.esjp.model.StructureAbstract;
import org.jcs.esjp.model.StructureFactory;
import org.jcs.esjp.model.StructureNormal;
import org.jcs.esjp.util.Settings;

public class SearchDataFrame
    extends JFrame implements ItemListener, DocumentListener
{
    MapPanel mappan;
    JComboBox<ObjectPosition> galaxyCombo;
    JComboBox<ObjectPosition> objectsCombo;
    JFormattedTextField txtQuantity;
    JFormattedTextField txtNotMinorPer;
    JFormattedTextField txtMinPrice;
    JFormattedTextField txtMaxPrice;
    Font font;
    final Map<String, Integer> mapFilter;

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

        mapFilter = new HashMap<String, Integer>();
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

        buildObjectsDropDown();

        final JPanel filterPanel = new JPanel();
        //filterPanel.setBounds(0, 0, 400, 200);
        filterPanel.setLayout(new GridLayout(4, 2));

        final NumberFormat format = NumberFormat.getIntegerInstance();

        final JCheckBox minQuantity = new JCheckBox("MIN Cantidad");
        minQuantity.setFont(font);
        minQuantity.setActionCommand(Settings.SearchSettings.MINQUANTITY.getKey());
        minQuantity.addItemListener(this);
        txtQuantity = new JFormattedTextField(format);
        txtQuantity.setValue(new Integer(1));
        txtQuantity.setName(Settings.SearchSettings.MINQUANTITY.getKey());
        txtQuantity.setEditable(false);
        txtQuantity.setFont(font);
        txtQuantity.getDocument().addDocumentListener(this);
        filterPanel.add(minQuantity);
        filterPanel.add(txtQuantity);

        final JCheckBox percentage = new JCheckBox("No < que x%");
        percentage.setFont(font);
        percentage.setActionCommand(Settings.SearchSettings.NOMINORPERCENT.getKey());
        percentage.addItemListener(this);
        txtNotMinorPer = new JFormattedTextField(format);
        txtNotMinorPer.setValue(new Integer(100));
        txtNotMinorPer.setName(Settings.SearchSettings.NOMINORPERCENT.getKey());
        txtNotMinorPer.setEditable(false);
        txtNotMinorPer.setFont(font);
        txtNotMinorPer.getDocument().addDocumentListener(this);
        filterPanel.add(percentage);
        filterPanel.add(txtNotMinorPer);

        final JCheckBox minPrice = new JCheckBox("MIN Price");
        minPrice.setFont(font);
        minPrice.setActionCommand(Settings.SearchSettings.MINPRICE.getKey());
        minPrice.addItemListener(this);
        txtMinPrice = new JFormattedTextField(format);
        txtMinPrice.setValue(new Integer(0));
        txtMinPrice.setName(Settings.SearchSettings.MINPRICE.getKey());
        txtMinPrice.setEditable(false);
        txtMinPrice.setFont(font);
        txtMinPrice.getDocument().addDocumentListener(this);
        filterPanel.add(minPrice);
        filterPanel.add(txtMinPrice);

        final JCheckBox maxPrice = new JCheckBox("MAX Price");
        maxPrice.setFont(font);
        maxPrice.setActionCommand(Settings.SearchSettings.MAXPRICE.getKey());
        maxPrice.addItemListener(this);
        txtMaxPrice = new JFormattedTextField(format);
        txtMaxPrice.setValue(new Integer(100000));
        txtMaxPrice.setName(Settings.SearchSettings.MAXPRICE.getKey());
        txtMaxPrice.setEditable(false);
        txtMaxPrice.setFont(font);
        txtMaxPrice.getDocument().addDocumentListener(this);
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
                analizeObjectsFilter(item);
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

    protected void buildObjectsDropDown() {
        final Collection<ObjectPosition> lst = getObjectsData().values();
        objectsCombo = new JComboBox<ObjectPosition>(lst.toArray(new ObjectPosition[0]));
        objectsCombo.setFont(font);
    }

    protected Map<String, ObjectPosition> getObjectsData()
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
                            if (!mapPos.containsKey(objSale.getName())) {
                                final ObjectPosition objPos = new ObjectPosition(objSale.getName());
                                objPos.getObject2Position().put(objSale, position);
                                mapPos.put(objSale.getName(), objPos);
                            } else {
                                final ObjectPosition secPos = mapPos.get(objSale.getName());
                                secPos.getObject2Position().put(objSale, position);
                            }
                        }
                    } else if (struc instanceof StructureFactory) {
                        for (final ObjectSale objSale : ((StructureFactory) struc).getObjSale()) {
                            if (!mapPos.containsKey(objSale.getName())) {
                                final ObjectPosition objPos = new ObjectPosition(objSale.getName());
                                objPos.getObject2Position().put(objSale, position);
                                mapPos.put(objSale.getName(), objPos);
                            } else {
                                final ObjectPosition secPos = mapPos.get(objSale.getName());
                                secPos.getObject2Position().put(objSale, position);
                            }
                        }
                        for (final ObjectPurchase objPur : ((StructureFactory) struc).getObjPurch()) {
                            if (!mapPos.containsKey(objPur.getName())) {
                                final ObjectPosition objPos = new ObjectPosition(objPur.getName());
                                objPos.getObject2Position().put(objPur, position);
                                mapPos.put(objPur.getName(), objPos);
                            } else {
                                final ObjectPosition secPos = mapPos.get(objPur.getName());
                                secPos.getObject2Position().put(objPur, position);
                            }
                        }
                    }
                }
            }
        }

        return mapPos;
    }

    protected void analizeObjectsFilter(final ObjectPosition _objectPosition) {
        final List<ObjectAbstract> removes = new ArrayList<ObjectAbstract>();
        for (final Object obj : _objectPosition.getObject2Position().keySet()) {
            boolean valid = true;
            final ObjectAbstract object = (ObjectAbstract) obj;
            for (final Entry<String, Integer> entry : mapFilter.entrySet()) {
                final String key = entry.getKey();
                final Integer value = entry.getValue();
                if (key.equals(Settings.SearchSettings.MINQUANTITY.getKey())) {
                    if (object.getQuantity() < value) {
                        valid = false;
                    }
                } else if (key.equals(Settings.SearchSettings.NOMINORPERCENT.getKey())) {
                    /*if (_object.getQuantity() < value) {
                        valid = false;
                    }*/
                } else if (key.equals(Settings.SearchSettings.MINPRICE.getKey())) {
                    if (object.getPrice() < value) {
                        valid = false;
                    }
                } else if (key.equals(Settings.SearchSettings.MAXPRICE.getKey())) {
                    if (object.getPrice() > value) {
                        valid = false;
                    }
                }
            }
            if (!valid) {
                removes.add(object);
            }
        }
        for (final ObjectAbstract remove : removes) {
            _objectPosition.getObject2Position().remove(remove);
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
            int cont = 0;
            for (final Component component : components) {
                if (component instanceof JCheckBox) {
                    final JCheckBox checkBox = (JCheckBox) component;
                    final JTextField txtValue = (JTextField) components[cont + 1];
                    if (checkBox.isSelected()) {
                        txtValue.setEditable(true);
                        mapFilter.put(checkBox.getActionCommand(), Integer.parseInt(txtValue.getText()));
                    } else {
                        txtValue.setEditable(false);
                        mapFilter.remove(checkBox.getActionCommand());
                    }
                }
                cont++;
            }
            //buildObjectsDropDown(mapFilter);
        }
    }

    @Override
    public void insertUpdate(final DocumentEvent e)
    {
        for (final String str : mapFilter.keySet()) {
            if (txtQuantity.isEditable() && txtQuantity.getName().equals(str)) {
                mapFilter.put(str, txtQuantity.getValue() instanceof Integer ? (Integer) txtQuantity.getValue() : 1);
            }
            if (txtNotMinorPer.isEditable() && txtNotMinorPer.getName().equals(str)) {
                mapFilter.put(str, txtNotMinorPer.getValue() instanceof Integer ? (Integer) txtNotMinorPer.getValue() : 100);
            }
            if (txtMinPrice.isEditable() && txtMinPrice.getName().equals(str)) {
                mapFilter.put(str, txtMinPrice.getValue() instanceof Integer ? (Integer) txtMinPrice.getValue() : 0);
            }
            if (txtMaxPrice.isEditable() && txtMaxPrice.getName().equals(str)) {
                mapFilter.put(str, txtMaxPrice.getValue() instanceof Integer ? (Integer) txtMaxPrice.getValue() : 100000);
            }
        }
    }

    @Override
    public void removeUpdate(final DocumentEvent e)
    {
        for (final String str : mapFilter.keySet()) {
            if (txtQuantity.isEditable() && txtQuantity.getName().equals(str)) {
                mapFilter.put(str, txtQuantity.getValue() instanceof Integer ? (Integer) txtQuantity.getValue() : 1);
            }
            if (txtNotMinorPer.isEditable() && txtNotMinorPer.getName().equals(str)) {
                mapFilter.put(str, txtNotMinorPer.getValue() instanceof Integer ? (Integer) txtNotMinorPer.getValue() : 100);
            }
            if (txtMinPrice.isEditable() && txtMinPrice.getName().equals(str)) {
                mapFilter.put(str, txtMinPrice.getValue() instanceof Integer ? (Integer) txtMinPrice.getValue() : 0);
            }
            if (txtMaxPrice.isEditable() && txtMaxPrice.getName().equals(str)) {
                mapFilter.put(str, txtMaxPrice.getValue() instanceof Integer ? (Integer) txtMaxPrice.getValue() : 100000);
            }
        }
    }

    @Override
    public void changedUpdate(final DocumentEvent e)
    {
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
