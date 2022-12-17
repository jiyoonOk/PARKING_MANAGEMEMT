package Pay;

import Parking.NormalPay;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDateTime;

//TODO 221217 03:52 SQL문 추가, 코드 간결하게 작성
//TODO 221217 14:20 (다은) 생성자 Pay 내용 메소드 checkPayType()으로 옮김

public class Pay extends JFrame {
    static String userId;
    static LocalDateTime userCarOut, currentDate = LocalDateTime.now();
    static boolean userIsReserved, userIsCancel;

    public Pay(String id) { userId = id; }

    public static void checkPayType() throws ParseException {
        String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
        String user = "root";
        String password = "wldbs1004";

        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 parking 데이터베이스 연결
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            //purchase 값 가져오기
            //(출차시간, 결제유무, 취소여부)
            strSql = "SELECT car_out, is_reserved, is_cancel From purchase WHERE user_id='" + userId + "';";
            ResultSet result = dbSt.executeQuery(strSql);

            while (result.next()) {
                userCarOut = result.getTimestamp("car_out").toLocalDateTime();
                userIsReserved = result.getBoolean("is_reserved");
                userIsCancel = result.getBoolean("is_cancel");
            }
            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException1 : " + e.getMessage());
        }

        if(userIsReserved) { //예약
            if (!userIsCancel && !(currentDate.isAfter(userCarOut))) { //초과 O, , 현재시간이 출차시간보다 이후면(초과)
                ExcessFeePay excessFee = new ExcessFeePay(userId);
                excessFee.setSize(500,400);
                excessFee.setVisible(true);
            }
            else {  //초과 X
                RsvPopUp rsvPipUp = new RsvPopUp(userId);
                rsvPipUp.setSize(500, 400);
                rsvPipUp.setVisible(true);
            }
        }
        else { //일반 출차
            try {
                NormalPay nolmalPay =  new NormalPay(userId);
                nolmalPay.setSize(500,400);
                nolmalPay.setVisible(true);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}