package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FoodSelector {
    JFrame jFrame = new JFrame();
    GridLayout mainFrameLay;
    JMenuBar navBar;
    JMenu FileComp;
    JMenuItem ThemeOpt;
    JSplitPane FoodAndBill;
    JMenuItem Settings;
    FoodSelector(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame = new JFrame();
        mainFrameLay = new GridLayout();
        navBar = new JMenuBar();
        FileComp = new JMenu("File");
        ThemeOpt = new JMenuItem("Theme");
        Settings = new JMenuItem("Settings");

        ThemeOpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.getContentPane().setBackground(new Color(0x262626));
            }
        });
        FoodSelect foodSelect = new FoodSelect();
        BillPlotter billPlotter = new BillPlotter();

        FoodAndBill = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        FoodAndBill.setDividerLocation(screenSize.width/2);
        FoodAndBill.setLeftComponent(foodSelect);
        FoodAndBill.setRightComponent(billPlotter);



        FileComp.add(ThemeOpt);
        FileComp.add(Settings);
        navBar.add(FileComp);
        jFrame.setJMenuBar(navBar);

        jFrame.add(FoodAndBill);
        jFrame.setLayout(mainFrameLay);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit t=Toolkit.getDefaultToolkit();
        Image image = t.getImage(getClass().getResource("appIcon/Fud-Icon.png"));
        jFrame.setIconImage(image);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        jFrame.setTitle("Food Selection - Food Billing System");
        jFrame.setSize(screenSize.width, screenSize.height);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new FoodSelector();
    }
}
