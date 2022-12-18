package Login;

import java.awt.*;
import javax.swing.*;

import UserMain.UserMain;
import java.awt.event.*;
import java.sql.*;

public class Join extends JFrame implements ActionListener, ItemListener {
	// 선언
	JTextField j_name, j_id, j_email, j_car_num, j_phone_num, j_card_num, j_referral, select;
	JPasswordField j_pw1, j_pw2;
	int is_femail, is_handicap, is_smallcar;
	JCheckBox smallcar, femail, handicap;
	static boolean is_id_checked;

	public Join() { // Join창에 필요한 컴포넌트들 생성
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
		jCheckId.setBounds(280, 160, 85, 20);
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
					"root", "wldbs1004");
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
			// if 문 – ID 중복 확인 선택시
			if (s == "중복확인") {
				is_id_checked = true;
				
				strSql = "SELECT * FROM parking.user WHERE id='" + id + "';";

				ResultSet result = dbSt.executeQuery(strSql); // DB로부터 읽어온 레코드를 객체화
				while (result.next()) { // DB에 아이디 비교
					is_id_checked = false; // 같은 아이디 있으면 false로 바꿈
				}
				if (id.length()<4||id.length()>20||checkIdMethod(id)!=1) {
					JOptionPane.showMessageDialog(this, "사용 불가능한 id 입니다. 아이디는 영문과 숫자 조합만 가능하며 4~20자리 사이로 입력해주세요!", "확인창", JOptionPane.INFORMATION_MESSAGE);
					j_id.setText("");
				}
				else if (is_id_checked==true)
					JOptionPane.showMessageDialog(this, "사용 가능한 id 입니다.", "확인창", JOptionPane.INFORMATION_MESSAGE);
				else {
					JOptionPane.showMessageDialog(this, "사용 중인 id 입니다.", "확인창", JOptionPane.INFORMATION_MESSAGE);
					j_id.setText("");
				}
			} else if (s == "JOIN") { // 확인 버튼 선택시

				if (name.equals("") || id.equals("") || pw1.equals("") || pw2.equals("") || email.equals("")
						|| carNum.equals("") || phone.equals("") || cardNum.equals("")) {
					JOptionPane.showMessageDialog(this, "필수 사항을 입력해주세요!", "회원가입 안내창", JOptionPane.INFORMATION_MESSAGE);
				} else if (!pw1.equals(pw2)) {
					JOptionPane.showMessageDialog(this, "비밀번호가 같지 않습니다!", "회원가입 안내창", JOptionPane.INFORMATION_MESSAGE);

				} else if(is_id_checked == false) {
					 JOptionPane.showMessageDialog(this, "아이디 중복확인을 해주세요!", "회원가입 안내창",
					 JOptionPane.INFORMATION_MESSAGE);
				}
				else if(pw1.length()<4||id.length()>20) {
					JOptionPane.showMessageDialog(this, "비밀번호는 4~20자리 사이로 입력해주세요!", "회원가입 안내창", 
							JOptionPane.INFORMATION_MESSAGE);
				} 
				else if(emailCheck(email)==1){
					JOptionPane.showMessageDialog(this, "유효하지 않는 이메일 형식입니다!", "회원가입 안내창",
							JOptionPane.INFORMATION_MESSAGE);
				} 
				else if(carNum.length()<7||carNum.length()>8) {
					JOptionPane.showMessageDialog(this, "차번호 길이는 7~8자리입니다!", "회원가입 안내창", 
							JOptionPane.INFORMATION_MESSAGE);
				} 
				else if(phone.length()<10||phone.length()>11||isPhoneNumInt(phone)==1) {
					JOptionPane.showMessageDialog(this, "휴대폰 번호를 10~11자리 사이로 숫자만 입력해주세요", "회원가입 안내창", 
							JOptionPane.INFORMATION_MESSAGE);
				}
				else if(isCardNumInt(cardNum)==1||cardNum.length()!=16) {
					JOptionPane.showMessageDialog(this, "카드 길이 16자리를 지켜서 카드 번호의 숫자만 입력해주세요!", "회원가입 안내창", 
							JOptionPane.INFORMATION_MESSAGE);
				}else if(!referral_id.equals("")) {
					strSql = "SELECT * FROM parking.user WHERE id='" + referral_id + "';";
					ResultSet result = dbSt.executeQuery(strSql);
					if (!result.next()) {
						JOptionPane.showMessageDialog(this, "존재하지 않은 회원 아이디입니다.", "로그인창", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
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
					JOptionPane.showMessageDialog(this, "회원가입되었습니다", "확인창", JOptionPane.INFORMATION_MESSAGE);

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

					UserMain m = new UserMain(id);
					m.setTitle("주차 프로그램 - UserMain");
					m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					m.setSize(1000, 700);
					m.setVisible(true);
					m.setLocationRelativeTo(null);
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

	
	public int checkIdMethod(String id) {
		int code; int check = 0;
		char letter;
		
		for(int i=0; i<id.length(); i++) {
			letter = id.charAt(i);
			code = (int)letter;
			if((code>=65&&code<=90)||(code>=97&&code<=127)||(code<=48&&code>=57)) {
				check =1;
			}
		}
		return check;
	}
	
	public int emailCheck(String email) {
		int check = 0;
		if(email.indexOf("@")==-1) {
			check = 1;
		}
		return check;
	}
	public int isCardNumInt(String cardNum) {
		int check=0;
		for (int i = 0; i < cardNum.length(); i++) {
			int code = (int) cardNum.charAt(i);
			if(code < 48 || code > 57) {
				check = 1;
			}
		}
		return check;
	}
	public int isPhoneNumInt(String phone) {
		int check=0;
		for (int i = 0; i < phone.length(); i++) {
			int code = (int) phone.charAt(i);
			if(code < 48 || code > 57) {
				check = 1;
			}
		}
		return check;
	}
	 
}// Join 메소드 끝