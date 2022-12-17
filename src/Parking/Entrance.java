package Parking;

import Pay.DBconnection;
import UserMain.UserMain;
import UserMain.ParkingLot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Random;

// TODO 221217 13:35 (다은) 생성자 매개변수 변경
// TODO 221217 14:50 (다은) 현재시간 타입 변경
// TODO 221217 17:30 (다은) 주차버튼 이벤트에 DBconnection 사용
// TODO 221217 19:22 (다은) 상수 변경
// TODO 221217 22:00 (다은) 구매 아이디 생성, sql 추가

public class Entrance extends JFrame {
    String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
    String user = "root";
    String password = "wldmsdl38!";
    String userId;
    JButton parkingBtn = new JButton("주차하기");       //주차완료버튼
    JButton cancelBtn = new JButton("취소");   //취소버튼
    public Entrance(String id) {
        userId = id;

        JLabel nameLabel     = new JLabel("이름: ");           //이름
        JLabel name          = new JLabel("");
        JLabel carNumLabel   = new JLabel("차량번호: ");       //차량번호
        JLabel carNum        = new JLabel("");
        JLabel currTime      = new JLabel("현재시간 : ");      //현재시간
        LocalDateTime userCarIn  = LocalDateTime.now();
        JLabel time          = new JLabel(String.valueOf(userCarIn));
        JLabel location      = new JLabel("주차구역: ");       //주차구역
        ParkingLot park = new ParkingLot(userId);
        JLabel userFloorNum = new JLabel(String.valueOf(park.userFloor));   //주차위치(층)
        JLabel userArea = new JLabel(String.valueOf(park.userNum));     //주차위치(구역)

        parkingBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int r = JOptionPane.showConfirmDialog(parkingBtn, "주차 하시겠습니까?", "안내", JOptionPane.YES_NO_OPTION);
                if(r == JOptionPane.YES_OPTION) {
                    //입차시간 업데이트
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
                    } catch (ClassNotFoundException e) {
                        System.err.println("드라이버 로드에 실패했습니다.");
                    }
                    userId = id;
                    try {
                        Connection con = DriverManager.getConnection(url, user, password);
                        System.out.println("DB 연결 완료");
                        Statement dbSt = con.createStatement();
                        System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

                        String strSql;
                        ResultSet result;

                        //구매아이디 전체확인 후 생성
                        result = dbSt.executeQuery("SELECT purchase.purchase_id FROM parking.purchase");
                        int purchase_id = new Random().nextInt(9999);
                        while (result.next()) {
                            if ( purchase_id == result.getInt("purchase_id")){
                                purchase_id = new Random().nextInt(9999);
                            }
                        }//purchase_id 생성

                        strSql = "INSERT INTO parking.purchase ('purchase_id','car_in','is_reserved','floor_num','area','user_id')" +
                                " VALUES ('"+purchase_id+"','"+userCarIn+"','0','"+userFloorNum+"')";
                        dbSt.executeQuery(strSql);

                        dbSt.close();

                        strSql = "SELECT small_car, handicap From user_special_needs WHERE id='" + userId + "';";
                        result = dbSt.executeQuery(strSql);
                        while (result.next()) {
                        }
                        dbSt.close();
                        con.close();
                    } catch (SQLException se) {
                        System.out.println("SQLException1 : " + se.getMessage());
                    }
                    JOptionPane.showMessageDialog(null, "주차 되었습니다!");
                    dispose();
                }
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "취소 되었습니다!");
                dispose();
            }
        });


        Container parkCt = getContentPane();
        parkCt.setLayout(null);

        nameLabel.setBounds(Reservation.FIRST_OF_INFO, Reservation.TOP_OF_PARK +10, Reservation.DEFAULT_SIZE, 25);
        name.setBounds(Reservation.FIRST_OF_HALF_INFO, Reservation.TOP_OF_PARK +10, Reservation.DEFAULT_SIZE, 25);
        carNumLabel.setBounds(Reservation.FIRST_OF_INFO, Reservation.TOP_OF_PARK +100, Reservation.DEFAULT_SIZE, 25);
        carNum.setBounds(Reservation.FIRST_OF_HALF_INFO, Reservation.TOP_OF_PARK +100, Reservation.DEFAULT_SIZE, 25);
        currTime.setBounds(Reservation.FIRST_OF_INFO, Reservation.TOP_OF_PARK +200, Reservation.DEFAULT_SIZE, 25);
        time.setBounds(Reservation.FIRST_OF_HALF_INFO, Reservation.TOP_OF_PARK +200, Reservation.DEFAULT_SIZE, 25);
        location.setBounds(Reservation.FIRST_OF_INFO, Reservation.TOP_OF_PARK +300, Reservation.DEFAULT_SIZE, 25);
        userFloorNum.setBounds(Reservation.FIRST_OF_HALF_INFO, Reservation.TOP_OF_PARK +300, 25, 25);
        userArea.setBounds(870, Reservation.TOP_OF_PARK +300, 25, 25);

        parkingBtn.setBounds(UserMain.FIRST_OF_INFO, 480, Reservation.DEFAULT_SIZE, 50);
        cancelBtn.setBounds(Reservation.FIRST_OF_HALF_INFO, 480, Reservation.DEFAULT_SIZE, 50);

        parkCt.add(park.floor);
        parkCt.add(park.car);
        parkCt.add(nameLabel);     parkCt.add(name);
        parkCt.add(carNumLabel);   parkCt.add(carNum);
        parkCt.add(currTime); parkCt.add(time);
        parkCt.add(location);      parkCt.add(userFloorNum);    parkCt.add(userArea);


        parkCt.add(parkingBtn);   parkCt.add(cancelBtn); //완료, 취소 버튼


    }//Entrance 생성자 끝

}//Entrance 클래스 끝