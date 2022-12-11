package totalLogin;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class Login extends JFrame implements ActionListener{
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
		lgId.setBounds(200, 320, 100, 20 );
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
		
	}//Login 생성자 끝
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		if(s=="LOGIN") {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드했습니다.");
			}catch(ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection
				("jdbc:mysql://localhost:3306/purchase?serverTimezone=UTC", "root", "root");
				System.out.println("DB 연결 완료했습니다.");
				Statement dbSt = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				
				String strSql; String id, pw;
				id = lgId.getText(); pw = lgPw.getText();
				strSql = "SELECT * FROM user WHERE user_id='"+id+"'and passwd = '"+pw+"';";
				ResultSet result = dbSt.executeQuery(strSql);
				if (result.next())
				{
					JOptionPane.showMessageDialog(this, "반갑습니다.", "로그인창",
							JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(this, "아이디나 비밀번호를 다시 확인해주세요.", "로그인실패창",
							JOptionPane.INFORMATION_MESSAGE);
				}dbSt.close();
				con.close();
			}catch(SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}
			//로그인시 넘어갈 화면 연결
		}else if(s=="JOIN") {
			Join join = new Join();
			join.setTitle("JOIN");
			join.setSize(400, 600);
			join.setLocationRelativeTo(null);
			join.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			join.setVisible(true);
		}else if(s=="관리자") {
			MngLogin mngLogin = new MngLogin();
			mngLogin.setTitle("MANAGER LOGIN");
			mngLogin.setSize(300, 200);
			mngLogin.setLocationRelativeTo(null);
			mngLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mngLogin.setVisible(true);
		}
	}//Login 이벤트 처리 끝
}//Login 매소드 끝	

class Join extends JFrame implements ActionListener{
	JTextField jName, jId, jEmail, jCarNum, jPhone, jCardNum, jRecomm, select;
	JPasswordField jPw1, jPw2;
	int isFemail, isDisable, isSmallcar;
	JCheckBox jSmallcar, jFemail, jDisable;
	
