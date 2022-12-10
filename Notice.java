

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Notice extends JFrame implements ActionListener  {
	
public Notice() {
	Container ct = getContentPane();
	Color color = new Color(255, 255, 255);
	
	 JLabel no = new JLabel("공지사항");
	 no.setBounds(150,25,70,30);
	 no.setSize(150,20);
	 Font font=new Font("맑은 고딕",Font.BOLD,20); //폰트, 굵기 지정
	 
	 LineBorder line = new LineBorder(Color.black,1,true);
	 
	 JButton btn1 = new JButton("1.0.0 업데이트 사항");
	 btn1.setBounds(-10,70,70,30);
	 btn1.setSize(400,50);
	 
	 JButton btn2 = new JButton("1.0.0 업데이트 사항");
	 btn2.setBounds(-10,119,70,30);
	 btn2.setSize(400,50);
	 
	 JButton btn3 = new JButton("1.0.0 업데이트 사항");
	 btn3.setBounds(-10,168,70,30);
	 btn3.setSize(400,50);
	 
	 JButton btn4 = new JButton("1.0.0 업데이트 사항");
	 btn4.setBounds(-10,217,70,30);
	 btn4.setSize(400,50);
	 
	 JButton btn5 = new JButton("1.0.0 업데이트 사항");
	 btn5.setBounds(-10,266,70,30);
	 btn5.setSize(400,50);
	 
	 JButton back = new JButton("이전");
		back.setBounds(145,360,80,30);
		back.setSize(90,30);
	 
	 ct.setLayout(null);
	 ct.add(no);
	 no.setFont(font);
	 ct.add(btn1);
	 ct.add(btn2);
	 ct.add(btn3);
	 ct.add(btn4);
	 ct.add(btn5);
	 ct.add(back);
	 btn1.setBorder(line);btn1.setBackground(color); //공지사항 구별 라인생성
	btn2.setBorder(line);btn2.setBackground(color);
	btn3.setBorder(line);btn3.setBackground(color);
	btn4.setBorder(line);btn4.setBackground(color);
	btn5.setBorder(line);btn5.setBackground(color);
	 
	btn1.addActionListener(this);
	btn2.addActionListener(this);
	btn3.addActionListener(this);
	btn4.addActionListener(this);
	btn5.addActionListener(this);
	 
}
public void actionPerformed(ActionEvent ae) {

	String s = ae.getActionCommand();

	if (s == "1.0.0 업데이트 사항") { 
		Noticecheck nocheck = new Noticecheck();
		nocheck.setSize(400, 500);
		nocheck.setLocation(400,0);
		nocheck.setTitle("공지사항 확인");
		nocheck.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nocheck.setVisible(true);}
		
	//else if ; //이전 버튼 눌렀을 때 메인메뉴창으로...
	
}
	

	public static void main(String[] args) {
		Notice nt=new Notice();
		nt.setSize(400,500);

		nt.setTitle("공지사항");
		nt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nt.setVisible(true);

	}


}
