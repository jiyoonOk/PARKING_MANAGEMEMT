import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.YES_OPTION;

public class AddText extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField title;
    private JTextArea content;
    private int id;

    public AddText() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(AdminMain.admin);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String a = title.getText();
                String b = content.getText();
                onOK(a, b);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {dispose();
            }
        });

        // X 클릭 시 onCancel() 호출
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // ESCAPE 시 onCancel() 호출
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(true);
    }
    public AddText(int id) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.id = id;

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String a = title.getText();
                String b = content.getText();
                onOK(id, a, b);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // X 클릭 시 onCancel() 호출
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // ESCAPE 시 onCancel() 호출
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(true);
        //System.exit(0);
    }

    private void onOK(String title, String content) {
        int notice_delete = JOptionPane.showConfirmDialog(this, "저장 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
        if (notice_delete == YES_OPTION) {
            DBconnection db = new DBconnection(title, content);
            JOptionPane.showMessageDialog(this, "저장이 되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
        };
    }
    private void onOK(int id, String title, String content) {
            int add_check = JOptionPane.showConfirmDialog(this, "저장 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
            if (add_check == YES_OPTION) {
                DBconnection db = new DBconnection(id, title, content);
                JOptionPane.showMessageDialog(this, "저장이 되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
            };
        }

}
