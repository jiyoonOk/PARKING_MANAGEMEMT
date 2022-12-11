
package UserMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.CostFrame;

public class UserMain extends JFrame{
    JButton fareTagButton = new JButton("요금표");
    JButton menuButton = new JButton("메뉴");
    JButton rsvButton = new JButton("예약");
    JButton parkButton = new JButton("주차");
    JButton checkButton = new JButton("조회");
    JButton payButton = new JButton("정산");

    public UserMain() {
        Container mainCt = getContentPane();
        mainCt.setLayout(new BorderLayout());

        /*-------------------Rsv, Check 와 겹치는 내용----------------------*/
        JPanel parkingLot = new JPanel();       //CENTER
        parkingLot.setLayout(new BorderLayout());
        parkingLot.setBackground(Color.LIGHT_GRAY);

        //TODO 1# 주차장 가져오기



        JPanel topPanel = new JPanel();       //NORTH
        topPanel.setLayout(new FlowLayout((FlowLayout.LEFT)));
        /*---------------CENTER, NORTH 에 위치하는 Panel 들----------------*/

        JPanel mainPanel = new JPanel();    //EAST
        GridLayout g = new GridLayout(6,1);
        g.setVgap(10); //상하여백
        g.setHgap(30); //좌우여백
        mainPanel.setLayout(g);


        fareTagButton.addActionListener(new ActionListener() {    //요금표 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                CostFrame costFrame = new CostFrame("요금표");
                costFrame.setSize(300,300);
                costFrame.setVisible(true);
                costFrame.setLocationRelativeTo(null);
            }
        });

        menuButton.addActionListener(new ActionListener() {       //메뉴 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                // TODO #### 메뉴창 가져오는거 왜 안돼지?
                    /*
                menu.setTitle("메뉴");
                menu.setSize(400, 600);
                menu.setVisible(true);
                */

            }
        });

        rsvButton.addActionListener(new ActionListener() {        //예약 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                Rsv m = new Rsv("주차 프로그램 - 주차예약");
                m.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                m.setSize(1000, 600);
                m.setVisible(true);

            }
        });

        parkButton.addActionListener(new ActionListener() {       //주차 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                Park p = new Park("주차 프로그램 - 일반주차");
                p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                p.setSize(1000, 600);
                p.setVisible(true);
            }
        });

        checkButton.addActionListener(new ActionListener() {      //조회 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                //조회 페이지 불러오기
                JOptionPane.showMessageDialog(null, "조회하기");
            }
        });

        payButton.addActionListener(new ActionListener() {        //정산 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                //정산 페이지 불러오기
                JOptionPane.showMessageDialog(null, "정산하기");
            }
        });


/*
        topPanel.add(floorCBox);       //층 선택 콤보박스
        TODO 2# ComboBox 추가하기
 */


        mainPanel.add(fareTagButton);    //요금표+메뉴버튼
        mainPanel.add(menuButton);
        mainPanel.add(rsvButton);          //예약버튼
        mainPanel.add(parkButton);         //주차버튼
        mainPanel.add(checkButton);        //조회버튼
        mainPanel.add(payButton);          //정산버튼

        mainCt.add(topPanel, BorderLayout.NORTH);      //상단 패널
        mainCt.add(parkingLot, BorderLayout.CENTER);  //주차장 패널
        mainCt.add(mainPanel, BorderLayout.EAST);    //우측 패널





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


