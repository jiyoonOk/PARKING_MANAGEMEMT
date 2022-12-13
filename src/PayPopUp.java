import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;


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
            if(false) { //예약 TODO 추후 조건문 추가하기
                if (false) { //초과금액 결제 클래스
                    ExcessFeePay excessFee = null;
                    try {
                        excessFee = new ExcessFeePay("초과금액결제");
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
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
    //테스트용 상수 선언
    String test_name = "양지은";
    String test_carNum = "38너1849";
    String test_inTime = "2022-10-01 16:42:30";
    String test_outTime = "2022-10-01 17:42:30";
    int test_amount; //총금액
    int test_point = 5400; //보유적립금

    JLabel userTotal, userAddPoint;
    JTextField jtfPoint;
    int diffMin, totalAmount, savePoint, totalPoint; //시간계산용변수, 총결제금액, 예상적립금, 총적립금
    NormalPay(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        JLabel name = new JLabel("이        름 : ");       JLabel userName = new JLabel(test_name);
        JLabel carNum = new JLabel("차량번호 : ");          JLabel userCarNum = new JLabel(test_carNum);
        JLabel inTime = new JLabel("입차시간 : ");          JLabel userInTime = new JLabel(test_inTime);
        JLabel outTime = new JLabel("출차시간 : ");         JLabel userOutTime = new JLabel(test_outTime);
        JLabel amount = new JLabel("결제금액 : ");          JLabel userAmount = new JLabel(Integer.toString(test_amount));
        JLabel point = new JLabel("적립금사용: ");          jtfPoint = new JTextField("0");
        JLabel nowPoint = new JLabel("보유 적립금: ");      JLabel userPoint = new JLabel(Integer.toString(test_point));

        JLabel total = new JLabel("총 결제금액: ");         userTotal = new JLabel(Integer.toString(totalAmount));
        JLabel addPoint = new JLabel("예정 적립금: ");      userAddPoint = new JLabel(Integer.toString(savePoint));

        //메소드 이용
        try {diffMin = calculateTime(test_inTime, test_outTime);}
        catch (ParseException e) {throw new RuntimeException(e);}
        totalAmount = calculateFee(diffMin);
        savePoint = (int) (totalAmount * 0.05);//예정 적립금
        totalPoint = calculatePoint(totalAmount, jtfPoint.getText(), savePoint); //총적립금

        JButton btnPoint = new JButton("전액사용");
        btnPoint.addActionListener(this);
        jtfPoint.addActionListener(this);
        JButton btnPay = new JButton("결제");
        btnPay.addActionListener(this);
        RsvPopUp rsvPipUp = new RsvPopUp("정상 출차");

        name.setBounds(50,100,100,20);      userName.setBounds(120,100,100,20);
        carNum.setBounds(50,120,100,20);    userCarNum.setBounds(120,120,100,20);
        inTime.setBounds(50,140,100,20);    userInTime.setBounds(120,140,100,20);
        outTime.setBounds(50,160,100,20);   userOutTime.setBounds(120,160,100,20);
        amount.setBounds(50,180,100,20);    userAmount.setBounds(120,180,100,20);
        point.setBounds(50,200,100,20);     jtfPoint.setBounds(120,200,100,20);  btnPoint.setBounds(200,200,70,20);
        total.setBounds(50,260,100,20);     userTotal.setBounds(120,260,100,20);
        addPoint.setBounds(50,280,100,20);  userAddPoint.setBounds(120,280,100,20);

        btnPay.setBounds(100,300,100,20);

        ct.add(name);    ct.add(userName);
        ct.add(carNum);  ct.add(userCarNum);
        ct.add(inTime);  ct.add(userInTime);
        ct.add(outTime); ct.add(userOutTime);
        ct.add(amount);  ct.add(userAmount);
        ct.add(point);   ct.add(jtfPoint); ct.add(btnPoint);
        ct.add(total);  ct.add(userTotal);
        ct.add(addPoint);  ct.add(userAddPoint);
        ct.add(btnPay);
    }
    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        if (s == "결제") {
            new Thread() {
                public void run() {
                    for (int i = 0; i < 3; i++) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                        }
                        JOptionPane.getRootFrame().dispose();
                    }
                }
            }.start();
            JOptionPane.showOptionDialog(getParent(), "결제중...", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "결제되었습니다.");
        }
        else {
            if (s == "전액사용") jtfPoint.setText(Integer.toString(test_point));
            try {diffMin = calculateTime(test_inTime, test_outTime);}
            catch (ParseException e) {throw new RuntimeException(e);}
            int intPoint = Integer.parseInt(String.valueOf(jtfPoint));
            totalAmount = calculateFee(diffMin) - intPoint; //총 결제금액
            savePoint = (int) (totalAmount * 0.05);//예정 적립금
            totalPoint = calculatePoint(totalAmount, jtfPoint.getText(), savePoint); //총적립금

        }
     }
    public static int calculateTime(String inTime, String outTime) throws ParseException {
        Date format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inTime);
        Date format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(outTime);
        long diffMin = (format2.getTime() - format1.getTime()) / 60000; //분 차이
        return Math.toIntExact(diffMin);
    }
    public static int calculateFee(int Time) {
        int Total = (Time/10) * 500;
        return Total;
    }

    public static int calculatePoint(int total, String usepoint, int savePoint) {

        int Point = total - Integer.parseInt(usepoint)+ savePoint;   //총적립금

        return Point;
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
    String test_name = "양지은";
    String test_carNum= "38너1849";
    String test_outTime = "2022-12-15 14:00:00"; //출차예정시간
    LocalTime now = LocalTime.now();
    SimpleDateFormat formatNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String test_now = formatNow.format(now); //현재시간
    int diffMin, timeOutCost; //초과시간, 초과금액
    ExcessFeePay(String title) throws ParseException {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);

        diffMin = NormalPay.calculateTime(test_outTime, test_now);     //초과시간
        timeOutCost = NormalPay.calculateFee(diffMin);        //초과금액

        JLabel name = new JLabel("이        름 : ");            JLabel userName = new JLabel(test_name);
        JLabel carNum = new JLabel("차량번호 : ");               JLabel userCarNum = new JLabel(test_carNum);
        JLabel timeOut = new JLabel("초과시간 : ");              JLabel userTimeOut = new JLabel(Integer.toString(diffMin));

        JLabel timeOutFee = new JLabel("총 초과 금액 : ");      JLabel userTimeOutCost = new JLabel(Integer.toString(timeOutCost));

        JButton b = new JButton("결제");
        b.addActionListener(this);

        name.setBounds(50,100,100,20);           name.setBounds(120,100,100,20);
        carNum.setBounds(50,120,100,20);         carNum.setBounds(120,120,100,20);
        timeOut.setBounds(50,140,100,20);        timeOut.setBounds(120,140,100,20);
        timeOutFee.setBounds(100,200,300,20);
        b.setBounds(100,300,100,20);

        ct.add(name);            ct.add(userName);
        ct.add(carNum);          ct.add(userCarNum);
        ct.add(timeOut);         ct.add(userTimeOut);
        ct.add(timeOutFee);     ct.add(userTimeOutCost);
        ct.add(b);
    }

    public void actionPerformed(ActionEvent ae) { //결제 버튼 누르면 이벤트
        new Thread() {
            public void run() {
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    JOptionPane.getRootFrame().dispose();
                }
            }
        }.start();
        JOptionPane.showOptionDialog(getParent(), "결제중...", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "결제되었습니다.");
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