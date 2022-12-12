package UserMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

public class Park extends JFrame {

    Container ct;

    JButton parkingButton = new JButton("완료");       //주차완료버튼
    JButton cancelButton = new JButton("취소");   //취소버튼

    public Park(String t) {
        super(t);



        JPanel panel = new JPanel();            //CENTER - 임시 주차장
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JPanel top_pnl = new JPanel();          //NORTH - 층선택 ComBox 들어감
        top_pnl.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel bottom_pnl = new JPanel();       //SOUTH - 확인취소 버튼
        bottom_pnl.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel park_pnl = new JPanel();          //EAST - 나머지 것들
        park_pnl.setLayout(new GridLayout(6,2));


        JLabel name = new JLabel("이름: ");              //이름
        JLabel carNum = new JLabel("차량번호: ");         //차량번호
        JLabel currTime = new JLabel("현재시간 : ");      //현재시간
        LocalTime now = LocalTime.now();
        JLabel time = new JLabel(now.getHour()+"시"+now.getMinute()+"분");
        JLabel location = new JLabel("주차구역: ");       //주차구역
        JLabel park_location = new JLabel(" 층"+" 구역");

        parkingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "주차 되었습니다!");
                dispose();
            }
        });
        /*TODO : 예약과 비교했을 떄,
            db에 입력하는 유형이 다르면 익명리스너로 생성.
           .                  같으면 공통된 액션리스너 클래스 생성.
         */
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        park_pnl.add(name);     park_pnl.add(new JLabel(""));
        park_pnl.add(carNum);   park_pnl.add(new JLabel(""));
        park_pnl.add(currTime); park_pnl.add(time);
        park_pnl.add(location); park_pnl.add(park_location);

        bottom_pnl.add(parkingButton);   bottom_pnl.add(cancelButton); //완료, 취소 버튼


        ct = getContentPane();
        ct.add(panel, BorderLayout.CENTER);
        ct.add(top_pnl, BorderLayout.NORTH);
        ct.add(bottom_pnl, BorderLayout.SOUTH);
        ct.add(park_pnl, BorderLayout.EAST);


    }//Park 생성자 끝

}//Park 클래스 끝

class ParkMain extends JFrame{
    public static void main(String[] args) {
        Park m = new Park("주차 프로그램 - 일반주차");
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(1000, 600);
        m.setVisible(true);
        m.setLocationRelativeTo(null);
    }

}