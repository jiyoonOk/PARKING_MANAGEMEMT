import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class MakeParkingLot extends JFrame {
    MakeParkingLot(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        JButton[] btn = new JButton[16];
        JComboBox floor;
        ImageIcon carImg = new ImageIcon("images/car.jpg");
        JLabel area = new JLabel("주차구역 : ");
        JLabel areaFloor = new JLabel("B1");
        JLabel areaNum = new JLabel();
        JButton b = new JButton("버튼동작안함");

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

        //주차구역 버튼 꾸미기
        String[] parkingLot = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4", "D1", "D2", "D3", "D4"}; //주차구역
        for (int i = 0; i < btn.length; i++) {
            btn[i] = new JButton(parkingLot[i]);
            //btn[i].setBorderPainted(false);   //외곽선 없애기
            btn[i].setOpaque(false);            //이미지 외 영역 투명하게
            btn[i].setBackground(Color.WHITE);  //흰색 배경
            btn[i].setContentAreaFilled(false); //내용 영역 채우기 없애기
        }

        //Panel P에 버튼 추가
        for (int i = 0; i < 4; i++) p[0].add(btn[i]); //A열
        for (int i = 4; i < 8; i++) p[2].add(btn[i]); //B열
        for (int i = 8; i < 12; i++) p[3].add(btn[i]); //C열
        for (int i = 12; i < 16; i++) p[5].add(btn[i]); //D열

        //층수 JCombobox
        String[] sfloor = {"B1", "B2", "B3"}; //층수
        floor = new JComboBox(sfloor);

        floor.setBounds(20, 50, 100, 30);
        car.setBounds(20, 100, 600, 350);
        area.setBounds(650, 250, 100, 20);
        areaFloor.setBounds(730, 250, 100, 20);
        areaNum.setBounds(770, 250, 100, 20);
        b.setBounds(700, 380, 100, 20);

        ct.add(area);       //주차구역 JLabel
        ct.add(areaFloor);  //층수 JLabel
        ct.add(areaNum);    //구역 JLabel
        ct.add(b);          //확인 버튼
        ct.add(car);        //주차장 패널
        ct.add(floor);      //콤보박스 층수(B1, B2, B3)

        FloorItemListener floorIL = new FloorItemListener(areaFloor, areaNum, floor, btn); //층수 리스너
        floor.addItemListener(floorIL);   //층수 콤보박스 선택 시 이벤트 연결
        AreaActionListener areaAL = new AreaActionListener(areaNum, btn, carImg);
        for (int i = 0; i < btn.length; i++) btn[i].addActionListener(areaAL); //주차구역 버튼 선택 시 이벤트 연결
    }
}
class FloorItemListener implements ItemListener {
    JLabel jAreaFloor, jAreaNum;
    JComboBox jFloor;
    JButton[] jBtn = new JButton[16];
    FloorItemListener(JLabel areaFloor, JLabel areaNum, JComboBox floor, JButton btn[]) {
        jAreaFloor = areaFloor;
        jAreaNum = areaNum;
        jFloor = floor;
        for(int i=0; i<btn.length; i++)
            jBtn[i] = btn[i];
    }
    public void itemStateChanged(ItemEvent ie) {
        String f = (String)jFloor.getSelectedItem();
        jAreaFloor.setText(f);
        for(int i=0; i<jBtn.length; i++) {
            jBtn[i].setIcon(null); //콤보박스 다른 층수 선택될 때마다 버튼 이미지 리셋
            jAreaNum.setText("");
        }
    }
}
class AreaActionListener implements ActionListener {
    JLabel jAreaNum;
    JButton[] jBtn = new JButton[16];
    ImageIcon jCarImg = new ImageIcon("images/car.jpg");
    AreaActionListener(JLabel areaNum, JButton btn[], ImageIcon jCarImg) {
        jAreaNum = areaNum;
        for(int i=0; i<btn.length; i++)
            jBtn[i] = btn[i];
    }
    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        switch(s) {
            case "A1" : {jBtn[0].setIcon(jCarImg); jAreaNum.setText("A1"); for(int i=1; i<jBtn.length; i++) {jBtn[i].setIcon(null);} break;}
            case "A2" : {jBtn[1].setIcon(jCarImg); jAreaNum.setText("A2"); for(int i=0; i<jBtn.length; i++) {if(i!=1) jBtn[i].setIcon(null);} break;}
            case "A3" : {jBtn[2].setIcon(jCarImg); jAreaNum.setText("A3"); for(int i=0; i<jBtn.length; i++) {if(i!=2) jBtn[i].setIcon(null);} break;}
            case "A4" : {jBtn[3].setIcon(jCarImg); jAreaNum.setText("A4"); for(int i=0; i<jBtn.length; i++) {if(i!=3) jBtn[i].setIcon(null);} break;}
            case "B1" : {jBtn[4].setIcon(jCarImg); jAreaNum.setText("B1"); for(int i=0; i<jBtn.length; i++) {if(i!=4) jBtn[i].setIcon(null);} break;}
            case "B2" : {jBtn[5].setIcon(jCarImg); jAreaNum.setText("B2"); for(int i=0; i<jBtn.length; i++) {if(i!=5) jBtn[i].setIcon(null);} break;}
            case "B3" : {jBtn[6].setIcon(jCarImg); jAreaNum.setText("B3"); for(int i=0; i<jBtn.length; i++) {if(i!=6) jBtn[i].setIcon(null);} break;}
            case "B4" : {jBtn[7].setIcon(jCarImg); jAreaNum.setText("B4"); for(int i=0; i<jBtn.length; i++) {if(i!=7) jBtn[i].setIcon(null);} break;}
            case "C1" : {jBtn[8].setIcon(jCarImg); jAreaNum.setText("C1"); for(int i=0; i<jBtn.length; i++) {if(i!=8) jBtn[i].setIcon(null);} break;}
            case "C2" : {jBtn[9].setIcon(jCarImg); jAreaNum.setText("C2"); for(int i=0; i<jBtn.length; i++) {if(i!=9) jBtn[i].setIcon(null);} break;}
            case "C3" : {jBtn[10].setIcon(jCarImg); jAreaNum.setText("C3"); for(int i=0; i<jBtn.length; i++) {if(i!=10) jBtn[i].setIcon(null);} break;}
            case "C4" : {jBtn[11].setIcon(jCarImg); jAreaNum.setText("C4"); for(int i=0; i<jBtn.length; i++) {if(i!=11) jBtn[i].setIcon(null);} break;}
            case "D1" : {jBtn[12].setIcon(jCarImg); jAreaNum.setText("D1"); for(int i=0; i<jBtn.length; i++) {if(i!=12) jBtn[i].setIcon(null);} break;}
            case "D2" : {jBtn[13].setIcon(jCarImg); jAreaNum.setText("D2"); for(int i=0; i<jBtn.length; i++) {if(i!=13) jBtn[i].setIcon(null);} break;}
            case "D3" : {jBtn[14].setIcon(jCarImg); jAreaNum.setText("D3"); for(int i=0; i<jBtn.length; i++) {if(i!=14) jBtn[i].setIcon(null);} break;}
            case "D4" : {jBtn[15].setIcon(jCarImg); jAreaNum.setText("D4"); for(int i=0; i<jBtn.length; i++) {if(i!=15) jBtn[i].setIcon(null);} break;}
            //case "확인": dispose(); break;
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



