package Parking;


import UserMain.ParkingLot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.*;

// TODO 221217 13:45 (다은) 생성자 매개변수 아이디로 변경
// TODO 221217 13:55 (다은) 전역변수 수정
// TODO 221217 19:22 (다은) 상수 변경
public class Reservation extends JFrame {
    String userId; //로그인한 사용자 정보
    int userPoint;
    String userName, userCarNu;
    int plusPoint;
    public static final int FIRST_OF_FRAME = 50, FIRST_OF_INFO = 725; //x 축
    public static final int TOP_OF_PARK = 100; //y 축
    public static final int FIRST_OF_HALF_INFO = 840, DEFAULT_SIZE = 110;

    String[] month, time; //월, 선택시간
    JComboBox monthComBox, dateComBox, hourComBox, minComBox, timeComBox; //월일시분 시간선택
    JTextField inputPoint_JTF; //사용할 포인트 입력
    public static int usePoint_int=0, paidMoney_int=0; //보유 포인트 값, 사용할 포인트 값, 결제금액
    JButton usePointBtn, useAllPointBtn, rsvBtn, cancelBtn; //포인트 사용, 전액사용, 예약, 취소 버튼
    public JLabel pointMention = new JLabel(); //포인트 적립 안내

    public Reservation(String id) {
        String classname = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
        String user = "root";
        String passwd = "wldbs1004";
        userId = id;

        //===============================================================================================

        LocalDate now = LocalDate.now();
        LocalTime now2 = LocalTime.now();

        month = new String[2];
        month[0] = String.valueOf(now.getMonth().getValue());                      //이번달
        if (month[0].equals("12")) month[1] = "1";
        else month[1] = String.valueOf(now.plusMonths(1).getMonth());  //다음달

        monthComBox = new JComboBox(month); //월 콤보박스 생성
        dateComBox  = new JComboBox();      //일 콤보박스 생성
        hourComBox  = new JComboBox();      //시 콤보박스 생성
        minComBox   = new JComboBox();      //분 콤보박스 생성
        int lastDate = now.lengthOfMonth(); //이번달 말일
        int currDate = now.getDayOfMonth(); //현재 일
        int currHour = now2.getHour();      //현재 시간
        int currMinute = now2.getMinute();  //현재 분
        monthComBox.addActionListener(new ActionListener() { //월 선택 시
            @Override
            public void actionPerformed(ActionEvent e) {
                dateComBox.removeAllItems();
                if (monthComBox.getSelectedItem().equals(month[0])) {
                    int amount = lastDate - currDate + 1; //선택 가능한 일수
                    for (int i = 0; i < amount; i++) {
                        dateComBox.addItem(String.valueOf(currDate + i));
                    }
                } else {
                    int nextMonthDate = now.plusMonths(1).lengthOfMonth(); //다음달 말일
                    for (int i = 0; i < nextMonthDate; i++) {
                        dateComBox.addItem(String.valueOf(i + 1));
                    }
                }}}); //월선택 이벤트 끝

        dateComBox.addActionListener(new ActionListener() { //일 선택 시
            @Override
            public void actionPerformed(ActionEvent e) {
                hourComBox.removeAllItems();
                if (monthComBox.getSelectedItem().equals(month[0]) && dateComBox.getSelectedItem().equals(currDate+1)) {
                    for (int i = currHour; i < 24; i++) { //남은시간만 표시
                        if (i < 9) hourComBox.addItem("0" + (i + 1)); //두자릿수로 나오게 하기
                        else hourComBox.addItem(String.valueOf(i + 1));
                    }
                } else {
                    for (int i = 0; i < 24; i++) { //01~24시
                        if (i < 9) hourComBox.addItem("0" + (i + 1)); //두자릿수로 나오게 하기
                        else hourComBox.addItem(String.valueOf(i + 1));
                    }
                }}}); //일선택 이벤트 끝

        hourComBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minComBox.removeAllItems();
                if (dateComBox.getSelectedItem().equals(currDate+1) && hourComBox.getSelectedItem().equals(currHour+1)) {
                    for (int i = currMinute/10; i < 6; i++) { //남은 분만 표시
                        minComBox.addItem(String.valueOf(i *10));
                    }
                } else {
                    for (int i = 0; i < 6; i++) {
                        if (i == 0) minComBox.addItem("00"); //두자릿수로 나오게 하기
                        else minComBox.addItem(String.valueOf(i *10));
                    }
                }}}); //시선택 이벤트 끝


        time = new String[]{"1시간", "2시간", "3시간"};
        timeComBox = new JComboBox(time);
        JLabel priceBefore = new JLabel("이용금액 : 0원");
        JLabel prieceAfter = new JLabel("최종금액 : 0원");
        timeComBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCost();
                priceBefore.setText("이용금액: " + paidMoney_int + "원");
            }});//사용시간선택 이벤트 끝

        //===============================================================================================


        try {
            Class.forName(classname);  //mysql jdbc Driver 연결
            System.err.println("JDBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try {
            Connection con = DriverManager.getConnection(url, user, passwd);
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement();
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
            String strSql;
            //user 테이블에서 로그인한 id에 맞는 userPoint 가져오기
            strSql = "SELECT user.name, user.car_num, user.point FROM parking.user WHERE user.id='"+userId+"';";
            ResultSet r = dbSt.executeQuery(strSql);
            while(r.next()){
                userName = r.getString("user.name");
                userCarNu = r.getString("user.car_num");
                userPoint = r.getInt("user.point");
            };
            System.out.println("포인트,이름,차량번호 추출 완료");

            dbSt.close();
            con.close();    //DB 연동 끊기
        } catch (NumberFormatException ex) {
            System.err.println("NumberFormatException : " + ex.getMessage());
        } catch (HeadlessException ex) {
            System.err.println("HeadlessException : " + ex.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLException : " + e.getMessage());
        }


        inputPoint_JTF     = new JTextField(8);          //사용할 포인트 입력
        inputPoint_JTF.setHorizontalAlignment(JTextField.RIGHT); //우측정렬
        usePointBtn        = new JButton("사용");            //사용 버튼
        useAllPointBtn     = new JButton("ALL");            //전액사용 버튼
        JLabel showMyPoint = new JLabel("보유 포인트: " + userPoint);   //보유(사용가능) 포인트 출력 -(4,1)
        showMyPoint.setForeground(Color.RED);
        usePointBtn.addActionListener(new ActionListener() { //포인트 사용버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent ae) {
                    int i = 0;
                    if (!(inputPoint_JTF.getText().equals(""))) //빈칸이 아닐 때만 입력받음
                        i = Integer.parseInt(inputPoint_JTF.getText());
                    getCost();
                    if (i <= userPoint) { //보유 포인트 내에서 사용하는지 확인
                        if (i > paidMoney_int) { //사용포인트 > 결제금액
                            JOptionPane.showMessageDialog(null, "결제 금액 이상으로 사용할 수 없습니다.");
                        } else { //사용포인트 < 결제금액
                            usePoint_int = i; // 확인 후 저장
                            paidMoney_int -= usePoint_int; //결제금액에서 포인트금액만큼 빼기
                            showMyPoint.setText("보유 포인트: " + (userPoint - usePoint_int));
                            prieceAfter.setText("최종금액: " + paidMoney_int + "원");
                        }
                    } else { //보유 포인트 범위 벗어남
                        JOptionPane.showMessageDialog(null, "보유 포인트를 확인해주세요");
                    }
            }
        });//포인트 사용버튼 이벤트 끝

        useAllPointBtn.addActionListener(new ActionListener() { //포인트 전액사용 버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userPoint > paidMoney_int) { //보유포인트 > 결제금액
                    showMyPoint.setText("보유 포인트: " + (userPoint - paidMoney_int - usePoint_int));
                    prieceAfter.setText("최종금액: 0원");
                } else {                         //보유포인트 < 결제금액
                    showMyPoint.setText("보유 포인트: 0");
                    prieceAfter.setText("최종금액: " + (paidMoney_int - userPoint) + "원");
                }
                inputPoint_JTF.setText("");
            }
        });//포인트 전액사용버튼 이벤트 끝

        //===============================================================================================


        rsvBtn = new JButton("예약하기");
        rsvBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //예약결제
                int result = JOptionPane.showConfirmDialog(null, "예약 하시겠습니까?\n(확인 시 결제로 넘어감)", "주차예약", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) { //결제 yes 한 경우
                    //TODO !# 결제창으로 넘어가기
                    //TODO !# : 결제정보 넘기기
                    // (선택한 연월일시) -> purchase.car_in 으로 바꿔서
                    // userPoint(보유포인트) -> user.userPoint
                    // timeComBox.getSelectedItem()(선택시간) -> purchase.car_out = purchase.car_in + 3시간
                    dispose();
                }
            }
        });//예약버튼 이벤트 끝

        cancelBtn = new JButton("취소");
        cancelBtn.addActionListener(new ActionListener() { //취소 버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) { //창 닫기
                userPoint += usePoint_int; //TODO !# 이거 필요한가?
                JOptionPane.showMessageDialog(null, "취소 되었습니다!");
                dispose();
            }});//취소버튼 이벤트 끝

        //===============================================================================================


        Container rsvCt = getContentPane();
        rsvCt.setLayout(null);


        JLabel specialInfo      = new JLabel("특별구역 10% 할인");   //안내문구
        //TODO DB : 이름, 차량번호 가져오기
        JLabel title            = new JLabel("주차예약");
        JLabel name_JLabel      = new JLabel("이    름: ");        //이름:
        JLabel name             = new JLabel(userName);
        JLabel carNum_JLabel    = new JLabel("차량번호: ");         //차량번호
        JLabel carNum           = new JLabel(userCarNu);
        JLabel location_JLabel  = new JLabel("주차구역: ");         //주차선택
        ParkingLot park         = new ParkingLot(userId);
        JLabel userFloorNum     = new JLabel(park.userFloor);          //주차위치(층)
        JLabel userArea         = new JLabel(park.userArea);           //주차위치(구역)
        JLabel rsv              = new JLabel("예약일시: ");         //시간선택
        JLabel month            = new JLabel("월");                //월
        JLabel date             = new JLabel("일");                //일
        JLabel hour             = new JLabel("시");                //시
        JLabel minute           = new JLabel("분");                //분
        JLabel time             = new JLabel("이용시간: ");         //이용시간 선택
        JLabel point            = new JLabel("포인트 사용: ");      //포인트


        specialInfo.setBounds(590, 60, 130, 30);

        park.floor.setBounds(FIRST_OF_FRAME, TOP_OF_PARK-50, 100, 30);
        park.car.setBounds(FIRST_OF_FRAME, TOP_OF_PARK, 650, 450);

        title.setBounds(800, 50, 75, 30);

        name_JLabel.setBounds(FIRST_OF_INFO, 100, DEFAULT_SIZE, 25);
        name.setBounds(FIRST_OF_HALF_INFO, 100, DEFAULT_SIZE, 25);
        carNum_JLabel.setBounds(FIRST_OF_INFO, 130, DEFAULT_SIZE, 25);
        carNum.setBounds(FIRST_OF_HALF_INFO, 130, DEFAULT_SIZE, 25);
        location_JLabel.setBounds(FIRST_OF_INFO, 160, DEFAULT_SIZE, 25);
        userFloorNum.setBounds(FIRST_OF_HALF_INFO, 160, 25, 25);
        userArea.setBounds(870, 160, 25, 25);

        rsv.setBounds(FIRST_OF_INFO, 190, DEFAULT_SIZE, 25);
        monthComBox.setBounds(FIRST_OF_INFO, 220, 80, 25);
        month.setBounds(810, 220, 25, 25);
        dateComBox.setBounds(FIRST_OF_HALF_INFO, 220, 80, 25);
        date.setBounds(925, 220, 25, 25);
        hourComBox.setBounds(FIRST_OF_INFO, 250, 80, 25);
        hour.setBounds(810, 250, 25, 25);
        minComBox.setBounds(FIRST_OF_HALF_INFO, 250, 80, 25);
        minute.setBounds(925, 250, 25, 25);

        time.setBounds(FIRST_OF_INFO, 280, DEFAULT_SIZE, 25);
        timeComBox.setBounds(FIRST_OF_HALF_INFO, 280, DEFAULT_SIZE, 25);

        point.setBounds(FIRST_OF_INFO, 310, DEFAULT_SIZE, 25);
        inputPoint_JTF.setBounds(FIRST_OF_INFO, 340, 95, 25);
        usePointBtn.setBounds(825, 340, 60, 25);
        useAllPointBtn.setBounds(890, 340, 60, 25);
        showMyPoint.setBounds(FIRST_OF_INFO, 370, 110, 25);

        priceBefore.setBounds(FIRST_OF_INFO, 400, 200, 25);
        prieceAfter.setBounds(FIRST_OF_INFO, 425, 200, 25);
        pointMention.setBounds(FIRST_OF_INFO, 450, 200, 25);

        rsvBtn.setBounds(FIRST_OF_INFO, 480, DEFAULT_SIZE, 50);
        cancelBtn.setBounds(FIRST_OF_HALF_INFO, 480, DEFAULT_SIZE, 50);

        rsvCt.add(specialInfo);
        rsvCt.add(park.floor);
        rsvCt.add(park.car);
        rsvCt.add(title);

        rsvCt.add(name_JLabel);
        rsvCt.add(name);
        rsvCt.add(carNum_JLabel);
        rsvCt.add(carNum);
        rsvCt.add(location_JLabel);
        rsvCt.add(userFloorNum);
        rsvCt.add(userArea);

        rsvCt.add(rsv);
        rsvCt.add(monthComBox);
        rsvCt.add(month);
        rsvCt.add(dateComBox);
        rsvCt.add(date);
        rsvCt.add(hourComBox);
        rsvCt.add(hour);
        rsvCt.add(minComBox);
        rsvCt.add(minute);

        rsvCt.add(time);
        rsvCt.add(timeComBox);

        rsvCt.add(point);
        rsvCt.add(inputPoint_JTF);
        rsvCt.add(usePointBtn);
        rsvCt.add(useAllPointBtn);
        rsvCt.add(showMyPoint);

        rsvCt.add(priceBefore);
        rsvCt.add(prieceAfter);
        rsvCt.add(pointMention);

        rsvCt.add(rsvBtn);
        rsvCt.add(cancelBtn); //예약, 취소 버튼


    }//Reservation 생성자 끝

    public void getCost() { //선택시간에 따른 금액 저장 / 10% 할인 적용
        if (timeComBox.getSelectedItem().equals("1시간")) {
            paidMoney_int = 2700;
        } else if (timeComBox.getSelectedItem().equals("2시간")) {
            paidMoney_int = 5400;
        } else if (timeComBox.getSelectedItem().equals("3시간")) {
            paidMoney_int = 8100;
        }
        plusPoint = (int)((paidMoney_int - usePoint_int) * 0.05);
        pointMention.setText("( "+plusPoint+" point 적립 예정)");
    }//getCost() 메소드 끝

}//Reservation 클래스 끝