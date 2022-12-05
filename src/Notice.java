

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Notice extends JFrame  {
	
public Notice() {
	Container ct = getContentPane();
	
	 JLabel no = new JLabel("공지사항");
	 no.setBounds(150,25,70,30);
	 no.setSize(150,20);
	 Font font=new Font("맑은 고딕",Font.BOLD,20); //폰트, 굵기 지정
	 
	 JLabel label = new JLabel("1.0.0 업데이트 사항",JLabel.CENTER);
	 label.setBounds(-10,70,70,30);
	 label.setSize(400,50);
	 
	 LineBorder line = new LineBorder(Color.black,1,true);
	 
	 JLabel label2 = new JLabel("1.0.0 업데이트 사항",JLabel.CENTER);
	 label2.setBounds(-10,120,70,30);
	 label2.setSize(400,50);
	 
	 JLabel label3 = new JLabel("1.0.0 업데이트 사항",JLabel.CENTER);
	 label3.setBounds(-10,170,70,30);
	 label3.setSize(400,50);
	 
	 JLabel label4 = new JLabel("1.0.0 업데이트 사항",JLabel.CENTER);
	 label4.setBounds(-10,220,70,30);
	 label4.setSize(400,50);
	 
	 JLabel label5 = new JLabel("1.0.0 업데이트 사항",JLabel.CENTER);
	 label5.setBounds(-10,270,70,30);
	 label5.setSize(400,50);
	 
	 JButton back = new JButton("이전");
		back.setBounds(145,360,80,30);
		back.setSize(90,30);
	 
	 ct.setLayout(null);
	 ct.add(no);
	 no.setFont(font);
	 ct.add(label);
	 ct.add(label2);
	 ct.add(label3);
	 ct.add(label4);
	 ct.add(label5);
	 ct.add(back);
	 label.setBorder(line); //공지사항 구별 라인? 생성
	 label2.setBorder(line);
	 label3.setBorder(line);
	 label4.setBorder(line);
	 label5.setBorder(line);
	 
	 
}
	

	

	public static void main(String[] args) {
		Notice nt=new Notice();
		nt.setSize(400,500);
		nt.setLocation(400, 0);
		nt.setTitle("공지사항");
		nt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nt.setVisible(true);

	}


}
