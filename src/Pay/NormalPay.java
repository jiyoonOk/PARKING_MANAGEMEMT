package Pay;

import Pay.DBconnection;
import com.oracle.tools.packager.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static Pay.Calculate.calculateFee;
import static Pay.Calculate.calculateTime;

//TODO 221217 03:52 SQL문 추가, 코드 간결하게 작성


public class NormalPay extends JFrame implements ActionListener {
    String userId, userName, userCarNum, userHours;
    String formatCarIn, formatNow;
    LocalDateTime userCarIn;
    int userTotalFee, userPoint;
    int calTotalFee, calAddPoint, calTotalPoint, discount;
    boolean userIsSmallCar, userIsHandicap;
    JTextField jtfPoint;
    JLabel LabelSpecialNeeds = new JLabel();
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
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement();
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;
            ResultSet result;
            userId = id;
            strSql = "SELECT user.name, user.car_num, user.point, purchase.car_in, purchase.hours From purchase, user WHERE purchase.user_id='" + userId + "' and user.id='" + userId + "';";
            result = dbSt.executeQuery(strSql);
            while (result.next()) {
                userName = result.getString("name");
                userCarNum = result.getString("car_num");
                userPoint = result.getInt("point");
                userCarIn = result.getTimestamp("car_in").toLocalDateTime();
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

        LocalDateTime now = LocalDateTime.now();
        formatCarIn = userCarIn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        formatNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        userHours = calculateTime(ChronoUnit.MINUTES.between(userCarIn, now)); //hours 계산 TODO 완료!!
        userTotalFee = calculateFee(ChronoUnit.MINUTES.between(userCarIn, now)); //초과금액 계산

        if (userIsSmallCar || userIsHandicap) {
            if(userIsSmallCar) LabelSpecialNeeds.setText("경차");
            else LabelSpecialNeeds.setText("장애인");
            JLabel LabelDiscount = new JLabel("주차 구역 10% 할인");
            LabelDiscount.setBounds(150, 200, 200, 20);
            LabelDiscount.setBounds(150, 200, 200, 20);
            discount = (int) (userTotalFee * 0.1);
        }


        JLabel name = new JLabel("이름 : ");          JLabel LabelUserName = new JLabel(userName);
        JLabel carNum = new JLabel("차량번호 : ");     JLabel LabelUserCarNum = new JLabel(userCarNum);
        JLabel inTime = new JLabel("입차시간 : ");     JLabel LabelUserInTime = new JLabel(formatCarIn);
        JLabel outTime = new JLabel("출차시간 : ");    JLabel LabelUserOutTime = new JLabel(formatNow);
        JLabel hours = new JLabel("주차시간 : ");      JLabel LabelUserHours = new JLabel(userHours);
        JLabel amount = new JLabel("결제금액 : ");     JLabel LabelUserAmount = new JLabel(String.valueOf(userTotalFee));
        JLabel point = new JLabel("적립금사용: ");      jtfPoint = new JTextField("0");
        JLabel nowPoint = new JLabel("보유 적립금: ");  JLabel LabelUserPoint = new JLabel(String.valueOf(userPoint));
        JLabel total = new JLabel("총 결제금액: ");     LabelUserTotalFee = new JLabel(String.valueOf(userTotalFee));
        JLabel addPoint = new JLabel("예정 적립금: ");  LabelUserAddPoint = new JLabel(String.valueOf(userTotalFee * 0.05));
        JButton btnUsePoint = new JButton("사용");
        JButton btnUseAllPoint = new JButton("전액사용");
        JButton btnPay = new JButton("결제");


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
            dispose();
        }
        else {
            int usePoint = 0;
            if (s == "전액사용")
                jtfPoint.setText(String.valueOf(userPoint));
            else usePoint = Integer.parseInt(jtfPoint.getText());
            //userTotalFeeAfterPoint = userTotalFee; //총 결제금액

            calTotalFee = userTotalFee-usePoint-discount; //총 결제금액 = 결제금액-적립금-특이사항할인
            calAddPoint = (int)(calTotalFee * 0.05); //예정 추가 적립금 계산
            calTotalPoint = userPoint - usePoint + calAddPoint; //총 적립금 = 보유적립금 - 사용적립금 + 추가적립금;
            LabelUserTotalFee.setText(String.valueOf(calTotalFee));
            LabelUserAddPoint.setText(String.valueOf(calAddPoint));
        }
        DBconnection.updateDB("purchase", "car_out", formatNow, "user_id", userId); //출차시간 업데이트
        DBconnection.updateDB("purchase", "hours", userHours, "user_id", userId); //출차시간 업데이트
        DBconnection.updateDB("purchase", "total_fee", String.valueOf(calTotalFee), "user_id", userId); //출차시간 업데이트
        DBconnection.updateDB("user", "point", String.valueOf(calTotalPoint), "id", userId); //출차시간 업데이트
        DBconnection.updateDB("purchase", "is_cancel", "0", "user_id", userId); //결제취소유무 업데이트 (0:결제O 1:결제O취소O)

        /*
        출차시간 insert <- LocalDateTime
        hours  insert <- String
        결제금액 insert <- String
        포인트  update <- String
         */
    }
}