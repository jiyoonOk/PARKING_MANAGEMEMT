
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;


public class Mypage extends JFrame implements ActionListener,ItemListener {

	
	JLabel title; //마이페이지
	JLabel name; //이름
	JLabel id; //아이디
	JLabel passwd; //비밀번호
	JLabel carn; //차량번호
	JLabel card; //카드번호
	JLabel call; //전화번호
	JLabel special; //특이사항
	JButton update; //수정하기 버튼
	JButton mov; //이전으로 버튼
	JButton del; //계정탈퇴 버튼
	JTextField name2,id2,passwd2,carn2,call2,card2;
	 JCheckBox cb1,cb2,cb3;
	 String t_specialneeds;
	
	
	public  Mypage(){
		Container ct = getContentPane();
		
		
		
		
		 title = new JLabel("마이페이지");
		 title.setBounds(140,25,70,30);
		 Font font=new Font("맑은 고딕",Font.BOLD,20); //폰트,굵기 지정
		 title.setSize(150,20);
		
		 name = new JLabel("이름 :");
		 name.setBounds(20,60,70,30);
		 
		 name2 = new JTextField("이름정보");
		 name2.setBounds(70,60,70,30);
		 
		 id = new JLabel("아이디 :");
		 id.setBounds(20,90,70,30);
		 
		 id2 = new JTextField("아이디정보");
		 id2.setBounds(80,90,100,30);
		 

		 
		 passwd = new JLabel("비밀번호 :");
		 passwd.setBounds(20,120,70,30);
		 
		 passwd2 = new JTextField("비밀번호정보");
		 passwd2.setBounds(80,120,100,30);
		 
		 carn = new JLabel("차량번호 :");
		 carn.setBounds(20,150,70,30);
		 
		 carn2 = new JTextField("차량번호정보");
		 carn2.setBounds(80,150,100,30);
		 
		 call = new JLabel("전화번호 :");
		 call.setBounds(20,180,70,30);
		 
		 call2 = new JTextField("전화번호정보");
		 call2.setBounds(80,180,100,30);
		 
		 card = new JLabel("카드번호 :");
		 card.setBounds(20,210,70,30);
		 
		 card2 = new JTextField("카드번호정보");
		 card2.setBounds(80,210,100,30);
		 
		 special=new JLabel("특이사항:");
		 special.setBounds(20,240,70,30);
		 
		cb1=new JCheckBox("여성");
		cb1.setBounds(20,270,60,20);
		
		cb2=new JCheckBox("장애인");
		cb2.setBounds(80,270,70,20);
		
		cb3=new JCheckBox("경차");
		cb3.setBounds(150,270,70,20);
		
		 
		 update = new JButton("수정하기");
			update.setBounds(20,310,80,30);
			update.setSize(90,30);
			
			mov = new JButton("이전으로");
				mov.setBounds(130,310,80,30);
				mov.setSize(90,30);
			
			del = new JButton("계정탈퇴");
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
			ct.add(cb1); ct.add(special);
			ct.add(cb2); ct.add(cb3);
			update.addActionListener(this); 
			del.addActionListener(this);
			
			}
	
	
	
	public void actionPerformed(ActionEvent ae) {
	String t_name="",t_id="",t_passwd="",t_carn="",t_call="",t_card="",t_specialneeds="";

	t_name=name2.getText(); t_id=id2.getText(); t_passwd=passwd2.getText();
	t_carn=carn2.getText(); t_call=call2.getText(); t_card=card2.getText();

		String s=ae.getActionCommand(); 
		/*
		 * try { Class.forName("com.mysql.cj.jdbc.Driver");
		 * System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함"); }
		 * catch(ClassNotFoundException e) { System.err.println("드라이버 로드에 실패했습니다."); }
		 * try { Connection con=DriverManager.getConnection(
		 * "jdbc:mysql://localhost:3306/test?serverTimezone=UTC", "root", "java2020");
		 * System.out.println("DB 연결 완료."); Statement dbSt = con.createStatement();
		 * System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다."); String strsql;
		 */
		//ResultSet result=dbSt.executeQuery(strSql);
		
		if(s=="수정하기") { //수정하기 버튼 누를 시 팝업 이벤트발생, 적힌 정보 DB에 업데이트

			
//	strsql="UPDATE user SET name='"+t_name+"',id='"+t_id+"',passwd='"+t_passwd+"',carn='"+t_carn+"'call='"+t_call+"',card='"+t_card+"',specialneeds='"+t_specialneeds+"';";  //!한줄로 작성!
//	dbSt.executeUpdate(strsql); //sql질의어 실행

		JOptionPane.showMessageDialog
			(this, "정보가 수정되었습니다.","수정완료",JOptionPane.INFORMATION_MESSAGE);
		} 
		
		
		if(s=="계정탈퇴") {//계정탈퇴 버튼 누를 시 팝업 이벤트발생
		
			
			int answer = JOptionPane.showConfirmDialog
				(this, "정말 계정을 삭제하시겠습니까?", "계정탈퇴",JOptionPane.OK_CANCEL_OPTION );
			
			if(answer == JOptionPane.YES_OPTION){ //사용자가 확인을 눌렀을 떄 DB 정보 삭제
				
//				strsql="DELETE FROM user WHERE id='"+t_id+"';";
//				dbSt.executeUpdate(strsql); 	
				
	//DB정보 삭제 후 로그인화면으로 전환
				JOptionPane.showMessageDialog
				(this, "계정이 삭제되었습니다.","계정탈퇴",JOptionPane.INFORMATION_MESSAGE);}
			} else{ //사용자가 취소를 눌렀을 때 팝업창 사라짐
			}
		if(s=="이전으로"){
//		  dbSt.close(); 
//		    con.close(); // DB연동 끊기
		}
//		    } catch (SQLException e) { 
//		    System.out.println("SQLException : "+e.getMessage()); } 
}

		
    public void itemStateChanged(ItemEvent e) {
    	t_specialneeds=e.getSource()+" "; //특이사항 선택시 변수저장
             
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
