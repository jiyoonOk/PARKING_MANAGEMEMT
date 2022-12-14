import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class Login extends JFrame implements ActionListener{

	String user = "root", passwd = "0000";
	JTextField lgId;
	JPasswordField lgPw;
	Login() {
		ImageIcon carImg = new ImageIcon("images/carImg.jpg");
		JLabel lgCarPic = new JLabel(carImg);
		JLabel lgTextI = new JLabel("ID : ");
		JTextField lgId = new JTextField();   
		JLabel lgTextPw = new JLabel("PASSWORD : ");
		JTextField lgPw = new JPasswordField();
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
				("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC", user, passwd);
				System.out.println("DB 연결 완료했습니다.");
				Statement dbSt = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				
				String strSql; String t_lgId, t_lgPw;
				t_lgId = lgId.getText(); t_lgPw = lgPw.getText();
				
				strSql = "SELECT * FROM parkingLogin WHERE id='"+t_lgId+"'and password = '"+t_lgPw+"';";
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
			join.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			join.setVisible(true);
		}else if(s=="관리자") {
			MngLogin mngLogin = new MngLogin();
			mngLogin.setTitle("MANAGER LOGIN");
			mngLogin.setSize(300, 200);
			mngLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mngLogin.setVisible(true);
		}
	}//Login 이벤트 처리 끝
}//Login 매소드 끝	

class Join extends JFrame implements ActionListener {
	JTextField jName, jId, jEmail, jCarNum, jPhone, jCardNum, jRecomm;
	JPasswordField jPw1, jPw2;
	
