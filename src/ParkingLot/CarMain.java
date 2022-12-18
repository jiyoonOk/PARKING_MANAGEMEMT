package ParkingLot;

import javax.swing.*;

class CarMain {
    public static void main(String[] args) {
        Car win = new Car("user2");
        win.setSize(500, 600);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
}
