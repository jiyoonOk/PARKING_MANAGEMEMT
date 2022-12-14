import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Vector;

import static javax.swing.JOptionPane.YES_OPTION;

public class AdminMain extends JFrame {
    static DBconnection usingDB = new DBconnection(); //DBConnection 안에 함수 쓰기 위한 용
    static int row;
    static String rowClickedPrimaryKey;
    //String question_id = "";
    static AdminMain admin;

    //main에 필요한 변수
    private JTabbedPane tab;
    private JPanel jp, btnjp;
    private JButton pwChangeB, logoutB;
    private JPanel salesAdminJp, userAdminJp, qnaJp, noticeJp;

    //userAdmin에 필요한 변수
    private JTextField userSearch;
    private JPanel userInfoPanel;
    private JTextField nameField, idField, carField, numberField, cardField;
    private JButton deleteUserButton, changeUserButton;
    JTable userTable, userParkingTable;
    private JScrollPane userScrollPane, userParkingScrollPane;
    Vector<Vector<String>> rowData, rowData2, rowData3, rowData4, rowData5;

    //salesAdmin에 필요한 변수
    private JComboBox purchaseCombo;
    private JRadioButton yearRB, halfRB, monthRB, weekRB, dayRB;
    private JLabel purchaseLabel;
    JTable salesTable;
    private UserAdmin user;
    private JScrollPane salesScrollPane;
    private JLabel total;

    //QnATab 에 필요한 변수
    private JTextField qnaTitleField, qnaIDField;
    private JTextArea qnaContensArea;
    private JButton qnaAddButton, qnaDeleteButton;
    JTable qnaJTable;
    private JScrollPane qnaScrollPane;

    //noticeTab에 필요한 변수
    private JTextField noticeTitleField;
    private JTextArea noticeContentsArea;
    private JButton noticeAddButton, noticeChangeButton, noticeDeleteButton;
    private JScrollPane noticeScrollPane;
    JTable noticeJTable;

    //DB에 필요한 변수들
    static Connection con = null;
    static String user_name = "root"; //  접속자 id
    static String password = "wldbs1004"; // 접속자 pw

