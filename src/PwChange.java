import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class PwChange extends JDialog implements ActionListener {
    private JPasswordField currentPw, newPw, rePw;
    private JButton pass, check, ok, cancel;
    private JPanel p;

    public PwChange(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);

        p = new JPanel();
        p.setLayout(new GridLayout(4, 3));

        currentPw = new JPasswordField(10);
        pass = new JButton("확인");
        pass.addActionListener(this);

        p.add(new JLabel("현재 비밀번호 : "));
        p.add(currentPw);
        p.add(pass);

        newPw = new JPasswordField(10);
        newPw.setEditable(false);
        p.add(new JLabel("신규 비밀번호 : "));
        p.add(newPw);
        p.add(new JLabel(""));

        rePw = new JPasswordField(10);
        rePw.setEditable(false);
        check = new JButton("재입력 확인");
        check.setEnabled(false);
        check.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (newPw.getText().equals(rePw.getText())) {
                    JOptionPane.showMessageDialog(p, "비밀번호를 동일하게 입력하였습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    ok.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(p, "비밀번호가 동일하지 않습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    rePw.setText("");
                }
            }
        });

        p.add(new JLabel("비밀번호 재입력 : "));
        p.add(rePw);
        p.add(check);

        ok = new JButton("변경");
        ok.setEnabled(false);
        ok.addActionListener(this);
        cancel = new JButton("취소");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        p.add(new JLabel(""));
        p.add(ok);
        p.add(cancel);

        add(p);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String sql = "", s = ae.getActionCommand();
        ResultSet result;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.err.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            AdminMain.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", AdminMain.user_name, AdminMain.password);
            System.out.println("연결 완료!");

            Statement stmt = AdminMain.con.createStatement();

            switch (s) {
                case "확인":{
                    sql = "select * from parking.manager where manager='" + currentPw.getText() + "';";
                    result = stmt.executeQuery(sql);
                    if (result.next()) {
                        JOptionPane.showMessageDialog(p, "비밀번호가 동일합니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                        newPw.setEditable(true);
                        rePw.setEditable(true);
                        check.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(p, "비밀번호가 동일하지 않습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                        currentPw.setText("");
                    }}
                    break;
                case "변경": {
                    sql = "update `parking`.`manager` set `manager` = '" + newPw.getText() + "' where (`manager` = '" + currentPw.getText() + "');";
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(p, "비밀번호가 변경되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                }break;
            }
            stmt.close();
            AdminMain.con.close();
        } catch (SQLException e) {
            System.err.println("연결 오류" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
    }
}//Pwchange class 종료
