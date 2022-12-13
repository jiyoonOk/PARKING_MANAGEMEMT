package common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ParkingLot extends JFrame {
    public static JLabel userFloor, userNum;
    public static JComboBox floor; //층수 콤보박스
    public static JPanel car;
    public static String[] sfloor = {"B1", "B2", "B3"}; //층수
    public ParkingLot() {

        JButton[][] btn = new JButton[3][16]; //구역 버튼

        userFloor = new JLabel(); //사용자선택값 층수(초기값-B1층)
        userNum = new JLabel(); //사용자선택값 구역
        ImageIcon[] carIcons = {
                new ImageIcon("images/Car.jpg"), //일반, checked
                new ImageIcon("images/woman.jpg"), //여성
                new ImageIcon("images/disabled.jpg"), //장애인
                new ImageIcon("images/smallCar.jpg"), //경차
                new ImageIcon("images/checkedWoman.jpg"), // 여성, checked
                new ImageIcon("images/checkedDisabled.jpg"), //장애인, checked
                new ImageIcon("images/checkedSmallCar.jpg") //경차, checked
        };

        //주차 현황 panel
        car = new JPanel();
        car.setLayout(new GridLayout(6, 1));
        JPanel p[] = new JPanel[6];
        for (int i = 0; i < p.length; i++)
            car.add(p[i] = new JPanel());

        p[0].setLayout(new GridLayout(1, 2)); //A열
        p[1].setBackground(Color.LIGHT_GRAY); //통로
        p[2].setLayout(new GridLayout(1, 2)); //B열
        p[3].setLayout(new GridLayout(1, 2)); //C열
        p[4].setBackground(Color.LIGHT_GRAY); //통로
        p[5].setLayout(new GridLayout(1, 2)); //D열

        //층수 JCombobox
        floor = new JComboBox(sfloor);

        //주차구역 버튼 꾸미기
        String[] parkingLot = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4", "D1", "D2", "D3", "D4"}; //주차구역

        //주차구역에 따른 버튼아이콘 초기화
        for (int i=0; i< sfloor.length; i++) {
            for (int j=0; j<2; j++)   btn[i][j] = new JButton(parkingLot[j], carIcons[1]); //A1, A2
            for (int j=2; j<4; j++)   btn[i][j] = new JButton(parkingLot[j], carIcons[2]); //A3, A4
            for (int j=4; j<12; j++)  btn[i][j] = new JButton(parkingLot[j]);              //B, C열
            for (int j=12; j<16; j++) btn[i][j] = new JButton(parkingLot[j], carIcons[3]); //D열
        }

        //층수에 따른 버튼 배열 btn[3][16] 만들기
        for (int i=0; i<sfloor.length; i++) {
            for (int j = 0; j < parkingLot.length; j++) {
                btn[i][j].setOpaque(false);            //이미지 외 영역 투명하게
                btn[i][j].setBackground(Color.WHITE);  //흰색 배경
                btn[i][j].setContentAreaFilled(false); //내용 영역 채우기 없애기
                btn[i][j].setForeground(Color.RED);    //글자색상 변경
                btn[i][j].setHorizontalTextPosition(JButton.CENTER); //텍스트 가운데 정렬
            }
        }

        //초기값으로 Panel P에 B1층 주차구역 버튼들 추가
        for (int i = 0; i < 4; i++) p[0].add(btn[0][i]); //A열
        for (int i = 4; i < 8; i++) p[2].add(btn[0][i]); //B열
        for (int i = 8; i < 12; i++) p[3].add(btn[0][i]); //C열
        for (int i = 12; i < 16; i++) p[5].add(btn[0][i]); //D열


        //주차층수 리스너 객체 생성 및 선언
        FloorItemListener floorIL = new FloorItemListener(userNum, floor, p, btn, carIcons);
        floor.addItemListener(floorIL);
        //주차구역 리스너 객체 생성 및 선언
        AreaActionListener areaAL = new AreaActionListener(userFloor, userNum, floor, btn, carIcons);
        for (int i = 0; i < btn.length; i++)
            for (int j=0; j<btn[0].length; j++)
                btn[i][j].addActionListener(areaAL);
    }
}

