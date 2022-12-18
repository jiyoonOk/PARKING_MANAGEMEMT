package Pay;

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

public class ExcessFeePay extends JFrame implements ActionListener {// 예약했는데 시간초과. JDialog 클래스 객체 생성
    LocalDateTime userCarOut;
    LocalDateTime currentDate = LocalDateTime.now();
    String userId, userName, userCarNum, userHours;
    String userPoint, userTotalFee, userAddPoint, userAddFee;
    Boolean userIsCancel;




    public ExcessFeePay(String id) throws ParseException {
        String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
        String user = "root";
        String password = "wldbs1004";
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        userId = id;
        try { //내가 mysql에 만든 parking 데이터베이스 연결
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            //purchase 값 가져오기
            strSql = "SELECT user.name, user.car_num, purchase.car_out, , purchase.total_fee, purchase.is_cancel From purchase, user WHERE purchase.user_id='" + userId + "' and user.id='" + userId + "';";
            ResultSet result = dbSt.executeQuery(strSql);

            while (result.next()) {
                userName = result.getString("name");
                userCarNum = result.getString("car_num");
                userCarOut = result.getTimestamp("car_out").toLocalDateTime();
                userTotalFee = result.getString("total_fee");
                userIsCancel = result.getBoolean("is_cancel");
            }
            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException1 : " + e.getMessage());
        }



        userHours = calculateTime(ChronoUnit.MINUTES.between(userCarOut, currentDate)); //hours 계산
        userAddFee = calculateFee(ChronoUnit.MINUTES.between(userCarOut, currentDate)); //초과금액 계산

        //String stringCurrentDatet = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        userTotalFee += userAddFee;
        userPoint = String.valueOf(Integer.parseInt(userPoint) + (Integer.parseInt(userAddFee) * 0.05));

        setTitle("초과 금액 결제");
        Container ct = getContentPane();
        ct.setLayout(null);

        JLabel name = new JLabel("이        름 : ");
        JLabel labelUserName = new JLabel(userName);
        JLabel carNum = new JLabel("차량번호 : ");
        JLabel labelUserCarNum = new JLabel(userCarNum);
        JLabel carOut = new JLabel("예정출차시간 : ");
        JLabel labelUserCarOut = new JLabel(String.valueOf(userCarOut));
        JLabel currunet = new JLabel("현재시간 : ");
        JLabel labelCurrunet = new JLabel(String.valueOf(currentDate));
        JLabel timeOut = new JLabel("초과시간 : ");
        JLabel labelUserTimeOut = new JLabel(userHours);

        JLabel timeOutFee = new JLabel("총 초과 금액 : ");
        JLabel labelUserTimeOutFee = new JLabel(userAddFee);

        JButton b = new JButton("결제");
        b.addActionListener(this);

        name.setBounds(50, 100, 100, 20);
        labelUserName.setBounds(150, 100, 100, 20);
        carNum.setBounds(50, 120, 100, 20);
        labelUserCarNum.setBounds(150, 120, 100, 20);
        carOut.setBounds(50, 140, 100, 20);
        labelUserCarOut.setBounds(150, 140, 200, 20);
        currunet.setBounds(50, 160, 100, 20);
        labelCurrunet.setBounds(150, 160, 200, 20);

        timeOut.setBounds(50, 180, 100, 20);
        labelUserTimeOut.setBounds(150, 180, 300, 20);
        timeOutFee.setBounds(50, 220, 300, 20);
        labelUserTimeOutFee.setBounds(150, 220, 150, 20);
        b.setBounds(100, 300, 100, 20);

        ct.add(name);           ct.add(labelUserName);
        ct.add(carNum);         ct.add(labelUserCarNum);
        ct.add(carOut);         ct.add(labelUserCarOut);
        ct.add(currunet);       ct.add(labelCurrunet);
        ct.add(timeOutFee);     ct.add(labelUserTimeOutFee);
        ct.add(timeOut);        ct.add(labelUserTimeOut);
        ct.add(b);
    }

    public void actionPerformed(ActionEvent ae) { //결제 버튼 누르면 이벤트
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

        DBconnection.updateDB("purchase", "car_out", userCarOut, "user_id", userId); //출차시간 업데이트
        DBconnection.updateDB("purchase", "hours", userHours, "user_id", userId); //출차시간 업데이트
        DBconnection.updateDB("purchase", "total_fee", userTotalFee, "user_id", userId); //출차시간 업데이트
        DBconnection.updateDB("user", "point", userPoint, "id", userId); //출차시간 업데이트
        DBconnection.updateDB("purchase", "is_cancel", "0", "user_id", userId); //결제취소유무 업데이트 (0:결제O 1:결제O취소O)
    }
}
