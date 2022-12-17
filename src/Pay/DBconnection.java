package Pay;

import java.sql.*;
import java.time.LocalDateTime;

// TODO 221217 03:52 insert에 where 조건문 입력 안 됨! 전부 update 쿼리문으로 작성
// TODO 221217 15:00 (다은) 비번 변경

public class DBconnection {

    //String형 update
    public static void updateDB(String table, String columns1, String index, String columns2, String condition) {
        String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
        String user = "root";
        String password = "wldbs1004";
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;
            strSql = " UPDATE " + table + " SET " + columns1 + "= '" + index + "' WHERE " + columns2 + "= '" + condition + "';";
            dbSt.executeUpdate(strSql); //DB로부터 읽어온 레코드 객체화

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException3 : " + e.getMessage());
        }
    }

    //출차시간 update
    public static void updateDB(String table, String columns1, LocalDateTime index, String columns2, String condition) {
        String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
        String user = "root";
        String password = "wlbds1004";
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;
            strSql = " UPDATE " + table + " SET " + columns1 + "= '" + index + "' WHERE " + columns2 + "= '" + condition + "';";
            dbSt.executeUpdate(strSql); //DB로부터 읽어온 레코드 객체화

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException3 : " + e.getMessage());
        }
    }
}
