
package UserMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.CostFrame;

public class UserMain extends JFrame{
    JButton fareTagButton, menuButton, rsvButton, parkButton, checkButton, payButton;

    public UserMain() {
        Container mainCt = getContentPane();
        mainCt.setLayout(new BorderLayout());

        
        JPanel parkingLot = new JPanel();       //CENTER
        parkingLot.setLayout(new BorderLayout());
        parkingLot.setBackground(Color.LIGHT_GRAY);

        //TODO !# 주차장 불러오기

        JPanel topPanel = new JPanel();       //NORTH
        topPanel.setLayout(new FlowLayout((FlowLayout.LEFT)));
        
        JPanel bottom_pnl = new JPanel();       //SOUTH - 확인취소 버튼 들어가는 곳과 규격 맞춤
        bottom_pnl.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel mainPanel = new JPanel();    //EAST
        GridLayout g1 = new GridLayout(6,1);
        g1.setVgap(10); //상하여백
        g1.setHgap(50); //좌우여백
        mainPanel.setLayout(g1);

        JPanel fare_menu_Panel = new JPanel();  //요금표랑 메뉴버튼 넣을 Panel
        fare_menu_Panel.setLayout(new FlowLayout(FlowLayout.CENTER));

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
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();                           //메뉴창 팝업
                // TODO #### 메뉴창 가져오는거 왜 안돼지?
                    /*
                menu.setTitle("메뉴");
                menu.setSize(400, 600);
                menu.setVisible(true);
                */

            }
        });

        rsvButton = new JButton("예약");
        rsvButton.addActionListener(new ActionListener() {        //예약 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
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


/*
        topPanel.add(floorCBox);       //층 선택 콤보박스
        TODO 2# ComboBox 추가하기
 */

        fare_menu_Panel.add(fareTagButton); //요금표
        fare_menu_Panel.add(menuButton);    //메뉴버튼

        mainPanel.add(fare_menu_Panel);     //요금표+메뉴버튼
        mainPanel.add(rsvButton);           //예약버튼
        mainPanel.add(parkButton);          //주차버튼
        mainPanel.add(checkButton);         //조회버튼
        mainPanel.add(payButton);           //정산버튼

        mainCt.add(topPanel, BorderLayout.NORTH);      //상단 패널
        mainCt.add(parkingLot, BorderLayout.CENTER);   //주차장 패널
        mainCt.add(mainPanel, BorderLayout.EAST);      //우측 패널





    }//UserMain 생성자 끝

}//UserMain 클래스 끝




class Main extends JFrame{
    public static void main(String[] args) {
        UserMain m = new UserMain();
        m.setTitle("주차 프로그램 - UserMain");
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(1000, 600);
        m.setVisible(true);
    }

}


