package Parking;

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
    final int DEFAULT_SIZE = 100, FIRST_OF_HALF_INFO = 825;
    String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
    String user = "root";
    String password = "wldbs1004";
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
        JLabel time          = new JLabel(userCarIn.getHour()+"시"+userCarIn.getMinute()+"분");
        JLabel location      = new JLabel("주차구역: ");       //주차구역
        ParkingLot park = new ParkingLot(userId);
        JLabel userFloorNum = new JLabel(park.userFloor);   //주차위치(층)
        JLabel userArea = new JLabel(park.userArea);     //주차위치(구역)

        parkingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = JOptionPane.showConfirmDialog(null, "주차 하시겠습니까?", "안내", JOptionPane.YES_NO_OPTION);
                if(r == JOptionPane.YES_OPTION) {

                    int f = 0; //층수 int 로 변환
                    if ( park.userFloor == "B1") { f = 1; }
                    else if ( park.userFloor == "B2") { f = 2; }
                    else if ( park.userFloor == "B3") { f = 3; }


                    //입차시간 업데이트
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
                    } catch (ClassNotFoundException cne) {
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

                        //구매 정보 추가(car_out,hours,total_fee,is_cancel 제외)
                        strSql = "INSERT INTO parking.purchase ('purchase_id','car_in','is_reserved','floor_num','area','user_id')" +
                                " VALUES ('"+purchase_id+"','"+userCarIn+"','0','"+f+"','"+park.userArea+"','"+userId+"');";
                        dbSt.executeQuery(strSql);
                        System.out.println("purchase 일반주차 추가 완료");

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

        nameLabel.setBounds(Reservation.FIRST_OF_INFO, Reservation.TOP_OF_PARK +10, DEFAULT_SIZE, 25);
        name.setBounds(FIRST_OF_HALF_INFO, Reservation.TOP_OF_PARK +10, DEFAULT_SIZE, 25);
        carNumLabel.setBounds(Reservation.FIRST_OF_INFO, Reservation.TOP_OF_PARK +100, DEFAULT_SIZE, 25);
        carNum.setBounds(FIRST_OF_HALF_INFO, Reservation.TOP_OF_PARK +100, DEFAULT_SIZE, 25);
        currTime.setBounds(Reservation.FIRST_OF_INFO, Reservation.TOP_OF_PARK +200, DEFAULT_SIZE, 25);
        time.setBounds(FIRST_OF_HALF_INFO, Reservation.TOP_OF_PARK +200, DEFAULT_SIZE, 25);
        location.setBounds(Reservation.FIRST_OF_INFO, Reservation.TOP_OF_PARK +300, DEFAULT_SIZE, 25);
        userFloorNum.setBounds(FIRST_OF_HALF_INFO, Reservation.TOP_OF_PARK +300, 25, 25);
        userArea.setBounds(870, Reservation.TOP_OF_PARK +300, 25, 25);

        parkingBtn.setBounds(Reservation.FIRST_OF_INFO, 480, Reservation.DEFAULT_SIZE, 50);
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