	Join() {
		JLabel jTitle = new JLabel("JOIN");
		jTitle.setFont(jTitle.getFont().deriveFont(45.0f));
		JLabel jinfo = new JLabel("*표시는 필수 사항입니다.");
		JLabel jTextN = new JLabel("*이름 : ");
		jName = new JTextField(8);
		JLabel jTextI = new JLabel("*아이디 : ");
		jId = new JTextField(8);
		JButton jCheckId = new JButton("중복확인"); //check overlap
		JLabel jTextPw1 = new JLabel("*비밀번호 : ");
		jPw1 = new JPasswordField(8);
		JLabel jTextPw2 = new JLabel("*비밀번호 재입력 : ");
		jPw2 = new JPasswordField(8);
		JLabel jTextE = new JLabel("*이메일 : ");
		jEmail = new JTextField(20);
		JLabel jTextCar = new JLabel("*차량번호 : ");
		jCarNum = new JTextField(8);
		JLabel jTextP = new JLabel("*전화번호 : ");
		jPhone = new JTextField(8);
		JLabel jTextCard = new JLabel("*카드번호 : ");
		jCardNum = new JTextField(16);
		JLabel jTextS = new JLabel("해당 사항 선택 : ");
		jSmallcar = new JCheckBox("경차");
		jFemail = new JCheckBox("여성");
		jDisable = new JCheckBox("장애인");
		JLabel jTextRcm = new JLabel("추천인ID : ");
		jRecomm = new JTextField(8);
		select = new JTextField("");
		JButton jBtn = new JButton("JOIN");
		
		
		//컴포넌트에 위치 크기 설정
		jTitle.setBounds(140, 10, 200, 100);
		jinfo.setBounds(20, 100, 150, 20);
		jTextN.setBounds(20, 130, 100, 20);
		jName.setBounds(150, 130, 100, 20);
		jTextI.setBounds(20, 160, 100, 20);
		jId.setBounds(150, 160, 100, 20);
		jCheckId.setBounds(270, 160, 90, 20);
		jTextPw1.setBounds(20, 190, 100, 20);
		jPw1.setBounds(150, 190, 100, 20);
		jTextPw2.setBounds(20, 220, 120, 20);
		jPw2.setBounds(150, 220, 100, 20);
		jTextE.setBounds(20, 250, 100, 20);
		jEmail.setBounds(150, 250, 150, 20);
		jTextCar.setBounds(20, 280, 100, 20);
		jCarNum.setBounds(150, 280, 150, 20);
		jTextP.setBounds(20, 310, 100, 20);
		jPhone.setBounds(150, 310, 150, 20);
		jTextCard.setBounds(20, 340, 100, 20);
		jCardNum.setBounds(150, 340, 150, 20);
		jTextS.setBounds(20, 380, 200, 20);
		jSmallcar.setBounds(40, 410, 80, 20);
		jFemail.setBounds(150, 410, 80, 20);
		jDisable.setBounds(270, 410, 80, 20);
		jTextRcm.setBounds(20, 450, 100, 20);
		jRecomm.setBounds(150, 450, 100, 20);
		select.setBounds(20, 480, 200, 40);
		jBtn.setBounds(160, 520, 80, 20);
		
		
		Container joinCt = getContentPane();
		joinCt.add(jTitle);
		
		joinCt.add(jinfo);
		joinCt.add(jTextN);
		joinCt.add(jName);
		joinCt.add(jTextI);
		joinCt.add(jId);
		joinCt.add(jCheckId);
		joinCt.add(jTextPw1);
		joinCt.add(jPw1);
		joinCt.add(jTextPw2);
		joinCt.add(jPw2);
		joinCt.add(jTextE);
		joinCt.add(jEmail);
		joinCt.add(jTextCar);
		joinCt.add(jCarNum);
		joinCt.add(jTextP);
		joinCt.add(jPhone);
		joinCt.add(jTextCard);
		joinCt.add(jCardNum);
		joinCt.add(jTextS);
		joinCt.add(jSmallcar);
		joinCt.add(jFemail);
		joinCt.add(jDisable);
		joinCt.add(jTextRcm);
		joinCt.add(jRecomm);
		joinCt.add(jBtn);
		
		jCheckId.addActionListener(this);
		jBtn.addActionListener(this);
		
		jSmallcar.addActionListener(this); 
		jFemail.addActionListener(this);   
		jDisable.addActionListener(this);
		
		joinCt.setLayout(null);
		joinCt.setBounds(0,0,400, 600);	
			
	}//Join 생성자 끝
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String name, id, pw1, pw2, email, carNum, phone, cardNum, referal_id;
		boolean is_idCheck=true; //중복확인 버튼 클릭 유무
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC 드라이버를 정상적으로 로드했습니다.");
		}catch(ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/purchase?serverTimezone=UTC", 
							"root", "root");
			System.out.println("DB 연결 완료");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다."); 
			String strSql;
			name = jName.getText(); id = jId.getText(); email = jEmail.getText(); carNum=jCarNum.getText(); 
			phone = jPhone.getText(); cardNum = jCardNum.getText(); referal_id=jRecomm.getText();
			pw1 = jPw1.getText(); pw2=jPw2.getText();
			if(s=="중복확인") {
				strSql = "SELECT * FROM user WHERE user_id =' "+ id + "';";
				String msg;
				ResultSet result = dbSt.executeQuery(strSql);
				while (result.next()) {
					is_idCheck = false;
					System.out.println("호호");
					}
				System.out.println("하하");
				if (is_idCheck==true) msg = "사용가능한 id입니다.";
				
				else msg = "사용중인 id입니다.";
				JOptionPane.showMessageDialog(this, msg, "확인창",
						JOptionPane.INFORMATION_MESSAGE);
			}
			// 선택 사항 처리
			if(jSmallcar.isSelected()) {	
				isSmallcar = 1;	
			}
			if(jFemail.isSelected()) {
				isFemail = 1;
			}
			if(jDisable.isSelected()) {
				isDisable = 1;
			}
			if(s=="JOIN"){
				//필수사항 입력 확인하는 조건문
				if(name.equals("")|| id.equals("")||pw1.equals("")||pw2.equals("")|| email.equals("")||carNum.equals("")||phone.equals("")||cardNum.equals("")) {
					JOptionPane.showMessageDialog(this, "필수 사항을 입력해주세요!", "회원가입 안내창",
							JOptionPane.INFORMATION_MESSAGE);
				}else if(!pw1.equals(pw2)){
					JOptionPane.showMessageDialog(this, "비밀번호가 같지 않습니다!", "회원가입 안내창",
							JOptionPane.INFORMATION_MESSAGE);
				}
				else if(is_idCheck=false){
					JOptionPane.showMessageDialog(this, "아이디 중복확인을 해주세요!", "회원가입 안내창",
							JOptionPane.INFORMATION_MESSAGE);
				}else {
					strSql = "INSERT INTO user(user_id, passwd, name, email, phone_num, car_num, card_num, referal_id, point )VALUES('"+ id + " ',' "+ pw1 +" ','" + name +" ',' " + email + "','" + phone + " ','" 
							+carNum+ " ',' " + cardNum + "','" +  referal_id + " ',' " + 0 + " ');";
					dbSt.executeUpdate(strSql);
					strSql = "INSERT INTO user_special_needs(user_id, woman, small_car, handicap)VALUES('"+ id + " ',' "+ isFemail + "','"+ isSmallcar + "','"+isDisable + " ');";
					dbSt.executeUpdate(strSql);
					if (!referal_id.equals("")) {
						strSql = "UPDATE user SET point = point + 5000 where referal_id='"+referal_id+"';";
						dbSt.executeUpdate(strSql);
					}
					JOptionPane.showMessageDialog(this, "회원가입되었습니다", "확인창",
							JOptionPane.INFORMATION_MESSAGE);
					System.out.println("데이터 삽입이 완료되었습니다.");
				}
				
				
			}
		}catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}//JOIN 액션이벤트 끝
}//Join 메소드 끝
class MngLogin extends JFrame implements ActionListener{
	JPasswordField mngPw;
	
	MngLogin (){
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
	
	}//MngLogin 생성자 끝
	//액션이벤트
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if (s.equals("LOGIN")) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드했습니다.");
			}catch(ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection
			("jdbc:mysql://localhost:3306/parkingmanager?serverTimezone=UTC", "root", "root");
				System.out.println("DB 연결이 완료되었습니다.");
				Statement dbSt = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				String strSql; String t_passwd;
				t_passwd = mngPw.getText();
				strSql = "SELECT*FROM parkingManager WHERE password='"+t_passwd+"';";
				ResultSet result = dbSt.executeQuery(strSql);
				if(result.next()) {
					JOptionPane.showMessageDialog(this, "매니저 계정으로 로그인 되었습니다.", "매니처로그인창",
							JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(this, "매니저 비밀번호가 틀렸습니다.", "매니저로그인창",
							JOptionPane.INFORMATION_MESSAGE);
				}dbSt.close();
				con.close();
			}catch(SQLException e) {
				System.out.println("SQLException: "+e.getMessage());
			}
		}
	} //mngLogin 액션이벤트 끝
}	//MngLogin 메소드 끝
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
