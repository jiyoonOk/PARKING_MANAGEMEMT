
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

import common.ChangePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMain extends JFrame{
    /*-----------------------Rsv 와 겹치는 내용-------------------------*/
    String[] floor_list = {"B1", "B2"}; //층 목록(B1 기본), 상수, 데베
    JComboBox floorCBox = new JComboBox(floor_list); //층 선택
    /*----------------------------------------------------------------*/

    public static Container ct;
    public static JPanel main_pnl;


    JLabel refresh = new JLabel("새로고침");
    //TODO : 진짜 넣어야 하나? 필요한가?

    JButton fareTag = new JButton("요금표");

    JButton menu = new JButton("메뉴");

    JButton rsv = new JButton("예약");
    JButton park = new JButton("주차");
    JButton check = new JButton("조회");
    JButton pay = new JButton("정산");

    public UserMain() {
        ct = getContentPane();
        ct.setLayout(new BorderLayout());

        JPanel fare_menu = new JPanel();//우측상단
        fare_menu.setLayout(new FlowLayout());

        main_pnl = new JPanel();//우측
        GridLayout g = new GridLayout(6,1);
        g.setVgap(10);
        g.setHgap(30);
        main_pnl.setLayout(g);

        /*-----------------------Rsv 와 겹치는 내용-------------------------*/
        JPanel park_pnl = new JPanel();//센터
        park_pnl.setLayout(new BorderLayout());
        park_pnl.setBackground(Color.LIGHT_GRAY);

        JPanel t_pnl = new JPanel();//위
        t_pnl.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel b_pnl = new JPanel();//아래
        b_pnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        /*----------------------------------------------------------------*/


        fareTag.addActionListener(new ActionListener() {//요금표
            @Override
            public void actionPerformed(ActionEvent e) {
                //요금표 불러오기
                JOptionPane.showMessageDialog(null, "요금표");
            }
        });

        menu.addActionListener(new ActionListener() {//메뉴
            @Override
            public void actionPerformed(ActionEvent e) {
                //메뉴 불러오기
                JOptionPane.showMessageDialog(null, "메뉴");
            }
        });

        rsv.addActionListener(new ActionListener() {//예약
            @Override
            public void actionPerformed(ActionEvent e) {
                //예약 페이지 불러오기
                new ChangePanel(main_pnl, Rsv.rsv_pnl);
                Rsv r = new Rsv();
                r.setTitle("예약하기");
                r.setSize(1000,600);
                r.setVisible(true);

            }
        });

        park.addActionListener(new ActionListener() {//주차
            @Override
            public void actionPerformed(ActionEvent e) {
                //주차 페이지 불러오기
                JOptionPane.showMessageDialog(null, "주차하기");
            }
        });

        check.addActionListener(new ActionListener() {//조회
            @Override
            public void actionPerformed(ActionEvent e) {
                //조회 페이지 불러오기
                JOptionPane.showMessageDialog(null, "조회하기");
            }
        });

        pay.addActionListener(new ActionListener() {//정산
            @Override
            public void actionPerformed(ActionEvent e) {
                //정산 페이지 불러오기
                JOptionPane.showMessageDialog(null, "정산하기");
            }
        });


        fare_menu.add(fareTag);
        fare_menu.add(menu);

        t_pnl.add(floorCBox);

        park_pnl.add(t_pnl, BorderLayout.NORTH);
        park_pnl.add(b_pnl, BorderLayout.SOUTH);

        b_pnl.add(refresh);

        main_pnl.add(fare_menu);
        main_pnl.add(rsv);
        main_pnl.add(park);
        main_pnl.add(check);
        main_pnl.add(pay);

        ct.add(park_pnl, BorderLayout.CENTER);
        ct.add(main_pnl, BorderLayout.EAST);





    }//UserMain 생성자 끝



}//UserMain 클래스 끝




