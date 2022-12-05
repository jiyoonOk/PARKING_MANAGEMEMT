package UserMain;

import javax.swing.*;
import java.awt.*;

public class Check extends JFrame {

    /*=================================================================*/
    JPanel parkPnl = new JPanel(); //주차장
    JButton[] parkBtn; //주차구역 버튼
    String[] park_floor;  String[] park_section; //주차구역 층, 위치
    JLabel[] parkLbl = new JLabel[40]; //주차위치 - 상수

    JPanel svcPnl = new JPanel(); //우측 서비스(예약,주차,조회,정산)
    /*=================================================================*/
    /*공통으로 모든 파일(UserMain, Rsv, Park, Check)에 들어있음*/



    JLabel title = new JLabel("주차조회"); //제목 - 문자

    //회원정보
    JLabel name = new JLabel(/*{회원 DB}에서 불러오기*/); //이름
    JLabel id = new JLabel(/*{회원 DB}에서 불러오기*/); //아이디
    JLabel carNum = new JLabel(/*{회원 DB}에서 불러오기*/); //차량번호
    JLabel floor = new JLabel(/*{주차장 DB}에서 불러오기*/); //주차구역
    JLabel ex_time = new JLabel(/*{주차 DB}에서 불러오기*/); //예약 - 출차예정시간
    JLabel cur_time = new JLabel(/*{주차 DB}에서 불러오기*/); //주차 - 입차시간
    //TODO : 출차예정시간이랑 {주차 DB}의 출차시간이랑 같은가?
    JButton check = new JButton("확인"); //확인버튼 - 문자
    
    
    public Check() {

        Container ct = getContentPane();
        ct.setLayout(new BorderLayout());
        ct.add(svcPnl, BorderLayout.EAST);
        
    }//Check 생성자 끝
}//Check 클래스 끝
