import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.font.ImageGraphicAttribute;

public class MakeParkingLot extends JFrame {
    MakeParkingLot(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        JButton[][] btn = new JButton[3][16]; //구역 버튼
        JComboBox floor; //층수 콤보박스
        ImageIcon[] carIcons = {
                new ImageIcon("images/car.jpg"),
                new ImageIcon("images/woman.jpg"),
                new ImageIcon("images/disabled.jpg"),
                new ImageIcon("images/smallCar.jpg"),
                new ImageIcon("images/checkedWoman.jpg"),
                new ImageIcon("images/checkedDisabled.jpg"),
                new ImageIcon("images/checkedSmallCar.jpg")
        };
        JLabel area = new JLabel("주차구역 : ");
        JLabel areaFloor = new JLabel("B1"); //사용자선택값 층수(초기값-B1층)
        JLabel areaNum = new JLabel(); //사용자선택값 구역

        //주차 현황 panel
        JPanel car = new JPanel();
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
        String[] sfloor = {"B1", "B2", "B3"}; //층수
        floor = new JComboBox(sfloor);

        //주차구역 버튼 꾸미기
        String[] parkingLot = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4", "D1", "D2", "D3", "D4"}; //주차구역
        String[] parkingLotA = {"A1", "A2", "A3", "A4"}; //A열
        String[] parkingLotB = {"B1", "B2", "B3", "B4"}; //B열
        String[] parkingLotC = {"C1", "C2", "C3", "C4"}; //C열
        String[] parkingLotD = {"D1", "D2", "D3", "D4"}; //D열

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
                btn[i][j].setHorizontalTextPosition(JButton.CENTER);
            }
        }

        //초기값으로 Panel P에 B1층 주차구역 버튼들 추가
        for (int i = 0; i < 4; i++) p[0].add(btn[0][i]); //A열
        for (int i = 4; i < 8; i++) p[2].add(btn[0][i]); //B열
        for (int i = 8; i < 12; i++) p[3].add(btn[0][i]); //C열
        for (int i = 12; i < 16; i++) p[5].add(btn[0][i]); //D열

        //setBounds 위치 지정
        floor.setBounds(20, 50, 100, 30); //층수 콤보박스
        car.setBounds(20, 100, 500, 400); //주차장 패널
        area.setBounds(650, 250, 100, 20); //주차구역 라벨
        areaFloor.setBounds(730, 250, 100, 20); //층수 (사용자선택)
        areaNum.setBounds(770, 250, 100, 20); //구역 (사용자선택0

        ct.add(floor);      //콤보박스 주차층수(B1, B2, B3)
        ct.add(car);        //주차장 패널
        ct.add(area);       //주차구역 JLabel
        ct.add(areaFloor);  //사용자가 선택한 주차층수 JLabel
        ct.add(areaNum);    //사용자가 선택한 주차구역 JLabel

        //주차층수 리스너 객체 생성 및 선언
        FloorItemListener floorIL = new FloorItemListener(areaNum, floor, p, btn, carIcons);
        floor.addItemListener(floorIL);
        //주차구역 리스너 객체 생성 및 선언
        AreaActionListener areaAL = new AreaActionListener(areaFloor, areaNum, floor, btn, carIcons);
        for (int i = 0; i < btn.length; i++)
            for (int j=0; j<btn[0].length; j++)
                btn[i][j].addActionListener(areaAL);
    }
}

//층수 선택 시 이벤트 추가 클래스
class FloorItemListener implements ItemListener {
    JLabel jAreaNum;
    JComboBox jFloor;
    JPanel[] jP;
    JButton[][] jBtn;
    ImageIcon[] jCarIcons;
    int i, j;
    FloorItemListener(JLabel areaNum, JComboBox floor, JPanel p[], JButton btn[][], ImageIcon carIcons[]) {
        jAreaNum = areaNum; //선택된 구역
        jFloor = floor; //층수 콤보박스
        jP = p.clone(); //패널(실인수-클래스 매개변수) 배열 복사
        jBtn = new JButton[btn.length][btn[0].length];
        jCarIcons = carIcons.clone();
        for (i=0; i<jBtn.length; i++)
            System.arraycopy(btn[i],0,jBtn[i],0,btn[0].length); //버튼(실인수-클래스 매개변수) 배열 복사
    }
    public void itemStateChanged(ItemEvent ie) {
        String f = (String)jFloor.getSelectedItem();
        //for (i=0; i<3; i++) for (j=4; j<12; j++) jBtn[i][j].setIcon(null);
        for (i=0; i<jBtn.length; i++) {
            for (j=0; j<2; j++)   jBtn[i][j].setIcon(jCarIcons[1]); //A1, A2
            for (j=2; j<4; j++)   jBtn[i][j].setIcon(jCarIcons[2]); //A3, A4
            for (j=4; j<12; j++)  jBtn[i][j].setIcon(null);         //B, C열
            for (j=12; j<16; j++) jBtn[i][j].setIcon(jCarIcons[3]); //D열
        }
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
    JLabel jAreaFloor, jAreaNum;
    JComboBox jFloor;
    JButton[][] jBtn;
    ImageIcon[] jCarIcons;
    int i,j;
    AreaActionListener(JLabel areaFloor, JLabel areaNum, JComboBox floor, JButton btn[][], ImageIcon carIcons[]) {
        jAreaFloor = areaFloor; //선택된 층수
        jAreaNum = areaNum;
        jFloor = floor;
        jBtn = new JButton[btn.length][btn[0].length];
        for (i=0; i<jBtn.length; i++)
            System.arraycopy(btn[i],0,jBtn[i],0,btn[0].length);
        jCarIcons = carIcons.clone();
    }
    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        String f = (String)jFloor.getSelectedItem();
        jAreaFloor.setText(f);
        jAreaNum.setText(s);
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
            case "B1" : jBtn[0][n].setIcon(jCarIcons[m]);
            case "B2" : jBtn[1][n].setIcon(jCarIcons[m]);
            case "B3" : jBtn[2][n].setIcon(jCarIcons[m]);
        }
        for (i=0; i<jBtn.length; i++) {
            for (j = 0; j < 2; j++) if (j != n) jBtn[i][j].setIcon(jCarIcons[1]);
            for (j = 2; j < 4; j++) if (j != n) jBtn[i][j].setIcon(jCarIcons[2]);
            for (j = 4; j < 12; j++) if (j != n) jBtn[i][j].setIcon(null);
            for (j = 12; j < 16; j++) if (j != n) jBtn[i][j].setIcon(jCarIcons[3]);
        }
    }
}
class Main {
    public static void main(String[] args) {
        MakeParkingLot win = new MakeParkingLot("주차");
        win.setSize(900, 600);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
}





