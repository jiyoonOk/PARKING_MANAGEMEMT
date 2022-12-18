package Pay;

import Pay.DBconnection;
import com.oracle.tools.packager.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static Pay.Calculate.calculateFee;
import static Pay.Calculate.calculateTime;

//TODO 221217 03:52 SQL문 추가, 코드 간결하게 작성


public class NormalPay extends JFrame implements ActionListener {
    String userId, userName, userCarNum, userHours;
    LocalDateTime userCarIn=null, userCarOut=LocalDateTime.now();
    String userPoint, userTotalFee = null, userAddPoint, userTotalFeeAfterPoint;
    Boolean userIsSmallCar, userIsHandicap;
    JTextField jtfPoint;
    JLabel LabelUserTotalFee, LabelUserAddPoint;

    public NormalPay(String id) throws ParseException {
        setTitle("출차 결제");
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
        userId = "jieunyang";
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement();
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;
            ResultSet result;

            strSql = "SELECT user.name, user.car_num, user.point, purchase.car_in, purchase.hours From purchase, user WHERE purchase.user_id='" + userId + "' and user.id='" + userId + "';";
            result = dbSt.executeQuery(strSql);
            while (result.next()) {
                userName = result.getString("name");
                userCarNum = result.getString("car_num");
                userPoint = result.getString("point");
                userCarIn = result.getTimestamp("car_in").toLocalDateTime();
                userCarOut = result.getTimestamp("car_out").toLocalDateTime();
                userHours = String.valueOf(result.getTimestamp("hours"));
            }

            strSql = "SELECT small_car, handicap From user_special_needs WHERE id='" + userId + "';";
            result = dbSt.executeQuery(strSql);
            while (result.next()) {
                userIsSmallCar = result.getBoolean("small_car");
               userIsHandicap = result.getBoolean("handicap");
            }
            dbSt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("SQLException1 : " + e.getMessage());
        }


        userHours = calculateTime(ChronoUnit.MINUTES.between(userCarIn, userCarOut)); //hours 계산
        userTotalFee = calculateFee(ChronoUnit.MINUTES.between(userCarIn, userCarOut)); //total_fee 계산
        userAddPoint = String.valueOf((int) (Integer.parseInt(userTotalFee) * 0.05)); //추가적립금 계산



        JLabel name = new JLabel("이름 : ");          JLabel LabelUserName = new JLabel(userName);
        JLabel carNum = new JLabel("차량번호 : ");     JLabel LabelUserCarNum = new JLabel(userCarNum);
        JLabel inTime = new JLabel("입차시간 : ");     JLabel LabelUserInTime = new JLabel(String.valueOf(userCarIn));
        JLabel outTime = new JLabel("출차시간 : ");    JLabel LabelUserOutTime = new JLabel(String.valueOf(userCarOut));
        JLabel hours = new JLabel("주차시간 : ");      JLabel LabelUserHours = new JLabel(userHours);
        JLabel amount = new JLabel("결제금액 : ");     JLabel LabelUserAmount = new JLabel(userTotalFee);
        JLabel point = new JLabel("적립금사용: ");      jtfPoint = new JTextField("0");
        JLabel nowPoint = new JLabel("보유 적립금: ");  JLabel LabelUserPoint = new JLabel(userPoint);
        JLabel total = new JLabel("총 결제금액: ");     LabelUserTotalFee = new JLabel(userTotalFee);
        JLabel addPoint = new JLabel("예정 적립금: ");  LabelUserAddPoint = new JLabel(userAddPoint);
        JButton btnUsePoint = new JButton("사용");
        JButton btnUseAllPoint = new JButton("전액사용");
        JButton btnPay = new JButton("결제");




        if (userIsSmallCar) {
            JLabel discountSmallCar = new JLabel("경차 구역 10% 할인");
            discountSmallCar.setBounds(150, 200, 200, 20);
            userTotalFee = String.valueOf(Integer.parseInt(userTotalFee) * 0.1);
        }
        if (userIsHandicap) {
            JLabel discountHandicap = new JLabel("장애인 구역 10% 할인");
            discountHandicap.setBounds(150, 200, 200, 20);
            userTotalFee = String.valueOf(Integer.parseInt(userTotalFee) * 0.1);
        }




