import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



class PopUp extends JFrame implements ActionListener {
    JButton b1, b2;
    PopUp() {
        Container ct = getContentPane();
        ct.setLayout(null);

        b1 = new JButton("정산");
        b1.setBounds(50,100,70,30);
        b2 = new JButton("요금표");
        b2.setBounds(150,100,100,30);
        ct.add(b1); ct.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("정산")){
            if(true) { //예약 TODO 추후 조건문 추가하기
                if (false) { //예약시간이 초과되었으면
                    BookTimeOut bto = new BookTimeOut("예약시간 초과이용");
                    bto.setSize(300,400);
                    bto.setVisible(true);
                }
                else {  //예약시간 정상 출차
                    Booked bd = new Booked("예약 주차 출차");
                    bd.setSize(300,400);
                    bd.setVisible(true);
                }
            }
            else { //그냥 출차
                Out out = new Out("주차 출차");
                out.setSize(300,400);
                out.setVisible(true);
            }
        }
        else {
            CostFrame cd = new CostFrame("요금표");
            cd.setSize(300,300);
            cd.setVisible(true);
        }
    }
}
class Out extends JFrame implements ActionListener{ // 예약 안 하고 그냥 출차. JDialog 클래스 객체 생성
    Out(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        //우선 상수 선언
        final String f_name = "양지은";
        final String f_carNum = "38너1849";
        final String f_inTime = "3시 30분";
        final String f_outTime = "6시";
        final String f_amount = "10000원";
        final String f_point = "5400원";

        JLabel l1_name = new JLabel("이        름 : ");
        JLabel l2_carNum = new JLabel("차량번호 : ");
        JLabel l3_inTime = new JLabel("입차시간 : ");
        JLabel l4_outTime = new JLabel("출차시간 : ");
        JLabel l5_amount = new JLabel("결제금액 : ");
        JLabel l6_point = new JLabel("적  립  금 : ");

        JLabel name = new JLabel(f_name);
        JLabel carNum = new JLabel(f_carNum);
        JLabel inTime = new JLabel(f_inTime);
        JLabel outTime = new JLabel(f_outTime);
        JLabel amount = new JLabel(f_amount);
        JLabel point = new JLabel(f_point);

        JButton b = new JButton("결제");
        b.addActionListener(this);
        l1_name.setBounds(50,100,100,20); name.setBounds(120,100,100,20);
        l2_carNum.setBounds(50,120,100,20); carNum.setBounds(120,120,100,20);
        l3_inTime.setBounds(50,140,100,20); inTime.setBounds(120,140,100,20);
        l4_outTime.setBounds(50,160,100,20); outTime.setBounds(120,160,100,20);
        l5_amount.setBounds(50,180,100,20); amount.setBounds(120,180,100,20);
        l6_point.setBounds(50,200,100,20); point.setBounds(120,200,100,20);
        b.setBounds(100,300,100,20);

        ct.add(l1_name); ct.add(name);
        ct.add(l2_carNum); ct.add(carNum);
        ct.add(l3_inTime); ct.add(inTime);
        ct.add(l4_outTime); ct.add(outTime);
        ct.add(l5_amount); ct.add(amount);
        ct.add(l6_point); ct.add(point);
        ct.add(b);

    }

