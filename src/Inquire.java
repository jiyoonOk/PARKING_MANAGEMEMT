import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* 주차 일반조회, 예약조회 클래스 */

public class Inquire extends JFrame {
    //TODO 클래스 합치고나서 생성자 어떻게 할건지?
    //JLabel userName, userId, userCarNum, userFloor, userArea, userInTime;
    Inquire(JLabel t_userName, JLabel t_userId, JLabel t_userCarNum, JLabel t_userFloor, JLabel t_userArea, JLabel t_userInTime) {
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout());
        boolean is_reserved = true; //주차 예약 유무. 임시로 true 해놓음
        JLabel userName = t_userName;
        JLabel userId = t_userName;
        JLabel userCarNum = t_userName;
        JLabel userFloor = t_userName;
        JLabel userArea = t_userName;
        JLabel userInTime = t_userName;

        //주차장 패널 (조최용)
        JButton[] btn = new JButton[16]; //구역 버튼
        ImageIcon[] carIcons = {
            new ImageIcon("images/Car.jpg"), //일반, checked
            new ImageIcon("images/woman.jpg"), //여성
            new ImageIcon("images/disabled.jpg"), //장애인
            new ImageIcon("images/smallCar.jpg"), //경차
            new ImageIcon("images/checkedWoman.jpg"), // 여성, checked
            new ImageIcon("images/checkedDisabled.jpg"), //장애인, checked
            new ImageIcon("images/checkedSmallCar.jpg") //경차, checked
        };

        JPanel carPanel = new JPanel();
        carPanel.setLayout(new GridLayout(6,1));
        JPanel p[] = new JPanel[6];
        for (int i = 0; i < p.length; i++)
            carPanel.add(p[i] = new JPanel());

        p[0].setLayout(new GridLayout(1, 2)); //A열
        p[1].setBackground(Color.LIGHT_GRAY); //통로
        p[2].setLayout(new GridLayout(1, 2)); //B열
        p[3].setLayout(new GridLayout(1, 2)); //C열
        p[4].setBackground(Color.LIGHT_GRAY); //통로
        p[5].setLayout(new GridLayout(1, 2)); //D열

        //주차구역 버튼 꾸미기
        String[] parkingLot = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4", "D1", "D2", "D3", "D4"}; //주차구역

        //선택된 주차구역 제외한 버튼아이콘 초기화
        for (int j=0; j<2; j++)   btn[j] = new JButton(parkingLot[j], carIcons[1]); //A1, A2
        for (int j=2; j<4; j++)   btn[j] = new JButton(parkingLot[j], carIcons[2]); //A3, A4
        for (int j=4; j<12; j++)  btn[j] = new JButton(parkingLot[j]);              //B, C열
        for (int j=12; j<16; j++) btn[j] = new JButton(parkingLot[j], carIcons[3]); //D열

        for (int j = 0; j < parkingLot.length; j++) {
            btn[j].setOpaque(false);            //이미지 외 영역 투명하게
            btn[j].setBackground(Color.WHITE);  //흰색 배경
            btn[j].setContentAreaFilled(false); //내용 영역 채우기 없애기
            btn[j].setForeground(Color.RED);    //글자색상 변경
            btn[j].setHorizontalTextPosition(JButton.CENTER); //텍스트 가운데 정렬
        }

        //초기값으로 Panel P에 B1층 주차구역 버튼들 추가
        for (int i = 0; i < 4; i++)     p[0].add(btn[i]); //A열
        for (int i = 4; i < 8; i++)     p[2].add(btn[i]); //B열
        for (int i = 8; i < 12; i++)    p[3].add(btn[i]); //C열
        for (int i = 12; i < 16; i++)   p[5].add(btn[i]); //D열

        ct.add(carPanel, BorderLayout.CENTER);

        //boolean is_reserved 예약 유무
        if (is_reserved) { //예약
            JPanel rsvPanel = new JPanel();
            ReservationInquiry rsv = new ReservationInquiry(rsvPanel, userName, userId, userCarNum, userFloor, userArea, userInTime);
            ct.add(rsvPanel, BorderLayout.EAST);
        } else { //일반
            JPanel gPanel = new JPanel();
            GeneralInquiry general = new GeneralInquiry(gPanel, userName, userId, userCarNum, userFloor, userArea, userInTime);
            ct.add(gPanel, BorderLayout.EAST);
        }
    }
}

