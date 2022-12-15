package UserMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import common.ParkingLot;
import view.CostFrame;
import view.Mypage;
import view.Notice;

public class UserMain extends JFrame{
    public static final int TOP_OF_FRAME = 30, TOP_OF_PARK = 90; //전체 기준
    public static final int FIRST_OF_FRAME = 50, FIRST_OF_INFO = 725; //주차장 기준
    public static final int WIDTH_OF_MAIN_BUTTON = 185, HEIGHT_OF_MAIN_BUTTON = 80; //메인 버튼들
    JButton fareTagButton, rsvButton, parkButton, checkButton, payButton;
    JMenu menu;
    JMenuItem myPage, notion, question, logout;
    String id;

    public UserMain(String loginId) {
        Container mainCt = getContentPane();
        mainCt.setLayout(null);

        id = loginId;

        //===============================================================================================

        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        menu       = new JMenu("메뉴");             //메뉴바에 표시되는 메뉴이름
        myPage     = new JMenuItem("마이페이지");  //아이템들
        notion     = new JMenuItem("공지사항");
        question   = new JMenuItem("문의사항");
        //-----------------------------------------------구분선 추가함
        logout     = new JMenuItem("로그아웃");


        myPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Mypage mg=new Mypage(id);
                mg.setSize(400,430);
                mg.setLocation(400, 0);
                mg.setTitle("마이페이지");
                mg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mg.setVisible(true);

            }
        });
        notion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notice nt=new Notice(id);
                nt.setSize(700,550);
                nt.setLocation(400, 0);
                nt.setTitle("공지사항");
                nt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nt.setVisible(true);
            }
        });
        question.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                question win = new question(id);
                win.setTitle("문의사항");
                win.setSize(800, 550);
                win.setLocation(400, 0);
                win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                win.setVisible(true);
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = JOptionPane.showConfirmDialog(null, "정말 로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });




        menu.add(myPage);    //연결
        menu.add(notion);
        menu.add(question);
        menu.addSeparator(); //구분선
        menu.add(logout);

        bar.add(menu);


        //===============================================================================================

        fareTagButton = new JButton("요금표");
        fareTagButton.addActionListener(new ActionListener() {    //요금표 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) {          //요금표 팝업
                CostFrame costFrame = new CostFrame("요금표");
                costFrame.setSize(300,300);
                costFrame.setVisible(true);
                costFrame.setLocationRelativeTo(null);
            }
        });


        rsvButton = new JButton("예약");
        rsvButton.addActionListener(new ActionListener() {        //예약버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) {          //예약화면 팝업
                Rsv m = new Rsv("주차 프로그램 - 주차예약");
                m.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                m.setSize(1000, 600);
                m.setVisible(true);

            }
        });

        parkButton = new JButton("주차");
        parkButton.addActionListener(new ActionListener() {       //주차 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                Park p = new Park("주차 프로그램 - 일반주차");
                p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                p.setSize(1000, 600);
                p.setVisible(true);
            }
        });

        checkButton = new JButton("조회");
        checkButton.addActionListener(new ActionListener() {      //조회 액션이벤트
            //DB와 연동한 사용자 정보값 받아온 변수들
            String userPurchaseId, userId, userName, userCarNum, userFloorNum, userArea;
            Timestamp userCarIn, userCarOut, userHours;
            LocalDateTime dateUserCarIn, dateUserCarOut;
            LocalTime dateUserHours;
            //String stringUserCarIn, stringUserCarOut, stringUserHours;
            int userTotalFee, userPoint;
            boolean userIsReserved;

            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
                String user = "root";
                String password = "wldbs1004"; //====================================================================================수정사항
                try { //mysql의 jdbc Driver 연결
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
                } catch (ClassNotFoundException nfe) {
                    System.err.println("드라이버 로드에 실패했습니다.");
                }
                try { //내가 mysql에 만든 parking 데이터베이스 연결
                    Connection con = DriverManager.getConnection(url, user, password); //=======================================수정사항
                    System.out.println("DB 연결 완료");
                    Statement dbSt = con.createStatement(); //질의어 생성해서 적용
                    System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

                    String strSql;

                    //purchase 값 가져오기 //=====================================================================================수정사항
                    strSql = "SELECT user.Name, user.car_num, user.point, purchase.purchase_id, purchase.user_id, purchase.car_in, purchase.car_out, purchase.hours, purchase.is_reserved, purchase.total_fee, purchase.floor_num, purchase.area From purchase, user WHERE purchase.user_id='+id+' and user.id='+id+';";
                    ResultSet result = dbSt.executeQuery(strSql);

                    while(result.next()) {
                        userName = result.getString("name");
                        userCarNum = result.getString("car_num");
                        userPoint = result.getInt("point");
                        userPurchaseId = result.getString("purchase_id");
                        userId = result.getString("user_id");
                        userCarIn = result.getTimestamp("car_in");
                        userCarOut = result.getTimestamp("car_out");
                        userHours = result.getTimestamp("hours");
                        userIsReserved = result.getBoolean("is_reserved");
                        userTotalFee = result.getInt("total_fee");
                        userFloorNum = result.getString("floor_num");
                        userArea = result.getString("area");
                    }
                    dbSt.close();
                    con.close(); //DB연동 끊기
                } catch (SQLException se) {  //================================================================================수정사항
                    System.out.println("SQLException1 : " + se.getMessage());
                }

                ////TimeStamp -> LocalDateTime
                //날짜 변환 : MySql과 java 간 시간 차이 +09:00 발생 (UTC 한국시간이 9시간 더 빠름)
                dateUserCarIn = userCarIn.toLocalDateTime();

                if(userIsReserved) { //예약 Inquire.java에서 쓴 예약 boolean값 받아쓰기
                    dateUserCarOut = userCarOut.toLocalDateTime();
                    LocalDateTime currentDate = LocalDateTime.now();
                    if (!(currentDate.isAfter(dateUserCarOut))) { //초과금액 결제, 현재시간이 출차시간보다 이후면(초과)
                        try {
                            dateUserCarOut = userCarOut.toLocalDateTime();
                            dateUserHours = userHours.toLocalDateTime().toLocalTime();
                            ExcessFeePay excessFee = new ExcessFeePay(userId, userName, userCarNum, dateUserCarIn, dateUserCarOut, dateUserHours, userTotalFee, userPoint); //나중에 생성자 보내라
                            excessFee.setSize(500,400);
                            excessFee.setVisible(true);
                        } catch (ParseException pe) {
                            throw new RuntimeException(pe);
                        }
                    }
                    else {  //예약 정상 출차 클래스
                        RsvPopUp rsvPipUp = new RsvPopUp(userName, userCarNum); //이름, 차량번호 생성자 보내기
                        rsvPipUp.setSize(500, 400);
                        rsvPipUp.setVisible(true);
                    }
                }
                else { //일반출차. 예약 boolean값 0일 경우
                    try {
                        NormalPay nolmalPay =  new NormalPay(userId, userName, userCarNum, dateUserCarIn, userTotalFee, userPoint);
                        nolmalPay.setSize(500,400);
                        nolmalPay.setVisible(true);
                    } catch (ParseException pae) {
                        throw new RuntimeException(pae);
                    }

                }
            }



        });

        payButton = new JButton("정산");
        payButton.addActionListener(new ActionListener() {        //정산 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO !# 결제창 불러오기
                int r = JOptionPane.showConfirmDialog(null, "출차 하시겠습니까?\n(확인 시 결제로 넘어감)", "안내", JOptionPane.YES_NO_OPTION);
                if(r == JOptionPane.YES_OPTION) {

                }
            }
        });

        ParkingLot park = new ParkingLot();
        park.floor.setBounds(FIRST_OF_FRAME, TOP_OF_FRAME, 100, 30);   //주차층수 콤보박스(B1, B2, B3)
        ParkingLot.car.setBounds(FIRST_OF_FRAME, TOP_OF_PARK, 650, 450);                   //주차장 패널

        fareTagButton.setBounds(850,TOP_OF_FRAME, 100, 30);            //요금표 버튼

        rsvButton.setBounds(FIRST_OF_INFO+20, TOP_OF_PARK+20, WIDTH_OF_MAIN_BUTTON, HEIGHT_OF_MAIN_BUTTON);             //예약 버튼
        parkButton.setBounds(FIRST_OF_INFO+20, TOP_OF_PARK+130, WIDTH_OF_MAIN_BUTTON, HEIGHT_OF_MAIN_BUTTON);            //주차 버튼
        checkButton.setBounds(FIRST_OF_INFO+20, TOP_OF_PARK+240, WIDTH_OF_MAIN_BUTTON, HEIGHT_OF_MAIN_BUTTON);           //조회 버튼
        payButton.setBounds(FIRST_OF_INFO+20, TOP_OF_PARK+350, WIDTH_OF_MAIN_BUTTON, HEIGHT_OF_MAIN_BUTTON);             //정산 버튼

        mainCt.add(park.floor);       //주차층수 콤보박스
        mainCt.add(ParkingLot.car);         //주차장패널
        mainCt.add(fareTagButton);          //요금표
        mainCt.add(rsvButton);              //예약버튼
        mainCt.add(parkButton);             //주차버튼
        mainCt.add(checkButton);            //조회버튼
        mainCt.add(payButton);              //정산버튼


    }//UserMain 생성자 끝

}//UserMain 클래스 끝


