package test_Javateam;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class Login extends JFrame implements ActionListener {
	JTextField lgId;
	JPasswordField lgPw;

	Login() {
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
				String id, pw;
				id = lgId.getText();
				pw = lgPw.getText();
				strSql = "SELECT * FROM user WHERE id='" + id + "'and passwd = '" + pw + "';";
				ResultSet result = dbSt.executeQuery(strSql);
				if (result.next()) {
					JOptionPane.showMessageDialog(this, "반갑습니다.", "로그인창", JOptionPane.INFORMATION_MESSAGE);
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
			MngLogin mngLogin = new MngLogin();
			mngLogin.setTitle("MANAGER LOGIN");
			mngLogin.setSize(300, 200);
			mngLogin.setLocationRelativeTo(null);
			mngLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mngLogin.setVisible(true);
		}
	}// Login 이벤트 처리 끝
}// Login 매소드 끝

class Join extends JFrame implements ActionListener, ItemListener {
	// 선언
	JTextField j_name, j_id, j_email, j_car_num, j_phone_num, j_card_num, j_referral;
	JPasswordField j_pw1, j_pw2;
	int is_femail, is_handicap, is_smallcar;
	JCheckBox smallcar, femail, handicap;

	Join() { // Join창에 필요한 컴포넌트들 생성
		JLabel jTitle = new JLabel("JOIN");
		jTitle.setFont(jTitle.getFont().deriveFont(45.0f));
		JLabel jinfo = new JLabel("*표시는 필수 사항입니다.");
		JLabel jTextN = new JLabel("*이름 : ");
		j_name = new JTextField();
		JLabel jTextI = new JLabel("*아이디 : ");
		j_id = new JTextField();
		JButton jCheckId = new JButton("중복확인");
		JLabel jTextPw1 = new JLabel("*비밀번호 : ");
		j_pw1 = new JPasswordField();
		JLabel jTextPw2 = new JLabel("*비밀번호 재입력 : ");
		j_pw2 = new JPasswordField();
		JLabel jTextE = new JLabel("*이메일 : ");
		j_email = new JTextField();
		JLabel jTextCar = new JLabel("*차량번호 : ");
		j_car_num = new JTextField();
		JLabel jTextP = new JLabel("*전화번호 : ");
		j_phone_num = new JTextField();
		JLabel jTextCard = new JLabel("*카드번호 : ");
		j_card_num = new JTextField();
		JLabel jTextS = new JLabel("해당 사항 선택 : ");
		smallcar = new JCheckBox("경차");
		femail = new JCheckBox("여성");
		handicap = new JCheckBox("장애인");
		JLabel jTextRcm = new JLabel("추천인ID : ");
		j_referral = new JTextField();
		JButton jBtn = new JButton("JOIN"); // 필요한 컴포넌트들 생성 끝

		// 컴포넌트에 위치 크기 설정
		jTitle.setBounds(140, 10, 200, 100);
		jinfo.setBounds(20, 100, 150, 20);
		jTextN.setBounds(20, 130, 100, 20);
		j_name.setBounds(150, 130, 100, 20);
		jTextI.setBounds(20, 160, 100, 20);
		j_id.setBounds(150, 160, 100, 20);
		jCheckId.setBounds(270, 160, 90, 20);
		jTextPw1.setBounds(20, 190, 100, 20);
		j_pw1.setBounds(150, 190, 100, 20);
		jTextPw2.setBounds(20, 220, 120, 20);
		j_pw2.setBounds(150, 220, 100, 20);
		jTextE.setBounds(20, 250, 100, 20);
		j_email.setBounds(150, 250, 150, 20);
		jTextCar.setBounds(20, 280, 100, 20);
		j_car_num.setBounds(150, 280, 150, 20);
		jTextP.setBounds(20, 310, 100, 20);
		j_phone_num.setBounds(150, 310, 150, 20);
		jTextCard.setBounds(20, 340, 100, 20);
		j_card_num.setBounds(150, 340, 150, 20);
		jTextS.setBounds(20, 380, 200, 20);
		smallcar.setBounds(40, 410, 80, 20);
		femail.setBounds(150, 410, 80, 20);
		handicap.setBounds(270, 410, 80, 20);
		jTextRcm.setBounds(20, 450, 100, 20);
		j_referral.setBounds(150, 450, 100, 20);
		jBtn.setBounds(160, 520, 80, 20);

		Container joinCt = getContentPane();
		joinCt.add(jTitle);
		// 컨테이너에 컴포넌트 부착
		joinCt.add(jinfo);
		joinCt.add(jTextN);
		joinCt.add(j_name);
		joinCt.add(jTextI);
		joinCt.add(j_id);
		joinCt.add(jCheckId);
		joinCt.add(jTextPw1);
		joinCt.add(j_pw1);
		joinCt.add(jTextPw2);
		joinCt.add(j_pw2);
		joinCt.add(jTextE);
		joinCt.add(j_email);
		joinCt.add(jTextCar);
		joinCt.add(j_car_num);
		joinCt.add(jTextP);
		joinCt.add(j_phone_num);
		joinCt.add(jTextCard);
		joinCt.add(j_card_num);
		joinCt.add(jTextS);
		joinCt.add(smallcar);
		joinCt.add(femail);
		joinCt.add(handicap);
		joinCt.add(jTextRcm);
		joinCt.add(j_referral);
		joinCt.add(jBtn);

		jCheckId.addActionListener(this);
		jBtn.addActionListener(this);

		smallcar.addActionListener(this);
		smallcar.addItemListener(this);
		femail.addActionListener(this);
		femail.addItemListener(this);
		handicap.addActionListener(this);
		handicap.addItemListener(this);

		joinCt.setLayout(null);
		joinCt.setBounds(0, 0, 400, 600);

	}// Join 생성자 끝

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String name, id, pw1, pw2, email, carNum, phone, cardNum, referral_id;
		// 중복확인 버튼 클릭 유무
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC 드라이버를 정상적으로 로드했습니다.");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC",
					"root", "root");
			System.out.println("DB 연결 완료");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			String strSql;
			// 사용자로부터 입력받은 값 문자열로 변환
			name = j_name.getText();
			id = j_id.getText();
			email = j_email.getText();
			carNum = j_car_num.getText();
			phone = j_phone_num.getText();
			cardNum = j_card_num.getText();
			referral_id = j_referral.getText();
			pw1 = j_pw1.getText();
			pw2 = j_pw2.getText();
			String msg;
			// if 문 – ID 중복 확인 선택시
			if (s == "중복확인") {
				strSql = "SELECT * FROM parking.user WHERE id='" + id + "';";
				boolean ok = true;

				ResultSet result = dbSt.executeQuery(strSql); // DB로부터 읽어온 레코드를 객체화
				while (result.next()) // DB에 아이디 비교
					ok = false; // 같은 아이디 있으면 false로 바꿈
				if (ok)
					msg = "사용 가능한 id입니다!"; // ok == true 의 의미
				else {
					msg = "사용중인 id입니다!";
					j_id.setText("");
				}

				JOptionPane.showMessageDialog(this, msg, "확인창", JOptionPane.INFORMATION_MESSAGE);
			} else if (s == "JOIN") { // 확인 버튼 선택시

				if (name.equals("") || id.equals("") || pw1.equals("") || pw2.equals("") || email.equals("")
						|| carNum.equals("") || phone.equals("") || cardNum.equals("")) {
					JOptionPane.showMessageDialog(this, "필수 사항을 입력해주세요!", "회원가입 안내창", JOptionPane.INFORMATION_MESSAGE);
				} else if (!pw1.equals(pw2)) {
					JOptionPane.showMessageDialog(this, "비밀번호가 같지 않습니다!", "회원가입 안내창", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 사항 입력
					strSql = "INSERT INTO user(id, passwd, name, email, phone_num, car_num, card_num, referral_id, point )VALUES('"
							+ id + " ',' " + pw1 + " ','" + name + " ',' " + email + "','" + phone + " ','" + carNum
							+ " ',' " + cardNum + "','" + referral_id + " ',' " + 0 + " ');";
					dbSt.executeUpdate(strSql); // sql 질의어 실행
					// 선택 사항 입력
					strSql = "INSERT INTO user_special_needs(id, woman, small_car, handicap)VALUES('" + id + " ',' "
							+ is_femail + "','" + is_smallcar + "','" + is_handicap + " ');";
					dbSt.executeUpdate(strSql);
					if (!referral_id.equals("")) {
						strSql = "UPDATE `parking`.`user` set `point` = point + '5000' WHERE (`id` = '" + referral_id
								+ "');";
						dbSt.executeUpdate(strSql);
						System.out.println(strSql);
					}
					JOptionPane.showMessageDialog(this, "회원가입되었습니다", "확인창", JOptionPane.INFORMATION_MESSAGE);// 회원가입되었다는
																												// 메시지 창
																												// 띄우기
					j_name.setText("");
					j_id.setText("");
					j_email.setText("");
					j_car_num.setText("");
					j_phone_num.setText("");
					j_card_num.setText("");
					j_referral.setText("");
					j_pw1.setText("");
					j_pw2.setText("");
					femail.setSelected(false);
					smallcar.setSelected(false);
					handicap.setSelected(false);
					System.out.println("데이터 삽입 완료");
				}

			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}

	}// join 액션이벤트

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED)
			if (e.getItem() == femail) {
				is_femail = 1;
			} else if (e.getItem() == smallcar) {
				is_smallcar = 1;
			} else if (e.getItem() == handicap) {
				is_handicap = 1;
			}
	}// JOIN 아이템이벤트 끝
}// Join 메소드 끝

class MngLogin extends JFrame implements ActionListener {
	JPasswordField mngPw;

	MngLogin() {
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

public class Total_Login {

	public static void main(String[] args) {
		Login login = new Login();
		login.setTitle("LOGIN");
		login.setSize(400, 600);
		login.setLocationRelativeTo(null);
		login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		login.setVisible(true);
	}

}