package UserMain;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;


public class Rsv extends JFrame {

    String month;
    String[] date, hour, min, time; //시간선택
    JComboBox monthComBox, dateComBox, hourComBox, minComBox, timeComBox; //월일시분 시간선택
    JTextField inputPoint_JTF; //사용할 포인트 입력
    public int myPoint_int, usePoint_int=0, paidMoney_int=0; //보유 포인트 값, 사용할 포인트 값, 결제금액
    JButton usePointBtn, useAllPointBtn, rsvBtn, cancelBtn; //포인트 사용, 전액사용, 예약, 취소 버튼



    public Rsv(String t) {
        super(t);

        LocalDate now = LocalDate.now();

        //TODO #### 시간 선택 좀 어떻게 해봐.. 꼬여서 헷갈리네
        month = String.valueOf(now.getMonth().getValue());
        monthComBox = new JComboBox();
        monthComBox.addItem(month); //월 콤보박스 생성
        dateComBox = new JComboBox();
        monthComBox.addActionListener(new ActionListener() { //월 선택 시
            @Override
            public void actionPerformed(ActionEvent e) {
                int currDate = now.getDayOfMonth(); //현재 일
                int lastDate = now.lengthOfMonth(); //이번달 말일
                int amount = lastDate -  currDate +1; //선택 가능한 일수
                date = new String[amount]; //오늘부터 말일까지
                for (int i=0; i<amount; i++) {
                    date[i] = String.valueOf(currDate+i);
                }
                dateComBox.addItem(date); //날짜 콤보박스 생성
            }
        }); //월선택 이벤트 끝

        dateComBox.addActionListener(new ActionListener() { //일 선택 시
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO #### 이거 필요 없을 듯? 예약 버튼 누르면 .getSelectedItem() 으로 값 가져오기
            }
        }); //날짜선택 이벤트 끝

        hour = new String[24];
        for(int i = 0; i<24; i++) { //1~12 저장
            if (i<9) hour[i] = "0"+(i+1); //00으로 나오게 하기
            else hour[i] = String.valueOf(i+1);
        }
        hourComBox = new JComboBox(hour); //시간 콤보박스 생성

        inputPoint_JTF = new JTextField();
        min = new String[6];
        for(int i = 0; i<6; i++) { //1~12 저장
            if (i == 0) min[i] = "00"; //00으로 나오게 하기
            else min[i] = String.valueOf((i+1)*10);
        }
        minComBox = new JComboBox(min); //분 콤보박스 생성