//층수 선택 시 이벤트 추가 클래스
class FloorItemListener implements ItemListener {
    JLabel jUserNum;
    JComboBox jFloor;
    JPanel[] jP;
    JButton[][] jBtn;
    ImageIcon[] jCarIcons;
    int i, j;
    FloorItemListener(JLabel userNum, JComboBox floor, JPanel p[], JButton btn[][], ImageIcon carIcons[]) {
        jUserNum = userNum; //선택된 구역
        jFloor = floor; //층수 콤보박스
        jP = p.clone(); //패널(실인수-클래스 매개변수) 배열 복사
        jBtn = new JButton[btn.length][btn[0].length];
        jCarIcons = carIcons.clone();
        for (i=0; i<jBtn.length; i++)
            System.arraycopy(btn[i],0,jBtn[i],0,btn[0].length); //버튼(실인수-클래스 매개변수) 배열 복사
    }
    public void itemStateChanged(ItemEvent ie) {
        String f = (String)jFloor.getSelectedItem();
        switch (f) {
            case "B1" : //panel jP에 1층 버튼 추가 및 2,3층 버튼 삭제
                for (i = 0; i < 4; i++  ) {jP[0].add(jBtn[0][i]); jP[0].remove(jBtn[1][i]); jP[0].remove(jBtn[2][i]);} //A열
                for (i = 4; i < 8; i++)   {jP[2].add(jBtn[0][i]); jP[2].remove(jBtn[1][i]); jP[2].remove(jBtn[2][i]);} //B열
                for (i = 8; i < 12; i++)  {jP[3].add(jBtn[0][i]); jP[3].remove(jBtn[1][i]); jP[3].remove(jBtn[2][i]);} //C열
                for (i = 12; i < 16; i++) {jP[5].add(jBtn[0][i]); jP[5].remove(jBtn[1][i]); jP[5].remove(jBtn[2][i]);} //D열
                break;
            case "B2" : //panel jP에 2층 버튼 추가 및 1,3층 버튼 삭제
                for (i = 0; i < 4; i++)   {jP[0].add(jBtn[1][i]); jP[0].remove(jBtn[0][i]); jP[0].remove(jBtn[2][i]);} //A열
                for (i = 4; i < 8; i++)   {jP[2].add(jBtn[1][i]); jP[2].remove(jBtn[0][i]); jP[2].remove(jBtn[2][i]);} //B열
                for (i = 8; i < 12; i++)  {jP[3].add(jBtn[1][i]); jP[3].remove(jBtn[0][i]); jP[3].remove(jBtn[2][i]);} //C열
                for (i = 12; i < 16; i++) {jP[5].add(jBtn[1][i]); jP[5].remove(jBtn[0][i]); jP[5].remove(jBtn[2][i]);} //D열
                break;
            case "B3" : //panel jP에 3층 버튼 추가 및 1,2층 버튼 삭제
                for (i = 0; i < 4; i++)   {jP[0].add(jBtn[2][i]); jP[0].remove(jBtn[0][i]); jP[0].remove(jBtn[1][i]);} //A열
                for (i = 4; i < 8; i++)   {jP[2].add(jBtn[2][i]); jP[2].remove(jBtn[0][i]); jP[2].remove(jBtn[1][i]);} //B열
                for (i = 8; i < 12; i++)  {jP[3].add(jBtn[2][i]); jP[3].remove(jBtn[0][i]); jP[3].remove(jBtn[1][i]);} //C열
                for (i = 12; i < 16; i++) {jP[5].add(jBtn[2][i]); jP[5].remove(jBtn[0][i]); jP[5].remove(jBtn[1][i]);} //D열
                break;
        }
    }
}
//주차구역 선택 시 이벤트 추가 클래스
class AreaActionListener implements ActionListener {
    JLabel jUserFloor, jUserNum;
    JComboBox jFloor;
    JButton[][] jBtn;
    ImageIcon[] jCarIcons;
    int i,j;
    AreaActionListener(JLabel userFloor, JLabel userNum, JComboBox floor, JButton btn[][], ImageIcon carIcons[]) {
        jUserFloor = userFloor; //선택된 층수
        jUserNum = userNum;
        jFloor = floor;
        jBtn = new JButton[btn.length][btn[0].length];
        for (i=0; i<jBtn.length; i++)
            System.arraycopy(btn[i],0,jBtn[i],0,btn[0].length);
        jCarIcons = carIcons.clone();
    }
    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        String f = (String)jFloor.getSelectedItem();
        jUserFloor.setText(f);
        jUserNum.setText(s);
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
    //주차 아이콘 설정 메소드
    public void settingIcon(int n, int m) { //n=버튼 배열 원소, m=주차아이콘 배열 원소
    String f = (String)jFloor.getSelectedItem();
    switch (f) {
        case "B1" : jBtn[0][n].setIcon(jCarIcons[m]); break;
        case "B2" : jBtn[1][n].setIcon(jCarIcons[m]); break;
        case "B3" : jBtn[2][n].setIcon(jCarIcons[m]); break;
    }
    for (i=0; i<3; i++) {
        for (j = 0; j < 2; j++) if (j != n) jBtn[i][j].setIcon(jCarIcons[1]);
        for (j = 2; j < 4; j++) if (j != n) jBtn[i][j].setIcon(jCarIcons[2]);
        for (j = 4; j < 12; j++) if (j != n) jBtn[i][j].setIcon(null);
        for (j = 12; j < 16; j++) if (j != n) jBtn[i][j].setIcon(jCarIcons[3]);
    }
}
}