    public AdminMain() {
        super("주차관리예약시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container ct = getContentPane();

        tab = new JTabbedPane();
        user = new UserAdmin();
        SalesAdmin sales = new SalesAdmin();
        QnaAdmin qna = new QnaAdmin();
        NoticeAdmin notice = new NoticeAdmin();

        tab.addTab("회원정보관리", user);
        tab.addTab("매출관리", sales);
        tab.addTab("문의사항관리", qna);
        tab.addTab("공지사항", notice);

        ct.add(jp);

        //
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
                //TODO : 로그인 화면으로 돌아가기
            }
        });

        pack();
        setVisible(true);

        //각 JTable 클릭하면 옆에 자세한 사항들 나오는 리스너들
        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                row = userTable.getSelectedRow();
                rowClickedPrimaryKey = userTable.getModel().getValueAt(row, 0).toString();

                nameField.setText(userTable.getModel().getValueAt(row, 1).toString());
                idField.setText(rowClickedPrimaryKey);
                carField.setText(userTable.getModel().getValueAt(row, 3).toString());
                numberField.setText(userTable.getModel().getValueAt(row, 2).toString());

                usingDB = new DBconnection();
                String card_num = usingDB.getData("SELECT card_num FROM parking.user where id = '" + rowClickedPrimaryKey + "';");
                cardField.setText(card_num);

                changeUserButton.setEnabled(true);
                deleteUserButton.setEnabled(true);
            }
        });
        qnaJTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                row = qnaJTable.getSelectedRow();
                rowClickedPrimaryKey = qnaJTable.getModel().getValueAt(row, 0).toString();

                qnaTitleField.setText(qnaJTable.getModel().getValueAt(row, 1).toString());
                qnaIDField.setText(qnaJTable.getModel().getValueAt(row, 4).toString());
                qnaContensArea.setText(qnaJTable.getModel().getValueAt(row, 2).toString());

                qnaAddButton.setEnabled(true);
                qnaDeleteButton.setEnabled(true);
            }
        });
        noticeJTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                row = noticeJTable.getSelectedRow();
                rowClickedPrimaryKey = noticeJTable.getModel().getValueAt(row,0).toString();

                noticeTitleField.setText(noticeJTable.getModel().getValueAt(row, 1).toString());
                noticeContentsArea.setText(noticeJTable.getModel().getValueAt(row, 2).toString());

                noticeChangeButton.setEnabled(true);
                noticeDeleteButton.setEnabled(true);
            }
        });

        //총 매출 뽑아옴
        int sum = 0;
        for (int i = 0; i < salesTable.getRowCount(); i++) {
            String a = salesTable.getModel().getValueAt(i, 1).toString();
            sum = sum + Integer.parseInt(a);
            //here i is the row wise iteration and 2 is the column number of mycalculation attribute
        }
        total.setText("총 매출 : " + sum + " 원");

    } //mainTest 생성자 종료

    public static void main(String[] args) {
        admin = new AdminMain();
        admin.setLocationRelativeTo(null);  //노트북 화면 기준으로 가운데에 창 출력시켜줌!
    } //admin 관리창 여는 main 함수


    // JTable들과 JScrollPane 관련 함수들
    private void createUIComponents() {
        // 모든 유저 조회하는 JTable
        Vector<String> columnName = new Vector<String>();
        columnName.add("ID");
        columnName.add("이름");
        columnName.add("전화번호");
        columnName.add("차량번호");
        columnName.add("포인트");
        rowData = new Vector<Vector<String>>();
        DefaultTableModel model = new DefaultTableModel(rowData, columnName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(model);
        userTable.setName("userTable");
        userScrollPane = new JScrollPane(userTable);

        // 회원 주차 이력 JTable
        Vector<String> columnName2 = new Vector<String>();
        columnName2.add("날짜");
        columnName2.add("주차 구역");
        columnName2.add("이용 금액");
        rowData2 = new Vector<Vector<String>>();
        DefaultTableModel model2 = new DefaultTableModel(rowData2, columnName2) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userParkingTable = new JTable(model2);
        userParkingTable.setName("userParkingTable");
        userParkingScrollPane = new JScrollPane(userParkingTable);

        // 문의사항 JTable
        Vector<String> columnName3 = new Vector<String>();
        columnName3.add("문의번호");
        columnName3.add("제목");
        columnName3.add("내용");
        columnName3.add("날짜");
        columnName3.add("ID");
        rowData3 = new Vector<Vector<String>>();
        DefaultTableModel model3 = new DefaultTableModel(rowData3, columnName3) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        qnaJTable = new JTable(model3);
        qnaJTable.setName("qnaJTable");
        qnaScrollPane = new JScrollPane(qnaJTable);

        // 공지사항 JTable
        Vector<String> columnName4 = new Vector<String>();
        columnName4.add("공지번호");
        columnName4.add("제목");
        columnName4.add("내용");
        columnName4.add("날짜");
        rowData4 = new Vector<Vector<String>>();
        DefaultTableModel model4 = new DefaultTableModel(rowData4, columnName4) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        noticeJTable = new JTable(model4);
        noticeJTable.setName("noticeJTable");
        noticeScrollPane = new JScrollPane(noticeJTable);

        // 매출관리 JTable
        //TODO : 결제취소는 어떻게 표현할 건지, DB는?? is_cancel로 하기로 함
        Vector<String> columnName5 = new Vector<String>();
        columnName5.add("이용 날짜"); /*columnName5.add("차량번호");*/
        columnName5.add("금액");
        columnName5.add("아이디");
        rowData5 = new Vector<Vector<String>>();
        DefaultTableModel model5 = new DefaultTableModel(rowData5, columnName5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        salesTable = new JTable(model5);
        salesTable.setName("salesTable");
        salesScrollPane = new JScrollPane(salesTable);
    }

        // 회원관리 탭 클래스
        class UserAdmin extends JPanel implements ActionListener {
            DBconnection allUserDB, userParkingDB;
            public UserAdmin() {

                allUserDB = new DBconnection("SELECT * from parking.user;", rowData, userTable);
                userParkingDB = new DBconnection("SELECT * from parking.purchase;", rowData2, userParkingTable);
                allUserDB.JTableUpdate();
                userParkingDB.JTableUpdate();

                TextHint hint = new TextHint(userSearch, "검색어를 입력하세요.");
                changeUserButton.addActionListener(this);
                deleteUserButton.addActionListener(this);


                userSearch.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //재사용성 지렸음~!~!
                        String sql = "select * from parking.user where id like '%"+userSearch.getText()+"%' OR name like '%"+userSearch.getText()+"%' OR car_num like '%"+userSearch.getText()+"%' OR phone_num like '%"+userSearch.getText()+"%';";
                        DBconnection searchUser = new DBconnection(sql, rowData, userTable);
                        searchUser.JTableUpdate();
                    }
                });
            }

            //TODO : 계정 변경, 저장
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("계정 변경")) {
                    nameField.setEditable(true);
                    idField.setEditable(true);
                    carField.setEditable(true);
                    numberField.setEditable(true);
                    cardField.setEditable(true);

                    changeUserButton.setText("저장");

                } else if (s.equals("저장")) {
                    //TODO : 입력 변경된 내용 받아서 DB 입력

                    JOptionPane.showMessageDialog(admin, "계정 변경이 완료되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);

                    nameField.setEditable(false);
                    idField.setEditable(false);
                    carField.setEditable(false);
                    numberField.setEditable(false);
                    cardField.setEditable(false);

                    changeUserButton.setText("계정 변경");
                } else if (s.equals("계정 삭제")) {
                    int user_delete = JOptionPane.showConfirmDialog(admin, "정말 계정을 삭제 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
                    if (user_delete == YES_OPTION) {

                        usingDB.DelectAttribute("user");

                        JOptionPane.showMessageDialog(admin, "계정이 삭제되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }//user admin 끝

        // 매출관리 탭 클래스
        class SalesAdmin extends JPanel implements ActionListener {
            DBconnection salesDB;

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

                salesDB = new DBconnection("SELECT * from parking.purchase order by car_out;", rowData5, salesTable);
                salesDB.JTableUpdate();

                purchaseCombo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(e.getActionCommand() + " 버튼 누름");
                        //TODO : 쿼리문으로 결제완료 / 결제취소 구분
                    }
                });
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand() + " 버튼 누름");
                //TODO : 년 / 6개월 / 1달 / 일주일 단위로 JTable 출력


            }

        }//salesAdmin 클래스 종료

        // 문의사항 탭 클래스
        class QnaAdmin extends JPanel implements ActionListener {
            DBconnection qnaDB;

            public QnaAdmin() {

                qnaDB = new DBconnection("SELECT * from parking.question order by question_date;", rowData3, qnaJTable);
                qnaDB.JTableUpdate();

                qnaAddButton.addActionListener(this);
                qnaDeleteButton.addActionListener(this);
            }

            //문의 답변, 삭제
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = Integer.parseInt(rowClickedPrimaryKey);
                switch (e.getActionCommand()) {
                    case "답변 작성":{
                        if (qnaTitleField.getText() == null) {
                            JOptionPane.showMessageDialog(admin, "답변할 문의글부터 선택해주세요!!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            AddText add = new AddText(a);
                        }} break;
                    case "삭제":{
                        int question_delete = JOptionPane.showConfirmDialog(admin, "정말 문의를 삭제 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
                        if (question_delete == YES_OPTION) {

                            usingDB.DelectAttribute("question");

                            JOptionPane.showMessageDialog(admin, "문의가 삭제되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                        };} break;
                }
            }
        }// QnaAdmin 클래스 종료

        // 공지사항 탭 클래스
        class NoticeAdmin extends JPanel implements ActionListener {
            DBconnection noticeDB;

            public NoticeAdmin() {
                // 제목, 내용 text DB 연결하기
                noticeDB = new DBconnection("SELECT * from parking.notice order by notice_date;", rowData4, noticeJTable);
                noticeDB.JTableUpdate();

                noticeAddButton.addActionListener(this);
                noticeChangeButton.addActionListener(this);
                noticeDeleteButton.addActionListener(this);
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand()) {
                    case "추가": {
                        AddText add = new AddText();
                    }break;
                    case "수정": {
                    }break;   //TODO : DB 수정
                    case "삭제": {
                        int notice_delete = JOptionPane.showConfirmDialog(admin, "정말 공지사항을 삭제 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
                        if (notice_delete == YES_OPTION) {

                            usingDB.DelectAttribute("notice");

                            JOptionPane.showMessageDialog(admin, "공지사항이 삭제되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                        }
                        ;
                    }break;
                }
                noticeDB.JTableUpdate();
            }
        } //noticeAdmin 종료
}//AdminMain 클래스 종료


