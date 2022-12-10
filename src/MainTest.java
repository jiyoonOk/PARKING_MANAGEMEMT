import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import static javax.swing.JOptionPane.YES_OPTION;

public class MainTest extends JFrame {
    static MainTest admin;

    //main에 필요한 변수
    private JTabbedPane tab;
    private JPanel jp, btnjp;
    private JButton pwChangeB, logoutB;
    private JPanel salesAdminJp, userAdminJp, qnaJp, noticeJp;

    //userAdminTab에 필요한 변수
    private JTextField userSearch;
    private JPanel userInfoPanel;
    private JTextField nameField, idField, carField, numberField, cardField;
    private JButton userDeleteButton, userChangeButton;
    private JTable userTable, userParkingTable;
    private JScrollPane userScrollPane, userParkingScrollPane;

    //salesAdminTab에 필요한 변수
    private JComboBox purchaseCombo;
    private JRadioButton yearRB, halfRB, monthRB, weekRB, dayRB;
    private JLabel purchaseLabel;
    private JTable salesTable;
    private JScrollPane salesScrollPane;

    //QnATab 에 필요한 변수
    private JTextField qnaTitleField, qnaIDField;
    private JTextArea qnaContensArea;
    private JButton qnaAddButton, qnaDeleteButton;
    private JTable qnaJTable;
    private JScrollPane qnaScrollPane;

    //noticeTab에 필요한 변수
    private JTextField noticeTitleField;
    private JTextArea noticeContentsArea;
    private JButton noticeAddButton, noticeChangeButton, noticeDeleteButton;
    private JScrollPane noticeScrollPane;
    private JTable noticeJTable;