	Join() {
		JLabel jTitle = new JLabel("JOIN");
		jTitle.setFont(jTitle.getFont().deriveFont(45.0f));
		JLabel jTextN = new JLabel("이름 : ");
		jName = new JTextField(8);
		JLabel jTextI = new JLabel("아이디 : ");
		jId = new JTextField(8);
		JButton jCheckId = new JButton("중복확인"); //check overlap
		JLabel jTextPw1 = new JLabel("비밀번호 : ");
		jPw1 = new JPasswordField(8);
		JLabel jTextPw2 = new JLabel("비밀번호 재입력 : ");
		//JLabel jCheckPw = new JLabel(""); //
		jPw2 = new JPasswordField(8);
		JLabel jTextE = new JLabel("이메일 : ");
		jEmail = new JTextField(20);
		JLabel jTextCar = new JLabel("차량번호 : ");
		jCarNum = new JTextField(8);
		JLabel jTextP = new JLabel("전화번호 : ");
		jPhone = new JTextField(8);
		JLabel jTextCard = new JLabel("카드번호 : ");
		jCardNum = new JTextField(16);
		JLabel jTextS = new JLabel("해당 사항 선택 : ");
		JCheckBox jLightCar = new JCheckBox("경차");
		JCheckBox jFemail = new JCheckBox("여성");
		JCheckBox jDisable = new JCheckBox("장애인");
		JLabel jTextRcm = new JLabel("추천인ID : ");
		jRecomm = new JTextField(8);
		JButton jBtn = new JButton("JOIN");
		
		//컴포넌트에 위치 크기 설정
		jTitle.setBounds(140, 10, 200, 100);
		jTextN.setBounds(20, 100, 100, 20);
		jName.setBounds(150, 100, 100, 20);
		jTextI.setBounds(20, 130, 100, 20);
		jId.setBounds(150, 130, 100, 20);
		jCheckId.setBounds(270, 130, 90, 20);
		jTextPw1.setBounds(20, 170, 100, 20);
		jPw1.setBounds(150, 170, 100, 20);
		//비밀번호 위에것과 맞게 췄는지 체크
		jTextPw2.setBounds(20, 200, 100, 20);
		jPw2.setBounds(150, 200, 100, 20);
		//jCheckPw.setBounds(40, 230, 100, 20);
		jTextE.setBounds(20, 260, 100, 20);
		jEmail.setBounds(150, 260, 150, 20);
		jTextCar.setBounds(20, 290, 100, 20);
		jCarNum.setBounds(150, 290, 150, 20);
		jTextP.setBounds(20, 320, 100, 20);
		jPhone.setBounds(150, 320, 100, 20);
		jTextCard.setBounds(20, 350, 100, 20);
		jCardNum.setBounds(150, 350, 100, 20);
		jTextS.setBounds(20, 380, 200, 20);
		jLightCar.setBounds(40, 410, 80, 20);
		jFemail.setBounds(150, 410, 80, 20);
		jDisable.setBounds(270, 410, 80, 20);
		jTextRcm.setBounds(20, 440, 100, 20);
		jRecomm.setBounds(150, 440, 100, 20);
		jBtn.setBounds(160, 470, 80, 20);
		
		JPanel jOption = new JPanel();
		
		jOption.add(jTextN);
		jOption.add(jName);
		jOption.add(jTextI);
		jOption.add(jId);
		jOption.add(jCheckId);
		jOption.add(jTextPw1);
		jOption.add(jPw1);
		jOption.add(jTextPw2);
		jOption.add(jPw2);
		jOption.add(jTextE);
		jOption.add(jEmail);
		jOption.add(jTextCar);
		jOption.add(jCarNum);
		jOption.add(jTextP);
		jOption.add(jPhone);
		jOption.add(jTextCard);
		jOption.add(jCardNum);
		jOption.add(jTextS);
		jOption.add(jLightCar);
		jOption.add(jFemail);
		jOption.add(jDisable);
		jOption.add(jTextRcm);
		jOption.add(jRecomm);
		jOption.add(jBtn);
		
		jCheckId.addActionListener(this);
		jBtn.addActionListener(this);
		
		Container joinCt = getContentPane();
		joinCt.add(jTitle);
		joinCt.setLayout(null);
		joinCt.add(jOption);
		jOption.setLayout(null);
		jOption.setBounds(0,0,400, 600);	
		
		
		
	}//Join 생성자 끝
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String t_jName, t_jId, t_jEmail, t_jCarNum, t_jPhone, t_jCardNum, t_jRecomm,
		t_jPw1, strSql;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC 드라이버를 정상적으로 로드했습니다.");
		}catch(ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC", 
							"root", "root");
			System.out.println("DB 연결 완료");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			t_jId = jId.getText();  
			
			if(s=="중복확인") {
				strSql = "SELECT * FROM user WHERE id =' "+ t_jId + "';";
				boolean ok = true; String msg;
				ResultSet result = dbSt.executeQuery(strSql);
				while (result.next())
					ok = false;
				if (ok) msg = "사용가능한 id입니다.";
				else msg = "사용중인 id입니다.";
				JOptionPane.showMessageDialog(this, msg, "확인창",
						JOptionPane.INFORMATION_MESSAGE);
			}else if(s=="JOIN"){
				t_jName = jName.getText(); t_jId = jId.getText(); t_jEmail = jEmail.getText(); t_jCarNum=jCarNum.getText(); 
				t_jPhone = jPhone.getText(); t_jCardNum = jCardNum.getText(); t_jRecomm=jRecomm.getText();
				t_jPw1 = jPw1.getText();
				strSql = "INSERT INTO join(name, id, passwd, email, phone, carNum )VALUES('"+ t_jName + " ',' "+ t_jId +" ','" + t_jEmail + "','" + t_jCarNum + " ','" 
						+t_jPhone+ " ',' " + t_jCardNum + "','" +  t_jRecomm + " ',' " + t_jPw1 + " ');";
				dbSt.executeUpdate(strSql);
				JOptionPane.showMessageDialog(this, "회원가입되었습니다", "확인창",
						JOptionPane.INFORMATION_MESSAGE);
				System.out.println("데이터 삽입이 완료되었습니다.");
			}
		}catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}//JOIN 액션이벤트 끝
	
}//Join 메소드 끝

class MngLogin extends JFrame implements ActionListener{

	String user = "root", passwd = "0000";
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
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection
						("jdbc:mysql://localhost:3306/parkingmanager?serverTimezone=UTC", user, passwd);
				System.out.println("DB 연결이 완료되었습니다.");
				Statement dbSt = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				String strSql;
				String t_passwd;
				t_passwd = mngPw.getText();
				strSql = "SELECT*FROM parkingManager WHERE password='" + t_passwd + "';";
				ResultSet result = dbSt.executeQuery(strSql);
				if (result.next()) {
					JOptionPane.showMessageDialog(this, "매니저 계정으로 로그인 되었습니다.", "매니처로그인창",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "매니저 비밀번호가 틀렸습니다.", "매니저로그인창",
							JOptionPane.INFORMATION_MESSAGE);
				}
				dbSt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}
		}
	}
}//MngLogin 메소드 끝
public class Total_Login {

	public static void main(String[] args) {
		Login login = new Login();
		login.setTitle("LOGIN");
		login.setSize(400, 600);
		login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		login.setVisible(true);
	}
	
}

