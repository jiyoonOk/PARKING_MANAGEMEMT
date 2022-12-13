package Test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.sql.*;

public class question_write extends JFrame implements ActionListener {
	JTextArea jta; //문의작성내용
	JTextField title; //제목
	private int t_question_id;
	
	
	public question_write() {
		Container ct = getContentPane();
		
		
		 title = new JTextField("");
		 title.setBounds(33,75,320,30);
		
		 JLabel no = new JLabel("문의사항");
		 no.setBounds(150,25,70,30);
		 no.setSize(150,20);
		 Font font=new Font("맑은 고딕",Font.BOLD,20); //폰트, 굵기 지정
		 
		 JLabel question = new JLabel("문의하기");
		 question.setBounds(33,50,320,20);
		 
		 jta=new JTextArea("",20,20);
		 jta.setBounds(33,110,320,240);  //문의사항 작성하는 공간생성
		 
		 
		 JButton reg = new JButton("등록하기");
			reg.setBounds(60,360,80,30);
			reg.setSize(90,30);
			
		 JButton del = new JButton("취소");
			del.setBounds(235,360,80,30);
			del.setSize(90,30);
		 
		 
		 
		 ct.setLayout(null);
		 ct.add(no);
		 no.setFont(font);
		 ct.add(question);
		 ct.add(jta);
		 ct.add(reg);
		 ct.add(del);
		 ct.add(title);
		 
		 reg.addActionListener(this);
		 del.addActionListener(this);
		
	
		 
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s=ae.getActionCommand();
		String t_question_title="",t_question_contents="",t_question_date=""; 
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date from = new Date();

		t_question_title=title.getText();
		t_question_contents=jta.getText();
		
		
		  try { Class.forName("com.mysql.cj.jdbc.Driver");
		  System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함"); }
		  catch(ClassNotFoundException e) { System.err.println("드라이버 로드에 실패했습니다."); }
		  
		  try { Connection con=DriverManager.getConnection(
		  "jdbc:mysql://localhost:3306/dbtest?serverTimezone=UTC", "root", "root");
		  System.out.println("DB 연결 완료."); Statement dbSt = con.createStatement();
		  System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다."); String strsql;
		 
			
		
		if(s=="등록하기") { 
			String text=jta.getText(); 	
			String text2=title.getText(); //등록하기 버튼 누를시 팝업 이벤트 발생
			
		if(text.length()>0&&text2.length()>0) {
		JOptionPane.showMessageDialog
			(this, "문의 등록이 완료되었습니다.","등록완료",JOptionPane.INFORMATION_MESSAGE);
		
		 Random ran = new Random(4);
		t_question_id=ran.nextInt(9999); //문의번호 1씩증가
		t_question_date= transFormat.format(from); //Date에서 String으로 형변환 후 문의일자 들어감
		dispose();
		
	strsql="INSERT INTO question(question_id,question_title, question_contents, question_date) "+" VALUES(" + t_question_id + ",'" + t_question_title + "','" + t_question_contents + "','"+ t_question_date+"')";
	dbSt.executeUpdate(strsql);
		//문의 번호, 작성 일자는 등록완료 시 번호랑 일자 들어감
		} 
		
		else{ //문의사항이 작성되지 않았을 경우 예외처리
				 JOptionPane.showMessageDialog
				(this, "문의사항을 입력하세요!","문의사항 등록실패",JOptionPane.INFORMATION_MESSAGE);}
				
		}
		
	
		if(s=="취소") { //문의사항 작성 창만 닫힘
			 dispose(); 	

		}
		
		dbSt.close(); 
		con.close(); // DB연동 끊기
	} catch (SQLException e) {
		System.out.println("SQLException : "+e.getMessage()); }
	  
	}
	
	

	public static void main(String[] args) {
		question_write in2=new question_write();
		in2.setSize(400,500);
		in2.setTitle("문의사항");
		in2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		in2.setVisible(true);


	
			

	}

}