    public MainTest() {
        super("주차관리예약시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container ct = getContentPane();

        tab = new JTabbedPane();
        UserAdmin user = new UserAdmin();
        SalesAdmin sales = new SalesAdmin();


        tab.addTab("회원정보관리", user);
        tab.addTab("매출관리", sales);

        ct.add(jp);

        //TODO : 패스워드 변경하고, 로그아웃하는 버튼리스너 클래스 분리
        pwChangeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PwChange change = new PwChange(admin, "비밀번호 변경", true);
                change.setLocationRelativeTo(admin);
                change.show();
            }
        });

        logoutB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(admin, "정말 로그아웃 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
            }
        });

        pack();
        setVisible(true);
    } //mainTest 생성자 종료

    public static void main(String[] args) {
        admin = new MainTest();
        admin.setLocationRelativeTo(null);
    } //admin 관리창 여는 main 함수

    //TODO : JTable들과 JScrollPane 관련 함수들
    private void createUIComponents() {
        // 모든 유저 조회하는 JTable
        Vector<String> columnName = new Vector<String>();
        columnName.add("회원번호"); columnName.add("이름"); columnName.add("아이디"); columnName.add("차량번호");
        Vector<Vector<String>> rowData = new Vector<Vector<String>>();
        DefaultTableModel model = new DefaultTableModel(rowData, columnName);
        userTable = new JTable(model);
        userScrollPane = new JScrollPane(userTable);

        // 회원 주차 이력 JTable
        Vector<String> columnName2 = new Vector<String>();
        columnName2.add("날짜"); columnName2.add("주차 구역"); columnName2.add("이용 날짜");
        Vector<Vector<String>> rowData2 = new Vector<Vector<String>>();
        DefaultTableModel model2 = new DefaultTableModel(rowData2, columnName2);
        userParkingTable = new JTable(model2);
        userParkingScrollPane = new JScrollPane(userParkingTable);

        // 문의사항 JTable
        Vector<String> columnName3 = new Vector<String>();
        columnName3.add("문의번호"); columnName3.add("제목"); columnName3.add("내용"); columnName3.add("차량번호");
        Vector<Vector<String>> rowData3 = new Vector<Vector<String>>();
        DefaultTableModel model3 = new DefaultTableModel(rowData3, columnName3);
        qnaJTable = new JTable(model3);
        qnaScrollPane = new JScrollPane(qnaJTable);

        // 공지사항 JTable
        Vector<String> columnName4 = new Vector<String>();
        columnName4.add("회원번호"); columnName4.add("이름"); columnName4.add("아이디"); columnName4.add("차량번호");
        Vector<Vector<String>> rowData4 = new Vector<Vector<String>>();
        DefaultTableModel model4 = new DefaultTableModel(rowData4, columnName4);
        noticeJTable = new JTable(model4);
        noticeScrollPane = new JScrollPane(noticeJTable);
    }

    // 회원관리 탭 클래스
    class UserAdmin extends JPanel implements ActionListener {
        public UserAdmin() {

            TextHint hint = new TextHint(userSearch, "ID를 입력하세요.");
            userChangeButton.addActionListener(this);
            userDeleteButton.addActionListener(this);

            //TODO : ID 검색뿐만 아니라, 이름, 차량번호로도 검색하는 방법 추가할 수 있음.
            userSearch.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //TODO : 테이블에서 ID 검색 결과 출력
                    System.out.println("ID : " + e.getActionCommand() + " 를 검색하겠습니다.");
                }
            });
        }


        //TODO : 이거 무조건 따로 클래스 빼기!!! 계정 변경 -> 저장 , 삭제
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();
            if (s.equals("계정 변경")) {
                nameField.setEditable(true);
                idField.setEditable(true);
                carField.setEditable(true);
                numberField.setEditable(true);
                cardField.setEditable(true);

                userChangeButton.setText("저장");

            } else if (s.equals("저장")) {
                //TODO : 입력 변경된 내용 받아서 데이터 변경

                //TODO : 저장이 완료되었다는 팝업창 뜨게 하기 ,
                JOptionPane.showMessageDialog(admin, "계정 변경이 완료되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);

                nameField.setEditable(false);
                idField.setEditable(false);
                carField.setEditable(false);
                numberField.setEditable(false);
                cardField.setEditable(false);

                userChangeButton.setText("계정 변경");
            } else if (s.equals("계정 삭제")) {
                int user_delete = JOptionPane.showConfirmDialog(admin, "정말 계정을 삭제 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
                if (user_delete == YES_OPTION) {
                    //TODO : DB에서 계정 삭제
                    JOptionPane.showMessageDialog(admin, "계정이 삭제되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//user admin 끝

    // 매출관리 탭 클래스
    class SalesAdmin extends JPanel implements ActionListener {

        public SalesAdmin() {

            ButtonGroup g = new ButtonGroup();
            yearRB.addActionListener(this);
            halfRB.addActionListener(this);
            monthRB.addActionListener(this);
            weekRB.addActionListener(this);
            g.add(yearRB);
            g.add(halfRB);
            g.add(monthRB);
            g.add(weekRB);
            g.add(dayRB);

            purchaseCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println(e.getActionCommand() + " 버튼 누름");
                    //TODO : 쿼리문으로 결제완료 / 결제취소 구분
                }
            });
            salesTable = new JTable();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand() + " 버튼 누름");
            //TODO : 년 / 6개월 / 1달 / 일주일 단위로 JTable 출력
        }
    }//salesAdmin 클래스 종료

    // 문의사항 탭 클래스
    class qnaAdmin extends JPanel implements ActionListener {

        public qnaAdmin() {
            //TODO : 제목, 아이디, 내용 DB 연결하기

            qnaAddButton.addActionListener(this);
            qnaDeleteButton.addActionListener(this);
        }

        //TODO : 문의 답변, 삭제 이벤트리스너 따로 클래스 빼기
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "답변 작성": ; break;
                case "삭제" : ; break;
            }
        }
    }
}//mainTest 클래스 종료

    // 관리자 비밀번호 변경 팝업창 클래스
    class PwChange extends JDialog {
        private JPasswordField currentPw, newPw, rePw;
        private JButton pass, check, ok, cancel;

        public PwChange(JFrame parent, String title, boolean modal) {
            super(parent, title, modal);

            JPanel p = new JPanel();
            p.setLayout(new GridLayout(4, 3));

            currentPw = new JPasswordField(10);
            pass = new JButton("확인");
            pass.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //TODO : DB에 저장되어있는 관리자 비밀번호와 동일한가?
                    JOptionPane.showMessageDialog(p, "비밀번호가 동일합니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    newPw.setEditable(true);
                    rePw.setEditable(true);
                    check.setEnabled(true);
                /*
                else {
                    JOptionPane.showMessageDialog(p, "비밀번호가 동일하지 않습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    currentPw.setText("");
                }
                 */
                }
            });

            p.add(new JLabel("현재 비밀번호 : "));
            p.add(currentPw);
            p.add(pass);


            newPw = new JPasswordField(10);
            newPw.setEditable(false);
            p.add(new JLabel("신규 비밀번호 : "));
            p.add(newPw);
            p.add(new JLabel(""));


            rePw = new JPasswordField(10);
            rePw.setEditable(false);
            check = new JButton("재입력 확인");
            check.setEnabled(false);
            check.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (newPw.getText().equals(rePw.getText())) {
                        JOptionPane.showMessageDialog(p, "비밀번호를 동일하게 입력하였습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                        ok.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(p, "비밀번호가 동일하지 않습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                        rePw.setText("");
                    }
                }
            });

            p.add(new JLabel("비밀번호 재입력 : "));
            p.add(rePw);
            p.add(check);


            ok = new JButton("확인");
            ok.setEnabled(false);
            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //TODO : 변경된 비밀번호 DB에 저장
                    JOptionPane.showMessageDialog(p, "비밀번호가 변경되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            });
            cancel = new JButton("취소");
            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            p.add(new JLabel(""));
            p.add(ok);
            p.add(cancel);

            add(p);
            pack();
        }
    }

