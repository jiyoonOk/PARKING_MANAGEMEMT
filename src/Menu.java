
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class Menu extends JFrame{
	public Menu (String title) {
		setTitle(title);
		JLabel mTitle = new JLabel("MENU");
		mTitle.setFont(mTitle.getFont().deriveFont(45.0f));
		
		JButton mMypage = new JButton("마이페이지");
		JButton mNotice = new JButton("공지사항");
		JButton mQuest = new JButton("문의사항");
		JButton mAbout = new JButton("ABOUT");
		JButton mLogout = new JButton("LOGOUT");
		
		//위치, 크기 설정
		mTitle.setBounds(130, 10, 200, 100);
		mMypage.setBounds(150, 180, 100, 30);
		mNotice.setBounds(150, 230, 100, 30);
		mQuest.setBounds(150, 280, 100, 30);
		mAbout.setBounds(150, 330, 100, 30);
		mLogout.setBounds(250, 430, 100, 30);
		
		Container menuCt = getContentPane();
		menuCt.setLayout(null);
		menuCt.add(mTitle);
		menuCt.add(mMypage);
		menuCt.add(mNotice);
		menuCt.add(mQuest);
		menuCt.add(mAbout);
		menuCt.add(mLogout);

	}


}
