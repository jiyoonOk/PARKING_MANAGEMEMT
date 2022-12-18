package UserMain;

import Parking.*;
import Pay.*;
import UserMenu.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

public class UserMain extends JFrame{
    public static final int FIRST_OF_INFO = 0;
    JButton rsvButton, parkButton, checkButton, payButton;
    JMenu menu;
    JMenuItem fareTag, myPage, notion, question, logout;
    String userId;

    public UserMain(String id) {
        userId = id;

        Container mainCt = getContentPane();
        mainCt.setLayout(new GridLayout(2,2));

    /*---------------------------------------------------------------------------------------------------------------*/
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        menu       = new JMenu("메뉴");             //메뉴바에 표시되는 메뉴이름
        fareTag    = new JMenuItem("요금표");     //아이템들
        //-----------------------------------------------구분선 추가함
        myPage     = new JMenuItem("마이페이지");
        notion     = new JMenuItem("공지사항");
        question   = new JMenuItem("문의사항");
        //-----------------------------------------------구분선 추가함
        logout     = new JMenuItem("로그아웃");


        //메뉴 아이템들 이벤트
        fareTag.addActionListener(new ActionListener() {    //요금표 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) {          //요금표 팝업
                CostFrame costFrame = new CostFrame("요금표");
                costFrame.setSize(300,300);
                costFrame.setVisible(true);
                costFrame.setLocationRelativeTo(null);
            }
        });
        myPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mypage mg=new Mypage(userId);
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
                Notice nt=new Notice();
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
                Question win = new Question(userId);
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
                int r = JOptionPane.showConfirmDialog(logout, "정말 로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        menu.add(fareTag);   //요금표
        menu.addSeparator(); //구분선
        menu.add(myPage);    //마이페이지
        menu.add(notion);    //공지사항
        menu.add(question);  //문의사항
        menu.addSeparator(); //구분선
        menu.add(logout);    //로그아웃

        bar.add(menu);
    /*---------------------------------------------------------------------------------------------------------------*/


        rsvButton   = new JButton("예약");
        parkButton  = new JButton("주차");
        checkButton = new JButton("조회");
        payButton   = new JButton("정산");
        rsvButton.addActionListener(new ActionListener() {        //예약버튼 클릭
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservation m = new Reservation(userId);
                m.setTitle("주차 프로그램 - 주차예약");
                m.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                m.setSize(1000, 600);
                m.setVisible(true);

            }});
        parkButton.addActionListener(new ActionListener() {       //주차버튼 클릭
            @Override
            public void actionPerformed(ActionEvent e) {
                Entrance p = new Entrance(userId);
                p.setTitle("주차 프로그램 - 주차일반");
                p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                p.setSize(1000, 600);
                p.setVisible(true);
            }});
        checkButton.addActionListener(new ActionListener() {      //조회버튼 클릭
            @Override
            public void actionPerformed(ActionEvent e) {
                Inquire p = new Inquire(userId);
                p.setTitle("주차 프로그래 - 조회");
                p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                p.setSize(1000, 600);
                p.setVisible(true);
                p.setLocationRelativeTo(null);
            }});
        payButton.addActionListener(new ActionListener() {        //정산버튼 클릭
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = JOptionPane.showConfirmDialog(mainCt, "출차 하시겠습니까?\n(확인 시 결제로 넘어감)", "안내", JOptionPane.YES_NO_OPTION);
                if(r == JOptionPane.YES_OPTION) {
                    Pay p = new Pay(userId);
                    try {
                        p.checkPayType();
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }}}});

        mainCt.add(rsvButton);              //예약버튼
        mainCt.add(parkButton);             //주차버튼
        mainCt.add(checkButton);            //조회버튼
        mainCt.add(payButton);              //정산버튼

    }//UserMain 생성자 끝
}//UserMain 클래스 끝