package UserMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Park extends JFrame {
    /*---------------------------------------------------------------------*/
    String[] floor_list = {"B1", "B2", "B3"};           //층 목록
    JComboBox floorCBox = new JComboBox(floor_list);    //층 선택
    /*---------------------------------------------------------------------*/

    Container ct;

    JButton parking_goButton = new JButton("완료");       //주차완료버튼
    JButton parking_cancelButton = new JButton("취소");   //취소버튼

    public Park(String t) {
        super(t);
        /*------------------UserMain, Rsv 와 겹치는 내용-------------------*/
        JPanel panel = new JPanel();       //CENTER
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JPanel t_pnl = new JPanel();       //NORTH
        t_pnl.setLayout(new FlowLayout((FlowLayout.LEFT)));
        /*---------------CENTER, NORTH 에 위치하는 Panel 들----------------*/

        JPanel park_pnl = new JPanel();   //EAST
        park_pnl.setLayout(new FlowLayout());
        park_pnl.setBackground(Color.YELLOW);

        JLabel title = new JLabel("주차일반");            //제목
        JLabel name = new JLabel("이름: ");              //이름
        JLabel carNum = new JLabel("차량번호: ");         //차량번호
        JLabel currTime = new JLabel("현재시간 : ");      //현재시간
        // TODO : 현재시간 월, 일, 시, 분 까지만 표시하기
        JLabel location = new JLabel("주차구역: ");       //주차구역
        JLabel park_floor = new JLabel("층");            //층
        JLabel park_section = new JLabel(" ");          //구역

        parking_goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        /*TODO : 예약과 비교했을 떄,
            db에 입력하는 유형이 다르면 익명리스너로 생성.
           .                  같으면 공통된 액션리스너 클래스 생성.
         */
        parking_cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        park_pnl.add(title); //일반주차
        park_pnl.add(name);     park_pnl.add(carNum);   //이름, 차량번호
        park_pnl.add(currTime); //현재시간
        park_pnl.add(location); //주차구역
        park_pnl.add(park_floor);   park_pnl.add(park_section); //층, 구역
        park_pnl.add(parking_goButton);   park_pnl.add(parking_cancelButton); //완료, 취소 버튼

        t_pnl.add(floorCBox);

        ct = getContentPane();
        ct.add(t_pnl, BorderLayout.NORTH);
        ct.add(panel, BorderLayout.CENTER);
        ct.add(park_pnl, BorderLayout.EAST);


    }//Park 생성자 끝

}//Park 클래스 끝

class ParkMain extends JFrame{
    public static void main(String[] args) {
        Park m = new Park("주차 프로그램 - 일반주차");
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(1000, 600);
        m.setVisible(true);
    }

}