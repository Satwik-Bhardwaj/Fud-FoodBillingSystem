package com.company;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
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
        FoodAndBill.setUI(new BasicSplitPaneUI()
        {
            @Override
            public BasicSplitPaneDivider createDefaultDivider()
            {
                return new BasicSplitPaneDivider(this)
                {
                    public void setBorder(Border b) {}

                    @Override
                    public void paint(Graphics g)
                    {
                        g.setColor(new Color(0x0FFFFFF, true));
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });
        FoodAndBill.setBorder(null);
        FoodAndBill.setDividerLocation(screenSize.width/2);
        FoodAndBill.setDividerSize(3);
        FoodAndBill.setContinuousLayout(true);
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
