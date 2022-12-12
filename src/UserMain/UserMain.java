
package UserMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import common.MakeParkingLot;
import view.CostFrame;

public class UserMain extends JFrame{
    public static final int TOP_OF_FRAME = 30, TOP_OF_PARK = 100; //전체 기준
    public static final int FIRST_OF_FRAME = 50, FIRST_OF_INFO = 725; //주차장 기준
    public static final int WIDTH_OF_MAIN_BUTTON = 225, HEIGHT_OF_MAIN_BUTTON = 100; //메인 버튼
    JButton fareTagButton, menuButton, rsvButton, parkButton, checkButton, payButton;

    public UserMain() {
        //---------------------------------------------------------------------------패널생성
        Container mainCt = getContentPane();
        mainCt.setLayout(null);


        //---------------------------------------------------------------------------


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


        menuButton = new JButton("메뉴");
        menuButton.addActionListener(new ActionListener() {       //메뉴버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) {          //메뉴창 팝업
                // TODO #### 메뉴창 가져오는거 왜 안돼지?
                /*
                Menu menu = new Menu("메뉴");
                menu.setSize(400, 600);
                menu.setLocationRelativeTo(null);
                menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                menu.setVisible(true);
                 */

            }
        });

        rsvButton = new JButton("예약");
        rsvButton.addActionListener(new ActionListener() {        //예약버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) {          //예약화면 팝업
                Rsv m = new Rsv("주차 프로그램 - 주차예약");
                m.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                m.setLocationRelativeTo(null);
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
                p.setLocationRelativeTo(null);
                p.setSize(1000, 600);
                p.setVisible(true);
            }
        });

        checkButton = new JButton("조회");
        checkButton.addActionListener(new ActionListener() {      //조회 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                //조회 페이지 불러오기
                JOptionPane.showMessageDialog(null, "조회하기");
            }
        });

        payButton = new JButton("정산");
        payButton.addActionListener(new ActionListener() {        //정산 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO !# 결제창 불러오기
                JOptionPane.showMessageDialog(null, "정산하기");
            }
        });

        MakeParkingLot park = new MakeParkingLot();
        park.floor.setBounds(FIRST_OF_FRAME, TOP_OF_FRAME, 100, 30);   //주차층수 콤보박스(B1, B2, B3)
        park.car.setBounds(FIRST_OF_FRAME, TOP_OF_PARK, 650, 450);                   //주차장 패널

        fareTagButton.setBounds(FIRST_OF_INFO,TOP_OF_FRAME, 100, 30);            //요금표 버튼
        menuButton.setBounds(855, TOP_OF_FRAME, 95, 30);                      //메뉴 버튼

        rsvButton.setBounds(FIRST_OF_INFO, TOP_OF_PARK+10, WIDTH_OF_MAIN_BUTTON, HEIGHT_OF_MAIN_BUTTON);             //예약 버튼
        parkButton.setBounds(FIRST_OF_INFO, TOP_OF_PARK+110, WIDTH_OF_MAIN_BUTTON, HEIGHT_OF_MAIN_BUTTON);            //주차 버튼
        checkButton.setBounds(FIRST_OF_INFO, TOP_OF_PARK+220, WIDTH_OF_MAIN_BUTTON, HEIGHT_OF_MAIN_BUTTON);           //조회 버튼
        payButton.setBounds(FIRST_OF_INFO, TOP_OF_PARK+330, WIDTH_OF_MAIN_BUTTON, HEIGHT_OF_MAIN_BUTTON);             //정산 버튼


        mainCt.add(park.floor);   //주차층수 콤보박스
        mainCt.add(park.car);                   //주차장패널
        mainCt.add(fareTagButton);          //요금표
        mainCt.add(menuButton);             //메뉴버튼
        mainCt.add(rsvButton);              //예약버튼
        mainCt.add(parkButton);             //주차버튼
        mainCt.add(checkButton);            //조회버튼
        mainCt.add(payButton);              //정산버튼


    }//UserMain 생성자 끝

}//UserMain 클래스 끝




class Main extends JFrame{
    public static void main(String[] args) {
        UserMain m = new UserMain();
        m.setTitle("주차 프로그램 - UserMain");
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(1000, 600);
        m.setVisible(true);
        m.setLocationRelativeTo(null);
    }

}


