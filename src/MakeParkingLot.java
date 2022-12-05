import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MakeParkingLot extends JFrame implements ActionListener, ItemListener {
    JLabel areaFloor, areaNum;
    JButton[] btn = new JButton[16];
    JComboBox floor;
    ImageIcon carImg = new ImageIcon("images/car.jpg");
    MakeParkingLot(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        JLabel area = new JLabel("주차구역 : ");
        areaFloor = new JLabel("B1");
        areaNum = new JLabel();
        JButton b = new JButton("확인");

        //주차 현황 panel
        JPanel car = new JPanel();
        car.setLayout(new GridLayout(6,1));
        JPanel p[] = new JPanel[6];
        for(int i=0; i<p.length; i++)
            car.add(p[i] = new JPanel());

        p[0].setLayout(new GridLayout(1,2)); //A열
        p[1].setBackground(Color.LIGHT_GRAY); //통로
        p[2].setLayout(new GridLayout(1,2)); //B열
        p[3].setLayout(new GridLayout(1,2)); //C열
        p[4].setBackground(Color.LIGHT_GRAY); //통로
        p[5].setLayout(new GridLayout(1,2)); //D열

        //주차구역 버튼 꾸미기
        String[] parkingLot = {"A1","A2", "A3", "A4", "B1","B2", "B3","B4", "C1","C2","C3","C4", "D1","D2","D3","D4"}; //주차구역
        for(int i=0; i<btn.length; i++) {
            btn[i] = new JButton(parkingLot[i]);
            //btn[i].setBorderPainted(false);   //외곽선 없애기
            btn[i].setOpaque(false);            //이미지 외 영역 투명하게
            btn[i].setBackground(Color.WHITE);  //흰색 배경
            btn[i].setContentAreaFilled(false); //내용 영역 채우기 없애기
        }

        //Panel P에 버튼 추가
        for(int i=0; i<4; i++)   p[0].add(btn[i]); //A열
        for(int i=4; i<8; i++)   p[2].add(btn[i]); //B열
        for(int i=8; i<12; i++)  p[3].add(btn[i]); //C열
        for(int i=12; i<16; i++) p[5].add(btn[i]); //D열

        //층수 JCombobox
        String[] sfloor = {"B1", "B2", "B3"}; //층수
        floor = new JComboBox(sfloor);

        floor.setBounds(20,50,100,30);
        car.setBounds(20,100,600,350);
        area.setBounds(650,250,100,20);
        areaFloor.setBounds(730,250,100,20);
        areaNum.setBounds(770,250,100,20);
        b.setBounds(700,380,100,20);

        ct.add(area);       //주차구역 JLabel
        ct.add(areaFloor);  //층수 JLabel
        ct.add(areaNum);    //구역 JLabel
        ct.add(b);          //확인 버튼
        ct.add(car);        //주차장 패널
        ct.add(floor);      //콤보박스 층수(B1, B2, B3)

        b.addActionListener(this); //확인 버튼 클릭 시
        for(int i=0; i<btn.length; i++) btn[i].addActionListener(this); //주차구역 선택 시
        floor.addItemListener(this);    //층수 선택 시
    }

    public void itemStateChanged(ItemEvent ie) { //층수 선택 시 이벤트
        String f = (String)floor.getSelectedItem();
        areaFloor.setText(f);
        for(int i=0; i<btn.length; i++) {
            btn[i].setIcon(null); //콤보박스 다른 층수 선택될 때마다
            areaNum.setText("");
        }
    }
    public void actionPerformed(ActionEvent ae) { //주차구역 선택 시 이벤트
        String s = ae.getActionCommand();
        switch(s) {
            case "A1" : {btn[0].setIcon(carImg); areaNum.setText("A1"); for(int i=1; i<btn.length; i++) {btn[i].setIcon(null);} break;}
            case "A2" : {btn[1].setIcon(carImg); areaNum.setText("A2"); for(int i=0; i<btn.length; i++) {if(i!=1) btn[i].setIcon(null);} break;}
            case "A3" : {btn[2].setIcon(carImg); areaNum.setText("A3"); for(int i=0; i<btn.length; i++) {if(i!=2) btn[i].setIcon(null);} break;}
            case "A4" : {btn[3].setIcon(carImg); areaNum.setText("A4"); for(int i=0; i<btn.length; i++) {if(i!=3) btn[i].setIcon(null);} break;}
            case "B1" : {btn[4].setIcon(carImg); areaNum.setText("B1"); for(int i=0; i<btn.length; i++) {if(i!=4) btn[i].setIcon(null);} break;}
            case "B2" : {btn[5].setIcon(carImg); areaNum.setText("B2"); for(int i=0; i<btn.length; i++) {if(i!=5) btn[i].setIcon(null);} break;}
            case "B3" : {btn[6].setIcon(carImg); areaNum.setText("B3"); for(int i=0; i<btn.length; i++) {if(i!=6) btn[i].setIcon(null);} break;}
            case "B4" : {btn[7].setIcon(carImg); areaNum.setText("B4"); for(int i=0; i<btn.length; i++) {if(i!=7) btn[i].setIcon(null);} break;}
            case "C1" : {btn[8].setIcon(carImg); areaNum.setText("C1"); for(int i=0; i<btn.length; i++) {if(i!=8) btn[i].setIcon(null);} break;}
            case "C2" : {btn[9].setIcon(carImg); areaNum.setText("C2"); for(int i=0; i<btn.length; i++) {if(i!=9) btn[i].setIcon(null);} break;}
            case "C3" : {btn[10].setIcon(carImg); areaNum.setText("C3"); for(int i=0; i<btn.length; i++) {if(i!=10) btn[i].setIcon(null);} break;}
            case "C4" : {btn[11].setIcon(carImg); areaNum.setText("C4"); for(int i=0; i<btn.length; i++) {if(i!=11) btn[i].setIcon(null);} break;}
            case "D1" : {btn[12].setIcon(carImg); areaNum.setText("D1"); for(int i=0; i<btn.length; i++) {if(i!=12) btn[i].setIcon(null);} break;}
            case "D2" : {btn[13].setIcon(carImg); areaNum.setText("D2"); for(int i=0; i<btn.length; i++) {if(i!=13) btn[i].setIcon(null);} break;}
            case "D3" : {btn[14].setIcon(carImg); areaNum.setText("D3"); for(int i=0; i<btn.length; i++) {if(i!=14) btn[i].setIcon(null);} break;}
            case "D4" : {btn[15].setIcon(carImg); areaNum.setText("D4"); for(int i=0; i<btn.length; i++) {if(i!=15) btn[i].setIcon(null);} break;}
            case "확인": dispose(); break;
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



