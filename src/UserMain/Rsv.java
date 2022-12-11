package UserMain;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;


public class Rsv extends JFrame {

    String[] month, date, hour, min, choose_time; //시간선택
    JComboBox monthComBox, dateComBox, hourComBox, minComBox, timeComBox; //월일시분 시간선택
    JTextField inputPoint; //사용할 포인트 입력
    int myPoint, usePoint, paidMoney; //보유 포인트 값, 사용할 포인트 값, 결제금액
    JButton usePointBtn, rsv_go, rsv_cancel; //포인트 사용, 예약, 취소 버튼



    public Rsv(String t) {
        super(t);

        //----------------------------------------------------------------예약일시선택
        month = new String[12]; //월 크기 그냥 12로
        for(int i = 0; i<12; i++) { //1~12 저장
            month[i] = String.valueOf(i+1);
        }
        monthComBox = new JComboBox(month); //월 콤보박스 생성
        dateComBox = new JComboBox();
        monthComBox.addActionListener(new ActionListener() { //월 선택 시
            @Override
            public void actionPerformed(ActionEvent e) {
                int m = monthComBox.getSelectedIndex(); //선택한 월 int 로 받기
                Calendar cal = Calendar.getInstance();
                cal.set(2023, m);
                date = new String[cal.getActualMaximum(Calendar.DATE)]; //월의 마지막날로 크기지정
                for (int i=0; i<date.length; i++) {
                    date[i] = String.format("%02d",i+1);
                }
                dateComBox = new JComboBox(date); //날짜 콤보박스 생성
            }
        }); //월 선택 이벤트 끝

        dateComBox.addActionListener(new ActionListener() { //일 선택 시
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO #### 이거 필요 없을 듯? 예약 버튼 누르면 .getSelectedItem() 으로 값 가져오기
            }
        }); //일 선택 이벤트 끝

        hour = new String[12];
        for(int i = 0; i<12; i++) { //1~12 저장
            if (i<10) hour[i] = "0"+(i+1); //00으로 나오게 하기
            hour[i] = String.valueOf(i+1);
        }
        hourComBox = new JComboBox(hour); //시간 콤보박스 생성

        min = new String[6];
        for(int i = 0; i<6; i++) { //1~12 저장
            if (i == 0) min[i] = String.valueOf(00); //00으로 나오게 하기
            else min[i] = String.valueOf((i+1)*10);
        }
        minComBox = new JComboBox(min); //분 콤보박스 생성

        //---------------------------------------------------------------------------



        inputPoint = new JTextField(10);      //사용할 포인트 입력
        usePointBtn = new JButton("사용");      //사용 버튼
        //TODO DB : myPoint = DB 에서 가져와서 저장

        usePointBtn.addActionListener(new ActionListener() { //포인트 사용 버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = Integer.parseInt(inputPoint.getText());
                if (i <= myPoint) usePoint = i; //보유 포인트 내에서 사용하는지 확인 후 저장
            }
        });

        rsv_go = new JButton("예약하기");
        rsv_go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "예약 하시겠습니까?\n(확인 시 결제로 넘어감)", "주차예약", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) { //결제 yes 한 경우
                    //TODO !# 결제창으로 넘어가기
                    //TODO DB : (myPoint-usePoint)(잔여포인트)와 paidMoney(결제금액) 넘기기
                    JOptionPane.showMessageDialog(null, "결제가 완료되었습니다!");

                }
                else { //결제 no 한 경우
                    dispose();
                }
            }
        });
        rsv_cancel = new JButton("취소");

        /*---------------UserMain, Check 와 겹치는 내용--------------------*/
        JPanel panel = new JPanel();        //CENTER
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JPanel t_pnl = new JPanel();        //NORTH
        t_pnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        /*---------------CENTER, NORTH 에 위치하는 Panel 들----------------*/

        JPanel rsv_pnl = new JPanel();      //EAST
        rsv_pnl.setLayout(new FlowLayout((FlowLayout.LEFT)));
        rsv_pnl.setBackground(Color.YELLOW);


        JLabel title = new JLabel("주차예약");      //제목 - 문자
        JLabel name = new JLabel("이름: ");        //이름
        JLabel carNum = new JLabel("차량번호: ");   //차량번호
        JLabel location = new JLabel("주차구역: "); //주차선택
        JLabel choose_floor = new JLabel("층");    //층
        JLabel choose_section = new JLabel();          //구역
        JLabel rsv = new JLabel("예약일시: ");      //시간선택
        JLabel month = new JLabel("월");           //월
        JLabel date = new JLabel("일");            //일
        JLabel hour = new JLabel("시");            //시
        JLabel minute = new JLabel("분");          //분
        JLabel time = new JLabel("이용시간: ");     //이용시간 선택
        JLabel point = new JLabel("포인트 사용: "); //포인트
        JLabel showMyPoint = new JLabel("보유 포인트: " + myPoint);   //보유(사용가능) 포인트 출력
        //선결제금액 - 문자, 상수
        JLabel price = new JLabel("선결제금액 : " + "원");
        JLabel plus_point = new JLabel("( [결제금액 * 10%] point 적립 예정)");


        rsv_pnl.add(title); //주차예약
        rsv_pnl.add(name);   rsv_pnl.add(carNum); //이름 :, 차량번호 :
        rsv_pnl.add(location); //주차구역 :
        rsv_pnl.add(choose_floor);   rsv_pnl.add(choose_section); //층, 구역
        rsv_pnl.add(rsv); //예약일시 :
        rsv_pnl.add(monthComBox);   rsv_pnl.add(month); //월
        rsv_pnl.add(dateComBox);    rsv_pnl.add(date); //일
        rsv_pnl.add(hourComBox);    rsv_pnl.add(hour); //시
        rsv_pnl.add(minComBox);  rsv_pnl.add(minute); //분
        rsv_pnl.add(time);   rsv_pnl.add(timeComBox); //이용시간 :, 시간선택
        rsv_pnl.add(point);  rsv_pnl.add(inputPoint);   rsv_pnl.add(usePointBtn); //포인트 사용 : , JTextField, 버튼
        rsv_pnl.add(showMyPoint); //보유포인트
        rsv_pnl.add(price); //선결제금액
        rsv_pnl.add(plus_point); //point 적립예정
        rsv_pnl.add(rsv_go); rsv_pnl.add(rsv_cancel); //예약, 취소 버튼

/*
        t_pnl.add(floorCBox);
        TODO 2# ComboBox 추가하기
 */

        Container rsvCt = getContentPane();
        rsvCt.add(t_pnl, BorderLayout.NORTH);
        rsvCt.add(panel, BorderLayout.CENTER);
        rsvCt.add(rsv_pnl, BorderLayout.EAST);

    }//Rsv 생성자 끝

}//Rsv 클래스 끝

class RsvMain extends JFrame{
    public static void main(String[] args) {
        Rsv m = new Rsv("주차 프로그램 - 주차예약");
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(1000, 600);
        m.setVisible(true);
    }

}