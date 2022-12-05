package UserMain;


import javax.swing.*;
import java.awt.*;

public class Rsv extends JFrame {
    /*-----------------------UserMain 과 겹치는 내용-------------------------*/
    String[] floor_list = {"B1", "B2"}; //층 목록(B1 기본), 상수, 데베
    JComboBox floorCBox = new JComboBox(floor_list); //층 선택
    /*---------------------------------------------------------------------*/

    public static JPanel rsv_pnl;

    JLabel title = new JLabel("주차예약"); //제목 - 문자
    JLabel name = new JLabel("이름: "/*{회원 DB}에서 불러오기*/); //이름
    JLabel carNum = new JLabel("차량번호: "/*{회원 DB}에서 불러오기*/); //차량번호
    JLabel location = new JLabel("주차구역: ");
    //주차선택
    JLabel choose_floor = new JLabel(/*화면에서 선택 시 불러오기*/"층"); //층
    JLabel choose_section = new JLabel(/*화면에서 선택 시 불러오기*/); //구역
    JLabel rsv = new JLabel("예약일시: ");
    JLabel month = new JLabel("월");
    JLabel date = new JLabel("일");
    JLabel hour = new JLabel("시");
    JLabel minute = new JLabel("분");
    JLabel time = new JLabel("이용시간: ");
    JLabel point = new JLabel("포인트 사용: ");
    //선결제금액 - 문자, 상수
    JLabel price = new JLabel("선결제금액 : " + "원");
    JLabel plus_point = new JLabel("(" + " point 적립 예정)");


    //예약일시
    String[] m = {"1","2","3"}; //월 - 상수
    JComboBox rsvMonth = new JComboBox(m);
    String[] d = {"1","2","3"}; //일 - 상수
    // TODO : 일 수를 월별로 어떻게 다르게 보이지?
    JComboBox rsvDate = new JComboBox(d);
    String[] h = {"1","2","3"}; //시 - 상수
    JComboBox rsvHour = new JComboBox(h);
    String[] min = {"1","2","3"}; //분 - 상수(10분단위)
    JComboBox rsvMinute = new JComboBox(min);


    //이용시간 선택 - 문자
    String[] choose_time = {"1시간", "2시간", "3시간"};
    JComboBox timeComBox = new JComboBox(choose_time);

    //포인트 사용
    JTextField usePoint = new JTextField();
    JButton usePntBtn = new JButton("사용");

    //예약 결정 버튼 - 문자
    JButton rsv_go = new JButton("예약하기");
    JButton rsv_cancel = new JButton("취소");
    //TODO : 만약 다른 취소 버튼들도 같은 역할을 한다면 변수명 cancel 로 바꾸기

    public Rsv() {

        rsv_pnl = new JPanel(); //우측 서비스(예약,주차,조회,정산)
        rsv_pnl.setLayout(null);
        rsv_pnl.setBackground(Color.YELLOW);

        /*---------------------UserMain 과 겹치는 내용-----------------------*/
        JPanel park_pnl = new JPanel();
        park_pnl.setLayout(new BorderLayout());
        park_pnl.setBackground(Color.LIGHT_GRAY);

        JPanel t_pnl = new JPanel();//위
        t_pnl.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel b_pnl = new JPanel();//아래
        b_pnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        /*-----------------------------------------------------------------*/


        title.setBounds(100, 20, 80, 30); //주차예약

        name.setBounds(50, 60, 80, 30); //이름
        carNum.setBounds(50, 80, 80, 30); //차량번호

        location.setBounds(50, 110, 80, 30); //주차구역
        choose_floor.setBounds(140, 110, 50, 30); //층
        choose_section.setBounds(200, 110, 50, 30);

        rsv.setBounds(50, 140, 80, 30); //예약일시

        rsvMonth.setBounds(50, 180, 50, 30); //월선택
        month.setBounds(110, 180, 30, 30); //월
        rsvDate.setBounds(150, 180, 50, 30); //일선택
        date.setBounds(210, 180, 30, 30); //일

        rsvHour.setBounds(50, 220, 50, 30); //시선택
        hour.setBounds(110, 220, 30, 30); //시
        rsvMinute.setBounds(150, 220, 50, 30); //분선택
        minute.setBounds(210, 220, 30, 30); //분

        time.setBounds(50, 270, 80, 30); //이용시간
        timeComBox.setBounds(120, 270, 80, 30); //이용시간선택

        point.setBounds(50, 310, 80, 30); //포인트사용
        usePoint.setBounds(50, 340, 100, 30); //포인트입력
        usePntBtn.setBounds(160, 340, 60, 30); //포인트사용버튼

        price.setBounds(50, 390, 80, 30); //선결제금액
        plus_point.setBounds(50, 410, 100, 20); //적립예정포인트
        plus_point.setForeground(Color.RED);

        rsv_go.setBounds(45, 460, 90, 40); //예약하기버튼
        RsvClickActionListener rsvc = new RsvClickActionListener();
        rsv_go.addActionListener(rsvc); //TODO : 해야함
        rsv_cancel.setBounds(150, 460, 60, 40); //취소버튼
        CancleClickActionListener cc = new CancleClickActionListener();
        rsv_cancel.addActionListener(cc); //TODO : 해야함

        rsv_pnl.add(title); //주차예약
        rsv_pnl.add(name);   rsv_pnl.add(carNum); //이름, 차량번호
        rsv_pnl.add(location); //주차구역
        rsv_pnl.add(choose_floor);   rsv_pnl.add(choose_section); //층, 구역
        rsv_pnl.add(rsv); //예약일시
        rsv_pnl.add(rsvMonth);   rsv_pnl.add(month); //월
        rsv_pnl.add(rsvDate);    rsv_pnl.add(date); //일
        rsv_pnl.add(rsvHour);    rsv_pnl.add(hour); //시
        rsv_pnl.add(rsvMinute);  rsv_pnl.add(minute); //분
        rsv_pnl.add(time);   rsv_pnl.add(timeComBox); //이용시간, 시간선택
        rsv_pnl.add(point);  rsv_pnl.add(usePoint);   rsv_pnl.add(usePntBtn); //포인트 사용, JTextField, 버튼
        rsv_pnl.add(price); //선결제금액
        rsv_pnl.add(plus_point); //point 적립예정
        rsv_pnl.add(rsv_go); rsv_pnl.add(rsv_cancel); //예약, 취소 버튼

        t_pnl.add(floorCBox);
        park_pnl.add(t_pnl, BorderLayout.NORTH);
        park_pnl.add(b_pnl, BorderLayout.SOUTH);

        UserMain.ct.add(park_pnl, BorderLayout.CENTER);
        UserMain.ct.add(rsv_pnl, BorderLayout.EAST);

    }//Rsv 생성자 끝

}//Rsv 클래스 끝
