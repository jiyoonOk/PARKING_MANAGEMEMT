package ParkingLot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class Car extends JFrame implements ItemListener{
    String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
    String user = "root";
    String password = "wldbs1004";
    String userId;
    static boolean userIsWoman, userIsSmallCar, userIsHandicap;
    static String[] sfloor = {"B1", "B2", "B3"}; //주차층수 콤보박스
    static JComboBox floor = new JComboBox(sfloor);
    static JLabel area = new JLabel("주차구역 : ");
    public static JLabel userFloor = new JLabel(); //사용자선택값 층수(초기값-B1층)
    static JLabel userArea = new JLabel(); //사용자선택값 구역

    static JPanel p[] = new JPanel[6]; //A~D열 패널


    static String[] parkingLot = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4", "D1", "D2", "D3", "D4"}; //주차구역

    static ImageIcon[] carIcons = {
            new ImageIcon("images/Car.jpg"), //일반, checked
            new ImageIcon("images/woman.jpg"), //여성
            new ImageIcon("images/disabled.jpg"), //장애인
            new ImageIcon("images/smallCar.jpg"), //경차
            new ImageIcon("images/checkedWoman.jpg"), // 여성, checked
            new ImageIcon("images/checkedDisabled.jpg"), //장애인, checked
            new ImageIcon("images/checkedSmallCar.jpg") //경차, checked
    };
    CardLayout cardLayout = new CardLayout();
    JPanel car, panelB1, panelB2, panelB3;

    public Car(String id) {
        userId = id;
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
            strSql = "SELECT user_special_needs.woman, user_special_needs.small_car, user_special_needs.handicap " +
                    "FROM parking.user_special_needs " +
                    "WHERE user_special_needs.id='"+userId+"';";
            ResultSet r = dbSt.executeQuery(strSql); //DB로부터 읽어온 레코드 객체화
            System.out.println("user_special_needs 추출 완료");

            while (r.next()) {
                userIsWoman = r.getBoolean("woman");
                userIsSmallCar = r.getBoolean("small_car");
                userIsHandicap = r.getBoolean("handicap");
            }

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException3 : " + e.getMessage());
        }

        Container ct = getContentPane();
        ct.setLayout(null);

        car = new JPanel();     //층수 껍데기 패널
        View1 B1 = new View1(); //B1층 패널
        View2 B2 = new View2(); //B2층 패널
        View3 B3 = new View3(); //B3층 패널

        car.setLayout(cardLayout);
        car.add("B1", B1);
        car.add("B2", B2);
        car.add("B3", B3);
        cardLayout.show(car, "B1");

        car.setBounds(20, 100, 300, 400);
        floor.setBounds(20, 40, 100, 20);
        area.setBounds(130, 40, 100, 20); //주차구역 라벨ct.add(car); ct.add(floor);
        userFloor.setBounds(240, 40, 50, 20); //층수 (사용자선택)
        userArea.setBounds(300, 40, 50, 20); //구역 (사용자선택0

        ct.add(floor);  ct.add(area);  ct.add(userFloor);  ct.add(userArea);
        ct.add(car);

        floor.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String f = (String)floor.getSelectedItem();
        switch (f) {
            case "B1" :
                cardLayout.show(car, "B1"); userFloor.setText("B1"); userArea.setText("");
                for (int i=0; i<2; i++)   { View2.btn[i].setIcon(carIcons[1]); View3.btn[i].setIcon(carIcons[1]); }
                for (int i=2; i<4; i++)   { View2.btn[i].setIcon(carIcons[2]); View3.btn[i].setIcon(carIcons[2]); }
                for (int i=4; i<12; i++)  { View2.btn[i].setIcon(null);        View3.btn[i].setIcon(null);        }
                for (int i=12; i<16; i++) { View2.btn[i].setIcon(carIcons[3]); View3.btn[i].setIcon(carIcons[3]); }
                break;
            case "B2" :
                cardLayout.show(car, "B2"); userFloor.setText("B2"); userArea.setText("");
                for (int i=0; i<2; i++)   { View1.btn[i].setIcon(carIcons[1]); View3.btn[i].setIcon(carIcons[1]); }
                for (int i=2; i<4; i++)   { View1.btn[i].setIcon(carIcons[2]); View3.btn[i].setIcon(carIcons[2]); }
                for (int i=4; i<12; i++)  { View1.btn[i].setIcon(null);        View3.btn[i].setIcon(null);        }
                for (int i=12; i<16; i++) { View1.btn[i].setIcon(carIcons[3]); View3.btn[i].setIcon(carIcons[3]); }
                break;
            case "B3" :
                cardLayout.show(car, "B3"); userFloor.setText("B3"); userArea.setText("");
                for (int i=0; i<2; i++)   { View1.btn[i].setIcon(carIcons[1]); View2.btn[i].setIcon(carIcons[1]); }
                for (int i=2; i<4; i++)   { View1.btn[i].setIcon(carIcons[2]); View2.btn[i].setIcon(carIcons[2]); }
                for (int i=4; i<12; i++)  { View1.btn[i].setIcon(null);        View2.btn[i].setIcon(null);        }
                for (int i=12; i<16; i++) { View1.btn[i].setIcon(carIcons[3]); View2.btn[i].setIcon(carIcons[3]); }
                break;
        }
    }
}

/*
class CardMain {
    public static void main(String[] args) {
        Card win = new Card("카드레이아웃테스트");
        win.setSize(500, 600);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
}*/
