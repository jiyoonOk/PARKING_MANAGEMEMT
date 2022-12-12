import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

import static javax.swing.JOptionPane.YES_OPTION;

public class AdminMain extends JFrame {
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
    private JTextField total;

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
        admin = new AdminMain();
        admin.setLocationRelativeTo(null);  //노트북 화면 기준으로 가운데에 창 출력시켜줌!
    } //admin 관리창 여는 main 함수

    //TODO : JTable들과 JScrollPane 관련 함수들
    private void createUIComponents() {
        // 모든 유저 조회하는 JTable
        Vector<String> columnName = new Vector<String>();
        columnName.add("ID"); columnName.add("이름"); columnName.add("전화번호"); columnName.add("차량번호"); columnName.add("포인트");
        rowData = new Vector<Vector<String>>();
        DefaultTableModel model = new DefaultTableModel(rowData, columnName){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }};
        userTable = new JTable(model);
        userTable.setName("userTable");
        userScrollPane = new JScrollPane(userTable);

        // 회원 주차 이력 JTable
        Vector<String> columnName2 = new Vector<String>();
        columnName2.add("날짜"); columnName2.add("주차 구역"); columnName2.add("이용 금액");
        rowData2 = new Vector<Vector<String>>();
        DefaultTableModel model2 = new DefaultTableModel(rowData2, columnName2){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }};
        userParkingTable = new JTable(model2);
        userParkingTable.setName("userParkingTable");
        userParkingScrollPane = new JScrollPane(userParkingTable);

        // 문의사항 JTable
        Vector<String> columnName3 = new Vector<String>();
        columnName3.add("문의번호"); columnName3.add("제목"); columnName3.add("내용"); columnName3.add("날짜"); columnName3.add("ID");
        rowData3 = new Vector<Vector<String>>();
        DefaultTableModel model3 = new DefaultTableModel(rowData3, columnName3){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }};
        qnaJTable = new JTable(model3);
        qnaJTable.setName("qnaJTable");
        qnaScrollPane = new JScrollPane(qnaJTable);

        // 공지사항 JTable
        Vector<String> columnName4 = new Vector<String>();
        columnName4.add("공지번호"); columnName4.add("제목"); columnName4.add("내용"); columnName4.add("날짜");
        rowData4 = new Vector<Vector<String>>();
        DefaultTableModel model4 = new DefaultTableModel(rowData4, columnName4){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }};
        noticeJTable = new JTable(model4);
        noticeJTable.setName("noticeJTable");
        noticeScrollPane = new JScrollPane(noticeJTable);

        // 매출관리 JTable
        //TODO : 결제취소는 어떻게 표현할 건지, DB는??
        Vector<String> columnName5 = new Vector<String>();
        columnName5.add("이용 날짜"); /*columnName5.add("차량번호");*/ columnName5.add("금액"); columnName5.add("아이디");
        rowData5 = new Vector<Vector<String>>();
        DefaultTableModel model5 = new DefaultTableModel(rowData5, columnName5){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }};
        salesTable = new JTable(model5);
        salesTable.setName("salesTable");
        salesScrollPane = new JScrollPane(salesTable);

        /*int sum = 0;
        for(int i =0;i<salesTable.getRowCount();i++){
            sum += Integer.parseInt(salesTable.getValueAt(i, 2).toString());
            //here i is the row wise iteration and 2 is the column number of mycalculation attribute
        }*/
        Vector<String> ex = new Vector<String>();
        Integer sum = 0;
        for(int i=0;i<rowData5.size();i++) {
            ex = rowData5.get(i);
            sum+=Integer.parseInt(ex.get(2)); }
        total = new JTextField("합계 : "+Integer.toString(sum)+" 원");
    }

    // 회원관리 탭 클래스
    class UserAdmin extends JPanel implements ActionListener {
        public UserAdmin() {

            DBconnection allUserDB = new DBconnection("SELECT * from parking.user;", rowData, userTable);
            DBconnection userParkingDB = new DBconnection("SELECT * from parking.purchase;", rowData2, userParkingTable);

            TextHint hint = new TextHint(userSearch, "ID를 입력하세요.");
            changeUserButton.addActionListener(this);
            deleteUserButton.addActionListener(this);

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

                changeUserButton.setText("저장");

            } else if (s.equals("저장")) {
                //TODO : 입력 변경된 내용 받아서 데이터 변경

                //TODO : 저장이 완료되었다는 팝업창 뜨게 하기 ,
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

            DBconnection salesDB = new DBconnection("SELECT * from parking.purchase;", rowData5, salesTable);

            purchaseCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println(e.getActionCommand() + " 버튼 누름");
                    //TODO : 쿼리문으로 결제완료 / 결제취소 구분
                }
            });
                //getSum();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand() + " 버튼 누름");
            //TODO : 년 / 6개월 / 1달 / 일주일 단위로 JTable 출력
        }

        /*public void getSum() {

            int sum = 0;
            for(int i =0;i<salesTable.getRowCount();i++){
                sum = sum +Integer.parseInt(salesTable.getValueAt(i, 2).toString());
                //here i is the row wise iteration and 2 is the column number of mycalculation attribute
            }
            total.setText(Integer.toString(sum));
        }*/
    }//salesAdmin 클래스 종료

    // 문의사항 탭 클래스
    class QnaAdmin extends JPanel implements ActionListener {

        public QnaAdmin() {
            //TODO : 제목, 아이디, 내용 text DB 연결하기

            DBconnection qnaDB = new DBconnection("SELECT * from parking.question;", rowData3, qnaJTable);

            qnaAddButton.addActionListener(this);
            qnaDeleteButton.addActionListener(this);
        }

        //TODO : 문의 답변, 삭제 이벤트리스너 따로 클래스 빼기
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "답변 작성": ; break;
                case "삭제" : int question_delete = JOptionPane.showConfirmDialog(admin, "정말 문의를 삭제 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
                    if (question_delete == YES_OPTION) {
                        //TODO : DB에서 계정 삭제
                        JOptionPane.showMessageDialog(admin, "문의가 삭제되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    }; break;
            }
        }
    }// QnaAdmin 클래스 종료

    // 공지사항 탭 클래스
    class NoticeAdmin extends JPanel implements ActionListener {

        public NoticeAdmin() {
            //TODO : 제목, 내용 text DB 연결하기
            noticeAddButton.addActionListener(this);
            noticeChangeButton.addActionListener(this);
            noticeDeleteButton.addActionListener(this);

            DBconnection noticeDB = new DBconnection("SELECT * from parking.notice;", rowData4, noticeJTable);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "추가" :  break; //TODO : 추가창 만들기
                case "수정" : break; //TODO : DB 수정
                case "삭제" : int notice_delete = JOptionPane.showConfirmDialog(admin, "정말 공지사항을 삭제 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
                    if (notice_delete == YES_OPTION) {
                        //TODO : DB에서 계정 삭제
                        JOptionPane.showMessageDialog(admin, "공지사항이 삭제되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    }; break;
            }
        }
    } //noticeAdmin 종료
}//mainTest 클래스 종료

    class PwChange extends JDialog implements ActionListener{
        private JPasswordField currentPw, newPw, rePw;
        private JButton pass, check, ok, cancel;
        private JPanel p;

        public PwChange(JFrame parent, String title, boolean modal) {
            super(parent, title, modal);

            p = new JPanel();
            p.setLayout(new GridLayout(4, 3));

            currentPw = new JPasswordField(10);
            pass = new JButton("확인");
            pass.addActionListener(this);

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

            ok = new JButton("변경");
            ok.setEnabled(false);
            ok.addActionListener(this);
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

        @Override
        public void actionPerformed(ActionEvent ae) {
            String sql = "", s = ae.getActionCommand();
            ResultSet result;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                System.err.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

                AdminMain.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", AdminMain.user_name, AdminMain.password);
                System.out.println("연결 완료!");

                Statement stmt = AdminMain.con.createStatement();

                switch (s){
                    case "확인" : sql = "select manager_pw from parking.manager where manager_pw='"+ currentPw.getText() +"';";
                        result = stmt.executeQuery(sql);
                        if (result.next()) {
                            JOptionPane.showMessageDialog(p, "비밀번호가 동일합니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                            newPw.setEditable(true);
                            rePw.setEditable(true);
                            check.setEnabled(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(p, "비밀번호가 동일하지 않습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                            currentPw.setText("");
                        }
                        break;
                    case "변경" : sql = "update parking.manager set manager_pw = '"+ newPw.getText() +"' where (manager_pw = '"+ currentPw.getText() +"');";
                        stmt.executeQuery(sql);
                        JOptionPane.showMessageDialog(p, "비밀번호가 변경되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
                stmt.close();
                AdminMain.con.close();
            } catch (SQLException e) {
                System.err.println("연결 오류" + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.err.println("드라이버 로드에 실패했습니다.");
            }
        }
    }//Pwchange class 종료

    class DBconnection {
        //DB를 위한 변수
        String sql = "";
        Vector<Vector<String>> rowData;
        JTable table;

        public DBconnection(String sql, Vector<Vector<String>> rowData, JTable table) {
            sql = sql;
            this.rowData = rowData;
            this.table = table;
            String t = table.getName();
            // JDBC 드라이버 로드
            try {
                Class.forName("com.mysql.jdbc.Driver");
                System.err.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

                AdminMain.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", AdminMain.user_name, AdminMain.password);
                System.out.println("연결 완료!");

                Statement stmt = AdminMain.con.createStatement();

                ResultSet result = stmt.executeQuery(sql);    //DB로부터 읽어온 데이터

                while (result.next()) {  //DB에서 읽어와 표에 출력하기
                    Vector<String> txt = new Vector<String>();
                    // 한 레코드를 읽으면 1차원 벡터로 만들어 표에 한 행씩 추가
                    switch (t) {
                        case "userTable": {
                            txt.add(result.getString("id"));
                            txt.add(result.getString("name"));
                            txt.add(result.getString("phone_num"));
                            txt.add(result.getString("car_num"));
                            txt.add(result.getString("point"));
                        }
                        break;
                        case "userParkingTable": {
                            txt.add(result.getString("car_in"));
                            txt.add(result.getString("area"));
                            txt.add(result.getString("total_fee"));
                        }
                        break;
                        case "salesTable": {
                            txt.add(result.getString("car_in"));
                            //txt.add(resultAllUser.getString("car_num"));
                            txt.add(result.getString("total_fee"));
                            txt.add(result.getString("user_id"));
                        }
                        break;
                        case "qnaJTable": {
                            txt.add(result.getString("question_id"));
                            txt.add(result.getString("question_title"));
                            txt.add(result.getString("question_contents"));
                            txt.add(result.getString("question_date"));
                            txt.add(result.getString("user_id"));
                            break;
                        }
                        case "noticeJtable": {
                            txt.add(result.getString("notice_id"));
                            txt.add(result.getString("notice_title"));
                            txt.add(result.getString("notice_contents"));
                            txt.add(result.getString("notice_date"));
                            break;
                        }
                    }
                    rowData.add(txt);
                    table.updateUI();
                }
                stmt.close();
                AdminMain.con.close();
            } catch (SQLException e) {
                System.err.println("연결 오류" + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.err.println("드라이버 로드에 실패했습니다.");
            }
        }
    }

