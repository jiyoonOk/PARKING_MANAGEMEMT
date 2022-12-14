import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;


class Pay extends JFrame implements ActionListener {

    //DB와 연동한 사용자 정보값 받아온 변수들
    String userId, userName, userCarNum, userFloorNum, userArea;
    Date userCarIn, userCarOut, userHours;
    int userTotalFee, userPoint;
    boolean userIsReserved;

    Pay() {
        Container ct = getContentPane();
        ct.setLayout(null);

        JButton b = new JButton("정산");
        b.setBounds(50,100,70,30);
        ct.add(b);

        b.addActionListener(this);
    }

    //TODO : 다은이랑 연결할 때 -> [출차] 버튼의 addActionListener 여기서부터 연결하기!!
    public void actionPerformed(ActionEvent ae) {
        //TODO : dev 전에는 여기서 jdbc 연결하면 되는데 나중에 dev하면 이거 지우고 !!!클래스 매개변수로 값 받아와야 함 !!!

        String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
        String user = "root";
        String password = "wldmsdl38!";
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
            strSql = "SELECT * FROM purchase WHERE user_id='jieunyang';";

            ResultSet result = dbSt.executeQuery(strSql); //DB로부터 읽어온 레코드 객체화

            while(result.next()) {
                userCarIn = result.getDate("car_in");
                userCarOut = result.getDate("car_out");
                userHours = result.getDate("hours");
                userIsReserved = result.getBoolean("is_reserved");
                userTotalFee = result.getInt("total_fee");
                userFloorNum = result.getString("floor_num");
                userArea = result.getString("area");
            }
            dbSt.close();

            //user 값 가져오기
            strSql = "SELECT name, car_num, point FROM user WHERE user_id='jieunyang';";

            result = dbSt.executeQuery(strSql); //DB로부터 읽어온 레코드 객체화

            while(result.next()) {
                userId = result.getString("id");
                userName = result.getString("name");
                userCarNum = result.getString("car_num");
                userPoint = result.getInt("point");
            }
            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException : " + e.getMessage());
        }

