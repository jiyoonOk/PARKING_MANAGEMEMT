
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class question extends JFrame implements ActionListener {
	Color color = new Color(255, 255, 255);
	JButton btn1, btn2, btn3, btn4, btn5;

	public question() {
		Container ct = getContentPane(); // 문의사항이 들오면 최근에 들어온 순서대로 위에 차례대로 뜸
	

		JLabel no = new JLabel("문의사항");
		no.setBounds(150, 25, 70, 30);
		no.setSize(150, 20);
		Font font = new Font("맑은 고딕", Font.BOLD, 20); // 폰트, 굵기 지정

		btn1 = new JButton("[문의]");
		btn1.setBounds(-10, 70, 70, 30);
		btn1.setSize(400, 50);

		LineBorder line = new LineBorder(Color.black, 1, true);

		btn2 = new JButton("[문의]");// 문의추가시 문의제목 들어옴
		btn2.setBounds(-10, 119, 70, 30);
		btn2.setSize(400, 50);

		btn3 = new JButton("[문의]");
		btn3.setBounds(-10, 168, 70, 30);
		btn3.setSize(400, 50);

		btn4 = new JButton("[문의]");
		btn4.setBounds(-10, 217, 70, 30);
		btn4.setSize(400, 50);

		btn5 = new JButton("[문의]");
		btn5.setBounds(-10, 266, 70, 30);
		btn5.setSize(400, 50);

		JButton back = new JButton("이전");
		back.setBounds(60, 360, 80, 30);
		back.setSize(90, 30);

		JButton que = new JButton("문의하기"); 
		que.setBounds(235, 360, 80, 30);
		que.setSize(90, 30);

		ct.setLayout(null);
		ct.add(no);
		no.setFont(font);
		ct.add(btn1);
		ct.add(btn2);
		ct.add(btn3);
		ct.add(btn4);
		ct.add(btn5);
		ct.add(back);
		ct.add(que);

		btn1.setBorder(line);btn1.setBackground(color);
		btn2.setBorder(line);btn2.setBackground(color);
		btn3.setBorder(line);btn3.setBackground(color);
		btn4.setBorder(line);btn4.setBackground(color);
		btn5.setBorder(line);btn5.setBackground(color);

		que.addActionListener(this);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);

	}
	
	

	public void actionPerformed(ActionEvent ae) {
		/*try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
			} catch(ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb.question?serverTimezone=UTC", "root", "question");
			System.out.println("DB 연결 완료."); 
			Statement dbSt = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			String strsql;*/
			
		String s = ae.getActionCommand();
	
		if (ae.getSource()==btn1||ae.getSource()==btn2||ae.getSource()==btn3||ae.getSource()==btn4||ae.getSource()==btn5) {
			
			answer ans = new answer();
			ans.setSize(800, 500);
			ans.setLocation(400,0);
			ans.setTitle("문의사항 확인");
			ans.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ans.setVisible(true);
			
		}

		else if (s == "문의하기") { // 문의하기 버튼 누를 시 문의사항작성 창으로..
			dispose();
			question_write in2 = new question_write();
			in2.setSize(400, 500);
			in2.setTitle("문의사항");
			in2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			in2.setVisible(true);	
		}

		else {//이전 버튼 눌렀을 때
//			dbSt.close(); 
//			con.close(); // DB연동 끊기
			
			}
	

//	}catch (SQLException e) {
//	System.out.println("SQLException : "+e.getMessage()); }
}
		


		
		

	public static void main(String[] args) {
		question in = new question();
		in.setSize(400, 500);
		in.setTitle("문의사항");
		in.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		in.setVisible(true);

	}

}
