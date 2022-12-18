package ParkingLot;

import Parking.Reservation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ParkingLot.Car.*;

public class AreaActionListener implements ActionListener {
    JButton[] jBtn;
    ImageIcon[] jCarIcons;
    int i,j;
    String f;
    int k;

    AreaActionListener(JButton[] btn, ImageIcon[] carIcons, String f, int k) {
        this.f = f;
        this.k = k;

        jBtn = btn.clone();
        jCarIcons = carIcons.clone();
    }

    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        Car.userFloor.setText(f);
        Car.userArea .setText(s);
        Reservation.location.updateUI();
        switch(s) {
            case "A1" : settingIcon(0, 4);  break; //여성
            case "A2" : settingIcon(1, 4);  break;
            case "A3" : settingIcon(2, 5);  break; //장애인
            case "A4" : settingIcon(3, 5);  break;
            case "B1" : settingIcon(4, 0);  break; //일반
            case "B2" : settingIcon(5, 0);  break;
            case "B3" : settingIcon(6, 0);  break;
            case "B4" : settingIcon(7, 0);  break;
            case "C1" : settingIcon(8, 0);  break;
            case "C2" : settingIcon(9, 0);  break;
            case "C3" : settingIcon(10, 0); break;
            case "C4" : settingIcon(11, 0); break;
            case "D1" : settingIcon(12, 6); break; //경차
            case "D2" : settingIcon(13, 6); break;
            case "D3" : settingIcon(14, 6); break;
            case "D4" : settingIcon(15, 6); break;
        }
    }
    void settingIcon(int n, int m) { //n=버튼 배열 원소, m=주차아이콘 배열 원소
        jBtn[n].setIcon(jCarIcons[m]);  //B1층 A3 jBtn[0][2].setIcion(carIcons[4]);

        for (j = 0; j < 2; j++) if (j != n)   jBtn[j].setIcon(jCarIcons[1]); //여성 구역 초기화
        for (j = 2; j < 4; j++) if (j != n)   jBtn[j].setIcon(jCarIcons[2]); //경차 구역 초기화
        for (j = 4; j < 12; j++) if (j != n)  jBtn[j].setIcon(null);        //일반 구역 초기화
        for (j = 12; j < 16; j++) if (j != n) jBtn[j].setIcon(jCarIcons[3]); //장애인 구역 초기화

    }
}
