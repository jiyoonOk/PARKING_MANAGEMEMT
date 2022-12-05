import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParkingSearch extends JFrame implements ActionListener{
    JButton b;
    ParkingSearch() {
        Container ct = getContentPane();
        ct.setLayout(null);

        b = new JButton("조회");
        b.setBounds(50,100,70,30);
        ct.add(b);

        b.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae) {
        //setVisible(false);
        //if () {} //주차일반조회 객체 생성
        // else {} //주차예약조회 객체 생성
        Search1 sch1 = new Search1("주차일반조회");
        sch1.setSize(900,600);
        sch1.setVisible(true);
    }
}

/*

예약 주차 조회 정산 버튼 누를때
메인 창에서 창 전환 기능 구현??
메인 창에서 -> 예약..주차.. 이런 창들이 더 하나 떠서 구현하는거야?
패널을 지우고 새로 올린다는게 클래스 내에서 한다는거야?

 */
class Search1 extends JFrame implements ActionListener {
    Search1(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        //우선 상수 선언하기
        final String f_name = "양지은";
        final String f_id = "qwertasd";
        final String f_carNum = "38너1849";
        final String f_area = "A구역 4";
        final String f_inTime = "3시 30분";

        JLabel label = new JLabel("주차 일반 조회");
        JLabel l1_name = new JLabel("이        름 : ");
        JLabel l2_id = new JLabel("아 이 디 : ");
        JLabel l3_carNum = new JLabel("차량번호 : ");
        JLabel l4_area = new JLabel("주차구역 : ");
        JLabel l5_inTime = new JLabel("입차시간 : ");
        JLabel name = new JLabel(f_name);
        JLabel id = new JLabel(f_id);
        JLabel carNum = new JLabel(f_carNum);
        JLabel area = new JLabel(f_area);
        JLabel inTime = new JLabel(f_inTime);
        JButton b = new JButton("확인");

        JPanel car = new JPanel();
        car.setLayout(new GridLayout(6,1));
        car.setBackground(Color.WHITE);
        JPanel p[] = new JPanel[6];
        for(int i=0; i<p.length; i++) {
            car.add(p[i] = new JPanel());
            p[i].setBackground(Color.GRAY);
        }
        p[0].setLayout(new GridLayout(1,2));
        p[2].setLayout(new GridLayout(1,2));
        p[3].setLayout(new GridLayout(1,2));
        p[5].setLayout(new GridLayout(1,2));

        String[] parkingLotA = {"A1","A2"};
        String[] parkingLotB = {"B1","B2"};
        String[] parkingLotC = {"C1","C2"};
        String[] parkingLotD = {"D1","D2"};
        JButton[] btnA = new JButton[2];
        JButton[] btnB = new JButton[2];
        JButton[] btnC = new JButton[2];
        JButton[] btnD = new JButton[2];

        for(int i=0; i<2; i++){
            btnA[i] = new JButton(parkingLotA[i]);
            btnB[i] = new JButton(parkingLotB[i]);
            btnC[i] = new JButton(parkingLotC[i]);
            btnD[i] = new JButton(parkingLotD[i]);
            p[0].add(btnA[i]);
            p[2].add(btnB[i]);
            p[3].add(btnC[i]);
            p[5].add(btnD[i]);
        }

        car.setBounds(20,100,600,350);
        label.setBounds(700,80,100,20);
        l1_name.setBounds(650,130,100,20); name.setBounds(730,130,100,20);
        l2_id.setBounds(650,170,100,20); id.setBounds(730,170,100,20);
        l3_carNum.setBounds(650,210,100,20); carNum.setBounds(730,210,100,20);
        l4_area.setBounds(650,250,100,20); area.setBounds(730,250,100,20);
        l5_inTime.setBounds(650,290,100,20); inTime.setBounds(730,290,100,20);
        b.setBounds(700,380,100,20);

        ct.add(label);
        ct.add(l1_name); ct.add(name);
        ct.add(l2_id); ct.add(id);
        ct.add(l3_carNum); ct.add(carNum);
        ct.add(l4_area); ct.add(area);
        ct.add(l5_inTime); ct.add(inTime);
        ct.add(b);
        ct.add(car);

        b.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}

class SearchMain {
    public static void main(String[] args) {
        ParkingSearch win = new ParkingSearch();
        win.setSize(900, 600);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
}