
/* < 주차 프로그램 메인화면 구현 >
* 전체적인 레이아웃은 FlowLayout, 그 중 주차구역은 GridLayout
* 모든 페이지에는 [주차구역 JPanel] 이 보임
*
* 조회 페이지를 제외하곤 공통적으로 [층 선택 JComboBox] 와 [새로고침 JLabel] 이 있음
* - [층 선택]으로 층 변경 시 [주차구역] 데이터도 변경
* - [새로고침 JLabel] 클릭하면 현재 층의 주차현황을 업데이트 후 [주차구역]에 적용
*
  1. 메인
  * [가격표 JButton] 클릭하면 {가격표} 팝업 나타남
  * [메뉴 JToggleButton] 클릭하면 {메뉴목록} 팝업 나타남
  * [예약 JButton] 클릭하면 [2.예약] 페이지로 넘어감
  * [주차 JButton] 클릭하면 [3.주차] 페이지로 넘어감
  * [조회 JButton] 클릭하면 [4.조회] 페이지로 넘어감
  * [정산 JButton] 클릭하면 {정산} 페이지로 넘어감

  2.예약, 3.주차
  * * [이름 JLabel]과 [차량번호 JLabel]은 {회원 DB}에서 불러오기 * *
  * * [주차구역 JPanel]에서 클릭 시 [주차구역 JLabel]??에 층과 구역 불러와짐 * *
  *
  - 예약
  * [예약일시 JComboBox]에서 예약할 월/일/시/분(10단위) 선택
  * [이용시간 JComboBox]에서 1~3시간 중 선택
  * [포인트 JTextField]에 사용할 포인트 작성, [보유포인트 JLabel]은 {회원 DB}에서 불러오기
  * [선결제금액 JLabel]은 ({선결제 가격} 불러와서) or (함수 이용해서) 표시, 10%할인 적용된 금액
  * [적립포인트 JLabel] = ([선결제금액 JLabel] - [포인트 JTextField]) * 0.03
  * [예약하기 JButton] 클릭 시 {회원 DB}로 정보 넘어감, {결제} 페이지로 넘어감
  - 주차
  * [현재시간 JLabel]은 (함수 이용해서) 표시
  * [완료 JButton] 클릭 시 {회원 DB}로 정보 넘어감, [1.메인] 페이지로 넘어감
  *
  * * [취소 JButton] 클릭 시 아무 변화 없이 [1.메인] 페이지로 넘어감 * *

  4. 조회
  * * [이름], [아이디], [차량번호], [주차구역] 모두 {회원 DB}에서 불러와서 JLabel 로 표시
  *
  - 예약조회
  * [출차예정시간 JLabel]은 {예약 DB}에서 불러오기
  - 일반조회
  * [입차시간 JLabel]은 {주차 DB}에서 불러오기
  *
  * * [확인 JButton] 클릭 시 아무 변화 없이 [1.메인] 페이지로 넘어감 * *

*/

package UserMain;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.CostFrame;


public class UserMain extends JFrame{
    /*-----------------------------------------------------------------------*/
    String[] floor_list = {"B1", "B2", "B3"};           //층 목록
    JComboBox floorCBox = new JComboBox(floor_list);    //층 선택
    /*-----------------------------------------------------------------------*/

    public static Container ct;     //TODO : 컨테이너랑 패널 재사용 하고싶은데..

    JButton fareTag = new JButton("요금표");

    JButton menu = new JButton("메뉴");

    JButton rsv = new JButton("예약");
    JButton park = new JButton("주차");
    JButton check = new JButton("조회");
    JButton pay = new JButton("정산");

    public UserMain() {
        ct = getContentPane();
        ct.setLayout(new BorderLayout());

        /*-------------------Rsv, Check 와 겹치는 내용----------------------*/
        JPanel panel = new JPanel();       //CENTER
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JPanel t_pnl = new JPanel();       //NORTH
        t_pnl.setLayout(new FlowLayout((FlowLayout.LEFT)));
        /*---------------CENTER, NORTH 에 위치하는 Panel 들----------------*/

        JPanel main_pnl = new JPanel();    //EAST
        GridLayout g = new GridLayout(6,1);
        g.setVgap(10); //상하여백
        g.setHgap(30); //좌우여백
        main_pnl.setLayout(g);

        JPanel fare_menu = new JPanel();//우측상단
        fare_menu.setLayout(new FlowLayout());




        fareTag.addActionListener(new ActionListener() {    //요금표 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                CostFrame costFrame = new CostFrame("요금표");
                costFrame.setSize(300,300);
                costFrame.setVisible(true);
            }
        });

        menu.addActionListener(new ActionListener() {       //메뉴 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "메뉴");
            }
        });

        rsv.addActionListener(new ActionListener() {        //예약 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                Rsv m = new Rsv("주차 프로그램 - 주차예약");
                m.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                m.setSize(1000, 600);
                m.setVisible(true);

            }
        });

        park.addActionListener(new ActionListener() {       //주차 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                Park p = new Park("주차 프로그램 - 일반주차");
                p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                p.setSize(1000, 600);
                p.setVisible(true);
            }
        });

        check.addActionListener(new ActionListener() {      //조회 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                //조회 페이지 불러오기
                JOptionPane.showMessageDialog(null, "조회하기");
            }
        });

        pay.addActionListener(new ActionListener() {        //정산 액션이벤트
            @Override
            public void actionPerformed(ActionEvent e) {
                //정산 페이지 불러오기
                JOptionPane.showMessageDialog(null, "정산하기");
            }
        });



        t_pnl.add(floorCBox);       //층 선택 콤보박스

        fare_menu.add(fareTag);     //요금표
        fare_menu.add(menu);        //메뉴버튼

        main_pnl.add(fare_menu);    //요금표+메뉴버튼
        main_pnl.add(rsv);          //예약버튼
        main_pnl.add(park);         //주차버튼
        main_pnl.add(check);        //조회버튼
        main_pnl.add(pay);          //정산버튼

        ct.add(t_pnl, BorderLayout.NORTH);      //상단 패널
        ct.add(panel, BorderLayout.CENTER);  //주차장 패널
        ct.add(main_pnl, BorderLayout.EAST);    //우측 패널





    }//UserMain 생성자 끝



}//UserMain 클래스 끝

class Main extends JFrame{
    public static void main(String[] args) {
        UserMain m = new UserMain();
        m.setTitle("주차 프로그램 - 메인");
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(1000, 600);
        m.setVisible(true);
    }

}


