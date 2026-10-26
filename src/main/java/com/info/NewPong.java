package com.info;

import javax.swing.*;

public class NewPong extends JFrame {

    public NewPong(){
        setTitle("Tenis v1.2");
        setSize(1000,733);
        add(new Campo(1000, 733));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args){
        new NewPong();
    }
}
