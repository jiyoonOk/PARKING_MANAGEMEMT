package UserMain;

import javax.swing.*;
import java.awt.*;

public class Check extends JFrame {
    JLabel title = new JLabel("주차조회");          //제목

    //회원정보
    JLabel name = new JLabel("이름: ");            //이름
    JLabel id = new JLabel("아이디: ");            //아이디
    JLabel carNum = new JLabel("차량번호: ");      //차량번호
    JLabel floor = new JLabel("주차구역: ");       //주차구역
    JLabel ex_time = new JLabel("출차예정시간: ");  //예약 - 출차예정시간
    JLabel cur_time = new JLabel("입차시간: ");    //주차 - 입차시간
    //TODO : 출차예정시간이랑 {주차 DB}의 출차시간이랑 같은가?
    JButton check = new JButton("확인");          //확인버튼
    //TODO : 액션 이벤트 연결 - 확인 누르면 메인으로
    
    
    public Check() {
        /*------------------UserMain, Rsv 와 겹치는 내용-------------------*/
        JPanel panel = new JPanel();       //CENTER
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JPanel t_pnl = new JPanel();       //NORTH
        t_pnl.setLayout(new FlowLayout((FlowLayout.LEFT)));
        /*---------------CENTER, NORTH 에 위치하는 Panel 들----------------*/

        JPanel check_pnl = new JPanel();   //EAST


        Container ct = getContentPane();
        ct.setLayout(new BorderLayout());
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