
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Noticecheck extends JFrame implements ActionListener {
	JTextArea jta; //문의작성내용
	JTextField title; //제목
	JLabel no,inquiry;
	JButton check; //확인버튼

	public Noticecheck() {
		Container ct = getContentPane();
		
		
		 title = new JTextField("공지사항 제목");
		 title.setBounds(33,75,320,30);
		
		  no = new JLabel("공지사항");
		 no.setBounds(150,25,70,30);
		 no.setSize(150,20);
		 Font font=new Font("맑은 고딕",Font.BOLD,20); //폰트, 굵기 지정
		 
		  inquiry = new JLabel("공지사항 내용");
		 inquiry.setBounds(33,50,320,20);
		 
		 jta=new JTextArea("공지사항 내용입니당",20,20);
		 jta.setBounds(33,110,320,240);  //공지사항 내용 확인
		 
			
		 check = new JButton("확인");
			check.setBounds(150,360,80,30);
			check.setSize(90,30);
		 
		 
		 
		 ct.setLayout(null);
		 ct.add(no);
		 no.setFont(font);
		 ct.add(inquiry);
		 ct.add(jta);
		 ct.add(check);
		 ct.add(title);
		 
		 jta. setEditable(false);
		 title. setEditable(false);//제목, 내용 수정불가
		 
		 check.addActionListener(this);
		 
	}
	 public void actionPerformed(ActionEvent ae) {
		 
		 
			
			String s=ae.getActionCommand();

			if(s=="확인") { //문의사항확인창 닫기
			dispose(); }

			 
		  }
	

	public static void main(String[] args) {
		Noticecheck nocheck=new Noticecheck();
		nocheck.setSize(400,500);
		nocheck.setLocation(400,0);
		nocheck.setTitle("공지사항 확인");
		nocheck.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nocheck.setVisible(true);

	}


}