        if(ae.getActionCommand().equals("정산")){
            if(userIsReserved) { //예약 Inquire.java에서 쓴 예약 boolean값 받아쓰기
                Date currentDate = PayMain.currentDateTime();
                try {
                    long result =  NormalPay.calculateTime(currentDate,userCarOut);
                    if (result>0) { //초과금액 결제 //(car_out - 현재시간)>0 일 경우
                        try {
                            ExcessFeePay excessFee = new ExcessFeePay(userId, userName, userCarNum, userCarOut, userHours, userTotalFee, userPoint); //나중에 생성자 보내라
                            excessFee.setSize(500,400);
                            excessFee.setVisible(true);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {  //예약 정상 출차 클래스
                        RsvPopUp rsvPipUp = new RsvPopUp("정상 출차"); //이름, 차량번호 생성자 보내기
                        rsvPipUp.setSize(500,400);
                        rsvPipUp.setVisible(true);
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
            else { //일반출차. 예약 boolean값 0일 경우
                NormalPay nomal = new NormalPay(userId, userName, userCarNum, userCarIn, userCarOut, userHours, userTotalFee, userPoint); //생성자 다 보내
                nomal.setSize(500,400);
                nomal.setVisible(true);
            }
        }

    }
}

class NormalPay extends JFrame implements ActionListener{

    String userId, userName, userCarNum, userFloorNum, userArea;
    Date userCarIn, userCarOut, userHours;
    int userTotalFee, userPoint;
    boolean userIsReserved;

    JTextField jtfPoint;
    long diffMin;
    int userAddPoint; //시간계산용변수, 총결제금액, 예상적립금, 총적립금
    NormalPay(String uId, String uName, String uCarNum, Date uCarIn, Date uCarOut, Date uHours, int uTotalFee, int uPoint) {
        userId = uId;
        userName = uName;
        userCarNum = uCarNum;
        userCarIn = uCarIn;
        userCarOut = uCarOut;
        userHours = uHours;
        userTotalFee = uTotalFee;
        userPoint = uPoint;

        setTitle("출차 결제");
        Container ct = getContentPane();
        ct.setLayout(null);

        JLabel name = new JLabel("이        름 : ");       JLabel LabelUserName = new JLabel(userName);
        JLabel carNum = new JLabel("차량번호 : ");          JLabel LabelUserCarNum = new JLabel(userCarNum);
        JLabel inTime = new JLabel("입차시간 : ");          JLabel LabelUserInTime = new JLabel((Icon) userCarIn);
        JLabel outTime = new JLabel("출차시간 : ");         JLabel LabelUserOutTime = new JLabel((Icon) userCarOut);
        JLabel amount = new JLabel("결제금액 : ");          JLabel LabelUserAmount = new JLabel(String.valueOf(userTotalFee));
        JLabel point = new JLabel("적립금사용: ");          jtfPoint = new JTextField("0");
        JLabel nowPoint = new JLabel("보유 적립금: ");      JLabel LabelUserPoint = new JLabel(String.valueOf(userPoint));

        JLabel total = new JLabel("총 결제금액: ");         JLabel LabelUserTotalFee = new JLabel(Integer.toString(userTotalFee));
        JLabel addPoint = new JLabel("예정 적립금: ");      JLabel LabelUserAddPoint = new JLabel(Integer.toString(userPoint));

        //적립금 사용 전 결제금액, 예정적립금 대입 시키기
        userTotalFee = calculateFee(userHours.getTime()); //결제금액
        userAddPoint = (int) (userTotalFee * 0.05); //예정 적립금
        userPoint = calculatePoint(userTotalFee, jtfPoint.getText(), userAddPoint); //총적립금
        //TODO userTotalFee Insert 메소드 호출 -> 작성 완료
        //TODO userHours Insert 메소드 호출 -> 작성 완료
        DBconnection.insertDB(userTotalFee);
        DBconnection.insertDB(userHours);
        //TODO userPoint Update 메소드 호출 -> 작성 완료
        DBconnection.updateDB("user", "point", userPoint, userId);


        JButton btnPoint = new JButton("전액사용");
        btnPoint.addActionListener(this);
        jtfPoint.addActionListener(this);
        JButton btnPay = new JButton("결제");
        btnPay.addActionListener(this);
        RsvPopUp rsvPipUp = new RsvPopUp("정상 출차");

        name.setBounds(50,100,100,20);      LabelUserName.setBounds(120,100,100,20);
        carNum.setBounds(50,120,100,20);    LabelUserCarNum.setBounds(120,120,100,20);
        inTime.setBounds(50,140,100,20);    LabelUserInTime.setBounds(120,140,100,20);
        outTime.setBounds(50,160,100,20);   LabelUserOutTime.setBounds(120,160,100,20);
        amount.setBounds(50,180,100,20);    LabelUserAmount.setBounds(120,180,100,20);
        point.setBounds(50,200,100,20);     jtfPoint.setBounds(120,200,100,20);  btnPoint.setBounds(200,200,70,20);
        total.setBounds(50,260,100,20);     LabelUserTotalFee.setBounds(120,260,100,20);
        addPoint.setBounds(50,280,100,20);  LabelUserAddPoint.setBounds(120,280,100,20);

        btnPay.setBounds(100,300,100,20);

        ct.add(name);      ct.add(LabelUserName);
        ct.add(carNum);    ct.add(LabelUserCarNum);
        ct.add(inTime);    ct.add(LabelUserInTime);
        ct.add(outTime);   ct.add(LabelUserOutTime);
        ct.add(amount);    ct.add(LabelUserAmount);
        ct.add(point);     ct.add(jtfPoint);            ct.add(btnPoint);
        ct.add(total);     ct.add(LabelUserTotalFee);
        ct.add(addPoint);  ct.add(LabelUserAddPoint);
        ct.add(btnPay);
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
            if (s == "전액사용") jtfPoint.setText(Integer.toString(userPoint));
            Date currentDate = PayMain.currentDateTime();
            try {
                diffMin = calculateTime(userCarIn, currentDate);
                int usePoint = Integer.parseInt(String.valueOf(jtfPoint));
                userTotalFee = calculateFee(diffMin) - usePoint; //총 결제금액
                //TODO : 결제금액  userTotalFee 업데이트 메소드 호출
                DBconnection.updateDB("purchase", "total_fee", userTotalFee, userId);
                userAddPoint = (int) (userTotalFee * 0.05);//예정 적립금
                userPoint = calculatePoint(userTotalFee, jtfPoint.getText(), userAddPoint); //총적립금

                JLabel LabelUserPoint = new JLabel(String.valueOf(userPoint));
                JLabel LabelUserTotalFee = new JLabel(Integer.toString(userTotalFee));
                JLabel LabelUserAddPoint = new JLabel(Integer.toString(userPoint));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
     }
    public static long calculateTime(Date inTime, Date outTime) throws ParseException {
        long diffMin = (outTime.getTime() - inTime.getTime()) / 60000; //분 차이
        return diffMin;
    }
    public static int calculateFee(Long hours) {
        String stringHours = String.valueOf(hours);
        int totalFee = Integer.parseInt(stringHours) / 10 * 500;
        //TODO 경차, 장애인 시 10% 할인 -> 작성 완료
        if (DBconnection.searchDB())
            totalFee *= 0.1;
        return totalFee;
    }
    public static int calculatePoint(int totalFee, String usePoint, int addPoint) {
        int totalPoint = totalFee - Integer.parseInt(usePoint)+ addPoint;   //총적립금
        return totalPoint;
    }
}
class RsvPopUp extends JFrame implements ActionListener {// 예약 하고 출차. JDialog 클래스 객체 생성
    RsvPopUp(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        String f_name = "양지은";
        String f_carNum = "38너1849";

        JLabel l1_name = new JLabel("이        름 : ");
        JLabel l2_carNum = new JLabel("차량번호 : ");
        JLabel l3_str = new JLabel("정상 출차 되었습니다");
        JLabel name = new JLabel(f_name);
        JLabel carNum = new JLabel(f_carNum);

        JButton b = new JButton("확인");
        b.addActionListener(this);

        l1_name.setBounds(50,100,100,20); name.setBounds(120,100,100,20);
        l2_carNum.setBounds(50,120,100,20); carNum.setBounds(120,120,100,20);
        l3_str.setBounds(50,200,300,20);
        b.setBounds(100,300,100,20);
        ct.add(l1_name); ct.add(name);
        ct.add(l2_carNum); ct.add(carNum);
        ct.add(l3_str);
        ct.add(b);
    }
    public void actionPerformed(ActionEvent ae) { //확인 버튼 누르면 창 닫기
        dispose();
    }
}

class ExcessFeePay extends JFrame implements ActionListener {// 예약했는데 시간초과. JDialog 클래스 객체 생성
    String userId, userName, userCarNum;
    Date userCarOut, userHours;
    //예약했는데 시간초과면 userCarOut, userHours 도 현재시간 기준으로 Update 해줘야 함 !!
    int userTotalFee, userPoint;

    Date currentDate = PayMain.currentDateTime();

    long diffMin;     //시간 다른거
    int userAddFee, userAddPoint; //예정 추가적립금
    Date diffDate = new Date(diffMin); //분단위 초과시간을 date형으로 바꿈
    ExcessFeePay(String uId, String uName, String uCarNum, Date uCarOut, Date uHours, int uTotalFee, int uPoint) throws ParseException {
        userId = uId;
        userName = uName;
        userCarNum = uCarNum;
        userCarOut = uCarOut;
        userHours = uHours;
        userTotalFee = uTotalFee; //초과이용자는 이미 예약하기 했을 때 userTotalFee가 정해져있음.
        userPoint = uPoint;

        diffMin = NormalPay.calculateTime(userCarOut, currentDate);    //초과시간(분단위)
        userAddFee = NormalPay.calculateFee(diffMin);                  //초과금액 계산
        String diffDateToString = String.valueOf(diffDate);

        userTotalFee += userAddFee;
        userPoint = (int) (userAddFee * 0.05);

        DBconnection.updateDB("purchase", "total_fee", userAddFee, userId);
        DBconnection.updateDB("user", "point", userPoint, userId);

        setTitle("초과 금액 결제");
        Container ct = getContentPane();
        ct.setLayout(null);

        JLabel name = new JLabel("이        름 : ");            JLabel labelUserName = new JLabel(userName);
        JLabel carNum = new JLabel("차량번호 : ");               JLabel labelUserCarNum = new JLabel(userCarNum);
        JLabel timeOut = new JLabel("초과시간 : ");              JLabel labelUserTimeOut = new JLabel(diffDateToString);

        JLabel timeOutFee = new JLabel("총 초과 금액 : ");      JLabel labelUserTimeOutFee = new JLabel(Integer.toString(userAddFee));

        JButton b = new JButton("결제");
        b.addActionListener(this);

        name.setBounds(50,100,100,20);           labelUserName.setBounds(120,100,100,20);
        carNum.setBounds(50,120,100,20);         labelUserCarNum.setBounds(120,120,100,20);
        timeOut.setBounds(50,140,100,20);        labelUserTimeOut.setBounds(120,140,300,20);
        timeOutFee.setBounds(50,200,300,20);     labelUserTimeOutFee.setBounds(120,160,150,20);
        b.setBounds(100,300,100,20);

        ct.add(name);            ct.add(labelUserName);
        ct.add(carNum);          ct.add(labelUserCarNum);
        ct.add(timeOut);         ct.add(labelUserTimeOut);
        ct.add(timeOutFee);      ct.add(labelUserTimeOutFee);
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
    }
}

class PayMain {
    public static void main(String[] args) {
        Pay win = new Pay();
    win.setSize(300, 300);
    win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    win.setVisible(true);
    }
    public static Date currentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}


class DBconnection {
    static boolean discount;
    //경차, 장애인 할인여부
    public static boolean searchDB() {
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC", "root", "wldmsdl38!");
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            //purchase 값 가져오기
            strSql = "SELECT small_car, handicap FROM user_special_needs WHERE id='jieunyang';";
            ResultSet result = dbSt.executeQuery(strSql); //DB로부터 읽어온 레코드 객체화

            boolean userIsSmallCar=false, userIsDandicap = false;
            while(result.next()) {
                userIsSmallCar = result.getBoolean("small_car");
                userIsDandicap = result.getBoolean("handicap");
            }

            if (userIsSmallCar == true || userIsDandicap == true) {
                discount = true;
            }

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException : " + e.getMessage());
        }
        return discount;
    }

    //total_fee insert
    public static void insertDB(int index) {
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC", "root", "wldmsdl38!");
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            //purchase 값 가져오기
            strSql = "INSERT INTO purchase (total_fee) VALUES ('" + index +"')";
            dbSt.executeQuery(strSql);

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException : " + e.getMessage());
        }
    }

    //Hours insert
    public static void insertDB(Date index) {
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC", "root", "wldmsdl38!");
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            //purchase 값 가져오기
            strSql = "INSERT INTO purchase (Hours) VALUES ('" + index +"')";
            dbSt.executeUpdate(strSql); //DB로부터 읽어온 레코드 객체화

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException : " + e.getMessage());
        }
    }

    //일반결제 결제금액, 포인트 update
    public static void updateDB(String table, String columns, int index, String condition) {
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?serverTimezone=UTC", "root", "wldmsdl38!");
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            //purchase 값 가져오기
            strSql = " UPDATE " + table + " SET " + columns + " = '"+ index +"' WHERE " + condition + " = '" + "jieunyang';";
            dbSt.executeUpdate(strSql); //DB로부터 읽어온 레코드 객체화

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException : " + e.getMessage());
        }
    }
}
