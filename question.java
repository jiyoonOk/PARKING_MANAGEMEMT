package Test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.sql.*;

public class question extends JFrame implements ActionListener, MouseListener {
	static String loginId;
	Vector<String> columnName; // 표의 각 컬럼 제목
	Vector<String> columnName2;
	Vector<Vector<String>> rowData;
	Vector<Vector<String>> Data;
	JTable table = null;
	JTable table2 = null;
	DefaultTableModel model = null;
	DefaultTableModel model2 = null;
	JScrollPane tableSP, tablesp;
	int row, row2; // 테이블에서 선택된 행 번호
	JButton quiryB; // 조회 버튼
	JTextField answer_title; // 답변사항 제목
	JTextArea jta, jta2; 

	JButton returnB, questionB; 

	public question(String id) // 생성자
	{
		loginId = id;

		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		JPanel top = new JPanel(); // 제목나오는 부분
		JPanel center = new JPanel(); // 표 출력되는 부분
		JPanel bottom = new JPanel(); // 조회 버튼 부분
		JPanel rightP = new JPanel(); 
		ct.add(top, BorderLayout.NORTH);
		ct.add(center, BorderLayout.CENTER);
		ct.add(bottom, BorderLayout.SOUTH);
		ct.add(rightP, BorderLayout.EAST);

////////////// 표 만들기 ////////////////////////
		columnName = new Vector<String>(); // 표의 컬럼 제목 만들기
		columnName.add("문의사항");
		columnName2 = new Vector<String>(); // 표의 컬럼 제목 만들기
		columnName2.add("답변사항");

		rowData = new Vector<Vector<String>>(); // 2차원 벡터로 표 내용 부분 만들기
		Data = new Vector<Vector<String>>();
		model = new DefaultTableModel(rowData, columnName);
		model2 = new DefaultTableModel(Data, columnName2);
		table = new JTable(model);
		table2 = new JTable(model2);
		tableSP = new JScrollPane(table);
		tablesp = new JScrollPane(table2);
		table.addMouseListener(this);
		quiryB = new JButton("조회");
		questionB = new JButton("문의하기");
		returnB = new JButton("이전");

		returnB.addActionListener(this);
		questionB.addActionListener(this);
		quiryB.addActionListener(this);
		top.setLayout(new FlowLayout());
		table.setRowHeight(70); // 크기 조절
		table2.setRowHeight(70);

		top.add(new JLabel("<<<<<문의사항>>>>> "));
		center.setLayout(new FlowLayout());
		center.add(tableSP);
		center.add(tablesp);
		bottom.setLayout(new FlowLayout());
		bottom.add(quiryB);
		bottom.add(questionB);
		bottom.add(returnB);

//////////////////// 오른쪽 문의사항 내용 ,답변사항 제목,내용 확인 /////////////////////////
		rightP.setLayout(new GridLayout(7, 1));
		
		JPanel p5 = new JPanel();
		p5.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l5 = new JLabel("문의 내용   :");
		p5.add(l5);
		
		
		JPanel p6 = new JPanel();
		p6.setLayout(new FlowLayout(FlowLayout.LEFT));
		jta2 = new JTextArea("", 50, 20);
		p6.add(jta2);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l1 = new JLabel("답변 제목   :");
		p1.add(l1);

		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		answer_title = new JTextField(20);
		p2.add(answer_title);

		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l3 = new JLabel("답변 내용   :");
		p3.add(l3);

		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jta = new JTextArea("", 50, 20);
		p4.add(jta);
		p4.setPreferredSize(new Dimension(200, 200));

		rightP.add(p5);
		rightP.add(p6);
		rightP.add(p1);
		rightP.add(p2);
		rightP.add(p3);
		rightP.add(p4);
	} // 생성자

// 조회,이전 버튼 클릭시 실행

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("이전")) {
			dispose();
		} // 화면닫음

		if (ae.getActionCommand().equals("문의하기")) { // 문의하기 버튼 누를 시 문의사항작성 창으로..
			question_write in2 = new question_write(getTitle());
			in2.setSize(400, 500);
			in2.setTitle("문의사항");
			in2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			in2.setVisible(true);

		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // mysql의 jdbc Driver 연결하기
			System.err.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC",
					"root", "wldbs1004");
			System.out.println("DB 연결 완료.");
			Statement dbSt = con.createStatement();
			String strSql;

			if (ae.getActionCommand().equals("조회")) {
				strSql = "SELECT * FROM parking.question;";// DB로부터 읽어온 레코드를 객체화한 것
				ResultSet result = dbSt.executeQuery(strSql);
				clearTable();
				table.updateUI(); // 테이블에 출력하기 전에 테이블 클리어하기

				while (result.next()) { // DB에서 문의정보 읽어와 표에 출력하기
					Vector<String> txt = new Vector<String>();
					txt.add(result.getString("question_title"));
					txt.add(result.getString("question_contents"));
					rowData.add(txt);
				} // while

				
				strSql = "SELECT * FROM parking.answer"; 
				ResultSet result1 = dbSt.executeQuery(strSql);
				clearTable2();
				table2.updateUI();
				
				while (result1.next()) {
					Vector<String> txt = new Vector<String>();
					txt.add(result1.getString("answer_title"));
					txt.add(result1.getString("answer_contents"));
					Data.add(txt);

				}

			} // 조회 버튼 클릭시

			table.updateUI();
			table2.updateUI();
			dbSt.close();
			con.close(); // DB연동 끊기

		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}

	} // actionPerformed 메소드

	

	public void mouseClicked(MouseEvent ae) {
			row = table.getSelectedRow();
			jta2.setText((String) model.getValueAt(row, 1));

			
			row2 = table2.getSelectedRow();
			answer_title.setText((String) model2.getValueAt(row, 0));
			jta.setText((String) model2.getValueAt(row, 1));

	}

	void clearTable() { // 테이블 클리어
		for (int i = 0; i < rowData.size();)
			rowData.remove(i);
	}
	void clearTable2() { // 테이블 클리어
		for (int i = 0; i < Data.size();)
			Data.remove(i);
	}

	public void mousePressed(MouseEvent ae) {
	}

	public void mouseReleased(MouseEvent ae) {
	}

	public void mouseEntered(MouseEvent ae) {
	}

	public void mouseExited(MouseEvent ae) {
	} // MouseListener가 상속해주는 추상메소드들을 모두 메소드 오버라이딩 해야 함

	public static void main(String args[]) {
		question win = new question(loginId);
		win.setTitle("문의사항");
		win.setSize(800, 533);
		win.setLocation(400, 0);
		win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		win.setVisible(true);
	}

}