package UserMain;

import javax.swing.*;
import java.awt.*;

// TODO @ 22# 포인트 적립률 수정함
public class CostFrame extends JFrame {       //요금표 popup 클래스 객체 생성
    public CostFrame(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        // JTable table = new JTable(cost);

        JLabel min = new JLabel("10분");
        JLabel min_cost = new JLabel("1000원");
        JLabel hour = new JLabel("1시간");
        JLabel hour_cost = new JLabel("3000원");
        JLabel notice1 = new JLabel("· 예약 회원은 시간당 예약 가능(최대 3시간)");
        JLabel notice2 = new JLabel("· 예약자는 선결제 시 10% 할인");
        JLabel notice3 = new JLabel("· 추가요금 할인 불가");
        JLabel notice4 = new JLabel("· 결제 금액의 5% 포인트 적립");

        min.setBounds(80, 50, 100, 20);
        min_cost.setBounds(150, 50, 100, 20);
        hour.setBounds(80, 80, 100, 20);
        hour_cost.setBounds(150, 80, 100, 20);
        notice1.setBounds(50, 140, 200, 20);
        notice2.setBounds(50, 160, 200, 20);
        notice3.setBounds(50, 180, 200, 20);
        notice4.setBounds(50, 200, 200, 20);
        ct.add(min);
        ct.add(min_cost);
        ct.add(hour);
        ct.add(hour_cost);
        ct.add(notice1);
        ct.add(notice2);
        ct.add(notice3);
        ct.add(notice4);
    }

}
