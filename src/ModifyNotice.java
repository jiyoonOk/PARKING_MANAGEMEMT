package test_Javateam;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class ModifyNotice extends JFrame {
	JTextField title;
	JTextArea content;
	String db_title, db_content;

	// 글 번호를 찾아서 기존의 콘텐츠 내용 갖고 옴 -->불러오기
	public void getNoticeContent(int id, JTextField title, JTextArea content) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드했습니다.");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC",
					"root", "root");
			System.out.println("DB 연결 완료했습니다.");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			String strSql;
			strSql = "SELECT answer_title, answer_content FROM parking.answer WHERE answer_id='" + id + "';";
			ResultSet result = dbSt.executeQuery(strSql);
			while (result.next()) {// 기존에 저장된 답변 내용불러오기
				db_title = result.getString(1);
				db_content = result.getString(2);
			}
			title.setText(db_title);
			content.setText(db_content);

			dbSt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}

	}
	//새로 변동한 내용 넣어줌
	public void setNoticeContent(int id) { // 입력한 변동 수정 값 db에 갖다넣기
		String modi_title, modi_content;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드했습니다.");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC",
					"root", "root");
			System.out.println("DB 연결 완료했습니다.");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			
			modi_title = title.getText(); //제목 변경사항 저장
			modi_content = content.getText(); //글내용 변경사항 저장
			String strSql;
			strSql = "UPDATE `parking`.`answer` SET `answer_title`='" + modi_title + "', `answer_contents`='"
					+ modi_content + " WHERE (`answer_id`='" + id + "');";
			dbSt.executeUpdate(strSql);
			dbSt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}

	}
}
