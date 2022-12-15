package Test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Mypage extends JFrame implements ActionListener, ItemListener {

	JLabel title; // 마이페이지
	JLabel name; // 이름
	JLabel id; // 아이디
	JLabel passwd; // 비밀번호
	JLabel carn; // 차량번호
	JLabel card; // 카드번호
	JLabel call; // 전화번호
	JLabel special; // 특이사항
	JLabel email; // 이메일
	JButton update; // 수정하기 버튼
	JButton mov; // 이전으로 버튼
	JButton del; // 계정탈퇴 버튼
	JTextField name2, id2, passwd2, carn2, call2, card2, email2;
	JCheckBox smallcar, femail, handicap;
	int is_femail, is_handicap, is_smallcar;

	// 중복확인 버튼 클릭 유무

	public Mypage() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC","root", "wldbs1004");
			System.out.println("DB 연결 완료.");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			String strSql = "SELECT * FROM parking.user;"; // where id=getText.jTextI() //jTextI에서 친 아이디로 정보가져오기,,,
			ResultSet result = dbSt.executeQuery(strSql);

			Container ct = getContentPane();

			title = new JLabel("마이페이지");
			title.setBounds(140, 25, 70, 30);
			Font font = new Font("맑은 고딕", Font.BOLD, 20); // 폰트,굵기 지정
			title.setSize(150, 20);

			while (result.next()) {
				name = new JLabel("이름 :");
				name.setBounds(20, 60, 70, 30);

				name2 = new JTextField(result.getString("name"));
				name2.setBounds(70, 60, 70, 30);

				id = new JLabel("아이디 :");
				id.setBounds(20, 90, 70, 30);

				id2 = new JTextField(result.getString("id"));
				id2.setBounds(80, 90, 100, 30);

				passwd = new JLabel("비밀번호 :");
				passwd.setBounds(20, 120, 70, 30);

				passwd2 = new JTextField(result.getString("passwd"));
				passwd2.setBounds(80, 120, 100, 30);

				email = new JLabel("이메일 :");
				email.setBounds(20, 150, 70, 30);

				email2 = new JTextField(result.getString("email"));
				email2.setBounds(80, 150, 160, 30);

				carn = new JLabel("차량번호 :");
				carn.setBounds(20, 180, 70, 30);

				carn2 = new JTextField(result.getString("car_num"));
				carn2.setBounds(80, 180, 100, 30);

				call = new JLabel("전화번호 :");
				call.setBounds(20, 210, 70, 30);

				call2 = new JTextField(result.getString("phone_num"));
				call2.setBounds(80, 210, 100, 30);

				card = new JLabel("카드번호 :");
				card.setBounds(20, 240, 70, 30);

				card2 = new JTextField(result.getString("card_num"));
				card2.setBounds(80, 240, 100, 30);
			}

			special = new JLabel("특이사항 :                 (*해당되는 사항을 선택해주세요*)");
			special.setBounds(20, 270, 400, 30);

			smallcar = new JCheckBox("경차");
			smallcar.setBounds(20, 300, 60, 20);

			femail = new JCheckBox("여성");
			femail.setBounds(80, 300, 70, 20);

			handicap = new JCheckBox("장애인");
			handicap.setBounds(150, 300, 70, 20);

			update = new JButton("수정하기");
			update.setBounds(20, 330, 80, 30);
			update.setSize(90, 30);

			mov = new JButton("이전으로");
			mov.setBounds(130, 330, 80, 30);
			mov.setSize(90, 30);

			del = new JButton("계정탈퇴");
			del.setBounds(280, 350, 80, 80);
			del.setSize(90, 20);

			ct.setLayout(null);
			title.setFont(font);
			ct.add(title);
			ct.add(name);
			ct.add(name2);
			ct.add(id);
			ct.add(id2);
			ct.add(passwd);
			ct.add(passwd2);
			ct.add(email);
			ct.add(email2);
			ct.add(carn);
			ct.add(carn2);
			ct.add(call);
			ct.add(call2);
			ct.add(card);
			ct.add(card2);
			ct.add(update);
			ct.add(mov);
			ct.add(del);
			ct.add(smallcar);
			ct.add(special);
			ct.add(femail);
			ct.add(handicap);
			update.addActionListener(this);
			del.addActionListener(this);
			mov.addActionListener(this);
			smallcar.addItemListener(this);
			femail.addItemListener(this);
			handicap.addItemListener(this);

			dbSt.close();
			con.close(); // DB연동 끊기
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}

	}

	public void actionPerformed(ActionEvent ae) {
		String t_name = "", t_id = "", t_passwd = "", t_carn = "", t_call = "", t_card = "", t_email = "";

		t_name = name2.getText();
		t_id = id2.getText();
		t_passwd = passwd2.getText();
		t_carn = carn2.getText();
		t_call = call2.getText();
		t_card = card2.getText();
		t_email = email2.getText();

		String s = ae.getActionCommand();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC","root", "wldbs1004");
			System.out.println("DB 연결 완료.");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			String strSql;

			if (s == "수정하기") { // 수정하기 버튼 누를 시 팝업 이벤트발생, 적힌 정보 DB에 업데이트

				strSql = "UPDATE user SET name='" + t_name + "',id='" + t_id + "',passwd='" + t_passwd + "',car_num='"
						+ t_carn + "',phone_num='" + t_call + "',card_num='" + t_card + "',email='" + t_email
						+ "'WHERE id='" + id2.getText() + "';";
				dbSt.executeUpdate(strSql); // 개인정보 수정
				strSql = "UPDATE parking.user_special_needs SET id='" + t_id + "',woman='" + is_femail + "',small_car='"
						+ is_smallcar + "',handicap='" + is_handicap + "'WHERE id='" + id2.getText() + "';";
				dbSt.executeUpdate(strSql); // 특이사항 수정

				JOptionPane.showMessageDialog(this, "정보가 수정되었습니다.", "수정완료", JOptionPane.INFORMATION_MESSAGE);
			}

			if (s == "계정탈퇴") {// 계정탈퇴 버튼 누를 시 팝업 이벤트발생

				int answer = JOptionPane.showConfirmDialog(this, "정말 계정을 삭제하시겠습니까?", "계정탈퇴",
						JOptionPane.OK_CANCEL_OPTION);

				if (answer == JOptionPane.YES_OPTION) { // 사용자가 확인을 눌렀을 떄 DB 정보 삭제
					strSql = "DELETE FROM user WHERE id='" + id2.getText() + "';";
					dbSt.executeUpdate(strSql);
					JOptionPane.showMessageDialog(this, "계정이 삭제되었습니다.", "계정탈퇴", JOptionPane.INFORMATION_MESSAGE);

					Login login = new Login();
					login.setTitle("LOGIN");
					login.setSize(400, 600);
					login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					login.setVisible(true); // DB정보 삭제 후 로그인화면으로 전환!!!!!!!
					dispose();
				} 

			} else { // 사용자가 취소를 눌렀을 때 팝업창 사라짐
			}
			if (s == "이전으로") {
				dispose();
			}

			dbSt.close();
			con.close(); // DB연동 끊기

		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	} // 액션이벤트 끝

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (e.getItem() == femail) {
				is_femail = 1;
			} else if (e.getItem() == smallcar) {
				is_smallcar = 1;
			} else if (e.getItem() == handicap) {
				is_handicap = 1;
			}
		}
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			if (e.getItem() == femail) {
				is_femail = 0;
			} else if (e.getItem() == smallcar) {
				is_smallcar = 0;
			} else if (e.getItem() == handicap) {
				is_handicap = 0;
			}

		}
	}

	public static void main(String[] args) {
		Mypage mg = new Mypage();
		mg.setSize(400, 430);
		mg.setLocation(400, 0);
		mg.setTitle("마이페이지");
		mg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mg.setVisible(true);

	}

}
