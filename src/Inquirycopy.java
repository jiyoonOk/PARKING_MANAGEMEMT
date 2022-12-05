

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Inquirycopy extends JFrame implements ActionListener {
	public Inquirycopy() {
		Container ct = getContentPane();
		
		 JLabel no = new JLabel("문의사항");
		 no.setBounds(150,25,70,30);
		 no.setSize(150,20);
		 Font font=new Font("맑은 고딕",Font.BOLD,20); //폰트, 굵기 지정
		 
		 JLabel label = new JLabel("[문의]",JLabel.CENTER);
		 label.setBounds(-10,70,70,30);
		 label.setSize(400,50);
		 
		 LineBorder line = new LineBorder(Color.black,1,true);
		 
		 JLabel label2 = new JLabel("[문의]",JLabel.CENTER);
		 label2.setBounds(-10,120,70,30);
		 label2.setSize(400,50);
		 
		 JLabel label3 = new JLabel("[문의]",JLabel.CENTER);
		 label3.setBounds(-10,170,70,30);
		 label3.setSize(400,50);
		 
		 JLabel label4 = new JLabel("[문의]",JLabel.CENTER);
		 label4.setBounds(-10,220,70,30);
		 label4.setSize(400,50);
		 
		 JLabel label5 = new JLabel("[문의]",JLabel.CENTER);
		 label5.setBounds(-10,270,70,30);
		 label5.setSize(400,50);
		 
		 JButton back = new JButton("이전");
			back.setBounds(60,360,80,30);
			back.setSize(90,30);
		 
		 JButton inq = new JButton("문의하기"); //문의사항 창으로..
			inq.setBounds(235,360,80,30);
			inq.setSize(90,30);
	 
		 
		 ct.setLayout(null);
		 ct.add(no);
		 no.setFont(font);
		 ct.add(label);  ct.add(label2); ct.add(label3);
		 ct.add(label4); ct.add(label5);
		 ct.add(back);
		 ct.add(inq);
		 
		 label.setBorder(line); label2.setBorder(line);
		 label3.setBorder(line);label4.setBorder(line);
		 label5.setBorder(line);
		 
		 inq.addActionListener(this);
		 
		 
		 
	}
	
	public void actionPerformed(ActionEvent ae) {

		if(ae.getActionCommand().equals("문의하기")) { //문의하기 버튼 누를 시 문의사항작성 창으로..
			//dispose()는 여기에 넣으면 안됨......
		Inquiry2 in2= new Inquiry2();
		in2.setSize(400,500);
		in2.setTitle("문의사항");
		in2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		in2.setVisible(true);
		
		}
	  }
	
		

		

		public static void main(String[] args) {
			Inquirycopy in=new Inquirycopy();
			in.setSize(400,500);
			in.setLocation(400, 0);
			in.setTitle("문의사항");
			in.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			in.setVisible(true);

		}


	}


