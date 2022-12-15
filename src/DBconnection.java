import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

class DBconnection {
    //DB를 위한 변수
    String sql = "";
    Vector<Vector<String>> rowData;
    JTable table;

    //기본 생성자
    public DBconnection() {
    }

    ;

    //모든 JTable 뽑아냄
    public DBconnection(String sql, Vector<Vector<String>> rowData, JTable table) {
        this.sql = sql;
        this.rowData = rowData;
        this.table = table;
    }

    public void JTableUpdate() {
        String t = table.getName();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setNumRows(0);
        // JDBC 드라이버 로드
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.err.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            AdminMain.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", AdminMain.user_name, AdminMain.password);
            System.out.println("연결 완료!");

            Statement stmt = AdminMain.con.createStatement();

            ResultSet result = stmt.executeQuery(sql);    //DB로부터 읽어온 데이터

            while (result.next()) {  //DB에서 읽어와 표에 출력하기
                Vector<String> txt = new Vector<String>();
                // 한 레코드를 읽으면 1차원 벡터로 만들어 표에 한 행씩 추가
                switch (t) {
                    case "userTable": {
                        txt.add(result.getString("id"));
                        txt.add(result.getString("name"));
                        txt.add(result.getString("phone_num"));
                        txt.add(result.getString("car_num"));
                        txt.add(result.getString("point"));
                    }
                    break;
                    case "userParkingTable": {
                        txt.add(result.getString("car_in"));
                        txt.add(result.getString("area"));
                        txt.add(result.getString("total_fee"));
                    }
                    break;
                    case "salesTable": {
                        txt.add(result.getString("car_in"));
                        //txt.add(resultAllUser.getString("car_num"));
                        txt.add(result.getString("total_fee"));
                        txt.add(result.getString("user_id"));
                    }
                    break;
                    case "qnaJTable": {
                        txt.add(result.getString("question_id"));
                        txt.add(result.getString("question_title"));
                        txt.add(result.getString("question_contents"));
                        txt.add(result.getString("question_date"));
                        txt.add(result.getString("user_id"));
                    }
                    break;
                    case "answerJTable": {
                        txt.add(result.getString("answer_id"));
                        txt.add(result.getString("question_id"));
                        txt.add(result.getString("answer_title"));
                        txt.add(result.getString("answer_contents"));
                        txt.add(result.getString("answer_date"));
                    }
                    break;
                    case "noticeJTable": {
                        txt.add(result.getString("notice_id"));
                        txt.add(result.getString("notice_title"));
                        txt.add(result.getString("notice_contents"));
                        txt.add(result.getString("notice_date"));
                    }
                    break;
                }
                rowData.add(txt);
                table.updateUI();
            }
            stmt.close();
            AdminMain.con.close();
        } catch (SQLException e) {
            System.err.println("JTable 연결 오류" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
    }

    //DB에 명령문 넣기만 할 때 : 추가, 수정, 삭제
    public void DBInstruct(String sql) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.err.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            AdminMain.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", AdminMain.user_name, AdminMain.password);
            System.out.println("연결 완료!");

            Statement stmt = AdminMain.con.createStatement();

            stmt.execute(sql);
            stmt.close();
            AdminMain.con.close();
        } catch (SQLException e) {
            System.err.println("연결 오류" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
    }

    //데이터 하나 찾아와서 return 해줌
    public String getData(String sql) {
        String data = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.err.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            AdminMain.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", AdminMain.user_name, AdminMain.password);
            System.out.println("연결 완료!");

            Statement stmt = AdminMain.con.createStatement();

            ResultSet result = stmt.executeQuery(sql);    //DB로부터 읽어온 데이터

            if (result.next()) {
                data = result.getString(1);
            }

            stmt.close();
            AdminMain.con.close();
        } catch (SQLException e) {
            System.err.println("getData 연결 오류" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        return data;
    }

}