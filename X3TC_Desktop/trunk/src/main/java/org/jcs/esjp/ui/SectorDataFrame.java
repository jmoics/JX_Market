package org.jcs.esjp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import org.jcs.esjp.model.ObjectAbstract;
import org.jcs.esjp.model.ObjectPurchase;
import org.jcs.esjp.model.ObjectSale;
import org.jcs.esjp.model.Sector;
import org.jcs.esjp.model.StructureAbstract;
import org.jcs.esjp.model.StructureFactory;
import org.jcs.esjp.model.StructureNormal;
import org.jcs.esjp.model.StructureOther;

public class SectorDataFrame
    extends JFrame
    implements ActionListener, TreeSelectionListener
{
    final JPanel dataView;
    JTree tree;

    public SectorDataFrame(final Sector _sector)
    {
        super(_sector.getName());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Place the button near the bottom of the window.
        final Container contentPane = getContentPane();

        dataView = new JPanel();
        dataView.setBackground(new Color(53, 66, 90));
        dataView.setLayout(new GridLayout(3, 2));
        initHelp();

        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createDataPane(_sector));
        splitPane.setRightComponent(dataView);

        contentPane.add(splitPane, BorderLayout.CENTER);
        contentPane.add(createButtonPane(), BorderLayout.PAGE_END);
    }

    // Make the button do the same thing as the default close operation
    // (DISPOSE_ON_CLOSE).
    @Override
    public void actionPerformed(final ActionEvent e)
    {
        setVisible(false);
        dispose();
    }

    protected JComponent createDataPane(final Sector _sector)
    {
        final DefaultMutableTreeNode top = new DefaultMutableTreeNode(_sector.getName());

        for (final StructureAbstract struct : _sector.getLstStruct()) {
            if (struct instanceof StructureNormal) {
                final DefaultMutableTreeNode structure = new DefaultMutableTreeNode(struct);
                top.add(structure);
                for (final ObjectSale object : ((StructureNormal) struct).getObjSale()) {
                    final DefaultMutableTreeNode obj = new DefaultMutableTreeNode(object);
                    structure.add(obj);
                }
            } else if (struct instanceof StructureFactory) {
                final DefaultMutableTreeNode structure = new DefaultMutableTreeNode(struct);
                top.add(structure);
                for (final ObjectSale object : ((StructureFactory) struct).getObjSale()) {
                    final DefaultMutableTreeNode obj = new DefaultMutableTreeNode(object);
                    structure.add(obj);
                    // cell.setImage("images/sale.png");
                }
                for (final ObjectPurchase object : ((StructureFactory) struct).getObjPurch()) {
                    final DefaultMutableTreeNode obj = new DefaultMutableTreeNode(object);
                    structure.add(obj);
                    // cell.setImage("images/purchase.png");
                }
            } else if (struct instanceof StructureOther) {

            } else {

            }
        }

        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setBackground(new Color(53, 66, 90));
        tree.setCellRenderer(new MyCellRenderer());
        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        final JScrollPane treeView = new JScrollPane(tree);
        final Dimension minimumSize = new Dimension(350, 700);
        treeView.setMinimumSize(minimumSize);
        treeView.setBackground(new Color(53, 66, 90));
        return treeView;
    }

    // Create the button that goes in the main window.
    protected JComponent createButtonPane()
    {
        final JButton button = new JButton("Cerrar Ventana");
        final Dimension size = button.getPreferredSize();
        button.setSize(size.width - 100, size.height - 100);
        button.addActionListener(this);

        // Center the button in a panel with some space around it.
        final JPanel pane = new JPanel(); // use default FlowLayout
        pane.setBackground(new Color(53, 66, 90));
        pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pane.add(button);

        return pane;
    }

    public class MyCellRenderer
        extends DefaultTreeCellRenderer
    {

        @Override
        public Color getBackgroundNonSelectionColor()
        {
            return new Color(53, 66, 90);
        }

        @Override
        public Color getBackgroundSelectionColor()
        {
            return new Color(53, 66, 90);
        }

        @Override
        public Color getBackground()
        {
            return new Color(53, 66, 90);
        }

        @Override
        public Color getTextSelectionColor()
        {
            return Color.WHITE;
        }

        @Override
        public Color getTextNonSelectionColor()
        {
            return Color.WHITE;
        }

        @Override
        public Font getFont()
        {
            return new Font("Tahoma", Font.PLAIN, 10);
        }

        @Override
        public Component getTreeCellRendererComponent(final JTree tree,
                                                      final Object value,
                                                      final boolean sel,
                                                      final boolean expanded,
                                                      final boolean leaf,
                                                      final int row,
                                                      final boolean hasFocus)
        {
            super.getTreeCellRendererComponent(
                            tree, value, sel,
                            expanded, leaf, row,
                            hasFocus);
            final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.getUserObject() instanceof ObjectAbstract) {
                final ObjectAbstract obj = (ObjectAbstract) node.getUserObject();
                if (obj.getIconPath() != null) {
                    setIcon(new ImageIcon(obj.getIconPath()));
                } else {
                    System.err.println("Icon for the ObjectAbstract was not found.");
                }
            } else if (node.getUserObject() instanceof StructureAbstract) {
                final StructureAbstract str = (StructureAbstract) node.getUserObject();
                if (str.getIconPath() != null) {
                    setIcon(new ImageIcon(str.getIconPath()));
                } else {
                    System.err.println("Icon for the StructureAbstract was not found.");
                }
            }

            return this;
        }
    }

    @Override
    public void valueChanged(final TreeSelectionEvent e)
    {
        final DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(0);
        formatter.setParseBigDecimal(true);

        final Object object = ((DefaultMutableTreeNode) ((JTree) e.getSource())
                        .getLastSelectedPathComponent()).getUserObject();
        if (object instanceof ObjectAbstract) {
            dataView.removeAll();
            final Font font = new Font("Tahoma", Font.PLAIN, 10);
            JLabel label = new JLabel("Precio");
            label.setFont(font);
            label.setForeground(new Color(255, 255, 255));
            dataView.add(label);

            label = new JLabel(formatter.format(((ObjectAbstract) object).getPrice()));
            label.setFont(font);
            label.setForeground(new Color(255, 255, 255));
            dataView.add(label);

            label = new JLabel("Cantidad");
            label.setFont(font);
            label.setForeground(new Color(255, 255, 255));
            dataView.add(label);

            label = new JLabel(formatter.format(((ObjectAbstract) object).getQuantity()));
            label.setFont(font);
            label.setForeground(new Color(255, 255, 255));
            dataView.add(label);

            label = new JLabel("Espacio Libre");
            label.setFont(font);
            label.setForeground(new Color(255, 255, 255));
            dataView.add(label);

            label = new JLabel(formatter.format(((ObjectAbstract) object).getFreeSpace()));
            label.setFont(font);
            label.setForeground(new Color(255, 255, 255));
            dataView.add(label);

            dataView.repaint();
        }
    }

    private void initHelp() {
        final JLabel label = new JLabel("Iniciando...");
        dataView.add(label);
    }
}
