package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class RoundedJPanel extends  JPanel{
    private Shape shape;

    public RoundedJPanel(GridBagLayout gridBagLayout){
        super(gridBagLayout);
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0,0,getWidth(), getHeight(),50, 50);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(new Color(0x0FFFFFF, true));
        g.drawRoundRect(0,0,getWidth(), getHeight(), 50,50);
    }
}

class FoodBilling extends JFrame{
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    FoodBilling(){
        System.out.println("WIDTH IS: "+screenSize.width+"HEIGHT IS"+screenSize.height);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc0 = new GridBagConstraints();

        RoundedJPanel logCard = new RoundedJPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        logCard.setBackground(new Color(0xFFFFFF));
        logCard.setPreferredSize(new Dimension(400, 300));

        gbc.ipadx = 10;
        gbc.ipady = 10;
        gbc.weighty = 1.0;
        JLabel userLabel = new JLabel();
        userLabel.setText("User ID");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        logCard.add(userLabel, gbc);

        JTextField textField1 = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        logCard.add(textField1, gbc);

        JLabel passLabel = new JLabel();
        passLabel.setText("Password");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.ipadx = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        logCard.add(passLabel, gbc);

        JTextField textField2 = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        logCard.add(textField2, gbc);

        JButton b1 = new JButton("SUBMIT");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        logCard.add(b1, gbc);
        logCard.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(logCard, gbc0);

        setTitle("Food Billing System - Log in/up");
        getContentPane().setBackground(new Color(0xD2D2D2));
        setSize(screenSize.width, screenSize.height);
        setVisible(true);
    }
}

public class Main {
    public static void main(String[] args) {
        new FoodBilling();
    }
}
