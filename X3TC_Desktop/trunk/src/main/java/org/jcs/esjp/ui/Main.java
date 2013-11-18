package org.jcs.esjp.ui;

public class Main
{

    public static void main(final String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                MainWindow.createAndShowGUI();
            }
        });

    }

}
