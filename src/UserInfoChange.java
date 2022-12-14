package id_dbConnect;

import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class UserInfoChange extends JFrame {
	JTextField nameField, idField, carField, numberField, cardField;
	JTextField search_id;
	String id, name, email, car_num, card_num, phone_num;

	// String passwd;
	// id검색버튼 누르면 발동하는 액션이벤트코드에 추가하는것이 어떤지..
	void getUserInfo() {// DB에서 회원 아이디 검색하면 받아와서 textField 띄우기 정보 받아오기
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
			//검색한 id의 해당 정보를 db에 검색하는 코드
			strSql = "SELECT id, name, email, phone_num, car_num, card_num FROM user WHERE id='" + id + "';";
			Statement dbSt = con.createStatement();
			ResultSet result = dbSt.executeQuery(strSql);
			//검색한 해당 정보 받아오는 반복문
			while (result.next()) {
				id = result.getString(1); //필요하지 않을수도
				name = result.getString(2); //필요하지 않을수도
				email = result.getString(3);
				phone_num = result.getString(4);
				car_num = result.getString(5);
				card_num = result.getString(6);

			}
			//String변수 받아온 값 TextField에 띄우기
			nameField.setText(name);
			carField.setText(car_num);
			numberField.setText(phone_num);
			cardField.setText(card_num);
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}

	void infoChange() {// 변경한 회원 정보 DB에 저장하기
		//TextField에 입력된 값 변수에 저장
		name = nameField.getText();
		id = idField.getText();
		car_num = carField.getText();
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
			strSql = "UPDATE `parking`.`user` SET `email`='" + email + "', `phone_num`='" + phone_num + "','car_num'='"
					+ car_num + "','card_num'='" + card_num + "' WHERE (`id` = '" + id + "');";

			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}
}
