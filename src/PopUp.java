import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



class Pay extends JFrame implements ActionListener {
    Pay() {
        Container ct = getContentPane();
        ct.setLayout(null);

        JButton b = new JButton("정산");
        b.setBounds(50,100,70,30);
        ct.add(b);

        b.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("정산")){
            if(true) { //예약 TODO 추후 조건문 추가하기
                if (false) { //초과금액 결제 클래스
                    ExcessFeePay excessFee = new ExcessFeePay("초과금액결제");
                    excessFee.setSize(500,400);
                    excessFee.setVisible(true);
                }
                else {  //예약 정상 출차 클래스
                    RsvPopUp rsvPipUp = new RsvPopUp("정상 출차");
                    rsvPipUp.setSize(500,400);
                    rsvPipUp.setVisible(true);
                }
            }
            else { //일반 출차 결제 클래스
                NormalPay nomal = new NormalPay("출차 결제");
                nomal.setSize(500,400);
                nomal.setVisible(true);
            }
        }

    }
}

class NormalPay extends JFrame implements ActionListener{
    NormalPay(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        //테스트용 상수 선언
        String f_name = "양지은";
        String f_carNum = "38너1849";
        String f_inTime = "3시 30분";
        String f_outTime = "6시";
        String f_amount = "10000원";
        String f_point = "5400원";

        JLabel name = new JLabel("이        름 : ");
        JLabel carNum = new JLabel("차량번호 : ");
        JLabel inTime = new JLabel("입차시간 : ");
        JLabel outTime = new JLabel("출차시간 : ");
        JLabel amount = new JLabel("결제금액 : ");
        JLabel point = new JLabel("적립금사용: ");

        JLabel userName = new JLabel(f_name);
        JLabel userCarNum = new JLabel(f_carNum);
        JLabel userInTime = new JLabel(f_inTime);
        JLabel userOutTime = new JLabel(f_outTime);
        JLabel userAmount = new JLabel(f_amount);
        JTextField userPoint = new JTextField();

        JButton btnPoint = new JButton("전액사용");
        JButton btnPay = new JButton("결제");
        btnPoint.addActionListener(this);
        btnPay.addActionListener(this);

        name.setBounds(50,100,100,20);    userName.setBounds(120,100,100,20);
        carNum.setBounds(50,120,100,20);  userCarNum.setBounds(120,120,100,20);
        inTime.setBounds(50,140,100,20);  userInTime.setBounds(120,140,100,20);
        outTime.setBounds(50,160,100,20); userOutTime.setBounds(120,160,100,20);
        amount.setBounds(50,180,100,20);  userAmount.setBounds(120,180,100,20);
        point.setBounds(50,200,100,20);   userPoint.setBounds(120,200,100,20);  btnPoint.setBounds(200,200,70,20);

        btnPay.setBounds(100,300,100,20);

        ct.add(name);    ct.add( userName);
        ct.add(carNum);  ct.add( userCarNum);
        ct.add(inTime);  ct.add( userInTime);
        ct.add(outTime); ct.add( userOutTime);
        ct.add(amount);  ct.add( userAmount);
        ct.add(point);   ct.add( userPoint); ct.add(btnPoint);
        ct.add(btnPay);
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
class RsvPopUp extends JFrame implements ActionListener {// 예약 하고 출차. JDialog 클래스 객체 생성
    RsvPopUp(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        String f_name = "양지은";
        String f_carNum = "38너1849";

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
class ExcessFeePay extends JFrame implements ActionListener {// 예약했는데 시간초과. JDialog 클래스 객체 생성
    ExcessFeePay(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        String f_name = "양지은";
        String f_carNum = "38너1849";
        String f_timeOut = "3시 30분";
        String f_amount = "10000";
        String f_timeOutCost = "5400원";

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

class PayMain {
        public static void main(String[] args) {
            Pay win = new Pay();
        win.setSize(300, 300);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
}