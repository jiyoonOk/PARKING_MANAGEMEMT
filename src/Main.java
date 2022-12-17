import Login.Login;
import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        Login login = new Login();
        login.setTitle("LOGIN");
        login.setSize(400, 600);
        login.setLocationRelativeTo(null);
        login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        login.setVisible(true);
    }
}