//주차 일반 조회
class GeneralInquiry extends JFrame implements ActionListener {
    //JLabel userName, userId, userCarNum, userFloor, userArea, userInTime;
    GeneralInquiry(JPanel gPanel, JLabel t_userName, JLabel t_userId, JLabel t_userCarNum, JLabel t_userFloor, JLabel t_userArea, JLabel t_userInTime) {
        Container ct = getContentPane();
        JPanel inquire = gPanel;
        inquire.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        JLabel title = new JLabel("주차 일반 조회");
        top.add(title);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(5, 1));
        JPanel p[] = new JPanel[5];
        for (int i = 0; i < p.length; i++)
            center.add(p[i] = new JPanel());
        JLabel name = new JLabel("이        름 : ");
        JLabel id = new JLabel("아 이 디 : ");
        JLabel carNum = new JLabel("차량번호 : ");
        JLabel area = new JLabel("주차구역 : ");
        JLabel inTime = new JLabel("입차시간 : ");
        /*
        JLabel userId = new JLabel();
        JLabel userCarNum = new JLabel();
        JLabel userFloor = new JLabel();
        JLabel userArea = new JLabel();
        JLabel userInTime = new JLabel();
         */
        p[0].add(name);     p[0].add(t_userName);
        p[1].add(id);       p[1].add(t_userId);
        p[2].add(carNum);   p[2].add(t_userCarNum);
        p[3].add(area);     p[3].add(t_userFloor); add(t_userArea);
        p[4].add(inTime);   p[4].add(t_userInTime);

        JPanel bottom = new JPanel();
        JButton b = new JButton("확인");
        bottom.add(b);

        inquire.add(top);
        inquire.add(center);
        inquire.add(bottom);

        ct.add(inquire);
        b.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}


//주차 예약 조회
class ReservationInquiry extends JFrame implements ActionListener {
    //JLabel userName, userId, userCarNum, userFloor, userArea, userInTime;
    ReservationInquiry(JPanel rsvPanel, JLabel t_userName, JLabel t_userId, JLabel t_userCarNum, JLabel t_userFloor, JLabel t_userArea, JLabel t_userInTime) {
        Container ct = getContentPane();
        JPanel inquire = rsvPanel;
        inquire.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        JLabel title = new JLabel("주차 일반 조회");
        top.add(title);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(5, 1));
        JPanel p[] = new JPanel[5];
        for (int i = 0; i < p.length; i++)
            center.add(p[i] = new JPanel());
        JLabel name = new JLabel("이        름 : ");
        JLabel id = new JLabel("아 이 디 : ");
        JLabel carNum = new JLabel("차량번호 : ");
        JLabel area = new JLabel("주차구역 : ");
        JLabel inTime = new JLabel("입차시간 : ");
        /*
        JLabel userId = new JLabel();
        JLabel userCarNum = new JLabel();
        JLabel userFloor = new JLabel();
        JLabel userArea = new JLabel();
        JLabel userInTime = new JLabel();
         */
        p[0].add(name);     p[0].add(t_userName);
        p[1].add(id);       p[1].add(t_userId);
        p[2].add(carNum);   p[2].add(t_userCarNum);
        p[3].add(area);     p[3].add(t_userFloor); add(t_userArea);
        p[4].add(inTime);   p[4].add(t_userInTime);

        JPanel bottom = new JPanel();
        JButton b = new JButton("확인");
        bottom.add(b);

        inquire.add(top);
        inquire.add(center);
        inquire.add(bottom);

        ct.add(inquire);
        b.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}

class InquireMain {
    public static void main(String[] args) {

        //테스트용 사용자 설정값
        JLabel t_userName   = new JLabel("양지은");
        JLabel t_userId     = new JLabel("jieunyang");
        JLabel t_userCarNum = new JLabel("123나1234");
        JLabel t_userFloor  = new JLabel("B1");
        JLabel t_userArea   = new JLabel("A4");
        JLabel t_userInTime = new JLabel("2022-12-15 12:00:00"); //입차시간
        JLabel t_userOutTime = new JLabel("2022-12-17 17:00:00"); //출차예정시간

        //사용자가 조회 버튼 클릭했을 때 코드 구현을 여기서부터 합치면 됨
        Inquire win = new Inquire(t_userName, t_userId, t_userCarNum, t_userFloor, t_userArea, t_userInTime); //예약
        // Inquire win = new Inquire(t_userName, t_userId, t_userCarNum, t_userFloor, t_userArea, t_userInTime); //일반
        win.setSize(900, 600);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
        win.setLocationRelativeTo(null);
    }
}