
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class answer extends JFrame implements ActionListener{
	
	JTextArea jta,jta1; //문의작성내용
	JTextField title; //문의제목
	JTextField answertitle; //답변제목
	public answer(){
		Container ct = getContentPane();
		
		
		title = new JTextField("문의사항 제목");
		 title.setBounds(33,75,320,30);
		
		 JLabel no = new JLabel("문의사항 확인");
		 no.setBounds(320,5,70,70);
		 no.setSize(150,50);
		 Font font=new Font("맑은 고딕",Font.BOLD,20); //폰트, 굵기 지정
		 
		 JLabel inquiry = new JLabel("문의사항 내용");
		 inquiry.setBounds(33,50,320,20);
		 
		 jta=new JTextArea("문의사항 내용이 없습니다.",20,20);
		 jta.setBounds(33,110,320,240);  //문의사항 내용 확인
		 
		 JLabel answer1= new JLabel("답변 내용");
		 answer1.setBounds(433,50,320,20);
		 
		 answertitle = new JTextField("답변 제목");
		 answertitle.setBounds(433,75,320,30);
		 
		
		 jta1=new JTextArea("답변이 작성되지 않았습니다. 조금만 기다려주세요~",20,20);
		 jta1.setBounds(433,110,320,240);  //답변 내용 확인
			
			
		 JButton check = new JButton("확인");
			check.setBounds(350,370,80,30);
			check.setSize(90,30);
		 
		 
		 ct.setLayout(null);
		 ct.add(no);
		 no.setFont(font);
		 ct.add(inquiry);
		 ct.add(jta);
		 ct.add(answer1);
		 ct.add(title);
		 ct.add(answertitle);
		 ct.add(jta1);
		 ct.add(check);
		 
		 jta. setEditable(false);
		 title. setEditable(false);
		 jta1. setEditable(false);
		 answertitle. setEditable(false);//문의,답변 수정불가
		 check.addActionListener(this);
	}
  public void actionPerformed(ActionEvent ae) {
		
		String s=ae.getActionCommand();

		if(s=="확인") { //답변창 닫기
		dispose(); }

		 
	  }
	
	public static void main(String[] args) {
		answer ans=new answer();
		ans.setSize(800,500);
		ans.setLocation(400,0);
		ans.setTitle("문의사항 확인");
		ans.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ans.setVisible(true);
		

	}

}


