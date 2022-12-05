package view;

import java.awt.*;
import javax.swing.*;

public class ParkingPanel extends JFrame {

    String[] marks_str = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10"}; //B,C,D까지
    JLabel[] marks_lbl = new JLabel[10]; //숫자변경(40)
    JButton[] parkingLoc = new JButton[10]; //주차 위치(칸) - 숫자변경(40)
    JPanel[] section = new JPanel[1]; //주차 열 - 숫자변경(6)
    JPanel area; //주차장

    public ParkingPanel() {//생성자

        for (int i=0; i<marks_str.length; i++) { //String 으로 JLabel 칸 이름 설정
            marks_lbl[i].setText(marks_str[i]);
        }
        for (int i=0; i<parkingLoc.length; i++) { //주차 칸 JButton 에 JLabel 적용
            parkingLoc[i].add(marks_lbl[i]); //버튼 이름
        }//TODO : 후에 클릭하면 사진으로 대체, 클릭한 버튼 인덱스 저장 필요

        for(int i=0; i<section.length; i++) { //주차 열 Panel 의 칸 레이아웃 설정 - 범위변경필요
            section[i].setLayout(new GridLayout(0, 10)); //주차 칸 10개
            if (i!=2 && i!=5) { //2열과 5열은 주차공간이 아님
                for (int j = 0; j < parkingLoc.length; j++) { //나머지 열에만 버튼 적용
                    section[i].add(parkingLoc[j]);
                }
            }
        }

        area.setLayout(new GridLayout(6,1)); //주차 열 6개, 그 중 4개만 사용
        for (int i=0; i<6; i++) {
            area.add(section[i]);
        }

        Container ct = getContentPane();
        ct.setLayout(new FlowLayout());
        ct.add(area);


    }//생성자 끝

}//class 끝
