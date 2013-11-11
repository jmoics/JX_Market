package org.jcs.esjp.test;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
        ShowCanvas canvas;

        public Window() {
                super();
                final Container container = getContentPane();
                canvas = new ShowCanvas();
                container.add(canvas);
                setSize(300, 200);
                setVisible(true);
        }

        public static void main(final String arg[]) {
                new Window();
        }
}

class ShowCanvas extends JPanel {
        int x, y;
        BufferedImage image;

        ShowCanvas() {
                setBackground(Color.white);
                setSize(450, 400);
                addMouseMotionListener(new MouseMotionHandler());

                final Image img = getToolkit().getImage("src/main/resources/images/argon.png");

                final MediaTracker mt = new MediaTracker(this);
                mt.addImage(img, 1);
                try {
                        mt.waitForAll();
                } catch (final Exception e) {
                        System.out.println("Image not found.");
                }
                image = new BufferedImage(img.getWidth(this), img.getHeight(this),
                                BufferedImage.TYPE_INT_ARGB);
                final Graphics2D g2 = image.createGraphics();
                g2.drawImage(img, 0, 0, this);
        }

        @Override
        public void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2D = (Graphics2D) g;
                g2D.drawImage(image, x, y, this);
        }

        class MouseMotionHandler extends MouseMotionAdapter {
                @Override
                public void mouseDragged(final MouseEvent e) {
                        x = e.getX();
                        y = e.getY();
                        repaint();
                }

                @Override
                public void mouseMoved(final MouseEvent e) {
                }
        }
}