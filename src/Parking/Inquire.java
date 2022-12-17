package Parking;

import Pay.DBconnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


// TODO 221217 03:52 SQL문 수정
// TODO 221217 15:10 (다은) 일반출차 수정

public class Inquire extends JFrame implements ActionListener {
    public JLabel userNum;
    public Component floor;
    public Component car;
    JPanel searchPanel;
    JLabel userName = new JLabel(), userId = new JLabel(), userCarNum = new JLabel();
    JLabel userFloor = new JLabel(), userArea = new JLabel(), userInTime = new JLabel(), userRsvOutTime = new JLabel();
    String userTotalFee;
    boolean isReserved;

    public Inquire(String Id)  {
        Container ct = getContentPane();
        ct.setLayout(null);
        boolean is_reserved = false; //주차 예약 유무. 임시로 false 해놓음
        searchPanel = new JPanel();


        String url = "jdbc:mysql://localhost:3306/parking?serverTimezone=UTC";
        String user = "root";
        String password = "root";

        try { //mysql의 jdbc Driver 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
        } catch (ClassNotFoundException e) {
            System.err.println("드라이버 로드에 실패했습니다.");
        }
        try { //내가 mysql에 만든 student 데이터베이스 연결
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement(); //질의어 생성해서 적용
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

            String strSql, checkReservedSql;

            strSql = "SELECT user.name, purchase.user_id, user.car_num, purchase.floor_num, purchase.area, purchase.car_in, purchase.car_out FROM purchase, user WHERE purchase.user_id='"+Id+"' and user.id='"+Id+"';";
            ResultSet result = dbSt.executeQuery(strSql); //DB로부터 읽어온 레코드 객체화


            while(result.next()) {
                userName   = new JLabel(result.getString("name"));
                userCarNum = new JLabel(result.getString("user.car_num"));
                userFloor  = new JLabel("B"+result.getString("purchase.floor_num"));
                userArea   = new JLabel(result.getString("purchase.area"));
                userInTime = new JLabel(result.getString("purchase.car_in")); //입차시간
                userRsvOutTime = new JLabel(result.getString("purchase.car_out")); //예약-출차예정시간
            }

            dbSt.close();

            checkReservedSql = "SELECT is_reserved FROM purchase WHERE user_id='user2';";
            ResultSet Reservedresult = dbSt.executeQuery(checkReservedSql);
            isReserved = Reservedresult.getBoolean("is_reserved");

            dbSt.close();
            con.close(); //DB연동 끊기
        } catch (SQLException e) {
            System.out.println("SQLException : " + e.getMessage());
        }


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

        p[0].setLayout    (new GridLayout(1, 2)); //A열
        p[1].setBackground(Color.LIGHT_GRAY); //통로
        p[2].setLayout    (new GridLayout(1, 2)); //B열
        p[3].setLayout    (new GridLayout(1, 2)); //C열
        p[4].setBackground(Color.LIGHT_GRAY); //통로
        p[5].setLayout    (new GridLayout(1, 2)); //D열

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
        searchPanel.setLayout(new GridLayout(7, 1));

        JPanel p2[] = new JPanel[7];
        for (int i = 0; i < p2.length; i++){
            searchPanel.add(p2[i] = new JPanel());
            p2[i].setLayout(new FlowLayout(FlowLayout.LEFT));
        }
        JLabel normalTitle = new JLabel("주차 일반 조회");
        normalTitle.setFont(new Font("D2Coding", Font.BOLD, 20));
        JLabel rsvtitle    = new JLabel("주차 예약 조회");
        rsvtitle.setFont(new Font("D2Coding", Font.BOLD, 20));
        JLabel name        = new JLabel("이        름 : ");
        JLabel id          = new JLabel("아 이 디 : ");
        JLabel carNum      = new JLabel("차량번호 : ");
        JLabel area        = new JLabel("주차구역 : ");
        JLabel inTime      = new JLabel("입차시간 : ");
        JLabel rsvOutTime  = new JLabel("출차예정시간 : ");

        JButton OKButton     = new JButton("확인");
        JButton cancelButton = new JButton("예약 취소");
        OKButton.addActionListener(this);
        cancelButton.addActionListener(this);

        p2[1].add(name);   p2[1].add(userName);
        p2[2].add(id);     p2[2].add(userId);
        p2[3].add(carNum); p2[3].add(userCarNum);
        p2[4].add(area);   p2[4].add(userFloor);   p2[4].add(userArea);
        p2[5].add(inTime); p2[5].add(userInTime);

        //예약 유무에 따른 패널 출력값 설정
        if (isReserved == true) {
            p2[0].add(rsvtitle);
            p2[6].add(rsvOutTime); p2[6].add(userRsvOutTime);
            /*
            예약 취소 버튼 나타나게 하기
            p2[7].add(cancelButton);
            ct.add(cancelButton);
             */
        } else {
            p2[0].add(normalTitle);
        }


        carPanel.setBounds(20, 100, 500, 400);
        searchPanel.setBounds(600, 100, 500, 400);
        OKButton.setBounds(650, 500, 100, 40);
        cancelButton.setBounds(750, 500, 100, 40);
        ct.add(carPanel);
        ct.add(searchPanel);
        ct.add(OKButton);

        searchPanel.updateUI();
    }

    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        if (s == "확인") dispose();
        else {
            /* TODO 221217 05:34 예약 취소 시 결제금액(total_fee) n원 -> 0원, 결제취소유무(is_cancel) 0->1 변경
               TODO 221217 23:55 (다은) DBconnection 수정
            1. is_cancel 1로 업데이트 하기
            2. total_fee 금액 0으로 업데이트 하기
            3. 예약이 취소되었습니다! 팝업창 게시
             */
            DBconnection.updateDB("parking.purchase", "purchase.total_fee", "0", "purchase.user_id", String.valueOf(userId)); //출차시간 업데이트
            DBconnection.updateDB("parking.purchase", "purchase.is_cancel", "1", "purchase.user_id", String.valueOf(userId)); //결제취소유무 업데이트 (0:결제O 1:결제O취소O)
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "예약이 취소되었습니다.");
        }
    }
}
