package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Unknown Land");
        window.setSize(1280, 1024);

        GameLoop gameLoop = new GameLoop();
        window.add(gameLoop);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
