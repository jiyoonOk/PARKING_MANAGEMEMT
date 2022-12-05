package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FloorCBox extends JFrame implements ActionListener {
    String[] floor_list = {"B1", "B2"}; //층 목록(B1 기본), 상수, 데베
    public FloorCBox() {
        JComboBox floorCBox = new JComboBox(floor_list); //층 선택
        add(floorCBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("B1")) {
            //주차장 B1으로 변경
        } else if (e.getActionCommand().equals("B2")) {
            //주차장 B2로 변경
        }
    }

}
