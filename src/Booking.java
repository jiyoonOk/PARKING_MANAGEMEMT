import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Booking extends JFrame implements ActionListener{
    JLabel name, id, carNum, areaFloor, areaNum, inTime;
    JButton[] btn = new JButton[16];
    JComboBox floor;
    ImageIcon carImg = new ImageIcon("images/car.jpg");
    Booking(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        //우선 상수 선언하기
        final String f_name = "양지은";
        final String f_id = "qwertasd";
        final String f_carNum = "38너1849";
        final String f_inTime = "3시 30분";

        JLabel label = new JLabel("주차 일반 조회");
        JLabel l1_name = new JLabel("이        름 : ");
        JLabel l2_id = new JLabel("아 이 디 : ");
        JLabel l3_carNum = new JLabel("차량번호 : ");
        JLabel l4_area = new JLabel("주차구역 : ");
        JLabel l5_inTime = new JLabel("입차시간 : ");
        name = new JLabel(f_name);
        id = new JLabel(f_id);
        carNum = new JLabel(f_carNum);
        areaFloor = new JLabel();
        areaNum = new JLabel();
        inTime = new JLabel(f_inTime);
        JButton b = new JButton("확인");

        //주차 현황 panel
        JPanel car = new JPanel();
        car.setLayout(new GridLayout(6,1));
        JPanel p[] = new JPanel[6];
        for(int i=0; i<p.length; i++)
            car.add(p[i] = new JPanel());

        p[1].setBackground(Color.LIGHT_GRAY); //통로
        p[4].setBackground(Color.LIGHT_GRAY); //통로

        p[0].setLayout(new GridLayout(1,2)); //A열
        p[2].setLayout(new GridLayout(1,2)); //B열
        p[3].setLayout(new GridLayout(1,2)); //C열
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
        label.setBounds(700,80,100,20);
        l1_name.setBounds(650,130,100,20); name.setBounds(730,130,100,20);
        l2_id.setBounds(650,170,100,20); id.setBounds(730,170,100,20);
        l3_carNum.setBounds(650,210,100,20); carNum.setBounds(730,210,100,20);
        l4_area.setBounds(650,250,100,20); areaFloor.setBounds(730,250,100,20); areaNum.setBounds(770,250,100,20);
        l5_inTime.setBounds(650,290,100,20); inTime.setBounds(730,290,100,20);
        b.setBounds(700,380,100,20);

        ct.add(label);
        ct.add(l1_name); ct.add(name);
        ct.add(l2_id); ct.add(id);
        ct.add(l3_carNum); ct.add(carNum);
        ct.add(l4_area); ct.add(areaFloor); ct.add(areaNum);
        ct.add(l5_inTime); ct.add(inTime);
        ct.add(b);
        ct.add(car);
        ct.add(floor);


        b.addActionListener(this); //확인 버튼
        for(int i=0; i<btn.length; i++) btn[i].addActionListener(this); //주차구역 버튼

    }
    public void actionPerformed(ActionEvent ae) {
        String f = (String)floor.getSelectedItem(); areaFloor.setText(f);
        //콤보박스 다른 층수 선택될 때마다 for(int i=0; i<btn.length; i++) btn[i].setIcon(null);
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

class BookingMain {
    public static void main(String[] args) {
        Booking win = new Booking("주차");
        win.setSize(900, 600);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
}