        time = new String[]{"1시간", "2시간", "3시간"};
        timeComBox = new JComboBox(time);
        JLabel price = new JLabel("선결제금액 : 0원");
        timeComBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeComBox.getSelectedItem().equals("1시간")) {
                    paidMoney_int = 2700;
                } else if (timeComBox.getSelectedItem().equals("2시간")) {
                    paidMoney_int = 5400;
                } else if (timeComBox.getSelectedItem().equals("3시간")) {
                    paidMoney_int = 8100;
                }
                price.setText("선결제금액: "+paidMoney_int+"원");
            }
        });//사용시간선택 이벤트 끝



        myPoint_int=10000;
        //TODO DB : 포인트 가져와서 초기화하기
        inputPoint_JTF = new JTextField(8);      //사용할 포인트 입력
        usePointBtn = new JButton("사용");            //사용 버튼
        useAllPointBtn = new JButton("전액사용");
        usePointBtn.addActionListener(new ActionListener() { //포인트 사용 버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = Integer.parseInt(inputPoint_JTF.getText());
                if (i <= myPoint_int){ //보유 포인트 내에서 사용하는지 확인
                    if (i > paidMoney_int) {
                        JOptionPane.showMessageDialog(null, "결제 금액을 초과하여 쓸 수 없습니다.");
                    }
                    usePoint_int = i; // 확인 후 저장
                    paidMoney_int -= usePoint_int; //결제금액에서 포인트금액만큼 빼기
                    price.setText("선결제금액: "+paidMoney_int+"원");
                } else { //보유 포인트 범위 벗어남
                    JOptionPane.showMessageDialog(null, "보유 포인트를 확인해주세요");
                }
            }
        });//포인트사용버튼 이벤트 끝

        rsvBtn = new JButton("예약하기");
        rsvBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "예약 하시겠습니까?\n(확인 시 결제로 넘어감)", "주차예약", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) { //결제 yes 한 경우
                    //TODO !# 결제창으로 넘어가기
                    //TODO DB : 결제정보 넘기기
                    // .getSelectedItem() (선택한 연월일시)
                    // myPoint_int - usePoint_int (잔여포인트)
                    // paidMoney_int (결제금액)
                    JOptionPane.showMessageDialog(null, "결제가 완료되었습니다!");
                    dispose();
                }
                else { //결제 no 한 경우

                }
            }
        });//예약버튼 이벤트 끝

        cancelBtn = new JButton("취소");
        cancelBtn.addActionListener(new ActionListener() { //취소 버튼 클릭 시
            @Override
            public void actionPerformed(ActionEvent e) { //창 닫기
                JOptionPane.showMessageDialog(null, "취소 되었습니다!");
                dispose();
            }
        });//취소버튼 이벤트 끝




        JPanel panel = new JPanel();            //CENTER - 임시 주차장
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JPanel top_pnl = new JPanel();          //NORTH - 층선택 ComBox 들어감
        top_pnl.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel bottom_pnl = new JPanel();       //SOUTH - 확인취소 버튼
        bottom_pnl.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel rsv_pnl = new JPanel();          //EAST - 나머지 것들
        rsv_pnl.setLayout(new GridLayout(4,1));



        JPanel p1 = new JPanel();       //rsv_pnl 첫번째 칸
        p1.setLayout(new GridLayout(4,2));
        //TODO DB : 이름, 차량번호 가져오기
        JLabel name_JLabel = new JLabel("이름: ");        //이름:  -(1,1)
        JLabel name = new JLabel("이다은");               //이름 -(1,2)
        JLabel carNum_JLabel = new JLabel("차량번호: ");   //차량번호 -(2,1)
        JLabel carNum = new JLabel("12오4453");           //차량번호 -(2,2)
        JLabel location_JLabel = new JLabel("주차구역: "); //주차선택 -(3,1)
        JLabel choose_location = new JLabel("층" + "구역");    //층, 구역 -(3,2)
        JLabel rsv = new JLabel("예약일시: ");      //시간선택 -(4,1)

        p1.add(name_JLabel);   p1.add(name);
        p1.add(carNum_JLabel);  p1.add(carNum);
        p1.add(location_JLabel); p1.add(choose_location);
        p1.add(rsv);


        JPanel p2 = new JPanel();       //rsv_pnl 두번쨰 칸
        p2.setLayout(new GridLayout(3,4));
        JLabel month = new JLabel("월");           //월 -(1,2)
        JLabel date = new JLabel("일");            //일 -(1,4)
        JLabel hour = new JLabel("시");            //시 -(2,2)
        JLabel minute = new JLabel("분");          //분 -(2,4)

        p2.add(monthComBox);    p2.add(month);      p2.add(dateComBox);     p2.add(date);
        p2.add(hourComBox);     p2.add(hour);       p2.add(minComBox);      p2.add(minute);


        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(4,2));
        JLabel time = new JLabel("이용시간: ");     //이용시간 선택 -(1,1)
        JLabel point = new JLabel("포인트 사용: "); //포인트 -(2,1)
        JLabel showMyPoint = new JLabel("보유 포인트: " + myPoint_int);   //보유(사용가능) 포인트 출력 -(4,1)
        showMyPoint.setForeground(Color.RED);

        p3.add(time);   p3.add(timeComBox);
        p3.add(point);  p3.add(new JLabel(""));
        p3.add(inputPoint_JTF); p3.add(usePointBtn); p3.add(useAllPointBtn);
        p3.add(showMyPoint);


        JPanel p4 = new JPanel();
        p4.setLayout(new GridLayout(2,1));
        //price 는 미리 정의
        JLabel plus_point = new JLabel("( [결제금액 * 3%] point 적립 예정)"); //-(2)

        p4.add(price);  p4.add(plus_point);



        rsv_pnl.add(p1);
        rsv_pnl.add(p2);
        rsv_pnl.add(p3);
        rsv_pnl.add(p4);

        bottom_pnl.add(rsvBtn); bottom_pnl.add(cancelBtn); //예약, 취소 버튼

/*
        top_pnl.add(floorCBox);
        TODO !# ComboBox 추가하기
 */

        Container rsvCt = getContentPane();
        rsvCt.add(panel, BorderLayout.CENTER);
        rsvCt.add(top_pnl, BorderLayout.NORTH);
        rsvCt.add(bottom_pnl, BorderLayout.SOUTH);
        rsvCt.add(rsv_pnl, BorderLayout.EAST);

    }//Rsv 생성자 끝

}//Rsv 클래스 끝

class RsvMain extends JFrame{
    public static void main(String[] args) {
        Rsv m = new Rsv("주차 프로그램 - 주차예약");
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(1000, 600);
        m.setVisible(true);
    }

}