package com.company;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.InternalFrameUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.company.WrapLayout;

class FoodSelector {
    FoodSelector(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame jFrame = new JFrame();
        JMenuBar navBar = new JMenuBar();
        JMenu FileComp = new JMenu("File");
        JMenuItem ThemeOpt = new JMenuItem("Theme");
        ThemeOpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.getContentPane().setBackground(new Color(0x262626));
            }
        });
        JMenuItem Settings = new JMenuItem("Settings");

        JPanel foodCategory = new JPanel();
        foodCategory.setBackground(new Color(232323));
        JButton[] catBtn = new JButton[50];
        for (int i=0; i<50; i++){
            catBtn[i] = new JButton("btn"+i);
            catBtn[i].setPreferredSize(new Dimension(23,23));
            catBtn[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            foodCategory.add(catBtn[i]);
        }
        JPanel foodDishes = new JPanel();
        foodDishes.setBackground(new Color(0x5B03D7));
        JPanel orderSection = new JPanel();
        JSplitPane foodPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, foodCategory, foodDishes);
        JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, foodPane, orderSection);



        foodCategory.setLayout(new BoxLayout(foodCategory, BoxLayout.PAGE_AXIS));
        JScrollPane categoryPane = new JScrollPane(foodCategory);
        categoryPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        categoryPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        foodDishes.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        JScrollPane dishPane = new JScrollPane(foodDishes);
        dishPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        dishPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JButton[] disBtn = new JButton[50];
        for (int i=0; i<50; i++){
            disBtn[i] = new JButton("btn"+i);
            disBtn[i].setPreferredSize(new Dimension(100,100));
            foodDishes.add(disBtn[i]);
        }

        orderSection.setBackground(new Color(0xE0E0E0));

        FileComp.add(ThemeOpt);
        FileComp.add(Settings);
        navBar.add(FileComp);
        jFrame.setJMenuBar(navBar);

        jFrame.add(mainPane);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Food Selection - Food Billing System");
        jFrame.setSize(screenSize.width, screenSize.height);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new FoodSelector();
    }
}
