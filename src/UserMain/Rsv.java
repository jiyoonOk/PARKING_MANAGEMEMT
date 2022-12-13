package UserMain;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Vector;

import common.ParkingLot;


public class Rsv extends JFrame {

    public static final int FIRST_OF_HALF_INFO = 840, DEFAULT_SIZE = 110;

    String[] month, time;           //월, 선택시간
    Vector<String> hour, min; //시, 분
    JComboBox monthComBox, dateComBox, hourComBox, minComBox, timeComBox; //월일시분 시간선택
    JTextField inputPoint_JTF; //사용할 포인트 입력
    public int myPoint_int, usePoint_int=0, paidMoney_int=0; //보유 포인트 값, 사용할 포인트 값, 결제금액
    JButton usePointBtn, useAllPointBtn, rsvBtn, cancelBtn; //포인트 사용, 전액사용, 예약, 취소 버튼




    public Rsv(String t) {
        super(t);

        LocalDate now = LocalDate.now();

        month = new String[2];
        month[0] = String.valueOf(now.getMonth().getValue());                      //이번달
        if(month[0].equals("12")) month[1] = "1";
        else month[1] = String.valueOf(now.plusMonths(1).getMonth());  //다음달

        monthComBox = new JComboBox(month);                         //월 콤보박스 생성
        dateComBox  = new JComboBox();                              //일 콤보박스 생성
        monthComBox.addActionListener(new ActionListener() { //월 선택 시
            @Override
            public void actionPerformed(ActionEvent e) {
                dateComBox.removeAllItems();
                if(monthComBox.getSelectedItem().equals(month[0])) {
                    int lastDate = now.lengthOfMonth(); //이번달 말일
                    int currDate = now.getDayOfMonth(); //현재 일
                    int amount   = lastDate - currDate +1; //선택 가능한 일수
                    for (int i=0; i<amount; i++) {
                        dateComBox.addItem(String.valueOf(currDate+i));
                    }
                }else {
                    int nextMonthDate = now.plusMonths(1).lengthOfMonth(); //다음달 말일
                    for (int i=0; i<nextMonthDate; i++) {
                        dateComBox.addItem(String.valueOf(i+1));
                    }
                }
            }
        }); //월선택 이벤트 끝

        hour = new Vector<>();
        for(int i = 0; i<24; i++) { //1~12 저장
            if (i<9) hour.add("0"+(i+1)); //00으로 나오게 하기
            else hour.add(String.valueOf(i+1));
        }
        hourComBox = new JComboBox(hour); //시간 콤보박스 생성

        inputPoint_JTF = new JTextField();
        min = new Vector<>();
        for(int i = 0; i<6; i++) { //1~12 저장
            if (i == 0) min.add("00"); //00으로 나오게 하기
            else min.add(String.valueOf((i+1)*10));
        }
        minComBox = new JComboBox(min); //분 콤보박스 생성

        time = new String[]{"1시간", "2시간", "3시간"};
        timeComBox = new JComboBox(time);
        JLabel priceBefore = new JLabel("선결제금액 : 0원");
        timeComBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCost();
                priceBefore.setText("선결제금액: "+paidMoney_int+"원");
            }
        });//사용시간선택 이벤트 끝



        myPoint_int=10000;
        //TODO DB : 포인트 가져와서 초기화하기
        inputPoint_JTF = new JTextField(8);      //사용할 포인트 입력
        usePointBtn = new JButton("사용");            //사용 버튼
        useAllPointBtn = new JButton("ALL");
        JLabel showMyPoint = new JLabel("보유 포인트: " + myPoint_int);   //보유(사용가능) 포인트 출력 -(4,1)
        showMyPoint.setForeground(Color.RED);
        usePointBtn.addActionListener(new ActionListener() { //포인트 사용 버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = 0;
                if (inputPoint_JTF.getText() != null) i = Integer.parseInt(inputPoint_JTF.getText());
                getCost();
                if (i <= myPoint_int){ //보유 포인트 내에서 사용하는지 확인
                    if (i > paidMoney_int) {
                        JOptionPane.showMessageDialog(null, "결제 금액을 초과하여 쓸 수 없습니다.");
                    } else {
                        usePoint_int = i; // 확인 후 저장
                        showMyPoint.setText("보유 포인트: " + myPoint_int);
                        paidMoney_int -= usePoint_int; //결제금액에서 포인트금액만큼 빼기
                        priceBefore.setText("선결제금액: " + paidMoney_int + "원");
                    }
                } else { //보유 포인트 범위 벗어남
                    JOptionPane.showMessageDialog(null, "보유 포인트를 확인해주세요");
                }
            }
        });//포인트사용버튼 이벤트 끝

        useAllPointBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        rsvBtn = new JButton("예약하기");
        rsvBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "예약 하시겠습니까?\n(확인 시 결제로 넘어감)", "주차예약", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) { //결제 yes 한 경우
                    //TODO !# 결제창으로 넘어가기
                    //TODO DB : 결제정보 넘기기
                    // (선택한 연월일시), usePoint(사용포인트), timeComBox.getSelectedItem()(선택시간)
                    JOptionPane.showMessageDialog(null, "결제가 완료되었습니다!");
                    dispose();
                }
                else { //결제 no 한 경우

                }
            }
        });//예약버튼 이벤트 끝

        cancelBtn = new JButton("취소");
        cancelBtn.addActionListener(new ActionListener() { //취소 버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) { //창 닫기
                JOptionPane.showMessageDialog(null, "취소 되었습니다!");
                dispose();
            }
        });//취소버튼 이벤트 끝



        Container rsvCt = getContentPane();
        rsvCt.setLayout(null);

        //TODO DB : 이름, 차량번호 가져오기
        JLabel title            = new JLabel("주차예약");
        JLabel name_JLabel      = new JLabel("이    름: ");         //이름:  -(1,1)
        JLabel name             = new JLabel("이다은");             //이름 -(1,2)
        JLabel carNum_JLabel    = new JLabel("차량번호: ");         //차량번호 -(2,1)
        JLabel carNum           = new JLabel("12오4453");          //차량번호 -(2,2)
        JLabel location_JLabel  = new JLabel("주차구역: ");         //주차선택 -(3,1)
        JLabel choose_areaFloor = ParkingLot.userFloor;                //주차위치(층)
        JLabel choose_areaNum   = ParkingLot.userNum;                  //주차위치(구역)
        JLabel rsv              = new JLabel("예약일시: ");         //시간선택 -(4,1)
        JLabel month            = new JLabel("월");                //월 -(1,2)
        JLabel date             = new JLabel("일");                //일 -(1,4)
        JLabel hour             = new JLabel("시");                //시 -(2,2)
        JLabel minute           = new JLabel("분");                //분 -(2,4)
        JLabel time             = new JLabel("이용시간: ");         //이용시간 선택 -(1,1)
        JLabel point            = new JLabel("포인트 사용: ");      //포인트 -(2,1)
        JLabel plus_point       = new JLabel("( [결제금액 * 3%] point 적립 예정)"); //-(2)



        title.setBounds(800, 50, 75, 30);

        name_JLabel.setBounds(UserMain.FIRST_OF_INFO, 100, DEFAULT_SIZE, 25); name.setBounds(FIRST_OF_HALF_INFO, 100, DEFAULT_SIZE, 25);
        carNum_JLabel.setBounds(UserMain.FIRST_OF_INFO, 130, DEFAULT_SIZE, 25); carNum.setBounds(FIRST_OF_HALF_INFO, 130, DEFAULT_SIZE, 25);
        location_JLabel.setBounds(UserMain.FIRST_OF_INFO, 160, DEFAULT_SIZE, 25);
        choose_areaFloor.setBounds(FIRST_OF_HALF_INFO, 160, 25, 25); choose_areaNum.setBounds(870, 160, 25, 25);

        rsv.setBounds(UserMain.FIRST_OF_INFO, 190, DEFAULT_SIZE, 25);
        monthComBox.setBounds(UserMain.FIRST_OF_INFO, 220, 80, 25); month.setBounds(810, 220, 25, 25);
        dateComBox.setBounds(FIRST_OF_HALF_INFO, 220, 80, 25); date.setBounds(925, 220, 25, 25);
        hourComBox.setBounds(UserMain.FIRST_OF_INFO, 250, 80, 25); hour.setBounds(810, 250, 25, 25);
        minComBox.setBounds(FIRST_OF_HALF_INFO, 250, 80, 25); minute.setBounds(925, 250, 25, 25);

        time.setBounds(UserMain.FIRST_OF_INFO, 280, DEFAULT_SIZE, 25); timeComBox.setBounds(FIRST_OF_HALF_INFO, 280, DEFAULT_SIZE, 25);

        point.setBounds(UserMain.FIRST_OF_INFO, 310, DEFAULT_SIZE, 25);
        inputPoint_JTF.setBounds(UserMain.FIRST_OF_INFO, 340, 95, 25);
        usePointBtn.setBounds(825, 340, 60, 25); useAllPointBtn.setBounds(890, 340, 60, 25);
        showMyPoint.setBounds(UserMain.FIRST_OF_INFO, 370, 110, 25);

        priceBefore.setBounds(UserMain.FIRST_OF_INFO, 400, 200, 25);

        plus_point.setBounds(UserMain.FIRST_OF_INFO, 430, 200, 25);

        rsvBtn.setBounds(UserMain.FIRST_OF_INFO, 470, DEFAULT_SIZE, 50); cancelBtn.setBounds(FIRST_OF_HALF_INFO, 470, DEFAULT_SIZE, 50);


        rsvCt.add(ParkingLot.floor);
        rsvCt.add(ParkingLot.car);
        rsvCt.add(title);

        rsvCt.add(name_JLabel);   rsvCt.add(name);
        rsvCt.add(carNum_JLabel);  rsvCt.add(carNum);
        rsvCt.add(location_JLabel); rsvCt.add(choose_areaFloor); rsvCt.add(choose_areaNum);

        rsvCt.add(rsv);
        rsvCt.add(monthComBox);    rsvCt.add(month);      rsvCt.add(dateComBox);     rsvCt.add(date);
        rsvCt.add(hourComBox);     rsvCt.add(hour);       rsvCt.add(minComBox);      rsvCt.add(minute);

        rsvCt.add(time);   rsvCt.add(timeComBox);

        rsvCt.add(point);
        rsvCt.add(inputPoint_JTF); rsvCt.add(usePointBtn); rsvCt.add(useAllPointBtn);
        rsvCt.add(showMyPoint);

        rsvCt.add(priceBefore);  rsvCt.add(plus_point);

        rsvCt.add(rsvBtn); rsvCt.add(cancelBtn); //예약, 취소 버튼


    }//Rsv 생성자 끝

    public void getCost() { //선택시간에 따른 금액 저장
        if (timeComBox.getSelectedItem().equals("1시간")) {
            paidMoney_int = 2700;
        } else if (timeComBox.getSelectedItem().equals("2시간")) {
            paidMoney_int = 5400;
        } else if (timeComBox.getSelectedItem().equals("3시간")) {
            paidMoney_int = 8100;
        }
    }//getCost() 메소드 끝

}//Rsv 클래스 끝

class RsvMain extends JFrame{
    public static void main(String[] args) {
        Rsv m = new Rsv("주차 프로그램 - 주차예약");
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(1000, 600);
        m.setVisible(true);
    }

}