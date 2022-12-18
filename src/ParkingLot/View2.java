package ParkingLot;

import javax.swing.*;
import java.awt.*;

import static ParkingLot.Car.*;

public class View2 extends JPanel {
    static JButton[] btn = new JButton[16]; //주차구역 버튼
    int i, j;
    View2() {
        setLayout (new GridLayout(6,1));

        for (int i = 0; i < p.length; i++)
            add(p[i] = new JPanel());

        p[0].setLayout(new GridLayout(1, 2)); //A열
        p[1].setBackground(Color.LIGHT_GRAY); //통로
        p[2].setLayout(new GridLayout(1, 2)); //B열
        p[3].setLayout(new GridLayout(1, 2)); //C열
        p[4].setBackground(Color.LIGHT_GRAY); //통로
        p[5].setLayout(new GridLayout(1, 2)); //D열

        //주차구역에 따른 버튼아이콘 초기화
        for (i=0; i<2; i++)   btn[i] = new JButton(parkingLot[i], carIcons[1]); //A1, A2
        for (i=2; i<4; i++)   btn[i] = new JButton(parkingLot[i], carIcons[2]); //A3, A4
        for (i=4; i<12; i++)  btn[i] = new JButton(parkingLot[i]);              //B, C열
        for (i=12; i<16; i++) btn[i] = new JButton(parkingLot[i], carIcons[3]); //D열


        //A~D열에 버튼 추가
        for (i=0; i<4; i++)   p[0].add(btn[i]); //A열
        for (i=4; i<8; i++)   p[2].add(btn[i]); //B열
        for (i=8; i<12; i++)  p[3].add(btn[i]); //C열
        for (i=12; i<16; i++) p[5].add(btn[i]); //D열

        //버튼 꾸미기
        for (i = 0; i < parkingLot.length; i++) {
            btn[i].setOpaque(false);            //이미지 외 영역 투명하게
            btn[i].setBackground(Color.WHITE);  //흰색 배경
            btn[i].setContentAreaFilled(false); //내용 영역 채우기 없애기
            btn[i].setForeground(Color.RED);    //글자색상 변경
            btn[i].setHorizontalTextPosition(JButton.CENTER); //텍스트 가운데 정렬
        }

        //DB 특이사항에 맞게 주차구역 막아놓음
        for (int i=0; i<sfloor.length; i++) {
            if (!userIsWoman)     for (j = 0; j < 2; j++)    btn[j].setEnabled(false);
            if (!userIsHandicap)  for (j = 2; j < 4; j++)    btn[j].setEnabled(false);
            if (!userIsSmallCar)  for (j = 12; j < 16; j++)  btn[j].setEnabled(false);
        }

        //주차구역 리스너 객체 생성 및 선언
        AreaActionListener areaAL = new AreaActionListener(btn, carIcons, "B2", 1);
        for (i = 0; i < btn.length; i++) btn[i].addActionListener(areaAL);
    }
}
