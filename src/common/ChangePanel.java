package common;

import UserMain.UserMain;

import javax.swing.JPanel;
import java.awt.*;

public class ChangePanel {
    public ChangePanel(JPanel p1, JPanel p2) {
        UserMain.ct.remove(p1);
        UserMain.ct.add(p2, BorderLayout.EAST);
        UserMain.ct.revalidate();
        UserMain.ct.repaint();
    }
}