class Main extends JFrame{
    public static void main(String[] args) {
        UserMain m = new UserMain("daeunlee"); // TODO 로그인 창으로 이부분 옮겨서 입력된 아이디 넣기
        m.setTitle("주차 프로그램 - UserMain");
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(1000, 700);
        m.setVisible(true);
        m.setLocationRelativeTo(null);
    }

}


//일반 출차
class NormalPay extends JFrame implements ActionListener {
    static String userId;
    String userName;
    String userCarNum;
    String userFloorNum;
    String userArea;
    LocalDateTime userCarIn, userCarOut;
    LocalTime userHours=null;
    int userTotalFee, userPoint;
    boolean userIsReserved;

    JTextField jtfPoint;
    long diffMin;
    int userAddPoint, userTotalPoint, userTotalFeeAfterUsePoint; //적립금 사용 후 예상적립금, 총적립금, 총결제금액
    String toHours;

    JLabel LabelUserTotalFee, LabelUserAddPoint;

    NormalPay(String uId, String uName, String uCarNum, LocalDateTime uCarIn, int uTotalFee, int uPoint) throws ParseException {
        userId = uId;
        userName = uName;
        userCarNum = uCarNum;
        userCarIn = uCarIn;
        userCarOut = LocalDateTime.now(); //출차시간은 현재시간을 기준으로 함
        userTotalFee = uTotalFee;
        userPoint = uPoint;

        //JLabel 붙이기 용 LocalDate -> String 변환함
        String stringUserCarIn = userCarIn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String stringUserCarOut = userCarOut.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        setTitle("출차 결제");
        Container ct = getContentPane();
        ct.setLayout(null);

        //적립금 사용 전 결제금액, 예정적립금 대입 시키기
        LocalDateTime currentDate = LocalDateTime.now();
        diffMin = ChronoUnit.MINUTES.between(userCarIn, currentDate); //출차시간 계산
        userTotalFee = Calculate.calculateFee(diffMin); //결제금액 계산
        userAddPoint = (int) (userTotalFee * 0.05); //예정 적립금 계산

        userTotalFeeAfterUsePoint = userTotalFee;  //총결제금액용
        userTotalPoint = userPoint; //총 적립금 변수

        JLabel name = new JLabel("이        름 : ");
        JLabel LabelUserName = new JLabel(userName);
        JLabel carNum = new JLabel("차량번호 : ");
        JLabel LabelUserCarNum = new JLabel(userCarNum);
        JLabel inTime = new JLabel("입차시간 : ");
        JLabel LabelUserInTime = new JLabel(stringUserCarIn);
        JLabel outTime = new JLabel("출차시간 : ");
        JLabel LabelUserOutTime = new JLabel(stringUserCarOut);
        JLabel amount = new JLabel("결제금액 : ");
        JLabel LabelUserAmount = new JLabel(String.valueOf(userTotalFee));
        JLabel point = new JLabel("적립금사용: ");
        jtfPoint = new JTextField("0");
        JLabel nowPoint = new JLabel("보유 적립금: ");
        JLabel LabelUserPoint = new JLabel(Integer.toString(userPoint));

        JLabel total = new JLabel("총 결제금액: ");
        LabelUserTotalFee = new JLabel(Integer.toString(userTotalFeeAfterUsePoint));

        JLabel addPoint = new JLabel("예정 적립금: ");
        LabelUserAddPoint = new JLabel(Integer.toString(userAddPoint));

        JButton btnUsePoint = new JButton("사용");
        JButton btnUseAllPoint = new JButton("전액사용");
        btnUsePoint.addActionListener(this);
        btnUseAllPoint.addActionListener(this);
        jtfPoint.addActionListener(this);
        JButton btnPay = new JButton("결제");
        btnPay.addActionListener(this);
        //RsvPopUp rsvPipUp = new RsvPopUp("정상 출차");
        toHours = Calculate.calculateTime(currentDate, userCarOut);


        name.setBounds(50, 100, 100, 20);       LabelUserName.setBounds(120, 100, 100, 20);
        carNum.setBounds(50, 120, 100, 20);     LabelUserCarNum.setBounds(120, 120, 100, 20);
        inTime.setBounds(50, 140, 100, 20);     LabelUserInTime.setBounds(120, 140, 200, 20);
        outTime.setBounds(50, 160, 100, 20);    LabelUserOutTime.setBounds(120, 160, 200, 20);
        amount.setBounds(50, 180, 100, 20);     LabelUserAmount.setBounds(120, 180, 100, 20);
        point.setBounds(50, 200, 100, 20);      jtfPoint.setBounds(120, 200, 70, 20);
        btnUsePoint.setBounds(200, 200, 80, 20); btnUseAllPoint.setBounds(280, 200, 100, 20);
        nowPoint.setBounds(120, 220, 100, 20);  LabelUserPoint.setBounds(200, 220, 70, 20);
        total.setBounds(50, 260, 100, 20);      LabelUserTotalFee.setBounds(120, 260, 100, 20);
        addPoint.setBounds(50, 280, 100, 20);   LabelUserAddPoint.setBounds(120, 280, 100, 20);

        btnPay.setBounds(100, 300, 100, 20);

        ct.add(name);     ct.add(LabelUserName);
        ct.add(carNum);   ct.add(LabelUserCarNum);
        ct.add(inTime);   ct.add(LabelUserInTime);
        ct.add(outTime);  ct.add(LabelUserOutTime);
        ct.add(amount);   ct.add(LabelUserAmount);
        ct.add(point);    ct.add(jtfPoint);         ct.add(btnUsePoint); ct.add(btnUseAllPoint);
        ct.add(nowPoint); ct.add(LabelUserPoint);
        ct.add(total);    ct.add(LabelUserTotalFee);
        ct.add(addPoint); ct.add(LabelUserAddPoint);
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
            if (s=="전액사용") jtfPoint.setText(Integer.toString(userPoint));
            LocalDateTime currentDate = LocalDateTime.now();
            int usePoint = Integer.parseInt(jtfPoint.getText());

            userTotalFeeAfterUsePoint = userTotalFee - usePoint; //총 결제금액
            userAddPoint = (int) (userTotalFeeAfterUsePoint * 0.05);//예정 적립금
            userTotalPoint = userPoint - usePoint + userAddPoint; //총 결제적립금

            LabelUserTotalFee.setText(String.valueOf(userTotalFeeAfterUsePoint));
            LabelUserAddPoint.setText(String.valueOf(userAddPoint));
        }
        //DB 업데이트 - 결제금액, 적립금, hours
        DBconnection.InsertDB("total_fee", String.valueOf(userTotalFeeAfterUsePoint), userId); //결제금액 DB 추가
        DBconnection.InsertDB("hours", toHours, userId);
        DBconnection.updateDB("user", "point", userTotalPoint, "id", userId); //적립금 업데이트
        DBconnection.updateDB("purchase", "car_out", userCarOut, "user_id", userId); //출차시간 업데이트

