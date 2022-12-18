package Pay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

// TODO 221217 03:52 SQL문 추가

public class RsvPopUp extends JFrame implements ActionListener {// 예약 하고 출차. JDialog 클래스 객체 생성
    String userId, userName, userCarNum;
    public RsvPopUp(String id) {
        setTitle("정상 출차");
        Container ct = getContentPane();
        ct.setLayout(null);

        String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
        String user = "root";
        String password = "wldbs1004";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        userId=id;
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement();
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            strSql = "SELECT name, car_num From user WHERE id='" + userId + "';";
            ResultSet result = dbSt.executeQuery(strSql);
            while (result.next()) {
                userName = result.getString("name");
                userCarNum = result.getString("car_num");
            }
            dbSt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("SQLException1 : " + e.getMessage());
        }

        JLabel l1_name = new JLabel("이        름 : ");
        JLabel l2_carNum = new JLabel("차량번호 : ");
        JLabel l3_str = new JLabel("정상 출차 되었습니다");
        JLabel name = new JLabel(userName);
        JLabel carNum = new JLabel(userCarNum);

        JButton b = new JButton("확인");
        b.addActionListener(this);

        l1_name.setBounds(50, 100, 100, 20);
        name.setBounds(120, 100, 100, 20);
        l2_carNum.setBounds(50, 120, 100, 20);
        carNum.setBounds(120, 120, 100, 20);
        l3_str.setBounds(50, 200, 300, 20);
        b.setBounds(100, 300, 100, 20);
        ct.add(l1_name);
        ct.add(name);
        ct.add(l2_carNum);
        ct.add(carNum);
        ct.add(l3_str);
        ct.add(b);
    }

    public void actionPerformed(ActionEvent ae) { //확인 버튼 누르면 창 닫기
        dispose();
    }
}