    public void actionPerformed(ActionEvent ae) {
        JOptionPane.showOptionDialog(getParent(), "결제중...",null, JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
        try {
            Thread.sleep(1000);
            JOptionPane.showMessageDialog(getParent(),"결제완료",null,JOptionPane.INFORMATION_MESSAGE);
            Thread.sleep(200);
            dispose();
        } catch (InterruptedException e) {}
    //TODO 결제중 팝업 오류 (결제중->3초 후->결제완료 떠야 하는데 결제중 x버튼 눌러야 3초 후 결제완료로 전환됨)
     }
}
class Booked extends JFrame implements ActionListener {// 예약 하고 출차. JDialog 클래스 객체 생성
    Booked(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        final String f_name = "양지은";
        final String f_carNum = "38너1849";

        JLabel l1_name = new JLabel("이        름 : ");
        JLabel l2_carNum = new JLabel("차량번호 : ");
        JLabel l3_str = new JLabel("정상 출차 되었습니다");
        JLabel name = new JLabel(f_name);
        JLabel carNum = new JLabel(f_carNum);

        JButton b = new JButton("확인");
        b.addActionListener(this);

        l1_name.setBounds(50,100,100,20); name.setBounds(120,100,100,20);
        l2_carNum.setBounds(50,120,100,20); carNum.setBounds(120,120,100,20);
        l3_str.setBounds(50,200,300,20);
        b.setBounds(100,300,100,20);
        ct.add(l1_name); ct.add(name);
        ct.add(l2_carNum); ct.add(carNum);
        ct.add(l3_str);
        ct.add(b);
    }
    public void actionPerformed(ActionEvent ae) { //확인 버튼 누르면 창 닫기
        dispose();
    }
}
class BookTimeOut extends JFrame implements ActionListener {// 예약했는데 시간초과. JDialog 클래스 객체 생성
    BookTimeOut(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        final String f_name = "양지은";
        final String f_carNum = "38너1849";
        final String f_timeOut = "3시 30분";
        final String f_amount = "10000";
        final String f_timeOutCost = "5400원";

        JLabel l1_name = new JLabel("이        름 : ");
        JLabel l2_carNum = new JLabel("차량번호 : ");
        JLabel l3_timeOut = new JLabel("초과시간 : ");
        JLabel l4_str = new JLabel("원 추가금액 발생했습니다.");
        JLabel name = new JLabel(f_name);
        JLabel carNum = new JLabel(f_carNum);
        JLabel timeOut = new JLabel(f_timeOut);
        JLabel amount = new JLabel(f_amount);
        JLabel timeOutCost = new JLabel(f_timeOutCost);

        JButton b = new JButton("결제");

        l1_name.setBounds(50,100,100,20); name.setBounds(120,100,100,20);
        l2_carNum.setBounds(50,120,100,20); carNum.setBounds(120,120,100,20);
        l3_timeOut.setBounds(50,140,100,20); timeOut.setBounds(120,140,100,20);
        amount.setBounds(50,200,300,20); l4_str.setBounds(100,200,300,20);
        b.setBounds(100,300,100,20);
        ct.add(l1_name); ct.add(name);
        ct.add(l2_carNum); ct.add(carNum);
        ct.add(l3_timeOut); ct.add(timeOut);
        ct.add(amount); ct.add(l4_str);
        ct.add(b);

        b.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) { //결제 버튼 누르면 이벤트
        JOptionPane.showOptionDialog(getParent(), "결제중...",null, JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
        try {
            Thread.sleep(1000);
            JOptionPane.showMessageDialog(getParent(),"결제완료",null,JOptionPane.INFORMATION_MESSAGE);
            Thread.sleep(200);
            dispose();
        } catch (InterruptedException e) {}
        //TODO 결제중 팝업 오류 수정 (결제중->3초 후->결제완료 떠야 하는데 결제중 x버튼 눌러야지만 3초 후 결제완료로 전환됨)

    }
}
class CostFrame extends JFrame{       //요금표 Dialog 클래스 객체 생성
    CostFrame(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        // JTable table = new JTable(cost);

        JLabel min = new JLabel("10분");
        JLabel min_cost = new JLabel("1000원");
        JLabel hour = new JLabel("1시간");
        JLabel hour_cost = new JLabel("3000원");
        JLabel notice1 = new JLabel("· 예약 회원은 시간당 예약 가능");
        JLabel notice2 = new JLabel("· 예약자는 선결제 시 10% 할인");
        JLabel notice3 = new JLabel("· 추가요금 할인 불가");
        JLabel notice4 = new JLabel("· 결제 금액의 3% 적립");

        min.setBounds(80,50,100,20); min_cost.setBounds(150,50,100,20);
        hour.setBounds(80,80,100,20); hour_cost.setBounds(150,80,100,20);
        notice1.setBounds(50,140,200,20);
        notice2.setBounds(50,160,200,20);
        notice3.setBounds(50,180,200,20);
        notice4.setBounds(50,200,200,20);
        ct.add(min); ct.add(min_cost);
        ct.add(hour); ct.add(hour_cost);
        ct.add(notice1); ct.add(notice2);
        ct.add(notice3); ct.add(notice4);
    }
}

class PopUpMain {
        public static void main(String[] args) {
        PopUp win = new PopUp();
        win.setSize(300, 300);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
}