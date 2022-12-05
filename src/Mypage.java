package Test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Mypage extends JFrame implements ActionListener {
	
	 

	
	public  Mypage(){
		Container ct = getContentPane();
		
		 JLabel title = new JLabel("마이페이지");
		 title.setBounds(140,25,70,30);
		 Font font=new Font("맑은 고딕",Font.BOLD,20); //폰트,굵기 지정
		 title.setSize(150,20);
		
		 JLabel name = new JLabel("이름 :");
		 name.setBounds(20,60,70,30);
		 
		 JTextField name2 = new JTextField("이름정보");
		 name2.setBounds(70,60,70,30);
		 
		 JLabel id = new JLabel("아이디 :");
		 id.setBounds(20,90,70,30);
		 
		 JTextField id2 = new JTextField("아이디정보");
		 id2.setBounds(80,90,100,30);
		 

		 
		 JLabel passwd = new JLabel("비밀번호 :");
		 passwd.setBounds(20,120,70,30);
		 
		 JTextField passwd2 = new JTextField("비밀번호정보");
		 passwd2.setBounds(80,120,100,30);
		 
		 JLabel carn = new JLabel("차량번호 :");
		 carn.setBounds(20,150,70,30);
		 
		 JTextField carn2 = new JTextField("차량번호정보");
		 carn2.setBounds(80,150,100,30);
		 
		 JLabel call = new JLabel("전화번호 :");
		 call.setBounds(20,180,70,30);
		 
		 JTextField call2 = new JTextField("전화번호정보");
		 call2.setBounds(80,180,100,30);
		 
		 JLabel card = new JLabel("카드번호 :");
		 card.setBounds(20,210,70,30);
		 
		 JTextField card2 = new JTextField("카드번호정보");
		 card2.setBounds(80,210,100,30);
		 
		 JButton update = new JButton("수정하기");
			update.setBounds(20,300,80,30);
			update.setSize(90,30);
			
			 JButton mov = new JButton("이전으로");
				mov.setBounds(120,300,80,30);
				mov.setSize(90,30);
			
			 JButton del = new JButton("계정탈퇴");
				del.setBounds(280,350,80,80);
				del.setSize(90,20);
		 
		 
	
		
			ct.setLayout(null);
			title.setFont(font);
			ct.add(title);
			ct.add(name); ct.add(name2);
			ct.add(id); ct.add(id2);
			ct.add(passwd); ct.add(passwd2); 
			ct.add(carn);	ct.add(carn2);
			ct.add(call); ct.add(call2);
			ct.add(card); ct.add(card2);
			ct.add(update);
			ct.add(mov);
			ct.add(del);
			
			update.addActionListener(this); 
			del.addActionListener(this); 
			
	} //생성자 끝
	
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getActionCommand().equals("수정하기")) { //수정하기 버튼 누를 시 팝업 이벤트발생
//수정하기 버튼 누를 시 TextField에 적힌 정보를 다시 저장 후 DB에도 저장
		//strsql="UPDATE Mypage_info SET name='"name2.getText())+
		//"',id='"+id2.getText()+"',passwd='"+passwd2.getText()+
		//"',carn='"+carn.getText()+"'call='"+call2.getText()+
		//"',card='"+card2.getText()+"';"  //!한줄로 작성!
		//DB이름.executeUpdate(strsql); //sql질의어 실행

		JOptionPane.showMessageDialog
			(this, "정보가 수정되었습니다.","수정완료",JOptionPane.INFORMATION_MESSAGE);} 
		
		
		if(ae.getActionCommand().equals("계정탈퇴")) {//계정탈퇴 버튼 누를 시 팝업 이벤트발생
		
			
			int answer = JOptionPane.showConfirmDialog
				(this, "정말 계정을 삭제하시겠습니까?", "계정탈퇴",JOptionPane.OK_CANCEL_OPTION );
			
			if(answer == JOptionPane.YES_OPTION){ //사용자가 확인을 눌렀을 떄 DB 정보 삭제
				//strsql="DELETE FROM Mypage_info WHERE name= passwd= carn= call= card= ;
				//dbSt.executeUpdate(strsql);
				JOptionPane.showMessageDialog
				(this, "계정이 삭제되었습니다.","계정탈퇴",JOptionPane.INFORMATION_MESSAGE);}
			} else{ //사용자가 취소를 눌렀을 때 팝업창 사라짐
			}
	}
		
	 
	


	public static void main(String[] args) {
		Mypage mg=new Mypage();
		mg.setSize(400,430);
		mg.setLocation(400, 0);
		mg.setTitle("마이페이지");
		mg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mg.setVisible(true);

	}


}