        name.setBounds(50, 100, 100, 20);        LabelUserName.setBounds(120, 100, 100, 20);
        carNum.setBounds(50, 120, 100, 20);      LabelUserCarNum.setBounds(120, 120, 100, 20);
        inTime.setBounds(50, 140, 100, 20);      LabelUserInTime.setBounds(120, 140, 200, 20);
        outTime.setBounds(50, 160, 100, 20);     LabelUserOutTime.setBounds(120, 160, 200, 20);
        hours.setBounds(50, 180, 100, 20);       LabelUserHours.setBounds(120, 180, 200, 20);
        amount.setBounds(50, 200, 100, 20);      LabelUserAmount.setBounds(120, 200, 100, 20);
        point.setBounds(50, 220, 100, 20);       jtfPoint.setBounds(120, 220, 70, 20);
        btnUsePoint.setBounds(200, 220, 80, 20); btnUseAllPoint.setBounds(280, 220, 100, 20);
        nowPoint.setBounds(120, 240, 100, 20);   LabelUserPoint.setBounds(200, 240, 70, 20);
        total.setBounds(50, 280, 100, 20);       LabelUserTotalFee.setBounds(120, 280, 100, 20);
        addPoint.setBounds(50, 300, 100, 20);    LabelUserAddPoint.setBounds(120, 300, 100, 20);
        btnPay.setBounds(100, 320, 100, 20);

        ct.add(name);     ct.add(LabelUserName);
        ct.add(carNum);   ct.add(LabelUserCarNum);
        ct.add(inTime);   ct.add(LabelUserInTime);
        ct.add(outTime);  ct.add(LabelUserOutTime);
        ct.add(hours);    ct.add(LabelUserHours);
        ct.add(amount);   ct.add(LabelUserAmount);
        ct.add(point);    ct.add(jtfPoint);         ct.add(btnUsePoint);   ct.add(btnUseAllPoint);
        ct.add(nowPoint); ct.add(LabelUserPoint);
        ct.add(total);    ct.add(LabelUserTotalFee);
        ct.add(addPoint); ct.add(LabelUserAddPoint);
        ct.add(btnPay);

        btnUsePoint.addActionListener(this);
        btnUseAllPoint.addActionListener(this);
        btnPay.addActionListener(this);
        jtfPoint.addActionListener(this);


    }


    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        if (s == "결제") {
            new Thread() {
                public void run() {
                    for (int i = 0; i < 3; i++) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                        }
                        JOptionPane.getRootFrame().dispose();
                    }
                }
            }.start();
            JOptionPane.showOptionDialog(getParent(), "결제중...", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "결제되었습니다.");
        }
        else {
            if (s == "전액사용") jtfPoint.setText(userPoint);
            int usePoint = Integer.parseInt(jtfPoint.getText());
            //userTotalFeeAfterPoint = userTotalFee; //총 결제금액

            userTotalFee = String.valueOf(Integer.parseInt(userTotalFee)-usePoint);
            userAddPoint = String.valueOf((int) (Integer.parseInt(userTotalFee)-usePoint) * 0.05);
            userPoint = String.valueOf(Integer.parseInt(userPoint) - usePoint + userAddPoint);

            LabelUserTotalFee.setText(userTotalFee);
            LabelUserAddPoint.setText(String.valueOf(userAddPoint));

            //총 결제금액 = 결제금액 - 사용적립금
            //총 적립금 = 보유적립금 - 사용적립금 + 추가적립금
            //총 결제금액, 총 적립금을 같이 써도 되나?

        }
        DBconnection.updateDB("purchase", "car_out", userCarOut, "user_id", userId); //출차시간 업데이트
        DBconnection.updateDB("purchase", "hours", userHours, "user_id", userId); //출차시간 업데이트
        DBconnection.updateDB("purchase", "total_fee", userTotalFee, "user_id", userId); //출차시간 업데이트
        DBconnection.updateDB("user", "point", userPoint, "id", userId); //출차시간 업데이트
        DBconnection.updateDB("purchase", "is_cancel", "0", "user_id", userId); //결제취소유무 업데이트 (0:결제O 1:결제O취소O)
        /*
        출차시간 insert <- LocalDateTime
        hours  insert <- String
        결제금액 insert <- String
        포인트  update <- String
         */
    }
}