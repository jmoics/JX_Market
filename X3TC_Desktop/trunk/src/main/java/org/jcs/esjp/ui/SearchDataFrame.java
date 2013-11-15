package org.jcs.esjp.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;

import org.jcs.esjp.model.ObjectAbstract;
import org.jcs.esjp.model.ObjectPurchase;
import org.jcs.esjp.model.ObjectSale;
import org.jcs.esjp.model.Sector;
import org.jcs.esjp.model.StructureAbstract;
import org.jcs.esjp.model.StructureFactory;
import org.jcs.esjp.model.StructureNormal;
import org.jcs.esjp.util.Settings;

public class SearchDataFrame
    extends JFrame
{
    MapPanel mappan;

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

        final List<ObjectPosition> lst = getGalaxyData();
        final JComboBox<ObjectPosition> galaxyCombo = new JComboBox<ObjectPosition>(lst.toArray(new ObjectPosition[0]));

        final JPanel radioPan = new JPanel(new GridLayout(1, 0));
        radioPan.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        final JRadioButton sectorButton = new JRadioButton("Sector");
        sectorButton.setActionCommand(Settings.SearchSettings.SECTOR.getKey());
        sectorButton.setSelected(true);

        final JRadioButton dockButton = new JRadioButton("Docks");
        dockButton.setActionCommand(Settings.SearchSettings.DOCK.getKey());
        dockButton.setSelected(true);

        final JRadioButton factButton = new JRadioButton("Fabrica");
        factButton.setActionCommand(Settings.SearchSettings.FACTORY.getKey());
        factButton.setSelected(true);

        /*
         * final ButtonGroup group = new ButtonGroup(); group.add(sectorButton); group.add(dockButton);
         * group.add(factButton);
         */

        radioPan.add(sectorButton);
        radioPan.add(dockButton);
        radioPan.add(factButton);

        final JButton searchButton = new JButton("Buscar");

        panel.add(galaxyCombo, BorderLayout.PAGE_START);
        panel.add(radioPan, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.PAGE_END);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return panel;
    }

    protected List<ObjectPosition> getGalaxyData()
    {
        final List<ObjectPosition> list = new ArrayList<ObjectPosition>();
        final Map<Integer, Map<Integer, Sector>> matrix = mappan.getMatrix();
        for (final Entry<Integer, Map<Integer, Sector>> entry : matrix.entrySet()) {
            final Integer posX = entry.getKey();
            for (final Entry<Integer, Sector> entry2 : entry.getValue().entrySet()) {
                final Integer posY = entry2.getKey();
                final Sector sector = entry2.getValue();
                final int position = posX * mappan.getMaxY() + posY;
                final ObjectPosition secPos = new ObjectPosition(sector, position);
                System.out.println("Sector --> " + sector.getName());
                list.add(secPos);
                for (final StructureAbstract struc : sector.getLstStruct()) {
                    final ObjectPosition strucPos = new ObjectPosition(struc, position);
                    if (struc instanceof StructureNormal) {
                        System.out.println("Normal --> " + struc.getName());
                        list.add(strucPos);
                    } else if (struc instanceof StructureFactory) {
                        System.out.println("Factory --> " + struc.getName());
                        list.add(strucPos);
                    }
                }
            }
        }

        orderObjectPositionList(list);

        return list;
    }

    protected List<ObjectPosition> getObjectsData()
    {
        final List<ObjectPosition> list = new ArrayList<ObjectPosition>();
        final Map<Integer, Map<Integer, Sector>> matrix = mappan.getMatrix();
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

        orderObjectPositionList(list);

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

    protected void orderObjectPositionList(final List<ObjectPosition> list)
    {
        Collections.sort(list, new Comparator<ObjectPosition>()
        {
            @Override
            public int compare(final ObjectPosition o1,
                               final ObjectPosition o2)
            {
                String name1 = "";
                String name2 = "";
                if (o1.getObject() instanceof Sector) {
                    name1 = ((Sector) o1.getObject()).getName();
                } else if (o1.getObject() instanceof StructureAbstract) {
                    name1 = ((StructureAbstract) o1.getObject()).getName();
                } else if (o1.getObject() instanceof ObjectAbstract) {
                    name1 = ((ObjectAbstract) o1.getObject()).getName();
                }

                if (o2.getObject() instanceof Sector) {
                    name2 = ((Sector) o2.getObject()).getName();
                } else if (o2.getObject() instanceof StructureAbstract) {
                    name2 = ((StructureAbstract) o2.getObject()).getName();
                } else if (o2.getObject() instanceof ObjectAbstract) {
                    name2 = ((ObjectAbstract) o2.getObject()).getName();
                }
                return name1.compareTo(name2);
            }
        });
    }

    public class ObjectPosition
    {
        private Object object;
        private int position;

        public ObjectPosition(final Object _object,
                              final int _position)
        {
            this.object = _object;
            this.position = _position;
        }

        public Object getObject()
        {
            return object;
        }

        public void setSector(final Object sector)
        {
            this.object = sector;
        }

        public int getPosition()
        {
            return position;
        }

        public void setPosition(final int position)
        {
            this.position = position;
        }

        @Override
        public String toString()
        {
            String ret;
            if (object instanceof Sector) {
                ret = ((Sector) object).getName();
            } else if (object instanceof StructureAbstract) {
                ret = ((StructureAbstract) object).getName();
            } else if (object instanceof ObjectAbstract) {
                ret = ((ObjectAbstract) object).getName();
            } else {
                ret = super.toString();
            }
            return ret;
        }
    }
}
