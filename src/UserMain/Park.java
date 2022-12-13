package UserMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import common.ParkingLot;

public class Park extends JFrame {

    JButton parkingButton = new JButton("완료");       //주차완료버튼
    JButton cancelButton  = new JButton("취소");   //취소버튼

    public Park(String t) {
        super(t);

        JLabel nameLabel     = new JLabel("이름: ");           //이름
        JLabel name          = new JLabel("이다은");
        JLabel carNumLabel   = new JLabel("차량번호: ");       //차량번호
        JLabel carNum        = new JLabel("12라3442");
        JLabel currTime      = new JLabel("현재시간 : ");      //현재시간
        LocalTime now        = LocalTime.now();
        JLabel time          = new JLabel(now.getHour()+"시"+now.getMinute()+"분");
        JLabel location      = new JLabel("주차구역: ");       //주차구역
        JLabel choose_areaFloor = ParkingLot.userFloor;   //주차위치(층)
        JLabel choose_areaNum = ParkingLot.userNum;       //주차위치(구역)

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


        Container parkCt = getContentPane();
        parkCt.setLayout(null);

        nameLabel.setBounds(UserMain.FIRST_OF_INFO, UserMain.TOP_OF_PARK+10, Rsv.DEFAULT_SIZE, 25);
        name.setBounds(Rsv.FIRST_OF_HALF_INFO, UserMain.TOP_OF_PARK+10, Rsv.DEFAULT_SIZE, 25);
        carNumLabel.setBounds(UserMain.FIRST_OF_INFO, UserMain.TOP_OF_PARK+100, Rsv.DEFAULT_SIZE, 25);
        carNum.setBounds(Rsv.FIRST_OF_HALF_INFO, UserMain.TOP_OF_PARK+100, Rsv.DEFAULT_SIZE, 25);
        currTime.setBounds(UserMain.FIRST_OF_INFO, UserMain.TOP_OF_PARK+200, Rsv.DEFAULT_SIZE, 25);
        time.setBounds(Rsv.FIRST_OF_HALF_INFO, UserMain.TOP_OF_PARK+200, Rsv.DEFAULT_SIZE, 25);
        location.setBounds(UserMain.FIRST_OF_INFO, UserMain.TOP_OF_PARK+300, Rsv.DEFAULT_SIZE, 25);
        choose_areaFloor.setBounds(Rsv.FIRST_OF_HALF_INFO, UserMain.TOP_OF_PARK+300, 25, 25);
        choose_areaNum.setBounds(870, UserMain.TOP_OF_PARK+300, 25, 25);


        parkCt.add(ParkingLot.floor);
        parkCt.add(ParkingLot.car);
        parkCt.add(nameLabel);     parkCt.add(name);
        parkCt.add(carNumLabel);   parkCt.add(carNum);
        parkCt.add(currTime); parkCt.add(time);
        parkCt.add(location);      parkCt.add(choose_areaFloor);    parkCt.add(choose_areaNum);


        parkCt.add(parkingButton);   parkCt.add(cancelButton); //완료, 취소 버튼


    }//Park 생성자 끝

}//Park 클래스 끝