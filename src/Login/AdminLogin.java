package Login;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminLogin extends JFrame implements ActionListener {
	JPasswordField mngPw;

	AdminLogin() {
		JLabel mngTitle = new JLabel("관리자 로그인");
		mngTitle.setFont(mngTitle.getFont().deriveFont(15.0f));
		JLabel mngTextPw = new JLabel("PASSWORD : ");
		mngPw = new JPasswordField(8);
		JButton mngLgBtn = new JButton("LOGIN");

		mngTitle.setBounds(100, 20, 200, 30);
		mngTextPw.setBounds(40, 80, 100, 20);
		mngPw.setBounds(150, 80, 100, 20);
		mngLgBtn.setBounds(110, 120, 80, 20);

		Container mngLgCt = getContentPane();
		mngLgCt.setLayout(null);
		mngLgCt.add(mngTitle);
		mngLgCt.add(mngTextPw);
		mngLgCt.add(mngPw);
		mngLgCt.add(mngLgBtn);

		mngLgBtn.addActionListener(this);

	}// MngLogin 생성자 끝
		// 액션이벤트

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if (s.equals("LOGIN")) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드했습니다.");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC",
						"root", "root");
				System.out.println("DB 연결이 완료되었습니다.");
				Statement dbSt = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				String strSql;
				String passwd;
				passwd = mngPw.getText();
				strSql = "SELECT*FROM parking.manager WHERE manager='" + passwd + "';";
				ResultSet result = dbSt.executeQuery(strSql);
				if (result.next()) {
					JOptionPane.showMessageDialog(this, "매니저 계정으로 로그인 되었습니다.", "매니처로그인창",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "매니저 비밀번호가 틀렸습니다.", "매니저로그인창", JOptionPane.INFORMATION_MESSAGE);
				}
				dbSt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}
		}
	} // mngLogin 액션이벤트 끝
} // MngLogin 메소드 끝