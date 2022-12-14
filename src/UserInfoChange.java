package id_dbConnect;

import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class UserInfoChange extends JFrame{
	JTextField nameField, idField, carField, numberField, cardField;
	JTextField search_id;
	String id, name,email, car_num, card_num, phone_num;
		//String passwd;
	
	void getUserInfo() {//DB에서 회원 정보 받아오기
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC 드라이버를 정상적으로 로드했습니다.");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC",
					"root", "root");
			System.out.println("DB 연결 완료");
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			String strSql;
			id = search_id.getText();
			strSql = "SELECT id, name, email, phone_num, car_num, card_num FROM user WHERE id='" + id + "';";
			Statement dbSt = con.createStatement();
			ResultSet result = dbSt.executeQuery(strSql);
			while (result.next()) {
				id = result.getString(1);
				name = result.getString(2);
				email = result.getString(3);
				phone_num = result.getString(4);
				car_num = result.getString(5);
				card_num = result.getString(6);
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}	
	}
	void infoChange() {//변경한 회원 정보 DB에 저장하기
		name = nameField.getText(); id = idField.getText(); car_num=carField.getText();
		phone_num = numberField.getText();
		card_num = cardField.getText();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC 드라이버를 정상적으로 로드했습니다.");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC",
					"root", "root");
			System.out.println("DB 연결 완료");
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			String strSql;
			id = search_id.getText();
strSql = "UPDATE `parking`.`user` SET `email`='"+email+"', `phone_num`='"
			+phone_num+"','car_num'='"+car_num+"','card_num'='"+card_num+"' WHERE (`id` = '"+id+"');";
			
			Statement dbSt = con.createStatement();
			ResultSet result = dbSt.executeQuery(strSql);
			
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}
}