        System.out.println("userTotalPoint : " + userTotalPoint);
        System.out.println("userTotalFeeAfterUsePoint : " + userTotalFeeAfterUsePoint);

    }
}

//예약 정상출차
class RsvPopUp extends JFrame implements ActionListener {// 예약 하고 출차. JDialog 클래스 객체 생성
    String userName, userCarNum;
    RsvPopUp(String uName, String uCarNum) {
        setTitle("정상 출차");
        Container ct = getContentPane();
        ct.setLayout(null);

        userName = uName;
        userCarNum = uCarNum;

        JLabel l1_name = new JLabel("이        름 : ");
        JLabel l2_carNum = new JLabel("차량번호 : ");
        JLabel l3_str = new JLabel("정상 출차 되었습니다");
        JLabel name = new JLabel(userName);
        JLabel carNum = new JLabel(userCarNum);

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

//예약 초과시간 출차
class ExcessFeePay extends JFrame implements ActionListener {// 예약했는데 시간초과. JDialog 클래스 객체 생성
    String userId, userName, userCarNum;
    LocalDateTime userCarIn, userCarOut;
    LocalTime userHours;
    //예약했는데 시간초과면 userCarOut, userHours 도 현재시간 기준으로 Update 해줘야 함 !!
    int userTotalFee, userPoint;

    LocalDateTime currentDate = LocalDateTime.now();

    long diffMin;     //시간 다른거
    String toHours;
    int userAddFee, userAddPoint; //예정 추가적립금
    ExcessFeePay(String uId, String uName, String uCarNum, LocalDateTime uCarIn, LocalDateTime uCarOut, LocalTime uHours, int uTotalFee, int uPoint) throws ParseException {
        userId = uId;
        userName = uName;
        userCarNum = uCarNum;
        userCarIn = uCarIn;
        userCarOut = uCarOut;
        userHours = uHours;
        userTotalFee = uTotalFee; //초과이용자는 이미 예약하기 했을 때 userTotalFee가 정해져있음.
        userPoint = uPoint;

        diffMin = ChronoUnit.MINUTES.between(currentDate, userCarOut);
        Calculate.calculateTime(currentDate, userCarOut); //초과시간(분단위)
        userAddFee = Calculate.calculateFee(diffMin);                  //초과금액 계산

        //JLabel 붙이기 용 LocalDate -> String 변환함
        LocalDateTime currentDate = LocalDateTime.now();
        String stringUserCarIn = userCarIn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String stringUserCarOut = userCarOut.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String stringCurrentDatet = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        int time = (int) (diffMin*60);
        int hour = time/(60*60);
        int minute = time/60-(hour*60);
        int second = time%60;
        toHours = hour+":"+minute+":"+second;

        userTotalFee += userAddFee;
        userPoint = (int) (userAddFee * 0.05);

        setTitle("초과 금액 결제");
        Container ct = getContentPane();
        ct.setLayout(null);

        JLabel name = new JLabel("이        름 : ");            JLabel labelUserName = new JLabel(userName);
        JLabel carNum = new JLabel("차량번호 : ");               JLabel labelUserCarNum = new JLabel(userCarNum);
        JLabel carOut = new JLabel("예정출차시간 : ");               JLabel labelUserCarOut = new JLabel(stringUserCarOut);
        JLabel currunet = new JLabel("현재시간 : ");               JLabel labelCurrunet = new JLabel(stringCurrentDatet);
        JLabel timeOut = new JLabel("초과시간 : ");              JLabel labelUserTimeOut = new JLabel(toHours);

        JLabel timeOutFee = new JLabel("총 초과 금액 : ");      JLabel labelUserTimeOutFee = new JLabel(Integer.toString(userAddFee));

        JButton b = new JButton("결제");
        b.addActionListener(this);

        name.setBounds(50,100,100,20);           labelUserName.setBounds(150,100,100,20);
        carNum.setBounds(50,120,100,20);         labelUserCarNum.setBounds(150,120,100,20);
        carOut.setBounds(50,140,100,20);         labelUserCarOut.setBounds(150,140,200,20);
        currunet.setBounds(50,160,100,20);         labelCurrunet.setBounds(150,160,200,20);

        timeOut.setBounds(50,180,100,20);        labelUserTimeOut.setBounds(150,180,300,20);
        timeOutFee.setBounds(50,220,300,20);     labelUserTimeOutFee.setBounds(150,220,150,20);
        b.setBounds(100,300,100,20);

        ct.add(name);            ct.add(labelUserName);
        ct.add(carNum);          ct.add(labelUserCarNum);
        ct.add(carOut);          ct.add(labelUserCarOut);
        ct.add(currunet);         ct.add(labelCurrunet);
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

        //DB 업데이트 - 결제금액, 적립금
        DBconnection.updateDB("purchase", "total_fee", userTotalFee, "user_id", userId);
        DBconnection.updateDB("user", "point", userPoint, "id", userId);
        DBconnection.InsertDB("hours", toHours, userId);
        DBconnection.updateDB("purchase", "car_out", currentDate, "user_id", userId);
    }
}


class Calculate {
    public static String calculateTime(LocalDateTime in, LocalDateTime out) {
        //long diffMin = ChronoUnit.MINUTES.between(in, out);
        //System.out.println("diffMin : " + diffMin);

        //시간차(분단위)를 HH:mm:ss로 변환하여 DB hours 업데이트
        int time = (int) (ChronoUnit.MINUTES.between(in, out)*60);

        int hour = time/(60*60);
        int minute = time/60-(hour*60);
        int second = time%60;
        String toHours = hour+":"+minute+":"+second;
        System.out.println(time +"초는 " + hour + "시간, " + minute + "분, " + second + "초입니다."+toHours);

        return toHours;
    }

    public static int calculateFee(long diffMin) {
        int TotalFee = Long.valueOf(diffMin).intValue() / 10 * 500;
        //TODO 경차, 장애인 시 10% 할인 -> 작성 완료
        if (DBconnection.searchDB())
            TotalFee *= 0.1;

        return TotalFee;
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
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", "root", "wldbs1004");
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            //purchase 값 가져오기
            strSql = "SELECT small_car, handicap FROM user_special_needs WHERE id='daeunlee';";
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
            System.out.println("SQLException1 : " + e.getMessage());
        }
        return discount;
    }
    /*
        //Hours insert
        public static void hoursInsertDB(String index) {
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

                strSql = " INSERT INTO purchase (hours) VALUES (" + "'" + index + "');";
                dbSt.execute(strSql); //DB로부터 읽어온 레코드 객체화

                dbSt.close();
                con.close(); //DB연동 끊기
            } catch (SQLException e) {
                System.out.println("SQLException2 : " + e.getMessage());
            }
        }

     */
    //일반 주차 결제금액, hours  추가 -> 오류 발생함
    public static void InsertDB(String column, String index, String condition) {
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", "root", "wldbs1004");
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;
            strSql = "INSERT INTO parking.purchase (" + column + ") VALUES ('" + index + "' WHERE user_id = '" + condition  + "';";
            dbSt.execute(strSql); //DB로부터 읽어온 레코드 객체화

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException2 : " + e.getMessage());
        }
    }

