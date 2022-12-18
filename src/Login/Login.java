package Login;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import UserMain.*;

import javax.swing.table.DefaultTableModel;

public class Login extends JFrame implements ActionListener {

	JTextField lgId;
	JPasswordField lgPw;
	String id, pw;

	public Login() {
		ImageIcon carImg = new ImageIcon("images/carImg.jpg");
		JLabel lgCarPic = new JLabel(carImg);
		JLabel lgTextI = new JLabel("ID : ");
		lgId = new JTextField();
		JLabel lgTextPw = new JLabel("PASSWORD : ");
		lgPw = new JPasswordField();
		JButton loginBtn = new JButton("LOGIN");
		JButton joinBtn = new JButton("JOIN");
		JButton mngBtn = new JButton("관리자");

		lgCarPic.setBounds(100, 60, 200, 178);
		lgTextI.setBounds(40, 320, 100, 20);
		lgId.setBounds(200, 320, 100, 20);
		lgTextPw.setBounds(40, 350, 100, 20);
		lgPw.setBounds(200, 350, 100, 20);
		loginBtn.setBounds(50, 440, 90, 20);
		joinBtn.setBounds(150, 440, 90, 20);
		mngBtn.setBounds(250, 440, 90, 20);

		Container loginCt = getContentPane();
		loginCt.setLayout(null);
		loginCt.add(lgCarPic);
		loginCt.add(lgTextI);
		loginCt.add(lgId);
		loginCt.add(lgTextPw);
		loginCt.add(lgPw);
		loginCt.add(loginBtn);
		loginCt.add(joinBtn);
		loginCt.add(mngBtn);

		loginBtn.addActionListener(this);
		joinBtn.addActionListener(this);
		mngBtn.addActionListener(this);
		
		
	}// Login 생성자 끝

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();

		if (s == "LOGIN") {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드했습니다.");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC",
						"root", "root");
				System.out.println("DB 연결 완료했습니다.");
				Statement dbSt = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

				String strSql;

				id = lgId.getText();
				pw = lgPw.getText();
				strSql = "SELECT * FROM user WHERE id='" + id + "'and passwd = '" + pw + "';";
				ResultSet result = dbSt.executeQuery(strSql);
				if (result.next()) {
					JOptionPane.showMessageDialog(this, "반갑습니다.", "로그인창", JOptionPane.INFORMATION_MESSAGE);
					UserMain m = new UserMain(id);
					m.setTitle("주차 프로그램 - UserMain");
					m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					m.setSize(1000, 700);
					m.setVisible(true);
					m.setLocationRelativeTo(null);
				} else {
					JOptionPane.showMessageDialog(this, "아이디나 비밀번호를 다시 확인해주세요.", "로그인실패창",
							JOptionPane.INFORMATION_MESSAGE);
				}
				dbSt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}
			// 로그인시 넘어갈 화면 연결
		} else if (s == "JOIN") {
			Join join = new Join();
			join.setTitle("JOIN");
			join.setSize(400, 600);
			join.setLocationRelativeTo(null);
			join.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			join.setVisible(true);
		} else if (s == "관리자") {
			AdminLogin mngLogin = new AdminLogin();
			mngLogin.setTitle("MANAGER LOGIN");
			mngLogin.setSize(300, 200);
			mngLogin.setLocationRelativeTo(null);
			mngLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mngLogin.setVisible(true);
		}
	}// Login 이벤트 처리 끝


	public static void main(String[] args) {

		Login login = new Login();
		login.setTitle("LOGIN");
		login.setSize(400, 600);
		login.setLocationRelativeTo(null);
		login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		login.setVisible(true);

	}// Login 매소드 끝
}