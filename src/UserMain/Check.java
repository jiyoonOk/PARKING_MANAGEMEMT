package UserMain;

import javax.swing.*;
import java.awt.*;

public class Check extends JFrame {

    JButton check;
    //TODO !# 액션 이벤트 연결 - 확인 누르면 메인으로
    
    public Check() {

        JPanel panel = new JPanel();            //CENTER - 임시 주차장
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JPanel top_pnl = new JPanel();          //NORTH - 층선택 ComBox 들어감
        top_pnl.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel bottom_pnl = new JPanel();       //SOUTH - 확인취소 버튼
        bottom_pnl.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel check_pnl = new JPanel();          //EAST - 나머지 것들
        check_pnl.setLayout(new GridLayout(6,2));

        JLabel name = new JLabel("이름: ");            //이름
        JLabel id = new JLabel("아이디: ");            //아이디
        JLabel carNum = new JLabel("차량번호: ");      //차량번호
        JLabel floor = new JLabel("주차구역: ");       //주차구역
        JLabel ex_time = new JLabel("출차예정시간: ");  //예약 - 출차예정시간
        JLabel cur_time = new JLabel("입차시간: ");    //주차 - 입차시간
        //TODO #### 출차예정시간이랑 {주차 DB}의 출차시간이랑 같은가?
        check = new JButton("확인");          //확인버튼

        check_pnl.add(name);
        //TODO !# 마저 추가하기


        Container ct = getContentPane();
        ct.setLayout(new BorderLayout());
        ct.add(panel, BorderLayout.CENTER);
        ct.add(top_pnl, BorderLayout.NORTH);
        ct.add(bottom_pnl, BorderLayout.SOUTH);
        ct.add(check_pnl, BorderLayout.EAST);
        
    }//Check 생성자 끝
}//Check 클래스 끝

class CheckMain extends JFrame{
    public static void main(String[] args) {
        Check m = new Check();
        m.setTitle("주차 프로그램 - 조회");
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(1000, 600);
        m.setVisible(true);
    }

}