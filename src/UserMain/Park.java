package UserMain;

import javax.swing.*;

public class Park extends JFrame {

    /*=================================================================*/
    String[] floor_list = {"B1", "B2", "B3"}; //층 목록(B1 기본), 상수, 데베
    JComboBox floorCBox = new JComboBox(floor_list); //층 선택
    /*=================================================================*/
    /*층 조회 필요한 파일(UserMain, Rsv, Park)에 들어있음*/


    /*=================================================================*/
    JPanel parkPnl = new JPanel(); //주차장
    JButton[] parkBtn; //주차구역 버튼
    String[] park_floor;  String[] park_section; //주차구역 층, 위치
    JLabel[] parkLbl = new JLabel[40]; //주차위치 - 상수

    JPanel svcPnl = new JPanel(); //우측 서비스(예약,주차,조회,정산)
    /*=================================================================*/
    /*공통으로 모든 파일(UserMain, Rsv, Park, Check)에 들어있음*/



    JLabel title = new JLabel("주차일반"); //제목 - 문자

    //회원 이름, 차량번호
    JLabel name = new JLabel(/*{회원 DB}에서 불러오기*/);
    JLabel carNum = new JLabel(/*{회원 DB}에서 불러오기*/);

    //현재시간 - 문자, 상수
    JLabel curTime = new JLabel("현재시간 : ");
    //TODO : 월, 일, 시, 분 까지만 표시하기

    //주차구역 - 문자, 상수
    JLabel parking_floor;  JLabel parking_section;

    //주차 확인 버튼 - 문자
    JButton parking_go = new JButton("완료");  JButton parking_cancel = new JButton("취소");

    public Park() {
        
    }//Park 생성자 끝
}//Park 클래스 끝
