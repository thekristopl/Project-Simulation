package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import layout.*;

public class JDeviceLabel extends JLabel {
    
    private int xPressed = 0;
    private int yPressed = 0;
    private final int routerID;
    JDevicePopUpMenu menu;
    
    public JDeviceLabel(String text, Icon icon, Point location, int routerID, Layout layout) {
        super();
        this.setPreferredSize(new Dimension(80, 80));
        this.setSize(this.getPreferredSize());
        this.setIcon(icon);
        this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.setToolTipText(text);
        this.setOpaque(true);
        this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        this.setText(text);
        this.setVerticalTextPosition(JLabel.BOTTOM);
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setLocation((int) location.getX(), (int) location.getY());
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new PopClickListener(layout, routerID));
        this.routerID = routerID;
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                //and when the Jlabel is dragged
                setLocation(e.getX() + JDeviceLabel.this.getX() - xPressed, e.getY() + JDeviceLabel.this.getY() - yPressed);
            }
        });
    }
    
    @Override
    public int hashCode() {
        return this.routerID;
    }

    public void removeSelf() {
        ((MainFrame) SwingUtilities.getWindowAncestor(this)).deleteRouter(this.routerID);
    }

    int getID() {
        return this.routerID;
    }

    class PopClickListener extends MouseAdapter {
        
        Layout l;
        private int id;
        
        public PopClickListener(Layout l, int routerID) {
            super();
            this.l = l;
            this.id = routerID;
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()) {
                doPop(e);
            }
            xPressed = e.getX();
            yPressed = e.getY();
            removeSelf();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger())
                doPop(e);
            
        }

        private void doPop(MouseEvent e) {
            menu = new JDevicePopUpMenu(l, id);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}