import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//주차 조회 클래스

public class Inquire extends JFrame implements ActionListener {
    //TODO 클래스 합치고나서 생성자 어떻게 할건지?
    JButton b;
    JPanel rsvPanel, normalPanel;
    Inquire() {
        Container ct = getContentPane();
        ct.setLayout(null);
        boolean is_reserved = true; //주차 예약 유무. 임시로 true 해놓음
        rsvPanel = new JPanel();
        normalPanel = new JPanel();
        JButton b = new JButton("확인");
        b.addActionListener(this);

        //테스트용 사용자 설정값
        JLabel userName   = new JLabel("양지은");
        JLabel userId     = new JLabel("jieunyang");
        JLabel userCarNum = new JLabel("123나1234");
        JLabel userFloor  = new JLabel("B1");
        JLabel userArea   = new JLabel("A4");
        JLabel userInTime = new JLabel("2022-12-15 12:00:00"); //입차시간
        JLabel userOutTime = new JLabel("2022-12-17 17:00:00"); //출차예정시간

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
        carPanel.setLayout(new GridLayout(6, 1));
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
        for (int j = 0; j < 2; j++)  {
            btn[j] = new JButton(parkingLot[j], carIcons[1]); //A1, A2
            if(parkingLot[j].equals(userArea.getText())) btn[j].setIcon(carIcons[4]);
        }
        for (int j = 2; j < 4; j++)  {
            btn[j] = new JButton(parkingLot[j], carIcons[2]); //A3, A4
            if(parkingLot[j].equals(userArea.getText())) btn[j].setIcon(carIcons[4]);
        }
        for (int j = 4; j < 12; j++) {
            btn[j] = new JButton(parkingLot[j]);              //B, C열
            if(parkingLot[j].equals(userArea.getText())) btn[j].setIcon(carIcons[4]);
        }
        for (int j = 12; j < 16; j++) {
            btn[j] = new JButton(parkingLot[j], carIcons[3]); //D열
            if(parkingLot[j].equals(userArea.getText())) btn[j].setIcon(carIcons[4]);
        }

        for (int j = 0; j < parkingLot.length; j++) {
            btn[j].setOpaque(false);            //이미지 외 영역 투명하게
            btn[j].setBackground(Color.WHITE);  //흰색 배경
            btn[j].setContentAreaFilled(false); //내용 영역 채우기 없애기
            btn[j].setForeground(Color.RED);    //글자색상 변경
            btn[j].setHorizontalTextPosition(JButton.CENTER); //텍스트 가운데 정렬
            if(!parkingLot[j].equals(userArea.getText())) btn[j].setEnabled(false); //텍스트 가운데 정렬
        }

        //초기값으로 Panel P에 B1층 주차구역 버튼들 추가
        for (int i = 0; i < 4; i++) p[0].add(btn[i]); //A열
        for (int i = 4; i < 8; i++) p[2].add(btn[i]); //B열
        for (int i = 8; i < 12; i++) p[3].add(btn[i]); //C열
        for (int i = 12; i < 16; i++) p[5].add(btn[i]); //D열

        //주차 조회 패널 만들기
        normalPanel.setLayout(new GridLayout(7, 1));

        JPanel p2[] = new JPanel[6];
        for (int i = 0; i < p2.length; i++){
            normalPanel.add(p2[i] = new JPanel());
            p2[i].setLayout(new FlowLayout(FlowLayout.LEFT));
        }
        JLabel normaltitle = new JLabel("주차 일반 조회");
        normaltitle.setFont(new Font("D2Coding", Font.BOLD, 20));
        normaltitle.setFont(normaltitle.getFont().deriveFont(15.0f));
        JLabel rsvtitle = new JLabel("주차 예약 조회");
        rsvtitle.setFont(new Font("D2Coding", Font.BOLD, 20));
        JLabel name = new JLabel("이        름 : ");
        JLabel id = new JLabel("아 이 디 : ");
        JLabel carNum = new JLabel("차량번호 : ");
        JLabel area = new JLabel("주차구역 : ");
        JLabel inTime = new JLabel("입차시간 : ");
        JLabel OutTime = new JLabel("출차예정시간 : ");

        p2[1].add(name);   p2[1].add(userName);
        p2[2].add(id);     p2[2].add(userId);
        p2[3].add(carNum); p2[3].add(userCarNum);
        p2[4].add(area);   p2[4].add(userFloor);   p2[4].add(userArea);

        //예약 유무에 따른 패널 출력값 설정
        if (is_reserved == true) {
            p2[0].add(rsvtitle);
            p2[5].add(inTime); p2[5].add(userInTime);
            ct.add(normalPanel);
        } else {
            p2[0].add(normaltitle);
            p2[5].add(OutTime); p2[5].add(userOutTime);
            ct.add(rsvPanel);
        }

        carPanel.setBounds(20, 100, 500, 400);
        normalPanel.setBounds(600, 100, 500, 400);
        rsvPanel.setBounds(600, 100, 500, 400);
        b.setBounds(650, 450, 100, 40);
        ct.add(carPanel);
        ct.add(b);
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}
class InquireMain {
    public static void main(String[] args) {
        Inquire win = new Inquire();
        win.setSize(900, 600);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
        win.setLocationRelativeTo(null);
    }
}