package Admin;

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
    static String password = "root"; // 접속자 pw

    public AdminMain() {
        super("주차관리예약시스템");
        $$$setupUI$$$();
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
                rowClickedPrimaryKey = noticeJTable.getModel().getValueAt(row, 0).toString();

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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        jp = new JPanel();
        jp.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tab = new JTabbedPane();
        jp.add(tab, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        userAdminJp = new JPanel();
        userAdminJp.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 12, new Insets(0, 0, 0, 0), -1, -1));
        tab.addTab("회원정보관리", userAdminJp);
        userSearch = new JTextField();
        userSearch.setText("");
        userAdminJp.add(userSearch, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 6, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("회원 상세 정보");
        userAdminJp.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 10, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 3, new Insets(0, 0, 0, 0), -1, -1));
        userAdminJp.add(userInfoPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 10, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nameField = new JTextField();
        nameField.setEditable(false);
        userInfoPanel.add(nameField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 2, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(15, -1), null, 0, false));
        idField = new JTextField();
        idField.setEditable(false);
        userInfoPanel.add(idField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(15, -1), null, 0, false));
        carField = new JTextField();
        carField.setEditable(false);
        userInfoPanel.add(carField, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(15, -1), null, 0, false));
        numberField = new JTextField();
        numberField.setEditable(false);
        userInfoPanel.add(numberField, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(15, -1), null, 0, false));
        cardField = new JTextField();
        cardField.setEditable(false);
        userInfoPanel.add(cardField, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(15, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("이름 : ");
        userInfoPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 16), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("아이디 : ");
        userInfoPanel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 16), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("차량 번호 : ");
        userInfoPanel.add(label4, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 16), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("전화 번호 : ");
        userInfoPanel.add(label5, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 16), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("카드 번호 : ");
        userInfoPanel.add(label6, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 16), null, 0, false));
        deleteUserButton = new JButton();
        deleteUserButton.setEnabled(false);
        deleteUserButton.setText("계정 삭제");
        userInfoPanel.add(deleteUserButton, new com.intellij.uiDesigner.core.GridConstraints(6, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        changeUserButton = new JButton();
        changeUserButton.setEnabled(false);
        changeUserButton.setText("계정 변경");
        userInfoPanel.add(changeUserButton, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, 30), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("회원 주차 이력");
        userAdminJp.add(label7, new com.intellij.uiDesigner.core.GridConstraints(2, 10, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userAdminJp.add(userScrollPane, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 3, 10, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        userScrollPane.setViewportView(userTable);
        userAdminJp.add(userParkingScrollPane, new com.intellij.uiDesigner.core.GridConstraints(3, 10, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 150), null, 0, false));
        userParkingScrollPane.setViewportView(userParkingTable);
        salesAdminJp = new JPanel();
        salesAdminJp.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 8, new Insets(0, 0, 0, 0), -1, -1));
        tab.addTab("매출관리", salesAdminJp);
        purchaseCombo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("전체");
        defaultComboBoxModel1.addElement("결제완료");
        defaultComboBoxModel1.addElement("결제취소");
        purchaseCombo.setModel(defaultComboBoxModel1);
        salesAdminJp.add(purchaseCombo, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        monthRB = new JRadioButton();
        monthRB.setText("1개월");
        salesAdminJp.add(monthRB, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        weekRB = new JRadioButton();
        weekRB.setText("일주일");
        salesAdminJp.add(weekRB, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        purchaseLabel = new JLabel();
        purchaseLabel.setText("");
        salesAdminJp.add(purchaseLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 8, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dayRB = new JRadioButton();
        dayRB.setFocusCycleRoot(false);
        dayRB.setOpaque(true);
        dayRB.setSelected(true);
        dayRB.setText("오늘");
        salesAdminJp.add(dayRB, new com.intellij.uiDesigner.core.GridConstraints(0, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        salesAdminJp.add(salesScrollPane, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 8, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        salesTable.setPreferredScrollableViewportSize(new Dimension(800, 700));
        salesScrollPane.setViewportView(salesTable);
        halfRB = new JRadioButton();
        halfRB.setText("6개월");
        salesAdminJp.add(halfRB, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        yearRB = new JRadioButton();
        yearRB.setText("1년");
        salesAdminJp.add(yearRB, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        total = new JLabel();
        total.setText("Label");
        salesAdminJp.add(total, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        qnaJp = new JPanel();
        qnaJp.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        tab.addTab("문의사항 관리", qnaJp);
        final JLabel label8 = new JLabel();
        label8.setText("제목 : ");
        qnaJp.add(label8, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        qnaTitleField = new JTextField();
        qnaTitleField.setEditable(false);
        qnaJp.add(qnaTitleField, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        qnaContensArea = new JTextArea();
        qnaContensArea.setEditable(false);
        qnaContensArea.setLineWrap(true);
        qnaContensArea.setWrapStyleWord(true);
        qnaJp.add(qnaContensArea, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(50, 50), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("내용 : ");
        qnaJp.add(label9, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        qnaDeleteButton = new JButton();
        qnaDeleteButton.setEnabled(false);
        qnaDeleteButton.setText("삭제");
        qnaJp.add(qnaDeleteButton, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        qnaAddButton = new JButton();
        qnaAddButton.setEnabled(false);
        qnaAddButton.setText("답변 작성");
        qnaJp.add(qnaAddButton, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("I  D :  ");
        qnaJp.add(label10, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        qnaIDField = new JTextField();
        qnaIDField.setEditable(false);
        qnaJp.add(qnaIDField, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        qnaJp.add(qnaScrollPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        qnaScrollPane.setViewportView(qnaJTable);
        noticeJp = new JPanel();
        noticeJp.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 5, new Insets(0, 0, 0, 0), -1, -1));
        tab.addTab("공지사항", noticeJp);
        noticeTitleField = new JTextField();
        noticeTitleField.setEditable(false);
        noticeJp.add(noticeTitleField, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        noticeContentsArea = new JTextArea();
        noticeContentsArea.setEditable(false);
        noticeContentsArea.setLineWrap(true);
        noticeContentsArea.setText("");
        noticeContentsArea.setWrapStyleWord(true);
        noticeJp.add(noticeContentsArea, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 2, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(50, 50), null, 0, false));
        noticeDeleteButton = new JButton();
        noticeDeleteButton.setEnabled(false);
        noticeDeleteButton.setText("삭제");
        noticeJp.add(noticeDeleteButton, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(5, -1), null, 0, false));
        noticeChangeButton = new JButton();
        noticeChangeButton.setEnabled(false);
        noticeChangeButton.setText("수정");
        noticeJp.add(noticeChangeButton, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(5, -1), null, 0, false));
        noticeAddButton = new JButton();
        noticeAddButton.setText("추가");
        noticeJp.add(noticeAddButton, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 2, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(5, -1), null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("내용 : ");
        noticeJp.add(label11, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("제목 : ");
        noticeJp.add(label12, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        noticeJp.add(noticeScrollPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 5, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        noticeJTable.setAutoCreateRowSorter(false);
        noticeScrollPane.setViewportView(noticeJTable);
        btnjp = new JPanel();
        btnjp.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        jp.add(btnjp, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pwChangeB = new JButton();
        pwChangeB.setText("비밀번호변경");
        btnjp.add(pwChangeB, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        btnjp.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(410, 11), null, 0, false));
        logoutB = new JButton();
        logoutB.setText("로그아웃");
        btnjp.add(logoutB, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jp;
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
                    String sql = "select * from parking.user where id like '%" + userSearch.getText() + "%' OR name like '%" + userSearch.getText() + "%' OR car_num like '%" + userSearch.getText() + "%' OR phone_num like '%" + userSearch.getText() + "%';";
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

            qnaDB = new DBconnection("SELECT * from parking.Question order by question_date;", rowData3, qnaJTable);
            qnaDB.JTableUpdate();

            qnaAddButton.addActionListener(this);
            qnaDeleteButton.addActionListener(this);
        }

        //문의 답변, 삭제
        @Override
        public void actionPerformed(ActionEvent e) {
            int a = Integer.parseInt(rowClickedPrimaryKey);
            switch (e.getActionCommand()) {
                case "답변 작성": {
                    if (qnaTitleField.getText() == null) {
                        JOptionPane.showMessageDialog(admin, "답변할 문의글부터 선택해주세요!!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        AddText add = new AddText(a);
                    }
                }
                break;
                case "삭제": {
                    int question_delete = JOptionPane.showConfirmDialog(admin, "정말 문의를 삭제 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
                    if (question_delete == YES_OPTION) {

                        usingDB.DelectAttribute("Question");

                        JOptionPane.showMessageDialog(admin, "문의가 삭제되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    }
                    ;
                }
                break;
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
                }
                break;
                case "수정": {
                }
                break;   //TODO : DB 수정
                case "삭제": {
                    int notice_delete = JOptionPane.showConfirmDialog(admin, "정말 공지사항을 삭제 하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
                    if (notice_delete == YES_OPTION) {

                        usingDB.DelectAttribute("notice");

                        JOptionPane.showMessageDialog(admin, "공지사항이 삭제되었습니다!", "알림창", JOptionPane.INFORMATION_MESSAGE);
                    }
                    ;
                }
                break;
            }
            noticeDB.JTableUpdate();
        }
    } //noticeAdmin 종료
}//AdminMain 클래스 종료


