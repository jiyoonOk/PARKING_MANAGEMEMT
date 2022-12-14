import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

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
            public void actionPerformed(ActionEvent e) {dispose();}
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
        setLocationRelativeTo(null);

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
        int notice_add = JOptionPane.showConfirmDialog(this, "저장 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
        if (notice_add == YES_OPTION) {

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format_time1 = format1.format(System.currentTimeMillis());

            String sql = "INSERT INTO parking.notice (notice_id, notice_title, notice_contents, notice_date) VALUES ('" + idNum("notice") + "', '" + title + "', '" + content + "', '" + format_time1 + "');";
            AdminMain.usingDB.DBInstruct(sql);

            JOptionPane.showMessageDialog(this, "저장이 되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);

        }
        else {
            dispose();
        }
    }
    private void onOK(int id, String title, String content) {
            int add_check = JOptionPane.showConfirmDialog(this, "저장 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
            if (add_check == YES_OPTION) {

                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format_time1 = format1.format(System.currentTimeMillis());

                String sql = "INSERT INTO parking.answer (answer_id, answer_title, answer_contents, answer_date, question_id) VALUES ('" + idNum("answer") + "', '" + title + "', '" + content + "', '" + format_time1 + "', '" + id + "');";
                AdminMain.usingDB.DBInstruct(sql);

                JOptionPane.showMessageDialog(this, "저장이 되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);

            }
            else {
            dispose();
            }
        }

    public int idNum(String DBtableName) {

        int count = Integer.parseInt(AdminMain.usingDB.getData("select MAX(" + DBtableName + "_id) from parking."+ DBtableName +";"));

        return count+1;
    }
}