    //초과금액주차 DB - Hours update
    public static void updateDB(String table, String columns1, long index, String columns2, String condition) {
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", "root", "wldbs1004");
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            //purchase 값 가져오기
            strSql = " UPDATE " + table + " SET " + columns1 + "= '" + index + "' WHERE " + columns2 + "= '" + condition + "';";
            dbSt.executeUpdate(strSql); //DB로부터 읽어온 레코드 객체화

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException3 : " + e.getMessage());
        }
    }

    //초과금액주차 DB - car_out update
    public static void updateDB(String table, String columns1, LocalDateTime index, String columns2, String condition) {
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", "root", "wldbs1004");
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            //purchase 값 가져오기
            strSql = " UPDATE " + table + " SET " + columns1 + "= '" + index + "' WHERE " + columns2 + "= '" + condition + "';";
            dbSt.executeUpdate(strSql); //DB로부터 읽어온 레코드 객체화

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException3 : " + e.getMessage());
        }
    }

    //일반결제 결제금액, 포인트 update
    public static void updateDB(String table, String columns1, int index, String columns2, String condition) {
        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", "root", "wldbs1004");
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql;

            //purchase 값 가져오기
            strSql = " UPDATE " + table + " SET " + columns1 + "= '" + index + "' WHERE " + columns2 + "= '" + condition + "';";
            dbSt.executeUpdate(strSql); //DB로부터 읽어온 레코드 객체화

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException4 : " + e.getMessage());
        }
    }
}