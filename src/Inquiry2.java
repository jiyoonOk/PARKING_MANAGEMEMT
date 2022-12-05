

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.sql.*;

public class Inquiry2 extends JFrame implements ActionListener {
	JTextArea jta;
	public Inquiry2() {
		Container ct = getContentPane();
		
		
		 JLabel no = new JLabel("문의사항");
		 no.setBounds(150,25,70,30);
		 no.setSize(150,20);
		 Font font=new Font("맑은 고딕",Font.BOLD,20); //폰트, 굵기 지정
		 
		 JLabel inquiry = new JLabel("문의하기");
		 inquiry.setBounds(30,60,70,30);
		 
		 jta=new JTextArea("문의사항을 입력하세요!",20,20);
		 jta.setBounds(33,90,320,250);  //문의사항 작성하는 공간생성
		 
		 
		 JButton reg = new JButton("등록하기");
			reg.setBounds(60,360,80,30);
			reg.setSize(90,30);
			
			 JButton del = new JButton("취소");
				del.setBounds(235,360,80,30);
				del.setSize(90,30);
		 
		 
		 
		 ct.setLayout(null);
		 ct.add(no);
		 no.setFont(font);
		 ct.add(inquiry);
		 ct.add(jta);
		 ct.add(reg);
		 ct.add(del);
		 
		 reg.addActionListener(this);
		 del.addActionListener(this);
		
	
		 
	}
	
	public void actionPerformed(ActionEvent ae) {

		if(ae.getActionCommand().equals("등록하기")) { 
			String text=jta.getText(); 
			//등록하기 버튼 누를시 팝업 이벤트 발생

		// 등록하기 버튼 누를 시 작성된 문의사항 DB로 넘어가서 문의사항 게시판에 올라감, 제목칸만들어야할까?

		if(text.length()>0) {
		JOptionPane.showMessageDialog
			(this, "문의 등록이 완료되었습니다.","등록완료",JOptionPane.INFORMATION_MESSAGE);
		//strsql="UPDATE Inquiry_info SET inq='"+jta.getText()"';"
		//dbSt.executeUpdate(strsql);
		} 
		else{ //문의사항이 작성되지 않았을 경우 예외처리
				 JOptionPane.showMessageDialog
				(this, "문의사항을 입력하세요!","문의사항 등록실패",JOptionPane.INFORMATION_MESSAGE);}
				
		}
		
	
		if(ae.getActionCommand().equals("취소")) { //문의사항 작성 창만 닫힘
			 dispose(); }
	  
	}
	
	

	public static void main(String[] args) {
		Inquiry2 in2=new Inquiry2();
		in2.setSize(400,500);
		in2.setTitle("문의사항");
		in2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		in2.setVisible(true);


	
			

	}

}
