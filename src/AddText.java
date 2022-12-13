import javax.swing.*;
import java.awt.event.*;

public class AddText extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField title;
    private JTextArea content;

    public AddText() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // X 클릭 시 onCancel() 호출
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // ESCAPE 시 onCancel() 호출
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {

        dispose();
    }

    private void onCancel() {
        // 필요한 경우 이곳에 코드 추가
        dispose();
    }

    public static void main(String[] args) {
        AddText dialog = new AddText();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
