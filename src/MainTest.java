import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainTest extends JFrame {
    static MainTest admin;
    //main에 필요한 변수
    private JTabbedPane tab;
    private JPanel jp, btnjp;
    private JButton pwChangeB, logoutB;
    private JPanel salesAdminJp, userAdminJp;

    //userAdmin에 필요한 변수
    private JTextField userSearch;
    private JTable allUsertable;
    private JPanel userInfoPanel;
    private JTextField nameField, idField, carField, numberField, cardField;
    private JButton deleteUserButton, changeUserButton;
    private JTable userParkList;

    //salesAdmin에 필요한 변수
    private JComboBox purchaseCombo;
    private JRadioButton yearRB, halfRB, monthRB, weekRB, dayRB;
    private JLabel purchaseLabel;
    private JTable salesTable;

    public MainTest() {
        super("주차관리예약시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Container ct = getContentPane();

        tab = new JTabbedPane();
        userAdmin user = new userAdmin();
        salesAdmin sales = new salesAdmin();

        tab.addTab("회원정보관리", user);
        tab.addTab("매출관리", sales);

        ct.add(jp);

        pack();
        setVisible(true);

    } //mainTest 생성자 종료

    public static void main(String[] args) {
        admin = new MainTest();
        admin.setLocationRelativeTo(null);
    }

    class userAdmin extends JPanel {
        public userAdmin() {
            TextHint hint = new TextHint(userSearch, "ID를 입력하세요.");
            changeUserButton.addActionListener(new ActionListener() {
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
                    } else {
                    }
                }
            });
            userSearch.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //TODO : 테이블에서 ID 검색 결과 출력
                    System.out.println("ID : " + e.getActionCommand() + " 를 검색하겠습니다.");
                }
            });
        }

    }//user admin 끝

    class salesAdmin extends JPanel implements ActionListener {

        public salesAdmin() {

            ButtonGroup g = new ButtonGroup();
            yearRB.addActionListener(this);
            halfRB.addActionListener(this);
            monthRB.addActionListener(this);
            weekRB.addActionListener(this);
            g.add(yearRB);
            g.add(halfRB);
            g.add(monthRB);
            g.add(weekRB);

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
}//mainTest 클래스 종료

