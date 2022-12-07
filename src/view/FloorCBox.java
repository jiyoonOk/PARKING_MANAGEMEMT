package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
class ComboBox extends JFrame implements ActionListener{
    String[] floor_list = {"B1", "B2", "B3"}; //층 목록(B1 기본), 상수, 데베
    JComboBox floorCBox; //층 선택
    ComboBox(JPanel p){
        floorCBox = new JComboBox(floor_list);
        floorCBox.addActionListener(this);
        p.add(floorCBox);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("B1")) {
            //주차장 B1으로 변경
        } else if (e.getActionCommand().equals("B2")) {
            //주차장 B2로 변경
        }
    }
}//TODO : 콤보박스 클래스 따로 만들고 싶은데..



