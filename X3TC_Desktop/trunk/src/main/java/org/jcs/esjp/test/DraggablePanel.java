package org.jcs.esjp.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class DraggablePanel extends JFrame {

    JButton jb;
    List<JPanel> mypanels = new ArrayList<JPanel>();
    public DraggablePanel() {
        jb = new JButton("Add Panel");
        jb.setBounds(10, 10, 100, 50);
        setSize(new Dimension(1000, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        add(jb);
        setVisible(true);
        initialize();
    }


    public void initialize() {

        jb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                // TODO Auto-generated method stub
                final JPanel panel = new JPanel();
                panel.setBounds(150,150,200,200);
                panel.setBackground(Color.black);
                mypanels.add(panel);
                add(panel);
                repaint();
                handleDrag();
            }
        });

    }

    public void handleDrag(){
        for(int i=0;i<mypanels.size();i++) {
            final int j = i;
            mypanels.get(i).addMouseMotionListener(new MouseMotionAdapter() {

                @Override
                public void mouseDragged(final MouseEvent me) {
                    me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);
                    mypanels.get(j).setLocation(me.getX(), me.getY());
                }

            });
        }
    }

    public static void main(final String args[]) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                new DraggablePanel();
            }
        });

    }


}