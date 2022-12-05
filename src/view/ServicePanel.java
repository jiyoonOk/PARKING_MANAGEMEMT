package view;

import UserMain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServicePanel {

    JPanel svcPnl = new JPanel(); //우측 서비스(예약,주차,조회,정산)
    JButton fareTag = new JButton("요금표");

    JButton menu = new JButton("메뉴");

    JButton rsv = new JButton("예약");
    JButton park = new JButton("주차");
    JButton check = new JButton("조회");
    JButton pay = new JButton("정산");
    
    public ServicePanel() {
        JPanel fare_menu = new JPanel();//우측상단
        fare_menu.setLayout(new FlowLayout());

        JPanel pnl = new JPanel();//우측
        GridLayout g = new GridLayout(6,1);
        g.setVgap(10);
        g.setHgap(30);
        pnl.setLayout(g);

        fareTag.addActionListener(new ActionListener() {//요금표
            @Override
            public void actionPerformed(ActionEvent e) {
                //요금표 불러오기
                JOptionPane.showMessageDialog(null, "요금표");
            }
        });

        menu.addActionListener(new ActionListener() {//메뉴
            @Override
            public void actionPerformed(ActionEvent e) {
                //메뉴 불러오기
                JOptionPane.showMessageDialog(null, "메뉴");
            }
        });

        rsv.addActionListener(new ActionListener() {//예약
            @Override
            public void actionPerformed(ActionEvent e) {
                //예약 페이지 불러오기
                Rsv r = new Rsv();
                r.setTitle("예약하기");
                r.setSize(1000,600);
                r.setVisible(true);

            }
        });

        park.addActionListener(new ActionListener() {//주차
            @Override
            public void actionPerformed(ActionEvent e) {
                //주차 페이지 불러오기
                JOptionPane.showMessageDialog(null, "주차하기");
            }
        });

        check.addActionListener(new ActionListener() {//조회
            @Override
            public void actionPerformed(ActionEvent e) {
                //조회 페이지 불러오기
                JOptionPane.showMessageDialog(null, "조회하기");
            }
        });

        pay.addActionListener(new ActionListener() {//정산
            @Override
            public void actionPerformed(ActionEvent e) {
                //정산 페이지 불러오기
                JOptionPane.showMessageDialog(null, "정산하기");
            }
        });



        fare_menu.add(fareTag);
        fare_menu.add(menu);

        pnl.add(fare_menu);
        pnl.add(rsv);
        pnl.add(park);
        pnl.add(check);
        pnl.add(pay);
    }
}
