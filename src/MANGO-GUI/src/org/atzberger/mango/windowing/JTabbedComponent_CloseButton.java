package org.atzberger.mango.windowing;

import org.atzberger.mango.windowing.JDialog_WindowPanelHolder;
import org.atzberger.application.selm_builder.Atz_Application_Data_Communication;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * Provides tabbed window panes that can hold panels.  This class provides the
 * close and windowing icons and functionality.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JTabbedComponent_CloseButton extends JPanel {

    private final JTabbedPane pane;
    private final Component component;

    public JTabbedComponent_CloseButton(final JTabbedPane pane, final Component component) {
      
        //unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        this.component = component;
        
        setOpaque(false);
        
        //make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(JTabbedComponent_CloseButton.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };
        
        add(label);
        //add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        //tab window button
        JButton buttonWin = new WindowButton();
        add(buttonWin);

        //tab close button
        JButton buttonClose = new CloseButton();
        add(buttonClose);

        
        //tab button @@@
        //JButton button2 = new CloseButton();
        //add(button2);
        
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    private class CloseButton extends JButton implements ActionListener {

        public CloseButton() {

            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(closeButtonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(JTabbedComponent_CloseButton.this);
            if (i != -1) {
                pane.remove(i);
            }
        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

        //paint the cross
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.RED);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
        
    }

    private final static MouseListener closeButtonMouseListener = new MouseAdapter() {
      
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
        
    };


    private class WindowButton extends JButton implements ActionListener {

        public WindowButton() {

            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("open this tab in window");

            org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.atzberger.application.selm_builder.application_Main.class).getContext().getResourceMap(JDialog_WindowPanelHolder.class);
            this.setIcon(resourceMap.getIcon("dock_LowerRight.png")); // NOI18N
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(windowButtonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }

      public void actionPerformed(ActionEvent e) {

        /* remove the panel */
        int i = pane.indexOfTabComponent(JTabbedComponent_CloseButton.this);
        
        JPanel jPanel = (JPanel) component;

        if (i != -1) {
          pane.remove(i);
        }
        
        /* open the panel in a window */
        JFrame jFrameMain = Atz_Application_Data_Communication.getApplSharedData().FrameView_Application_Main.getFrame();
        JDialog_WindowPanelHolder jDialog_holder = new JDialog_WindowPanelHolder(jFrameMain, false, jPanel);

        jDialog_holder.setPanel(jPanel);

        jPanel.setVisible(true);

        jDialog_holder.setVisible(true);
        
      }

        //we don't want to update UI for this button
        public void updateUI() {
        }

        //paint the cross
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.RED);
            }

//            /* arrow */
//            int delta = 6;
//            int q = 2;
//            int     w = getWidth() - 1;
//            int     h = getHeight() - 1;
//            g2.drawLine(delta + q , delta, w - delta - 1  + q,  delta);
//            g2.drawLine(w - delta - 1  + q, delta, w - delta - 1  + q, h - delta - 1);
//            //g2.drawLine(delta, h - delta - 1, w - delta - 1, delta);
//            g2.drawLine(delta/2  + q, h - (delta/2) - 1, w - delta - 1  + q, delta);


            /* square */
            int delta = 6;
            int     w = getWidth();
            int     h = getHeight();
            int     q = -1;
            int     r = (int) (h/20.0);
            if (r < 1) {
              r = 1;
            }
            g2.drawLine(delta + q - 1,      delta + q,     w - delta + q + 1,  delta + q);
            g2.drawLine(delta + q - 1,      delta + q + r, w - delta + q + 1,  delta + q + r);
            g2.drawLine(delta + q - 1,      delta + q,     delta + q - 1,      h - delta + q);
            g2.drawLine(delta + q - 1,      h - delta + q, w - delta + q + 1,  h - delta + q);
            g2.drawLine(w - delta + q + 1,  delta + q,     w - delta + q + 1,  h - delta + q);

            g2.dispose();
        }

    }

    private final static MouseListener windowButtonMouseListener = new MouseAdapter() {

        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }

    };


}


